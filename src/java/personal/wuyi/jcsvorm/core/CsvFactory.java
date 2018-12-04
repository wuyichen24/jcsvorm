/*
 * Copyright 2018 Wuyi Chen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package personal.wuyi.jcsvorm.core;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.reflect.FieldUtils;

import com.google.common.base.Strings;

import personal.wuyi.io.file.csv.CSVReader;
import personal.wuyi.io.file.csv.HeaderOption;
import personal.wuyi.jcsvorm.annotation.CsvColumn;
import personal.wuyi.jcsvorm.annotation.CsvEntity;
import personal.wuyi.jcsvorm.api.Function;
import personal.wuyi.reflect.ReflectUtil;

/**
 * Object-Relational Mapping (ORM) with CSV data entities.
 * 
 * @author  Wuyi Chen
 * @date    03/14/2017
 * @version 1.1
 * @since   1.1
 */
public class CsvFactory {
	private CsvFactory() {}
	
	/**
	 * Read and parse a CSV file into a list of objects in a certain type.
	 * 
	 * @param  clazz
	 *         The type of the object you want to convert to.
	 *         
	 * @param  csvFilePath
	 *         The path of the CSV file.
	 * 
	 * @param  header
	 *         The option to specify the CSV file has the first line as header 
	 *         or not.
	 * 
	 * @return  A list of objects in certain type.
	 * 
	 * @throws  IOException
	 *          If the CSV file is not existing for given path.
	 * 
	 * @throws  IllegalAccessException
	 *          If a field in a Java class is enforcing Java language access 
	 *          control and the underlying field is either inaccessible or final.
	 * 
	 * @throws  ParseException
	 *          If there is an error occurred when parsing a value in CSV file.
	 *          
	 * @since   1.1
	 */
	public static <T> List<T> readCsv(final Class<T> clazz, final String csvFilePath, final HeaderOption header) throws IOException, IllegalAccessException, ParseException {
		final List<CSVRecord> recordList = getRecord(csvFilePath, header);
		return transformCsvRecords(recordList, clazz, header);
	}
	
	/**
	 * Write a CSV file from a list of objects in a certain type.
	 * 
	 * <p>The order of columns is based on the {@code pos} parameter of the 
	 * {@code CsvColumn} on each field.
	 * 
	 * @param  tList
	 *         A list of objects needs to be exported, element is by row.
	 * 
	 * @param  csvFilePath
	 *         The path of the CSV file.
	 * 
	 * @param  header
	 *         The option to specify CSV file has the first line as header or 
	 *         not.
	 * 
	 * @throws  IllegalAccessException
	 *          If a field in a Java class is enforcing Java language access 
	 *          control and the underlying field is either inaccessible or final.
	 * 
	 * @throws  IOException
	 *          If there is an error occurred when writing a CSV file.
	 *          
	 * @since   1.1
	 */
	public static <T> void writeCsv(final List<T> tList, final String csvFilePath, final HeaderOption header) throws IllegalAccessException, IOException {
		final Map<Integer, List<String>> map = transformObjects(tList, header);
		writeCsvFile(csvFilePath, map);
	}
	
	/**
	 * Write a CSV file from a list of objects in a certain type.
	 * 
	 * @param  tList
	 *         A list of objects needs to be exported, element is by row.
	 *         
	 * @param  csvFilePath
	 *         The path of the CSV file.
	 * 
	 * @param  columnList
	 *         The list of column names.
	 * 
	 * @param  header
	 *         The option to specify CSV file has the first line as header or 
	 *         not.
	 *         
	 * @throws  IllegalAccessException
	 *          If a field in a Java class is enforcing Java language access 
	 *          control and the underlying field is either inaccessible or final.
	 * 
	 * @throws  IOException
	 *          If there is an error occurred when writing a CSV file.
	 *          
	 * @since   1.1  
	 */
	public static <T> void writeCsv(final List<T> tList, final String csvFilePath, final List<String> columnList, final HeaderOption header) throws IllegalAccessException, IOException {
		final Map<Integer, List<String>> map = transformObjects(tList, columnList, header);
		writeCsvFile(csvFilePath, map);
	}
	
	/**
	 * Read CSV file as a list of CSVRecord.
	 * 
	 * @param  csvFilePath
	 *         The path of the CSV file.
	 *         
	 * @param  header
	 *         The option to specify CSV file has the first line as header or not.
	 *         
	 * @return  A list of CSVRecord.
	 * 
	 * @throws  IOException
	 *          If the CSV file is not existing for given path.
	 *          
	 * @since   1.1
	 */
	protected static List<CSVRecord> getRecord(final String csvFilePath, final HeaderOption header) throws IOException {
		final CSVReader reader = new CSVReader(csvFilePath, CSVFormat.DEFAULT, header);
		return reader.getCsvEntity().getRecords();
	}
	
	/**
	 * Transform a list of CSVRecord to a list of objects in a certain type.
	 * 
	 * @param  list
	 *         A list of CSVRecord.
	 *         
	 * @param  clazz
	 *         The type of the object you want to convert to.
	 *         
	 * @param  header
	 *         The option to specify CSV file has the first line as header or 
	 *         not.
	 *         
	 * @return  A list of objects in certain type.
	 *          
	 * @throws  IllegalAccessException
	 *          If a field in a Java class is enforcing Java language access 
	 *          control and the underlying field is either inaccessible or final.
	 *          
	 * @throws  ParseException
	 *          If there is an error occurred when parsing a value in CSV file.
	 *          
	 * @since   1.1
	 */
	protected static <T> List<T> transformCsvRecords(final List<CSVRecord> list, final Class<T> clazz, final HeaderOption header) throws IllegalAccessException, ParseException {
		checkClassHasCsvEntityAnnotation(clazz);
		
		verifyParametersInCsvColumnAnnotation(clazz, header);
		
		return CsvPipe.map(list, clazz, new Function<CSVRecord, T>() {
			@Override
			public T call(CSVRecord record, Class<T> clazz) throws IllegalAccessException, ParseException {
				final List<Field> fieldList = FieldUtils.getFieldsListWithAnnotation(clazz, CsvColumn.class);
				final T t = ReflectUtil.getInstance(clazz, true);
				
				for (Field field : fieldList) {
					final CsvColumn column = field.getAnnotation(CsvColumn.class);
		            
					field.setAccessible(true);
		            if (ReflectUtil.isStaticField(field)) {
		            	throw new IllegalArgumentException(field.getName() + " field can not be static.");
		            } else if (!Strings.isNullOrEmpty(column.name())) {                      // if name is not empty, use name to locate the column first
		            	if (record.isMapped(column.name())) {                                //     if the column is mapped, transform the value
		            		setValue(t, field, column, record.get(column.name()));       
		            	} else if (column.required()) {                                      //     if the column is not mapped but it is required, throw an exception
		            		throw new IllegalArgumentException("The column " + column.name() + " is required in the CSV file, but it is missing in the CSV file right now.");
		            	}                                                                    //     if the column is not mapped but it is not required, ignore that column
		            } else if (column.pos() != -Integer.MAX_VALUE) {                         // if name is empty use index to locate the column
		            	setValue(t, field, column, record.get(column.pos()));
		            }
				}
				
				return t;
			}
		});
	}
	
	/**
	 * Check the Java class has annotated by {@code CsvEntity}.
	 * 
	 * @param  clazz
	 *         The Java class needs to be checked.
	 *         
	 * @since   1.1
	 */
	protected static void checkClassHasCsvEntityAnnotation(final Class<?> clazz) {
		final CsvEntity entity = clazz.getAnnotation(CsvEntity.class); 
		if (entity == null) {
			throw new IllegalArgumentException(clazz.getSimpleName() + " needs to have " + CsvEntity.class.getSimpleName() + " anntation.");
		}
	}
	
	/**
	 * Check the parameters in each {@code CsvColumn} annotation based on 
	 * different header options.
	 * 
	 * <p>This function is to check the minimum requirement for doing the 
	 * column mapping between the fields in the Java class and the columns in 
	 * the CSV file. For writing CSV files, if some parameters are missing, it 
	 * can use the default order in the list of fields. But for reading CSV 
	 * files, some parameter are mandatory otherwise the program can not know 
	 * which column needs to map to which field.
	 * 
	 * <p>There are 2 rules for {@code CsvColumn} annotation:
	 * <ul>
	 * 	<li>If you specify the CSV file is without the header line, the 
	 *      {@code CsvColumn} annotation should have {@code pos} parameter.
	 *  <li>If you specify the CSV file is with the header line, the 
	 *      {@code CsvColumn} annotation should have one of {@code name} 
	 *      parameter or {@code pos} parameter at least.
	 * </ul>
	 * 
	 * @param  clazz
	 *         The type of the object you want to convert to.
	 *         
	 * @param  header
	 *         The option to specify CSV file has the first line as header or not.
	 *         
	 * @since   1.1
	 */
	protected static <T> void verifyParametersInCsvColumnAnnotation(final Class<T> clazz, final HeaderOption header) {
		final List<Field> fieldList = FieldUtils.getFieldsListWithAnnotation(clazz, CsvColumn.class);
			
		for (Field field : fieldList) {
			final CsvColumn column = field.getAnnotation(CsvColumn.class);
	        
	        if (header == HeaderOption.WITHOUT_HEADER) {
	        	if (column.pos() == -Integer.MAX_VALUE) {
	        		throw new IllegalArgumentException("For field " + field.getName() + ", pos parameter is required for the CsvColumn annotation when the CSV file doesn't have header line.");
	        	}
			} else if (header == HeaderOption.WITH_HEADER && column.pos() == -Integer.MAX_VALUE && column.name().isEmpty()) {
	        	throw new IllegalArgumentException("For field " + field.getName() + ", must specify at least one parameter among pos and name in the CsvColumn annotation when the CSV file has a header line.");
	        }
		}
	}
	
	/**
	 * Transform a list of T objects to a hash map for aligning up columns 
	 * (based on the pos parameter in the {@code CsvColumn} annotation).
	 * 
	 * <p>This function will transform a list of T objects to the hash map for 
	 * aligning up columns. Each entry in the hash map represents one column 
	 * in the CSV file, the key will be ordinal numbers starting from 0 to 
	 * indicate the order of columns, the value will be a list of values in 
	 * that column.
	 * 
	 * <p>This function will also check the {@code pos} parameters in all the 
	 * {@code CsvColumn} are ordinal numbers or not. If yes, it will use the 
	 * ordinal order of the {@code pos} value as the order of the columns.  
	 * 
	 * @param  tList
	 *         The list of T objects.
	 * 
	 * @param  header
	 *         The option to specify the CSV file has the first line as header 
	 *         or not.
	 *         
	 * @return  The hash map for storing all the data separated by columns.
	 * 
	 * @throws  IllegalAccessException
	 *          If a field in a Java class is enforcing Java language access 
	 *          control and the underlying field is either inaccessible or 
	 *          final.
	 *          
	 * @since   1.1
	 */
	protected static <T> Map<Integer, List<String>> transformObjects(final List<T> tList, final HeaderOption header) throws IllegalAccessException {		
		final Class<? extends Object> clazz = tList.get(0).getClass();
		checkClassHasCsvEntityAnnotation(clazz);
		final List<Field> fieldList = FieldUtils.getFieldsListWithAnnotation(clazz, CsvColumn.class);
		
		if (checkPosSequentialAndNoRepeat(fieldList)) {
			sortFieldListByPos(fieldList);
		}
		
		return transformObjectsIntoColumnBasedMap(tList, fieldList, header);
	}
	
	/**
	 * Transform a list of T objects to a hash map for aligning up columns.
	 * (Based on the order in the column name)
	 * 
	 * <p>This function will transform a list of T objects to the hash map for 
	 * aligning up columns. Each entry in the hash map represents one column 
	 * in the CSV file, the key will be ordinal numbers starting from 0 to 
	 * indicate the order of columns, the value will be a list of values in 
	 * that column.
	 * 
	 * <p>This function will use the order in the list of column names to 
	 * decide the column order in the CSV file.
	 * 
	 * @param  tList
	 *         The list of T objects.
	 * 
	 * @param  columnList
	 *         The list of column names.
	 * 
	 * @param  header
	 *         The option to specify the CSV file has the first line as header 
	 *         or not.
	 * 
	 * @return  The hash map for storing all the data separated by columns.
	 * 
	 * @throws  IllegalAccessException
	 *          If a field in a Java class is enforcing Java language access 
	 *          control and the underlying field is either inaccessible or 
	 *          final.
	 *          
	 * @since   1.1    
	 */
	protected static <T> Map<Integer, List<String>> transformObjects(final List<T> tList, final List<String> columnList, final HeaderOption header) throws IllegalAccessException {
		final Class<? extends Object> clazz = tList.get(0).getClass();
		checkClassHasCsvEntityAnnotation(clazz);
		final List<Field> fieldList = FieldUtils.getFieldsListWithAnnotation(clazz, CsvColumn.class);
		
		List<Field> sortedFieldList = sortFieldListByColumnList(clazz, fieldList, columnList);
		
		return transformObjectsIntoColumnBasedMap(tList, sortedFieldList, header);
	}
	
	/**
	 * Transform a list of T objects to a hash map based on the sorted 
	 * {@code Field} list.
	 * 
	 * <p>The sorted {@code Field} list decides the order of columns.
	 * 
	 * @param  tList
	 *         The list of T objects.
	 * 
	 * @param  sortedFieldList
	 *         The sorted {@code Field} list.
	 * 
	 * @param  header
	 *         The option to specify the CSV file has the first line as header 
	 *         or not.
	 * 
	 * @return  The hash map for storing all the data separated by columns.
	 * 
	 * @throws  IllegalAccessException
	 *          If a field in a Java class is enforcing Java language access 
	 *          control and the underlying field is either inaccessible or 
	 *          final.
	 *          
	 * @since   1.1
	 */
	protected static <T> Map<Integer, List<String>> transformObjectsIntoColumnBasedMap(final List<T> tList, final List<Field> sortedFieldList, final HeaderOption header) throws IllegalAccessException {
		final Map<Integer, List<String>> map = new HashMap<>();
		
		for (int i = 0; i < sortedFieldList.size(); i++) {
			map.put(i, new ArrayList<>());
		}
		
		if (header == HeaderOption.WITH_HEADER) {
			addHeaderToMap(map, sortedFieldList);
		}
		addRecordsToMap(tList, map, sortedFieldList);
		
		return map;
	}
	
	/**
	 * Check the {@code pos} parameters in all the {@code CsvColumn} are 
	 * ordinal numbers or not.
	 * 
	 * <p>{@code pos} parameters should be sequential, starting from 0 with no 
	 * repeat values.
	 * 
	 * @param  fieldList
	 *         The list of {@code Field} objects.
	 *         
	 * @return  {@code true} if {@code pos} parameters are sequential, 
	 *          starting from 0 with no repeat values.
	 *          {@code false} otherwise.
	 *          
	 * @since   1.1
	 */
	protected static boolean checkPosSequentialAndNoRepeat(final List<Field> fieldList) {
		final boolean[] boolArray = new boolean[fieldList.size()];
		
		for (Field field : fieldList) {
			final CsvColumn column = field.getAnnotation(CsvColumn.class);
			final int pos = column.pos();
			
			if (pos < 0 || pos >= fieldList.size()) {   // index out of bound
				return false;
			}
			if (boolArray[pos]) {         // repeat
				return false;
			}
			
			boolArray[pos] = true;
		}
		
		for (boolean bool : boolArray) {
			if (!bool) {         // not sequential
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Sort a list of {@code Field} objects by the ascending order of the 
	 * {@code pos} values of the {@code CsvColumn} annotation marked on each 
	 * field.
	 * 
	 * @param  fieldList
	 *         The list of {@code Field} objects.
	 *         
	 * @since   1.1
	 */
	protected static void sortFieldListByPos(final List<Field> fieldList) {
		fieldList.sort(new Comparator<Field>() {
		    @Override
		    public int compare(Field f1, Field f2) {
				final CsvColumn c1 = f1.getAnnotation(CsvColumn.class);
				final CsvColumn c2 = f2.getAnnotation(CsvColumn.class);

		        return c1.pos() - c2.pos();
		     }
		});
	}
	
	/**
	 * Sort the list of {@code Field} based on the order in the list of column 
	 * names.
	 * 
	 * @param  clazz
	 *         The class where the field list from.
	 *         
	 * @param  fieldList
	 *         The list of {@code Field} objects.
	 *         
	 * @param  columnList
	 *         The list of column names.
	 *         
	 * @return  The list of sorted {@code Field}.
	 * 
	 * @since   1.1  
	 */
	protected static List<Field> sortFieldListByColumnList(final Class<?> clazz, final List<Field> fieldList, final List<String> columnList) {
		final List<Field> sortedFieldList = new ArrayList<>();
		
		for (String columnName : columnList) {
			if (!Strings.isNullOrEmpty(columnName)) {
				sortedFieldList.add(getFieldFromFieldListByParameter(clazz, fieldList, columnName));
			}
		}
		
		return sortedFieldList;
	}
	
	/**
	 * Get a {@code Field} object from a {@code Field} list by the name in the 
	 * {@code CsvColumn} annotation.
	 * 
	 * @param  clazz
	 *         The class where the field list from.
	 *         
	 * @param  fieldList
	 *         The list of {@code Field} objects.
	 * 
	 * @param  columnName
	 *         The column name of the field needs to retrieve.
	 *         
	 * @return  The {@code Field} object annotated by {@code CsvColumn} with 
	 *          columnName as the value for the name parameter.
	 *          
	 * @since   1.1  
	 */
	protected static Field getFieldFromFieldListByParameter(final Class<?> clazz, final List<Field> fieldList, String columnName) {
		for (Field field : fieldList) {
			final CsvColumn cc = field.getAnnotation(CsvColumn.class);
			if (cc.name().equals(columnName)) {
				return field;
			}
		}
		throw new NoSuchElementException("Can not find a field in the " + clazz.getSimpleName() + " class which the name parameter in the CsvColumn is " + columnName + ".");
	}
	
	/**
	 * Add the header line into the hash map.
	 * 
	 * @param  map
	 *         The hash map.
	 *         
	 * @param  fieldList
	 *         The list of {@code Field} objects.
	 *         
	 * @since   1.1
	 */
	protected static void addHeaderToMap(final Map<Integer, List<String>> map, final List<Field> fieldList) {
		for (int i = 0; i < fieldList.size(); i++) {
			final CsvColumn column = fieldList.get(i).getAnnotation(CsvColumn.class);
			map.get(i).add(Strings.nullToEmpty(column.name()));
		}
	}
	
	/**
	 * Add all the data into the hash map.
	 * 
	 * @param  tList
	 *         The list of T objects.
	 *         
	 * @param  map
	 *         The hash map.
	 *       
	 * @param  fieldList
	 *         The list of {@code Field} objects.
	 *         
	 * @throws  IllegalAccessException
	 *          If a field in a Java class is enforcing Java language access 
	 *          control and the underlying field is either inaccessible or 
	 *          final.
	 *          
	 * @since   1.1
	 */
	protected static <T> void addRecordsToMap(final List<T> tList, final Map<Integer, List<String>> map, final List<Field> fieldList) throws IllegalAccessException {
		for (T t : tList) {
			for (int i = 0; i < fieldList.size(); i++) {
				fieldList.get(i).setAccessible(true);
				map.get(i).add(getValue(t, fieldList.get(i)));
			}
		}
	}
	
	/**
	 * Write records into a CSV file.
	 * 
	 * @param  csvFilePath
	 *         The path of the CSV file.
	 *         
	 * @param  map
	 *         The map contains the key as column index and the value as the 
	 *         values in the certain column.
	 * 
	 * @throws  IOException
	 *          Error occurred when  writing the CSV file. 
	 *          
	 * @since   1.1
	 */
	protected static void writeCsvFile(final String csvFilePath, final Map<Integer, List<String>> map) throws IOException {
		final FileWriter fileWriter = new FileWriter(csvFilePath);
	    final CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withRecordSeparator("\n"));
	    
	    final int columnNum = map.size();
	    final int rowNum    = map.get(0).size();
	    
	    for(int i = 0; i < rowNum; i++) {
	    	final List<Object> objectList = new ArrayList<>();
	    	for (int j = 0; j < columnNum; j++) {
	    		objectList.add(map.get(j).get(i));
	    	}
	    	csvFilePrinter.printRecord(objectList);
	    }
	    
	    csvFilePrinter.close();	
	}
	
	/**
	 * Set the value for a certain field in Java object
	 * 
	 * @param  t
	 *         The object in the certain type.
	 *         
	 * @param  field
	 *         The {@code Field} object represents one field in a class.
	 *         
	 * @param  column
	 *         The CsvColumn annotation.
	 *         
	 * @param  value
	 *         The value in a certain cell in CSV file.
	 *          
	 * @throws  IllegalAccessException
	 *          If a field in a Java class is enforcing Java language access 
	 *          control and the underlying field is either inaccessible or final.
	 *          
	 * @throws  ParseException
	 *          If there is an error occurred when parsing a value in CSV file.
	 *          
	 * @since   1.1
	 */
	protected static <T> void setValue(final T t, final Field field, final CsvColumn column, final String value) throws IllegalAccessException, ParseException {
		final Class<?> fieldType = field.getType(); 
		
		if (value == null) {
			field.set(t, null);
		} else if (fieldType == Object.class){
			field.set(t, value);
		} else if (fieldType == String.class) {
			field.set(t, value);
		} else if (fieldType == Integer.class || fieldType == Integer.TYPE) {
			field.set(t, Integer.parseInt(value));
		} else if (fieldType == Long.class || fieldType == Long.TYPE) {
			field.set(t, Long.parseLong(value));
		} else if (fieldType == Double.class || fieldType == Double.TYPE) {
			field.set(t, Double.parseDouble(value));
		} else if (fieldType == Float.class || fieldType == Float.TYPE) {
			field.set(t, Float.parseFloat(value));
		} else if (fieldType == Short.class || fieldType == Short.TYPE) {
			field.set(t, Short.parseShort(value));
		} else if (fieldType == Character.class || fieldType == Character.TYPE) { 
			field.set(t, value.charAt(0));
		} else if (fieldType == Boolean.class || fieldType == Boolean.TYPE) { 
			field.set(t, Boolean.parseBoolean(value));
		} else if (fieldType == java.util.Date.class || fieldType == java.sql.Date.class || fieldType == Timestamp.class || fieldType == Calendar.class || fieldType == LocalDate.class) {
			if (Strings.isNullOrEmpty(column.format())) {
				throw new IllegalArgumentException("The field " + field.getName() + " needs to spefic the format for parsing date or date time.");
			}
			final java.util.Date date = (new SimpleDateFormat(column.format())).parse(value);
			if (fieldType == java.util.Date.class) {
				field.set(t, date);
			} else if (fieldType == Timestamp.class) {
				field.set(t, new Timestamp(date.getTime()));
			} else if (fieldType == Calendar.class) {
				final Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				field.set(t, cal);
			} else if (fieldType == java.sql.Date.class) {
				field.set(t, new java.sql.Date(date.getTime()));
			} else if (fieldType == LocalDate.class) {
				field.set(t, LocalDate.parse(value, DateTimeFormatter.ofPattern(column.format())));
			}
		} else if (fieldType == LocalDateTime.class) { 
			field.set(t, LocalDateTime.parse(value, DateTimeFormatter.ofPattern(column.format())));
		} else {
			throw new IllegalArgumentException("The field " + field.getName() + " is not an easy type (" + fieldType.getName() + ") for parsing CSV file.");
		}
	}
	
	/**
	 * Get the value for a certain field in Java object and convert it to 
	 * {@code String}.
	 * 
	 * @param  t
	 *         The object in the certain type.
	 *         
	 * @param  field
	 *         The {@code Field} object represents one field in a class.
	 * 
	 * @return  The {@code String} value of the value in the field.
	 * 
	 * @throws  IllegalAccessException
	 *          If the {@code CsvColumn} annotation of a date time field is 
	 *          missing the format parameter.
	 *          
	 * @since   1.1
	 */
	protected static <T> String getValue(final T t, final Field field) throws IllegalAccessException {
		final Class<?> fieldType = field.getType();
		final Object value = field.get(t);
		
		if (value == null) {
			return null;
		} else if (fieldType == String.class) {
			return (String) value;
		} else if (fieldType == Object.class) {
			return value.toString();
		} else if (fieldType == Integer.class || fieldType == Integer.TYPE) {
			return Integer.toString((int) value);
		} else if (fieldType == Long.class    || fieldType == Long.TYPE) {
			return Long.toString((long) value);
		} else if (fieldType == Double.class  || fieldType == Double.TYPE) {
			return Double.toString((double) value);
		} else if (fieldType == Float.class   || fieldType == Float.TYPE) {
			return Float.toString((float) value);
		} else if (fieldType == Short.class   || fieldType == Short.TYPE) {
			return Short.toString((short) value);
		} else if (fieldType == Character.TYPE) {
			return Character.toString((char) value);  
		} else if (fieldType == Boolean.class || fieldType == Boolean.TYPE) { 
			return Boolean.toString((boolean) value);
		} else if (fieldType == java.util.Date.class || fieldType == java.sql.Date.class ||  fieldType == Timestamp.class || fieldType == Calendar.class || fieldType == LocalDate.class || fieldType == LocalDateTime.class) {
			final CsvColumn column = field.getAnnotation(CsvColumn.class);
			if (Strings.isNullOrEmpty(column.format())) {
				throw new IllegalArgumentException("The field " + field.getName() + " needs to spefic the format for writing date or date time.");
			}
			final SimpleDateFormat df = new SimpleDateFormat(column.format());
			if (fieldType == java.util.Date.class) {
				return df.format((java.util.Date) value);
			} else if (fieldType == Timestamp.class) {
				return df.format((Timestamp) value);
			} else if (fieldType == Calendar.class) {
				return df.format(((Calendar) value).getTime());
			} else if (fieldType == java.sql.Date.class) {
				return df.format((java.sql.Date) value);
			} else if (fieldType == LocalDate.class) {
				return ((LocalDate) value).format(DateTimeFormatter.ofPattern(column.format()));
			} else if (fieldType == LocalDateTime.class) {
				return ((LocalDateTime) value).format(DateTimeFormatter.ofPattern(column.format()));
			}
		} else {
			throw new IllegalArgumentException("The field " + field.getName() + " is not an easy type (" + fieldType.getName() + ") for writing CSV file.");
		}
		
		return null;
	}
}

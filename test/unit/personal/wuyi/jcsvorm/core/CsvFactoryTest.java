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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import personal.wuyi.io.file.csv.HeaderOption;
import personal.wuyi.jcsvorm.core.model.correct.AllTypeRecord;
import personal.wuyi.jcsvorm.core.model.correct.User1;
import personal.wuyi.jcsvorm.core.model.correct.User2;
import personal.wuyi.jcsvorm.core.model.correct.User3;
import personal.wuyi.jcsvorm.core.model.correct.User4;

/**
 * Test class for CsvFactory.
 * 
 * @author  Wuyi Chen
 * @date    03/14/2017
 * @version 1.2
 * @since   1.1
 */
public class CsvFactoryTest {
	private SimpleDateFormat  df  = new SimpleDateFormat("yyyy-MM-dd");
	private DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm");
	
	/**
	 * Use name to map the column, file has header line
	 */
	@Test
	public void readCsvTest1() throws IllegalArgumentException, IllegalAccessException, IOException, ParseException {
		List<User1> userList = CsvFactory.readCsv(User1.class, "data/data_with_header.csv", HeaderOption.WITH_HEADER);
		List<String> lineList = Files.readAllLines(Paths.get("data/data_with_header.csv"));
		Assert.assertEquals(lineList.size() - 1, userList.size());   // ignore 1 header line
		for (int i = 0; i < userList.size(); i++) {
			User1    user = userList.get(i);
			String[] line = lineList.get(i+1).split(",");
			Assert.assertEquals(line[0],                       user.getName());
			Assert.assertEquals(Integer.parseInt(line[1]),     user.getSalary());
			Assert.assertEquals(line[2],                       df.format(user.getDob()));
			Assert.assertEquals(Double.parseDouble(line[3]),   user.getPerformance(), 0.0);
			Assert.assertEquals(Boolean.parseBoolean(line[4]), user.isHealth());
		}
	}
	
	/**
	 * Use pos to map the column, file has header line
	 */
	@Test
	public void readCsvTest2() throws IllegalArgumentException, IllegalAccessException, IOException, ParseException {
		List<User2> userList = CsvFactory.readCsv(User2.class, "data/data_with_header.csv", HeaderOption.WITH_HEADER);
		List<String> lineList = Files.readAllLines(Paths.get("data/data_with_header.csv"));
		Assert.assertEquals(lineList.size() - 1, userList.size());   // ignore 1 header line
		for (int i = 0; i < userList.size(); i++) {
			User2    user = userList.get(i);
			String[] line = lineList.get(i+1).split(",");
			Assert.assertEquals(line[0],                       user.getName());
			Assert.assertEquals(Integer.parseInt(line[1]),     user.getSalary());
			Assert.assertEquals(line[2],                       df.format(user.getDob()));
			Assert.assertEquals(Double.parseDouble(line[3]),   user.getPerformance(), 0.0);
			Assert.assertEquals(Boolean.parseBoolean(line[4]), user.isHealth());
		}
	}
	
	/**
	 * Some field use name to map, some field use pos to map, file has header line
	 */
	@Test
	public void readCsvTest3() throws IllegalArgumentException, IllegalAccessException, IOException, ParseException {
		List<User3> userList = CsvFactory.readCsv(User3.class, "data/data_with_header.csv", HeaderOption.WITH_HEADER);
		List<String> lineList = Files.readAllLines(Paths.get("data/data_with_header.csv"));
		Assert.assertEquals(lineList.size() - 1, userList.size());   // ignore 1 header line
		for (int i = 0; i < userList.size(); i++) {
			User3    user = userList.get(i);
			String[] line = lineList.get(i+1).split(",");
			Assert.assertEquals(line[0],                       user.getName());
			Assert.assertEquals(Integer.parseInt(line[1]),     user.getSalary());
			Assert.assertEquals(line[2],                       df.format(user.getDob()));
			Assert.assertEquals(Double.parseDouble(line[3]),   user.getPerformance(), 0.0);
			Assert.assertEquals(Boolean.parseBoolean(line[4]), user.isHealth());
		}
	}
	
	/**
	 * Use pos to map the column, file has no header line
	 */
	@Test
	public void readCsvTest4() throws IllegalArgumentException, IllegalAccessException, IOException, ParseException {
		List<User2> userList = CsvFactory.readCsv(User2.class, "data/data_without_header.csv", HeaderOption.WITHOUT_HEADER);
		List<String> lineList = Files.readAllLines(Paths.get("data/data_without_header.csv"));
		Assert.assertEquals(lineList.size(), userList.size());   // no header line
		for (int i = 0; i < userList.size(); i++) {
			User2    user = userList.get(i);
			String[] line = lineList.get(i).split(",");     // no header line
			Assert.assertEquals(line[0],                       user.getName());
			Assert.assertEquals(Integer.parseInt(line[1]),     user.getSalary());
			Assert.assertEquals(line[2],                       df.format(user.getDob()));
			Assert.assertEquals(Double.parseDouble(line[3]),   user.getPerformance(), 0.0);
			Assert.assertEquals(Boolean.parseBoolean(line[4]), user.isHealth());
		}
	}
	
	/**
	 * Use name to map the column, test all types
	 */
	@Test
	public void readCsvTest5() throws IllegalAccessException, IOException, ParseException {
		List<AllTypeRecord> recordList = CsvFactory.readCsv(AllTypeRecord.class, "data/all_type_input.csv", HeaderOption.WITH_HEADER);
		List<String> lineList = Files.readAllLines(Paths.get("data/all_type_input.csv"));
		Assert.assertEquals(lineList.size() - 1, recordList.size());   // ignore 1 header line
		for (int i = 0; i < recordList.size(); i++) {
			AllTypeRecord record = recordList.get(i);
			String[]      line   = lineList.get(i+1).split(",");
			Assert.assertEquals(line[0],                       record.getStringValue());
			Assert.assertEquals(Integer.parseInt(line[1]),     record.getIntegerValue());
			Assert.assertEquals(Long.parseLong(line[2]),       record.getLongValue());
			Assert.assertEquals(Double.parseDouble(line[3]),   record.getDoubleValue(), 0.0);
			Assert.assertEquals(Float.parseFloat(line[4]),     record.getFloatValue(),  0.0);
			Assert.assertEquals(Short.parseShort(line[5]),     record.getShortValue());
			Assert.assertEquals(line[6].charAt(0),             record.getCharValue());
			Assert.assertEquals(Boolean.parseBoolean(line[7]), record.getBooleanValue());
			Assert.assertEquals(line[8],                       df.format(record.getUtilDateValue()));
			Assert.assertEquals(line[9],                       df.format(record.getTimestampValue()));
			Assert.assertEquals(line[10],                      df.format(record.getCalendarValue().getTime()));
			Assert.assertEquals(line[11],                      df.format(record.getSqlDateValue()));
			Assert.assertEquals(line[12],                      dtf1.format(record.getLocalDateValue()));
			Assert.assertEquals(line[13],                      dtf2.format(record.getLocalDateTimeValue()));
		}
	}
	
	/**
	 * write csv based on pos
	 */
	@Test
	public void writeCsvTest1() throws IllegalArgumentException, IllegalAccessException, IOException {
		List<User4> userList = new ArrayList<>();
		userList.add(new User4("Wang", 2000, Calendar.getInstance().getTime(), 23.45, true));
		userList.add(new User4("Sam", 3000, Calendar.getInstance().getTime(), 40.34, false));
		userList.add(new User4("Joe", 4000, Calendar.getInstance().getTime(), 33.98, true));
		CsvFactory.writeCsv(userList, "data/output.csv", HeaderOption.WITH_HEADER);
		
		List<String> lineList = Files.readAllLines(Paths.get("data/output.csv"));
		Assert.assertEquals(lineList.size() - 1, userList.size());   // ignore 1 header line
		String[] headerLine = lineList.get(0).split(",");
		Assert.assertEquals("name",         headerLine[0]);
		Assert.assertEquals("int_amount",   headerLine[1]);
		Assert.assertEquals("dob",          headerLine[2]);
		Assert.assertEquals("perf_balance", headerLine[3]);
		Assert.assertEquals("health",       headerLine[4]);
		for (int i = 0; i < userList.size(); i++) {
			User4    user = userList.get(i);
			String[] line = lineList.get(i+1).split(",");
			Assert.assertEquals(line[0],                       user.getName());
			Assert.assertEquals(Integer.parseInt(line[1]),     user.getSalary());
			Assert.assertEquals(line[2],                       df.format(user.getDob()));
			Assert.assertEquals(Double.parseDouble(line[3]),   user.getPerformance(), 0.0);
			Assert.assertEquals(Boolean.parseBoolean(line[4]), user.isHealth());
		}
	}
	
	/**
	 * write csv with only selected columns
	 */
	@Test
	public void writeCsvTest2() throws IllegalArgumentException, IllegalAccessException, IOException {
		List<User4> userList = new ArrayList<>();
		userList.add(new User4("Wang", 2000, Calendar.getInstance().getTime(), 23.45, true));
		userList.add(new User4("Sam", 3000, Calendar.getInstance().getTime(), 40.34, false));
		userList.add(new User4("Joe", 4000, Calendar.getInstance().getTime(), 33.98, true));
		CsvFactory.writeCsv(userList, "data/output2.csv", Arrays.asList("perf_balance", "int_amount"), HeaderOption.WITH_HEADER);
		
		List<String> lineList = Files.readAllLines(Paths.get("data/output2.csv"));
		String[] headerLine = lineList.get(0).split(",");
		Assert.assertEquals("perf_balance", headerLine[0]);
		Assert.assertEquals("int_amount",   headerLine[1]);
		for (int i = 0; i < userList.size(); i++) {
			User4    user = userList.get(i);
			String[] line = lineList.get(i+1).split(",");
			Assert.assertEquals(Double.parseDouble(line[0]),   user.getPerformance(), 0.0);
			Assert.assertEquals(Integer.parseInt(line[1]),     user.getSalary());
		}
	}
	
	/**
	 * write csv based on pos, test all types
	 */
	@Test
	public void writeCsvTest3() throws IllegalAccessException, IOException {
		List<AllTypeRecord> recordList = new ArrayList<>();
		AllTypeRecord record1 = new AllTypeRecord();
		record1.setStringValue("opqopqopq");
		record1.setIntegerValue(99);
		record1.setLongValue(44883377L);
		record1.setDoubleValue(3.14159);
		record1.setFloatValue(3.14f);
		record1.setShortValue((short) 99);
		record1.setCharValue('x');
		record1.setBooleanValue(true);
		record1.setUtilDateValue(Calendar.getInstance().getTime());
		record1.setTimestampValue(new Timestamp(Calendar.getInstance().getTime().getTime()));
		record1.setCalendarValue(Calendar.getInstance());
		record1.setSqlDateValue(new java.sql.Date (Calendar.getInstance().getTime().getTime()));
		record1.setLocalDateValue(LocalDateTime.now().toLocalDate());
		record1.setLocalDateTimeValue(LocalDateTime.now());
		recordList.add(record1);
		CsvFactory.writeCsv(recordList, "data/all_type_output.csv", HeaderOption.WITH_HEADER);
		
		List<String> lineList = Files.readAllLines(Paths.get("data/all_type_output.csv"));
		Assert.assertEquals(lineList.size() - 1, recordList.size());   // ignore 1 header line
		String[] headerLine = lineList.get(0).split(",");
		Assert.assertEquals("string_value",          headerLine[0]);
		Assert.assertEquals("integer_value",         headerLine[1]);
		Assert.assertEquals("long_value",            headerLine[2]);
		Assert.assertEquals("double_value",          headerLine[3]);
		Assert.assertEquals("float_value",           headerLine[4]);
		Assert.assertEquals("short_value",           headerLine[5]);
		Assert.assertEquals("char_value",            headerLine[6]);
		Assert.assertEquals("boolean_value",         headerLine[7]);
		Assert.assertEquals("util_date_value",       headerLine[8]);
		Assert.assertEquals("timestamp_value",       headerLine[9]);
		Assert.assertEquals("calendar_value",        headerLine[10]);
		Assert.assertEquals("sql_date_value",        headerLine[11]);
		Assert.assertEquals("local_date_value",      headerLine[12]);
		Assert.assertEquals("local_date_time_value", headerLine[13]);
		for (int i = 0; i < recordList.size(); i++) {
			AllTypeRecord record = recordList.get(i);
			String[]      line   = lineList.get(i+1).split(",");
			Assert.assertEquals(line[0],                       record.getStringValue());
			Assert.assertEquals(Integer.parseInt(line[1]),     record.getIntegerValue());
			Assert.assertEquals(Long.parseLong(line[2]),       record.getLongValue());
			Assert.assertEquals(Double.parseDouble(line[3]),   record.getDoubleValue(), 0.0);
			Assert.assertEquals(Float.parseFloat(line[4]),     record.getFloatValue(),  0.0);
			Assert.assertEquals(Short.parseShort(line[5]),     record.getShortValue());
			Assert.assertEquals(line[6].charAt(0),             record.getCharValue());
			Assert.assertEquals(Boolean.parseBoolean(line[7]), record.getBooleanValue());
			Assert.assertEquals(line[8],                       df.format(record.getUtilDateValue()));
			Assert.assertEquals(line[9],                       df.format(record.getTimestampValue()));
			Assert.assertEquals(line[10],                      df.format(record.getCalendarValue().getTime()));
			Assert.assertEquals(line[11],                      df.format(record.getSqlDateValue()));
			Assert.assertEquals(line[12],                      dtf1.format(record.getLocalDateValue()));
			Assert.assertEquals(line[13],                      dtf2.format(record.getLocalDateTimeValue()));
		}
	}
}

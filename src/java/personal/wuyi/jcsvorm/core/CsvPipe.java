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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import personal.wuyi.jcsvorm.api.Function;

/**
 * The static class for transforming one CSVRecord to one object in the 
 * certain Java type.
 * 
 * @author  Wuyi Chen
 * @date    03/14/2017
 * @version 1.1
 * @since   1.1
 */
public class CsvPipe {
	private CsvPipe() {}
	
	/**
	 * Map the list of {@code CSVRecord} to the list of objects in other type.
	 * 
	 * @param  list
	 *         The list of {@code CSVRecord}.
	 * 
	 * @param  clazz
	 *         The type of the object you want to convert to.
	 * 
	 * @param  f
	 *         The function interface to handle each element.
	 *         
	 * @return  The list of objects in other type.
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
	public static <T> List<T> map(final List<CSVRecord> list, final Class<T> clazz, final Function<CSVRecord, T> f) throws IllegalAccessException, ParseException {
		final List<T> newlist = new ArrayList<>();
		for (CSVRecord record : list) {
			newlist.add(f.call(record, clazz));
		}
		return newlist;
	}
}

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

import static org.junit.Assert.fail;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import org.junit.Test;

import personal.wuyi.io.file.csv.HeaderOption;
import personal.wuyi.jcsvorm.core.model.correct.User4;
import personal.wuyi.jcsvorm.core.model.wrong.UserError1;
import personal.wuyi.jcsvorm.core.model.wrong.UserError10;
import personal.wuyi.jcsvorm.core.model.wrong.UserError2;
import personal.wuyi.jcsvorm.core.model.wrong.UserError3;
import personal.wuyi.jcsvorm.core.model.wrong.UserError4;
import personal.wuyi.jcsvorm.core.model.wrong.UserError5;
import personal.wuyi.jcsvorm.core.model.wrong.UserError6;
import personal.wuyi.jcsvorm.core.model.wrong.UserError7;
import personal.wuyi.jcsvorm.core.model.wrong.UserError8;
import personal.wuyi.jcsvorm.core.model.wrong.UserError9;

/**
 * Test class for CsvFactory.
 * 
 * <p>This class is to test throwing exceptions in {@code CsvFactory} class.
 * 
 * @author  Wuyi Chen
 * @date    12/01/2017
 * @version 1.2
 * @since   1.2
 */
public class CsvFactoryExceptionTest {
	@Test
	public void transformCsvRecordsExceptionTest() throws IllegalAccessException, IOException, ParseException {
		// test field can not be static
		try {
			CsvFactory.readCsv(UserError1.class, "data/data_with_header.csv", HeaderOption.WITH_HEADER);
	        fail("Expected an IllegalArgumentException to be thrown");
	    } catch (IllegalArgumentException e) {
	        assertThat(e.getMessage(), is("name field can not be static."));
	    }
		
		// test field is required, but missing in csv
		try {
			CsvFactory.readCsv(UserError2.class, "data/data_with_header.csv", HeaderOption.WITH_HEADER);
	        fail("Expected an IllegalArgumentException to be thrown");
	    } catch (IllegalArgumentException e) {
	        assertThat(e.getMessage(), is("The column nick_name is required in the CSV file, but it is missing in the CSV file right now."));
	    }
		
		// test class is missing CsvEntity annotation
		try {
			CsvFactory.readCsv(UserError3.class, "data/data_with_header.csv", HeaderOption.WITH_HEADER);
	        fail("Expected an IllegalArgumentException to be thrown");
	    } catch (IllegalArgumentException e) {
	        assertThat(e.getMessage(), is("UserError3 needs to have CsvEntity anntation."));
	    }
		
		// test if csv is without header, the pos parameter is mandatory in CsvColumn annotation
		try {
			CsvFactory.readCsv(UserError4.class, "data/data_without_header.csv", HeaderOption.WITHOUT_HEADER);
			fail("Expected an IllegalArgumentException to be thrown");
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), is("For field name, pos parameter is required for the CsvColumn annotation when the CSV file doesn't have header line."));
		}
		
		// test if csv is with header, CsvColumn annotation should have one parameter at least, either name or pos
		try {
			CsvFactory.readCsv(UserError5.class, "data/data_with_header.csv", HeaderOption.WITH_HEADER);
			fail("Expected an IllegalArgumentException to be thrown");
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), is("For field name, must specify at least one parameter among pos and name in the CsvColumn annotation when the CSV file has a header line."));
		}
	}
	
	@Test
	public void checkPosSequentialAndNoRepeatExceptionTest () throws IllegalAccessException, IOException {
		// test negative pos value
		List<UserError6> userList1 = new ArrayList<>();
		userList1.add(new UserError6("Wang", 2000, Calendar.getInstance().getTime(), 23.45, true));
		userList1.add(new UserError6("Sam", 3000, Calendar.getInstance().getTime(), 40.34, false));
		userList1.add(new UserError6("Joe", 4000, Calendar.getInstance().getTime(), 33.98, true));
		CsvFactory.writeCsv(userList1, "data/output_error.csv", HeaderOption.WITH_HEADER);
		
		// test duplicated pos value
		List<UserError7> userList2 = new ArrayList<>();
		userList2.add(new UserError7("Wang", 2000, Calendar.getInstance().getTime(), 23.45, true));
		userList2.add(new UserError7("Sam", 3000, Calendar.getInstance().getTime(), 40.34, false));
		userList2.add(new UserError7("Joe", 4000, Calendar.getInstance().getTime(), 33.98, true));
		CsvFactory.writeCsv(userList2, "data/output_error.csv", HeaderOption.WITH_HEADER);
		
		// test not sequential pos values
		List<UserError8> userList3 = new ArrayList<>();
		userList3.add(new UserError8("Wang", 2000, Calendar.getInstance().getTime(), 23.45, true));
		userList3.add(new UserError8("Sam", 3000, Calendar.getInstance().getTime(), 40.34, false));
		userList3.add(new UserError8("Joe", 4000, Calendar.getInstance().getTime(), 33.98, true));
		CsvFactory.writeCsv(userList3, "data/output_error.csv", HeaderOption.WITH_HEADER);
	}
	
	@Test
	public void getFieldFromFieldListByParameterExceptionTest() throws IllegalAccessException, IOException {
		// test in the column list, provide a column is not existing in class
		try {
			List<User4> userList = new ArrayList<>();
			userList.add(new User4("Wang", 2000, Calendar.getInstance().getTime(), 23.45, true));
			userList.add(new User4("Sam", 3000, Calendar.getInstance().getTime(), 40.34, false));
			userList.add(new User4("Joe", 4000, Calendar.getInstance().getTime(), 33.98, true));
			CsvFactory.writeCsv(userList, "data/output2.csv", Arrays.asList("perf_balance", "int_aabbcc"), HeaderOption.WITH_HEADER);
		} catch (NoSuchElementException e) {
			assertThat(e.getMessage(), is("Can not find a field in the User4 class which the name parameter in the CsvColumn is int_aabbcc."));
		}
	}
	
	@Test
	public void setValueExceptionTest() throws IllegalAccessException, IOException, ParseException {
		// test a date field is missing format parameter 
		try {
			CsvFactory.readCsv(UserError9.class, "data/data_with_header.csv", HeaderOption.WITH_HEADER);
			fail("Expected an IllegalArgumentException to be thrown");
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), is("The field dob needs to spefic the format for parsing date or date time."));
		}
		
		// test a field is not an easy type
		try {
			CsvFactory.readCsv(UserError10.class, "data/data_with_header.csv", HeaderOption.WITH_HEADER);
			fail("Expected an IllegalArgumentException to be thrown");
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), is("The field pattern is not an easy type (java.util.regex.Pattern) for parsing CSV file."));
		}
	}
	
	@Test
	public void getValueExceptionTest() throws IllegalAccessException, IOException {
		// test a date field is missing format parameter 
		try {
			List<UserError9> userList1 = new ArrayList<>();
			userList1.add(new UserError9("Wang", 2000, Calendar.getInstance().getTime(), 23.45, true));
			userList1.add(new UserError9("Sam", 3000, Calendar.getInstance().getTime(), 40.34, false));
			userList1.add(new UserError9("Joe", 4000, Calendar.getInstance().getTime(), 33.98, true));
			CsvFactory.writeCsv(userList1, "data/output_error.csv", HeaderOption.WITH_HEADER);
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), is("The field dob needs to spefic the format for writing date or date time."));
		}
		
		// test a field is not an easy type
		try {
			List<UserError10> userList1 = new ArrayList<>();
			userList1.add(new UserError10("Wang", 2000, Calendar.getInstance().getTime(), 23.45, Pattern.compile(".xx.")));
			userList1.add(new UserError10("Sam", 3000, Calendar.getInstance().getTime(), 40.34,  Pattern.compile(".xx.")));
			userList1.add(new UserError10("Joe", 4000, Calendar.getInstance().getTime(), 33.98,  Pattern.compile(".xx.")));
			CsvFactory.writeCsv(userList1, "data/output_error.csv", HeaderOption.WITH_HEADER);
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), is("The field pattern is not an easy type (java.util.regex.Pattern) for writing CSV file."));
		}
	}
}

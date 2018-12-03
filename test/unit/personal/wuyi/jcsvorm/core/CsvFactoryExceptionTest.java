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

import org.junit.Test;

import personal.wuyi.io.file.csv.HeaderOption;
import personal.wuyi.jcsvorm.core.model.correct.User1;
import personal.wuyi.jcsvorm.core.model.wrong.UserError1;
import personal.wuyi.jcsvorm.core.model.wrong.UserError2;
import personal.wuyi.jcsvorm.core.model.wrong.UserError3;
import personal.wuyi.jcsvorm.core.model.wrong.UserError4;
import personal.wuyi.jcsvorm.core.model.wrong.UserError5;

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
}

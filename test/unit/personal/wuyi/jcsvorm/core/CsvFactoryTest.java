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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import personal.wuyi.io.file.csv.HeaderOption;
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
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
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
}

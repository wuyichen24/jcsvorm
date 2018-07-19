package personal.wuyi.jcsvorm.core;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import personal.wuyi.io.file.csv.HeaderOption;

/**
 * Test class for CsvFactory.
 * 
 * @author  Wuyi Chen
 * @date    03/14/2017
 * @version 1.1
 * @since   1.1
 */
public class CsvFactoryTest {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * Use name to map the column, file has header line
	 */
	@Test
	public void readCsvTest1() throws IllegalArgumentException, IllegalAccessException, IOException, ParseException {
		List<User1> userList = CsvFactory.readCsv(User1.class, "data/data_with_header.csv", HeaderOption.WITH_HEADER);
		for (User1 user : userList) {
			System.out.println(user.getName() + " " + user.getSalary() + " " + df.format(user.getDob()) + " " + user.getPerformance() + " " + user.isHealth());
		}
	}
	
	/**
	 * Use pos to map the column, file has header line
	 */
	@Test
	public void readCsvTest2() throws IllegalArgumentException, IllegalAccessException, IOException, ParseException {
		List<User2> userList = CsvFactory.readCsv(User2.class, "data/data_with_header.csv", HeaderOption.WITH_HEADER);
		for (User2 user : userList) {
			System.out.println(user.getName() + " " + user.getSalary() + " " + df.format(user.getDob()) + " " + user.getPerformance() + " " + user.isHealth());
		}
	}
	
	/**
	 * Some field use name to map, some field use pos to map, file has header line
	 */
	@Test
	public void readCsvTest3() throws IllegalArgumentException, IllegalAccessException, IOException, ParseException {
		List<User3> userList = CsvFactory.readCsv(User3.class, "data/data_with_header.csv", HeaderOption.WITH_HEADER);
		for (User3 user : userList) {
			System.out.println(user.getName() + " " + user.getSalary() + " " + df.format(user.getDob()) + " " + user.getPerformance() + " " + user.isHealth());
		}
	}
	
	/**
	 * Use pos to map the column, file has no header line
	 */
	@Test
	public void readCsvTest4() throws IllegalArgumentException, IllegalAccessException, IOException, ParseException {
		List<User2> userList = CsvFactory.readCsv(User2.class, "data/data_without_header.csv", HeaderOption.WITHOUT_HEADER);
		for (User2 user : userList) {
			System.out.println(user.getName() + " " + user.getSalary() + " " + df.format(user.getDob()) + " " + user.getPerformance() + " " + user.isHealth());
		}
	}
	
	@Test
	public void writeCsvTest1() throws IllegalArgumentException, IllegalAccessException, IOException {
		List<User4> userList = new ArrayList<>();
		userList.add(new User4("Wang", 2000, Calendar.getInstance().getTime(), 23.45, true));
		userList.add(new User4("Sam", 3000, Calendar.getInstance().getTime(), 40.34, false));
		userList.add(new User4("Joe", 4000, Calendar.getInstance().getTime(), 33.98, true));
		CsvFactory.writeCsv(userList, "data/output.csv", HeaderOption.WITH_HEADER);
	}
	
	@Test
	public void writeCsvTest2() throws IllegalArgumentException, IllegalAccessException, IOException {
		List<User4> userList = new ArrayList<>();
		userList.add(new User4("Wang", 2000, Calendar.getInstance().getTime(), 23.45, true));
		userList.add(new User4("Sam", 3000, Calendar.getInstance().getTime(), 40.34, false));
		userList.add(new User4("Joe", 4000, Calendar.getInstance().getTime(), 33.98, true));
		CsvFactory.writeCsv(userList, "data/output2.csv", Arrays.asList("perf_balance", "int_amount"), HeaderOption.WITH_HEADER);
	}
}

package personal.wuyi.jcsvorm.core;

import static org.junit.Assert.fail;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.text.ParseException;

import org.junit.Test;

import personal.wuyi.io.file.csv.HeaderOption;
import personal.wuyi.jcsvorm.core.model.wrong.UserError1;

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
	}
}

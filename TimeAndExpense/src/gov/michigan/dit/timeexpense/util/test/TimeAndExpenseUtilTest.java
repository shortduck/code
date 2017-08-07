package gov.michigan.dit.timeexpense.util.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TimeAndExpenseUtilTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}
// Zack changed 
	@After
	public void tearDown() throws Exception {
	}
 
	@Test
	public void testConstructDateFromDateAndTime_ValidAM() {
		Date now = new Date(); 
		Date expected = new Date(now.getTime());
		expected.setHours(7);
		expected.setMinutes(7);
		
		Date actual = TimeAndExpenseUtil.constructDateFromDateAndTime(now, "07:07 AM");
		
		assertEquals(expected.getTime(), actual.getTime());
	}

	@Test
	public void testConstructDateFromDateAndTime_ValidPM() {
		Date now = new Date(); 
		Date expected = new Date(now.getTime());
		expected.setHours(19);
		expected.setMinutes(7);
		
		Date actual = TimeAndExpenseUtil.constructDateFromDateAndTime(now, "07:07 PM");
		
		assertEquals(expected.getTime(), actual.getTime());
	}

	@Test
	public void testConstructDateFromDateAndTime_Invalid() {
		Date now = new Date(); 
		Date expected = new Date(now.getTime());
		expected.setHours(19);
		expected.setMinutes(7);
		
		Date actual = TimeAndExpenseUtil.constructDateFromDateAndTime(now, "mohnish");
		
		assertNull(actual);
	}

	@Test
	public void testConstructDateFromDateAndTime_InvalidFormat() {
		Date now = new Date(); 
		Date expected = new Date(now.getTime());
		expected.setHours(19);
		expected.setMinutes(7);
		
		Date actual = TimeAndExpenseUtil.constructDateFromDateAndTime(now, "07:7");
		
		assertNull(actual);
	}
	
	@Test
	public void testConstructTimeString_NullDate(){
		assertEquals("", TimeAndExpenseUtil.constructTimeString(null));
	}

	@Test
	public void testConstructTimeString_ValidDate(){
		Date now = new Date();
		now.setHours(5);
		now.setMinutes(4);
		
		assertEquals("5:04 AM", TimeAndExpenseUtil.constructTimeString(now));
	}
	
	@Test
	public void testConstructDateString(){
		Date now = new Date();
		
		assertEquals("04/01/2010", TimeAndExpenseUtil.constructDateString(now));
	}
	
}

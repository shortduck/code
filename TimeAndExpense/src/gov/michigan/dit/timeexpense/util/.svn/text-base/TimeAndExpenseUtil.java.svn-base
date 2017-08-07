package gov.michigan.dit.timeexpense.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

/**
 * Utility class for generic functionality to be reused across the project.
 * 
 * @author chaudharym
 */
public class TimeAndExpenseUtil {

	private static Logger log = Logger.getLogger(TimeAndExpenseUtil.class);
	
	public String getCommaDelimitedValues(List<String> items){
		StringBuffer buff = new StringBuffer();
		
		for(String item : items){
			buff.append("'"+item+"'");
			buff.append(",");
		}
		return buff.substring(0, buff.length()-1);
	}
	
	public static Object getPrivateField(Object obj, Class clazz, String fieldname){
		Object result = null;
		
		try{
			Field field = clazz.getDeclaredField(fieldname);
			field.setAccessible(true);
			result = field.get(obj);
		}catch(Exception ex){}
		
		return result;
	}
	
	public String getJsonStringForGrid (List list){
		
		Gson gson = new Gson();
		StringBuilder jsonString = new StringBuilder ();
		jsonString.append("{\"numRows\":");
		jsonString.append(list.size());
		jsonString.append(",\"items\":");
		jsonString.append(gson.toJson(list));
		jsonString.append("}");
		return jsonString.toString();
	}
	
	/**
	 * Round to 2 decimal digits. This method will be used primarily for calculations
	 * @param toRound
	 * @return
	 */
	
	public static double roundToTwoDigits (double toRound){
		BigDecimal roundedValue = new BigDecimal (toRound);
		roundedValue = roundedValue.setScale(2, BigDecimal.ROUND_HALF_UP);
		return roundedValue.doubleValue();	
		
	}
	
	/**
	 * Rounds down to 2 decimal digits. This method will be used primarily for calculations
	 * @param toRound
	 * @return
	 */
	
	public static double roundToTwoDigitsDown (double toRound){
		BigDecimal roundedValue = new BigDecimal (toRound);
		roundedValue = roundedValue.setScale(2, BigDecimal.ROUND_DOWN);
		return roundedValue.doubleValue();	
		
	}
	
	/**
	 * Use for display purposes
	 * @param toRound
	 * @return
	 */
	
	public static String displayAmountTwoDigits (double toRound){
		DecimalFormat df = new DecimalFormat("####0.00");
		return df.format(toRound);
		
	}
	
	/**
	 * Use for display purposes
	 * @param toRound
	 * @return
	 */
	
	public static String displayAmountTwoDigits (String toRound){
		DecimalFormat df = new DecimalFormat("####0.00");
		return df.format(Double.parseDouble(toRound));
	}
	
	/**
	 * Format date and time fields for display
	 * @param date
	 * @return
	 */
	
	public static String displayDateTime (Date date){
		if (date != null){
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"); 
		return df.format(date);
		} else {
			return "";
		}
		
	}
	
	/**
	 * Format date and time fields for display
	 * @param date
	 * @return
	 */
	
	public static String displayDate (Date date){
		if (date != null){
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
		return df.format(date);
		} else {
			return "";
		}
		
	}
	
	/**
	 * Format date and time fields for display
	 * @param date
	 * @return
	 */
	
	public static Date getDateFromString (String date){
		Date convertedDate = null;
		if (!StringUtils.isEmpty(date)){
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
		try {
			convertedDate = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		} 
		return convertedDate;
	}
	
	/**
	 * Sets time component in the passed date format. The accepted time format
	 * is in the form NN:NN CC (N=> numeric and C=> alphabetical character) e.g.
	 * 7:04 AM, 08:45 PM etc.
	 * 
	 * @param date
	 * @param time
	 * @return
	 */
	public static Date constructDateFromDateAndTime(Date date, String time){
		if(date ==null || time == null || "".equals(time))
			return null;
		
	    //Set the time pattern string
		String timeRegexPattern = "^(([1-9])|(1[0-2])):[0-5][0-9]\\s[A-Za-z]{2}";
	      
	    if(!time.matches(timeRegexPattern)) return null;
		
	    int colonIndex = time.indexOf(":");
	    
	    int hrs = Integer.parseInt(time.substring(0, colonIndex));
	    if(hrs == 12) hrs = 0;
	    
	    int mins = Integer.parseInt(time.substring(colonIndex+1, colonIndex+3));
	    boolean isPMTime = "P".equalsIgnoreCase(time.substring(colonIndex+4, colonIndex+5));
	    
	    if(isPMTime) hrs += 12;
	    
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.HOUR_OF_DAY, hrs);
	    cal.set(Calendar.MINUTE, mins);
	    cal.set(Calendar.SECOND, 0);
	    
	    return cal.getTime();
	}

	
	/**
	 * Constructs a string representation of the time component of the provided
	 * date. The constructed time string is of the format "hh:mm am" e.g.
	 * 7:04 AM, 18:45 PM etc.
	 * 
	 * @param date
	 * @return String representing the time component
	 */
	public static String constructTimeString(Date date){
		return (date == null) ? "" : (new SimpleDateFormat("h:mm a")).format(date);
	}
	
	/**
	 * Format date and time fields for display
	 * @param date
	 * @return
	 */
	
	public static String constructDateString (Date date){
		return (date == null) ? "" : (new SimpleDateFormat("MM/dd/yyyy")).format(date);
	}
	
	/**
	 * Escapes a given JSON string and returns the escaped JSON as string. 
	 * 
	 * @param string in JSON format to be escaped
	 * @return escaped JSON format string
	 */
    static String escapeJSON(String s) {
    	StringBuffer sb = new StringBuffer();
    	
		for(int i=0;i<s.length();i++){
			char ch=s.charAt(i);
			switch(ch){
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
				sb.append("\\/");
				break;
			default:
                //Reference: http://www.unicode.org/versions/Unicode5.1.0/
				if((ch>='\u0000' && ch<='\u001F') || (ch>='\u007F' && ch<='\u009F') || (ch>='\u2000' && ch<='\u20FF')){
					String ss=Integer.toHexString(ch);
					sb.append("\\u");
					for(int k=0;k<4-ss.length();k++){
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				}
				else{
					sb.append(ch);
				}
			}
		}
	
		return sb.toString();
    }	
/**
 * Adds a backslash ("/") in front of an apostrophe "'". This is needed so that Ajax response load
 * correctly
 * @param s
 * @return
 */
   public static String escapeApostrophe(String s) {
	   StringBuilder sb = new StringBuilder("");
   	 if (s!= null){
   		 if (s.indexOf("'") >= 0){
   			sb.append(s.replaceAll("'", "\\\\'"));
   		 } else{
   			 // no "'" present, return original String
   			 return s;
   		 }
   			 
   	 }

   	 return sb.toString();
   }	
	
}

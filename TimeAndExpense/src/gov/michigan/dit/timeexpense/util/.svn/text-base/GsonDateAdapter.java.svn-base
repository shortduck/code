package gov.michigan.dit.timeexpense.util;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Custom Gson formatter for <code>java.util.Date</code> type.
 * It parses <code>java.util.Date</code> into "YYYY-MM-DD" format.
 * 
 * @author chaudharym
 */
public class GsonDateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date>{

	private SimpleDateFormat formatter = new SimpleDateFormat(IConstants.DEFAULT_DATE_FORMAT);
	
	public JsonElement serialize(Date date, Type type, JsonSerializationContext context) {
		return new JsonPrimitive(formatter.format(date));
	}

	public Date deserialize(JsonElement json, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		  if (!(json instanceof JsonPrimitive)) {
		      throw new JsonParseException("The date should be a string value");
		    }
		    try {
		      return formatter.parse(json.getAsString());
		    } catch (ParseException e) {
		      throw new JsonParseException(e);
		    }
	}
}

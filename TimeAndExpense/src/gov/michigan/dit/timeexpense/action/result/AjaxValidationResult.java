package gov.michigan.dit.timeexpense.action.result;

import gov.michigan.dit.timeexpense.action.BaseAction;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;

/**
 * Custom Struts result type providing the facility to push
 * validation errors to the client. It returns to types of errors:<br/>
 * <ul>
 * 	<li>Validation errors : Errors arising due to invalid data. E.g. required fields missing etc.</li>
 *  <li>Errors : Any errors arising in the input data due to the business rules.</li>
 * </ul>
 * 
 * @author chaudharym
 * 
 */
public class AjaxValidationResult implements Result {
	private static final long serialVersionUID = -244919224L;
	
	protected Map<String, String> jsonProperties;
	protected BaseAction action; 

	protected Gson parser;
	
	private static final String COLON = ":";
	private static final String COMMA = ",";
	private static final String EMPTY_JSON_SET_ENDING_WITH_COMMA = "{},";
	
	
	public AjaxValidationResult() {
		parser = new Gson();
		jsonProperties = new HashMap<String, String>();
	}
	
	/**
	 * Prepares response in json format for two error types:
	 * <ul>
	 * 	<li>Validation errors : Errors arising due to invalid data. E.g. required fields missing etc.</li>
	 *  <li>Errors : Any errors arising in the input data due to the business rules.</li>
	 * </ul>
	 */
	public void execute(ActionInvocation invocation) throws Exception {
		
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		PrintWriter responseStream = ServletActionContext.getResponse().getWriter();
		
		// as every Action extends from BaseAction
		action  = (BaseAction)invocation.getAction();
		
		populateJsonVariablesMap();
		
		StringBuilder buff = new StringBuilder();
		buff.append("{");
		
		for(String propertyName : jsonProperties.keySet()){
			String propertyValue = jsonProperties.get(propertyName);
			
			// if property exists, push it
			if(propertyValue != null && !"".equals(propertyValue)){
				buff.append(propertyName);
				buff.append(COLON);
				buff.append(propertyValue);
				buff.append(COMMA);
			// else push valid empty json object	
			}else{
				buff.append(propertyName);
				buff.append(COLON);
				buff.append(EMPTY_JSON_SET_ENDING_WITH_COMMA);
			}
		}
		
		buff.deleteCharAt(buff.length()-1);
		buff.append("}");
		
		// write all properties to output stream
		responseStream.append(buff.toString());
	}

	protected void populateJsonVariablesMap(){
		jsonProperties.put("validationErrors", getValidationErrorJson());
		jsonProperties.put("errors", getErrorsJson());
	}
	
	private String getValidationErrorJson() {
		// errors for basic data validation failure
		// e.g. required fields etc.
		return parser.toJson(action.getFieldErrors());
	}

	private String getErrorsJson() {
		// errors due to business rules invalidating input data
		return parser.toJson(action.getTimeExpenseErrors());
	}

}

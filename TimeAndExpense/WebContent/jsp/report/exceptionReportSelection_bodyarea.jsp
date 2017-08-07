<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@page
	import="gov.michigan.dit.timeexpense.model.core.UserProfile"%><%@taglib
	prefix="s" uri="/struts-tags"%><%@taglib
	uri="http://www.ibm.com/siteedit/sitelib" prefix="siteedit"%>

<s:form>
	<table height="523" border="0">
		<tr>
			<td style="height: 252px; width: 80%;" valign="top" width="774"
				height="234">

				<table border="0"
					style="font-size: 16pt; font-family: arial, helvetica, sans-serif">
					<tbody>
						<tr>
							<td align="center" width="490" height="41">Exception Report</td>
						</tr>
					</tbody>
				</table>

				<form action="expense_report_viewer.html">
					<fieldset style="color: black; width: 50%">
						<legend
							style="font-weight: bold; font-size: 9pt; font-family: arial, helvetica, sans-serif">Select
							employees</legend>
						<table
							style="font-size: 9pt; font-family: arial, helvetica, sans-serif">
							<tr height="10px">
								<td colspan="2"></td>
							</tr>
							<tr>
								<td>Dept&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td><input id="department_cb" name="chosenDept">
								</td>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
							</tr>
							<tr height="5px">
								<td colspan="2"></td>
							</tr>
							<tr>
								<td>Agency&nbsp;&nbsp;&nbsp;</td>
								<td><select name="agency_dd">
										<option value="01">08 Information Technology</option>
										<option value="02" selected="selected">01 Department
											of information Technology</option>
										<option value="03">867 Agency Services</option>
								</select></td>
							</tr>
							<tr height="5px">
								<td colspan="2"></td>
							</tr>
							<tr>
								<td>TKU&nbsp;&nbsp;&nbsp;</td>
								<td><select name="agency_dd">
										<option value="01">08 Information Technology</option>
										<option value="02">01 Department of information
											Technology</option>
										<option value="03" selected="selected">867 Agency
											Services</option>
								</select></td>
							</tr>
							<tr height="5px">
								<td colspan="2"></td>
							</tr>
							<tr>
								<td>EID&nbsp;&nbsp;&nbsp;</td>
								<td valign="bottom" colspan="2"><input name="eid" size="9"
									maxlength="9" value="">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="checkbox">My Employees</td>
							</tr>
						</table>
					</fieldset>

					<table>
						<tr height="5px">
							<td></td>
						</tr>
					</table>

					<fieldset style="color: black; width: 50%">
						<legend
							style="font-weight: bold; font-size: 9pt; font-family: arial, helvetica, sans-serif">Select
							expense reports</legend>
						<table
							style="font-size: 9pt; font-family: arial, helvetica, sans-serif">
							<tr height="20px">
								<td colspan="5"></td>
							</tr>
							<tr>
								<td>Payment Date from&nbsp;</td>
								<td><input name="pay_date_from" value="01/01/2008" size="8"
									maxlength="10">
								</td>
								<td><img
									src="${pageContext.request.contextPath}/images/calendar.gif"
									class="clImg" alt="" height="17">&nbsp;&nbsp;&nbsp;To&nbsp;&nbsp;</td>
								<td><input name="pay_date_to" value="01/31/2008" size="8"
									maxlength="10">
								</td>
								<td><img
									src="${pageContext.request.contextPath}/images/calendar.gif"
									class="clImg" alt="" height="17">
								</td>
							</tr>
							<tr height="15px">
								<td colspan="5"></td>
							</tr>
							<tr>
								<td>Expense Date from&nbsp;</td>
								<td><input name="travel_date_from" value="01/01/2008"
									size="8" maxlength="10">
								</td>
								<td><img
									src="${pageContext.request.contextPath}/images/calendar.gif"
									class="clImg" alt="" height="17">&nbsp;&nbsp;&nbsp;To&nbsp;&nbsp;</td>
								<td><input name="travel_date_to" value="01/31/2008"
									size="8" maxlength="10">
								</td>
								<td><img
									src="${pageContext.request.contextPath}/images/calendar.gif"
									class="clImg" alt="" height="17">
								</td>
							</tr>
							<tr height="25px">
								<td colspan="5"></td>
							</tr>
							<tr>
								<td>Error codes between&nbsp;</td>
								<td><input name="travel_date_from" value="00001" size="5"
									maxlength="5">
								</td>
								<td align="center">&nbsp;and&nbsp;</td>
								<td><input name="travel_date_to" value="99999" size="5"
									maxlength="5">
								</td>
								<td>&nbsp;</td>
							</tr>
							<tr height="10px">
								<td colspan="5"></td>
							</tr>
							<tr>
								<td colspan="5">Severity&nbsp;&nbsp;&nbsp; <input
									type="radio" name="severity" value="error">Errors<input>
									&nbsp;&nbsp;&nbsp; <input type="radio" name="severity"
									value="error">Warnings<input>
									&nbsp;&nbsp;&nbsp; <input type="radio" name="severity"
									value="error" checked="checked">Both<input></td>
							</tr>
						</table>
					</fieldset>

					<table width="50%">
						<tr>
							<td align="right"><input type="submit" name="select"
								value=" Select "></td>
						</tr>
					</table>
					<s:form></s:form>

				</form>
			</td>
		</tr>
	</table>
</s:form>
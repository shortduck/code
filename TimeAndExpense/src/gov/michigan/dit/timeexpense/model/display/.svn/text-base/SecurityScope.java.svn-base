package gov.michigan.dit.timeexpense.model.display;

import gov.michigan.dit.timeexpense.model.core.Agency;
import gov.michigan.dit.timeexpense.model.core.Department;
import gov.michigan.dit.timeexpense.model.core.Tku;
import gov.michigan.dit.timeexpense.model.core.SystemCodes;
import gov.michigan.dit.timeexpense.model.core.SystemCodesPK;
import java.util.List;

/**
 * Provides convenient access to departments, agencies and tkus. Also exposes
 * methods to provide custom JSON formats for them.
 * 
 * @author chaudharym
 */
public class SecurityScope {

	private List<Department> departments;
	private List<Agency> agencies;
	private List<Tku> tkus;
	

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public List<Agency> getAgencies() {
		return agencies;
	}

	public void setAgencies(List<Agency> agencies) {
		this.agencies = agencies;
	}

	public List<Tku> getTkus() {
		return tkus;
	}

	public void setTkus(List<Tku> tkus) {
		this.tkus = tkus;
	}

	

	public String getDepartmentsJsonWithoutArray() {
		StringBuilder buff = new StringBuilder();

		if (departments == null) {
			return "";
		}

		for (Department dept : departments) {
			buff.append("{code:\"");
			buff.append(dept.getDepartment());
			buff.append("\",display:\"");
			buff.append(dept.getDepartment());
			buff.append(" ");
			buff.append(dept.getName());
			buff.append("\"},");
		}

		removeLastCommaCharacter(buff);
		return buff.toString();
	}

	
	public String getDepartmentsMinusAllScopeJsonWithoutArray() {
		StringBuilder buff = new StringBuilder();

		if (departments == null) {
			return "";
		}

		for (Department dept : departments) {
			if ("ALL".equalsIgnoreCase(dept.getDepartment())) {
				continue;
			}
			buff.append("{code:\"");
			buff.append(dept.getDepartment());
			buff.append("\",display:\"");
			buff.append(dept.getDepartment());
			buff.append(" ");
			buff.append(dept.getName());
			buff.append("\"},");
		}

		removeLastCommaCharacter(buff);
		return buff.toString();

	}

	public String getCombinedDepartmentCodeNameJson() {
		StringBuilder buff = new StringBuilder();

		buff.append("[");
		buff.append(getDepartmentsJsonWithoutArray());
		buff.append("]");

		return buff.toString();
	}

	public String getCombinedDepartmentCodeNameWithEmptyElementJson() {
		StringBuilder buff = new StringBuilder();

		buff.append("[");
		buff.append(getEmptyElementJson());
		buff.append(",");
		buff.append(getDepartmentsJsonWithoutArray());
		// remove trailing comma if no depts present!
		removeLastCommaCharacter(buff);
		buff.append("]");

		return buff.toString();
	}

	

	
	public String getCombinedDepartmentCodeNameWithEmptyElementWithoutAllScopeJson() {
		StringBuilder buff = new StringBuilder();

		buff.append("[");
		buff.append(getEmptyElementJson());
		buff.append(",");
		buff.append(getDepartmentsMinusAllScopeJsonWithoutArray());
		// remove trailing comma if no depts present!
		removeLastCommaCharacter(buff);
		buff.append("]");

		return buff.toString();
	}

	public String getAgenciesJsonWithoutArray() {
		StringBuilder buff = new StringBuilder();

		if (agencies == null) {
			return "";
		}

		for (Agency agency : agencies) {
			buff.append("{code:\"");
			buff.append(agency.getAgencyPK().getAgency());
			buff.append("\",display:\"");
			buff.append(agency.getAgencyPK().getAgency());
			buff.append(" ");
			buff.append(agency.getName());
			buff.append("\"},");
		}

		removeLastCommaCharacter(buff);
		return buff.toString();
	}

	public String getAgenciesMinusAllScopeJsonWithoutArray() {
		StringBuilder buff = new StringBuilder();

		if (agencies == null) {
			return "";
		}

		for (Agency agency : agencies) {
			if ("ALL".equalsIgnoreCase(agency.getAgencyPK().getAgency())) {
				continue;
			}
			buff.append("{code:\"");
			buff.append(agency.getAgencyPK().getAgency());
			buff.append("\",display:\"");
			buff.append(agency.getAgencyPK().getAgency());
			buff.append(" ");
			buff.append(agency.getName());
			buff.append("\"},");
		}

		removeLastCommaCharacter(buff);
		return buff.toString();
	}

	public String getCombinedAgencyCodeNameJson() {
		StringBuilder buff = new StringBuilder();

		buff.append("[");
		buff.append(getAgenciesJsonWithoutArray());
		buff.append("]");

		return buff.toString();
	}

	public String getCombinedAgencyCodeNameWithEmptyElementJson() {
		StringBuilder buff = new StringBuilder();

		buff.append("[");
		buff.append(getEmptyElementJson());
		buff.append(",");
		buff.append(getAgenciesJsonWithoutArray());
		// remove trailing comma if no agencies present!
		removeLastCommaCharacter(buff);
		buff.append("]");

		return buff.toString();
	}

	public String getCombinedAgencyCodeNameWithEmptyElementWithoutAllScopeJson() {
		StringBuilder buff = new StringBuilder();

		buff.append("[");
		buff.append(getEmptyElementJson());
		buff.append(",");
		buff.append(getAgenciesMinusAllScopeJsonWithoutArray());
		// remove trailing comma if no agencies present!
		removeLastCommaCharacter(buff);
		buff.append("]");

		return buff.toString();
	}

	public String getTkusJsonWithoutArray() {
		StringBuilder buff = new StringBuilder();

		if (tkus == null) {
			return "";
		}

		for (Tku tku : tkus) {
			buff.append("{code:\"");
			buff.append(tku.getTkuPK().getTku());
			buff.append("\",display:\"");
			buff.append(tku.getTkuPK().getTku());
			buff.append(" ");
			buff.append(tku.getName());
			buff.append("\"},");
		}

		removeLastCommaCharacter(buff);
		return buff.toString();
	}

	public String getTkusMinusAllScopeJsonWithoutArray() {
		StringBuilder buff = new StringBuilder();

		if (tkus == null) {
			return "";
		}

		for (Tku tku : tkus) {
			if ("ALL".equalsIgnoreCase(tku.getTkuPK().getTku())) {
				continue;
			}

			buff.append("{code:\"");
			buff.append(tku.getTkuPK().getTku());
			buff.append("\",display:\"");
			buff.append(tku.getTkuPK().getTku());
			buff.append(" ");
			buff.append(tku.getName());
			buff.append("\"},");
		}

		removeLastCommaCharacter(buff);
		return buff.toString();
	}

	public String getCombinedTkuCodeNameJson() {
		StringBuilder buff = new StringBuilder();

		buff.append("[");
		buff.append(getTkusJsonWithoutArray());
		buff.append("]");

		return buff.toString();
	}

	public String getCombinedTkuCodeNameWithEmptyElementJson() {
		StringBuilder buff = new StringBuilder();

		buff.append("[");
		buff.append(getEmptyElementJson());
		buff.append(",");
		buff.append(getTkusJsonWithoutArray());
		// remove trailing comma if no agencies present!
		removeLastCommaCharacter(buff);
		buff.append("]");

		return buff.toString();
	}

	public String getCombinedTkuCodeNameWithEmptyElementWithoutAllScopeJson() {
		StringBuilder buff = new StringBuilder();

		buff.append("[");
		buff.append(getEmptyElementJson());
		buff.append(",");
		buff.append(getTkusMinusAllScopeJsonWithoutArray());
		// remove trailing comma if no agencies present!
		removeLastCommaCharacter(buff);
		buff.append("]");

		return buff.toString();
	}

	private void removeLastCommaCharacter(StringBuilder buff) {
		if (buff.length() > 0 && ',' == buff.charAt(buff.length() - 1))
			buff.deleteCharAt(buff.length() - 1);
	}

	private String getEmptyElementJson() {
		return "{code:\"\",display:\"\"}";
	}

	public String getFirstDepartmentDisplayValue() {
		if (departments == null || departments.size() < 1)
			return "";

		return departments.get(0).getDepartment() + " "
				+ departments.get(0).getName();
	}


	public String getFirstAgencyDisplayValue() {
		if (agencies == null || agencies.size() < 1)
			return "";

		return agencies.get(0).getAgencyPK().getAgency() + " "
				+ agencies.get(0).getName();
	}

	public String getFirstTkuDisplayValue() {
		if (tkus == null || tkus.size() < 1)
			return "";

		return tkus.get(0).getTkuPK().getTku() + " " + tkus.get(0).getName();
	}

}

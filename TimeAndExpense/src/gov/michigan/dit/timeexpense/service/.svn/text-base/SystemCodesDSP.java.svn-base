package gov.michigan.dit.timeexpense.service;

/**
 * @Smriti
 */

import gov.michigan.dit.timeexpense.dao.SystemCodeDAO;
import gov.michigan.dit.timeexpense.model.core.SystemCodes;
import gov.michigan.dit.timeexpense.model.core.SystemCodesPK;
import gov.michigan.dit.timeexpense.model.db.ErrorMessages;
import gov.michigan.dit.timeexpense.model.display.NewDisplaySystemCd;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

public class SystemCodesDSP {

	private SystemCodeDAO syscodeDao;
	private SecurityManager security;
	CommonDSP commonDsp;

	public SystemCodesDSP(EntityManager em) {
		syscodeDao = new SystemCodeDAO(em);
		security = new SecurityManager(em);
		commonDsp = new CommonDSP(em);
		// UserProfile profile = super.getLoggedInUser();

	}
	
	public ErrorMessages getErrorCode(String retvalidate )
	{
		ErrorMessages errMess = commonDsp.getErrorCode(retvalidate);
		return errMess;
	}
	/**
	 * 
	 * @param tempPassObj
	 * @return boolean true all values pass , pass values fail
	 */
	 public String validateData(SystemCodes tempPassObj)
	{
		
		 if(tempPassObj.getSystemCodesPK().getSystemCode()==null || tempPassObj.getSystemCodesPK().getSystemCode().trim().length()==0)
		 {
			 return IConstants.SYSTEM_CODE_VALUE_MISSING;
		 }
		 if(tempPassObj.getSystemCodesPK().getStartDate()==null || tempPassObj.getSystemCodesPK().getStartDate().toString().trim().length()==0)
		 {
			 return IConstants.SYSTEM_CODE_ST_DATE_MISSING;
		 }
		 if(tempPassObj.getDescription()==null || tempPassObj.getDescription().toString().trim().length()==0)
		 {
			 return IConstants.SYSTEM_CODE_DESC_MISSING;
		 }
		 if(tempPassObj.getValue()==null||tempPassObj.getValue().trim().length()==0)
		 {
			 return IConstants.SYSTEM_CODE_VALUE_MISSING;
		 }
		 if(tempPassObj.getEndDate()==null ||tempPassObj.getEndDate().toString().trim().length()==0)
		 {
			 return IConstants.SYSTEM_CODE_END_DATE_MISSING;
		 } 
		return IConstants.SUCCESS;
	}

	/**
	 * The call from action to get the list of distinct System Codes. Duplicate
	 * display entries are filtered by using rge Set
	 * 
	 * @return List<SystemCodes>
	 */
	public List<SystemCodes> getDistinctSystemCodes() {
		List<SystemCodes> sysCodes = null;
		List<SystemCodes> sysCodesR = null;
		sysCodes = syscodeDao.findAllSysCodes();

		HashSet hs = new HashSet();
		int i;
		List<NewDisplaySystemCd> sysCodesN = new ArrayList<NewDisplaySystemCd>(
				sysCodes.size());
		sysCodesR = new ArrayList<SystemCodes>(sysCodes.size());
		String tempKey = "";
		for (i = 0; i < sysCodes.size(); i++) {
			NewDisplaySystemCd tempSys = new NewDisplaySystemCd();
			tempSys.setSystemCode(sysCodes.get(i).getSystemCodesPK()
					.getSystemCode());
			tempSys.setDescription(sysCodes.get(i).getDescription());

			tempKey = sysCodes.get(i).getSystemCodesPK().getSystemCode() + ","
					+ sysCodes.get(i).getDescription();
			if (hs.contains(tempKey)) {
				// Avoid duplicate display
			} else {
				hs.add(tempKey);
				sysCodesR.add(sysCodes.get(i));
				sysCodesN.add(tempSys);

			}
		}
		return sysCodesR;
	}


	/**
	 * Gets the selected system code *
	 * 
	 * @param codeVal
	 * @return List<NewDisplaySystemCd>
	 */
	public List<NewDisplaySystemCd> getSelectedCode(String codeVal) {
		String newCodeVal;

		newCodeVal = codeVal.substring(0, 4);
		List<SystemCodes> sysCodeSelect = syscodeDao
				.findSelectedCode(newCodeVal);
		List<NewDisplaySystemCd> newClassCodeDisplay = new ArrayList(
				sysCodeSelect.size());
		for (int i = 0; i < sysCodeSelect.size(); i++) {
			NewDisplaySystemCd newTemp = new NewDisplaySystemCd();
			newTemp.setDescription(sysCodeSelect.get(i).getDescription());
			newTemp.setEndDate(sysCodeSelect.get(i).getEndDate());
			newTemp.setModifiedDate(sysCodeSelect.get(i).getModifiedDate());
			newTemp.setModifiedUserId(sysCodeSelect.get(i).getModifiedUserId());
			newTemp.setStartDate(sysCodeSelect.get(i).getSystemCodesPK()
					.getStartDate());
			newTemp.setSystemCode(sysCodeSelect.get(i).getSystemCodesPK()
					.getSystemCode());
			newTemp.setValue(sysCodeSelect.get(i).getValue());
			newTemp.setEdit("edit");
			newClassCodeDisplay.add(newTemp);

		}
		return newClassCodeDisplay;

	}

	/**
	 * Gets the list of system codes This method is called to display the search
	 * results in the object class NewDisplaySystemCd
	 * 
	 * @return List<NewDisplaySystemCd>
	 */
	public List<NewDisplaySystemCd> getAllSystemCodes() {
		List<SystemCodes> sysCodeSelect = syscodeDao.findAllSysCodes();
		List<NewDisplaySystemCd> newClassCodeDisplay = new ArrayList(
				sysCodeSelect.size());
		for (int i = 0; i < sysCodeSelect.size(); i++) {
			NewDisplaySystemCd newTemp = new NewDisplaySystemCd();
			newTemp.setDescription(sysCodeSelect.get(i).getDescription());
			newTemp.setEndDate(sysCodeSelect.get(i).getEndDate());
			newTemp.setModifiedDate(sysCodeSelect.get(i).getModifiedDate());
			newTemp.setModifiedUserId(sysCodeSelect.get(i).getModifiedUserId());
			newTemp.setStartDate(sysCodeSelect.get(i).getSystemCodesPK()
					.getStartDate());
			newTemp.setSystemCode(sysCodeSelect.get(i).getSystemCodesPK()
					.getSystemCode());
			newTemp.setValue(sysCodeSelect.get(i).getValue());
			newTemp.setEdit("edit");
			newClassCodeDisplay.add(newTemp);

		}
		return newClassCodeDisplay;

	}

	/**
	 * Action to delete a selected system Code Deletes the selected system code
	 * *
	 * 
	 * @param sysUpdCd
	 * @return List<NewDisplaySystemCd>
	 */
	public List<NewDisplaySystemCd> deleteSystemCodes(SystemCodes sysUpdCd) {
		NewDisplaySystemCd retSysUpdCd = new NewDisplaySystemCd();
		boolean retSysCd = syscodeDao.deleteSystemCode(sysUpdCd);
		List<NewDisplaySystemCd> modRetSysCdList = new ArrayList(1);
		NewDisplaySystemCd modRetSysCdOne = new NewDisplaySystemCd();
		modRetSysCdOne.setSystemCode("");
		modRetSysCdOne.setStartDate(null);
		modRetSysCdOne.setDescription("");
		modRetSysCdOne.setEndDate(null);
		modRetSysCdOne.setModifiedDate(null);
		modRetSysCdOne.setModifiedUserId("");
		modRetSysCdOne.setValue("");

		modRetSysCdList.add(modRetSysCdOne);
		return modRetSysCdList;
	}

	/**
	 * Modifies the Exisiting system code The modified value is dislayed by
	 * class NewDisplaySystemCd
	 * 
	 * @param sysUpdCd
	 * @return List<NewDisplaySystemCd>
	 * @throws Exception
	 */
	public List<NewDisplaySystemCd> updateSystemCodes(SystemCodes sysUpdCd)
			throws Exception {
		// profile.getUserId();
		NewDisplaySystemCd retSysUpdCd = new NewDisplaySystemCd();
		SystemCodes retSysCd = syscodeDao.updateSysCdData(sysUpdCd);
		List<NewDisplaySystemCd> modRetSysCdList = new ArrayList(1);
		NewDisplaySystemCd modRetSysCdOne = new NewDisplaySystemCd();
		modRetSysCdOne.setSystemCode(retSysCd.getSystemCodesPK()
				.getSystemCode());
		modRetSysCdOne.setStartDate(retSysCd.getSystemCodesPK().getStartDate());
		modRetSysCdOne.setDescription(retSysCd.getDescription());
		modRetSysCdOne.setEndDate(retSysCd.getEndDate());
		// modRetSysCdOne.setModifiedDate(retSysCd.getModifiedDate());
		modRetSysCdOne.setModifiedDate(getCurrentDateTs());
		modRetSysCdOne.setModifiedUserId(retSysCd.getModifiedUserId());
		modRetSysCdOne.setValue(retSysCd.getValue());
		modRetSysCdOne.setDateCurrent(getCurrentdate());
		modRetSysCdList.add(modRetSysCdOne);
		return modRetSysCdList;
	}

	/**
	 * This method is used called to create a new code
	 * 
	 * @param sysUpdCd
	 * @return List<NewDisplaySystemCd>
	 */
	public List<NewDisplaySystemCd> createSystemCodes(SystemCodes sysUpdCd) {
		NewDisplaySystemCd retSysUpdCd = new NewDisplaySystemCd();
		SystemCodes retSysCd = syscodeDao.createSysCdData(sysUpdCd);
		List<NewDisplaySystemCd> modRetSysCdList = new ArrayList(1);
		NewDisplaySystemCd modRetSysCdOne = new NewDisplaySystemCd();
		modRetSysCdOne.setSystemCode(retSysCd.getSystemCodesPK()
				.getSystemCode());
		modRetSysCdOne.setStartDate(retSysCd.getSystemCodesPK().getStartDate());
		modRetSysCdOne.setDescription(retSysCd.getDescription());
		modRetSysCdOne.setEndDate(retSysCd.getEndDate());
		modRetSysCdOne.setModifiedDate(retSysCd.getModifiedDate());
		modRetSysCdOne.setModifiedUserId(retSysCd.getModifiedUserId());
		modRetSysCdOne.setValue(retSysCd.getValue());

		modRetSysCdOne.setDateCurrent(getCurrentdate());
		modRetSysCdList.add(modRetSysCdOne);
		return modRetSysCdList;
	}

	/**
	 * gets current date *
	 * 
	 * @return date in String format
	 */
	public String getCurrentdate() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		// get current date time with Date()
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		String retDate = dateFormat.format(date).toString();
		return retDate;
	}

	/**
	 * This is used to get date
	 * 
	 * @return current date
	 * @throws Exception
	 */
	public Date getCurrentDateTs() throws Exception {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat formatter;
		formatter = new SimpleDateFormat("MM/dd/yyyy");
		String retDate = dateFormat.format(date).toString();
		date = (Date) formatter.parse(retDate);
		Date date1 = new Date();
		date1 = (Date) formatter.parse(dateFormat.format(date));
		return date1;
	}

}

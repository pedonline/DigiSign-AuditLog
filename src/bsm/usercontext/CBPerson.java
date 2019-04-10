/**
* @author Weerayut Wichaidit
* @version 2017-05-15
*/
package bsm.usercontext;

import java.util.Date;
import java.util.List;

import bsm.dspo.dao.common.DigiSignVerifyInfomation;
import dspo.hrm.model.Person;
import dspo.util.CommonLog;
import dspo.util.LOG_LEVEL;

public class CBPerson implements java.io.Serializable {

	private static String className = "CBPerson";

	private static final long serialVersionUID = -6711456332989671275L;

	private Integer PersonID = 0;
	private String tName = "";
	private String tLastName = "";
	private String PersonCode = "";
	private Date AdmissionDate = null;
	private String InternalPhoneNumber = "";
	private Integer Gender = 0;
	private String EMail = "";
	private Date BirthDay = null;
	private List<DigiSignVerifyInfomation> DigiSignVerifyInfomationList = null;
	private Boolean IsEntityValid = false;

	public void setHBtoCB(Person ao_Obj) {
		String methodName = "setHBtoCB";
		CommonLog.Print(LOG_LEVEL.INFO_LEVEL, className, methodName, "[START]");
		this.setPersonID(ao_Obj.getPersonID());
		this.setPersonCode(ao_Obj.getCode());
		this.settName(ao_Obj.getTName());
		this.settLastName(ao_Obj.getTLastName());
		this.setAdmissionDate(ao_Obj.getAdmissionDate());
		this.setInternalPhoneNumber(ao_Obj.getInternalPhoneNumber());

		if (ao_Obj.getOBossPerson() != null) {
			Person lo_bossPerson = ao_Obj.getOBossPerson();
			StringBuffer ls_BossPersonName = new StringBuffer();
			if (lo_bossPerson.getTPrefixName() != null) {
				ls_BossPersonName.append(lo_bossPerson.getTPrefixName() + " ");
			}
			if (lo_bossPerson.getTName() != null) {
				ls_BossPersonName.append(lo_bossPerson.getTName() + " ");
			}
			if (lo_bossPerson.getTLastName() != null) {
				ls_BossPersonName.append(lo_bossPerson.getTLastName() + " ");
			}

		}

		this.setBirthDay(ao_Obj.getBirthDay());
		this.setEMail(ao_Obj.getEmailAddress());
		this.setInternalPhoneNumber(ao_Obj.getInternalPhoneNumber());

		CommonLog.Print(LOG_LEVEL.INFO_LEVEL, className, methodName, "[END]");

	}

	public void setCBToHB(Person ao_Obj) {
		String methodName = "setCBtoHB";
		CommonLog.Print(LOG_LEVEL.INFO_LEVEL, className, methodName, "[START]");

		ao_Obj.setCode(this.getPersonCode());
		ao_Obj.setLoginName(this.getPersonCode());


		if (this.gettName() != null) {
			ao_Obj.setTName(this.gettName());
		} else {
		}
		if (this.gettLastName() != null) {
			ao_Obj.setTLastName(this.gettLastName());
		} else {
		}

		if (this.getAdmissionDate() != null) {
			ao_Obj.setAdmissionDate(this.getAdmissionDate());
		} else {
			ao_Obj.setAdmissionDate(null);
		}

		if (this.getInternalPhoneNumber() != null) {
			ao_Obj.setInternalPhoneNumber(this.getInternalPhoneNumber());
		} else {
		}


		ao_Obj.setGender(this.getGender());

		if (this.getBirthDay() != null) {
			ao_Obj.setBirthDay(this.getBirthDay());
		} else {
			ao_Obj.setBirthDay(null);
		}

		CommonLog.Print(LOG_LEVEL.INFO_LEVEL, className, methodName, "[END]");
	}

	public Integer getPersonID() {
		return PersonID;
	}

	public void setPersonID(Integer personID) {
		PersonID = personID;
	}

	public String gettName() {
		return tName;
	}

	public void settName(String tName) {
		this.tName = tName;
	}

	public String gettLastName() {
		return tLastName;
	}

	public void settLastName(String tLastName) {
		this.tLastName = tLastName;
	}

	public String getPersonCode() {
		return PersonCode;
	}

	public void setPersonCode(String personCode) {
		PersonCode = personCode;
	}

	public Date getAdmissionDate() {
		return AdmissionDate;
	}

	public void setAdmissionDate(Date admissionDate) {
		AdmissionDate = admissionDate;
	}

	public String getInternalPhoneNumber() {
		return InternalPhoneNumber;
	}

	public void setInternalPhoneNumber(String internalPhoneNumber) {
		InternalPhoneNumber = internalPhoneNumber;
	}

	public Integer getGender() {
		return Gender;
	}

	public void setGender(Integer gender) {
		Gender = gender;
	}

	public String getEMail() {
		return EMail;
	}

	public void setEMail(String eMail) {
		EMail = eMail;
	}


	public Date getBirthDay() {
		return BirthDay;
	}

	public void setBirthDay(Date birthDay) {
		BirthDay = birthDay;
	}

	public List<DigiSignVerifyInfomation> getDigiSignVerifyInfomationList() {
		return DigiSignVerifyInfomationList;
	}

	public void setDigiSignVerifyInfomationList(List<DigiSignVerifyInfomation> digiSignVerifyInfomationList) {
		DigiSignVerifyInfomationList = digiSignVerifyInfomationList;
	}

	public Boolean getIsEntityValid() {
		return IsEntityValid;
	}

	public void setIsEntityValid(Boolean isEntityValid) {
		IsEntityValid = isEntityValid;
	}
	
}

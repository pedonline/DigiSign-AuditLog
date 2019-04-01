/**
* @author Weerayut Wichaidit
* @version 2018-08-21
*/
package bsm.dsal.model;

public class Message {
	String keysotreType;
	String option;
	String keysotreFile;
	String passwordKeysotre;
	String userName;
	String userPassword;

	
	public Message() {
		this.keysotreType = "";
		this.option = "";
		this.keysotreFile = null;
		this.passwordKeysotre = "";
		this.userName = "";
		this.userPassword = "";
	
	}
	
	public Message(String keysotreType,String option, String keysotreFile, String passwordKeysotre, String userName, String userPassword,String contentCode,String content) {
		this.keysotreType = keysotreType;
		this.option = option;
		this.keysotreFile = keysotreFile;
		this.passwordKeysotre = passwordKeysotre;
		this.userName = userName;
		this.userPassword = userPassword;
	
	}
	public Message(String keysotreType,String option, String keysotreFile, String passwordKeysotre, String userName, String userPassword,String contentCode, String content,String formFieldName, String ownerSignatureCode, String ownerSignatureName,String workPositionCode, String workPositionName, String workOrganizationCode, String workOrganizationName,String location, String reason, String licen) {
		this.keysotreType = keysotreType;
		this.option = option;
		this.keysotreFile = keysotreFile;
		this.passwordKeysotre = passwordKeysotre;
		this.userName = userName;
		this.userPassword = userPassword;		
	}
	public String getKeysotreType() {
		return keysotreType;
	}

	public void setKeysotreType(String keysotreType) {
		this.keysotreType = keysotreType;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}


	public String getKeysotreFile() {
		return keysotreFile;
	}

	public void setKeysotreFile(String keysotreFile) {
		this.keysotreFile = keysotreFile;
	}

	public String getPasswordKeysotre() {
		return passwordKeysotre;
	}

	public void setPasswordKeysotre(String passwordKeysotre) {
		this.passwordKeysotre = passwordKeysotre;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

}

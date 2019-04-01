/**
* @author Weerayut Wichaidit
* @version 2018-08-21
*/
package bsm.dsal.model;

public class ResponseMessage {
	String responseMethod;
	String contentCode;
	String content;
	String digisignAlias;
	String digisignOTK;
	String digisignCertificate;
	
	
	
	public ResponseMessage() {
		this.responseMethod = "";
		this.contentCode = "";
		this.content = "";
		this.digisignAlias = "";
		this.digisignOTK = "";
		this.digisignCertificate = "";
	}


	public ResponseMessage(String responseMethod,String option, String keysotreFile, String passwordKeysotre, String userName, String userPassword,String contentCode, String content) {
		this.responseMethod = responseMethod;
		this.contentCode = contentCode;
		this.content = content;

	}


	public String getResponseMethod() {
		return responseMethod;
	}


	public void setResponseMethod(String responseMethod) {
		this.responseMethod = responseMethod;
	}


	public String getContentCode() {
		return contentCode;
	}


	public void setContentCode(String contentCode) {
		this.contentCode = contentCode;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getDigisignOTK() {
		return digisignOTK;
	}


	public void setDigisignOTK(String digisignOTK) {
		this.digisignOTK = digisignOTK;
	}

	public String getDigisignCertificate() {
		return digisignCertificate;
	}


	public void setDigisignCertificate(String digisignCertificate) {
		this.digisignCertificate = digisignCertificate;
	}


	public String getDigisignAlias() {
		return digisignAlias;
	}


	public void setDigisignAlias(String digisignAlias) {
		this.digisignAlias = digisignAlias;
	}


	@Override
	public String toString() {
		return "ResponseMessage [responseMethod=" + responseMethod + ", contentCode=" + contentCode + ", content="
				+ content + ", digisignOTK=" + digisignOTK + ", digisignCertificate=" + digisignCertificate + "]";
	}


	



}

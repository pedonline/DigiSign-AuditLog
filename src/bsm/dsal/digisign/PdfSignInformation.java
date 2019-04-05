/**
* @author Weerayut Wichaidit
* @version 2018-09-19
*/
package bsm.dsal.digisign;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.codec.binary.Base64;

import dspo.util.CommonLog;
import dspo.util.LOG_LEVEL;
import esi.ds.server.common.SecreteKeyCryptography;
import esi.ds.server.model.PdfProperty;


@ManagedBean(name = "PdfSignInformation", eager=true)
@SessionScoped
public class PdfSignInformation implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7080040556186577128L;
	private String digisignAlias;
	private PdfProperty pdfProperty = null;
	private String pdfContentUUID;
	private String pdfSignedContentUUID;
	private byte[] pdfContent = null;
	private String pdfContentString = "";
	
	
	public PdfSignInformation() {
		super();
		this.digisignAlias = "";
		this.pdfProperty = new PdfProperty();
		this.pdfContent = null;
		this.pdfContentString = "";
	}
	
	public void reset() {
		this.digisignAlias = "";
		this.pdfProperty = new PdfProperty();
		this.pdfContent = null;
		this.pdfContentUUID = null;
		this.pdfSignedContentUUID = null;
		this.pdfContentString = "";
	}
	
	public PdfSignInformation(String digisignAlias, PdfProperty pdfProperty,byte[] pdfContent) {
		super();
		this.digisignAlias = digisignAlias;
		this.pdfProperty = pdfProperty;
		this.pdfContent = pdfContent;
		this.pdfContentString = Base64.encodeBase64String(pdfContent);
	
	}
	public PdfSignInformation(String digisignAlias, String formFieldName, String ownerSignatureCode, String ownerSignatureName, String workPositionCode, String workPositionName, String workOrganizationCode, String workOrganizationName, String location, String reason, String licen, byte[] pdfContent) {
		super();
		this.digisignAlias = digisignAlias;
		this.pdfProperty = new PdfProperty( formFieldName,  ownerSignatureCode,  ownerSignatureName,  workPositionCode,  workPositionName,  workOrganizationCode,  workOrganizationName,  location,  reason,  licen);
		this.pdfContent = pdfContent;
		this.pdfContentString = Base64.encodeBase64String(pdfContent);
	
	}
	public String getDigisignAlias() {
		return digisignAlias;
	}
	public void setDigisignAlias(String digisignAlias) {
		this.digisignAlias = digisignAlias;
	}
	public PdfProperty getPdfProperty() {
		return pdfProperty;
	}
	public void setPdfProperty(PdfProperty pdfProperty) {
		this.pdfProperty = pdfProperty;
	}
	public byte[] getPdfContent() {
		return pdfContent;
	}
	public void setPdfContent(byte[] pdfContent) {
		this.pdfContent = pdfContent;
		this.pdfContentString = Base64.encodeBase64String(pdfContent);
	}
	public String getPdfContentString() {
		this.pdfContentString = Base64.encodeBase64String(this.pdfContent);
		return pdfContentString;
	}
	public void setPdfContentString(String pdfContentString) {
		this.pdfContentString = pdfContentString;
	}
	public String getPdfContentUUID() {
		return pdfContentUUID;
	}
	public void setPdfContentUUID(String pdfContentUUID) {
		this.pdfContentUUID = pdfContentUUID;
	}
	
	public String getPdfSignedContentUUID() {
		return pdfSignedContentUUID;
	}
	public void setPdfSignedContentUUID(String pdfSignedContentUUID) {
		this.pdfSignedContentUUID = pdfSignedContentUUID;
	}
	public String getApplicationURL() {
		String lo_ApplicationURL = "";
		ExternalContext lo_externalContext = FacesContext.getCurrentInstance().getExternalContext();
		lo_ApplicationURL = lo_externalContext.getRequestScheme()+"://"+lo_externalContext.getRequestServerName()+":"+lo_externalContext.getRequestServerPort()+lo_externalContext.getRequestContextPath();
		System.out.println(lo_ApplicationURL);
		return lo_ApplicationURL;
	}
	
	@Override
	public String toString() {
		return "PdfSignInformation [digisignAlias=" + digisignAlias + ", pdfProperty=" + pdfProperty
				+ ", pdfContentUUID=" + pdfContentUUID + ", pdfSignedContentUUID=" + pdfSignedContentUUID
				+ ", pdfContent=" + Arrays.toString(pdfContent) + ", pdfContentString=" + pdfContentString + "]";
	}
	
	

}

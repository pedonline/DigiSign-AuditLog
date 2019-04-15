/**
* @author Weerayut Wichaidit
* @version 2018-09-19
*/
package bsm.dsal.digisign;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.codec.binary.Base64;

import bsm.dsal.common.CommonLog;
import bsm.dsal.common.LOG_LEVEL;
import esi.ds.server.common.SecreteKeyCryptography;


@ManagedBean(name = "digiSignInformation", eager=true)
@SessionScoped
public class DigiSignInformation implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3358404190730694530L;
	private String digisignAlias;
	private String digisignOTK;
	private String digisignCertificate;
	private String digisignDigitalSignature;
	private Date digisignBeginTime;
	private Date digisignExpiredTime;
	private long MINTimeInterval = 5;
	
	public DigiSignInformation() {
		super();
		this.digisignAlias = "";
		this.digisignOTK = "";
		this.digisignDigitalSignature = "";
		long digisignTime = System.currentTimeMillis();
		this.digisignBeginTime = new Date(digisignTime);
		this.digisignExpiredTime = new Date(digisignTime);
	}
	public DigiSignInformation(String digisignAlias, String digisignOTK) {
		super();
		this.digisignAlias = digisignAlias;
		this.digisignOTK = digisignOTK;
		this.digisignDigitalSignature = "";
		long digisignTime = System.currentTimeMillis();
		this.digisignBeginTime = new Date(digisignTime);
		if(digisignAlias != null && digisignOTK != null && !digisignAlias.equals("") && !digisignOTK.equals("")){
			this.digisignExpiredTime = new Date(digisignTime+(this.MINTimeInterval*60000));
		}else{
			this.digisignExpiredTime = new Date(digisignTime);
		}
	}
	public DigiSignInformation(String digisignAlias, String digisignOTK, String digisignDigitalSignature,String digisignCertificate) {
		super();
		this.digisignAlias = digisignAlias;
		this.digisignOTK = digisignOTK;
		this.digisignDigitalSignature = digisignDigitalSignature;
		this.digisignCertificate = digisignCertificate;
		long digisignTime = System.currentTimeMillis();
		this.digisignBeginTime = new Date(digisignTime);
		if(digisignAlias != null && digisignOTK != null && !digisignAlias.equals("") && !digisignOTK.equals("")){
			this.digisignExpiredTime = new Date(digisignTime+(this.MINTimeInterval*60000));
		}else{
			this.digisignExpiredTime = new Date(digisignTime);
		}
	}
	public DigiSignInformation(String digisignAlias, String digisignOTK, String digisignDigitalSignature) {
		super();
		this.digisignAlias = digisignAlias;
		this.digisignOTK = digisignOTK;
		this.digisignDigitalSignature = digisignDigitalSignature;
		long digisignTime = System.currentTimeMillis();
		this.digisignBeginTime = new Date(digisignTime);
		if(digisignAlias != null && !digisignAlias.equals("") && digisignDigitalSignature != null && !digisignDigitalSignature.equals("")){
			this.digisignExpiredTime = new Date(digisignTime+(this.MINTimeInterval*60000));
		}else{
			this.digisignExpiredTime = new Date(digisignTime);
		}
	}
	public DigiSignInformation(String digisignAlias, String digisignOTK, String digisignDigitalSignature,int MINTimeInterval) {
		super();
		this.digisignAlias = digisignAlias;
		this.digisignOTK = digisignOTK;
		this.digisignDigitalSignature = digisignDigitalSignature;
		this.MINTimeInterval = MINTimeInterval;
		long digisignTime = System.currentTimeMillis();
		this.digisignBeginTime = new Date(digisignTime);
		if(digisignAlias != null && !digisignAlias.equals("") && digisignDigitalSignature != null && !digisignDigitalSignature.equals("") && MINTimeInterval > 0){
			this.digisignExpiredTime = new Date(digisignTime+(MINTimeInterval*60000));
		}else{
			this.digisignExpiredTime = new Date(digisignTime);
		}
	}
	private float DigisignTimeDiffMinutes(){
		long diff = System.currentTimeMillis() - this.digisignBeginTime.getTime();
		float diffMinutes = ((float) diff )/ (60 * 1000); 
		return diffMinutes;
	}
	public String LeftTime() {
		float time = this.MINTimeInterval-DigisignTimeDiffMinutes();
		int minutes = (int) time;
		int sec =  (int) (60 * (time - minutes));
		return minutes+"m "+sec+"s";
	}
	public boolean isExpired(){
		System.out.println("DigisignTimeDiffMinutes() >> "+DigisignTimeDiffMinutes());
		System.out.println("this.MINTimeInterval >> "+this.MINTimeInterval);
		System.out.println("this.digisignBeginTime >> "+this.digisignBeginTime.getTime());
		System.out.println("this.digisignExpiredTime >> "+this.digisignExpiredTime.getTime());
		if(DigisignTimeDiffMinutes() >= this.MINTimeInterval || this.digisignBeginTime.getTime() == this.digisignExpiredTime.getTime()){
			return true;
		}else{
			return false;
		}
	}
	
	public String getOTK() throws InvalidKeyException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
//		PublicKey  ls_punlicKey = SecreteKeyCryptography.GetBytePublicKeyFromKeyStore(this.digisignAlias, "kensington", "E:/Dropbox/Thesis/MSIT/keystore.wic.jks");
		CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
		InputStream in = new ByteArrayInputStream(Base64.decodeBase64(this.getDigisignCertificate()));
		X509Certificate digisigncert = (X509Certificate)certFactory.generateCertificate(in);
		PublicKey  ls_punlicKey = digisigncert.getPublicKey();
		Cipher cipherDecrypt = Cipher.getInstance(ls_punlicKey.getAlgorithm());
        cipherDecrypt.init(Cipher.DECRYPT_MODE, ls_punlicKey); 
        byte[] lb_OTK = cipherDecrypt.doFinal(Base64.decodeBase64(this.digisignOTK));
        String ls_OTK = Base64.encodeBase64String(lb_OTK);
		CommonLog.Print(LOG_LEVEL.INFO_LEVEL, "DigiSignInformation", "getOTK", "Decrypt OTK : " + ls_OTK);
		return ls_OTK;		
	}
	
	public String getDigisignAlias() {
		return digisignAlias;
	}
	public void setDigisignAlias(String digisignAlias) {
		this.digisignAlias = digisignAlias;
	}
	public String getDigisignOTK() {
		return digisignOTK;
	}
	public void setDigisignOTK(String digisignOTK) {
		long digisignTime = System.currentTimeMillis();
		this.digisignBeginTime = new Date(digisignTime);
		if(digisignAlias != null && digisignOTK != null && !digisignAlias.equals("") && !digisignOTK.equals("")){
			this.digisignExpiredTime = new Date(digisignTime+(this.MINTimeInterval*60000));
		}else{
			this.digisignExpiredTime = new Date(digisignTime);
		}
		this.digisignOTK = digisignOTK;
	}
	public String getDigisignDigitalSignature() {
		return digisignDigitalSignature;
	}
	public void setDigisignDigitalSignature(String digisignDigitalSignature) {
		this.digisignDigitalSignature = digisignDigitalSignature;
	}
	public Date getDigisignBeginTime() {
		return digisignBeginTime;
	}
	public void setDigisignBeginTime(Date digisignBeginTime) {
		this.digisignBeginTime = digisignBeginTime;
	}
	public Date getDigisignExpiredTime() {
		return digisignExpiredTime;
	}
	public void setDigisignExpiredTime(Date digisignExpiredTime) {
		this.digisignExpiredTime = digisignExpiredTime;
	}
	public String getDigisignCertificate() {
		return digisignCertificate;
	}
	public void setDigisignCertificate(String digisignCertificate) {
		this.digisignCertificate = digisignCertificate;
	}
	@Override
	public String toString() {
		return "DigiSignInformation [DigisignAlias=" + digisignAlias + ", DigisignOTK=" + digisignOTK
				+ ", DigisignDigitalSignature=" + digisignDigitalSignature + ", DigisignBeginTime=" + digisignBeginTime
				+ ", DigisignExpiredTime=" + digisignExpiredTime + ", MINTimeInterval=" + MINTimeInterval + "]";
	}
	

}

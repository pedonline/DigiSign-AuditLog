/**
* @author Weerayut Wichaidit
* @version 2017-05-15
*/
package bsm.dsal.dao.common;

import java.util.Date;

import bsm.dsal.hrm.model.AuditLog;
import esi.pdf.common.CertificateDetail;

public class DigiSignVerifyInfomation extends AuditLog{
	private String signName;
	private Date signDate;
	private boolean verified;
	private boolean signatureAuthenticity;
	private CertificateDetail certificateDetail = null;
	
	
	public DigiSignVerifyInfomation(AuditLog ao_AuditLog) {
		super(ao_AuditLog.getAuditLogId(), ao_AuditLog.getAction(), ao_AuditLog.getDetail(), ao_AuditLog.getDetailSignature(), ao_AuditLog.getOTKSignature(),
				ao_AuditLog.getCreatedDate(), ao_AuditLog.getEntityId(), ao_AuditLog.getEntityName(), ao_AuditLog.getAlias());
		this.signName = "";
		this.signDate = null;
		this.verified = false;
		this.signatureAuthenticity = false;
		this.certificateDetail = null;
	}
	public String getSignName() {
		return signName;
	}
	public void setSignName(String signName) {
		this.signName = signName;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public boolean isSignatureAuthenticity() {
		return signatureAuthenticity;
	}
	public void setSignatureAuthenticity(boolean signatureAuthenticity) {
		this.signatureAuthenticity = signatureAuthenticity;
	}
	public CertificateDetail getCertificateDetail() {
		return certificateDetail;
	}
	public void setCertificateDetail(CertificateDetail certificateDetail) {
		this.certificateDetail = certificateDetail;
	}
	@Override
	public String toString() {
		return "DigiSignVerifyInfomation [signName=" + signName + ", signDate=" + signDate + ", verified=" + verified
				+ ", signatureAuthenticity=" + signatureAuthenticity + ", certificateDetail=" + certificateDetail + "]";
	}
	
	
}

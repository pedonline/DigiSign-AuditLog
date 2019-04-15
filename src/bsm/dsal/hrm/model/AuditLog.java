/**
* @author Weerayut Wichaidit
* @version 2017-05-15
*/
package bsm.dsal.hrm.model;

import java.util.Arrays;

// Generated Jan 27, 2010 7:12:23 PM by Hibernate Tools 3.2.5.Beta

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "auditlog")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "auditLogId")
public class AuditLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Long auditLogId = 0L;
	private String action = "";
	private Byte[] detail = null;
	private Byte[] detailSignature = null;
	private Byte[] OTKSignature = null;
	private Date createdDate = null;
	private long entityId = 0L;
	private String entityEmbeddedId = "";
	private String entityName = "";
	private String Alias = "";
	private Byte[] Certificate = null;

	public AuditLog() {
		this.auditLogId = 0L;
		this.action = "";
		this.detail = null;
		this.detailSignature = null;
		this.OTKSignature = null;
		this.createdDate = null;
		this.entityId = 0L;
		this.entityEmbeddedId = "";
		this.entityName = "";
		this.Alias = "";
		this.Certificate = null;
	}

	public AuditLog(String action, Byte[] detail, Date createdDate,long entityId, String entityName) {
		this.action = action;
		this.detail = detail;
		this.createdDate = createdDate;
		this.entityId = entityId;
		this.entityName = entityName;
	}
	public AuditLog(String action, Byte[] detail, Date createdDate,long entityId, String entityName,String Alias) {
		this.action = action;
		this.detail = detail;
		this.createdDate = createdDate;
		this.entityId = entityId;
		this.entityName = entityName;
		this.Alias = Alias;
	}
	public AuditLog(String action, Byte[] detail, Date createdDate,long entityId,String entityEmbeddedId, String entityName,String Alias) {
		super();
		this.action = action;
		this.detail = detail;
		this.createdDate = createdDate;
		this.entityId = entityId;
		this.entityEmbeddedId = entityEmbeddedId;
		this.entityName = entityName;
		this.Alias = Alias;
	}
	
	public AuditLog(String action, Byte[] detail, Date createdDate,long entityId,String entityEmbeddedId, String entityName,String Alias,Byte[] certificate) {
		this.action = action;
		this.detail = detail;
		this.createdDate = createdDate;
		this.entityId = entityId;
		this.entityEmbeddedId = entityEmbeddedId;
		this.entityName = entityName;
		this.Alias = Alias;
		this.Certificate = certificate;
	}
	
	public AuditLog(Long auditLogId, String action, Byte[] detail, Byte[] detailSignature, Byte[] oTKSignature,
			Date createdDate, long entityId, String entityName, String alias) {
		super();
		this.auditLogId = auditLogId;
		this.action = action;
		this.detail = detail;
		this.detailSignature = detailSignature;
		this.OTKSignature = oTKSignature;
		this.createdDate = createdDate;
		this.entityId = entityId;
		this.entityName = entityName;
		this.Alias = alias;
	}
	
	public AuditLog(Long auditLogId, String action, Byte[] detail, Byte[] detailSignature, Byte[] oTKSignature,
			Date createdDate, long entityId,String entityEmbeddedId, String entityName, String alias) {
		super();
		this.auditLogId = auditLogId;
		this.action = action;
		this.detail = detail;
		this.detailSignature = detailSignature;
		this.OTKSignature = oTKSignature;
		this.createdDate = createdDate;
		this.entityId = entityId;
		this.entityEmbeddedId = entityEmbeddedId;
		this.entityName = entityName;
		this.Alias = alias;
	}
	
	public AuditLog(Long auditLogId, String action, Byte[] detail, Byte[] detailSignature, Byte[] oTKSignature,
			Date createdDate, long entityId,String entityEmbeddedId, String entityName, String alias,Byte[] certificate) {
		super();
		this.auditLogId = auditLogId;
		this.action = action;
		this.detail = detail;
		this.detailSignature = detailSignature;
		this.OTKSignature = oTKSignature;
		this.createdDate = createdDate;
		this.entityId = entityId;
		this.entityEmbeddedId = entityEmbeddedId;
		this.entityName = entityName;
		this.Alias = alias;
		this.Certificate = certificate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AUDIT_LOG_ID", unique = true, nullable = false)
	public Long getAuditLogId() {
		return this.auditLogId;
	}

	public void setAuditLogId(Long auditLogId) {
		this.auditLogId = auditLogId;
	}

	@Column(name = "ACTION", nullable = false, length = 100)
	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Column(name = "DETAIL", nullable = false)
	public Byte[] getDetail() {
		return this.detail;
	}

	public void setDetail(Byte[] detail) {
		this.detail = detail;
	}
	
	@Column(name = "DETAIL_SIGNATURE", nullable = true, length = 65535)
	public Byte[] getDetailSignature() {
		return detailSignature;
	}

	public void setDetailSignature(Byte[] detailSignature) {
		this.detailSignature = detailSignature;
	}

	@Column(name = "CREATED_DATE", nullable = false, length = 23)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "ENTITY_ID", nullable = false)
	public long getEntityId() {
		return this.entityId;
	}

	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}
	@Column(name = "ENTITY_EMBEDDED_ID", nullable = false)
	public String getEntityEmbeddedId() {
		return entityEmbeddedId;
	}

	public void setEntityEmbeddedId(String entityEmbeddedId) {
		this.entityEmbeddedId = entityEmbeddedId;
	}

	@Column(name = "ENTITY_NAME", nullable = false)
	public String getEntityName() {
		return this.entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	@Column(name = "OTK_SIGNATURE",nullable = true, length = 65535)
	public Byte[] getOTKSignature() {
		return OTKSignature;
	}

	public void setOTKSignature(Byte[] oTKSignature) {
		OTKSignature = oTKSignature;
	}
	
	@Column(name = "ALIAS", nullable = false)
	public String getAlias() {
		return Alias;
	}

	public void setAlias(String alias) {
		Alias = alias;
	}
	@Column(name = "CERTIFICATE", nullable = true)
	public Byte[] getCertificate() {
		return Certificate;
	}

	public void setCertificate(Byte[] certificate) {
		Certificate = certificate;
	}

	@Override
	public String toString() {
		return "AuditLog [auditLogId=" + auditLogId + ", action=" + action + ", detail=" + detail + ", detailSignature="
				+ Arrays.toString(detailSignature) + ", OTKSignature=" + Arrays.toString(OTKSignature)
				+ ", createdDate=" + createdDate + ", entityId=" + entityId + ", entityName=" + entityName + ", Alias="
				+ Alias + "]";
	}

}

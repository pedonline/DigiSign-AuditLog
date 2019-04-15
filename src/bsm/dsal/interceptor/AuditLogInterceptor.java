/**
* @author Weerayut Wichaidit
* @version 2017-05-15
*/
package bsm.dsal.interceptor;

import java.io.Serializable;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

import bsm.dsal.util.AuditLogUtil;

public class AuditLogInterceptor extends EmptyInterceptor{
	
	private Session session;
	private String OTK;
	private String OTKSign;
	private String Alias;
	private X509Certificate Certificate;
	private Byte[] CertificateByte;
	private Set inserts = new HashSet();
	private Set updates = new HashSet();
	private Set deletes = new HashSet();
	
	public AuditLogInterceptor() {
		super();
	}
	
	public AuditLogInterceptor(String oTK, String alias) {
		super();
		OTK = oTK;
		Alias = alias;
	}
	public AuditLogInterceptor(String oTKSign,String oTK, String alias) {
		super();
		OTK = oTK;
		Alias = alias;
		OTKSign = oTKSign;
	}
	public AuditLogInterceptor(String oTKSign,String oTK, String alias,X509Certificate certificate) {
		super();
		OTK = oTK;
		Alias = alias;
		OTKSign = oTKSign;
		try {
			Certificate = certificate;
			CertificateByte = ArrayUtils.toObject(certificate.getEncoded());
		} catch (CertificateEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setSession(Session session) {
		this.session=session;
	}
		
	public Session getSession() {
		return session;
	}

	public String getOTK() {
		return OTK;
	}

	public void setOTK(String oTK) {
		OTK = oTK;
	}

	public String getAlias() {
		return Alias;
	}

	public void setAlias(String alias) {
		Alias = alias;
	}

	public String getOTKSign() {
		return OTKSign;
	}

	public void setOTKSign(String oTKSign) {
		OTKSign = oTKSign;
	}

	public X509Certificate getCertificate() {
		return Certificate;
	}

	public void setCertificate(X509Certificate certificate) {
		try {
			Certificate = certificate;
			CertificateByte = ArrayUtils.toObject(certificate.getEncoded());
		} catch (CertificateEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public boolean onSave(Object entity,Serializable id,Object[] state,String[] propertyNames,Type[] types)throws CallbackException {		
		System.out.println("onSave");		
		if (entity instanceof IAuditLog){
			inserts.add(entity);
		}
		return false;
			
	}
	
	public boolean onFlushDirty(Object entity,Serializable id,Object[] currentState,Object[] previousState,String[] propertyNames,Type[] types)
		throws CallbackException {	
		System.out.println("onFlushDirty");		
		if (entity instanceof IAuditLog){
			updates.add(entity);
		}
		return false;		
	}
	
	public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {		
		System.out.println("onDelete");		
		if (entity instanceof IAuditLog){
			deletes.add(entity);
		}
	}

	//called before commit into database
	public void preFlush(Iterator iterator) {
		System.out.println("preFlush");
		IAuditLog entity = (IAuditLog) iterator.next();
		System.out.println("preFlush - "+entity);
	}	
	
	//called after committed into database
	public void postFlush(Iterator iterator) {
		System.out.println("postFlush");		
		try{		
			for (Iterator it = inserts.iterator(); it.hasNext();) {
				IAuditLog entity = (IAuditLog) it.next();
				System.out.println("postFlush - insert");
				System.out.println("postFlush - entity" +entity);
				System.out.println("postFlush - OTKSign" +OTKSign);
				System.out.println("postFlush - OTK" +OTK);
				System.out.println("postFlush - Alias" +Alias);
				System.out.println("postFlush - CertificateByte" +CertificateByte);				
				AuditLogUtil.LogIt("Saved",entity,OTKSign,OTK,Alias,CertificateByte);
			}				
			for (Iterator it = updates.iterator(); it.hasNext();) {
				IAuditLog entity = (IAuditLog) it.next();
				System.out.println("postFlush - update");
				AuditLogUtil.LogIt("Updated",entity,OTKSign,OTK,Alias,CertificateByte);
			}				
			for (Iterator it = deletes.iterator(); it.hasNext();) {
				IAuditLog entity = (IAuditLog) it.next();
				System.out.println("postFlush - delete");
				AuditLogUtil.LogIt("Deleted",entity,OTKSign,OTK,Alias,CertificateByte);
			}	
			
		} finally {
			inserts.clear();
			updates.clear();
			deletes.clear();
		}
	}	
	public void afterTransactionCompletion(Transaction tx) {
		System.out.println("afterTransactionCompletion "+tx.isActive());
	}
}

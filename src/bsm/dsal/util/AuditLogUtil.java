/**
* @author Weerayut Wichaidit
* @version 2017-05-15
*/
package bsm.dsal.util;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.plw.util.Common;

import bsm.dsal.hrm.model.AuditLog;
import bsm.dsal.interceptor.IAuditLog;
import bsm.dsal.interceptor.SecreteKeyCryptography;
import bsm.dsal.model.util.HibernateUtil;

public class AuditLogUtil{
	
	public static void LogIt(String action,IAuditLog entity/*,String OTK*/){
		System.out.println("AuditLogUtil.LogIt START");
		Session tempSession = HibernateUtil.getSession();
		Transaction MyHTs = tempSession.beginTransaction();
		Byte[] LogDeatil = ArrayUtils.toObject(entity.getLogDeatil());
		try {
			AuditLog auditRecord = new AuditLog(action,LogDeatil, new Date(),entity.getId(),entity.getEmbeddedId(), entity.getClass().getName());
			tempSession.save(auditRecord);
			auditRecord.setCreatedDate(Common.GetCurrTime());
			tempSession.flush();
			MyHTs.commit();
			System.out.println(auditRecord);
		} catch (Exception e) {
			e.printStackTrace();
			MyHTs.rollback();
		}finally {	
			tempSession.close();
			
		}
		System.out.println("AuditLogUtil.LogIt END");
	}
	public static void LogIt(String action,IAuditLog entity,String OTKSign,String OTK,String Alias,Byte[] Certificate){
		System.out.println("AuditLogUtil.LogIt START");
		System.out.println("AuditLogUtil.LogIt entity.getClass().getName()" + entity.getClass().getName());
		System.out.println("AuditLogUtil.LogIt entity.getId()" + entity.getId());
		System.out.println("AuditLogUtil.LogIt entity.getEmbeddedId()" + entity.getEmbeddedId());
		Byte[] lb_LogDeatil = ArrayUtils.toObject(entity.getLogDeatil());
		System.out.println("AuditLogUtil.LogIt entity.getLogDeatil()" + new String(entity.getLogDeatil(),Charset.forName("UTF-8")));
		Session tempSession = HibernateUtil.getSession();
		Transaction MyHTs = tempSession.beginTransaction();
		try {
			AuditLog auditRecord = new AuditLog();
			
			auditRecord.setAction(action);
			auditRecord.setDetail(lb_LogDeatil);
			auditRecord.setCreatedDate(new Date());
			auditRecord.setEntityId(entity.getId());
			auditRecord.setEntityEmbeddedId(entity.getEmbeddedId());
			auditRecord.setEntityName(entity.getClass().getName());
			auditRecord.setAlias(Alias);
			
			
			tempSession.save(auditRecord);
			auditRecord.setCreatedDate(Common.GetCurrTime());		
			byte[] lo_md = SecreteKeyCryptography.MessageDigest(ArrayUtils.toPrimitive(auditRecord.getDetail()));
			auditRecord.setDetailSignature(ArrayUtils.toObject(SecreteKeyCryptography.encrypt(OTK, Base64.encodeBase64String(lo_md))));
			auditRecord.setOTKSignature(ArrayUtils.toObject(Base64.decodeBase64(OTKSign)));
			auditRecord.setCertificate(Certificate);
			tempSession.flush();
			MyHTs.commit();
			System.out.println(auditRecord);
		} catch (Exception e) {
			e.printStackTrace();
			MyHTs.rollback();
		}finally {	
			tempSession.close();
			
		}
		System.out.println("AuditLogUtil.LogIt END");
	}
}
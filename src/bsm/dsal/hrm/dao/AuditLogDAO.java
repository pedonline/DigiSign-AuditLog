/**
* @author Weerayut Wichaidit
* @version 2017-05-15
*/
package bsm.dsal.hrm.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;

import com.plw.util.DataNotFoundException;

import bsm.dsal.hrm.model.AuditLog;


public class AuditLogDAO {

	public static AuditLog GetByID(Session MyHSs, Integer ai_auditLogId, Integer SUID, Integer SGID) throws DataNotFoundException {
		AuditLog theAuditLog = null;
		CriteriaBuilder builder =MyHSs.getCriteriaBuilder();
		CriteriaQuery<AuditLog> criteria = builder.createQuery(AuditLog.class);
		criteria.where(builder.equal(criteria.from(AuditLog.class).get("auditLogId"), ai_auditLogId));	
		Query query = MyHSs.createQuery(criteria);
		query.setFirstResult(0);
		query.setMaxResults(1);
		theAuditLog = (AuditLog) query.getSingleResult();
		if (theAuditLog != null) {
			return theAuditLog;
		} else {
			throw new DataNotFoundException("AuditLog [" + ai_auditLogId + "] is not found.");
		}
	}
	
	public static String GenSQLByAlias(int ai_CommandType,String as_Alias,Integer SUID, Integer SGID,int... StatusList) {
		StringBuffer sb_SQLCommand = new StringBuffer();
		if (ai_CommandType == 10) {
			sb_SQLCommand.append("SELECT COUNT(*) ");
		} else {
			sb_SQLCommand.append("SELECT AL.* ");
		}
		sb_SQLCommand.append("FROM auditlog AL ");
		sb_SQLCommand.append("WHERE AL.ALIAS LIKE '"+as_Alias+"'");

		if (ai_CommandType != 10) {
			sb_SQLCommand.append("ORDER BY AL.CREATED_DATE DESC ");
		}
		return sb_SQLCommand.toString();
	}
	
	
	public static int GetSizeByAlias(Session MyHSs, String as_Alias, Integer SUID, Integer SGID,int... StatusList) throws DataNotFoundException, Exception {
		String ls_SQLCommand = GenSQLByAlias(10,as_Alias,SUID,SGID,StatusList);
		Query query = MyHSs.createNativeQuery(ls_SQLCommand);
		query.setFirstResult(0);
		query.setMaxResults(1);
		Integer RowCount = (Integer) query.getSingleResult();
		return RowCount.intValue();
	}
	
	
	
	public static List<AuditLog> FindByAlias(Session MyHSs, String as_Alias, Integer SUID,Integer SGID, int BeginRow, int MaxRow,int... StatusList) throws DataNotFoundException, Exception {
		assert BeginRow < 0 : "BeginRow is less than 0";
		assert MaxRow < 0 : "MaxRow is less than 0";
		try {
			String ls_SQLCommand =  GenSQLByAlias(20,as_Alias,SUID,SGID,StatusList);
			Query query = MyHSs.createNativeQuery(ls_SQLCommand,AuditLog.class);
			query.setFirstResult(BeginRow);
			if (MaxRow > 0) {
				query.setMaxResults(MaxRow);
			}
			List<AuditLog> AuditLogList = query.getResultList();
			if (AuditLogList.isEmpty() != true) {
				return AuditLogList;
			} else {
				throw new DataNotFoundException("Find AuditLog By Alias is not found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
		}
	}
	
	public static String GenSQLByEntityID(int ai_CommandType,Integer  as_EntityID,Integer SUID, Integer SGID,int... StatusList) {
		StringBuffer sb_SQLCommand = new StringBuffer();
		if (ai_CommandType == 10) {
			sb_SQLCommand.append("SELECT COUNT(*) ");
		} else {
			sb_SQLCommand.append("SELECT AL.* ");
		}
		sb_SQLCommand.append("FROM auditlog AL ");
		sb_SQLCommand.append("WHERE AL.ENTITY_ID = "+as_EntityID+" ");

		if (ai_CommandType != 10) {
			sb_SQLCommand.append("ORDER BY AL.CREATED_DATE  ");
		}
		return sb_SQLCommand.toString();
	}
	
	
	public static int GetSizeByEntityID(Session MyHSs,Integer  as_EntityID, Integer SUID, Integer SGID,int... StatusList) throws DataNotFoundException, Exception {
		String ls_SQLCommand = GenSQLByEntityID(10,as_EntityID,SUID,SGID,StatusList);
		Query query = MyHSs.createNativeQuery(ls_SQLCommand);
		query.setFirstResult(0);
		query.setMaxResults(1);
		Integer RowCount = (Integer) query.getSingleResult();
		return RowCount.intValue();
	}
	
	public static List<AuditLog> FindByEntityID(Session MyHSs, Integer  as_EntityID, Integer SUID,Integer SGID, int BeginRow, int MaxRow,int... StatusList) throws DataNotFoundException, Exception {
		assert BeginRow < 0 : "BeginRow is less than 0";
		assert MaxRow < 0 : "MaxRow is less than 0";
		try {
			String ls_SQLCommand =  GenSQLByEntityID(20,as_EntityID,SUID,SGID,StatusList);
			Query query = MyHSs.createNativeQuery(ls_SQLCommand,AuditLog.class);
			query.setFirstResult(BeginRow);
			if (MaxRow > 0) {
				query.setMaxResults(MaxRow);
			}
			List<AuditLog> AuditLogList = query.getResultList();
			if (AuditLogList.isEmpty() != true) {
				return AuditLogList;
			} else {
				throw new DataNotFoundException("Find AuditLog By Alias is not found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
		}
	}
	
	public static String GenSQLByEntityIDEmbeddedID(int ai_CommandType,Integer  as_EntityID,String EmbeddedId,Integer SUID, Integer SGID,int... StatusList) {
		StringBuffer sb_SQLCommand = new StringBuffer();
		if (ai_CommandType == 10) {
			sb_SQLCommand.append("SELECT COUNT(*) ");
		} else {
			sb_SQLCommand.append("SELECT AL.* ");
		}
		sb_SQLCommand.append("FROM auditlog AL ");
		sb_SQLCommand.append("WHERE  ");
		if(as_EntityID != null && as_EntityID > 0) {
			sb_SQLCommand.append(" AL.ENTITY_ID = "+as_EntityID+" ");
			if(EmbeddedId != null && !EmbeddedId.isEmpty()) {
				sb_SQLCommand.append(" AND ");
			}
		}
		if(EmbeddedId != null && !EmbeddedId.isEmpty()) {
			sb_SQLCommand.append(" AL.ENTITY_EMBEDDED_ID LIKE '"+EmbeddedId+"' ");
		}

		if (ai_CommandType != 10) {
			sb_SQLCommand.append("ORDER BY AL.CREATED_DATE  ");
		}
		return sb_SQLCommand.toString();
	}
	
	
	public static int GetSizeByEntityIDEmbeddedID(Session MyHSs,Integer  as_EntityID,String EmbeddedId, Integer SUID, Integer SGID,int... StatusList) throws DataNotFoundException, Exception {
		String ls_SQLCommand = GenSQLByEntityIDEmbeddedID(10,as_EntityID,EmbeddedId,SUID,SGID,StatusList);
		Query query = MyHSs.createNativeQuery(ls_SQLCommand);
		query.setFirstResult(0);
		query.setMaxResults(1);
		Integer RowCount = (Integer) query.getSingleResult();
		return RowCount.intValue();
	}
	
	public static List<AuditLog> FindByEntityIDEmbeddedID(Session MyHSs, Integer  as_EntityID,String EmbeddedId, Integer SUID,Integer SGID, int BeginRow, int MaxRow,int... StatusList) throws DataNotFoundException, Exception {
		assert BeginRow < 0 : "BeginRow is less than 0";
		assert MaxRow < 0 : "MaxRow is less than 0";
		try {
			String ls_SQLCommand =  GenSQLByEntityIDEmbeddedID(20,as_EntityID,EmbeddedId,SUID,SGID,StatusList);
			Query query = MyHSs.createNativeQuery(ls_SQLCommand,AuditLog.class);
			query.setFirstResult(BeginRow);
			if (MaxRow > 0) {
				query.setMaxResults(MaxRow);
			}
			List<AuditLog> AuditLogList = query.getResultList();
			if (AuditLogList.isEmpty() != true) {
				return AuditLogList;
			} else {
				throw new DataNotFoundException("Find AuditLog By Alias is not found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
		}
	}
	
	public static String GenSQLByEmbeddedIDEntityName(int ai_CommandType,String EmbeddedId,String entityName,Integer SUID, Integer SGID,int... StatusList) {
		StringBuffer sb_SQLCommand = new StringBuffer();
		if (ai_CommandType == 10) {
			sb_SQLCommand.append("SELECT COUNT(*) ");
		} else {
			sb_SQLCommand.append("SELECT AL.* ");
		}
		sb_SQLCommand.append("FROM auditlog AL ");
		sb_SQLCommand.append("WHERE  ");
		if(entityName != null && !entityName.isEmpty()) {
			sb_SQLCommand.append(" AL.ENTITY_NAME LIKE '"+entityName+"' ");
		}
		if(EmbeddedId != null && !EmbeddedId.isEmpty()) {
			sb_SQLCommand.append("AND AL.ENTITY_EMBEDDED_ID LIKE '"+EmbeddedId+"' ");
		}
		if (ai_CommandType != 10) {
			sb_SQLCommand.append("ORDER BY AL.CREATED_DATE  ");
		}
		return sb_SQLCommand.toString();
	}
	
	
	public static int GetSizeByEmbeddedIDEntityName(Session MyHSs,String EmbeddedId,String entityName, Integer SUID, Integer SGID,int... StatusList) throws DataNotFoundException, Exception {
		String ls_SQLCommand = GenSQLByEmbeddedIDEntityName(10,EmbeddedId,entityName,SUID,SGID,StatusList);
		Query query = MyHSs.createNativeQuery(ls_SQLCommand);
		query.setFirstResult(0);
		query.setMaxResults(1);
		Integer RowCount = (Integer) query.getSingleResult();
		return RowCount.intValue();
	}
	
	public static List<AuditLog> FindByEmbeddedIDEntityName(Session MyHSs, String EmbeddedId,String entityName, Integer SUID,Integer SGID, int BeginRow, int MaxRow,int... StatusList) throws DataNotFoundException, Exception {
		assert BeginRow < 0 : "BeginRow is less than 0";
		assert MaxRow < 0 : "MaxRow is less than 0";
		try {
			String ls_SQLCommand =  GenSQLByEmbeddedIDEntityName(20,EmbeddedId,entityName,SUID,SGID,StatusList);
			Query query = MyHSs.createNativeQuery(ls_SQLCommand,AuditLog.class);
			query.setFirstResult(BeginRow);
			if (MaxRow > 0) {
				query.setMaxResults(MaxRow);
			}
			List<AuditLog> AuditLogList = query.getResultList();
			if (AuditLogList.isEmpty() != true) {
				return AuditLogList;
			} else {
				throw new DataNotFoundException("Find AuditLog By Alias is not found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
		}
	}
	
	public static String GenSQLLastVersionByEmbeddedIDEntityName(String EmbeddedId,String entityName,Integer SUID, Integer SGID,int... StatusList) {
		StringBuffer sb_SQLCommand = new StringBuffer();
		
		sb_SQLCommand.append("SELECT TOP 1 AL.* ");
	
		sb_SQLCommand.append("FROM auditlog AL ");
		sb_SQLCommand.append("WHERE  ");
		if(entityName != null && !entityName.isEmpty()) {
			sb_SQLCommand.append(" AL.ENTITY_NAME LIKE '"+entityName+"' ");
		}
		if(EmbeddedId != null && !EmbeddedId.isEmpty()) {
			sb_SQLCommand.append("AND AL.ENTITY_EMBEDDED_ID LIKE '"+EmbeddedId+"' ");
		}
		sb_SQLCommand.append("ORDER BY AL.CREATED_DATE desc,AL.AUDIT_LOG_ID  desc  ");
		return sb_SQLCommand.toString();
	}
	
		
	public static AuditLog GetLastVersionByEmbeddedIDEntityName(Session MyHSs, String EmbeddedId,String entityName, Integer SUID,Integer SGID,int... StatusList) throws DataNotFoundException, Exception {
		try {
			String ls_SQLCommand =  GenSQLLastVersionByEmbeddedIDEntityName(EmbeddedId,entityName,SUID,SGID,StatusList);
			Query query = MyHSs.createNativeQuery(ls_SQLCommand,AuditLog.class);
			List<AuditLog> AuditLogList = query.getResultList();
			if (AuditLogList != null && !AuditLogList.isEmpty()) {
				return AuditLogList.get(0);
			} else {
				throw new DataNotFoundException("Find AuditLog By Alias is not found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
		}
	}
	
}

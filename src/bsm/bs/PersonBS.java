package bsm.bs;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.Session;
import org.hibernate.Transaction;

import bsm.dsal.digisign.DigiSignInformation;
import bsm.dspo.interceptor.AuditLogInterceptor;
import bsm.usercontext.CBPerson;
import dspo.hrm.dao.PersonDAO;
import dspo.hrm.model.Person;
import dspo.hrm.model.util.DSPOHrmModelUtil;
import dspo.util.CommonLog;
import dspo.util.LOG_LEVEL;

public class PersonBS {
	private static String className = "PersonBS";
	
	
	
	public static CBPerson DigiSignAddPerson(CBPerson ao_CBObj, String LoginUser, Integer ai_CurrOrgID, Integer ai_personType, Integer ai_PositionID,DigiSignInformation ao_DigiSignInformation)
			throws Exception {
		String methodName = "AddPerson";
		CommonLog.Print(LOG_LEVEL.INFO_LEVEL, className, methodName, "[START]");
		AuditLogInterceptor interceptor = new AuditLogInterceptor();
		interceptor.setAlias(ao_DigiSignInformation.getDigisignAlias());
		interceptor.setOTKSign(ao_DigiSignInformation.getDigisignOTK());
		interceptor.setOTK(ao_DigiSignInformation.getOTK());
		CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
		InputStream in = new ByteArrayInputStream(Base64.decodeBase64(ao_DigiSignInformation.getDigisignCertificate()));
		X509Certificate digisigncert = (X509Certificate)certFactory.generateCertificate(in);
		interceptor.setCertificate(digisigncert);
		Session MyHSs = DSPOHrmModelUtil.getSession(interceptor);		
		Transaction MyHTs = MyHSs.beginTransaction();
		Person lo_Person = new Person();
		ao_CBObj.setCBToHB(lo_Person);			
		try {
			lo_Person.setType(lo_Person.getType());
			PersonDAO.Add(MyHSs, lo_Person, LoginUser);
		} catch (Exception ex) {
			CommonLog.Print(LOG_LEVEL.ERROR_LEVEL, className, methodName, "[EROR] ADD Process Exception");
			MyHTs.rollback();
			MyHSs.close();
			throw ex;
		}
		ao_CBObj.setHBtoCB(lo_Person);
		MyHTs.commit();
		MyHSs.close();

		CommonLog.Print(LOG_LEVEL.INFO_LEVEL, className, methodName, "[END]");
		return ao_CBObj;
	}

	
}

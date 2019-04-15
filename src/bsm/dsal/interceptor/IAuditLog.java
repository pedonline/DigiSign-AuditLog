/**
* @author Weerayut Wichaidit
* @version 2017-05-15
*/
package bsm.dsal.interceptor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bsm.dsal.dao.common.DigiSignVerifyInfomation;
import bsm.dsal.hrm.dao.AuditLogDAO;
import bsm.dsal.hrm.model.AuditLog;
import esi.pdf.common.CertificateDetail;

public interface IAuditLog {

	public Long getId();
	public String getEmbeddedId();

	public byte[] getLogDeatil();

	
	public static String getJsonNormal(Object lo_class) {
		System.out.println(lo_class);
		GsonBuilder gsonBuilder = new GsonBuilder();
	    Gson gson = gsonBuilder.create();
		String jsonStr = gson.toJson(lo_class);
		return jsonStr;
	}
	
	public static String getJSONUtil(Object lo_class) {
		Hibernate.initialize(lo_class);
		ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate5Module().configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true)).enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, SerializationFeature.CLOSE_CLOSEABLE);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a z");
		mapper.setDateFormat(df);
		 String json = null;
		try {
			json = mapper.writer().withoutRootName().writeValueAsString(lo_class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	
	public static String getJsonLogDeatil(Object lo_class) {
		return IAuditLog.getJSONUtil(lo_class);
	}
	
	public static byte[] getJsonByteLogDeatil(Object lo_class) {
		System.out.println("getJsonByteLogDeatil >> "+IAuditLog.getJSONUtil(lo_class));
		return IAuditLog.getJSONUtil(lo_class).getBytes(Charset.forName("UTF-8"));
	}
		
	public default List<DigiSignVerifyInfomation> LoadAuditLog(Session tempSession) {
		Integer lo_Id = null;
		List<DigiSignVerifyInfomation> DigiSignVerifyInfomationList = null;
		if(this.getId() != null) {
			lo_Id = this.getId().intValue();
		}
		try{
			List<AuditLog> AuditLoglist = AuditLogDAO.FindByEmbeddedIDEntityName(tempSession, this.getEmbeddedId(),this.getClass().getName(), null, null, 0, 0);
			if(AuditLoglist != null) {
				DigiSignVerifyInfomationList = new ArrayList<DigiSignVerifyInfomation>();		
			}
			for(AuditLog lo_AuditLog : AuditLoglist){
				DigiSignVerifyInfomation lo_DigiSignVerifyInfomationItem = new DigiSignVerifyInfomation(lo_AuditLog);
				boolean valid = false;
				boolean verified = false;
				boolean signatureAuthenticity = false;
				System.out.print(lo_AuditLog.getCreatedDate()+"|"+lo_AuditLog.getAlias()+"|"+lo_AuditLog.getAction()+"|"+lo_AuditLog.getEntityId()+"|"+lo_AuditLog.getEntityName()+"|");
				try{
					lo_DigiSignVerifyInfomationItem.setSignDate(lo_AuditLog.getCreatedDate());
					lo_DigiSignVerifyInfomationItem.setSignName(lo_AuditLog.getAlias());
					CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
					InputStream in = new ByteArrayInputStream(ArrayUtils.toPrimitive(lo_AuditLog.getCertificate()));
					X509Certificate ls_Certificate = (X509Certificate)certFactory.generateCertificate(in);
					PublicKey publicKey = ls_Certificate.getPublicKey();
					lo_DigiSignVerifyInfomationItem.setCertificateDetail(new CertificateDetail(ls_Certificate));
					Cipher cipherDecrypt = Cipher.getInstance(publicKey.getAlgorithm());
			        cipherDecrypt.init(Cipher.DECRYPT_MODE, publicKey); 
					Byte[] lo_OTKSignatureByte = lo_AuditLog.getOTKSignature();
					
	
					byte[] lo_OTKSignature =  ArrayUtils.toPrimitive(lo_OTKSignatureByte);
	
					byte[] lb_OTK = cipherDecrypt.doFinal(lo_OTKSignature);
					byte[] lo_DetailSignature = ArrayUtils.toPrimitive(lo_AuditLog.getDetailSignature());
					byte[] md1 = SecreteKeyCryptography.decrypt(Base64.encodeBase64String(lb_OTK),Base64.encodeBase64String(lo_DetailSignature) );				
					byte[] md2 = SecreteKeyCryptography.MessageDigest(ArrayUtils.toPrimitive(lo_AuditLog.getDetail()));
		
					if(Arrays.equals(md1, md2)){
						verified = true;
						signatureAuthenticity = true;
					}else{
						verified = false;
						signatureAuthenticity = false;
					}
				
				} catch (BadPaddingException  be) {
					valid = false;
					verified = false;
					signatureAuthenticity = false;
					//e.printStackTrace();
				} catch (IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					valid = false;
					verified = false;
					signatureAuthenticity = false;
				}catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					valid = false;
					verified = false;
					signatureAuthenticity = false;
					e.printStackTrace();
				} catch (CertificateException e) {
					// TODO Auto-generated catch block
					valid = false;
					verified = false;
					signatureAuthenticity = false;
					e.printStackTrace();
				}  catch (NoSuchPaddingException e) {
					// TODO Auto-generated catch block
					valid = false;
					verified = false;
					signatureAuthenticity = false;
					e.printStackTrace();
				} catch (InvalidKeyException e) {
					// TODO Auto-generated catch block
					valid = false;
					verified = false;
					signatureAuthenticity = false;
					e.printStackTrace();
				}catch (Exception e) {
					// TODO Auto-generated catch block
					valid = false;
					verified = false;
					signatureAuthenticity = false;
					e.printStackTrace();
				}
				lo_DigiSignVerifyInfomationItem.setVerified(verified);
				lo_DigiSignVerifyInfomationItem.setSignatureAuthenticity(signatureAuthenticity);
				DigiSignVerifyInfomationList.add(lo_DigiSignVerifyInfomationItem);
				if(valid){
					System.out.print("valid");
					
				}else{
					System.out.print("invalid");
				}
				System.out.println();
			}
		}catch (Exception oe) {
			oe.printStackTrace();
		}
		return DigiSignVerifyInfomationList;
	}
	
	public default Boolean IsEntityValid(Session MyHSs)  {
		
		boolean valid = false;
		boolean verified = false;
		boolean signatureAuthenticity = false;
		try{
			AuditLog AuditLog = AuditLogDAO.GetLastVersionByEmbeddedIDEntityName(MyHSs, this.getEmbeddedId(), this.getClass().getName(), null, null);
			CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
			InputStream in = new ByteArrayInputStream(ArrayUtils.toPrimitive(AuditLog.getCertificate()));
			X509Certificate ls_Certificate = (X509Certificate)certFactory.generateCertificate(in);
			PublicKey publicKey = ls_Certificate.getPublicKey();
			Cipher cipherDecrypt = Cipher.getInstance(publicKey.getAlgorithm());
	        cipherDecrypt.init(Cipher.DECRYPT_MODE, publicKey); 
			Byte[] lo_OTKSignatureByte = AuditLog.getOTKSignature();
			

			byte[] lo_OTKSignature =  ArrayUtils.toPrimitive(lo_OTKSignatureByte);

			byte[] lb_OTK = cipherDecrypt.doFinal(lo_OTKSignature);
			byte[] lo_DetailSignature = ArrayUtils.toPrimitive(AuditLog.getDetailSignature());
			byte[] md1 = SecreteKeyCryptography.decrypt(Base64.encodeBase64String(lb_OTK),Base64.encodeBase64String(lo_DetailSignature) );				
			byte[] md2 = SecreteKeyCryptography.MessageDigest(ArrayUtils.toPrimitive(AuditLog.getDetail()));

			if(Arrays.equals(md1, md2)){
				verified = true;
				signatureAuthenticity = true;
			}else{
				verified = false;
				signatureAuthenticity = false;
			}
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new Hibernate5Module().configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, false)).enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, SerializationFeature.CLOSE_CLOSEABLE);
			String lo_LogDeatil = new String(this.getLogDeatil(),Charset.forName("UTF-8"));
			JsonNode tree1 = mapper.readTree(lo_LogDeatil);			
			JsonNode tree2 = mapper.readTree(new String(ArrayUtils.toPrimitive(AuditLog.getDetail()),Charset.forName("UTF-8")));
			if(tree1.equals(tree2)){
				valid = true;
			}else {
				valid = false;
			}
			System.out.println("######### Object IS "+valid);
			System.out.println("##############################################");
			System.out.println(lo_LogDeatil);
			System.out.println("##############################################");
			System.out.println(AuditLog.getDetail());
			System.out.println("##############################################");
		
		} catch (BadPaddingException  be) {
			valid = false;
			verified = false;
			signatureAuthenticity = false;
			//e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			valid = false;
			verified = false;
			signatureAuthenticity = false;
		}catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			valid = false;
			verified = false;
			signatureAuthenticity = false;
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			valid = false;
			verified = false;
			signatureAuthenticity = false;
			e.printStackTrace();
		}  catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			valid = false;
			verified = false;
			signatureAuthenticity = false;
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			valid = false;
			verified = false;
			signatureAuthenticity = false;
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			valid = false;
			verified = false;
			signatureAuthenticity = false;
			e.printStackTrace();
		}

		if(valid && verified && signatureAuthenticity) {
			return true;
		}else {
			return false;
		}
	}
	
	
}
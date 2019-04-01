package bsm.dsal.common;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.DigestException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

public class SecreteKeyCryptography {
//	static SecretKeyFactory factory ;
	static int pswdIterations = 65536  ;
	static int keySize = 128;
	static byte[] ivBytes;
	static byte[] saltBytes = {0,1,2,3,4,5,6};
	public static byte[] encrypt(String key, String value) {
		try {
//			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//			byte[] lo_key = Base64.decodeBase64(key);
//			String s = new String(lo_key);
//			PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), saltBytes, pswdIterations, keySize);
//			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
//			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			byte[] encodedKey = Base64.decodeBase64(key);
		    SecretKey secretKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
//			SecretKey secretKey = factory.generateSecret(spec);
//			SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getEncoded(),"AES");

			Cipher cipher = Cipher.getInstance("AES");
//			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] toChapter1Digest = Base64.decodeBase64(value);//MessageDigest(value);
			System.out.println("MessageDigest string: " + Base64.encodeBase64String(toChapter1Digest));
			byte[] encrypted = cipher.doFinal(toChapter1Digest);
			System.out.println("encrypted string: " + Base64.encodeBase64String(encrypted));

	//		return Base64.encodeBase64String(encrypted);
			return encrypted;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static byte[] decrypt(String key, String encrypted) {
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//			byte[] lo_key = Base64.decodeBase64(key);
//			String s = new String(lo_key);
//			PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), saltBytes, pswdIterations, keySize);
//			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
//			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			byte[] encodedKey     = Base64.decodeBase64(key);
		    SecretKey secretKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
//			SecretKey secretKey = factory.generateSecret(spec);
//			SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getEncoded(),"AES");

			Cipher cipher = Cipher.getInstance("AES");
//			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);

			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
//			return new String(original);
			return original;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
	public static byte[] MessageDigest(String value) throws NoSuchAlgorithmException {
		byte[] toChapter1Digest = null;
		 MessageDigest md = MessageDigest.getInstance("SHA-512");
		 md.update(Base64.decodeBase64(value));	
		 toChapter1Digest = md.digest();
		 return toChapter1Digest;
	}
	
	public static PublicKey GetBytePublicKeyFromKeyStore(String as_FileName,String as_Password,String as_Alias) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException{
//		FileInputStream is = new FileInputStream("E:/Dropbox/Thesis/MSIT/keystore.wic.jks");
		FileInputStream is = new FileInputStream(as_FileName);
	    KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
//	    String password = "kensington";
	    char[] passwd = as_Password.toCharArray();
	    keystore.load(is, passwd);
//	    String alias = "Weerayut.Wic";
//	    Key key = keystore.getKey(as_Alias, as_AliasPassword.toCharArray());
	    Certificate cert = keystore.getCertificate(as_Alias);
        // Get public key
        PublicKey publicKey = cert.getPublicKey();

        String publicKeyString = Base64.encodeBase64String(publicKey.getEncoded());
//        System.out.println(publicKeyString);
        return publicKey;
	}
	
	public static Certificate GetByteCertificateFromKeyStore(String as_Alias,String as_Password,String as_FileName) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException{
//		FileInputStream is = new FileInputStream("E:/Dropbox/Thesis/MSIT/keystore.wic.jks");
		FileInputStream is = new FileInputStream(as_FileName);
	    KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
//	    String password = "kensington";
	    char[] passwd = as_Password.toCharArray();
	    keystore.load(is, passwd);
//	    String alias = "Weerayut.Wic";
	    Key key = keystore.getKey(as_Alias, passwd);
	    Certificate cert = keystore.getCertificate(as_Alias);

        return cert;
	}
	
	public static PublicKey GetBytePublicKeyFromKeyStore(byte[] as_FileKeystore,String as_Password,String as_Alias) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException{
//		FileInputStream is = new FileInputStream("E:/Dropbox/Thesis/MSIT/keystore.wic.jks");
		InputStream is = new ByteArrayInputStream(as_FileKeystore);
	    KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
//	    String password = "kensington";
	    char[] passwd = as_Password.toCharArray();
	    keystore.load(is, passwd);
//	    String alias = "Weerayut.Wic";
//	    Key key = keystore.getKey(as_Alias, passwd);
	    Certificate cert = keystore.getCertificate(as_Alias);
        // Get public key
        PublicKey publicKey = cert.getPublicKey();

        String publicKeyString = Base64.encodeBase64String(publicKey.getEncoded());
//        System.out.println(publicKeyString);
        return publicKey;
	}
	
		
	public static Key GetStringPrivateKeyFromP12(String as_FileName,String as_Password,String as_Alias,String as_AliasPassword) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException{
		KeyStore p12 = KeyStore.getInstance("pkcs12");
        p12.load(new FileInputStream(as_FileName), as_Password.toCharArray());
//        X509Certificate c = (X509Certificate) p12.getCertificate(as_Alias);
        Key key = p12.getKey(as_Alias, as_AliasPassword.toCharArray());
        
        System.out.println(key.getFormat());
        return key;
	}
	
	public static Key GetStringPrivateKeyFromP12(byte[] as_FileKeystore,String as_Password,String as_Alias,String as_AliasPassword) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException{
		KeyStore p12 = KeyStore.getInstance("pkcs12");
		InputStream is = new ByteArrayInputStream(as_FileKeystore);
        p12.load(is, as_Password.toCharArray());
//        X509Certificate c = (X509Certificate) p12.getCertificate(as_Alias);
        Key key = p12.getKey(as_Alias, as_AliasPassword.toCharArray());
        
        System.out.println(key.getFormat());
        return key;
	}
	
	public static Certificate GetByteCertificateFromP12(byte[] as_FileKeystore,String as_Password,String as_Alias,String as_AliasPassword) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException{
		KeyStore p12 = KeyStore.getInstance("pkcs12");
		InputStream is = new ByteArrayInputStream(as_FileKeystore);
        p12.load(is, as_Password.toCharArray());
//        X509Certificate c = (X509Certificate) p12.getCertificate(as_Alias);
	    Certificate cert = p12.getCertificate(as_Alias);
        return cert;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		String key = "o17Zk3UpCdwFbJ7tHlqY8g=="; // 128 bit key
//		String initVector = "RandomInitVector"; // 16 bytes IV
		String initVector = "UHElHPNZwJ2teaqegBVpjZljEPpwIZButUPqnwPfnBcUUB+sMuWlIQ1KCSt7xiUQL1zzqr1LH3I8Gs+0Zc6qIg=="; // 16 bytes IV
		String encrypt = Base64.encodeBase64String(encrypt(key,  initVector));
		String decrypt = Base64.encodeBase64String(decrypt(key,  encrypt));
//		String decrypt = new String(decrypt(key, "jQzxzx/WkvA+8p5AGW96NZcVzGFJHG+DGxYIlSj+dcwzR4gcoMAXcUF3HukFV3DjLqobcx6XPgWuLjGNyDFvKDdoNIbI1bMuFQiUefNTjp4y/Hrs457X8KbBY7EVUR2Pi8n8+ohZPln0PZFPT5yP3tZlcshva4fK/cU8to7HMLkFweAuNV1haYdbiL/Y9bZOlqQNyJrYrOyhCgHxMD4OlPpouRzeRd6L+x9XDYMN+iGkNbZjtygGDT8yvs60adhN2aqzA6SvHNPZuH9f5cgHYoXykGhCXfVl0sVtmDN82YMOHMhao7QglYBgsYcJblw9anylYiOQax8vNYpGsg6fAQ=="));
		System.out.println(decrypt);
		System.out.println((initVector.equals(decrypt)));//

		
		try {
			InputStream is = new FileInputStream("E:\\Dropbox\\Thesis\\MSIT\\weerayut.wic_ku.th.p12");
			byte[] bytes = IOUtils.toByteArray(is);
			SecreteKeyCryptography.GetStringPrivateKeyFromP12(bytes, "kensington", "weerayut.wic@ku.th", "kensington");
		} catch (UnrecoverableKeyException | KeyStoreException | CertificateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

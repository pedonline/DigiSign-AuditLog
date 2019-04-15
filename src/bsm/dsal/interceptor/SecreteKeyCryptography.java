/**
* @author Weerayut Wichaidit
* @version 2017-05-15
*/
package bsm.dsal.interceptor;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class SecreteKeyCryptography {
	static int pswdIterations = 65536  ;
	static int keySize = 128;
	static byte[] ivBytes;
	static byte[] saltBytes = {0,1,2,3,4,5,6};
	public static byte[] encrypt(String key, String value) {
		try {
			byte[] encodedKey = Base64.decodeBase64(key);
		    SecretKey secretKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
			Cipher cipher = Cipher.getInstance("AES");

			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] toChapter1Digest = Base64.decodeBase64(value);
			System.out.println("MessageDigest string: " + Base64.encodeBase64String(toChapter1Digest));
			byte[] encrypted = cipher.doFinal(toChapter1Digest);
			System.out.println("encrypted string: " + Base64.encodeBase64String(encrypted));
			return encrypted;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static byte[] decrypt(String key, String encrypted) {
		try {
			byte[] encodedKey     = Base64.decodeBase64(key);
		    SecretKey secretKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
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
	
	public static byte[] MessageDigest(byte[] value) throws NoSuchAlgorithmException {
		byte[] toChapter1Digest = null;
		 MessageDigest md = MessageDigest.getInstance("SHA-512");
		 md.update(value);	
		 toChapter1Digest = md.digest();
		 return toChapter1Digest;
	}
	
	public static PublicKey GetBytePublicKeyFromKeyStore(String as_Alias,String as_Password,String as_FileName) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException{
		FileInputStream is = new FileInputStream(as_FileName);
	    KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
	    char[] passwd = as_Password.toCharArray();
	    keystore.load(is, passwd);
	    Certificate cert = keystore.getCertificate(as_Alias);
        PublicKey publicKey = cert.getPublicKey();
        return publicKey;
	}
	
	public static Certificate GetByteCertificateFromKeyStore(String as_Alias,String as_Password,String as_FileName) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException{
		FileInputStream is = new FileInputStream(as_FileName);
	    KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
	    char[] passwd = as_Password.toCharArray();
	    keystore.load(is, passwd);
	    Certificate cert = keystore.getCertificate(as_Alias);

        return cert;
	}
	
	public static PublicKey GetBytePublicKeyFromKeyStore(String as_Alias,String as_Password,byte[] as_FileKeystore) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException{
		InputStream is = new ByteArrayInputStream(as_FileKeystore);
	    KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
	    char[] passwd = as_Password.toCharArray();
	    keystore.load(is, passwd);
	    Certificate cert = keystore.getCertificate(as_Alias);
        PublicKey publicKey = cert.getPublicKey();
        return publicKey;
	}
	
		
	public static Key GetStringPrivateKeyFromP12(String as_Alias,String as_Password,String as_FileName) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException{
		KeyStore p12 = KeyStore.getInstance("pkcs12");
        p12.load(new FileInputStream(as_FileName), as_Password.toCharArray());
        Key key = p12.getKey(as_Alias, as_Password.toCharArray());      
        System.out.println(key.getFormat());
        return key;
	}
	
	public static Key GetStringPrivateKeyFromP12(String as_Alias,String as_Password,byte[] as_FileKeystore) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException{
		KeyStore p12 = KeyStore.getInstance("pkcs12");
		InputStream is = new ByteArrayInputStream(as_FileKeystore);
        p12.load(is, as_Password.toCharArray());
        Key key = p12.getKey(as_Alias, as_Password.toCharArray());       
        System.out.println(key.getFormat());
        return key;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		String key = "o17Zk3UpCdwFbJ7tHlqY8g=="; // 128 bit key
		String initVector = "UHElHPNZwJ2teaqegBVpjZljEPpwIZButUPqnwPfnBcUUB+sMuWlIQ1KCSt7xiUQL1zzqr1LH3I8Gs+0Zc6qIg=="; // 16 bytes IV
		String encrypt = Base64.encodeBase64String(encrypt(key,  initVector));
		String decrypt = Base64.encodeBase64String(decrypt(key,  encrypt));
		System.out.println(decrypt);
		System.out.println((initVector.equals(decrypt)));//

		
	}
}

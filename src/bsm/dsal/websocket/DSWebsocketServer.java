/**
* @author Weerayut Wichaidit
* @version 2018-08-21
*/
package bsm.dsal.websocket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.google.gson.Gson;

import bsm.dsal.common.OneTimeKeyGenerator;
import bsm.dsal.model.Message;
import bsm.dsal.model.ResponseMessage;

public class DSWebsocketServer extends WebSocketServer{
	private static int TCP_PORT = 9000;

    private Set<WebSocket> conns;

    public DSWebsocketServer() {
        super(new InetSocketAddress(TCP_PORT));
        conns = new HashSet<>();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conns.add(conn);
        System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        conns.remove(conn);
        System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Message from client: " + message);
        String PKCS12_BASE64_PREFIX = "pkcs12;base64,"; 
        Gson gson = new Gson();
        Message lo_message = gson.fromJson(message, Message.class);
        System.out.println("Message On Obj :: " + lo_message);
        String ls_returnMessage = "";
        if(lo_message != null){
        	String lo_option = lo_message.getOption(); 
        	String lo_keysotreType = lo_message.getKeysotreType(); 
        	ResponseMessage responseMessage = new ResponseMessage();
        	if(lo_keysotreType.equals("PKCS12")){
        		try {
                	String ls_fileStore = lo_message.getKeysotreFile();
                	int index = ls_fileStore.indexOf(PKCS12_BASE64_PREFIX);
                	String data = ls_fileStore.substring(index + PKCS12_BASE64_PREFIX.length());
                	byte[] fileStore =  Base64.decodeBase64(data);
                	KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
                    ks.load(new ByteArrayInputStream(fileStore), lo_message.getPasswordKeysotre().toCharArray());
                	KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(lo_message.getUserName(), new KeyStore.PasswordProtection(lo_message.getUserPassword().toCharArray()));
            		X509Certificate cert = (X509Certificate) keyEntry.getCertificate();
                    PrivateKey pk = (PrivateKey) keyEntry.getPrivateKey();
                    responseMessage.setDigisignCertificate(Base64.encodeBase64String(cert.getEncoded()));	
                    responseMessage.setDigisignAlias(lo_message.getUserName());               			
                    if(lo_option.equals("ENCRYPT_OTK")){
                    	responseMessage.setResponseMethod("ENCRYPT_OTK");
                    	String ls_OTK = OneTimeKeyGenerator.KeyGenerator();
                    	Cipher cipherEncrypt;
						try {
							cipherEncrypt = Cipher.getInstance(pk.getAlgorithm());
							System.out.println(pk.getAlgorithm());
	                        cipherEncrypt.init(Cipher.ENCRYPT_MODE, pk); 
	                        byte[] e_string = cipherEncrypt.doFinal(Base64.decodeBase64(ls_OTK));
	                        ls_returnMessage = Base64.encodeBase64String(e_string);
	                        responseMessage.setDigisignOTK(ls_returnMessage);
						} catch (NoSuchPaddingException e) {
							ls_returnMessage = "ENCRYPT_OTK: "+e.getMessage();
							e.printStackTrace();
						} catch (InvalidKeyException e) {
							ls_returnMessage = "ENCRYPT_OTK: "+e.getMessage();
							e.printStackTrace();
						} catch (IllegalBlockSizeException e) {
							ls_returnMessage = "ENCRYPT_OTK: "+e.getMessage();
							e.printStackTrace();
						} catch (BadPaddingException e) {
							ls_returnMessage = "ENCRYPT_OTK: "+e.getMessage();
							e.printStackTrace();
						}    	
                    }
                    
                    ls_returnMessage = gson.toJson(responseMessage);
               
                } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException
        				| IOException | UnrecoverableEntryException e) {
                	ls_returnMessage = "SIGN: "+e.getMessage();
        			e.printStackTrace();
        		}
        	}
        }
        
        System.out.println("Return Message Object: " + ls_returnMessage);
        if(ls_returnMessage!= null && !ls_returnMessage.isEmpty()){
        	conn.send(ls_returnMessage);
        }else{
        	conn.send("error Message is null...!");
        }

    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        if (conn != null) {
            conns.remove(conn);
        }
        System.out.println("ERROR from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
        ex.printStackTrace();
    }

	@Override
	public void onStart() {
		System.out.println( "Server started!" );
	}
	
	public static void main( String[] args ) throws UnknownHostException {
		WebSocketImpl.DEBUG = false;
		BouncyCastleProvider provider = new BouncyCastleProvider();
        Security.addProvider(provider);
		DSWebsocketServer test = new DSWebsocketServer();
		test.setConnectionLostTimeout( 0 );
		test.start();
	}
}

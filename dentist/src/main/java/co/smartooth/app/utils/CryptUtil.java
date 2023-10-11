package co.smartooth.app.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import org.apache.commons.codec.binary.Hex;

public class CryptUtil {
	
	

    private static String secret_key="smartoothkorea";

    // 암호화
    public String generateEncryptedKey(String passKey) {
        String strKey = getSHA512();
        try {

            final Cipher encryptCipher = Cipher.getInstance("AES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, generateMySQLAESKey(strKey, "UTF-8"));
            return new String(Hex.encodeHex(encryptCipher.doFinal(passKey.getBytes("UTF-8")))).toUpperCase();
        } catch (Exception e) {
            //log.error("Encrypted Key Error", e);
            return null;
        }
    }

    // 복호화
    public static String generateKeyDecrypt(String passwordhex) {
        String strKey = getSHA512();
        try {
            final Cipher decryptCipher = Cipher.getInstance("AES");
            decryptCipher.init(Cipher.DECRYPT_MODE, generateMySQLAESKey(strKey, "UTF-8"));
            return new String(decryptCipher.doFinal(Hex.decodeHex(passwordhex.toCharArray())));
        } catch (Exception e) {
            //log.error("Key Decrypted Error", e);
            return null;
        }
    }

    private static SecretKeySpec generateMySQLAESKey(final String key, final String encoding) {
        try {
            final byte[] finalKey = new byte[16];
            int i = 0;
            for(byte b : key.getBytes(encoding))
                finalKey[i++%16] ^= b;
            return new SecretKeySpec(finalKey, "AES");
        } catch(UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    // SHA512
    private static String getSHA512() {
        String toReturn = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(secret_key.getBytes("utf8"));
            toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toReturn;
    }
	
	
//	IOS url-encode로 인해 '+'가 공백으로 바뀌는 증상 대응코드
//	AES128 : 키값 16bytes
//	AES192 : 키값 24bytes
//	AES256 : 키값 32bytes
//	public static final String PASSWD_SALT = "d9976d879b0ddde5f9cb7d76b24cc9a1";
//	public static final String PASSWORD_IV = "af9aa276320bc35e22c4497ea49c2568";
//	public static final int PASSWD_ITERATION = 1000;
//	public static final int PASSWD_KEY_SIZE = 128;
//	
//	
//	public static String algorithm = "AES/CBC/PKCS5Padding";
//	private static String key = "smartoothkoreaco";
//	private final String iv = key.substring(0, 16);
//	
//	public String encrypt(String text) throws Exception{
//		
//		Cipher cipher = Cipher.getInstance(algorithm);
//		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
//		IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
//		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
//		
//		byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
//		
//		return java.util.Base64.getEncoder().encodeToString(encrypted);
//		
//	}
//	
//	public String decrypt(String cipherText){
//		
//		Cipher cipher = null;
//		byte[] decodedBytes = null;
//		byte[] decrypted = null;
//
//		try {
//			cipher = Cipher.getInstance(algorithm);
//			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
//			IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
//			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);
//		
//			decodedBytes = java.util.Base64.getDecoder().decode(cipherText);
//			decrypted = cipher.doFinal(decodedBytes);
//			return new String(decrypted, "UTF-8");
//		
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return "false"; 
//		}
//		
//	}
}
package co.smartooth.app.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtil {
	
//	IOS url-encode로 인해 '+'가 공백으로 바뀌는 증상 대응코드
//	AES128 : 키값 16bytes
//	AES192 : 키값 24bytes
//	AES256 : 키값 32bytes
	
	public static final String PASSWD_SALT = "d9976d879b0ddde5f9cb7d76b24cc9a1";
	public static final String PASSWORD_IV = "af9aa276320bc35e22c4497ea49c2568";
	public static final int PASSWD_ITERATION = 1000;
	public static final int PASSWD_KEY_SIZE = 128;
	
	
	public static String algorithm = "AES/CBC/PKCS5Padding";
	private static String key = "smartoothkoreaco";
	private final String iv = key.substring(0, 16);
	
	public String encrypt(String text) throws Exception{
		
		Cipher cipher = Cipher.getInstance(algorithm);
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
		IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
		
		byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
		
		return java.util.Base64.getEncoder().encodeToString(encrypted);
		
	}
	
	public String decrypt(String cipherText){
		
		Cipher cipher = null;
		byte[] decodedBytes = null;
		byte[] decrypted = null;

		try {
			cipher = Cipher.getInstance(algorithm);
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);
		
			decodedBytes = java.util.Base64.getDecoder().decode(cipherText);
			decrypted = cipher.doFinal(decodedBytes);
			return new String(decrypted, "UTF-8");
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "false"; 
		}
		
	}
}
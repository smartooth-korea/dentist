package co.smartooth.app.utils;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;
 
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
 
public class AES256Util {
    //키값 32바이트: AES256(24: AES192, 16: AES128)
//    public static final String secretKey = "01234567890123450123456789012345";
    public static final String secretKey = "smartoothkoreacosmartoothkoreaco";
    //public static final byte[] ivBytes = "0123456789012345".getBytes();
    public static final byte[] ivBytes = "smartoothkoreaco".getBytes();
 
    //AES256 암호화
    public String aesEncode(String str) {
        try {
            byte[] textBytes = str.getBytes("UTF-8");
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");
            Cipher cipher = null;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
            return Base64.getMimeEncoder().encodeToString(cipher.doFinal(textBytes));
        } catch (Exception e) {
        	e.printStackTrace();
        	return "false";
        }
    }
 
    //AES256 복호화
    public String aesDecode(String str) {
        try {
        	
        	//	IOS url-encode로 인해 '+'가 공백으로 바뀌는 증상 대응코드
        	str = str.replace(" ", "+");
 
            byte[] textBytes = Base64.getMimeDecoder().decode(str);
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
            return new String(cipher.doFinal(textBytes), "UTF-8");
        } catch (Exception e) {
        	e.printStackTrace();
        	return "false"; 
        }
    }
}
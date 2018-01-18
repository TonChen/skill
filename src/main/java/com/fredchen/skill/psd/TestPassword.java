package com.fredchen.skill.psd;

       

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;

import javacommon.util.MD5Util;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

       
/** 

 * 3DES加密工具类 

 *  

 * @author liufeng  

 * @date 2012-10-11 

 */

public class TestPassword {  

    // 密钥  

    private final static String secretKey = "CfthOpenApi@haha#Encrytp";  

    // 向量  

    private final static String iv = "19283127";  

    // 加解密统一使用的编码方式  

    private final static String encoding = "utf-8";  

       

    /** 

     * 3DES加密 

     *  

     * @param plainText 普通文本 

     * @return 

     * @throws Exception  

     */

    public static String encode(String plainText)  {  

        Key deskey = null;  

        DESedeKeySpec spec;
		try {
			spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
			
			deskey = keyfactory.generateSecret(spec);  
			
			
			
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");  
			
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());  
			
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);  
			
			byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));  
			return Base64.encode(encryptData);  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return plainText;  
    }  

       

    /** 

     * 3DES解密 

     *  

     * @param encryptText 加密文本 

     * @return 

     * @throws Exception 

     */

    public static String decode(String encryptText) {  

        Key deskey = null;  

        DESedeKeySpec spec;
		try {
			spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
			
			deskey = keyfactory.generateSecret(spec);  
			
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");  
			
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());  
			
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);  
			
			
			
			byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));  
			
			
			
			return new String(decryptData, encoding);  
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return encryptText;  
    }  
    
    
    public static void main(String[] args) throws UnsupportedEncodingException {
    	String eUrl = URLEncoder.encode("bt1v0+ChRe58/t/7B01KaLYpOfceb9+rgemmveFhq+9ZlxrsqUkEmh/h8PO8a5zFR6Gj5IE9vmqKgBLEvRwFDA==", "UTF-8");
    	System.out.println(eUrl);
    	String dUrl = URLDecoder.decode(eUrl, "UTF-8");
    	System.out.println(dUrl);
    	String md5 = MD5Util.getMD5(eUrl+"1460958479");
    	System.out.println(md5);

		System.err.println(34/10);
	}
}
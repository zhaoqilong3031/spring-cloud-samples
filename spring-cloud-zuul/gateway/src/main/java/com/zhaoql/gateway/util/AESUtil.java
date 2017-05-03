package com.zhaoql.gateway.util;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder; 
import sun.misc.BASE64Encoder;  

public class AESUtil {
		
		static Logger logger = LoggerFactory.getLogger(AESUtil.class);
	
	    /**
	     * 将byte[]转为各种进制的字符串
	     * @param bytes byte[]
	     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
	     * @return 转换后的字符串
	     */ 
	    public static String binary(byte[] bytes, int radix){ 
	        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数 
	    } 
	     
	    /**
	     * base 64 encode
	     * @param bytes 待编码的byte[]
	     * @return 编码后的base 64 code
	     */ 
	    public static String base64Encode(byte[] bytes){ 
	        return new BASE64Encoder().encode(bytes); 
	    } 
	     
	    /**
	     * base 64 decode
	     * @param base64Code 待解码的base 64 code
	     * @return 解码后的byte[]
	     * @throws Exception
	     */ 
	    public static byte[] base64Decode(String base64Code) { 
	    	if (isEmpty(base64Code)) {
	    		return null;
	    	}
	    	byte[] bytes = null;
			try {
				bytes = new BASE64Decoder().decodeBuffer(base64Code);
			} catch (IOException e) {
				logger.error("BASE64解密失败", e);
			}
	        return  bytes; 
	    } 
	     
	    /**
	     * 获取byte[]的md5值
	     * @param bytes byte[]
	     * @return md5
	     * @throws Exception
	     */ 
	    public static byte[] md5(byte[] bytes) { 
	    	if (bytes == null || bytes.length == 0) {
	    		return null;
	    	}
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(bytes); 
				return md.digest(); 
			} catch (NoSuchAlgorithmException e) {
				logger.error("MD5获取byte[]失败", e);
			} 
			return null;
	    } 
	     
	    /**
	     * 获取字符串md5值
	     * @param msg 
	     * @return md5
	     * @throws Exception
	     */ 
	    public static byte[] md5(String msg) { 
	        return isEmpty(msg) ? null : md5(msg.getBytes()); 
	    } 
	     
	    /**
	     * 结合base64实现md5加密
	     * @param msg 待加密字符串
	     * @return 获取md5后转为base64
	     * @throws Exception
	     */ 
	    public static String md5Encrypt(String msg) { 
	        return isEmpty(msg) ? null : base64Encode(md5(msg)); 
	    } 
	     
	    /**
	     * AES加密
	     * @param content 待加密的内容
	     * @param encryptKey 加密密钥
	     * @return 加密后的byte[]
	     * @throws Exception
	     */ 
	    public static byte[] aesEncryptToBytes(String content, String encryptKey)  { 
	        try {
				KeyGenerator kgen = KeyGenerator.getInstance("AES"); 
				kgen.init(128, new SecureRandom(encryptKey.getBytes())); 
 
				Cipher cipher = Cipher.getInstance("AES"); 
				cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES")); 
				 
				return cipher.doFinal(content.getBytes("utf-8"));
			} catch (Exception e) {
				logger.error("AES加密失败", e);
				return null;
			} 
	         
	        
	    } 
	     
	    /**
	     * AES加密为base 64 code
	     * @param content 待加密的内容
	     * @param encryptKey 加密密钥
	     * @return 加密后的base 64 code
	     * @throws Exception
	     */ 
	    public static String aesEncrypt(String content, String encryptKey)  { 
	        return base64Encode(aesEncryptToBytes(content, encryptKey)); 
	    } 
	     
	    /**
	     * AES解密
	     * @param encryptBytes 待解密的byte[]
	     * @param decryptKey 解密密钥
	     * @return 解密后的String
	     * @throws Exception
	     */ 
	    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) { 
	         if (encryptBytes == null || decryptKey == null){
	        	 return null;
	         }
			try {
				KeyGenerator kgen = KeyGenerator.getInstance("AES"); 
				kgen.init(128, new SecureRandom(decryptKey.getBytes())); 
				 
				Cipher cipher = Cipher.getInstance("AES"); 
				cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES")); 
				byte[] decryptBytes = cipher.doFinal(encryptBytes);
				return new String(decryptBytes);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	         
	        return null ; 
	    } 
	     
	    /**
	     * 将base 64 code AES解密
	     * @param encryptStr 待解密的base 64 code
	     * @param decryptKey 解密密钥
	     * @return 解密后的string
	     * @throws Exception
	     */ 
	    public static String aesDecrypt(String encryptStr, String decryptKey) { 
	        return isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey); 
	    } 
	   
	    private static boolean isEmpty(String str){
	        boolean flag=true;
	        if(str!=null&&!"".equals(str))
	            flag=false;
	        return flag;
	    }
	    
	    /*public static void main(String[] args) { 
	        String content = "3456"; 
	        String key = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiJ1bnNldCIsImF1ZCI6WyJnZXR3YXkiLCJjYXJfcmVzb3VyY2UiXSwiY3VzdG9tQXV0aGVzIjoibHA0VXpndER2VnlOZ2pGMk5Ed09Ddz09Iiwic2NvcGUiOlsicmVhZCJdLCJleHAiOjE0ODg3MDYwOTcsImF1dGhvcml0aWVzIjpbIlJPTEVfY2xpZW50Il0sImp0aSI6IjRiNWM1ZDJjLTJhOTAtNDc1My05MzcwLTU2ZWJiZGIyZTczMyIsImNsaWVudF9pZCI6IjEyMyJ9.w4hDEsRZNDqNuEyfm7tDq99ONLkPj-NRKzwFC4euQFM78WSO0YxHTejv0e29f2skwGm3p_qSWcYoiatC1dpw15-QNQDdxbqgL4b87-oRb80xHc7VODkOmeLbJcbmFW5cowJeMBR7wFrYMFwCDxcPWGKeCOBcfV1GmYDrfLVZHrM"; 
	        System.out.println(key.length());
	        String encrypt = aesEncrypt(content, key); 
	        System.out.println("加密后：" + encrypt); 
	         
	        String decrypt = aesDecrypt(encrypt, key); 
	        System.out.println("解密后：" + decrypt); 
	    } */
	}  
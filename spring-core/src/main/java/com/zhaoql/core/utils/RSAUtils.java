package com.zhaoql.core.utils;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;

import com.zhaoql.core.exception.CustomException;

public class RSAUtils {

	/**
	 * 加密算法RSA
	 */
	public static final String SIGN_ALGORITHM = "RSA";
	public static final String SIGN_ALGORITHMS = "SHA1withRSA";

	/**
	 * 获取公钥的key
	 */
	private static final String PUBLIC_KEY = "PublicKey";

	/**
	 * 获取私钥的key
	 */
	private static final String PRIVATE_KEY = "PrivateKey";

	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
		// String
		// publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDE/qLX5dNCDrWs3fpfuMryBQSskVzmhmLitT3RR/Bsk8R0reewaHZ3Xk/w0h8l9qJp7r+NFve3eLopDIf0q6Sz80hd1sjRrMvXGq/eehH8SzKIru1vZv5FIim5CORU5I784KPZHzFcWqvcOWCidnvuYIRKmR94Gy/fcjV1Ngw4gQIDAQAB";
		// String
		// privateKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMT+otfl00IOtazd+l+4yvIFBKyRXOaGYuK1PdFH8GyTxHSt57BodndeT/DSHyX2omnuv40W97d4uikMh/SrpLPzSF3WyNGsy9car956EfxLMoiu7W9m/kUiKbkI5FTkjvzgo9kfMVxaq9w5YKJ2e+5ghEqZH3gbL99yNXU2DDiBAgMBAAECgYA/AXZR+SbdbNj8hi7LKM54I9S/4OpGrNq5yeAQrKKgB48BBpIg4Phq61ZEHuBpgv2lRgAr/xmRq+JWuLRPNArEF7M+01rQJOiqqyoz8mRigtm/lHqC7+PdNwmB+sxdCocVpRh8FvAop7iEyvMdOS2ESey4FPunfCD73Z4hmv1oxQJBAOQtE8ABGskyUo3Xqh1kxK1iQjHtnHfSsOnm5Y6XUMMsC5EqPrw0TAvjB012rYSbK0sd3L17a83XwfFVE82YONcCQQDdBCz6So9iBTfziMsQq4uUPDwBsbQadMJJ/Ff/vNRdt8wrBGQhcx79S9LjcGjLl8hg81tPUJofvAsaYjpisDZnAkEAvtCLkafa1KKGsyPFmWsppq1jGgnRLcs+M3zqQAJ+gZkV20Hu0MkTR2WGN9ulZB23aMwCS1spXiyufbleJDLGZQJAc5y9kmfyPQoRzJjf8GZr6yzfDtvQJI6zG1SfC63RQPzmFhKD9mNr8H/7XvHsP6O0M9LZDtJQ7QzGmhSMChe3PQJAOfjNIbehb5n2JCheKT1V+o45Mw2OQTr6wLEhxRlIMA+4eiDSTeMGVbi5oA4fVQftYR1uOSr34abEzBY/P3iLrQ==";

		// String publicKey =
		// "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCJnofEAu7Gt/qCjSnPN+k/Vsfal/7fQ1N024XVBLk6KGVqk4O63DGp9rrNT9YxxiDEfU7OV4wYjUAKzbRCWt8nsAqeWwk32ff046w6SkliZdtYB+hPajOf7AL50+o1v+jtmFHZEoYDmG4BktWfnlc6+DggeX7ZRj+GfnQaHAJcgwIDAQAB";
		// String privateKey =
		// "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMT+otfl00IOtazd+l+4yvIFBKyRXOaGYuK1PdFH8GyTxHSt57BodndeT/DSHyX2omnuv40W97d4uikMh/SrpLPzSF3WyNGsy9car956EfxLMoiu7W9m/kUiKbkI5FTkjvzgo9kfMVxaq9w5YKJ2e+5ghEqZH3gbL99yNXU2DDiBAgMBAAECgYA/AXZR+SbdbNj8hi7LKM54I9S/4OpGrNq5yeAQrKKgB48BBpIg4Phq61ZEHuBpgv2lRgAr/xmRq+JWuLRPNArEF7M+01rQJOiqqyoz8mRigtm/lHqC7+PdNwmB+sxdCocVpRh8FvAop7iEyvMdOS2ESey4FPunfCD73Z4hmv1oxQJBAOQtE8ABGskyUo3Xqh1kxK1iQjHtnHfSsOnm5Y6XUMMsC5EqPrw0TAvjB012rYSbK0sd3L17a83XwfFVE82YONcCQQDdBCz6So9iBTfziMsQq4uUPDwBsbQadMJJ/Ff/vNRdt8wrBGQhcx79S9LjcGjLl8hg81tPUJofvAsaYjpisDZnAkEAvtCLkafa1KKGsyPFmWsppq1jGgnRLcs+M3zqQAJ+gZkV20Hu0MkTR2WGN9ulZB23aMwCS1spXiyufbleJDLGZQJAc5y9kmfyPQoRzJjf8GZr6yzfDtvQJI6zG1SfC63RQPzmFhKD9mNr8H/7XvHsP6O0M9LZDtJQ7QzGmhSMChe3PQJAOfjNIbehb5n2JCheKT1V+o45Mw2OQTr6wLEhxRlIMA+4eiDSTeMGVbi5oA4fVQftYR1uOSr34abEzBY/P3iLrQ==";
		try {
			// String data =
			// "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiLnlKjmiLflkI3np7AiLCJpYXQiOjE0NzY3NzM1NDd9.txVlophXQ2pOlIwL7Lkp_JJSRpSGF-qRuqr4qhwOqOlayEF-ytUHiDuazvqK-cJd9rFo2xuczzjf72LmKeujMZOh4zXP42j1IlfVxA68uD8H8pX-ZAp90xlJZ3puCTHIvCPTjBZ-Gnen9Bh9kzZGjlNt2pIOetQix7275-c1lt8";
			// String encryptedData = encryptByPrivateKey(data, privateKey);
			// System.err.println(encryptedData);
			// System.err.println(new String(decryptByPublicKey(encryptedData,
			// publicKey)));
			//
			// System.err.println(Base64Utils.decode(data.getBytes()));
			// String encryptedDatas = encryptByPublicKey(data, publicKey);
			// System.err.println(encryptedDatas);
			// System.err.println(decryptByPrivateKey(encryptedDatas,
			// privateKey));
			Map<String, String> aa = getKeys();
			System.err.println(aa.get(PUBLIC_KEY));
			System.err.println(aa.get(PRIVATE_KEY));
			// String
			// content="sellerId=5edcd0504e1811e6fda1579f3ebe8971&totalPrice=0.10&tradeNo=201610120854085518Z2&type=0";
			// String sign = rsaSign(content, privateKey,"utf-8");
			// System.err.println("");
			// System.err.println(rsaSignCheck(content, sign,
			// publicKey,"utf-8"));
			// Map<String,String> map = getKeys();
			// System.err.println(map.get(PUBLIC_KEY));
			// System.err.println(map.get(PRIVATE_KEY));
			// System.err.println(getPrivate(SIGN_ALGORITHM, privateKey));
			// String dataa = "aaaa";
			// String sign = rsaSign(dataa, privateKey, "utf-8");
			// System.err.println(rsaSign("eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiLnlKjmiLflkI3np7AiLCJpYXQiOjE0NzY3NzM5NzB9",
			// privateKey, "US-ASCII"));
			// System.err.println(rsaSignCheck(dataa, sign, publicKey,
			// "utf-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static HashMap<String, String> getKeys() {
		try {
			HashMap<String, String> map = new HashMap<>();
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(SIGN_ALGORITHM);
			keyPairGen.initialize(1024);
			KeyPair keyPair = keyPairGen.generateKeyPair();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			map.put(PUBLIC_KEY, new String(Base64Utils.encode(publicKey.getEncoded())));
			map.put(PRIVATE_KEY, new String(Base64Utils.encode(privateKey.getEncoded())));
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("生成秘钥对失败");
		}
	}


	public static String decryptByPrivateKey(String content, String privateKey) throws Exception {
		return decryptByPrivateKey(content, privateKey, "utf-8");
	}

	public static String decryptByPrivateKey(String content, String privateKey, String charset) throws Exception {
		PrivateKey privateK = getPrivate(SIGN_ALGORITHM, privateKey);
		Cipher cipher = Cipher.getInstance(SIGN_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, privateK);
		byte[] data = StringUtils.isEmpty(charset) ? content.getBytes() : content.getBytes(charset);
		byte[] encryptedData = Base64Utils.decode(data);
		int inputLen = encryptedData.length;
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			return new String(out.toByteArray());
		} catch (Exception e) {
			throw new CustomException("公钥解密失败", e);
		} finally {

		}
	}


	public static String decryptByPublicKey(String content, String publicKey) throws Exception {
		return decryptByPublicKey(content, publicKey, "utf-8");
	}


	public static String decryptByPublicKey(String content, String publicKey, String charset) throws Exception {
		PublicKey publicK = getPublicKey(SIGN_ALGORITHM, publicKey);
		Cipher cipher = Cipher.getInstance(SIGN_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, publicK);
		byte[] data = StringUtils.isEmpty(charset) ? content.getBytes() : content.getBytes(charset);
		byte[] encryptedData = Base64Utils.decode(data);
		int inputLen = encryptedData.length;
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			return new String(out.toByteArray());
		} catch (Exception e) {
			throw new CustomException("公钥解密失败", e);
		} finally {

		}
	}

	 
	public static String encryptByPublicKey(String content, String publicKey) throws Exception {
		return encryptByPublicKey(content, publicKey, "utf-8");
	}

	
	public static String encryptByPublicKey(String content, String publicKey, String charset) throws Exception {
		PublicKey publicK = getPublicKey(SIGN_ALGORITHM, publicKey);
		Cipher cipher = Cipher.getInstance(SIGN_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicK);
		byte[] data = StringUtils.isEmpty(charset) ? content.getBytes() : content.getBytes(charset);
		int inputLen = data.length;
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			return Base64Utils.encodeToString(out.toByteArray());
		} catch (Exception e) {
			throw new CustomException("公钥加密失败", e);
		} finally {

		}
	}


	public static String encryptByPrivateKey(String content, String privateKey) throws Exception {
		return encryptByPrivateKey(content, privateKey, "utf-8");
	}


	public static String encryptByPrivateKey(String content, String privateKey, String charset) throws Exception {
		PrivateKey privateK = getPrivate(SIGN_ALGORITHM, privateKey);
		Cipher cipher = Cipher.getInstance(SIGN_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, privateK);
		byte[] data = StringUtils.isEmpty(charset) ? content.getBytes() : content.getBytes(charset);
		int inputLen = data.length;
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			return Base64Utils.encodeToString(out.toByteArray());
		} catch (Exception e) {
			throw new CustomException("私钥加密失败", e);
		} finally {

		}
	}

	public static String rsaSign(String content, String privateKey, String charset) {
		try {
			PrivateKey priKey = getPrivate(SIGN_ALGORITHM, privateKey);
			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			signature.initSign(priKey);
			if (StringUtils.isEmpty(charset)) {
				signature.update(content.getBytes());
			} else {
				signature.update(content.getBytes(charset));
			}
			return Base64Utils.encodeToString(signature.sign());
		} catch (Exception e) {
			throw new CustomException("签名失败", e);
		}
	}

	public static boolean rsaSignCheck(String content, String sign, String publicKey, String charset) {
		try {
			PublicKey pubKey = getPublicKey(SIGN_ALGORITHM, publicKey);
			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			signature.initVerify(pubKey);
			if (StringUtils.isEmpty(charset)) {
				signature.update(content.getBytes());
			} else {
				signature.update(content.getBytes(charset));
			}
			return signature.verify(Base64Utils.decode(sign.getBytes()));
		} catch (Exception e) {
			throw new CustomException("签名验证失败", e);
		}
	}

	public static PublicKey getPublicKey(String algorithm, String publicKey) throws Exception {
		if (StringUtils.isBlank(publicKey) || StringUtils.isEmpty(algorithm)) {
			return null;
		}
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		byte[] encodedKey = Base64Utils.decode(publicKey.getBytes());
		return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
	}

	public static PrivateKey getPrivate(String algorithm, String privateKey) throws Exception {
		if (StringUtils.isBlank(privateKey) || StringUtils.isEmpty(algorithm)) {
			return null;
		}
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		byte[] encodedKey = Base64Utils.decode(privateKey.getBytes());
		return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
	}
}
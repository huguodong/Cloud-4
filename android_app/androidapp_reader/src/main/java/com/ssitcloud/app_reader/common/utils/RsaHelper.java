package com.ssitcloud.app_reader.common.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;

public class RsaHelper {

	/**
	 * 生成RSA密钥对
	 * 
	 * @param keyLength
	 *            密钥长度，范围：512～2048
	 */
	public static KeyPair generateRSAKeyPair(int keyLength) {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(keyLength);
			return kpg.genKeyPair();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	public static String encodePublicKeyToXml(PublicKey key) {
		if (!RSAPublicKey.class.isInstance(key)) {
			return null;
		}
		RSAPublicKey pubKey = (RSAPublicKey) key;
		StringBuilder sb = new StringBuilder(256);

		sb.append("<RSAKeyValue>");
		sb.append("<Modulus>")
				.append(Base64Helper.encode(pubKey.getModulus().toByteArray()))
				.append("</Modulus>");
		sb.append("<Exponent>")
				.append(Base64Helper.encode(pubKey.getPublicExponent()
						.toByteArray())).append("</Exponent>");
		sb.append("</RSAKeyValue>");
		return sb.toString();
	}

	public static PublicKey decodePublicKeyFromXml(String xml) {
		xml = xml.replaceAll("\r", "").replaceAll("\n", "");
		BigInteger modulus = new BigInteger(1, Base64Helper.decode(StringHelper
				.GetMiddleString(xml, "<Modulus>", "</Modulus>")));
		BigInteger publicExponent = new BigInteger(1,
				Base64Helper.decode(StringHelper.GetMiddleString(xml,
						"<Exponent>", "</Exponent>")));

		RSAPublicKeySpec rsaPubKey = new RSAPublicKeySpec(modulus,
				publicExponent);

		KeyFactory keyf;
		try {
			keyf = KeyFactory.getInstance("RSA");
			return keyf.generatePublic(rsaPubKey);
		} catch (Exception e) {
			return null;
		}
	}

	public static PrivateKey decodePrivateKeyFromXml(String xml) {
		xml = xml.replaceAll("\r", "").replaceAll("\n", "");
		BigInteger modulus = new BigInteger(1, Base64Helper.decode(StringHelper
				.GetMiddleString(xml, "<Modulus>", "</Modulus>")));
		BigInteger publicExponent = new BigInteger(1,
				Base64Helper.decode(StringHelper.GetMiddleString(xml,
						"<Exponent>", "</Exponent>")));
		BigInteger privateExponent = new BigInteger(1,
				Base64Helper.decode(StringHelper.GetMiddleString(xml, "<D>",
						"</D>")));
		BigInteger primeP = new BigInteger(1, Base64Helper.decode(StringHelper
				.GetMiddleString(xml, "<P>", "</P>")));
		BigInteger primeQ = new BigInteger(1, Base64Helper.decode(StringHelper
				.GetMiddleString(xml, "<Q>", "</Q>")));
		BigInteger primeExponentP = new BigInteger(1,
				Base64Helper.decode(StringHelper.GetMiddleString(xml, "<DP>",
						"</DP>")));
		BigInteger primeExponentQ = new BigInteger(1,
				Base64Helper.decode(StringHelper.GetMiddleString(xml, "<DQ>",
						"</DQ>")));
		BigInteger crtCoefficient = new BigInteger(1,
				Base64Helper.decode(StringHelper.GetMiddleString(xml,
						"<InverseQ>", "</InverseQ>")));

		RSAPrivateCrtKeySpec rsaPriKey = new RSAPrivateCrtKeySpec(modulus,
				publicExponent, privateExponent, primeP, primeQ,
				primeExponentP, primeExponentQ, crtCoefficient);

		KeyFactory keyf;
		try {
			keyf = KeyFactory.getInstance("RSA");
			return keyf.generatePrivate(rsaPriKey);
		} catch (Exception e) {
			return null;
		}
	}

	public static String encodePrivateKeyToXml(PrivateKey key) {
		if (!RSAPrivateCrtKey.class.isInstance(key)) {
			return null;
		}
		RSAPrivateCrtKey priKey = (RSAPrivateCrtKey) key;
		StringBuilder sb = new StringBuilder();

		sb.append("<RSAKeyValue>");
		sb.append("<Modulus>")
				.append(Base64Helper.encode(priKey.getModulus().toByteArray()))
				.append("</Modulus>");
		sb.append("<Exponent>")
				.append(Base64Helper.encode(priKey.getPublicExponent()
						.toByteArray())).append("</Exponent>");
		sb.append("<P>")
				.append(Base64Helper.encode(priKey.getPrimeP().toByteArray()))
				.append("</P>");
		sb.append("<Q>")
				.append(Base64Helper.encode(priKey.getPrimeQ().toByteArray()))
				.append("</Q>");
		sb.append("<DP>")
				.append(Base64Helper.encode(priKey.getPrimeExponentP()
						.toByteArray())).append("</DP>");
		sb.append("<DQ>")
				.append(Base64Helper.encode(priKey.getPrimeExponentQ()
						.toByteArray())).append("</DQ>");
		sb.append("<InverseQ>")
				.append(Base64Helper.encode(priKey.getCrtCoefficient()
						.toByteArray())).append("</InverseQ>");
		sb.append("<D>")
				.append(Base64Helper.encode(priKey.getPrivateExponent()
						.toByteArray())).append("</D>");
		sb.append("</RSAKeyValue>");
		return sb.toString();
	}

    /**
     *拆分数组
     */
    private static byte[][] splitArray(byte[] data,int len){
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
		if (y != 0) {
			z = 1;
		}
		byte[][] arrays = new byte[x+z][];

        for(int i=0,length = x+z; i<length; i++){
			byte[] arr;
            if(z==1 && i==length-1){
				arr = new byte[y];
                System.arraycopy(data, i*len, arr, 0, y);
            }else{
				arr = new byte[len];
                System.arraycopy(data, i*len, arr, 0, len);
            }
            arrays[i] = arr;
        }

        return arrays;
    }

	/**
	 * 用公钥加密
	 * @param data 数据
	 * @param pubKey 公钥
	 * @return 加密后的数据
	 */
	public static byte[] encryptData(byte[] data, PublicKey pubKey) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			int pukSplitLength = ((RSAPublicKey) pubKey).getModulus().bitLength()/8-11;
			byte[][] encryptedData = splitArray(data,pukSplitLength);
			byte[] ming = new byte[0];
			for (byte[] bytes : encryptedData) {
				byte[] temp = cipher.doFinal(bytes);
				int mingLength = ming.length;
				ming = Arrays.copyOf(ming,mingLength+temp.length);
				System.arraycopy(temp,0,ming,mingLength,temp.length);
			}
			return ming;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 用私钥加密
	 * @param data 数据
	 * @param priKey 私钥
	 */
	public static byte[] decryptData(byte[] data, PrivateKey priKey) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, priKey);

			int prkSplitLength = ((RSAPrivateKey) priKey).getModulus().bitLength()/8;
			byte[][] decryptData = splitArray(data,prkSplitLength);
			byte[] mi = new byte[0];
			for (byte[] bytes : decryptData) {
				byte[] temp = cipher.doFinal(bytes);
				int miLength = mi.length;
				mi = Arrays.copyOf(mi,miLength+temp.length);
				System.arraycopy(temp,0,mi,miLength,temp.length);
			}
			return mi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据指定私钥对数据进行签名(默认签名算法为"SHA1withRSA")
	 * 
	 * @param data
	 *            要签名的数据
	 * @param priKey
	 *            私钥
	 */
	public static byte[] signData(byte[] data, PrivateKey priKey) {
		return signData(data, priKey, "SHA1withRSA");
	}

	/**
	 * 根据指定私钥和算法对数据进行签名
	 * 
	 * @param data
	 *            要签名的数据
	 * @param priKey
	 *            私钥
	 * @param algorithm
	 *            签名算法
	 */
	public static byte[] signData(byte[] data, PrivateKey priKey,
			String algorithm) {
		try {
			Signature signature = Signature.getInstance(algorithm);
			signature.initSign(priKey);
			signature.update(data);
			return signature.sign();
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 用指定的公钥进行签名验证(默认签名算法为"SHA1withRSA")
	 * 
	 * @param data
	 *            数据
	 * @param sign
	 *            签名结果
	 * @param pubKey
	 *            公钥
	 */
	public static boolean verifySign(byte[] data, byte[] sign, PublicKey pubKey) {
		return verifySign(data, sign, pubKey, "SHA1withRSA");
	}

	/**
	 * 
	 * @param data
	 *            数据
	 * @param sign
	 *            签名结果
	 * @param pubKey
	 *            公钥
	 * @param algorithm
	 *            签名算法
	 */
	public static boolean verifySign(byte[] data, byte[] sign,
			PublicKey pubKey, String algorithm) {
		try {
			Signature signature = Signature.getInstance(algorithm);
			signature.initVerify(pubKey);
			signature.update(data);
			return signature.verify(sign);
		} catch (Exception ex) {
			return false;
		}
	}
	
	/**
	 * RSA加密
	 *
	 * <p>2016年4月13日 下午6:45:03
	 * <p>create by hjc
	 * @param str 明文密码
	 * @param publicKey 公钥
	 * @return Base64编码过的密文
	 */
	public static String encryRSA(String str,String publicKey) {
		//公钥转化
		PublicKey p = decodePublicKeyFromXml(publicKey);
		//将字符串转换
		byte[] strArry = str.getBytes(); 
		//加密
		byte[] encryptedDataByteArray = encryptData(strArry, p);
		
		return Base64Helper.encode(encryptedDataByteArray);
	}
	
	/**
	 * RSA 解密
	 *
	 * <p>2016年4月13日 下午7:24:30
	 * <p>create by hjc
	 * @param str  Base64编码过的密文
	 * @param privateKey  私钥
 	 * @return 报错的话返回NULL
	 */
	public static String decryRSA(String str,String privateKey) {
		PrivateKey p = decodePrivateKeyFromXml(privateKey);
		byte[] strArry = Base64Helper.decode(str);
		byte[] decryptedDataByteArray = RsaHelper.decryptData(
				strArry, p);
		String pwd = null;
		try {
			pwd = new String(decryptedDataByteArray,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pwd;
	}
}

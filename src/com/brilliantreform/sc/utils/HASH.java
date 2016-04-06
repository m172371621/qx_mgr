package com.brilliantreform.sc.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class HASH {

	private static Logger logger = Logger.getLogger(HASH.class);
	
	public static String md5(String s){
		return hash(s,"UTF-8","MD5");
	}
	
	public static String sha1(String s){
		return hash(s,"UTF-8","SHA1");
	}
	
	public static String md5(String s,String charset){
		
		return hash(s,charset,"MD5");
	}
	
	public static String sha1(String s,String charset){
		
		return hash(s,charset,"SHA1");
	}
	
	public static String hash(String s,String charset,String algorithm){
		MessageDigest md = null;
		StringBuffer sb = new StringBuffer();
		try {
			md = MessageDigest.getInstance(algorithm);
			md.update(s.getBytes(charset));
			byte[] result = md.digest();
			
	        for (byte b : result) {
	            int i = b & 0xff;
	            if (i <= 0xf) {
	                sb.append(0);
	            }
	            sb.append(Integer.toHexString(i));
	        }
		} catch (UnsupportedEncodingException e){
			logger.error("编码异常:", e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("算法异常:", e);
		}
		
        if(sb.length()==0) return null;
        return sb.toString();
	}
	
	
	public static void main(String[] args){
		
	}
}

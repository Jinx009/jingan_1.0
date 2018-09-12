/**
 * 版权所有(C)，中国银联股份有限公司，2002-2014，所有权利保留。
 * 
 * 项目名：	gateway-service
 * 文件名：	ThreeDES.java
 * 模块说明：	
 * 修改历史：
 * 2014-7-30 - linhui - 创建。
 */
package com.protops.gateway.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

/**
 * @author linhui
 * 
 */
@SuppressWarnings("restriction")
public class ThreeDES {
    static {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
    }
    
    /**
     * 3DES加密
     * @param key
     * @param value
     * @return
     */
    public static String encrypt(String key, String value) {
        byte[] encoded = encryptMode(Base64.decodeBase64(key), value.getBytes());
        return Base64.encodeBase64URLSafeString(encoded);
    }
    
    public static String decrypt(String key, String value) {
        byte[] decoded = decryptMode(Base64.decodeBase64(key), Base64.decodeBase64(value));
        return new String(decoded);
    }
    
    // 定义 加密算法,可用 DES,DESede,Blowfish
    private static final String Algorithm = "DESede";

    // keybyte为加密密钥，长度为24字节
    // src为被加密的数据缓冲区（源）
    private static byte[] encryptMode(byte[] keybyte, byte[] src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            // 加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    // keybyte为加密密钥，长度为24字节
    // src为加密后的缓冲区
    private static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            // 解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

}

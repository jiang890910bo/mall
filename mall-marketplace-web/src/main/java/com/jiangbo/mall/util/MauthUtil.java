package com.jiangbo.mall.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


/**
 * 加密方式工具类
 */
public class MauthUtil {
    private static Logger logger = LoggerFactory.getLogger(MauthUtil.class);

    private static String metaKey = "NGQxNmUwMjM4M2Y0MTI2MTM3NDI0Y2MxMjA1N2IyNDM=";
    // for 16 hex char
    private static char[] HEXCHAR = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static void main(String[] args) throws Exception {
//        String msg = System.currentTimeMillis() + ":1000";
//        String token = encode(msg);
//        decode(token);
        try {
            System.out.println(MauthUtil.decode("fa024d25cdd182b8a0f0e2a6af1937e7e7aafe9780095252b219a7bbceccc129"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String encode(String msg){
        try {
            //生成对称算法
            Cipher cipher = Cipher.getInstance("AES");
            //将自定义密匙字节数据,转换为“AES” 要求的128位(16字节)的密匙字节数据
            String buffer = new String(Base64.decodeBase64(metaKey));
            byte[] keyStr = toBytes(buffer);

            //定义密匙
            SecretKeySpec secretKey = new SecretKeySpec(keyStr, "AES");

            //设置算法为加密模式，并且设置密匙
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] data = msg.getBytes("UTF-8");
            //原数据加密，并且返回加密数据
            byte[] encryptData = cipher.doFinal(data);

            return toHexString(encryptData);
        }catch (Exception e){
            logger.error("加密失败msg:{}",e);
        }
        return null;
    }

    public static String encode(String msg, String secretKeyStr){
        try {
            //生成对称算法
            Cipher cipher = Cipher.getInstance("AES");
            //将自定义密匙字节数据,转换为“AES” 要求的128位(16字节)的密匙字节数据
            String buffer = new String(Base64.decodeBase64(secretKeyStr));
            byte[] keyStr = toBytes(buffer);

            //定义密匙
            SecretKeySpec secretKey = new SecretKeySpec(keyStr, "AES");

            //设置算法为加密模式，并且设置密匙
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] data = msg.getBytes("UTF-8");
            //原数据加密，并且返回加密数据
            byte[] encryptData = cipher.doFinal(data);

            return toHexString(encryptData);
        }catch (Exception e){
            logger.error("加密失败msg:{}",e);
        }
        return null;
    }

    public static String decode(String msg) throws Exception{
        try {
            Cipher ecipher = Cipher.getInstance("AES");
            String buffer = new String(Base64.decodeBase64(metaKey));
            byte[] keyStr = toBytes(buffer);
            SecretKeySpec aesKey = new SecretKeySpec(keyStr, "AES");
            ecipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] bytes = ecipher.doFinal(toBytes(msg));
            return new String(bytes,"UTF-8");
        }catch (Exception e){
            logger.error("解密失败msg:{}", e);
        }
        return null;
    }

    public static String decode(String msg, String secretKeyStr) throws Exception{
        try {
            Cipher ecipher = Cipher.getInstance("AES");
            String buffer = new String(Base64.decodeBase64(secretKeyStr));
            byte[] keyStr = toBytes(buffer);
            SecretKeySpec aesKey = new SecretKeySpec(keyStr, "AES");
            ecipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] bytes = ecipher.doFinal(toBytes(msg));
            return new String(bytes,"UTF-8");
        }catch (Exception e){
            logger.error("解密失败msg:{}", e);
        }
        return null;
    }

    /**
     * 据高智说这样特殊处理的原因是为了兼容ios
     *
     * @param s
     * @return
     */
    static final byte[] toBytes(String s) {
        byte[] bytes;
        bytes = new byte[s.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),
                    16);
        }
        return bytes;
    }

    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEXCHAR[(b[i] & 0xf0) >>> 4]);
            sb.append(HEXCHAR[b[i] & 0x0f]);
        }
        return sb.toString();
    }

}


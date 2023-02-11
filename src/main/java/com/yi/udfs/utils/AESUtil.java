package com.yi.udfs.utils;

import io.airlift.slice.Slice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @author YI
 * @description AES 加解密
 * @date create in 2021/8/17 10:32
 */
public class AESUtil {
    private static Logger logger = LoggerFactory.getLogger(AESUtil.class);

    /**
     * 加密
     *
     * @param sSrc 待加密字符串
     * @param sKey 密钥
     * @return 加密后的字符串
     * @throws Exception
     */
    public static String Encrypt(String sSrc, Slice sKey) throws Exception {
        if (sKey == null || sKey.length() != 16) {
            logger.error("Key为空null或者Key长度不是16位！");
            return "null";
        }

        // 不加密
        if (StringUtil.empty(sSrc)) {
            return sSrc;
        }

        byte[] raw = sKey.toStringUtf8().getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        //"算法/模式/补码方式"
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(StandardCharsets.UTF_8));
        //此处使用BASE64做转码功能，同时能起到2次加密的作用。
        return new BASE64Encoder().encode(encrypted);
    }

    /**
     * 解密
     *
     * @param sSrc 待解密字符串
     * @param sKey 密钥
     * @return 解密字符串
     */
    public static String Decrypt(String sSrc, Slice sKey) {
        try {
            if (sKey == null || sKey.length() != 16) {
                logger.error("Key为空null或者Key长度不是16位！");
                return "null";
            }

            byte[] raw = sKey.toStringUtf8().getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            //"算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            //先用base64解密
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);

            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            logger.error("加密失败：{}", ex.getMessage());
        }

        return sSrc;
    }
}

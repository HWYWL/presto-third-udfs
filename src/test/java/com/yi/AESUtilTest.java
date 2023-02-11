package com.yi;

import com.yi.udfs.utils.AESUtil;
import io.airlift.slice.Slice;
import io.airlift.slice.Slices;
import org.junit.Test;

/**
 * @author YI
 * @description
 * @date create in 2021/8/17 10:43
 */
public class AESUtilTest {
    /*
     * 此处使用AES-128-ECB加密模式，key需要为16位。
     */
    private static final String cKey = "J4NwD#*rLq!fzo&j";
    // 需要加密的字串
    private static final String cSrc = "金科文化";

    @Test
    public void encryptTest() throws Exception {
        Slice slice = Slices.utf8Slice(cKey);
        // 加密
        String enString = AESUtil.Encrypt(cSrc, slice);
        System.out.println("加密后的字串是：" + enString);
    }

    @Test
    public void decryptTest() {
        String enString = "V8oKxjKtVrDkTce7TODww==";
        Slice slice = Slices.utf8Slice(cKey);
        // 解密
        String DeString = AESUtil.Decrypt(enString, slice);
        System.out.println("解密后的字串是：" + DeString);
    }
}

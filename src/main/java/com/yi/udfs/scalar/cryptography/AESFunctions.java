package com.yi.udfs.scalar.cryptography;

import com.facebook.presto.spi.function.Description;
import com.facebook.presto.spi.function.ScalarFunction;
import com.facebook.presto.spi.function.SqlType;
import com.facebook.presto.spi.type.StandardTypes;
import com.yi.udfs.utils.AESUtil;
import com.yi.udfs.utils.StringUtil;
import io.airlift.slice.Slice;
import io.airlift.slice.Slices;

/**
 * @author YI
 * @description 使用AES加密解密 AES-128-ECB加密
 * @date create in 2021/8/17 11:00
 */
public class AESFunctions {
    public AESFunctions() {
    }

    @Description("aes选择加密")
    @ScalarFunction("aes_choose_encrypt")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice aes_choose_encrypt(@SqlType(StandardTypes.VARCHAR) Slice cSrc, @SqlType(StandardTypes.VARCHAR) Slice cKey) throws Exception {
        if (cSrc == null || StringUtil.empty(cSrc.toStringUtf8())) {
            return cSrc;
        }

        String enString = cSrc.toStringUtf8();
        // 解密
        String deString = AESUtil.Decrypt(cSrc.toStringUtf8(), cKey);
        if (enString.equals(deString)) {
            // 加密
            enString = AESUtil.Encrypt(cSrc.toStringUtf8(), cKey);
        }

        return Slices.utf8Slice(enString);
    }

    @Description("aes加密")
    @ScalarFunction("aes_encrypt")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice aesEncrypt(@SqlType(StandardTypes.VARCHAR) Slice cSrc, @SqlType(StandardTypes.VARCHAR) Slice cKey) throws Exception {
        if (cSrc == null || StringUtil.empty(cSrc.toStringUtf8())) {
            return cSrc;
        }

        // 加密
        String enString = AESUtil.Encrypt(cSrc.toStringUtf8(), cKey);
        return Slices.utf8Slice(enString);
    }

    @Description("aes解密")
    @ScalarFunction("aes_decrypt")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice aesDecrypt(@SqlType(StandardTypes.VARCHAR) Slice cSrc, @SqlType(StandardTypes.VARCHAR) Slice cKey) {
        if (cSrc == null || StringUtil.empty(cSrc.toStringUtf8())) {
            return cSrc;
        }
        // 解密
        String deString = AESUtil.Decrypt(cSrc.toStringUtf8(), cKey);
        return Slices.utf8Slice(deString);
    }
}

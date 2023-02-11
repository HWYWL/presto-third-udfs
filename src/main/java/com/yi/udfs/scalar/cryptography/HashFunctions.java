package com.yi.udfs.scalar.cryptography;

import com.facebook.presto.spi.function.Description;
import com.facebook.presto.spi.function.ScalarFunction;
import com.facebook.presto.spi.function.SqlType;
import com.facebook.presto.spi.type.StandardTypes;
import io.airlift.slice.Slice;
import io.airlift.slice.Slices;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author YI
 * @deprecated hash计算
 * date create in 2021-8-17 10:18:31
 */
public class HashFunctions {
    public HashFunctions() {
    }

    @Description("md5 hash")
    @ScalarFunction("md5")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice md5(@SqlType(StandardTypes.VARCHAR) Slice string) {
        return Slices.utf8Slice(DigestUtils.md5Hex(string.toStringUtf8()));
    }

    @Description("sha256 hash")
    @ScalarFunction("sha256")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice sha256(@SqlType(StandardTypes.VARCHAR) Slice string) {
        return Slices.utf8Slice(DigestUtils.sha256Hex(string.toStringUtf8()));
    }
}

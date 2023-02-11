package com.yi.udfs.utils;

import org.apache.commons.codec.binary.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author YI
 * date create 2021-8-17 10:27:33
 */
public class StringUtil extends StringUtils {

    /**
     * @param str 字符串判空
     * @return
     */
    public static boolean empty(String str) {
        if (str == null) {
            return true;
        }
        if ("".equals(str.trim())) {
            return true;
        }
        if ("null".equals(str.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串除了正常判空外，是否还为0
     *
     * @param str 字符串判空
     * @return
     */
    public static boolean zeroEmpty(String str) {

        return empty(str) || "0".equals(str);
    }

    public static int getFromIndex(String str, String modelStr, Integer count) {
        //对子字符串进行匹配
        Matcher slashMatcher = Pattern.compile(modelStr).matcher(str);
        int index = 0;
        //matcher.find();尝试查找与该模式匹配的输入序列的下一个子序列
        while (slashMatcher.find()) {
            index++;
            //当modelStr字符第count次出现的位置
            if (index == count) {
                break;
            }
        }
        //matcher.start();返回以前匹配的初始索引。
        return slashMatcher.start();
    }

    private static class OneLinePrintWriter extends PrintWriter {
        public OneLinePrintWriter(Writer out) {
            super(out);
        }

        @Override
        public void println() {
            //原本是打印换行符，改为打印自定的标记符
            print(" |");
        }
    }
}

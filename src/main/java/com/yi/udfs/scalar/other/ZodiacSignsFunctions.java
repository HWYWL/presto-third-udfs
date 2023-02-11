package com.yi.udfs.scalar.other;

import com.facebook.presto.spi.function.Description;
import com.facebook.presto.spi.function.ScalarFunction;
import com.facebook.presto.spi.function.SqlType;
import com.facebook.presto.spi.type.StandardTypes;
import com.yi.udfs.model.Language;
import com.yi.udfs.utils.StringUtil;
import io.airlift.slice.Slice;
import io.airlift.slice.Slices;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;

import static java.util.concurrent.TimeUnit.DAYS;

/**
 * @author YI
 * @deprecated 根据日期展示星座
 * date create in 2021/8/17 9:43
 */
public class ZodiacSignsFunctions {
    public ZodiacSignsFunctions() {
    }

    public final static DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

    private static final String[] zodiacCnArray = {"魔羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座"};
    private static final String[] zodiacEnArray = {"Capricorn", "Aquarius", "Pisces", "Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius"};

    @ScalarFunction("zodiac_cn")
    @Description("从输入的日期字符串或单独的月和日参数，返回生肖的中文字符串(中文).")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice getZodiacSignCn(@SqlType(StandardTypes.VARCHAR) Slice string) {
        if (string == null || StringUtil.empty(string.toStringUtf8())) {
            return null;
        }

        try {
            LocalDate date = LocalDate.parse(string.toStringUtf8(), DEFAULT_DATE_FORMATTER);
            String zodiac = getZodiac(date.getMonthOfYear(), date.getDayOfMonth(), Language.CN);
            return Slices.utf8Slice(zodiac);
        } catch (Exception e) {
            return null;
        }
    }

    @ScalarFunction("zodiac_cn")
    @Description("输入毫秒时间戳，返回星座信息(中文).")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice getZodiacSignCn(@SqlType(StandardTypes.DATE) long t) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(DAYS.toMillis(t));
            LocalDate date = LocalDate.fromCalendarFields(calendar);
            String zodiac = getZodiac(date.getMonthOfYear(), date.getDayOfMonth(), Language.CN);
            return Slices.utf8Slice(zodiac);
        } catch (Exception e) {
            return null;
        }
    }

    @ScalarFunction("zodiac_en")
    @Description("输入日期，返回星座信息(英文).")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice getZodiacSignEn(@SqlType(StandardTypes.VARCHAR) Slice string) {
        if (string == null || StringUtil.empty(string.toStringUtf8())) {
            return null;
        }

        try {
            LocalDate date = LocalDate.parse(string.toStringUtf8(), DEFAULT_DATE_FORMATTER);
            String zodiac = getZodiac(date.getMonthOfYear(), date.getDayOfMonth(), Language.EN);
            return Slices.utf8Slice(zodiac);
        } catch (Exception e) {
            return null;
        }
    }

    @ScalarFunction("zodiac_en")
    @Description("输入毫秒时间戳，返回星座信息(英文).")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice getZodiacSignEn(@SqlType(StandardTypes.DATE) long t) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(DAYS.toMillis(t));
            LocalDate date = LocalDate.fromCalendarFields(calendar);
            String zodiac = getZodiac(date.getMonthOfYear(), date.getDayOfMonth(), Language.EN);
            return Slices.utf8Slice(zodiac);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断所属星座类型
     *
     * @param month    月份
     * @param day      日
     * @param language 语言
     * @return 星座
     */
    private static String getZodiac(int month, int day, Language language) {
        int[] splitDay = {19, 18, 20, 20, 20, 21, 22, 22, 22, 22, 21, 21};
        int index = month;
        // 如果日期在分割日之前，idx -1;否则idx未改变
        if (day <= splitDay[month - 1]) {
            index = index - 1;
        } else if (month == 12) {
            index = 0;
        }
        // 根据语言返回星座信息
        if (language == Language.CN) {
            return zodiacCnArray[index];
        } else {
            return zodiacEnArray[index];
        }
    }
}

package org.zephyr.orm.util;

import com.google.common.base.CaseFormat;

/**
 * @author yu.zhang
 * @date 2019-09-11
 *
 * 这里的实现思路可以分析下（大致上与自己写的那个意思一样，但是guava的实现明显更强大）
 */
public class StringUtils {
    public static final String COMMON_SPLITOR = "_";

    public static String convertToUpperCamel(String origin) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, origin);
    }

    public static String convertToUnderScore(String origin) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, origin);
    }

    public static String convertToLowerCamel(String origin) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, origin);
    }

    private static String convertToCamel(String origin, boolean startWithUpper) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] s = origin.split(COMMON_SPLITOR);
        if (startWithUpper) {
            stringBuilder.append(s[0].replace(s[0].substring(0, 1), s[0].substring(0, 1).toUpperCase()));
        } else {
            stringBuilder.append(s[0].replace(s[0].substring(0, 1), s[0].substring(0, 1).toLowerCase()));
        }
        for (int i = 1; i < s.length; i++) {
            stringBuilder.append(s[i].replace(s[i].substring(0, 1), s[i].substring(0, 1).toUpperCase()));
        }
        return stringBuilder.toString();
    }

}

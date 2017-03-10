package com.usebilbo.vertx.util;

import static com.usebilbo.vertx.util.Utils.coalesce;
import static com.usebilbo.vertx.util.Utils.isEmpty;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public final class CvtUtils {
    private static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();
    private static final Pattern EMAIL = Pattern.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
                                                         + "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+"
                                                         + "(?:[a-z]{2}|com|org|net|edu|gov|mil|biz|info|mobi|name|aero|asia|jobs|museum)\\b",
                                                         Pattern.CASE_INSENSITIVE);
    private static final Set<String> BOOL_TRUE = new HashSet<>(Arrays.asList("true", "yes", "on"));

    private CvtUtils() {}

    public static int toInt(String text, int defaultValue) {
        if (isEmpty(text)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static long toLong(String text, long defaultValue) {
        if (isEmpty(text)) {
            return defaultValue;
        }
        try {
            return Long.parseLong(text);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static long abbreviatedToLong(String text) {
        String src = coalesce(text, "").toUpperCase();
        StringBuilder digits = new StringBuilder();
        long multiplier = 1;

        for (int i = 0; i < src.length(); i++) {
            char chr = src.charAt(i);
            if (Character.isDigit(chr)) {
                digits.append(chr);
                continue;
            }

            switch (chr) {
                case 'K':
                    multiplier = 1024L;
                    break;
                case 'M':
                    multiplier = 1024L * 1024;
                    break;
                case 'G':
                    multiplier = 1024L * 1024 * 1024;
                    break;
                case 'T':
                    multiplier = 1024L * 1024 * 1024 * 1024;
                    break;
            }
            break;
        }

        return toLong(digits.toString(), 0) * multiplier;
    }

    public static long abbreviatedTimeToLong(String text) {
        String src = coalesce(text, "").toUpperCase();
        StringBuilder digits = new StringBuilder();
        long multiplier = 1;
        long sign = 1L;

        for (int i = 0; i < src.length(); i++) {
            char chr = src.charAt(i);
            if (digits.length() == 0 && chr == '-') {
                sign = -1L;
                continue;
            }

            if (Character.isDigit(chr)) {
                digits.append(chr);
                continue;
            }

            switch (chr) {
                case 'S':
                    multiplier = TimeUnit.SECONDS.toMillis(1);
                    break;
                case 'M':
                    multiplier = TimeUnit.MINUTES.toMillis(1);
                    break;
                case 'H':
                    multiplier = TimeUnit.HOURS.toMillis(1);
                    break;
                case 'D':
                    multiplier = TimeUnit.DAYS.toMillis(1);
                    break;
            }
            break;
        }

        return sign * toLong(digits.toString(), 0) * multiplier;
    }

    public static String toHex(int source) {
        char[] hexChars = new char[8];

        hexChars[0] = HEX_DIGITS[(source >>> 28) & 0x0F];
        hexChars[1] = HEX_DIGITS[(source >>> 24) & 0x0F];
        hexChars[2] = HEX_DIGITS[(source >>> 20) & 0x0F];
        hexChars[3] = HEX_DIGITS[(source >>> 16) & 0x0F];
        hexChars[4] = HEX_DIGITS[(source >>> 12) & 0x0F];
        hexChars[5] = HEX_DIGITS[(source >>>  8) & 0x0F];
        hexChars[6] = HEX_DIGITS[(source >>>  4) & 0x0F];
        hexChars[7] = HEX_DIGITS[ source         & 0x0F];

        return new String(hexChars);
    }

    public static String toHex(long source) {
        char[] hexChars = new char[16];

        hexChars[ 0] = HEX_DIGITS[(int)((source >>> 60) & 0x0F)];
        hexChars[ 1] = HEX_DIGITS[(int)((source >>> 56) & 0x0F)];
        hexChars[ 2] = HEX_DIGITS[(int)((source >>> 52) & 0x0F)];
        hexChars[ 3] = HEX_DIGITS[(int)((source >>> 48) & 0x0F)];
        hexChars[ 4] = HEX_DIGITS[(int)((source >>> 44) & 0x0F)];
        hexChars[ 5] = HEX_DIGITS[(int)((source >>> 40) & 0x0F)];
        hexChars[ 6] = HEX_DIGITS[(int)((source >>> 36) & 0x0F)];
        hexChars[ 7] = HEX_DIGITS[(int)((source >>> 32) & 0x0F)];
        hexChars[ 8] = HEX_DIGITS[(int)((source >>> 28) & 0x0F)];
        hexChars[ 9] = HEX_DIGITS[(int)((source >>> 24) & 0x0F)];
        hexChars[10] = HEX_DIGITS[(int)((source >>> 20) & 0x0F)];
        hexChars[11] = HEX_DIGITS[(int)((source >>> 16) & 0x0F)];
        hexChars[12] = HEX_DIGITS[(int)((source >>> 12) & 0x0F)];
        hexChars[13] = HEX_DIGITS[(int)((source >>>  8) & 0x0F)];
        hexChars[14] = HEX_DIGITS[(int)((source >>>  4) & 0x0F)];
        hexChars[15] = HEX_DIGITS[(int)(source          & 0x0F)];

        return new String(hexChars);
    }

    public static String toHex(String prefix, byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_DIGITS[v >>> 4];
            hexChars[j * 2 + 1] = HEX_DIGITS[v & 0x0F];
        }
        String result = new String(hexChars);
        return (prefix == null) ? result : new StringBuilder(prefix).append(result).toString();
    }

    public static boolean validateEmail(String email) {
        if (Utils.isEmpty(email)) {
            return false;
        }
        return EMAIL.matcher(email).matches();
    }

    public static boolean toBool(String source, boolean defaultValue) {
        return BOOL_TRUE.contains(Utils.coalesce(source, "").toLowerCase(Locale.US));
    }
}

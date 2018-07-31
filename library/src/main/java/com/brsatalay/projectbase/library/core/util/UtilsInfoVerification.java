package com.brsatalay.projectbase.library.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilsInfoVerification {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean emailCheck(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }


    public static boolean creditCardCheck(String str) {

        int[] ints = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            ints[i] = Integer.parseInt(str.substring(i, i + 1));
        }
        for (int i = ints.length - 2; i >= 0; i = i - 2) {
            int j = ints[i];
            j = j * 2;
            if (j > 9) {
                j = j % 10 + 1;
            }
            ints[i] = j;
        }
        int sum = 0;
        for (int anInt : ints) {
            sum += anInt;
        }
        return sum % 10 == 0;
    }


    private static boolean tckCheck(Long tckn) {
        try {
            String tmp = tckn.toString();

            if (tmp.length() == 11) {
                int totalOdd = 0;

                int totalEven = 0;

                for (int i = 0; i < 9; i++) {
                    int val = Integer.valueOf(tmp.substring(i, i + 1));

                    if (i % 2 == 0) {
                        totalOdd += val;
                    } else {
                        totalEven += val;
                    }
                }

                int total = totalOdd + totalEven + Integer.valueOf(tmp.substring(9, 10));

                int lastDigit = total % 10;

                if (tmp.substring(10).equals(String.valueOf(lastDigit))) {
                    int check = (totalOdd * 7 - totalEven) % 10;

                    if (tmp.substring(9, 10).equals(String.valueOf(check))) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {

        }

        return false;
    }
}

package com.brsatalay.projectbase.library.core.util;

import java.text.DecimalFormat;

/**
 * Created by barisatalay on 22.03.2018.
 */

public class UtilsString {
    public static String addStr(String baseString, String newValue, String seperetor){
        String result=baseString;
        if(!result.isEmpty() && !newValue.isEmpty())
            result += seperetor;
        result += newValue;
        return result;
    }

    public static String QuotedStr(String value){
        return "'"+value+"'";
    }

    public static String wrapText(String oldText, int length){
        if(oldText == null || oldText.isEmpty())
            return "";

        if(oldText.length() <= length){
            return oldText;
        }

        return oldText.substring(0, length - 1) + "...";
    }

    public static String decimalToStr(double decimalValue){
        if (decimalValue == 0)
            return "";
        DecimalFormat df = new DecimalFormat("#,###.00");

        return df.format(decimalValue);
    }

//    private String DecimalToString(double decimalValue) {
//        return decimalValue == 0 ? "" : String.valueOf(decimalValue);
//    }
}

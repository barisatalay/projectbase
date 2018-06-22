package com.brsatalay.projectbase.library.core.util;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by barisatalay on 20.04.2018.
 */

public class UtilsHtml {
    public static Spanned textToHtml(String pValue){
        if (android.os.Build.VERSION.SDK_INT < 25){
            return Html.fromHtml(pValue);
        } else{
            return Html.fromHtml(pValue, Html.FROM_HTML_MODE_COMPACT);
        }
    }

    /**
     * <br /> gördüğü yerleri alt satıra atacaktır.
     *
     */
    public static String brToLB(String oldStr, int addEndLineCount){
        if(oldStr == null || oldStr.isEmpty()) return "";

        StringBuffer stringBuffer = new StringBuffer();
        String seperatorItem = "<br />";
        String Temp = oldStr.replace("<br/>",seperatorItem);

        if(oldStr.indexOf(seperatorItem) > -1){
            while(Temp.indexOf(seperatorItem) > -1){
                int endIndex = Temp.indexOf(seperatorItem);// + seperatorItem.length();
                String copyStr = Temp.substring(0, endIndex);
                if(!copyStr.isEmpty())
                    stringBuffer.append(copyStr).append("\n");
                Temp = Temp.substring(copyStr.length() + seperatorItem.length(), Temp.length());
            }
//            stringBuffer.append(waitForSend);

        }else
            stringBuffer.append(oldStr);

        for(int i=0;i<addEndLineCount;i++){
            stringBuffer.append("\n");
        }

        //5 Satır boşluk bıraktım
        return stringBuffer.toString();
    }

    public static String clearHtmlTags(String oldStr){
        if (oldStr == null || oldStr.length() == 0) {
            return oldStr;
        }


        Pattern REMOVE_TAGS = Pattern.compile("<[^>]*>");
//        Pattern REMOVE_TAGS = Pattern.compile("<.+?>");

        Matcher m = REMOVE_TAGS.matcher(oldStr);
        return m.replaceAll("");
    }

    public static String HtmlDecode(String oldStr){

        if(oldStr == null || oldStr.isEmpty()) return "";

        String result = oldStr;

        result = result.replace("#015;","");
        result = result.replace("#012;","");
        result = result.replace("&amp;","&");
        result = result.replace("&","{AND}");
        result = TextUtils.htmlEncode(result);
        result = result.replace("{AND}","&");
        return result;
    }

    public static String runAllHtmMethod(String oldText){
        String newStr = oldText;

        if (newStr == null || newStr.length() == 0) {
            return newStr;
        }

        newStr = clearHtmlTags(newStr);
        newStr = HtmlDecode(newStr);

        return newStr;
    }
}

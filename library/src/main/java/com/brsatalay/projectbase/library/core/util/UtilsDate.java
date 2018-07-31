package com.brsatalay.projectbase.library.core.util;


import com.brsatalay.projectbase.library.core.data.constant.FormatSettings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by barisatalay on 10.04.2018.
 */

public class UtilsDate {
    public static String getDateTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String getDateTime(String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    public static Date getStrToDateTime(String strDate){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String tempDateStr = strDate;
        if(tempDateStr.length()=="dd/MM/yyyy".length()) tempDateStr = tempDateStr + " 00:00:00";

        try {
            return format.parse(tempDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getStrToDateTime(String strDate, String dateFormat){
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        try {
            return format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String changeStrDatePattern(String dateStr, String oldPattern, String newPattern){
        Date date = getStrToDateTime(dateStr, oldPattern);
        return getDateToStr(date, newPattern);
    }

    public static Calendar dateToCalendar(Date value){
        Calendar cal = Calendar.getInstance();
        cal.setTime(value);
        return cal;
    }

    public static Date getStrToDate(String strDate){
        return getStrToDate(strDate, "dd/MM/yyyy");
    }

    public static Date getStrToDate(String strDate, String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDate(String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    public static String getDate(String format, int addDay){
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Calendar c = dateToCalendar(new Date());
        c.add(Calendar.DATE, addDay);

        return sdf.format(c.getTime());
    }

    public static Calendar getCalendarDate(String format, int addDay){
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Calendar c = dateToCalendar(new Date());
        c.add(Calendar.DATE, addDay);

        return c;
    }

    public static String addZeroMount(String value){
        return value.length()==1?"0"+value:value;
    }

    public static String getTime(String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    public static long dateToLong(String date, String format) {
        return dateToCalendar(getStrToDate(date)).getTimeInMillis();
    }

    public static String getDateToStr(Date tarih, String format) {
        if(tarih == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(tarih);
    }

    public static String convertSqlDateToParam(String date, String format){
        SimpleDateFormat sqlFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat newFormat = new SimpleDateFormat(format);
        try {
            return newFormat.format(sqlFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String onlyDateSql(String value){
        if(value.isEmpty()) return "";
        if(value.equalsIgnoreCase("1899-12-30T23:59:00")) return "";

        String d1 = value.substring(0,value.indexOf("T"));
        SimpleDateFormat format = new SimpleDateFormat(FormatSettings.SqlDate);
        SimpleDateFormat newFormat = new SimpleDateFormat(FormatSettings.ShortDateFormat);

        Date date = null;
        try {
            date = format.parse(d1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");

//        return newFormat.format(date);
        return newFormat.format(date);
    }

    public static String onlyTimeSql(String value){
        if(value.isEmpty()) return "";
        if(value.equalsIgnoreCase("1899-12-30T23:59:00")) return "";

        String newTime = value.substring(value.indexOf("T") + 1, (value.indexOf("T") + 1) + 5);

        return newTime;
    }

    public static String addZeroIfSingle(String value) {
        return value.length()==1?"0"+value:value;
    }
}

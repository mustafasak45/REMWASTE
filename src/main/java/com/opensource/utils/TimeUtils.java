package com.opensource.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {


    public static String getTime(String format){

        return DateTime.now()//.withZone(DateTimeZone.UTC)
                .toString(format);//"dd/MM/yyyy HH:mm:ss,SSS");
    }
    public static String getDateTimeFormat(){

        return DateTime.now()//.withZone(DateTimeZone.UTC)
                .toString();//"dd/MM/yyyy HH:mm:ss,SSS");
    }

    public static long getTimeMillis(){

        DateTime utc = new DateTime();
        return utc.getMillis();
    }

    public static long currentTimeMillis(){

        return System.currentTimeMillis();
    }

    public static Long getTimeMillisFromTime(String time, String format){

        return org.joda.time.LocalDateTime.parse(time, DateTimeFormat.forPattern(format))
                .toDateTime().getMillis();
    }

    public static DateTime getDateTimeFromTime(String time, String format, String language, int forOffsetHours){

        return org.joda.time.LocalDateTime.parse(time, DateTimeFormat.forPattern(format).withLocale(Locale.forLanguageTag(language)))
                .toDateTime(DateTimeZone.forOffsetHours(forOffsetHours));
    }

    public static Long getTimeMillisFromTime(String time, String format, int forOffsetHours){

        return org.joda.time.LocalDateTime.parse(time, DateTimeFormat.forPattern(format))
                .toDateTime(DateTimeZone.forOffsetHours(forOffsetHours)).getMillis();
    }

    public static Long getTimeMillisFromTime(String time, String format, String language, int forOffsetHours){

        return org.joda.time.LocalDateTime.parse(time, DateTimeFormat.forPattern(format).withLocale(Locale.forLanguageTag(language)))
                .toDateTime(DateTimeZone.forOffsetHours(forOffsetHours)).getMillis();
    }

    public static Long getTimeMillisFromTime(String time, String format, String zoneID){

        return org.joda.time.LocalDateTime.parse(time, DateTimeFormat.forPattern(format))
                .toDateTime(DateTimeZone.forID(zoneID)).getMillis();
    }

    public static String getTimeFromMillis(String format, long millis){

        DateTime dateTime = new DateTime(millis);
        return dateTime.toString(format);
    }

    public static String getTimeFromMillisPlus(String format, long millis, int year, int month
            , int day, int hour, int minute, int second, int millisecond){

        DateTime dateTime = new DateTime(millis);
        return dateTime.plusYears(year).plusMonths(month).plusDays(day).plusHours(hour)
                .plusMinutes(minute).plusSeconds(second).plusMillis(millisecond).toString(format);
    }

    public static String getTimeFromMillisPlus(String format, long millis, int year, int month
            , int day, int hour, int minute, int second, int millisecond, String forOffsetHours, String language){

        DateTime dateTime = null;
        if (forOffsetHours.equals("null")){
            dateTime = new DateTime(millis);
        }else {
            dateTime = new DateTime(millis, DateTimeZone.forOffsetHours(Integer.parseInt(forOffsetHours)));
        }
        dateTime = dateTime.plusYears(year).plusMonths(month).plusDays(day).plusHours(hour)
                .plusMinutes(minute).plusSeconds(second).plusMillis(millisecond);
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(format);
        //"EEE, dd MMM yyyy HH:mm:ss"
        //"dd/MM/yyyy HH:mm:ss,SSS"
        String time = "";
        if (language.equals("null")){
            time = dateTimeFormatter.withLocale(Locale.getDefault()).print(dateTime);
        }else {
            time = dateTimeFormatter.withLocale(Locale.forLanguageTag(language)).print(dateTime);
        }
        return time;
    }

    public static String getTimeFromMillis(String format, long millis, int forOffsetHours){

        DateTime dateTime = new DateTime(millis, DateTimeZone.forOffsetHours(forOffsetHours));
        return dateTime.toString(format);
    }

    public static String getTimeFromMillisWithZoneId(String format, long millis, String zoneID){

        DateTime dateTime = new DateTime(millis, DateTimeZone.forID(zoneID));
        return dateTime.toString(format);
    }

    public static String getTimeWithZoneId(String format, String zoneID){

        //"Europe/Istanbul"
        return DateTime.now(DateTimeZone.forID(zoneID))
                .toString(format);
    }

    public static String getTimeWithForOffsetHours(String format, int forOffsetHours){

        return DateTime.now(DateTimeZone.forOffsetHours(forOffsetHours))
                .toString(format);
    }

    public Instant getTimeToInstant(int yearAmount){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, yearAmount);
        return calendar.toInstant();
    }

    public Date getTimeDate(int yearAmount){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, yearAmount);
        return calendar.getTime();
    }

    //  İstenilen tarihi istenilen formatta veren ve gün sayısı verilerek ileri tarihli tarih döndüren metod
    public static String getDateWithFormat(int daysOffset, String format) {
        return LocalDate.now().plusDays(daysOffset).format(java.time.format.DateTimeFormatter.ofPattern(format));
    }
}

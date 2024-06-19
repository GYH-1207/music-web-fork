package com.javaclimb.music.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeParser {

    public static String parseLocalDateTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日\nHH:mm:ss");
        return time.format(formatter);
    }

    public static String parseDate(Date time) {
        LocalDateTime localDateTime = time.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return parseLocalDateTime(localDateTime);
    }

    public static String parseDateTime(Object time) {
        if(time == null) {
            return null;
        } else if (time instanceof LocalDateTime) {
            return parseLocalDateTime((LocalDateTime) time);
        } else if (time instanceof Date) {
            return parseDate((Date) time);
        } else {
            return "需要LocalDateTime或Date类型";
        }
    }

    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = new Date();

        System.out.println(parseDateTime(localDateTime));
        System.out.println(parseDateTime(date));
    }
}
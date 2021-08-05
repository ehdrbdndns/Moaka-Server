package com.moaka.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Time에 관련된 Class
 */
public class Time {

    /**
     * @return 현재 시간 FORM에 맞춰 리턴
     * "yyyy-MM-dd HH:mm:ss"
     * "yyyy년 MM월dd일 HH시mm분ss초"
     * "yyyy_MM_dd_HH_mm_ss"
     */
    public static String TimeFormatter(String format) {
        Date now = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(now);
    }

    /**
     * @return 현재 시간 + 1day를 늘려서 Stamp로 리턴
     * 현재 Stamp에서 하루를 늘린 Stamp
     */
    public static Date LongTimeStamp() {
        Date currentDate = new Date();
        System.out.println(currentDate);
        // convert date to calendar
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        // manipulate date
        c.add(Calendar.YEAR, 0);
        c.add(Calendar.MONTH, 0);
        c.add(Calendar.DATE, 1); //same with c.add(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.HOUR, 0);
        c.add(Calendar.MINUTE, 0);
        c.add(Calendar.SECOND, 0);
        // convert calendar to date
        Date currentDatePlusOne = c.getTime();
        return currentDatePlusOne;
    }
}

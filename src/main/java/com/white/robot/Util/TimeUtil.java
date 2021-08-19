package com.white.robot.Util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getDay() {
        return Calendar.getInstance().get(Calendar.DATE);
    }

    public static int getMin() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    public static int getSec() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }

    public static String getToday() {
        return getYear() + String.valueOf(getMonth()) + getDay();
    }

    public static String getLastToday() {
        String last = String.valueOf(getDay() - 1);
        return getYear() + String.valueOf(getMonth()) + last;
    }

    public static Timestamp getNowTimestamp() {
        return new Timestamp(new Date().getTime());
    }
}

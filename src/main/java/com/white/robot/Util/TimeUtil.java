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

    public static boolean monthJudge(int month) {
        return month == 1 || month == 3 || month == 5
                || month == 7 || month == 8 || month == 10 || month == 12;
    }

    public static String getLastToday() {

        String month = String.valueOf(getMonth());
        String last="1";
        if (getDay() != 1) {
            last = String.valueOf(getDay() - 1);
        } else if (getDay() == 1 && monthJudge(getMonth())) {
            last = "31";
            month = String.valueOf(getMonth() - 1);
        }
        else if (getDay() == 1 && !monthJudge(getMonth())) {
            last = "30";
            month = String.valueOf(getMonth() - 1);
        }
        return getYear() + month + last;
    }

    public static Timestamp getNowTimestamp() {
        return new Timestamp(new Date().getTime());
    }
}

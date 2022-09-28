package ru.simple.shop.task.testapp.util;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.util.Calendar;

@UtilityClass
public class DateUtil {

    public static Instant getSevenDayAgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        return calendar.toInstant();
    }

    public static Instant getSixMonthAgo() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -6);
        return cal.toInstant();
    }
}

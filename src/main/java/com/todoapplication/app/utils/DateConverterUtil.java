package com.todoapplication.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;
@Component
public class DateConverterUtil {
	public Date getDateFrom(String dateString) throws ParseException {
        // Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        return date;
    }

    public static Date getDateFrom(String dateString, String pattern) throws ParseException {
        Date date = new SimpleDateFormat(pattern).parse(dateString);
        return date;
    }

    public static boolean isSameDateAndTime(Date dateA, Date dateB) {
        return dateA.getTime() == dateB.getTime();

    }

    public static boolean dateWithinRange(Date testDate, Date startDate, Date endDate) {
        return (testDate.after(startDate) || testDate.equals(startDate))
                && (testDate.before(endDate) || testDate.equals(endDate));

    }

    @SuppressWarnings("deprecation")
	public static void resetTimeOfDate(Date date) {

        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);
        date.setTime(cal.getTime().getTime());

    }
    public static LocalDate toLocalDateFormat(Date startDate) {
        LocalDate localDateFormat = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDateFormat;
    }

    public static Date fromLocalDateFormat(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime toLocalDateTimeFormat(Date startDate) {
        LocalDateTime localDateTimeFormat = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTimeFormat;
    }

    public static Date fromLocalDateTimeFormat(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}

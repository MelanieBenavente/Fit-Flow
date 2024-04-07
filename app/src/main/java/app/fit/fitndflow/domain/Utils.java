package app.fit.fitndflow.domain;


import android.content.Context;

import com.fit.fitndflow.R;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import app.fit.fitndflow.data.dto.StringInLanguagesDto;

public class Utils {
    public static final String SPANISH = "es";
    private static final String ENGLISH_FORMAT = "yyyy/MM/dd";
    private static final String SPANISH_FORMAT = "dd/MM/yyyy";

    public static String getSpanishFormatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(SPANISH_FORMAT);
        return dateFormat.format(date);
    }

    public static String getEnglishFormatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(ENGLISH_FORMAT);
        return dateFormat.format(date);
    }

    public static StringInLanguagesDto convertToStringInLanguages(String language, String nameCategory) {
        StringInLanguagesDto stringInLanguagesDto;
        if (language.equals(SPANISH)) {
            stringInLanguagesDto = new StringInLanguagesDto(nameCategory, "");
        } else {
            stringInLanguagesDto = new StringInLanguagesDto("", nameCategory);
        }
        return stringInLanguagesDto;
    }

    public static String getCalendarFormatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(" dd, MMM");
        return dateFormat.format(date);
    }

    public static String dayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        String[] dayNames = new DateFormatSymbols().getShortWeekdays();

        return dayNames[dayOfWeek];
    }

    public static boolean isYesterday(Date date) {
        Calendar calYesterday = Calendar.getInstance();
        calYesterday.setTime(new Date());
        calYesterday.add(Calendar.DAY_OF_YEAR, -1);

        Calendar calFechaParametro = Calendar.getInstance();
        calFechaParametro.setTime(date);


        return calYesterday.get(Calendar.YEAR) == calFechaParametro.get(Calendar.YEAR)
                && calYesterday.get(Calendar.MONTH) == calFechaParametro.get(Calendar.MONTH)
                && calYesterday.get(Calendar.DAY_OF_YEAR) == calFechaParametro.get(Calendar.DAY_OF_YEAR);
    }

    public static boolean isToday(Date date) {
        Calendar calToday = Calendar.getInstance();
        calToday.setTime(new Date());

        Calendar calFechaParametro = Calendar.getInstance();
        calFechaParametro.setTime(date);


        return calToday.get(Calendar.YEAR) == calFechaParametro.get(Calendar.YEAR)
                && calToday.get(Calendar.MONTH) == calFechaParametro.get(Calendar.MONTH)
                && calToday.get(Calendar.DAY_OF_YEAR) == calFechaParametro.get(Calendar.DAY_OF_YEAR);
    }

    public static boolean isTomorrow(Date date) {
        Calendar calTomorrow = Calendar.getInstance();
        calTomorrow.setTime(new Date());
        calTomorrow.add(Calendar.DAY_OF_YEAR, +1);

        Calendar calFechaParametro = Calendar.getInstance();
        calFechaParametro.setTime(date);


        return calTomorrow.get(Calendar.YEAR) == calFechaParametro.get(Calendar.YEAR)
                && calTomorrow.get(Calendar.MONTH) == calFechaParametro.get(Calendar.MONTH)
                && calTomorrow.get(Calendar.DAY_OF_YEAR) == calFechaParametro.get(Calendar.DAY_OF_YEAR);
    }
}

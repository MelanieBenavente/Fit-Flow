package app.fit.fitndflow.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    private static final String SPANISH_FORMAT = "dd/MM/yyyy";
    public static String getSpanishFormatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(SPANISH_FORMAT);
        return dateFormat.format(date);
    }

    public static boolean isYesterday(Date date){
        Calendar calYesterday = Calendar.getInstance();
        calYesterday.setTime(new Date());
        calYesterday.add(Calendar.DAY_OF_YEAR, -1);

        Calendar calFechaParametro = Calendar.getInstance();
        calFechaParametro.setTime(date);


        return calYesterday.get(Calendar.YEAR) == calFechaParametro.get(Calendar.YEAR)
                && calYesterday.get(Calendar.MONTH) == calFechaParametro.get(Calendar.MONTH)
                && calYesterday.get(Calendar.DAY_OF_YEAR) == calFechaParametro.get(Calendar.DAY_OF_YEAR);
    }
    public static boolean isToday(Date date){
        Calendar calToday = Calendar.getInstance();
        calToday.setTime(new Date());

        Calendar calFechaParametro = Calendar.getInstance();
        calFechaParametro.setTime(date);


        return calToday.get(Calendar.YEAR) == calFechaParametro.get(Calendar.YEAR)
                && calToday.get(Calendar.MONTH) == calFechaParametro.get(Calendar.MONTH)
                && calToday.get(Calendar.DAY_OF_YEAR) == calFechaParametro.get(Calendar.DAY_OF_YEAR);
    }
    public static boolean isTomorrow(Date date){
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

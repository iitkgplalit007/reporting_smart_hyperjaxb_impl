package handlers;

import java.util.Calendar;
import java.util.Date;

public class DateHandler extends java.sql.Date{
    public DateHandler(long date) {
        super(date);
    }

    @Override
    public String toString(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return Integer.toString(year)+Integer.toString(month)+Integer.toString(day);
    }
}

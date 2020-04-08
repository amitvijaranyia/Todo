package com.example.todo.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAndTimeUtils {

    private DateAndTimeUtils(){}

    public static String getDateToDisplayInMainActivity(long timeInMilliSec){
        Date date = new Date(timeInMilliSec);

        SimpleDateFormat df = new SimpleDateFormat("HH:mm dd-MM-yy");

        return df.format(date);
    }

}

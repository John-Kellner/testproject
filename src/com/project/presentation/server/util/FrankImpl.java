package com.project.presentation.server.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by john on 13.11.2016.
 */
public class FrankImpl {
    int i = 0;
    private String value = "NotInUse";

    public void run(){
        for (int u=0; u<5; u++){
            i++;
        }

        final Calendar calendar = new GregorianCalendar();

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
    }

    public void setValue(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

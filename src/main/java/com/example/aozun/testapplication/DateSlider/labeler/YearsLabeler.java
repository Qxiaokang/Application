package com.example.aozun.testapplication.DateSlider.labeler;

import com.example.aozun.testapplication.DateSlider.TimeObject;

import java.util.Calendar;

/**
 * A Labeler that displays months
 */
public class YearsLabeler extends Labeler {
    private final String mFormatString;

    public YearsLabeler(String formatString) {
        super(200, 60);
        mFormatString = formatString;
    }

    @Override
    public TimeObject add(long time, int val) {
        return timeObjectfromCalendar(Util.addYears(time, val));
    }

    @Override
    protected TimeObject timeObjectfromCalendar(Calendar c) {
        return Util.getYears(c, mFormatString);
    }
}
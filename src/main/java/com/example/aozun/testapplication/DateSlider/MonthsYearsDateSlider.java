/*
 * Copyright (C) 2011 Daniel Berndt - Codeus Ltd  -  DateSlider
 *
 * DateSlider which allows for an easy selection of only a month and a year
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.aozun.testapplication.DateSlider;

import android.content.Context;

import com.example.aozun.testapplication.R;

import java.util.Calendar;

public class MonthsYearsDateSlider extends DateSlider {

	public MonthsYearsDateSlider(Context context, OnDateSetListener l, Calendar calendar) {
		this(context, l, calendar, null, null);
	}
    public MonthsYearsDateSlider(Context context, OnDateSetListener l, Calendar calendar, 
    		Calendar minDate, Calendar maxDate) {
        super(context, R.layout.monthsyearsdateslider, l, calendar, minDate, maxDate);
    }

    /**
     * override the setTitle method so that only the month and the year are shown.
     */
    @Override
    protected void setTitle() {
        if (mTitleText != null) {
            final Calendar c = getTime();
            mTitleText.setText("选择时间：" +String.format("%1$02d",c.get(Calendar.YEAR)-1900)+"年"+String.format("%02d",c.get(Calendar.MONTH))+"个月");
                    //String.format(": %tB %tY",c,c));
        }
    }

}

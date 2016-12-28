/*
 * Copyright (C) 2011 Daniel Berndt - Codeus Ltd  -  DateSlider
 *
 * Class for setting up the dialog and initialsing the underlying
 * ScrollLayouts
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

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.aozun.testapplication.R;

import java.util.Calendar;

/**
 * A Dialog subclass that hosts a SliderContainer and a couple of buttons,
 * displays the current time in the header, and notifies an observer
 * when the user selectes a time.
 */
public class DateSlider extends Dialog{

//	private static String TAG = "DATESLIDER";

    protected OnDateSetListener onDateSetListener;
    protected Calendar mInitialTime, minTime, maxTime;
    protected int mLayoutID;
    protected TextView mTitleText;
    protected SliderContainer mContainer;
    protected int minuteInterval;
    protected String title="选择日期：";

    public DateSlider(Context context, int layoutID, OnDateSetListener l, Calendar initialTime) {
    	this(context,layoutID,l,initialTime, null, null, 1);
    }
    
    public DateSlider(Context context, int layoutID, OnDateSetListener l, Calendar initialTime, int minInterval) {
    	this(context,layoutID,l,initialTime, null, null, minInterval);
    }
    
    public DateSlider(Context context, int layoutID, OnDateSetListener l,
            Calendar initialTime, Calendar minTime, Calendar maxTime) {
    	this(context,layoutID,l,initialTime, minTime, maxTime, 1);
    }
    
    public DateSlider(Context context, int layoutID, OnDateSetListener l,
            Calendar initialTime, Calendar minTime, Calendar maxTime, int minInterval) {
        super(context);
        this.onDateSetListener = l;
        this.minTime = minTime; this.maxTime = maxTime;
        mInitialTime = Calendar.getInstance(initialTime.getTimeZone());
        mInitialTime.setTimeInMillis(initialTime.getTimeInMillis());
        mLayoutID = layoutID;
        Log.e("printLayoutid:",layoutID+"");
        this.minuteInterval = minInterval;
        if (minInterval>1) {
        	int minutes = mInitialTime.get(Calendar.MINUTE);
    		int diff = ((minutes+minuteInterval/2)/minuteInterval)*minuteInterval - minutes;
    		mInitialTime.add(Calendar.MINUTE, diff);
        }
    }

    /**
     * Set up the dialog with all the views and their listeners
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState!=null) {
            Calendar c = (Calendar)savedInstanceState.getSerializable("time");
            if (c != null) {
                mInitialTime = c;
            }
        }

        this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(mLayoutID);
        this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.dialogtitle);

        mTitleText = (TextView) this.findViewById(R.id.dateSliderTitleText);
        mContainer = (SliderContainer) this.findViewById(R.id.dateSliderContainer);

        mContainer.setOnTimeChangeListener(onTimeChangeListener);
        mContainer.setMinuteInterval(minuteInterval);
        mContainer.setTime(mInitialTime);
        if (minTime!=null) mContainer.setMinTime(minTime);
        if (maxTime!=null) mContainer.setMaxTime(maxTime);

        Button okButton = (Button) findViewById(R.id.dateSliderOkButton);
        okButton.setOnClickListener(okButtonClickListener);

        Button cancelButton = (Button) findViewById(R.id.dateSliderCancelButton);
        cancelButton.setOnClickListener(cancelButtonClickListener);
        
        Button clearButton=(Button)findViewById(R.id.dateSliderClearButton);
        clearButton.setOnClickListener(clearButtonClickListener);
        
        /*Button addButton = (Button) findViewById(R.id.dateSliderAddButton);
        addButton.setOnClickListener(new android.view.View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mContainer.addYears(10);
			}});
        
        Button minusButton = (Button) findViewById(R.id.dateSliderMinusButton);
        minusButton.setOnClickListener(new android.view.View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mContainer.addYears(-10);
			}});*/
    }

    public void setTime(Calendar c) {
        mContainer.setTime(c);
    }

    private View.OnClickListener okButtonClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (onDateSetListener!=null)
                onDateSetListener.onDateSet(DateSlider.this, getTime());
            dismiss();
        }
    };

    private View.OnClickListener cancelButtonClickListener = new View.OnClickListener() {
        public void onClick(View v) {
        	if (onDateSetListener!=null)
                onDateSetListener.onDateCancel(DateSlider.this);
            dismiss();
        }
    };

    private View.OnClickListener clearButtonClickListener = new View.OnClickListener() {
        public void onClick(View v) {
        	if (onDateSetListener!=null)
                onDateSetListener.onDataClear(DateSlider.this);
            dismiss();
        }
    };
    
    private SliderContainer.OnTimeChangeListener onTimeChangeListener = new SliderContainer.OnTimeChangeListener() {

        public void onTimeChange(Calendar time) {
            setTitle();
        }
    };

    @Override
    public Bundle onSaveInstanceState() {
        Bundle savedInstanceState = super.onSaveInstanceState();
        if (savedInstanceState==null) savedInstanceState = new Bundle();
        savedInstanceState.putSerializable("time", getTime());
        return savedInstanceState;
    }

    /**
     * @return The currently displayed time
     */
    protected Calendar getTime() {
        return mContainer.getTime();
    }

    /**
     * This method sets the title of the dialog
     */
    protected void setTitle() {
        if (mTitleText != null) {
            final Calendar c = getTime();
            //mTitleText.setText(getContext().getString(R.string.dateSliderTitle) +
                    //String.format(": %te. %tB %tY", c, c, c));
                mTitleText.setText(title+
                        String.format("%tY年%tm月%td日 ", c, c, c));
                
        }
    }

    public void setTitle(String text){
    	title=text;
    }

    /**
     * Defines the interface which defines the methods of the OnDateSetListener
     */
    public interface OnDateSetListener {
        /**
         * this method is called when a date was selected by the user
         * @param view			the caller of the method
         *
         */
        public void onDateSet(DateSlider view, Calendar selectedDate);
        public void onDateCancel(DateSlider view);
        public void onDataClear(DateSlider view);
    }
    
    
}

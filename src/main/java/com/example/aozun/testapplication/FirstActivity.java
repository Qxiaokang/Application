package com.example.aozun.testapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.aozun.testapplication.DateSlider.DateSlider;
import com.example.aozun.testapplication.DateSlider.MonthYearDateSlider;
import com.example.aozun.testapplication.dialog.IDDatePickerDialog;

import java.util.Calendar;

/**日期选择
 * Created by HHD-H-I-0369 on 2016/11/18.
 */
public class FirstActivity extends Activity implements View.OnClickListener{
    private EditText et = null, idtext;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        initViews();
    }

    private void initViews(){
        et = (EditText) findViewById(R.id.datetext);
        idtext = (EditText) findViewById(R.id.idet);
        et.setOnClickListener(this);
        idtext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            //年月选择
            case R.id.datetext:
                Calendar c1 = Calendar.getInstance();
                Calendar c2 = Calendar.getInstance();
                Calendar c3 = Calendar.getInstance();
                c2.add(Calendar.YEAR, -100);
                c3.add(Calendar.YEAR, 100);
                String da1 = et.getText().toString();
                if(da1.length() == 6){
                    c1.set(Integer.parseInt(da1.substring(0, 4)), Integer.parseInt(da1.substring(4, 6)) - 1, 1);
                }
                MonthYearDateSlider monthYearDateSlider = new MonthYearDateSlider(this, dateSetListener, c1, c2, c3);
                monthYearDateSlider.setTitle("请选择");
                monthYearDateSlider.show();
                break;

            //有效期选择
            case R.id.idet:
                IDDatePickerDialog idDatePickerDialog=new IDDatePickerDialog(this);
                idDatePickerDialog.setOnDateSetListener(new IDDatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(String startDate,
                                          String endDate, boolean isValid) {
                        if(isValid){
                            idtext.setText(startDate+"-长期有效");
                        }else{
                            idtext.setText(startDate+"-"+endDate);
                        }
                    }

                    @Override
                    public void onCancel() {
                        // TODO Auto-generated method stub
                    }

                });

                idDatePickerDialog.show();
                break;
        }
    }

    DateSlider.OnDateSetListener dateSetListener = new DateSlider.OnDateSetListener(){
        @Override
        public void onDateSet(DateSlider view, Calendar selectedDate){
            et.setText(String.format("%d%02d", selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH) + 1));
        }

        @Override
        public void onDateCancel(DateSlider view){
        }

        @Override
        public void onDataClear(DateSlider view){
            et.setText("");
        }
    };
}

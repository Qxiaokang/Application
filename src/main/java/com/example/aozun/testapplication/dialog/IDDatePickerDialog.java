package com.example.aozun.testapplication.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import com.example.aozun.testapplication.R;

import java.util.Calendar;

public class IDDatePickerDialog extends BaseKeyinDialog implements OnDateChangedListener{

	
	private Button apply,cancel;
	private Button startminus5,startadd5,startminusday5,startaddday5,endminus5,endadd5;
	private DatePicker startDatePicker,endDatePicker;
	private CheckBox  idValid;
	private int start_year=Calendar.getInstance().get(Calendar.YEAR);
	private int start_monthOfYear=Calendar.getInstance().get(Calendar.MONTH);
	private int start_dayOfMonth=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	private int end_year=Calendar.getInstance().get(Calendar.YEAR)+10;
	private int end_monthOfYear=Calendar.getInstance().get(Calendar.MONTH);
	private int end_dayOfMonth=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	private boolean isCheck=false;
	

	public IDDatePickerDialog(Context context){
		super(context);
		keyinTitle="证件有效期";
			/*this.start_year=Integer.parseInt(tmp.substring(0, 4));
			this.start_monthOfYear=Integer.parseInt(tmp.substring(4, 6))-1;
			this.start_dayOfMonth=Integer.parseInt(tmp.substring(6));
		*/
		init();
	}
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ipo_dialog_date_picker);
		initViews();
		initValues();
	}
	
	private void initViews(){
		apply=(Button)findViewById(R.id.apply);
		apply.setOnClickListener(clickListener);
		cancel=(Button)findViewById(R.id.cancel);
		cancel.setOnClickListener(clickListener);
		startminus5=(Button)findViewById(R.id.startminus5);
		startminus5.setOnTouchListener(clickListener5);
		startadd5=(Button)findViewById(R.id.startadd5);
		startadd5.setOnTouchListener(clickListener5);
		startminusday5=(Button)findViewById(R.id.startminusday5);
		startminusday5.setOnTouchListener(clickListener5);
		startaddday5=(Button)findViewById(R.id.startaddday5);
		startaddday5.setOnTouchListener(clickListener5);
		endminus5=(Button)findViewById(R.id.endminus5);
		endminus5.setOnTouchListener(clickListener5);
		endadd5=(Button)findViewById(R.id.endadd5);
		endadd5.setOnTouchListener(clickListener5);
		idValid=(CheckBox) findViewById(R.id.idvalid);
		idValid.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
					if(isChecked){
						isCheck=true;
						endDatePicker.setVisibility(View.INVISIBLE);
						endminus5.setVisibility(View.INVISIBLE);
						endadd5.setVisibility(View.INVISIBLE);
					}else{
						isCheck=false;
						endDatePicker.setVisibility(View.VISIBLE);
						endminus5.setVisibility(View.VISIBLE);
						endadd5.setVisibility(View.VISIBLE);
					}
			}});
		startDatePicker=(DatePicker)findViewById(R.id.startDatePicker);
		endDatePicker=(DatePicker)findViewById(R.id.endDatePicker);
		startDatePicker.setCalendarViewShown(false);
		endDatePicker.setCalendarViewShown(false);
		
		//initStyle(startDatePicker);
		//initStyle(endDatePicker);
		}
	private void init(){
		WindowManager.LayoutParams a=this.getWindow().getAttributes();
		a.width=w;//setAttributes(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		this.getWindow().setAttributes(a);
		this.setCancelable(true);
		this.setTitle(keyinTitle);
	}
	private void initValues(){
		idValid.setChecked(isCheck);
		startDatePicker.init(start_year, start_monthOfYear, start_dayOfMonth, this);
		endDatePicker.init(end_year, end_monthOfYear, end_dayOfMonth, this);
		if(idValid.isChecked()){
			endDatePicker.setVisibility(View.INVISIBLE);
			endminus5.setVisibility(View.INVISIBLE);
			endadd5.setVisibility(View.INVISIBLE);
		}
	}
	private Button.OnTouchListener clickListener5=new Button.OnTouchListener(){
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if(event.getAction()==MotionEvent.ACTION_UP){
				switch (v.getId()) {
				case R.id.startminus5:
					startDatePicker.init(start_year-5, start_monthOfYear, start_dayOfMonth, IDDatePickerDialog.this);
					break;
				case R.id.startadd5:
					startDatePicker.init(start_year+5, start_monthOfYear, start_dayOfMonth, IDDatePickerDialog.this);
					break;
				case R.id.startminusday5:
					startDatePicker.init(start_year, start_monthOfYear, start_dayOfMonth-5, IDDatePickerDialog.this);
					break;
				case R.id.startaddday5:
					startDatePicker.init(start_year, start_monthOfYear, start_dayOfMonth+5, IDDatePickerDialog.this);
					break;
				case R.id.endminus5:
					endDatePicker.init(end_year-5, end_monthOfYear, end_dayOfMonth, IDDatePickerDialog.this);
					break;
				case R.id.endadd5:
					endDatePicker.init(end_year+5, end_monthOfYear, end_dayOfMonth, IDDatePickerDialog.this);
					break;
				default:
					break;
				}
			}
			return true;
		}
	};
	private Button.OnClickListener clickListener=new Button.OnClickListener(){

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.apply:
				if(onDateSetListener!=null){
					apply.requestFocus();
					apply.requestFocusFromTouch();
					//					int yearDiff=end_year-start_year;
					/*//formFields.get(0).getValEditText().setText(String.valueOf((start_year*100+start_monthOfYear+1)*100+start_dayOfMonth));
					//formFields.get(1).getValEditText().setText(String.valueOf((end_year*100+end_monthOfYear+1)*100+end_dayOfMonth));
					if(isCheck){
						formFields.get(2).getValEditText().setText("1");
						formFields.get(1).getValEditText().setText("");
		        	}
		        	else
		        		formFields.get(2).getValEditText().setText("");
					onDateSetListener.onDateSet(formFields.get(0).getTextValue(), formFields.get(1).getTextValue(),isCheck);
				}*/
					String stratDate=String.valueOf((start_year*100+start_monthOfYear+1)*100+start_dayOfMonth);
					String endDate=String.valueOf((end_year*100+end_monthOfYear+1)*100+end_dayOfMonth);
					if(isCheck){
						onDateSetListener.onDateSet(stratDate,"长期有效",isCheck);
					}else {
						onDateSetListener.onDateSet(stratDate,endDate,isCheck);
					}

				}
				dismiss();
				break;
			case R.id.cancel:
				if(onDateSetListener!=null){
					onDateSetListener.onCancel();
				}
				dismiss();
				break;

			default:
				break;
			}
		}
	};
	
	private OnDateSetListener onDateSetListener;
	public interface OnDateSetListener {
        void onDateSet(String startDate, String endDate, boolean isValid);
        void onCancel();
    }
	public void setOnDateSetListener(OnDateSetListener onDateSetListener){
		this.onDateSetListener=onDateSetListener;
	}
	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		if(view==startDatePicker){
			int yearDiff=this.end_year-this.start_year;
			if(yearDiff<=0){
				yearDiff=10;
			}
			this.start_year=year;
			this.start_monthOfYear=monthOfYear;
			this.start_dayOfMonth=dayOfMonth;
			
			endDatePicker.init(year+yearDiff, monthOfYear, dayOfMonth, this);
		}else if(view==endDatePicker){
			if(this.start_year*10000+this.start_monthOfYear*100+this.start_dayOfMonth>
			year*10000+monthOfYear*100+dayOfMonth){
				endDatePicker.init(end_year, end_monthOfYear, end_dayOfMonth, IDDatePickerDialog.this);
				return;
			}
			int yearDiff=this.end_year-this.start_year;
			if(yearDiff<=0){
				yearDiff=10;
			}
			this.end_year=year;
			this.end_monthOfYear=monthOfYear;
			this.end_dayOfMonth=dayOfMonth;
			
			//startDatePicker.init(year-yearDiff, monthOfYear, dayOfMonth, this);
		}
//		updateDate(view,year,monthOfYear,dayOfMonth);
		
	}
	
	public   boolean CheckData(){
		return true;
	
	}
	@Override
	public boolean SetContentSize() {
		WindowManager.LayoutParams params = this.getWindow().getAttributes(); 
		params.width = LayoutParams.WRAP_CONTENT;  
		params.height = LayoutParams.WRAP_CONTENT;
		params.y=h/2-BaseKeyinDialog.ScreenH/2+50;
		this.getWindow().setAttributes(params);
		return true;
	}
	@Override
	public  void SetFocus(){
	}
	
}

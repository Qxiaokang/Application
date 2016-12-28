package com.example.aozun.testapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;



public abstract class BaseKeyinDialog extends Dialog {

	public BaseKeyinDialog(Context context) {
		super(context);
	}
	public BaseKeyinDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}
	
	public static int ScreenW=1280;
	public static int ScreenH=768;
	protected String keyinTitle;
	protected int h=300;
	protected int w=800;
	//protected String path=Environment.getExternalStorageDirectory()+File.separator+"mobileTerm"+File.separator+"formwork"+File.separator;
	protected String staticReg="^[^\"!<>'&]*$";
	protected String[] dynamicMemory;
	public int getH() {
		return h;
	}
	public int getW() {
		return w;
	}
	public String[] getDynamicMemory() {
		return dynamicMemory;
	}
	public void setDynamicMemory(String[] dynamicMemory) {
		this.dynamicMemory = dynamicMemory;
	}
	public void SetSize(int w,int h){
		this.h=h;
		this.w=w;
		WindowManager.LayoutParams params = this.getWindow().getAttributes(); 
		params.width = w;  
		params.height = h ;
		//params.y=h/2-BaseKeyinDialog.ScreenH/2+50;
		this.getWindow().setAttributes(params);
	}
	/*public   void SetFormFields(List<FormField> formFileds){
		this.formFields=formFileds;
	}*/
	
	public  abstract boolean CheckData();
	public abstract void SetFocus();
//	public Boolean SerializableDynamicMemory(String key) throws IOException 
//	{
//		FileOutputStream f =null;
//		ObjectOutputStream s=null;
//		File fl=new File(path);
//		try
//		{
//			if(!fl.exists())
//				fl.mkdirs();
//			f = new FileOutputStream(path+File.separator+key+".obj");
//			s = new ObjectOutputStream(f);
//			s.writeObject(dynamicMemory);
//	
//			s.flush();
//			s.close();
//			f.close();
//		}
//		catch(Exception ex)
//		{
//			if(s!=null)s.close();
//			if(f!=null)f.close();			
//			return false;
//		}
//		
//		return true;
//	}
//	public Boolean DeserializeDynamicMemory(String key) throws IOException
//	{
//		FileInputStream in=null;
//		ObjectInputStream s=null;
//		File  fl=null;
//		try
//		{
//			 fl=new File(path+File.separator+key+".obj");
//			 if(!fl.exists()) 
//			 {
//				 dynamicMemory=new String[1];
//				 return true;
//			 }
//			 in = new FileInputStream(fl);
//	
//			 s = new ObjectInputStream(in);
//			 dynamicMemory=null;
//			
//			 dynamicMemory = (String[])s.readObject();
//			 s.close();
//			 in.close();
//		}
//		catch(Exception ex)
//		{
//			if(s!=null)s.close();
//			if(in!=null)in.close();
//			if(fl!=null)
//				fl.delete();
//			return false;
//		}
//		
//		
//		return true;
//	}
	public abstract boolean SetContentSize();
	public void ShowDialog(){
		/*if(formFields==null || formFields.size()<1)
			return;
		if(formFields.get(0).obtainLock())
			return;*/
		this.show();
		this.SetSize(w, h);
		this.SetContentSize();
		this.setTitle(keyinTitle);
		SetFocus();
	}
	
	public void showDialog(){
		this.show();
		this.SetSize(w, h);
		this.SetContentSize();
		this.setTitle(keyinTitle);
		SetFocus();
	}
}

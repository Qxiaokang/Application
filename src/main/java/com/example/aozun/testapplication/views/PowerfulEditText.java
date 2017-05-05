package com.example.aozun.testapplication.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.utils.LogUtils;

/**
 * Created by Admin on 2017/5/4.
 *
 */
public class PowerfulEditText extends EditText{
    private static  final int ET_TYPE_NORMAL=0;
    private static  final int ET_TYPE_DELETE=1;
    private static  final int ET_TYPE_PWD=2;

    private int et_type;
    private Drawable right_drawable;
    private boolean eyeOpen=false;
    public PowerfulEditText(Context context){
        this(context,null);
    }

    public PowerfulEditText(Context context, AttributeSet attrs){
        //
        this(context,attrs,android.R.attr.editTextStyle);
    }

    public PowerfulEditText(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PowerfulEditText);
        et_type=typedArray.getInt(R.styleable.PowerfulEditText_et_type,ET_TYPE_NORMAL);
        LogUtils.d("et_type:"+et_type);
        init();
    }

    private void init(){
        //获取EditText的rightDrawable  左上右下
        right_drawable=getCompoundDrawables()[2];
        //右侧没有图标，类型为删除
        if(et_type==ET_TYPE_DELETE){
            right_drawable=getResources().getDrawable(R.drawable.bt_delete_select);
            right_drawable.setBounds(0,0,40,40);
        }
        if(et_type==ET_TYPE_PWD){
            right_drawable=getResources().getDrawable(R.drawable.invisible_pwd);
            right_drawable.setBounds(0,0,60,40);
        }
        //默认设置右侧图标不显示
        //setRightIconVisible(et_type==ET_TYPE_NORMAL?false:true);
        addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
                    LogUtils.d("charlength:"+charSequence.length());
                    setRightIconVisible(charSequence.length()>0);
            }

            @Override
            public void afterTextChanged(Editable editable){
            }
        });
    }


    //设置右侧图标显示或隐藏
    private void setRightIconVisible(boolean b){
        Drawable rt=b?right_drawable:null;
        setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1],rt,getCompoundDrawables()[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_UP){
            if(getCompoundDrawables()[2]!=null){
                boolean istouched=event.getX()>(getWidth()-getTotalPaddingRight())&&event.getX()<(getWidth()-getPaddingRight());
                if(istouched){
                    //如果点击删除，清空值
                    if(et_type==ET_TYPE_DELETE){
                           this.setText("");
                    }
                    if(et_type==ET_TYPE_PWD){
                        LogUtils.i("eyeOpen："+eyeOpen);
                        if(eyeOpen){
                            //变为密文
                            this.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            //this.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            eyeOpen=false;
                        }else {
                            //变为明文
                            this.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            eyeOpen=true;
                        }
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

}

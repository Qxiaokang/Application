package com.example.aozun.testapplication.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.utils.LogUtils;
import com.example.aozun.testapplication.utils.MainApplication;
import com.example.aozun.testapplication.utils.UniversalUtils;
import com.example.aozun.testapplication.views.OpenMenuTextView;
import com.example.aozun.testapplication.views.RoundImageView;
import com.example.aozun.testapplication.views.ShopButton;

/**
 * 自定义view类
 */

public class ViewsActivity extends AppCompatActivity implements View.OnClickListener{
    private Button snackDel, snackAdd, endbt;//删除,添加,添加完成的按钮
    private ShopButton shopButton;//加入购物车的按钮
    private OpenMenuTextView openMenuTextView;//菜单按钮
    private RoundImageView imageViewa, imageViewb, imageViewc, imageViewd;//四个圆形的菜单
    private int changeTime = 1000;//变化的时间控制
    AppCompatImageView appCompatImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snackbar);
        MainApplication.getInstance().addActivity(this);
        initViews();

    }

    //views初始化
    private void initViews(){
        snackDel = (Button) findViewById(R.id.snack_del_id);
        snackDel.setOnClickListener(this);
        snackAdd = (Button) findViewById(R.id.snack_add_id);
        snackAdd.setOnClickListener(this);
        endbt = (Button) findViewById(R.id.end_id);
        endbt.setOnClickListener(this);
        openMenuTextView = (OpenMenuTextView) findViewById(R.id.open_menu_id);
        openMenuTextView.setChangeTime(changeTime);
        shopButton = (ShopButton) findViewById(R.id.shopbt_id);
        shopButton.setShopText("加入购物车");
        imageViewa = (RoundImageView) findViewById(R.id.imag_a);
        imageViewb = (RoundImageView) findViewById(R.id.imag_b);
        imageViewc = (RoundImageView) findViewById(R.id.imag_c);
        imageViewd = (RoundImageView) findViewById(R.id.imag_d);

        openMenuTextView.setTvlistener(new OpenMenuTextView.Tvlistener(){
            @Override
            public void setOnTvlistener(View view){
                //open
                if(openMenuTextView.getAdd()){
                    setImageVisible(View.VISIBLE);
                    translateX(imageViewa, 0, 300);
                    translateX(imageViewb, 0, 260);
                    translateY(imageViewb, 0, -150);
                    translateX(imageViewc, 0, 150);
                    translateY(imageViewc, 0, -260);
                    translateY(imageViewd, 0, -300);
                }
                //hidden
                if(openMenuTextView.getDel()){
                    translateX(imageViewa, 300, 0);
                    translateX(imageViewb, 260, 0);
                    translateY(imageViewb, -150, 0);
                    translateX(imageViewc, 150, 0);
                    translateY(imageViewc, -260, 0);
                    translateY(imageViewd, -300, 0);
                }
            }
        });
        imageViewa.setOnClickListener(this);
        appCompatImageView= (AppCompatImageView) findViewById(R.id.img_anim);
        appCompatImageView.setOnClickListener(this);

    }

    //点击事件
    @Override
    public void onClick(View view){
        switch(view.getId()){
            //删除按钮
            case R.id.snack_del_id:
                showDelSnackbar();
                break;
            //添加按钮
            case R.id.snack_add_id:
                showAddSnackbar();
                break;
            //添加完成
            case R.id.end_id:
                UniversalUtils.getInstance().showToast(ViewsActivity.this, "添加商品数量为" + shopButton.getMcount());
                break;
            case R.id.imag_a:
                UniversalUtils.getInstance().showToast(ViewsActivity.this, "点击签到");
                break;

            case R.id.img_anim:
                UniversalUtils.getInstance().showToast(ViewsActivity.this,"animImage");
                //VectorDrawable轨迹动画
                ImageView imageView= (ImageView) view;
                Drawable drawable=imageView.getDrawable();
                LogUtils.d("onclick+");
                if(drawable instanceof Animatable){
                    LogUtils.d("start");
                    ((Animatable) drawable).start();
                }
                anim(view);
                break;
            default:
                break;
        }
    }

    //删除的snackbar
    private void showDelSnackbar(){
        Snackbar.make(snackDel, "是否删按钮？", Snackbar.LENGTH_LONG).setAction("是", new View.OnClickListener(){
            @Override
            public void onClick(View view){
                shopButton.setClickable(false);
                shopButton.setVisibility(View.INVISIBLE);
                UniversalUtils.getInstance().showToast(ViewsActivity.this, "删除成功");
            }
        }).show();
    }

    //添加的snackbar
    private void showAddSnackbar(){
        Snackbar.make(snackDel, "是否添加按钮？", Snackbar.LENGTH_LONG).setAction("是", new View.OnClickListener(){
            @Override
            public void onClick(View view){
                shopButton.setVisibility(View.VISIBLE);
                shopButton.setClickable(true);
                UniversalUtils.getInstance().showToast(ViewsActivity.this, "添加成功");
            }
        }).show();
    }

    //平移X
    private void translateX(View view, int x, final int endX){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", x, endX).setDuration(changeTime);
        animator.start();
        animator.addListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animator){
                //平移开始的时候不可点击
                setImageClickable(false);
            }
            @Override
            public void onAnimationEnd(Animator animator){
                //停止后改为可点击
                setImageClickable(true);
                //endX为0时隐藏,不可点击
                if(endX == 0){
                    setImageVisible(View.INVISIBLE);
                    setImageClickable(false);
                }

            }
            @Override
            public void onAnimationCancel(Animator animator){
            }

            @Override
            public void onAnimationRepeat(Animator animator){
            }
        });
    }

    //平移Y
    private void translateY(View view, int y, final int endY){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", y, endY).setDuration(changeTime);
        animator.start();
        animator.addListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animator){
                setImageClickable(false);
            }
            @Override
            public void onAnimationEnd(Animator animator){
                setImageClickable(true);
                //enY为0时，改为隐藏,不可点击
                if(endY == 0){
                    setImageVisible(View.INVISIBLE);
                    setImageClickable(false);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator){
            }

            @Override
            public void onAnimationRepeat(Animator animator){
            }
        });
    }

    //设置imagview是否可见
    private void setImageVisible(int visible){
        imageViewa.setVisibility(visible);
        imageViewb.setVisibility(visible);
        imageViewc.setVisibility(visible);
        imageViewd.setVisibility(visible);
    }
    //设置imagview是否可点击
    private void setImageClickable(boolean bo){
        imageViewa.setClickable(bo);
        imageViewb.setClickable(bo);
        imageViewc.setClickable(bo);
        imageViewd.setClickable(bo);
    }
    /**
     * VectorDrawable实现路径变换动画
     * 方法适用于版本大于等于Android LoLLIPOP (5.0)
     * @param  view
    * */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public  void anim(View view){
        ImageView imageView = (ImageView) view;
        AnimatedVectorDrawable drawable = (AnimatedVectorDrawable)getDrawable(R.drawable.star_vector);
        imageView.setImageDrawable(drawable);
        if(drawable!=null){
            drawable.start();
        }
    }


}

package com.example.aozun.testapplication.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.adapter.GridAdapter;
import com.example.aozun.testapplication.utils.LogUtils;
import com.example.aozun.testapplication.utils.MainApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 简单的RxJava异步操作
 * RxJava 有四个基本概念：
 * Observable (可观察者，即被观察者)、
 * Observer (观察者)、
 * subscribe (订阅)、事件。
 * Observable 和 Observer 通过 subscribe() 方法实现订阅关系，
 * 从而 Observable 可以在需要的时候发出事件来通知 Observer。
 * <p/>
 * 与传统观察者模式不同， RxJava 的事件回调方法除了普通事件 onNext()
 * （相当于 onClick() / onEvent()）之外，还定义了两个特殊的事件：onCompleted() 和 onError()。
 * onCompleted(): 事件队列完结。RxJava 不仅把每个事件单独处理，还会把它们看做一个队列。RxJava 规定，
 * 当不会再有新的 onNext() 发出时，需要触发 onCompleted() 方法作为标志。
 * onError(): 事件队列异常。在事件处理过程中出异常时，onError() 会被触发，同时队列自动终止，不允许再有事件发出。
 * 在一个正确运行的事件序列中, onCompleted() 和 onError() 有且只有一个，并且是事件序列中的最后一个。
 * 需要注意的是，onCompleted() 和 onError() 二者也是互斥的，即在队列中调用了其中一个，就不应该再调用另一个。
 */
public class RxActivtity extends BaseActivity implements View.OnClickListener{
    private Button pic_bt;
    private GridAdapter gridAdapter;
    private GridView picgrid;
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath();
    private File[] folders;
    private List<Bitmap> bitmaps = new ArrayList<>();
    private LinearLayout piclinear;//放图片的linearLayout
    private ImageView imageView;
    private Button time_bt;
    private Subscription subscription;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        MainApplication.getInstance().addActivity(this);
        setContentView(R.layout.activity_rx_activtity);
        initViews();
    }

    //初始化
    private void initViews(){
        folders = new File[]{new File(path + File.separator + "xiao"), new File(path + File.separator + "Pictures")};
        pic_bt = (Button) findViewById(R.id.loadpic);
        pic_bt.setOnClickListener(this);
        picgrid = (GridView) findViewById(R.id.grid_v);
        piclinear = (LinearLayout) findViewById(R.id.sl_linear);
        time_bt= (Button) findViewById(R.id.time_id);
        time_bt.setOnClickListener(this);

    }

    //添加imageView
    private void addImageView(){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(50, 100, 50, 100);
        layoutParams.gravity = Gravity.CENTER;
        imageView = new ImageView(this);
        imageView.setLayoutParams(layoutParams);
    }

    @Override
    public void onClick(View view){
        int index=0;
        switch(view.getId()){
            case R.id.loadpic:
                initRx();
                break;
            case R.id.time_id:
                startTime();

                break;
        }
    }

    //rxjava 加载图片
    private void initRx(){
        Observable.from(folders).flatMap(new Func1<File, Observable<File>>(){
            @Override
            public Observable<File> call(File file){
                return Observable.from(file.listFiles());
            }
        }).filter(new Func1<File, Boolean>(){
            @Override
            public Boolean call(File file){
                return file.getName().endsWith(".png") || file.getName().endsWith(".jpg");
            }
            //map(): 事件对象的直接变换，
        }).map(new Func1<File, Bitmap>(){
            @Override
            public Bitmap call(File file){
                return getBitmapFromfile(file);
            }
            //subscribeOn(): 指定 subscribe() 所发生的线程 即Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程。
            // observeOn(): 指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Bitmap>(){
            @Override
            public void call(Bitmap bitmap){
                bitmaps.add(bitmap);
                addImageView();
                imageView.setImageBitmap(bitmap);
                piclinear.addView(imageView);
                LogUtils.e("添加bitmap");
            }
        });
    }

    //将file转化成bitmap
    private Bitmap getBitmapFromfile(File file){
        Bitmap bitmap = null;
        try{
            bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return bitmap;
    }

    Observer<String> observer = null;
    Subscriber<String> subscriber = null;

    //创建observer
    private void createObserver(){
        //Observer 即观察者，它决定事件触发的时候将有怎样的行为。 RxJava 中的 Observer 接口的实现方式：
        observer = new Observer<String>(){
            @Override
            public void onCompleted(){
                LogUtils.d("onCompleted");
            }

            @Override
            public void onError(Throwable e){
                LogUtils.e("onError");
            }

            @Override

            public void onNext(String s){
                LogUtils.d("onNext: " + s);
            }
        };
        //除了 Observer 接口之外，RxJava 还内置了一个实现了 Observer 的抽象类：Subscriber。 Subscriber 对 Observer 接口进行了一些扩展，但他们的基本使用方式是完全一样的：
        subscriber = new Subscriber<String>(){
            @Override
            public void onCompleted(){
                LogUtils.d("onCompleted");
            }

            @Override
            public void onError(Throwable e){
                LogUtils.e("onError");
            }

            @Override
            public void onNext(String s){
                LogUtils.d("onNext: " + s);
            }
        };
    }

    //创建 Observable
    //Observable 即被观察者，它决定什么时候触发事件以及触发怎样的事件。 RxJava 使用 create() 方法来创建一个 Observable ，并为它定义事件触发规则：
    Observable<String> observable = null;

    private void createObservable(){
        observable = Observable.create(new Observable.OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber){
                subscriber.onNext("Hello");
                subscriber.onNext("what are you doing?");
                subscriber.onCompleted();
            }
        });
        //create() 方法是 RxJava 最基本的创造事件序列的方法。基于这个方法， RxJava 还提供了一些方法用来快捷创建事件队列
        //just(T...): 将传入的参数依次发送出来。
        observable = Observable.just("Hello", "what are you doing?");
        //from(T[]) / from(Iterable<? extends T>) : 将传入的数组或 Iterable 拆分成具体对象后，依次发送出来。
        String[] strings = new String[]{"Hello", "what are you doing?"};
        observable = Observable.from(strings);
        //上面 just(T...) 的例子和 from(T[]) 的例子，都和之前的 create(OnSubscribe) 的例子是等价的。
    }
    // Subscribe (订阅)

    //创建了 Observable 和 Observer 之后，再用 subscribe() 方法将它们联结起来，整条链子就可以工作了。代码形式很简单：
    private void subscribe(){
        observable.subscribe(subscriber);
        //或者
        observable.subscribe(observer);
    }
    //利用Rxjava计时
    private void startTime(){
        List<String> list=new ArrayList<>();
        int i=1;
        while(i<3601){
            list.add(i+"");
            i++;
        }
        String [] str= new String[list.size()];
        for(int j = 0; j < list.size(); j++){
            str[j]=list.get(j);
        }


     subscription= Observable.from(str).map(new Func1<String, Integer>(){
          @Override
          public Integer call(String s){
                  SystemClock.sleep(1000);
                  LogUtils.e("thread--i:"+Integer.parseInt(s));
              return Integer.parseInt(s);
          }
      }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
              .subscribe(new Action1<Integer>(){
                  @Override
                  public void call(Integer integer){
                      LogUtils.e("main:--i:"+integer);
                      time_bt.setText(integer+"s");
                  }

              });

    }

    @Override
    protected void onDestroy(){
        LogUtils.e("destroy");
        super.onDestroy();
        unSubscribe();
    }

    //取消订阅
    private void unSubscribe(){
        if(subscription!=null){
            subscription.unsubscribe();
        }
    }
}

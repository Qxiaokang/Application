package com.example.aozun.testapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.example.aozun.testapplication.R;

/**
 * @author xk
 * map page
* */
public class MapActivity extends Activity implements View.OnClickListener{
    private MapView mapView;
    private BaiduMap mBaiduMap;
    private ImageView iv_location;
    private BDLocation location;
    private BitmapDescriptor mCurrentMarker;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        initMap();
    }

    private void moveLocation(){
        mCurrentMode= MyLocationConfiguration.LocationMode.NORMAL;
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);
        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.warning);
        MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
        mBaiduMap.setMyLocationConfiguration(config);
        // 当不需要定位图层时关闭定位图层
        //mBaiduMap.setMyLocationEnabled(false);
    }

    private void initMap(){
        mapView= (MapView) findViewById(R.id.map_id);
        iv_location= (ImageView) findViewById(R.id.local_icon);
        iv_location.setOnClickListener(this);
        mBaiduMap=mapView.getMap();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.local_icon:
                moveLocation();
                break;
            default:
                break;
        }
    }
    /**
  * ---GAO DE MAP--
  * private AMap aMap=null;
    private MapView mapView=null;
    private MyLocationStyle myLocationStyle=null;
    private ImageView ivLocation=null;
//    private AMapLocationClient client=null;
//    private AMapLocationClientOption clientOption=null;

    private void initMap(Bundle savedInstanceState){
        mapView= (MapView) findViewById(R.id.map_id);//before  onCreate
        mapView.onCreate(savedInstanceState);
        if(aMap==null){
            aMap=mapView.getMap();
            aMap.getUiSettings().setMyLocationButtonEnabled(true);
        }

        ivLocation= (ImageView) findViewById(R.id.local_icon);
        ivLocation.setOnClickListener(this);
        if(myLocationStyle==null){
            myLocationStyle=new MyLocationStyle();
            myLocationStyle.interval(10000);
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);
            aMap.setMyLocationStyle(myLocationStyle);//set location blue point style
        }
        //aMap.setMyLocationEnabled(true);
        aMap.setOnMyLocationChangeListener(onMyLocationChangeListener);
    }
    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();//draw map again
    }
    @Override
    protected void onPause(){
        super.onPause();
        mapView.onResume();//stop draw map
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);//save map state
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.local_icon:
                LogUtils.d("---location---");
                aMap.setMyLocationEnabled(true);//show location blue point
                //location();
                break;
            default:
                break;
        }
    }
    AMap.OnMyLocationChangeListener onMyLocationChangeListener=new AMap.OnMyLocationChangeListener(){
        @Override
        public void onMyLocationChange(Location location){
            double longitude=location.getLongitude();
            double latitude=location.getLatitude();
            LogUtils.d("---longitude:"+longitude+"    latitude:"+latitude);
            aMap.clear();
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(longitude,latitude),19));
        }
    };
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(mapView!=null){
            mapView.onDestroy();//onDestroy map
        }
    }*/
  /** AMapLocationListener listener=new AMapLocationListener(){
       @Override
       public void onLocationChanged(AMapLocation aMapLocation){
           double longitude=aMapLocation.getLongitude();
           double latitude=aMapLocation.getLatitude();
           LogUtils.d("---longitude:"+longitude+"    latitude:"+latitude);
       }
   };
   private void location(){
       if(client==null){
           client=new AMapLocationClient(getApplicationContext());
       }
       client.setLocationListener(listener);
       clientOption=new AMapLocationClientOption();
       //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
       clientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

       //clientOption.setOnceLocation(true);//default false one location
       clientOption.setInterval(2000);//one time

       clientOption.setNeedAddress(true);//result address message
       client.setLocationOption(clientOption);
       client.startLocation();
   }*/
}

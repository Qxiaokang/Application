package com.example.aozun.testapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.utils.LogUtils;

import java.util.List;

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
    private LocationClientOption locationClientOption=null;
    private LocationManager locationManager=null;
    private LocationClient locationClient=null;
    private String provider;
    private boolean ifFrist=true;
    BDLocationListener bdLocationListener=new BDLocationListener(){
        @Override
        public void onReceiveLocation(BDLocation bdLocation){
            LogUtils.d("address:"+bdLocation.getAddrStr());
                LogUtils.e("bdlocation:"+bdLocation.getLongitude()+"   "+bdLocation.getLatitude());

        }
        @Override
        public void onConnectHotSpotMessage(String s, int i){
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        initMap();
        //moveLocation();
    }

    private void moveLocation(){
        mBaiduMap.setMyLocationEnabled(true);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> list = locationManager.getProviders(true);
        if (list.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (list.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(this, "当前不能提供位置信息", Toast.LENGTH_LONG).show();
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            navigateTo(location);
        }
        locationManager.requestLocationUpdates(provider, 5000, 1,
                locationListener);
    }

    LocationListener locationListener=new LocationListener(){
        @Override
        public void onLocationChanged(Location location){
            LogUtils.e("location:"+location.getLongitude()+"   "+location.getLatitude());
                navigateTo(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle){

        }

        @Override
        public void onProviderEnabled(String s){

        }

        @Override
        public void onProviderDisabled(String s){

        }
    };


    private void navigateTo(Location location) {
        // 按照经纬度确定地图位置
        if (ifFrist) {
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            // 移动到某经纬度
            mBaiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomBy(5f);
            // 放大
            mBaiduMap.animateMapStatus(update);

            ifFrist = false;
        }
        // 显示个人位置图标
        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(location.getLatitude());
        builder.longitude(location.getLongitude());
        MyLocationData data = builder.build();
        mBaiduMap.setMyLocationData(data);
    }
    private void initMap(){
        mapView= (MapView) findViewById(R.id.map_id);
        iv_location= (ImageView) findViewById(R.id.local_icon);
        iv_location.setOnClickListener(this);
        mBaiduMap=mapView.getMap();
        locationClient=new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(bdLocationListener);
        //initLocation();
    }
    private void initLocation(){
    locationClientOption = new LocationClientOption();
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
    //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        locationClientOption.setCoorType("bd09ll");
    //可选，默认gcj02，设置返回的定位结果坐标系

    int span=1000;
        locationClientOption.setScanSpan(span);
    //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        locationClientOption.setIsNeedAddress(true);
    //可选，设置是否需要地址信息，默认不需要

        locationClientOption.setOpenGps(true);
    //可选，默认false,设置是否使用gps

        locationClientOption.setLocationNotify(true);
    //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        locationClientOption.setIsNeedLocationDescribe(true);
    //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        locationClientOption.setIsNeedLocationPoiList(true);
    //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        locationClientOption.setIgnoreKillProcess(false);
    //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        locationClientOption.SetIgnoreCacheException(false);
    //可选，默认false，设置是否收集CRASH信息，默认收集

        locationClientOption.setEnableSimulateGps(false);
    //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
         locationClient.setLocOption(locationClientOption);
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
        locationClient.stop();
        locationClient.unRegisterLocationListener(bdLocationListener);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.local_icon:
                locationClient.start();
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

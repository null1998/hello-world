package com.example.whoere;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Text;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;

public class LocationActivity extends AppCompatActivity {
    String hisLongitude="",hisLatitude="";
    double hisLongitude1=0.0,hisLatitude1=0.0;
    public LocationClient mLocationClient;
    private Button resetButton;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LatLng myLatLng,hisLatLng;
    private boolean isFirstLocate=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_location);
        Intent intent = getIntent();
        hisLongitude=intent.getStringExtra("hisLongitude");
        hisLatitude=intent.getStringExtra("hisLatitude");
        parse(hisLongitude,hisLatitude);
        resetButton = findViewById(R.id.reset_button);
        //clearButton = findViewById(R.id.clear_button);
        mMapView = (MapView) findViewById(R.id.mmap);
        mBaiduMap = mMapView.getMap();

        //mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        List<String> permissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String [] permissions =permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(LocationActivity.this,permissions,1);
        }else{
            requestLocation();
        }

        resetButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                mBaiduMap.setMyLocationEnabled(true);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(myLatLng).zoom(16f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        });

    }
    private void parse(String hisLongitude,String hisLatitude){
        try{
            hisLongitude1 =parseDouble(hisLongitude);
            hisLatitude1 = parseDouble(hisLatitude);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void requestLocation(){
        LocationClientOption option =new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);
        //option.setIsNeedAddress(true);//是否设置地址
        //option.setIsNeedLocationDescribe(true);//是否需要位置描述信息
        //option.setIsNeedLocationPoiList(true);//是否获取周围poi
        mLocationClient.setLocOption(option);
        option.setScanSpan(5000);
        mLocationClient.start();
    }
    private void setMarker(LatLng LLng){
        ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.p);
        giflist.add(bitmap);
        MarkerOptions ooD = new MarkerOptions().position(LLng).icons(giflist)
                .zIndex(0).period(10);

        if (true) {
            ooD.animateType(MarkerOptions.MarkerAnimateType.grow);
        }
        Marker mMarkerD = (Marker) (mBaiduMap.addOverlay(ooD));

    }

    protected void onResume(){
        super.onResume();
        mMapView.onResume();
        hisLatLng= new LatLng(hisLatitude1,hisLongitude1);
        setMarker(hisLatLng);
    }
    protected void onPause(){
        super.onPause();
        mMapView.onPause();
    }
    protected void onStop(){
        super.onStop();
    }
    protected  void onDestroy(){
        super.onDestroy();
        mLocationClient.stop();
        mMapView.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);
    }
    public void onRequestPermissionResult(int requestCode,String[] permissions,int[]grantResults){
        switch(requestCode){
            case 1:
                if(grantResults.length>0){
                    for(int result : grantResults){
                        if(result!=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"必须同意所有权限才能使用本程序",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else{
                    Toast.makeText(this,"发生未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    private  void navigateTo(BDLocation location){
        if(isFirstLocate){
            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
            MapStatus.Builder mMapStatus = new MapStatus.Builder();
            mMapStatus.target(ll).zoom(16);
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus.build());
            mBaiduMap.animateMapStatus(mMapStatusUpdate);
            isFirstLocate = false;
        }

        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        mBaiduMap.setMyLocationData(locationData);
    }


    private class MyLocationListener implements BDLocationListener{
        public void onReceiveLocation(final BDLocation location) {
            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeNetWorkLocation) {
                myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                navigateTo(location);
            }
        }
        public void onConnectHotSpotMessage(String s,int i){

        }

    }
}

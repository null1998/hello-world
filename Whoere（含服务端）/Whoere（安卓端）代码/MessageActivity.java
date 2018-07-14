package com.example.whoere;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MessageActivity extends AppCompatActivity {
    private List<Msg> msgList = new ArrayList<>();
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    DrawerLayout drawerLayout;
    private ImageView imageView;
    private EditText editText1,editText2;
    private Button button,myButton,hobbyButton,locationButton,leaveButton;
    String mynickname="somebody";
    String myusername;
    Handler handler =new Handler();
    Handler handler2 = new Handler();
    public LocationClient mLocationClient;
    private double myLongitude=0,myLatitude=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new myLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_message);
        findView();
        Intent intent = getIntent();
        myusername = intent.getStringExtra("myusername");
        chatGet(myusername);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean isSuccess=HttpConnection.sendToOne(editText2.getText().toString(),editText1.getText().toString(),myusername,mynickname);
                        if(!"".equals(editText1.getText().toString())){
                            showSend(isSuccess);
                        }
                    }
                }).start();

            }
        });
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MyActivity.class);
                intent.putExtra("myusername",myusername);
                startActivity(intent);
            }
        });
        hobbyButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("myusername",myusername);
                startActivity(intent);
            }
        });
        locationButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                  new Thread(new Runnable() {
                      @Override
                      public void run() {
                          handler2.postDelayed(postLocRunnable,3000);
                      }
                  }).start();
                  runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          Toast.makeText(getApplicationContext(),"分享位置成功！退出程序即可自动取消分享！",Toast.LENGTH_LONG).show();
                      }
                  });
            }
        });
        leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageActivity.this.finish();
                System.exit(0);

            }
        });

        List<String> permissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(MessageActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(MessageActivity.this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(MessageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String [] permissions =permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MessageActivity.this,permissions,1);
        }else{
            requestLocation();
        }
    }

    private void set_myusername(String myusername){
        this.myusername=myusername;
    }

    private void chatGet(final String myusername){
               set_myusername(myusername);
               handler.postDelayed(runnable,3000);
    }
    Runnable runnable= new Runnable() {
        @Override
        public void run() {
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       try {

                       OkHttpClient client = new OkHttpClient();

                       RequestBody requestBody = new FormBody.Builder().add("username", myusername).build();

                       Request request = new Request.Builder().url("http://47.94.221.23:8080/Test/ChatGet").post(requestBody).build();

                       Response response = client.newCall(request).execute();
                       String tmp = response.body().string();
                       if(!tmp.isEmpty()) {
                           showResponse(tmp);
                       }

                   }catch(IOException e){
                           e.printStackTrace();
                       }
               }
               }).start();
            handler.postDelayed(this, 3000);
        }
    };
    Runnable postLocRunnable=new Runnable() {
        @Override
        public void run() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        OkHttpClient client = new OkHttpClient();

                        RequestBody requestBody = new FormBody.Builder().add("myusername",myusername).add("myLongitude",String.valueOf(myLongitude)).add("myLatitude",String.valueOf(myLatitude)).build();

                        Request request = new Request.Builder().url("http://47.94.221.23:8080/Test/PostLocation").post(requestBody).build();

                        Response response = client.newCall(request).execute();

                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }).start();
            handler2.postDelayed(postLocRunnable,3000);
        }
    };

    private void findView(){
        imageView=findViewById(R.id.icon);
        button=findViewById(R.id.bt_username);
        myButton=findViewById(R.id.My);
        hobbyButton=findViewById(R.id.i_want);
        locationButton=findViewById(R.id.myLocation);
        leaveButton=findViewById(R.id.leave);
        editText1=findViewById(R.id.query_username);
        editText2=findViewById(R.id.query);
        drawerLayout=findViewById(R.id.main_drawer_layout);
        msgRecyclerView=(RecyclerView)findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter=new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
    }

private void showSend(final boolean isSuccess){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(isSuccess) {
                    Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_SHORT).show();
                    Msg msg = new Msg(editText1.getText().toString(), Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1);
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    editText1.setText("");
                }
                else{
                    Toast.makeText(getApplicationContext(), "发送失败，对方已下线", Toast.LENGTH_SHORT).show();
                }
            }
        });
}
private void showResponse(final String tmp){
    runOnUiThread(new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getApplicationContext(),"有一条新消息!",Toast.LENGTH_SHORT).show();
            Msg msg= new Msg(tmp,Msg.TYPE_RECEIVED);
            msgList.add(msg);
            adapter.notifyItemInserted(msgList.size()-1);
            msgRecyclerView.scrollToPosition(msgList.size()-1);
        }
    });
}

    private void requestLocation(){
        LocationClientOption option =new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);
        mLocationClient.setLocOption(option);
        option.setScanSpan(5000);
        mLocationClient.start();
    }

    private class myLocationListener implements BDLocationListener {
        public void onReceiveLocation(final BDLocation location) {
            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeNetWorkLocation) {

                myLongitude=location.getLongitude();
                myLatitude=location.getLatitude();
            }
        }
        public void onConnectHotSpotMessage(String s,int i){

        }

    }
}




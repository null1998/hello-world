package com.example.whoere;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

/**
 * Created by 啊啊啊 on 2018/5/14.
 */

public class HttpConnection {
    //登录
    public  String LoginByPost(String username, String password) {
        String result =null;

        try {
            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder().add("username",username).add("password",password).build();

            Request request = new Request.Builder().url("http://47.94.221.23:8080/Test/login").post(requestBody).build();

            Response response = client.newCall(request).execute();

            result =response.body().string();


        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }
    //注册
    public static String RegisterByPost(String username,String password,String nickname,String sex,String intro){
        String result =null;
        try {
            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder().add("username", username).add("password", password).add("nickname",nickname).add("sex",sex).add("intro",intro).build();

            Request request = new Request.Builder().url("http://47.94.221.23:8080/Test/register").post(requestBody).build();

            Response response = client.newCall(request).execute();

            result = response.body().string();
        }catch(IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public static String [] getData(String myusername){
        String jsonData =null;
        String []data = new String [5];
        try {
            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder().add("myusername", myusername).build();

            Request request = new Request.Builder().url("http://47.94.221.23:8080/Test/getData").post(requestBody).build();

            Response response = client.newCall(request).execute();

            jsonData = response.body().string();

            JSONObject jsonObject = new JSONObject(jsonData);
            data[0]=jsonObject.getString("username");
            data[1]=jsonObject.getString("nickname");
            data[2]=jsonObject.getString("password");
            data[3]=jsonObject.getString("sex");
            data[4]=jsonObject.getString("intro");
        }catch(IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
        return data;
    }
    public static boolean resetData(String myusername,String nickname,String password,String sex,String intro){
        String result="";

        try {
            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder().add("myusername", myusername).add("nickname",nickname).add("password", password).add("sex",sex).add("intro",intro).build();

            Request request = new Request.Builder().url("http://47.94.221.23:8080/Test/ResetData").post(requestBody).build();

            Response response = client.newCall(request).execute();

            result = response.body().string();
            if(result.equals("success")){
                return true;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return false;
    }


    //搜索感兴趣的内容
    public  static ArrayList<String> searchUsers(String query){
        String jsonData = null;
        ArrayList<String> list =null;
        try {
            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder().add("query",query).build();

            Request request = new Request.Builder().url("http://47.94.221.23:8080/Test/SearchUsers").post(requestBody).build();

            Response response = client.newCall(request).execute();

            jsonData = response.body().string();
            //将传来的json数据解析
            list = parseJson(jsonData);

        }catch(IOException e){
            e.printStackTrace();
        }
        return list;
    }


    private static ArrayList<String>  parseJson(String jsonData){
        String username = "";
        String intro = "";
        ArrayList<String> list = new ArrayList<String>();
        try{
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                username = jsonObject.getString("username");
                intro = jsonObject.getString("intro");
                list.add("有关"+"#"+username+"#"+"的介绍："+intro);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return  list;
    }

    public static boolean sendToOne(String ToUsername,String content,String myusername,String mynickname){
        try {
            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder().add("username",ToUsername)
                    .add("content",content).add("myusername",myusername)
                            .add("mynickname",mynickname).build();

            Request request = new Request.Builder().url("http://47.94.221.23:8080/Test/ChatSend").post(requestBody).build();

            Response response = client.newCall(request).execute();

            String result = response.body().string();

            return result.equals("send success");

        }catch(IOException e){
            e.printStackTrace();
        }
        return true;
    }
    public static String[] getLocation(String ToUsername){
        String jsonData =null;
        String location[]=new String[2];
        try{OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder().add("ToUsername", ToUsername).build();

            Request request = new Request.Builder().url("http://47.94.221.23:8080/Test/GetLocation").post(requestBody).build();

            Response response = client.newCall(request).execute();

            jsonData = response.body().string();

            JSONObject jsonObject = new JSONObject(jsonData);
            location[0]=jsonObject.getString("longitude");
            location[1]=jsonObject.getString("latitude");

        }catch(IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
        return location;
    }


}












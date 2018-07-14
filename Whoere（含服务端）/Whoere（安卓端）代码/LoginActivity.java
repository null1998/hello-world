package com.example.whoere;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static android.os.Build.ID;
import static android.provider.Telephony.Carriers.PASSWORD;



public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText username, password;
    private  Button login;
    private TextView textView;
    final int LOGIN=0;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPass;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        findView();
        login.setOnClickListener(this);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onClick(View view){
        switch(view.getId()) {
            case R.id.login:
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String result = new HttpConnection().LoginByPost(username.getText().toString(), password.getText().toString());
                    if (result.equals("success")) {
                        editor=pref.edit();
                        if(rememberPass.isChecked()) {
                            editor.putBoolean("remember_password",true);
                            editor.putString("account", username.getText().toString());
                            editor.putString("password", password.getText().toString());
                        }else{
                            editor.clear();
                        }
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
                        intent.putExtra("myusername",username.getText().toString());
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }
                }
            }).start();
        }

    }
    private void findView() {
        username =findViewById(R.id.username);
        password = findViewById(R.id.password);
        login =findViewById(R.id.login);
        textView=findViewById(R.id.textview);
        rememberPass=findViewById(R.id.remember_pass);
        boolean isRemember=pref.getBoolean("remember_password",false);
        if(isRemember){
            String account = pref.getString("account","");
            String password1 =pref.getString("password","");
            username.setText(account);
            password.setText(password1);
            rememberPass.setChecked(true);
        }
    }





}
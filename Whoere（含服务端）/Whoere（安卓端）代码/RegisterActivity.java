package com.example.whoere;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText username,password,nickname,sex,intro;
    Button register;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findView();
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        new Thread(new Runnable() {

            public void run() {
                String result = "";
                if(username.getText().toString().isEmpty()||nickname.getText().toString().isEmpty()||password.getText().toString().isEmpty()||sex.getText().toString().isEmpty()||intro.getText().toString().isEmpty()) {
                    showResponse("",false);
                }else{
                    result = new HttpConnection().RegisterByPost(username.getText().toString(),password.getText().toString(),nickname.getText().toString(),sex.getText().toString(),intro.getText().toString());
                }
                showResponse(result,true);
            }

        }).start();
    }

    public void findView(){
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        nickname = findViewById(R.id.nickname);
        sex = findViewById(R.id.sex);
        intro=findViewById(R.id.intro);
        register = findViewById(R.id.register);
    }
    private void showResponse(final String result,final boolean isTrue){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (result.equals("success")) {
                     Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                     Intent intent = new Intent();
                }
                else{
                    Toast.makeText(getApplicationContext(),"注册失败,已存在相同昵称或用户名",Toast.LENGTH_SHORT).show();
                }
                if(!isTrue){
                    Toast.makeText(getApplicationContext(),"输入不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

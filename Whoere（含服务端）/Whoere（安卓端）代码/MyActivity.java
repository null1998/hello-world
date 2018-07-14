package com.example.whoere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends AppCompatActivity {
    private String myusername;
    private String nickname="",password="",sex="",intro="";
    private TextView textView1,textView2,textView3,textView4,textView;
    private EditText editText1,editText2,editText3,editText4;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        findView();
        Intent intent = getIntent();
        myusername=intent.getStringExtra("myusername");

       submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               if(editText1.getText().toString().isEmpty()){
                   nickname=(textView1.getText().toString()).substring(9);
               }
               else{
                   nickname=editText1.getText().toString();
               }

               if(editText2.getText().toString().isEmpty()){
                   password=(textView2.getText().toString()).substring(9);
               }
               else{
                   password=editText2.getText().toString();
               }

               if(editText3.getText().toString().isEmpty()){
                   sex=(textView3.getText().toString()).substring(4);
               }else{
                   sex=editText3.getText().toString();
               }

               if(editText4.getText().toString().isEmpty()){
                   intro=(textView4.getText().toString()).substring(10);
               }
               else{
                   intro=editText4.getText().toString();
               }
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       boolean isSuccess=HttpConnection.resetData(myusername,nickname,password,sex,intro);
                       if(isSuccess){
                           showTextView(nickname,password,sex,intro,true);
                       }
                       else {
                           showTextView("","","","",false);
                       }
                   }
               }).start();
           }
       });
    }
    public void onStart(){
        super.onStart();
    }
    public void onResume(){
        super.onResume();
        setTextView();
    }
    private void setTextView(){
        new Thread(new Runnable() {
            @Override
            public void run() {
              String data[]=new String[5];
              data=HttpConnection.getData(myusername);
              showTextView(data[1],data[2],data[3],data[4],true);
            }
        }).start();
    }

    private void findView(){
        textView1=findViewById(R.id.nickname);
        textView2=findViewById(R.id.password);
        textView3=findViewById(R.id.sex);
        textView4=findViewById(R.id.intro);
        textView=findViewById(R.id.username);
        editText1=findViewById(R.id.set_nickname);
        editText2=findViewById(R.id.set_password);
        editText3=findViewById(R.id.set_sex);
        editText4=findViewById(R.id.set_intro);
        submit=findViewById(R.id.submit);
    }
    private void showTextView(final String nickname,final String password,final String sex,final String intro,final boolean isSuccess){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(isSuccess) {
                    textView1.setText("nickname:"+nickname);
                    textView2.setText("password:"+password);
                    textView3.setText("sex:"+sex);
                    textView4.setText("introduce:"+intro);
                    textView.setText("username[" + myusername + "]");
                    editText1.setText("");
                    editText2.setText("");
                    editText3.setText("");
                    editText4.setText("");
                    //Toast.makeText(getApplicationContext(),"修改成功！",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"修改失败，已有相同昵称啦",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

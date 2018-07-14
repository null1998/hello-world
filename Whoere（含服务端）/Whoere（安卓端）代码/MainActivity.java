package com.example.whoere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
     SearchView searchView;
     ListView listView;
     String myusername;
     ArrayList<String> list=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        myusername=intent.getStringExtra("myusername");
        findView();
        //设置是否显示搜索框展开时的提交按钮
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("搜索你感兴趣的东西吧");
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                    if(list!=null) {
                        list.clear();
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            list = HttpConnection.searchUsers(query);
                            showResponse(list,query);
                        }
                    }).start();


                return false;
            }
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String s[] = (list.get(i)).split("#");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String ToUsername = s[1];
                        String hisLocation[]=new String[2];
                        hisLocation=HttpConnection.getLocation(ToUsername);
                        Intent intent  = new Intent(getApplicationContext(),LocationActivity.class);
                        intent.putExtra("hisLongitude",hisLocation[0]);
                        intent.putExtra("hisLatitude",hisLocation[1]);
                        startActivity(intent);
                    }
                }).start();


            }
        });
    }

    private void findView(){
        searchView = findViewById(R.id.search_view);
        listView = findViewById(R.id.list_view);
    }
    private void showResponse(final ArrayList<String> list,final String query){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(list.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "查无匹配对象", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "已查询到与"+query+"相关信息"+list.size()+"条", Toast.LENGTH_LONG).show();
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.adapter,list);
                    listView.setAdapter(adapter);
                }
            }
        });
    }
}

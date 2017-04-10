package com.example.administrator.aap_interphase.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.aap_interphase.Adapter.MyAdpter_Time;
import com.example.administrator.aap_interphase.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Activity_time extends AppCompatActivity {

    private ImageView img_back;
    private RecyclerView recyclerView;
    private MyAdpter_Time adpter_time;
    private List<String> times = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        init();
    }

    private void init() {
        img_back= (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Activity_time.this,MainActivity.class);
                startActivity(intent);
            }
        });
        times.add("10分钟");
        times.add("20分钟");
        times.add("30分钟");
        times.add("自定义时间");
        recyclerView = (RecyclerView) findViewById(R.id.recyleview);
        adpter_time = new MyAdpter_Time(times,Activity_time.this);
        LinearLayoutManager manger = new LinearLayoutManager(Activity_time.this);
        recyclerView.setLayoutManager(manger);
        //绘制分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(Activity_time.this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adpter_time);
    }
}

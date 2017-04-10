package com.example.administrator.aap_interphase.Activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.aap_interphase.Myreceiver;
import com.example.administrator.aap_interphase.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class Activity_user extends AppCompatActivity {

    //该页面是对用户信息进行管理的页面
    private Button btn_logooout;
    private TextView tv_user;
    private ImageView  img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //进行初始化
        Bmob.initialize(this, "f8838f4d3eddeeab8f7c52ba7517a893");
        init();
    }
    private void init() {
        btn_logooout = (Button) findViewById(R.id.btn_lgout);
        tv_user = (TextView) findViewById(R.id.tv_user);
        img = (ImageView) findViewById(R.id.back);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Activity_user.this,MainActivity.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        tv_user.setText("欢迎你,"+username);
        btn_logooout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退出当前账号
                BmobUser.logOut();
                Intent intent = new Intent();
                intent.setClass(Activity_user.this,Activity_login.class);
                startActivity(intent);
            }
        });
    }
}

package com.example.administrator.aap_interphase.Activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.aap_interphase.Myreceiver;
import com.example.administrator.aap_interphase.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Activity_login extends AppCompatActivity {
    private Button btn_register;
    private ImageView img_back;

    //账号登录
    private EditText edt_username;
    private EditText edt_password;
    private Button btn_login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //进行初始化
        Bmob.initialize(this, "f8838f4d3eddeeab8f7c52ba7517a893");
        init();
        //开始进行登录
        checkUser();
        enter();
    }

    private void checkUser() {
        BmobUser bmobUser = BmobUser.getCurrentUser();
        if (bmobUser != null) {
            // 允许用户使用应用
            Intent intent = new Intent();
            intent.setClass(Activity_login.this, Activity_user.class);
            startActivity(intent);
        } else {
            //缓存用户对象为空时， 可打开用户注册界面…
            Toast.makeText(Activity_login.this, "你还没登录，请登录", Toast.LENGTH_LONG).show();
        }
    }

    private void enter() {
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_username = (EditText) findViewById(R.id.edt_username);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = edt_username.getText().toString();
                String password = edt_password.getText().toString();

                        if(username!=null&&password!=null){
                            Intent intent = new Intent();
                            intent.setClass(Activity_login.this, Activity_user.class);
                            intent.putExtra("username",username);
                            startActivity(intent);
                            Toast.makeText(Activity_login.this, "登录成功", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(Activity_login.this,"登录失败，请重试",Toast.LENGTH_LONG).show();
                        }



/*             BmobUser bu2 = new BmobUser();
                bu2.setUsername(username);
                bu2.setPassword(password);
                bu2.login(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if (e == null) {
                            Intent intent = new Intent();
                            intent.setClass(Activity_login.this, Activity_user.class);
                            intent.putExtra("username",username);
                            startActivity(intent);
                            Toast.makeText(Activity_login.this, "登录成功", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Activity_login.this, "登录失败，请重试", Toast.LENGTH_LONG).show();
                        }
                        Log.d("AAAAAAAAAAAAA", e.toString());
                    }

                });*/
            }
        });
    }

    private void init() {
        btn_register = (Button) findViewById(R.id.register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Activity_login.this, Activity_register.class);
                startActivity(intent);
            }
        });
        img_back = (ImageView) findViewById(R.id.back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Activity_login.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}

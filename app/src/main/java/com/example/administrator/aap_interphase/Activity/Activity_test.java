package com.example.administrator.aap_interphase.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.aap_interphase.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/4/10 0010.
 */

public class Activity_test extends AppCompatActivity{
    private EditText edt_username;
    private EditText edt_password;
    private Button btn_login;
    private Button btn_reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        //进行初始化
        Bmob.initialize(this, "f8838f4d3eddeeab8f7c52ba7517a893");
        //检查用户是否已经登录
        checkUser();
        initViews();
    }

    private void checkUser() {
        BmobUser bmobUser = BmobUser.getCurrentUser();
        if(bmobUser != null){
            // 允许用户使用应用
            Intent intent = new Intent();
            intent.setClass(Activity_test.this,MainActivity.class);
            startActivity(intent);
        }else{
            //缓存用户对象为空时， 可打开用户注册界面…
            Toast.makeText(Activity_test.this,"你还没登录，请登录",Toast.LENGTH_LONG).show();
        }
    }

    private void initViews() {
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_paaword);
        btn_reg = (Button) findViewById(R.id.btn_reg);
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Activity_test.this,Activity_register.class);
                startActivity(intent);
            }
        });

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = edt_username.getText().toString();
                String password = edt_password.getText().toString();

                BmobUser bu2 = new BmobUser();
                bu2.setUsername(username);
                bu2.setPassword(password);
                bu2.login(new SaveListener<BmobUser>() {

                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if(e==null){
                            Intent intent = new Intent();
                            intent.setClass(Activity_test.this,MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(Activity_test.this,"登录成功",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(Activity_test.this,"登录失败，请重试",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}

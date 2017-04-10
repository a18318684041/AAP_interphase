package com.example.administrator.aap_interphase.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.aap_interphase.Myreceiver;
import com.example.administrator.aap_interphase.R;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;


public class Activity_register extends AppCompatActivity {
    private ImageView img_back;


    private EditText edt_username;
    private EditText edt_password;
    private EditText edt_confirm;
    private EditText edt_phone;
    private EditText edt_code;
    private Button btn_register;
    private Button btn_getcode;

    private CircleImageView head_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        //开始进行注册
        register();
    }

    private void register() {
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_confirm = (EditText) findViewById(R.id.edt_confirm);
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_code = (EditText) findViewById(R.id.edt_code);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_getcode = (Button) findViewById(R.id.btn_getcode);

        //点击获取验证码
        btn_getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edt_phone.getText().toString();
                BmobSMS.requestSMSCode(phone, "test", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if (e == null) {
                            Toast.makeText(Activity_register.this, "发送成功", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        //开始进行注册
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = edt_code.getText().toString();
                final String phone = edt_phone.getText().toString();
                BmobSMS.verifySmsCode(phone, code, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        String username = edt_username.getText().toString();
                        String password = edt_password.getText().toString();
                        String confirm = edt_confirm.getText().toString();
                        if (password.equals(confirm)) {
                            BmobUser user = new BmobUser();
                            user.setUsername(username);
                            user.setPassword(password);
                            user.setMobilePhoneNumber(phone);
                            user.setMobilePhoneNumberVerified(true);
                            user.signUp(new SaveListener<BmobUser>() {
                                @Override
                                public void done(BmobUser s, BmobException e) {
                                    //已经注册过的手机号不能再次进行注册
                                    if (e == null) {
                                        Toast.makeText(Activity_register.this, "注册成功", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            Intent intent = new Intent();
                            intent.setClass(Activity_register.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Activity_register.this, "密码不一致，注册失败", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private void init() {
        img_back = (ImageView) findViewById(R.id.back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Activity_register.this, Activity_login.class);
                startActivity(intent);
            }
        });
        //进行头像的选择
        head_img = (CircleImageView) findViewById(R.id.head_img);
        head_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1001);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == 1001
                    && resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                head_img.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                Toast.makeText(Activity_register.this, picturePath, Toast.LENGTH_LONG).show();
            }
        }
    }
}

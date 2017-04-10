package com.example.administrator.aap_interphase.Activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.aap_interphase.Adapter.FragmentAdpter;
import com.example.administrator.aap_interphase.Adapter.Myadpter;
import com.example.administrator.aap_interphase.Myreceiver;
import com.example.administrator.aap_interphase.R;
import com.example.administrator.aap_interphase.Fragment.tab;
import com.example.administrator.aap_interphase.Fragment.tab2;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Myreceiver netWorkStateReceiver;
    //tablayout的使用
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentAdpter adpter;
    private List<Fragment> fragmentss;

    //左侧导航栏的初始化
    private ListView listView;
    private Myadpter myadpter;
    private List<String> title = new ArrayList<>();
    private List<Integer> head = new ArrayList<>();

    //用户的点击事件
    private ImageView img;
    String isclose = "false";

    //在线音乐的搜索
    private ImageView btn_search;


    @Override
    protected void onResume() {
        super.onResume();
        if (netWorkStateReceiver == null) {
            netWorkStateReceiver = new Myreceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkStateReceiver, filter);
        System.out.println("注册");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(netWorkStateReceiver);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //监听网络状态
        chenkNet();
        //初始化tablayout
        inittab();
        //初始化侧边导航栏
        initNav();
        //跳转到搜索页面
        initOnline();
    }

    private void chenkNet() {

    }

    private void initOnline() {
        btn_search = (ImageView) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,Activity_onLineMusic.class);
                startActivity(intent);
            }
        });
    }

    private void inittab() {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.vp);
        fragmentss = new ArrayList<>();
        fragmentss.add(new tab());
        fragmentss.add(new tab2());
        adpter = new FragmentAdpter(getSupportFragmentManager(), fragmentss);
        viewPager.setAdapter(adpter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initNav() {
        listView = (ListView) findViewById(R.id.listview);
        title.add("用户中心");
        title.add("皮肤中心");
        title.add("天气信息");
        title.add("定时关闭");
        head.add(R.drawable.user_skin);
        head.add(R.drawable.clothes);
        head.add(R.drawable.weather);
        head.add(R.drawable.clock);
        myadpter = new Myadpter(title, head, MainActivity.this);
        listView.setAdapter(myadpter);

        //进行listview的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id == 0) {
                    //用户中心,如果没有登录就跳入登录界面
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, Activity_login.class);
                    startActivity(intent);
                } else if (id == 1) {
                    //皮肤中心
                } else if (id == 2) {
                    //天气信息
                } else if (id == 3) {
                    //定时关闭应用
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, Activity_time.class);
                    startActivity(intent);
                }
            }
        });
        //图片的跳转事件
        img = (ImageView) findViewById(R.id.user);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_login.class);
                startActivity(intent);
            }
        });
    }
}

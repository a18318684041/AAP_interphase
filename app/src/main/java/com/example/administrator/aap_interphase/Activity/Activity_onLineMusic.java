package com.example.administrator.aap_interphase.Activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.aap_interphase.Adapter.MyAdpter_OnLine;
import com.example.administrator.aap_interphase.Myreceiver;
import com.example.administrator.aap_interphase.R;
import com.example.administrator.aap_interphase.bean.onLine_song;
import com.example.administrator.aap_interphase.utils.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class Activity_onLineMusic extends AppCompatActivity{


    //进行网络歌曲的截取
    private Button  btn_search;
    private EditText edt_search;
    private List<onLine_song> songs  = new ArrayList<>();;
    //搜索关键字地址
    //public static String KEY_SEARCH_URL = "http://www.xiami.com/search/song?key=";
    //ID接口
   // public static String ID_SEARCH_URL = "http://www.xiami.com/song/playlist/id/";
    Document document = null;
    List<String> ids = new ArrayList<String>();
            ;
    private RecyclerView recyclerView;
    private MyAdpter_OnLine adpter_onLine;

    private ImageView img_back;

    Myreceiver netWorkStateReceiver;
    @Override
    protected void onResume() {
        super.onResume();
        if (netWorkStateReceiver == null) {
            netWorkStateReceiver = new Myreceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkStateReceiver, filter);
        super.onResume();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlinemusic);
        initView();
    }

    private void initView() {
        btn_search = (Button) findViewById(R.id.btn_search);
        edt_search = (EditText) findViewById(R.id.edt_search);
        recyclerView = (RecyclerView) findViewById(R.id.recyleview);
        //开始搜索网络上的歌曲
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              songs.clear();
                ids.clear();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

/*                        songs.clear();*/
                        String input = edt_search.getText().toString();
                        String KEY_SEARCH_URL = "http://www.xiami.com/search/song?key=";
                        String ID_SEARCH_URL = "http://www.xiami.com/song/playlist/id/";
                        KEY_SEARCH_URL += input;
                        try {
                            document = Jsoup.connect(KEY_SEARCH_URL).get();
                            Elements elements = document.getElementsByClass("track_list");
                            if (elements.size() != 0) {
                                Elements all = elements.get(0).getElementsByClass("chkbox");
                                int size = all.size();
                                for (int i = 0; i < size; i++) {
                                    String id = all.get(i).select("input").attr("value");
                                    ids.add(id);
                                }
                            }
                            //获取歌曲图片，歌名，以及歌手
                            int idsize = ids.size();
                            for (int i = 0; i < idsize; i++) {
                                String postUrl = ID_SEARCH_URL + ids.get(i);
                                Document d = Jsoup.connect(postUrl).get();
                                Elements element = d.select("trackList");
                                for (Element e : element) {
                                    String song = e.select("songName").text();
                                    String singer = e.select("artist").text();
                                    String imgUrl = e.select("pic").text();
                                    String downloadUrl = StringUtils.decodeMusicUrl(e.select(
                                            "location").text());
                                    String Lyricurl = e.select("lyric").text();
                                    onLine_song s = new onLine_song(song,singer,imgUrl, downloadUrl);
                                    songs.add(s);
                                    Log.d("AAA", e.select("pic").text());
                                }
                                /*adpter_onLine.changeData(songs);*/
                                // recyclerView.removeAllViews();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adpter_onLine = new MyAdpter_OnLine(songs);
                                    adpter_onLine.notifyDataSetChanged();
                                    LinearLayoutManager manger = new LinearLayoutManager(Activity_onLineMusic.this);
                                    recyclerView.setLayoutManager(manger);
                                    //绘制分割线
                                    recyclerView.addItemDecoration(new DividerItemDecoration(Activity_onLineMusic.this,DividerItemDecoration.VERTICAL));
                                    recyclerView.setAdapter(adpter_onLine);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        });

        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_onLineMusic.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

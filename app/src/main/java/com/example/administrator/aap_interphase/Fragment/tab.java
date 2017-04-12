package com.example.administrator.aap_interphase.Fragment;

import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.aap_interphase.Adapter.MyAdpter_Localmusic;
import com.example.administrator.aap_interphase.Adapter.MyAdpter_test;
import com.example.administrator.aap_interphase.R;
import com.example.administrator.aap_interphase.bean.Song;
import com.example.administrator.aap_interphase.utils.HanzitoPinyin;
import com.example.administrator.aap_interphase.utils.Music_Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class tab extends Fragment {


    //装载歌曲的列表
    List<Song> test = new ArrayList<>();
    private MyAdpter_Localmusic adapter;
    private ListView list_view;

    //正在播放歌曲的ID
    private int ID = 0;
    private TextView flow_name;
    private ImageView play;
    private ImageView last;
    private ImageView next;
    private MediaPlayer mediapalyer;
    private List<Song> songs = new ArrayList<>();


    //侧边栏更新listview的控件

    private com.example.administrator.aap_interphase.View.testView testView;

    //设置进度条
    private SeekBar seekbar;
    private Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int position = mediapalyer.getCurrentPosition();
            int mMax = mediapalyer.getDuration();
            int sMax = seekbar.getMax();
            seekbar.setProgress(position * sMax / mMax);
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        //进行进度条位置的刷新
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mHandle.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab1, container, false);
        //进行本地音乐列表的刷新
        list_view = (ListView) view.findViewById(R.id.list_view);
        Cursor cursor = view.getContext().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
                null, MediaStore.Audio.AudioColumns.IS_MUSIC);
        test = Music_Utils.getMusicData(view.getContext());
        adapter = new MyAdpter_Localmusic(view.getContext(), test);
        list_view.setAdapter(adapter);
        cursor.close();

        //进行侧边栏控制listview的滑动.
        testView = (com.example.administrator.aap_interphase.View.testView) view.findViewById(R.id.slide_item);
        TextView textView = new TextView(view.getContext());
        testView.setTextViewDialog(textView);
        testView.setUpdateListView(new com.example.administrator.aap_interphase.View.testView.UpdateListView() {
            @Override
            public void updateListView(String currentChar) {
                int positionForSection = adapter.getPositionForSection(currentChar.charAt(0));
                list_view.setSelection(positionForSection);
            }
        });
        list_view.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int sectionForPosition = adapter.getSectionForPosition(firstVisibleItem);
                testView.updateLetterIndexView(sectionForPosition);
            }
        });


        //对暂停、播放、上一首、下一首的实现
        play = (ImageView) view.findViewById(R.id.play);
        flow_name = (TextView) view.findViewById(R.id.flow_name);
        last = (ImageView) view.findViewById(R.id.last);
        next = (ImageView) view.findViewById(R.id.next);
        //进度条
        seekbar = (SeekBar) view.findViewById(R.id.seekbar);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int dest = seekBar.getProgress();
                int mMax = mediapalyer.getDuration();
                int sMax = seekBar.getMax();
                mediapalyer.seekTo(mMax * dest / sMax);
            }
        });
        //上一首
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //根据当前播放的ID去播放下一首
                if (mediapalyer.isPlaying()) {
                    mediapalyer.stop();
                    mediapalyer.release();
                    try {
                        if (ID == 0) {
                            ID = test.size() - 1;
                        } else {
                            ID = ID - 1;
                        }
                        Log.d("AAA", String.valueOf(ID));
                        mediapalyer = new MediaPlayer();
                        mediapalyer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediapalyer.setDataSource(test.get(ID).path);
                        flow_name.setText(test.get(ID).song);
                        mediapalyer.prepare();
                        mediapalyer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        if (ID == 0) {
                            ID = test.size() - 1;
                        } else {
                            ID = ID - 1;
                        }
                        mediapalyer = new MediaPlayer();
                        mediapalyer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediapalyer.setDataSource(test.get(ID).path);
                        flow_name.setText(test.get(ID).song);
                        mediapalyer.prepare();
                        mediapalyer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        //下一首
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediapalyer.isPlaying()) {
                    mediapalyer.stop();
                    mediapalyer.release();
                    try {
                        if (ID == (test.size() - 1)) {
                            ID = 0;
                        } else {
                            ID = ID + 1;
                        }
                        Log.d("AAA", String.valueOf(ID));
                        mediapalyer = new MediaPlayer();
                        mediapalyer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediapalyer.setDataSource(test.get(ID).path);
                        flow_name.setText(test.get(ID).song);
                        mediapalyer.prepare();
                        mediapalyer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        if (ID == (test.size() - 1)) {
                            ID = 0;
                        } else {
                            ID = ID + 1;
                        }
                        mediapalyer = new MediaPlayer();
                        mediapalyer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediapalyer.setDataSource(test.get(ID).path);
                        flow_name.setText(test.get(ID).song);
                        mediapalyer.prepare();
                        mediapalyer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //播放
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mediapalyer.isPlaying()) {
                    mediapalyer.start();
                    boolean a = mediapalyer.isPlaying();
                    Toast.makeText(view.getContext(), "播放状态" + a, Toast.LENGTH_LONG).show();
                    play.setImageResource(R.drawable.pause);

                } else {
                    mediapalyer.pause();
                    boolean a = mediapalyer.isPlaying();
                    Toast.makeText(view.getContext(), "播放状态" + a, Toast.LENGTH_LONG).show();
                    play.setImageResource(R.drawable.play);
                }
            }
        });
        mediapalyer = new MediaPlayer();
        mediapalyer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //listview的点击事件
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String song_id = String.valueOf(id);
                ID = Integer.parseInt(song_id);
                flow_name.setText(test.get(Integer.parseInt(song_id)).song);
                try {

                    if (!mediapalyer.isPlaying()) {
                        mediapalyer.setDataSource(test.get(Integer.parseInt(song_id)).path);
                        mediapalyer.prepare();
                        mediapalyer.start();
                        boolean a = mediapalyer.isPlaying();
                        Toast.makeText(view.getContext(), "播放状态" + a, Toast.LENGTH_LONG).show();
                    } else {
                        mediapalyer.stop();
                        mediapalyer.release();
                        mediapalyer = new MediaPlayer();
                        mediapalyer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediapalyer.setDataSource(test.get(Integer.parseInt(song_id)).path);
                        flow_name.setText(test.get(ID).song);
                        mediapalyer.prepare();
                        mediapalyer.start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
}

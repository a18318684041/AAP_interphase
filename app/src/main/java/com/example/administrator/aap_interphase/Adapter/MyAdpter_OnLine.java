package com.example.administrator.aap_interphase.Adapter;

import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.aap_interphase.R;
import com.example.administrator.aap_interphase.bean.onLine_song;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/4/9 0009.
 */
public class MyAdpter_OnLine extends RecyclerView.Adapter<MyAdpter_OnLine.ViewHolder>{

    private List<onLine_song> songs;
    private MediaPlayer mediaPlayer;
    public MyAdpter_OnLine(List<onLine_song> songs){
        this.songs = songs;
    }
    public void changeData(List<onLine_song> songs){
        songs.clear();
        this.songs = songs;
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils
                        .get()
                        .url(songs.get(position).getImgUrl())
                        .build()
                        .execute(new BitmapCallback()
                        {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(Bitmap response, int id) {
                                holder.head.setImageBitmap(response);
                            }
                        });

            }
        }).start();

        holder.list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        //释放资源后必须重新初始化
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(songs.get(position).getUrl());
                        mediaPlayer.prepare();;
                        mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.tv_singer.setText(songs.get(position).getSinger());
        holder.tv_song.setText(songs.get(position).getSong());
    }
    @Override
    public int getItemCount() {
        return songs.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView head;
        TextView tv_song;
        TextView tv_singer;
        LinearLayout list;
        public ViewHolder(View itemView) {
            super(itemView);
            head = (ImageView) itemView.findViewById(R.id.head);
            tv_song = (TextView) itemView.findViewById(R.id.songs);
            tv_singer  = (TextView) itemView.findViewById(R.id.singer);
            list = (LinearLayout) itemView.findViewById(R.id.list);
        }
    }
}

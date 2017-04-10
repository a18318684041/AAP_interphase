package com.example.administrator.aap_interphase.Adapter;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.aap_interphase.Activity.Activity_song;
import com.example.administrator.aap_interphase.R;
import com.example.administrator.aap_interphase.bean.Song;
import com.example.administrator.aap_interphase.bean.onLine_song;
import com.example.administrator.aap_interphase.utils.Music_Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/4/9 0009.
 */
public class MyAdpter_Singer extends RecyclerView.Adapter<MyAdpter_Singer.ViewHolder>{

    List<String> singers;

    public MyAdpter_Singer(List<String> singers){
        this.singers =singers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singer_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_singer.setText(singers.get(position));
    }
    @Override
    public int getItemCount() {
        return singers.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_singer;
        LinearLayout list;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_singer = (TextView) itemView.findViewById(R.id.artist);
            list = (LinearLayout) itemView.findViewById(R.id.list);
        }
    }
}

package com.example.administrator.aap_interphase.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.aap_interphase.R;
import com.example.administrator.aap_interphase.bean.Song;

import java.util.ArrayList;
import java.util.List;

public class MyAdpter_test extends RecyclerView.Adapter<MyAdpter_test.ViewHolder> {

    private List<Song> songList;
    private Context context;

    public MyAdpter_test(List<Song> songList, Context context) {
        this.songList = songList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    ArrayList<String> first = new ArrayList<>();

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.song_name.setText(songList.get(position).song);
        holder.song_singer.setText(songList.get(position).singer);

        Song  song = songList.get(position);
        if(first.contains(song.first_char)){
            holder.song_firstchar.setVisibility(View.GONE);
        }else {
            holder.song_firstchar.setVisibility(View.VISIBLE);
            holder.song_firstchar.setText(song.first_char);
            first.add(song.first_char);
        }
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView song_name;
        TextView song_singer;
        TextView song_firstchar;
        public ViewHolder(View itemView) {
            super(itemView);
            song_name = (TextView) itemView.findViewById(R.id.name);
            song_singer = (TextView) itemView.findViewById(R.id.artist);
            song_firstchar = (TextView) itemView.findViewById(R.id.name);
        }
    }
}

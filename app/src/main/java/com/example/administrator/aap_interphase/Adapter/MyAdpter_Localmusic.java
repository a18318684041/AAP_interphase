package com.example.administrator.aap_interphase.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.administrator.aap_interphase.utils.HanzitoPinyin;
import com.example.administrator.aap_interphase.utils.Music_Utils;
import com.example.administrator.aap_interphase.R;
import com.example.administrator.aap_interphase.bean.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/12 0012.
 */

public class MyAdpter_Localmusic extends BaseAdapter implements SectionIndexer {
    private Context context;
    private List<Song> songList;

    //装载首字母的集合
    List<String> first = new ArrayList<>();
    //装载首字母的第一个字母的position 的集合
    List<Integer> p = new ArrayList<>();
    public MyAdpter_Localmusic(Context context, List<Song> songList){
        this.context = context;
        this.songList = songList;
    }
    @Override
    public int getCount() {
        return songList.size();
    }

    @Override
    public Object getItem(int position) {
        return songList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview,parent,false);
        }
        //判断singer的首字符属于哪个范围
        Song  song = songList.get(position);
        TextView tv_firstletter = (TextView) convertView.findViewById(R.id.tvt_firstLetter);
       tv_firstletter.setText(song.first_char);
       if(first.contains(song.first_char)){
           tv_firstletter.setVisibility(View.GONE);
       }else {
            tv_firstletter.setVisibility(View.VISIBLE);
            //tv_firstletter.setText(song.first_char);
            first.add(song.first_char);
            p.add(position);
       }
        for (int i = 0;i<p.size();i++){
            if(position==p.get(i)){
                tv_firstletter.setVisibility(View.VISIBLE);
            }
        }
/*        if (position==0){
            tv_firstletter.setText(songList.get(0).first_char);
        }else if(!songList.get(position-1).first_char.equals(songList.get(position).first_char) && songList.get(position).first_char.equals(songList.get(position+1).first_char)){
            tv_firstletter.setText(song.first_char);
        }else {
            tv_firstletter.setHeight(0);
        }*/
        TextView song_name = (TextView) convertView.findViewById(R.id.name);
        TextView song_size = (TextView) convertView.findViewById(R.id.artist);
        song_name.setText(songList.get(position).song);
        song_size.setText(songList.get(position).singer);
        return convertView;
    }


    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < songList.size(); i++) {
            if (songList.get(i).first_char.charAt(0) == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return songList.get(position).first_char.charAt(0);
    }
}

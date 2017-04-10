package com.example.administrator.aap_interphase.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.aap_interphase.Adapter.MyAdpter_Singer;
import com.example.administrator.aap_interphase.R;
import com.example.administrator.aap_interphase.bean.Song;
import com.example.administrator.aap_interphase.utils.Music_Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/4 0004.
 */

public class tab2 extends Fragment {
    private RecyclerView recyclerView;
    private MyAdpter_Singer adpter_singer;
    private List<String> test = new ArrayList<>();
    private List<Song> singers = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyleview);
        Cursor cursor = view.getContext().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
                null, MediaStore.Audio.AudioColumns.IS_MUSIC);
        singers = Music_Utils.getMusicData(view.getContext());
        //将重复的歌手名字合并
        for (int i = 0; i < singers.size(); i++) {
            if(!test.contains(singers.get(i).singer)) {
                test.add(singers.get(i).singer);
            }
        }
        cursor.close();
        adpter_singer = new MyAdpter_Singer(test);
        LinearLayoutManager manger = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(manger);
        //绘制分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adpter_singer);
        return view;
    }
}

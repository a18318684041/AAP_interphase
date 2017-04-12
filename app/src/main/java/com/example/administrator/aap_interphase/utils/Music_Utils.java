package com.example.administrator.aap_interphase.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.example.administrator.aap_interphase.bean.Song;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public class Music_Utils {
    public static List getMusicData(Context context) {
        List<Song> list = new ArrayList();
        List<Song> test = new ArrayList();
        List<Song> kong = new ArrayList();
        String[] zimu = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        // 媒体库查询语句（写一个工具类MusicUtils）
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
                null, MediaStore.Audio.AudioColumns.IS_MUSIC);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Song song = new Song();
                song.song = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                song.singer = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                song.path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                song.duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                song.size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));

                if (song.size > 1000 * 800) {
                    // 注释部分是切割标题，分离出歌曲名和歌手 （本地媒体库读取的歌曲信息不规范）
                    if (song.song.contains("-")) {
                        String[] str = song.song.split("-");
                        song.singer = str[0];
                        song.song = str[1];

                        String zifu = HanzitoPinyin.getPinYinHeadChar(str[0]);
                        //将所有首字符转换为大写，避免在排序的时候出现差错
                        if (String.valueOf(zifu.charAt(0)).toUpperCase() != null) {
                            song.first_char = String.valueOf(zifu.charAt(0)).toUpperCase();
                        }
                    }
                    Log.d("AAA",song.singer+"首字符:"+song.first_char);
                    list.add(song);
                }
            }
            // 释放资源
            cursor.close();
            //对test进行排序
        }
        for(int k = 0;k<zimu.length;k++){
            for(int i = 0;i<list.size();i++){
                if(list.get(i).first_char!=null) {
                    if(list.get(i).first_char.equals(zimu[k])){
                        test.add(list.get(i));
                    }
                }else {
                 list.get(i).first_char="#";
                 kong.add(list.get(i));
                }
            }
        }
        test.addAll(kong);
        return test;
    }

    //格式化歌曲时间
    public static String formatTime(int time) {
        if (time / 1000 % 60 < 10) {
            return time / 1000 / 60 + ":0" + time / 1000 % 60;
        } else {
            return time / 1000 / 60 + ":" + time / 1000 % 60;
        }

    }

}

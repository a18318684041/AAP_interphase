package com.example.administrator.aap_interphase.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.aap_interphase.R;

import java.util.List;

/**
 * Created by Administrator on 2017/4/5 0005.
 */
public class Myadpter extends BaseAdapter {

    private List<String> title;
    private List<Integer> head;
    private Context context;

    public Myadpter(List<String> title, List<Integer> head, Context context) {
        this.title = title;
        this.head = head;
        this.context = context;
    }

    @Override
    public int getCount() {
        return title.size();
    }

    @Override
    public Object getItem(int position) {
        return title.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.name);
        tv.setText(title.get(position));
        ImageView img = (ImageView) convertView.findViewById(R.id.head);
        img.setImageResource(head.get(position));
        return convertView;
    }
}

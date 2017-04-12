package com.example.administrator.aap_interphase.Adapter;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
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
import com.example.administrator.aap_interphase.bean.onLine_song;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

public class MyAdpter_Time extends RecyclerView.Adapter<MyAdpter_Time.ViewHolder> {
    int time = -1;
    private List<String> times;
    private Context context;

    public MyAdpter_Time(List<String> times, Context context) {
        this.times = times;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_time.setText(times.get(position));

        //点击设置开始定时
        holder.list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //自定义关闭时间,弹出一个对话框让用户自己输入需要关闭的时间
                if (position == 0) {
                    time = 10 * 60 * 1000;
                    Toast.makeText(context, "应用将于" + times.get(position) + "后关闭", Toast.LENGTH_LONG).show();
                    holder.img_gou.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    time = 20 * 60 * 1000;
                    Toast.makeText(context, "应用将于" + times.get(position) + "后关闭", Toast.LENGTH_LONG).show();
                    holder.img_gou.setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    time = 30 * 60 * 1000;
                    Toast.makeText(context, "应用将于" + times.get(position) + "后关闭", Toast.LENGTH_LONG).show();
                    holder.img_gou.setVisibility(View.VISIBLE);
                } else if (position == 3) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("请输入定时关闭的分钟");
                    View view = LayoutInflater.from(context).inflate(R.layout.dialog, null);
                    builder.setView(view);
                    final EditText edt_time = (EditText) view.findViewById(R.id.edt_time);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            time = Integer.parseInt(edt_time.getText().toString());
                            time = time * 1000 * 60;
                            Toast.makeText(context, "应用将于" + (time / (1000 * 60)) + "分钟后关闭", Toast.LENGTH_LONG).show();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(time);
                                        Intent intent = new Intent(Intent.ACTION_MAIN);
                                        intent.addCategory(Intent.CATEGORY_HOME);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent);
                                        System.exit(0);

                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }).start();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }else {
                    Toast.makeText(context, "应用将于分钟后关闭", Toast.LENGTH_LONG).show();
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (time != -1) {
                                Thread.sleep(time);
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                                System.exit(0);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

    }

    @Override
    public int getItemCount() {
        return times.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_gou;
        TextView tv_time;
        LinearLayout list;

        public ViewHolder(View itemView) {
            super(itemView);
            img_gou = (ImageView) itemView.findViewById(R.id.img_gou);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            list = (LinearLayout) itemView.findViewById(R.id.list);
        }
    }
}

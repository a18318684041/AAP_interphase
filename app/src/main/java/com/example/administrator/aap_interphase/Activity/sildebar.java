package com.example.administrator.aap_interphase.Activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.aap_interphase.R;



public class sildebar extends View {
    //装载首字母的集合
    private String characters[] = {  "A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z" ,"#"};
    private int choose = -1;
    private Paint paint = new Paint();
    //开始进行触摸时间的监听
    private  OnTouchLetterChangedListener mOnTouchLetterChangedListener;
         public sildebar(Context context, AttributeSet attrs) {
                 super(context, attrs);
             }
    public void setOnTouchLitterChangedListener(
            OnTouchLetterChangedListener onTouchLetterChangedListener) {
        this.mOnTouchLetterChangedListener = onTouchLetterChangedListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        //每个字母的高度
        int singleHeight = height/characters.length;
        for (int i = 0; i <characters.length ; i++) {
            paint.setColor(getResources().getColor(R.color.myblcak));
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(35);
            if (i == choose) {// choose变量表示当前显示的字符位置，若没有触摸则为-1
                paint.setColor(getResources().getColor(R.color.red));
                paint.setFakeBoldText(true);
            }
            // 计算字符的绘制的位置
            float xPos = width / 2 - paint.measureText(characters[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            // 在画布上绘制字符
            canvas.drawText(characters[i], xPos, yPos, paint);
            paint.reset();// 每次绘制完成后不要忘记重制Paint
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();
        int c = (int) (y / getHeight() * characters.length);

        switch (action) {
            case MotionEvent.ACTION_UP:
                choose = -1;//
                setBackgroundColor(0x0000);
                invalidate();
                break;

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                setBackgroundColor(getResources().getColor(R.color.darkgray));
                if (choose != c) {
                    if (c >= 0 && c < characters.length) {
                        if (mOnTouchLetterChangedListener != null) {
                            mOnTouchLetterChangedListener
                                    .touchLetterChanged(characters[c]);
                        }
                        choose = c;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }


    //监听触摸时间的发生
    public interface OnTouchLetterChangedListener {
        public void touchLetterChanged(String s);
    }
}

package com.example.cm.fake_main_page.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.cm.fake_main_page.R;

import java.util.Timer;
import java.util.TimerTask;

public class TextSwitchView extends TextSwitcher implements ViewSwitcher.ViewFactory{

    private int index= -1;
    private Context context;
    private String[] resources = {"窗前明月光","疑是地上霜","举头望明月","低头思故乡"};
    private Timer timer;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case 1:
                    index = next();
                    updateText();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public TextSwitchView(Context context) {
        this(context, null);
    }

    public TextSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        if (timer == null) {
            timer = new Timer();
        }
        setFactory(this);
        setInAnimation(AnimationUtils.loadAnimation(context, R.anim.in_animation));
        setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.out_animation));
    }

    public void setResources(String[] res) {
        resources = res;
    }

    public void setTextStillTime(long time) {
        if (timer == null) {
            timer = new Timer();
        } else {
            timer.scheduleAtFixedRate(new MyTask(), 1, time);
        }
    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(1);
        }
    }

    private int next() {
        int flag = index + 1;
        if (flag > resources.length - 1) {
            flag = flag - resources.length;
        }
        return flag;
    }

    private void updateText() {
        setText(resources[index]);
    }

    @Override
    public View makeView() {
        TextView tv = new TextView(context);
        tv.setTextColor(Color.parseColor("#A9A9A9"));
        tv.setTextSize(18);
        return tv;
    }
}

package com.xriamer.mixdemo.Activitys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;


import com.xriamer.mixdemo.R;

public class StartActivity extends BaseActivity {
    private static final int TIME = 500;
    private static final int GO_INITPD = 1000;
    private static final int GO_WELCOME = 1001;
    private static final int GO_LOGIN = 1002;
    private boolean isFirst;
    private boolean setPd;

    public StartActivity() {
        isFirst = true;
        setPd = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_start);
        initThink();
    }

    private void initThink() {
        SharedPreferences preferences = getSharedPreferences("box", MODE_PRIVATE);//box是SP的文件名
        isFirst = preferences.getBoolean("flag", true);//读取“key==flag”的值，默认为True
        setPd = preferences.getBoolean("setpd", false);//读取“key==setpd”的值，默认为False
        Log.d("StartActivity", "setPd is" + setPd);
        Log.d("StartActivity", "isFirst is" + isFirst);
        if (!isFirst && !setPd) {
            handler.sendEmptyMessageDelayed(GO_INITPD, TIME);
        } else if (setPd) {
            handler.sendEmptyMessageDelayed(GO_LOGIN, TIME);
        } else {
            handler.sendEmptyMessageDelayed(GO_WELCOME, TIME);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("flag", false);//添加数据
            editor.commit();//存完数据要提交
        }
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_INITPD:
                    goInitPd();//跳转至初始化PIN码界面
                    break;
                case GO_WELCOME:
                    goWelcome();//跳转至引导界面
                    break;
                case GO_LOGIN:
                    goLogin();//跳转至登录界面
                    break;
            }
        }
    };

    private void goInitPd() {
        Intent intent = new Intent(StartActivity.this, InitPdActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        finish();
    }

    private void goWelcome() {
        Intent intent = new Intent(StartActivity.this, WelcomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        finish();
    }

    private void goLogin() {
        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        finish();
    }



}

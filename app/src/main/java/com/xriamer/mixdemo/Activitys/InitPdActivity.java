package com.xriamer.mixdemo.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xriamer.mixdemo.R;

public class InitPdActivity extends BaseActivity {

    private Button button;
    private EditText ed1;
    private EditText ed2;
    private boolean setPd = false;
    private String pd1 = "";
    private String pd2 = "";
    private static final String TAG = "InitPdActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initpd);
        button = findViewById(R.id.confirm_button);
        ed1 = findViewById(R.id.pd1);
        ed2 = findViewById(R.id.pd2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd1 = ed1.getText().toString();
                pd2 = ed2.getText().toString();
                if (pd1.equals(pd2) && pd1.length() == 4) {
                    initThink();//把PIN码和setpd存入SP
                    Intent intent = new Intent(InitPdActivity.this, MainActivity.class);
                    startActivity(intent);//跳转至主界面
                    finish();//销毁当前Activity
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(InitPdActivity.this);
                    dialog.setTitle("温馨提示");
                    dialog.setMessage("输入密码有误，请重新输入！");
                    dialog.setCancelable(false);//设置为不可取消
                    dialog.setPositiveButton("清空", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ed1.setText("");
                            ed2.setText("");
                        }
                    });
                    dialog.setNegativeButton("返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.show();
                }
            }
        });
        Log.d(TAG, "onCreate");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    private void initThink() {
        SharedPreferences perferences = getSharedPreferences("box", MODE_PRIVATE);
        SharedPreferences.Editor editor = perferences.edit();
        editor.putString("password", pd1);
        editor.putBoolean("setpd", true);
        editor.commit();
    }
}


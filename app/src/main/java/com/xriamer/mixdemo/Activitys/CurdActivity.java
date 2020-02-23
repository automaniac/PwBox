package com.xriamer.mixdemo.Activitys;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xriamer.mixdemo.DB.MyDatabaseHelper;
import com.xriamer.mixdemo.R;

public class CurdActivity extends BaseActivity {

    private TextView cur_title;
    private TextView cur_account;
    private TextView cur_password;
    private Button btn_update;
    private Button btn_delete;
    private MyDatabaseHelper dbHelper;
    private String inputTitle;
    private String inputAccount;
    private String inputPassword;
    private String newPassword;
    private static final String TAG = "CurdActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curd);
        dbHelper = new MyDatabaseHelper(this, "PwBox.db", null, 1);
        initView();
        initData();
     Log.d(TAG,"onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CurdActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void initData() {
        Intent intent = getIntent();
        inputTitle = intent.getStringExtra("title");
        inputAccount = intent.getStringExtra("account");
        inputPassword = intent.getStringExtra("password");
        cur_title.setText(inputTitle);
        cur_account.setText(inputAccount);
        cur_password.setText(inputPassword);
    }

    private void initView() {
        cur_title = findViewById(R.id.cur_title);
        cur_account = findViewById(R.id.cur_account);
        cur_password = findViewById(R.id.cur_password);
        btn_update = findViewById(R.id.cur_update);
        btn_delete = findViewById(R.id.cur_delete);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CurdActivity.this);
                View view = LayoutInflater.from(CurdActivity.this).inflate(R.layout.settingpassword, null);
                TextView cancel = view.findViewById(R.id.choosepage_cancel);
                TextView sure = view.findViewById(R.id.choosepage_sure);
                final EditText edittext = view.findViewById(R.id.choosepage_edittext);
                final Dialog dialog = builder.create();
                dialog.show();
                dialog.getWindow().setContentView(view);
                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newPassword = edittext.getText().toString();
                        if (newPassword.equals(inputPassword)) {
                            dialog.dismiss();
                            Toast.makeText(CurdActivity.this, "密码未变动", Toast.LENGTH_SHORT).show();
                        } else if (newPassword.isEmpty()) {
                            dialog.dismiss();
                            Toast.makeText(CurdActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                        } else {
                            cur_password.setText(newPassword);
                            dialog.dismiss();
                            Toast.makeText(CurdActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put("password", newPassword);
                            db.update("PwBox", values, "account=? and title=?", new String[]{inputAccount, inputTitle});
                        }
                    }
                });
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(CurdActivity.this);
                dialog.setTitle("温馨提示");
                dialog.setMessage("确认删除" + inputTitle + "？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.delete("PwBox", "account=? and title=?", new String[]{inputAccount, inputTitle});
                        onBackPressed();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                dialog.show();
            }
        });

    }
}

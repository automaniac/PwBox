package com.xriamer.mixdemo.Activitys;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.xriamer.mixdemo.DB.MyDatabaseHelper;
import com.xriamer.mixdemo.R;

import java.security.AccessControlContext;

public class AddActivity extends BaseActivity {
    private static final String TAG = "AddActivity";
    private EditText ed_title;
    private EditText ed_account;
    private EditText ed_password;
    private Button button;
    private String title;
    private String account;
    private String password;
    private MyDatabaseHelper dbHelper;
    //private Button button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        dbHelper = new MyDatabaseHelper(this, "PwBox.db", null, 1);
        InitView();
    }

    private void InitView() {
        ed_title = findViewById(R.id.et_title);
        ed_account = findViewById(R.id.et_account);
        ed_password = findViewById(R.id.et_password);
        button = findViewById(R.id.btn_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = ed_title.getText().toString();
                account = ed_account.getText().toString();
                password = ed_password.getText().toString();
                if (!title.isEmpty() && !account.isEmpty() && !password.isEmpty()) {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("title", title);
                    values.put("account", account);
                    values.put("password", password);
                    db.insert("PwBox", null, values);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(AddActivity.this);
                    dialog.setTitle("温馨提示");
                    dialog.setMessage("保存成功");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("继续添加", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            ed_account.setText("");
                            ed_password.setText("");
                            ed_title.setText("");
                            ed_title.requestFocus();
                        }
                    });
                    dialog.setNegativeButton("返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                        }
                    });
                    dialog.show();

                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(AddActivity.this);
                    dialog.setTitle("温馨提示");
                    dialog.setMessage("信息未录入完整!");
                    dialog.setCancelable(false);
                    dialog.setNegativeButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
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
        finish();

    }

}

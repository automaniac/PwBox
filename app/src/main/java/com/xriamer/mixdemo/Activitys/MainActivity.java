package com.xriamer.mixdemo.Activitys;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xriamer.mixdemo.Adapter.BoxAdapter;
import com.xriamer.mixdemo.DB.MyDatabaseHelper;
import com.xriamer.mixdemo.R;
import com.xriamer.mixdemo.Util.Box;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {
    private Button button;
    private List<Box> boxList = new ArrayList<>();
    private ListView listView;
    private MyDatabaseHelper dbHelper;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBox();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Box box = boxList.get(position);
                String title = box.getTitle();//获取标题
                String account = box.getAccount();//获取账号
                String password = box.getPassword();//获取密码
                Intent intent = new Intent(MainActivity.this,
                        CurdActivity.class);//准备从MainActivity跳转至CurdActivity
                intent.putExtra("title", title);
                intent.putExtra("account", account);
                intent.putExtra("password", password);
                startActivity(intent);//跳转
                finish();//销毁当前Activity

            }
        });
        button = findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                finish();
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
        finish();

    }

    //通过重写onCreateOptionsMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //菜单响应事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.set://如果点击的是修改PIN码选项则调用changePW方法
                changePw();
                break;
            default:
        }
        return true;
    }

    private void changePw() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.changepin, null);
        TextView cancel = view.findViewById(R.id.changepin_cancel);
        TextView sure = view.findViewById(R.id.changepin_sure);
        final EditText newPin1 = view.findViewById(R.id.changepin_first);
        final EditText newPin2 = view.findViewById(R.id.changepin_comfirm);
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
                SharedPreferences sharedPreferences = getSharedPreferences("box", MODE_PRIVATE);
                String oldPin = sharedPreferences.getString("password", "");
                String pin1 = newPin1.getText().toString();
                String pin2 = newPin2.getText().toString();
                if (pin1.isEmpty() && pin2.isEmpty()) {
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, "PIN码不能为空", Toast.LENGTH_SHORT).show();
                } else if (pin1.equals(oldPin)) {
                    Log.d("MainActivity", "oldPin is " + oldPin);
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, "PIN码未改动", Toast.LENGTH_SHORT).show();
                } else if (!(pin1.length() == 4)) {
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, "PIN码长度错误", Toast.LENGTH_SHORT).show();
                } else if (!pin1.equals(pin2)) {
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, "PIN码密码不一致", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("password", pin1);
                    editor.commit();
                    Toast.makeText(MainActivity.this, "PIN码修改成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initBox() {
        dbHelper = new MyDatabaseHelper(this, "PwBox.db", null, 1);
        listView = findViewById(R.id.list_view);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        BoxAdapter adapter = new BoxAdapter(MainActivity.this, R.layout.pd_item, boxList);
        Cursor cursor = db.query("PwBox", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String account = cursor.getString(cursor.getColumnIndex("account"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                Box box = new Box(title, account, password, R.drawable.a);
                boxList.add(box);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}

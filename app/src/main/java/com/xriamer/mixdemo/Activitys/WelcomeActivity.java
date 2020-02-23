package com.xriamer.mixdemo.Activitys;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.xriamer.mixdemo.Adapter.ViewPagerAdapter;
import com.xriamer.mixdemo.R;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ViewPager vp;
    private List<View> views;
    private ViewPagerAdapter adapter;
    private Button button;
    private ImageView dots[];
    private int ids[] = {R.id.iv1, R.id.iv2, R.id.iv3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        initView();
        initDots();
    }

    private void initDots() {
        dots = new ImageView[views.size()];
        for (int i = 0; i < ids.length; i++) {
            dots[i] = findViewById(ids[i]);
        }
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<>();
        views.add(inflater.inflate(R.layout.layout_guide_one, null));
        views.add(inflater.inflate(R.layout.layout_guide_two, null));
        views.add(inflater.inflate(R.layout.layout_guide_three, null));

        adapter = new ViewPagerAdapter(views, this);
        button = views.get(2).findViewById(R.id.btn_start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, InitPdActivity.class);
                startActivity(intent);
                finish();
            }
        });
        vp = findViewById(R.id.view_pager);
        vp.setAdapter(adapter);//将vpAdapter与vp控件绑定
        vp.addOnPageChangeListener(this);//添加回调
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        for (int i = 0; i < ids.length; i++) {
            if (position == i) {
                dots[i].setImageResource(R.drawable.gray);
            } else {
                dots[i].setImageResource(R.drawable.black);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

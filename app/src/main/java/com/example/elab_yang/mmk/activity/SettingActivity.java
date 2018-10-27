package com.example.elab_yang.mmk.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.elab_yang.mmk.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setToolbar();
        setStatusbar();
        set();
    }

    public void setToolbar() {
        Toolbar mytoolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setTitle("");
    }

    public void setStatusbar() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    public void set() {
        CardView card_view1 = (CardView) findViewById(R.id.card_view1);
        card_view1.setOnClickListener(this);
        CardView card_view2 = (CardView) findViewById(R.id.card_view2);
        card_view2.setOnClickListener(this);
        CardView card_view3 = (CardView) findViewById(R.id.card_view3);
        card_view3.setOnClickListener(this);
        CardView card_view4 = (CardView) findViewById(R.id.card_view4);
        card_view4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_view1:
                // 개인정보 입력
                Toast.makeText(getApplicationContext(), "개인정보 수정", Toast.LENGTH_SHORT).show();
                break;
            case R.id.card_view2:
                // Database 삭제
                Toast.makeText(getApplicationContext(), "Database 삭제", Toast.LENGTH_SHORT).show();
                break;
            case R.id.card_view3:
                // 앱 평가하기
                Toast.makeText(getApplicationContext(), "앱 평가하기", Toast.LENGTH_SHORT).show();
                break;
            case R.id.card_view4:
                // 앱 소개하기
                Toast.makeText(getApplicationContext(), "앱 소개하기", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

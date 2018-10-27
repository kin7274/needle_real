package com.example.elab_yang.mmk.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.example.elab_yang.mmk.R;

public class SplashActivity extends AppCompatActivity {
    Handler handler = new Handler();
    Runnable r = () -> {
        // 2초 뒤에 다음화면(MainActivity)으로 넘어간다
        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if (pref.getBoolean("activity_executed", false)) {
            // 고인물. 디바이스스캔 다음에 할게요를 누른 당신!!
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // 디바이스부터 연결하시죠
            Intent intent = new Intent(SplashActivity.this, DeviceScanActivity.class);
            startActivity(intent);
            finish();
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // 상태바 제거
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // lottie
        LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        animationView.loop(true);
        animationView.playAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 2초 뒤에 Runnable 객체 수행
        handler.postDelayed(r, 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 예약 취소
        handler.removeCallbacks(r);
    }
}

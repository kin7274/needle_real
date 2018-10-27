package com.example.elab_yang.mmk.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elab_yang.mmk.R;

public class ViewInsulinActivity extends AppCompatActivity {
    private final static String TAG = ViewInsulinActivity.class.getSimpleName();
    String[] data_detail = {"", "", "", ""};
    String[] data_detail2 = {"", "", "", ""};
    // data_detail[0] = 품목;
    // data_detail[1] = 하위 품명;
    // data_detail[2] = 단위;
    // data_detail[3] = 투약시간;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_insulin);
        setToolbar();
        setStatusbar();
        //
        TextView text11 = (TextView) findViewById(R.id.text11);
        TextView text12 = (TextView) findViewById(R.id.text12);
        TextView text13 = (TextView) findViewById(R.id.text13);
        TextView text14 = (TextView) findViewById(R.id.text14);
        // 2
        TextView text21 = (TextView) findViewById(R.id.text21);
        TextView text22 = (TextView) findViewById(R.id.text22);
        TextView text23 = (TextView) findViewById(R.id.text23);
        TextView text24 = (TextView) findViewById(R.id.text24);
        //
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String set_data = "";
        String AAAA = null;
        try {
            AAAA = pref.getString("SET_DATA", set_data);
            if (AAAA.contains("%")) {
                // 이도류
                String[] AAAAA = AAAA.split("%");
                // AAAAA[0] = 1번;
                // AAAAA[1] = 21번;
                data_detail = AAAAA[0].split("#");
//                Log.d(TAG, "[0] = " + data_detail[0]);  // 초속효성
//                Log.d(TAG, "[1] = " + data_detail[1]);  // 애피드라
//                Log.d(TAG, "[2] = " + data_detail[2]);  // 30
//                Log.d(TAG, "[3] = " + data_detail[3]);  // 아침식전
                text11.setText(data_detail[0]);
                text12.setText(data_detail[1]);
                text13.setText(data_detail[2]);
                text14.setText(data_detail[3]);

                data_detail2 = AAAAA[1].split("#");
//                Log.d(TAG, "[0] = " + data_detail2[0]);  //
//                Log.d(TAG, "[1] = " + data_detail2[1]);
//                Log.d(TAG, "[2] = " + data_detail2[2]);
//                Log.d(TAG, "[3] = " + data_detail2[3]);
                text21.setText(data_detail2[0]);
                text22.setText(data_detail2[1]);
                text23.setText(data_detail2[2]);
                text24.setText(data_detail2[3]);
            } else {
                // 일도류
                data_detail = AAAA.split("#");
//                Log.d(TAG, "[0] = " + data_detail[0]);
//                Log.d(TAG, "[1] = " + data_detail[1]);
//                Log.d(TAG, "[2] = " + data_detail[2]);
//                Log.d(TAG, "[3] = " + data_detail[3]);
                text11.setText(data_detail[0]);
                text12.setText(data_detail[1]);
                text13.setText(data_detail[2]);
                text14.setText(data_detail[3]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // TODO 스낵바로 알림
            Toast.makeText(getApplicationContext(), "인슐린을 설정해주세요!", Toast.LENGTH_SHORT).show();
            finish();
        }
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

}

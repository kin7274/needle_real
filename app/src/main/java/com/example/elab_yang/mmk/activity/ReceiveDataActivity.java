package com.example.elab_yang.mmk.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.example.elab_yang.mmk.R;
import com.example.elab_yang.mmk.adapter.MyRecyclerAdapter;
import com.example.elab_yang.mmk.model.CardItem;
import com.example.elab_yang.mmk.model.DB;

import java.util.ArrayList;
import java.util.List;

public class ReceiveDataActivity extends AppCompatActivity {
    private final static String TAG = ReceiveDataActivity.class.getSimpleName();

    DB db;
    SQLiteDatabase sql;

    String data = "";
    String abc = "";
    List<CardItem> lists;
    private MyRecyclerAdapter mAdapter;
    RecyclerView recycler_view;
    String[] data_detail = {"", "", "", ""};
    String[] data_detail2 = {"", "", "", ""};
    // data_detail[0] = 품목;
    // data_detail[1] = 하위 품명;
    // data_detail[2] = 단위;
    // data_detail[3] = 투약시간;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getdb);
//        setRecyclerView();

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        int i_start = pref.getInt("INDEX", 0);
        Log.d(TAG, "i_start = " + i_start);
        Log.d(TAG, i_start + "번째부터 블루투스값 받으면 돼");

        String set_data = "";
        String AAAA = null;
        try {
            AAAA = pref.getString("SET_DATA", set_data);
            if (AAAA.contains("%")) {
                // 이도류
                Log.d(TAG, "이도류");
                String[] AAAAA = AAAA.split("%");
                // AAAA[0] = 내가 설정한 1번;
                // AAAA[1] = 내가 설정한 2번;
                data_detail = AAAAA[0].split("#");

                data_detail2 = AAAAA[1].split("#");

                // 이제 구분해보자
                Log.d(TAG, "1[3] = " + data_detail[3]);
                // data_detail[3] = 1번 투약시간
                Log.d(TAG, "2[3] = " + data_detail2[3]);
                // data_detail2[3] = 2번 투약시간

            } else {
                // 일도류
                Log.d(TAG, "일도류");
                data_detail = AAAA.split("#");
                Log.d(TAG, "[0] = " + data_detail[0]);
                Log.d(TAG, "[1] = " + data_detail[1]);
                Log.d(TAG, "[2] = " + data_detail[2]);
                Log.d(TAG, "[3] = " + data_detail[3]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        db = new DB(this);
        Intent intent = getIntent();
        data = intent.getStringExtra("BLE");
        // & = end bit로 구분
        Log.d(TAG, "data = " + data);

        int i = getCharNumber(data, '&');
        int i_end = getCharNumber(data, '&');

        ////////////////////////////////////
        // i를 캐시에 저장하자
        // BLE 전송 인덱스야, 책갈피
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("INDEX", i_end);
        Log.d(TAG, "INDEX = " + i_end);
        editor.apply();
        ////////////////////////////////////

        Log.d(TAG, "몇개의 데이터가 있을까? " + i);
        String[] str = data.split("&");
        data = "";


//        Log.d(TAG, "str[0] = " + str[0]);
//        Log.d(TAG, "str[1] = " + str[1]);
//        Log.d(TAG, "str[2] = " + str[2]);
//        Log.d(TAG, "str[3] = " + str[3]);
//        Log.d(TAG, "str[4] = " + str[4]);
//        Log.d(TAG, "str[5] = " + str[5]);

        sql = db.getWritableDatabase();
//        db.onUpgrade(sql, 1, 2);

//        for (int y = 0; y < i; y++) {
        for (int y = i_start; y < i; y++) {
            Log.d(TAG, "지금 " + (y + 1) + "번째 진행중");
            Log.d(TAG, "str[y] 전체 = " + str[y]);
            Log.d(TAG, "str[y] 년도 = " + str[y].substring(0, 4));
            Log.d(TAG, "str[y] 월   = " + str[y].substring(4, 6));
            Log.d(TAG, "str[y] 일   = " + str[y].substring(6, 8));
            Log.d(TAG, "str[y] 시   = " + str[y].substring(8, 10));
            Log.d(TAG, "str[y] 분   = " + str[y].substring(10, 12));

            abc = str[y].substring(0, 4) + "-" + str[y].substring(4, 6) + "-" + str[y].substring(6, 8) + "-" + str[y].substring(8, 10) + "-" + str[y].substring(10, 12);
            //                   년도                       월                             일                             시                               분
//            Log.d(TAG, "지금 " + (y+1) + "번째 진행중");

            // str[y].substring(8, 10) = 이게 시간값인데
            int hh = Integer.parseInt(str[y].substring(8, 10));
            // 지금은 hh시입니다!
            String ddiyong = what_hh(hh);
            // 비교
            if (data_detail[3].equals(data_detail2[3])) {
                // 둘이 한 세트구나?
                // 한개로만 비교할게
                if (ddiyong.equals(data_detail[3])) {
                    // 둘다 1번
                    setDB(abc, data_detail[0] + "," + data_detail2[0], data_detail[1] + "," + data_detail2[1], data_detail[2] + "," + data_detail2[2], data_detail2[3]);
                } else {
                    // 그럼 뭐임..
                    // TODO 예외처리
                    setDB(abc, null, null, null, null);
                }
            } else {
                if (ddiyong.equals(data_detail[3])) {
                    // 1번 약이구나
                    setDB(abc, data_detail[0], data_detail[1], data_detail[2], data_detail[3]);

                } else if (ddiyong.equals(data_detail2[3])) {
                    // 2번 약이구나
                    setDB(abc, data_detail2[0], data_detail2[1], data_detail2[2], data_detail2[3]);
                } else {
                    // 둘다 안썻네... 뭐임그럼..
                    // TODO 예외처리
                    setDB(abc, null, null, null, null);
                }
            }

            abc = "";
            data = "";

//            int str_long = str.length;
//            for(int z=0; z<str_long; z++) {
//                str[z] = "";
//            }
        }

//            lists.add(new CardItem2(abc[0], abc[1], abc[2], abc[3], abc[4], abc[5]));
//            mAdapter.notifyDataSetChanged();
//            setDB(abc[0], data_detail[0], data_detail[1], data_detail[2], data_detail[3]);
    }

    // 시간에 따른 식사상태 구분;
    // 형식 : 00 ~ 24시
    // 21-05(8h) : 취침전;
    // 05-11(6h) : 아침식전;
    // 11-16(5h) : 점심식전;
    // 16-21(5h) : 저녁식전;
    public String what_hh(int hh) {
        if ((hh >= 05) && (hh < 11)) {
            return "아침식전";
        } else if ((hh >= 11) && (hh < 16)) {
            return "점심식전";
        } else if ((hh >= 16) && (hh < 21)) {
            return "저녁식전";
        } else {
            return "취침전";
        }
    }

//    public void setRecyclerView() {
//        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
//        recycler_view.setHasFixedSize(false);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
//        recycler_view.setLayoutManager(layoutManager);
//        try {
//            lists = new ArrayList<>();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        mAdapter = new MyRecyclerAdapter(lists);
//        recycler_view.setAdapter(mAdapter);
//    }

    // 특정문자 반복 갯수 확인
    int getCharNumber(String str, char c) {
        int cnt = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c)
                cnt++;
        }
        return cnt;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }

    public void setDB(String str1, String str2, String str3, String str4, String str5) {
        sql = db.getWritableDatabase();
        sql.execSQL(String.format("INSERT INTO tb_needle VALUES(null, '%s', '%s', '%s', '%s', '%s')", str1, str2, str3, str4, str5));
        Toast.makeText(getApplicationContext(), "동기화햇구요", Toast.LENGTH_SHORT).show();
        sql.close();
        finish();
    }
}

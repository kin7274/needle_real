package com.example.elab_yang.mmk.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.elab_yang.mmk.R;

public class TwoInsulinActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "TwoInsulinActivity";
    Context mContext;
    // 1, 2번 약 배열
    String[] set1 = {"", "", "", ""};
    String[] set2 = {"", "", "", ""};
    //
    TextView text11, text12, text13, text14;
    TextView text21, text22, text23, text24;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twoinsulin);
        mContext = this;
        set();
    }

    public void set(){
        text11 = (TextView) findViewById(R.id.text11);
        text12 = (TextView) findViewById(R.id.text12);
        text13 = (TextView) findViewById(R.id.text13);
        text14 = (TextView) findViewById(R.id.text14);
        text11.setOnClickListener(this);
        text12.setOnClickListener(this);
        text13.setOnClickListener(this);
        text14.setOnClickListener(this);
        text21 = (TextView) findViewById(R.id.text21);
        text22 = (TextView) findViewById(R.id.text22);
        text23 = (TextView) findViewById(R.id.text23);
        text24 = (TextView) findViewById(R.id.text24);
        text21.setOnClickListener(this);
        text22.setOnClickListener(this);
        text23.setOnClickListener(this);
        text24.setOnClickListener(this);
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //// 1번 설정에 관해
        // 1번 종류
        // spinner01 : 인슐린 종류(5)
        final String[] items = {getResources().getString(R.string.insulin_name), getResources().getString(R.string.insulin_name1), getResources().getString(R.string.insulin_name2), getResources().getString(R.string.insulin_name3), getResources().getString(R.string.insulin_name4)};
        // spinner02 : 하위 품목
        // 초속효성
        final String[] items1 = {getResources().getString(R.string.insulin_name0_0), getResources().getString(R.string.insulin_name0_1), getResources().getString(R.string.insulin_name0_2)};
        // 속효성
        final String[] items2 = {getResources().getString(R.string.insulin_name1_0)};
        // 중간형
        final String[] items3 = {getResources().getString(R.string.insulin_name2_0), getResources().getString(R.string.insulin_name2_1), getResources().getString(R.string.insulin_name2_2), getResources().getString(R.string.insulin_name2_3)};
        // 혼합형
        final String[] items4 = {getResources().getString(R.string.insulin_name3_0), getResources().getString(R.string.insulin_name3_1), getResources().getString(R.string.insulin_name3_2), getResources().getString(R.string.insulin_name3_3)};
        // 지속형
        final String[] items5 = {getResources().getString(R.string.insulin_name4_0), getResources().getString(R.string.insulin_name4_1)};
        // spinner03 : 식사상태
        final String[] items6 = {getResources().getString(R.string.state_0_0), getResources().getString(R.string.state_0_1), getResources().getString(R.string.state_0_2), getResources().getString(R.string.state_0_3)};
        // 임시사용 1번
        final String[] items99;
        // 임시사용 2번
        final String[] items999;
        // 스위치문
        switch (v.getId()) {
            case R.id.text11:
                // 품목
                ArrayAdapter<String> adapter11 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, items);
                adapter11.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                // 리스트 목록 클릭 이벤트
                AlertDialog.Builder builder11 = new AlertDialog.Builder(this)
                        .setTitle("1-1. 종류")
                        .setNegativeButton("NO", null)
                        .setItems(items, (dialog, position) -> {
//                                Toast.makeText(getApplicationContext(), "선택한 값 : " + items[position], Toast.LENGTH_SHORT).show();
                            set1[0] = items[position];
                            // 메인 텍스트에 값 넣음
                            text11.setText(items[position]);
                        });
                builder11.create();
                builder11.show();
                break;
            case R.id.text12:
                // 하위품목
                String mykinds = set1[0];
                if (mykinds.equals("초속효성")) {
                    items99 = items1;
                } else if (mykinds.equals("속효성")) {
                    items99 = items2;
                } else if (mykinds.equals("중간형")) {
                    items99 = items3;
                } else if (mykinds.equals("혼합형")) {
                    items99 = items4;
                } else {
                    items99 = items5;
                }
                ArrayAdapter<String> adapter12 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, items99);
                adapter12.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                // 리스트 목록 클릭 이벤트
                AlertDialog.Builder builder12 = new AlertDialog.Builder(this)
                        .setTitle("1-2. 하위품명")
                        .setNegativeButton("NO", null)
                        .setItems(items99, (dialog, position) -> {
//                                Toast.makeText(getApplicationContext(), "선택한 값 : " + items99[position], Toast.LENGTH_SHORT).show();
                            set1[1] = items99[position];
                            text12.setText(items99[position]);
                        });
                builder12.create();
                builder12.show();
                break;
            case R.id.text13:
                // 단위
                final EditText et = new EditText(this);
                AlertDialog.Builder builder13 = new AlertDialog.Builder(this)
                        // 숫자만 입력가능하도록, 키패드를 띄울까?
                        .setTitle("1-3. 단위")
                        .setPositiveButton("저장", (dialog, position) -> {
                            set1[2] = et.getText().toString();
                            // 입력한 값이 숫자인지 확인
//                                if (Pattern.matches("^[0-9]+$", settingdata1[2])) {
                            // 숫자인 경우
                            text13.setText(et.getText().toString());
//                                } else {
                            // 숫자가 아니네?
//                                    Toast.makeText(getApplicationContext(), "숫자만 입력해주세요", Toast.LENGTH_SHORT).show();
//                                }
                        })
                        .setView(et);
                builder13.create();
                builder13.show();
                break;
            case R.id.text14:
                // 투약시간
                ArrayAdapter<String> adapter14 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, items6);
                adapter14.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                AlertDialog.Builder builder14 = new AlertDialog.Builder(this)
                        .setTitle("1-4. 식사상태")
                        .setNegativeButton("NO", null)
                        .setItems(items6, (dialog, position) -> {
//                                Toast.makeText(getApplicationContext(), "선택한 값 : " + items6[position], Toast.LENGTH_SHORT).show();
                            set1[3] = items6[position];
                            text14.setText(items6[position]);
                        });
                builder14.create();
                builder14.show();
                break;
            // 이제 2번째 약 설정
            case R.id.text21:
                // 품목
                ArrayAdapter<String> adapter21 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, items);
                adapter21.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                // 리스트 목록 클릭 이벤트
                AlertDialog.Builder builder21 = new AlertDialog.Builder(this)
                        .setTitle("2-1. 종류")
                        .setNegativeButton("NO", null)
                        .setItems(items, (dialog, position) -> {
//                                Toast.makeText(getApplicationContext(), "선택한 값 : " + items[position], Toast.LENGTH_SHORT).show();
                            set2[0] = items[position];
                            // 메인 텍스트에 값 넣음
                            text21.setText(items[position]);
                        });
                builder21.create();
                builder21.show();
                break;
            case R.id.text22:
                // 하위품목
                String mykinds2 = set2[0];
                if (mykinds2.equals("초속효성")) {
                    items999 = items1;
                } else if (mykinds2.equals("속효성")) {
                    items999 = items2;
                } else if (mykinds2.equals("중간형")) {
                    items999 = items3;
                } else if (mykinds2.equals("혼합형")) {
                    items999 = items4;
                } else {
                    items999 = items5;
                }
                ArrayAdapter<String> adapter22 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, items999);
                adapter22.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                // 리스트 목록 클릭 이벤트
                AlertDialog.Builder builder22 = new AlertDialog.Builder(this)
                        .setTitle("2-2. 하위품명")
                        .setNegativeButton("NO", null)
                        .setItems(items999, (dialog, position) -> {
//                                Toast.makeText(getApplicationContext(), "선택한 값 : " + items99[position], Toast.LENGTH_SHORT).show();
                            set2[1] = items999[position];
                            text22.setText(items999[position]);
                        });
                builder22.create();
                builder22.show();
                break;
            case R.id.text23:
                // 단위
                final EditText et2 = new EditText(this);
                AlertDialog.Builder builder23 = new AlertDialog.Builder(this)
                        // 숫자만 입력가능하도록, 키패드를 띄울까?
                        .setTitle("2-3. 단위")
                        .setPositiveButton("저장", (dialog, position) -> {
                            set2[2] = et2.getText().toString();
                            // 입력한 값이 숫자인지 확인
//                                if (Pattern.matches("^[0-9]+$", settingdata1[2])) {
                            // 숫자인 경우
                            text23.setText(et2.getText().toString());
//                                } else {
                            // 숫자가 아니네?
//                                    Toast.makeText(getApplicationContext(), "숫자만 입력해주세요", Toast.LENGTH_SHORT).show();
//                                }
                        })
                        .setView(et2);
                builder23.create();
                builder23.show();
                break;
            case R.id.text24:
                // 투약시간
                ArrayAdapter<String> adapter24 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, items6);
                adapter24.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                AlertDialog.Builder builder24 = new AlertDialog.Builder(this)
                        .setTitle("2-4. 식사상태")
                        .setNegativeButton("NO", null)
                        .setItems(items6, (dialog, position) -> {
//                                Toast.makeText(getApplicationContext(), "선택한 값 : " + items6[position], Toast.LENGTH_SHORT).show();
                            set2[3] = items6[position];
                            text24.setText(items6[position]);
                        });
                builder24.create();
                builder24.show();
                break;
            case R.id.button1:
                // 저장
                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                String set_data = set1[0] + "#" + set1[1] + "#" + set1[2] + "#" + set1[3] + "%" + set2[0] + "#" + set2[1] + "#" + set2[2] + "#" + set2[3];
                editor.putString("SET_DATA", set_data);
                Log.d(TAG, "set_data = " + set_data);
                editor.apply();
                finish();
                break;
        }
    }
}

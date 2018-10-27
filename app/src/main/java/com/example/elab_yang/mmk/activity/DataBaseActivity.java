package com.example.elab_yang.mmk.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.elab_yang.mmk.R;
import com.example.elab_yang.mmk.adapter.MyRecyclerAdapter;
import com.example.elab_yang.mmk.model.CardItem;
import com.example.elab_yang.mmk.model.DB;
import com.example.elab_yang.mmk.model.EventCard;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class DataBaseActivity extends AppCompatActivity {
    private final static String TAG = DataBaseActivity.class.getSimpleName();
    Context mContext;
    DB db;
    SQLiteDatabase sql;

    Button add_btn;

    String data;
    //    String abc[] = {"", "", "", "", "", "", ""};
    List<CardItem> lists;
    private MyRecyclerAdapter mAdapter;
    RecyclerView recycler_view;

    EventBus bus = EventBus.getDefault();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        bus.register(this);
        mContext = this;
        setRecyclerView();
        add_btn = (Button) findViewById(R.id.add_btn);
        add_btn.setOnClickListener(v -> {
//            selectDialog();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(DataBaseActivity.this)
                    .inflate(R.layout.edit_box, null, false);
            builder.setView(view);

            final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
            final EditText edit1 = (EditText) view.findViewById(R.id.edit1);
            final EditText edit2 = (EditText) view.findViewById(R.id.edit2);
            final EditText edit3 = (EditText) view.findViewById(R.id.edit3);
            final EditText edit4 = (EditText) view.findViewById(R.id.edit4);
            final EditText edit5 = (EditText) view.findViewById(R.id.edit5);
            ButtonSubmit.setText("삽입");
            final AlertDialog dialog = builder.create();
            ButtonSubmit.setOnClickListener(v1 -> {
                String strEdit1 = edit1.getText().toString();
                String strEdit2 = edit2.getText().toString();
                String strEdit3 = edit3.getText().toString();
                String strEdit4 = edit3.getText().toString();
                String strEdit5 = edit3.getText().toString();
                // 디뽈트값
                lists.add(new CardItem(setImage(strEdit1), strEdit1, strEdit2, strEdit3, strEdit4, strEdit5, setImage2(strEdit5)));
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            });

            dialog.show();
        });
        db = new DB(this);
        getDB();
    }

    public void setRecyclerView() {
        // 객체 생성
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 반대로 쌓기
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recycler_view.setLayoutManager(layoutManager);
        // 배열 null 예외처리
        try {
            lists = new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAdapter = new MyRecyclerAdapter(lists);
//        mAdapter.setOnClickListener((MyRecyclerAdapter.MyRecyclerViewClickListener) this);
        recycler_view.setAdapter(mAdapter);
    }

    public void getDB() {
        sql = db.getReadableDatabase();
        // 화면 clear
        data = "";
        Cursor cursor;
        lists.clear();
        cursor = sql.rawQuery("select*from tb_needle", null);
        while (cursor.moveToNext()) {
            data += cursor.getString(0) + ","
                    + cursor.getString(1) + ","
                    + cursor.getString(2) + ","
                    + cursor.getString(3) + ","
                    + cursor.getString(4) + ","
                    + cursor.getString(5) + "\n";
//            cursor.getString(2) = 인슐린 종류;
            Log.d(TAG, "약값은 " + cursor.getString(2));
            lists.add(new CardItem(setImage(cursor.getString(2)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), setImage2(cursor.getString(5))));
        }
        mAdapter.notifyDataSetChanged();
        cursor.close();
        sql.close();
        Toast.makeText(getApplicationContext(), "조회하였습니다.", Toast.LENGTH_SHORT).show();
    }

    public int setImage(String str) {
        if (str.equals("초속효성")) {
            return R.drawable.red;
        } else if (str.equals("속효성")) {
            return R.drawable.bae;
        } else if (str.equals("중간성")) {
            return R.drawable.green;
        } else if (str.equals("지속성")) {
            return R.drawable.yellow;
        } else if (str.equals("혼합형")) {
            return R.drawable.blue;
        } else {
            return R.drawable.ic_launcher_background;
        }
    }

    public int setImage2(String str) {
        if (str.equals("아침식전")) {
            return R.drawable.red1;
        } else if (str.equals("점심식전")) {
            return R.drawable.blue1;
        } else if (str.equals("저녁식전")) {
            return R.drawable.bae1;
        } else if (str.equals("취침전")) {
            return R.drawable.yellow1;
        } else {
            return R.drawable.ic_launcher_background;
        }
    }

    public void set_setDB() {
        int cnt = lists.size();
//        Toast.makeText(getApplicationContext(), "cnt = " + cnt, Toast.LENGTH_SHORT).show();
        sql = db.getWritableDatabase();
        db.onUpgrade(sql, 1, 2);
        for (int i = 0; i < cnt; i++) {
//            Log.d(TAG, i + " = " + lists.get(i).getDate());
//            Log.d(TAG, i + " = " + lists.get(i).getTime());
//            Log.d(TAG, i + " = " + lists.get(i).getSpeed());
//            Log.d(TAG, i + " = " + lists.get(i).getDistance());
//            Log.d(TAG, i + " = " + lists.get(i).getBpm());
//            Log.d(TAG, i + " = " + lists.get(i).getKcal());
            setDB(lists.get(i).getTime(), lists.get(i).getKind(), lists.get(i).getName(), lists.get(i).getUnit(), lists.get(i).getState());

        }
    }

    // DB에 저장하는 메서드
    public void setDB(String str1, String str2, String str3, String str4, String str5) {
        sql = db.getWritableDatabase();
        sql.execSQL(String.format("INSERT INTO tb_needle VALUES(null, '%s', '%s', '%s', '%s', '%s')", str1, str2, str3, str4, str5));
        sql.close();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "뒤로가기버튼 누름", Toast.LENGTH_SHORT).show();
        showDialog("골라", "저장할까?");
    }


    public void showDialog(String title, String context) {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this);
        dialog.setTitle(title)
                .setMessage(context)
                .setPositiveButton("YES", (dialogInterface, which) -> {
                    // 새로고침 한번 하겠다는 뜻
                    DataBaseActivity.this.set_setDB();
                    DataBaseActivity.this.finish();
                })
                .setNegativeButton("NO", (dialogInterface, which) -> {
                    // 그냥 나가겠다는 뜻
                    finish();
                });
        dialog.create();
        dialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        bus.unregister(this);
        super.onStop();

    }

    @Subscribe
    public void getEventFromAdapter(EventCard event) {

        Log.e(TAG, "getEventFromAdapter: " + event.getKind() + event.getName() + event.getTime() + event.getPosistion());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(DataBaseActivity.this).inflate(R.layout.edit_box, null, false);
        builder.setView(view);
        final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
        final EditText edit1 = (EditText) view.findViewById(R.id.edit1);
        final EditText edit2 = (EditText) view.findViewById(R.id.edit2);
        final EditText edit3 = (EditText) view.findViewById(R.id.edit3);
        final EditText edit4 = (EditText) view.findViewById(R.id.edit4);
        final EditText edit5 = (EditText) view.findViewById(R.id.edit5);
        ButtonSubmit.setText("삽입");
        final AlertDialog dialog = builder.create();
        ButtonSubmit.setOnClickListener(v1 -> {
            String strEdit1 = edit1.getText().toString();
            String strEdit2 = edit2.getText().toString();
            String strEdit3 = edit3.getText().toString();
            String strEdit4 = edit4.getText().toString();
            String strEdit5 = edit5.getText().toString();

            // 디뽈트값
//            lists.set(event.getPosistion(), new CardItem(setImage(""), null, null, null, null, null, setImage2("")));
            lists.set(event.getPosistion(), new CardItem(setImage(strEdit1), strEdit1, strEdit2, strEdit3, strEdit4, strEdit5, setImage2(strEdit5)));

            mAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });


        dialog.show();
    }


}

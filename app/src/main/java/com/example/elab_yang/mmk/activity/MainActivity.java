package com.example.elab_yang.mmk.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elab_yang.mmk.R;
import com.example.elab_yang.mmk.adapter.DeviceAdapter;
import com.example.elab_yang.mmk.model.Device;

import java.util.ArrayList;
import java.util.HashSet;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    RecyclerView recyclerView;
    DeviceAdapter deviceAdapter;
    HashSet<Device> deviceDatabase = new HashSet<>();
    ArrayList<Device> deviceArrayList;
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusbar();
        textview = (TextView) findViewById(R.id.textview);
        textview.setVisibility(View.GONE);
        setNavi();
        Paper.init(this);
        setDevice();
    }

    public void setStatusbar() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    // 네비게이션메뉴 설정
    public void setNavi() {
        Toolbar mytoolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setTitle("");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, mytoolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // deviceAdapter 불러오기
    public void setDevice() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        deviceDatabase = Paper.book("device").read("user_device");
        if (deviceDatabase != null) {
            if (deviceDatabase.size() != 0) {
                deviceArrayList = new ArrayList<>(deviceDatabase);
                deviceAdapter = new DeviceAdapter(this, deviceArrayList);
                recyclerView.setAdapter(deviceAdapter);
                for (int i = 0; i < deviceArrayList.size(); i++) {
                    Device device = deviceArrayList.get(i);
                    Log.e(TAG, "onCreate: " + device.getDeviceName() + ", " + device.getDeviceAddress());
                }
            }
        } else {
            Log.e(TAG, "onCreate: " + "등록된 장비 없음");
            textview.setVisibility(View.VISIBLE);
        }
    }

    // 뒤로가기 버튼 클릭시
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // 네비게이션메뉴 클릭 이벤트
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_profile:
                // 호구조사
//                Toast.makeText(getApplicationContext(),"장치 추가", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                break;
            case R.id.nav_view_insulin:
                // 인슐린 보기
//                Toast.makeText(getApplicationContext(),"인슐린 보기", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, ViewInsulinActivity.class));
                break;

            case R.id.nav_add_device:
                // 장치 추가
                Toast.makeText(getApplicationContext(), "장치 추가", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, NeedleScanActivity.class));
                break;
            case R.id.nav_view_database:
                // VIEW DATABASE
                Toast.makeText(getApplicationContext(), "VIEW DATABASE", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, DataBaseActivity.class));
                break;
            case R.id.nav_setting:
                //환경설정
//                Toast.makeText(getApplicationContext(),"환경설정", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;

            case R.id.nav_set_insulin:
                // 인슐린 설정
                Toast.makeText(getApplicationContext(), "인슐린 설정", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, SettingInsulinActivity.class));
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}

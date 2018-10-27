package com.example.elab_yang.mmk.activity;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.elab_yang.mmk.R;
import com.example.elab_yang.mmk.adapter.NeedleScanAdapter;

import java.util.ArrayList;
import java.util.List;

public class NeedleScanActivity extends AppCompatActivity {
    private static final String TAG = "DeviceScanActivity";
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1000;
    private static final int REQUEST_ENABLE_BT = 1;
    private static final long SCAN_PERIOD = 10000; // Stops scanning after 10 seconds.

    RecyclerView recyclerView;
    NeedleScanAdapter adapter;

    ArrayList<BluetoothDevice> bleDeviceList;

    BluetoothManager bluetoothManager;
    BluetoothAdapter bluetoothAdapter;
    BluetoothLeScanner bluetoothLeScanner;

    Button button;
    Handler handler;

    boolean mScanning;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_needle_scan);
        setStatusbar();
        preferences = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        bleDeviceList = new ArrayList<>();
        handler = new Handler();

        checkScanPermission();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        checkBleSupport();
        getBluetoothAdapter();
        checkBluetoothSupport();
        //
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(v -> {
            if (!mScanning) {
                button.setText("STOP");
                bleDeviceList.clear();
                adapter.notifyDataSetChanged();
                scanLeDevice(true);
            } else {
                button.setText("SCAN");
                scanLeDevice(false);
            }
        });
    }

    public void setStatusbar() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
    }


    private void checkBleSupport() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "x", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void getBluetoothAdapter() {
        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
    }

    private void checkBluetoothSupport() {
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "x", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkScanPermission() {
        if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("권한 허용");
            builder.setMessage("위치 권한을 허용해주세욥!");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(dialog -> requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION));
            builder.show();
        }
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    bluetoothLeScanner.stopScan(leScanCallback);
                    button.setText("SCAN");
                }
            }, SCAN_PERIOD);
            mScanning = true;
            startNEWBTLEDiscovery();
        } else {
            mScanning = false;
            bluetoothLeScanner.stopScan(leScanCallback);
        }
    }

    private ScanCallback leScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice device = result.getDevice();
            if (bleDeviceList.size() < 1) {
                bleDeviceList.add(device);
                adapter.notifyDataSetChanged();
            } else {
                boolean flag = true;
                for (int i = 0; i < bleDeviceList.size(); i++) {
                    if (device.getAddress().equals(bleDeviceList.get(i).getAddress())) {
                        flag = false;
                    }
                }
                if (flag) {
                    bleDeviceList.add(device);
                    adapter.notifyDataSetChanged();
                }
            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, " +
                            "this app will not be able to discover BLE Device when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(dialog -> finish());
                    builder.show();
                }
                return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!bluetoothAdapter.isEnabled()) {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
        adapter = new NeedleScanAdapter(bleDeviceList, this);
        recyclerView.setAdapter(adapter);
        scanLeDevice(true);
        button.setText("STOP");
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanLeDevice(false);
        bleDeviceList.clear();
    }

    private void stopBLEDiscovery() {
        if (adapter != null)
            bluetoothLeScanner.stopScan(leScanCallback);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startNEWBTLEDiscovery() {
        bluetoothLeScanner.startScan(getScanFilters(), getScanSettings(), leScanCallback);
    }

    private List<ScanFilter> getScanFilters() {
        List<ScanFilter> allFilters = new ArrayList<>();
        ScanFilter scanFilter0 = new ScanFilter.Builder().setDeviceName("NeedleBT").build();
        allFilters.add(scanFilter0);
        return allFilters;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private ScanSettings getScanSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return new ScanSettings.Builder()
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                    .setMatchMode(ScanSettings.MATCH_MODE_STICKY)
                    .build();
        } else {
            return new ScanSettings.Builder()
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                    .build();
        }
    }
}

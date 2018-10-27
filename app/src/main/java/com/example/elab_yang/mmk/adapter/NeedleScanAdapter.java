package com.example.elab_yang.mmk.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.elab_yang.mmk.R;
import com.example.elab_yang.mmk.activity.MainActivity;
import com.example.elab_yang.mmk.model.Device;

import java.util.ArrayList;
import java.util.HashSet;

import io.paperdb.Paper;

class NeedleScanViewHolder extends RecyclerView.ViewHolder {
    TextView deviceName;
    TextView deviceAddress;
    LinearLayout container;

    public NeedleScanViewHolder(View itemView) {
        super(itemView);
        this.deviceName = itemView.findViewById(R.id.device_name);
        this.deviceAddress = itemView.findViewById(R.id.device_address);
        this.container = itemView.findViewById(R.id.container);
    }
}

public class NeedleScanAdapter extends RecyclerView.Adapter<NeedleScanViewHolder> {
    Context context;
    ArrayList<BluetoothDevice> deviceArrayList;
    HashSet<Device> deviceDatabase = new HashSet<>();
    ArrayList<Device> tmpArrayList;
    boolean flag = false;

    public NeedleScanAdapter(ArrayList<BluetoothDevice> deviceArrayList, Context context) {
        this.deviceArrayList = deviceArrayList;
        this.context = context;
        Paper.init(this.context);
    }

    @NonNull
    @Override
    public NeedleScanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.listitem_device, parent, false);
        return new NeedleScanViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NeedleScanViewHolder holder, int position) {
        holder.deviceName.setTextSize(18.0f);
        final String deviceName = deviceArrayList.get(position).getName();
        final String deviceAddress = deviceArrayList.get(position).getAddress();
        if (deviceName != null && deviceName.length() > 0) {
            holder.deviceName.setText(deviceName);
        } else {
            holder.deviceName.setText("Unknown_device");
        }
        holder.deviceAddress.setText(deviceAddress);

        holder.container.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Check");
            builder.setMessage(deviceName + " 장비를 등록합니다.");
            builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
                if (Paper.book("device").read("user_device") != null) {
                    deviceDatabase = Paper.book("device").read("user_device");
                    tmpArrayList = new ArrayList<>(deviceDatabase);

                    for (Device d : tmpArrayList) {
                        if (d.getDeviceAddress().equals(deviceAddress)) {
                            flag = true;
                            Log.e("디바이스 ", "onBindViewHolder: " + d.getDeviceAddress());
                            Log.e("디바이스  ", "onBindViewHolder: " + "이미 장비 추가되어 있음");
                        } else {
                            flag = false;
                        }
                    }
                    if (!flag) {
                        deviceDatabase.add(new Device(deviceName, deviceAddress));
                        Paper.book("device").write("user_device", deviceDatabase);
                    }
                } else {
                    deviceDatabase.add(new Device(deviceName, deviceAddress));
                    Paper.book("device").write("user_device", deviceDatabase);
                }
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            });
            builder.show();
        });
    }

    @Override
    public int getItemCount() {
        return deviceArrayList.size();
    }
}

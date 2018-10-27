/*  Copyright (C) 2015-2018 Andreas Shimokawa, Carsten Pfeiffer, Daniele
    Gobbetti, Lem Dulfo

    This file is part of Gadgetbridge.

    Gadgetbridge is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Gadgetbridge is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>. */
package com.example.elab_yang.mmk.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elab_yang.mmk.activity.DeviceControlActivity;
import com.example.elab_yang.mmk.R;
import com.example.elab_yang.mmk.activity.AutoReceiveActivity;
import com.example.elab_yang.mmk.model.Device;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.paperdb.Paper;

import static android.content.Context.MODE_PRIVATE;
import static com.example.elab_yang.mmk.model.IntentConst.DEVICEADDRESS;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    private final Context context;
    private List<Device> deviceList;
    private int expandedDevicePosition = RecyclerView.NO_POSITION;
    private ViewGroup parent;
    private static final String TAG = "DeviceAdapter";
    boolean deviceFlag;
    HashSet<Device> deviceDatabase = new HashSet<>();
    ArrayList<Device> deviceArrayList;

    public DeviceAdapter(Context context, List<Device> deviceList) {
        this.context = context;
        this.deviceList = deviceList;
    }

    @NonNull
    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Paper.init(context);
        this.parent = parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        String deviceName = deviceList.get(position).getDeviceName();
        String deviceAddress = deviceList.get(position).getDeviceAddress();
        holder.deviceNameLabel.setText(deviceList.get(position).getDeviceName());
        holder.deviceStatusLabel.setText(deviceList.get(position).getDeviceAddress());

        // 서비스 연결
        holder.fetchActivityData.setOnClickListener(v -> {
            Intent intent = new Intent(context, AutoReceiveActivity.class);
            intent.putExtra(DEVICEADDRESS, deviceAddress);
            context.startActivity(intent);
        });

        // 동기화 -> 로티
        holder.showActivityTracks.setOnClickListener(v -> {
            SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
            String set_data = "";
            String AAAA = "";
            AAAA = pref.getString("SET_DATA", set_data);
            if (AAAA.equals("")) {
                Log.d(TAG, "onBindViewHolder: 설정부터해");
            } else {
                Log.d(TAG, "onBindViewHolder: 설정미리햇구나?");
                Intent intent = new Intent(context, DeviceControlActivity.class);
                intent.putExtra(DEVICEADDRESS, deviceAddress);
                context.startActivity(intent);
            }
        });

        // 장치에 대한 간단한 설명
        holder.deviceInfoView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("투약기");
            builder.setMessage("마법의 스마트니들");
            builder.setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        // 연결장치 제거
        holder.deviceRemove.setOnClickListener(v -> {
            Log.e(TAG, "deviceAddress: --> " + deviceAddress);
            Log.e(TAG, "deviceName: --> " + deviceName);
            deviceDatabase = Paper.book("device").read("user_device");
            deviceArrayList = new ArrayList<>(deviceDatabase);
            for (Device d : deviceArrayList) {
                Log.e(TAG, "deviceArrayList: --> " + d.getDeviceAddress());
                Log.e(TAG, "deviceArrayList: --> " + d.getDeviceName());
            }
            Log.e(TAG, "onBindViewHolder: " + deviceList.get(position).getDeviceName());
            Log.e(TAG, "onBindViewHolder: " + deviceList.indexOf(deviceList.get(position)));
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("장비 삭제하기");
            builder.setMessage("등록하신 장비를 삭제하시겠어요?");
            builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
                Log.e(TAG, "onClick: " + deviceAddress);
                deviceList.remove(deviceList.get(position));
                HashSet<Device> tmpSet = new HashSet<>(deviceList);
                for (Device d : tmpSet) {
                    Log.e(TAG, "tmpSet: --> " + d.getDeviceAddress());
                    Log.e(TAG, "tmpSet: --> " + d.getDeviceName());
                }
                Paper.book("device").delete("user_device");
                Paper.book("device").write("user_device", tmpSet);
                notifyDataSetChanged();
            });
            builder.show();
        });
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardview;

        ImageView deviceImageView;
        TextView deviceNameLabel;
        TextView deviceStatusLabel;

        ImageView fetchActivityData;
        ImageView showActivityTracks;
        ImageView deviceInfoView;
        ImageView deviceRemove;

        ViewHolder(View view) {
            super(view);
            cardview = view.findViewById(R.id.card_view);

            deviceImageView = view.findViewById(R.id.device_image);
            deviceNameLabel = view.findViewById(R.id.device_name);
            deviceStatusLabel = view.findViewById(R.id.device_status);
            fetchActivityData = view.findViewById(R.id.device_action_fetch_activity);
            showActivityTracks = view.findViewById(R.id.device_action_show_activity_tracks);
            deviceInfoView = view.findViewById(R.id.device_info_image);
            deviceRemove = view.findViewById(R.id.device_info_trashcan);
        }
    }
}
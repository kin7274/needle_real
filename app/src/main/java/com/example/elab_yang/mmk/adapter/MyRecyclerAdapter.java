package com.example.elab_yang.mmk.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elab_yang.mmk.R;
import com.example.elab_yang.mmk.model.CardItem;
import com.example.elab_yang.mmk.model.EventCard;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> implements DialogInterface.OnClickListener, OnLongClickListener {
    static Context mContext;
    private final List<CardItem> mDataList;
    EventBus bus;

    public MyRecyclerAdapter(List<CardItem> dataList) {
        mDataList = dataList;
        bus = EventBus.getDefault();
    }

//    public MyRecyclerAdapter(Context mContext, List<CardItem> dataList) {
//        this.mContext = mContext;
//        this.mDataList = dataList;
//    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        CardItem item = mDataList.get(position);

        CardView cardview = holder.cardview;

        ImageView image = holder.image;
        ImageView image2 = holder.image2;

        TextView time = holder.time;
        TextView kind = holder.kind;
        TextView name = holder.name;
        TextView unit = holder.unit;
//        TextView state = holder.state;

        holder.image.setImageResource(item.getImage());

        holder.time.setText(item.getTime());
        holder.kind.setText(item.getKind());
        holder.name.setText(item.getName());
        holder.unit.setText(item.getUnit());
//        holder.state.setText(item.getState());

        holder.image2.setImageResource(item.getImage2());
//        holder.speed.setText(item.getSpeed());
//        holder.distance.setText(item.getDistance());
//        holder.bpm.setText(item.getBpm());
//        holder.kcal.setText(item.getKcal());

        holder.cardview.setOnClickListener(v -> {
            Toast.makeText(cardview.getContext(), "쌔게누르면 지워져", Toast.LENGTH_SHORT).show();

            bus.post(new EventCard(mDataList.get(position).getTime(), mDataList.get(position).getKind(), mDataList.get(position).getName(), mDataList.get(position).getUnit(), mDataList.get(position).getState(), position));

        });

        holder.cardview.setOnLongClickListener(v -> {
            removeAt(position);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void setOnClickListener() {
    }

    public void removeAt(int position) {
        mDataList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDataList.size());
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardview;
        ImageView image, image2;
        TextView time, kind, name, unit, state;

        public ViewHolder(View itemView) {
            super(itemView);
            cardview = (CardView) itemView.findViewById(R.id.cardview);
            //
            image = (ImageView) itemView.findViewById(R.id.image);
            //
            time = (TextView) itemView.findViewById(R.id.time);
            kind = (TextView) itemView.findViewById(R.id.kind);
            name = (TextView) itemView.findViewById(R.id.name);
            unit = (TextView) itemView.findViewById(R.id.unit);
            //
            image2 = (ImageView) itemView.findViewById(R.id.image2);

            cardview.setOnClickListener(v -> {
                Toast.makeText(mContext, "띠용", Toast.LENGTH_SHORT).show();
            });
            itemView.setOnClickListener(v -> {
//                    Toast.makeText(mContext, "머요ㅡㅡ", Toast.LENGTH_LONG).show();
            });
        }
    }
}
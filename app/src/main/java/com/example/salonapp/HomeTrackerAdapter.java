package com.example.salonapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeTrackerAdapter extends RecyclerView.Adapter<HomeTrackerAdapter.ViewHolder> {

    Context context;
    ArrayList<OrderDetail> orderModel;

    //Create constructor for cart adapter
    public HomeTrackerAdapter(Context context, ArrayList<OrderDetail> orderModel){
        this.context = context;
        this.orderModel = orderModel;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mtvOrderNo, mtvOrderTime, mtvOrderStatus;
        //TODO::BUTTON ON CLICK

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            mtvOrderNo = itemView.findViewById(R.id.tvOrderNo);
            mtvOrderTime = itemView.findViewById(R.id.tvOrderTime);
            mtvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
        }

    }


    @NonNull
    @Override
    public HomeTrackerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_track_orcer,parent,false);
        return new HomeTrackerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeTrackerAdapter.ViewHolder holder, int position) {
        holder.mtvOrderNo.setText(orderModel.get(position).getOrderNo());
        holder.mtvOrderTime.setText(orderModel.get(position).getOrderTime());
        holder.mtvOrderStatus.setText(orderModel.get(position).getOrderStatus());
    }

    @Override
    public int getItemCount() {
        return orderModel.size();
    }
}

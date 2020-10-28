package com.example.salonapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        Button mbtnTrackOrder;
        //TODO::BUTTON ON CLICK INTENT TO ANOTHER PAGE

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            mtvOrderNo = itemView.findViewById(R.id.tvOrderNo);
            mtvOrderTime = itemView.findViewById(R.id.tvOrderTime);
            mtvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            mbtnTrackOrder = itemView.findViewById(R.id.btnTrackOrder);
        }

    }


    @NonNull
    @Override
    public HomeTrackerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_track_orcer,parent,false);
        return new HomeTrackerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeTrackerAdapter.ViewHolder holder, int position) {
        holder.mtvOrderNo.setText(orderModel.get(position).getOrderNo());
        holder.mtvOrderTime.setText(orderModel.get(position).getOrderTime());
        holder.mtvOrderStatus.setText(orderModel.get(position).getOrderStatus());

        holder.mbtnTrackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO :: Pass info the new Track order intent
                Toast.makeText(context,holder.mtvOrderStatus.getText().toString()+" ",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderModel.size();
    }
}

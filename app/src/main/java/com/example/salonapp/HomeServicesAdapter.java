package com.example.salonapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeServicesAdapter extends RecyclerView.Adapter<HomeServicesAdapter.ViewHolder> {

    Context context;
    ArrayList<ServicesDetail> serviceModel;

    //Create constructor for cart adapter
    public HomeServicesAdapter(Context context, ArrayList<ServicesDetail> serviceModel){
        this.context = context;
        this.serviceModel = serviceModel;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mtvServiceName, mtvServicePrice, mtvServiceTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            mtvServiceName = itemView.findViewById(R.id.tvServiceName);
            mtvServicePrice = itemView.findViewById(R.id.tvServicePrice);
            mtvServiceTime = itemView.findViewById(R.id.tvServiceTime);
        }

    }

    @NonNull
    @Override
    public HomeServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_features_service,parent,false);
        return new HomeServicesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeServicesAdapter.ViewHolder holder, int position) {
        holder.mtvServiceName.setText(serviceModel.get(position).getServiceName());
        holder.mtvServicePrice.setText(serviceModel.get(position).getServicePrice());
        holder.mtvServiceTime.setText(serviceModel.get(position).getServiceTime());
    }

    @Override
    public int getItemCount() {
        return serviceModel.size();
    }
}

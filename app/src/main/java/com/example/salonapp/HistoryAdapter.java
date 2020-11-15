package com.example.salonapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    Context context;
    //TODO::Adapter for history
    ArrayList<HistoryDetail> historyList;
    //private boolean clicked=true;

    //Create constructor for cart adapter
    public HistoryAdapter(Context context, ArrayList<HistoryDetail> historyList){
        this.context = context;
        this.historyList = historyList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mreceiptID;
        TextView mreceiptPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            mreceiptID = itemView.findViewById(R.id.receiptID);
            mreceiptPrice = itemView.findViewById(R.id.receiptPrice);

        }

    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_single_history,parent,false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        holder.mreceiptID.setText(historyList.get(position).getHistoryID());
        holder.mreceiptPrice.setText(historyList.get(position).getHistoryPrice());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "History Clicked",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
}

package com.example.salonapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class BookServiceAdapter extends RecyclerView.Adapter<BookServiceAdapter.ViewHolder>{

    private String serviceName[];
    private String servicePrice[];

    //Create constructor for cart adapter
    public BookServiceAdapter(String serviceName[],String servicePrice[]){
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mtvBookService, mtvBookSerPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            mtvBookService = itemView.findViewById(R.id.tvBookService);
            mtvBookSerPrice = itemView.findViewById(R.id.tvBookServicePrice);
        }

    }

    @NonNull
    @Override
    public BookServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_book_service,parent,false);
        return new BookServiceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookServiceAdapter.ViewHolder holder, int position) {
        final String name = serviceName[position];
        holder.mtvBookService.setText(name);

        final String price = servicePrice[position];
        holder.mtvBookSerPrice.setText(price);
    }

    @Override
    public int getItemCount() {
        return serviceName.length;
    }
}

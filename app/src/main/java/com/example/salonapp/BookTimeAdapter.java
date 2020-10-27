package com.example.salonapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookTimeAdapter extends RecyclerView.Adapter<BookTimeAdapter.ViewHolder> {
    private ArrayList<String> time;
    private boolean clicked=true;

    //Create constructor for cart adapter
    public BookTimeAdapter(ArrayList<String> time){
        this.time = time;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mivTimeBg;
        TextView mtvBookTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            mtvBookTime = itemView.findViewById(R.id.tvBookTime);
            mivTimeBg = itemView.findViewById(R.id.ivTimeBg);

        }

    }

    @NonNull
    @Override
    public BookTimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_select_timeslot,parent,false);
        return new BookTimeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookTimeAdapter.ViewHolder holder, int position) {
        final String data = time.get(position);
        holder.mtvBookTime.setText(data);

        //When time is chosen
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clicked==true){
                    Toast.makeText(view.getContext(),data + " selected.",Toast.LENGTH_SHORT).show();
                    holder.mivTimeBg.setImageResource(R.drawable.selected_bg);
                    clicked=false;

                    //TODO::Pass time to new Intent
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return time.size();
    }
}

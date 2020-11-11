package com.example.salonapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {

    RecyclerView mrvHistory;
    HistoryAdapter historyAdapter;
    ArrayList<OrderDetail> orderDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        mrvHistory = v.findViewById(R.id.rvHistory);

        orderDetails = new ArrayList<>();
        orderDetails.add(new OrderDetail("#123123123","","","","","","","","",""));

        //Design Horizontal Layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getContext(),LinearLayoutManager.HORIZONTAL,false);
        mrvHistory.setLayoutManager(layoutManager);

        //TODO::History Adapter
        //Set adapter for recycler view
        historyAdapter = new HistoryAdapter(getContext(), orderDetails);
        mrvHistory.setAdapter(historyAdapter);


        return v;
    }
}

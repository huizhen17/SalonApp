package com.example.salonapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {

    RecyclerView mrvHistory;
    HistoryAdapter historyAdapter;
    ArrayList<HistoryDetail> historyList = new ArrayList<>();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userID;
    int counter=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        mrvHistory = v.findViewById(R.id.rvHistory);

        userID = mAuth.getCurrentUser().getUid();
        //Get instant update
        CollectionReference getOrderDB =  db.collection("userDetail").document(userID).collection("orderHistory");
        getOrderDB.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error==null) {
                    if (value.isEmpty()){
                        Toast.makeText(getContext(),"No order found.",Toast.LENGTH_SHORT).show();
                    }else {
                        for (QueryDocumentSnapshot document : value) {
                            retrieveQuery(document.toObject(HistoryDetail.class), value.size());
                        }
                    }
                }else
                    Toast.makeText(getContext(),"Fail to retrieve data.",Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    private void retrieveQuery(HistoryDetail orderDetail, int size) {
        historyList.add(orderDetail);
            counter = size;
            if (historyList.size()==counter){
            historyAdapter = new HistoryAdapter(getContext(), historyList);
                historyAdapter.notifyDataSetChanged();
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                mrvHistory.setLayoutManager(layoutManager);
                mrvHistory.setAdapter(historyAdapter);
            }
        }
    }

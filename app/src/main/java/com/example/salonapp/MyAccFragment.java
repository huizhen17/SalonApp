package com.example.salonapp;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyAccFragment extends Fragment {

    TextView mtvUsername, mtvUserPhone, mtvUserEmail, mtvUserAddress;
    ImageView mivEditAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_acc,container,false);
        mtvUsername = v.findViewById(R.id.tvUserName);
        mtvUserEmail = v.findViewById(R.id.tvUserEmail);
        mtvUserPhone = v.findViewById(R.id.tvUserPhone);
        mtvUserAddress = v.findViewById(R.id.tvUserAddress);
        mivEditAdd = v.findViewById(R.id.ivEditAddress);

        mivEditAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),AddAddress.class);
                startActivity(i);
                Toast.makeText(getContext(),"Edit Address", Toast.LENGTH_SHORT).show();
            }
        });


        return v;
    }
}

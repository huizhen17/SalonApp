package com.example.salonapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class OrderConfirmation extends AppCompatActivity {

    TextView mtvMyOrderNo, mtvMyOrderDate, mtvMyOrderTime, mtvMyOrderAddress, mtvMyOrderAmount, mtvMyOrderService;
    String orderID, date="", time, address="", amount, service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        mtvMyOrderNo = findViewById(R.id.tvMyOrderNo);
        mtvMyOrderDate = findViewById(R.id.tvMyOrderDate);
        mtvMyOrderTime = findViewById(R.id.tvMyOrderTime);
        mtvMyOrderAddress = findViewById(R.id.tvMyOrderAddress);
        mtvMyOrderAmount = findViewById(R.id.tvMyOrderPrice);
        mtvMyOrderService = findViewById(R.id.tvMyOrderService);

        Bundle bundle = getIntent().getExtras();
        date = bundle.getString("date",date);
        time = bundle.getString("time",time);
        address = bundle.getString("address",address);
        service =bundle.getString("serviceName",service);
        amount = bundle.getString("servicePrice",amount);

        mtvMyOrderDate.setText(date);
        mtvMyOrderTime.setText(time);
        mtvMyOrderAddress.setText(address);
        mtvMyOrderService.setText(service);
        mtvMyOrderAmount.setText(amount);




    }



    public void btnOrderConfirm_onClick(View view) {
        Toast.makeText(OrderConfirmation.this,"Order Sent!",Toast.LENGTH_SHORT).show();
    }
}

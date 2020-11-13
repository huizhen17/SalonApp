package com.example.salonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class OrderSummary extends AppCompatActivity {

    //TODO::Nid wait order status = complete to proceed this page

    TextView mtvPayId, mtvPayDate, mtvPayTime, mtvPayAddress, mtvPayPrice, mtvPayService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        mtvPayId = findViewById(R.id.tvPayOrderNo);
        mtvPayDate = findViewById(R.id.tvPayOrderDate);
        mtvPayTime = findViewById(R.id.tvPayOrderTime);
        mtvPayAddress = findViewById(R.id.tvMyOrderAddress);
        mtvPayPrice = findViewById(R.id.tvPayOrderPrice);
        mtvPayService = findViewById(R.id.tvPayOrderService);
    }

    public void btnOrderPay_onClick(View view) {
        //TODO :: CHANGE order status = complete and remove the data from order Detail
        Toast.makeText(OrderSummary.this,"Payment Completed",Toast.LENGTH_SHORT).show();
    }

    public void btnTopUp_onClicked(View view) {
        Toast.makeText(OrderSummary.this,"Top Up",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(OrderSummary.this,TopUpActivity.class);
        startActivity(i);
    }
}

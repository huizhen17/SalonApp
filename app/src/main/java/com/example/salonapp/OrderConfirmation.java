package com.example.salonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class OrderConfirmation extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    TextView mtvMyOrderNo, mtvMyOrderDate, mtvMyOrderTime, mtvMyOrderAddress, mtvMyOrderAmount, mtvMyOrderService;
    String userID,orderID, date="", time="", address="", amount="", service="",latitude="",longitude="",status="",link="",workerID="";

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

        userID = mAuth.getCurrentUser().getUid();

        //Generate random ID from firebase
        DocumentReference ref = db.collection("orderDetail").document();
        orderID = ref.getId();

        Bundle bundle = getIntent().getExtras();
        date = bundle.getString("date",date);
        time = bundle.getString("time",time);
        address = bundle.getString("address",address);
        service =bundle.getString("serviceName",service);
        amount = bundle.getString("servicePrice",amount);
        latitude = bundle.getString("latitude",latitude);
        longitude = bundle.getString("longitude",longitude);

        mtvMyOrderNo.setText(orderID);
        mtvMyOrderDate.setText(date);
        mtvMyOrderTime.setText(time);
        mtvMyOrderAddress.setText(address);
        mtvMyOrderService.setText(service);
        mtvMyOrderAmount.setText(amount);

    }



    public void btnOrderConfirm_onClick(View view) {
        Toast.makeText(OrderConfirmation.this,"Order Sent!",Toast.LENGTH_SHORT).show();
        status = "pending";

        Toast.makeText(OrderConfirmation.this,"Order ID:" + orderID,Toast.LENGTH_SHORT).show();
        OrderDetail orderDetail = new OrderDetail(orderID,date,time,status,amount,latitude,longitude,address,link,workerID);

        //Save order detail to firebase
        DocumentReference documentReference = db.collection("userDetail").document(userID).collection("orderDetail").document(orderID);
        documentReference.set(orderDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OrderConfirmation.this,"Fail to save. Try it later!",Toast.LENGTH_SHORT).show();
            }
        });

        //Passing data back to fragment
        Bundle newBundle = new Bundle();
        newBundle.putString("orderID", orderID);
        HomeFragment objects = new HomeFragment();
        objects.setArguments(newBundle);
        finish();
    }
}

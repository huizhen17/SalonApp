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

import java.util.HashMap;
import java.util.Map;

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

        OrderDetail orderDetail = new OrderDetail(orderID,date,time,status,service,amount,latitude,longitude,address,link,workerID);

        //Save order detail to firebase
        DocumentReference documentReference = db.collection("userDetail").document(userID).collection("currentOrder").document("currentOrder");
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

        //Save order detail for web
        DocumentReference webReference = db.collection("userDetail").document(userID);
        webReference.update("currentOrder",orderID).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OrderConfirmation.this,"Fail to save. Try it later!",Toast.LENGTH_SHORT).show();
            }
        });

        //Save order detail to firebase
        DocumentReference orderReference = db.collection("orderDetail").document(orderID);
        Map<String,Object> user = new HashMap<>();
        user.put("userID",userID);
        orderReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OrderConfirmation.this,"Fail to save. Try it later!",Toast.LENGTH_SHORT).show();
            }
        });

        finish();
    }
}

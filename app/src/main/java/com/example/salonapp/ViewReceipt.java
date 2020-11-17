package com.example.salonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ViewReceipt extends AppCompatActivity {

    //TODO::rating
    //TODO::get data from intent/db
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    TextView mtvReceiptNo, mtvReceiptDate, mtvReceiptTime, mtvReceiptAddress,
            mtvReceiptAmount, mtvReceiptService,mtvWorkerID;
    String userID = mAuth.getCurrentUser().getUid();
    String orderID="", workerID="", orderTime="",orderDate="",orderAddress="",orderService="",orderAmount="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_receipt);

        mtvReceiptNo = findViewById(R.id.tvReceiptNo);
        mtvReceiptDate = findViewById(R.id.tvReceiptDate);
        mtvReceiptTime = findViewById(R.id.tvReceiptTime);
        mtvReceiptAddress = findViewById(R.id.tvReceiptAddress);
        mtvReceiptAmount = findViewById(R.id.tvReceiptPrice);
        mtvReceiptService = findViewById(R.id.tvReceiptService);
        mtvWorkerID = findViewById(R.id.tvWorkerID);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            orderID = bundle.getString("orderID");
            orderTime = bundle.getString("orderTime");
            orderDate = bundle.getString("orderDate");
            orderAddress = bundle.getString("orderAddress");
            orderAmount = bundle.getString("orderAmount");
            orderService = bundle.getString("orderService");
        }

        mtvReceiptNo.setText(orderID);
        mtvReceiptTime.setText(orderTime);
        mtvReceiptDate.setText(orderDate);
        mtvReceiptAddress.setText(orderAddress);
        mtvReceiptAmount.setText(orderAmount);
        mtvReceiptService.setText(orderService);

        final DocumentReference documentReference = db.collection("userDetail").document(userID).collection("currentOrder").document("currentOrder");
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                workerID = documentSnapshot.getString("orderWorkerID");
                mtvWorkerID.setText(workerID);
            }
        });

    }

    public void btnRating_onClick(View view) {

        //Store user order data to historyDetail
        DocumentReference documentReference = db.collection("userDetail").document(userID).collection("orderHistory").document(orderID);
        Map<String,Object> historyData = new HashMap<>();
        historyData.put("historyID",orderID);
        historyData.put("historyWorkerID",workerID);
        historyData.put("historyPrice",orderAmount);
        historyData.put("historyService",orderService);
        documentReference.set(historyData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

        //Remove current order data in userDetail
        DocumentReference delOrderReference = db.collection("userDetail").document(userID).collection("currentOrder").document("currentOrder");
        delOrderReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

        Toast.makeText(ViewReceipt.this,"Service complete",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(ViewReceipt.this,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}

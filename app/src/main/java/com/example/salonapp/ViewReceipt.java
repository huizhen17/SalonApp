package com.example.salonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewReceipt extends AppCompatActivity {

    //TODO::create interface
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
    }
}

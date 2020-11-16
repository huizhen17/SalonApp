package com.example.salonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class OrderSummary extends AppCompatActivity {


    TextView mtvPayId, mtvPayDate, mtvPayTime, mtvPayAddress, mtvPayPrice, mtvPayService;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String orderID, orderStatus, orderTime,orderDate,orderAddress,orderService,orderAmount;
    String userID=mAuth.getCurrentUser().getUid();

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

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            orderID = bundle.getString("orderID");
            orderStatus = bundle.getString("orderStatus");
            orderTime = bundle.getString("orderTime");
        }

        DocumentReference documentReference = db.collection("userDetail").document(userID).collection("currentOrder").document("currentOrder");
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                orderDate = documentSnapshot.getString("orderDate");
                orderAddress = documentSnapshot.getString("orderAddress");
                orderAmount = documentSnapshot.getString("orderPrice");
                orderService = documentSnapshot.getString("orderService");
                mtvPayDate.setText(orderDate);
                mtvPayAddress.setText(orderAddress);
                mtvPayPrice.setText(orderAmount);
                mtvPayService.setText(orderService);
            }
        });

        mtvPayId.setText(orderID);
        mtvPayTime.setText(orderTime);


    }

    public void btnOrderPay_onClick(View view) {
        //TODO :: CHANGE order status = complete and remove the data from order Detail
        Intent i = new Intent(OrderSummary.this,ScanPayment.class);
        i.putExtra("orderID",mtvPayId.getText().toString());
        i.putExtra("orderTime",mtvPayTime.getText().toString());
        i.putExtra("orderDate",mtvPayDate.getText().toString());
        i.putExtra("orderAddress",mtvPayAddress.getText().toString());
        i.putExtra("orderAmount",mtvPayPrice.getText().toString());
        i.putExtra("orderService",mtvPayService.getText().toString());
        startActivity(i);
        //Toast.makeText(OrderSummary.this,"Payment Completed",Toast.LENGTH_SHORT).show();
    }

    public void btnTopUp_onClicked(View view) {
        Toast.makeText(OrderSummary.this,"Top Up",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(OrderSummary.this,TopUpActivity.class);
        startActivity(i);
    }
}

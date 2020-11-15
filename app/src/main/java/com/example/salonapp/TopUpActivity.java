package com.example.salonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TopUpActivity extends AppCompatActivity {

    TextView mtvFive, mtvTen, mtvTwenty, mtvFifty, mtvHundred, mtvTwoHundred;
    TextView mtvCreditAmt, mtvTopUpAmt, mtvTotalAmt;
    EditText metInputAmt;
    Button mbtnDoneTopUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        mtvFive = findViewById(R.id.tvFive);
        mtvTen = findViewById(R.id.tvTen);
        mtvTwenty = findViewById(R.id.tvTwenty);
        mtvFifty = findViewById(R.id.tvFifty);
        mtvHundred = findViewById(R.id.tvHundred);
        mtvTwoHundred = findViewById(R.id.tvTwoHundred);
        mtvCreditAmt = findViewById(R.id.tvCreditAmt);
        mtvTopUpAmt = findViewById(R.id.tvTopUpAmt);
        mtvTotalAmt = findViewById(R.id.tvTotalAmt);
        metInputAmt = findViewById(R.id.etInputAmt);
        mbtnDoneTopUp = findViewById(R.id.btnDoneTopUp);

        mbtnDoneTopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

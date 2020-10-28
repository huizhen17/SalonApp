package com.example.salonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddAddress extends AppCompatActivity {

    TextView mtvHouseNo, mtvHouseBlock, mtvHouseLvl, mtvHouseCondo, mtvHouseGarden, mtvHouseStreet, mtvHousePostcode, mtvHouseCity, mtvHouseState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        mtvHouseNo = findViewById(R.id.tvHouseNo);
        mtvHouseBlock = findViewById(R.id.tvHouseBlock);
        mtvHouseLvl = findViewById(R.id.tvHouseLvl);
        mtvHouseCondo = findViewById(R.id.tvHouseBuilding);
        mtvHouseGarden = findViewById(R.id.tvHouseGarden);
        mtvHouseStreet = findViewById(R.id.tvHouseStreet);
        mtvHousePostcode = findViewById(R.id.tvHousePostcode);
        mtvHouseCity = findViewById(R.id.tvHouseCity);
        mtvHouseState = findViewById(R.id.tvHouseState);

    }

    public void btnSavedAddress_onClick(View view) {
        Toast.makeText(AddAddress.this,"Saved.",Toast.LENGTH_SHORT).show();
    }
}

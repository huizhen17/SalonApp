package com.example.salonapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class BookAppointment extends AppCompatActivity {

    TextView mtvBookDate, mtvBookAddress;
    RecyclerView mrvService;
    RecyclerView mrvTime;
    BookTimeAdapter bookTimeAdapter;
    BookServiceAdapter bookServiceAdapter;
    Dialog addServiceDialog;
    CheckBox mchkBoxCut, mchkBoxStyling, mchkBoxColor, mchkBoxFacial, mchkBoxMakeUp;
    Button mbtnAddService, mbtnCanService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        addServiceDialog = new Dialog(this);

        mtvBookDate = findViewById(R.id.tvBookDate);
        mtvBookAddress = findViewById(R.id.tvBookAddress);

        mrvTime = findViewById(R.id.mrvTimeSlot);
        mrvService = findViewById(R.id.mrvBookServiceInfo);

        String timeslot[] = new String[]{"10.00am","10.30am","11.00am","11.30am","12.00pm","12.30pm","1.30pm","2.00pm","2.30pm",
                                        "3.00pm","3.30pm","4.00pm","4.30pm","5.00pm"};

        //Set adapter for time slot
        bookTimeAdapter = new BookTimeAdapter(timeslot);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
        mrvTime.setLayoutManager(layoutManager);
        mrvTime.setAdapter(bookTimeAdapter);

        String serviceName[] = new String[]{"Hair Cut"};
        String servicePrice[] = new String[]{"35"};

        bookServiceAdapter = new BookServiceAdapter(serviceName,servicePrice);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        mrvService.setLayoutManager(layoutManager1);
        mrvService.setAdapter(bookServiceAdapter);

    }

    public void btnBookApt_onClick(View view) {
        Toast.makeText(BookAppointment.this,"Order Sent!",Toast.LENGTH_SHORT).show();
    }

    /*
        Function for user to add on services features
     */
    public void txtAddService_OnClick(View view) {
        //Display pop out dialog
        addServiceDialog.setContentView(R.layout.activity_service_dialog);
        addServiceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addServiceDialog.show();
        addServiceDialog.setCancelable(false);

        mchkBoxCut = addServiceDialog.findViewById(R.id.chkBoxCut);
        mchkBoxStyling = addServiceDialog.findViewById(R.id.chkBoxStyle);
        mchkBoxColor = addServiceDialog.findViewById(R.id.chkBoxColor);
        mchkBoxFacial = addServiceDialog.findViewById(R.id.chkBoxFace);
        mchkBoxMakeUp = addServiceDialog.findViewById(R.id.chkBoxMakeUp);

        mbtnAddService = addServiceDialog.findViewById(R.id.btnAddService);
        mbtnCanService = addServiceDialog.findViewById(R.id.btnCancelService);

        mbtnAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addServiceDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Service added",Toast.LENGTH_SHORT).show();
            }
        });

        mbtnCanService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addServiceDialog.dismiss();
            }
        });

    }
}

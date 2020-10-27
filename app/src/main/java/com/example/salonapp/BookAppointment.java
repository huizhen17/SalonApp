package com.example.salonapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BookAppointment extends AppCompatActivity {

    TextView mtvBookDate, mtvBookAddress, mtvEmptySlot;
    RecyclerView mrvService;
    RecyclerView mrvTime;
    BookTimeAdapter bookTimeAdapter;
    BookServiceAdapter bookServiceAdapter;
    Dialog addServiceDialog;
    CheckBox mchkBoxCut, mchkBoxStyling, mchkBoxColor, mchkBoxFacial, mchkBoxMakeUp;
    Button mbtnAddService, mbtnCanService;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    ArrayList<String> results = new ArrayList<>();
    String mark="pm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        addServiceDialog = new Dialog(this);

        mtvBookDate = findViewById(R.id.tvBookDate);
        mtvBookAddress = findViewById(R.id.tvBookAddress);

        mtvEmptySlot = findViewById(R.id.tvEmptySlot);

        mrvTime = findViewById(R.id.mrvTimeSlot);
        mrvService = findViewById(R.id.mrvBookServiceInfo);

        final Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);
        final int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);

        if(hourOfDay < 15){
            results = getTimeSet(true);
            mtvEmptySlot.setVisibility(View.INVISIBLE);
            //Set adapter for time slot
            bookTimeAdapter = new BookTimeAdapter(results);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
            mrvTime.setLayoutManager(layoutManager);
            mrvTime.setAdapter(bookTimeAdapter);

        }else{
            Toast.makeText(BookAppointment.this,"123 NO AVAILABLE SLOT PROVIDED",Toast.LENGTH_SHORT).show();
            mrvTime.setVisibility(View.INVISIBLE);
            mtvEmptySlot.setVisibility(View.VISIBLE);
        }

        //Set system date
        String date = day + "/" + (month+1) + "/" + year;
        mtvBookDate.setText(date);

        //When user wishes to change date
        mtvBookDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(BookAppointment.this,
                        R.style.DatePickerTheme,mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = day + "/" + (month+1) + "/" + year;
                mtvBookDate.setText(date);
            }
        };

        //When address is clicked
        mtvBookAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BookAppointment.this,mtvBookAddress.getText().toString()+"",Toast.LENGTH_SHORT).show();
            }
        });

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("serviceName");
        String price = bundle.getString("servicePrice");

        String serviceName[] = new String[]{name};
        String servicePrice[] = new String[]{price};

        bookServiceAdapter = new BookServiceAdapter(serviceName,servicePrice);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        mrvService.setLayoutManager(layoutManager1);
        mrvService.setAdapter(bookServiceAdapter);

    }

    public void btnBookApt_onClick(View view) {
        //TODO::Pass address/date to new Intent
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

    private ArrayList<String> getTimeSet(boolean isCurrentDay) {
        String startTime = "09" + ":" + "00" + " AM ";
        String endTime = "06" + ":" + "00" + " PM ";
        int start,marker;

        ArrayList results = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // get current hour
        if(!isCurrentDay)
            calendar.set(Calendar.HOUR_OF_DAY,9); // set default hour to 9

        calendar.set(Calendar.MINUTE, 0); // set default min to 0

        marker = calendar.get(Calendar.AM_PM);
        if(marker==1){
            mark = " PM ";
        }
        else{
            mark = " AM ";
        }

        if(hour + 3 < 9){
            calendar.set(Calendar.HOUR_OF_DAY,9);
            start = calendar.get(Calendar.HOUR_OF_DAY);
        }
        else {
            calendar.set(Calendar.HOUR_OF_DAY,hour+3);
            start = calendar.get(Calendar.HOUR_OF_DAY);

            if(start>=12){
                switch (start){
                    case 12 : start = 12; mark = " PM ";break;
                    case 13 : start = 1;  mark = " PM ";break;
                    case 14 : start = 2;  mark = " PM ";break;
                    case 15 : start = 3;  mark = " PM ";break;
                    case 16 : start = 4;  mark = " PM ";break;
                    case 17 : start = 5;  mark = " PM ";break;
                }
            }
        }

        //Toast.makeText(this,start,Toast.LENGTH_SHORT).show();

        startTime = start + ":" + "00" + mark;

        try {
            Date dateObj1 = sdf.parse(startTime);
            Date dateObj2 = sdf.parse(endTime);

            long dif = dateObj1.getTime();

            while (dif < dateObj2.getTime()) {
                Date slot1 = new Date(dif);
                dif += 1800000;
                SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm a");
                results.add(sdf1.format(slot1));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return results;
    }

}

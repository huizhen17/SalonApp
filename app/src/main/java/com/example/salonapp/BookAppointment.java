package com.example.salonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class BookAppointment extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView mtvBookDate, mtvBookAddress, mtvEmptySlot,mtvGetCurrentLoc;
    RecyclerView mrvService;
    RecyclerView mrvTime;
    BookTimeAdapter bookTimeAdapter;
    BookServiceAdapter bookServiceAdapter;
    Dialog addServiceDialog,editAddressDialog;
    CheckBox mchkBoxCut, mchkBoxStyling, mchkBoxColor, mchkBoxFacial, mchkBoxMakeUp;
    EditText metAddress;
    Button mbtnAddService, mbtnCanService, mbtnBookAppointment,mbtnConfirm,mbtnCanEdit;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    ArrayList<String> results = new ArrayList<>();
    String mark = "pm", name = "", price = "", time = "",userID;
    String latitude, longitude;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        addServiceDialog = new Dialog(this);
        editAddressDialog = new Dialog(this);

        mtvBookDate = findViewById(R.id.tvBookDate);
        mtvBookAddress = findViewById(R.id.tvBookAddress);
        mtvEmptySlot = findViewById(R.id.tvEmptySlot);
        mrvTime = findViewById(R.id.mrvTimeSlot);
        mrvService = findViewById(R.id.mrvBookServiceInfo);
        mbtnBookAppointment = findViewById(R.id.btnBookAppointment);

        //get user select date
        currentDate();

        //Retrieve address from db
        userID = mAuth.getCurrentUser().getUid();
        DocumentReference user = db.collection("userDetail").document(userID);
        user.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String address = documentSnapshot.getString("address");
                longitude = documentSnapshot.getString("longitude");
                latitude = documentSnapshot.getString("latitude");
                mtvBookAddress.setText(address);
                if(address.equals("")){
                    mtvBookAddress.setText("Click to add new Address");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BookAppointment.this,"Try again later",Toast.LENGTH_SHORT).show();
            }
        });


        //When address is clicked
        mtvBookAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mtvBookAddress.getText().toString().equals("Click to add new Address")){
                    Intent i = new Intent(BookAppointment.this,AddAddress.class);
                    //i.putExtra("book","pass from book appointment");
                    startActivity(i);
                }else{
                    editAddressDialog.setContentView(R.layout.activity_address_dialog);
                    editAddressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    editAddressDialog.show();
                    editAddressDialog.setCancelable(false);

                    metAddress = editAddressDialog.findViewById(R.id.etAddress);
                    mbtnConfirm = editAddressDialog.findViewById(R.id.btnDoneEdit);
                    mbtnCanEdit = editAddressDialog.findViewById(R.id.btnCancelEdit);

                    metAddress.setText(mtvBookAddress.getText().toString());


                    mbtnConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mtvBookAddress.setText(metAddress.getText().toString());
                            Geocoding locationAddress = new Geocoding();
                            locationAddress.getAddressFromLocation(metAddress.getText().toString(),
                                    getApplicationContext());
                            latitude = String.valueOf(Geocoding.getLatitude());
                            longitude= String.valueOf(Geocoding.getLongitude());
                            editAddressDialog.dismiss();
                        }
                    });

                    mbtnCanEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            editAddressDialog.dismiss();
                        }
                    });
                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("serviceName");
        price = bundle.getString("servicePrice");

        String serviceName[] = new String[]{name};
        String servicePrice[] = new String[]{price};

        bookServiceAdapter = new BookServiceAdapter(this, serviceName, servicePrice);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mrvService.setLayoutManager(layoutManager1);
        mrvService.setAdapter(bookServiceAdapter);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

        //Initialize FLPC
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        mtvGetCurrentLoc = findViewById(R.id.tvGetCurrentLoc);
        mtvGetCurrentLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check permission
                if(ActivityCompat.checkSelfPermission(BookAppointment.this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    //when permission granted
                    getlocation();
                }else{
                    //when permission denied
                    ActivityCompat.requestPermissions(BookAppointment.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }
            }
        });


    }

    private void getlocation(){
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //Initialize location
                Location location = task.getResult();
                if(location!=null){
                    //Initialize geocoder
                    Geocoder geocoder = new Geocoder(BookAppointment.this,Locale.getDefault());
                    double lat = location.getLatitude();
                    double lot = location.getLongitude();

                    latitude = String.valueOf(lat);
                    longitude = String.valueOf(lot);

                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        String fullAddress = addresses.get(0).getAddressLine(0);
                        mtvBookAddress.setText(fullAddress);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    public void currentDate(){
        final Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);
        final int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);

        //Check available slot if current hour is < 15 display available slot
        if (hourOfDay < 15) {
            results = getTimeSet(true);
            mtvEmptySlot.setVisibility(View.INVISIBLE);
            //Set adapter for time slot
            bookTimeAdapter = new BookTimeAdapter(this, results);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
            mrvTime.setLayoutManager(layoutManager);
            mrvTime.setAdapter(bookTimeAdapter);

        } else {
            Toast.makeText(BookAppointment.this, " NO AVAILABLE SLOT PROVIDED", Toast.LENGTH_SHORT).show();
            mrvTime.setVisibility(View.INVISIBLE);
            mbtnBookAppointment.setVisibility(View.INVISIBLE);
            mtvEmptySlot.setVisibility(View.VISIBLE);
        }

        //Set system date
        String date = day + "/" + (month + 1) + "/" + year;
        mtvBookDate.setText(date);

        //When user wishes to change date
        mtvBookDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(BookAppointment.this,
                        R.style.DatePickerTheme, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = day + "/" + (month + 1) + "/" + year;
                mtvBookDate.setText(date);
            }
        };
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            time = intent.getStringExtra("time");
        }
    };


    public void btnBookApt_onClick(View view) {
        Intent i = new Intent(BookAppointment.this, OrderConfirmation.class);
        i.putExtra("address", mtvBookAddress.getText().toString());
        i.putExtra("date", mtvBookDate.getText().toString());
        i.putExtra("serviceName", name);
        i.putExtra("servicePrice", price);
        i.putExtra("time", time);
        i.putExtra("latitude", latitude);
        i.putExtra("longitude", longitude);
        startActivity(i);
        finish();
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
                Toast.makeText(getApplicationContext(), "Service added", Toast.LENGTH_SHORT).show();
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
        int start, marker;

        ArrayList results = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // get current hour
        if (!isCurrentDay)
            calendar.set(Calendar.HOUR_OF_DAY, 9); // set default hour to 9

        calendar.set(Calendar.MINUTE, 0); // set default min to 0

        marker = calendar.get(Calendar.AM_PM);
        if (marker == 1) {
            mark = " PM ";
        } else {
            mark = " AM ";
        }

        if (hour + 3 < 9) {
            calendar.set(Calendar.HOUR_OF_DAY, 9);
            start = calendar.get(Calendar.HOUR_OF_DAY);
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, hour + 3);
            start = calendar.get(Calendar.HOUR_OF_DAY);

            if (start >= 12) {
                switch (start) {
                    case 12:
                        start = 12;
                        mark = " PM ";
                        break;
                    case 13:
                        start = 1;
                        mark = " PM ";
                        break;
                    case 14:
                        start = 2;
                        mark = " PM ";
                        break;
                    case 15:
                        start = 3;
                        mark = " PM ";
                        break;
                    case 16:
                        start = 4;
                        mark = " PM ";
                        break;
                    case 17:
                        start = 5;
                        mark = " PM ";
                        break;
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

package com.example.salonapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddAddress extends AppCompatActivity {

    EditText mtvHouseNo, mtvHouseBlock, mtvHouseLvl, mtvHouseCondo, mtvHouseGarden, mtvHouseStreet, mtvHousePostcode, mtvHouseCity, mtvHouseState;
    String houseNo, houseBlock, houseLvl, houseCondo, houseGarden, houseStreet, housePostcode, houseCity, houseState;
    UserAddress userAddress = new UserAddress();
    String address="",userID="";
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        mtvHouseCity = findViewById(R.id.tvHouseCity);
        mtvHousePostcode = findViewById(R.id.tvHousePostcode);
        mtvHouseState = findViewById(R.id.tvHouseState);

        userID = mAuth.getCurrentUser().getUid();

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String addressFound = bundle.getString("addressExist");
            if(!addressFound.isEmpty()){
                //if address found, extract out from firebase
                extractAddress();
            }
        }

    }

    public void btnSavedAddress_onClick(View view) {



        if(!mtvHouseNo.getText().toString().isEmpty()||!mtvHouseGarden.getText().toString().isEmpty()||!mtvHouseStreet.getText().toString().isEmpty()
                ||!mtvHouseCity.getText().toString().isEmpty()||!mtvHouseCity.getText().toString().isEmpty()||!mtvHouseState.getText().toString().isEmpty()){
            retrieveAddress();
            //Back to parent activity and pass data back
            Toast.makeText(AddAddress.this,"Saved.",Toast.LENGTH_SHORT).show();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", address);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }
        else{
            Toast.makeText(AddAddress.this,"Empty field found.",Toast.LENGTH_SHORT).show();
        }


    }

    public void extractAddress(){
        DocumentReference address = db.collection("userDetail").document(userID).collection("userAddress").document("address");
        address.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                mtvHouseNo.setText(documentSnapshot.getString("houseNumber"));
                mtvHouseBlock.setText(documentSnapshot.getString("block"));
                mtvHouseLvl.setText(documentSnapshot.getString("level"));
                mtvHouseCondo.setText(documentSnapshot.getString("condo"));
                mtvHouseGarden.setText(documentSnapshot.getString("garden"));
                mtvHouseStreet.setText(documentSnapshot.getString("street"));
                mtvHouseCity.setText(documentSnapshot.getString("city"));
                mtvHousePostcode.setText(documentSnapshot.getString("postcode"));
                mtvHouseState.setText(documentSnapshot.getString("state"));
            }
        });
    }

    public void retrieveAddress(){
        houseNo = mtvHouseNo.getText().toString();
        houseBlock = mtvHouseBlock.getText().toString();
        houseLvl = mtvHouseLvl.getText().toString();
        houseCondo = mtvHouseCondo.getText().toString();
        houseGarden = mtvHouseGarden.getText().toString();
        houseStreet = mtvHouseStreet.getText().toString();
        houseCity = mtvHouseCity.getText().toString();
        housePostcode = mtvHousePostcode.getText().toString();
        houseState = mtvHouseState.getText().toString();

        userAddress = new UserAddress(houseNo,houseBlock,houseLvl,houseCondo,houseGarden,houseStreet,houseCity,housePostcode,houseState);
        address = userAddress.generateAddress();

        //Saved address components into database
        DocumentReference addressDB = db.collection("userDetail").document(userID).collection("userAddress").document("address");
        Map<String, Object> address = new HashMap<>();
        address.put("houseNumber", houseNo);
        address.put("block", houseBlock);
        address.put("level", houseLvl);
        address.put("condo", houseCondo);
        address.put("garden", houseGarden);
        address.put("street", houseStreet);
        address.put("city", houseCity);
        address.put("postcode", housePostcode);
        address.put("state", houseState);
        addressDB.set(address).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddAddress.this, "Address added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(AddAddress.this, "Address fail to add", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

package com.example.salonapp;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAccFragment extends Fragment {

    public static final int REQUEST_CODE = 11;
    public static final int RESULT_CODE = 12;
    public static final String EXTRA_KEY_TEST = "testKey";

    TextView mtvUsername, mtvUserPhone, mtvUserEmail, mtvUserAddress;
    ImageView mivEditAdd;
    Button mbtnLogOut;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userID,address;
    ArrayList<UserAddress> userAddresses = new ArrayList<>();
    UserAddress userAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_acc,container,false);
        mtvUsername = v.findViewById(R.id.tvUserName);
        mtvUserEmail = v.findViewById(R.id.tvUserEmail);
        mtvUserPhone = v.findViewById(R.id.tvUserPhone);
        mtvUserAddress = v.findViewById(R.id.tvUserAddress);
        mivEditAdd = v.findViewById(R.id.ivEditAddress);
        mbtnLogOut = v.findViewById(R.id.btnLogOut);

        //Edit address
        mivEditAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), AddAddress.class);
                i.putExtra("addressExist", mtvUserAddress.getText().toString());
                startActivityForResult(i, 1);
                //getActivity().finish();

            }
        });

        //Retrieve data from firebase
        userID = mAuth.getCurrentUser().getUid();
        DocumentReference user = db.collection("userDetail").document(userID);
        user.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String name = documentSnapshot.getString("name");
                String email = documentSnapshot.getString("email");
                String phone = documentSnapshot.getString("phoneNo");
                String address = documentSnapshot.getString("address");

                mtvUsername.setText(name);
                mtvUserEmail.setText(email);
                mtvUserPhone.setText(phone);
                mtvUserAddress.setText(address);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Problem on retrieving data. Please try again later.",Toast.LENGTH_SHORT).show();
            }
        });

        //Log out
        mbtnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent i= new Intent(getContext(),LoginActivity.class);
                startActivity(i);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().finish();
            }
        });

        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getActivity();
        if(requestCode == 1 && resultCode == Activity.RESULT_OK) {
            //some code
            address = data.getStringExtra("result");
            mtvUserAddress.setText(address);

            //Saved address to database
            DocumentReference documentReference = db.collection("userDetail").document(userID);
            Map<String,Object> userAddress = new HashMap<>();
            userAddress.put("address",address);
            documentReference.update(userAddress).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getContext(), "Address updated successfully", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Address updated fail", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}

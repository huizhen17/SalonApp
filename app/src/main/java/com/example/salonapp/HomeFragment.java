package com.example.salonapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView mtvOrderNo, mtvOrderTime, mtvOrderStatus,mtvContact;
    Button mbtnTrackOrder;
    ImageView mivWalletScan, mivWalletPay, mivWalletReload,mivPhone;
    RecyclerView mRvHomeServices;
    HomeServicesAdapter homeServicesAdapter;
    ArrayList<ServicesDetail> serviceList;
    ArrayList<OrderDetail> orderList;
    String userID,orderID;
    int counter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home,container,false);

        mivWalletScan = v.findViewById(R.id.ivWalletScan);
        mivWalletPay = v.findViewById(R.id.ivWalletPay);
        mivWalletReload = v.findViewById(R.id.ivWalletReload);
        mRvHomeServices = v.findViewById(R.id.mrvHomeFeatures);
        mtvOrderNo = v.findViewById(R.id.tvOrderNo);
        mtvOrderTime = v.findViewById(R.id.tvOrderTime);
        mtvOrderStatus = v.findViewById(R.id.tvOrderStatus);
        mbtnTrackOrder = v.findViewById(R.id.btnTrackOrder);
        mivPhone = v.findViewById(R.id.ivPhone);
        mtvContact = v.findViewById(R.id.tvContactRider);

        serviceList = new ArrayList<>();
        serviceList.add(new ServicesDetail("Hair Cut","45","35"));
        serviceList.add(new ServicesDetail("Hair Styling","120","45"));
        serviceList.add(new ServicesDetail("Hair Coloring","100","88"));
        serviceList.add(new ServicesDetail("Facial","45","40"));
        serviceList.add(new ServicesDetail("Make Up","35","35"));

        //Design Horizontal Layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getContext(),LinearLayoutManager.HORIZONTAL,false);
        mRvHomeServices.setLayoutManager(layoutManager);

        //Set adapter for recycler view
        homeServicesAdapter = new HomeServicesAdapter(getContext(), serviceList);
        mRvHomeServices.setAdapter(homeServicesAdapter);


        userID = mAuth.getCurrentUser().getUid();
        CollectionReference collectionReference = db.collection("userDetail").document(userID).collection("orderDetail");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error==null) {
                    if (value.isEmpty()){
                        mbtnTrackOrder.setVisibility(View.VISIBLE);
                        mtvOrderNo.setVisibility(View.VISIBLE);
                        mtvOrderStatus.setVisibility(View.VISIBLE);
                        mtvOrderTime.setVisibility(View.VISIBLE);
                    }else {
                        for (QueryDocumentSnapshot document : value) {
                            String id = document.getId();
                            String status = (String) document.getString("orderStatus").toUpperCase();
                            String time = (String) document.getString("orderTime");

                            mtvOrderNo.setText(id);
                            mtvOrderStatus.setText(status);
                            mtvOrderTime.setText(time);

                            //If rider OTW user can track the rider location
                            if(mtvOrderStatus.getText().toString().equalsIgnoreCase("RIDER OTW")){
                                mbtnTrackOrder.setVisibility(View.VISIBLE);
                                mivPhone.setVisibility(View.VISIBLE);
                                mtvContact.setVisibility(View.VISIBLE);
                            }else{
                                mbtnTrackOrder.setVisibility(View.INVISIBLE);
                                mivPhone.setVisibility(View.INVISIBLE);
                                mtvContact.setVisibility(View.INVISIBLE);
                            }
                        }
                    }

                }else
                    Toast.makeText(getContext(),"Fail to retrieve data.",Toast.LENGTH_SHORT).show();
            }
        });


        //TODO::Messaging??
        //When track button is clicked
        mbtnTrackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),mtvOrderStatus.getText().toString(),Toast.LENGTH_SHORT).show();
                if(mtvOrderStatus.getText().toString().equalsIgnoreCase("RIDER OTW")){
                    //TODO::Get worker ID get worker latitude & longtitude
                    //TODO:: Intent to waze app
                }else if(mtvOrderStatus.getText().toString().equalsIgnoreCase("ARRIVED")){
                    //TODO::Intent to payment
                }
            }
        });

        mtvContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Contact Rider",Toast.LENGTH_SHORT).show();
            }
        });

        //when wallet is on clicked
        mivWalletScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Scan",Toast.LENGTH_SHORT).show();
            }
        });

        mivWalletPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Pay",Toast.LENGTH_SHORT).show();
            }
        });

        mivWalletReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Reload",Toast.LENGTH_SHORT).show();
            }
        });


        return v;
    }

}

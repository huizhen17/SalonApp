package com.example.salonapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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

import com.codemybrainsout.ratingdialog.RatingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    TextView mtvOrderNo, mtvOrderTime, mtvOrderStatus,mtvContact, mtvCredit;
    Button mbtnTrackOrder, mbtnPayment;
    ImageView mivWalletScan, mivWalletPay, mivWalletReload,mivPhone;
    RecyclerView mRvHomeServices;
    HomeServicesAdapter homeServicesAdapter;
    ArrayList<ServicesDetail> serviceList;
    ArrayList<OrderDetail> orderList;
    String userID,orderID,orderStatus,orderTime,link;
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
        mtvCredit = v.findViewById(R.id.tvCredit);
        mbtnTrackOrder = v.findViewById(R.id.btnTrackOrder);
        mbtnPayment = v.findViewById(R.id.btnPayment);
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

        loadMain();

        //when wallet is on clicked
        mivWalletScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),ScanPayment.class);
                startActivity(i);
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
                Intent i = new Intent(getContext(),TopUpActivity.class);
                startActivity(i);
            }
        });


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMain();
    }

    public void loadMain(){
        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("userDetail").document(userID).collection("currentOrder").document("currentOrder");
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error==null) {
                    if (!value.exists()){
                        mbtnTrackOrder.setVisibility(View.INVISIBLE);
                        mbtnPayment.setVisibility(View.INVISIBLE);
                        mivPhone.setVisibility(View.INVISIBLE);
                        mtvContact.setVisibility(View.INVISIBLE);
                        mtvOrderNo.setText("No Appointment");
                        mtvOrderStatus.setText("No Appointment");
                        mtvOrderTime.setText("No Appointment");
                    }else {
                        orderID = value.getString("orderID").toUpperCase();
                        orderStatus = value.getString("orderStatus").toUpperCase();
                        orderTime = value.getString("orderTime");

                        mtvOrderNo.setText(orderID);
                        mtvOrderStatus.setText(orderStatus);
                        mtvOrderTime.setText(orderTime);

                        //If rider OTW user can track the rider location
                        if(mtvOrderStatus.getText().toString().equalsIgnoreCase("RIDER OTW")){
                            link = value.getString("orderLink");
                            mbtnTrackOrder.setVisibility(View.VISIBLE);
                            mbtnPayment.setVisibility(View.INVISIBLE);
                            mivPhone.setVisibility(View.VISIBLE);
                            mtvContact.setVisibility(View.VISIBLE);
                        }
                        else if(mtvOrderStatus.getText().toString().equalsIgnoreCase("ARRIVED")){
                            mbtnPayment.setVisibility(View.VISIBLE);
                            mbtnTrackOrder.setVisibility(View.INVISIBLE);
                            mivPhone.setVisibility(View.INVISIBLE);
                            mtvContact.setVisibility(View.INVISIBLE);
                        } else{
                            mbtnTrackOrder.setVisibility(View.INVISIBLE);
                            mbtnPayment.setVisibility(View.INVISIBLE);
                            mivPhone.setVisibility(View.INVISIBLE);
                            mtvContact.setVisibility(View.INVISIBLE);
                        }
                    }
                }else
                    Toast.makeText(getContext(),"Fail to retrieve data.",Toast.LENGTH_SHORT).show();
            }
        });

        //When track button is clicked
        mbtnTrackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),mtvOrderStatus.getText().toString(),Toast.LENGTH_SHORT).show();
                if(mtvOrderStatus.getText().toString().equalsIgnoreCase("RIDER OTW")){
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(link));
                    startActivity(i);
                }
            }
        });

        mbtnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mtvOrderStatus.getText().toString().equalsIgnoreCase("ARRIVED")){
                    Intent i = new Intent(getContext(),OrderSummary.class);
                    i.putExtra("orderID",orderID);
                    i.putExtra("orderStatus",orderStatus);
                    i.putExtra("orderTime",orderTime);
                    startActivity(i);
                }
            }
        });

        mtvContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Contact Rider",Toast.LENGTH_SHORT).show();
            }
        });
    }

}

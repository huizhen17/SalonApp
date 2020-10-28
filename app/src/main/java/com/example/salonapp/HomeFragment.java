package com.example.salonapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    RecyclerView mRvHomeServices,mRvHomeTracker;
    HomeServicesAdapter homeServicesAdapter;
    HomeTrackerAdapter homeTrackerAdapter;
    ArrayList<UserDetail> userList;
    UserAddress userAddress;
    UserDetail userDetail;
    ArrayList<UserAddress> userAddressList;
    ArrayList<ServicesDetail> serviceList;
    ArrayList<OrderDetail> orderList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home,container,false);
        mRvHomeServices = v.findViewById(R.id.mrvHomeFeatures);
        mRvHomeTracker = v.findViewById(R.id.mrvTrackOrder);

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


        userAddressList = new ArrayList<>();
        userAddressList.add(new UserAddress("1","Z","","","Lebuh Bukit Jambul, Bukit Jambul","Bayan Lepas","11900","Pulau Pinang"));

        userList = new ArrayList<>();
        userList.add(new UserDetail("John Smith","john@gmail.com","0123456789","12345678",userAddress,"10.50","dk","dk"));

        orderList = new ArrayList<>();
        orderList.add(new OrderDetail("#123456789",userDetail,"24/10/2020","9.30am","Order Pending","45"));

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(
                getContext(),LinearLayoutManager.VERTICAL,false);
        mRvHomeTracker.setLayoutManager(layoutManager1);
        //Set adapter for recycler view
        homeTrackerAdapter = new HomeTrackerAdapter(getContext(), orderList);
        mRvHomeTracker.setAdapter(homeTrackerAdapter);

        return v;
    }
}

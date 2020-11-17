package com.example.salonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class ScanPayment extends AppCompatActivity {

    CodeScanner codeScanner;
    CodeScannerView codeScannerView;
    String orderID="",orderTime="",orderAmount="",orderAddress="",orderService="",orderDate="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_payment);
        codeScannerView = findViewById(R.id.scannerCam);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            orderID = bundle.getString("orderID");
            orderTime = bundle.getString("orderTime");
            orderDate = bundle.getString("orderDate");
            orderAddress = bundle.getString("orderAddress");
            orderAmount = bundle.getString("orderAmount");
            orderService = bundle.getString("orderService");
        }

        codeScanner = new CodeScanner(this, codeScannerView);
        requestForCamera();
    }

    private void requestForCamera(){
        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                codeScanner.startPreview();
                startCamera();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                View parentLayout = findViewById(android.R.id.content);
                final Snackbar snackbar = Snackbar.make(parentLayout,"Camera permission is needed in order to scan QR code", Snackbar.LENGTH_LONG);
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.setDuration(10000);
                snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
                snackbar.setAction("Setting", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                        Context context = ScanPayment.this;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                        intent.setData(uri);
                        context.startActivity(intent);

                        requestForCamera();
                    }
                });
                snackbar.show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    public void startCamera(){
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                ScanPayment.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ScanPayment.this,"Result",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        codeScannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScanPayment.this,ViewReceipt.class);
                i.putExtra("orderID",orderID);
                i.putExtra("orderTime",orderTime);
                i.putExtra("orderDate",orderDate);
                i.putExtra("orderAddress",orderAddress);
                i.putExtra("orderAmount",orderAmount);
                i.putExtra("orderService",orderService);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                codeScanner.startPreview();
                startCamera();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                View parentLayout = findViewById(android.R.id.content);
                final Snackbar snackbar = Snackbar.make(parentLayout,"Camera permission is needed in order to scan QR code", Snackbar.LENGTH_LONG);
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.setDuration(10000);
                snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
                snackbar.setAction("Setting", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                        Context context = ScanPayment.this;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                        intent.setData(uri);
                        context.startActivity(intent);
                        requestForCamera();
                    }
                });
                snackbar.show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }
}

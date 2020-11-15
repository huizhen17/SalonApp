package com.example.salonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class SignUpActivity extends AppCompatActivity {
    //Connect to firestore
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    EditText metName, metPhone, metEmail, metPassword;
    String username, email, phone, password;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        metName = findViewById(R.id.etSignUpName);
        metPhone = findViewById(R.id.etSignUpPhone);
        metEmail = findViewById(R.id.etSignUpEmail);
        metPassword = findViewById(R.id.etSignUpPs);
    }

    public void btnSignUp_onClick(View view) {
        username = metName.getText().toString().trim();
        email = metEmail.getText().toString().trim();
        phone = metPhone.getText().toString().trim();
        password = metPassword.getText().toString().trim();

        if(username.isEmpty()){
            Toast.makeText(SignUpActivity.this,"Name is required",Toast.LENGTH_SHORT).show();
            return;
        }

        if(email.isEmpty()){
            Toast.makeText(SignUpActivity.this,"Email is required",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(SignUpActivity.this,"Email is invalid",Toast.LENGTH_SHORT).show();
            return;
        }

        if(phone.isEmpty()){
            Toast.makeText(SignUpActivity.this,"Phone is required",Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.isEmpty()){
            Toast.makeText(SignUpActivity.this,"Password is required",Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Sign Up successfully.",
                                    Toast.LENGTH_SHORT).show();
                            userID = mAuth.getCurrentUser().getUid();
                            FirebaseInstanceId.getInstance().getInstanceId()
                                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                            if (task.isSuccessful()) {
                                                String token = task.getResult().getToken();
                                                DocumentReference store = db.collection("userDetail").document(mAuth.getCurrentUser().getUid());
                                                store.update("token", token);
                                            }
                                        }
                                    });
                            DocumentReference user = db.collection("userDetail").document(userID);
                            UserDetail userDetail = new UserDetail(username,email,phone,null,"0");
                            //If system successfully store user details it will go to main if not it
                            //will toast message
                            user.set(userDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    e.getMessage();
                                    Toast.makeText(SignUpActivity.this, "Sign Up failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Log.e("sign up error",task.getException().getMessage());
                        }

                    }
                });


    }

    public void txtSignIn_onClick(View view) {
        Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

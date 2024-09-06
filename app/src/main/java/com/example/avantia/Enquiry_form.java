package com.example.avantia;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Enquiry_form extends AppCompatActivity {

    EditText nametxt,addresstxt,phn_numtxt,emailtxt,course_nametxt,modetxt;

    Button submit_btn;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enquiry_form);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nametxt = findViewById(R.id.name);
        addresstxt = findViewById(R.id.address);
        phn_numtxt = findViewById(R.id.phn_num);
        emailtxt= findViewById(R.id.email);
        course_nametxt = findViewById(R.id.course_name);
        modetxt = findViewById(R.id.mode);
        submit_btn = findViewById(R.id.submit);
//        ImageView menubaru = findViewById(R.id.menubarrenquiry);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

//        menubaru.setOnClickListener(v -> {
//            Fragment fragment = new BlankFragment();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.main, fragment).commit();
//        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nametxt.getText().toString().trim();
                String address = addresstxt.getText().toString().trim();
                String number = phn_numtxt.getText().toString();
                String email = emailtxt.getText().toString();
                String course_name = course_nametxt.getText().toString();
                String modee = modetxt.getText().toString();


                if(TextUtils.isEmpty(name)){
                    nametxt.setError("Name is required");
                    return;
                }
                if(TextUtils.isEmpty(address)){
                    addresstxt.setError("address is required");
                    return;
                }
                if(TextUtils.isEmpty(number)){
                    phn_numtxt.setError("number is required");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    emailtxt.setError("email is required");
                    return;
                }
                if(TextUtils.isEmpty(course_name)){
                    submit_btn.setError("course name is required");
                    return;
                }
                if(TextUtils.isEmpty(modee)){
                    modetxt.setError("course name is required");
                    return;
                }


                firebaseAuth.createUserWithEmailAndPassword(email,address).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Enquiry_form.this,"Successfull", Toast.LENGTH_SHORT).show();
                            userId = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firestore.collection("users_info").document(userId);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Name",name);
                            user.put("Address",address);
                            user.put("Email",email);
                            user.put("Phone Number",number);
                            user.put("Course Name",course_name);
                            user.put("Course Mode",modee);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "onSuccess: Your Record is Successfully Received"+userId);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: failure"+ e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(Enquiry_form.this,"Successfull" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
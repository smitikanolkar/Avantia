package com.example.avantia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class login extends AppCompatActivity {

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        TextView signup = findViewById(R.id.signup);
//        ImageView menubaru = findViewById(R.id.menubarrlogin);



        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            navigateToHomeActivity(user.getUid());
        }


//        menubaru.setOnClickListener(v -> {
//            Fragment fragment = new BlankFragment();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.main, fragment).commit();
//        });

        login.setOnClickListener(view -> {
            ProgressDialog progressDialog = new ProgressDialog(login.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(true);
            progressDialog.show();

            String em = email.getText().toString();
            String pass = password.getText().toString();
            auth.signInWithEmailAndPassword(em, pass).addOnCompleteListener(login.this, task -> {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    FirebaseUser user1 = auth.getCurrentUser();
                    if (user1 != null) {
                        navigateToHomeActivity(user1.getUid());
                    }
                } else {
                    Toast.makeText(login.this, "Operation Failed.", Toast.LENGTH_SHORT).show();
                }
            });
        });

        signup.setOnClickListener(view -> {
            Intent i = new Intent(login.this, signup.class);
            startActivity(i);
            finish();
        });


    }

    private void navigateToHomeActivity(String userId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        ProgressDialog progressDialog = new ProgressDialog(login.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                if (dataSnapshot.exists()) {
                    String role = dataSnapshot.child("Role").getValue(String.class);
                    if (role != null) {
                        Intent intent;
                        if (role.equals("Admin")) {
                            intent = new Intent(login.this, Home.class);
                        } else if (role.equals("Student")) {
                            intent = new Intent(login.this, studentHome.class);
                        } else {
                            Toast.makeText(login.this, "Unknown role", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        intent.putExtra("User UID", userId);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(login.this, "Role not defined", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(login.this, "User data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(login.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

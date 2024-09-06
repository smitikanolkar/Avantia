//package com.example.avantia;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class signup extends AppCompatActivity {
//
//    FirebaseAuth auth;
//    DatabaseReference database;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_signup);
//        auth = FirebaseAuth.getInstance();
//        database = FirebaseDatabase.getInstance().getReference();
//
//        EditText first_name = findViewById(R.id.first_name);
//        EditText last_name = findViewById(R.id.last_name);
//        EditText email = findViewById(R.id.email);
//        EditText password = findViewById(R.id.password);
//        EditText confirm_password = findViewById(R.id.confirm_password);
//        RadioGroup radioGroup = findViewById(R.id.radio_group);
//        Button signup = findViewById(R.id.signup);
//        TextView login = findViewById(R.id.login);
//        ImageView back = findViewById(R.id.backdashboard);
//
//        signup.setOnClickListener(view -> {
//            ProgressDialog progressDialog = new ProgressDialog(signup.this);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//
//            Thread thread = new Thread(() -> {
//                String pass = password.getText().toString();
//                String confirmPass = confirm_password.getText().toString();
//                String em = email.getText().toString();
//                String firstName = first_name.getText().toString();
//                String lastName = last_name.getText().toString();
//
//                if (!pass.equals(confirmPass)) {
//                    runOnUiThread(() -> {
//                        confirm_password.setError("Password doesn't match");
//                        progressDialog.dismiss();
//                    });
//                    return;
//                }
//
//                auth.createUserWithEmailAndPassword(em, pass).addOnCompleteListener(signup.this, task -> {
//                    if (task.isSuccessful()) {
//                        FirebaseUser user = auth.getCurrentUser();
//                        if (user != null) {
//                            String userId = user.getUid();
//                            DatabaseReference ref = database.child("Users").child(userId);
//                            ref.child("First Name").setValue(firstName);
//                            ref.child("Last Name").setValue(lastName);
//
//                            // Get the selected radio button value
//                            int selectedRadioId = radioGroup.getCheckedRadioButtonId();
//                            if (selectedRadioId != -1) {
//                                RadioButton selectedRadioButton = findViewById(selectedRadioId);
//                                String role = selectedRadioButton.getText().toString();
//                                ref.child("Role").setValue(role);
//                            }
//
//                            runOnUiThread(() -> {
//                                progressDialog.dismiss();
//                                Intent i = new Intent(signup.this, Home.class);
//                                i.putExtra("User UID", userId);
//                                startActivity(i);
//                                finish();
//                            });
//                        }
//                    } else {
//                        runOnUiThread(() -> {
//                            Toast.makeText(signup.this, "Operation Failed", Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
//                        });
//                    }
//                });
//            });
//            thread.start();
//        });
//
//        login.setOnClickListener(view -> {
//            Intent i = new Intent(signup.this, MainActivity.class);
//            startActivity(i);
//            finish();
//        });
//
//        back.setOnClickListener(v -> {
//            Intent i = new Intent(signup.this, MainActivity.class);
//            startActivity(i);
//            finish();
//        });
//    }
//}
package com.example.avantia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        EditText first_name = findViewById(R.id.first_name);
        EditText last_name = findViewById(R.id.last_name);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        EditText confirm_password = findViewById(R.id.confirm_password);
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        Button signup = findViewById(R.id.signup);
        TextView login = findViewById(R.id.login);
//        ImageView menubaru = findViewById(R.id.menubarrsignup);


//        menubaru.setOnClickListener(v -> {
//            Fragment fragment = new BlankFragment();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.main, fragment).commit();
//        });

        signup.setOnClickListener(view -> {
            ProgressDialog progressDialog = new ProgressDialog(signup.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            String pass = password.getText().toString();
            String confirmPass = confirm_password.getText().toString();
            String em = email.getText().toString();
            String firstName = first_name.getText().toString();
            String lastName = last_name.getText().toString();

            if (firstName.isEmpty() || lastName.isEmpty() || em.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                progressDialog.dismiss();
                Toast.makeText(signup.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!pass.equals(confirmPass)) {
                confirm_password.setError("Password doesn't match");
                progressDialog.dismiss();
                return;
            }

            // Get the selected radio button value
            int selectedRadioId = radioGroup.getCheckedRadioButtonId();
            if (selectedRadioId == -1) {
                Toast.makeText(signup.this, "Please select a role", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return;
            }
            RadioButton selectedRadioButton = findViewById(selectedRadioId);
            String role = selectedRadioButton.getText().toString();

            auth.createUserWithEmailAndPassword(em, pass).addOnCompleteListener(signup.this, task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {
                        String userId = user.getUid();
                        DatabaseReference ref = database.child("Users").child(userId);
                        ref.child("First Name").setValue(firstName);
                        ref.child("Last Name").setValue(lastName);
                        ref.child("Email").setValue(em);
                        ref.child("Password").setValue(pass);
                        ref.child("Role").setValue(role);

                        runOnUiThread(() -> {
                            progressDialog.dismiss();
                            Intent i = new Intent(signup.this, login.class);
                            i.putExtra("User UID", userId);
                            startActivity(i);
                            finish();
                        });
                    }
                } else {
                    Toast.makeText(signup.this, "Operation Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        });

        login.setOnClickListener(view -> {
            Intent i = new Intent(signup.this, login.class);
            startActivity(i);
            finish();
        });


    }
}

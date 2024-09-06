package com.example.avantia;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Home extends AppCompatActivity {

    private String userUID;
    private String firstName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        ProgressDialog progressDialog = new ProgressDialog(Home.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Bundle b = getIntent().getExtras();
        if (b != null) {
            userUID = b.getString("User UID");
        }

        TextView name = findViewById(R.id.admin_name);
        Button createQuiz = findViewById(R.id.createQuiz);
        ImageView backbtn = findViewById(R.id.back);
        RelativeLayout your_quizzes = findViewById(R.id.your_quizzes);
        EditText quiz_title = findViewById(R.id.quiz_title);
        ImageView signout = findViewById(R.id.signout);


        ValueEventListener listener = new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot usersRef = snapshot.child("Users").child(userUID);
                firstName = Objects.requireNonNull(usersRef.child("First Name").getValue()).toString();

                name.setText("Welcome "+firstName+"!");
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this, "Can't connect", Toast.LENGTH_SHORT).show();
            }
        };
        database.addValueEventListener(listener);

        signout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(Home.this, MainActivity.class);
            startActivity(i);
            finish();
        });

        backbtn.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(Home.this, MainActivity.class);
            startActivity(i);
            finish();
        });

        createQuiz.setOnClickListener(v -> {
            if (quiz_title.getText().toString().isEmpty()) {
                quiz_title.setError("Quiz title cannot be empty");
                return;
            }
            Intent i = new Intent(Home.this, ExamEditor.class);
            i.putExtra("Quiz Title", quiz_title.getText().toString());
            quiz_title.setText("");
            startActivity(i);
        });


        your_quizzes.setOnClickListener(v -> {
            Intent i = new Intent(Home.this, listQuizzes.class);
            i.putExtra("Operation", "List Created Quizzes");
            startActivity(i);
        });

    }
}
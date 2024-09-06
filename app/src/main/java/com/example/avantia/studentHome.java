package com.example.avantia;

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

public class studentHome extends AppCompatActivity {

    private String userUID;
    private String firstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        ProgressDialog progressDialog = new ProgressDialog(studentHome.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Bundle b = getIntent().getExtras();
        userUID = b.getString("User UID");

        TextView name = findViewById(R.id.admin_name);
        TextView total_questions = findViewById(R.id.total_questions);
        TextView total_points = findViewById(R.id.total_points);
        Button startQuiz = findViewById(R.id.startQuiz);
        RelativeLayout solvedQuizzes = findViewById(R.id.solvedQuizzes);
        EditText start_quiz_id = findViewById(R.id.start_quiz_id);
        ImageView signout = findViewById(R.id.signout);
        ImageView back = findViewById(R.id.back);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot usersRef = snapshot.child("Users").child(userUID);
                firstName = usersRef.child("First Name").getValue().toString();

                if (usersRef.hasChild("Total Points")) {
                    String totalPoints = usersRef.child("Total Points").getValue().toString();
                    int points = Integer.parseInt(totalPoints);
                    total_points.setText(String.format("%03d", points));
                }
                if (usersRef.hasChild("Total Questions")) {
                    String totalQuestions = usersRef.child("Total Questions").getValue().toString();
                    int questions = Integer.parseInt(totalQuestions);
                    total_questions.setText(String.format("%03d", questions));
                }
                name.setText("Welcome "+firstName+"!");
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(studentHome.this, "Can't connect", Toast.LENGTH_SHORT).show();
            }
        };
        database.addValueEventListener(listener);

        signout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(studentHome.this, MainActivity.class);
            startActivity(i);
            finish();
        });

        startQuiz.setOnClickListener(v-> {
            if (start_quiz_id.getText().toString().equals("")) {
                start_quiz_id.setError("Quiz title cannot be empty");
                return;
            }
            Intent i = new Intent(studentHome.this, Exam.class);
            i.putExtra("Quiz ID", start_quiz_id.getText().toString());
            start_quiz_id.setText("");
            startActivity(i);
        });

        solvedQuizzes.setOnClickListener(v -> {
            Intent i = new Intent(studentHome.this, listQuizzes.class);
            i.putExtra("Operation", "List Solved Quizzes");
            startActivity(i);
        });


        back.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(studentHome.this, MainActivity.class);
            startActivity(i);
            finish();
        });


    }
}
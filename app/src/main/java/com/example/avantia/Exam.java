package com.example.avantia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Exam extends AppCompatActivity {

    private Question[] data;
    private String quizID;
    private String uid;
    private String email;
    private int oldTotalPoints = 0;
    private int oldTotalQuestions = 0;
    private TextView timerTextView;
    private CountDownTimer countDownTimer;
    // private static final long EXAM_DURATION = 600000; // 10 minutes in milliseconds
    private static final long EXAM_DURATION = 60000; // 1 minute in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        quizID = getIntent().getStringExtra("Quiz ID");
        ListView listview = findViewById(R.id.listview);
        Button submit = findViewById(R.id.submit);
        TextView title = findViewById(R.id.title);
        timerTextView = findViewById(R.id.timerTextView);
//        ImageView menubaru = findViewById(R.id.menubarrexam);

//        menubaru.setOnClickListener(v -> {
//            Fragment fragment = new BlankFragment();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.main, fragment).commit();
//        });

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        // Check if the user has already taken the quiz
        database.child("Quizzes").child(quizID).child("Answers").orderByChild("Email").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // User has already taken the quiz
//                            Toast.makeText(Exam.this, "You have already answered this quiz.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Exam.this,errorPage.class);
                            startActivity(intent);
                            finish(); // Close the activity
                        } else {
                            // Load the quiz
                            loadQuiz(database, listview, title);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Exam.this, "Can't connect", Toast.LENGTH_SHORT).show();
                    }
                });

        submit.setOnClickListener(v -> submitAnswers());

        startTimer(EXAM_DURATION);
    }


    private void startTimer(long duration) {
        countDownTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                submitAnswers();
            }
        }.start();
    }

    private void submitAnswers() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("Quizzes").child(quizID)
                .child("Answers").child(uid);
        int totalPoints = oldTotalPoints;
        int points = 0;
        for (int i = 0; i < data.length; i++) {
            ref.child(String.valueOf(i + 1)).setValue(data[i].getSelectedAnswer());
            if (data[i].getSelectedAnswer() == data[i].getCorrectAnswer()) {
                totalPoints++;
                points++;
            }
        }
        ref.child("Points").setValue(points);
        ref.child("Email").setValue(email); // Store the user's email

        int totalQuestions = oldTotalQuestions + data.length;
        database.child("Users").child(uid).child("Total Points").setValue(totalPoints);
        database.child("Users").child(uid).child("Total Questions").setValue(totalQuestions);
        database.child("Users").child(uid).child("Quizzes Solved").child(quizID).setValue("");

        Intent i = new Intent(Exam.this, results.class);
        i.putExtra("Quiz ID", quizID);
        startActivity(i);
        finish();
    }

    private void loadQuiz(DatabaseReference database, ListView listview, TextView title) {
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Quizzes").hasChild(quizID)) {
                    DataSnapshot ref = snapshot.child("Quizzes").child(quizID);
                    title.setText(ref.child("Title").getValue().toString());
                    int num = Integer.parseInt(ref.child("Total Questions").getValue().toString());
                    data = new Question[num];
                    for (int i = 0; i < num; i++) {
                        DataSnapshot qRef = ref.child("Questions").child(String.valueOf(i));
                        Question question = new Question();
                        question.setQuestion(qRef.child("Question").getValue().toString());
                        question.setOption1(qRef.child("Option 1").getValue().toString());
                        question.setOption2(qRef.child("Option 2").getValue().toString());
                        question.setOption3(qRef.child("Option 3").getValue().toString());
                        question.setOption4(qRef.child("Option 4").getValue().toString());
                        int ans = Integer.parseInt(qRef.child("Ans").getValue().toString());
                        question.setCorrectAnswer(ans);
                        data[i] = question;
                    }
                    ListAdapter listAdapter = new ListAdapter(data);
                    listview.setAdapter(listAdapter);
                    DataSnapshot ref2 = snapshot.child("Users").child(uid);
                    if (ref2.hasChild("Total Points")) {
                        oldTotalPoints = Integer.parseInt(ref2.child("Total Points").getValue().toString());
                    }
                    if (ref2.hasChild("Total Questions")) {
                        oldTotalQuestions = Integer.parseInt(ref2.child("Total Questions").getValue().toString());
                    }
                } else {
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Exam.this, "Can't connect", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class ListAdapter extends BaseAdapter {
        Question[] arr;

        ListAdapter(Question[] arr2) {
            arr = arr2;
        }

        @Override
        public int getCount() {
            return arr.length;
        }

        @Override
        public Object getItem(int i) {
            return arr[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.question, parent, false);
                holder = new ViewHolder();
                holder.question = convertView.findViewById(R.id.question);
                holder.option1 = convertView.findViewById(R.id.option1);
                holder.option2 = convertView.findViewById(R.id.option2);
                holder.option3 = convertView.findViewById(R.id.option3);
                holder.option4 = convertView.findViewById(R.id.option4);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.question.setText(arr[i].getQuestion());
            holder.option1.setText(arr[i].getOption1());
            holder.option2.setText(arr[i].getOption2());
            holder.option3.setText(arr[i].getOption3());
            holder.option4.setText(arr[i].getOption4());

            holder.option1.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) arr[i].setSelectedAnswer(1);
            });
            holder.option2.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) arr[i].setSelectedAnswer(2);
            });
            holder.option3.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) arr[i].setSelectedAnswer(3);
            });
            holder.option4.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) arr[i].setSelectedAnswer(4);
            });

            // Clear all RadioButtons to avoid incorrect recycling state
            holder.option1.setChecked(arr[i].getSelectedAnswer() == 1);
            holder.option2.setChecked(arr[i].getSelectedAnswer() == 2);
            holder.option3.setChecked(arr[i].getSelectedAnswer() == 3);
            holder.option4.setChecked(arr[i].getSelectedAnswer() == 4);

            return convertView;
        }

        class ViewHolder {
            TextView question;
            RadioButton option1;
            RadioButton option2;
            RadioButton option3;
            RadioButton option4;
        }
    }
}

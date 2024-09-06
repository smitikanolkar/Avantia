package com.example.avantia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ourCourses extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12;
    ImageView img1, img2, img3, img4,menu_barr;
    TextView signinn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_our_courses);

        btn1 = findViewById(R.id.enquiryy1);
        btn2 = findViewById(R.id.enquiryy2);
        btn3 = findViewById(R.id.enquiryy3);
        btn4 = findViewById(R.id.enquiryy4);
        btn5 = findViewById(R.id.enquiryy5);
        btn6 = findViewById(R.id.enquiryy6);
        btn7 = findViewById(R.id.enquiryy7);
        btn8 = findViewById(R.id.enquiryy8);
        btn9 = findViewById(R.id.enquiryy9);
        btn10 = findViewById(R.id.enquiryy10);
        btn11 = findViewById(R.id.enquiryy11);
        btn12 = findViewById(R.id.enquiryy12);
//        menu_barr = findViewById(R.id.menubarCourse);
        signinn = findViewById(R.id.signinCourses);

        img1 = findViewById(R.id.instagram2);
        img2 = findViewById(R.id.facebook2);
        img3 = findViewById(R.id.twitter2);
        img4 = findViewById(R.id.linkedin2);

        btn1.setOnClickListener(v -> startActivity(new Intent(ourCourses.this, Enquiry_form.class)));
        btn2.setOnClickListener(v -> startActivity(new Intent(ourCourses.this, Enquiry_form.class)));
        btn3.setOnClickListener(v -> startActivity(new Intent(ourCourses.this, Enquiry_form.class)));
        btn4.setOnClickListener(v -> startActivity(new Intent(ourCourses.this, Enquiry_form.class)));
        btn5.setOnClickListener(v -> startActivity(new Intent(ourCourses.this, Enquiry_form.class)));
        btn6.setOnClickListener(v -> startActivity(new Intent(ourCourses.this, Enquiry_form.class)));
        btn7.setOnClickListener(v -> startActivity(new Intent(ourCourses.this, Enquiry_form.class)));
        btn8.setOnClickListener(v -> startActivity(new Intent(ourCourses.this, Enquiry_form.class)));
        btn9.setOnClickListener(v -> startActivity(new Intent(ourCourses.this, Enquiry_form.class)));
        btn10.setOnClickListener(v -> startActivity(new Intent(ourCourses.this, Enquiry_form.class)));
        btn11.setOnClickListener(v -> startActivity(new Intent(ourCourses.this, Enquiry_form.class)));
        btn12.setOnClickListener(v -> startActivity(new Intent(ourCourses.this, Enquiry_form.class)));

        img1.setOnClickListener(v -> {
            gotoUrl("https://www.instagram.com/avanteia/?igshid=MWZjMTM2ODFkZg%3D%3D");
        });

        img2.setOnClickListener(v -> {
            gotoUrl("https://www.facebook.com/profile.php?id=61550634330052&mibextid=ZbWKwL");
        });

        img3.setOnClickListener(v -> {
            gotoUrl("https://x.com/i/flow/login?redirect_after_login=%2Favanteia_");
        });

        img4.setOnClickListener(v -> {
            gotoUrl("https://www.linkedin.com/company/avanteia-pvt-ltd/");
        });

//        menu_barr.setOnClickListener(v -> {
//            Fragment fragment = new BlankFragment();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.main, fragment).commit();
//        });

    }

    private Object gotoUrl(String url) {
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
        return null;
    }
}
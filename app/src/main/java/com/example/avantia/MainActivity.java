package com.example.avantia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    ImageView img1, img2, img3, img4, menu_bar;
    Button b1, b2, b3, b4, b5;
    TextView txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8, txt9, Signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        b1 = findViewById(R.id.enquiry1);
        b2 = findViewById(R.id.enquiry2);
        b3 = findViewById(R.id.enquiry3);
        b4 = findViewById(R.id.enquiry4);
        b5 = findViewById(R.id.whatsapp);

        txt1 = findViewById(R.id.about);
        txt2 = findViewById(R.id.blog);
        txt3 = findViewById(R.id.coursess);
        txt4 = findViewById(R.id.Services);
        txt5 = findViewById(R.id.phone);
        txt6 = findViewById(R.id.email);
        txt7 = findViewById(R.id.pernem);
        txt8 = findViewById(R.id.mapusa);
        txt9 = findViewById(R.id.margoa);
        menu_bar = findViewById(R.id.menubar);
        Signin = findViewById(R.id.signin);

        img1 = findViewById(R.id.instagram);
        img2 = findViewById(R.id.facebook);
        img3 = findViewById(R.id.twitter);
        img4 = findViewById(R.id.linkedin);

        // Set click listeners
        txt1.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, About.class)));
        txt2.setOnClickListener(v -> gotoUrl("https://medium.com/@avanteiapvtltd/master-cybersecurity-in-goa-unlock-your-potential-with-avanteias-industry-leading-courses-d683c3b31414"));
        txt3.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ourCourses.class)));
        txt4.setOnClickListener(v -> gotoUrl("https://service.avanteia.com/"));
        txt5.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MainActivity.class)));
        txt6.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MainActivity.class)));
        txt7.setOnClickListener(v -> gotoUrl("https://www.google.com/maps/place/15%C2%B043'00.6%22N+73%C2%B047'55.1%22E/@15.716856,73.7981881,18z/data=!4m4!3m3!8m2!3d15.7168333!4d73.7986389"));
        txt8.setOnClickListener(v -> gotoUrl("https://www.google.com/maps/place/Shree+Hanuman+Maharudra+Sansthan,+8%2F3,+Hutatma+Circle,+Mapusa,+Goa+403507/@15.5922697,73.8078248,17z/data=!4m6!3m5!1s0x3bbfeae3ae61a91f:0xe3e98b4676b5efd5!8m2!3d15.5910889!4d73.8101578!16s%2Fg%2F11bv2lbxty"));
        txt9.setOnClickListener(v -> gotoUrl("https://www.google.com/maps/place/Audi+Mapari+flat,+Almira+Apartments,+Madgaon,+Goa+403601/@15.2709369,73.9546619,17z/data=!4m6!3m5!1s0x3bbfb3a58644f195:0x25ceb7b5cc2780ad!8m2!3d15.2709369!4d73.9572422!16s%2Fg%2F11pcrvz6h2"));

        img1.setOnClickListener(v -> gotoUrl("https://www.instagram.com/avanteia/?igshid=MWZjMTM2ODFkZg%3D%3Dl"));
        img2.setOnClickListener(v -> gotoUrl("https://www.facebook.com/profile.php?id=61550634330052&mibextid=ZbWKwL"));
        img3.setOnClickListener(v -> gotoUrl("https://x.com/avanteia_?t=sV0G82ym9ljRla3WRy50fA&s=09"));
        img4.setOnClickListener(v -> gotoUrl("https://www.linkedin.com/company/avanteia-pvt-ltd/"));

        b1.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Enquiry_form.class)));
        b2.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Enquiry_form.class)));
        b3.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Enquiry_form.class)));
        b4.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Enquiry_form.class)));
        b5.setOnClickListener(v -> gotoUrl("https://api.whatsapp.com/send/?phone=919307402403&text&type=phone_number&app_absent=0"));

        menu_bar.setOnClickListener(v -> {
            Fragment fragment = new BlankFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main, fragment).commit();
        });

        Signin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, login.class);
            startActivity(intent);
            finish();
        });
    }

    private void gotoUrl(String url) {
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

}

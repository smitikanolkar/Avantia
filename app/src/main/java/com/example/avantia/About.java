package com.example.avantia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);

        ImageView image = findViewById(R.id.instagramu1);
        ImageView image1 = findViewById(R.id.facebooku1);
        ImageView image2 = findViewById(R.id.twitteru1);
        ImageView image3 = findViewById(R.id.linkedinu1);
//        ImageView menubaru = findViewById(R.id.menubarrAbout);

        TextView text = findViewById(R.id.aboutuu);
        TextView text1 = findViewById(R.id.bloguu);
        TextView text2 = findViewById(R.id.coursesuu);
        TextView text3 = findViewById(R.id.Servicesuu);
        TextView text4 = findViewById(R.id.pernemuu);
        TextView text5 = findViewById(R.id.mapusauu);
        TextView text6 = findViewById(R.id.margoauu);


        text.setOnClickListener(v -> startActivity(new Intent(About.this, About.class)));
        text1.setOnClickListener(v -> gotoUrl("https://avanteia.com/blog.html"));
        text2.setOnClickListener(v -> startActivity(new Intent(About.this, ourCourses.class)));
        text3.setOnClickListener(v -> gotoUrl("https://service.avanteia.com/"));
        text4.setOnClickListener(v -> gotoUrl("https://www.google.com/maps/place/15%C2%B043'00.6%22N+73%C2%B047'55.1%22E/@15.716856,73.7981881,18z/data=!4m4!3m3!8m2!3d15.7168333!4d73.7986389"));
        text5.setOnClickListener(v -> gotoUrl("https://www.google.com/maps/place/Shree+Hanuman+Maharudra+Sansthan,+8%2F3,+Hutatma+Circle,+Mapusa,+Goa+403507/@15.5922697,73.8078248,17z/data=!4m6!3m5!1s0x3bbfeae3ae61a91f:0xe3e98b4676b5efd5!8m2!3d15.5910889!4d73.8101578!16s%2Fg%2F11bv2lbxty"));
        text6.setOnClickListener(v -> gotoUrl("https://www.google.com/maps/place/Audi+Mapari+flat,+Almira+Apartments,+Madgaon,+Goa+403601/@15.2709369,73.9546619,17z/data=!4m6!3m5!1s0x3bbfb3a58644f195:0x25ceb7b5cc2780ad!8m2!3d15.2709369!4d73.9572422!16s%2Fg%2F11pcrvz6h2"));


        image.setOnClickListener(v -> gotoUrl("https://www.instagram.com/avanteia/?igshid=MWZjMTM2ODFkZg%3D%3Dl"));
        image1.setOnClickListener(v -> gotoUrl("https://www.facebook.com/profile.php?id=61550634330052&mibextid=ZbWKwL"));
        image2.setOnClickListener(v -> gotoUrl("https://x.com/avanteia_?t=sV0G82ym9ljRla3WRy50fA&s=09"));
        image3.setOnClickListener(v -> gotoUrl("https://www.linkedin.com/company/avanteia-pvt-ltd/"));
//        menubaru.setOnClickListener(v -> {
//            Fragment fragment = new BlankFragment();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.main, fragment).commit();
//        });

    }

    private void gotoUrl(String url) {
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}
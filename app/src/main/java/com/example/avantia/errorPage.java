package com.example.avantia;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

public class errorPage extends AppCompatActivity {

    private static final long DELAY_TIME_MS = 5000; // 5000 milliseconds = 5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_error_page);

        // Finish the activity after a delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish(); // Finish the activity after the delay
            }
        }, DELAY_TIME_MS);


        ImageView gifImageView = findViewById(R.id.gif_img2);
//        ImageView menubaru = findViewById(R.id.menubarrerror);
        Glide.with(this).load(R.drawable.comp_3).into(gifImageView);

//        menubaru.setOnClickListener(v -> {
//            Fragment fragment = new BlankFragment();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.main, fragment).commit();
//        });
    }
}

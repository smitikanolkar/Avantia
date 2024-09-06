package com.example.avantia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class BlankFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        TextView home = view.findViewById(R.id.home);
        TextView cource = view.findViewById(R.id.cource);
        TextView about = view.findViewById(R.id.about);
        TextView blog = view.findViewById(R.id.blog);
        TextView contact = view.findViewById(R.id.contact);
        TextView call = view.findViewById(R.id.call);
        ImageView closeImage = view.findViewById(R.id.close);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        cource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ourCourses.class);
                startActivity(intent);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), About.class);
                startActivity(intent);
            }
        });

        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://medium.com/@avanteiapvtltd/master-cybersecurity-in-goa-unlock-your-potential-with-avanteias-industry-leading-courses-d683c3b31414");
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://avanteia.com/contact.html");
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }

        });

        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });

        return view;
    }

    private void makePhoneCall() {
        String phoneNumber = "tel:+919307402403";
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(phoneNumber));

        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            startActivity(callIntent);
        } else {
            // Request the permission if not already granted
            requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, 1);
        }
    }

    private void closeFragment() {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .remove(BlankFragment.this)
                    .commit();
        }
    }
    private void gotoUrl(String url) {
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}
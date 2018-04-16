package com.example.strig.baiterekapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.strig.baiterekapp.fragments.GalleryFragment;

public class DescriptionActivity extends AppCompatActivity {

    GalleryFragment gallery;
    Button map_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        gallery = new GalleryFragment();
        map_button = findViewById(R.id.map_button);

        map_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DescriptionActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });
    }
}

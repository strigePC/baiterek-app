package com.example.strig.baiterekapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LeaderboardActivity extends AppCompatActivity {
    LinearLayout leaderboardLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        leaderboardLayout = findViewById(R.id.leaderboard_layout);

        TextView entry = new TextView(this);
        entry.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        entry.setText(String.format(getResources().getString(R.string.leaderboard_entry_text),
                "Anuar", "5/5"));

        leaderboardLayout.addView(entry);
    }
}

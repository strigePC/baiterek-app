package com.example.strig.baiterekapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Map;

public class LeaderboardActivity extends AppCompatActivity {
    TextView entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        entries = findViewById(R.id.leadeboard_entries);
    }

    public void populateLeaderboard(Map<String, Long> map) {
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            entries.setText(entries.getText().toString() + "\n"
                    + String.format(getResources().getString(R.string.leaderboard_entry_text), entry.getKey(), String.valueOf(entry.getValue())));
        }
    }
}

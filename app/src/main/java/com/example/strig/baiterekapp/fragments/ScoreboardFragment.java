package com.example.strig.baiterekapp.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreboardFragment extends ListFragment {
    public static final String TAG="LEADER_FRAGMENT_TAG";
    Map <String, Long> map = new HashMap<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Quiz");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Log.e(TAG, "onDataChange: postsnapshop value "+postSnapshot.getValue());
                    map.put(postSnapshot.getKey(), (long)postSnapshot.getValue());
                }
                Log.e(TAG, "Get Data"+map);
                populateLeaderboard(map);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: ");
//        entries = getView().findViewById(R.id.leadeboard_entries);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void populateLeaderboard(Map<String, Long> map) {
        Log.e(TAG, "populateLeaderboard: ");
        ArrayList<String> users = new ArrayList<String>();
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            Log.e(TAG, "populateLeaderboard: ");
            users.add(entry.getKey()+" "+entry.getValue());

//            entries.setText(entries.getText().toString() + "\n"
//                    + String.format(getResources().getString(R.string.leaderboard_entry_text), entry.getKey(), String.valueOf(entry.getValue())));
        }
        ArrayAdapter<String> adapter = new  ArrayAdapter <String>(getActivity(),
                android.R.layout.simple_list_item_1 ,users);
        setListAdapter(adapter);
    }
}

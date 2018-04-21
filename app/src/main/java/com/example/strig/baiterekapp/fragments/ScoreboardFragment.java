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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreboardFragment extends ListFragment {
    public static final String TAG="LEADER_FRAGMENT_TAG";
    Map <String, Long> map = new HashMap<>();
    private ArrayList<String> users= new ArrayList<>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ArrayAdapter<String> adapter = new  ArrayAdapter <>(getActivity(),
                android.R.layout.simple_list_item_1 ,users);
        setListAdapter(adapter);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query usersRef = database.getReference("Quiz").orderByValue();
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Log.e(TAG, "onDataChange: postsnapshop value "+postSnapshot.getValue());
                    users.add(postSnapshot.getKey()+" - "+postSnapshot.getValue());
                    Collections.reverse(users);
                    adapter.notifyDataSetChanged();

                }
                Log.e(TAG, "Get Data"+map);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: ");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}

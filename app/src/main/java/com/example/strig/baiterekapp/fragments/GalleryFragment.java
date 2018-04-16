package com.example.strig.baiterekapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.strig.baiterekapp.OpenGlActivity;
import com.example.strig.baiterekapp.R;

public class GalleryFragment extends Fragment implements View.OnClickListener {

    ImageView image;

    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        View view = getView();
        if (view != null) {
            image = view.findViewById(R.id.gallery_image);

            image.setImageResource(R.drawable.baiterek6);
            image.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(getActivity(), OpenGlActivity.class);
        startActivity(i);
    }
}

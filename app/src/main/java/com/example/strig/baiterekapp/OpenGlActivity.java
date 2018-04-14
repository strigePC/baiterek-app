package com.example.strig.baiterekapp;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class OpenGlActivity extends AppCompatActivity {

    private GLSurfaceView glView;   // Use GLSurfaceView
    public static final String TAG = "OPENGL_ACTIVITY_TAG";

    // Call back when the activity is started, to initialize the view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        glView = new GLSurfaceView(this);           // Allocate a GLSurfaceView
        glView.setRenderer(new MyGLRenderer(this)); // Use a custom renderer
        this.setContentView(glView);                // This activity sets to GLSurfaceView
    }

    // Call back when the activity is going into the background
    @Override
    protected void onPause() {
        super.onPause();
        glView.onPause();
    }

    // Call back after onPause()
    @Override
    protected void onResume() {
        super.onResume();
        glView.onResume();
    }
}

package com.example.strig.baiterekapp;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.example.strig.baiterekapp.helpers.MyGLRenderer;


public class OpenGlActivity extends AppCompatActivity {

    private GLSurfaceView glView;   // Use GLSurfaceView
    public static final String TAG = "OPENGL_ACTIVITY_TAG";
    float mPreviousX,mPreviousY;
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    MyGLRenderer mRenderer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        glView = new GLSurfaceView(this);
        mRenderer =new MyGLRenderer(this); // Allocate a GLSurfaceView
        glView.setRenderer(mRenderer); // Use a custom renderer
        this.setContentView(glView);
        if (getActionBar()!=null){
            Log.e(TAG, "onCreate: action bar not null");
            getActionBar().setDisplayHomeAsUpEnabled(true);// This activity sets to GLSurfaceView
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - mPreviousX;
                float dy = y - mPreviousY;
                mRenderer.mAngleX += dx * TOUCH_SCALE_FACTOR;
                mRenderer.mAngleY += dy * TOUCH_SCALE_FACTOR;
                glView.requestRender();
                break;
            case MotionEvent.ACTION_DOWN:
                MyGLRenderer.autoRotate=false;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                MyGLRenderer.autoRotate=true;

        }
        mPreviousX = x;
        mPreviousY = y;
        return true;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

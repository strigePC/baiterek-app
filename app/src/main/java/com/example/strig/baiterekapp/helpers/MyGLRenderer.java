package com.example.strig.baiterekapp.helpers;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private static float angleCube = 0;     // rotational angle in degree for cube
    private static float speedCube = 0.5f; // rotational speed for cube
    private PhotoCube cube;     // (NEW)
    public static final String TAG = "GL_RENDER_TAG";
    public float mAngleX = 0,mAngleY = 0;
    public static boolean autoRotate=true;

    // Constructor
    public MyGLRenderer(Context context) {
        Log.e(TAG, "MyGLRenderer: ");
        cube = new PhotoCube(context);    //
    }

    // Call back when the surface is first created or re-created.
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.e(TAG, "onSurfaceCreated: ");
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);  // Set color's clear-value to black
        gl.glClearDepthf(1.0f);            // Set depth's clear-value to farthest
        gl.glEnable(GL10.GL_DEPTH_TEST);   // Enables depth-buffer for hidden surface removal
        gl.glDepthFunc(GL10.GL_LEQUAL);    // The type of depth testing to do
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);  // nice perspective view
        gl.glShadeModel(GL10.GL_SMOOTH);   // Enable smooth shading of color
        gl.glDisable(GL10.GL_DITHER);      // Disable dithering for better performance

        // Setup Texture, each time the surface is created
        cube.loadTexture(gl);             // Load images into textures
        gl.glEnable(GL10.GL_TEXTURE_2D);  // Enable texture
    }

    // Call back after onSurfaceCreated() or whenever the window's size changes.
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.e(TAG, "onSurfaceChanged: ");
        if (height == 0) height = 1;   // To prevent divide by zero
        float aspect = (float)width / height;

        // Set the viewport (display area) to cover the entire window
        gl.glViewport(0, 0, width, height);

        // Setup perspective projection, with aspect ratio matches viewport
        gl.glMatrixMode(GL10.GL_PROJECTION); // Select projection matrix
        gl.glLoadIdentity();                 // Reset projection matrix
        // Use perspective projection
        GLU.gluPerspective(gl, 45, aspect, 0.1f, 100.f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);  // Select model-view matrix
        gl.glLoadIdentity();

    }

    // Call back to draw the current frame.
    @Override
    public void onDrawFrame(GL10 gl) {
        // Clear color and depth buffers
        Log.e(TAG, "onDrawFrame: mAngleX "+mAngleY+", and mAngleY "+mAngleY);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        // ----- Render the Cube -----
        gl.glLoadIdentity();                  // Reset the model-view matrix
        gl.glTranslatef(0.0f, 0.0f, -6.0f);   // Translate into the screen
        // Update the rotational angle after each refresh.
        if (autoRotate){
            gl.glRotatef(mAngleX, 0, 1.0f, 0f);
            mAngleX += speedCube;
            gl.glRotatef(mAngleY, 0.15f, 0, 0f);
            mAngleY += speedCube;
        } else {
            gl.glRotatef(mAngleX,0.0f, 1.0f, 0.0f);
            gl.glRotatef(mAngleY, 1.0f, 0.0f, 0.0f);// Rotate
        }
        cube.draw(gl);

    }
}


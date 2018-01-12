package com.example.zhuwenbo.trywithresourcesproblem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            reproduceProblem();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This works perfectly on API 19, but crashes on API < 19.
    private void reproduceProblem() throws IOException {
        File file = new File("/sdcard/whateverfile");
        file.createNewFile();
        try (FileOutputStream fos = new FileOutputStream(file);
             FileLock lock = fos.getChannel().lock()) {
            Log.d(TAG, "This is in try block, FileLock is " + lock);
        }
        Log.d(TAG, "This is after try block.");
    }
}

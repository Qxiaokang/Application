package com.example.aozun.testapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class SocketActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_socket);
    }
}

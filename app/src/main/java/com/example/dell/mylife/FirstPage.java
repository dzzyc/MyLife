package com.example.dell.mylife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by dell on 2018/4/5.
 */

public class FirstPage extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(){
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void exit(){
        finish();
    }
}

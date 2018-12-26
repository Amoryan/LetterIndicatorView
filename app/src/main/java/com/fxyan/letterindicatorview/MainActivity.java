package com.fxyan.letterindicatorview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fxyan.letterindicatorview.activity.WeChatContactActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toWeChatContact(View view) {
        startActivity(new Intent(this, WeChatContactActivity.class));
    }
}

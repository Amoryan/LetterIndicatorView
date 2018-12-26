package com.fxyan.letterindicatorview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fxyan.letterindicatorview.activity.CityListActivity;
import com.fxyan.letterindicatorview.activity.WeChatContactActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#f8f9fa"));
    }

    public void toWeChatContact(View view) {
        startActivity(new Intent(this, WeChatContactActivity.class));
    }

    public void toCityList(View view) {
        startActivity(new Intent(this, CityListActivity.class));
    }
}

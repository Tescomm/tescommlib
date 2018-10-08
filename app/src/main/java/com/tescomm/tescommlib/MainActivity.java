package com.tescomm.tescommlib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tescomm.customview.addresspicker.CityPickerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void photohelperClick(View view) {
        startActivity(new Intent(getApplicationContext(),PhoteHelperActivity.class));
    }

    public void addresspickerClick(View view){
        startActivity(new Intent(getApplicationContext(),CityPickerActivity.class));
    }
}

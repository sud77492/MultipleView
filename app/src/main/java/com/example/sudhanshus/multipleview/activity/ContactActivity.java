package com.example.sudhanshus.multipleview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;

import com.example.sudhanshus.multipleview.R;

public class ContactActivity extends AppCompatActivity {

    AppCompatEditText aiName;
    AppCompatEditText aiEmail;
    AppCompatEditText aiPhone;
    AppCompatEditText aiMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initView();
        initToolbar();
        initListener();
    }

    private void initListener() {

    }

    private void initView() {

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Tools.setSystemBarColor(this, R.color.amber_600);
    }
}

package com.example.sudhanshus.multipleview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.sudhanshus.multipleview.R;
import com.example.sudhanshus.multipleview.utils.Utils;

public class ContactActivity extends AppCompatActivity {

    AppCompatEditText acName;
    AppCompatEditText acEmail;
    AppCompatEditText acPhone;
    AppCompatEditText acMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initView();
        initToolbar();
        initListener();
    }

    private void initListener() {
        if(!validateData()) {

        }else{
            Utils.showLog(Log.ERROR, "VALIDATE", "FAIL", true);
        }
    }

    private void initView() {
        acName = findViewById(R.id.acName);
        acEmail = findViewById(R.id.acEmail);
        acPhone = findViewById(R.id.acPhone);
        acMessage = findViewById(R.id.acMessage);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Tools.setSystemBarColor(this, R.color.amber_600);
    }

    private boolean validateData(){
        Boolean check = false;
        if(Utils.isEmpty(acName)){
            acName.setError("Enter Name");
            check = true;
        }
        if(!Utils.isValidEmail1(acEmail.getText().toString())){
            acEmail.setError("Invalid Email");
            check = true;
        }
        if(Utils.isEmpty(acEmail)){
            acEmail.setError("Enter Email");
            check = true;
        }

        if(Utils.isEmpty(acPhone)){
            acPhone.setError("Enter Message");
            check = true;
        }
        if(Utils.isEmpty(acMessage)){
            acMessage.setError("Enter Message");
            check = true;
        }

        return check;
    }
}

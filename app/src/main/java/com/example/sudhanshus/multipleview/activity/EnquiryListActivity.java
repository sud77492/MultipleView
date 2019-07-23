package com.example.sudhanshus.multipleview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.sudhanshus.multipleview.R;
import com.example.sudhanshus.multipleview.adapter.EnquiryAdapter;
import com.example.sudhanshus.multipleview.model.Enquiry;
import com.example.sudhanshus.multipleview.utils.UserDetailsPref;

import java.util.ArrayList;
import java.util.List;

public class EnquiryListActivity extends AppCompatActivity {
    private FloatingActionButton fab_add;
    private ActionBar actionBar;
    private Toolbar toolbar;
    UserDetailsPref userDetailsPref;
    RecyclerView rvEnquiry;
    EnquiryAdapter enquiryAdapter;
    

    private List<Enquiry> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry_list);
        initView();
        initData();
        loginCheck();
        initToolbar();
        initListener();
    }

    private void initData() {
        userDetailsPref = UserDetailsPref.getInstance();

        rvEnquiry.setLayoutManager(new LinearLayoutManager(this));
        rvEnquiry.setHasFixedSize(true);
        enquiryAdapter = new EnquiryAdapter(this, items);
        rvEnquiry.setAdapter(enquiryAdapter);


    }

    private void loginCheck() {
        if(userDetailsPref.getIntPref(EnquiryListActivity.this, UserDetailsPref.USER_ID) != 0){
            Intent intent = new Intent(EnquiryListActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void initListener() {
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnquiryListActivity.this, EnquiryActivity.class);
                startActivity(intent);
            }
        });


        enquiryAdapter.setOnItemClickListener(new EnquiryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Enquiry obj, int position) {
                //Snackbar.make(parent_view, "Item " + obj.name + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void initView() {
        fab_add = findViewById(R.id.fab_add);
        rvEnquiry = findViewById(R.id.rvEnquiry);
        rvEnquiry = findViewById(R.id.rvEnquiry);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Drawer News");
        //Tools.setSystemBarColor(this);
    }
}

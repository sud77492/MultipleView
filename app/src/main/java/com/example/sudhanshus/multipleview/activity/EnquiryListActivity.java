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
import com.example.sudhanshus.multipleview.utils.AppConfigTags;
import com.example.sudhanshus.multipleview.utils.UserDetailsPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EnquiryListActivity extends AppCompatActivity {
    private FloatingActionButton fab_add;
    private ActionBar actionBar;
    private Toolbar toolbar;
    UserDetailsPref userDetailsPref;
    RecyclerView rvEnquiry;
    EnquiryAdapter enquiryAdapter;
    String response;
    

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
        response = userDetailsPref.getStringPref(EnquiryListActivity.this, UserDetailsPref.RESPONSE);
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArrayContact = jsonObject.getJSONArray(AppConfigTags.CONTACTDATA);
            for(int i = 0; i < jsonArrayContact.length(); i++){
                JSONObject jsonObjectContact = jsonArrayContact.getJSONObject(i).getJSONObject(AppConfigTags.CONTACT);
                items.add(new Enquiry(jsonObjectContact.getInt(AppConfigTags.ID),
                        jsonObjectContact.getInt(AppConfigTags.CLIENT_ID),
                        jsonObjectContact.getInt(AppConfigTags.PRODUCT_ID),
                        jsonObjectContact.getInt(AppConfigTags.USER_ID),
                        jsonObjectContact.getString(AppConfigTags.PRODUCT_TITLE),
                        jsonObjectContact.getString(AppConfigTags.ORDER_NO),
                        jsonObjectContact.getString(AppConfigTags.NAME),
                        jsonObjectContact.getString(AppConfigTags.EMAIL),
                        jsonObjectContact.getString(AppConfigTags.PHONE),
                        jsonObjectContact.getString(AppConfigTags.ADDRESS),
                        jsonObjectContact.getString(AppConfigTags.CITY),
                        jsonObjectContact.getString(AppConfigTags.DETAIL),
                        jsonObjectContact.getString(AppConfigTags.ACTIVE),
                        jsonObjectContact.getString(AppConfigTags.EXT_DATA),
                        jsonObjectContact.getString(AppConfigTags.CHK_MAIL),
                        jsonObjectContact.getString(AppConfigTags.CREATED),
                        jsonObjectContact.getString(AppConfigTags.MODIFIED)
                ));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

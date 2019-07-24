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
import com.example.sudhanshus.multipleview.adapter.ContactAdapter;
import com.example.sudhanshus.multipleview.model.Contact;
import com.example.sudhanshus.multipleview.utils.AppConfigTags;
import com.example.sudhanshus.multipleview.utils.UserDetailsPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {
    private FloatingActionButton fab_add;
    private ActionBar actionBar;
    private Toolbar toolbar;
    UserDetailsPref userDetailsPref;
    RecyclerView rvEnquiry;
    ContactAdapter contactAdapter;
    String response;
    

    private List<Contact> items = new ArrayList<>();

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
        response = userDetailsPref.getStringPref(ContactListActivity.this, UserDetailsPref.RESPONSE);
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArrayContact = jsonObject.getJSONArray(AppConfigTags.CONTACTDATA);
            for(int i = 0; i < jsonArrayContact.length(); i++){
                JSONObject jsonObjectContact = jsonArrayContact.getJSONObject(i);
                JSONObject jsonObject1 = jsonObjectContact.getJSONObject(AppConfigTags.CONTACT);
                //jsonObjectContact.getJSONObject(AppConfigTags.CONTACT);
                items.add(new Contact(jsonObject1.getInt(AppConfigTags.ID),
                        jsonObject1.getInt(AppConfigTags.CLIENT_ID),
                        jsonObject1.getInt(AppConfigTags.USER_ID),
                        jsonObject1.getString(AppConfigTags.NAME),
                        jsonObject1.getString(AppConfigTags.EMAIL),
                        jsonObject1.getString(AppConfigTags.PHONE),
                        jsonObject1.getString(AppConfigTags.SUBJECT),
                        jsonObject1.getString(AppConfigTags.DETAIL),
                        jsonObject1.getString(AppConfigTags.ST_DETAIL),
                        jsonObject1.getString(AppConfigTags.ACTIVE),
                        jsonObject1.getString(AppConfigTags.CREATED),
                        jsonObject1.getString(AppConfigTags.MODIFIED)
                ));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        rvEnquiry.setLayoutManager(new LinearLayoutManager(this));
        rvEnquiry.setHasFixedSize(true);
        contactAdapter = new ContactAdapter(ContactListActivity.this, items);
        rvEnquiry.setAdapter(contactAdapter);


    }

    private void loginCheck() {
        if(userDetailsPref.getIntPref(ContactListActivity.this, UserDetailsPref.USER_ID) != 0){
            Intent intent = new Intent(ContactListActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void initListener() {
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactListActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });


        contactAdapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Contact obj, int position) {

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

package com.example.sudhanshus.multipleview.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sudhanshus.multipleview.R;
import com.example.sudhanshus.multipleview.adapter.EnquiryAdapter;
import com.example.sudhanshus.multipleview.model.Enquiry;
import com.example.sudhanshus.multipleview.utils.UserDetailsPref;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab_add;
    private ActionBar actionBar;
    private Toolbar toolbar;
    UserDetailsPref userDetailsPref;
    RecyclerView rvEnquiry;
    EnquiryAdapter enquiryAdapter;
    private static final String[] ENQUIRY_ELEMENT = new String[]{
            "Enquiry", "Contact Us"
    };
    private boolean[] clicked_enquiry = new boolean[ENQUIRY_ELEMENT.length];

    private List<Enquiry> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        loginCheck();
        initToolbar();
        initNavigationMenu();
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
        if(userDetailsPref.getIntPref(MainActivity.this, UserDetailsPref.USER_ID) != 0){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void initListener() {
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EnquiryActivity.class);
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

    private void initNavigationMenu() {
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {
                Toast.makeText(getApplicationContext(), item.getTitle() + " Selected", Toast.LENGTH_SHORT).show();
                actionBar.setTitle(item.getTitle());
                Log.e("Drawer Element", ""+item.getTitle() + " - ");
                switch (item.getTitle().toString()){
                    case "Contact Us":
                        showSingleChoiceDialog();
                        break;
                }
                drawer.closeDrawers();
                return true;
            }
        });

        // open drawer at start
        //drawer.openDrawer(GravityCompat.START);
    }

    private String single_choice_selected;

    private void showSingleChoiceDialog() {
        single_choice_selected = ENQUIRY_ELEMENT[0];
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Type of Enquiry");
        builder.setSingleChoiceItems(ENQUIRY_ELEMENT, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                single_choice_selected = ENQUIRY_ELEMENT[i];
                switch(i){
                    case 0:
                        Intent contact = new Intent(MainActivity.this, EnquiryActivity.class);
                        startActivity(contact);
                        break;
                    case 1:
                        Intent enquiry = new Intent(MainActivity.this, ContactActivity.class);
                        startActivity(enquiry);
                        break;
                }
            }
        });
        builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Snackbar.make(parent_view, "selected : " + single_choice_selected, Snackbar.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.CANCEL, null);
        builder.show();
    }
}

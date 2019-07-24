package com.example.sudhanshus.multipleview.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sudhanshus.multipleview.R;
import com.example.sudhanshus.multipleview.utils.AppConfigTags;
import com.example.sudhanshus.multipleview.utils.AppConfigURL;
import com.example.sudhanshus.multipleview.utils.Constants;
import com.example.sudhanshus.multipleview.utils.NetworkConnection;
import com.example.sudhanshus.multipleview.utils.UserDetailsPref;
import com.example.sudhanshus.multipleview.utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class EnquiryActivity extends AppCompatActivity {

    AppCompatEditText acName;
    AppCompatEditText acEmail;
    AppCompatEditText acPhone;
    AppCompatEditText acAddress;
    AppCompatEditText acCity;
    
    UserDetailsPref userDetailsPref;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry);
        initView();
        initData();
        initToolbar();
        initListener();
    }

    private void initData() {
        userDetailsPref = UserDetailsPref.getInstance();
        progressDialog = new ProgressDialog(EnquiryActivity.this);
    }

    private void initListener() {

    }

    private void initView() {
        acName = findViewById(R.id.acName);
        acEmail = findViewById(R.id.acEmail);
        acPhone = findViewById(R.id.acPhone);
        acAddress = findViewById(R.id.acAddress);
        acCity = findViewById(R.id.acCity);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Tools.setSystemBarColor(this, R.color.amber_600);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //if (item.getItemId() == android.R.id.home) {
            if(!validateData()) {
                userEnquiryToServer();
            }else{
                Utils.showLog(Log.ERROR, "VALIDATE", "FAIL", true);
            }
            //finish();
        //} else {
        //    Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        //}
        return super.onOptionsItemSelected(item);
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
            acPhone.setError("Enter Phone Number");
            check = true;
        }
        if(Utils.isEmpty(acCity)){
            acCity.setError("Enter City");
            check = true;
        }
        if(Utils.isEmpty(acAddress)){
            acAddress.setError("Enter Address");
            check = true;
        }

        return check;
    }


    private void userEnquiryToServer () {
        if (NetworkConnection.isNetworkAvailable(EnquiryActivity.this)) {
            Utils.showProgressDialog(progressDialog, getResources().getString(R.string.progress_dialog_text_please_wait), true);
            Utils.showLog(Log.INFO, "" + AppConfigTags.URL, AppConfigURL.URL_SAVE_CONTACT, true);
            StringRequest strRequest1 = new StringRequest(Request.Method.POST, AppConfigURL.URL_SAVE_CONTACT,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Utils.showLog(Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);

                            if (response != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    boolean success = jsonObj.getBoolean(AppConfigTags.SUCCESS);
                                    String message = jsonObj.getString(AppConfigTags.MESSAGE);
                                    if (success) {
                                        Intent intent = new Intent(EnquiryActivity.this, EnquiryListActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Utils.showToast(EnquiryActivity.this, message, true);
                                    }
                                    progressDialog.dismiss();
                                } catch (Exception e) {
                                    progressDialog.dismiss();
                                    Utils.showToast(EnquiryActivity.this, "API ERROR", true);
                                    //Utils.showSnackBar(MainActivity.this, clMain, getResources().getString(R.string.snackbar_text_exception_occurred), Snackbar.LENGTH_LONG, getResources().getString(R.string.snackbar_action_dismiss), null);
                                    e.printStackTrace();
                                }
                            } else {
                                //Utils.showSnackBar(MainActivity.this, clMain, getResources().getString(R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources().getString(R.string.snackbar_action_dismiss), null);
                                Utils.showToast(EnquiryActivity.this, "API ERROR", true);
                                Utils.showLog(Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                            }
                            progressDialog.dismiss();
                            //swipeRefreshLayout.setRefreshing (false);
                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // swipeRefreshLayout.setRefreshing (false);
                            progressDialog.dismiss();
                            Utils.showLog(Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString(), true);
                            Utils.showToast(EnquiryActivity.this, "API ERROR", true);
                            //Utils.showSnackBar(MainActivity.this, clMain, getResources().getString(R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources().getString(R.string.snackbar_action_dismiss), null);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams () throws AuthFailureError {
                    Map<String, String> params = new Hashtable<String, String>();
                    params.put(AppConfigTags.CLINETID, "1");
                    params.put(AppConfigTags.NAME, acName.getText().toString());
                    params.put(AppConfigTags.EMAIL, acEmail.getText().toString());
                    params.put(AppConfigTags.MOBILE, acPhone.getText().toString());
                    params.put(AppConfigTags.CITY, acCity.getText().toString());
                    params.put(AppConfigTags.ADDRESS, acAddress.getText().toString());
                    params.put(AppConfigTags.FIREBASE_ID, userDetailsPref.getStringPref(EnquiryActivity.this, UserDetailsPref.FIREBASE_ID));
                    params.put(AppConfigTags.DEVICE_ID, Settings.Secure.getString(getApplicationContext().getContentResolver(),
                            Settings.Secure.ANDROID_ID));
                    params.put(AppConfigTags.DEVICE_NAME, Utils.getDeviceInfo(EnquiryActivity.this));
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, "" + params, true);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put(AppConfigTags.HEADER_API_KEY, Constants.api_key);
                    Utils.showLog(Log.INFO, AppConfigTags.HEADERS_SENT_TO_THE_SERVER, "" + params, false);
                    return params;
                }
            };
            Utils.sendRequest(strRequest1, 60);
        } else {

            Utils.showToast(EnquiryActivity.this, "API ERROR", true);
        }
    }

}

package com.example.sudhanshus.multipleview.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.balysv.materialripple.MaterialRippleLayout;
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

public class SignUpActivity extends AppCompatActivity {
    private EditText etName;
    private EditText etEmail;
    private EditText etMobile;
    private MaterialRippleLayout btCreateAccount;

    ProgressDialog progressDialog;
    UserDetailsPref userDetailsPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initView();
        initData();
        initListener();


    }

    private void initData() {
        progressDialog = new ProgressDialog(SignUpActivity.this);
        userDetailsPref = UserDetailsPref.getInstance();
    }


    private void initView() {
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etMobile = findViewById(R.id.etMobile);
        btCreateAccount = findViewById(R.id.btCreateAccount);
    }

    private void initListener() {
        btCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateData()) {
                    createAccount();
                }else{
                    Utils.showLog(Log.ERROR, "VALIDATE", "FAIL", true);
                }
            }
        });
    }

    private boolean validateData(){
        Boolean check = false;
        if(isEmpty(etName)){
            etName.setError("Enter Name");
            check = true;
        }
        if(!Utils.isValidEmail1(etEmail.getText().toString())){
            etEmail.setError("Invalid Email");
            check = true;
        }
        if(isEmpty(etEmail)){
            etEmail.setError("Enter Email");
            check = true;
        }

        if(isEmpty(etMobile)){
            etMobile.setError("Enter Mobile Number");
            check = true;
        }

        return check;
    }

    private void createAccount () {
        if (NetworkConnection.isNetworkAvailable(SignUpActivity.this)) {
            Utils.showProgressDialog(progressDialog, getResources().getString(R.string.progress_dialog_text_please_wait), true);
            Utils.showLog(Log.INFO, "" + AppConfigTags.URL, AppConfigURL.URL_REGISTER, true);
            StringRequest strRequest1 = new StringRequest(Request.Method.POST, AppConfigURL.URL_REGISTER,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Utils.showLog(Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);

                            if (response != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    boolean error = jsonObj.getBoolean(AppConfigTags.ERROR);
                                    String message = jsonObj.getString(AppConfigTags.MESSAGE);
                                    if (!error) {
                                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Utils.showToast(SignUpActivity.this, message, true);
                                        //Utils.showSnackBar(MainActivity.this, clMain, message, Snackbar.LENGTH_LONG, null, null);
                                    }
                                    progressDialog.dismiss();
                                } catch (Exception e) {
                                    progressDialog.dismiss();
                                    Utils.showToast(SignUpActivity.this, "API ERROR", true);
                                    //Utils.showSnackBar(MainActivity.this, clMain, getResources().getString(R.string.snackbar_text_exception_occurred), Snackbar.LENGTH_LONG, getResources().getString(R.string.snackbar_action_dismiss), null);
                                    e.printStackTrace();
                                }
                            } else {
                                //Utils.showSnackBar(MainActivity.this, clMain, getResources().getString(R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources().getString(R.string.snackbar_action_dismiss), null);
                                Utils.showToast(SignUpActivity.this, "API ERROR", true);
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
                            Utils.showToast(SignUpActivity.this, "API ERROR", true);
                            //Utils.showSnackBar(MainActivity.this, clMain, getResources().getString(R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources().getString(R.string.snackbar_action_dismiss), null);
                        }
                    }) {


                @Override
                protected Map<String, String> getParams () throws AuthFailureError {
                    Map<String, String> params = new Hashtable<String, String>();
                    params.put(AppConfigTags.CLINETID, "1");
                    params.put(AppConfigTags.NAME, etName.getText().toString());
                    params.put(AppConfigTags.EMAIL, etEmail.getText().toString());
                    params.put(AppConfigTags.MOBILE, etMobile.getText().toString());
                    params.put(AppConfigTags.FIREBASE_ID, userDetailsPref.getStringPref(SignUpActivity.this, UserDetailsPref.FIREBASE_ID));
                    params.put(AppConfigTags.DEVICE_ID, Settings.Secure.getString(getApplicationContext().getContentResolver(),
                            Settings.Secure.ANDROID_ID));
                    params.put(AppConfigTags.DEVICE_NAME, Utils.getDeviceInfo(SignUpActivity.this));
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

            Utils.showToast(SignUpActivity.this, "API ERROR", true);
        }
    }

    boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}

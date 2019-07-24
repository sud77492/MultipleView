package com.example.sudhanshus.multipleview.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

public class LoginActivity extends AppCompatActivity {

    private View parent_view;
    UserDetailsPref userDetailsPref;
    ProgressDialog progressDialog;
    private TextInputEditText tiEmail;
    private TextInputEditText tiPassword;
    private Button btSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initFirebase();
        initView();
        initData();
        initListener();


    }



    private void initView() {
        tiEmail = findViewById(R.id.tiEmail);
        tiPassword = findViewById(R.id.tiPassword);
        btSignIn = findViewById(R.id.btSignIn);
        parent_view = findViewById(android.R.id.content);

    }

    private void initData() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        userDetailsPref = UserDetailsPref.getInstance();
    }

    private void initListener() {
        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateData()) {
                    loginCheck();
                }else{
                    Utils.showLog(Log.ERROR, "VALIDATE", "FAIL", true);
                }
            }
        });
        ((View) findViewById(R.id.sign_up)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateData(){
        Boolean check = false;

        if(!Utils.isValidEmail1(tiEmail.getText().toString())){
            tiEmail.setError("Invalid Email");
            check = true;
        }
        if(Utils.isEmpty(tiEmail)){
            tiEmail.setError("Enter Email");
            check = true;
        }

        if(Utils.isEmpty(tiPassword)){
            tiPassword.setError("Enter Password");
            check = true;
        }

        return check;
    }


    private void loginCheck () {
        if (NetworkConnection.isNetworkAvailable(LoginActivity.this)) {
            Utils.showProgressDialog(progressDialog, getResources().getString(R.string.progress_dialog_text_please_wait), true);
            Utils.showLog(Log.INFO, "" + AppConfigTags.URL, AppConfigURL.URL_LOGIN, true);
            StringRequest strRequest1 = new StringRequest(Request.Method.POST, AppConfigURL.URL_LOGIN,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Utils.showLog(Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);

                            if (response != null) {
                                try {
                                    userDetailsPref.putStringPref(LoginActivity.this, UserDetailsPref.RESPONSE, response);
                                    JSONObject jsonObj = new JSONObject(response);
                                    boolean success = jsonObj.getBoolean(AppConfigTags.SUCCESS);
                                    String message = jsonObj.getString(AppConfigTags.MESSAGE);
                                    if (success) {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Utils.showToast(LoginActivity.this, message, true);
                                    }
                                    progressDialog.dismiss();
                                } catch (Exception e) {
                                    progressDialog.dismiss();
                                    Utils.showToast(LoginActivity.this, "API ERROR", true);
                                    //Utils.showSnackBar(MainActivity.this, clMain, getResources().getString(R.string.snackbar_text_exception_occurred), Snackbar.LENGTH_LONG, getResources().getString(R.string.snackbar_action_dismiss), null);
                                    e.printStackTrace();
                                }
                            } else {
                                //Utils.showSnackBar(MainActivity.this, clMain, getResources().getString(R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources().getString(R.string.snackbar_action_dismiss), null);
                                Utils.showToast(LoginActivity.this, "API ERROR", true);
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
                            Utils.showToast(LoginActivity.this, "API ERROR", true);
                            //Utils.showSnackBar(MainActivity.this, clMain, getResources().getString(R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources().getString(R.string.snackbar_action_dismiss), null);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams () throws AuthFailureError {
                    Map<String, String> params = new Hashtable<String, String>();
                    params.put(AppConfigTags.CLINETID, "1");
                    params.put(AppConfigTags.EMAIL, tiEmail.getText().toString());
                    params.put(AppConfigTags.PASSWORD, tiPassword.getText().toString());
                    params.put(AppConfigTags.FIREBASE_ID, userDetailsPref.getStringPref(LoginActivity.this, UserDetailsPref.FIREBASE_ID));
                    params.put(AppConfigTags.DEVICE_ID, Settings.Secure.getString(getApplicationContext().getContentResolver(),
                            Settings.Secure.ANDROID_ID));
                    params.put(AppConfigTags.DEVICE_NAME, Utils.getDeviceInfo(LoginActivity.this));
                    params.put(AppConfigTags.USER_LOGIN_KEY, "c9f0f895fb98ab9159f51fd0297e236d");
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

            Utils.showToast(LoginActivity.this, "API ERROR", true);
        }
    }

    private void initFirebase () {
        /*FirebaseInstanceId.getInstance ().getInstanceId ().addOnCompleteListener (new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete (@NonNull Task<InstanceIdResult> task) {
                //ConsumerDetailsPref consumerDetailsPref = ConsumerDetailsPref.getInstance ();
                //consumerDetailsPref.putStringPref (getApplicationContext (), ConsumerDetailsPref.CONSUMER_FIREBASE_ID, task.getResult ().getToken ());
                Log.e ("karman", "Firebase Token : " + task.getResult ().getToken ());
            }
        });*/
    }

    
}
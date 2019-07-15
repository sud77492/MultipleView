package com.example.sudhanshus.multipleview.helper;

import android.util.Log;

import com.example.sudhanshus.multipleview.utils.AppConfigTags;
import com.example.sudhanshus.multipleview.utils.UserDetailsPref;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;


public class MyFirebaseInstanceService extends FirebaseInstanceIdService {

    private static final String TAG="MyFirebaseInstanceServi";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken =           FirebaseInstanceId.getInstance().getToken();
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        /* If you want to send messages to this application instance or manage this apps subscriptions on the server side, send the Instance ID token to your app server.*/

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        Log.d("TOKEN ", refreshedToken.toString());
        UserDetailsPref userDetailsPref = UserDetailsPref.getInstance();
        userDetailsPref.putStringPref(getApplicationContext(), AppConfigTags.FIREBASE_ID, refreshedToken.toString());
    }
}
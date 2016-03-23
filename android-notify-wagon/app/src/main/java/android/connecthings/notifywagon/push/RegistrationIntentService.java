package android.connecthings.notifywagon.push;

import android.app.IntentService;

import android.connecthings.notifywagon.R;
import android.connecthings.notifywagon.model.UserNotify;
import android.connecthings.notifywagon.utils.ConnectionManagerServices;
import android.connecthings.notifywagon.utils.NwSharedPreference;
import android.connecthings.util.Log;
import android.connecthings.util.Utils;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;


public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};
    public static final String REGISTRATION_COMPLETE = "registrationComplete";

    private NwSharedPreference sharedPreference=null;

    public RegistrationIntentService() {
        super(TAG);
        sharedPreference = NwSharedPreference.getInstance();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NwSharedPreference sharedPreference = NwSharedPreference.getInstance();

        try {
            // [START register_for_gcm]
            // Initially this call goes out to the network to retrieve the token, subsequent calls
            // are local.
            // R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.
            // See https://developers.google.com/cloud-messaging/android/start for details on this file.
            // [START get_token]
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            // [END get_token]
            Log.i(TAG, "GCM Registration Token: " + token);

            // TODO: Implement this method to send any registration to your app's servers.
            sendRegistrationToServer(token);

            // Subscribe to topic channels
            subscribeTopics(token);

            // You should store a boolean that indicates whether the generated token has been
            // sent to your server. If the boolean is false, send the token to your server,
            // otherwise your server should have already received the token.

            // [END register_for_gcm]
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            pushNotificationProcessComplete(false);
        }

    }

    public void pushNotificationProcessComplete(boolean status){
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        sharedPreference.saveTokenRegistrationStatus(status);
        Intent registrationComplete = new Intent(REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    /**
     * Persist registration to third-party servers.
     *
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
       if(sharedPreference.getTokenRegistrationStatus()){
            ConnectionManagerServices.getInstance().updateUser(token, Utils.getDeviceId(getApplicationContext()), new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d(TAG, "error statusCode ", statusCode, " response ", responseString);
                    sharedPreference.saveTokenRegistrationStatus(false);
                    pushNotificationProcessComplete(false);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.d(TAG, "sucess statusCode ", statusCode, " response ", responseString);
                    pushNotificationProcessComplete(true);
                }
            });
        }else {
            UserNotify userNotify = new UserNotify(Utils.getDeviceId(this), sharedPreference.getPseudo(), token);
            ConnectionManagerServices.getInstance().saveUser(userNotify, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d(TAG, "error statusCode ", statusCode, " response ", responseString);
                    pushNotificationProcessComplete(false);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.d(TAG, "sucess statusCode ", statusCode, " response ", responseString);
                    pushNotificationProcessComplete(true);
                }
            });

        }
        //*/
    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    // [START subscribe_topics]
    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }
    // [END subscribe_topics]
}

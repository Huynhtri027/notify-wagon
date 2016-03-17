package android.connecthings.notifywagon.push;


import android.app.Fragment;
import android.connecthings.notifywagon.R;
import android.connecthings.notifywagon.utils.NwSharedPreference;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class FragmentPushTokenStatus extends Fragment{

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG="FragmentPushTokenStatus";

    private TextView tvPushTokenStatus = null;
    private BroadcastReceiver registrationBroadcastReceiver;
    private boolean isReceiverRegistered;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_push_token_status, container, false);
        tvPushTokenStatus = (TextView) view.findViewById(R.id.tvPushTokenStatus);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        registrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (NwSharedPreference.getInstance().getTokenRegistrationStatus()) {
                    tvPushTokenStatus.setText(getString(R.string.push_status_register));
                } else {
                    tvPushTokenStatus.setText(getString(R.string.push_status_register));
                }
            }
        };

        register();

    }

    public void register(){
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(getActivity(), RegistrationIntentService.class);
            getActivity().startService(intent);
        }
    }

    public void onResume(){
        super.onResume();
        if(!isReceiverRegistered){
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(registrationBroadcastReceiver, new IntentFilter(RegistrationIntentService.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }

    public void onPause(){
        super.onPause();
        if(isReceiverRegistered){
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(registrationBroadcastReceiver);
            isReceiverRegistered = false;
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(getActivity(), resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                getActivity().finish();
            }
            return false;
        }
        return true;
    }


}

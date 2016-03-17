package android.connecthings.notifywagon.beacon;

import android.connecthings.adtag.model.sdk.BeaconContent;
import android.connecthings.notifywagon.utils.NotificationUtils;
import android.connecthings.util.Log;
import android.connecthings.util.adtag.beacon.model.BeaconNotification;

/**
 */
public class NwBeaconNotification implements BeaconNotification{

    private static final String TAG = "NwBeaconNotification";

    @Override
    public int createNotification(BeaconContent beaconContent) {
        Log.d(TAG, "gogo notification ", beaconContent);
        return NotificationUtils.BEACON_NOTIFICATION;
    }

}

package android.connecthings.notifywagon.beacon;

import android.connecthings.adtag.adtagEnum.FEED_STATUS;
import android.connecthings.adtag.model.sdk.BeaconContent;
import android.connecthings.util.Log;
import android.connecthings.util.adtag.beacon.model.ApplicationState;
import android.connecthings.util.adtag.beacon.model.BeaconRange;
import android.connecthings.util.adtag.beacon.parser.AppleBeacon;

import org.altbeacon.beacon.Region;

import java.util.List;

/**
 */
public class NwBeaconRange implements BeaconRange{


    private static final String TAG = "NwBeaconRange";

    @Override
    public void didRangeBeaconsInRegion(List<AppleBeacon> beacons, List<BeaconContent> beaconContents, Region region, BeaconContent.INFORMATION_STATUS information_status, FEED_STATUS feed_status) {
        Log.d(TAG, "ranging ", beacons, " beaconContents ", beaconContents);
    }
}

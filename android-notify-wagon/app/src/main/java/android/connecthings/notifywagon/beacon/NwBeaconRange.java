package android.connecthings.notifywagon.beacon;

import android.connecthings.adtag.adtagEnum.FEED_STATUS;
import android.connecthings.adtag.model.sdk.BeaconContent;
import android.connecthings.util.Log;
import android.connecthings.util.adtag.beacon.model.BeaconRange;
import android.connecthings.util.adtag.beacon.parser.AppleBeacon;

import org.altbeacon.beacon.Region;

import java.util.List;

/**
 */
public class NwBeaconRange implements BeaconRange{


    private static final String TAG = "NwBeaconRange";
    private List<BeaconContent> previousBeaconContentList = null;
    private BeaconExitEnterCentralizer beaconExitEnterCentralizer;

    public NwBeaconRange(){
        beaconExitEnterCentralizer = BeaconExitEnterCentralizer.getInstance();
    }

    @Override
    public void didRangeBeaconsInRegion(List<AppleBeacon> beacons, List<BeaconContent> beaconContents, Region region, BeaconContent.INFORMATION_STATUS information_status, FEED_STATUS feed_status) {
        Log.d(TAG, "ranging ", beacons, " beaconContents ", beaconContents);
        if(previousBeaconContentList != null){
            beaconContents.removeAll(previousBeaconContentList);
            Log.d(TAG, "beaconContents: ", beaconContents);
            //very simple test normally beaconContents size in demo will be only 1 at maximum
            if(beaconContents.size() !=0 ){
                beaconExitEnterCentralizer.onEnter(beaconContents.get(0));
            }
        }else{
            if(beaconContents.size() !=0 ){
                beaconExitEnterCentralizer.onEnter(beaconContents.get(0));
                previousBeaconContentList = beaconContents;
            }

        }

    }
}

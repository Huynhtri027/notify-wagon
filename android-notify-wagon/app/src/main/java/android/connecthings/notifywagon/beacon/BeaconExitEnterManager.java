package android.connecthings.notifywagon.beacon;

import android.connecthings.adtag.model.sdk.BeaconContent;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class BeaconExitEnterManager {

    private static BeaconExitEnterManager INSTANCE;

    private BeaconContent previousBeaconContent;
    private BeaconContent currentBeaconContent;
    private List<BeaconContent> beaconContentHistory;


    private BeaconExitEnterManager(){
        beaconContentHistory = new ArrayList<>();
    }

    public static BeaconExitEnterManager getInstance(){
        if(INSTANCE == null){
            INSTANCE = new BeaconExitEnterManager();
        }
        return INSTANCE;
    }

    public void onEnter(BeaconContent beaconContent){
        beaconContentHistory.add(beaconContent);
        previousBeaconContent = currentBeaconContent;
        currentBeaconContent = beaconContent;
        notifyBackendAboutExitEnter();
    }

    public BeaconContent getPreviousBeaconContent(){
        return previousBeaconContent;
    }

    public BeaconContent getCurrentBeaconContent(){
        return currentBeaconContent;
    }

    private void notifyBackendAboutExitEnter(){

    }

}

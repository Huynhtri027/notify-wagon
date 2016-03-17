package android.connecthings.notifywagon.beacon;

import android.connecthings.adtag.model.sdk.BeaconContent;
import android.connecthings.notifywagon.model.Box;
import android.connecthings.notifywagon.model.NwBeacon;

import java.util.ArrayList;
import java.util.List;

/**
 *  Hack : the SDK Connecthings is not designed to do this normally
 */
public class BeaconExitEnterCentralizer {

    private static BeaconExitEnterCentralizer INSTANCE;

    private BeaconContent previousBeaconContent;
    private BeaconContent currentBeaconContent;

    private NwBeacon previousNwBeacon;
    private NwBeacon currentNwBeacon;

    private List<BeaconContent> beaconContentHistory;

    private OnEnterPlace onEnterPlace;

    private BeaconExitEnterCentralizer(){
        beaconContentHistory = new ArrayList<>();
    }

    public static BeaconExitEnterCentralizer getInstance(){
        if(INSTANCE == null){
            INSTANCE = new BeaconExitEnterCentralizer();
        }
        return INSTANCE;
    }

    public void registerOnEnterPlace(OnEnterPlace onEnterPlace){
        this.onEnterPlace = onEnterPlace;
    }

    public void unregisterOnEnterPlace(){
        this.onEnterPlace = null;
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

    private void notifyBackendAboutExitEnter() {
        previousNwBeacon = currentNwBeacon;
        currentNwBeacon = new NwBeacon(currentBeaconContent, new Box());

        if(onEnterPlace == null){
            createNotification(previousNwBeacon, currentNwBeacon);
        }else{
            onEnterPlace.onEnterPlace(previousNwBeacon, currentNwBeacon);
        }
    }

    private void createNotification(NwBeacon previousNwBeacon, NwBeacon currentNwBeacon){

    }

}

package android.connecthings.notifywagon.beacon;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.connecthings.adtag.model.sdk.BeaconContent;
import android.connecthings.notifywagon.ActivityHome;
import android.connecthings.notifywagon.R;
import android.connecthings.notifywagon.ResponseHandler.GsonResponseHandler;
import android.connecthings.notifywagon.model.AdtagModel;
import android.connecthings.notifywagon.model.Box;
import android.connecthings.notifywagon.model.EnterExitBox;
import android.connecthings.notifywagon.model.NwBeacon;
import android.connecthings.notifywagon.utils.ConnectionManagerServices;
import android.connecthings.notifywagon.utils.NotificationUtils;
import android.connecthings.notifywagon.utils.NwSharedPreference;
import android.connecthings.util.Log;
import android.connecthings.util.adtag.beacon.AdtagBeaconManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 *  Hack : the SDK Connecthings is not designed to do this normally
 */
public class BeaconExitEnterCentralizer {


    private static final String TAG = "ExitEnterCentralizer";

    private static BeaconExitEnterCentralizer INSTANCE;

    private BeaconContent previousBeaconContent;
    private BeaconContent currentBeaconContent;

    private NwBeacon previousNwBeacon;
    private NwBeacon currentNwBeacon;

    private List<BeaconContent> beaconContentHistory;

    private OnEnterPlace onEnterPlace;

    private BeaconNotificationManager beaconNotificationManager;

    ConnectionManagerServices connectionManagerServices;

    private Gson gson;

    private String pseudo;

    private BeaconExitEnterCentralizer(Context context){
        beaconContentHistory = new ArrayList<>();
        connectionManagerServices = ConnectionManagerServices.getInstance();
        beaconNotificationManager = new BeaconNotificationManager(context);
        gson = new Gson();
    }

    public static void initInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new BeaconExitEnterCentralizer(context);
        }
    }

    public static BeaconExitEnterCentralizer getInstance(){
        if(INSTANCE == null){
            throw new IllegalAccessError("must be initialized with the initInstance method on your application class");
        }
        return INSTANCE;
    }

    public void registerOnEnterPlace(OnEnterPlace onEnterPlace){
        this.onEnterPlace = onEnterPlace;
        if(currentNwBeacon!=null){
           this.onEnterPlace.onEnterPlace(previousNwBeacon, currentNwBeacon);
        }
    }

    public void unregisterOnEnterPlace(){
        this.onEnterPlace = null;
    }

    public void onEnter(BeaconContent beaconContent){
        beaconContentHistory.add(beaconContent);
        previousBeaconContent = currentBeaconContent;
        currentBeaconContent = beaconContent;
        notifyBackendAboutExitEnter(previousBeaconContent, currentBeaconContent);
    }

    public BeaconContent getPreviousBeaconContent(){
        return previousBeaconContent;
    }

    public BeaconContent getCurrentBeaconContent(){
        return currentBeaconContent;
    }


    private void notifyBackendAboutExitEnter(final BeaconContent previousBeaconContent, final BeaconContent currentBeaconContent) {
        if(TextUtils.isEmpty(pseudo)){
            pseudo = NwSharedPreference.getInstance().getPseudo();
        }
        String idPreviousBeaconContent = previousBeaconContent==null?null:previousBeaconContent.getValue(AdtagModel.CATEGORY.PLACE, AdtagModel.FIELD.ID);
        String idCurrentBeaconContent = currentBeaconContent==null?null:currentBeaconContent.getValue(AdtagModel.CATEGORY.PLACE, AdtagModel.FIELD.ID);
        Log.d(TAG, "notify backend about exit enter ", idPreviousBeaconContent, idCurrentBeaconContent);
        connectionManagerServices.updatePlaceStatus(pseudo, idPreviousBeaconContent, idCurrentBeaconContent, new GsonResponseHandler<EnterExitBox>(EnterExitBox.class) {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onBackendError(currentBeaconContent);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, EnterExitBox box) {
                onBackendSuccess(currentBeaconContent, box.getBox());
            }
        });
    }

    private void onBackendSuccess(final BeaconContent currentBeaconContent, Box box){

        previousNwBeacon = currentNwBeacon;
        currentNwBeacon = new NwBeacon(currentBeaconContent, box);

        if(onEnterPlace == null){
            beaconNotificationManager.createNotification(previousNwBeacon, currentNwBeacon);
        }else{
            onEnterPlace.onEnterPlace(previousNwBeacon, currentNwBeacon);
        }
    }

    private void onBackendError(BeaconContent beaconContent){
        if(onEnterPlace != null){
            onEnterPlace.onBackendError(currentNwBeacon, new NwBeacon(beaconContent, new Box()));
        }
    }



}

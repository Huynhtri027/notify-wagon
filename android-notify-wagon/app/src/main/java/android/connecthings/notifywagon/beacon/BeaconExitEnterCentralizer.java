package android.connecthings.notifywagon.beacon;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.connecthings.adtag.model.sdk.BeaconContent;
import android.connecthings.notifywagon.ActivityHome;
import android.connecthings.notifywagon.R;
import android.connecthings.notifywagon.model.AdtagModel;
import android.connecthings.notifywagon.model.Box;
import android.connecthings.notifywagon.model.NwBeacon;
import android.connecthings.notifywagon.utils.NotificationUtils;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

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

    private NotificationManager notificationManager;
    private Context applicationContext;

    private BeaconExitEnterCentralizer(Context context){
        beaconContentHistory = new ArrayList<>();
        applicationContext = context.getApplicationContext();
        notificationManager = (NotificationManager) applicationContext.getSystemService(context.NOTIFICATION_SERVICE);
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

    //#TODO: move it in his own class later
    private void createNotification(NwBeacon previousNwBeacon, NwBeacon currentNwBeacon){
        Intent notifyIntent = new Intent(Intent.ACTION_MAIN);
        notifyIntent.setClass(applicationContext, ActivityHome.class);

        PendingIntent intent = PendingIntent.getActivity(applicationContext, 0,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(applicationContext);
        mNotificationBuilder.setContentTitle(currentNwBeacon.getValue(AdtagModel.CATEGORY.PLACE, AdtagModel.FIELD.NAME));
        mNotificationBuilder.setContentText(new StringBuilder().append(currentNwBeacon.getValue(AdtagModel.CATEGORY.PLACE, AdtagModel.FIELD.NAME))
                .append(" line ").append(currentNwBeacon.getValue(AdtagModel.CATEGORY.LINE, AdtagModel.FIELD.DIRECTION)));

        mNotificationBuilder.setContentIntent(intent)
                            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_normal)
                            .setSound(defaultSoundUri)
                            .setAutoCancel(true);

        notificationManager.notify(NotificationUtils.BEACON_NOTIFICATION, mNotificationBuilder.getNotification());
    }

}

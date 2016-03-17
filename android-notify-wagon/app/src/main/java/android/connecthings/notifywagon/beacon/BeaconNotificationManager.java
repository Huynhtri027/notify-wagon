package android.connecthings.notifywagon.beacon;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.connecthings.notifywagon.ActivityHome;
import android.connecthings.notifywagon.R;
import android.connecthings.notifywagon.model.AdtagModel;
import android.connecthings.notifywagon.model.Message;
import android.connecthings.notifywagon.model.NwBeacon;
import android.connecthings.notifywagon.model.Wagon;
import android.connecthings.notifywagon.utils.NotificationUtils;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

/**
 */
public class BeaconNotificationManager {

    private Context applicationContext;
    private NotificationManager notificationManager;

    public BeaconNotificationManager(Context context){
        applicationContext = context.getApplicationContext();
        notificationManager = (NotificationManager) applicationContext.getSystemService(context.NOTIFICATION_SERVICE);
    }

    //#TODO: move it in his own class later
    public void createNotification(NwBeacon previousNwBeacon, NwBeacon currentNwBeacon){
        Intent notifyIntent = new Intent(Intent.ACTION_MAIN);
        notifyIntent.setClass(applicationContext, ActivityHome.class);

        PendingIntent intent = PendingIntent.getActivity(applicationContext, 0,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(applicationContext);
        mNotificationBuilder.setContentTitle(generateTitle(previousNwBeacon, currentNwBeacon));
        mNotificationBuilder.setContentText(generateDesc(previousNwBeacon, currentNwBeacon));

        mNotificationBuilder.setContentIntent(intent)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_normal)
                .setSound(defaultSoundUri)
                .setAutoCancel(true);

        notificationManager.notify(NotificationUtils.BEACON_NOTIFICATION, mNotificationBuilder.build());
    }

    public String generateTitle(NwBeacon previousNwBeacon, NwBeacon currentNwBeacon){
        return applicationContext.getString(R.string.beacon_notif_title,
                currentNwBeacon.getValue(AdtagModel.CATEGORY.PLACE, AdtagModel.FIELD.NAME),
                currentNwBeacon.getValue(AdtagModel.CATEGORY.LINE, AdtagModel.FIELD.LINE),
                currentNwBeacon.getValue(AdtagModel.CATEGORY.LINE, AdtagModel.FIELD.DIRECTION));
    }

    public String  generateDesc(NwBeacon previousNwBeacon, NwBeacon currentNwBeacon){
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        if(currentNwBeacon.getMessagePlace().size() > 0) {
            Message message = currentNwBeacon.getMessagePlace().get(currentNwBeacon.getMessageFriends().size() - 1);
            sb.append(message.getType()).append(" : ").append(message.getMessage());
            isFirst = false;
        }
        if(currentNwBeacon.getFriends().size()>0){
            if(isFirst) {
                sb.append(",\n");
            }
            int countWagon = 0;
            for(Wagon wagon : currentNwBeacon.getFriends()){
                countWagon += wagon.getUsers().size();
            }

            sb.append(applicationContext.getString(R.string.friends_in_train, countWagon));
            isFirst = false;
        }
        int otherMessages = currentNwBeacon.getMessagePlace().size() + currentNwBeacon.getMessageFriends().size() -1;
        if(otherMessages>0){
            if(isFirst) {
                sb.append(",\n");
            }
            sb.append(applicationContext.getString(isFirst?R.string.friend_messages:R.string.other_messages, otherMessages));
        }
        return sb.toString();
    }



}

package android.connecthings.notifywagon;

import android.app.Application;
import android.connecthings.notifywagon.utils.NotifyWagonSharedPreference;


/**
 * Server API Key help
 AIzaSyBbP3q6vgrDVRgX7XWNJgquq4RGRrcMfoQ
 Sender ID: help
 645651425429
 */
public class ApplicationNotifyWagon extends Application{

    public void onCreate(){
        super.onCreate();
        NotifyWagonSharedPreference.init(this);
    }
}

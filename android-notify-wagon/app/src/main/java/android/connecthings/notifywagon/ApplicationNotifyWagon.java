package android.connecthings.notifywagon;

import android.app.Application;
import android.connecthings.adtag.AdtagInitializer;
import android.connecthings.adtag.analytics.AdtagLogsManager;
import android.connecthings.notifywagon.beacon.BeaconExitEnterCentralizer;
import android.connecthings.notifywagon.beacon.NwBeaconNotification;
import android.connecthings.notifywagon.utils.NwSharedPreference;
import android.connecthings.util.adtag.beacon.AdtagBeaconManager;
import android.connecthings.util.connection.Network;
import android.connecthings.util.connection.Url;


/**
 * Server API Key help
 AIzaSyBbP3q6vgrDVRgX7XWNJgquq4RGRrcMfoQ
 Sender ID: help
 645651425429
 */
public class ApplicationNotifyWagon extends Application{

    public static final String UUID= "B0462602-CBF5-4ABB-87DE-B05340DCCBC2";


    public void onCreate(){
        super.onCreate();
        NwSharedPreference.init(this);
        AdtagInitializer.initInstance(this).initUrlType(Url.UrlType.ITG)
                .initUser("notifyWagonSdk", "GDGqldrqVKu7luz2DPyD").initCompany("hackathonalstom");
        AdtagLogsManager.initInstance(this, Network.ALL, 200, 1000 * 60 * 2);
        AdtagBeaconManager beaconManager = AdtagBeaconManager.initInstance(this, UUID);
        beaconManager.setBackgroundScanPeriod(0, 30 * 1000);
        BeaconExitEnterCentralizer.initInstance(this);
        beaconManager.saveBleAccessAuthorize(true);
        beaconManager.updateBeaconNotification(new NwBeaconNotification());
    }
}

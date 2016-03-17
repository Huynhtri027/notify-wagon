package android.connecthings.notifywagon;

import android.connecthings.notifywagon.beacon.BeaconExitEnterCentralizer;
import android.connecthings.notifywagon.beacon.NwBeaconRange;
import android.connecthings.notifywagon.beacon.OnEnterPlace;
import android.connecthings.notifywagon.model.AdtagModel;
import android.connecthings.notifywagon.model.NwBeacon;
import android.connecthings.notifywagon.utils.Adapter_Alert;
import android.connecthings.notifywagon.utils.ConnectionManagerServices;
import android.connecthings.util.BLE_STATUS;
import android.connecthings.util.Log;
import android.connecthings.util.adtag.beacon.AdtagBeaconManager;
import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class ActivityHome extends AppCompatActivity  implements OnEnterPlace{

    private static final String TAG = "ActivityHome";

    private NwBeaconRange nwBeaconRange;
    private AdtagBeaconManager adtagBeaconManager;
    private BeaconExitEnterCentralizer beaconExitEnterCentralizer;
    private TextView placeName = null;
    Context context;
    String[] messages;
    Adapter_Alert adpter_alert_message, adpter_friends, adpter_message;
    ViewPager view_Alert_place;
    ConnectionManagerServices connectionManagerServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        adtagBeaconManager = AdtagBeaconManager.getInstance();
        nwBeaconRange = new NwBeaconRange();
        beaconExitEnterCentralizer = BeaconExitEnterCentralizer.getInstance();
        view_Alert_place = (ViewPager) findViewById(R.id.viewpager1);
       // placeName = (TextView) findViewById(R.id.tv_place);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public void onResume(){
        super.onResume();
        if(adtagBeaconManager.checkBleStatus() != BLE_STATUS.NOT_SUPPORTED && adtagBeaconManager.isBleAccessAuthorize()){
            adtagBeaconManager.onActivityResumed(nwBeaconRange);
            beaconExitEnterCentralizer.registerOnEnterPlace(this);
        }
    }

    public void onPause(){
        super.onPause();
        if(adtagBeaconManager.checkBleStatus() != BLE_STATUS.NOT_SUPPORTED && adtagBeaconManager.isBleAccessAuthorize()){
            adtagBeaconManager.onActivityPaused(nwBeaconRange);
            beaconExitEnterCentralizer.unregisterOnEnterPlace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the ActivityHome/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onEnterPlace(NwBeacon previousBeacon, NwBeacon currentBeacon){
     //   placeName.setText(currentBeacon.getValue(AdtagModel.CATEGORY.PLACE, AdtagModel.FIELD.NAME));
        Log.d(TAG, "success friends: ", currentBeacon.getBox().getFriends());
        Log.d(TAG, "success message place: ", currentBeacon.getBox().getMessagePlace());
        Log.d(TAG, "success message friends: ", currentBeacon.getBox().getMessageFriends());
        adpter_alert_message = new Adapter_Alert(currentBeacon.getBox().getMessagePlace());
        view_Alert_place.setAdapter(adpter_alert_message);
    }

    public void onBackendError(NwBeacon previousBeacon, NwBeacon currentBeacon){
        Log.d(TAG, "backend error");
    }


}

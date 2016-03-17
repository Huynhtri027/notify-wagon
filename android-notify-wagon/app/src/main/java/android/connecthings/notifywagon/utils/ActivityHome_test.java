package android.connecthings.notifywagon.utils;

import android.connecthings.notifywagon.R;
import android.connecthings.notifywagon.beacon.BeaconExitEnterCentralizer;
import android.connecthings.notifywagon.beacon.NwBeaconRange;
import android.connecthings.notifywagon.beacon.OnEnterPlace;
import android.connecthings.notifywagon.model.AdtagModel;
import android.connecthings.notifywagon.model.NwBeacon;
import android.connecthings.notifywagon.utils.ConnectionManagerServices;
import android.connecthings.util.BLE_STATUS;
import android.connecthings.util.adtag.beacon.AdtagBeaconManager;
import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class ActivityHome_test extends AppCompatActivity  implements OnEnterPlace{


    private NwBeaconRange nwBeaconRange;
    private AdtagBeaconManager adtagBeaconManager;
    private BeaconExitEnterCentralizer beaconExitEnterCentralizer;

    private TextView placeName = null;

    Context context;

    String[] timeSets;
    String[] coreTargets;
    String[] equipments;

    int[] timeImages;
    int[] coreImages;
    int[] equipmentImages;

    Adapter_Alert adapter1, adapter2, adapter3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_home_test);

        context = this;

        coreTargets = new String[]{"Full body", "Core",
                "Legs", "Upper Body"};

        coreImages = new int[]{R.drawable.alert_index, R.drawable.alert_index,
                R.drawable.alert_index, R.drawable.alert_index, R.drawable.alert_index, R.drawable.alert_index};



        ViewPager view1 = (ViewPager) findViewById(R.id.viewpager1);
        ViewPager view2 = (ViewPager) findViewById(R.id.viewpager2);
        ViewPager view3 = (ViewPager) findViewById(R.id.viewpager3);

        adapter1 = new Adapter_Alert(coreTargets, coreImages);


        view1.setAdapter(adapter1);
        view2.setAdapter(adapter2);
        view3.setAdapter(adapter3);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
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
        placeName.setText(currentBeacon.getValue(AdtagModel.CATEGORY.PLACE, AdtagModel.FIELD.NAME));
    }

    @Override
    public void onBackendError(NwBeacon previousBeacon, NwBeacon currentBeacon) {

    }


}

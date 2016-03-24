package android.connecthings.notifywagon;

import android.app.DialogFragment;
import android.connecthings.notifywagon.beacon.BeaconExitEnterCentralizer;
import android.connecthings.notifywagon.beacon.NwBeaconRange;
import android.connecthings.notifywagon.beacon.OnEnterPlace;
import android.connecthings.notifywagon.fragment.DialogMessageType;
import android.connecthings.notifywagon.model.AdtagModel;
import android.connecthings.notifywagon.model.NwBeacon;
import android.connecthings.notifywagon.push.FragmentPushTokenStatus;
import android.connecthings.notifywagon.utils.AdapterFriends;
import android.connecthings.notifywagon.utils.AdapterAlert;
import android.connecthings.notifywagon.utils.AdapterWagon;
import android.connecthings.notifywagon.utils.ConnectionManagerServices;
import android.connecthings.util.BLE_STATUS;
import android.connecthings.util.Log;
import android.connecthings.util.adtag.beacon.AdtagBeaconManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ActivityHome extends AppCompatActivity  implements OnEnterPlace{

    private static final String TAG = "ActivityHome";

    private NwBeaconRange nwBeaconRange;
    private AdtagBeaconManager adtagBeaconManager;
    private BeaconExitEnterCentralizer beaconExitEnterCentralizer;
    private TextView placeName = null;
    private String[] messages;
    private AdapterAlert adpter_alert_message;
    private AdapterFriends adpter_message_friends;
    private AdapterWagon adapter_voiture;
    private ViewPager viewAlertplace, viewAlertvoiture, viewAlertMessagefriends;
    private TextView tvNotifAlert, tvNotifMessage, tvNotiffWagon;
    private TextView tvPlaceName, tvMetroLigne, tvDirection, tvVitesset, tvTempeature, tvInfoWagon;
    private ConnectionManagerServices connectionManagerServices;

    private FragmentPushTokenStatus fragmentPushTokenStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.logo_header);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(new DialogMessageType(),DialogMessageType.TAG);
            }
        });

        adtagBeaconManager = AdtagBeaconManager.getInstance();
        nwBeaconRange = new NwBeaconRange();
        beaconExitEnterCentralizer = BeaconExitEnterCentralizer.getInstance();
        viewAlertplace = (ViewPager) findViewById(R.id.viewpager1);
        viewAlertvoiture = (ViewPager) findViewById(R.id.viewpager2);
       // placeName = (TextView) findViewById(R.id.tv_place);
        viewAlertMessagefriends = (ViewPager)findViewById(R.id.viewpager3);
        //retreive notification information from box view
        tvNotifAlert = (TextView)findViewById(R.id.notificationNumberAlert);
        tvNotiffWagon = (TextView)findViewById(R.id.notificationNumberFriend);
        tvNotifMessage = (TextView)findViewById(R.id.notificationNumberMessageFriends);
        //retreive home information from box view
        tvPlaceName = (TextView)findViewById(R.id.tv_gare);
        tvMetroLigne = (TextView)findViewById(R.id.tv_ligneMetro);
        tvDirection =  (TextView)findViewById(R.id.tv_direction);
        tvVitesset = (TextView)findViewById(R.id.tv_vitesse);
        tvTempeature = (TextView)findViewById(R.id.tv_temperature);

        this.initAdapter();

        fragmentPushTokenStatus = (FragmentPushTokenStatus) getFragmentManager().findFragmentByTag(FragmentPushTokenStatus.TAG);
        if(fragmentPushTokenStatus == null){
            fragmentPushTokenStatus = new FragmentPushTokenStatus();
            getFragmentManager()
                        .beginTransaction()
                        .add(fragmentPushTokenStatus, FragmentPushTokenStatus.TAG)
                         .commit();
        }
    }

    public void showDialog(DialogFragment fragment, String name){
        fragment.show(getFragmentManager(), name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_home, menu);
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
     //  placeName.setText(currentBeacon.getValue(AdtagModel.CATEGORY.PLACE, AdtagModel.FIELD.NAME));
        Log.d(TAG, "success friends: ", currentBeacon.getBox().getWagonBox());
        Log.d(TAG, "success message place: ", currentBeacon.getBox().getMessagePlace());
        Log.d(TAG, "success message friends: ", currentBeacon.getBox().getMessageFriends());


// check for notification count
        if (currentBeacon.getBox().getMessagePlace().size()>0){
            tvNotifAlert.setText(currentBeacon.getBox().getMessagePlace().size() + "");
        }

        tvNotiffWagon.setText(currentBeacon.getBox().getWagonBox().getCount() + "");

        if (currentBeacon.getBox().getMessageFriends().size()>0){
            tvNotifMessage.setText(currentBeacon.getBox().getMessageFriends().size() + "");
        }
        if (currentBeacon!=null){
            this.setInformationToFirstBox(currentBeacon);
        }

        this.refreshUpdater(currentBeacon);
    }

    public void onBackendError(NwBeacon previousBeacon, NwBeacon currentBeacon){
        Log.d(TAG, "backend error");
    }

    public void onProgress() {

    }


    public void setInformationToFirstBox (NwBeacon currentBeacon){

        tvPlaceName.setText("");
        tvTempeature.setText("");
        tvMetroLigne.setText("");
        tvDirection.setText("");
        tvVitesset.setText("");
        tvTempeature.setText("");
        
        String placeName = currentBeacon.getValue(AdtagModel.CATEGORY.PLACE,AdtagModel.FIELD.NAME);
        String rootName = currentBeacon.getValue(AdtagModel.CATEGORY.PLACE,AdtagModel.FIELD.ROOT_NAME);
        String metroLigne = currentBeacon.getValue(AdtagModel.CATEGORY.LINE,AdtagModel.FIELD.LINE);
        String direction = currentBeacon.getValue(AdtagModel.CATEGORY.LINE,AdtagModel.FIELD.DIRECTION);
        String vitesse = currentBeacon.getValue(AdtagModel.CATEGORY.INFO_WAGON,AdtagModel.FIELD.VITESSE);
        String tempeature = currentBeacon.getValue(AdtagModel.CATEGORY.INFO_WAGON,AdtagModel.FIELD.TEMPERATURE);

        tvPlaceName.setText(rootName + " - " + placeName + "");
        tvMetroLigne.setText(getString(R.string.tv_metro_ligne, metroLigne));
        tvDirection.setText(getString(R.string.tv_metro_direction, direction));

        if(!placeName.toLowerCase().contains(getString(R.string.tv_quai))){
            tvVitesset.setText(getString(R.string.tv_metro_vitesse, vitesse));
            tvTempeature.setText(getString(R.string.tv_metro_temperature, tempeature));
        }

    }

    public void initAdapter(){

        adpter_alert_message = new AdapterAlert();
        viewAlertplace.setAdapter(adpter_alert_message);

        adpter_message_friends = new AdapterFriends();
        viewAlertMessagefriends.setAdapter(adpter_message_friends);

        adapter_voiture = new AdapterWagon();
        viewAlertvoiture.setAdapter(adapter_voiture);

    }
    public void refreshUpdater(NwBeacon currentBeacon){
        //update liste from refreshing adapter
        adpter_alert_message.updateListe(currentBeacon.getBox().getMessagePlace());
        adpter_message_friends.updateListe(currentBeacon.getBox().getMessageFriends());
        adapter_voiture.updateListe(currentBeacon);
        //update updater
        adpter_alert_message.notifyDataSetChanged();
        adpter_message_friends.notifyDataSetChanged();
        adapter_voiture.notifyDataSetChanged();
    }
}
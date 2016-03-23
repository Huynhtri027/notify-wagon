package android.connecthings.notifywagon.model;

import android.connecthings.adtag.model.Access;
import android.connecthings.adtag.model.Campaign;
import android.connecthings.adtag.model.FullPoi;
import android.connecthings.adtag.model.sdk.Beacon;
import android.connecthings.adtag.model.sdk.BeaconAlertStrategyParameter;
import android.connecthings.adtag.model.sdk.BeaconContent;
import android.connecthings.adtag.model.sdk.Technology;
import android.connecthings.util.BEACON_PROXIMITY;
import android.os.Parcel;

import java.util.ArrayList;

/**
 */
public class NwBeacon {

    private BeaconContent beaconContent;
    private Box box;

    public NwBeacon(BeaconContent beaconContent, Box box){
        this.beaconContent = beaconContent;
        this.box = box;
    }

    public Box getBox(){
        return box;
    }

    public ArrayList<Message> getMessagePlace() {
        return box.getMessagePlace();
    }

    public WagonBox getWagonBox() {
        return box.getWagonBox();
    }

    public ArrayList<Message> getMessageFriends() {
        return box.getMessageFriends();
    }

    public Beacon getBeacon() {
        return beaconContent.getBeacon();
    }

    public String getMinor() {
        return beaconContent.getMinor();
    }

    public void updateBeacon(Beacon beacon) {
        beaconContent.updateBeacon(beacon);
    }

    public void addToCache() {
        beaconContent.addToCache();
    }

    public String getMajor() {
        return beaconContent.getMajor();
    }

    public Technology getBeaconTechnology() {
        return beaconContent.getBeaconTechnology();
    }

    public String getAction() {
        return beaconContent.getAction();
    }

    public String getAlertTitle() {
        return beaconContent.getAlertTitle();
    }

    public String getAlertDescription() {
        return beaconContent.getAlertDescription();
    }

    public FullPoi getPoi() {
        return beaconContent.getPoi();
    }

    public BEACON_PROXIMITY getProximity() {
        return beaconContent.getProximity();
    }

    public void setTechnologyType(Technology.TYPE technologyType) {
        beaconContent.setTechnologyType(technologyType);
    }

    public int getRssi() {
        return beaconContent.getRssi();
    }

    public Campaign getCampaign() {
        return beaconContent.getCampaign();
    }

    public Access getAccess() {
        return beaconContent.getAccess();
    }

    public void actionIsDone() {
        beaconContent.actionIsDone();
    }

    public String getValue(String category, String field) {
        return beaconContent.getValue(category, field);
    }

    public boolean getBoolean(String category, String fieldName) {
        return beaconContent.getBoolean(category, fieldName);
    }

    public boolean isCacheStillAvailable() {
        return beaconContent.isCacheStillAvailable();
    }

    public boolean identifyTechnologyType() {
        return beaconContent.identifyTechnologyType();
    }

    public boolean isEmpty(String category, String fieldName) {
        return beaconContent.isEmpty(category, fieldName);
    }

    public String getRedirectUrl() {
        return beaconContent.getRedirectUrl();
    }

    public String getNotificationDescription() {
        return beaconContent.getNotificationDescription();
    }

    public String findRedirectUrl(Technology.TYPE type) {
        return beaconContent.findRedirectUrl(type);
    }

    public boolean isReadyForAction() {
        return beaconContent.isReadyForAction();
    }

    public String getNotificationTitle() {
        return beaconContent.getNotificationTitle();
    }

    public double getDistance() {
        return beaconContent.getDistance();
    }

    public void readFromParcel(Parcel from) {
        beaconContent.readFromParcel(from);
    }

    public boolean hasInformation() {
        return beaconContent.hasInformation();
    }

    public String getUuid() {
        return beaconContent.getUuid();
    }

    public void setBeaconAlertStrategyParameter(BeaconAlertStrategyParameter beaconAlertStrategyParameter) {
        beaconContent.setBeaconAlertStrategyParameter(beaconAlertStrategyParameter);
    }

    public BeaconAlertStrategyParameter getBeaconAlertStrategyParameter() {
        return beaconContent.getBeaconAlertStrategyParameter();
    }

    public int describeContents() {
        return beaconContent.describeContents();
    }

    public void writeToParcel(Parcel dest, int flags) {
        beaconContent.writeToParcel(dest, flags);
    }

    public Technology findTechnology(Technology.TYPE type) {
        return beaconContent.findTechnology(type);
    }

    public String getMultimediaUrl(String category, String fieldName) {
        return beaconContent.getMultimediaUrl(category, fieldName);
    }

    public String getUri() {
        return beaconContent.getUri();
    }

    public String getIdentifier() {
        return beaconContent.getIdentifier();
    }

    public Technology.TYPE getTechnologyType() {
        return beaconContent.getTechnologyType();
    }

    public void setIdentifier(String identifier) {
        beaconContent.setIdentifier(identifier);
    }
}

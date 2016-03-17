package android.connecthings.notifywagon.beacon;

import android.connecthings.notifywagon.model.NwBeacon;

/**
 */
public interface OnEnterPlace {

    public void onEnterPlace(NwBeacon previousBeacon, NwBeacon currentBeacon);

}

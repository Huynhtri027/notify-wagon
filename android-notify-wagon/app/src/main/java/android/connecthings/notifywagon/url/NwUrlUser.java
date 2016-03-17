package android.connecthings.notifywagon.url;

import android.connecthings.util.connection.Url;
import android.text.TextUtils;

/**
 */
public class NwUrlUser extends NwUrl {

    public NwUrlUser(){
        super();
    }

    public Url updatePlace(String pseudo,  String exitPlaceId, String enterPlaceId){
        exitPlaceId = TextUtils.isEmpty(exitPlaceId)?PARAMETERS.NONE:exitPlaceId;
        enterPlaceId = TextUtils.isEmpty(enterPlaceId)?PARAMETERS.NONE:enterPlaceId;
        return addPath(PATH.API_USER).addPath(pseudo).addPath(PATH.UPDATE_PLACE).addPath(exitPlaceId).addPath(enterPlaceId);
    }

    public Url saveUser(){
        return addPath("api/user");
    }


    public Url updateUser(String phoneId){
        return addPath("api/user").addPath(phoneId);
    }

}

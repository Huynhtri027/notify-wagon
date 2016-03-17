package android.connecthings.notifywagon.url;

import android.connecthings.util.connection.Url;
import android.connecthings.util.connection.UrlRoot;

/**
 * Created by ssr on 17/03/16.
 */
public class UrlNotifyWagon extends Url {

    private final static String PSEUDO ="PSEUDO";
    private final static String PLACEID ="PLACEID";
    private final static String USERID ="USERID";

    private final static String MESSAGE_BOX="MESSAGE_BOX";
    private final static String USER_TOKEN="USER_TOKEN";

    private UrlRoot urlRoot;
    public UrlNotifyWagon() {
        super();
       urlRoot=new UrlRoot("http://");
    }
    public UrlRoot getUrlRoot(){
        return urlRoot;
    }

    public Url getBoxMessage(String pseudo , String placeID ){
    return addPath(MESSAGE_BOX).addPathParameter(PSEUDO,pseudo).addPathParameter(PLACEID, placeID);
    }

    public Url getUserToUpdateToken (String userId){
      return addPath(USER_TOKEN).addPathParameter(USERID,userId);
    }

}

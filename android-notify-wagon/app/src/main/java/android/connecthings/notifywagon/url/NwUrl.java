package android.connecthings.notifywagon.url;

import android.connecthings.util.connection.Url;
import android.connecthings.util.connection.UrlRoot;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 */
public class NwUrl extends Url {

    public static final class PARAMETERS {
        public final static String PSEUDO ="PSEUDO";
        public final static String PLACEID ="PLACEID";
        public final static String USERID ="USERID";
        public static final String NONE="none";
    }

    public static final class PATH {
        public final static String API_MESSAGE="api/message/dispatch";
        public final static String API_USER="api/user";
        public final static String UPDATE_PLACE="updatePlace";
    }


    private final static String USER_TOKEN="USER_TOKEN";


    public NwUrl() {
        super(new UrlRoot[]{new NwUrlRoot(), new NwUrlRoot(), new NwUrlRoot(), new NwUrlRoot(), new NwUrlRoot()});
    }



}

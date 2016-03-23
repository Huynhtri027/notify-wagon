package android.connecthings.notifywagon.utils;

import android.connecthings.notifywagon.model.Message;
import android.connecthings.notifywagon.model.UserNotify;
import android.connecthings.notifywagon.url.NwUrlUser;
import android.connecthings.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 */
public class ConnectionManagerServices {

    private static final String TAG = "ConnectionManagerServices";

    private static ConnectionManagerServices INSTANCE;

    private AsyncHttpClient clientAsync = new AsyncHttpClient();
    private SyncHttpClient clientSync = new SyncHttpClient();

    private Gson gson;

    private ConnectionManagerServices() {
        gson = new Gson();
    }

    public static ConnectionManagerServices getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ConnectionManagerServices();
        }
        return INSTANCE;
    }

    public void updatePlaceStatus(String pseudo, String exitPlaceId, String enterPlaceId, ResponseHandlerInterface responseHandler){
        NwUrlUser urlUser = new NwUrlUser();
        urlUser.updatePlace(pseudo, exitPlaceId, enterPlaceId);
        Log.d(TAG, "urlUser ", urlUser);
        clientAsync.get(urlUser.toString(), responseHandler);
    }

    public void saveUser(UserNotify userNotify, ResponseHandlerInterface responseHandler) {
        StringEntity se = null;
        NwUrlUser urlUser = new NwUrlUser();
        urlUser.saveUser();
        Log.d(TAG, "url user: ", urlUser.toString());
        try {
            se = new StringEntity(gson.toJson(userNotify, UserNotify.class));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        clientSync.post(null, urlUser.toString(), se, "application/json", responseHandler);
    }

    public void updateUser(String token, String phoneId, ResponseHandlerInterface responseHandler){
        StringEntity se = null;
        NwUrlUser urlUser = new NwUrlUser();
        urlUser.updateUser(phoneId);
        Log.d(TAG, "url user update: ", urlUser.toString());
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("pushToken",token);
            se = new StringEntity(jsonParams.toString());
            Log.d("json",jsonParams.toString()+"");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        // clientAsync.post(null,url, se, "application/json", responseHandler);
        clientSync.put(null, urlUser.toString(), se, "application/json", responseHandler);
    }

    public void sendMessage(Message message, ResponseHandlerInterface responseHandler) {
        NwUrlUser urlUser = new NwUrlUser();
        urlUser.sendMessage();
        Log.d(TAG, "send message ", urlUser.toString());
        StringEntity se = null;
        try {
            se = new StringEntity(new Gson().toJson(message));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        clientAsync.post(null, urlUser.toString(), se, "application/json", responseHandler);
    }

}

package android.connecthings.notifywagon.utils;

import android.connecthings.notifywagon.model.AlertMessage;
import android.connecthings.notifywagon.model.Message;
import android.connecthings.notifywagon.model.UserNotify;
import android.connecthings.notifywagon.url.UrlNotifyWagon;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by ssr on 16/03/16.
 */
public class ConnectionManagerServices {

    private static AsyncHttpClient client = new AsyncHttpClient();
    private static AsyncHttpResponseHandler responseHandler;

    public ConnectionManagerServices() {
        responseHandler  = new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("SERVER FEEDBACK -", "Success Register");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("SERVER FEEDBACK-FAILED",statusCode+"");
            }
        };
    }

    public void registerUSerToJsonWebService(UserNotify userNotify, String url) {
        StringEntity se = null;
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("phoneId",userNotify.getPhoneId());
            jsonParams.put("pseudo",userNotify.getPseudo());
            se = new StringEntity(jsonParams.toString());
            Log.d("json",jsonParams.toString()+"");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        client.post(null,url, se, "application/json", responseHandler);
    }

    public void registerUSerToJsonWebServiceWithToken(UserNotify userNotify, String url) {
        StringEntity se = null;
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("phoneId",userNotify.getPhoneId());
            jsonParams.put("pseudo",userNotify.getPseudo());
            jsonParams.put("pushToken",userNotify.getTokenId());
            se = new StringEntity(jsonParams.toString());
            Log.d("json",jsonParams.toString()+"");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        client.post(null,url, se, "application/json", responseHandler);
    }

    public void sendMessageToFriendsToJsonWebService(Message message, String url) {
        StringEntity se = null;
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("message",message.getMessage());
            se = new StringEntity(jsonParams.toString());
            Log.d("json",jsonParams.toString()+"");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        client.post(null,url, se, "application/json", responseHandler);
    }

    public void sendAlertMessageToJsonWebService(AlertMessage alertMessage , String url){
        StringEntity se = null;
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("message",alertMessage.getMessage());
            jsonParams.put("type",alertMessage.getType());
            jsonParams.put("sender",alertMessage.getSender());
            jsonParams.put("places",alertMessage.getPlaces());
            se = new StringEntity(jsonParams.toString());
            Log.d("json",jsonParams.toString()+"");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        client.post(null,url, se, "application/json", responseHandler);
    }
    //update URL custom
    public void updateTokenInformation(UserNotify userNotify, String newToken){
        StringEntity se = null;
        String url =  new UrlNotifyWagon().getUserToUpdateToken(userNotify.getPhoneId()).toString();
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("pushToken",newToken);
            se = new StringEntity(jsonParams.toString());
            Log.d("json",jsonParams.toString()+"");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        // client.post(null,url, se, "application/json", responseHandler);
        client.put(null, url, se, "application/json", responseHandler);
    }


}

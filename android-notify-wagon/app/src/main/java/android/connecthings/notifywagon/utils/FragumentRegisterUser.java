package android.connecthings.notifywagon.utils;


import android.app.Fragment;
import android.connecthings.notifywagon.Model.Personne;
import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

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
public class FragumentRegisterUser extends Fragment  {

    public static final String TAG="fragmentRegisterUser";
    private static AsyncHttpClient client = new AsyncHttpClient();

    private static AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            Log.d(TAG,"Success Register");
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Log.d(TAG,"Failed Sorry");
        }
    };

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

    }

    public void registerJSonObject(Personne personne, String url) {
        StringEntity se = null;
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("phoneId",personne.getPhoneId());
            jsonParams.put("pseudo",personne.getPseudo());
            se = new StringEntity(jsonParams.toString());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        client.post(null,url, se, "application/json", responseHandler);
    }


}

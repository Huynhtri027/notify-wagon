package android.connecthings.notifywagon.ResponseHandler;

import android.connecthings.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 */
public abstract class GsonResponseHandler<ToParse> extends TextHttpResponseHandler{

    private static final String TAG = "GsonHandler";

    private Class<ToParse> mClass;
    private Gson mGson;

    public GsonResponseHandler(Class<ToParse> mClass){
        this.mClass = mClass;
        mGson = new Gson();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        Log.d(TAG, "error statusCode ", statusCode, " response ", responseString);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        Log.d(TAG, "sucess statusCode ", statusCode, " response ", responseString);
        onSuccess(statusCode, headers, parseObject(responseString));
    }

    public abstract void onSuccess(int statusCode, Header[] headers, ToParse toParse);

    public ToParse parseObject(String responseString){
        return mGson.fromJson(responseString, mClass);
    }
}

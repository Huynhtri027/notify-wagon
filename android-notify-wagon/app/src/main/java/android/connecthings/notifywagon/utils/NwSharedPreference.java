package android.connecthings.notifywagon.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 */
public class NwSharedPreference {

    private static NwSharedPreference INSTANCE;
    private static final String TOKEN_REGISTRATION_STATUS="android.connecthings.tokenRegistrationStatus";
    private static final String PSEUDO="com.connecthings.pseudo";

    SharedPreferences sharedPreferences;

    private NwSharedPreference(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    public static void init(Context context){
        if(INSTANCE == null){
            INSTANCE = new NwSharedPreference(context);
        }
    }

    public static NwSharedPreference getInstance(){
        if(INSTANCE == null){
            throw new IllegalAccessError("NwSharedPreference must be initialized using the init method in your Application class");
        }
        return INSTANCE;
    }

    public void saveTokenRegistrationStatus(boolean status){
        sharedPreferences.edit().putBoolean(TOKEN_REGISTRATION_STATUS, status).apply();
    }

    public boolean getTokenRegistrationStatus(){
        return sharedPreferences.getBoolean(TOKEN_REGISTRATION_STATUS, false);
    }

    public void savePseudo(String pseudo){
        sharedPreferences.edit().putString(PSEUDO, pseudo).apply();
    }

    public String getPseudo(){
        return sharedPreferences.getString(PSEUDO, "");
    }

}

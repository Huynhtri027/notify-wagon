package android.connecthings.notifywagon.model;

/**
 */
public class UserNotify {

    String phoneId;
    String pseudo;
    String pushToken;

    public UserNotify(String phoneId) {
        this.phoneId = phoneId;
    }

    public UserNotify(String phoneId, String pseudo, String token) {
        this.phoneId = phoneId;
        this.pseudo = pseudo;
        this.pushToken = token;
    }


    public String getPhoneId() {
        return phoneId;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getPushToken() {
        return pushToken;
    }
}

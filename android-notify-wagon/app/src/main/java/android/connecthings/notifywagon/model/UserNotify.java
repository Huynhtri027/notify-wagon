package android.connecthings.notifywagon.model;

/**
 * Created by ssr on 16/03/16.
 */
public class UserNotify {

    String phoneId;
    String pseudo;

    public UserNotify(String phoneId) {
        this.phoneId = phoneId;
    }

    public UserNotify(String phoneId, String pseudo) {
        this.phoneId = phoneId;
        this.pseudo = pseudo;
    }

    public String getPhoneId() {
        return phoneId;
    }
    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }
    public String getPseudo() {
        return pseudo;
    }
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}

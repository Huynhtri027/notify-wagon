package android.connecthings.notifywagon.Model;

/**
 * Created by ssr on 16/03/16.
 */
public class Personne {

    String phoneId;
    String pseudo;

    public Personne(String phoneId) {
        this.phoneId = phoneId;
    }

    public Personne(String phoneId, String pseudo) {
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

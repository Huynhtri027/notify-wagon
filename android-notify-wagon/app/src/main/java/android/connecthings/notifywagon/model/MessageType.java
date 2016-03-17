package android.connecthings.notifywagon.model;

/**
 * Created by ssr on 16/03/16.
 */
public class MessageType extends Message {
    public String type;
    public String sender;
    public String places;

    public MessageType(String message, String type, String sender, String places) {
        super(message);
        this.type = type;
        this.sender = sender;
        this.places = places;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places;
    }

    public MessageType(String message) {
        super(message);
    }
}

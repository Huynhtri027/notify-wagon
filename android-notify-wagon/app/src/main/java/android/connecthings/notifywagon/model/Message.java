package android.connecthings.notifywagon.model;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class Message {

    public static enum TYPE{social, alert};

    private String message;
    private String sender;
    private TYPE type;
    private List<String> places;
    private String direction;
    private String line;

    public Message(){
        places = new ArrayList<>();
    }

    public Message(String message, String sender, TYPE type, String placeId, String direction, String line) {
        this.message = message;
        this.sender = sender;
        this.type = type;
        this.places = new ArrayList<>();
        this.places.add(placeId);
    }

    public Message(String message, String sender, TYPE type, String direction, String line) {
        this.message = message;
        this.sender = sender;
        this.type = type;
        this.direction = direction;
        this.line = line;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public TYPE getType() {
        return type;
    }

    public List<String> getPlaces() {
        return places;
    }

    public String getDirection() {
        return direction;
    }

    public String getLine() {
        return line;
    }
}

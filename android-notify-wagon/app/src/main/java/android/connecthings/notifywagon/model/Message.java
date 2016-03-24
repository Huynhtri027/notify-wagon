package android.connecthings.notifywagon.model;

import android.connecthings.notifywagon.R;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class Message {

    public static enum TYPE{alert(R.string.title_alert),
                            social(R.string.title_social),
                            clean(R.string.title_clean),
                            lost(R.string.title_lost),
                            agression(R.string.title_agression),
                            ill(R.string.title_ill),
                            pickpocket(R.string.title_pickpocket);

        private final int titleId;

        TYPE(int titleId) {
            this.titleId = titleId;
        }

        public int getTitleId() {
            return titleId;
        }
    };

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
        if(!TextUtils.isEmpty(placeId)) {
            this.places.add(placeId);
        }
        this.direction = direction;
        this.line = line;
    }

    public Message(String message, String sender, TYPE type, String placeId) {
       this(message, sender, type, placeId, null, null);
    }

    public Message(String message, String sender, TYPE type, String direction, String line) {
        this(message, sender, type, null, direction, line);
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

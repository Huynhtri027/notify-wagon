package android.connecthings.notifywagon.model;

import java.util.ArrayList;

/**
 */
public class Box {

    ArrayList<Message> messagePlace;
    ArrayList<Message> messageFriends;
    WagonBox wagonBox;

    public Box() {
        this.messageFriends =  new ArrayList<Message>();
        this.messagePlace =  new ArrayList<Message>();
    }



    public ArrayList<Message> getMessagePlace() {
        return messagePlace;
    }

    public ArrayList<Message> getMessageFriends() {
        return messageFriends;
    }

    public WagonBox getWagonBox() {
        return wagonBox;
    }
}

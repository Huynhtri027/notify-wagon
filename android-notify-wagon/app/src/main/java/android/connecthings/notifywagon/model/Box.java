package android.connecthings.notifywagon.model;

import java.util.ArrayList;

/**
 */
public class Box {

    ArrayList<Message> messagePlace;
    ArrayList<Message> messageFriends;
    ArrayList<Wagon> friends;

    public Box() {
        this.friends = new ArrayList<>();
        this.messageFriends =  new ArrayList<Message>();
        this.messagePlace =  new ArrayList<Message>();
    }


    public ArrayList<Message> getMessagePlace() {
        return messagePlace;
    }

    public ArrayList<Message> getMessageFriends() {
        return messageFriends;
    }

    public ArrayList<Wagon> getFriends() {
        return friends;
    }
}

package android.connecthings.notifywagon.model;

import java.util.ArrayList;

/**
 */
public class Box {

    ArrayList<MessageType> messagePlace;
    ArrayList<MessageType> messageFriends;
    ArrayList<String> friends;

    public Box() {
        this.friends = new ArrayList<String>();
        this.messageFriends =  new ArrayList<MessageType>();
        this.messagePlace =  new ArrayList<MessageType>();
    }

    public void addFriendsToList(String name){
        this.friends.add(name);
    }
    public void addMessagePlace(MessageType messagePlace){
        this.messagePlace.add(messagePlace);
    }
    public void addMessageFriends(MessageType messageFriend){
        messageFriend.setType("social");
        this.messagePlace.add(messageFriend);
    }

    public ArrayList<MessageType> getMessagePlace() {
        return messagePlace;
    }

    public ArrayList<MessageType> getMessageFriends() {
        return messageFriends;
    }

}

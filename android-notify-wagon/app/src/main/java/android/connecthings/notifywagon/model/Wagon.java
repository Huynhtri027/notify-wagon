package android.connecthings.notifywagon.model;

import java.util.List;

/**
 */
public class Wagon {

    private String id;
    private String name;
    private int position;
    private List<String> users;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public List<String> getUsers() {
        return users;
    }
}

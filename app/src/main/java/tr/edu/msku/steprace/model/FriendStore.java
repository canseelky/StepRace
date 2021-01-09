package tr.edu.msku.steprace.model;

import java.util.ArrayList;
import java.util.List;

public class FriendStore {

    private static List<Friend> friends = new ArrayList<>();

    public FriendStore() {
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }
}

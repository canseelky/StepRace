package tr.edu.msku.steprace.model;

import java.util.ArrayList;
import java.util.List;


public class UserStore {
    private static List<User> users = new ArrayList<>();

    public List<User> getUsers() {

        return users;
    }

    public void setUsers(List<User> users) {

        this.users = users;
    }

}


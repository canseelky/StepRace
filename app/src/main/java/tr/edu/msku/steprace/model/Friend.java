package tr.edu.msku.steprace.model;

public class Friend {
    private String name;
    private String surname;
    private String ppUrl;
    private String id;

    public Friend() {
    }


    public Friend(String id) {
        this.id = id;

    }

    public Friend(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPpUrl() {
        return ppUrl;
    }

    public void setPpUrl(String ppUrl) {
        this.ppUrl = ppUrl;
    }
}

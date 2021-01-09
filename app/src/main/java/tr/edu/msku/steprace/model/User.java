package tr.edu.msku.steprace.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public  class User {
    private String name;
    private String surname;
    private String email;
    private Date dateOfBirth;
    private URL image;
    private int numOfSteps;
    private Date date;
    private List<Friend> friends;


    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;

    }


    public User(String name, String surname, String email, Date dateOfBirth, URL image, int numOfSteps, Date date) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.image = image;
        this.numOfSteps = numOfSteps;
        this.date = date;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public URL getImage() {
        return image;
    }

    public void setImage(URL image) {
        this.image = image;
    }

    public int getNumOfSteps() {
        return numOfSteps;
    }

    public void setNumOfSteps(int numOfSteps) {
        this.numOfSteps = numOfSteps;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }



    @Override
    public String toString() {
        return "Users{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", numOfSteps=" + numOfSteps +
                ", date=" + date +
                '}';
    }
}










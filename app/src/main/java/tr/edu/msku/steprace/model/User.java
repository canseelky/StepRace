package tr.edu.msku.steprace.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public  class User {
    private String name;
    private String surname;
    private String city;
    private String email;
    private String dateOfBirth;
    private URL image;
    private String user_id;


    public User(String name, String surname,String city) {
        this.name = name;
        this.surname = surname;
        this.city =city;

    }

    public String getUser_id() {

        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public User(String name, String surname, String user_id, String city, String email, String dateOfBirth) {
        this.name = name;
        this.city =city;
        this.surname = surname;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.user_id = user_id;
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
    public String getCity() {
        return city;
    }

    public void setCity(String name) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public URL getImage() {
        return image;
    }

    public void setImage(URL image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "Users{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}










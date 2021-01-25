package tr.edu.msku.steprace.model;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User implements Serializable {
    private String name;
    private String surname;
    private String city;
    private String email;
    private String dateOfBirth;
    private String user_id;
    private int month_data;


    public User() {

    }
    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public User(String name, String surname,String city) {
        this.name = name;
        this.surname = surname;
        this.city =city;

    }

    public User(String name, String surname, String city, String user_id) {
        this.name = name;
        this.city =city;
        this.surname = surname;
        this.user_id = user_id;
    }

    public User(String name, int month_data) {
        this.name = name;
        this.month_data = month_data;


    }

    public User(String user_id) {
        this.user_id =user_id;

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



    public int getMonth_data() {
        return month_data;
    }

    public void setMonth_data(int month_data) {
        this.month_data = month_data;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}










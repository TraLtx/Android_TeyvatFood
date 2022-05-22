package com.example.teyvatfood.model;

import java.io.Serializable;

public class Account implements Serializable {

    private int id;
    private String username;
    private String password;

    private Address address;
    private Information information;

    public Account() {
        super();
    }

    public Account(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Account(String username, String password, Address address, Information information) {
        super();
        this.username = username;
        this.password = password;
        this.address = address;
        this.information = information;
    }

    public Account(int id, String username, String password, Address address, Information information) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.address = address;
        this.information = information;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Information getInfomation() {
        return information;
    }

    public void setInfomation(Information information) {
        this.information = information;
    }
}

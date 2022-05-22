package com.example.teyvatfood.model;

import java.io.Serializable;

public class Information implements Serializable {

    private int id;
    private String name;
    private String phone;


    public Information() {
        super();
    }

    public Information(String name, String phone) {
        super();
        this.name = name;
        this.phone = phone;
    }

    public Information(int id, String name, String phone) {
        super();
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

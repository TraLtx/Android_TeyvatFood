package com.example.teyvatfood.model;

import java.io.Serializable;

public class Food implements Serializable {

    private int id;
    private String image;
    private String fname;
    private String fdescribe;
    private String ftype;
    private String country;
    private float price;

    public Food() {
    }

    public Food(String image, String fname, String fdescribe, String ftype, String country, float price) {
        this.image = image;
        this.fname = fname;
        this.fdescribe = fdescribe;
        this.ftype = ftype;
        this.country = country;
        this.price = price;
    }

    public Food(int id, String image, String fname, String fdescribe, String ftype, String country, float price) {
        this.id = id;
        this.image = image;
        this.fname = fname;
        this.fdescribe = fdescribe;
        this.ftype = ftype;
        this.country = country;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFdescribe() {
        return fdescribe;
    }

    public void setFdescribe(String fdescribe) {
        this.fdescribe = fdescribe;
    }

    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

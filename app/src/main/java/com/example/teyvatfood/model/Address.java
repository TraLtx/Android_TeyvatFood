package com.example.teyvatfood.model;

import java.io.Serializable;

public class Address implements Serializable {

    private int id;
    private String city;
    private String district;
    private String street;
    private String numHouse;


    public Address() {
        super();
    }

    public Address(String city, String district, String street, String numHouse) {
        super();
        this.city = city;
        this.district = district;
        this.street = street;
        this.numHouse = numHouse;
    }

    public Address(int id, String city, String district, String street, String numHouse) {
        super();
        this.id = id;
        this.city = city;
        this.district = district;
        this.street = street;
        this.numHouse = numHouse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumHouse() {
        return numHouse;
    }

    public void setNumHouse(String numHouse) {
        this.numHouse = numHouse;
    }
}

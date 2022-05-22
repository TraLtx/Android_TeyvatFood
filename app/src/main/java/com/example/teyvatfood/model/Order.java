package com.example.teyvatfood.model;

import java.io.Serializable;

public class Order implements Serializable {

    private int id;
    private String cusName;
    private String cusPhone;
    private String cusAddress;
    private String createDate;
    private int orderPrice;
    private String orderStatus;
    private Cart cart;
    private Account account;

    public Order() {
    }

    public Order(String cusName, String cusPhone, String cusAddress, String createDate, String orderStatus, Cart cart) {
        this.cusName = cusName;
        this.cusPhone = cusPhone;
        this.cusAddress = cusAddress;
        this.createDate = createDate;
        this.orderStatus = orderStatus;
        this.cart = cart;
    }

    public Order(int id, String cusName, String cusPhone, String cusAddress, String createDate, String orderStatus, Cart cart) {
        this.id = id;
        this.cusName = cusName;
        this.cusPhone = cusPhone;
        this.cusAddress = cusAddress;
        this.createDate = createDate;
        this.orderStatus = orderStatus;
        this.cart = cart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusPhone() {
        return cusPhone;
    }

    public void setCusPhone(String cusPhone) {
        this.cusPhone = cusPhone;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

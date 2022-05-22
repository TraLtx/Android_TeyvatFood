package com.example.teyvatfood.model;

import java.io.Serializable;
import java.util.List;

public class Cart implements Serializable {

    private int id;
    private String status;
    private Account account;
    private List<FoodOrder> listFoodOrder;

    public Cart(int id, String status, Account account, List<FoodOrder> listFoodOrder) {
        this.id = id;
        this.status = status;
        this.account = account;
        this.listFoodOrder = listFoodOrder;
    }

    public Cart(String status, Account account, List<FoodOrder> listFoodOrder) {
        this.status = status;
        this.account = account;
        this.listFoodOrder = listFoodOrder;
    }

    public Cart() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<FoodOrder> getListFoodOrder() {
        return listFoodOrder;
    }

    public void setListFoodOrder(List<FoodOrder> listFoodOrder) {
        this.listFoodOrder = listFoodOrder;
    }
}

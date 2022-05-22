package com.example.teyvatfood.model;

import java.io.Serializable;

public class FoodOrder implements Serializable {

    private int id;
    private int quantity;
    private float price;

    private Food food;

    //
    private int cartID;

    public FoodOrder() {
    }

    public FoodOrder(int quantity, float price, Food food) {
        this.quantity = quantity;
        this.price = price;
        this.food = food;
    }

    public FoodOrder(int id, int quatity, float price, Food food) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.food = food;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuatity() {
        return quantity;
    }

    public void setQuatity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }
}

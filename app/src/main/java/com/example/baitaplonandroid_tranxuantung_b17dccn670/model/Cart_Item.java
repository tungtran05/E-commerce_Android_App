package com.example.baitaplonandroid_tranxuantung_b17dccn670.model;

import java.io.Serializable;

public class Cart_Item implements Serializable {

    public static final long serialVersionUID = 3L;

    private long id;

    private int quantity;

    private Item item;

    public Cart_Item(long id, int quantity, Item item) {
        this.id = id;
        this.quantity = quantity;
        this.item = item;
    }

    public Cart_Item() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Cart_Item{" +
                "id=" + id +
                ", item=" + item +
                '}';
    }
}

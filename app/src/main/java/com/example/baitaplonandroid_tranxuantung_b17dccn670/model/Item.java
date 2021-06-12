package com.example.baitaplonandroid_tranxuantung_b17dccn670.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Item implements Serializable {

    public static final long serialVersionUID = 1L;

    private long id;

    private String name;

    private String color;

    private String size;

    private float sellPrice;

    private float salePrice;

    private int quantity;

    private boolean active;

    private Date createdAt;

    private Date updatedAt;

    private List<ItemImage> itemImageList;

    public Item(long id, String name, String color, String size, float sellPrice, float salePrice, int quantity, boolean active, Date createdAt, Date updatedAt, List<ItemImage> itemImageList) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.size = size;
        this.sellPrice = sellPrice;
        this.salePrice = salePrice;
        this.quantity = quantity;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.itemImageList = itemImageList;
    }

    public Item() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public float getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(float sellPrice) {
        this.sellPrice = sellPrice;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<ItemImage> getItemImageList() {
        return itemImageList;
    }

    public void setItemImageList(List<ItemImage> itemImageList) {
        this.itemImageList = itemImageList;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", sellPrice=" + sellPrice +
                ", salePrice=" + salePrice +
                ", quantity=" + quantity +
                ", active=" + active +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", itemImageList=" + itemImageList +
                '}';
    }
}

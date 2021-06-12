package com.example.baitaplonandroid_tranxuantung_b17dccn670.model;

public class OrderHistory {

    private long id;
    private String recipientName;
    private String recipientPhoneNumber;
    private String createdAt;
    private String status;
    private String paymentMethod;
    private float totalPayment;

    public OrderHistory() {
    }

    public OrderHistory(long id, String recipientName, String recipientPhoneNumber, String createdAt, String status, String paymentMethod, float totalPayment) {
        this.id = id;
        this.recipientName = recipientName;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.createdAt = createdAt;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.totalPayment = totalPayment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientPhoneNumber() {
        return recipientPhoneNumber;
    }

    public void setRecipientPhoneNumber(String recipientPhoneNumber) {
        this.recipientPhoneNumber = recipientPhoneNumber;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public float getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(float totalPayment) {
        this.totalPayment = totalPayment;
    }

    @Override
    public String toString() {
        return "OrderHistory{" +
                "id=" + id +
                ", recipientName='" + recipientName + '\'' +
                ", recipientPhoneNumber='" + recipientPhoneNumber + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", status='" + status + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", totalPayment='" + totalPayment + '\'' +
                '}';
    }
}

package com.example.nkinvoicing;

import java.io.Serializable;
import java.util.Date;

public class TableItem implements Serializable {
    String description;
    Date date;
    int quantity;
    Double price;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmount() {
        return price*quantity;
    }




    public TableItem(String description, Date date, int quantity, Double price) {
        this.description = description;
        this.date = date;
        this.quantity = quantity;
        this.price = price;
    }
}

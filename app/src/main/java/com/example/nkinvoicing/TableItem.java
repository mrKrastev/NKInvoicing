package com.example.nkinvoicing;

import java.io.Serializable;
import java.util.Date;

public class TableItem implements Serializable {
    String description;
    String date;
    int quantity;
    Double price;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    @Override
    public String toString() {
        return "TableItem{" +
                "description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

    public TableItem(String description, String date, int quantity, Double price) {
        this.description = description;
        this.date = date;
        this.quantity = quantity;
        this.price = price;
    }

}

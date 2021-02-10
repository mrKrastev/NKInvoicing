package com.example.nkinvoicing;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class InvoiceData implements Serializable {
    URI logoImage;
    Contacts contacts;
    List<TableItem> tbItems;
    String invoiceNo;
    String invoiceDate;
    String dueDate;
    Boolean invoicePaid=false;
    private String ID;

    public InvoiceData(Contacts contacts, String invoiceNo, String invoiceDate, String dueDate) {
        ID= UUID.randomUUID().toString();
        this.contacts = contacts;
        this.tbItems = new ArrayList<>();
        this.invoiceNo = invoiceNo;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
        logoImage= null;
    }

    public String getID(){
        return ID;
    }
    @Override
    public String toString() {
        return "InvoiceData{" +
                "contacts=" + contacts.toString() +
                ", tbItems=" + tbItems +
                ", invoiceNo='" + invoiceNo + '\'' +
                ", invoiceDate='" + invoiceDate + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", invoicePaid=" + invoicePaid +
                '}';
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void addTableItem(TableItem tbitem){
        tbItems.add(tbitem);
    }

    public void removeTableItem(TableItem tbitem){
        tbItems.remove(tbitem);
    }

    public List<TableItem> getTableItems(){
        return tbItems;
    }


    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getInvoicePaid() {
        return invoicePaid;
    }

    public void setInvoicePaid(Boolean invoicePaid) {
        this.invoicePaid = invoicePaid;
    }

    public void clearTableItems() {
        tbItems=new ArrayList<TableItem>();
    }
}

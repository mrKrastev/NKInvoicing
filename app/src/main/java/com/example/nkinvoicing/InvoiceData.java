package com.example.nkinvoicing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class InvoiceData implements Serializable {
    Contacts contacts;
    List<TableItem> tbItems;
    String invoiceNo;
    String invoiceDate;
    String dueDate;
    Boolean invoicePaid=false;

    public InvoiceData(Contacts contacts, String invoiceNo, String invoiceDate, String dueDate) {
        this.contacts = contacts;
        this.tbItems = new ArrayList<>();
        this.invoiceNo = invoiceNo;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
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
}

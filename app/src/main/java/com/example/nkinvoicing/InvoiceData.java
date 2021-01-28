package com.example.nkinvoicing;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class InvoiceData implements Serializable {
    Contacts contacts;
    List <TableItem> tbItems;
    String invoiceNo;
    Date invoiceDate;
    Date dueDate;
    Boolean invoicePaid=false;

    public InvoiceData(Contacts contacts, List<TableItem> tbItems, String invoiceNo, Date invoiceDate, Date dueDate) {
        this.contacts = contacts;
        this.tbItems = tbItems;
        this.invoiceNo = invoiceNo;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
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

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getInvoicePaid() {
        return invoicePaid;
    }

    public void setInvoicePaid(Boolean invoicePaid) {
        this.invoicePaid = invoicePaid;
    }
}

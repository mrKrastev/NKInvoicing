package com.example.nkinvoicing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MyDatabaseManager extends SQLiteOpenHelper {
    public static final String INVOICES_TABLE = "INVOICES_TABLE";
    public static final String CONTACTS = "CONTACTS";
    private static final String CONTACTS_TABLE = CONTACTS + "_TABLE";
    private static final String ITEMS_TABLE = "ITEMS_TABLE";
    public static final String INVOICE_NO_COLUMN = "INVOICE_NO_COLUMN";
    public static final String DATE_COLUMN = "DATE_COLUMN";
    public static final String ISSUE_DATE_COLUMN = "ISSUE_" + DATE_COLUMN;
    public static final String DUE_DATE_COLUMN = "DUE_" + DATE_COLUMN;
    public static final String PAID_COLUMN = "PAID_COLUMN";
    public static final String USER_NAME_COLUMN = "USER_NAME_COLUMN";
    public static final String RECEIVER_NAME_COLUMN = "RECEIVER_NAME_COLUMN";
    public static final String USER_POSTCODE_COLUMN = "USER_POSTCODE_COLUMN";
    public static final String RECEIVER_POSTCODE_COLUMN = "RECEIVER_POSTCODE_COLUMN";
    public static final String USER_ADDRESS_COLUMN = "USER_ADDRESS_COLUMN";
    public static final String RECEIVER_ADDRESS_COLUMN = "RECEIVER_ADDRESS_COLUMN";
    public static final String USER_TEL_COLUMN = "USER_TEL_COLUMN";
    public static final String RECEIVER_TEL_COLUMN = "RECEIVER_TEL_COLUMN";
    public static final String USER_COMPID_COLUMN = "USER_COMPID_COLUMN";
    public static final String RECEIVER_COMPID_COLUMN = "RECEIVER_COMPID_COLUMN";
    public static final String USER_EMAIL_COLUMN = "USER_EMAIL_COLUMN";
    public static final String RECEIVER_EMAIL_COLUMN = "RECEIVER_EMAIL_COLUMN";
    public static final String DESCRIPTION_COLUMN = "DESCRIPTION_COLUMN";
    public static final String QUANTITY_COLUMN = "QUANTITY_COLUMN";
    public static final String PRICE_COLUMN = "PRICE_COLUMN";
    public static final String UIC_COLUMN = "UIC_COLUMN";
    private static final String UNIQUE_INVOICE_CODE ="UNIQUE_INVOICE_CODE" ;
    private static final String UNIQUE_CONTACTS_CODE ="UNIQUE_CONTACTS_CODE" ;
    private static final String IMAGEURI ="IMAGEURI" ;
    private static final String USER_DETAILS_TABLE ="USER_DETAILS_TABLE" ;
    private static final String USER_SAVED_COMPANY = "USER_SAVED_COMPANY";
    private static final String USER_SAVED_COMPID = "USER_SAVED_COMPID";
    private static final String USER_SAVED_TEL ="USER_SAVED_TEL" ;
    private static final String USER_SAVED_POSTCODE ="USER_SAVED_POSTCODE" ;
    private static final String USER_SAVED_EMAIL ="USER_SAVED_EMAIL" ;
    private static final String USER_SAVED_IMAGEURI ="USER_SAVED_IMAGEURI";
    private static final String SAVED_CONTACTS_CODE = "SAVED_CONTACTS_CODE";

    public MyDatabaseManager(@Nullable Context context) {
        super(context, "invoices.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createInvoiceTable= "CREATE TABLE " + INVOICES_TABLE + "( "+
                UNIQUE_INVOICE_CODE + " PRIMARY KEY, " +
                INVOICE_NO_COLUMN + " INT, " +
                ISSUE_DATE_COLUMN + " TEXT, " +
                DUE_DATE_COLUMN + " TEXT, " +
                PAID_COLUMN + " BOOL, " +
                IMAGEURI + " TEXT, " +
                CONTACTS + " TEXT )";

        String createUserDetailsTable= "CREATE TABLE " + USER_DETAILS_TABLE + "( "+
                SAVED_CONTACTS_CODE + " PRIMARY KEY, " +
                USER_SAVED_COMPANY + " TEXT, " +
                USER_SAVED_COMPID + " INT, " +
                USER_SAVED_TEL + " TEXT, " +
                USER_SAVED_POSTCODE + " TEXT, " +
                USER_SAVED_EMAIL + " BOOL, " +
                USER_SAVED_IMAGEURI + " TEXT )";

        String createContactsTable= "CREATE TABLE " + CONTACTS_TABLE + "( " +
                UNIQUE_CONTACTS_CODE + " PRIMARY KEY, " +
                USER_NAME_COLUMN + " TEXT, " +
                RECEIVER_NAME_COLUMN + " TEXT, " +
                USER_POSTCODE_COLUMN + " TEXT, " +
                RECEIVER_POSTCODE_COLUMN + " TEXT, " +
                USER_ADDRESS_COLUMN + " TEXT, " +
                RECEIVER_ADDRESS_COLUMN + " TEXT, " +
                USER_TEL_COLUMN + " TEXT, " +
                RECEIVER_TEL_COLUMN + " TEXT, " +
                USER_COMPID_COLUMN + " TEXT, " +
                RECEIVER_COMPID_COLUMN + " TEXT, " +
                USER_EMAIL_COLUMN + " TEXT, " +
                RECEIVER_EMAIL_COLUMN + " TEXT )";

        String createItemsTable= "CREATE TABLE " + ITEMS_TABLE + "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DESCRIPTION_COLUMN + " TEXT, " +
                DATE_COLUMN + " TEXT, " +
                QUANTITY_COLUMN + " INT, " +
                PRICE_COLUMN + " REAL, " +
                UIC_COLUMN + " TEXT )";

        db.execSQL(createItemsTable);
        db.execSQL(createContactsTable);
        db.execSQL(createInvoiceTable);
        db.execSQL(createUserDetailsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public Contacts getUserSavedDetails(){
        Contacts userSavedContacts=new Contacts("userDetails");
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlStatement ="SELECT * FROM " + USER_DETAILS_TABLE;
        Cursor cursor = db.rawQuery(sqlStatement,null);
        if(cursor.moveToFirst()){
            userSavedContacts.userCompany=cursor.getString(cursor.getColumnIndex(USER_SAVED_COMPANY));
            userSavedContacts.userPostcode=cursor.getString(cursor.getColumnIndex(USER_SAVED_POSTCODE));
            userSavedContacts.userTel=cursor.getString(cursor.getColumnIndex(USER_SAVED_TEL));
            userSavedContacts.userCompanyID=cursor.getString(cursor.getColumnIndex(USER_SAVED_COMPID));
            userSavedContacts.userEmail=cursor.getString(cursor.getColumnIndex(USER_SAVED_EMAIL));
            userSavedContacts.userLogo=cursor.getString(cursor.getColumnIndex(USER_SAVED_IMAGEURI));
        }else{
            cursor.close();
            db.close();
            return null;
        }
        cursor.close();
        db.close();
        return userSavedContacts;
    }
    public boolean saveUserDetails(Contacts c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentContacts = new ContentValues();
        //____________SAVING CONTACTS________________________________________
        contentContacts.put(SAVED_CONTACTS_CODE,"userDetails");
        contentContacts.put(USER_SAVED_COMPANY, c.userCompany);
        contentContacts.put(USER_SAVED_POSTCODE, c.userPostcode);
        contentContacts.put(USER_SAVED_TEL, c.userTel);
        contentContacts.put(USER_SAVED_COMPID, c.userCompanyID);
        contentContacts.put(USER_SAVED_EMAIL, c.userEmail);

        db.insert(USER_DETAILS_TABLE, null, contentContacts);
        db.close();
        return true;
    }
    public Boolean updateUserDetails(Contacts c) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentContacts = new ContentValues();
        //Updating CONTACTS________________________________________
        contentContacts.put(SAVED_CONTACTS_CODE,"userDetails");
        contentContacts.put(USER_SAVED_COMPANY, c.userCompany);
        contentContacts.put(USER_SAVED_POSTCODE, c.userPostcode);
        contentContacts.put(USER_SAVED_TEL, c.userTel);
        contentContacts.put(USER_SAVED_COMPID, c.userCompanyID);
        contentContacts.put(USER_SAVED_EMAIL, c.userEmail);
        Boolean result = (db.update(USER_DETAILS_TABLE, contentContacts, SAVED_CONTACTS_CODE+"='"+"userDetails"+"';", null))>0;
        db.close();
        return true;
    }
    public Boolean updateUserLogo(String uri) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentContacts = new ContentValues();
        contentContacts.put(USER_SAVED_IMAGEURI,uri);
        Boolean result = (db.update(USER_DETAILS_TABLE, contentContacts, SAVED_CONTACTS_CODE+"='"+"userDetails"+"';", null))>0;
        db.close();
        return result;
    }

    public List<InvoiceData> getAllInvoices(){
        List<InvoiceData> invoices = new ArrayList<>();
        String selectInvoicesSQLstatement= "SELECT * FROM " + INVOICES_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectInvoicesSQLstatement,null);
        if(cursor.moveToFirst()){
            //loop through the invoice records
            do{
                InvoiceData invData = new InvoiceData(cursor.getString(cursor.getColumnIndex(UNIQUE_INVOICE_CODE)));
                invData.invoiceNo=cursor.getString(cursor.getColumnIndex(INVOICE_NO_COLUMN));
                invData.invoiceDate=cursor.getString(cursor.getColumnIndex(ISSUE_DATE_COLUMN));
                invData.dueDate=cursor.getString(cursor.getColumnIndex(DUE_DATE_COLUMN));
                invData.invoicePaid=cursor.getInt(cursor.getColumnIndex(PAID_COLUMN))>0;
                invData.contacts=getContacts(cursor.getString(cursor.getColumnIndex(CONTACTS)));
                invData.tbItems=getTableItems(cursor.getString(cursor.getColumnIndex(UNIQUE_INVOICE_CODE)));
                if(cursor.getString(cursor.getColumnIndex(IMAGEURI))!="") {
                    invData.logoImage = URI.create(cursor.getString(cursor.getColumnIndex(IMAGEURI)));
                }
                invoices.add(invData);
            }while(cursor.moveToNext());

        }else{
            cursor.close();
            db.close();
            return invoices;
        }
        cursor.close();
        db.close();
        return invoices;

    }
    public Contacts getContacts(String contactID){
        Contacts contacts=new Contacts(contactID);
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlStatement ="SELECT * FROM " + CONTACTS_TABLE + " WHERE " + UNIQUE_CONTACTS_CODE + "='"+contactID+"';";
        Cursor cursor = db.rawQuery(sqlStatement,null);
        if(cursor.moveToFirst()){
            contacts.userCompany=cursor.getString(cursor.getColumnIndex(USER_NAME_COLUMN));
            contacts.receiverCompany=cursor.getString(cursor.getColumnIndex(RECEIVER_NAME_COLUMN));
            contacts.userPostcode=cursor.getString(cursor.getColumnIndex(USER_POSTCODE_COLUMN));
            contacts.receiverPostcode=cursor.getString(cursor.getColumnIndex(RECEIVER_POSTCODE_COLUMN));
            contacts.userAddress=cursor.getString(cursor.getColumnIndex(USER_ADDRESS_COLUMN));
            contacts.receiverAddress=cursor.getString(cursor.getColumnIndex(RECEIVER_ADDRESS_COLUMN));
            contacts.userTel=cursor.getString(cursor.getColumnIndex(USER_TEL_COLUMN));
            contacts.receiverTel=cursor.getString(cursor.getColumnIndex(RECEIVER_TEL_COLUMN));
            contacts.userCompanyID=cursor.getString(cursor.getColumnIndex(USER_COMPID_COLUMN));
            contacts.receiverCompanyID=cursor.getString(cursor.getColumnIndex(RECEIVER_COMPID_COLUMN));
            contacts.userEmail=cursor.getString(cursor.getColumnIndex(USER_EMAIL_COLUMN));
            contacts.receiverEmail=cursor.getString(cursor.getColumnIndex(RECEIVER_EMAIL_COLUMN));
        }else{
            cursor.close();
            db.close();
            return null;
        }
        cursor.close();
        db.close();
        return contacts;
    }

    public List<TableItem> getTableItems(String invoiceID){
        List<TableItem> items= new ArrayList<>();
        String sqlStatement="SELECT * FROM " + ITEMS_TABLE + " WHERE " + UIC_COLUMN + "='"+invoiceID+"';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlStatement,null);

        if(cursor.moveToFirst()){
            //loop through the item table records
            do{
                TableItem tbitem = new TableItem();
                tbitem.description=cursor.getString(cursor.getColumnIndex(DESCRIPTION_COLUMN));
                tbitem.date=cursor.getString(cursor.getColumnIndex(DATE_COLUMN));
                tbitem.price=cursor.getDouble(cursor.getColumnIndex(PRICE_COLUMN));
                tbitem.quantity=cursor.getInt(cursor.getColumnIndex(QUANTITY_COLUMN));
                tbitem.setInvoiceID(cursor.getString(cursor.getColumnIndex(UIC_COLUMN)));
                items.add(tbitem);
            }while(cursor.moveToNext());

        }else{
            return items;
        }

        return items;
    }

    public boolean deleteItemFromDB(InvoiceData invData){
        SQLiteDatabase db = getWritableDatabase();
        String deleteQuery = "DELETE FROM " + INVOICES_TABLE + " WHERE " + UNIQUE_INVOICE_CODE+"='"+invData.getID()+"';";
        Cursor cursor = db.rawQuery(deleteQuery, null);
        if(cursor.moveToFirst()){
            db.close();
            return false;
        }else{
            deleteTableItemsFromDB(invData);
            db.close();
            return true;
        }
    }
    public boolean deleteTableItemsFromDB(InvoiceData invData){
        SQLiteDatabase db = getWritableDatabase();
        String deleteQuery = "DELETE FROM " + ITEMS_TABLE + " WHERE " + UIC_COLUMN+"='"+invData.getID()+"';";
        Boolean deleteSuccess = (db.delete(ITEMS_TABLE,UIC_COLUMN+"='"+invData.getID()+"';",null))>0;
        Cursor cursor = db.rawQuery(deleteQuery, null);
        if(cursor.moveToFirst()){
            db.close();
            return false;
        }else{
            db.close();
            return true;
        }
    }






    public boolean saveInvoiceToDB(InvoiceData invData){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentContacts= new ContentValues();
        ContentValues contentTableItems= new ContentValues();
        ContentValues contentInvoice= new ContentValues();
        //____________SAVING CONTACTS________________________________________
        contentContacts.put(UNIQUE_CONTACTS_CODE,invData.contacts.getContactsID());
        contentContacts.put(USER_NAME_COLUMN,invData.contacts.userCompany);
        contentContacts.put(RECEIVER_NAME_COLUMN,invData.contacts.receiverCompany);
        contentContacts.put(USER_POSTCODE_COLUMN,invData.contacts.userPostcode);
        contentContacts.put(RECEIVER_POSTCODE_COLUMN,invData.contacts.receiverPostcode);
        contentContacts.put(USER_ADDRESS_COLUMN,invData.contacts.userAddress);
        contentContacts.put(RECEIVER_ADDRESS_COLUMN,invData.contacts.receiverAddress);
        contentContacts.put(USER_TEL_COLUMN,invData.contacts.userTel);
        contentContacts.put(RECEIVER_TEL_COLUMN,invData.contacts.receiverTel);
        contentContacts.put(USER_COMPID_COLUMN,invData.contacts.userCompanyID);
        contentContacts.put(RECEIVER_COMPID_COLUMN,invData.contacts.receiverCompanyID);
        contentContacts.put(USER_EMAIL_COLUMN,invData.contacts.userEmail);
        contentContacts.put(RECEIVER_EMAIL_COLUMN,invData.contacts.receiverEmail);
        //____________SAVING INVOICE DATA________________________________________
        contentInvoice.put(INVOICE_NO_COLUMN,invData.invoiceNo);
        contentInvoice.put(ISSUE_DATE_COLUMN,invData.invoiceDate);
        contentInvoice.put(DUE_DATE_COLUMN,invData.dueDate);
        contentInvoice.put(CONTACTS,invData.contacts.getContactsID());
        contentInvoice.put(PAID_COLUMN,invData.invoicePaid);
        contentInvoice.put(UNIQUE_INVOICE_CODE,invData.getID());
        if(invData.logoImage!=null) {
            contentInvoice.put(IMAGEURI, String.valueOf(invData.logoImage));
        }else {
            contentInvoice.put(IMAGEURI, "");
        }
        //____________SAVING TABLE ITEMS DATA________________________________________
        for (TableItem item:invData.tbItems) {
            contentTableItems.put(DESCRIPTION_COLUMN,item.description);
            contentTableItems.put(DATE_COLUMN,item.date);
            contentTableItems.put(QUANTITY_COLUMN,item.quantity);
            contentTableItems.put(PRICE_COLUMN,item.price);
            contentTableItems.put(UIC_COLUMN,invData.getID());
            db.insert(ITEMS_TABLE, null, contentTableItems);
        }
        db.insert(INVOICES_TABLE, null, contentInvoice);
        db.insert(CONTACTS_TABLE, null, contentContacts);
        db.close();
        return true;
    }

    public Boolean updateInvoice(InvoiceData invData) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentContacts= new ContentValues();
        contentContacts.put(USER_NAME_COLUMN,invData.contacts.userCompany);
        contentContacts.put(RECEIVER_NAME_COLUMN,invData.contacts.receiverCompany);
        contentContacts.put(USER_POSTCODE_COLUMN,invData.contacts.userPostcode);
        contentContacts.put(RECEIVER_POSTCODE_COLUMN,invData.contacts.receiverPostcode);
        contentContacts.put(USER_ADDRESS_COLUMN,invData.contacts.userAddress);
        contentContacts.put(RECEIVER_ADDRESS_COLUMN,invData.contacts.receiverAddress);
        contentContacts.put(USER_TEL_COLUMN,invData.contacts.userTel);
        contentContacts.put(RECEIVER_TEL_COLUMN,invData.contacts.receiverTel);
        contentContacts.put(USER_COMPID_COLUMN,invData.contacts.userCompanyID);
        contentContacts.put(RECEIVER_COMPID_COLUMN,invData.contacts.receiverCompanyID);
        contentContacts.put(USER_EMAIL_COLUMN,invData.contacts.userEmail);
        contentContacts.put(RECEIVER_EMAIL_COLUMN,invData.contacts.receiverEmail);
        Boolean deleteSuccess = (db.delete(ITEMS_TABLE,UIC_COLUMN+"='"+invData.getID()+"';",null))>0;
            if(invData.tbItems.isEmpty()) {
                Log.e("oof", "updateInvoice: tbitems is empty huh ;o ");
            }else{
                for (TableItem item : invData.tbItems) {
                ContentValues contentTableItems = new ContentValues();
                contentTableItems.put(DESCRIPTION_COLUMN, item.description);
                contentTableItems.put(DATE_COLUMN, item.date);
                contentTableItems.put(QUANTITY_COLUMN, item.quantity);
                contentTableItems.put(PRICE_COLUMN, item.price);
                contentTableItems.put(UIC_COLUMN, invData.getID());
                db.insert(ITEMS_TABLE, null, contentTableItems);
            }
        }
            ContentValues newURI= new ContentValues();
            newURI.put(IMAGEURI, String.valueOf(invData.logoImage));
        Boolean imageUpdate = (db.update(INVOICES_TABLE, newURI, UNIQUE_INVOICE_CODE+"='"+invData.getID()+"';", null))>0;
        Boolean result = (db.update(CONTACTS_TABLE, contentContacts, UNIQUE_CONTACTS_CODE+"='"+invData.contacts.getContactsID()+"';", null))>0;

        return result;
    }

    public Boolean changeToPaid(String ID){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentContacts= new ContentValues();
        contentContacts.put(PAID_COLUMN,Boolean.TRUE);
        Boolean result = (db.update(INVOICES_TABLE, contentContacts, UNIQUE_INVOICE_CODE+"='"+ID+"';", null))>0;
        return result;
    }

    public boolean hasInvoices() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + INVOICES_TABLE, null);

        if (mCursor.getCount()==0)
        {
            // I AM EMPTY
            db.close();
            return false;

        } else
        {
            // proceed
            db.close();
            return true;
        }
    }
    public boolean hasSavedDetails() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + USER_DETAILS_TABLE, null);

        if (mCursor.getCount()==0)
        {
            // I AM EMPTY
            db.close();
            return false;

        } else
        {
            // proceed
            db.close();
            return true;
        }
    }
}

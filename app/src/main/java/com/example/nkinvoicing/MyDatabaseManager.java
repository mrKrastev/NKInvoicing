package com.example.nkinvoicing;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

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
                CONTACTS + " TEXT )";

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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

        return true;
    }
}

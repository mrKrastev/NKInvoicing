package com.example.nkinvoicing;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.Date;

import static java.lang.Integer.parseInt;
import static java.lang.Math.round;

public class StandardInvoice extends AppCompatActivity {

    Intent contactsEditIntent;
    Contacts contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.standard_invoice);
        contacts = (Contacts) getIntent().getSerializableExtra("Contacts");
        if(contacts==null){
                contacts = new Contacts(getString(R.string.user_company_name),
                getString(R.string.user_address),
                getString(R.string.user_postcode),
                getString(R.string.user_tel),
                getString(R.string.user_company_id),
                getString(R.string.user_email),
                getString(R.string.receiver_company_name),
                getString(R.string.receiver_address),
                getString(R.string.receiver_postcode),
                getString(R.string.receiver_tel),
                getString(R.string.receiver_company_id),
                getString(R.string.receiver_email));
        }
        createContacts(contacts);
        createTable();
        contactsEditIntent = new Intent(this,EditContacts.class);
        contactsEditIntent.putExtra("Contacts",contacts);
    }
    public void createContacts(Contacts c){
        TextView userCompany = findViewById(R.id.userCompanyNamelbl);
        TextView userAddress = findViewById(R.id.userAddresslbl);
        TextView userPostcode = findViewById(R.id.userPostcodelbl);
        TextView userTel = findViewById(R.id.userTelLbl);
        TextView userCompanyID = findViewById(R.id.userCompanyID);
        TextView userEmail = findViewById(R.id.userEmailLbl);
        //--------------------------------------------------------------------------
        TextView receiverCompany = findViewById(R.id.receiverName);
        TextView receiverAddress = findViewById(R.id.receiverAddress);
        TextView receiverPostcode = findViewById(R.id.receiverPostcode);
        TextView receiverTel = findViewById(R.id.receiverTelLbl);
        TextView receiverCompanyID = findViewById(R.id.receiverCompanyID);
        TextView receiverEmail = findViewById(R.id.receiverEmailLbl);
        //~~~~~~~~~~~~~~~~~~~~~~~~Setting Text from Contacts Object ~~~~~~~~~~~~~~~~~~~~~~
        userCompany.setText(c.userCompany);
        userAddress.setText(c.userAddress);
        userPostcode.setText(c.userPostcode);
        userTel.setText(c.userTel);
        userCompanyID.setText(c.userCompanyID);
        userEmail.setText(c.userEmail);
        //----------------------------------------------------
        receiverCompany.setText(c.receiverCompany);
        receiverAddress.setText(c.receiverAddress);
        receiverPostcode.setText(c.receiverPostcode);
        receiverTel.setText(c.receiverTel);
        receiverCompanyID.setText(c.receiverCompanyID);
        receiverEmail.setText(c.receiverEmail);
    }
    public void createTable() {
        DecimalFormat f = new DecimalFormat("##.00");
        Double GrossValue=0.00;
        TableLayout stk = (TableLayout) findViewById(R.id.standard_invoice_table);
        TableRow tableHeaderFields = new TableRow(this);
        tableHeaderFields.setMinimumHeight(100);
        TextView tv0 = new TextView(this);
        tv0.setText(" Description ");
        tv0.setTextColor(0xFD206E1A);
        tv0.setWidth(350);
        tableHeaderFields.addView(tv0);
        TextView tv5 = new TextView(this);
        tv5.setText(" Date ");
        tv5.setTextColor(0xFD206E1A);
        tv5.setWidth(30);
        tableHeaderFields.addView(tv5);
        TextView tv1 = new TextView(this);
        tv1.setText(" Qty(hours) ");
        tv1.setTextColor(0xFD206E1A);
        tv1.setWidth(230);
        tv1.setGravity(Gravity.CENTER);
        tableHeaderFields.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Rate(£) ");
        tv2.setWidth(150);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(0xFD206E1A);
        tableHeaderFields.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" Amount (£) ");
        tv3.setWidth(230);
        tv3.setTextColor(0xFD206E1A);
        tv3.setGravity(Gravity.RIGHT);
        tableHeaderFields.addView(tv3);
        stk.addView(tableHeaderFields);

        //~~~~~~~~~~ Populate table ~~~~~~~~~~~~~~~~~
        for (int i = 0; i < 5; i++) {
            TableRow tbrow = new TableRow(this);
            tbrow.setMinimumHeight(150);
            TextView descriptionField = new TextView(this);
            String descriptionValue = "Web Developer Services";
            descriptionField.setText(descriptionValue);
            descriptionField.setTextColor(Color.BLACK);
            descriptionField.setGravity(Gravity.LEFT);
            descriptionField.setMaxWidth(350);
            tbrow.addView(descriptionField);
            TextView dateField = new TextView(this);
            CharSequence dateValue  = DateFormat.format("dd/MM/yy", new Date());
            dateField.setText(dateValue);
            dateField.setTextColor(Color.BLACK);
            dateField.setGravity(Gravity.LEFT);
            dateField.setMaxWidth(30);
            tbrow.addView(dateField);
            TextView qtyField = new TextView(this);
            int qtyValue=40;
            qtyField.setText(""+qtyValue);
            qtyField.setTextColor(Color.BLACK);
            qtyField.setMaxWidth(230);
            qtyField.setGravity(Gravity.CENTER);
            tbrow.addView(qtyField);
            TextView priceField = new TextView(this);
            Double priceValue = 15.69;
            priceField.setText("£"+priceValue);
            priceField.setTextColor(Color.BLACK);
            priceField.setGravity(Gravity.CENTER);
            priceField.setMaxWidth(150);
            tbrow.addView(priceField);
            TextView totalField = new TextView(this);
            Double totalFieldValue =priceValue * qtyValue;
            totalField.setText("£" + f.format(totalFieldValue));
            totalField.setMaxWidth(230);
            totalField.setTextColor(Color.BLACK);
            totalField.setGravity(Gravity.RIGHT);
            tbrow.addView(totalField);
            GrossValue=GrossValue + totalFieldValue;
            stk.addView(tbrow);

        }
        //summary rows:

        //~~~~~~Total Before VAT row ~~~~~~~~
        TableRow SUMrow = new TableRow(this);
        SUMrow.setGravity(Gravity.RIGHT);
        SUMrow.setMinimumHeight(100);
        TextView SUMlabel = new TextView(this);
        SUMlabel.setText(" GROSS: ");
        SUMlabel.setTextColor(Color.WHITE);
        SUMlabel.setBackgroundColor(0xFD206E1A);
        SUMlabel.setGravity(Gravity.LEFT);
        SUMrow.addView(SUMlabel);
        TextView SUMvaluelbl = new TextView(this);
        SUMvaluelbl.setText(f.format(GrossValue));
        SUMvaluelbl.setTextColor(0xFD206E1A);
        SUMvaluelbl.setMinimumWidth(250);
        SUMvaluelbl.setGravity(Gravity.RIGHT);
        SUMrow.addView(SUMvaluelbl);
        stk.addView(SUMrow);

        //~~~~~~ VAT/TAX ~~~~~~~~
        TableRow VATrow = new TableRow(this);
        VATrow.setGravity(Gravity.RIGHT);
        VATrow.setMinimumHeight(100);
        TextView VATlabel = new TextView(this);
        VATlabel.setText(" TAX: (20%)");
        VATlabel.setTextColor(Color.WHITE);
        VATlabel.setBackgroundColor(0xFD206E1A);
        VATlabel.setGravity(Gravity.LEFT);
        VATrow.addView(VATlabel);
        TextView VATvalue = new TextView(this);
        VATvalue.setText("£"+f.format(GrossValue*0.2));
        VATvalue.setTextColor(0xFD206E1A);
        VATvalue.setMinimumWidth(250);
        VATvalue.setGravity(Gravity.RIGHT);
        VATrow.addView(VATvalue);
        stk.addView(VATrow);

        // ~~~~~ Total with VAT ~~~~~~~~~~~~~
        TableRow NETrow = new TableRow(this);
        NETrow.setGravity(Gravity.RIGHT);
        NETrow.setMinimumHeight(100);
        TextView NETlabel = new TextView(this);
        NETlabel.setText("TOTAL: ");
        NETlabel.setTextColor(Color.WHITE);
        NETlabel.setBackgroundColor(0xFD206E1A);
        NETlabel.setGravity(Gravity.LEFT);
        NETrow.addView(NETlabel);
        TextView NETvalue = new TextView(this);
        NETvalue.setText("£"+f.format((GrossValue*0.8)));
        NETvalue.setTextColor(0xFD206E1A);
        NETvalue.setMinimumWidth(250);
        NETvalue.setGravity(Gravity.RIGHT);
        NETrow.addView(NETvalue);
        stk.addView(NETrow);

    }

    public void editContacts(View view){

        startActivity(contactsEditIntent);
    }
}

package com.example.nkinvoicing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EditContacts extends AppCompatActivity {
    Contacts contacts;
    InvoiceData invData;
    Intent backToInvoice;
    // input fields
   private EditText userCompany;
   private EditText userAddress;
   private EditText userPostcode;
   private EditText userTel;
   private EditText userCompanyID;
   private EditText userEmail;
   private EditText receiverCompany;
   private EditText receiverAddress;
   private EditText receiverPostcode;
   private  EditText receiverTel;
   private EditText receiverCompanyID;
   private EditText receiverEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contacts);
        //Getting the invoice object
        invData =(InvoiceData) getIntent().getSerializableExtra("InvoiceData");
        //setting the contacts to be edited
        contacts = invData.contacts;
        //generating intent
        backToInvoice = new Intent(this,StandardInvoice.class);
        //assigning variables to the input fields
        //retrieving the user input fields
         userCompany = findViewById(R.id.userCompanyInput);
         userAddress = findViewById(R.id.userAddressInput);
         userPostcode = findViewById(R.id.userPostcodeInput);
         userTel = findViewById(R.id.userTelNoInput);
         userCompanyID = findViewById(R.id.userCompanyIDInput);
         userEmail = findViewById(R.id.userEmailInput);
        //retrieving the receiver input fields
         receiverCompany = findViewById(R.id.receiverCompanyInput);
         receiverAddress = findViewById(R.id.receiverAddressInput);
         receiverPostcode = findViewById(R.id.receiverPostcodeInput);
         receiverTel = findViewById(R.id.receiverTelNoInput);
         receiverCompanyID = findViewById(R.id.receiverCompIDInput);
         receiverEmail = findViewById(R.id.receiverEmailnput);
        //populating the input fields with the existing contact details so that the users dont have to type everything again
        loadContacts(contacts);
    }
    public void loadContacts(Contacts c){

        //~~~~~~~~~~~~~~~~~~~~~~~~Setting Text from Contacts Object ~~~~~~~~~~~~~~~~~~~~~~
        //user
        userCompany.setText(c.userCompany);
        userAddress.setText(c.userAddress);
        userPostcode.setText(c.userPostcode);
        userTel.setText(c.userTel);
        userCompanyID.setText(c.userCompanyID);
        userEmail.setText(c.userEmail);
        //receiver
        receiverCompany.setText(c.receiverCompany);
        receiverAddress.setText(c.receiverAddress);
        receiverPostcode.setText(c.receiverPostcode);
        receiverTel.setText(c.receiverTel);
        receiverCompanyID.setText(c.receiverCompanyID);
        receiverEmail.setText(c.receiverEmail);
    }
    public void cancelEditContacts(View view){
        //you basically return without updating anything (i might need an id later in here)
        backToInvoice.putExtra("InvoiceData",invData);
        startActivity(backToInvoice);
    }
    public void updateContacts(View view){
        
        //update the string resources:
        contacts.userCompany = userCompany.getText().toString();
        contacts.userAddress = userAddress.getText().toString();
        contacts.userPostcode = userPostcode.getText().toString();
        contacts.userTel = userTel.getText().toString();
        contacts.userCompanyID = userCompanyID.getText().toString();
        contacts.userEmail = userEmail.getText().toString();
        //-------------------------------------------------------
        contacts.receiverCompany = receiverCompany.getText().toString();
        contacts.receiverAddress = receiverAddress.getText().toString();
        contacts.receiverPostcode = receiverPostcode.getText().toString();
        contacts.receiverTel = receiverTel.getText().toString();
        contacts.receiverCompanyID = receiverCompanyID.getText().toString();
        contacts.receiverEmail = receiverEmail.getText().toString();

        invData.setContacts(contacts);

       backToInvoice.putExtra("InvoiceData",invData);
        startActivity(backToInvoice);
    }
}

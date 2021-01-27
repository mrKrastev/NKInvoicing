package com.example.nkinvoicing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EditContacts extends AppCompatActivity {
    Contacts contacts;
    Intent backToInvoice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contacts);
        contacts = (Contacts) getIntent().getSerializableExtra("Contacts");
        backToInvoice = new Intent(this,StandardInvoice.class);
        loadContacts(contacts);
    }
    public void loadContacts(Contacts c){
        EditText userCompany = findViewById(R.id.userCompanyInput);
        EditText userAddress = findViewById(R.id.userAddressInput);
        EditText userPostcode = findViewById(R.id.userPostcodeInput);
        EditText userTel = findViewById(R.id.userTelNoInput);
        EditText userCompanyID = findViewById(R.id.userCompanyIDInput);
        EditText userEmail = findViewById(R.id.userEmailInput);
        //--------------------------------------------------------------------------
        EditText receiverCompany = findViewById(R.id.receiverCompanyInput);
        EditText receiverAddress = findViewById(R.id.receiverAddressInput);
        EditText receiverPostcode = findViewById(R.id.receiverPostcodeInput);
        EditText receiverTel = findViewById(R.id.receiverTelNoInput);
        EditText receiverCompanyID = findViewById(R.id.receiverCompanyInput);
        EditText receiverEmail = findViewById(R.id.receiverEmailnput);
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
    public void cancelEditContacts(View view){
        //you basically return without updating anything (i might need an id later in here)
        backToInvoice.putExtra("Contacts",contacts);
        startActivity(backToInvoice);
    }
    public void updateContacts(View view){
        //get the views:
        EditText userCompany = findViewById(R.id.userCompanyInput);
        EditText userAddress = findViewById(R.id.userAddressInput);
        EditText userPostcode = findViewById(R.id.userPostcodeInput);
        EditText userTel = findViewById(R.id.userTelNoInput);
        EditText userCompanyID = findViewById(R.id.userCompanyIDInput);
        EditText userEmail = findViewById(R.id.userEmailInput);
        //--------------------------------------------------------------------------
        EditText receiverCompany = findViewById(R.id.receiverCompanyInput);
        EditText receiverAddress = findViewById(R.id.receiverAddressInput);
        EditText receiverPostcode = findViewById(R.id.receiverPostcodeInput);
        EditText receiverTel = findViewById(R.id.receiverTelNoInput);
        EditText receiverCompanyID = findViewById(R.id.receiverCompIDInput);
        EditText receiverEmail = findViewById(R.id.receiverEmailnput);
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



       backToInvoice.putExtra("Contacts",contacts);
        startActivity(backToInvoice);
    }
}

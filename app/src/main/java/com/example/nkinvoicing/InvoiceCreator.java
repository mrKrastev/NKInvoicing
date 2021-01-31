package com.example.nkinvoicing;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.List;

public class InvoiceCreator extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    //Widget booleans
    Boolean issuePickerOn=false;
    Boolean duePickerOn=false;
    //UI elements
        //Invoice details
    EditText issueDate;
    EditText dueDate;
    EditText invoiceNo;
        //Contacts
        EditText userCompany;
    EditText userAddress;
    EditText userPostcode;
    EditText userTel;
    EditText userCompanyID;
    EditText userEmail;
    //--------------------------------------------------------------------------
    EditText receiverCompany;
    EditText receiverAddress;
    EditText receiverPostcode;
    EditText receiverTel;
    EditText receiverCompanyID;
    EditText receiverEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_creation_ui);

        issueDate = findViewById(R.id.issueDateInput);
        dueDate = findViewById(R.id.dueDateInput);
        invoiceNo = findViewById(R.id.invoiceNoInput);
        //Contacts
        userCompany = findViewById(R.id.userCompanyInput2);
        userAddress = findViewById(R.id.userAddressInput2);
        userPostcode = findViewById(R.id.userPostcodeInput2);
        userTel = findViewById(R.id.userTelNoInput2);
        userCompanyID = findViewById(R.id.userCompanyIDInput2);
        userEmail = findViewById(R.id.userEmailInput2);
        //--------------------------------------------------------------------------
        receiverCompany = findViewById(R.id.recCompanyInput2);
        receiverAddress = findViewById(R.id.recAddressInput2);
        receiverPostcode = findViewById(R.id.recPostcodeInput2);
        receiverTel = findViewById(R.id.recTelNoInput2);
        receiverCompanyID = findViewById(R.id.recCompanyIDInput2);
        receiverEmail = findViewById(R.id.recEmailInput2);
    }

    //~~~~~~~~~~~~~ Date Picker Widget methods~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void pickIssueDate(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
        issuePickerOn=true;
    }
    public void pickDueDate(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
        duePickerOn=true;
    }
    public void processIssueDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (day_string +
                "/" + month_string + "/" + year_string);
        if(issuePickerOn) {
            issueDate.setText(dateMessage);
            issuePickerOn=false;
        }
        if(duePickerOn){
            dueDate.setText(dateMessage);
            duePickerOn=false;
        }
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        processIssueDatePickerResult(year,month,dayOfMonth);
    }
    //~~~~~~~~~~~~~~~~~~~ Buttons methods ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void nextInvoiceStage(View view){
        List<TableItem> tableItems = null;
        //assign Contact Details
        Contacts contacts = new Contacts(String.valueOf(userCompany.getText()),
                String.valueOf(userAddress.getText()),
                String.valueOf(userPostcode.getText()),
                String.valueOf(userTel.getText()),
                String.valueOf(userCompanyID.getText()),
                String.valueOf(userEmail.getText()),
                String.valueOf(receiverCompany.getText()),
                String.valueOf(receiverAddress.getText()),
                String.valueOf(receiverPostcode.getText()),
                String.valueOf(receiverTel.getText()),
                String.valueOf(receiverCompanyID.getText()),
                String.valueOf(receiverEmail.getText()));

        //Create invoice Object
        InvoiceData invObj = new InvoiceData(contacts,
                tableItems,
                String.valueOf(invoiceNo.getText()),
                String.valueOf(issueDate.getText()),
                String.valueOf(dueDate.getText()));
        //send object accross
        Intent passInvoiceData = new Intent(this,StandardTableCreator.class);
        passInvoiceData.putExtra("InvoiceData",invObj);
        startActivity(passInvoiceData);

    }

    public void returnToPick (View view){

        Intent it = new Intent(this,PickInvoice.class);
        startActivity(it);
    }
}

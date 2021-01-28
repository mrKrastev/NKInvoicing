package com.example.nkinvoicing;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class InvoiceCreator extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Boolean issuePickerOn=false;
    Boolean duePickerOn=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_creation_ui);
    }
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
            EditText issueDate = findViewById(R.id.issueDateInput);
            issueDate.setText(dateMessage);
            issuePickerOn=false;
        }
        if(duePickerOn){
            EditText dueDate = findViewById(R.id.dueDateInput);
            dueDate.setText(dateMessage);
            duePickerOn=false;
        }
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        processIssueDatePickerResult(year,month,dayOfMonth);
    }
}

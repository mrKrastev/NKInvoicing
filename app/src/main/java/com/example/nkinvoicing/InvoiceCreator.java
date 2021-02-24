package com.example.nkinvoicing;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InvoiceCreator extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    //Widget booleans
    private Boolean issuePickerOn=false;
    private Boolean duePickerOn=false;
    //UI elements
    //Invoice details--------------------------------------------
    private EditText issueDate;
    private EditText dueDate;
    private EditText invoiceNo;
    //user details-----------------------------------------------
    private EditText userCompany;
    private Spinner userAddress;
    private EditText userPostcode;
    private EditText userTel;
    private EditText userCompanyID;
    private EditText userEmail;
    private Button userFindAddressBtn;
    //receiver details -----------------------------------------
    private EditText receiverCompany;
    private EditText receiverAddress;
    private EditText receiverPostcode;
    private EditText receiverTel;
    private EditText receiverCompanyID;
    private EditText receiverEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_creation_ui);
        //assigning invoice variables
        issueDate = findViewById(R.id.issueDateInput);
        dueDate = findViewById(R.id.dueDateInput);
        invoiceNo = findViewById(R.id.invoiceNoInput);
        //assigning contact variables
        //user
        userCompany = findViewById(R.id.userCompanyInput2);
        userAddress = findViewById(R.id.userAddressInput2);
        userPostcode = findViewById(R.id.userPostcodeInput2);
        userTel = findViewById(R.id.userTelNoInput2);
        userCompanyID = findViewById(R.id.userCompanyIDInput2);
        userEmail = findViewById(R.id.userEmailInput2);
        userFindAddressBtn=findViewById(R.id.findByPostcodeBtn);
        userFindAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postcode=String.valueOf(userPostcode.getText());
                getAddresses(postcode);

            }
        });

        //receiver
        receiverCompany = findViewById(R.id.recCompanyInput2);
        receiverAddress = findViewById(R.id.recAddressInput2);
        receiverPostcode = findViewById(R.id.recPostcodeInput2);
        receiverTel = findViewById(R.id.recTelNoInput2);
        receiverCompanyID = findViewById(R.id.recCompanyIDInput2);
        receiverEmail = findViewById(R.id.recEmailInput2);
    }


    private void getAddresses(String postcode) {
        final List<String> addresses=new ArrayList<>();
        //do api work here
        CharSequence apiKey="vrVCjPqswkKpQTttOKmCJA30353";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(InvoiceCreator.this);
        String url ="https://api.getAddress.io/find/"+postcode+"?api-key="+apiKey;
        // Request a jsonarray response from the provided URL.
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray responseArr =response.getJSONArray("addresses");
                    String myAddress;
                    String myNeatAddress;
                    for (int i=0;i<responseArr.length();i++){
                        myAddress = responseArr.getString(i);
                        myNeatAddress=myAddress.replace(" , , , , ","");
                        addresses.add(myNeatAddress);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(InvoiceCreator.this,R.layout.my_spinner_layout,addresses);
                    adapter.setNotifyOnChange(true); //only need to call this once
                    userAddress.setAdapter(adapter);
                    userAddress.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Toast.makeText(InvoiceCreator.this,"Rip: "+error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("IDFK", "onErrorResponse: "+error.toString(), null);


            }
        });

// Access the RequestQueue through your singleton class.
        queue.add(jsonArrayRequest);
    }

    //~~~~~~~~~~~~~ Date Picker Widget methods~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void pickIssueDate(View view) { //method for button which picks Issue Date
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
        issuePickerOn=true; //flags which input field should be updated
    }
    public void pickDueDate(View view) { // method for button which picks Due Date
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
        duePickerOn=true; //flags which input field should be updated
    }
    public void processIssueDatePickerResult(int year, int month, int day) {
        //formatting the date
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (day_string +
                "/" + month_string + "/" + year_string);
        //setting the input text from the result
        if(issuePickerOn) {
            issueDate.setText(dateMessage);
            issuePickerOn=false;
        }
        if(duePickerOn){
            dueDate.setText(dateMessage);
            duePickerOn=false;
        }
    }
    //implementing the DatePickerFragment
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        processIssueDatePickerResult(year,month,dayOfMonth);
    }
    //~~~~~~~~~~~~~~~~~~~ Buttons methods ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void nextInvoiceStage(View view){ // preparing the objects and changing views
        //assigning Contact Details
        Contacts contacts = new Contacts(String.valueOf(userCompany.getText()),
                String.valueOf(userAddress.getSelectedItem().toString()),
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
        //Creating invoice Object
        InvoiceData invObj = new InvoiceData(contacts,
                String.valueOf(invoiceNo.getText()),
                String.valueOf(issueDate.getText()),
                String.valueOf(dueDate.getText()));
        //sending object across
        Intent passInvoiceData = new Intent(this,StandardTableCreator.class);
        passInvoiceData.putExtra("InvoiceData",invObj);
        startActivity(passInvoiceData);

    }

    public void returnToPick (View view){
        //a return button just returning to the previous screen
        Intent it = new Intent(this,PickInvoice.class);
        startActivity(it);
    }
}

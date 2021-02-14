package com.example.nkinvoicing;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.List;

public class StandardInvoice extends AppCompatActivity {

    private InvoiceData invData;
    private TextView invoiceNo;
    private TextView issueDate;
    private TextView dueDate;
    ImageView logo;
    FloatingActionButton logoPickerButton;
    private static final int IMAGE_PICK_CODE=1000;
    private static final int PERMISSION_CODE=1001;
    MyDatabaseManager db;
    Boolean saved;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.standard_invoice_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.updateBtn){
            if(saved==false) {
                db.saveInvoiceToDB(invData);
                saved=true;
                Toast.makeText(this, "Invoice Saved!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "You already saved this invoice!", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        saved=false;
        db = new MyDatabaseManager(StandardInvoice.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.standard_invoice);
        //getting the invoice data object
        invData = (InvoiceData) getIntent().getSerializableExtra("InvoiceData");

        //setting the contacts object from the invoice data

            createContacts(invData.contacts);

        //generating the table from the invoice table items
        createTable(invData.tbItems);
        //setting the uneditable fields
        issueDate=findViewById(R.id.issueDatelbl);
        invoiceNo = findViewById(R.id.invoiceIDlbl);
        dueDate = findViewById(R.id.dueDateLbl);
        issueDate.setText(invData.invoiceDate);
        invoiceNo.setText(invData.invoiceNo);
        dueDate.setText(invData.dueDate);
        logo=findViewById(R.id.logoImg);

       if(invData.logoImage!=null){
           Log.e("uriback",invData.logoImage.toString() );
                logo.setImageURI(Uri.parse(invData.logoImage.toString()));
        }
        logoPickerButton=findViewById(R.id.chooseImgBtn);
        logoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                    //permission request needed:
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //popup
                        requestPermissions(permissions,PERMISSION_CODE);
                    }else{
                    //we got permission
                    pickImageFromGallery();
                    }
                }else{
                    //dont need permission lol
                    pickImageFromGallery();
                }
            }
        });

    }

    private void pickImageFromGallery() {
        Intent intent = new Intent (Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }
    private Uri getImageUri(String myURI)  {
        Uri uri = Uri.parse(myURI);
        return uri;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode==IMAGE_PICK_CODE){
            //set the image
            logo.setImageURI(data.getData());
            Uri imageUri = data.getData();
            try {
                URI juri = new URI(imageUri.toString());
                 invData.logoImage=juri;
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(this, "Permission Denied :C", Toast.LENGTH_SHORT).show();
                }
            }
        }
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
    public void createTable(List<TableItem> items) {
        DecimalFormat f = new DecimalFormat("##.00"); //setting 2dp for values in the table
        Double GrossValue=0.00; // setting default value for GROSS
        TableLayout table = (TableLayout) findViewById(R.id.standard_invoice_table);//getting the table view
        //Generating the headers of the table---------------------------------------------------------------
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
        table.addView(tableHeaderFields);

        //~~~~~~~~~~ Populate table ~~~~~~~~~~~~~~~~~
        for (TableItem item:items) { //looping through each item in the Invoice Data and making a row
            TableRow tbrow = new TableRow(this);
            tbrow.setMinimumHeight(150);
            //----------------Description field----------------------
            TextView descriptionField = new TextView(this);
            String descriptionValue = item.description;
            descriptionField.setText(descriptionValue);
            descriptionField.setTextColor(Color.BLACK);
            descriptionField.setGravity(Gravity.LEFT);
            descriptionField.setMaxWidth(350);
            tbrow.addView(descriptionField);
            //----------------Date Field------------------------------
            TextView dateField = new TextView(this);
            CharSequence dateValue  = item.date;
            dateField.setText(dateValue);
            dateField.setTextColor(Color.BLACK);
            dateField.setGravity(Gravity.LEFT);
            dateField.setMaxWidth(30);
            tbrow.addView(dateField);
            //---------------Quantity Field---------------------------
            TextView qtyField = new TextView(this);
            qtyField.setText(""+item.quantity);
            qtyField.setTextColor(Color.BLACK);
            qtyField.setMaxWidth(230);
            qtyField.setGravity(Gravity.CENTER);
            tbrow.addView(qtyField);
            //----------------Price----------------------------------
            TextView priceField = new TextView(this);
            priceField.setText("£"+item.price);
            priceField.setTextColor(Color.BLACK);
            priceField.setGravity(Gravity.CENTER);
            priceField.setMaxWidth(150);
            tbrow.addView(priceField);
            TextView totalField = new TextView(this);
            Double totalFieldValue =item.getAmount();
            totalField.setText("£" + f.format(totalFieldValue));
            totalField.setMaxWidth(230);
            totalField.setTextColor(Color.BLACK);
            totalField.setGravity(Gravity.RIGHT);
            tbrow.addView(totalField);
            GrossValue=GrossValue + totalFieldValue; //accumulating the gross value
            table.addView(tbrow);// adding the row to the table

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
        table.addView(SUMrow);

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
        table.addView(VATrow);

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
        table.addView(NETrow);

    }

    public void editContacts(View view){
        //passing the invoice object to edit the contacts
        Intent contactsEditIntent = new Intent(this,EditContacts.class);
        contactsEditIntent.putExtra("InvoiceData",invData);
        startActivity(contactsEditIntent);
    }
    public void editTable(View view){
        //returning the invoice object to the table creating view
        Intent tableEditIntent = new Intent(this,StandardTableCreator.class);
        tableEditIntent.putExtra("InvoiceData",invData);
        startActivity(tableEditIntent);
    }
}

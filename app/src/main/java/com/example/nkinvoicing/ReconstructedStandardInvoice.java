package com.example.nkinvoicing;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class ReconstructedStandardInvoice extends StandardInvoice {
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
    private ImageView paidStatusImage;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.standard_invoice_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.updateBtn){

            if(db.updateInvoice(invData)) {

                Toast.makeText(this, "Invoice Updated!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Update Failed ;c", Toast.LENGTH_SHORT).show();
            }
        }else if(item.getItemId()==R.id.backToMain){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else if(item.getItemId()==R.id.shareBtn){
            Bitmap myImg= getScrollScreenShot((ScrollView) findViewById(R.id.mainScrollView));
            Intent intent = new Intent(this, InvoiceImage.class);
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            myImg.compress(Bitmap.CompressFormat.PNG, 50, bs);
            intent.putExtra("IMAGE", bs.toByteArray());
            startActivity(intent);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        saved=false;
        db = new MyDatabaseManager(ReconstructedStandardInvoice.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_preview);
        //getting the invoice data object
        invData = (InvoiceData) getIntent().getSerializableExtra("InvoiceData");

        //setting the contacts object from the invoice data
        createContacts(invData.getContacts());
        //generating the table from the invoice table items
        createTable(invData.getTableItems());
        //setting the uneditable fields
        paidStatusImage =findViewById(R.id.imageView2);
        if(invData.invoicePaid) {
            paidStatusImage.setImageDrawable(getResources().getDrawable(R.drawable.paid_stamp_paid_grunge_stamp_sign_icon_editable_vector_illustration_isolated_white_background_123572302));
        }
        issueDate=findViewById(R.id.issueDatelbl2);
        invoiceNo = findViewById(R.id.invoiceIDlbl2);
        dueDate = findViewById(R.id.dueDateLbl2);
        issueDate.setText(invData.invoiceDate);
        invoiceNo.setText(invData.invoiceNo);
        dueDate.setText(invData.dueDate);
        logo=findViewById(R.id.logoImg2);

        if(invData.logoImage!=null){
            Log.e("uriback",invData.logoImage.toString() );
            logo.setImageURI(Uri.parse(invData.logoImage.toString()));
        }
        logoPickerButton=findViewById(R.id.chooseImgBtn2);
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
        super.createContacts(c);
    }
    public void createTable(List<TableItem> items) {
      super.createTable(items);

    }

    public void editContacts(View view){
        //passing the invoice object to edit the contacts
        Intent contactsEditIntent = new Intent(this,EditContacts.class);
        contactsEditIntent.putExtra("InvoiceData",invData);
        contactsEditIntent.putExtra("EditString", "EDIT");
        startActivity(contactsEditIntent);
    }
    public void editTable(View view){
        //returning the invoice object to the table creating view
        Intent tableEditIntent = new Intent(this,StandardTableCreator.class);
        tableEditIntent.putExtra("InvoiceData",invData);
        tableEditIntent.putExtra("String","EDITTABLE");
        startActivity(tableEditIntent);
    }


    //__________________________________EXPERIMENT_________________________________
    public Bitmap getScrollScreenShot(ScrollView view) {
        findViewById(R.id.chooseImgBtn2).setVisibility(View.INVISIBLE);
        findViewById(R.id.editContactBtn3).setVisibility(View.INVISIBLE);
        findViewById(R.id.editServicesBtn2).setVisibility(View.INVISIBLE);
        findViewById(R.id.issueDatelbl3).setVisibility(View.INVISIBLE);
        if (null != view) {
            int height = 0;
//Get the ScrollView correctly
            for (int i = 0; i < view.getChildCount(); i++) {
                height += view.getChildAt(i).getHeight();
            }
            if (height > 0) {
// Create a cache to save the cache
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), height, Bitmap.Config.RGB_565);

// Can easily understand Canvas as a drawing board and bitmap is a block canvas

                Canvas canvas = new Canvas(bitmap);

// Draw the contents of the view to the specified canvas Canvas
                view.draw(canvas);
                findViewById(R.id.chooseImgBtn2).setVisibility(View.VISIBLE);
                findViewById(R.id.editContactBtn3).setVisibility(View.VISIBLE);
                findViewById(R.id.editServicesBtn2).setVisibility(View.VISIBLE);
                findViewById(R.id.issueDatelbl3).setVisibility(View.VISIBLE);
                return bitmap;
            }

        }

        return null;

    }
}

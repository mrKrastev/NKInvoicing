package com.example.nkinvoicing;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;


public class InvoiceImage extends AppCompatActivity implements Serializable {
    ImageView img;
    Bitmap b;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_screen);
        img=findViewById(R.id.invoiceImageScreenshot);
        if(getIntent().hasExtra("IMAGE")) {
            b = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("IMAGE"),0,getIntent()
                            .getByteArrayExtra("IMAGE").length);
            img.setImageBitmap(b);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.save_image){
           saveImage();
            Toast.makeText(InvoiceImage.this,
                    "Image Saved!",
                    Toast.LENGTH_LONG).show();
           item.setVisible(false);
        }
        return super.onOptionsItemSelected(item);
    }


    public void saveImage(){
        //get bitmap from ImageVIew
        //not always valid, depends on your drawable
        ContentResolver cr = getContentResolver();
        String title = "myBitmap";
        String description = "My bitmap created by Android-er";
        String savedURL = MediaStore.Images.Media
                .insertImage(cr, b, title, description);

        Toast.makeText(InvoiceImage.this,
                savedURL,
                Toast.LENGTH_LONG).show();
        //always save as
        String fileName = "test.jpg";

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        File ExternalStorageDirectory = Environment.getExternalStorageDirectory();
        File file = new File(ExternalStorageDirectory + File.separator + fileName);

        FileOutputStream fileOutputStream = null;
        try {
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes.toByteArray());
            String imagePath = file.getAbsolutePath();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}

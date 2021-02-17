package com.example.nkinvoicing;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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
        //save as pdf

    }
}

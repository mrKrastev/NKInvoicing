package com.example.nkinvoicing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void PickInvoiceType (View view){
        /*Toast.makeText(this, "haha", Toast.LENGTH_SHORT).show();*/
        Intent it = new Intent(this,PickInvoice.class);
        startActivity(it);
    }
}
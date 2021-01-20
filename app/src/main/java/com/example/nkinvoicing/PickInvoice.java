package com.example.nkinvoicing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class PickInvoice extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_invoice_type);
    }
public void selectInvoice (View view){
        Intent it = new Intent(this,StandardInvoice.class);
        startActivity(it);
}
}

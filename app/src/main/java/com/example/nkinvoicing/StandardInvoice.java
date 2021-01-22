package com.example.nkinvoicing;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Integer.parseInt;

public class StandardInvoice extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.standard_invoice);
        init();
    }
    public void init() {
        /*
        int tableId = getResources().getIdentifier(standard_invoice_table, "id", getPackageName());*/
        TableLayout stk = (TableLayout) findViewById(R.id.standard_invoice_table);
        TableRow tbrow0 = new TableRow(this);
        tbrow0.setMinimumHeight(100);
        TextView tv0 = new TextView(this);
        tv0.setText(" Description ");
        tv0.setTextColor(0xFD206E1A);
        tv0.setMinimumWidth(300);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Quantity ");
        tv1.setTextColor(0xFD206E1A);
        tv1.setMinimumWidth(200);
        tv1.setGravity(Gravity.CENTER);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Price ");
        tv2.setMinimumWidth(200);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(0xFD206E1A);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" Total ");
        tv3.setMinimumWidth(300);
        tv3.setTextColor(0xFD206E1A);
        tv3.setGravity(Gravity.RIGHT);
        tbrow0.addView(tv3);
        stk.addView(tbrow0);
        for (int i = 0; i < 25; i++) {
            TableRow tbrow = new TableRow(this);
            tbrow.setMinimumHeight(150);
            TextView t1v = new TextView(this);
            t1v.setText("Some Description");
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.LEFT);
            t1v.setMaxWidth(300);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText("20000");
            t2v.setTextColor(Color.BLACK);
            t2v.setMaxWidth(200);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText("3");
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.CENTER);
            t3v.setMaxWidth(200);
            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setText("£" + (parseInt(String.valueOf(t3v.getText()))* parseInt(String.valueOf(t2v.getText()))));
            t4v.setMaxWidth(300);
            t4v.setTextColor(Color.BLACK);
            t4v.setGravity(Gravity.RIGHT);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }

    }
}

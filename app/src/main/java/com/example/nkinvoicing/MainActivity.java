package com.example.nkinvoicing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.cardview.widget.CardView;
import androidx.gridlayout.widget.GridLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
GridLayout myGrid;
MyDatabaseManager dbMngr;
private List<InvoiceData> invoices;
HashMap<String,InvoiceData> invoiceHashMap;
Intent refresh;
Intent editInvoice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        refresh=new Intent(this,MainActivity.class);
        super.onCreate(savedInstanceState);
        dbMngr = new MyDatabaseManager(MainActivity.this);
        setContentView(R.layout.activity_main);
        myGrid = findViewById(R.id.myGrid);
        invoices = dbMngr.getAllInvoices();
        invoiceHashMap=new HashMap<>();
        getCards(this,invoices);
        editInvoice = new Intent(this,ReconstructedStandardInvoice.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu,menu);
        return true;
    }
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        ActionMenuItemView picker = findViewById(R.id.statusDropdown);
        switch(item.getItemId()) {
            case R.id.Paid:
                // code block
                picker.setTitle("Status: "+"Paid");
                getCards(this, (filterPaid(true)));
                break;
            case R.id.Unpaid:
                // code block
                picker.setTitle("Status: "+"Unpaid");
                getCards(this, (filterPaid(false)));
                break;
            case R.id.OverDue:
                // code block
                picker.setTitle("Status: "+"Overdue");
                break;
            case R.id.Any:
                // code block
                picker.setTitle("Status: "+"Any");
                getCards(this, dbMngr.getAllInvoices());
                break;
        }
        return true;
    }

    private List<InvoiceData>filterPaid(Boolean paid){
        List<InvoiceData> filteredMap=new ArrayList<InvoiceData>();
        for (String s : invoiceHashMap.keySet()) {
            if(invoiceHashMap.get(s).invoicePaid==paid){
                filteredMap.add(invoiceHashMap.get(s));
            }
        }
        return filteredMap;
    }



    public void PickInvoiceType (View view){
        Intent it = new Intent(this,PickInvoice.class);
        startActivity(it);
    }

    public void getCards(final MainActivity view, List<InvoiceData> submittedInvoices){
        myGrid.removeAllViews();
        for (InvoiceData invObject: submittedInvoices) {
            invoiceHashMap.put(invObject.getID(),invObject);
            final CardView c = new CardView(this);
            TextView objectTitle = new TextView(this);
            TextView amountLbl = new TextView(this);
            TextView dueDateLbl = new TextView(this);
            TextView issueDateLbl = new TextView(this);
            TextView statusLbl = new TextView(this);
             TextView[] mylabels = {objectTitle,amountLbl,dueDateLbl,issueDateLbl,statusLbl};
            LinearLayout mainLinearLayout = new LinearLayout(this);
            LinearLayout vlayout = new LinearLayout(this);
            vlayout.setOrientation(LinearLayout.HORIZONTAL);
            ImageView img = new ImageView(this);

            for (int j=0; j<mylabels.length;j++){

                mylabels[j].setMaxWidth(300);
                mylabels[j].setElegantTextHeight(true);
                mylabels[j].setSingleLine(false);
                mylabels[j].setGravity(Gravity.CENTER);

            }

            objectTitle.setText(invObject.contacts.receiverCompany);
            objectTitle.setTextSize(15);
            objectTitle.setTextColor(Color.rgb(244, 199, 163));

            issueDateLbl.setText("Issue Date: "+invObject.invoiceDate);
            issueDateLbl.setTextSize(12);
            issueDateLbl.setTextColor(Color.rgb(199, 227, 168));


            amountLbl.setText("Amount: £"+invObject.getAmount());
            amountLbl.setTextSize(12);
            amountLbl.setTextColor(Color.rgb(167, 224, 215));
            amountLbl.setMaxWidth(150);

            //set as paid or not
            if(invObject.invoicePaid){
                statusLbl.setText("Status:Paid");
                statusLbl.setTextColor(Color.rgb(60, 249, 96));
            }else{
                statusLbl.setText("Status:Unpaid");
                statusLbl.setTextColor(Color.rgb(249, 127, 117));
            }
            statusLbl.setTextSize(12);
            statusLbl.setMaxWidth(120);

            dueDateLbl.setText("Due: "+invObject.dueDate);
            dueDateLbl.setTextSize(12);
            dueDateLbl.setTextColor(Color.rgb(195, 174, 211));

            Bitmap bmp;
            int width=300;
            int height=300;
            bmp= BitmapFactory.decodeResource(getResources(),R.drawable.photo_add_roundedblack_512);//image is your image
            bmp= Bitmap.createScaledBitmap(bmp, width,height, true);
            img.setImageBitmap(bmp);
            img.setPadding(10,10,10,10);

            mainLinearLayout.setOrientation(LinearLayout.VERTICAL);
            mainLinearLayout.setGravity(Gravity.CENTER);
            vlayout.setGravity(Gravity.CENTER);


            mainLinearLayout.addView(img);
            mainLinearLayout.addView(objectTitle);
            mainLinearLayout.addView(issueDateLbl);
            vlayout.addView(amountLbl);
            vlayout.addView(statusLbl);
            mainLinearLayout.addView(vlayout);
            mainLinearLayout.addView(dueDateLbl);



            c.addView(mainLinearLayout);
            c.setCardBackgroundColor(Color.rgb(51, 51, 51));
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(
                    ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                    ViewGroup.MarginLayoutParams.WRAP_CONTENT
            );
            marginLayoutParams.setMargins(8, 8, 8, 8);
            c.setLayoutParams(marginLayoutParams);
            c.setTag(invObject.getID());
            c.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    showInvoice(c);
                } });
            c.setOnLongClickListener(new View.OnLongClickListener() {
                boolean clicked=false;
                public boolean onLongClick(View v) {
                    clicked=selectOptions(c,clicked);
                    return true;
                } });
            myGrid.addView(c);
        }
    }

    private void showInvoice(CardView c) {
        editInvoice.putExtra("InvoiceData", invoiceHashMap.get(c.getTag()));
        startActivity(editInvoice);
    }

    private boolean selectOptions(final CardView c, boolean clicked) {
        if(!clicked) {
            Button delete = new Button(this);
            delete.setBackgroundColor(Color.RED);
            delete.setTextColor(Color.WHITE);
            delete.setText("Delete");
            delete.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            delete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean deleted=dbMngr.deleteItemFromDB(invoiceHashMap.get(c.getTag()));
                    if(deleted){
                        Toast.makeText(MainActivity.this, "Invoice Deleted!", Toast.LENGTH_LONG).show();
                        invoices.remove(invoiceHashMap.get(c.getTag()));
                        invoiceHashMap.remove(c.getTag());
                        getCards(MainActivity.this, invoices);
                    }else{
                        Toast.makeText(MainActivity.this, "Unable to delete invoice :C", Toast.LENGTH_SHORT).show();
                    }
                }
            });



            Button setPaid = new Button(this);
            setPaid.setBackgroundColor(Color.rgb(90, 153, 167));
            setPaid.setTextColor(Color.WHITE);
            setPaid.setText("Mark Paid");
            setPaid.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            setPaid.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean markedPaid=dbMngr.changeToPaid(c.getTag().toString());
                    if(markedPaid){
                        Toast.makeText(MainActivity.this, "Status Updated!", Toast.LENGTH_SHORT).show();
                        invoiceHashMap.get(c.getTag()).invoicePaid=true;
                        invoices=dbMngr.getAllInvoices();
                        getCards(MainActivity.this, invoices);
                    }else{
                        Toast.makeText(MainActivity.this, "Unable to update invoice status :C", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            LinearLayout btnLayout = new LinearLayout(this);
            btnLayout.setBackgroundColor(Color.argb(120,191, 181, 180));
            btnLayout.setVerticalFadingEdgeEnabled(true);
            btnLayout.setHorizontalFadingEdgeEnabled(true);
            btnLayout.setTag(c.getTag()+"lnrLayout");
            btnLayout.setOrientation(LinearLayout.VERTICAL);
            btnLayout.setGravity(Gravity.CENTER);
            btnLayout.addView(setPaid);
            btnLayout.addView(delete);
            c.addView(btnLayout);
            return true;
        }else{
            LinearLayout mylayout=c.findViewWithTag(c.getTag()+"lnrLayout");
            c.removeView(mylayout);
            return false;
        }
    }
}
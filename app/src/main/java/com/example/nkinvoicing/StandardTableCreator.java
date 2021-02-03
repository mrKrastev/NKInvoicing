package com.example.nkinvoicing;

import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;

public class StandardTableCreator extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

   InvoiceData invData;
   Intent showInvoice;
   int RowCounter=1;
   int inputChangeIdentifier;
   HashMap<Integer,TableItem> hsitems;
    LinearLayout main;
    ConstraintLayout mainConstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hsitems = new HashMap();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_input_ui);
        main = (LinearLayout) findViewById(R.id.mainLayout);
        main.setGravity(Gravity.CENTER);
        mainConstr=(ConstraintLayout) findViewById(R.id.mainConstraint);
        invData = (InvoiceData) getIntent().getSerializableExtra("InvoiceData");
        showInvoice = new Intent(this,StandardInvoice.class);
        if(!invData.tbItems.isEmpty()){
            Button forRenaming = findViewById(R.id.confirmInvoiceBtn);
            forRenaming.setText("Update Invoice");
            displayInitialData(invData.tbItems);
        }else{
        generateItem();};
        Button addMore=(Button) findViewById(R.id.addMoreBtn);
        addMore.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {
            generateItem();
            Toast.makeText(StandardTableCreator.this, "New Item Added", Toast.LENGTH_SHORT).show();
        }});
    }

    private void displayInitialData(List<TableItem>items) {
        RowCounter=1;
        for (TableItem item:items) {

            final TextView description = getLabel("Description "+RowCounter);
            final TextView date = getLabel("Date "+RowCounter);
            final TextView quantity = getLabel("Quantity "+RowCounter);
            final TextView price = getLabel("Price "+RowCounter);

            final EditText descriptionInp= getTextInput("descriptionInput "+RowCounter);
            final EditText dateInp= getTextInput("dateInput "+RowCounter);
            final FloatingActionButton pickDateBtn = getButton(RowCounter);
            final EditText priceInp= getNumericInput("priceInput "+RowCounter);
            final EditText qtyInp= getNumericInput("qtyInput "+RowCounter);

            descriptionInp.setText(item.description);
            dateInp.setText(item.date);
            priceInp.setText(String.valueOf(item.price));
            qtyInp.setText(String.valueOf(item.quantity));

            Button collapseRowBtn = new Button(this);
            collapseRowBtn.setText("Table item "+RowCounter+" ▼");
            collapseRowBtn.setTextColor(Color.rgb(233, 159, 89));
            collapseRowBtn.setBackgroundColor(Color.TRANSPARENT);
            collapseRowBtn.setTextSize(26);
            collapseRowBtn.setWidth(ActionBar.LayoutParams.WRAP_CONTENT);
            collapseRowBtn.setGravity(Gravity.LEFT);
            collapseRowBtn.setOnClickListener(new View.OnClickListener() {
                boolean collapsed=false;
                int desHeight;
                int dateHeight;
                int priceHeight;
                int qtyHeight;
                int desHeightInp;
                int dateHeightInp;
                int priceHeightInp;
                int qtyHeightInp;
                int pickDateHeight;
                public void onClick(View v) {
                    if(!collapsed){
                        //store heights before:
                        desHeight = description.getHeight();
                        dateHeight = date.getHeight();
                        priceHeight = price.getHeight();
                        qtyHeight = quantity.getHeight();
                        desHeightInp = descriptionInp.getHeight();
                        dateHeightInp = dateInp.getHeight();
                        priceHeightInp = priceInp.getHeight();
                        qtyHeightInp = qtyInp.getHeight();
                        pickDateHeight=pickDateBtn.getHeight();

                        //collapse the labels:
                        collapse(description,1000,desHeight);
                        collapse(date,1000,dateHeight);
                        collapse(price,1000,priceHeight);
                        collapse(quantity,1000,qtyHeight);
                        //collapse the inputs:
                        collapse(descriptionInp,1000,desHeightInp);
                        collapse(dateInp,1000,dateHeightInp);
                        collapse(priceInp,1000,priceHeightInp);
                        collapse(qtyInp,1000,qtyHeightInp);
                        collapse(pickDateBtn,1000,pickDateHeight);


                        collapsed=true;

                    }else{
                        //expand labels:

                        expand(description,1000, desHeight);
                        expand(price,1000, priceHeight);
                        expand(quantity,1000, qtyHeight);
                        expand(date,1000, dateHeight);

                        //expand inputs:
                        expand(descriptionInp,1000,desHeightInp);
                        expand(priceInp,1000, priceHeightInp);
                        expand(qtyInp,1000, qtyHeightInp);
                        expand(dateInp,1000, dateHeightInp);
                        expand(pickDateBtn,1000,pickDateHeight);

                        collapsed=false;
                    }
                }
            });

            main.addView(collapseRowBtn);
            main.addView(description);
            main.addView(descriptionInp);
            main.addView(date);
            main.addView(dateInp);
            main.addView(pickDateBtn);
            main.addView(quantity);
            main.addView(qtyInp);
            main.addView(price);
            main.addView(priceInp);

            hsitems.put(RowCounter,new TableItem(null,null,0,null));

            RowCounter=RowCounter+1;

        }
    }

    public void generateItem(){


        final TextView description = getLabel("Description "+RowCounter);
        final TextView date = getLabel("Date "+RowCounter);
        final TextView quantity = getLabel("Quantity "+RowCounter);
        final TextView price = getLabel("Price "+RowCounter);

        final EditText descriptionInp= getTextInput("descriptionInput "+RowCounter);
        final EditText dateInp= getTextInput("dateInput "+RowCounter);
        final FloatingActionButton pickDateBtn = getButton(RowCounter);
        final EditText priceInp= getNumericInput("priceInput "+RowCounter);
        final EditText qtyInp= getNumericInput("qtyInput "+RowCounter);

        Button collapseRowBtn = new Button(this);
        collapseRowBtn.setText("Table item "+RowCounter+" ▼");
        collapseRowBtn.setTextColor(Color.rgb(233, 159, 89));
        collapseRowBtn.setBackgroundColor(Color.TRANSPARENT);
        collapseRowBtn.setTextSize(26);
        collapseRowBtn.setWidth(ActionBar.LayoutParams.WRAP_CONTENT);
        collapseRowBtn.setGravity(Gravity.LEFT);
        collapseRowBtn.setOnClickListener(new View.OnClickListener() {
            boolean collapsed=false;
            int desHeight;
            int dateHeight;
            int priceHeight;
            int qtyHeight;
            int desHeightInp;
            int dateHeightInp;
            int priceHeightInp;
            int qtyHeightInp;
            int pickDateHeight;
            public void onClick(View v) {
                if(!collapsed){
                    //store heights before:
                    desHeight = description.getHeight();
                   dateHeight = date.getHeight();
                   priceHeight = price.getHeight();
                    qtyHeight = quantity.getHeight();
                    desHeightInp = descriptionInp.getHeight();
                    dateHeightInp = dateInp.getHeight();
                    pickDateHeight = pickDateBtn.getHeight();
                    priceHeightInp = priceInp.getHeight();
                    qtyHeightInp = qtyInp.getHeight();


                //collapse the labels:
                collapse(description,1000,desHeight);
                collapse(date,1000,dateHeight);
                collapse(price,1000,priceHeight);
                collapse(quantity,1000,qtyHeight);
                //collapse the inputs:
                    collapse(descriptionInp,1000,desHeightInp);
                    collapse(dateInp,1000,dateHeightInp);
                    collapse(priceInp,1000,priceHeightInp);
                    collapse(qtyInp,1000,qtyHeightInp);
                    collapse(pickDateBtn,1000,pickDateHeight);


                collapsed=true;

            }else{
                //expand labels:

                expand(description,1000, desHeight);
                expand(price,1000, priceHeight);
                expand(quantity,1000, qtyHeight);
                expand(date,1000, dateHeight);

                //expand inputs:
                    expand(descriptionInp,1000,desHeightInp);
                    expand(priceInp,1000, priceHeightInp);
                    expand(qtyInp,1000, qtyHeightInp);
                    expand(dateInp,1000, dateHeightInp);
                    expand(pickDateBtn,1000,pickDateHeight);
                collapsed=false;
            }
            }
        });

        main.addView(collapseRowBtn);
        main.addView(description);
        main.addView(descriptionInp);
        main.addView(date);
        main.addView(pickDateBtn);
        main.addView(dateInp);
        main.addView(quantity);
        main.addView(qtyInp);
        main.addView(price);
        main.addView(priceInp);

        hsitems.put(RowCounter,new TableItem(null,null,0,null));

        RowCounter=RowCounter+1;
    }

    private FloatingActionButton getButton(int rowCounter) {
        FloatingActionButton btn = new FloatingActionButton(this);
        btn.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(79, 77, 75)));
        btn.setImageDrawable(getResources().getDrawable(R.drawable.icon_calendar_green));
        ImageViewCompat.setImageTintList(
                btn,
                ColorStateList.valueOf(Color.rgb(244, 149, 30))
        );
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               pickDate(v);
            }
        });
        btn.setId(rowCounter);
        return btn;
    }

    public TextView getLabel (String labelName){
        TextView label = new TextView(this);
        label.setText(labelName);
        label.setTextColor(Color.rgb(223, 152, 137));
        label.setTextSize(24);
        label.setWidth(ActionBar.LayoutParams.WRAP_CONTENT);
        label.setGravity(Gravity.CENTER);
        return label;
    }
    public EditText getTextInput(String inputTag){
        EditText input = new EditText(this);
        input.setTag(inputTag);
        input.setTextColor(Color.rgb(169, 214, 208));
        input.setTextSize(24);
        input.setWidth(ActionBar.LayoutParams.WRAP_CONTENT);
        input.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        input.setElegantTextHeight(true);
        input.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        input.setSingleLine(false);
        input.setMinimumHeight(150);
        input.setGravity(Gravity.CENTER);
        return input;
    }
    public EditText getNumericInput (String inputTag){
        EditText input = new EditText(this);
        input.setTag(inputTag);
        input.setTextColor(Color.rgb(169, 214, 208));
        input.setTextSize(24);
        input.setWidth(ActionBar.LayoutParams.WRAP_CONTENT);
        input.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        input.setElegantTextHeight(true);
        input.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        input.setSingleLine(false);
        input.setMinimumHeight(150);
        input.setGravity(Gravity.CENTER);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        return input;
    }
    public void pickDate(View view) {
        int button = view.getId();
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
         inputChangeIdentifier = button;
    }


    //________Experiment________________
    public static void expand(final View v, int duration, int targetHeight) {
        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }
    public static void collapse(final View v, int duration, int currentHeight) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(currentHeight, 0);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }


    public InvoiceData generateInvoiceData(){
        invData.clearTableItems();
        for (Integer i: hsitems.keySet() ) {
            String descriptionName = "descriptionInput "+i;
            EditText description = (EditText) main.findViewWithTag(descriptionName);

            String dateName = "dateInput "+i;
            EditText date = (EditText) main.findViewWithTag(dateName);

            String qtyName = "qtyInput "+i;
            EditText qty = (EditText) main.findViewWithTag(qtyName);
            if(String.valueOf(qty.getText()).equals("")){
                qty.setText("0");
            }

            String priceName = "priceInput "+i;
            EditText price = (EditText) main.findViewWithTag(priceName);
            if(String.valueOf(price.getText()).equals("")){
                price.setText("0");
            }

            hsitems.get(i).setDescription(String.valueOf(description.getText()));
            hsitems.get(i).setDate(String.valueOf(date.getText()));
            hsitems.get(i).setQuantity(Integer.parseInt(String.valueOf(qty.getText())));
            hsitems.get(i).setPrice(Double.parseDouble(String.valueOf(price.getText())));

            invData.addTableItem(hsitems.get(i));


        }

        return invData;
    }

    public void showInvoice(View v){
        Intent i = new Intent(this,StandardInvoice.class);
        i.putExtra("InvoiceData", generateInvoiceData());
        startActivity(i);
    }

    //_________DATE PICKER IMPLEMENTS_________________________
    public void processIssueDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (day_string +
                "/" + month_string + "/" + year_string);
        EditText date=main.findViewWithTag("dateInput "+inputChangeIdentifier);
        date.setText(dateMessage);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        processIssueDatePickerResult(year,month,dayOfMonth);
    }
}



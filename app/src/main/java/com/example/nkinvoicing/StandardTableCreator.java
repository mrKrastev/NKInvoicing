package com.example.nkinvoicing;

import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class StandardTableCreator extends AppCompatActivity {

   InvoiceData invData;
   Intent showInvoice;
   int RowCounter=1;
   HashMap<Integer,TableItem> items;
    LinearLayout main;
    ConstraintLayout mainConstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        items = new HashMap<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_input_ui);
        main = (LinearLayout) findViewById(R.id.mainLayout);
        mainConstr=(ConstraintLayout) findViewById(R.id.mainConstraint);
        invData = (InvoiceData) getIntent().getSerializableExtra("InvoiceData");
        showInvoice = new Intent(this,StandardInvoice.class);
        generateItem();
        Button addMore=(Button) findViewById(R.id.addMoreBtn);
        addMore.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {
            generateItem();
            Toast.makeText(StandardTableCreator.this, "New Item Added", Toast.LENGTH_SHORT).show();
        }});
    }
    public void generateItem(){


        final TextView description = getLabel("Description "+RowCounter);
        final TextView date = getLabel("Date "+RowCounter);
        final TextView quantity = getLabel("Quantity "+RowCounter);
        final TextView price = getLabel("Price "+RowCounter);

        final EditText descriptionInp=getInput("descriptionInput "+RowCounter);
        final EditText dateInp=getInput("dateInput "+RowCounter);
        final EditText priceInp=getInput("priceInput "+RowCounter);
        final EditText qtyInp=getInput("qtyInput "+RowCounter);

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

                collapsed=false;
            }
            }
        });

        main.addView(collapseRowBtn);
        main.addView(description);
        main.addView(descriptionInp);
        main.addView(date);
        main.addView(dateInp);
        main.addView(quantity);
        main.addView(qtyInp);
        main.addView(price);
        main.addView(priceInp);

        items.put(RowCounter,new TableItem(null,null,0,null));

        RowCounter=RowCounter+1;
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
    public EditText getInput (String inputTag){
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


    //________Experiment________________
    public static void expand(final View v, int duration, int targetHeight) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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

        for (Integer i: items.keySet() ) {
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

            items.get(i).setDescription(String.valueOf(description.getText()));
            items.get(i).setDate(String.valueOf(date.getText()));
            items.get(i).setQuantity(Integer.parseInt(String.valueOf(qty.getText())));
            items.get(i).setPrice(Double.parseDouble(String.valueOf(price.getText())));

            invData.addTableItem(items.get(i));

            Toast.makeText(this, invData.toString(), Toast.LENGTH_SHORT).show();
        }

        return invData;
    }

    public void showInvoice(View v){
        Intent i = new Intent(this,StandardInvoice.class);
        i.putExtra("InvoiceData", generateInvoiceData());
        //startActivity(i);
    }
}



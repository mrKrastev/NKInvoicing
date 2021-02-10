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

   private InvoiceData invData;
   private Intent showInvoice;
   private int RowCounter=1; //to keep track of the number of items and link their inputs
   private int inputChangeIdentifier;
   private HashMap<Integer,TableItem> hsitems;
   private LinearLayout main;
   private ConstraintLayout mainConstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hsitems = new HashMap();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_input_ui);
        //getting the layouts
        main = (LinearLayout) findViewById(R.id.mainLayout);
        main.setGravity(Gravity.CENTER);
        mainConstr=(ConstraintLayout) findViewById(R.id.mainConstraint);
        //getting the invoice data object
        invData = (InvoiceData) getIntent().getSerializableExtra("InvoiceData");
        //creating an intent for later
        showInvoice = new Intent(this,StandardInvoice.class);
        //if the intent comes from invoice creator, tbItems would be empty so we generate an empty table item to fill in, otherwise we prepopulate it
        if(!invData.tbItems.isEmpty()){
            Button forRenaming = findViewById(R.id.confirmInvoiceBtn); //cosmetic change for the button as it would be updating the data rather than generating it
            forRenaming.setText("Update Invoice");
            displayInitialData(invData.tbItems);// getting what was already in the table and setting it to the inputs of the table items on screen
        }else{
        generateItem();}; //if not we just generate an empty input sector
        Button addMore=(Button) findViewById(R.id.addMoreBtn);
        addMore.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {
            generateItem(); //every time the button is clicked, we get another set of inputs with unique names
            Toast.makeText(StandardTableCreator.this, "New Item Added", Toast.LENGTH_SHORT).show(); //nice message cue that the item is created
        }});
    }

    private void displayInitialData(List<TableItem>items) {
        RowCounter=1; //resetting the counter just in case :D
        for (TableItem item:items) { //for each item that came with the invoiceData object, we generate an input sector to edit this specific item

            //setting the labels as the name of the label + the row counter, this way its always unique and the items within the same item have the same number following them
            //labels:
            final TextView description = getLabel("Description "+RowCounter);
            final TextView date = getLabel("Date "+RowCounter);
            final TextView quantity = getLabel("Quantity "+RowCounter);
            final TextView price = getLabel("Price "+RowCounter);
            //inputs
            final EditText descriptionInp= getTextInput("descriptionInput "+RowCounter);
            final EditText dateInp= getTextInput("dateInput "+RowCounter);
            final FloatingActionButton pickDateBtn = getButton(RowCounter);
            final EditText priceInp= getNumericInput("priceInput "+RowCounter);
            final EditText qtyInp= getNumericInput("qtyInput "+RowCounter);
            //setting their initial values ( to be edited)
            descriptionInp.setText(item.description);
            dateInp.setText(item.date);
            priceInp.setText(String.valueOf(item.price));
            qtyInp.setText(String.valueOf(item.quantity));
            //creating the collapse trigger as well as the label that goes with it
            Button collapseRowBtn = new Button(this);
            collapseRowBtn.setText("Table item "+RowCounter+" ▼");
            collapseRowBtn.setTextColor(Color.rgb(233, 159, 89));
            collapseRowBtn.setBackgroundColor(Color.TRANSPARENT);
            collapseRowBtn.setTextSize(26);
            collapseRowBtn.setWidth(ActionBar.LayoutParams.WRAP_CONTENT);
            collapseRowBtn.setGravity(Gravity.LEFT);
            collapseRowBtn.setOnClickListener(new View.OnClickListener() { //upon clicking the button collapse/expand methods should trigger
                boolean collapsed=false; // flag to know which method should be called
                //variables to store the initial height of the items (to be used when expanding later)
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
                        collapse(description,1000,desHeight); //setting the object, the duration of the animation and the initial height to go from
                        collapse(date,1000,dateHeight);
                        collapse(price,1000,priceHeight);
                        collapse(quantity,1000,qtyHeight);
                        //collapse the inputs:
                        collapse(descriptionInp,1000,desHeightInp);
                        collapse(dateInp,1000,dateHeightInp);
                        collapse(priceInp,1000,priceHeightInp);
                        collapse(qtyInp,1000,qtyHeightInp);
                        collapse(pickDateBtn,1000,pickDateHeight);


                        collapsed=true; // flagging that the objects have been collapsed

                    }else{
                        //expand labels:

                        expand(description,1000, desHeight); // expanding by passing the object, the duration, and the target height
                        expand(price,1000, priceHeight);
                        expand(quantity,1000, qtyHeight);
                        expand(date,1000, dateHeight);

                        //expand inputs:
                        expand(descriptionInp,1000,desHeightInp);
                        expand(priceInp,1000, priceHeightInp);
                        expand(qtyInp,1000, qtyHeightInp);
                        expand(dateInp,1000, dateHeightInp);
                        expand(pickDateBtn,1000,pickDateHeight);

                        collapsed=false; //flagging again
                    }
                }
            });
            //adding the collapse button, the labels and the input fields to the main
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
            //adding the item to a hashmap with the id number as a key and  an object ( TableItem ) corresponding to this item section
            hsitems.put(RowCounter,new TableItem(null,null,0,null));

            RowCounter=RowCounter+1; // setting the counter for the next set of inputs

        }
    }

    public void generateItem(){

        // creating labels
        final TextView description = getLabel("Description "+RowCounter);
        final TextView date = getLabel("Date "+RowCounter);
        final TextView quantity = getLabel("Quantity "+RowCounter);
        final TextView price = getLabel("Price "+RowCounter);
        //creating inputs
        final EditText descriptionInp= getTextInput("descriptionInput "+RowCounter);
        final EditText dateInp= getTextInput("dateInput "+RowCounter);
        final FloatingActionButton pickDateBtn = getButton(RowCounter);
        final EditText priceInp= getNumericInput("priceInput "+RowCounter);
        final EditText qtyInp= getNumericInput("qtyInput "+RowCounter);
        //creating collapse button
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
        //adding views to main
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
        //updating the hashmap
        hsitems.put(RowCounter,new TableItem(null,null,0,null));

        RowCounter=RowCounter+1; //updating the counter
    }

    private FloatingActionButton getButton(int rowCounter) { //method to generate a fab with a calendar popping up
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
        btn.setId(rowCounter); //setting the id to correspond to the row counter, this way there will be an unique fab for each table item input section
        return btn;
    }

    public TextView getLabel (String labelName){ //method to get myself labels with the same parameters
        TextView label = new TextView(this);
        label.setText(labelName);
        label.setTextColor(Color.rgb(223, 152, 137));
        label.setTextSize(24);
        label.setWidth(ActionBar.LayoutParams.WRAP_CONTENT);
        label.setGravity(Gravity.CENTER);
        return label;
    }
    public EditText getTextInput(String inputTag){ // method to get myself inputs that have the same parameters for text input
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
    public EditText getNumericInput (String inputTag){ //method to get myself a numeric input
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
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        return input;
    }
    public void pickDate(View view) { //method which makes a calendar to pop up
        int button = view.getId();
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
         inputChangeIdentifier = button; //identifier that shows which fab was clicked, this way i can update the correct Date input field
    }


    //________the exapand/collapse methods________________________________________________________________________________________________
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

    public InvoiceData generateInvoiceData(){ // method which finalizes the input data and sends it over to the next screen
        invData.clearTableItems(); //ensuring that the table items field in invoice data object is clean before adding everything in
        for (Integer i: hsitems.keySet() ) { // using the hashmap from earlier to get each object and assign its fields to the inputs of the same number
            //getting the input related to the number of the hashmap key
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
            //setting the fields of the object which is related to this key with the corresponding inputs
            hsitems.get(i).setDescription(String.valueOf(description.getText()));
            hsitems.get(i).setDate(String.valueOf(date.getText()));
            hsitems.get(i).setQuantity(Integer.parseInt(String.valueOf(qty.getText())));
            hsitems.get(i).setPrice(Double.parseDouble(String.valueOf(price.getText())));
            hsitems.get(i).setInvoiceID(invData.getID());
            invData.addTableItem(hsitems.get(i)); //adding the object to the invoice data


        }

        return invData;
    }

    public void showInvoice(View v){ //moving to the next screen
        Intent i = new Intent(this,StandardInvoice.class);
        i.putExtra("InvoiceData", generateInvoiceData()); // calling the finalizing method here
        startActivity(i);
    }

    //_________DATE PICKER IMPLEMENTS______________________________________________________________________
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



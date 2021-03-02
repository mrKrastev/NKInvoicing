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
import android.view.ViewGroup;
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
import com.google.android.material.internal.ViewUtils;

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
    String dbUpdate=null;
    private Intent backToRecInvoice;

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
        dbUpdate=(String) getIntent().getSerializableExtra("String");
        //creating an intent for later
            backToRecInvoice = new Intent(this,ReconstructedStandardInvoice.class);
            showInvoice = new Intent(this, StandardInvoice.class);
        //if the intent comes from invoice creator, tbItems would be empty so we generate an empty table item to fill in, otherwise we prepopulate it
        if(!invData.tbItems.isEmpty()){
            Button forRenaming = findViewById(R.id.confirmInvoiceBtn); //cosmetic change for the button as it would be updating the data rather than generating it
            forRenaming.setText("Update Invoice");
            displayInitialData(invData.tbItems);// getting what was already in the table and setting it to the inputs of the table items on screen
        }else{
        generateItem(new TableItem());}; //if not we just generate an empty input sector
        Button addMore=(Button) findViewById(R.id.addMoreBtn);
        addMore.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {
            generateItem(new TableItem()); //every time the button is clicked, we get another set of inputs with unique names
            Toast.makeText(StandardTableCreator.this, "New Item Added", Toast.LENGTH_SHORT).show(); //nice message cue that the item is created
        }});
    }

    private void displayInitialData(List<TableItem>items) {
        RowCounter=1; //resetting the counter just in case :D
        for (TableItem item:items) { //for each item that came with the invoiceData object, we generate an input sector to edit this specific item

          generateItem(item);

        }
    }

    public void generateItem(TableItem item){
        item.setInvoiceID(invData.getID());
         //creating list to hold everything
        final LinearLayout tempLayout = new LinearLayout(this);
        tempLayout.setOrientation(LinearLayout.VERTICAL);
        tempLayout.setGravity(Gravity.CENTER);
        tempLayout.setTag("listNo"+RowCounter);
        //creating header
        final LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER);
        header.setMinimumWidth(LinearLayout.LayoutParams.MATCH_PARENT);

        header.setTag(RowCounter);
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
        //populating with fake data
        descriptionInp.setText(item.description);
        dateInp.setText(item.date);
        priceInp.setText(String.valueOf(item.price));
        qtyInp.setText(String.valueOf(item.quantity));
        //creating delete button
        Button deleteItemBtn = new Button(this);
        deleteItemBtn.setAllCaps(false);
        deleteItemBtn.setTextSize(15);
        deleteItemBtn.setText("Delete");
        deleteItemBtn.setTag("delete"+RowCounter);
        deleteItemBtn.setBackgroundColor(Color.TRANSPARENT);
        deleteItemBtn.setTextColor(Color.rgb(249, 96, 60));
        deleteItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.removeView(tempLayout);
                main.removeView(header);
                hsitems.remove(header.getTag());
            }
        });
        //creating collapse button
        final Button collapseRowBtn = new Button(this);
        collapseRowBtn.setAllCaps(false);
        collapseRowBtn.setText("Table Item "+"▼");
        collapseRowBtn.setTextColor(Color.rgb(234, 168, 80));
        collapseRowBtn.setBackgroundColor(Color.TRANSPARENT);
        collapseRowBtn.setTextSize(26);
        collapseRowBtn.setGravity(Gravity.LEFT);
        collapseRowBtn.setPadding(0,0,100,0);
        collapseRowBtn.setOnClickListener(new View.OnClickListener() {
            boolean collapsed=false;

            int layoutHeight;
            public void onClick(View v) {
                if(!collapsed){
                    layoutHeight=tempLayout.getHeight();
                    collapse(tempLayout,1000,layoutHeight);
                collapsed=true;
                collapseRowBtn.setText("Table Item "+"►");

            }else{
                expand(tempLayout,1000,layoutHeight);
                collapsed=false;
                    collapseRowBtn.setText("Table Item "+"▼");

            }
            }
        });
        header.addView(collapseRowBtn);
        header.addView(deleteItemBtn);
        //adding views to a single layout
        main.addView(header);
        tempLayout.addView(description);
        tempLayout.addView(descriptionInp);
        tempLayout.addView(date);
        tempLayout.addView(pickDateBtn);
        tempLayout.addView(dateInp);
        tempLayout.addView(quantity);
        tempLayout.addView(qtyInp);
        tempLayout.addView(price);
        tempLayout.addView(priceInp);
        //adding to main
        main.addView(tempLayout);
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


            String priceName = "priceInput "+i;
            EditText price = (EditText) main.findViewWithTag(priceName);

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
        if(dbUpdate!=null){
            backToRecInvoice.putExtra("InvoiceData", generateInvoiceData());
        startActivity(backToRecInvoice);
        }else {
            Intent i = new Intent(this, StandardInvoice.class);
            i.putExtra("InvoiceData", generateInvoiceData()); // calling the finalizing method here
            startActivity(i);
        }
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



package com.example.nkinvoicing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.gridlayout.widget.GridLayout;

import android.app.ActionBar;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.Layout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM;

public class MainActivity extends AppCompatActivity {
GridLayout myGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyDatabaseManager dbMngr = new MyDatabaseManager(MainActivity.this);
        setContentView(R.layout.activity_main);
        myGrid = findViewById(R.id.myGrid);
        getCards(this);
    }
    public void PickInvoiceType (View view){
        Intent it = new Intent(this,PickInvoice.class);
        startActivity(it);
    }

    public void getCards(final MainActivity view){
        for (int i = 1; i<31;i++){
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

            objectTitle.setText("Company name here");
            objectTitle.setTextSize(15);
            objectTitle.setTextColor(Color.rgb(244, 199, 163));

            issueDateLbl.setText("Issued on: 12/12/12");
            issueDateLbl.setTextSize(12);
            issueDateLbl.setTextColor(Color.rgb(199, 227, 168));

            amountLbl.setText("Amount: £21312");
            amountLbl.setTextSize(12);
            amountLbl.setTextColor(Color.rgb(167, 224, 215));
            amountLbl.setMaxWidth(150);

            statusLbl.setText("Status:Unpaid");
            statusLbl.setTextSize(12);
            statusLbl.setTextColor(Color.rgb(249, 127, 117));
            statusLbl.setMaxWidth(120);

            dueDateLbl.setText("Due: 12/12/12");
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
            c.setId(i);
            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(view, "Card ID: "+c.getId(), Toast.LENGTH_SHORT).show();
                }
            });

            myGrid.addView(c);
        }
    }
}
package com.example.nkinvoicing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.gridlayout.widget.GridLayout;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM;

public class MainActivity extends AppCompatActivity {
GridLayout myGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            TextView t = new TextView(this);
            LinearLayout l = new LinearLayout(this);
            t.setText("OBJECT");
            t.setTextSize(18);
            t.setTextColor(Color.WHITE);
            ImageView img = new ImageView(this);
            Bitmap bmp;
            int width=300;
            int height=300;
            bmp= BitmapFactory.decodeResource(getResources(),R.drawable.photo_add_roundedblack_512);//image is your image
            bmp= Bitmap.createScaledBitmap(bmp, width,height, true);
            img.setImageBitmap(bmp);
            img.setPadding(10,10,10,10);
            l.addView(img);
            l.addView(t);
            l.setOrientation(LinearLayout.VERTICAL);
            l.setGravity(Gravity.CENTER);
            c.addView(l);
            c.setCardBackgroundColor(Color.TRANSPARENT);
            c.setId(i);
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(
                    ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                    ViewGroup.MarginLayoutParams.WRAP_CONTENT
            );

            marginLayoutParams.setMargins(8, 8, 8, 8);

            c.setLayoutParams(marginLayoutParams);
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
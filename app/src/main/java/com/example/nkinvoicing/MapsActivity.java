package com.example.nkinvoicing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private HashMap<String, InvoiceData> invoicesMap;
    RequestQueue queue;
    ArrayList<Marker> markers;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        invoicesMap = (HashMap<String,InvoiceData>) getIntent().getSerializableExtra("InvoicesMap");
        queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        markers=new ArrayList<>();
        counter=0;
        for (String invoiceCode: invoicesMap.keySet()
        ) {
            placeInvoice(invoicesMap.get(invoiceCode), this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(markers!=null){
            markers.clear();
            counter=0;
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(7);


    }



    @Override
    public boolean onMarkerClick(Marker marker) {

        marker.showInfoWindow();
        return false;
    }

    private void placeInvoice(final InvoiceData invObject, final MapsActivity mapsActivity){
        String key="pk.1ee96e5268c5902ba346595e1be98f42";
        String url= null;
        try {
            url = "https://us1.locationiq.com/v1/search.php?key="+key+"&format=json&q="+ URLEncoder.encode(invObject.contacts.receiverAddress,"UTF-8").replace("+","%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // Request a jsonObject response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                counter++;
                JSONObject json_obj = new JSONObject();
                try {
                    for(int i=0;i<response.length();i++) {
                        if (response.getJSONObject(i).toString().contains("lat") && response.getJSONObject(i).toString().contains("lon")) {
                            json_obj = response.getJSONObject(i);
                            break;
                        }
                    }
                    } catch (JSONException ex) {
                    Toast.makeText(mapsActivity, "Array cant get object", Toast.LENGTH_SHORT).show();
                }
                LatLng myCoordinates = null;
                try {
                    myCoordinates = new LatLng(
                                Double.parseDouble(json_obj.getString("lat")),
                                Double.parseDouble(json_obj.getString("lon"))
                        );
                } catch (JSONException ex) {
                    Toast.makeText(mapsActivity, "Json is null", Toast.LENGTH_SHORT).show();
                }
                Marker marker = mMap.addMarker(new MarkerOptions().position(myCoordinates).title(invObject.contacts.receiverCompany+"["+invObject.getID()+"]"));
                marker.setSnippet("Issue Date: "+invObject.invoiceDate);
                markers.add(marker);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(myCoordinates));
                    Toast.makeText(mapsActivity, String.valueOf(myCoordinates), Toast.LENGTH_SHORT).show();
                    mMap.setOnMarkerClickListener(mapsActivity);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Toast.makeText(MapsActivity.this,"Address not found ._.", Toast.LENGTH_SHORT).show();
                Log.e("IDFK", "onErrorResponse: "+error.toString(), null);

            }
        });

// Access the RequestQueue through your singleton class.
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        queue.add(jsonArrayRequest);
    }
}
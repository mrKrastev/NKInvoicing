package com.example.nkinvoicing;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap.setMaxZoomPreference(17);
        mMap.setMinZoomPreference(17);
        // Add a marker in Sydney and move the camera
        placeInvoice("17 Tysoe Road, Birmingham, United Kingdom", this);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(this, "This works", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void placeInvoice(String address, final com.example.nkinvoicing.MapsActivity mapsActivity){
        String key="pk.1ee96e5268c5902ba346595e1be98f42";
       String url="https://us1.locationiq.com/v1/search.php?key="+key+"&format=json&q="+address;
        RequestQueue queue = Volley.newRequestQueue(MapsActivity.this);
        // Request a jsonObject response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject json_obj = response.getJSONObject(1);   //get the 3rd item
                       LatLng myCoordinates = new LatLng(
                            Double.parseDouble(json_obj.getString("lat")),
                            Double.parseDouble(json_obj.getString("lon"))
                    );
                    Marker marker = mMap.addMarker(new MarkerOptions().position(myCoordinates).title("My marker"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(myCoordinates));
                    mMap.setOnMarkerClickListener(mapsActivity);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
        queue.add(jsonArrayRequest);
    }
}
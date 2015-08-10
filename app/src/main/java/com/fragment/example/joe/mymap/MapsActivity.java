package com.fragment.example.joe.mymap;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import static com.google.android.gms.maps.CameraUpdateFactory.*;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private static final int GPS_ERRORDIALOG_REQUEST = 9001;//ADDED

    private static final double SEATTLE_LAT = 47.60621,
            SEATTLE_LNG = -122.3;
    private static final float DEFAULTZOOM=7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }
    public void onSearch(View view)
    {
        EditText location_tf = (EditText)findViewById(R.id.TFaddress) ;//added  this
        String location = location_tf.getText().toString();//added convert to string form
        List<Address> addressList= null;//needed it outside the try/catch. Null so as not to error below for addressList
        if (location !=null || !location.equals(""))
        {
            Geocoder geocoder = new Geocoder(this);//added geocoder
            try {
               addressList = geocoder.getFromLocationName(location, 1); //added try/catch as required
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0); //to fetch the first value
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());//added the LatLng
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));//copied from down below without the 0,0 default
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));//added to get my position

        }
    }

    public void onZoom(View view)
    {
        if (view.getId()== R.id.Bzoomin)
        {
          mMap.animateCamera(CameraUpdateFactory.zoomIn());
        }

        if (view.getId()== R.id.Bzoomout)
    {
        mMap.animateCamera(CameraUpdateFactory.zoomOut());
    }
    }



    public void  changeType(View view)//added for the type button to change map type
    {
    if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }
        else
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

                    // Check if we were successful in obtaining the map.
                    // if (mMap != null) {
                    setUpMap();

            }
        }



    private void setUpMap() {

   mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    mMap.setMyLocationEnabled(true);
        double lat=47;
   double lng=-122;
     LatLng ll = new LatLng(lat, lng);
    CameraUpdate update = newLatLng(ll);

     mMap.moveCamera(update);

    }


}
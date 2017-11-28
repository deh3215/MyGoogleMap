package com.example.a32150.mygooglemap;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

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
        // Add a marker in Sydney and move the camera
        setMap(-34, 151, "雪梨好吃");
    }
    void setMap(double lat, double lng, String title)    {
        LatLng latLng = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(latLng).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        int[] ids = {R.id.spinner, R.id.spinner1, R.id.spinner2};
        int[] arrays = {R.array.point_of_view, R.array.spot, R.array.map_type};

        for(int i=0 ; i<3; i++) {
            MenuItem item = menu.findItem(ids[i]);
            Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    arrays[i], R.layout.support_simple_spinner_dropdown_item_myself);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item_myself);
            spinner.setAdapter(adapter);
            spinner.setGravity(Gravity.RIGHT);
            spinner.setOnItemSelectedListener(itemSelectedListener);
        }

        return true;//super.onCreateOptionsMenu(menu);
    }

    AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            if(i==0)    return;

            String site = ((TextView)view).getText().toString();

            Toast.makeText(MapsActivity.this, ((TextView)view).getText(), Toast.LENGTH_SHORT).show();

            switch(adapterView.getId())   {
                case R.id.spinner:
                    switch(i)   {//設定角度
                        case 1:setPointOfView(0,16.9f);break;
                        case 2:setPointOfView(60,17);break;
                    }
                    break;
                case R.id.spinner1:
                    switch(i)   {
                        case 1:setMap(40.6892128, -74.0447512, site);break;
                        case 2:setMap(48.8581961, 2.2938632, site);break;
                        case 3:setMap(30.8216698, 111.0029461, site);break;
                    }
                    break;

                case R.id.spinner2:
                    switch(i)   {
                        case 1:mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);break;
                        case 2:mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);break;
                        case 3:mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);break;
                        case 4:mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);break;
                        case 5:mMap.setMapType(GoogleMap.MAP_TYPE_NONE);break;
                    }
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    void setPointOfView(float angle, float level) {
        LatLng latlng = mMap.getCameraPosition().target;
        CameraUpdate camUpdate = CameraUpdateFactory.
                newCameraPosition(new CameraPosition.Builder()
                        .target(latlng)
                        .tilt(angle)
                        .zoom(level)
                        .build());
        mMap.animateCamera(camUpdate);
    }
}

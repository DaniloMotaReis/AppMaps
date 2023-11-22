package com.example.appmaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleMapOptions options = new GoogleMapOptions();

        options.mapType(GoogleMap.MAP_TYPE_HYBRID)
                .compassEnabled(true);

        SupportMapFragment mapFragment = SupportMapFragment.newInstance(options);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.map, mapFragment)
                .commit();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        googleMap.setTrafficEnabled(true);
    }

    public void changeMapType(View view){
        if(googleMap != null){

            int id = view.getId();

            if (id == R.id.btnNorm){
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            } else if (id == R.id.btnSate){
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            } else if (id == R.id.btnTerr) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            } else if (id == R.id.btnHibr) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            } else if (id == R.id.btnNone) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                googleMap.addMarker(new MarkerOptions()

                        .position(new LatLng(48.8584,2.2945))
                        .title("Você está aqui"));

                LatLng torreEiffel = new LatLng(48.8584, 2.2945);
                googleMap.addMarker(new MarkerOptions().position(torreEiffel).title("Marcador na Torre Eiffel"));
            }
        }
    }
}
package com.example.appmaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class Map extends AppCompatActivity implements OnMapReadyCallback {
    {/*Objetos do mapa, o googleMap é o objeto do map, já o fusedLocationProviderClient está liagado a parte de localização atual*/}
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        {/* Esse if faz a validação da permissão de localização dentro do aplicativo*/}
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        {/* Esse é um objeto utilizado na criação do map para atribuir algumas configurações iniciais nele*/}
        GoogleMapOptions options = new GoogleMapOptions();

        {/*Atribuindo uma opção simples ao objeto*/}
        options.mapType(GoogleMap.MAP_TYPE_HYBRID)
                .compassEnabled(true);

        {/*Criando o fragmento do mapa e o ligando a fragment do xml*/}
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
        LatLng fatec = new LatLng(-23.598010941769395, -46.92689222302488);
        googleMap.addMarker(new MarkerOptions()
                .position(fatec)
                .title("Fatec Cotia")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.logofatec))
        );

        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                getLastKnowLocation();
            }
        });
    }

    {/*Essa função é a qu faz as troca dos filtros do mapa*/}
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
            }
        }
    }

    private void getLastKnowLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();
                                float zoom = 15.0f;
                                LatLng latLng = new LatLng(latitude, longitude);
                                googleMap.addMarker(new MarkerOptions()
                                        .position(latLng)
                                        .title("Localização Atual"));

                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(latLng)
                                        .zoom(zoom)
                                        .build();

                                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 2000, null);
                            }
                        }
                    });
        }
    }
}
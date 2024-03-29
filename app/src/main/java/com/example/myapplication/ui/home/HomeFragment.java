package com.example.myapplication.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private HomeViewModel homeViewModel;
    private AutoCompleteTextView search;
    private TextView genericName, brandName, pharmacyName, location;
    private CardView cardView;
    private ImageView imageAd;
    private MapView mapView;
    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyAs1h8I_2kwCmFkdOeNLDAABuZnSECsuro";
    GoogleMap mMap;

    List<PharmacyModel> pharmacyList;
    PharmacyModel selected = null;

    private static final String URL = "http://medlocator.000webhostapp.com/medlocator/api/search/available.php?search=";



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mapView = root.findViewById(R.id.mapView);
        search = root.findViewById(R.id.searchText);
        cardView = root.findViewById(R.id.cardView);
        genericName = root.findViewById(R.id.genericName);
        brandName = root.findViewById(R.id.brandName);
        pharmacyName = root.findViewById(R.id.pharmacyName);
        imageAd = root.findViewById(R.id.imageAd);
        location = root.findViewById(R.id.location);

        imageAd.setImageResource(R.mipmap.ads2);
        imageAd.setScaleType(ImageView.ScaleType.FIT_XY);
        map(savedInstanceState);
        loadData();


        return root;
    }

    private void loadData(){
        pharmacyList = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.GET, URL, response -> {
            try {
                JSONObject object = new JSONObject(response);
                JSONArray jsonArray = object.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String pharmacyName = jsonObject.getString("pharmacyName");
                    String pharmacyId = jsonObject.getString("pharmacyId");
                    String location = jsonObject.getString("location");
                    String longitude = jsonObject.getString("longitude");
                    String latitude = jsonObject.getString("latitude");
                    String genericName = jsonObject.getString("genericName");
                    String brandName = jsonObject.getString("brandName");

                    PharmacyModel model = new PharmacyModel(pharmacyName, pharmacyId, location, longitude, latitude, genericName, brandName);
                    pharmacyList.add(model);
                }

                PharmacyAdapter adapter = new PharmacyAdapter(getContext(), pharmacyList);
                search.setAdapter(adapter);
                search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selected = (PharmacyModel) parent.getItemAtPosition(position);
                        onMapReady(mMap);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            Toast.makeText(getContext(), "An error occured while connecting to the server", Toast.LENGTH_SHORT).show();
        });
        queue.add(request);
    }

    private void map(Bundle savedInstanceState){
        Bundle mapViewBundle = null;

        if(savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);

        }
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mMap != null){
            mMap.clear();
        }
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {

        LatLng ny = new LatLng(10.6840,122.9563);
        Marker marker = null;
        mMap = map;
        mMap.setMinZoomPreference(12);


        if(null != selected){
            mMap.clear();
            cardView.setVisibility(View.VISIBLE);
            genericName.setText(selected.getGenericName());
            brandName.setText(selected.getBrandName());
            pharmacyName.setText(selected.getPharmacyName());
            location.setText(selected.getLocation());
            ny = new LatLng(Double.parseDouble(selected.getLatitude()), Double.parseDouble(selected.getLongitude()));
            mMap.addMarker(new MarkerOptions().position(ny).title(selected.getPharmacyName()));
        }






//        UiSettings uiSettings = map.getUiSettings();
//        uiSettings.setIndoorLevelPickerEnabled(true);
//        uiSettings.setMyLocationButtonEnabled(true);
//        uiSettings.setMapToolbarEnabled(true);
//        uiSettings.setCompassEnabled(true);
//        uiSettings.setZoomControlsEnabled(true);

        CameraPosition.Builder camBuilder = CameraPosition.builder();
        camBuilder.bearing(45);
        camBuilder.tilt(30);
        camBuilder.target(ny);
        camBuilder.zoom(15);

        CameraPosition cp = camBuilder.build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
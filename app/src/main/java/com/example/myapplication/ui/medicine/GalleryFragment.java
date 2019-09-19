package com.example.myapplication.ui.medicine;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.ui.home.PharmacyModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private MedicineViewModel medicineViewModel;

    RecyclerView recyclerView;
    Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    EditText search;


    ArrayList<MedicineItem> medicineList = new ArrayList<>();

    private static final String URL = "http://medlocator.000webhostapp.com/medlocator/api/search/medinfo.php?search=";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        search = root.findViewById(R.id.search);

        search.addTextChangedListener(watcher);
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());


        recyclerView.setLayoutManager(mLayoutManager);

        loadData();

        return root;
    }
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {



        }

        @Override
        public void afterTextChanged(Editable editable) {
            filter(editable.toString());

        }
    };

    private void filter(String text){
        ArrayList<MedicineItem> filteredList = new ArrayList<>();
        for(MedicineItem item : medicineList){
            if(item.getGenericName().toLowerCase().concat(item.getBrandName().toLowerCase().concat(item.getMedIndication().toLowerCase().concat(item.getPharmacyName().toLowerCase()))).contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        mAdapter.filterList(filteredList);
    }


    private void loadData(){

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.GET, URL, response -> {

            try {
                JSONObject object = new JSONObject(response);
                JSONArray jsonArray = object.getJSONArray("data");
                medicineList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String pharmacyName = jsonObject.getString("pharmacyName");
                    String pharmacyId = jsonObject.getString("pharmacyId");
                    String genericName = jsonObject.getString("genericName");
                    String brandName = jsonObject.getString("brandName");
                    String medIndication = jsonObject.getString("medIndication");

                    MedicineItem model = new MedicineItem(pharmacyName, pharmacyId, genericName, brandName, medIndication);
                    medicineList.add(model);
                }

                mAdapter = new Adapter(medicineList);
                recyclerView.setAdapter(mAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            Toast.makeText(getContext(), "An error occured while connecting to the server", Toast.LENGTH_SHORT).show();
        });
        queue.add(request);
    }


}
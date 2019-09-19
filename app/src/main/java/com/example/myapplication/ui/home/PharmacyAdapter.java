package com.example.myapplication.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class PharmacyAdapter extends ArrayAdapter<PharmacyModel> {

    private List<PharmacyModel> pharmacyListFull;

    public PharmacyAdapter(@NonNull Context context, @NonNull List<PharmacyModel> pharmacyList) {
        super(context, 0, pharmacyList);
        pharmacyListFull = new ArrayList<>(pharmacyList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return pharmacyFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(null == convertView){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pharmacy_search_layout, parent, false);
        }

        TextView genericName, brandName, pharmacyName;
        ImageView imageView;

        imageView = convertView.findViewById(R.id.image);
        genericName = convertView.findViewById(R.id.genericName);
        brandName = convertView.findViewById(R.id.brandName);
        pharmacyName = convertView.findViewById(R.id.pharmacyName);

        PharmacyModel pharmacy = getItem(position);

        if(null != pharmacy){
            genericName.setText(pharmacy.getGenericName());
            brandName.setText(pharmacy.getBrandName());
            pharmacyName.setText(pharmacy.getPharmacyName());
        }

        return convertView;
    }

    private Filter pharmacyFilter  = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<PharmacyModel> suggestions = new ArrayList<>();

            if(null == constraint || 0 == constraint.length()){
                suggestions.addAll(pharmacyListFull);
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(PharmacyModel item : pharmacyListFull){
                    if(item.getBrandName().toLowerCase().concat(item.getGenericName().toLowerCase()).concat(item.getPharmacyName().toLowerCase()).concat(item.getLatitude().toLowerCase()).concat(item.getLongitude().toLowerCase()).contains(filterPattern)){
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((PharmacyModel) resultValue).getGenericName();
        }
    };
}

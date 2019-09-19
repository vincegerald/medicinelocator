package com.example.myapplication.ui.medicine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<MedicineItem> mItemsList;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextView1, mTextView2, mTextView3, mTextView4;
        public CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.title);
            mTextView2 = itemView.findViewById(R.id.description);
            mTextView3 = itemView.findViewById(R.id.desc);
            mTextView4 = itemView.findViewById(R.id.pharmacyName);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    public Adapter(ArrayList<MedicineItem> items){
        mItemsList = items;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MedicineItem currentItem = mItemsList.get(position);

        holder.mTextView1.setText(currentItem.getGenericName());
        holder.mTextView2.setText(currentItem.getBrandName());
        holder.mTextView3.setText(currentItem.getMedIndication());
        holder.mTextView4.setText(currentItem.getPharmacyName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mItemsList.size();
    }

    public void filterList(ArrayList<MedicineItem> filteredList){
        mItemsList = filteredList;
        notifyDataSetChanged();
    }
}

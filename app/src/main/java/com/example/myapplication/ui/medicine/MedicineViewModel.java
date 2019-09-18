package com.example.myapplication.ui.medicine;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MedicineViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MedicineViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}
package com.javierproyect.pistio.ui.home;

import android.widget.Button;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.javierproyect.pistio.R;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Crear Usuarios");


    }

    public LiveData<String> getText() {
        return mText;
    }



}
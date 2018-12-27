package com.sarvarcorp.fitnestraning.models;


import com.sarvarcorp.fitnestraning.App;
import com.sarvarcorp.fitnestraning.base.BaseViewModel;
import com.sarvarcorp.fitnestraning.entities.Mobileapp;
import com.sarvarcorp.fitnestraning.entities.UniversalItem;
import com.sarvarcorp.fitnestraning.repositories.MobileappRepository;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;

public class MobileappViewModel extends BaseViewModel {
    private LiveData<Mobileapp> mobileapp;
    private MobileappRepository mobileappRepository;

    @Inject
    public MobileappViewModel() {
        this.mobileappRepository = App.getComponent().provideMobileappRepository();
    }

    public void init() {
        if (this.mobileapp != null) {
            return;
        }
        mobileapp = mobileappRepository.get();
    }


    public LiveData<Mobileapp> get() {
        return mobileapp;
    }
}


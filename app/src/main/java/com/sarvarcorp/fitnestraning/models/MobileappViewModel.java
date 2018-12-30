package com.sarvarcorp.fitnestraning.models;


import com.sarvarcorp.fitnestraning.App;
import com.sarvarcorp.fitnestraning.base.BaseViewModel;
import com.sarvarcorp.fitnestraning.daos.MobileappConfigDao;
import com.sarvarcorp.fitnestraning.entities.Mobileapp;
import com.sarvarcorp.fitnestraning.entities.MobileappConfig;
import com.sarvarcorp.fitnestraning.entities.UniversalItem;
import com.sarvarcorp.fitnestraning.repositories.MobileappRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;

public class MobileappViewModel extends BaseViewModel {
    private LiveData<Mobileapp> mobileapp;
    private LiveData<List<MobileappConfig>> configs;
    private MobileappRepository mobileappRepository;
    private MobileappConfigDao mobileappConfigDao;

    @Inject
    public MobileappViewModel() {
        this.mobileappRepository = App.getComponent().provideMobileappRepository();
        this.mobileappConfigDao = App.getComponent().provideAppDatabase().mobileappConfigDao();
    }

    public void init() {
        if (this.mobileapp != null) {
            return;
        }
        mobileapp = mobileappRepository.get();
        configs = mobileappConfigDao.getAll();
    }


    public LiveData<Mobileapp> get() {
        return mobileapp;
    }

    public LiveData<List<MobileappConfig>> getConfigs() {
        return configs;
    }
}


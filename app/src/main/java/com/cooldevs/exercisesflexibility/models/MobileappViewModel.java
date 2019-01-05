package com.cooldevs.exercisesflexibility.models;


import com.cooldevs.exercisesflexibility.App;
import com.cooldevs.exercisesflexibility.base.BaseViewModel;
import com.cooldevs.exercisesflexibility.daos.MobileappConfigDao;
import com.cooldevs.exercisesflexibility.entities.Mobileapp;
import com.cooldevs.exercisesflexibility.entities.MobileappConfig;
import com.cooldevs.exercisesflexibility.repositories.MobileappRepository;

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


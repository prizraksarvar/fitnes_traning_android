package com.sarvarcorp.fitnestraning.models;


import com.sarvarcorp.fitnestraning.App;
import com.sarvarcorp.fitnestraning.base.BaseViewModel;
import com.sarvarcorp.fitnestraning.entities.UniversalItem;
import com.sarvarcorp.fitnestraning.repositories.UniversalItemRepository;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;

public class UniversalItemViewModel extends BaseViewModel {
    private String userToken;
    private LiveData<UniversalItem> universalItem;
    private UniversalItemRepository universalItemRepository;

    @Inject
    public UniversalItemViewModel() {
        this.universalItemRepository = App.getComponent().provideUniversalItemRepository();
    }

    public void init(int id) {
        if (this.universalItem != null) {
            return;
        }
        universalItem = universalItemRepository.get(id);
    }


    public LiveData<UniversalItem> get() {
        return universalItem;
    }
}


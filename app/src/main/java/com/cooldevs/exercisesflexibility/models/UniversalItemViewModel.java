package com.cooldevs.exercisesflexibility.models;


import com.cooldevs.exercisesflexibility.App;
import com.cooldevs.exercisesflexibility.base.BaseViewModel;
import com.cooldevs.exercisesflexibility.entities.UniversalItem;
import com.cooldevs.exercisesflexibility.repositories.UniversalItemRepository;

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


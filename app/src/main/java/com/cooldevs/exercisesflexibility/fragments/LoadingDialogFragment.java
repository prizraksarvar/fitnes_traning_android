package com.cooldevs.exercisesflexibility.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cooldevs.exercisesflexibility.R;
import com.cooldevs.exercisesflexibility.base.BaseDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class LoadingDialogFragment extends BaseDialogFragment {

    public LoadingDialogFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_loading;
    }
}

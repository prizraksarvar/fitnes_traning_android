package com.cooldevs.exercisesflexibility.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cooldevs.exercisesflexibility.App;
import com.cooldevs.exercisesflexibility.R;
import com.cooldevs.exercisesflexibility.base.BaseFragment;
import com.cooldevs.exercisesflexibility.workers.AdRewardedWorker;

import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;


public class ExtraButtonFragment extends BaseFragment {
    protected TextView rateAppText;
    protected TextView bonusText;
    protected TextView otherAppText;
    protected ConstraintLayout rateAppButton;
    protected ConstraintLayout bonusButton;
    protected ConstraintLayout otherAppButton;

    protected boolean isProccess = false;


    @Override
    protected int getLayoutID() {
        return R.layout.fragment_extra_buttons;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        setViews(view);
        setViewsLocales();
        setViewsEvents();
        return view;
    }

    protected void setViews(View view) {
        rateAppText = view.findViewById(R.id.extraRateAppTextView);
        bonusText = view.findViewById(R.id.extraBonusTextView);
        otherAppText = view.findViewById(R.id.extraOtherAppTextView);
        rateAppButton = view.findViewById(R.id.extraRateAppButton);
        bonusButton = view.findViewById(R.id.extraBonusButton);
        otherAppButton = view.findViewById(R.id.extraOtherAppButton);
    }

    protected void setViewsLocales() {
        rateAppText.setText(App.getComponent().provideStaticData().getMobileappConfigsLocaleValue("loc-rate-app"));
        bonusText.setText(App.getComponent().provideStaticData().getMobileappConfigsLocaleValue("loc-bonus"));
        otherAppText.setText(App.getComponent().provideStaticData().getMobileappConfigsLocaleValue("loc- other-apps"));
    }

    protected void setViewsEvents() {
        rateAppButton.setOnClickListener(v -> showRateDialog());
        bonusButton.setOnClickListener(v -> bonus());
        otherAppButton.setOnClickListener(v -> otherApps());
    }

    protected void setProccessStarted() throws InterruptedException {
        if (isProccess)
            throw new InterruptedException();
        isProccess = true;
        new Handler().postDelayed(()->{isProccess=false;},400);
    }

    protected void bonus() {
        try {
            setProccessStarted();
        } catch (InterruptedException e) {
            return;
        }
        AdRewardedWorker.getInstance().show();
    }

    protected void otherApps() {
        try {
            setProccessStarted();
        } catch (InterruptedException e) {
            return;
        }
        //getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://dev?id=" + getString(R.string.appDeveloperID))));
        try {
            getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=" + getString(R.string.appDeveloperID))));
        } catch (Exception exception) {
            Toast.makeText(getContext(),"Play market not found",Toast.LENGTH_SHORT).show();
            exception.printStackTrace();
        }
    }


    public void showRateDialog() {
        try {
            setProccessStarted();
        } catch (InterruptedException e) {
            return;
        }
        final RateAppDialogFragment dialog = new RateAppDialogFragment();
        if (!App.getComponent().provideStaticData().getFragmentManager().isStateSaved())
            dialog.show(App.getComponent().provideStaticData().getFragmentManager(),dialog.getClass().getName());
    }
}

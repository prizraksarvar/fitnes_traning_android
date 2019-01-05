package com.sarvarcorp.fitnestraning.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sarvarcorp.fitnestraning.App;
import com.sarvarcorp.fitnestraning.R;
import com.sarvarcorp.fitnestraning.base.BaseFragment;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;


public class ExtraButtonFragment extends BaseFragment {
    protected TextView rateAppText;
    protected TextView bonusText;
    protected TextView otherAppText;
    protected ConstraintLayout rateAppButton;
    protected ConstraintLayout bonusButton;
    protected ConstraintLayout otherAppButton;
    protected int currentCode = 0;


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
        rateAppButton.setOnClickListener(v -> rateApp());
        bonusButton.setOnClickListener(v -> bonus());
        otherAppButton.setOnClickListener(v -> otherApps());
    }

    protected void rateApp() {
        showRateDialog();
    }

    protected void bonus() {
        App.getAdRewardWorker().show(String.format(Locale.ENGLISH,"%d",currentCode));
    }

    protected void otherApps() {
        //getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://dev?id=" + getString(R.string.appDeveloperID))));
        getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=" + getString(R.string.appDeveloperID))));
    }

    public void showRateDialog() {
        final RateAppDialogFragment dialog = new RateAppDialogFragment();
        dialog.show(App.getComponent().provideStaticData().getFragmentManager(),dialog.getClass().getName());
    }
}

package com.sarvarcorp.fitnestraning.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sarvarcorp.fitnestraning.App;
import com.sarvarcorp.fitnestraning.R;
import com.sarvarcorp.fitnestraning.base.BaseDialogFragment;
import com.sarvarcorp.fitnestraning.base.BaseFragment;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class RateAppDialogFragment extends BaseDialogFragment {
    protected TextView titleTextView = null;
    protected TextView descriptionTextView = null;
    protected ImageView imageView = null;

    protected Button yesButton = null;
    protected Button noButton = null;

    @Override
    protected int getLayoutID() {
        return R.layout.ui_dialog_rate_app;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        App.getComponent().provideStaticData().setTimerFragment(null);
        super.onDestroy();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        initRateDialog(view);
        return view;
    }

    protected void initRateDialog(View rootView) {
        titleTextView = rootView.findViewById(R.id.rateTitleTextView);
        descriptionTextView = rootView.findViewById(R.id.rateDescriptionTextView);
        imageView = rootView.findViewById(R.id.rateImageView);
        yesButton = rootView.findViewById(R.id.rateYesButton);
        noButton = rootView.findViewById(R.id.rateNoButton);

        yesButton.setOnClickListener(v -> showRateApp());
        noButton.setOnClickListener(v -> showMailToDeveloper());

        titleTextView.setText(App.getComponent().provideStaticData().getMobileappConfigsLocaleValue("loc-rate-app-title"));
        descriptionTextView.setText(App.getComponent().provideStaticData().getMobileappConfigsLocaleValue("loc-rate-app-description"));
        yesButton.setText(App.getComponent().provideStaticData().getMobileappConfigsLocaleValue("loc-rate-app-yes"));
        noButton.setText(App.getComponent().provideStaticData().getMobileappConfigsLocaleValue("loc-rate-app-no"));
    }

    protected void showRateApp() {
        getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getString(R.string.appBundleID))));
        dismiss();
    }

    public void showMailToDeveloper() {
        getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mailto://" + getString(R.string.developerEmail))));
        dismiss();
    }
}

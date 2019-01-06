package com.cooldevs.exercisesflexibility.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cooldevs.exercisesflexibility.App;
import com.cooldevs.exercisesflexibility.R;
import com.cooldevs.exercisesflexibility.base.BaseDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;


public class RateAppDialogFragment extends BaseDialogFragment implements Observer<String> {
    protected TextView titleTextView = null;
    protected TextView descriptionTextView = null;
    protected ImageView imageView = null;
    protected LiveData<String> imageLiveData = null;

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
        imageLiveData = App.getComponent().provideAppDatabase().universalItemDao().getFirstImage();
        imageLiveData.observe(this,this);
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
        getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + getString(R.string.developerEmail))));
        dismiss();
    }

    @Override
    public void onChanged(String image) {
        if (image==null || image.equals("")) {
            return;
        }
        Glide.with(this.getContext())
                .load(image)
                .into(imageView);
    }
}

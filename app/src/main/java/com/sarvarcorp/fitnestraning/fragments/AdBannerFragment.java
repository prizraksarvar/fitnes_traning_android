package com.sarvarcorp.fitnestraning.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.sarvarcorp.fitnestraning.R;
import com.sarvarcorp.fitnestraning.base.BaseFragment;


public class AdBannerFragment extends BaseFragment {
    private AdView mAdView = null;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_ad_banner;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        initBunnerAds(view);
        return view;
    }

    protected void initBunnerAds(View rootView) {
        MobileAds.initialize(this.getActivity(), getString(R.string.adsApplicationID));
        AdView adView = new AdView(this.getActivity());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getString(R.string.adsInterstitialID));
        mAdView = rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}

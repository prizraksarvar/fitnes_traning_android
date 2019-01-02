package com.sarvarcorp.fitnestraning;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.sarvarcorp.fitnestraning.base.BaseAppCompatActivity;
import com.sarvarcorp.fitnestraning.entities.Mobileapp;
import com.sarvarcorp.fitnestraning.entities.MobileappConfig;
import com.sarvarcorp.fitnestraning.entities.UniversalItem;
import com.sarvarcorp.fitnestraning.fragments.LoadingFragment;
import com.sarvarcorp.fitnestraning.fragments.UniversalItemsFragment;
import com.sarvarcorp.fitnestraning.models.MobileappViewModel;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends BaseAppCompatActivity implements Observer<Mobileapp> {
    private String activityState = "";
    private Toolbar toolbar;
    private AdView mAdView;
    private MobileappViewModel viewModel;
    private LiveData<Mobileapp> mobileappLiveData;
    private LiveData<List<MobileappConfig>> mobileappConfigsLiveData;
    private MobileappConfigsObserver mobileappConfigsObserver;

    private boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.getComponent().provideStaticData().setFragmentManager(getSupportFragmentManager());
        App.getComponent().provideStaticData().setMainActivity(this);
        App.getComponent().provideStaticData().setAppApiKey(getResources().getString(R.string.app_backend_api_key));
        App.getComponent().provideStaticData().setMobileappId(getResources().getInteger(R.integer.app_backend_app_id));

        loading = true;
        toolbar = this.findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        showLoadingFragment();
        toolbar.setVisibility(View.GONE);

        viewModel = ViewModelProviders.of(this).get(MobileappViewModel.class);
        viewModel.init();
        mobileappLiveData = viewModel.get();

        mobileappLiveData.observe(this,this);
        mobileappConfigsObserver = new MobileappConfigsObserver();
        mobileappConfigsLiveData = viewModel.getConfigs();
        mobileappConfigsLiveData.observe(this,mobileappConfigsObserver);
    }

    protected void showLoadingFragment() {
        App.getComponent().provideFragmentWorker().showFragment(LoadingFragment.class, false);
    }

    protected void setViewsLocales() {
        TextView rateAppText = findViewById(R.id.extraRateAppTextView);
        TextView bonusText = findViewById(R.id.extraBonusTextView);
        TextView otherAppText = findViewById(R.id.extraOtherAppTextView);
        rateAppText.setText(App.getComponent().provideStaticData().getMobileappConfigsLocaleValue("loc-rate-app"));
        bonusText.setText(App.getComponent().provideStaticData().getMobileappConfigsLocaleValue("loc-bonus"));
        otherAppText.setText(App.getComponent().provideStaticData().getMobileappConfigsLocaleValue("loc- other-apps"));
    }

    protected void showStartFragment(Mobileapp mobileapp) {
        try {
            setViewsLocales();
            //toolbar.setVisibility(View.VISIBLE);
            UniversalItem universalItem = new UniversalItem();
            universalItem.id = 0;
            universalItem.name = App.getComponent().provideStaticData().getMobileappConfigsLocaleValue("loc-select-training");
            universalItem.viewType = mobileapp.viewType;
            universalItem.backgroundColor = mobileapp.backgroundColor;
            universalItem.description = mobileapp.description;
            universalItem.image = mobileapp.image;
            UniversalItemsFragment fragment = null;
            fragment = (UniversalItemsFragment) App.getComponent().provideFragmentWorker().getFragment(UniversalItemsFragment.class);
            fragment.setCurrentItem(universalItem);
            App.getComponent().provideFragmentWorker().showFragment(UniversalItemsFragment.class,false, fragment, null);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        App.getComponent().provideFragmentWorker().onActivityDestroy();

        super.onDestroy();
        System.exit(0);
    }

    @Override
    public void onChanged(Mobileapp mobileapp) {
        if (mobileapp == null || mobileapp.id == 0) {
            return;
        }
        App.getComponent().provideStaticData().setMobileapp(mobileapp);
        if (loading) {
            loading=false;
            showStartFragment(mobileapp);
        } else {
            onMobileappUpdate();
        }
    }

    protected void onMobileappUpdate() {
        //TODO: need implement live theme update, I think need broadcast event
    }

    public void showToolbar() {
        toolbar.setVisibility(View.VISIBLE);
    }

    public void hideToolbar() {
        toolbar.setVisibility(View.GONE);
    }

    public void showToolbarBackButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void hideToolbarBackButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    public void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    protected class MobileappConfigsObserver implements Observer<List<MobileappConfig>> {

        @Override
        public void onChanged(List<MobileappConfig> mobileappConfigs) {
            if (mobileappConfigs == null) {
                return;
            }
            App.getComponent().provideStaticData().setMobileappConfigs(mobileappConfigs);
        }
    }
}

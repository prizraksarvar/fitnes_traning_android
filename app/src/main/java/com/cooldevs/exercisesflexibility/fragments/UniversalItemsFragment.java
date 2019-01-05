package com.cooldevs.exercisesflexibility.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cooldevs.exercisesflexibility.App;
import com.cooldevs.exercisesflexibility.base.BaseFragment;
import com.cooldevs.exercisesflexibility.entities.UniversalItem;
import com.cooldevs.exercisesflexibility.fragments.universalitem.BaseUniversalItemFragment;
import com.cooldevs.exercisesflexibility.fragments.universalitem.Card3UniversalItemFragment;
import com.cooldevs.exercisesflexibility.fragments.universalitem.CardUniversalItemFragment;
import com.cooldevs.exercisesflexibility.fragments.universalitem.ImageListUniversalItemFragment;
import com.cooldevs.exercisesflexibility.fragments.universalitem.TitleImageListUniversalItemFragment;
import com.cooldevs.exercisesflexibility.fragments.universalitem.TitleListUniversalItemFragment;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;


public class UniversalItemsFragment extends BaseFragment {
    protected UniversalItem currentItem;
    protected BaseUniversalItemFragment currentBackendFragment;

    public UniversalItemsFragment() {

    }

    @Override
    protected int getLayoutID() {
        return currentBackendFragment.getLayoutID();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentBackendFragment.onCreate();
    }

    public UniversalItem getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(UniversalItem currentItem) {
        this.currentItem = currentItem;
        initCurrentBackendFragment();
    }

    protected void initCurrentBackendFragment() {
        String viewType = "";
        if (currentItem != null) {
            viewType = currentItem.viewType;
        }
        boolean isList = false;
        switch (viewType) {
            case "list_with_image": currentBackendFragment = new ImageListUniversalItemFragment(); isList = true; break;
            case "list_with_image_title": currentBackendFragment = new TitleImageListUniversalItemFragment(); isList = true; break;
            case "cart": currentBackendFragment = new CardUniversalItemFragment(); break;
            case "cart_3": currentBackendFragment = new Card3UniversalItemFragment(); break;
            default: currentBackendFragment = new TitleListUniversalItemFragment();
        }
        currentBackendFragment.setCurrentItem(currentItem);
        currentBackendFragment.setFragment(this);
        App.getAdRewardWorker().setCurrentCode(String.format(Locale.ENGLISH,"%d",isList?currentItem.id:currentItem.parentId));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        currentBackendFragment.onCreateView(view);
        return view;
    }

    @Override
    public void prepareEnterAnimation(FragmentTransaction fragmentTranaction, BaseFragment oldFragment, View view) {
        currentBackendFragment.prepareEnterAnimation(fragmentTranaction,oldFragment,view);
    }
}

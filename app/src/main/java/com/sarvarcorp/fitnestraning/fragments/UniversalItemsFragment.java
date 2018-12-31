package com.sarvarcorp.fitnestraning.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sarvarcorp.fitnestraning.App;
import com.sarvarcorp.fitnestraning.R;
import com.sarvarcorp.fitnestraning.base.BaseFragment;
import com.sarvarcorp.fitnestraning.design.animation.ButtonToFragmentTransition;
import com.sarvarcorp.fitnestraning.entities.Mobileapp;
import com.sarvarcorp.fitnestraning.entities.UniversalItem;
import com.sarvarcorp.fitnestraning.fragments.universalitem.BaseUniversalItemFragment;
import com.sarvarcorp.fitnestraning.fragments.universalitem.Card3UniversalItemFragment;
import com.sarvarcorp.fitnestraning.fragments.universalitem.CardUniversalItemFragment;
import com.sarvarcorp.fitnestraning.fragments.universalitem.ImageListUniversalItemFragment;
import com.sarvarcorp.fitnestraning.fragments.universalitem.ListUniversalItemFragment;
import com.sarvarcorp.fitnestraning.fragments.universalitem.TitleImageListUniversalItemFragment;
import com.sarvarcorp.fitnestraning.fragments.universalitem.TitleListUniversalItemFragment;
import com.sarvarcorp.fitnestraning.listadapter.UniversalItemRecyclerViewAdapter;
import com.sarvarcorp.fitnestraning.models.UniversalItemsViewModel;

import java.lang.reflect.InvocationTargetException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


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
        switch (viewType) {
            case "list_with_image": currentBackendFragment = new ImageListUniversalItemFragment(); break;
            case "list_with_image_title": currentBackendFragment = new TitleImageListUniversalItemFragment(); break;
            case "cart": currentBackendFragment = new CardUniversalItemFragment(); break;
            case "cart_3": currentBackendFragment = new Card3UniversalItemFragment(); break;
            default: currentBackendFragment = new TitleListUniversalItemFragment();
        }
        currentBackendFragment.setCurrentItem(currentItem);
        currentBackendFragment.setFragment(this);
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

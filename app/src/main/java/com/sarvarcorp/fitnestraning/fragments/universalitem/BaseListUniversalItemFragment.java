package com.sarvarcorp.fitnestraning.fragments.universalitem;

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
import com.sarvarcorp.fitnestraning.fragments.UniversalItemsFragment;
import com.sarvarcorp.fitnestraning.listadapter.UniversalItemRecyclerViewAdapter;
import com.sarvarcorp.fitnestraning.models.UniversalItemsViewModel;

import java.lang.reflect.InvocationTargetException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseListUniversalItemFragment extends BaseUniversalItemFragment implements UniversalItemRecyclerViewAdapter.UniversalItemListListener {
    protected RecyclerView mItemsListView;
    protected UniversalItemRecyclerViewAdapter mRecyclerViewAdepter;

    public abstract int getLayoutID();

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        initRecyclerView(view);
    }

    protected void setBackgroundColor() {
        if (currentItem.backgroundColor != null && !currentItem.backgroundColor.equals("")) {
            int color = Color.parseColor(currentItem.backgroundColor);
            color = Color.argb(Color.alpha(color),Color.red(color),Color.green(color),Color.blue(color));
            layout.setBackgroundColor(color);
        }
    }

    protected void initRecyclerView(View view) {
        mItemsListView = view.findViewById(R.id.universalItemsRecyclerView);
        mRecyclerViewAdepter = new UniversalItemRecyclerViewAdapter(this,fragment);
        mItemsListView.setLayoutManager(new LinearLayoutManager(fragment.getContext()));
        //mItemsListView.setLayoutManager(new GridLayoutManager(this.getContext(),2));
        mItemsListView.setAdapter(mRecyclerViewAdepter);
        mRecyclerViewAdepter.setValues(viewModel.getList());
    }

    @Override
    public void onClickListItem(View view, UniversalItem universalItem) {
        if (universalItem != null && universalItem.id > 0) {
            try {
                UniversalItemsFragment fragment = (UniversalItemsFragment) App.getComponent().provideFragmentWorker().getFragment(UniversalItemsFragment.class);
                fragment.setCurrentItem(universalItem);
                App.getComponent().provideFragmentWorker().showFragment(UniversalItemsFragment.class, true, fragment, view);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | Fragment.InstantiationException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}

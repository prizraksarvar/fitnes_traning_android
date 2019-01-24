package com.cooldevs.exercisesflexibility.fragments.universalitem;

import android.graphics.Color;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cooldevs.exercisesflexibility.App;
import com.cooldevs.exercisesflexibility.R;
import com.cooldevs.exercisesflexibility.base.BaseFragment;
import com.cooldevs.exercisesflexibility.design.animation.ButtonToFragmentTransition;
import com.cooldevs.exercisesflexibility.entities.Mobileapp;
import com.cooldevs.exercisesflexibility.entities.UniversalItem;
import com.cooldevs.exercisesflexibility.fragments.UniversalItemsFragment;
import com.cooldevs.exercisesflexibility.models.UniversalItemsViewModel;

import java.lang.reflect.InvocationTargetException;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public abstract class BaseUniversalItemFragment {
    protected UniversalItemsViewModel viewModel;
    protected UniversalItem currentItem;
    protected UniversalItemsFragment fragment;

    protected ViewGroup layout;

    public abstract int getLayoutID();

    public void setFragment(UniversalItemsFragment fragment) {
        this.fragment = fragment;
    }

    public void onCreate() {
        viewModel = ViewModelProviders.of(fragment).get(UniversalItemsViewModel.class);
        viewModel.init(currentItem != null ? currentItem.id : 0);
        Log.d("app/test","BaseUniversalItemFragment:onCreate = " + (currentItem != null ? currentItem.id : 0));
    }

    public UniversalItem getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(UniversalItem currentItem) {
        this.currentItem = currentItem;
    }

    public void onCreateView(View view) {
        initViews(view);

        if (currentItem !=null) {
            setSharedTransitions();
            setBackgroundColor();
        }
    }

    protected void setSharedTransitions() {
        ViewCompat.setTransitionName(layout, getLayoutSharedName());
    }

    protected void initViews(View view) {
        layout = view.findViewById(R.id.universalItemsFragmentLayout);
    }

    protected void setImageToView(ImageView imageView, String image) {
        if (image.equals("") || imageView == null)
            return;

        Glide.with(fragment.getContext())
                .load(image)
                .into(imageView);
    }

    protected void setBackgroundColor() {
        if (currentItem.backgroundColor != null && !currentItem.backgroundColor.equals("")) {
            int color = Color.parseColor(currentItem.backgroundColor);
            color = Color.argb(Color.alpha(color),Color.red(color),Color.green(color),Color.blue(color));
            layout.setBackgroundColor(color);
        }
    }

    public void onClickListItem(View view, UniversalItem universalItem) {
        if (universalItem!=null && universalItem.id>0) {
            try {
                UniversalItemsFragment fragment = (UniversalItemsFragment) App.getComponent().provideFragmentWorker().getFragment(UniversalItemsFragment.class);
                fragment.setCurrentItem(universalItem);
                App.getComponent().provideFragmentWorker().showFragment(UniversalItemsFragment.class,true, fragment, view);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | Fragment.InstantiationException | java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    public void prepareEnterAnimation(FragmentTransaction fragmentTranaction, BaseFragment oldFragment, View view) {
        if (currentItem ==null || view==null)
            return;
        ConstraintLayout layout = view.findViewById(R.id.universalItemButton);
        fragmentTranaction.addSharedElement(layout,getLayoutSharedName());
        fragment.setSharedElementEnterTransition(new ButtonToFragmentTransition());
        fragment.setEnterTransition(new ChangeBounds());
        fragment.setExitTransition(new Fade());
        fragment.setSharedElementReturnTransition(new ButtonToFragmentTransition());
    }

    protected String getLayoutSharedName() {
        return "universalItemLayout"+ getSharedNameID();
    }

    protected Mobileapp getMobileapp() {
        return App.getComponent().provideStaticData().getMobileapp();
    }
    protected String getSharedNameID() {
        return "" + currentItem.id;
    }
}

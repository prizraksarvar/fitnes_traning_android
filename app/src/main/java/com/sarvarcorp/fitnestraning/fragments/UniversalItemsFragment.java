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

import com.sarvarcorp.fitnestraning.App;
import com.sarvarcorp.fitnestraning.R;
import com.sarvarcorp.fitnestraning.base.BaseFragment;
import com.sarvarcorp.fitnestraning.design.animation.ButtonToFragmentTransition;
import com.sarvarcorp.fitnestraning.entities.UniversalItem;
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


public class UniversalItemsFragment extends BaseFragment implements UniversalItemRecyclerViewAdapter.UniversalItemListListener {
    private UniversalItemsViewModel viewModel;
    private RecyclerView mItemsListView;
    private UniversalItemRecyclerViewAdapter mRecyclerViewAdepter;
    private UniversalItem currentItem;

    private TextView title;
    private ConstraintLayout layout;


    public UniversalItemsFragment() {

    }

    @Override
    protected int getLayoutID() {
        int layoutID = 0;
        String viewType = "";
        if (currentItem != null && currentItem.id != 0) {
            viewType = currentItem.viewType;
        } else {
            viewType = App.getComponent().provideStaticData().getMobileapp().viewType;
        }

        switch (viewType) {
            case "list_with_image": layoutID = R.layout.ui_fragment_image_title_list; break;
            case "list_with_image_title": layoutID = R.layout.ui_fragment_image_title_list; break;
            case "cart": layoutID = R.layout.ui_fragment_title_list; break;
            default: layoutID = R.layout.ui_fragment_title_list;
        }

        return layoutID;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(UniversalItemsViewModel.class);
        viewModel.init(currentItem != null ? currentItem.id : 0);
    }

    public UniversalItem getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(UniversalItem currentItem) {
        this.currentItem = currentItem;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initRecyclerView(view);

        layout = view.findViewById(R.id.universalItemsFragmentLayout);
        title = view.findViewById(R.id.universalItemTitleTextView);
        String titleString = "";
        if (currentItem !=null) {
            titleString = currentItem.name;
        }
        title.setText(titleString);

        if (currentItem !=null) {
            ViewCompat.setTransitionName(title, getTitleSharedName());
            ViewCompat.setTransitionName(layout, getLayoutSharedName());
            setBackgroundColor();
        }
        return view;
    }

    private void setBackgroundColor() {
        if (!currentItem.backgroundColor.equals("")) {
            int color = Color.parseColor(currentItem.backgroundColor);
            color = Color.argb(50,Color.red(color),Color.green(color),Color.blue(color));
            layout.setBackgroundColor(color);
        }
    }

    public void initRecyclerView(View view) {
        mItemsListView = view.findViewById(R.id.universalItemsRecyclerView);
        mRecyclerViewAdepter = new UniversalItemRecyclerViewAdapter(this,this);
        mItemsListView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        //mItemsListView.setLayoutManager(new GridLayoutManager(this.getContext(),2));
        mItemsListView.setAdapter(mRecyclerViewAdepter);
        mRecyclerViewAdepter.setValues(viewModel.getList());
    }

    @Override
    public void onClickListItem(View view, UniversalItem universalItem) {
        if (universalItem!=null && universalItem.id>0) {
            try {
                if (true) {
                    UniversalItemsFragment fragment = (UniversalItemsFragment) App.getComponent().provideFragmentWorker().getFragment(UniversalItemsFragment.class);
                    fragment.setCurrentItem(universalItem);
                    App.getComponent().provideFragmentWorker().showFragment(UniversalItemsFragment.class,true, fragment, view);
                } else {
                    UniversalItemFragment fragment = (UniversalItemFragment) App.getComponent().provideFragmentWorker().getFragment(UniversalItemFragment.class);
                    fragment.setUniversalItem(universalItem);
                    App.getComponent().provideFragmentWorker().showFragment(UniversalItemFragment.class,true, fragment, view);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void prepareEnterAnimation(FragmentTransaction fragmentTranaction, BaseFragment oldFragment, View view) {
        if (currentItem ==null)
            return;
        ConstraintLayout layout = view.findViewById(R.id.universalItemButton);
        TextView title = view.findViewById(R.id.universalItemTitleTextView);
        ImageView imageView = view.findViewById(R.id.universalItemImageView);
        fragmentTranaction.addSharedElement(title,getTitleSharedName());
        fragmentTranaction.addSharedElement(layout,getLayoutSharedName());
        if (imageView!=null && !false) {
            fragmentTranaction.addSharedElement(imageView,getImageSharedName());
        }
        setSharedElementEnterTransition(new ButtonToFragmentTransition());
        setEnterTransition(new ChangeBounds());
        setExitTransition(new Fade());
        setSharedElementReturnTransition(new ButtonToFragmentTransition());
    }

    private String getTitleSharedName() {
        return "universalItemTitle"+ currentItem.id;
    }

    private String getLayoutSharedName() {
        return "universalItemLayout"+ currentItem.id;
    }

    private String getImageSharedName() {
        return "universalItemImage"+ currentItem.id;
    }
}

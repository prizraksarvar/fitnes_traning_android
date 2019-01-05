package com.cooldevs.exercisesflexibility.fragments.universalitem;

import android.graphics.Color;
import android.view.View;

import com.cooldevs.exercisesflexibility.App;
import com.cooldevs.exercisesflexibility.R;
import com.cooldevs.exercisesflexibility.entities.UniversalItem;
import com.cooldevs.exercisesflexibility.fragments.UniversalItemsFragment;
import com.cooldevs.exercisesflexibility.listadapter.UniversalItemRecyclerViewAdapter;

import java.lang.reflect.InvocationTargetException;

import androidx.fragment.app.Fragment;
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

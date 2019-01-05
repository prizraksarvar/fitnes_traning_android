package com.cooldevs.exercisesflexibility.listadapter.universalitem;

import android.view.View;
import android.widget.TextView;

import com.cooldevs.exercisesflexibility.R;
import com.cooldevs.exercisesflexibility.base.BaseFragment;
import com.cooldevs.exercisesflexibility.listadapter.UniversalItemRecyclerViewAdapter;

import androidx.core.view.ViewCompat;

public class DrawableButtonListViewHolder extends BaseListViewHolder {
    protected final TextView titleView;

    public DrawableButtonListViewHolder(View itemView, BaseFragment fragment, final UniversalItemRecyclerViewAdapter.UniversalItemListListener listener) {
        super(itemView, fragment, listener);
        titleView = itemView.findViewById(R.id.universalItemTitleTextView);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + universalItem.id + "'";
    }

    @Override
    public void update() {
        super.update();
        titleView.setText(universalItem.name);
        ViewCompat.setTransitionName(titleView,"universalItemTitle"+ universalItem.id);
    }

    @Override
    protected void setBackgroundColor() {

    }
}

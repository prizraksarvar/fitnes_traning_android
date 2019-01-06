package com.cooldevs.exercisesflexibility.listadapter.universalitem;

import android.view.View;
import android.widget.ImageView;

import com.cooldevs.exercisesflexibility.App;
import com.cooldevs.exercisesflexibility.R;
import com.cooldevs.exercisesflexibility.base.BaseFragment;
import com.cooldevs.exercisesflexibility.entities.UniversalItem;
import com.cooldevs.exercisesflexibility.entities.UniversalItemExtraColumn;
import com.cooldevs.exercisesflexibility.listadapter.UniversalItemRecyclerViewAdapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public class DrawableButtonImageListViewHolder extends DrawableButtonListViewHolder implements Observer<UniversalItemExtraColumn> {
    protected ImageView statusImageView;
    protected LiveData<UniversalItemExtraColumn> universalItemExtraColumn;

    public DrawableButtonImageListViewHolder(View itemView, BaseFragment fragment, final UniversalItemRecyclerViewAdapter.UniversalItemListListener listener) {
        super(itemView, fragment, listener);
        statusImageView = itemView.findViewById(R.id.uiItemStatusImage);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + universalItem.id + "'";
    }

    @Override
    public void update() {
        super.update();
        setStatusImage();
    }

    protected void setStatusImage() {
        //setImageToView(statusImageView,universalItem.smallImage,"universalItemStatusImage");
    }

    @Override
    public void setUniversalItem(UniversalItem universalItem) {
        super.setUniversalItem(universalItem);
        universalItemExtraColumn = App.getComponent().provideAppDatabase().universalItemExtraColumnDao().get(universalItem.id);
        universalItemExtraColumn.observe(fragment,this);
    }

    @Override
    public void onChanged(UniversalItemExtraColumn universalItemExtraColumn) {
        int v = View.GONE;
        if (universalItemExtraColumn!=null && universalItemExtraColumn.isShown) {
            v = View.VISIBLE;
        }
        statusImageView.setVisibility(v);
    }
}

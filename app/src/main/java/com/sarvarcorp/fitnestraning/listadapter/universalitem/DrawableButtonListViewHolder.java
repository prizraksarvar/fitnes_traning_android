package com.sarvarcorp.fitnestraning.listadapter.universalitem;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sarvarcorp.fitnestraning.App;
import com.sarvarcorp.fitnestraning.R;
import com.sarvarcorp.fitnestraning.base.BaseFragment;
import com.sarvarcorp.fitnestraning.entities.UniversalItem;
import com.sarvarcorp.fitnestraning.listadapter.UniversalItemRecyclerViewAdapter;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

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

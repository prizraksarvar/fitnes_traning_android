package com.sarvarcorp.fitnestraning.listadapter.universalitem;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sarvarcorp.fitnestraning.App;
import com.sarvarcorp.fitnestraning.R;
import com.sarvarcorp.fitnestraning.entities.UniversalItem;
import com.sarvarcorp.fitnestraning.listadapter.UniversalItemRecyclerViewAdapter;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseListViewHolder extends RecyclerView.ViewHolder {
    protected final View view;
    protected final ViewGroup containerView;
    protected UniversalItem universalItem;

    public BaseListViewHolder(View itemView, final UniversalItemRecyclerViewAdapter.UniversalItemListListener listener) {
        super(itemView);
        view = itemView;
        containerView = itemView.findViewById(R.id.universalItemButton);

        containerView.setBackgroundColor(Color.parseColor("#5a595b"));

        containerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickListItem(itemView, universalItem);
            }
        });
    }

    @Override
    public String toString() {
        return super.toString() + " '" + universalItem.id + "'";
    }

    public void update() {
        ViewCompat.setTransitionName(containerView,"universalItemLayout"+ universalItem.id);

        setBackgroundColor();
    }

    protected void setBackgroundColor() {
        if (!universalItem.backgroundColor.equals("")) {
            containerView.setBackgroundColor(Color.parseColor(universalItem.backgroundColor));
        }
    }

    public void setUniversalItem(UniversalItem universalItem) {
        this.universalItem = universalItem;
        update();
    }
}

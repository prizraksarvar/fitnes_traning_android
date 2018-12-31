package com.sarvarcorp.fitnestraning.listadapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sarvarcorp.fitnestraning.App;
import com.sarvarcorp.fitnestraning.R;
import com.sarvarcorp.fitnestraning.base.BaseFragment;
import com.sarvarcorp.fitnestraning.entities.UniversalItem;
import com.sarvarcorp.fitnestraning.listadapter.universalitem.BaseListViewHolder;
import com.sarvarcorp.fitnestraning.listadapter.universalitem.DrawableButtonImageListViewHolder;
import com.sarvarcorp.fitnestraning.listadapter.universalitem.DrawableButtonListViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

public class UniversalItemRecyclerViewAdapter
        extends RecyclerView.Adapter<BaseListViewHolder>
        implements Observer<List<UniversalItem>> {
    private LiveData<List<UniversalItem>> mValues;

    private UniversalItemListListener listener;
    private BaseFragment fragment;

    public UniversalItemRecyclerViewAdapter(UniversalItemListListener listener, BaseFragment fragment) {
        this.listener = listener;
        this.fragment = fragment;
    }

    @Override
    public int getItemViewType(int position) {
        String viewType = mValues.getValue().get(position).listViewType;
        switch (viewType) {
            case "button": return R.layout.list_item_universal_button;
            case "small_button": return R.layout.list_item_universal_small_button;
            case "small_button_with_image": return R.layout.list_item_universal_small_button_with_image;
            case "drawable_button": return R.layout.ui_view_item_drawable_button;
            case "drawable_button_image": return R.layout.ui_view_item_drawable_button_image;
            default: return R.layout.list_item_universal_with_image;
        }
    }

    @NonNull
    @Override
    public BaseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        switch (viewType) {
            case  R.layout.list_item_universal_button: return new DrawableButtonListViewHolder(view, fragment, listener);//TODO: need implement
            case  R.layout.list_item_universal_small_button: return new DrawableButtonListViewHolder(view, fragment, listener);//TODO: need implement
            case  R.layout.list_item_universal_small_button_with_image: return new DrawableButtonListViewHolder(view, fragment, listener);//TODO: need implement
            case  R.layout.ui_view_item_drawable_button: return new DrawableButtonListViewHolder(view, fragment, listener);
            case  R.layout.ui_view_item_drawable_button_image: return new DrawableButtonImageListViewHolder(view, fragment, listener);
            default: return new DrawableButtonListViewHolder(view, fragment, listener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseListViewHolder holder, int position) {
        if (mValues==null || mValues.getValue()==null)
            return;
        holder.setUniversalItem(mValues.getValue().get(position));
    }

    public void setValues(LiveData<List<UniversalItem>> values) {
        mValues = values;
        mValues.observeForever(this);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mValues!=null && mValues.getValue()!=null)
            return mValues.getValue().size();
        return 0;
    }

    @Override
    public void onChanged(@Nullable List<UniversalItem> products) {
        notifyDataSetChanged();
    }

    public interface UniversalItemListListener {
        void onClickListItem(View view, UniversalItem universalItem);
    }
}

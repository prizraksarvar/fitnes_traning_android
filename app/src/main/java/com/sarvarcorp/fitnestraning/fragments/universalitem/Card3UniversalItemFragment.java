package com.sarvarcorp.fitnestraning.fragments.universalitem;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sarvarcorp.fitnestraning.App;
import com.sarvarcorp.fitnestraning.R;
import com.sarvarcorp.fitnestraning.base.BaseFragment;
import com.sarvarcorp.fitnestraning.fragments.TimerFragment;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentTransaction;

public class Card3UniversalItemFragment extends TitleImageUniversalItemFragment {
    protected TimerFragment timerFragment;
    protected TextView descriptionView;

    @Override
    public int getLayoutID() {
        return R.layout.ui_fragment_cart_3;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        timerFragment = App.getComponent().provideStaticData().getTimerFragment();
        timerFragment.setTimerTime(currentItem.timerTime);
        descriptionView = view.findViewById(R.id.universalItemsDescriptionTextView);
    }

    @Override
    public void onCreateView(View view) {
        super.onCreateView(view);
        setDescription(currentItem !=null ? currentItem.description : "");
    }

    protected void setDescription(String description) {
        if (descriptionView == null)
            return;
        descriptionView.setText(description);
    }
}

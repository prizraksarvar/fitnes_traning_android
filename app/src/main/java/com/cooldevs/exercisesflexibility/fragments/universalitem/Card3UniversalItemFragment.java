package com.cooldevs.exercisesflexibility.fragments.universalitem;

import android.view.View;
import android.widget.TextView;

import com.cooldevs.exercisesflexibility.App;
import com.cooldevs.exercisesflexibility.R;
import com.cooldevs.exercisesflexibility.fragments.TimerFragment;

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

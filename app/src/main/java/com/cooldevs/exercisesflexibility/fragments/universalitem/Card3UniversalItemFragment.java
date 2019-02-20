package com.cooldevs.exercisesflexibility.fragments.universalitem;

import android.view.View;
import android.widget.TextView;

import com.cooldevs.exercisesflexibility.App;
import com.cooldevs.exercisesflexibility.R;
import com.cooldevs.exercisesflexibility.entities.UniversalItemExtraColumn;
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
        descriptionView = view.findViewById(R.id.universalItemsDescriptionTextView);
        timerFragment = App.getComponent().provideStaticData().getTimerFragment();
        if (timerFragment==null) {
            return;
        }
        timerFragment.setTimerTime(currentItem.timerTime);
        timerFragment.setOnCompleteListener(this::setIsShown);
    }

    protected void setIsShown() {
        App.getComponent().provideExecutor().execute(()->{
            UniversalItemExtraColumn ec = App.getComponent().provideAppDatabase().universalItemExtraColumnDao().getSync(currentItem.id);
            if (ec == null) {
                ec = new UniversalItemExtraColumn();
                ec.id = currentItem.id;
            }
            ec.isShown = true;
            App.getComponent().provideAppDatabase().universalItemExtraColumnDao().save(ec);
        });
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

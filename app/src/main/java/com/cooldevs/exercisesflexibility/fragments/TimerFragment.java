package com.cooldevs.exercisesflexibility.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cooldevs.exercisesflexibility.App;
import com.cooldevs.exercisesflexibility.R;
import com.cooldevs.exercisesflexibility.base.BaseFragment;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class TimerFragment extends BaseFragment {
    protected ViewGroup timerLayout = null;
    protected TextView timerTextView = null;
    protected TextView timerButton = null;

    protected int timerTime = 0;
    protected int currentTimerTime = 0;

    protected Handler timerHandler;
    protected TimerHandler timerHandlerRunable;

    @Override
    protected int getLayoutID() {
        return R.layout.ui_fragment_timer;
    }

    public void setTimerTime(int timerTime) {
        this.timerTime = timerTime;
        currentTimerTime = timerTime;
        updateTimerValue();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().provideStaticData().setTimerFragment(this);
        timerHandlerRunable = new TimerHandler();
        timerHandler = new Handler();
    }

    @Override
    public void onDestroy() {
        App.getComponent().provideStaticData().setTimerFragment(null);
        super.onDestroy();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        initTimer(view);
        return view;
    }

    protected void initTimer(View rootView) {
        timerLayout = rootView.findViewById(R.id.uiTimerLayout);
        timerTextView = rootView.findViewById(R.id.uiTimerTextView);
        timerButton = rootView.findViewById(R.id.uiTimerButton);
        updateTimerValue();
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        };
        timerLayout.setOnClickListener(listener);
        timerButton.setOnClickListener(listener);
    }

    protected void updateTimerValue() {
        String text = "";
        int m = currentTimerTime / 60;
        int s = currentTimerTime % 60;
        text = String.format(Locale.ENGLISH, "%2d", m);
        text += ":" + String.format(Locale.ENGLISH,"%02d", s);
        timerTextView.setText(text);
    }

    public void startTimer() {
        currentTimerTime = timerTime;
        updateTimerValue();
        timerButton.setText("Restart");
        nextTimerSecond();
    }

    protected void nextTimerSecond() {
        timerHandler.removeCallbacksAndMessages(null);
        timerHandler.postDelayed(timerHandlerRunable, 1000);
    }

    protected class TimerHandler implements Runnable {

        @Override
        public void run() {
            currentTimerTime--;
            updateTimerValue();
            if (currentTimerTime>0)
                nextTimerSecond();
        }
    }
}

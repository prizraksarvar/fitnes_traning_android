package com.sarvarcorp.fitnestraning.base;

import android.os.AsyncTask;

import java.util.concurrent.Executor;

public class BaseExecuter extends Base implements Executor {
    private BaseAsyncTask asyncTask;

    public BaseExecuter() {
        asyncTask = new BaseAsyncTask();
    }

    @Override
    public void execute(Runnable runnable) {
        asyncTask.execute(runnable);
    }

    private class BaseAsyncTask extends AsyncTask<Runnable,Void,Void> {

        @Override
        protected Void doInBackground(Runnable... runnables) {
            int count = runnables.length;
            for (int i = 0; i<count; i++) {
                runnables[i].run();
            }
            return null;
        }
    }
}

package com.dejun.androidcourse.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dejun.androidcourse.R;
import com.dejun.commonsdk.constant.Extras;
import com.dejun.commonsdk.model.ProgressModel;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ForthActivity extends AppCompatActivity {
    public static void startActivity(Activity activity,ProgressModel progressModel){
            Intent intent = new Intent(activity, ForthActivity.class);
            intent.putExtra(Extras.FIRST,progressModel);
            activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forth);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(ProgressModel progressModel){
        Logger.d(progressModel.getProgress());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new ProgressModel(100));
    }
}

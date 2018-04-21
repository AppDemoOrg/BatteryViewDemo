package com.abt.battery;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.abt.R;

/**
 * @描述：     @BatteryActivity
 * @作者：     @黄卫旗
 * @创建时间： @2018-04-21
 */
public class BatteryActivity extends AppCompatActivity {

    private BatteryView mHorizontalBattery;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);
        initView();
        refreshView();
    }

    private void initView() {
        mHandler = new Handler();
        mHorizontalBattery = (BatteryView) findViewById(R.id.horizontalBattery);
    }

    /**
     * 每隔一秒刷新BatteryView
     */
    private void refreshView() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                BatteryEvent batteryEvent = getCurrentBatteryInfo();
                if (null != mHorizontalBattery) {
                    mHorizontalBattery.setProgress(batteryEvent.level);
                    mHorizontalBattery.setBatteryCharge(batteryEvent.isCharging);
                }
                mHandler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    /**
     * 获取当前电池属性
     */
    public final BatteryEvent getCurrentBatteryInfo() {
        final Intent intent = this.getApplicationContext().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        final int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0 );//电量（0-100）
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        final boolean isCharging  = (status == BatteryManager.BATTERY_STATUS_CHARGING)
                || (status == BatteryManager.BATTERY_STATUS_FULL);
        return new BatteryEvent(level,isCharging,status == BatteryManager.BATTERY_STATUS_FULL);
    }

}

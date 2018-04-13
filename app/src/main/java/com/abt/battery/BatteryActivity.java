package com.abt.battery;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.abt.R;

import java.util.Timer;
import java.util.TimerTask;

public class BatteryActivity extends Activity {

//    for BatteryViewOriginal2
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_battery);
//
//
//        BatteryViewOriginal2 view = (BatteryViewOriginal2) findViewById(R.id.battery);
//
//    }


    private BatteryView horizontalBattery, verticalBattery;

    private int power;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    horizontalBattery.setPower(power += 5);
                    if (power == 100) {
                        power = 0;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);

        horizontalBattery = (BatteryView) findViewById(R.id.horizontalBattery);
        verticalBattery = (BatteryView) findViewById(R.id.verticalBattery);

        verticalBattery.setColor(Color.BLACK);
        verticalBattery.setPower(85);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0);
            }
        }, 0, 100);
    }

}

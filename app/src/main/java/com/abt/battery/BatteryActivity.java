package com.abt.battery;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.abt.R;

public class BatteryActivity extends Activity {

//    for BatteryViewOriginal2
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_battery);
//        BatteryViewOriginal2 view = (BatteryViewOriginal2) findViewById(R.id.battery);
//    }

/*    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    horizontalBattery.setProgress(power += 5);
                    if (power == 100) {
                        power = 0;
                    }
                    break;
                default:
                    break;
            }
        }
    };*/

    private BatteryView horizontalBattery/*, verticalBattery*/;
    //private int power;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);

        horizontalBattery = (BatteryView) findViewById(R.id.horizontalBattery);
        horizontalBattery.setColor(Color.BLACK);
        horizontalBattery.setProgress(21);
        horizontalBattery.setBatteryCharge(false);
    }
/*        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0);
            }
        }, 0, 100);*/


/*        verticalBattery = (BatteryView) findViewById(R.id.verticalBattery);
        verticalBattery.setColor(Color.BLACK);
        verticalBattery.setProgress(85);
    }*/

}

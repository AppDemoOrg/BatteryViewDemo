package com.abt;

import android.app.Application;

public class BasicApplication extends Application {

    private static BasicApplication sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        init();
        //initComplete();
    }

    public static final BasicApplication getAppContext(){
        return sContext;
    }

    private final void init(){
        if(BuildConfig.DEBUG){
            //DebugManage.initialize(this);
        }
    }

    //public abstract void initComplete();

}

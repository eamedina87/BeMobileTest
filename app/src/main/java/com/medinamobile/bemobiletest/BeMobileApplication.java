package com.medinamobile.bemobiletest;

import android.app.Application;
import android.content.Context;
//import android.support.multidex.MultiDex;

/**
 * Created by Erick on 2/17/2018.
 */

public class BeMobileApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
  //      MultiDex.install(base);
    }
}

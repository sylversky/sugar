package com.example;

import android.app.Application;
import com.orm.SugarContext;
import com.orm.SugarDbConfiguration;

public class ClientApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this, new SugarDbConfiguration().setEncryptedPassword("mysugarpassword"));
    }

    @Override
    public void onTerminate() {
        SugarContext.terminate();
        super.onTerminate();
    }
}

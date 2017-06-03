package com.trillbit.app;

import android.app.Application;

import com.trillbit.app.dagger.AppModule;
import com.trillbit.app.dagger.DaggerNetComponent;
import com.trillbit.app.dagger.NetComponent;
import com.trillbit.model.login.LoginResponse;
import com.trillbit.network.manager.RetrofitManager;

/**
 * Created by Anil on 4/24/2017.
 */

public class TrillbitApplication extends Application {
    private NetComponent mNetComponent;
    @Override
    public void onCreate() {
        super.onCreate();
       // ActiveAndroid.initialize(this);
        mNetComponent = DaggerNetComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this)) // This also corresponds to the name of your module: %component_name%Module
                .retrofitManager(new RetrofitManager(BuildConfig.API_BASE_URL))
                .build();
      /*  if (BuildConfig.DEBUG) {
            AndroidDevMetrics.initWith(this);
        }*/
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }

}

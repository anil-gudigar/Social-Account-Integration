package com.trillbit.app.dagger;

import com.trillbit.app.TrillbitApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Anil on 4/24/2017.
 */
@Module
public class AppModule {

    TrillbitApplication mApplication;

    public AppModule(TrillbitApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    TrillbitApplication providesApplication() {
        return mApplication;
    }
}

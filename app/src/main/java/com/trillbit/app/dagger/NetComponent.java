package com.trillbit.app.dagger;

import com.trillbit.features.login.activity.LoginActivity;
import com.trillbit.features.login.presenter.LoginPresenterImpl;
import com.trillbit.network.manager.RetrofitManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Anil on 4/24/2017.
 */
@Singleton
@Component(modules={AppModule.class, RetrofitManager.class})
public interface  NetComponent {
    void inject(LoginActivity activity);
    LoginPresenterImpl getContactPresenterImpl();
}

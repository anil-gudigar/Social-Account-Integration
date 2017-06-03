package com.trillbit.features.login.presenter;


import com.trillbit.model.audio.AudioRequest;
import com.trillbit.model.login.LoginRequest;

/**
 * Created by anil on 4/24/2017.
 */

public interface LoginPresenter<V> {

    void attachedView(V view, boolean loadContactFromServer);

    void detachView();

    void onResume();

    void onLoginError();

    void requestLogin(LoginRequest loginRequest);

    void saveAudio(String access_token,AudioRequest audioRequest);
}

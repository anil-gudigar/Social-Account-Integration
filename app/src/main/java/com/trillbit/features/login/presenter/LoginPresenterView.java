package com.trillbit.features.login.presenter;

import com.trillbit.model.audio.AudioResponse;
import com.trillbit.model.login.LoginResponse;

/**
 * Created by anil on 4/24/2017.
 */

public interface LoginPresenterView {
    void showProgress(String message);

    void hideProgress();

    void onLoginSuccess(LoginResponse loginResponse);

    void onFailure(String message);

    void onSaveAudioSuccess(AudioResponse audioResponse);
}

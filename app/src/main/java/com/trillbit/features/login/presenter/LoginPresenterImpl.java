package com.trillbit.features.login.presenter;

import android.util.Log;

import com.trillbit.app.constants.AppConstants;
import com.trillbit.callbacks.AudioCallback;
import com.trillbit.callbacks.LoginCallback;
import com.trillbit.model.audio.AudioRequest;
import com.trillbit.model.audio.AudioResponse;
import com.trillbit.model.login.LoginResponse;
import com.trillbit.model.login.LoginRequest;
import com.trillbit.network.client.APIRequestClient;
import com.trillbit.utils.NetworkError;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by anil on 4/24/2017.
 */

public class LoginPresenterImpl implements LoginPresenter<LoginPresenterView> {
    private LoginPresenterView mView = null;
    private CompositeDisposable compositeDisposable;
    private APIRequestClient apiRequestClient;

    @Inject
    public LoginPresenterImpl(APIRequestClient apiRequestClient) {
        this.apiRequestClient = apiRequestClient;
    }

    @Override
    public void attachedView(LoginPresenterView loginPresenterView, boolean loadContactFromServer) {
        mView = loginPresenterView;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void detachView() {
        onStop();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onLoginError() {

    }

    @Override
    public void requestLogin(final LoginRequest loginRequest) {
        mView.showProgress("Signing In");
        Log.i(AppConstants.APP_TAG, "requestLogin  :" + loginRequest.toString());
        Disposable disposable = apiRequestClient.requestLogin(loginRequest, new LoginCallback() {
            @Override
            public void onSuccess(LoginResponse loginResponse) {
                Log.i(AppConstants.APP_TAG, "OnSucess :" + loginResponse.toString());
                mView.hideProgress();
                mView.onLoginSuccess(loginResponse);
            }

            @Override
            public void onError(NetworkError networkError) {
                mView.hideProgress();
                Log.i(AppConstants.APP_TAG, "onError :" + networkError.toString());
                mView.onFailure(networkError.getAppErrorMessage());
            }
        });
        compositeDisposable.add(disposable);
    }

    @Override
    public void saveAudio(String access_token, AudioRequest audioRequest) {
        mView.showProgress("Saving Audio");
        Log.i(AppConstants.APP_TAG, "requestLogin  :" + audioRequest.toString());
        Disposable disposable = apiRequestClient.saveAudio(access_token, audioRequest, new AudioCallback() {
            @Override
            public void onSuccess(AudioResponse response) {
                Log.i(AppConstants.APP_TAG, "OnSucess :" + response.toString());
                mView.hideProgress();
                mView.onSaveAudioSuccess(response);
            }

            @Override
            public void onError(NetworkError networkError) {
                mView.hideProgress();
                Log.i(AppConstants.APP_TAG, "onError :" + networkError.toString());
                mView.onFailure(networkError.getAppErrorMessage());
            }
        });
        compositeDisposable.add(disposable);
    }

    public void onStop() {
        compositeDisposable.clear();
        compositeDisposable.dispose();
    }

}

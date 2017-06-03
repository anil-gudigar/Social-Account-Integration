package com.trillbit.network.client;


import android.util.Log;

import com.trillbit.app.constants.AppConstants;
import com.trillbit.callbacks.AudioCallback;
import com.trillbit.callbacks.LoginCallback;
import com.trillbit.model.audio.AudioRequest;
import com.trillbit.model.audio.AudioResponse;
import com.trillbit.model.login.LoginResponse;
import com.trillbit.model.login.LoginRequest;
import com.trillbit.network.repository.TrillbitDataRepository;
import com.trillbit.utils.NetworkError;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by anil on 4/24/2017.
 */

public class APIRequestClient {
    private final TrillbitDataRepository mTrillbitDataRepository;

    /**
     * Retrofit Request Client
     *
     * @param trillbitDataRepository @see {@link TrillbitDataRepository}
     */
    @Inject
    public APIRequestClient(TrillbitDataRepository trillbitDataRepository) {
        mTrillbitDataRepository = trillbitDataRepository;
    }

    /**
     * API call to get all contact i.e GET /contacts.json
     *
     * @param callback callback for success / failure @see {@link LoginCallback}
     */
    public Disposable requestLogin(LoginRequest loginRequest, final LoginCallback callback) {
        Log.i(AppConstants.APP_TAG, "APIRequestClient ->requestLogin  :" + loginRequest.toString());
        return mTrillbitDataRepository.requestLoginObservable(loginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoginResponse>() {
                                   @Override
                                   public void onNext(LoginResponse loginResponse) {
                                       callback.onSuccess(loginResponse);
                                   }

                                   @Override
                                   public void onError(Throwable e) {
                                       callback.onError(new NetworkError(e));
                                   }

                                   @Override
                                   public void onComplete() {

                                   }
                               }
                );
    }


    /**
     * API call to get all contact i.e GET /contacts.json
     *
     * @param callback callback for success / failure @see {@link LoginCallback}
     */
    public Disposable saveAudio(String Acess_token, AudioRequest audioRequest, final AudioCallback callback) {
        Log.i(AppConstants.APP_TAG, "APIRequestClient ->requestLogin  :" + audioRequest.toString());
        return mTrillbitDataRepository.saveAudioObservable(Acess_token, audioRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<AudioResponse>() {
                                   @Override
                                   public void onNext(AudioResponse audioResponse) {
                                       callback.onSuccess(audioResponse);
                                   }

                                   @Override
                                   public void onError(Throwable e) {
                                       callback.onError(new NetworkError(e));
                                   }

                                   @Override
                                   public void onComplete() {

                                   }
                               }
                );
    }


}

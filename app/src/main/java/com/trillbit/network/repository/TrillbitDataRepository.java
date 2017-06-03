package com.trillbit.network.repository;


import com.trillbit.app.constants.AppConstants;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import com.trillbit.model.audio.AudioRequest;
import com.trillbit.model.audio.AudioResponse;
import com.trillbit.model.login.LoginResponse;
import com.trillbit.model.login.LoginRequest;

/**
 * Created by anil on 4/24/2017.
 */

public interface TrillbitDataRepository {

    @Headers({AppConstants.API_CONSTANTS.CONTENT_TYPE_APPLICATION_JSON})
    @POST(AppConstants.API_CONSTANTS.REQUEST_LOGIN)
    Observable<LoginResponse> requestLoginObservable(@Body LoginRequest loginRequest);

    @Headers({AppConstants.API_CONSTANTS.CONTENT_TYPE_APPLICATION_JSON})
    @POST(AppConstants.API_CONSTANTS.REQUEST_SAVE_AUDIO)
    Observable<AudioResponse> saveAudioObservable(@Header("token") String Access_Token, @Body AudioRequest audioRequest);
}

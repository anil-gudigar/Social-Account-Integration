package com.trillbit.callbacks;

import com.trillbit.model.audio.AudioResponse;
import com.trillbit.model.login.LoginResponse;
import com.trillbit.utils.NetworkError;


/**
 * Created by anil on 4/24/2017.
 */

public interface AudioCallback {
    void onSuccess(AudioResponse response);

    void onError(NetworkError networkError);
}

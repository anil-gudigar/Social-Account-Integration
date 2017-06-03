package com.trillbit.callbacks;

import com.trillbit.model.login.LoginResponse;
import com.trillbit.utils.NetworkError;


/**
 * Created by anil on 4/24/2017.
 */

public interface LoginCallback {
    void onSuccess(LoginResponse response);

    void onError(NetworkError networkError);
}

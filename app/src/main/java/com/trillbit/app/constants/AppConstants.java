package com.trillbit.app.constants;

/**
 * Created by anil on 4/24/2017.
 */

public class AppConstants {
    public static String APP_TAG = "trillbit";

    public static class API_CONSTANTS {
        public static final String CONTENT_TYPE_APPLICATION_JSON = "Content-Type: application/json;";
        public static final String REQUEST_LOGIN = "/v1/login/";
        public static final String REQUEST_SAVE_AUDIO = "/v1/save_audio/";
        public static class LOGIN_TYPE {
            public static final String GOOGLE = "google";
            public static final String FACEBOOK = "facebook";
        }
    }


}

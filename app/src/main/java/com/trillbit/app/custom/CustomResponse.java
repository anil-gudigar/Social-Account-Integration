package com.trillbit.app.custom;

import com.google.gson.annotations.SerializedName;

/**
 * Created by anil on 4/24/2017.
 */

public class CustomResponse {
    @SerializedName("status")
    public String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @SuppressWarnings({"unused", "used by Retrofit"})
    public CustomResponse() {
    }

    public CustomResponse(String status) {
        this.status = status;
    }
}

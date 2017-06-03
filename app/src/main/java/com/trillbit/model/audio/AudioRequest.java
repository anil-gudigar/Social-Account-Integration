package com.trillbit.model.audio;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by anil on 26/4/17.
 */

public class AudioRequest {
    @SerializedName("audio_data")
    @Expose
    private byte[] audio_data;

    public byte[] getAudio_data() {
        return audio_data;
    }

    public void setAudio_data(byte[] audio_data) {
        this.audio_data = audio_data;
    }
}

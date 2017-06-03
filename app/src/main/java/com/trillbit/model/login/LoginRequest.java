package com.trillbit.model.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by anil on 4/24/2017.
 */

public class LoginRequest implements Parcelable {
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("auth")
    @Expose
    private String auth;
    @SerializedName("login_type")
    @Expose
    private String login_type;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getLogin_type() {
        return login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "user_id='" + user_id + '\'' +
                ", auth='" + auth + '\'' +
                ", login_type='" + login_type + '\'' +
                '}';
    }

    public static final Parcelable.Creator<LoginRequest> CREATOR
            = new Parcelable.Creator<LoginRequest>() {
        public LoginRequest createFromParcel(Parcel in) {
            return new LoginRequest(in);
        }

        public LoginRequest[] newArray(int size) {
            return new LoginRequest[size];
        }
    };

    public LoginRequest() {
        super();
    }

    private LoginRequest(Parcel in) {
        super();
        if (null != in) {
            login_type = in.readString();
            auth = in.readString();
            user_id = in.readString();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(login_type);
        parcel.writeString(auth);
        parcel.writeString(user_id);
    }

}

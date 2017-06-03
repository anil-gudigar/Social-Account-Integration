package com.trillbit.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by anil on 4/24/2017.
 */

public class Payload {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("google_id")
    @Expose
    private String google_id;
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("facebook_id")
    @Expose
    private String facebook_id;
    @SerializedName("access_token")
    @Expose
    private String access_token;

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getGoogle_id ()
    {
        return google_id;
    }

    public void setGoogle_id (String google_id)
    {
        this.google_id = google_id;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getFacebook_id ()
    {
        return facebook_id;
    }

    public void setFacebook_id (String facebook_id)
    {
        this.facebook_id = facebook_id;
    }

    public String getAccess_token ()
    {
        return access_token;
    }

    public void setAccess_token (String access_token)
    {
        this.access_token = access_token;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", google_id='" + google_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", facebook_id='" + facebook_id + '\'' +
                ", access_token='" + access_token + '\'' +
                '}';
    }
}

package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chandrav on 2/21/15.
 */
public class User {
    private String name;
    private long uid;
    private String screenname;
    private String profileImgUrl;

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenname() {
        return screenname;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }
    
    public static User fromJSON(JSONObject jsonObject){
        User user = new User();
        try {
            user.name = jsonObject.getString("name");
            user.uid = jsonObject.getLong("id");
            user.screenname = jsonObject.getString("screen_name");
            user.profileImgUrl = jsonObject.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}

package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by chandrav on 2/21/15.
 */
public class Tweet {
    private String body;

    public User getUser() {
        return user;
    }

    private User user;
    private long uid;
    private String createdAt;

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public static Tweet fromJson(JSONObject jsonObject){
        Tweet tweet = new Tweet();

        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            Log.d("DEBUG", e.toString());
            e.printStackTrace();
        }
        return tweet;
        
    }


    public static ArrayList<Tweet> fromJsonArray(JSONArray json) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for(int i = 0; i < json.length(); i++){
            try {
                Tweet tweet =Tweet.fromJson(json.getJSONObject(i));
                if(tweet != null){
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return tweets;
    }
}

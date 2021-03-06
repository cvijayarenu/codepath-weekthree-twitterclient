package com.codepath.apps.restclienttemplate.Utils;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1/"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "dB7DKG5KlobYkY99gLzdnxWTo";       // Change this
	public static final String REST_CONSUMER_SECRET = "lbp0WrUQ0ow1uKedjbSu3c17BHM3yoLGM7fRnq9iMg4tIsyvJ9"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cptwitterclient"; // Change this (here and in manifest)
    public static final int REST_TWEETS_COUNT = 25;

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public void getInterestingnessList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
    
    
    public void getHomeTimeline(Long max_id, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", REST_TWEETS_COUNT);
//        params.put("since_id",1 );
        if(max_id != null){
            params.put("max_id", max_id - 1);
        }

        getClient().get(apiUrl, params, handler);
        
    }
    
    public void getMentionsTimeline(Long max_id, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", REST_TWEETS_COUNT);
        //params.put("include_rts", 1);
        if(max_id != null){
            params.put("max_id", max_id - 1);
        }

        getClient().get(apiUrl, params, handler);


    }
    
    public void getUserTimeline(Long userid, Long max_id, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", REST_TWEETS_COUNT);
        params.put("user_id", userid);
        //params.put("include_rts", 1);
        if(max_id != null){
            params.put("max_id", max_id - 1);
        }

        getClient().get(apiUrl, params, handler);


    }
    
    public void getUserInfo(Long userid, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("users/show.json");
        RequestParams params = new RequestParams();
        params.put("user_id", userid);

        getClient().get(apiUrl, params, handler);



    }
    
    
    public void composeTweet(AsyncHttpResponseHandler handler, String post){
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", post);
        getClient().post(apiUrl, params, handler);
    }
    
    
    public void getTweetDetails(Long tweetId, AsyncHttpResponseHandler handler){
        
        String apiUrl = getApiUrl("statuses/show.json");
        RequestParams params = new RequestParams();
        params.put("id", tweetId);
        getClient().get(apiUrl, params, handler);
                
        
    }
}
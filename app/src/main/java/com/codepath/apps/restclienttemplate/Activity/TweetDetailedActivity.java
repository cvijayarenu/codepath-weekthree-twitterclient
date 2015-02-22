package com.codepath.apps.restclienttemplate.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.Utils.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TweetDetailedActivity extends ActionBarActivity {
    private TwitterClient twitterClient;
    private JSONObject tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detailed);
        Long tweetId = getIntent().getLongExtra("id", 1);
        twitterClient = TwitterApplication.getRestClient();
        twitterClient.getTweetDetails(tweetId, new JsonHttpResponseHandler(){
            
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                tweet = response;
                TextView tvId = (TextView) findViewById(R.id.tvTweetId);
                TextView tvText = (TextView) findViewById(R.id.tvTweetText);
                TextView tvUser = (TextView) findViewById(R.id.tvUserName);
                try {
                    tvId.setText(tweet.getString("id_str"));
                    tvText.setText(tweet.getString("text"));
                    tvUser.setText(tweet.getJSONObject("user").getString("name"));
                    
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tweet_detailed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/html");
            try {
                String user = tweet.getJSONObject("user").getString("screen_name");
                String id_str = tweet.getString("id_str");
                String shareContent = "https://twitter.com/"+user+"/status/"+ id_str;
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(shareContent));
                startActivity(Intent.createChooser(sharingIntent,"Share using"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

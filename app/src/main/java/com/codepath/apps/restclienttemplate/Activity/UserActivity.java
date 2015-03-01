package com.codepath.apps.restclienttemplate.Activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.Adapters.TweetsArrayAdapter;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.Utils.EndlessScrollListener;
import com.codepath.apps.restclienttemplate.Utils.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserActivity extends ActionBarActivity {
    private static final int REQUEST_CODE = 1;
    private long userid;
    private TwitterClient twitterClient;
    private Long max_id = null;

    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    
    private TextView tvName;
    private TextView tvTagline;
    private TextView tvFollowing;
    private TextView tvFollowers;
    private ImageView ivprofileimage;
    
    



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        userid = getIntent().getLongExtra("id", -1);
        twitterClient = TwitterApplication.getRestClient();

        lvTweets = (ListView) findViewById(R.id.lvTweets);
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(this, tweets);
        lvTweets.setAdapter(aTweets);
        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(UserActivity.this, TweetDetailedActivity.class);
                i.putExtra("id", tweets.get(position).getUid());
                startActivity(i); // brings up t
            }
        });

        
        tvName = (TextView) findViewById(R.id.tvUserName);
        tvTagline = (TextView) findViewById(R.id.tvTagline);
        tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        ivprofileimage = (ImageView) findViewById(R.id.ivUserProfileImage);
        
        

        populateUserinfo();
        populateTimeline(true);

    }

    private void populateUserinfo() {
        twitterClient.getUserInfo(userid, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                //Toast.makeText(UserActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                try {
                    String followers = json.getString("followers_count");
                    String following = json.getString("friends_count");
                    String name = json.getString("name");
                    String tagline = json.getString("description");
                    String profileimageurl = json.getString("profile_image_url");
                    
                    
                    tvName.setText(name);
                    tvFollowing.setText(following + " Following");
                    tvFollowers.setText(followers + " Followers");
                    tvTagline.setText(tagline);
                    Picasso.with(UserActivity.this).load(profileimageurl).into(ivprofileimage);
                    
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }

            
        });
    }

    private void populateTimeline(Boolean clear) {
        twitterClient.getUserTimeline(userid, max_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                aTweets.addAll(Tweet.fromJsonArray(json));
                //Toast.makeText(UserActivity.this, "Hello", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }


        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

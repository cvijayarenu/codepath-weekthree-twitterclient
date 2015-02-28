package com.codepath.apps.restclienttemplate.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.apps.restclienttemplate.Activity.TweetDetailedActivity;
import com.codepath.apps.restclienttemplate.Adapters.TweetsArrayAdapter;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.Utils.EndlessScrollListener;
import com.codepath.apps.restclienttemplate.Utils.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by chandrav on 2/28/15.
 */
public class TimelineFragment extends Fragment {

    private static final int REQUEST_CODE = 1;
    private TwitterClient twitterClient;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    private SwipeRefreshLayout swipeContainer;
    private Long max_id = null;

    public static TimelineFragment newInstance() {
        Bundle args = new Bundle();
        TimelineFragment fragment = new TimelineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);

        lvTweets = (ListView) view.findViewById(R.id.lvTweets);

        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(getActivity().getApplicationContext(), tweets);
        lvTweets.setAdapter(aTweets);
        lvTweets.setOnScrollListener( new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                populateTimeline(false);
            }
        });
        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity().getApplicationContext(), TweetDetailedActivity.class);
                i.putExtra("id", tweets.get(position).getUid());
                startActivity(i); // brings up t
            }
        });

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

        twitterClient = TwitterApplication.getRestClient();
        populateTimeline(true);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                populateTimeline(true);
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return view;
    }

    private void populateTimeline(Boolean clear) {
        if (clear) {
            aTweets.clear();
            max_id = null;
        }
        twitterClient.getHomeTimeline(max_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                //Log.d("DEBUG", json.toString());
                swipeContainer.setRefreshing(false);
                aTweets.addAll(Tweet.fromJsonArray(json));
                max_id = aTweets.getItem(aTweets.getCount() - 1 ).getUid();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }


        });
    }
    
}

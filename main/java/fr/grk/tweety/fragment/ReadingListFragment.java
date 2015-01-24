package fr.grk.tweety.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.grk.tweety.R;
import fr.grk.tweety.adapters.TweetsAdapter;
import fr.grk.tweety.loaders.TweetsLoader;
import fr.grk.tweety.model.Tweet;
import fr.grk.tweety.utils.AccountManager;


public class ReadingListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Tweet>> {

    private static final int LOADER_TWEETS = 1000;
    private String mUserHandle;
    private String mToken;
    private TweetsAdapter mListAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserHandle = AccountManager.getUserHandle(getActivity());
        mToken = AccountManager.getUserToken(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reading_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListAdapter = new TweetsAdapter();
        //*
        setListAdapter(mListAdapter);
        view.findViewById(R.id.post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(LOADER_TWEETS, null, this);
    }

    @Override
    public Loader<List<Tweet>> onCreateLoader(int id, Bundle args) {
        return new TweetsLoader(getActivity(), mUserHandle, mToken);
    }

    @Override
    public void onLoadFinished(Loader<List<Tweet>> loader, List<Tweet> data) {
        mListAdapter.setTweets(data);
        setListAdapter(mListAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<Tweet>> loader) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Log.e("READING_LIST", "called");
    }



    public void post() {
        new PostTweetFragment().show(getFragmentManager(), "post_tweet");
    }
}

package fr.grk.tweety.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import fr.grk.tweety.R;
import fr.grk.tweety.activity.TweetsActivity;
import fr.grk.tweety.adapters.UsersAdapter;
import fr.grk.tweety.loaders.UsersLoader;
import fr.grk.tweety.model.User;


public class UsersFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<User>> {


    private static final int LOADER_USERS = 1000;

    private UsersAdapter mListAdapter;
    private boolean mIsMasterDetailsMode;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    //*/
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsMasterDetailsMode = (getActivity().findViewById(R.id.tweet_content) != null);
        mListAdapter = new UsersAdapter();
        setListAdapter(mListAdapter);
        //if (mIsMasterDetailsMode) {
            //getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //}


    }
    //*/


    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(LOADER_USERS, null, this);
    }

    @Override
    public Loader<List<User>> onCreateLoader(int i, Bundle bundle) {
        return new UsersLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> data) {
        mListAdapter.setUsers(data, getActivity());
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) {

    }


    //*
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        //super.onListItemClick(l, v, position, id);
        User user = mListAdapter.getItem(position);

        if (!mIsMasterDetailsMode) {
            Intent intent = new Intent(getActivity(), TweetsActivity.class);
            intent.putExtras(TweetsFragment.newArguments(user));
            startActivity(intent);
        } else {
            android.support.v4.app.Fragment tweetFragment = new TweetsFragment();
            tweetFragment.setArguments(TweetsFragment.newArguments(user));
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tweet_content, tweetFragment)
                    .commit();
        }
    }

    public void reloadList() {
        getLoaderManager().restartLoader(0, null, this);
    }


    //*/
}


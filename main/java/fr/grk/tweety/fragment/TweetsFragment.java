package fr.grk.tweety.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;

import java.util.List;

import fr.grk.tweety.activity.TweetsActivity;
import fr.grk.tweety.adapters.TweetsAdapter;
import fr.grk.tweety.loaders.TweetsLoader;
import fr.grk.tweety.model.Tweet;
import fr.grk.tweety.model.User;
import fr.grk.tweety.utils.AccountManager;

/**
 * Created by grk on 05/12/14.
 */
public class TweetsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Tweet>> {


    private static final int LOADER_TWEETS = 10;
    private static final String ARG_USER  = "user";
    private User mUser;

    private TweetsAdapter mListAdapter;


    public TweetsFragment() {
    }


    public static Bundle newArguments(User user){
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = getArguments().getParcelable(ARG_USER);
    }

    //*/
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListAdapter = new TweetsAdapter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof TweetsActivity){
            getActivity().setTitle(mUser.getHandle());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(LOADER_TWEETS, null, this);
    }

    @Override
    public Loader<List<Tweet>> onCreateLoader(int i, Bundle bundle) {
        return new TweetsLoader(getActivity(), mUser.getHandle(), AccountManager.getUserToken(getActivity()));
    }

    @Override
    public void onLoadFinished(Loader<List<Tweet>> loader, List<Tweet> data) {
        mListAdapter.setTweets(data);
        setListAdapter(mListAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<Tweet>> loader) {

    }
}

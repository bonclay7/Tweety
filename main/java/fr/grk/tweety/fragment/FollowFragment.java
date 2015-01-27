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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.grk.tweety.R;
import fr.grk.tweety.activity.FollowersActivity;
import fr.grk.tweety.activity.TweetsActivity;
import fr.grk.tweety.adapters.ShowFollowAdapter;
import fr.grk.tweety.adapters.TweetsAdapter;
import fr.grk.tweety.adapters.UsersAdapter;
import fr.grk.tweety.loaders.ShowFollowsLoader;
import fr.grk.tweety.loaders.TweetsLoader;
import fr.grk.tweety.loaders.UsersLoader;
import fr.grk.tweety.model.Tweet;
import fr.grk.tweety.model.User;
import fr.grk.tweety.utils.AccountManager;


//One single fragment for followings and followers view based on ARG_USER_TYPE
public class FollowFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<User>> {

    private static final int LOADER_FOLLOW = 100000;
    private static final String ARG_USER = "user";
    //Permits to know if it's followings or followers we want to show
    private static final String ARG_USERS_TYPE = "view";
    private User mUser;


    private ShowFollowAdapter mListAdapter;


    public FollowFragment() {
    }


    public static Bundle newArguments(User user, String usersType) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        args.putString(ARG_USERS_TYPE, usersType);
        return args;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_follow, container, false);
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
        mListAdapter = new ShowFollowAdapter();
        setListAdapter(mListAdapter);

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String userType = getArguments().getString(ARG_USERS_TYPE);
        String title;
        if (userType.equals(AccountManager.P_USERS_TYPE_FOLLOWERS))title = "Followers "+mUser.getHandle();
        else title = "Followings "+mUser.getHandle();
        getActivity().setTitle(title);
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(LOADER_FOLLOW, null, this);
    }

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        return new ShowFollowsLoader(getActivity(), mUser.getHandle(), getArguments().getString(ARG_USERS_TYPE));
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> data) {
        mListAdapter.setUsers(data, getActivity());
        setListAdapter(mListAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) {

    }
}


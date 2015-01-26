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
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.grk.tweety.R;
import fr.grk.tweety.activity.FollowersActivity;
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
    private static final String ARG_USER = "user";
    private User mUser;

    ImageView profilePictureView;
    TextView handleTextView;
    Button tweetsButton;
    Button followingsButton;
    Button followersButton;

    private TweetsAdapter mListAdapter;


    public TweetsFragment() {
    }


    public static Bundle newArguments(User user) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        return args;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tweets, container, false);
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
        setListAdapter(mListAdapter);

        //Setting up picture profile
        profilePictureView = (ImageView) view.findViewById(R.id.profile_picture);
        Picasso.with(view.getContext()).load(mUser.getPicture()).into(profilePictureView);

        //followers count
        followersButton = (Button) view.findViewById(R.id.followers_button);
        followersButton.setText(getString(R.string.followers_count_button, mUser.getFollowers()));

        followersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionShowFollowers();
            }
        });

        //followings count
        followingsButton = (Button) view.findViewById(R.id.followings_button);
        followingsButton.setText(getString(R.string.followings_count_button, mUser.getFollows()));

        followingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionShowFollowings();
            }
        });
    }


    public void actionShowFollowers(){
        if (mUser.getFollowers() > 0){
            Intent intent = new Intent(getActivity(), FollowersActivity.class);
            intent.putExtras(FollowFragment.newArguments(mUser, AccountManager.P_USERS_TYPE_FOLLOWERS));
            startActivity(intent);
        }else{
            Toast.makeText(getActivity(), "Please help "+ mUser.getHandle()+" !", Toast.LENGTH_SHORT).show();
        }
    }

    public void actionShowFollowings(){
        if (mUser.getFollows() > 0){
            Intent intent = new Intent(getActivity(), FollowersActivity.class);
            intent.putExtras(FollowFragment.newArguments(mUser, AccountManager.P_USERS_TYPE_FOLLOWINGS));
            startActivity(intent);
        }else{
            Toast.makeText(getActivity(), "Please help "+ mUser.getHandle()+" !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof TweetsActivity) {
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

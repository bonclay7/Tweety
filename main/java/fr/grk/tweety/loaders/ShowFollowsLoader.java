package fr.grk.tweety.loaders;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.grk.tweety.model.User;
import fr.grk.tweety.utils.AccountManager;
import fr.grk.tweety.utils.ApiClient;

/**
 * Created by grk on 05/12/14.
 */

public class ShowFollowsLoader extends AsyncTaskLoader<List<User>> {


    private List<User> mResult;
    private String mUsersType;
    private String mUserHandle;
    private Context context ;


    public ShowFollowsLoader(Context context, String userHandle,String usersType) {
        super(context);
        this.mUsersType = usersType;
        this.mUserHandle = userHandle;
        this.context = context;
    }


    @Override
    public List<User> loadInBackground() {
        try {
            if (mUsersType.equals(AccountManager.P_USERS_TYPE_FOLLOWERS)) return new ApiClient().getFollowers(mUserHandle);
            else if (mUsersType.equals(AccountManager.P_USERS_TYPE_FOLLOWINGS)) return new ApiClient().getFollowings(mUserHandle);
            else return new ArrayList<>();
        } catch (IOException e) {
            Log.e(ShowFollowsLoader.class.getName(), "Failed to download users", e);
            return null;
        }
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mResult != null) {
            deliverResult(mResult);
        }
        if (takeContentChanged() || mResult == null) {
            forceLoad();
        }
    }


    @Override
    public void deliverResult(List<User> data) {
        mResult = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }
}

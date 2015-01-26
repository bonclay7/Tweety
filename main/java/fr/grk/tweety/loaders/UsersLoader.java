package fr.grk.tweety.loaders;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import fr.grk.tweety.model.User;
import fr.grk.tweety.utils.AccountManager;
import fr.grk.tweety.utils.ApiClient;

/**
 * Created by grk on 05/12/14.
 */

public class UsersLoader extends AsyncTaskLoader<List<User>> {


    private List<User> mResult;

    public UsersLoader(Context context) {
        super(context);
    }


    @Override
    public List<User> loadInBackground() {
        try {
            return new ApiClient().getUsers(AccountManager.getUserHandle(getContext()));
        } catch (IOException e) {
            Log.e(UsersLoader.class.getName(), "Failed to download users", e);
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

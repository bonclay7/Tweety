package fr.grk.tweety.loaders;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import fr.grk.tweety.model.Tweet;
import fr.grk.tweety.utils.ApiClient;

/**
 * Created by grk on 05/12/14.
 */

public class TweetsLoader extends AsyncTaskLoader<List<Tweet>> {


    private List<Tweet> mResult;
    private String mUserHandle;
    private String mToken;


    public TweetsLoader(Context context, String userHandle, String token) {
        super(context);
        this.mUserHandle = userHandle;
        this.mToken = token;
    }

    @Override
    public List<Tweet> loadInBackground() {
        try {
            return new ApiClient().getUserTweets(mUserHandle);
        }catch(IOException e){
            Log.e( TweetsLoader.class.getName(), "Failed to download tweets", e);
            return null;
        }
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mResult != null){
            deliverResult(mResult);
        }
        if (takeContentChanged() || mResult == null){
            forceLoad();
        }
    }


    @Override
    public void deliverResult(List<Tweet> data) {
        Log.i( TweetsLoader.class.getName(), "Returned data: " + data);
        mResult = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }
}

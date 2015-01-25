package fr.grk.tweety.loaders;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import fr.grk.tweety.R;
import fr.grk.tweety.utils.ApiClient;

/**
 * Created by grk on 25/01/15.
 */
public class FollowAsyncTask extends AsyncTask<String, Void, Boolean> {

    private Context context;
    private ProgressDialog pgDialog;
    private String followeeHandle;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setFolloweeHandle(String followeeHandle) {
        this.followeeHandle = followeeHandle;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            String handle = params[0];
            String followeeHandle = params[1];
            String token= params[2];
            return new ApiClient().follow(handle, followeeHandle, token);
        } catch (IOException e) {
            Log.e(FollowAsyncTask.class.getName(), "follow failed", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(Boolean actionSucceed) {
        pgDialog.dismiss();
        if (actionSucceed){
            Toast.makeText(context, "You are following " + followeeHandle + " now !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.login_error, Toast.LENGTH_SHORT).show();
        }
    }


}

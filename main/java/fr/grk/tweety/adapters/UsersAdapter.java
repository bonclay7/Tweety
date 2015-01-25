package fr.grk.tweety.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import fr.grk.tweety.R;
import fr.grk.tweety.model.User;
import fr.grk.tweety.utils.AccountManager;
import fr.grk.tweety.utils.ApiClient;
import fr.grk.tweety.utils.ReloadFragmentInterface;

/**
 * Created by grk on 24/01/15.
 */
public class UsersAdapter extends BaseAdapter {

    private List<User> mUsers;
    private Context context;
    private ReloadFragmentInterface mCallback;




    private ProgressDialog progressDialog;


    public void setUsers(List<User> users, Context context) {
        this.mUsers = users;
        this.context = context;

        try {
            mCallback = (ReloadFragmentInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnTweetPostedListener");
        }

        notifyDataSetChanged();
    }

    public List<User> getUsers() {
        return mUsers;
    }

    @Override
    public int getCount() {
        return (mUsers == null ? 0 : mUsers.size());
    }

    @Override
    public User getItem(int position) {
        return mUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (getCount() == 0) ? 0 : getItem(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (null == convertView) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        }

        final User user = getItem(position);

        TextView handleView = (TextView) convertView.findViewById(R.id.handle);
        handleView.setText(user.getHandle());

        ImageView profilePictureView = (ImageView) convertView.findViewById(R.id.profile_picture);
        Picasso.with(convertView.getContext()).load(user.getPicture()).into(profilePictureView);

        TextView followingsView = (TextView) convertView.findViewById(R.id.followings);
        followingsView.setText(followingsView.getText().toString().replace("%1d", user.getFollows() + ""));

        TextView followersView = (TextView) convertView.findViewById(R.id.followers);
        followersView.setText(followersView.getText().toString().replace("%1d", user.getFollowers() + ""));

        ImageButton followButton = (ImageButton) convertView.findViewById(R.id.action_follow);
        ImageButton unfollowButton = (ImageButton) convertView.findViewById(R.id.action_unfollow);


        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                follow(user.getHandle());
            }
        });

        unfollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               unfollow(user.getHandle());
            }
        });


        if (user.isFollowed()) {
            followButton.setVisibility(View.INVISIBLE);
            unfollowButton.setVisibility(View.VISIBLE);
        }
        else {
            followButton.setVisibility(View.VISIBLE);
            unfollowButton.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }


    public void follow(final String followeeHandle) {



        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(context.getString(R.string.connecting));
        progressDialog.show();

        new AsyncTask<String, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {
                try {
                    String handle = params[0];
                    String followeeHandle = params[1];
                    String token= params[2];
                    return new ApiClient().follow(handle, followeeHandle, token);
                } catch (IOException e) {
                    Log.e(UsersAdapter.class.getName(), "follow failed", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Boolean actionSucceed) {
                progressDialog.dismiss();
                if (actionSucceed){
                    Toast.makeText(context, "You are following "+followeeHandle+ " now !", Toast.LENGTH_SHORT).show();
                    mCallback.discoverListChanged();
                } else {
                    Toast.makeText(context, R.string.login_error, Toast.LENGTH_SHORT).show();
                }
            }

        }.execute(AccountManager.getUserHandle(context), followeeHandle, AccountManager.getUserToken(context));
    }

    public void unfollow(final String followeeHandle) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(context.getString(R.string.connecting));
        progressDialog.show();

        new AsyncTask<String, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {
                try {
                    String handle = params[0];
                    String followeeHandle = params[1];
                    String token= params[2];
                    return new ApiClient().unfollow(handle, followeeHandle, token);
                } catch (IOException e) {
                    Log.e(UsersAdapter.class.getName(), "follow failed", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Boolean actionSucceed) {
                progressDialog.dismiss();
                if (actionSucceed){
                    Toast.makeText(context, "You are not following "+followeeHandle+ " anymore", Toast.LENGTH_SHORT).show();
                    mCallback.discoverListChanged();
                } else {
                    Toast.makeText(context, R.string.login_error, Toast.LENGTH_SHORT).show();
                }
            }

        }.execute(AccountManager.getUserHandle(context), followeeHandle, AccountManager.getUserToken(context));
    }
}

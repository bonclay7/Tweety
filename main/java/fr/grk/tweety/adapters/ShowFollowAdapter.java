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
public class ShowFollowAdapter extends BaseAdapter {

    private List<User> mUsers;
    private Context context;




    private ProgressDialog progressDialog;


    public void setUsers(List<User> users, Context context) {
        this.mUsers = users;
        this.context = context;

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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_user_item, parent, false);
        }

        final User user = getItem(position);

        TextView handleView = (TextView) convertView.findViewById(R.id.handle);
        handleView.setText(user.getHandle());

        ImageView profilePictureView = (ImageView) convertView.findViewById(R.id.profile_picture);
        Picasso.with(convertView.getContext()).load(user.getPicture()).into(profilePictureView);

        return convertView;
    }

}

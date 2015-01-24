package fr.grk.tweety.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.grk.tweety.R;
import fr.grk.tweety.model.User;

/**
 * Created by grk on 24/01/15.
 */
public class UsersAdapter extends BaseAdapter {

    private List<User> mUsers;


    public void setUsers(List<User> users) {
        this.mUsers = users;
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
        return (getCount() == 0 ) ? 0 : getItem(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (null == convertView) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        }

        User user = getItem(position);

        TextView handleView = (TextView) convertView.findViewById(R.id.handle);
        handleView.setText(user.getHandle());

        ImageView profilePictureView = (ImageView) convertView.findViewById(R.id.profile_picture);
        Picasso.with(convertView.getContext()).load(user.getPicture()).into(profilePictureView);

        TextView followingsView = (TextView) convertView.findViewById(R.id.followings);
        followingsView.setText(followingsView.getText().toString().replace("%1d", user.getFollows()+""));

        TextView followersView = (TextView) convertView.findViewById(R.id.followers);
        followersView.setText(followersView.getText().toString().replace("%1d", user.getFollowers()+""));


        return convertView;
    }

}

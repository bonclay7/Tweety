package fr.grk.tweety.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fr.grk.tweety.R;
import fr.grk.tweety.model.Tweet;


/**
 * Created by grk on 05/12/14.
 */
public class TweetsAdapter extends BaseAdapter {

    private List<Tweet> mTweets;


    public void setTweets(List<Tweet> tweets) {
        this.mTweets = tweets;
        notifyDataSetChanged();
    }

    public List<Tweet> getTweets() {
        return mTweets;
    }

    @Override
    public int getCount() {
        return (mTweets == null ? 0 : mTweets.size());
    }

    @Override
    public Tweet getItem(int position) {
        return mTweets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (null == convertView) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item, parent, false);
        }

        Tweet t = getItem(position);

        TextView messageView = (TextView) convertView.findViewById(R.id.content);
        messageView.setText(t.getContent());


        TextView timeView = (TextView) convertView.findViewById(R.id.date);
        timeView.setText(t.getAt());

        TextView handleView = (TextView) convertView.findViewById(R.id.handle);
        handleView.setText(t.getBy());


        return convertView;
    }

}

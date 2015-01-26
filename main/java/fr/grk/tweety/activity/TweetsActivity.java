package fr.grk.tweety.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import fr.grk.tweety.R;
import fr.grk.tweety.fragment.TweetsFragment;


/**
 * Created by grk on 05/12/14.
 */
public class TweetsActivity extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweets_activity);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //*
        if (savedInstanceState == null) {
            TweetsFragment tweetsFragment = new TweetsFragment();
            tweetsFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, tweetsFragment)
                    .commit();
        }
        //*/

    }
}

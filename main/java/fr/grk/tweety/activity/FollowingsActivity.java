package fr.grk.tweety.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import fr.grk.tweety.R;
import fr.grk.tweety.fragment.FollowFragment;

/**
 * Created by grk on 26/01/15.
 */
public class FollowingsActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_follow);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            FollowFragment followFragment = new FollowFragment();
            followFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.follows_list, followFragment)
                    .commit();
        }
    }

}

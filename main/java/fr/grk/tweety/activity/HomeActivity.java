package fr.grk.tweety.activity;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import fr.grk.tweety.R;
import fr.grk.tweety.fragment.PostTweetFragment;
import fr.grk.tweety.fragment.ReadingListFragment;

public class HomeActivity extends ActionBarActivity implements PostTweetFragment.OnTweetPostedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void readingListChanged() {
        //*

        //*/

        //*

        ReadingListFragment readingListFragment = (ReadingListFragment) getSupportFragmentManager().findFragmentById(R.id.users_list);
        Log.e("HOME", readingListFragment.toString());

        readingListFragment.reloadList();

        /*
        ReadingListFragment newFragment = new ReadingListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction.replace(R.id.users_list, newFragment);
        transaction.remove(readingListFragment);
        transaction.add(R.id.users_list, newFragment);

        //transaction.addToBackStack(null);
        transaction.commit();
        //*/


    }
}

package fr.grk.tweety.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;

import fr.grk.tweety.R;
import fr.grk.tweety.fragment.ReadingListFragment;
import fr.grk.tweety.fragment.TweetsFragment;
import fr.grk.tweety.fragment.UsersFragment;
import fr.grk.tweety.model.User;
import fr.grk.tweety.utils.AccountManager;
import fr.grk.tweety.utils.ApiClient;
import fr.grk.tweety.utils.ReloadFragmentInterface;

public class HomeActivity extends ActionBarActivity implements ReloadFragmentInterface, ActionBar.TabListener {


    private AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ProgressDialog progressDialog;
    private static final int TABS_COUNT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        final ActionBar actionBar = getSupportActionBar();

        //getActionBar()
        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        actionBar.setHomeButtonEnabled(false);

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

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

        switch (id){
            case R.id.action_settings:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_details:
                showUserDetails();
                return true;
            default :
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void showUserDetails(){
        final Context context = this;

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(context.getString(R.string.connecting));
        progressDialog.show();

        new AsyncTask<String, Void, User>() {

            @Override
            protected User doInBackground(String... params) {
                try {
                    String handle = params[0];
                    return new ApiClient().getUser(handle);
                } catch (IOException e) {
                    Log.e(HomeActivity.class.toString(), "follow failed", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(User user) {
                progressDialog.dismiss();
                if (user != null){
                    Intent intent = new Intent(context, TweetsActivity.class);
                    intent.putExtras(TweetsFragment.newArguments(user));
                    startActivity(intent);
                } else {
                    Toast.makeText(context, R.string.login_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                progressDialog.dismiss();
            }
        }.execute(AccountManager.getUserHandle(context));


    }

    @Override
    public void readingListChanged() {

        //ReadingListFragment readingListFragment = (ReadingListFragment) getSupportFragmentManager().findFragmentById(R.id.pager);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof ReadingListFragment) {
                ((ReadingListFragment) fragment).reloadList();
            }
        }
        //ViewPager pager = (ViewPager) getSupportFragmentManager().findFragmentById(R.id.reading_list_fragment);

        //getSupportFragmentManager().findFragmentByTag()
        //Log.e("HOME", readingListFragment.toString());

        //readingListFragment.reloadList();

    }

    @Override
    public void discoverListChanged() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof ReadingListFragment) {
                ((ReadingListFragment) fragment).reloadList();
            } else if (fragment instanceof UsersFragment) {
                ((UsersFragment) fragment).reloadList();
            }
        }
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }


    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new ReadingListFragment();
                default:
                    return new UsersFragment();
            }
        }


        @Override
        public int getCount() {
            return TABS_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Timeline";
                default:
                    return "Decouverte";
            }
        }
    }


}

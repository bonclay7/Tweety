package fr.grk.tweety.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import fr.grk.tweety.R;
import fr.grk.tweety.utils.AccountManager;
import fr.grk.tweety.utils.ApiClient;
import fr.grk.tweety.utils.SessionDbDataSource;

public class LoginActivity extends ActionBarActivity {

    private EditText mHandleText;
    private EditText mPasswordText;
    private ProgressDialog progressDialog;
    private Context context = this;
    private SessionDbDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mHandleText = (EditText) findViewById(R.id.handle);
        mPasswordText = (EditText) findViewById(R.id.password);

        findViewById(R.id.connect_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }


    private void login(){
        final String handle = mHandleText.getText().toString().trim();
        String password = mPasswordText.getText().toString();

        if (handle.isEmpty()) {
            mHandleText.setError(getString(R.string.required));
            mHandleText.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            mPasswordText.setError(getString(R.string.required));
            mPasswordText.requestFocus();
            return;
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.connecting));
        progressDialog.show();

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                try {
                    String handle = params[0];
                    String password = params[1];
                    return new ApiClient().login(handle, password);
                } catch (IOException e) {
                    Log.e(LoginActivity.class.getName(), "Login failed", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String token) {
                progressDialog.dismiss();
                if (token != null) {
                    AccountManager.login(context, token, handle);
                    dataSource = new SessionDbDataSource(LoginActivity.this);
                    dataSource.open();
                    dataSource.saveUserSession(handle, token);
                    dataSource.close();
                    startActivity(new Intent(context, HomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(context, R.string.login_error, Toast.LENGTH_SHORT).show();
                }
            }

        }.execute(handle, password);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_connection, menu);
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

        if (id == R.id.home){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }




        return super.onOptionsItemSelected(item);
    }
}

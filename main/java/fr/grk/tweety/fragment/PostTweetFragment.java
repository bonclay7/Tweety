package fr.grk.tweety.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import fr.grk.tweety.R;
import fr.grk.tweety.utils.AccountManager;
import fr.grk.tweety.utils.ApiClient;


public class PostTweetFragment extends DialogFragment implements DialogInterface.OnShowListener, DialogInterface.OnDismissListener {
    private static final int LOADER_TWEETS = 10;
    private static final String ARG_USER = "user";

    private String mUserHandle;
    private String mToken;

    private EditText mMessageText;
    private boolean postSucceed = false;

    private OnTweetPostedListener mCallback;


    public interface OnTweetPostedListener {
        public void readingListChanged();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnTweetPostedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnTweetPostedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserHandle = AccountManager.getUserHandle(getActivity());
        mToken = AccountManager.getUserToken(getActivity());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_post_tweet, null);
        mMessageText = (EditText) view.findViewById(R.id.post_content);


        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.new_tweety)
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        dialog.setOnShowListener(this);
        return dialog;
    }


    @Override
    public void onShow(DialogInterface dialog) {
        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postTweet();
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (postSucceed) updateParentList();
    }

    private void updateParentList(){

    }

    public void postTweet() {
        String message = mMessageText.getText().toString();

        if (message.isEmpty()) {
            mMessageText.setError(getString(R.string.required));
            mMessageText.requestFocus();
            return;
        }


        new AsyncTask<String, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {
                try {
                    String handle = params[0];
                    String token = params[1];
                    String message = params[2];
                    return new ApiClient().postTweet(handle, token, message);
                } catch (IOException e) {
                    Log.e(LoginFragment.class.getName(), "Login failed", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Boolean resultOK) {
                if (resultOK) {
                    Fragment target = getTargetFragment();
                    if (target != null) {
                        target.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
                    }
                    mCallback.readingListChanged();
                    postSucceed = true;
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), R.string.login_error, Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(mUserHandle, mToken, message);
    }
}

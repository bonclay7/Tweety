package fr.grk.tweety.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
/**
 * Created by grk on 22/01/15.
 */
public class AccountManager {

    private static final String PREF_API_TOKEN = "apiToken";
    private static final String PREF_API_HANDLE = "apiHandle";
    public static final String P_USERS_TYPE_FOLLOWERS = "followers";
    public static final String P_USERS_TYPE_FOLLOWINGS = "followings";

    public static boolean isConnected(Context context) {
        return getUserToken(context) != null;
    }

    public static String getUserToken(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(PREF_API_TOKEN, null);
    }

    public static String getUserHandle(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(PREF_API_HANDLE, null);
    }

    public static void login(Context context, String token, String handle) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref.edit()
                .putString(PREF_API_TOKEN, token)
                .putString(PREF_API_HANDLE, handle)
                .apply();
    }

    public static void logout(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref.edit()
                .remove(PREF_API_TOKEN)
                .remove(PREF_API_HANDLE)
                .apply();
    }

}

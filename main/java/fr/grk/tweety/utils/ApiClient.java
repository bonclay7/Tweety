package fr.grk.tweety.utils;

import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import fr.grk.tweety.model.Tweet;
import fr.grk.tweety.model.User;
import fr.grk.tweety.model.Wrapper;

/**
 * Created by grk on 22/01/15.
 */
public class ApiClient {

    private static final String LOG_TAG = "API_CLIENT";
    private static final String API_BASE = "http://54.149.229.137:8080/MicroBlogging/api/";
    private static int TIMEOUT_VALUE = 10000;
    private static final String HOST_ID = Build.DEVICE + "-" + Build.VERSION.SDK_INT;


    public String login(String handle, String password) throws IOException {

        HttpParams httpParameters = new BasicHttpParams();

        HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT_VALUE);
        HttpConnectionParams.setSoTimeout(httpParameters, TIMEOUT_VALUE);

        String url = new URL(API_BASE + "authentication/" + ":" + handle).toString();


        HttpPost request = new HttpPost(url);
        request.addHeader(new BasicHeader("password", password));
        request.addHeader(new BasicHeader("hostID", HOST_ID));


        HttpClient client = new DefaultHttpClient(httpParameters);
        HttpResponse result = client.execute(request);


        if (result.getStatusLine().getStatusCode() == 200) {
            return (new Gson().fromJson(EntityUtils.toString(result.getEntity()), Wrapper.class)).getSession().getToken();
        } else {
            return null;
        }


    }

    public List<Tweet> getReadingList(String handle, String token) throws IOException {

        HttpParams httpParameters = new BasicHttpParams();

        HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT_VALUE);
        HttpConnectionParams.setSoTimeout(httpParameters, TIMEOUT_VALUE);

        String url = new URL(API_BASE + "tweets/:" + handle + "/reading_list").toString();


        HttpGet request = new HttpGet(url);
        request.addHeader(new BasicHeader("Authorization", token + "." + HOST_ID));


        HttpClient client = new DefaultHttpClient(httpParameters);
        HttpResponse result = client.execute(request);

        String res = EntityUtils.toString(result.getEntity());

        if (result.getStatusLine().getStatusCode() == 200) {
            return (new Gson().fromJson(res, Wrapper.class)).getTweets();
        } else {
            return new ArrayList<>();
        }


    }

    public boolean postTweet(String handle, String token, String message) throws IOException {

        HttpParams httpParameters = new BasicHttpParams();

        HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT_VALUE);
        HttpConnectionParams.setSoTimeout(httpParameters, TIMEOUT_VALUE);

        String url = new URL(API_BASE + "tweets/:" + handle + "/").toString();

        HttpPost request = new HttpPost(url);

        token += "."+HOST_ID;
        request.addHeader(new BasicHeader("Authorization", token));

        message = "{\"message\": \"" + message + "\"}";
        StringEntity input = new StringEntity(message);
        input.setContentType("application/json");
        request.setEntity(input);


        HttpClient client = new DefaultHttpClient(httpParameters);
        HttpResponse result = client.execute(request);

        Log.e(LOG_TAG, url);
        Log.e(LOG_TAG, message);
        Log.e(LOG_TAG, token);
        Log.e(LOG_TAG, result.getStatusLine().getStatusCode() + "");


        if (result.getStatusLine().getStatusCode() == 201) {
            return true;
        } else {
            return false;
        }

    }

    public List<User> getUsers(String handle) throws IOException {
        InputStream stream = new URL(API_BASE + "followings/:" + handle + "/discover").openStream();
        String response = IOUtils.toString(stream);
        return (new Gson().fromJson(response, Wrapper.class)).getStats();
    }

    public User getUser(String handle) throws IOException {
        InputStream stream = new URL(API_BASE + "users/stats/:" + handle).openStream();
        String response = IOUtils.toString(stream);
        return (new Gson().fromJson(response, Wrapper.class)).getUser();
    }

    public List<User> getFollowers(String handle) throws IOException {
        InputStream stream = new URL(API_BASE + "followers/:" + handle).openStream();
        String response = IOUtils.toString(stream);
        return (new Gson().fromJson(response, Wrapper.class)).getUsers();
    }

    public List<User> getFollowings(String handle) throws IOException {
        InputStream stream = new URL(API_BASE + "followings/:" + handle).openStream();
        String response = IOUtils.toString(stream);
        return (new Gson().fromJson(response, Wrapper.class)).getUsers();
    }


    public boolean follow(String handle, String followeeHandle, String token) throws IOException {
        HttpParams httpParameters = new BasicHttpParams();

        HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT_VALUE);
        HttpConnectionParams.setSoTimeout(httpParameters, TIMEOUT_VALUE);

        String url = new URL(API_BASE + "followings/:" + handle + "/follow/:" + followeeHandle).toString();
        Log.e(LOG_TAG, url);


        HttpPut request = new HttpPut(url);
        request.addHeader(new BasicHeader("Authorization", token+"."+HOST_ID));

        HttpClient client = new DefaultHttpClient(httpParameters);
        HttpResponse result = client.execute(request);

        Log.e(LOG_TAG, request.toString());
        Log.e(LOG_TAG, result.getStatusLine().getStatusCode() + "");


        if (result.getStatusLine().getStatusCode() == 202) {
            return true;
        } else {
            return false;
        }
    }


    public boolean unfollow(String handle, String followeeHandle, String token) throws IOException {
        HttpParams httpParameters = new BasicHttpParams();

        HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT_VALUE);
        HttpConnectionParams.setSoTimeout(httpParameters, TIMEOUT_VALUE);

        String url = new URL(API_BASE + ":" + handle + "/follow/" + followeeHandle).toString();
        Log.e(LOG_TAG, url);


        HttpDelete request = new HttpDelete(url);
        request.addHeader(new BasicHeader("Authorization", token+"."+HOST_ID));


        HttpClient client = new DefaultHttpClient(httpParameters);
        HttpResponse result = client.execute(request);

        Log.e(LOG_TAG, request.toString());
        Log.e(LOG_TAG, result.getStatusLine().getStatusCode() + "");


        if (result.getStatusLine().getStatusCode() == 204) {
            return true;
        } else {
            return false;
        }
    }


    public List<Tweet> getUserTweets(String handle) throws IOException {
        InputStream stream = new URL(API_BASE + "tweets/:" + handle).openStream();
        String response = IOUtils.toString(stream);
        return new Gson().fromJson(response, Wrapper.class).getTweets();
    }

}
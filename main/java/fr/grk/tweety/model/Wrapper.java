package fr.grk.tweety.model;

import java.util.List;

/**
 * Created by grk on 22/01/15.
 */
public class Wrapper {
    private String server;
    private Session session;
    private List<Tweet> tweets;
    private List<User> stats;


    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public List<User> getStats() {
        return stats;
    }

    public void setStats(List<User> stats) {
        this.stats = stats;
    }
}

package fr.grk.tweety.model;

import java.util.List;

/**
 * Created by grk on 22/01/15.
 */
public class Wrapper {
    private String server;
    private Session session;
    private User user;
    private List<Tweet> tweets;
    private List<User> stats;
    private List<User> users;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

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

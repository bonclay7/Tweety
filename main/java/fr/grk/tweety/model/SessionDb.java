package fr.grk.tweety.model;

/**
 * Created by grk on 27/01/15.
 */
public class SessionDb {

    private long id;
    private String token;
    private String handle;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    @Override
    public String toString() {
        return handle +"-" +token;
    }
}

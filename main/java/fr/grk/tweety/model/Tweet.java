package fr.grk.tweety.model;

/**
 * Created by grk on 22/01/15.
 */
public class Tweet {
    private String content;
    private String by;
    private String at;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getAt() {
        return at;
    }

    public void setAt(String at) {
        this.at = at;
    }
}

package fr.grk.tweety.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by grk on 22/01/15.
 */
public class User implements Parcelable {

    private String handle;
    private String picture;
    //private String email;
    private int followers;
    private int follows;
    private boolean followed;

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(handle);
        dest.writeString(picture);
        dest.writeInt(followers);
        dest.writeInt(follows);
        dest.writeByte((byte) (followed ? 1 : 0));
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            User user = new User();
            user.handle = source.readString();
            user.picture = source.readString();
            user.followers = source.readInt();
            user.follows = source.readInt();
            user.followed = source.readByte() != 0;
            return user;
        }

        @Override
        public User[] newArray(int size) {
            return new User[0];
        }
    };
}

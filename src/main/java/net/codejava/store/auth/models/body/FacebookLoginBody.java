package net.codejava.store.auth.models.body;

public class FacebookLoginBody {
    String facebookUserID;
    String fullname;
    String avatarUrl;
    long birthDay = -1;
    String email;
    boolean gender;

    public String getFacebookUserID() {
        return facebookUserID;
    }

    public void setFacebookUserID(String facebookUserID) {
        this.facebookUserID = facebookUserID;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public long getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(long birthDay) {
        this.birthDay = birthDay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
}

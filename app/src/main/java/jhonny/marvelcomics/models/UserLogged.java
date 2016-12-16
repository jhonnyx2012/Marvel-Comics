package jhonny.marvelcomics.models;

import com.google.gson.Gson;

public class UserLogged {
    private String email;
    private String name;
    private String gender;
    private String profileImageUrl;
    private String link;

    public UserLogged(String email, String name, String gender, String link, String profileImageUrl) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.link = link;
        this.profileImageUrl = profileImageUrl;
    }

    public String getLink() {
        return link;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAsString() {
        return new Gson().toJson(this);
    }
}

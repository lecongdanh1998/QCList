package vn.edu.poly.qclist.RetrofitClient.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobi_app_level")
    @Expose
    private String mobi_app_level;

    public User(String name, String email, String mobi_app_level) {
        this.name = name;
        this.email = email;
        this.mobi_app_level = mobi_app_level;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobi_app_level() {
        return mobi_app_level;
    }

    public void setMobi_app_level(String mobi_app_level) {
        this.mobi_app_level = mobi_app_level;
    }
}

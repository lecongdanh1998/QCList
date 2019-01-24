package vn.edu.poly.qclist.RetrofitClient.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class user_info implements Serializable {
    @SerializedName("mobi_app_level")
    @Expose
    private String mobi_app_level;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;

    public user_info(String mobi_app_level, String name, String email) {
        this.mobi_app_level = mobi_app_level;
        this.name = name;
        this.email = email;
    }

    public user_info() {
    }

    public String getMobi_app_level() {
        return mobi_app_level;
    }

    public void setMobi_app_level(String mobi_app_level) {
        this.mobi_app_level = mobi_app_level;
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


}

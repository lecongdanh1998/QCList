package vn.edu.poly.qclist.RetrofitClient.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {
    @SerializedName("user_info")
    @Expose
    private ArrayList<user_info> user_info;

    public ArrayList<user_info> getData() {
        return user_info;
    }

    public void setData(ArrayList<user_info> user_info) {
        this.user_info = user_info;
    }

}

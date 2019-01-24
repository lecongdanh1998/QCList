package vn.edu.poly.qclist.RetrofitClient.Approve;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Approve {
    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

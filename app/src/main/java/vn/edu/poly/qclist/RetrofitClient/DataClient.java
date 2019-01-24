package vn.edu.poly.qclist.RetrofitClient;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import vn.edu.poly.qclist.RetrofitClient.User.User;

public interface DataClient {
    @POST("/auth/signin")
    Call<User> LoginData(@Body HashMap<String, String> hashMap);

}

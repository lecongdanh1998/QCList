package vn.edu.poly.qclist.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.GET;
import retrofit2.http.POST;
import vn.edu.poly.qclist.RetrofitClient.Approve.Approve;
import vn.edu.poly.qclist.RetrofitClient.Getlist_security.Product;
import vn.edu.poly.qclist.RetrofitClient.Login.Data;

public interface DataClient {
    @FormUrlEncoded
    @POST("/auth/signin")
    Call<Data> LoginData( @Field("login") String login,
                          @Field("password") String password
                          );

    @FormUrlEncoded
    @POST("/api/search/qc_approve")
    Call<Approve> ApproveData(
            @Field("gate_id") String gate_id,
            @Field("mc") String mc,
            @Field("moldy") String moldy,
            @Field("burn") String burn,
            @Field("odor") String odor,
            @Field("remarks") String remarks
    );

    @GET("/api/search/getlist_security")
    Call<ArrayList<Product>> ProductData();

}

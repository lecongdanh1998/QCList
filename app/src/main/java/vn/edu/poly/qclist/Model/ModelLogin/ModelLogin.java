package vn.edu.poly.qclist.Model.ModelLogin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.poly.qclist.Component.BaseActivity;
import vn.edu.poly.qclist.RetrofitClient.APIUtils;
import vn.edu.poly.qclist.RetrofitClient.DataClient;
import vn.edu.poly.qclist.RetrofitClient.Login.Data;
import vn.edu.poly.qclist.RetrofitClient.Login.user_info;

public class ModelLogin {
    Context context;
    Activity activity;
    ModelReponsetoPresenterLogin callback1;
    private ProgressDialog progressDialog;

    public ModelLogin(Context context, ModelReponsetoPresenterLogin callback1) {
        this.context = context;
        this.activity = (Activity) context;
        this.callback1 = callback1;
        progressDialog = new ProgressDialog(context);
    }

    private void setContentDialog(String title, String messager) {
        progressDialog.setTitle(title);
        progressDialog.setMessage(messager);
    }

    public void SignIn(final String email, String password) {
        setContentDialog("Đăng nhập", "Vui lòng chờ");
        progressDialog.show();
        progressDialog.setCancelable(false);
        DataClient dataClient = APIUtils.getData();
        Call<Data> callback = dataClient.LoginData(email, password);
        callback.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.body() == null) {
                    progressDialog.dismiss();
                } else {
                    if (response.body().getData() == null) {
                        Toast.makeText(context, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    } else {
                        BaseActivity.editorgetMobi_app_level = BaseActivity.dataResultgetMobi_app_level.edit();
                        BaseActivity.editorgetMobi_app_level.putString("Level", response.body().getData().get(0).getMobi_app_level());
                        BaseActivity.editorgetMobi_app_level.commit();
                        callback1.onSignInSuccess();
                    }
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(context, "Vui lòng kiểm tra kết nối Internet", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

}

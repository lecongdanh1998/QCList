package vn.edu.poly.qclist.Model.ModelLogin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.poly.qclist.RetrofitClient.APIUtils;
import vn.edu.poly.qclist.RetrofitClient.DataClient;
import vn.edu.poly.qclist.RetrofitClient.User.User;

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
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("login", email);
        hashMap.put("password", password);
        Call<User> callback = dataClient.LoginData(hashMap);
        callback.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() == null) {
                    Toast.makeText(context, "Email hoặc password không đúng", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {
                    callback1.onSignInSuccess();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "Vui lòng kiểm tra kết nối Internet", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

}

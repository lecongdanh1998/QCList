package vn.edu.poly.qclist.Presenter.PresenterLogin;

import android.app.Activity;
import android.content.Context;

import vn.edu.poly.qclist.Model.ModelLogin.ModelLogin;
import vn.edu.poly.qclist.Model.ModelLogin.ModelReponsetoPresenterLogin;


public class PresenterLogin implements ModelReponsetoPresenterLogin {
    Context context;
    Activity activity;
    PresenterReponsetoViewLogin callback;
    ModelLogin modelLogin;


    public PresenterLogin(Context context, PresenterReponsetoViewLogin callback) {
        this.context = context;
        this.activity = (Activity) context;
        this.callback = callback;
        modelLogin = new ModelLogin(context, this);
    }

    public void SignIn(final String email, String password) {
        modelLogin.SignIn(email, password);
    }

    @Override
    public void onSignInSuccess() {
        callback.onSignInSuccess();
    }
}

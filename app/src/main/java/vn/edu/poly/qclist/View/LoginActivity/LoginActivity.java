package vn.edu.poly.qclist.View.LoginActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import vn.edu.poly.qclist.Component.BaseActivity;
import vn.edu.poly.qclist.Presenter.PresenterLogin.PresenterLogin;
import vn.edu.poly.qclist.Presenter.PresenterLogin.PresenterReponsetoViewLogin;
import vn.edu.poly.qclist.R;

public class LoginActivity extends BaseActivity implements PresenterReponsetoViewLogin, View.OnClickListener {
    PresenterLogin presenterLogin;
    private EditText inputEmail, inputPassword;
    private Button btnLogin;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initControl();
        initData();
        initOnClick();
    }

    private void initOnClick() {
        btnLogin.setOnClickListener(this);
    }

    private void initData() {
        presenterLogin = new PresenterLogin(this, this);
    }

    private void initControl() {
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btn_login);
    }

    @Override
    public void onSignInSuccess() {
//        intentView();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                email = inputEmail.getText().toString().trim();
                password = inputPassword.getText().toString().trim();
                presenterLogin.SignIn(email, password);
                break;
        }
    }

    private void intentView(Class c) {
        Intent intent = new Intent(LoginActivity.this, c);
        startActivity(intent);
        finish();
    }
}

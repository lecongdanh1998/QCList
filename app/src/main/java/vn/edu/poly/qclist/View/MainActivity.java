package vn.edu.poly.qclist.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import vn.edu.poly.qclist.Component.BaseActivity;
import vn.edu.poly.qclist.R;
import vn.edu.poly.qclist.View.LoginActivity.LoginActivity;
import vn.edu.poly.qclist.View.QCList.QCListActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener  {
    Button btn_purchase_list, btn_qc_list, btn_sign_out;
    String level = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControl();
        initData();
        initOnClick();
    }

    private void initOnClick() {
        btn_sign_out.setOnClickListener(this);
        btn_purchase_list.setOnClickListener(this);
        btn_qc_list.setOnClickListener(this);
    }

    private void initData() {
        level = dataResultgetMobi_app_level.getString("Level", "");
        if (!level.toString().equals("")) {
            if (level.equals("2.0")) {
                btn_purchase_list.setEnabled(false);
                btn_purchase_list.setAlpha(0.5f);
                btn_qc_list.setEnabled(true);
                btn_qc_list.setAlpha(1);
            } else if (level.equals("1.0")) {
                btn_purchase_list.setEnabled(true);
                btn_purchase_list.setAlpha(1);
                btn_qc_list.setEnabled(false);
                btn_qc_list.setAlpha(0.5f);
            }
        }
    }

    private void initControl() {
        btn_purchase_list = findViewById(R.id.btn_purchase_list);
        btn_qc_list = findViewById(R.id.btn_qc_list);
        btn_sign_out = findViewById(R.id.btn_sign_out);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_purchase_list:
                Toast.makeText(this, "Chưa có chức năng", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_qc_list:
                intentView(QCListActivity.class);
                break;
            case R.id.btn_sign_out:
                editorgetMobi_app_level = dataResultgetMobi_app_level.edit();
                editorgetMobi_app_level.putString("Level", "");
                editorgetMobi_app_level.commit();
                intentView(LoginActivity.class);
                break;
        }
    }

    private void intentView(Class c) {
        Intent intent = new Intent(MainActivity.this, c);
        startActivity(intent);
        finish();
    }
}

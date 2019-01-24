package vn.edu.poly.qclist.View.QCList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import vn.edu.poly.qclist.Component.BaseActivity;
import vn.edu.poly.qclist.Presenter.PresenterQCList.PresenterQCList;
import vn.edu.poly.qclist.Presenter.PresenterQCList.PresenterReponsetoViewQCList;
import vn.edu.poly.qclist.R;
import vn.edu.poly.qclist.View.BarcodeActivity.BarCodeActivity;
import vn.edu.poly.qclist.View.LoginActivity.LoginActivity;
import vn.edu.poly.qclist.View.QualityAnalysit.QualityAnalysitActivity;

public class QCListActivity extends BaseActivity implements PresenterReponsetoViewQCList, View.OnClickListener {
    PresenterQCList presenterQCList;
    Button btn_scan_barcode, txt_result;
    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qclist);
        initControl();
        initData();
        initOnClick();

    }

    private void initOnClick() {
        btn_scan_barcode.setOnClickListener(this);
        txt_result.setOnClickListener(this);
    }

    private void initData() {
        result = dataResult.getString("Result", "");
        if (!result.toString().equals("")) {
            txt_result.setText(result);
        }
        presenterQCList = new PresenterQCList(this, this);
    }

    private void initControl() {
        txt_result = findViewById(R.id.txt_result);
        btn_scan_barcode = findViewById(R.id.btn_scan_barcode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scan_barcode:
                intentView(BarCodeActivity.class);
                break;
            case R.id.txt_result:
                editorQuality = dataResultQuality.edit();
                editorQuality.putString("Quality", result);
                editorQuality.commit();
                intentView(QualityAnalysitActivity.class);
                break;
        }
    }

    private void intentView(Class c) {
        Intent intent = new Intent(QCListActivity.this, c);
        startActivity(intent);
        finish();
    }
}

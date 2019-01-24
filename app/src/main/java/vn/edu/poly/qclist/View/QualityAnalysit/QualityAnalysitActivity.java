package vn.edu.poly.qclist.View.QualityAnalysit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import vn.edu.poly.qclist.Component.BaseActivity;
import vn.edu.poly.qclist.R;
import vn.edu.poly.qclist.View.QCList.QCListActivity;

public class QualityAnalysitActivity extends BaseActivity implements View.OnClickListener {
    String quality = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_analysit);
        initControl();
        initData();
        initOnClick();
    }

    private void initOnClick() {
    }

    private void initData() {
        quality = dataResultQuality.getString("Quality", "");
        if (!quality.toString().equals("")) {
            Toast.makeText(this, quality, Toast.LENGTH_SHORT).show();
        }
    }

    private void initControl() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void onBackPressed() {
        intentView(QCListActivity.class);
    }

    private void intentView(Class c) {
        Intent intent = new Intent(QualityAnalysitActivity.this, c);
        startActivity(intent);
        finish();
    }
}

package vn.edu.poly.qclist.View.QualityAnalysit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import vn.edu.poly.qclist.Component.BaseActivity;
import vn.edu.poly.qclist.Model.ModelQualityAnalysis.QualityAnalysisModelImpl;
import vn.edu.poly.qclist.Presenter.PresenterQualysis.QualityAnalysisPresenter;
import vn.edu.poly.qclist.Presenter.PresenterQualysis.QualityAnalysisPresenterImpl;
import vn.edu.poly.qclist.R;
import vn.edu.poly.qclist.RetrofitClient.Approve.QCApprove;
import vn.edu.poly.qclist.View.QCList.QCListActivity;

public class QualityAnalysitActivity extends BaseActivity implements View.OnClickListener, QualityAnalysisView {
    private String gateID = "";
    private EditText mEditTextGateId, mEditTextMC, mEditTextMoldy, mEditTextBurn, mEditTextOdor, mEditTextRemark;
    private Button mButtonApprove;
    private QualityAnalysisPresenter mQualityAnalysisPresenter;
    String quality = "";
    TextView nameBarcode;
    ImageView imt_back_tool_bar_qc_list;
    TextView txt_tool_bar_qc_list, txt_title_tool_bar_qc_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_analysit);
        initDataIntent();
        initView();
        initData();
        initOnClick();
    }

    private void initDataIntent() {

    }

    private void initView() {
        imt_back_tool_bar_qc_list = findViewById(R.id.imt_back_tool_bar_qc_list);
        nameBarcode = findViewById(R.id.nameBarcode);
        mEditTextGateId = findViewById(R.id.edt_gateId_quality_analysis);
        mEditTextMC = findViewById(R.id.edt_mc_quality_analysis);
        mEditTextMoldy = findViewById(R.id.edt_moldy_quality_analysis);
        mEditTextBurn = findViewById(R.id.edt_burn_quality_analysis);
        mEditTextOdor = findViewById(R.id.edt_odor_quality_analysis);
        mEditTextRemark = findViewById(R.id.edt_remark_quality_analysis);
        mEditTextMoldy = findViewById(R.id.edt_gateId_quality_analysis);
        mButtonApprove = findViewById(R.id.btn_approve_quality_analysis);
        txt_title_tool_bar_qc_list = findViewById(R.id.txt_title_tool_bar_qc_list);
        txt_tool_bar_qc_list = findViewById(R.id.txt_tool_bar_qc_list);
    }

    private void initOnClick() {
        imt_back_tool_bar_qc_list.setOnClickListener(this);
        mButtonApprove.setOnClickListener(this);
    }

    private void initData() {
        txt_tool_bar_qc_list.setText("QC List");
        txt_title_tool_bar_qc_list.setText("Quality Analysis");
        gateID = dataResult.getString("id", "");
        quality = dataResult.getString("Result", "");
        if (!gateID.toString().equals("")) {
            mEditTextGateId.setText(gateID);
        }
        if (!quality.toString().equals("")) {
            nameBarcode.setText(quality);
        }
        mQualityAnalysisPresenter = new QualityAnalysisPresenterImpl(new QualityAnalysisModelImpl(this), this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_approve_quality_analysis:
                pushdataTosever();
                break;
            case R.id.imt_back_tool_bar_qc_list:
                onBackPressed();
                break;
        }
    }


    private void pushdataTosever() {
        String gate_id = mEditTextGateId.getText().toString().trim();
        String mc = mEditTextMC.getText().toString().trim();
        String moldy = mEditTextMoldy.getText().toString().trim();
        String burn = mEditTextBurn.getText().toString().trim();
        String odor = mEditTextOdor.getText().toString().trim();
        String remarks = mEditTextRemark.getText().toString().trim();
        //create new data mQCApprove
        QCApprove mQcApprove = new QCApprove(gate_id, mc, moldy, burn, odor, remarks);
        //call listener send data to sever
        mQualityAnalysisPresenter.pushDataApproveToSever(mQcApprove);
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

    @Override
    public void onPushDataSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        intentView(QCListActivity.class);
    }

    @Override
    public void onPushDataError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

}

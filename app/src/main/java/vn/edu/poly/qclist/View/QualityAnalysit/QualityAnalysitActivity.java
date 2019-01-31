package vn.edu.poly.qclist.View.QualityAnalysit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private EditText mEditTextGateId, mEditTextMC, mEditTextMoldy, mEditTextBurn, mEditTextOdor, mEditTextRemark, edt_estimated_bags_quality_analysis, edt_approx_quantity_quality_analysi, edt_supplier_quality_analysis;
    private Button mButtonApprove;
    private QualityAnalysisPresenter mQualityAnalysisPresenter;
    String quality = "";
    TextView nameBarcode;
    ImageView imt_back_tool_bar_qc_list;
    TextView txt_tool_bar_qc_list, txt_title_tool_bar_qc_list;
    String level = "";
    String approx_quantity = "";
    String supplier_name = "";
    String estimated_bags = "";
    LinearLayout linearLayout_Purchase, linearLayout_QCList;

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
        edt_estimated_bags_quality_analysis = findViewById(R.id.edt_estimated_bags_quality_analysis);
        edt_approx_quantity_quality_analysi = findViewById(R.id.edt_approx_quantity_quality_analysis);
        edt_supplier_quality_analysis = findViewById(R.id.edt_supplier_quality_analysis);
        linearLayout_QCList = findViewById(R.id.linearLayout_QCList);
        linearLayout_Purchase = findViewById(R.id.linearLayout_Purchase);
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
        txt_tool_bar_qc_list.setOnClickListener(this);
        imt_back_tool_bar_qc_list.setOnClickListener(this);
        mButtonApprove.setOnClickListener(this);
    }

    private void initData() {
        level = dataResultgetMobi_app_level.getString("Level", "");
        approx_quantity = dataResult.getString("approx_quantity", "");
        supplier_name = dataResult.getString("supplier_name", "");
        estimated_bags = dataResult.getString("estimated_bags", "");
        txt_title_tool_bar_qc_list.setText("Quality Analysis");
        gateID = dataResult.getString("id", "");
        quality = dataResult.getString("Result", "");
        if (!level.toString().equals("")) {
            if (level.equals("2.0")) {
                linearLayout_Purchase.setVisibility(View.GONE);
                linearLayout_QCList.setVisibility(View.VISIBLE);
                txt_tool_bar_qc_list.setText("QC List");
            } else if (level.equals("1.0")) {
                edt_approx_quantity_quality_analysi.setText(approx_quantity);
                edt_estimated_bags_quality_analysis.setText(estimated_bags);
                edt_supplier_quality_analysis.setText(supplier_name);
                linearLayout_Purchase.setVisibility(View.VISIBLE);
                linearLayout_QCList.setVisibility(View.GONE);
                txt_tool_bar_qc_list.setText("Purchase List");
            }
        }
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
            case R.id.txt_tool_bar_qc_list:
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

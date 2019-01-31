package vn.edu.poly.qclist.Model.ModelQualityAnalysis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.poly.qclist.Component.BaseActivity;
import vn.edu.poly.qclist.RetrofitClient.APIUtils;
import vn.edu.poly.qclist.RetrofitClient.Approve.Approve;
import vn.edu.poly.qclist.RetrofitClient.Approve.QCApprove;
import vn.edu.poly.qclist.RetrofitClient.DataClient;
import vn.edu.poly.qclist.View.MainActivity;
import vn.edu.poly.qclist.View.QCList.QCListActivity;

public class QualityAnalysisModelImpl implements QualityAnalysisModel {

    private Context mContext;
    private Activity mActivity;
    private DataClient mDataClient;
    private String TAG = "QUALITY_ANALYSIS";
    String level = "";

    public QualityAnalysisModelImpl(Context mContext) {
        this.mContext = mContext;
        this.mActivity = (Activity) mContext;
        mDataClient = APIUtils.getData();
        level = BaseActivity.dataResultgetMobi_app_level.getString("Level", "");
    }

    @Override
    public void pushBarCodeToSever(final onPushBarCodeListener listener, QCApprove mQcApprover) {
        if (!level.toString().equals("")) {
            if (level.equals("2.0")) {
                Call<Approve> mApproveCall = mDataClient.ApproveData(
                        mQcApprover.getGate_id(),
                        mQcApprover.getMc(),
                        mQcApprover.getMoldy(),
                        mQcApprover.getBurn(),
                        mQcApprover.getOdor(),
                        mQcApprover.getRemarks()
                );
                mApproveCall.enqueue(new Callback<Approve>() {
                    @Override
                    public void onResponse(Call<Approve> call, Response<Approve> response) {
                        if (response.body() != null) {
                            listener.pushBarcodeSuccess(response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<Approve> call, Throwable t) {
                        Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
            } else if (level.equals("1.0")) {
                Call<Approve> mApproveCall = mDataClient.ApproveDataPurchase(
                        mQcApprover.getGate_id()
                );
                mApproveCall.enqueue(new Callback<Approve>() {
                    @Override
                    public void onResponse(Call<Approve> call, Response<Approve> response) {
                        if (response.body() != null) {
                            listener.pushBarcodeSuccess(response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<Approve> call, Throwable t) {
                        Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
        }


    }


}

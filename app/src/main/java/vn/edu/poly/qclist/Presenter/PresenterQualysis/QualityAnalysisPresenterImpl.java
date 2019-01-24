package vn.edu.poly.qclist.Presenter.PresenterQualysis;

import vn.edu.poly.qclist.Model.ModelQualityAnalysis.QualityAnalysisModel;
import vn.edu.poly.qclist.RetrofitClient.Approve.QCApprove;
import vn.edu.poly.qclist.View.QualityAnalysit.QualityAnalysisView;

public class QualityAnalysisPresenterImpl implements QualityAnalysisPresenter, QualityAnalysisModel.onPushBarCodeListener {

    private QualityAnalysisModel mQualityAnalysisModel;
    private QualityAnalysisView mQualityAnalysisView;

    public QualityAnalysisPresenterImpl(QualityAnalysisModel mQualityAnalysisModel, QualityAnalysisView mQualityAnalysisView) {
        this.mQualityAnalysisModel = mQualityAnalysisModel;
        this.mQualityAnalysisView = mQualityAnalysisView;
    }

    @Override
    public void pushBarcodeSuccess(String message) {
        if (mQualityAnalysisView != null){
            mQualityAnalysisView.onPushDataSuccess(message);
        }
    }

    @Override
    public void pushBarcodeError(String error) {
        if (mQualityAnalysisView != null){
            mQualityAnalysisView.onPushDataError(error);
        }
    }

    @Override
    public void pushDataApproveToSever(QCApprove mQcApprove) {
        if (mQualityAnalysisModel != null){
            mQualityAnalysisModel.pushBarCodeToSever(this, mQcApprove);
        }
    }

    @Override
    public void onDestroy() {
        mQualityAnalysisView = null;
    }
}

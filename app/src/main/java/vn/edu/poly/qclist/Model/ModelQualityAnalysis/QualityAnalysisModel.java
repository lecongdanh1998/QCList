package vn.edu.poly.qclist.Model.ModelQualityAnalysis;

import vn.edu.poly.qclist.RetrofitClient.Approve.QCApprove;

public interface QualityAnalysisModel {

    interface onPushBarCodeListener {
        void pushBarcodeSuccess(String message);

        void pushBarcodeError(String error);
    }

    void pushBarCodeToSever(onPushBarCodeListener listener, QCApprove mQcApprove);
}

package vn.edu.poly.qclist.Presenter.PresenterQualysis;

import vn.edu.poly.qclist.RetrofitClient.Approve.QCApprove;

public interface QualityAnalysisPresenter {
    void pushDataApproveToSever(QCApprove mQcApprove);

    void onDestroy();
}

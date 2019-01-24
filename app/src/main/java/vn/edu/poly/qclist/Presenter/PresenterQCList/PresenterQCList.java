package vn.edu.poly.qclist.Presenter.PresenterQCList;

import android.app.Activity;
import android.content.Context;

import vn.edu.poly.qclist.Model.ModelQCList.ModelQCList;
import vn.edu.poly.qclist.Model.ModelQCList.ModelReponsePresenterQCList;

public class PresenterQCList implements ModelReponsePresenterQCList {
    Context context;
    Activity activity;
    PresenterReponsetoViewQCList callback;
    ModelQCList modelQCList;

    public PresenterQCList(Context context, PresenterReponsetoViewQCList callback) {
        this.context = context;
        this.activity = (Activity) context;
        this.callback = callback;
        modelQCList = new ModelQCList(context, this);
    }
}

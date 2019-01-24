package vn.edu.poly.qclist.Model.ModelQCList;

import android.app.Activity;
import android.content.Context;


public class ModelQCList {
    Context context;
    Activity activity;
    ModelReponsePresenterQCList callback1;

    public ModelQCList(Context context, ModelReponsePresenterQCList callback1) {
        this.context = context;
        this.callback1 = callback1;
        this.activity = (Activity) context;
    }

}

package vn.edu.poly.qclist.Presenter.PresenterQCList;

import android.app.Activity;
import android.content.Context;

import vn.edu.poly.qclist.Adapter.AdapterQCList;
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

    public void initOnClickData(int position){
        modelQCList.initOnClickData(position);
    }

    public void ProductData(){
        modelQCList.ProductData();
    }


    @Override
    public void onDataQCList(AdapterQCList adapterQCList) {
        callback.onDataQCList(adapterQCList);
    }

    @Override
    public void OnClickData() {
        callback.OnClickData();
    }
}

package vn.edu.poly.qclist.Model.ModelTabLayout;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;
import vn.edu.poly.qclist.Adapter.AdapterTabLayoutFrangment;
import vn.edu.poly.qclist.R;

public class ModelTabLayoutBarCode {
    ModelReponsetoPresenterTabLayout callback;
    Activity activity;
    Context context;


    public ModelTabLayoutBarCode(ModelReponsetoPresenterTabLayout callback, Context context) {
        this.callback = callback;
        this.context = context;
        this.activity = (Activity) context;
    }




}

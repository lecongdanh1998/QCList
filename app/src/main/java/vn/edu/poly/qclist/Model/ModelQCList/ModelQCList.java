package vn.edu.poly.qclist.Model.ModelQCList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.poly.qclist.Adapter.AdapterQCList;
import vn.edu.poly.qclist.Component.BaseActivity;
import vn.edu.poly.qclist.RetrofitClient.APIUtils;
import vn.edu.poly.qclist.RetrofitClient.DataClient;
import vn.edu.poly.qclist.RetrofitClient.Getlist_security.Product;


public class ModelQCList {
    Context context;
    Activity activity;
    ModelReponsePresenterQCList callback1;
    AdapterQCList adapterQCList;
    ArrayList<Product> arrayList;

    public ModelQCList(Context context, ModelReponsePresenterQCList callback1) {
        this.context = context;
        this.callback1 = callback1;
        this.activity = (Activity) context;
        arrayList = new ArrayList<>();
    }


    public void ProductData() {
        DataClient dataClient = APIUtils.getData();
        Call<ArrayList<Product>> callback = dataClient.ProductData();
        callback.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if (response.body() == null) {
                } else {
                    arrayList.clear();
                    for (int i = 0; i < response.body().size(); i++) {
                        arrayList.add(new Product(response.body().get(i).getProduct(),
                                response.body().get(i).getApprox_quantity(),
                                response.body().get(i).getName(),
                                response.body().get(i).getMc(),
                                response.body().get(i).getOdor(),
                                response.body().get(i).getSupplier_name(),
                                response.body().get(i).getWarehouse_id(),
                                response.body().get(i).getBurn(),
                                response.body().get(i).getState(),
                                response.body().get(i).getMoldy(),
                                response.body().get(i).getEstimated_bags(),
                                response.body().get(i).getVehicle(),
                                response.body().get(i).getRemarks(),
                                response.body().get(i).getWarehouse_name(),
                                response.body().get(i).getId()
                        ));
                    }
                    adapterQCList = new AdapterQCList(context, response.body());
                    callback1.onDataQCList(adapterQCList);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Log.d("ThrowableError", "" + t.getMessage());
                Toast.makeText(context, "Vui lòng kiểm tra kết nối Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initOnClickData(int position) {
        BaseActivity.editorResult = BaseActivity.dataResult.edit();
        BaseActivity.editorResult.putString("Result", arrayList.get(position).getName());
        BaseActivity.editorResult.putString("id", arrayList.get(position).getId());
        BaseActivity.editorResult.commit();
        callback1.OnClickData();
    }


}

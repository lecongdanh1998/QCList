package vn.edu.poly.qclist.View.BarcodeActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.poly.qclist.Adapter.AdapterQCList;
import vn.edu.poly.qclist.Component.BaseActivity;
import vn.edu.poly.qclist.R;
import vn.edu.poly.qclist.RetrofitClient.APIUtils;
import vn.edu.poly.qclist.RetrofitClient.DataClient;
import vn.edu.poly.qclist.RetrofitClient.Getlist_security.Product;
import vn.edu.poly.qclist.View.QCList.QCListActivity;
import vn.edu.poly.qclist.View.QualityAnalysit.QualityAnalysitActivity;

import static android.Manifest.permission.CAMERA;

public class BarCodeActivity extends BaseActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScanner;
    public static final int REQUEST_CAMERA = 12;
    ArrayList<Product> arrayList;
    int requestcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code);
        initLoadData();
        mScanner = new ZXingScannerView(this);
        setContentView(mScanner);
    }

    private void initLoadData() {
        arrayList = new ArrayList<>();
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
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Log.d("ThrowableError", "" + t.getMessage());
                Toast.makeText(BarCodeActivity.this, "Vui lòng kiểm tra kết nối Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initPermissionCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
            } else {
                requestPermission();
            }
        }
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    boolean cameraAccept = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccept) {
                        Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                displayShowMessage("You need to allow access for both permission", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                    }
                                });
                            }
                        }
                    }
                }
                break;
        }
    }

    private void displayShowMessage(String s, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(this)
                .setMessage(s)
                .setPositiveButton("Oke", onClickListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if (mScanner == null) {
                    mScanner = new ZXingScannerView(this);
                    setContentView(mScanner);
                }
                mScanner.setResultHandler(this);
                mScanner.startCamera();
            } else {
                requestPermission();
            }
        } else {
            mScanner.setResultHandler(this);
            mScanner.startCamera();
//            mScanner.setFlash(true);
            mScanner.setAutoFocus(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScanner.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScanner.stopCamera();
    }

    @Override
    public void handleResult(final Result result) {
        final String scanResult = result.getText();
        for (int i = 0; i < arrayList.size(); i++) {
            requestcode = 0;
            if (arrayList.get(i).getName().toString().equals(scanResult)) {
                editorResult = dataResult.edit();
                editorResult.putString("Result", scanResult);
                editorResult.putString("id", arrayList.get(i).getId());
                editorResult.commit();
                intentView(QualityAnalysitActivity.class);
                requestcode = 1;
                break;
            }
        }

        if (requestcode == 0){
            Toast.makeText(this, "Mã barcode không đúng", Toast.LENGTH_SHORT).show();
            mScanner.resumeCameraPreview(BarCodeActivity.this);
        }


//        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
//        mBuilder.setTitle("Scan result");
//        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int i) {
//                mScanner.resumeCameraPreview(BarCodeActivity.this);
//            }
//        });
//        mBuilder.setNegativeButton("Visit", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(scanResult));
//                startActivity(intent);
//            }
//        });
//        mBuilder.setMessage(scanResult);
//        AlertDialog mAlertDialog = mBuilder.create();
//        mAlertDialog.show();
    }

    @Override
    public void onBackPressed() {
        intentView(QCListActivity.class);
        super.onBackPressed();
    }

    private void intentView(Class c) {
        Intent intent = new Intent(BarCodeActivity.this, c);
        startActivity(intent);
        finish();
    }


}

package vn.edu.poly.qclist.View.BarcodeActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
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

public class BarCodeActivity extends Fragment implements ZXingScannerView.ResultHandler, View.OnClickListener {
    private ZXingScannerView mScanner;
    private View view;
    public static final int REQUEST_CAMERA = 12;
    ArrayList<Product> arrayList;
    int requestcode;
    String cameraId;
    RelativeLayout relativeLayoutbarcode, relativeLayout_highlight, relativeLayout_highlight_background;
    CircleImageView circleImageView_highlight;
    boolean showhide = false;

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getContext() fragment
        view = inflater.inflate(R.layout.activity_bar_code, container, false);
        initView();
        initLoadData();
        initOnClick();
        mScanner = new ZXingScannerView(getContext());
        relativeLayoutbarcode.addView(mScanner);
        return view;
    }

    private void initView() {
        relativeLayout_highlight_background = view.findViewById(R.id.relativeLayout_highlight_background);
        circleImageView_highlight = view.findViewById(R.id.circleImageView_highlight);
        relativeLayoutbarcode = view.findViewById(R.id.relativeLayoutbarcode);
        relativeLayout_highlight = view.findViewById(R.id.relativeLayout_highlight);
    }

    private void initOnClick() {
        relativeLayout_highlight.setOnClickListener(this);
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
                Toast.makeText(getContext(), "Vui lòng kiểm tra kết nối Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA}, REQUEST_CAMERA);
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
                        Toast.makeText(getContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
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
        new AlertDialog.Builder(getContext())
                .setMessage(s)
                .setPositiveButton("Oke", onClickListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if (mScanner == null) {
                    mScanner = new ZXingScannerView(getContext());
                    relativeLayoutbarcode.addView(mScanner);
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
    public void onPause() {
        super.onPause();
        mScanner.stopCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mScanner.stopCamera();
    }

    @Override
    public void handleResult(final Result result) {
        final String scanResult = result.getText();
        BaseActivity.editorResult = BaseActivity.dataResult.edit();
        BaseActivity.editorResult.putString("Result", scanResult);
//        editorResult.putString("id", arrayList.get(i).getId());
        BaseActivity.editorResult.putString("id", "361");
        BaseActivity.editorResult.commit();
        intentView(QualityAnalysitActivity.class);
//        for (int i = 0; i < arrayList.size(); i++) {
//            requestcode = 0;
//            if (arrayList.get(i).getName().toString().equals(scanResult)) {
//
//                requestcode = 1;
//                break;
//            }
//        }
//
//        if (requestcode == 0){
//            Toast.makeText(getContext(), "Mã barcode không đúng", Toast.LENGTH_SHORT).show();
//            mScanner.resumeCameraPreview(getContext());
//        }


//        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
//        mBuilder.setTitle("Scan result");
//        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int i) {
//                mScanner.resumeCameraPreview(getContext());
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

    private void intentView(Class c) {
        Intent intent = new Intent(getContext(), c);
        startActivity(intent);
        getActivity().finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relativeLayout_highlight:
                if (showhide == false) {
                    showPin();
                    showhide = true;
                } else {
                    hidePin();
                    showhide = false;
                }
                break;
        }
    }

    private void showPin() {
        circleImageView_highlight.setImageResource(R.drawable.ic_highlight_black_24dp);
        relativeLayout_highlight_background.setBackground(getContext().getResources().getDrawable(R.drawable.rounded_rlaaa));
        mScanner.setFlash(true);

    }

    private void hidePin() {
        circleImageView_highlight.setImageResource(R.drawable.ic_highlight_white_24dp);
        relativeLayout_highlight_background.setBackground(getContext().getResources().getDrawable(R.drawable.rounded_rlaa));
        mScanner.setFlash(false);

    }

}

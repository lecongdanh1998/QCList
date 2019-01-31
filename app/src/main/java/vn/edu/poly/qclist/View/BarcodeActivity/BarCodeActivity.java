package vn.edu.poly.qclist.View.BarcodeActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.Result;

import java.io.IOException;
import java.lang.reflect.Field;
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

public class BarCodeActivity extends Fragment implements View.OnClickListener, SurfaceHolder.Callback,
        Detector.Processor<Barcode> {
//    private ZXingScannerView mScanner;
    private View view;
    public static final int REQUEST_CAMERA = 12;
    ArrayList<Product> arrayList;
    int requestcode;
    String cameraId;
    RelativeLayout relativeLayoutbarcode, relativeLayout_highlight, relativeLayout_highlight_background;
    CircleImageView circleImageView_highlight;
    boolean showhide = false;
    /*
    /create surface view show camerasource
    create field for gms vision scanner barcode;
     */
    private SurfaceView mSurfaceView;
    private BarcodeDetector mBarcodeDetector;
    private CameraSource mCameraSource;
    private SparseArray<Barcode> mBarcodeSparseArray;
    private String valueBarCode = "";
    private Handler mHandler;
    private boolean success = false;
    private Runnable mRunnable;
    private Camera mCamera;
    private SurfaceHolder mSurfaceHolder;
    private CameraSource.Builder mBuilder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getContext() fragment
        view = inflater.inflate(R.layout.activity_bar_code, container, false);
        initView();
        initLoadData();
        initOnClick();
        initScanner();
//        mScanner = new ZXingScannerView(getContext());
//        relativeLayoutbarcode.addView(mScanner);
        return view;
    }

    private void initView() {
        relativeLayout_highlight_background = view.findViewById(R.id.relativeLayout_highlight_background);
        circleImageView_highlight = view.findViewById(R.id.circleImageView_highlight);
//        relativeLayoutbarcode = view.findViewById(R.id.relativeLayoutbarcode);
        mSurfaceView = view.findViewById(R.id.surface_view_barcode);
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
                                displayShowMessage("You need to allow access for both permission",
                                        new DialogInterface.OnClickListener() {
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
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mScanner.stopCamera();
    }

//    @Override
//    public void handleResult(final Result result) {
//        final String scanResult = result.getText();
//        BaseActivity.editorResult = BaseActivity.dataResult.edit();
//        BaseActivity.editorResult.putString("Result", scanResult);
//        BaseActivity.editorResult.putString("id", "361");
//        BaseActivity.editorResult.commit();
//        intentView(QualityAnalysitActivity.class);
//    }

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
//        mScanner.setFlash(true);
        mCamera = getCamera(mCameraSource);
        if (mCamera != null) {
            try {
                Camera.Parameters param = mCamera.getParameters();
                param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(param);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void hidePin() {
        circleImageView_highlight.setImageResource(R.drawable.ic_highlight_white_24dp);
        relativeLayout_highlight_background.setBackground(getContext().getResources().getDrawable(R.drawable.rounded_rlaa));
//        mScanner.setFlash(false);
        mCamera = getCamera(mCameraSource);
        if (mCamera != null) {
            try {
                Camera.Parameters param = mCamera.getParameters();
                param.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(param);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    //init barcode scanner of gms vision
    private void initScanner() {
        mBarcodeDetector = new BarcodeDetector.Builder(getContext()).build();
        mBuilder = new CameraSource.Builder(getContext(), mBarcodeDetector)
                .setAutoFocusEnabled(false)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(30.0f)
                .setRequestedPreviewSize(1600, 1024);
        mCameraSource = mBuilder.build();
        mSurfaceView.getHolder().addCallback(this);
        mBarcodeDetector.setProcessor(this);
    }

    private static Camera getCamera(@NonNull CameraSource cameraSource) {
        Field[] declaredFields = CameraSource.class.getDeclaredFields();
        Camera camera = null;
        for (Field field : declaredFields) {
            if (field.getType() == Camera.class) {
                field.setAccessible(true);
                try {
                    camera = (Camera) field.get(cameraSource);
                    if (camera != null) {
                        return camera;
                    }
                    return null;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return camera;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mSurfaceHolder = surfaceHolder;
        try {
            mCameraSource.start(mSurfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mCameraSource.stop();
    }

    @Override
    public void release() {
        final String scanResult = mBarcodeSparseArray.valueAt(0).displayValue;
        BaseActivity.editorResult = BaseActivity.dataResult.edit();
        BaseActivity.editorResult.putString("Result", scanResult);
        BaseActivity.editorResult.putString("id", "361");
        BaseActivity.editorResult.commit();
        intentView(QualityAnalysitActivity.class);
    }

    @Override
    public void receiveDetections(Detector.Detections<Barcode> detections) {
        mBarcodeSparseArray = detections.getDetectedItems();
        if (mBarcodeSparseArray.size() > 0) {
            mBarcodeDetector.release();
        } else {
            Field[] declaredFields = CameraSource.class.getDeclaredFields();
            for (Field field : declaredFields) {
                if (field.getType() == Camera.class) {
                    field.setAccessible(true);
                    try {
                        final Camera camera = (Camera) field.get(mCameraSource);
                        camera.autoFocus(new Camera.AutoFocusCallback() {
                            @SuppressLint("MissingPermission")
                            @Override
                            public void onAutoFocus(boolean success, Camera camera) {
                                if (success) {
                                    mBuilder.setAutoFocusEnabled(false);
                                    try {
                                        mCameraSource.start();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                        break;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

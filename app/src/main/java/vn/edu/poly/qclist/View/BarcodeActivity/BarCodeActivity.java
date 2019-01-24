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
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import vn.edu.poly.qclist.Component.BaseActivity;
import vn.edu.poly.qclist.R;
import vn.edu.poly.qclist.View.QCList.QCListActivity;

import static android.Manifest.permission_group.CAMERA;

public class BarCodeActivity extends BaseActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScanner;
    public static final int REQUEST_CAMERA = 123456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code);
        mScanner = new ZXingScannerView(this);
        setContentView(mScanner);
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
        editorResult = dataResult.edit();
        editorResult.putString("Result",scanResult);
        editorResult.commit();
        intentView(QCListActivity.class);
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

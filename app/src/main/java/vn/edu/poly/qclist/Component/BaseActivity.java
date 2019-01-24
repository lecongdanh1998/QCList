package vn.edu.poly.qclist.Component;

import android.Manifest;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import vn.edu.poly.qclist.R;

public class BaseActivity extends AppCompatActivity {
    public static SharedPreferences dataResult;
    public static SharedPreferences.Editor editorResult;
    public static SharedPreferences dataResultQuality;
    public static SharedPreferences.Editor editorQuality;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEditor();
    }

    private void initEditor() {
        dataResult = getSharedPreferences("data_Result", MODE_PRIVATE);
        dataResultQuality = getSharedPreferences("data_Quality", MODE_PRIVATE);
    }


}

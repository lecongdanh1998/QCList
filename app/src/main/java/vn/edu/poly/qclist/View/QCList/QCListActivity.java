package vn.edu.poly.qclist.View.QCList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import vn.edu.poly.qclist.Adapter.AdapterQCList;
import vn.edu.poly.qclist.Component.BaseActivity;
import vn.edu.poly.qclist.Presenter.PresenterQCList.PresenterQCList;
import vn.edu.poly.qclist.Presenter.PresenterQCList.PresenterReponsetoViewQCList;
import vn.edu.poly.qclist.R;
import vn.edu.poly.qclist.View.BarcodeActivity.BarCodeActivity;
import vn.edu.poly.qclist.View.LoginActivity.LoginActivity;
import vn.edu.poly.qclist.View.MainActivity;
import vn.edu.poly.qclist.View.QualityAnalysit.QualityAnalysitActivity;
import vn.edu.poly.qclist.View.TabLayoutBarCode.TabLayoutActivity;

public class QCListActivity extends BaseActivity implements PresenterReponsetoViewQCList, View.OnClickListener {
    PresenterQCList presenterQCList;
    Button btn_scan_barcode;
    String result = "";
    ListView listView;
    ImageView imt_back_tool_bar_qc_list;
    TextView txt_tool_bar_qc_list, txt_title_tool_bar_qc_list;
    String level = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qclist);
        initControl();
        initData();
        initOnClick();

    }

    private void initOnClick() {
        txt_tool_bar_qc_list.setOnClickListener(this);
        imt_back_tool_bar_qc_list.setOnClickListener(this);
        btn_scan_barcode.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                presenterQCList.initOnClickData(position);
            }
        });

    }

    private void initData() {
        presenterQCList = new PresenterQCList(this, this);
        presenterQCList.ProductData();
        txt_tool_bar_qc_list.setText("Back");
        level = dataResultgetMobi_app_level.getString("Level", "");
        if (!level.toString().equals("")) {
            if (level.equals("2.0")) {
                txt_title_tool_bar_qc_list.setText("QC List");
                btn_scan_barcode.setVisibility(View.VISIBLE);
            } else if (level.equals("1.0")) {
                txt_title_tool_bar_qc_list.setText("Purchase List");
                btn_scan_barcode.setVisibility(View.GONE);
            }
        }


    }

    private void initControl() {

        imt_back_tool_bar_qc_list = findViewById(R.id.imt_back_tool_bar_qc_list);
        txt_tool_bar_qc_list = findViewById(R.id.txt_tool_bar_qc_list);
        txt_title_tool_bar_qc_list = findViewById(R.id.txt_title_tool_bar_qc_list);
        listView = findViewById(R.id.listview_barcode);
        btn_scan_barcode = findViewById(R.id.btn_scan_barcode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scan_barcode:
                intentView(TabLayoutActivity.class);
                break;
            case R.id.imt_back_tool_bar_qc_list:
            case R.id.txt_tool_bar_qc_list:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        intentView(MainActivity.class);
    }

    private void intentView(Class c) {
        Intent intent = new Intent(QCListActivity.this, c);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDataQCList(AdapterQCList adapterQCList) {
        listView.setAdapter(adapterQCList);
        setListViewHeightBasedOnChildren(listView);
        adapterQCList.notifyDataSetChanged();
    }

    @Override
    public void OnClickData() {
        intentView(QualityAnalysitActivity.class);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}

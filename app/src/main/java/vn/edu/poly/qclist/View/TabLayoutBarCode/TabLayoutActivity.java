package vn.edu.poly.qclist.View.TabLayoutBarCode;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.poly.qclist.Adapter.AdapterTabLayoutFrangment;
import vn.edu.poly.qclist.Component.BaseActivity;
import vn.edu.poly.qclist.R;
import vn.edu.poly.qclist.View.BarcodeActivity.BarCodeActivity;
import vn.edu.poly.qclist.View.QCList.QCListActivity;
import vn.edu.poly.qclist.View.TabLayoutBarCode.ChoViet.ChoViet;
import vn.edu.poly.qclist.View.TabLayoutBarCode.CongDong.CongDong;
import vn.edu.poly.qclist.View.TabLayoutBarCode.TinNhan.TinNhan;
import vn.edu.poly.qclist.View.TabLayoutBarCode.Toi.Toi;

public class TabLayoutActivity extends BaseActivity implements View.OnClickListener {
    public TabLayout tab_layout;
    ArrayList listFragment;
    ArrayList<String> listTitle;
    AdapterTabLayoutFrangment adapterTabLayoutFrangment;
    public TextView tabOne, tabTwo, tabThree, tabFour, tabFive;
    public int index_change;
    public ViewPager mViewPager;
    RelativeLayout relativeLayoutMargin;
    CircleImageView circleImageViewBackground;
    ImageView imageViewAva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        initControl();
        initData();
        initEvent();
        initOnClick();
        createTabIcons();
    }

    private void initControl() {
        circleImageViewBackground = findViewById(R.id.circleImageViewBackground);
        imageViewAva = findViewById(R.id.imageViewAva);
        relativeLayoutMargin = findViewById(R.id.relativeLayoutMargin);
        tab_layout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_content);
        tab_layout.setupWithViewPager(mViewPager);
    }

    private void initData() {
        tab_layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tab_layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) relativeLayoutMargin.getLayoutParams();
                params.setMargins(0, 0, 0, tab_layout.getHeight() / 2);
                relativeLayoutMargin.setLayoutParams(params);
            }
        });
    }

    private void initEvent() {
        listFragment = new ArrayList();
        listFragment.add(new ChoViet());
        listFragment.add(new CongDong());
        listFragment.add(new BarCodeActivity());
        listFragment.add(new TinNhan());
        listFragment.add(new Toi());
        listTitle = new ArrayList<>();

        listTitle.add(getResources().getString(R.string.txt_choviet));
        listTitle.add(getResources().getString(R.string.txt_congdong));
        listTitle.add("");
        listTitle.add(getResources().getString(R.string.txt_tinhan));
        listTitle.add(getResources().getString(R.string.txt_toi));
        adapterTabLayoutFrangment = new AdapterTabLayoutFrangment(
                getSupportFragmentManager(), this, listTitle, listFragment
        );

        mViewPager.setAdapter(adapterTabLayoutFrangment);
    }

    private void initOnClick() {
        relativeLayoutMargin.setOnClickListener(this);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint({"ResourceAsColor", "ResourceType"})
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tabOne.setText(getResources().getString(R.string.txt_choviet));
                        tabOne.setTextColor(getResources().getColor(R.color.color_text_tablayout));
                        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.restaurantblue, 0, 0);
                        tab_layout.getTabAt(0).setCustomView(tabOne);
                        tabTwo.setText(getResources().getString(R.string.txt_congdong));
                        tabTwo.setTextColor(getResources().getColor(R.color.color_text_tablayoutactivity));
                        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.documentaryblack, 0, 0);
                        tab_layout.getTabAt(1).setCustomView(tabTwo);
                        // icon Tham quan
                        tabThree.setText("");
                        circleImageViewBackground.setImageResource(R.color.color_text_tablayoutactivity);
                        imageViewAva.setImageResource(R.drawable.qrwhite);
//                        tabThree.setTextColor(getResources().getColor(R.color.color_text_tablayoutactivity));
//                        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.shoppingblack, 0, 0);
                        tab_layout.getTabAt(2).setCustomView(tabThree);
                        // icon Tìm kiếm
                        tabFour.setText(getResources().getString(R.string.txt_tinhan));
                        tabFour.setTextColor(getResources().getColor(R.color.color_text_tablayoutactivity));
                        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.beachblack, 0, 0);
                        tab_layout.getTabAt(3).setCustomView(tabFour);
                        // icon giỏ hàng
                        tabFive.setText(getResources().getString(R.string.txt_toi));
                        tabFive.setTextColor(getResources().getColor(R.color.color_text_tablayoutactivity));
                        tabFive.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.schoolblack, 0, 0);
                        tab_layout.getTabAt(4).setCustomView(tabFive);
                        break;
                    case 1:
                        tabOne.setText(getResources().getString(R.string.txt_choviet));
                        tabOne.setTextColor(getResources().getColor(R.color.color_text_tablayoutactivity));
                        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.restaurantblack, 0, 0);
                        tab_layout.getTabAt(0).setCustomView(tabOne);

                        tabTwo.setText(getResources().getString(R.string.txt_congdong));
                        tabTwo.setTextColor(getResources().getColor(R.color.color_text_tablayout));
                        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.documentaryblue, 0, 0);
                        tab_layout.getTabAt(1).setCustomView(tabTwo);
                        // icon Tham quan
                        tabThree.setText("");
                        circleImageViewBackground.setImageResource(R.color.color_text_tablayoutactivity);
                        imageViewAva.setImageResource(R.drawable.qrwhite);
//                        tabThree.setTextColor(getResources().getColor(R.color.color_text_tablayoutactivity));
//                        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.shoppingblack, 0, 0);
                        tab_layout.getTabAt(2).setCustomView(tabThree);
                        // icon Tìm kiếm
                        tabFour.setText(getResources().getString(R.string.txt_tinhan));
                        tabFour.setTextColor(getResources().getColor(R.color.color_text_tablayoutactivity));
                        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.beachblack, 0, 0);
                        tab_layout.getTabAt(3).setCustomView(tabFour);
                        // icon giỏ hàng
                        tabFive.setText(getResources().getString(R.string.txt_toi));
                        tabFive.setTextColor(getResources().getColor(R.color.color_text_tablayoutactivity));
                        tabFive.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.schoolblack, 0, 0);
                        tab_layout.getTabAt(4).setCustomView(tabFive);
                        break;
                    case 2:
                        tabOne.setText(getResources().getString(R.string.txt_choviet));
                        tabOne.setTextColor(getResources().getColor(R.color.color_text_tablayoutactivity));
                        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.restaurantblack, 0, 0);
                        tab_layout.getTabAt(0).setCustomView(tabOne);

                        tabTwo.setText(getResources().getString(R.string.txt_congdong));
                        tabTwo.setTextColor(getResources().getColor(R.color.color_text_tablayoutactivity));

                        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.documentaryblack, 0, 0);
                        tab_layout.getTabAt(1).setCustomView(tabTwo);
                        // icon Tham quan
                        tabThree.setText("");
                        circleImageViewBackground.setImageResource(R.color.color_text_tablayout);
                        imageViewAva.setImageResource(R.drawable.qrblue);
//                        tabThree.setTextColor(getResources().getColor(R.color.color_text_tablayout));
//                        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.cartblue, 0, 0);
                        tab_layout.getTabAt(2).setCustomView(tabThree);
                        // icon Tìm kiếm
                        tabFour.setText(getResources().getString(R.string.txt_tinhan));
                        tabFour.setTextColor(getResources().getColor(R.color.color_text_tablayoutactivity));
                        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.beachblack, 0, 0);
                        tab_layout.getTabAt(3).setCustomView(tabFour);
                        // icon giỏ hàng
                        tabFive.setText(getResources().getString(R.string.txt_toi));
                        tabFive.setTextColor(getResources().getColor(R.color.color_text_tablayoutactivity));
                        tabFive.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.schoolblack, 0, 0);
                        tab_layout.getTabAt(4).setCustomView(tabFive);
                        break;
                    case 3:

                        tabOne.setText(getResources().getString(R.string.txt_choviet));
                        tabOne.setTextColor(getResources().getColor(R.color.color_text_tablayoutactivity));
                        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.restaurantblack, 0, 0);
                        tab_layout.getTabAt(0).setCustomView(tabOne);

                        tabTwo.setText(getResources().getString(R.string.txt_congdong));
                        tabTwo.setTextColor(getResources().getColor(R.color.color_text_tablayoutactivity));
                        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.documentaryblack, 0, 0);
                        tab_layout.getTabAt(1).setCustomView(tabTwo);
                        // icon Tham quan
                        tabThree.setText("");
                        circleImageViewBackground.setImageResource(R.color.color_text_tablayoutactivity);
                        imageViewAva.setImageResource(R.drawable.qrwhite);
//                        tabThree.setTextColor(getResources().getColor(R.color.color_text_tablayoutactivity));
//                        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.shoppingblack, 0, 0);
                        tab_layout.getTabAt(2).setCustomView(tabThree);
                        // icon Tìm kiếm
                        tabFour.setText(getResources().getString(R.string.txt_tinhan));
                        tabFour.setTextColor(getResources().getColor(R.color.color_text_tablayout));
                        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.beachblue, 0, 0);
                        tab_layout.getTabAt(3).setCustomView(tabFour);
                        // icon giỏ hàng
                        tabFive.setText(getResources().getString(R.string.txt_toi));
                        tabFive.setTextColor(getResources().getColor(R.color.color_text_tablayoutactivity));
                        tabFive.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.schoolblack, 0, 0);
                        tab_layout.getTabAt(4).setCustomView(tabFive);
                        break;
                    case 4:
                        tabOne.setText(getResources().getString(R.string.txt_choviet));
                        tabOne.setTextColor(getResources().getColor(R.color.color_text_tablayoutactivity));
                        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.restaurantblack, 0, 0);
                        tab_layout.getTabAt(0).setCustomView(tabOne);

                        tabTwo.setText(getResources().getString(R.string.txt_congdong));
                        tabTwo.setTextColor(getResources().getColor(R.color.color_text_tablayoutactivity));
                        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.documentaryblack, 0, 0);
                        tab_layout.getTabAt(1).setCustomView(tabTwo);
                        // icon Tham quan
                        tabThree.setText("");
                        circleImageViewBackground.setImageResource(R.color.color_text_tablayoutactivity);
                        imageViewAva.setImageResource(R.drawable.qrwhite);
//                        tabThree.setTextColor(getResources().getColor(R.color.color_text_tablayoutactivity));
//                        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.shoppingblack, 0, 0);
                        tab_layout.getTabAt(2).setCustomView(tabThree);
                        // icon Tìm kiếm
                        tabFour.setText(getResources().getString(R.string.txt_tinhan));
                        tabFour.setTextColor(getResources().getColor(R.color.color_text_tablayoutactivity));
                        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.beachblack, 0, 0);
                        tab_layout.getTabAt(3).setCustomView(tabFour);
                        // icon giỏ hàng
                        tabFive.setText(getResources().getString(R.string.txt_toi));
                        tabFive.setTextColor(getResources().getColor(R.color.color_text_tablayout));
                        tabFive.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.schoolblue, 0, 0);
                        tab_layout.getTabAt(4).setCustomView(tabFive);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void createTabIcons() {
        tabOne = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_tab, null);
        tabOne.setText(getResources().getString(R.string.txt_choviet));
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.restaurantblack, 0, 0);
        tab_layout.getTabAt(0).setCustomView(tabOne);
        // icon Hôm nay
        tabTwo = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_tab, null);
        tabTwo.setText(getResources().getString(R.string.txt_congdong));
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.documentaryblack, 0, 0);
        tab_layout.getTabAt(1).setCustomView(tabTwo);
        // icon Tham quan
        tabThree = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_tab, null);
        tabThree.setText("");

        circleImageViewBackground.setImageResource(R.color.color_text_tablayout);
        imageViewAva.setImageResource(R.drawable.qrblue);
//        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.shoppingblack, 0, 0);
        tab_layout.getTabAt(2).setCustomView(tabThree);
        // icon Tìm kiếm
        tabFour = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_tab, null);
        tabFour.setText(getResources().getString(R.string.txt_tinhan));
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.beachblack, 0, 0);
        tab_layout.getTabAt(3).setCustomView(tabFour);
        // icon giỏ hàng
        tabFive = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_tab, null);
        tabFive.setText(getResources().getString(R.string.txt_toi));
        tabFive.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.schoolblack, 0, 0);
        tab_layout.getTabAt(4).setCustomView(tabFive);
        // icon Tài khoản
        mViewPager.setCurrentItem(2);
    }


    @Override
    public void onBackPressed() {
        intentView(QCListActivity.class);
        super.onBackPressed();
    }

    private void intentView(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relativeLayoutMargin:
                mViewPager.setCurrentItem(2);
                break;
        }
    }
}

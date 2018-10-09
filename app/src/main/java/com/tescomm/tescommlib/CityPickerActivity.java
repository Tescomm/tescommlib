package com.tescomm.tescommlib;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.tescomm.customview.addresspicker.CityPickerView;
import com.tescomm.customview.entities.CityBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 城市选择demo
 */

public class CityPickerActivity  extends AppCompatActivity implements View.OnClickListener {
    View pop_top;
    TextView tvMyIntentAddress;
    RelativeLayout rlMyIntentWorkAddress;
    private String strPosition;
    private String strAddress;
    private com.tescomm.customview.addresspicker.CityPickerView addressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
      setContentView( R.layout.addresspicker_activity);
        rlMyIntentWorkAddress = (RelativeLayout)findViewById(R.id.rl_my_intent_work_address);
        tvMyIntentAddress = (TextView) findViewById(R.id.tv_my_intent_address);
        rlMyIntentWorkAddress.setOnClickListener(this);
        pop_top = (View) findViewById(R.id.pop_top);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rl_my_intent_work_address:
                //  mPresenter.getResult(PERSON_WORK_ADDRESS);
                showAddressPickerPop();
//                startActivityForResult(new Intent(this, RegionSelectActivity.class), REGION_REQUEST_CODE);
               /* Intent intent = new Intent(PersonIntentActivity.this, AddressActivity.class);
                startActivity(intent);*/
                break;
            default:
                break;
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            if (this.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 显示地址选择的pop
     */
    private void showAddressPickerPop() {
        final PopupWindow popupWindow = new PopupWindow(this);
        View rootView = LayoutInflater.from(this).inflate(R.layout.city_picker_pop, null, false);
//        hideKeyboard();
        addressView = rootView.findViewById(R.id.apvAddress);

        addressView.initTitle("请选择", "", "");

        addressView.setOnAddressPickerSure(new CityPickerView.OnAddressPickerListener() {
            @Override
            public void onCancalClick() {
                popupWindow.dismiss();
            }

            @Override
            public void onSureClick(String address, String provinceCode, String cityCode, String districtCode) {

                tvMyIntentAddress.setText(address);
                tvMyIntentAddress.setVisibility(View.VISIBLE);
                strAddress = address;
                popupWindow.dismiss();

//                roomID = districtCode;districtCode
//                tv_resident_address.setText( address);
//                popupWindow.dismiss();

            }

            /**
             * 获取第一级列表的数据
             * 此方法应该从服务器获取数据，完成后调用getAddressResultSuccess，传入获取的数据
             */
            @Override
            public void onOneClick() {
//                maddressPrsent.getAddressResult("", 1);
//                mPresenter.getRoomResult("","",1);
                getAddressResultSuccess(null,1);
            }
            /**
             * 获取第二级列表的数据
             * 此方法应该从服务器获取数据，完成后调用getAddressResultSuccess，传入获取的数据
             */
            @Override
            public void onTwoClick(String oneid) {
//                maddressPrsent.getAddressResult(oneid, 2);
//                mPresenter.getRoomResult(oneid,"",2);
                getAddressResultSuccess(null,2);
            }
            /**
             * 获取第三级列表的数据
             * 此方法应该从服务器获取数据，完成后调用getAddressResultSuccess，传入获取的数据
             */
            @Override
            public void onThreeClick(String oneid, String twoid) {
//                maddressPrsent.getAddressResult(twoid, 3);
//                mPresenter.getRoomResult(oneid,twoid,3);
                getAddressResultSuccess(null,3);
            }
        });

//        maddressPrsent.getAddressResult("", 1);
        /**
         * 初始化数据。
         * 应该从服务器获取数据，完成后调用getAddressResultSuccess，传入获取的数据
         */
        getAddressResultSuccess(null,1);
        popupWindow.setContentView(rootView);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        ColorDrawable dw = new ColorDrawable(mContext.getResources().getColor(R.color.white));
        popupWindow.setBackgroundDrawable(new ColorDrawable(getApplication().getResources().getColor(R.color.black_bg)));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(pop_top, 0, 0);
    }

    public void getAddressResultSuccess(List<CityBean> listHttpResponse, int type) {
        List<CityBean.AddressItemBean> beans = new ArrayList<CityBean.AddressItemBean>();
        if (type == 1) {
//            for (AddressBean bean : listHttpResponse) {
//                CityBean.AddressItemBean item = new CityBean.AddressItemBean();
//                item.setI(bean.getCode());
//                item.setN(bean.getName());
//                beans.add(item);
//            }
            if(listHttpResponse==null){
                CityBean.AddressItemBean item = new CityBean.AddressItemBean();
                item.setI("1");
                item.setN("北京");
                beans.add(item);
                CityBean.AddressItemBean item2 = new CityBean.AddressItemBean();
                item2.setI("2");
                item2.setN("上海");
                beans.add(item2);
            }
            addressView.flushProviceList(beans);
        } else if (type == 2) {
//            for (AddressBean bean : listHttpResponse) {
//                CityBean.AddressItemBean item = new CityBean.AddressItemBean();
//                item.setI(bean.getCode());
//                item.setN(bean.getName());
//                beans.add(item);
//            }
            if(listHttpResponse==null){
                CityBean.AddressItemBean item = new CityBean.AddressItemBean();
                item.setI("1");
                item.setN("海淀区");
                beans.add(item);
                CityBean.AddressItemBean item2 = new CityBean.AddressItemBean();
                item2.setI("2");
                item2.setN("朝阳区");
                beans.add(item2);
            }
            addressView.flushCityList(beans);
        } else if (type == 3) {
//            for (AddressBean bean : listHttpResponse) {
//                CityBean.AddressItemBean item = new CityBean.AddressItemBean();
//                item.setI(bean.getCode());
//                item.setN(bean.getName());
//                beans.add(item);
//            }
            if(listHttpResponse==null){
                CityBean.AddressItemBean item = new CityBean.AddressItemBean();
                item.setI("1");
                item.setN("中关村");
                beans.add(item);
                CityBean.AddressItemBean item2 = new CityBean.AddressItemBean();
                item2.setI("2");
                item2.setN("望京");
                beans.add(item2);
            }
            addressView.flushDistrictList(beans);
        }
    }
}

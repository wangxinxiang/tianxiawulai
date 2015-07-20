package com.example.txwl_first;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.LauncherActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.example.txwl_first.Util.Constant;
import com.example.txwl_first.Util.DataVeri;
import com.example.txwl_first.Util.TXWLApplication;

import java.util.ArrayList;
import java.util.Calendar;

public class HouseLoanActivity extends Activity implements AddItem{
    private TextView tv_title,tv_right;
    private ImageButton ibtn_title_back,ibtn_sub;
    private LinearLayout ll_my_loan_detail,ll_person_detail,layout_item_three;

    //房贷个人信息编辑字段
    private final String[] houseowner = {"房主姓名","性别","年龄","手机号","工作单位","身份证号","所在地区","详细地址","借款金额","借款日","还款日","年利率","详细说明"};

    //车贷个人信息编辑字段
    private final String[] carowner = {"车主姓名","性别","年龄","手机号","工作单位","车牌号","借款金额","借款日","还款日","年利率","详细说明"};

    //信用贷、其它贷款个人信息编辑字段
    private final String[] creditowner = {"借款人姓名","性别","年龄","手机号","工作单位","所在地区","详细地址","借款金额","借款日","还款日","年利率","详细说明"};


    //车贷详情显示字段
    private final String[] car_owner_show = {"车子外观","发动机号","车主证件","车主头像","合同照片"};



    //房贷默认图片背景
    private final Integer[] house_image_selectors = {R.drawable.btn_house_surface_selector,R.drawable.btn_house_pager_number_selector,
    R.drawable.btn_house_pager_number_selector,R.drawable.btn_house_head_image_selector,R.drawable.btn_car_contact_image_selector};

    //车贷默认图片背景
    private final Integer[] car_image_selectors = {R.drawable.btn_car_surface_image_selector,R.drawable.btn_car_engine_number_selector,
            R.drawable.btn_car_pager_image_selector,R.drawable.btn_car_head_image_selector,R.drawable.btn_car_contact_image_selector};

    //信用贷、其它贷款默认图片背景
    private final Integer[] credit_image_selectors = {R.drawable.loan_pager_image_selector,R.drawable.btn_loan_head_image_selector,
            R.drawable.btn_car_contract_image_selector};


    /**
     * 以下几个数组需要访问接口
     */
    //车贷详情描述字段
    private String[] car_owner_des_show = {"这个车子外观9成新，开了5万公里","CA7165AT4","5002361999322110765","傅旭东照片","借款协议书"};

    //车贷详情图片背景
    private Integer[] car_own_images_show = {R.drawable.btn_car_surface_image_selector,R.drawable.btn_car_engine_number_selector,
            R.drawable.btn_car_pager_image_selector,R.drawable.btn_car_head_image_selector,R.drawable.btn_car_contact_image_selector};

    //房贷详情图片背景
    private Integer[] house_own_image_show = {R.drawable.btn_house_surface_selector,R.drawable.btn_house_pager_number_selector,
            R.drawable.btn_house_pager_number_selector,R.drawable.btn_house_head_image_selector,R.drawable.btn_car_contact_image_selector};

    //信用贷详情图片背景
    private final Integer[] credit_own_image_show = {R.drawable.loan_pager_image_selector,R.drawable.btn_loan_head_image_selector,
            R.drawable.btn_car_contract_image_selector};

    //其它贷款详情图片背景
    private final Integer[] other_own_image_show = {R.drawable.loan_pager_image_selector,R.drawable.btn_loan_head_image_selector,
            R.drawable.btn_car_contract_image_selector};



    //日期选择器标示号
    private static final int DATE_DIALOG_ID = 0;
    private static final int DATE_PAMENT_DIALOG_ID = 1;

    //借款还款时间
    private String loan_datetime,paymenttime;

    //判断来自Activity的哪个按钮点击事件
    private String from_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        initView();
        setOnClickListenser();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setOnClickListenser() {
        ibtn_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        tv_right.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //遍历所有控件，获取数据
//                getData();
//                Log.d("遍历子控件", getData().toString());
//                //输入字段格式检查
//                checkData();
//
//                //TODO：联网上传接口数据
//
//            }
//        });

    }

    //检查字段信息
    private void checkData() {
        String[] check_data_str = getData().toString().split(",");

        for(int i = 0;i<check_data_str.length;i++){
            Log.d("char-->",check_data_str.length+" "+i+" "+check_data_str[i].toString());

                //检测房贷字段
                if(from_button.equals("house_loan_button")) {
                    switch (i) {
                        case 0:
                            if (check_data_str[0].equals(""))
                                Toast.makeText(getApplicationContext(), "姓名不能为空", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            if (check_data_str[2].equals("")) {
                                Toast.makeText(getApplicationContext(), "年龄不能为空", Toast.LENGTH_SHORT).show();
                            }
//                    if(DataVeri.isAge(check_data_str[2])){
//                        Toast.makeText(getApplicationContext(), "年龄输入有误", Toast.LENGTH_SHORT).show();
//                    }
                            break;
                        case 3:
                            if (check_data_str[3].equals("")) {
                                Toast.makeText(getApplicationContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
                            }
                            if (DataVeri.isMobileNum(check_data_str[3])) {
                                Toast.makeText(getApplicationContext(), "手机号输入有误", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 5:
                            if (check_data_str[5].equals("")) {
                                Toast.makeText(getApplicationContext(), "身份证号不能为空", Toast.LENGTH_SHORT).show();
                            }
                            if (DataVeri.isIDNum(check_data_str[5])) {
                                Toast.makeText(getApplicationContext(), "身份号输入有误", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 8:
                            if (check_data_str[8].equals("")) {
                                Toast.makeText(getApplicationContext(), "借款金额不能为空", Toast.LENGTH_SHORT).show();
                            }
//                    if(DataVeri.isMoney(check_data_str[8])){
//                        Toast.makeText(getApplicationContext(), "借款金额输入有误", Toast.LENGTH_SHORT).show();
//                    }
                            break;
                        case 11:
                            if (check_data_str[11].equals("")) {
                                Toast.makeText(getApplicationContext(), "年利率不能为空", Toast.LENGTH_SHORT).show();
                            }
//                    if(DataVeri.isYear(check_data_str[11])){
//                        Toast.makeText(getApplicationContext(), "年利率输入有误", Toast.LENGTH_SHORT).show();
//                    }
                            break;
                    }
                }

                //检测车贷字段
                if(from_button.equals("car_loan_button")){
                    switch (i){
                        case 0:
                            break;
                        case 1:
                            break;
                    }
                }

        }
    }


    private StringBuffer getData() {
        StringBuffer data = new StringBuffer();
        //循环遍历LinearLayout Item，获取子控件数据
        Log.d("ChildCount-->", "|" + ll_person_detail.getChildCount());
        for(int i =0;i<ll_person_detail.getChildCount();i++){
            RelativeLayout childLinearlayout = (RelativeLayout) ll_person_detail.getChildAt(i);
            Log.d("ChildChildCount-->", "|" + childLinearlayout.getChildCount());
            for(int j = 0;j<childLinearlayout.getChildCount();j++){
                View v = childLinearlayout.getChildAt(j);
                if(v instanceof LinearLayout){
                    for (int z=0;z<((LinearLayout) v).getChildCount();z++){
                        View vv = ((LinearLayout) v).getChildAt(z);
                        if((vv instanceof TextView)&&vv.getId()==R.id.right&&vv.getVisibility()==View.VISIBLE){
                            data.append(((TextView) vv).getText()+",");
                        }
                    }
                }

                if(v instanceof EditText&&v.getVisibility()==View.VISIBLE){
                    data.append(((EditText) v).getText()+",");
                }

                if(v instanceof RadioGroup){
                    if(v.getVisibility()==View.VISIBLE) {
                        for (int k = 0; k < ((RadioGroup) v).getChildCount(); k++) {
                            View vvv = ((RadioGroup) v).getChildAt(k);
                            if ((vvv instanceof RadioButton) && ((RadioButton) vvv).isChecked()) {
                                data.append(((RadioButton) vvv).getText().toString()+",");
                            }
                        }
                    }
                }
            }
        }
        data.deleteCharAt(data.length() - 1);
        return data;
    }

    private void initView() {
        from_button = getIntent().getStringExtra("fromButton");
        ll_person_detail = (LinearLayout) findViewById(R.id.ll_person_detail);
        ll_my_loan_detail = (LinearLayout) findViewById(R.id.ll_my_loan_detail);
        layout_item_three = (LinearLayout) findViewById(R.id.layout_item_three);
        tv_title = (TextView) findViewById(R.id.tv_title);
        ibtn_title_back = (ImageButton) findViewById(R.id.ibtn_title_back);
        ibtn_sub = (ImageButton) findViewById(R.id.ibtn_sub);
        ibtn_title_back.setVisibility(View.VISIBLE);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setVisibility(View.VISIBLE);

        //清空布局
        ll_person_detail.removeAllViews();
        ll_my_loan_detail.removeAllViews();

        //动态填充两个LinearLayout布局
        switch (from_button){
            //字段输入上传到接口
            case "house_loan_button":
                fill_LinearLayout("房贷",houseowner,house_image_selectors);
                break;
            case "car_loan_button":
                fill_LinearLayout("车贷",carowner,car_image_selectors);
                break;
            case "credit_loan_button":
                fill_LinearLayout("信用贷",creditowner,credit_image_selectors);
                break;
            case "other_loan_button":
                fill_LinearLayout("其它",creditowner,credit_image_selectors);
                break;
            case "menufragment":
                fill_LinearLayout("添加老赖",creditowner,credit_image_selectors);
                break;
            //从接口获取网络数据填充
            case "query_listview_car_item":
                layout_item_three.setVisibility(View.VISIBLE);
                fill_LinearLayout("详情",carowner,car_own_images_show);
                break;
            case "query_listview_house_item":
                layout_item_three.setVisibility(View.VISIBLE);
                fill_LinearLayout("详情",houseowner,house_own_image_show);
                break;
            case "query_listview_credit_item":
                layout_item_three.setVisibility(View.VISIBLE);
                fill_LinearLayout("详情",creditowner,credit_own_image_show);
                break;
            case "query_listview_other_item":
                layout_item_three.setVisibility(View.VISIBLE);
                fill_LinearLayout("详情",creditowner,other_own_image_show);
                break;
        }
    }

    @Override
    public void fill_LinearLayout(String title_name, String[] owner, Integer[] selectors) {
        tv_title.setText(title_name);
        //如果处于显示状态
        if("query_listview_car_item".equals(from_button)||"query_listview_house_item".equals(from_button)||
                "query_listview_credit_item".equals(from_button)||"query_listview_other_item".equals(from_button)){
            tv_right.setText("举报");
            //跳转到举报界面
            tv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                    startActivity(intent);
                }
            });

            for(int i =0;i< selectors.length;i++){
                AddHouse_Items(ll_my_loan_detail,selectors[i],car_owner_show[i],car_owner_des_show[i], i);
            }

            for(int i = 0;i<owner.length;i++){
                AddItems(ll_person_detail, owner[i], "test");
            }
        }else {
            tv_right.setText("提交");
            tv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //遍历所有控件，获取数据
                    getData();
                    Log.d("遍历子控件", getData().toString());
                    //输入字段格式检查
                    checkData();

                    //TODO：联网上传接口数据

                }
            });
            for(int i =0;i< selectors.length;i++){
                AddHouse_Items(ll_my_loan_detail,selectors[i],"备注","", i);
            }

            for(int i = 0;i<owner.length;i++){
                AddItems(ll_person_detail, owner[i], "test");
            }
        }
    }



    /**
     * paras data1 : 静态字段
     * paras data2 : 从服务端获取的字段，需要处理成数组
     */
    @Override
    public void AddItems(LinearLayout linearLayout, String data1, String data2) {
        RelativeLayout ll_detail = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_one, null);
        TextView left = (TextView) ll_detail.findViewById(R.id.left);
        TextView right = (TextView) ll_detail.findViewById(R.id.right);
        RadioGroup rg_male_female = (RadioGroup) ll_detail.findViewById(R.id.rg_male_female);
        EditText et_input_content = (EditText) ll_detail.findViewById(R.id.et_input_content);
        et_input_content.setVisibility(View.VISIBLE);
        left.setText(data1);

        //如果处于显示状态，right需要显示
        if("query_listview_car_item".equals(from_button)||"query_listview_house_item".equals(from_button)||
                "query_listview_credit_item".equals(from_button)||"query_listview_other_item".equals(from_button)){
            right.setVisibility(View.VISIBLE);
            et_input_content.setVisibility(View.GONE);
            if("性别".equals(data1)){
                rg_male_female.setVisibility(View.GONE);
                right.setVisibility(View.VISIBLE);
            }
            if("借款日".equals(data1)||"还款日".equals(data1)){
                ll_detail.findViewById(R.id.btn_entry).setVisibility(View.GONE);
            }

        }else {
            if("性别".equals(data1)){
                right.setVisibility(View.GONE);
                et_input_content.setVisibility(View.GONE);
                rg_male_female.setVisibility(View.VISIBLE);
            }
            if(data1.equals("借款金额")){
                right.setText(data2 + "元");
            }
            if(data1.equals("年利率")){
                right.setText(data2+"％");
            }
            if(data1.equals("借款日")){
                et_input_content.setVisibility(View.GONE);
                ll_detail.findViewById(R.id.btn_entry).setVisibility(View.VISIBLE);
                right.setVisibility(View.VISIBLE);
                ll_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        showDialog(DATE_DIALOG_ID);
                    }
                });
            }

            if(data1.equals("还款日")){
                et_input_content.setVisibility(View.GONE);
                ll_detail.findViewById(R.id.btn_entry).setVisibility(View.VISIBLE);
                right.setVisibility(View.VISIBLE);
                ll_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        showDialog(DATE_PAMENT_DIALOG_ID);
                    }
                });
            }
        }
        linearLayout.addView(ll_detail);
    }

    /**
     *
     * @param layout 填充容器对象
     * @param house ImageButton 背景图片资源
     * @param remark 备注或描述字段
     * @param descrip_content 描述内容
     */
    private void AddHouse_Items(LinearLayout layout,Integer house,String remark,String descrip_content, int i){
        //如果处于显示状态
        if("query_listview_car_item".equals(from_button)||"query_listview_house_item".equals(from_button)||
                "query_listview_credit_item".equals(from_button)||"query_listview_other_item".equals(from_button)){
            RelativeLayout rl_item_five = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_five,null);
            ImageButton btn_photo = (ImageButton) rl_item_five.findViewById(R.id.btn_photo);
            TextView tv_descrip = (TextView) rl_item_five.findViewById(R.id.tv_descrip);
            TextView tv_descrip_content = (TextView) rl_item_five.findViewById(R.id.tv_descrip_content);
            btn_photo.setImageResource(house);
            tv_descrip.setText(remark);
            tv_descrip_content.setText(descrip_content);
            layout.addView(rl_item_five);

        }else{
            LinearLayout rl_house_detail = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_two, null);
            ImageButton btn_photo = (ImageButton) rl_house_detail.findViewById(R.id.btn_photo);
            EditText et_remark = (EditText) rl_house_detail.findViewById(R.id.et_remark);
            btn_photo.setImageResource(house);
            et_remark.setText(remark);
            layout.addView(rl_house_detail);

            btn_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TXWLApplication.getInstance().showTextToast("上传图片");
                    Intent intent = new Intent();
                    intent.putExtra("from", "" + i);
                    startActivityForResult(intent, Constant.GETPHOTO);
                }
            });
        }
    }

    //设置日期选择对话框
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            loan_datetime = new StringBuffer().append(year+"-").append(month+1+"-").append(day).toString();
            switch (from_button){
                case "house_loan_button":
                    LocateDatePikerItem(9,loan_datetime);
                    break;
                case "car_loan_button":
                    LocateDatePikerItem(7,loan_datetime);
                    break;
                case "credit_loan_button":
                case "other_loan_button":
                case "menufragment":
                    LocateDatePikerItem(8,loan_datetime);
            }
        }
    };

    private void LocateDatePikerItem(int date_item,String date_time) {
        RelativeLayout rl_loan_time =(RelativeLayout)ll_person_detail.getChildAt(date_item);
        LinearLayout ll_loan_time = (LinearLayout)rl_loan_time.getChildAt(3);
        TextView tv_loan_time = (TextView) ll_loan_time.getChildAt(0);
        tv_loan_time.setText(date_time);
    }

    private DatePickerDialog.OnDateSetListener mDatePaymentSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            paymenttime = new StringBuffer().append(year + "-").append(month + 1 + "-").append(day).toString();
            switch (from_button){
                case "house_loan_button":
                    LocateDatePikerItem(10,paymenttime);
                    break;
                case "car_loan_button":
                    LocateDatePikerItem(8,paymenttime);
                    break;
                case "credit_loan_button":
                case "other_loan_button":
                case "menufragment":
                    LocateDatePikerItem(9,paymenttime);
            }
        }
    };


    protected Dialog onCreateDialog(int id){
        final  Calendar c = Calendar.getInstance();
        switch (id){
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,mDateSetListener,c.get(Calendar.YEAR),c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH));
            case DATE_PAMENT_DIALOG_ID:
                return new DatePickerDialog(this,mDatePaymentSetListener,c.get(Calendar.YEAR),c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH));
        }
        return null;
    }
}

package com.example.txwl_first;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.example.txwl_first.Util.Constant;
import com.example.txwl_first.Util.DataVeri;
import com.example.txwl_first.Util.TXWLApplication;
import com.example.txwl_first.bean.QueryDetailResultBean;
import com.example.txwl_first.business.LoaderBusiness;

import java.io.File;
import java.util.Calendar;

public class LoanActivity extends Activity implements AddItem {

    protected TextView tv_title, tv_right;
    protected ImageButton ibtn_title_back, ibtn_sub;
    protected LinearLayout ll_my_loan_detail, ll_person_detail, layout_item_three;

    //循环遍历控件得到的需要上传到服务端的数据数组
    protected String[] check_data_str;

    protected String[] image_url = new String[5];     //储存要提交的图片
    protected String[] image_remark = new String[5];      //储存图片的备注

    //房贷个人信息编辑字段
    protected final String[] houseowner = {"房主姓名", "性别", "年龄", "手机号", "工作单位", "身份证号", "所在地区", "详细地址", "借款金额", "借款日", "还款日", "年利率", "详细说明"};

    //车贷个人信息编辑字段
    protected final String[] carowner = {"车主姓名", "性别", "年龄", "手机号", "工作单位", "车牌号", "借款金额", "借款日", "还款日", "年利率", "详细说明"};

    //信用贷、其它贷款个人信息编辑字段
    protected final String[] creditowner = {"借款人姓名", "性别", "年龄", "手机号", "工作单位", "所在地区", "详细地址", "借款金额", "借款日", "还款日", "年利率", "详细说明"};


    protected  String[] image_show;
    //车贷详情显示字段
    protected final String[] car_owner_show = {"车子外观", "发动机号", "车主证件", "车主头像", "合同照片"};
    //房贷详情显示字段
    protected final String[] house_owner_show = {"房子外观", "房产证号", "房主证件", "房主头像", "合同照片"};
    //信用贷、其它贷款详情显示字段
    protected final String[] credit_owner_show = {"借款人证件", "借款人头像", "合同照片"};


    //房贷默认图片背景
    protected Integer[] house_image_selectors = {R.drawable.btn_house_surface_selector, R.drawable.btn_house_pager_number_selector,
            R.drawable.btn_house_pager_image_selector, R.drawable.btn_house_head_image_selector, R.drawable.btn_car_contact_image_selector};

    //车贷默认图片背景
    protected Integer[] car_image_selectors = {R.drawable.btn_car_surface_image_selector, R.drawable.btn_car_engine_number_selector,
            R.drawable.btn_car_pager_image_selector, R.drawable.btn_car_head_image_selector, R.drawable.btn_car_contact_image_selector};

    //信用贷、其它贷款默认图片背景
    protected Integer[] credit_image_selectors = {R.drawable.loan_pager_image_selector, R.drawable.btn_loan_head_image_selector,
            R.drawable.btn_car_contract_image_selector};


    /**
     * 以下几个数组需要访问接口
     */
    //详情描述字段
    protected String[] car_owner_des_show = new String[5];
    //车贷,房贷等详情图片背景
    protected String[] car_own_images_show = new String[5];




    //日期选择器标示号
    protected static final int DATE_DIALOG_ID = 0;
    protected static final int DATE_PAMENT_DIALOG_ID = 1;

    //借款还款时间
    protected String loan_datetime, paymenttime;

    //判断来自Activity的哪个按钮点击事件
    protected String from_button;

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
    }


    //检查字段信息
    protected void checkData() {
        check_data_str = getData().toString().split(",");

        for (int i = 0; i < check_data_str.length; i++) {
            Log.d("char-->", check_data_str.length + " " + i + " " + check_data_str[i].toString());

            //检测车贷字段
            switch (i) {
                case 0:
                    if ("".equals(check_data_str[0]))
                        Toast.makeText(getApplicationContext(), "姓名不能为空", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    if (!DataVeri.isNaN(check_data_str[2])) {
                        Toast.makeText(getApplicationContext(), "年龄输入有误", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!DataVeri.isAge(check_data_str[2])) {
                            Toast.makeText(getApplicationContext(), "年龄输入有误", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 3:
                    if (!DataVeri.isMobileNum(check_data_str[3])) {
                        Toast.makeText(getApplicationContext(), "手机号输入有误", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }

    }


    protected StringBuffer getData() {
        StringBuffer data = new StringBuffer();
        //循环遍历LinearLayout Item，获取子控件数据
        Log.d("ChildCount-->", "|" + ll_person_detail.getChildCount());
        for (int i = 0; i < ll_person_detail.getChildCount(); i++) {
            RelativeLayout childLinearlayout = (RelativeLayout) ll_person_detail.getChildAt(i);
            Log.d("ChildChildCount-->", "|" + childLinearlayout.getChildCount());
            for (int j = 0; j < childLinearlayout.getChildCount(); j++) {
                View v = childLinearlayout.getChildAt(j);
                if (v instanceof LinearLayout) {
                    for (int z = 0; z < ((LinearLayout) v).getChildCount(); z++) {
                        View vv = ((LinearLayout) v).getChildAt(z);
                        if ((vv instanceof TextView) && vv.getId() == R.id.right && vv.getVisibility() == View.VISIBLE) {
                            data.append(((TextView) vv).getText() + ",");
                        }
                    }
                }

                if (v instanceof EditText && v.getVisibility() == View.VISIBLE) {
                    data.append(((EditText) v).getText() + ",");
                }

                if (v instanceof RadioGroup) {
                    if (v.getVisibility() == View.VISIBLE) {
                        for (int k = 0; k < ((RadioGroup) v).getChildCount(); k++) {
                            View vvv = ((RadioGroup) v).getChildAt(k);
                            if ((vvv instanceof RadioButton) && ((RadioButton) vvv).isChecked()) {
                                data.append(((RadioButton) vvv).getText().toString() + ",");
                            }
                        }
                    }
                }
            }
        }
        data.deleteCharAt(data.length() - 1);

        //遍历备注
        for (int i = 0; i < ll_my_loan_detail.getChildCount(); i++) {
            RelativeLayout relativeLayout = (RelativeLayout) ll_my_loan_detail.getChildAt(i);
            EditText editText = (EditText) relativeLayout.getChildAt(1);
            image_remark[i] = editText.getText().toString();
            if ("".equals(image_remark[i])) {
                TXWLApplication.getInstance().showTextToast("备注不能为空");
            }
        }

        return data;
    }

    protected void initView() {
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
    }

    @Override
    public void fill_LinearLayout(String title_name, String[] owner, Integer[] selectors) {
        tv_title.setText(title_name);
        //如果处于显示状态
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
                registToInternet();
            }
        });
        for (int i = 0; i < selectors.length; i++) {
            AddHouse_Items(ll_my_loan_detail, selectors[i], "备注", i);
        }

        for (int i = 0; i < owner.length; i++) {
            AddItems(ll_person_detail, owner[i], null);
        }

    }

    @Override
    public void fill_LinearLayout(String title_name, String[] owner, String[] selectors, QueryDetailResultBean queryDetailResultBean) {
        tv_title.setText(title_name);
        //如果处于显示状态
        tv_right.setText("举报");
        //跳转到举报界面
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                startActivity(intent);
            }
        });

        for (int i = 0; i < selectors.length; i++) {
            AddHouse_Items(ll_my_loan_detail, selectors[i], image_show[i], car_owner_des_show[i]);
        }

        for (int i = 0; i < owner.length; i++) {
            AddItems(ll_person_detail, owner[i], queryDetailResultBean);
        }

    }

    protected void registToInternet() {
    }


    /**
     * paras data1 : 静态字段
     * paras data2 : 从服务端获取的字段，需要处理成数组
     */
    @Override
    public void AddItems(LinearLayout linearLayout, String data1, QueryDetailResultBean queryDetailResultBean) {
        RelativeLayout ll_detail = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_one, null);
        TextView left = (TextView) ll_detail.findViewById(R.id.left);
        TextView right = (TextView) ll_detail.findViewById(R.id.right);
        RadioGroup rg_male_female = (RadioGroup) ll_detail.findViewById(R.id.rg_male_female);
        EditText et_input_content = (EditText) ll_detail.findViewById(R.id.et_input_content);
        et_input_content.setVisibility(View.VISIBLE);
        left.setText(data1);

        //如果处于显示状态，right需要显示
        if ("query_listview_car_item".equals(from_button) || "query_listview_house_item".equals(from_button) ||
                "query_listview_credit_item".equals(from_button) || "query_listview_other_item".equals(from_button)) {
            right.setVisibility(View.VISIBLE);
            et_input_content.setVisibility(View.GONE);
            if ("性别".equals(data1)) {
                rg_male_female.setVisibility(View.GONE);
                right.setVisibility(View.VISIBLE);
                right.setText(queryDetailResultBean.getSex());
            }
            if (data1.equals(houseowner[0]) || data1.equals(carowner[0]) || data1.equals(creditowner[0])) {
                right.setText(queryDetailResultBean.getName());
            } else if (data1.equals(houseowner[3]) || data1.equals(carowner[3]) || data1.equals(creditowner[3])) {
                right.setText(queryDetailResultBean.getMobile());
            } else if (data1.equals(houseowner[4]) || data1.equals(carowner[4]) || data1.equals(creditowner[4])) {
                right.setText(queryDetailResultBean.getCompanyname());
            } else if ("借款金额".equals(data1)) {
                right.setText(queryDetailResultBean.getAccount());
            } else if ("年利率".equals(data1)) {
                right.setText(queryDetailResultBean.getAnnualrate());
            } else if ("详细说明".equals(data1)) {
                right.setText(queryDetailResultBean.getDescription());
            }

            if ("年龄".equals(data1)) {
                et_input_content.setInputType(InputType.TYPE_CLASS_NUMBER);
                right.setText(queryDetailResultBean.getSex());
            }
            if ("借款日".equals(data1) || "还款日".equals(data1)) {
                ll_detail.findViewById(R.id.btn_entry).setVisibility(View.GONE);
                right.setText("借款日".equals(data1) ? queryDetailResultBean.getLoanday() : queryDetailResultBean.getRepayday());
            }
            if ("借款金额".equals(data1)) {
                et_input_content.setInputType(InputType.TYPE_CLASS_NUMBER);
                right.setText(queryDetailResultBean.getAccount() + "元");
            }
            if ("年利率".equals(data1)) {
                et_input_content.setInputType(InputType.TYPE_CLASS_NUMBER);
                right.setText(queryDetailResultBean.getAnnualrate() + "％");
            }

        } else {
            if ("性别".equals(data1)) {
                right.setVisibility(View.GONE);
                et_input_content.setVisibility(View.GONE);
                rg_male_female.setVisibility(View.VISIBLE);
            }
            if (data1.equals("借款金额")) {
                et_input_content.setInputType(InputType.TYPE_CLASS_NUMBER);
            }
            if (data1.equals("年利率")) {
                et_input_content.setInputType(InputType.TYPE_CLASS_NUMBER);
            }
            if (data1.equals("借款日")) {
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

            if (data1.equals("还款日")) {
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
     * @param layout 填充容器对象
     * @param house  ImageButton 背景图片资源
     * @param remark 备注或描述字段
     */
    private void AddHouse_Items(LinearLayout layout, Integer house, String remark, int position) {
        RelativeLayout rl_house_detail = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_two, null);
        ImageButton btn_photo = (ImageButton) rl_house_detail.findViewById(R.id.btn_photo);
        EditText et_remark = (EditText) rl_house_detail.findViewById(R.id.et_remark);
        btn_photo.setImageResource(house);
        et_remark.setHint(remark);
        layout.addView(rl_house_detail);
        //添加图片上传功能
        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoanActivity.this, PhotoActivity.class);
                intent.putExtra("from", position);
                startActivityForResult(intent, Constant.GETPHOTO);
            }
        });
    }

    private void AddHouse_Items(LinearLayout layout, String house, String remark, String descrip_content) {
        //如果处于显示状态
        RelativeLayout rl_item_five = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_five, null);
        ImageButton btn_photo = (ImageButton) rl_item_five.findViewById(R.id.btn_photo);
        TextView tv_descrip = (TextView) rl_item_five.findViewById(R.id.tv_descrip);
        TextView tv_descrip_content = (TextView) rl_item_five.findViewById(R.id.tv_descrip_content);
        LoaderBusiness.loadImage(house, btn_photo);
        btn_photo.setClickable(false);
        tv_descrip.setText(remark);
        tv_descrip_content.setText(descrip_content);
        layout.addView(rl_item_five);

    }

    //设置日期选择对话框
    protected DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            loan_datetime = new StringBuffer().append(year + "-").append(month + 1 + "-").append(day).toString();
            setLoanTime();
        }
    };

    protected void setLoanTime() {

    }

    protected void setPaymentTime() {

    }


    protected void LocateDatePikerItem(int date_item, String date_time) {
        RelativeLayout rl_loan_time = (RelativeLayout) ll_person_detail.getChildAt(date_item);
        LinearLayout ll_loan_time = (LinearLayout) rl_loan_time.getChildAt(3);
        TextView tv_loan_time = (TextView) ll_loan_time.getChildAt(0);
        tv_loan_time.setText(date_time);
    }

    protected DatePickerDialog.OnDateSetListener mDatePaymentSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            paymenttime = new StringBuffer().append(year + "-").append(month + 1 + "-").append(day).toString();
            setPaymentTime();
        }
    };


    protected Dialog onCreateDialog(int id) {
        final Calendar c = Calendar.getInstance();
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH));
            case DATE_PAMENT_DIALOG_ID:
                return new DatePickerDialog(this, mDatePaymentSetListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH));
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.GETPHOTO:
                if (data != null) {
                    //获取结果
                    int position = data.getIntExtra("from", -1);
                    File imgFile = data.getParcelableExtra("img");
                    image_url[position] = data.getStringExtra("imgUrl");
                    //设置图片
                    RelativeLayout childView = (RelativeLayout) ll_my_loan_detail.getChildAt(position);
                    ImageButton imageButton = (ImageButton) childView.getChildAt(0);

                    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getPath());
                    imageButton.setImageBitmap(bitmap);
                }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}

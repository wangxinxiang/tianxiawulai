package com.example.txwl_first;

import android.util.Log;
import android.widget.Toast;
import com.example.txwl_first.Util.DataVeri;

/**
 * Created by Administrator on 2015/7/20.
 */
public class CarLoanActivity extends HouseLoanActivity{

    @Override
    protected void checkData() {
        String[] check_data_str = getData().toString().split(",");

        for (int i = 0; i < check_data_str.length; i++) {
            Log.d("char-->", check_data_str.length + " " + i + " " + check_data_str[i].toString());

            //检测房贷字段
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



    }
}

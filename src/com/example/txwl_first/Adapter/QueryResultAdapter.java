package com.example.txwl_first.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.txwl_first.R;
import com.example.txwl_first.bean.LoanBean;

import java.util.ArrayList;

public class QueryResultAdapter extends BaseAdapter {
    private Context mContext;
    private LoanBean loanBean;
    private ArrayList<LoanBean> loanBeans = new ArrayList<LoanBean>();
    private HolderView holderView;

    public QueryResultAdapter(Context context,ArrayList<LoanBean> loanBeanArrayList) {
        super();
        this.mContext = context;
        this.loanBeans = loanBeanArrayList;
    }

    @Override
    public int getCount() {
        return loanBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            holderView = new HolderView();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_three,null);
            holderView.own_user_head_image = (ImageView) convertView.findViewById(R.id.img_user_head);
            holderView.tv_own_user_name = (TextView) convertView.findViewById(R.id.tv_username);
            holderView.tv_own_user_phone = (TextView) convertView.findViewById(R.id.tv_user_phone_num);
            holderView.tv_debt_type = (TextView) convertView.findViewById(R.id.over_day);
            holderView.tv_debt_money = (TextView) convertView.findViewById(R.id.tv_money_count);
            holderView.tv_own_user_place = (TextView) convertView.findViewById(R.id.loan_type);

            convertView.setTag(holderView);
        }else {
            holderView = (HolderView) convertView.getTag();
        }
        final LoanBean loanBeanlist = loanBeans.get(position);

        holderView.own_user_head_image.setImageResource(R.drawable.defaul_head_image);
        holderView.tv_own_user_name.setText(loanBeanlist.getOwn_user_name());
        holderView.tv_own_user_phone.setText(loanBeanlist.getOwn_user_phone());
        holderView.tv_debt_type.setText(loanBeanlist.getDebt_type());
        holderView.tv_debt_money.setText(loanBeanlist.getDebt_money());
        holderView.tv_own_user_place.setText(loanBeanlist.getOwn_user_place());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class HolderView{
        private ImageView own_user_head_image;
        private TextView tv_own_user_name;
        private TextView tv_own_user_phone;
        private TextView tv_own_user_place;
        private TextView tv_debt_descrip;
        private TextView tv_debt_money;
        private TextView tv_debt_type;
        private TextView tv_reward_user_name;
        private TextView tv_reward_money;
        private TextView tv_reward_user_phone;
        private TextView tv_over_day;
    }
}

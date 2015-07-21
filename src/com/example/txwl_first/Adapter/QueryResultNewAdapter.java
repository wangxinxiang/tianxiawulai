package com.example.txwl_first.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.txwl_first.R;
import com.example.txwl_first.bean.QueryResultItemBean;
import com.example.txwl_first.business.LoaderBusiness;

import java.util.ArrayList;

public class QueryResultNewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<QueryResultItemBean> loanBeans = new ArrayList<>();
    private HolderView holderView;

    public QueryResultNewAdapter(Context context, ArrayList<QueryResultItemBean> loanBeanArrayList) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.query_result_item,null);
            holderView.own_user_head_image = (ImageView) convertView.findViewById(R.id.img_user_head);
            holderView.tv_own_user_name = (TextView) convertView.findViewById(R.id.tv_query_name);
            holderView.tv_own_user_phone = (TextView) convertView.findViewById(R.id.tv_query_phone_num);
            holderView.tv_debt_type = (TextView) convertView.findViewById(R.id.tv_query_over_day);
            holderView.tv_debt_money = (TextView) convertView.findViewById(R.id.tv_query_money_count);
            holderView.tv_own_user_place = (TextView) convertView.findViewById(R.id.tv_query_place);
            holderView.tv_reward_phone =  (TextView) convertView.findViewById(R.id.tv_reward_phone);
            holderView.tv_query_reward_num =   (TextView) convertView.findViewById(R.id.tv_query_reward_num);

            convertView.setTag(holderView);
        }else {
            holderView = (HolderView) convertView.getTag();
        }
        final QueryResultItemBean loanBeanlist = loanBeans.get(position);

        LoaderBusiness.loadImage(loanBeanlist.getOwneridimg(), holderView.own_user_head_image);
        holderView.tv_own_user_name.setText(loanBeanlist.getName());
        holderView.tv_own_user_phone.setText(loanBeanlist.getMobile());

        switch (loanBeanlist.getStatus2()) {
            case "1":
                holderView.tv_debt_type.setText(loanBeanlist.getDate());
                break;
            case "2":
                holderView.tv_debt_type.setText("已还款");
                holderView.tv_debt_type.setTextColor(mContext.getResources().getColor(R.color.orange_text));
                break;
            case "3":
                holderView.tv_debt_type.setText("已进黑名单");
                holderView.tv_debt_type.setTextColor(mContext.getResources().getColor(R.color.black));
                holderView.tv_query_reward_num.setText(loanBeanlist.getRegistcompany() + loanBeanlist.getContactname() + "悬赏" + loanBeanlist.getRewardmoney() + "元催收");
                holderView.tv_reward_phone.setText(loanBeanlist.getContactname() + "电话：" + loanBeanlist.getContactmobile());
                break;
        }

        holderView.tv_debt_money.setText(loanBeanlist.getDescription());
        holderView.tv_own_user_place.setText(loanBeanlist.getProvince());

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
        private TextView tv_debt_money;
        private TextView tv_debt_type;
        private TextView tv_reward_phone;
        private TextView tv_query_reward_num;
    }

    private String getLoan_type(String i) {
        switch (i) {
            case "1":
                return "车贷";
            case "2":
                return "房贷";
            case "3":
                return "信用贷";
            default:
                return "其他";
        }
    }
}

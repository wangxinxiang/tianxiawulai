package com.example.txwl_first.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.txwl_first.R;
import com.example.txwl_first.Util.DataVeri;
import com.example.txwl_first.bean.QueryResultItemBean;
import com.example.txwl_first.business.LoaderBusiness;

import java.util.ArrayList;

public class QueryResultNewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<QueryResultItemBean> loanBeans = new ArrayList<>();
    private HolderView holderView;
    private Resources resources;

    public QueryResultNewAdapter(Context context, ArrayList<QueryResultItemBean> loanBeanArrayList, Resources resources) {
        super();
        this.mContext = context;
        this.resources = resources;
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

        holderView.tv_debt_type.setText(loanBeanlist.getDate());
        holderView.tv_debt_money.setText("欠" + loanBeanlist.getRegistcompany() + DataVeri.getMoneyFromDouble(loanBeanlist.getAccount()) + "元");
        switch (loanBeanlist.getStatus2()) {
            case "1":
                holderView.tv_debt_type.setText(connetText(0, loanBeanlist.getDate(), "", ""));
                break;
            case "2":
                holderView.tv_debt_type.setTextColor(mContext.getResources().getColor(R.color.orange_text));
                break;
            case "3":
                holderView.tv_debt_type.setTextColor(mContext.getResources().getColor(R.color.black));
                if (!"5".equals(loanBeanlist.getRegisttype())) {
                    holderView.tv_query_reward_num.setText(connetText(1, loanBeanlist.getRegistcompany(), loanBeanlist.getContactname(), loanBeanlist.getRewardmoney() ));
                    holderView.tv_reward_phone.setText(loanBeanlist.getContactname() + "电话：" + loanBeanlist.getContactmobile());
                }

                holderView.tv_debt_money.setText("恶意拖欠" + loanBeanlist.getRegistcompany() + DataVeri.getMoneyFromDouble(loanBeanlist.getAccount()) + "元");
                break;
        }

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

    private SpannableStringBuilder connetText(int mode,String one,String two,String three) {
        //mode 值为判断返回字段类型
        //0  剩余天数
        //1 为第2行 赏金字段

        SpannableStringBuilder builder=new SpannableStringBuilder();

        if (one==null ||"".equals(one)||one.length()==0){
            one=" ";
        }

        if (two==null || "".equals(two)||two.length()==0){
            two=" ";
        }

        if (three==null || "".equals(three)||three.length()==0){
            three=" ";
        }
        int start;
        int end;
        switch (mode){
            case 0:
                builder.append(one);
                start = one.indexOf(":") + 1;
                end = one.length() - 1;
                builder.setSpan(new ForegroundColorSpan(resources.getColor(R.color.orange_text)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case 1:
                three = DataVeri.getMoneyFromDouble(three);
                start=one.length()+two.length()+2;
                end=start+three.length();
                builder.append(one);
                builder.append(two);
                builder.append("悬赏");
                builder.append(three);
                builder.append("元催收");
                builder.setSpan(new ForegroundColorSpan(resources.getColor(R.color.orange_text)),start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
        }
        return builder;
    }

}

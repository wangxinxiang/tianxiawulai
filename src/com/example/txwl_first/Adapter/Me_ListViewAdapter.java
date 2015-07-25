package com.example.txwl_first.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.txwl_first.R;
import com.example.txwl_first.Util.DataVeri;
import com.example.txwl_first.bean.GetMyInfoItemBean;
import com.example.txwl_first.bean.GetPersonalInfoBean;
import com.example.txwl_first.business.LoaderBusiness;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/9 0009.
 */
public class Me_ListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private ArrayList<GetMyInfoItemBean> mDatas;
    public Me_ListViewAdapter(Context context, ArrayList<GetMyInfoItemBean> list) {
        this.mContext=context;
        mInflater=LayoutInflater.from(context);
        this.mDatas=list;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.item_fragment_me,null);
            viewHolder=new ViewHolder();
            viewHolder.img_user_head= (ImageView) convertView.findViewById(R.id.img_user_head);
            viewHolder.tv_username= (TextView) convertView.findViewById(R.id.tv_username);
            viewHolder.tv_user_phone_num= (TextView) convertView.findViewById(R.id.tv_user_phone_num);
            viewHolder.loan_type= (TextView) convertView.findViewById(R.id.loan_type);
            viewHolder.tv_money_count= (TextView) convertView.findViewById(R.id.tv_money_count);
            viewHolder.tv_manager= (TextView) convertView.findViewById(R.id.tv_manager);
            viewHolder.tv_year= (TextView) convertView.findViewById(R.id.tv_year);
            viewHolder.over_day= (TextView) convertView.findViewById(R.id.over_day);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        GetMyInfoItemBean item=mDatas.get(position);
        LoaderBusiness.loadImage(item.getOwneridimg(),viewHolder.img_user_head);
        viewHolder.tv_username.setText(item.getName());
        viewHolder.tv_user_phone_num.setText(item.getMobile());
        viewHolder.loan_type.setText(DataVeri.getLoan_type(item.getRegisttype()));
        viewHolder.tv_money_count.setText("借款金额："+item.getAccount());
        viewHolder.tv_manager.setText("经办人"+item.getRegistname());
        viewHolder.tv_year.setText("利率："+item.getAnnualrate()+"%");
        viewHolder.over_day.setText(item.getDate());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        ImageView img_user_head;
        TextView tv_username;
        TextView tv_user_phone_num;
        TextView loan_type;
        TextView tv_money_count;
        TextView tv_manager;
        TextView tv_year;
        TextView over_day;
    }
}

package com.example.txwl_first.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.txwl_first.R;
import com.example.txwl_first.bean.GetLaolaiListItemBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/9 0009.
 */
public class LaolaiListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private ArrayList<GetLaolaiListItemBean> list;
    public LaolaiListAdapter(Context context, ArrayList<GetLaolaiListItemBean> list) {
        this.mContext=context;
        mInflater=LayoutInflater.from(context);
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
            if(convertView==null){
                convertView=mInflater.inflate(R.layout.laolai_list_item,null);
                viewHolder=new ViewHolder();
                viewHolder.tv_case_title= (TextView) convertView.findViewById(R.id.tv_case_title);
                viewHolder.tv_case_time= (TextView) convertView.findViewById(R.id.tv_case_time);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= (ViewHolder) convertView.getTag();
            }
            GetLaolaiListItemBean item=list.get(position);
            viewHolder.tv_case_title.setText(item.getCasetitle());
            viewHolder.tv_case_time.setText(item.getCasetime());
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        TextView tv_case_title;
        TextView tv_case_time;
    }
}

package com.example.txwl_first.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.txwl_first.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/9 0009.
 */
public class Me_ListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<String> mDatas;
    public Me_ListViewAdapter(Context context, ArrayList<String> list) {
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
            convertView=mInflater.inflate(R.layout.me_item,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_name.setText("我是的"+mDatas.get(position).toString()+"第"+position+"项");
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        TextView tv_name;
    }
}

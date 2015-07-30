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
import android.widget.TextView;
import com.example.txwl_first.R;
import com.example.txwl_first.bean.QueryResultLaolaiItemBean;

import java.util.ArrayList;

public class QueryResultLaolaiAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<QueryResultLaolaiItemBean> list = new ArrayList<>();
    private HolderView holderView;
    private Resources resources;

    public QueryResultLaolaiAdapter(Context context, ArrayList<QueryResultLaolaiItemBean> ArrayList, Resources resources) {
        super();
        this.mContext = context;
        this.resources = resources;
        this.list = ArrayList;
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
        if(convertView==null){
            holderView = new HolderView();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_query_result_laolai,null);
            holderView.tv_query_name = (TextView) convertView.findViewById(R.id.tv_query_name);
            holderView.tv_query_age = (TextView) convertView.findViewById(R.id.tv_query_age);
            holderView.tv_query_cardnum = (TextView) convertView.findViewById(R.id.tv_query_cardnum);
            holderView.tv_query_area = (TextView) convertView.findViewById(R.id.tv_query_area);
            holderView.tv_query_casecount = (TextView) convertView.findViewById(R.id.tv_query_casecount);
            convertView.setTag(holderView);
        }else {
            holderView = (HolderView) convertView.getTag();
        }
        final QueryResultLaolaiItemBean bean = list.get(position);

        holderView.tv_query_name.setText(bean.getName());
        holderView.tv_query_age.setText(bean.getAge());

        holderView.tv_query_cardnum.setText(bean.getCardnum());
        holderView.tv_query_area.setText(bean.getArea());
        holderView.tv_query_casecount.setText(connetText("失信记录：",bean.getCasecount(),"条"));

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class HolderView{
        private TextView tv_query_name;
        private TextView tv_query_age;
        private TextView tv_query_cardnum;
        private TextView tv_query_area;
        private TextView tv_query_casecount;
    }

    private SpannableStringBuilder connetText(String one,String two,String three) {

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
        int start=one.length();
        int end=start+two.length();
        builder.append(one);
        builder.append(two);
        builder.append(three);
        builder.setSpan(new ForegroundColorSpan(resources.getColor(R.color.orange_text)),start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

}

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
import com.example.txwl_first.bean.BlackListBean;
import com.example.txwl_first.bean.BlackListItemBean;
import com.example.txwl_first.bean.QueryResultItemBean;
import com.example.txwl_first.business.LoaderBusiness;

import java.util.ArrayList;

public class BlackListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<BlackListItemBean> blackList=null;
    private Resources resources;

    public BlackListAdapter(Context context, ArrayList<BlackListItemBean> blackList,Resources resources) {
        super();
        this.mContext = context;
        this.blackList = blackList;
        this.resources=resources;
    }

    @Override
    public int getCount() {
        return blackList.size();
    }

    @Override
    public BlackListItemBean getItem(int position) {
        return blackList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderView holderView=null;
        if(convertView==null){
            holderView = new HolderView();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_black,null);

            holderView.img_user_head = (ImageView) convertView.findViewById(R.id.img_user_head);
            holderView.tv_username = (TextView) convertView.findViewById(R.id.tv_username);
            holderView.tv_user_phone_num = (TextView) convertView.findViewById(R.id.tv_user_phone_num);
            holderView.tv_province = (TextView) convertView.findViewById(R.id.tv_province);
            holderView.tv_descrip = (TextView) convertView.findViewById(R.id.tv_descrip);
            holderView.tv_reward = (TextView) convertView.findViewById(R.id.tv_reward);
            holderView.tv_contact =  (TextView) convertView.findViewById(R.id.tv_contact);

            convertView.setTag(holderView);
        }else {
            holderView = (HolderView) convertView.getTag();
        }

        final BlackListItemBean item = blackList.get(position);

        holderView.tv_username.setText(item.getName());//设置用户名
        holderView.tv_user_phone_num.setText(item.getMobile());//手机号码
        holderView.tv_province.setText(item.getProvince());//地点
        holderView.tv_descrip.setText(connetText(0,item.getCompanyname(),item.getAccount(),""));//项目描述
        holderView.tv_reward.setText(connetText(1,item.getRegistcompany(),item.getContactname(),item.getRewardmoney()));//赏金
        holderView.tv_contact.setText(connetText(2,item.getContactname(),item.getContactmobile(),""));//联系人电话

//        holderView.tv_descrip.setText(connetText(0,"658小贷公司","10",""));//项目描述
//        holderView.tv_reward.setText(connetText(1,"658小贷","张先生","20"));//赏金
//        holderView.tv_contact.setText(connetText(2,"张先生","55555",""));//联系人电话

        LoaderBusiness.loadImage(item.getOwneridimg(), holderView.img_user_head);//加载图片


        return convertView;
    }

    private SpannableStringBuilder connetText(int mode,String one,String two,String three) {
        //mode 值为判断返回字段类型
        //0 为第1行 描述字段
        //1 为第2行 赏金字段
        //2 为第3行 联系电话字段

        SpannableStringBuilder builder=new SpannableStringBuilder();

        if (one==null||"".equals(one)||one.length()==0){
            one=" ";
        }

        if (two==null||"".equals(two)||two.length()==0){
            two=" ";
        }

        if (three==null||"".equals(three)||three.length()==0){
            three=" ";
        }
        switch (mode){
            case 0:
                builder.append("恶意拖欠");
                builder.append(one);
                builder.append(two);
                builder.append("元");
                break;
            case 1:
                int start=one.length()+two.length()+2;
                int end=start+three.length();
                builder.append(one);
                builder.append(two);
                builder.append("悬赏");
                builder.append(three);
                builder.append("元催收");
                builder.setSpan(new ForegroundColorSpan(resources.getColor(R.color.orange_text)),start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case 2:
                builder.append(one);
                builder.append("电话：");
                builder.append(two);
                break;
        }
        return builder;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class HolderView{
        private ImageView img_user_head;
        private TextView tv_username;
        private TextView tv_user_phone_num;
        private TextView tv_province;
        private TextView tv_descrip;
        private TextView tv_reward;
        private TextView tv_contact;
    }


}

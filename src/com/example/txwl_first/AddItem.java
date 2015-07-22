package com.example.txwl_first;

import android.widget.LinearLayout;
import com.example.txwl_first.bean.QueryDetailResultBean;

/**
 * Created by licheng on 15/7/15.
 */
public interface AddItem {
    void AddItems(LinearLayout linearLayout, String data1, QueryDetailResultBean queryDetailResultBean);
    void fill_LinearLayout(String title_name, String[] owner, Integer[] selectors);
    void fill_LinearLayout(String title_name, String[] owner, String[] selectors, QueryDetailResultBean queryDetailResultBean);
}

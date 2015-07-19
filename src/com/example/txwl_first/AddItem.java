package com.example.txwl_first;

import android.widget.LinearLayout;

/**
 * Created by licheng on 15/7/15.
 */
public interface AddItem {
    void AddItems(LinearLayout linearLayout,String data1,String data2);
    void fill_LinearLayout(String title_name,String[] owner,Integer[] selectors);
}

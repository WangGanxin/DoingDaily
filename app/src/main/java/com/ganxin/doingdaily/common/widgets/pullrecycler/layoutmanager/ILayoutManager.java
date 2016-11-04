package com.ganxin.doingdaily.common.widgets.pullrecycler.layoutmanager;

import android.support.v7.widget.RecyclerView;

import com.ganxin.doingdaily.common.widgets.pullrecycler.BaseListAdapter;


/**
 * Description : ILayoutManager  <br/>
 * author : WangGanxin <br/>
 * date : 2016/11/4 <br/>
 * email : ganxinvip@163.com <br/>
 */
public interface ILayoutManager {
    RecyclerView.LayoutManager getLayoutManager();
    int findLastVisiblePosition();
    void setUpAdapter(BaseListAdapter adapter);
}

package com.ganxin.doingdaily.common.widgets.pullrecycler.layoutmanager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.ganxin.doingdaily.common.widgets.pullrecycler.BaseListAdapter;

/**
 * Description : 线性布局管理器  <br/>
 * author : WangGanxin <br/>
 * date : 2016/12/8 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class MyLinearLayoutManager extends LinearLayoutManager implements ILayoutManager {
    public MyLinearLayoutManager(Context context) {
        super(context);
    }

    public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return this;
    }

    @Override
    public int findFirstVisiblePosition() {
        return findFirstVisibleItemPosition();
    }

    @Override
    public int findLastVisiblePosition() {
        return findLastVisibleItemPosition();
    }

    @Override
    public void setUpAdapter(BaseListAdapter adapter) {

    }
}

package com.ganxin.doingdaily.common.widgets.pullrecycler.layoutmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import com.ganxin.doingdaily.common.widgets.pullrecycler.BaseListAdapter;

/**
 * Description : 瀑布流布局管理器  <br/>
 * author : WangGanxin <br/>
 * date : 2017/7/18 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class MyStaggeredGridLayoutManager extends StaggeredGridLayoutManager implements ILayoutManager {

    public MyStaggeredGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyStaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return this;
    }

    @Override
    public int findFirstVisiblePosition() {
        return 0;
    }

    @Override
    public int findLastVisiblePosition() {

        // 由于是StaggeredGridLayoutManager 取最底部数据可能有两个item，所以判断这之中有一个正好是 最后一条数据的index就OK
        int[] bottom = findLastCompletelyVisibleItemPositions(new int[2]);
        int lastItemCount = getItemCount() - 1;

        if (bottom[0] == lastItemCount) {
            return bottom[0];
        } else {
            return bottom[1];
        }
    }

    @Override
    public void setUpAdapter(BaseListAdapter adapter) {

    }
}

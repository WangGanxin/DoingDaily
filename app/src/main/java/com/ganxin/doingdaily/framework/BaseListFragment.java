package com.ganxin.doingdaily.framework;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.widgets.pullrecycler.BaseListAdapter;
import com.ganxin.doingdaily.common.widgets.pullrecycler.BaseViewHolder;
import com.ganxin.doingdaily.common.widgets.pullrecycler.DividerItemDecoration;
import com.ganxin.doingdaily.common.widgets.pullrecycler.PullRecycler;
import com.ganxin.doingdaily.common.widgets.pullrecycler.layoutmanager.ILayoutManager;
import com.ganxin.doingdaily.common.widgets.pullrecycler.layoutmanager.MyLinearLayoutManager;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Description : BaseListFragment  <br/>
 * author : WangGanxin <br/>
 * date : 2016/10/9 <br/>
 * email : ganxinvip@163.com <br/>
 * @param <D>    data数据类型
 */
public abstract class BaseListFragment<D> extends BaseFragment implements PullRecycler.OnRecyclerRefreshListener {

    @BindView(R.id.pullRecycler)
    protected PullRecycler pullRecycler;

    protected BaseListAdapter adapter;
    protected ArrayList<D> mDataList;

    @Override
    public int setContentLayout() {
        return R.layout.fragment_base_list;
    }

    @Override
    public void setUpView(View view) {
        setUpAdapter();
        pullRecycler.setOnRefreshListener(this);
        pullRecycler.setLayoutManager(getLayoutManager());
        pullRecycler.addItemDecoration(getItemDecoration());
        pullRecycler.setAdapter(adapter);
    }

    protected void setUpAdapter() {
        adapter = new ListAdapter();
    }

    protected ILayoutManager getLayoutManager() {
        return new MyLinearLayoutManager(getContext());
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(getContext(), R.drawable.list_divider);
    }

    public class ListAdapter extends BaseListAdapter {

        @Override
        protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
            return getViewHolder(parent, viewType);
        }

        @Override
        protected int getDataCount() {
            return mDataList != null ? mDataList.size() : 0;
        }

        @Override
        protected int getDataViewType(int position) {
            return getItemType(position);
        }

        @Override
        public boolean isSectionHeader(int position) {
            return BaseListFragment.this.isSectionHeader(position);
        }
    }

    protected boolean isSectionHeader(int position) {
        return false;
    }

    protected int getItemType(int position) {
        return 0;
    }

    protected abstract BaseViewHolder getViewHolder(ViewGroup parent, int viewType);
}

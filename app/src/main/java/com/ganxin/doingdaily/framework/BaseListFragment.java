package com.ganxin.doingdaily.framework;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.utils.LogUtil;
import com.ganxin.doingdaily.common.widgets.pullrecycler.BaseListAdapter;
import com.ganxin.doingdaily.common.widgets.pullrecycler.BaseViewHolder;
import com.ganxin.doingdaily.common.widgets.pullrecycler.DividerItemDecoration;
import com.ganxin.doingdaily.common.widgets.pullrecycler.PullRecycler;
import com.ganxin.doingdaily.common.widgets.pullrecycler.layoutmanager.ILayoutManager;
import com.ganxin.doingdaily.common.widgets.pullrecycler.layoutmanager.MyLinearLayoutManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description : BaseListFragment  <br/>
 * author : WangGanxin <br/>
 * date : 2016/10/9 <br/>
 * email : ganxinvip@163.com <br/>
 * @param <D>    data数据类型
 * @param <T>    presenter
 */
public abstract class BaseListFragment<D,T extends BasePresenter> extends Fragment implements PullRecycler.OnRecyclerRefreshListener {

    @BindView(R.id.pullRecycler)
    protected PullRecycler pullRecycler;

    protected T mPresenter;

    private boolean isVisibleToUser;
    private boolean isViewInitialized;
    private boolean isDataInitialized;
    private boolean isLazyLoadEnabled;

    protected BaseListAdapter adapter;
    protected ArrayList<D> mDataList;

    /**
     * 执行于setUpView之后
     * @return
     */
    protected abstract T setPresenter();
    protected abstract void setUpData();

    /**
     * 是否开启懒加载
     */
    public void enableLazyLoad(){
        isLazyLoadEnabled = true;
    }

    public void setUpView() {
        setUpAdapter();
        pullRecycler.setOnRefreshListener(this);
        pullRecycler.setLayoutManager(getLayoutManager());
        pullRecycler.addItemDecoration(getItemDecoration());
        pullRecycler.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.logI("BaseListFragment",toString() + ":onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.logI("BaseListFragment",toString() + ":onCreate");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        LogUtil.logI("BaseListFragment",toString() + ":setUserVisibleHint:" + isVisibleToUser);
        checkIfLoadData();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.logI("BaseListFragment",toString() + ":onCreateView");
        View mRootView= inflater.inflate(R.layout.fragment_base_list, container, false);
      //  enableLazyLoad();
        ButterKnife.bind(this,mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.logI("BaseListFragment",toString() + ":onViewCreated");
        if (!isLazyLoadEnabled){
            setUpView();
            initPresenter();
        }else {
            setUpView();
            isViewInitialized = true;
            if (savedInstanceState != null){
                onRestoreInstanceState(savedInstanceState);
            }
            if (isDataInitialized){
                initPresenter();
            }else {
                checkIfLoadData();
            }
        }
    }

    private void initPresenter() {
        if(mPresenter==null){
            mPresenter=setPresenter();
          //  mPresenter.attatchView((V)this);
            mPresenter.attatchView(this);
            setUpData();
        }
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        isDataInitialized = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.logI("BaseListFragment",toString() + ":onActivityCreated");

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        LogUtil.logI("BaseListFragment",toString() + ":onViewStateRestored");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewInitialized = false;
        if(mPresenter!=null){
            mPresenter.detachView();
        }
        LogUtil.logI("BaseListFragment",toString() + ":onDestroyView");
    }

    private void checkIfLoadData() {
        if (isVisibleToUser && isViewInitialized && !isDataInitialized) {
            isDataInitialized = true;
            initPresenter();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.logI("BaseListFragment",toString() + ":onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.logI("BaseListFragment",toString() + ":onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.logI("BaseListFragment",toString() + ":onPause");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.logI("BaseListFragment",toString() + ":onSaveInstanceState");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.logI("BaseListFragment",toString() + ":onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.logI("BaseListFragment",toString() + ":onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.logI("BaseListFragment",toString() + ":onDetach");
    }


    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        LogUtil.logI("BaseListFragment",toString() + ":onInflate");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtil.logI("BaseListFragment",toString() + ":onHiddenChanged:" + hidden);
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
            LogUtil.i("adapter---------");
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

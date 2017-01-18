package com.ganxin.doingdaily.framework;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ganxin.doingdaily.common.utils.LogUtil;

import butterknife.ButterKnife;

/**
 * Description : BaseFragment  <br/>
 * author : WangGanxin <br/>
 * date : 2016/10/9 <br/>
 * email : ganxinvip@163.com <br/>
 * @param <V> View
 * @param <T> Presenter
 */
public abstract class BaseFragment<V extends BaseView,T extends BasePresenter<V>> extends Fragment{

    protected T mPresenter;

    private boolean isVisibleToUser;
    private boolean isViewInitialized;
    private boolean isDataInitialized;
    private boolean isLazyLoadEnabled;

    protected Activity mActivity;

    /**
     * 执行于OnCreateView方法
     * @return layoutId 返回布局Id
     */
    public abstract int setContentLayout();
    /**
     * 执行于onCreateView之后
     * @param view onCreateView创建的视图
     */
    public abstract void setUpView(View view);
    /**
     * 执行于setUpView之后
     * @return
     */
    protected abstract T setPresenter();

    public T getmPresenter() {
        return mPresenter;
    }

    /**
     * 是否开启懒加载
     */
    public void enableLazyLoad(){
        isLazyLoadEnabled = true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.logI("BaseFragment",toString() + ":onAttach");
        this.mActivity= (Activity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.logI("BaseFragment",toString() + ":onCreate");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        LogUtil.logI("BaseFragment",toString() + ":setUserVisibleHint:" + isVisibleToUser);
        checkIfLoadData();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.logI("BaseFragment",toString() + ":onCreateView");
        View mRootView= inflater.inflate(setContentLayout(), container, false);
        ButterKnife.bind(this,mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.logI("BaseFragment",toString() + ":onViewCreated");
        if (!isLazyLoadEnabled){
            setUpView(view);
            initPresenter();
        }else {
            setUpView(view);
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

    protected void initPresenter() {
        if(mPresenter==null){
            mPresenter=setPresenter();
            mPresenter.attatchView((V)this);
        }
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        isDataInitialized = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.logI("BaseFragment",toString() + ":onActivityCreated");

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        LogUtil.logI("BaseFragment",toString() + ":onViewStateRestored");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewInitialized = false;
        if(mPresenter!=null){
            mPresenter.detachView();
        }
        LogUtil.logI("BaseFragment",toString() + ":onDestroyView");
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
        LogUtil.logI("BaseFragment",toString() + ":onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.logI("BaseFragment",toString() + ":onResume");
    }

    protected void onBack(){
        getActivity().onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.logI("BaseFragment",toString() + ":onPause");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.logI("BaseFragment",toString() + ":onSaveInstanceState");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.logI("BaseFragment",toString() + ":onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.logI("BaseFragment",toString() + ":onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.logI("BaseFragment",toString() + ":onDetach");
    }


    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        LogUtil.logI("BaseFragment",toString() + ":onInflate");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtil.logI("BaseFragment",toString() + ":onHiddenChanged:" + hidden);
    }
}

package com.ganxin.doingdaily.framework;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Description : BasePresenter  <br/>
 * author : WangGanxin <br/>
 * date : 2016/10/28 <br/>
 * email : ganxinvip@163.com <br/>
 */
public abstract class BasePresenter<T> {
    protected Reference<T> mViewRef; //View接口类型的弱引用
    protected RxManager mRxManager;

    public void attatchView(T view){
        mViewRef=new WeakReference<>(view); //建立关联
        mRxManager = new RxManager();
        this.onStart();
    }

    public T getView(){
        return mViewRef.get();
    }

    public RxManager getRxManager() {
        return mRxManager;
    }

    public boolean isViewAttachted(){
        return mViewRef!=null&&mViewRef.get()!=null;
    }

    public void detachView(){
        if(mViewRef!=null){
            mViewRef.clear();
            mViewRef=null;
        }

        if(mRxManager!=null){
            mRxManager.clear();
        }
    }

    public abstract void onStart();
}

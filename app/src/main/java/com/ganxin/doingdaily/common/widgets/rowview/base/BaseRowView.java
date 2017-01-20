package com.ganxin.doingdaily.common.widgets.rowview.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 *
 * Description : BaseRowView  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/13 <br/>
 * email : mail@wangganxin.me <br/>
 */
public abstract class BaseRowView<T extends BaseRowDescriptor> extends LinearLayout {
    protected OnRowChangedListener listener;
    protected T descriptor;

    public BaseRowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public BaseRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRowView(Context context) {
        super(context);
    }

    public void setOnRowChangedListener(OnRowChangedListener listener) {
        this.listener = listener;
    }

    public abstract void notifyDataChanged(T descriptor);

    public void notifyDataChanged(String content) {

    }

    public String getContent() {
        return null;
    }

}

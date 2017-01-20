package com.ganxin.doingdaily.common.widgets.rowview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.ganxin.doingdaily.BuildConfig;
import com.ganxin.doingdaily.common.widgets.rowview.base.BaseRowDescriptor;
import com.ganxin.doingdaily.common.widgets.rowview.base.BaseRowView;
import com.ganxin.doingdaily.common.widgets.rowview.base.OnRowChangedListener;
import com.ganxin.doingdaily.common.widgets.rowview.group.GroupDescriptor;
import com.ganxin.doingdaily.common.widgets.rowview.group.GroupView;

import java.util.ArrayList;

/**
 *
 * Description : 容器类  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/13 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class ContainerView extends LinearLayout {

    private Context context;
    private ArrayList<GroupDescriptor> descriptors;
    private OnRowChangedListener listener;
    private boolean hasPaddingTop;

    public ContainerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView(context);
    }

    public ContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }

    public ContainerView(Context context) {
        super(context);
        initializeView(context);
    }

    private void initializeView(Context context) {
        this.context = context;
        setOrientation(VERTICAL);
    }

    public void initializeData(ArrayList<GroupDescriptor> descriptors, OnRowChangedListener listener) {
        this.descriptors = descriptors;
        this.listener = listener;
    }

    public void notifyDataChanged() {
        removeAllViews();
        if (descriptors != null && descriptors.size() > 0) {
            GroupView group = null;
            float density = context.getResources().getDisplayMetrics().density;
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//            params.bottomMargin = (int) (10 * density);
            for (int i = 0; i < descriptors.size(); i++) {
                group = new GroupView(context);
                group.initializeData(descriptors.get(i), listener);
                if (i == 0 && hasPaddingTop) {
                    group.hasPaddingTop(hasPaddingTop);
                } else {
                    if (i != 0) {
                        group.hasPaddingTop(true);
                    }
                }
                group.notifyDataChanged();
                addView(group, params);
            }
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.GONE);
        }
    }

    public void notifyDataChanged(int rowId, String content) {
        BaseRowView row = (BaseRowView) findViewById(rowId);
        if (row != null) {
            row.notifyDataChanged(content);
        } else {
            if (BuildConfig.DEBUG) {
                throw new IllegalArgumentException("can't find the row by id");
            }
        }
    }

    public void notifyDataChanged(int rowId, BaseRowDescriptor descriptor) {
        BaseRowView row = (BaseRowView) findViewById(rowId);
        if (row != null) {
            row.notifyDataChanged(descriptor);
        } else {
            if (BuildConfig.DEBUG) {
                throw new IllegalArgumentException("can't find the row by id");
            }
        }
    }

    public String getContentById(int id) {
        BaseRowView row = (BaseRowView) findViewById(id);
        if (row != null) {
            return row.getContent();
        }
        if (BuildConfig.DEBUG) {
            throw new IllegalArgumentException("can't find the row by id");
        }
        return null;
    }

    public void hasPaddingTop(boolean hasPaddingTop) {
        this.hasPaddingTop = hasPaddingTop;
    }
}

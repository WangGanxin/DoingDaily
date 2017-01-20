package com.ganxin.doingdaily.common.widgets.rowview.normal;

import com.ganxin.doingdaily.common.widgets.rowview.base.BaseRowDescriptor;

/**
 *
 * Description : NormalRowDescriptor  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/13 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class NormalRowDescriptor extends BaseRowDescriptor {
    public int iconResId;
    public String label;
    public boolean hasAction;


    public NormalRowDescriptor(int rowId) {
        super(rowId);
    }

    public NormalRowDescriptor iconResId(int iconResId) {
        this.iconResId = iconResId;
        return this;
    }

    public NormalRowDescriptor label(String label) {
        this.label = label;
        return this;
    }

    public NormalRowDescriptor hasAction(boolean hasAction) {
        this.hasAction = hasAction;
        return this;
    }

}

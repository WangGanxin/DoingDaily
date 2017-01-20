package com.ganxin.doingdaily.common.widgets.rowview.text;

import com.ganxin.doingdaily.common.widgets.rowview.base.BaseRowDescriptor;

/**
 * Description : TextRowDescriptor  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/13 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class TextRowDescriptor extends BaseRowDescriptor {
    public String content;
    public boolean hasAction;

    public TextRowDescriptor(int rowId) {
        super(rowId);
    }

    public TextRowDescriptor content(String content) {
        this.content = content;
        return this;
    }

    public TextRowDescriptor hasAction(boolean hasAction) {
        this.hasAction = hasAction;
        return this;
    }

}

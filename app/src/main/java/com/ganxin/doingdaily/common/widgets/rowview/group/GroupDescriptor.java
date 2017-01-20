package com.ganxin.doingdaily.common.widgets.rowview.group;

import android.graphics.Color;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.widgets.rowview.base.BaseRowDescriptor;

import java.util.ArrayList;

/**
 *
 * Description : GroupDescriptor  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/13 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class GroupDescriptor {
    public String headerLabel;
    public boolean isHeaderHtml;
    public int headerSize;
    public int headerColor = R.color.colorPrimary;

    public String bottomLabel;
    public ArrayList<BaseRowDescriptor> descriptors;
    public int bgColor = Color.WHITE;
    public int dividerColor = R.color.divider;


    public GroupDescriptor() {

    }

    public GroupDescriptor addDescriptor(BaseRowDescriptor descriptor) {
        if (descriptors == null) {
            descriptors = new ArrayList<>();
        }
        descriptors.add(descriptor);
        return this;
    }

    public GroupDescriptor headerLabel(String headerLabel) {
        this.headerLabel = headerLabel;
        return this;
    }

    public GroupDescriptor headerHtml(boolean isHeaderHtml) {
        this.isHeaderHtml = isHeaderHtml;
        return this;
    }

    public GroupDescriptor headerColor(int color) {
        this.headerColor = color;
        return this;
    }

    public GroupDescriptor headerSize(int size) {
        this.headerSize = size;
        return this;
    }

    public GroupDescriptor bgColor(int color) {
        this.bgColor = color;
        return this;
    }

    public GroupDescriptor bottomLabel(String bottomLabel) {
        this.bottomLabel = bottomLabel;
        return this;
    }

}

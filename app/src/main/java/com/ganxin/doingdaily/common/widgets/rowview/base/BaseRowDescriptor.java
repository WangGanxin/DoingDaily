package com.ganxin.doingdaily.common.widgets.rowview.base;

import android.view.View;

/**
 *
 * Description : BaseRowDescriptor  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/13 <br/>
 * email : mail@wangganxin.me <br/>
 */
public abstract class BaseRowDescriptor {
	public int rowId = View.NO_ID;
	
	public BaseRowDescriptor(int rowId){
		this.rowId = rowId;
	}
}

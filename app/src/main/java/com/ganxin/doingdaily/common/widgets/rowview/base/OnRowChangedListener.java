package com.ganxin.doingdaily.common.widgets.rowview.base;
/**
 *
 * Description : 监听接口  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/13 <br/>
 * email : mail@wangganxin.me <br/>
 */
public interface OnRowChangedListener {
	void onRowChanged(int rowId);

	void onRowChanged(int rowId, String content);
}

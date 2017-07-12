package com.ganxin.doingdaily.module.wechat;

import com.ganxin.doingdaily.common.data.model.WechatCategory;
import com.ganxin.doingdaily.common.data.source.callback.WechatDataSource;
import com.ganxin.doingdaily.common.data.source.WechatRepository;

import java.util.Collections;
import java.util.List;

/**
 * Description : WechatPresenter  <br/>
 * author : WangGanxin <br/>
 * date : 2016/11/4 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class WechatPresenter extends WechatContract.Presenter{

    @Override
    public void onStart() {
        getCategory();
    }

    @Override
    protected void getCategory() {
        WechatRepository.getInstance().getCategory(new WechatDataSource.GetCategoryCallback() {
            @Override
            public void onCategoryLoaded(WechatCategory wechatCategory) {
                if(wechatCategory!=null){
                    List<WechatCategory.ShowapiResBodyBean.TypeListBean> list=wechatCategory.getShowapi_res_body().getTypeList();
                    Collections.reverse(list);
                    getView().addTabs(list);
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}

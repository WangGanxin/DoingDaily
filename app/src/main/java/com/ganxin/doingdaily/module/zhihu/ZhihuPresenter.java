package com.ganxin.doingdaily.module.zhihu;

import com.ganxin.doingdaily.common.data.model.WechatCategory;
import com.ganxin.doingdaily.common.data.source.WechatDataSource;
import com.ganxin.doingdaily.common.data.source.WechatRepository;

import java.util.Collections;
import java.util.List;

/**
 * Description : ZhihuPresenter  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/10 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class ZhihuPresenter extends ZhihuContract.Presenter{

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

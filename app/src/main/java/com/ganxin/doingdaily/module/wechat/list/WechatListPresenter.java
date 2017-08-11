package com.ganxin.doingdaily.module.wechat.list;

import com.ganxin.doingdaily.common.data.model.WechatContent;
import com.ganxin.doingdaily.common.data.model.WechatContentlistBean;
import com.ganxin.doingdaily.common.data.source.callback.WechatDataSource;
import com.ganxin.doingdaily.common.data.source.WechatRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description : WechatListPresenter  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/16 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class WechatListPresenter extends WechatListContract.Presenter {
    @Override
    public void onStart() {

    }

    @Override
    protected void getListContent(String id, final int pageIndex) {
        Map<String, String> options = new HashMap<>();
        options.put("typeId", id);
        options.put("page", String.valueOf(pageIndex));
        options.put("needContent", "1");

        WechatRepository.getInstance().getCategoryContent(options, new WechatDataSource.GetCategoryContentCallback() {
            @Override
            public void onCategoryContentLoaded(WechatContent wechatContent) {
                if (wechatContent != null) {
                    List<WechatContentlistBean> list =wechatContent.getShowapi_res_body().getPagebean().getContentlist();
                    if (list != null && list.size() > 0) {
                        try {
                            if (pageIndex == 1) {
                                getView().refreshContentList(list);
                            } else {
                                getView().addContentList(list);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                getView().loadComplete();
            }

            @Override
            public void onDataNotAvailable() {
                getView().loadComplete();
            }
        });
    }
}

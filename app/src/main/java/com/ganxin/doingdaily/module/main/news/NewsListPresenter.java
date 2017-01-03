package com.ganxin.doingdaily.module.main.news;

import com.ganxin.doingdaily.common.data.model.ContentlistBean;
import com.ganxin.doingdaily.common.data.model.NewsContent;
import com.ganxin.doingdaily.common.network.NetworkManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Description : NewsPresenter  <br/>
 * author : WangGanxin <br/>
 * date : 2016/11/4 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class NewsListPresenter extends NewsListContract.Presenter{

    @Override
    public void onStart() {

    }

    @Override
    protected void getContents(String channelId, int pageIndex) {
        Map<String,String> options=new HashMap<>();
        options.put("channelId",channelId);
        options.put("page",String.valueOf(pageIndex));
        options.put("needContent","1");
        options.put("needHtml","1");
        options.put("needAllList","0");
        options.put("maxResult","20");

        NetworkManager.getAPI().getNewsContent(options).subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(new Action1<NewsContent>() {
                    @Override
                    public void call(NewsContent newsContent) {
                        if(newsContent !=null){
                            List<ContentlistBean> contentlistBeanList=newsContent.getShowapi_res_body().getPagebean().getContentlist();

                            if(contentlistBeanList!=null&&contentlistBeanList.size()>0){
                                getView().addContentlist(contentlistBeanList);
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
    }
}

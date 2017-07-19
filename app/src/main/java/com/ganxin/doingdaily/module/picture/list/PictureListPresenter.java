package com.ganxin.doingdaily.module.picture.list;

import com.ganxin.doingdaily.common.data.model.NewslistBean;
import com.ganxin.doingdaily.common.data.model.PictureBean;
import com.ganxin.doingdaily.common.data.model.PictureGankBean;
import com.ganxin.doingdaily.common.data.model.PictureGankRsp;
import com.ganxin.doingdaily.common.data.model.PictureShowBean;
import com.ganxin.doingdaily.common.data.source.CommonRepository;
import com.ganxin.doingdaily.common.data.source.callback.CommonDataSource;
import com.ganxin.doingdaily.common.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description : PictureListPresenter  <br/>
 * author : WangGanxin <br/>
 * date : 2017/7/17 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class PictureListPresenter extends PictureListContract.Presenter {

    @Override
    public void onStart() {

    }

    @Override
    protected void getGankPictures(final int pageIndex) {

        CommonRepository.getInstance().getGankPictures(pageIndex, new CommonDataSource.GankPictureCallback() {
            @Override
            public void onPicturesLoaded(PictureGankBean pictureGankBean) {
                if (pictureGankBean != null) {
                    List<PictureGankRsp> newslist = pictureGankBean.getResults();

                    if (newslist != null && newslist.size() > 0) {
                        List<PictureBean> pictureBeanList = convertToPictureBean(newslist);
                        if (pageIndex == 1) {
                            getView().refreshContentList(pictureBeanList);
                        } else {
                            getView().addContentList(pictureBeanList);
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

    @Override
    protected void getShowPictures(final int pageIndex) {
        Map<String, String> options = new HashMap<>();
        options.put("page", String.valueOf(pageIndex));
        options.put("num", "15");
        options.put("rand", "1");


        CommonRepository.getInstance().getShowPictures(options, new CommonDataSource.ShowPictureCallback() {
            @Override
            public void onPicturesLoaded(PictureShowBean pictureShowBean) {
                if (pictureShowBean != null) {
                    List<NewslistBean> newslist = pictureShowBean.getShowapi_res_body().getNewslist();

                    if (newslist != null && newslist.size() > 0) {
                        List<PictureBean> pictureBeanList = convertToPictureBean(newslist);
                        if (pageIndex == 1) {
                            getView().refreshContentList(pictureBeanList);
                        } else {
                            getView().addContentList(pictureBeanList);
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

    private List<PictureBean> convertToPictureBean(List<?> list) {
        List<PictureBean> pictureBeanList = new ArrayList<>();

        PictureBean bean;

        for (Object obj : list) {
            if (obj instanceof NewslistBean) {
                NewslistBean result = (NewslistBean) obj;

                bean = new PictureBean();
                bean.setUrl(result.getPicUrl());
                bean.setTitle(result.getTitle());

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                Date date=DateUtils.string2Date(result.getCtime(),dateFormat);
                bean.setDate(DateUtils.date2String(date,DateUtils.FORMAT_ZH_yyyyMMdd));

                pictureBeanList.add(bean);

            } else if (obj instanceof PictureGankRsp) {
                PictureGankRsp result = (PictureGankRsp) obj;

                bean = new PictureBean();
                bean.setUrl(result.getUrl());
                bean.setTitle(result.getDesc());
                bean.setDate(DateUtils.date2String(result.getPublishedAt(),DateUtils.FORMAT_ZH_yyyyMMdd));

                pictureBeanList.add(bean);
            }
        }

        return pictureBeanList;
    }
}

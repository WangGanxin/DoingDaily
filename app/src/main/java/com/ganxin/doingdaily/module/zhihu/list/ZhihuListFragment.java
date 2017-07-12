package com.ganxin.doingdaily.module.zhihu.list;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.constants.ConstantValues;
import com.ganxin.doingdaily.common.data.model.StoriesBean;
import com.ganxin.doingdaily.common.data.model.TopStoriesBean;
import com.ganxin.doingdaily.common.data.model.ZhihuBeforeNewsBean;
import com.ganxin.doingdaily.common.data.model.ZhihuLatestNewsBean;
import com.ganxin.doingdaily.common.utils.ActivityUtils;
import com.ganxin.doingdaily.common.utils.DateUtils;
import com.ganxin.doingdaily.common.widgets.pullrecycler.BaseViewHolder;
import com.ganxin.doingdaily.common.widgets.pullrecycler.PullRecycler;
import com.ganxin.doingdaily.framework.BaseListFragment;
import com.ganxin.doingdaily.module.zhihu.article.ZhihuArticleFragment;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description : 知乎列表  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/10 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class ZhihuListFragment extends BaseListFragment<ZhihuListContract.View, ZhihuListContract.Presenter, StoriesBean> implements ZhihuListContract.View {

    private List<TopStoriesBean> topStories = new ArrayList();
    private String currentDate;

    public static ZhihuListFragment newInstance() {
        ZhihuListFragment fragment = new ZhihuListFragment();
        return fragment;
    }

    @Override
    protected void setUpData() {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pullRecycler.setRefreshing();
            }
        }, 200);
    }

    @Override
    protected int getItemType(int position) {

        if (mDataList.get(position).getType() == ConstantValues.VIEW_ZHIHU_BANNER) {
            return ConstantValues.VIEW_ZHIHU_BANNER;
        } else if (mDataList.get(position).getType() == ConstantValues.VIEW_ZHIHU_DATE) {
            return ConstantValues.VIEW_ZHIHU_DATE;
        } else {
            return ConstantValues.VIEW_ZHIHU_SUMMARY;
        }
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ConstantValues.VIEW_ZHIHU_BANNER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_zhihu_banner, parent, false);
            return new ZhihuListFragment.BannerHolder(itemView);
        } else if (viewType == ConstantValues.VIEW_ZHIHU_DATE) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_zhihu_date, parent, false);
            return new ZhihuListFragment.DateHolder(itemView);
        } else if (viewType == ConstantValues.VIEW_ZHIHU_SUMMARY) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_zhihu_summary, parent, false);
            return new ZhihuListFragment.SummaryHolder(itemView);
        }
        return null;
    }

    @Override
    protected ZhihuListContract.Presenter setPresenter() {
        return new ZhihuListPresenter();
    }

    @Override
    public void onRefresh(int action) {

        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            mPresenter.getLatestNews();
        } else {
            mPresenter.getBeforeNews(currentDate);
        }
    }

    @Override
    public void refreshList(ZhihuLatestNewsBean newsBean) {

        currentDate = newsBean.getDate();

        topStories.clear();
        topStories.addAll(newsBean.getTop_stories());

        StoriesBean bannerBean = new StoriesBean();
        bannerBean.setType(ConstantValues.VIEW_ZHIHU_BANNER);

        StoriesBean dateBean = new StoriesBean();
        dateBean.setType(ConstantValues.VIEW_ZHIHU_DATE);
        dateBean.setTitle(getString(R.string.zhihu_item_today));

        mDataList.clear();
        mDataList.add(bannerBean);
        mDataList.add(dateBean);
        mDataList.addAll(newsBean.getStories());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addList(ZhihuBeforeNewsBean beforeNewsBean) {

        currentDate = beforeNewsBean.getDate();

        StoriesBean dateBean = new StoriesBean();
        dateBean.setType(ConstantValues.VIEW_ZHIHU_DATE);
        dateBean.setTitle(DateUtils.string2DateString3(beforeNewsBean.getDate(), DateUtils.FORMAT_ZH_yyyyMMdd));

        mDataList.add(dateBean);
        mDataList.addAll(beforeNewsBean.getStories());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadComplete() {
        pullRecycler.onRefreshCompleted();
    }

    class BannerHolder extends BaseViewHolder {

        @BindView(R.id.bannerView)
        Banner banner;

        public BannerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindViewHolder(int position) {

            List<String> images = new ArrayList<>();
            List<String> titles = new ArrayList<>();

            for (TopStoriesBean topStory : topStories) {
                images.add(topStory.getImage());
                titles.add(topStory.getTitle());
            }

            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
            banner.setIndicatorGravity(BannerConfig.CENTER);
            banner.setImageLoader(new GlideImageLoader());
            banner.setImages(images);
            banner.setBannerTitles(titles);
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), banner, ConstantValues.SHARE_IMAGE).toBundle();
                        ActivityUtils.startActivityByAnimation(getActivity(), ZhihuArticleFragment.newInstance(1234, ""), bundle);
                    } else {
                        ActivityUtils.startActivity(mActivity, ZhihuArticleFragment.newInstance(1234, ""));
                    }
                }
            });
            banner.start();
        }

        @Override
        public void onItemClick(View view, int position) {

        }
    }

    class DateHolder extends BaseViewHolder {

        @BindView(R.id.summary_date_tv)
        TextView mItemDate;

        public DateHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindViewHolder(int position) {
            StoriesBean bean = mDataList.get(position);
            if (bean != null) {
                mItemDate.setText(bean.getTitle());
            }
        }

        @Override
        public void onItemClick(View view, int position) {

        }
    }

    class SummaryHolder extends BaseViewHolder {

        @BindView(R.id.summary_item_title)
        TextView mItemTitle;
        @BindView(R.id.summary_item_img)
        ImageView mItemImg;

        public SummaryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindViewHolder(int position) {
            StoriesBean bean = mDataList.get(position);
            if (bean != null) {
                mItemTitle.setText(bean.getTitle());

                if (bean.getImages().get(0).endsWith(".gif")) {
                    Glide.with(getContext())
                            .load(bean.getImages().get(0))
                            .asGif()
                            .placeholder(R.drawable.placeholder_img_loading)
                            .dontAnimate()
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(mItemImg);
                } else {
                }
                Glide.with(getContext())
                        .load(bean.getImages().get(0))
                        .placeholder(R.drawable.placeholder_img_loading)
                        .crossFade()
                        .centerCrop()
                        .into(mItemImg);
            }
        }

        @Override
        public void onItemClick(View view, int position) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), mItemImg, ConstantValues.SHARE_IMAGE).toBundle();
                ActivityUtils.startActivityByAnimation(getActivity(), ZhihuArticleFragment.newInstance(1234, ""), bundle);
            } else {
                ActivityUtils.startActivity(mActivity, ZhihuArticleFragment.newInstance(1234, ""));
            }
        }
    }
}

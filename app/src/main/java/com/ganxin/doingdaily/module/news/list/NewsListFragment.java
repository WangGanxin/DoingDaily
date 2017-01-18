package com.ganxin.doingdaily.module.news.list;

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
import com.ganxin.doingdaily.common.data.model.NewsContentlistBean;
import com.ganxin.doingdaily.common.utils.ActivityUtils;
import com.ganxin.doingdaily.common.utils.DateUtils;
import com.ganxin.doingdaily.common.widgets.pullrecycler.BaseViewHolder;
import com.ganxin.doingdaily.common.widgets.pullrecycler.PullRecycler;
import com.ganxin.doingdaily.framework.BaseListFragment;
import com.ganxin.doingdaily.module.news.article.NewsArticleFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description : 新闻列表界面  <br/>
 * author : WangGanxin <br/>
 * date : 2016/11/3 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class NewsListFragment extends BaseListFragment<NewsListContract.View, NewsListContract.Presenter,NewsContentlistBean> implements NewsListContract.View {

    private int pageIndex = 1;
    private String channelId;

    public static NewsListFragment newInstance(String channelId) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ConstantValues.KEY_CHANNEL_ID, channelId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected NewsListContract.Presenter setPresenter() {
        return new NewsListPresenter();
    }

    @Override
    protected void setUpData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            channelId = bundle.getString(ConstantValues.KEY_CHANNEL_ID);
            pullRecycler.setRefreshing();
        }
    }

    @Override
    protected int getItemType(int position) {
        if (mDataList.get(position).isHavePic()) {
            return ConstantValues.VIEW_TYPE_IMAGE;
        } else {
            return ConstantValues.VIEW_TYPE_TXT;
        }
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ConstantValues.VIEW_TYPE_IMAGE) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_news_image, parent, false);
            return new ImageViewHolder(itemView);
        } else if (viewType == ConstantValues.VIEW_TYPE_TXT) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_news_txt, parent, false);
            return new TxtViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onRefresh(int action) {

        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }
        mPresenter.getListContent(channelId, pageIndex);
    }

    @Override
    public void refreshContentList(List<NewsContentlistBean> contentlistBeanList) {
        mDataList.clear();
        mDataList.addAll(contentlistBeanList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addContentList(List<NewsContentlistBean> contentlistBeanList) {
        mDataList.addAll(contentlistBeanList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadComplete() {
        pullRecycler.onRefreshCompleted();
    }

    class TxtViewHolder extends BaseViewHolder {

        @BindView(R.id.news_item_title)
        TextView newsItemTitle;
        @BindView(R.id.news_item_content)
        TextView newsItemContent;
        @BindView(R.id.news_item_source)
        TextView newsItemSource;
        @BindView(R.id.news_item_time)
        TextView newsItemTime;

        public TxtViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindViewHolder(int position) {
            NewsContentlistBean bean = mDataList.get(position);
            if (bean != null) {
                newsItemTitle.setText(bean.getTitle());
                newsItemContent.setText(bean.getDesc());
                newsItemSource.setText(getString(R.string.news_item_source, bean.getSource()));
                newsItemTime.setText(DateUtils.getOnTime(bean.getPubDate()));
            }
        }

        @Override
        public void onItemClick(View view, int position) {
            NewsContentlistBean bean = mDataList.get(position);
            ActivityUtils.startActivity(mActivity,NewsArticleFragment.newInstance(ConstantValues.VIEW_TYPE_TXT,bean));
        }
    }

    class ImageViewHolder extends BaseViewHolder {

        @BindView(R.id.news_item_title)
        TextView newsItemTitle;
        @BindView(R.id.news_item_source)
        TextView newsItemSource;
        @BindView(R.id.news_item_time)
        TextView newsItemTime;
        @BindView(R.id.news_item_img)
        ImageView newsItemImg;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindViewHolder(int position) {
            NewsContentlistBean bean = mDataList.get(position);
            if (bean != null) {
                newsItemTitle.setText(bean.getTitle());
                newsItemSource.setText(getString(R.string.news_item_source, bean.getSource()));
                newsItemTime.setText(DateUtils.getOnTime(bean.getPubDate()));

                if(bean.getImageurls().get(0).getUrl().endsWith(".gif")){
                    Glide.with(getContext())
                            .load(bean.getImageurls().get(0).getUrl())
                            .asGif()
                            .placeholder(R.drawable.placeholder_img_loading)
                            .dontAnimate()
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(newsItemImg);
                }
                else{
                    Glide.with(getContext())
                            .load(bean.getImageurls().get(0).getUrl())
                            .placeholder(R.drawable.placeholder_img_loading)
                            .crossFade()
                            .centerCrop()
                            .into(newsItemImg);
                }
            }
        }

        @Override
        public void onItemClick(View view, int position) {
            NewsContentlistBean bean = mDataList.get(position);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Bundle bundle=ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), newsItemImg,ConstantValues.SHARE_IMAGE).toBundle();
                ActivityUtils.startActivityByAnimation(getActivity(),NewsArticleFragment.newInstance(ConstantValues.VIEW_TYPE_IMAGE,bean),bundle);
            }
            else{
                ActivityUtils.startActivity(mActivity,NewsArticleFragment.newInstance(ConstantValues.VIEW_TYPE_IMAGE,bean));
            }
        }
    }
}

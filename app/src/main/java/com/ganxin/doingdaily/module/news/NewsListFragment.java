package com.ganxin.doingdaily.module.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.data.model.ContentlistBean;
import com.ganxin.doingdaily.common.utils.DateUtils;
import com.ganxin.doingdaily.common.widgets.pullrecycler.BaseViewHolder;
import com.ganxin.doingdaily.common.widgets.pullrecycler.PullRecycler;
import com.ganxin.doingdaily.framework.BaseListFragment;

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
public class NewsListFragment extends BaseListFragment<ContentlistBean, NewsListContract.Presenter> implements NewsListContract.View {

    private static final String CHANNEL_ID = "channelId";

    private static final int VIEW_TYPE_TXT = 0;
    private static final int VIEW_TYPE_IMAGE = 1;

    private int pageIndex = 1;
    private String channelId;

    public static NewsListFragment newInstance(String channelId) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CHANNEL_ID, channelId);
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
            channelId = bundle.getString(CHANNEL_ID);
            pullRecycler.setRefreshing();
        }
    }

    @Override
    protected int getItemType(int position) {
        if (mDataList.get(position).isHavePic()) {
            return VIEW_TYPE_IMAGE;
        } else {
            return VIEW_TYPE_TXT;
        }
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_IMAGE) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_news_image, parent, false);
            return new ImageViewHolder(itemView);
        } else if (viewType == VIEW_TYPE_TXT) {
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
        mPresenter.getContents(channelId, pageIndex);
    }

    @Override
    public void addContentlist(List<ContentlistBean> contentlistBeanList) {
        mDataList.addAll(contentlistBeanList);
        adapter.notifyDataSetChanged();
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
            ContentlistBean bean = mDataList.get(position);
            if (bean != null) {
                newsItemTitle.setText(bean.getTitle());
                newsItemContent.setText(bean.getDesc());
                newsItemSource.setText(getString(R.string.news_item_source, bean.getSource()));
                newsItemTime.setText(DateUtils.getOnTime(bean.getPubDate()));
            }
        }

        @Override
        public void onItemClick(View view, int position) {

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
            ContentlistBean bean = mDataList.get(position);
            if (bean != null) {
                newsItemTitle.setText(bean.getTitle());
                newsItemSource.setText(getString(R.string.news_item_source, bean.getSource()));
                newsItemTime.setText(DateUtils.getOnTime(bean.getPubDate()));

                Glide.with(getContext())
                        .load(bean.getImageurls().get(0).getUrl())
                        .placeholder(R.drawable.placeholder_img_loading)
                        .crossFade()
                        .centerCrop()
                        .into(newsItemImg);
            }
        }

        @Override
        public void onItemClick(View view, int position) {

        }
    }
}

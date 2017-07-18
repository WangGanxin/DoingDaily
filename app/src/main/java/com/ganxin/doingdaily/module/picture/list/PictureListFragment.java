package com.ganxin.doingdaily.module.picture.list;

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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description : 图片列表界面  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/17 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class PictureListFragment extends BaseListFragment<PictureListContract.View, PictureListContract.Presenter, NewsContentlistBean> implements PictureListContract.View {

    private int pageIndex = 1;
    private int tab;

    /**
     * @param type tab类型
     * @return
     */
    public static PictureListFragment newInstance(int type) {
        PictureListFragment fragment = new PictureListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantValues.KEY_PICTURE_TAB, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected PictureListContract.Presenter setPresenter() {
        return new PictureListPresenter();
    }

    @Override
    protected void setUpData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            tab = bundle.getInt(ConstantValues.KEY_PICTURE_TAB);
            pullRecycler.setRefreshing();
        }
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_picture, parent, false);
        return new ImageViewHolder(itemView);
    }

    @Override
    public void onRefresh(int action) {

        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }
        //  mPresenter.getListContent(tab, pageIndex);
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

                if (bean.getImageurls().get(0).getUrl().endsWith(".gif")) {
                    Glide.with(getContext())
                            .load(bean.getImageurls().get(0).getUrl())
                            .asGif()
                            .placeholder(R.drawable.placeholder_img_loading)
                            .dontAnimate()
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(newsItemImg);
                } else {
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
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), newsItemImg, ConstantValues.SHARE_IMAGE).toBundle();
                ActivityUtils.startActivityByAnimation(getActivity(), NewsArticleFragment.newInstance(ConstantValues.VIEW_TYPE_IMAGE, bean), bundle);
            } else {
                ActivityUtils.startActivity(mActivity, NewsArticleFragment.newInstance(ConstantValues.VIEW_TYPE_IMAGE, bean));
            }
        }
    }
}

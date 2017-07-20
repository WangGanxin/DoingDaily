package com.ganxin.doingdaily.module.picture.list;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.constants.ConstantValues;
import com.ganxin.doingdaily.common.data.model.PictureBean;
import com.ganxin.doingdaily.common.utils.ActivityUtils;
import com.ganxin.doingdaily.common.utils.GlideUtils;
import com.ganxin.doingdaily.common.widgets.RatioImageView;
import com.ganxin.doingdaily.common.widgets.pullrecycler.BaseViewHolder;
import com.ganxin.doingdaily.common.widgets.pullrecycler.ItemDecoration.StaggeredDividerItemDecoration;
import com.ganxin.doingdaily.common.widgets.pullrecycler.PullRecycler;
import com.ganxin.doingdaily.common.widgets.pullrecycler.layoutmanager.ILayoutManager;
import com.ganxin.doingdaily.common.widgets.pullrecycler.layoutmanager.MyStaggeredGridLayoutManager;
import com.ganxin.doingdaily.framework.BaseListFragment;
import com.ganxin.doingdaily.module.picture.browser.PictureBrowserFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description : 图片列表界面  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/17 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class PictureListFragment extends BaseListFragment<PictureListContract.View, PictureListContract.Presenter, PictureBean> implements PictureListContract.View {

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
    protected ILayoutManager getLayoutManager() {
        MyStaggeredGridLayoutManager layoutManager = new MyStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE); //防止item 交换位置
        return layoutManager;
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        int vertical = getResources().getDimensionPixelOffset(R.dimen.staggered_vertical_spacing);
        int horizontal = getResources().getDimensionPixelOffset(R.dimen.staggered_horizontal_spacing);
        return new StaggeredDividerItemDecoration(vertical, horizontal);
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

        switch (tab) {
            case ConstantValues.PICTURE_TAB_GANK:
                mPresenter.getGankPictures(pageIndex);
                break;
            case ConstantValues.PICTURE_TAB_SHOW:
                mPresenter.getShowPictures(pageIndex);
                break;
        }
    }

    @Override
    public void refreshContentList(List<PictureBean> pictureBeanList) {
        mDataList.clear();
        mDataList.addAll(pictureBeanList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addContentList(List<PictureBean> pictureBeanList) {
        int poistionStart = mDataList.size() - 1;
        mDataList.addAll(pictureBeanList);
        //adapter.notifyDataSetChanged();
        adapter.notifyItemRangeChanged(poistionStart, pictureBeanList.size());
    }

    @Override
    public void loadComplete() {
        pullRecycler.onRefreshCompleted();
    }

    class ImageViewHolder extends BaseViewHolder {

        @BindView(R.id.picture_desc_tv)
        TextView descTv;
        @BindView(R.id.picture_iv)
        RatioImageView ratioImageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindViewHolder(int position) {
            PictureBean bean = mDataList.get(position);

            if (position % 2 == 0) {
                ratioImageView.setImageRatio(0.7f);
            } else {
                ratioImageView.setImageRatio(0.6f);
            }

            if (bean != null) {
                GlideUtils.display(ratioImageView, bean.getUrl());
                descTv.setText(bean.getDate());
            }
        }

        @Override
        public void onItemClick(View view, int position) {

            PictureBean bean = mDataList.get(position);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), ratioImageView, ConstantValues.SHARE_IMAGE).toBundle();
                ActivityUtils.startActivityByAnimation(mActivity, PictureBrowserFragment.newInstance(bean), bundle);
            } else {
                ActivityUtils.startActivity(mActivity, PictureBrowserFragment.newInstance(bean));
            }
        }
    }
}

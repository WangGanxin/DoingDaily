package com.ganxin.doingdaily.module.wechat.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.constants.ConstantValues;
import com.ganxin.doingdaily.common.data.model.WechatContentlistBean;
import com.ganxin.doingdaily.common.utils.ActivityUtils;
import com.ganxin.doingdaily.common.utils.DateUtils;
import com.ganxin.doingdaily.common.widgets.pullrecycler.BaseViewHolder;
import com.ganxin.doingdaily.common.widgets.pullrecycler.PullRecycler;
import com.ganxin.doingdaily.framework.BaseListFragment;
import com.ganxin.doingdaily.module.wechat.article.WechatArticleFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description : 微信列表页面  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/16 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class WechatListFragment extends BaseListFragment<WechatListContract.View, WechatListContract.Presenter, WechatContentlistBean> implements WechatListContract.View {

    private int pageIndex = 1;
    private String id;

    public static WechatListFragment newInstance(String categoryId) {
        WechatListFragment fragment = new WechatListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ConstantValues.KEY_CATEGORY_ID, categoryId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected WechatListContract.Presenter setPresenter() {
        return new WechatListPresenter();
    }

    @Override
    protected void setUpData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString(ConstantValues.KEY_CATEGORY_ID);
            pullRecycler.setRefreshing();
        }
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_wechat, parent, false);
        return new WeixinViewHolder(itemView);
    }

    @Override
    public void onRefresh(int action) {

        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }
        mPresenter.getListContent(id, pageIndex);
    }

    @Override
    public void refreshContentList(List<WechatContentlistBean> contentlist) {
        mDataList.clear();
        mDataList.addAll(contentlist);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addContentList(List<WechatContentlistBean> contentlist) {
        mDataList.addAll(contentlist);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadComplete() {
        pullRecycler.onRefreshCompleted();
    }

    class WeixinViewHolder extends BaseViewHolder {

        @BindView(R.id.wechat_item_source)
        TextView wechatItemSource;
        @BindView(R.id.wechat_item_title)
        TextView wechatItemTitle;
        @BindView(R.id.wechat_item_readNum)
        TextView wechatItemReadNum;
        @BindView(R.id.wechat_item_likeNum)
        TextView wechatItemLikeNum;
        @BindView(R.id.wechat_item_time)
        TextView wechatItemTime;

        public WeixinViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindViewHolder(int position) {
            WechatContentlistBean bean = mDataList.get(position);
            if (bean != null) {
                wechatItemSource.setText(getString(R.string.news_item_source, bean.getUserName()));
                wechatItemTitle.setText(bean.getTitle());
                wechatItemReadNum.setText(getString(R.string.wechat_item_readNum, bean.getRead_num()));
                wechatItemLikeNum.setText(getString(R.string.wechat_item_likeNum, bean.getLike_num()));
                wechatItemTime.setText(DateUtils.getOnTime(bean.getDate()));
            }
        }

        @Override
        public void onItemClick(View view, int position) {
            WechatContentlistBean bean = mDataList.get(position);
            ActivityUtils.startActivity(mActivity, WechatArticleFragment.newInstance(bean));
        }
    }
}

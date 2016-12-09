package com.ganxin.doingdaily.module.main.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.data.model.News;
import com.ganxin.doingdaily.common.network.NetworkManager;
import com.ganxin.doingdaily.common.utils.LogUtil;
import com.ganxin.doingdaily.common.widgets.pullrecycler.BaseViewHolder;
import com.ganxin.doingdaily.common.widgets.pullrecycler.PullRecycler;
import com.ganxin.doingdaily.framework.BaseFragment;
import com.ganxin.doingdaily.framework.BaseListFragment;
import com.ganxin.doingdaily.framework.ITabFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Description : 新闻界面  <br/>
 * author : WangGanxin <br/>
 * date : 2016/11/3 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class NewsFragment extends BaseListFragment<News.ShowapiResBodyBean.ChannelListBean> implements ITabFragment {

    private int page = 1;

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_news_list_item, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    protected NewsContract.Presenter setPresenter() {
        return new NewsPresenter();
    }

    @Override
    public void onRefresh(final int action) {

        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            page = 1;
        }

        NetworkManager.getAPI().getNews().subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        LogUtil.i("222--total----" + news.getShowapi_res_body().getTotalNum());
                        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
                            mDataList.clear();
                        }
                        if (news.getShowapi_res_body().getTotalNum()  == 0) {
                            pullRecycler.enableLoadMore(false);
                        } else {
                            pullRecycler.enableLoadMore(true);
                            mDataList.addAll(news.getShowapi_res_body().getChannelList());
                            adapter.notifyDataSetChanged();
                        }
                        pullRecycler.onRefreshCompleted();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.i("----on error--" + throwable.getMessage());
                        pullRecycler.onRefreshCompleted();
                    }
                });

    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }


    class NewsViewHolder extends BaseViewHolder {

        @BindView(R.id.title)
        TextView title;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindViewHolder(int position) {

        }

        @Override
        public void onItemClick(View view, int position) {

        }
    }
}

package com.ganxin.doingdaily.module.video;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.data.model.VideoBean;
import com.ganxin.doingdaily.common.share.ShareController;
import com.ganxin.doingdaily.common.utils.GlideUtils;
import com.ganxin.doingdaily.common.widgets.pullrecycler.BaseViewHolder;
import com.ganxin.doingdaily.common.widgets.pullrecycler.PullRecycler;
import com.ganxin.doingdaily.framework.BaseListFragment;
import com.ganxin.doingdaily.framework.ITabFragment;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description : 视频列表页面  <br/>
 * author : WangGanxin <br/>
 * date : 2017/7/21 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class VideoListFragment extends BaseListFragment<VideoListContract.View, VideoListContract.Presenter, VideoBean> implements VideoListContract.View, ITabFragment {

    private int pageIndex = 1;
    private boolean mFull = false;

    @Override
    protected VideoListContract.Presenter setPresenter() {
        return new VideoListPresenter();
    }

    @Override
    protected void setUpData() {

        pullRecycler.setRefreshing();
        pullRecycler.setOnScrollListener(new PullRecycler.OnRecyclerScrollListener() {

            private int firstVisibleItem, lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                firstVisibleItem = getLayoutManager().findFirstVisiblePosition();
                lastVisibleItem = getLayoutManager().findLastVisiblePosition();

                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(VideoViewHolder.TAG)
                            && (position < firstVisibleItem || position > lastVisibleItem)) {

                        //如果滑出去了上面和下面就是否，和今日头条一样
                        //是否全屏
                        if (!mFull) {
                            GSYVideoPlayer.releaseAllVideos();
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (newConfig.orientation != ActivityInfo.SCREEN_ORIENTATION_USER) {
            mFull = false;
        } else {
            mFull = true;
        }
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_video, parent, false);
        VideoViewHolder videoViewHolder = new VideoViewHolder(getContext(), itemView);
        return videoViewHolder;
    }

    @Override
    public void onRefresh(int action) {

        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }
        mPresenter.getVideoList(pageIndex);
    }

    @Override
    public void refreshContentList(List<VideoBean> contentlist) {
        mDataList.clear();
        mDataList.addAll(contentlist);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addContentList(List<VideoBean> contentlist) {
        mDataList.addAll(contentlist);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadComplete() {
        pullRecycler.onRefreshCompleted();
    }

    @Override
    protected void onBack() {
        if (StandardGSYVideoPlayer.backFromWindowFull(getContext())) {
            return;
        }
        super.onBack();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    class VideoViewHolder extends BaseViewHolder {

        public static final String TAG = "VideoViewHolder";

        @BindView(R.id.video_item_title)
        TextView videoTitle;
        @BindView(R.id.video_item_hot)
        TextView videoHot;
        @BindView(R.id.video_item_name)
        TextView videoUserName;
        @BindView(R.id.video_item_share)
        ImageView videoShareIv;
        @BindView(R.id.video_item_player)
        StandardGSYVideoPlayer videoPlayer;

        private Context mContext;
        private ImageView imageView;

        public VideoViewHolder(Context context, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = context;
            imageView = new ImageView(context);
        }

        @Override
        public void onBindViewHolder(final int position) {
            final VideoBean bean = mDataList.get(position);
            if (bean != null) {
                videoTitle.setText(Html.fromHtml(bean.getText()));
                videoHot.setText(getResources().getString(R.string.video_hot_num, bean.getLove()));
                videoUserName.setText(getResources().getString(R.string.video_user, bean.getName()));
                videoShareIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShareController.getInstance().shareLink(mActivity, bean.getWeixin_url(), bean.getText(), bean.getProfile_image(), new UhareListener());
                    }
                });

                //设置封面
                GlideUtils.display(imageView, bean.getProfile_image());

                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                if (imageView.getParent() != null) {
                    ViewGroup viewGroup = (ViewGroup) imageView.getParent();
                    viewGroup.removeView(imageView);
                }

                videoPlayer.setIsTouchWiget(false);
                videoPlayer.setThumbImageView(imageView);

                //默认缓存路径
                videoPlayer.setUp(bean.getVideo_uri(), true, null, bean.getText());

                //增加title
                videoPlayer.getTitleTextView().setVisibility(View.GONE);

                //设置返回键
                videoPlayer.getBackButton().setVisibility(View.GONE);

                videoPlayer.setRotateViewAuto(true);
                videoPlayer.setLockLand(true);
                videoPlayer.setPlayTag(TAG);
                videoPlayer.setShowFullAnimation(true);
                //循环
                //gsyVideoPlayer.setLooping(true);
                videoPlayer.setNeedLockFull(true);

                //gsyVideoPlayer.setSpeed(2);

                videoPlayer.setPlayPosition(position);
                //设置全屏按键功能
                videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resolveFullBtn(videoPlayer);
                    }
                });

                videoPlayer.setStandardVideoAllCallBack(new SampleListener() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        if (!videoPlayer.isIfCurrentIsFullscreen()) {
                            //静音
                            GSYVideoManager.instance().setNeedMute(true);
                        }
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        //全屏不静音
                        GSYVideoManager.instance().setNeedMute(true);
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);
                    }
                });
            }
        }

        @Override
        public void onItemClick(View view, int position) {

        }

        /**
         * 全屏幕按键处理
         */
        private void resolveFullBtn(final StandardGSYVideoPlayer standardGSYVideoPlayer) {
            standardGSYVideoPlayer.startWindowFullscreen(mContext, true, true);
        }

        /**
         * 分享回调
         */
        private class UhareListener implements UMShareListener {

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                Toast.makeText(mContext, getString(R.string.tips_share_success), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {

            }
        }
    }
}

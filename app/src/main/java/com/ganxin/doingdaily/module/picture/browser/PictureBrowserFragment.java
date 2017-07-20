package com.ganxin.doingdaily.module.picture.browser;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.constants.ConstantValues;
import com.ganxin.doingdaily.common.data.model.PictureBean;
import com.ganxin.doingdaily.common.share.ShareController;
import com.ganxin.doingdaily.common.utils.GlideUtils;
import com.ganxin.doingdaily.common.utils.SnackbarUtil;
import com.ganxin.doingdaily.common.utils.StorageManager;
import com.ganxin.doingdaily.common.utils.SystemHelper;
import com.ganxin.doingdaily.framework.BaseFragment;
import com.ganxin.library.SwipeLoadDataLayout;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;

import butterknife.BindView;

/**
 * Description : 图片浏览界面  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/17 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class PictureBrowserFragment extends BaseFragment<PictureBrowserContract.View, PictureBrowserContract.Presenter> implements PictureBrowserContract.View, UMShareListener {

    @BindView(R.id.photoview)
    PhotoView photoView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.contentLayout)
    LinearLayout contentLayout;
    @BindView(R.id.swipeLoadDataLayout)
    SwipeLoadDataLayout swipeLoadDataLayout;

    private PhotoViewAttacher attacher;
    protected boolean isToolBarHiding = false;

    private PictureBean currentPicture;

    public static PictureBrowserFragment newInstance(PictureBean bean) {
        PictureBrowserFragment fragment = new PictureBrowserFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ConstantValues.KEY_PICTURE_BEAN, bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_picture_browser;
    }

    @Override
    public void setUpView(View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            currentPicture = (PictureBean) bundle.getSerializable(ConstantValues.KEY_PICTURE_BEAN);
        }

        AppCompatActivity mAppCompatActivity = (AppCompatActivity) getActivity();
        mAppCompatActivity.setSupportActionBar(toolbar);

        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setHasOptionsMenu(true); //处理 onOptionsItemSelected方法不被调用
        attacher = new PhotoViewAttacher(photoView);
        attacher.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                toogleToolBar();
            }
        });

        if (currentPicture != null) {
            swipeLoadDataLayout.setStatus(SwipeLoadDataLayout.LOADING);
            swipeLoadDataLayout.setOnReloadListener(new SwipeLoadDataLayout.OnReloadListener() {
                @Override
                public void onReload(View v, int status) {
                    if (status != SwipeLoadDataLayout.LOADING) {
                        swipeLoadDataLayout.setStatus(SwipeLoadDataLayout.SUCCESS);
                    }
                }
            });
            swipeLoadDataLayout.setColorSchemeResources(
                    R.color.colorPrimary,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);

            actionBar.setTitle(currentPicture.getDate());
            GlideUtils.display(photoView, currentPicture.getUrl(), new RequestListener() {
                @Override
                public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                    swipeLoadDataLayout.setStatus(SwipeLoadDataLayout.SUCCESS);
                    swipeLoadDataLayout.setEnabled(false);
                    ViewCompat.setTransitionName(photoView, ConstantValues.SHARE_IMAGE);
                    return false;
                }
            });
        }
    }

    @Override
    protected PictureBrowserContract.Presenter setPresenter() {
        return new PictureBrowserPresenter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBack();
                return false;
            case R.id.action_share:
                share();
                break;
            case R.id.action_save:
                downlaod();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void share() {
        if (currentPicture != null) {
            ShareController.getInstance().shareLink(mActivity, currentPicture.getUrl(), currentPicture.getDate(), currentPicture.getUrl(), this);
        }
    }

    private void downlaod() {

        if (currentPicture != null) {
            String localPath = StorageManager.getInstance().getImageDir().getAbsolutePath();

            String url = currentPicture.getUrl();
            String fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
            FileDownloader.setup(mActivity);
            FileDownloader.getImpl().create(url)
                    .setPath(localPath + File.separator + fileName)
                    .setListener(new FileDownloadListener() {
                        @Override
                        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            swipeLoadDataLayout.setStatus(SwipeLoadDataLayout.LOADING);
                            SnackbarUtil.shortSnackbar(contentLayout, getString(R.string.tips_save_pending), SnackbarUtil.Info);
                        }

                        @Override
                        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                        }

                        @Override
                        protected void completed(BaseDownloadTask task) {
                            swipeLoadDataLayout.setStatus(SwipeLoadDataLayout.SUCCESS);
                            swipeLoadDataLayout.setEnabled(false);
                            SystemHelper.UpdateMedia(mActivity, Uri.fromFile(new File(task.getPath())));
                            SnackbarUtil.longSnackbar(contentLayout, getString(R.string.tips_save_success), SnackbarUtil.Info);
                        }

                        @Override
                        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                        }

                        @Override
                        protected void error(BaseDownloadTask task, Throwable e) {

                        }

                        @Override
                        protected void warn(BaseDownloadTask task) {

                        }
                    }).start();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_picture_browser, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ShareController.getInstance().release(mActivity);
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        SnackbarUtil.shortSnackbar(contentLayout, getString(R.string.tips_share_success), SnackbarUtil.Info);
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }

    @Override
    protected void onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBack();
        }
    }

    private void toogleToolBar() {
        appBarLayout.animate()
                .translationY(isToolBarHiding ? 0 : -appBarLayout.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        isToolBarHiding = !isToolBarHiding;
    }
}

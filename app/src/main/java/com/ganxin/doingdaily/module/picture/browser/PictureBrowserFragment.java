package com.ganxin.doingdaily.module.picture.browser;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.constants.ConstantValues;
import com.ganxin.doingdaily.common.data.model.PictureBean;
import com.ganxin.doingdaily.common.share.ShareController;
import com.ganxin.doingdaily.common.utils.GlideUtils;
import com.ganxin.doingdaily.common.utils.SnackbarUtil;
import com.ganxin.doingdaily.framework.BaseFragment;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;

/**
 * Description : 图片浏览界面  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/17 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class PictureBrowserFragment extends BaseFragment<PictureBrowserContract.View, PictureBrowserContract.Presenter> implements PictureBrowserContract.View, UMShareListener {

    @BindView(R.id.picture_big_iv)
    ImageView pictureBigIv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.contentLayout)
    LinearLayout contentLayout;

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

        if (currentPicture != null) {
            actionBar.setTitle(currentPicture.getDate());
            GlideUtils.display(pictureBigIv, currentPicture.getUrl());
            ViewCompat.setTransitionName(pictureBigIv, ConstantValues.SHARE_IMAGE);
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
                //share();
                break;
            case R.id.action_browser:
                //SystemHelper.SystemBrowser(getActivity(),bean.getLink());
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_news_article, menu);
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


}

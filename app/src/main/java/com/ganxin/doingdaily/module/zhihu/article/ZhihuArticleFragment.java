package com.ganxin.doingdaily.module.zhihu.article;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebBackForwardList;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.constants.ConstantValues;
import com.ganxin.doingdaily.common.data.model.ZhihuArticleBean;
import com.ganxin.doingdaily.common.share.ShareController;
import com.ganxin.doingdaily.common.utils.GlideUtils;
import com.ganxin.doingdaily.common.utils.SnackbarUtil;
import com.ganxin.doingdaily.common.utils.SystemHelper;
import com.ganxin.doingdaily.framework.BaseFragment;
import com.ganxin.library.SwipeLoadDataLayout;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import butterknife.BindView;

/**
 * Description : 知乎详情页面  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/11 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class ZhihuArticleFragment extends BaseFragment<ZhihuArticleContract.View, ZhihuArticleContract.Presenter> implements ZhihuArticleContract.View, UMShareListener {

    @BindView(R.id.articalLayout)
    CoordinatorLayout articalLayout;
    @BindView(R.id.headImage)
    ImageView headImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.swipeLoadDataLayout)
    SwipeLoadDataLayout swipeLoadDataLayout;

    private ZhihuArticleBean articleBean;
    private String articleId;

    /**
     * @param articleTitle 文章标题
     * @param articleId    文章Id
     * @param imageUrl     图片地址
     * @return
     */
    public static ZhihuArticleFragment newInstance(String articleTitle, int articleId, String imageUrl) {
        ZhihuArticleFragment fragment = new ZhihuArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ConstantValues.KEY_ZHIHU_ARTICLE_TITLE, articleTitle);
        bundle.putInt(ConstantValues.KEY_ZHIHU_ARTICLE_ID, articleId);
        bundle.putString(ConstantValues.KEY_ZHIHU_ARTICLE_IMAGE, imageUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_zhihu_article;
    }

    @Override
    public void setUpView(View view) {

        AppCompatActivity mAppCompatActivity = (AppCompatActivity) getActivity();
        mAppCompatActivity.setSupportActionBar(toolbar);

        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setHasOptionsMenu(true); //处理 onOptionsItemSelected方法不被调用


        final String articleTitle = getArguments().getString(ConstantValues.KEY_ZHIHU_ARTICLE_TITLE);
        String imageUrl = getArguments().getString(ConstantValues.KEY_ZHIHU_ARTICLE_IMAGE);
        this.articleId = String.valueOf(getArguments().getInt(ConstantValues.KEY_ZHIHU_ARTICLE_ID));

        if (!TextUtils.isEmpty(articleTitle)) {
            collapsingToolbarLayout.setTitle(articleTitle);
        }

        if (!TextUtils.isEmpty(imageUrl)) {
            GlideUtils.display(headImage,imageUrl,R.drawable.placeholder_img_loading2);
        }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setDomStorageEnabled(true);// 开启DOM storage API 功能
        webView.getSettings().setDatabaseEnabled(true);// 开启database storage API功能
        webView.getSettings().setAppCacheEnabled(true); // 开启Application Cache功能
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        swipeLoadDataLayout.setColorSchemeResources(
                R.color.colorPrimary,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeLoadDataLayout.setOnReloadListener(new SwipeLoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                if (status != SwipeLoadDataLayout.LOADING) {
                    mPresenter.getArticle(getArticleId());
                }
            }
        });
    }

    @Override
    protected ZhihuArticleContract.Presenter setPresenter() {
        return new ZhihuArticlePresenter();
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
            case R.id.action_browser:
                SystemHelper.SystemBrowser(getActivity(), articleBean.getShare_url());
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void share() {
        if (articleBean != null) {
            if (!TextUtils.isEmpty(articleBean.getImage())) {
                ShareController.getInstance().shareLink(mActivity, articleBean.getShare_url(), articleBean.getTitle(), articleBean.getImage(), this);
            } else {
                ShareController.getInstance().shareLink(mActivity, articleBean.getShare_url(), articleBean.getTitle(), this);
            }
        }
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
        SnackbarUtil.shortSnackbar(articalLayout, getString(R.string.tips_share_success), SnackbarUtil.Info);
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }


    @Override
    public String getArticleId() {
        return this.articleId;
    }

    @Override
    public void setArticle(ZhihuArticleBean articleBean) {
        this.articleBean = articleBean;
        loadLocalHtml(articleBean.getBody());
    }

    @Override
    public void loading() {
        webView.setVisibility(View.GONE);
        swipeLoadDataLayout.setVisibility(View.VISIBLE);
        swipeLoadDataLayout.setStatus(SwipeLoadDataLayout.LOADING);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getArticle(getArticleId());
            }
        }, 300);
    }

    @Override
    public void loadComplete() {
        swipeLoadDataLayout.setStatus(SwipeLoadDataLayout.SUCCESS);
        swipeLoadDataLayout.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadError() {
        swipeLoadDataLayout.setVisibility(View.VISIBLE);
        swipeLoadDataLayout.setStatus(SwipeLoadDataLayout.ERROR);
        webView.setVisibility(View.GONE);
    }

    @Override
    protected void onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backAction();
        }
    }

    private void loadLocalHtml(String bodyHtml) {
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/zhihu.css\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + bodyHtml + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
        webView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }

    @Override
    public void backAction() {
        String curUrl = webView.getUrl();
        boolean isExit = true;
        if (!TextUtils.isEmpty(curUrl)) {
            if (webView.canGoBack()) {
                WebBackForwardList backList = webView.copyBackForwardList();

                if (backList != null && backList.getCurrentIndex() == 1) {
                    isExit = false;
                    loadLocalHtml(articleBean.getBody());
                }
            }
        }

        if (isExit) {
            mActivity.finish();
        }
    }
}

package com.ganxin.doingdaily.common.share;

import android.app.Activity;
import android.text.TextUtils;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.application.DoingDailyApp;
import com.ganxin.doingdaily.common.constants.ConstantValues;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.ShareBoardConfig;

/**
 * Description : 分享面板管理者  <br/>
 * author : WangGanxin <br/>
 * date : 2017/2/16 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class ShareController {

    private ShareBoardConfig shareBoardConfig;

    private static class ShareHolder {
        static final ShareController INSTANCE = new ShareController();
    }

    private ShareController() {
        if (shareBoardConfig == null) {
            shareBoardConfig = new ShareBoardConfig();
        }
        shareBoardConfig.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
        shareBoardConfig.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
        shareBoardConfig.setTitleTextColor(DoingDailyApp.getAppContext().getResources().getColor(R.color.colorPrimary));
        shareBoardConfig.setShareboardBackgroundColor(DoingDailyApp.getAppContext().getResources().getColor(R.color.background_page));
        shareBoardConfig.setMenuItemTextColor(DoingDailyApp.getAppContext().getResources().getColor(R.color.text_level_3));
        shareBoardConfig.setCancelButtonTextColor(DoingDailyApp.getAppContext().getResources().getColor(R.color.text_level_3));
        shareBoardConfig.setIndicatorVisibility(false);
        shareBoardConfig.setCancelButtonVisibility(true);
    }

    public static ShareController getInstance() {
        return ShareHolder.INSTANCE;
    }

    /**
     * 推荐好友-分享app
     * @param activity
     * @param callBack
     */
    public void shareApp(Activity activity, UMShareListener callBack) {

        if (activity == null) {
            return;
        }

        UMImage image = new UMImage(activity, R.drawable.ic_logo_rectangle);
        ShareAction shareAction = new ShareAction(activity);
        shareAction
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA)
                .withTargetUrl(ConstantValues.BUGLY_URL)
                .withTitle(DoingDailyApp.getAppContext().getResources().getString(R.string.app_name))
                .withText(DoingDailyApp.getAppContext().getResources().getString(R.string.app_description))
                .withMedia(image)
                .setCallback(callBack)
                .open(shareBoardConfig);
    }

    /**
     * 分享链接
     * @param activity
     * @param url
     * @param title
     * @param callBack
     */
    public void shareLink(Activity activity, String url, String title, UMShareListener callBack) {
        shareLink(activity, url, title, null, callBack);
    }

    /**
     * 分享链接
     * @param activity
     * @param url
     * @param title
     * @param imageUrl
     * @param callBack
     */
    public void shareLink(Activity activity, String url, String title, String imageUrl, UMShareListener callBack) {

        if (activity == null || TextUtils.isEmpty(url)) {
            return;
        }

        if(!TextUtils.isEmpty(imageUrl)){
            UMImage image = new UMImage(activity,imageUrl);
            ShareAction shareAction = new ShareAction(activity);
            shareAction
                    .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA)
                    .withTargetUrl(url)
                    .withTitle(title)
                    .withMedia(image)
                    .setCallback(callBack)
                    .open(shareBoardConfig);
        }
        else{
            UMImage image = new UMImage(activity, R.drawable.ic_logo_rectangle);
            ShareAction shareAction = new ShareAction(activity);
            shareAction
                    .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA)
                    .withTargetUrl(url)
                    .withTitle(title)
                    .withMedia(image)
                    .setCallback(callBack)
                    .open(shareBoardConfig);
        }

    }

    public void release(Activity activity) {
        UMShareAPI.get(activity).release();
    }
}

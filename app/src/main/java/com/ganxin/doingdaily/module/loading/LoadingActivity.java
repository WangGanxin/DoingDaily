package com.ganxin.doingdaily.module.loading;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.constants.ConstantValues;
import com.ganxin.doingdaily.common.utils.AppStatusTracker;
import com.ganxin.doingdaily.framework.BaseActivity;
import com.ganxin.doingdaily.module.MainActivity;

import butterknife.BindView;

/**
 * Description : 启动界面  <br/>
 * author : WangGanxin <br/>
 * date : 2016/10/9 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class LoadingActivity extends BaseActivity<LoadingContract.View, LoadingContract.Presenter> implements LoadingContract.View {

    @BindView(R.id.background_iv)
    ImageView backgroundIv;
    @BindView(R.id.logo_iv)
    ImageView logoIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppStatusTracker.getInstance().setAppStatus(ConstantValues.STATUS_OFFLINE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_loading);
    }

    @Override
    protected LoadingContract.Presenter createPresenter() {
        return new LoadingPresenter();
    }

    @Override
    protected void initView() {
        logoIv.startAnimation(createLogoAnimation());
        backgroundIv.startAnimation(createBackgroundAnimation());
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public Animation createBackgroundAnimation() {
        ScaleAnimation scaleAnimationa = new ScaleAnimation(1.0f, 1.3f, 1.0f, 1.3f
                , Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimationa.setFillAfter(true);
        scaleAnimationa.setDuration(3000);
        scaleAnimationa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                jumpToMain();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return scaleAnimationa;
    }

    @Override
    public Animation createLogoAnimation() {
        ScaleAnimation tvScaleAnim = new ScaleAnimation(1.0f, 1.3f, 1.0f, 1.3f
                , Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5f);
        TranslateAnimation tvTranslateAnim = new TranslateAnimation(0, 0, 0, -40);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(tvScaleAnim);
        animationSet.addAnimation(tvTranslateAnim);
        animationSet.setDuration(3000);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    @Override
    public void jumpToMain() {
        Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}

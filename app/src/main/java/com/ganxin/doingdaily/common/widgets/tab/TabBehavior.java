
package com.ganxin.doingdaily.common.widgets.tab;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Description : 自定义底部tab显示隐藏的Behavior  <br/>
 * author : WangGanxin <br/>
 * date : 2016/12/15 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class TabBehavior extends  CoordinatorLayout.Behavior<View> {
    public TabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        float translationY = Math.abs(dependency.getTop());
        child.setTranslationY(translationY);
        return true;
    }

}


package com.ganxin.doingdaily.common.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ganxin.doingdaily.framework.BaseContainerActivity;
import com.ganxin.doingdaily.framework.BaseFragment;

/**
 * Description : ActivityUtils:This provides methods to help Activities load their UI.  <br/>
 * author : WangGanxin <br/>
 * date : 2016/11/4 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class ActivityUtils {

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        if (fragmentManager != null && fragment != null) {

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(frameId, fragment);
            transaction.commit();
        }
    }

    public static void startActivity(@NonNull Activity activity, @NonNull BaseFragment fragment) {
        Intent intent = new Intent(activity, BaseContainerActivity.class);
        BaseContainerActivity.setFragment(fragment);
        activity.startActivity(intent);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void startActivityByAnimation(@NonNull Activity activity, @NonNull BaseFragment fragment, @NonNull Bundle options) {
        Intent intent = new Intent(activity, BaseContainerActivity.class);
        BaseContainerActivity.setFragment(fragment);
        ActivityCompat.startActivity(activity,intent,options);
    }
}

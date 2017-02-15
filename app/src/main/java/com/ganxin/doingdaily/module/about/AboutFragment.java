package com.ganxin.doingdaily.module.about;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.feedback.FeedbackAgent;
import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.constants.ConstantValues;
import com.ganxin.doingdaily.common.utils.DeviceUtil;
import com.ganxin.doingdaily.common.utils.SystemHelper;
import com.ganxin.doingdaily.common.widgets.rowview.ContainerView;
import com.ganxin.doingdaily.common.widgets.rowview.base.OnRowChangedListener;
import com.ganxin.doingdaily.common.widgets.rowview.group.GroupDescriptor;
import com.ganxin.doingdaily.common.widgets.rowview.normal.NormalRowDescriptor;
import com.ganxin.doingdaily.common.widgets.rowview.text.TextRowDescriptor;
import com.ganxin.doingdaily.framework.BaseFragment;
import com.ganxin.doingdaily.framework.ITabFragment;
import com.tencent.bugly.beta.Beta;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Description : 关于界面  <br/>
 * author : WangGanxin <br/>
 * date : 2016/11/3 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class AboutFragment extends BaseFragment<AboutContract.View, AboutContract.Presenter> implements AboutContract.View, ITabFragment, OnRowChangedListener {

    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.containerView)
    ContainerView containerView;
    @BindView(R.id.version_tv)
    TextView versionTv;

    @Override
    public int setContentLayout() {
        return R.layout.fragment_about;
    }

    @Override
    public void setUpView(View view) {
        versionTv.setText(getString(R.string.app_version, DeviceUtil.getVersionName(mActivity)));

        ArrayList<GroupDescriptor> groupDescriptors = new ArrayList<>();
        GroupDescriptor group1 = new GroupDescriptor();
        group1.addDescriptor(new TextRowDescriptor(R.string.row_somthing)
                .content(getString(R.string.row_somthing))
                .hasAction(false));
        group1.headerLabel(getString(R.string.row_header_something));

        GroupDescriptor group2 = new GroupDescriptor();
        group2.addDescriptor(new NormalRowDescriptor(R.string.row_share)
                .iconResId(R.drawable.ic_row_social_share)
                .label(getString(R.string.row_share))
                .hasAction(true));
        group2.addDescriptor(new NormalRowDescriptor(R.string.row_feedback)
                .iconResId(R.drawable.ic_row_feedback)
                .label(getString(R.string.row_feedback))
                .hasAction(true));
        group2.addDescriptor(new NormalRowDescriptor(R.string.row_update)
                .iconResId(R.drawable.ic_row_update)
                .label(getString(R.string.row_update))
                .hasAction(true));
        group2.addDescriptor(new NormalRowDescriptor(R.string.row_github)
                .iconResId(R.drawable.ic_row_github)
                .label(getString(R.string.row_github))
                .hasAction(true));

        group2.headerLabel(getString(R.string.row_header_other));

        groupDescriptors.add(group1);
        groupDescriptors.add(group2);

        containerView.initializeData(groupDescriptors, this);
        containerView.hasPaddingTop(true);
        containerView.notifyDataChanged();
    }

    @Override
    protected AboutContract.Presenter setPresenter() {
        return new AboutPresenter();
    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }

    @Override
    public void onRowChanged(int rowId) {
        switch (rowId) {
            case R.string.row_share:

                break;
            case R.string.row_feedback:
                FeedbackAgent agent = new FeedbackAgent(getActivity());
                agent.startDefaultThreadActivity();
                break;
            case R.string.row_update:
                Beta.checkUpgrade();
                break;
            case R.string.row_github:
                SystemHelper.SystemBrowser(getContext(), ConstantValues.GITHUB_URL);
                break;
        }
    }

    @Override
    public void onRowChanged(int rowId, String content) {

    }
}

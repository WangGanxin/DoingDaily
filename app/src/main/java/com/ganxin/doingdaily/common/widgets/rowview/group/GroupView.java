package com.ganxin.doingdaily.common.widgets.rowview.group;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ganxin.doingdaily.BuildConfig;
import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.utils.DensityUtil;
import com.ganxin.doingdaily.common.widgets.rowview.base.BaseRowDescriptor;
import com.ganxin.doingdaily.common.widgets.rowview.base.BaseRowView;
import com.ganxin.doingdaily.common.widgets.rowview.base.OnRowChangedListener;
import com.ganxin.doingdaily.common.widgets.rowview.normal.NormalRowDescriptor;
import com.ganxin.doingdaily.common.widgets.rowview.normal.NormalRowView;
import com.ganxin.doingdaily.common.widgets.rowview.text.TextRowDescriptor;
import com.ganxin.doingdaily.common.widgets.rowview.text.TextRowView;

import java.util.ArrayList;

/**
 * Description : GroupView  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/13 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class GroupView extends LinearLayout {

    private ArrayList<BaseRowDescriptor> descriptors;
    private Context context;
    private OnRowChangedListener listener;
    private String headerLabel;
    private int padding;
    private float density;
    private boolean hasPaddingTop = false;
    private boolean isHeaderHtml;
    private int headerColor;
    private int headerSize;
    private int bgColor;
    private int dividerColor;
    private boolean showTips;
    private String bottomLabel;

    public GroupView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView(context);
    }

    public GroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }

    public GroupView(Context context) {
        super(context);
        initializeView(context);
    }

    private void initializeView(Context context) {
        this.context = context;
        setOrientation(VERTICAL);
        setBackgroundResource(android.R.color.transparent);
        density = context.getResources().getDisplayMetrics().density;
        padding = (int) (20 * density);
    }

    public void initializeData(GroupDescriptor descriptor, OnRowChangedListener listener) {
        this.descriptors = descriptor.descriptors;
        this.headerLabel = descriptor.headerLabel;
        this.headerColor = descriptor.headerColor;
        this.headerSize = descriptor.headerSize;
        this.isHeaderHtml = descriptor.isHeaderHtml;
        this.bgColor = descriptor.bgColor;
        this.dividerColor = descriptor.dividerColor;
        this.bottomLabel = descriptor.bottomLabel;
        this.listener = listener;
    }

    public void notifyDataChanged() {
        removeAllViews();
        BaseRowView row = null;
        View line = null;
        if (hasPaddingTop || headerLabel != null) {

            TextView headerView = new TextView(context);
            LayoutParams headerParams;
            if (headerLabel == null) {
                headerParams = new LayoutParams(LayoutParams.MATCH_PARENT, padding / 2);
                addView(headerView, headerParams);
            } else {
                headerParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                if (headerColor > 0) {
                    headerView.setTextColor(getResources().getColor(headerColor));
                }
                if (headerSize > 0) {
                    headerView.setTextSize(TypedValue.COMPLEX_UNIT_SP, headerSize);
                }
                if (isHeaderHtml) {
                    headerView.setText(Html.fromHtml(headerLabel));
                    headerView.setMovementMethod(LinkMovementMethod.getInstance());
                } else {
                    headerView.setText(headerLabel);
                }
                headerView.setPadding(padding, padding / 2, padding, padding / 2);
                addView(headerView, headerParams);
            }

        }
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, DensityUtil.getInstance(getContext()).getDip2Px(1));
        //params.leftMargin = (int) (40 * density);
        BaseRowDescriptor descriptor = null;
        int lineColor = getResources().getColor(dividerColor);
        if (descriptors != null && descriptors.size() > 0) {
            for (int i = 0; i < descriptors.size(); i++) {
                descriptor = descriptors.get(i);
                if (descriptor instanceof NormalRowDescriptor) {
                    row = new NormalRowView(context);
                }
                else if(descriptor instanceof TextRowDescriptor){
                    row=new TextRowView(context);
                }
//                else if (descriptor instanceof ProfileRowDescriptor) {
//                    row = new ProfileRowView(context);
//                }
//                else if (descriptor instanceof OneRowDescriptor) {
//                    row = new OneRowView(context);
//                }
//                else if (descriptor instanceof ToggleRowDescriptor) {
//                    row = new ToggleRowView(context);
//                } else if (descriptor instanceof SocialRowDescriptor) {
//                    row = new SocialRowView(context);
//                } else if (descriptor instanceof RadioGroupRowDescriptor) {
//                    row = new RadioGroupRowView(context);
//                } else if (descriptor instanceof MultiRowDescriptor) {
//                    row = new MultiRowView(context);
//                } else if (descriptor instanceof GridRowDescriptor) {
//                    row = new GridRowView(context);
//                } else if (descriptor instanceof VerticalRowDescriptor) {
//                    row = new VerticalRowView(context);
//                } else if (descriptor instanceof ImgUploadRowDescriptor) {
//                    row = new ImgUploadRowView(context);
//                } else if (descriptor instanceof VerifyRowDescriptor) {
//                    row = new VerifyRowView(context);
//                } else if (descriptor instanceof TableRowDescriptor) {
//                    row = new TableRowView(context);
//                } else if (descriptor instanceof InsuranceRowDescriptor) {
//                    row = new InsuranceRowView(context);
//                } else if (descriptor instanceof LinkRowDescriptor) {
//                    row = new LinkRowView(context);
//                } else if (descriptor instanceof ButtonRowDescriptor) {
//                    row = new ButtonRowView(context);
//                } else if (descriptor instanceof HtmlRowDescriptor) {
//                    row = new HtmlRowView(context);
//                } else if (descriptor instanceof LinceseUploadRowDescriptor) {
//                    row = new LinceseUploadRowView(context);
//                } else if (descriptor instanceof QuotationTipRowDescriptor) {
//                    row = new QuotationTipRowView(context);
                else {
                    throw new IllegalArgumentException("you forget to initialize the right RowView with " + descriptor.getClass().getSimpleName());
                }
                row.setMinimumHeight((int) (50 * density));
//                row.setTag(descriptor.rowId);
                row.setId(descriptor.rowId);
                row.setPadding(padding, padding / 2, padding, padding / 2);
                row.setOnRowChangedListener(listener);
                row.notifyDataChanged(descriptor);
                addView(row);
                if (i != descriptors.size() - 1) {
                    line = new View(context);
                    line.setBackgroundColor(lineColor);
                    addView(line, params);
                }
            }


            if (bottomLabel != null) {
                LayoutParams bottomParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                TextView bottomView = new TextView(context);
                bottomView.setText(bottomLabel);
                bottomView.setTextColor(getResources().getColor(headerColor));
                bottomView.setPadding(padding, padding / 2, padding, padding / 2);
//                bottomView.setTextSize(12, TypedValue.COMPLEX_UNIT_SP);
                bottomView.setBackgroundResource(R.color.background_page);
                addView(bottomView, bottomParams);
            }
        } else {
//            setVisibility(View.GONE);
        }
    }

    public void notifyDataChanged(int rowId, String content) {
        BaseRowView row = (BaseRowView) findViewById(rowId);
        if (row != null) {
            row.notifyDataChanged(content);
        } else {
            if (BuildConfig.DEBUG) {
                throw new IllegalArgumentException("can't find the row by id");
            }
        }
    }

    public void notifyDataChanged(int rowId, BaseRowDescriptor descriptor) {
        BaseRowView row = (BaseRowView) findViewById(rowId);
        if (row != null) {
            row.notifyDataChanged(descriptor);
        } else {
            if (BuildConfig.DEBUG) {
                throw new IllegalArgumentException("can't find the row by id");
            }
        }
    }

    public String getContentById(int id) {
        BaseRowView row = (BaseRowView) findViewById(id);
        if (row != null) {
            return row.getContent();
        }
        if (BuildConfig.DEBUG) {
            throw new IllegalArgumentException("can't find the row by id");
        }
        return null;
    }

    public void hasPaddingTop(boolean hasPaddingTop) {
        this.hasPaddingTop = hasPaddingTop;
    }

}

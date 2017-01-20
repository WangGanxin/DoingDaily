package com.ganxin.doingdaily.common.widgets.rowview.normal;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.widgets.rowview.base.BaseRowView;

/**
 *
 * Description : NormalRowView  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/13 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class NormalRowView extends BaseRowView<NormalRowDescriptor> implements OnClickListener {

    private Context context;
    private ImageView mWidgetRowActionImg;
    private TextView mWidgetRowLabel;
    private ImageView mWidgetRowIconImg;

    public NormalRowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initializeView();
    }

    public NormalRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeView();
    }

    public NormalRowView(Context context) {
        super(context);
        this.context = context;
        initializeView();
    }

    private void initializeView() {
        LayoutInflater.from(context).inflate(R.layout.widget_normal_row, this);
        mWidgetRowActionImg = (ImageView) findViewById(R.id.mWidgetRowActionImg);
        mWidgetRowLabel = (TextView) findViewById(R.id.mWidgetRowLabel);
        mWidgetRowIconImg = (ImageView) findViewById(R.id.mWidgetRowIconImg);
       // mWidgetRowActionImg.setImageResource(R.drawable.ic_row_forward);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onRowChanged(descriptor.rowId);
        }
    }

    @Override
    public void notifyDataChanged(String content) {
        mWidgetRowLabel.setText(content);
        this.descriptor.label = content;
    }

    @Override
    public void notifyDataChanged(NormalRowDescriptor descriptor) {
        this.descriptor =  descriptor;
        if (descriptor != null) {
            if (descriptor.iconResId > 0) {
                mWidgetRowIconImg.setImageResource(descriptor.iconResId);
                mWidgetRowIconImg.setVisibility(VISIBLE);
            } else {
                mWidgetRowIconImg.setVisibility(GONE);
            }
            mWidgetRowLabel.setText(descriptor.label);
            if (descriptor.hasAction && descriptor.rowId > 0) {
                setOnClickListener(this);
                setBackgroundResource(R.drawable.md_selector_white);
                mWidgetRowActionImg.setVisibility(VISIBLE);
            } else {
                setBackgroundColor(Color.WHITE);
                mWidgetRowActionImg.setVisibility(GONE);
            }
        } else {
            setVisibility(GONE);
        }
    }

}

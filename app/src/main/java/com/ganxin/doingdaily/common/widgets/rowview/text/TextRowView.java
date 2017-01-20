package com.ganxin.doingdaily.common.widgets.rowview.text;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.widgets.rowview.base.BaseRowView;

/**
 * Description : 仅显示文本Row  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/13 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class TextRowView extends BaseRowView<TextRowDescriptor> implements OnClickListener {

    private Context context;
    private TextView mWidgetRowContent;

    public TextRowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initializeView();
    }

    public TextRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeView();
    }

    public TextRowView(Context context) {
        super(context);
        this.context = context;
        initializeView();
    }

    private void initializeView() {
        LayoutInflater.from(context).inflate(R.layout.widget_text_row, this);
        mWidgetRowContent = (TextView) findViewById(R.id.mWidgetRowContent);
        setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onRowChanged(descriptor.rowId);
        }
    }

    @Override
    public void notifyDataChanged(String content) {
        mWidgetRowContent.setText(content);
        this.descriptor.content = content;
    }

    @Override
    public void notifyDataChanged(TextRowDescriptor descriptor) {
        this.descriptor = descriptor;
        if (descriptor != null) {
            mWidgetRowContent.setText(descriptor.content);
        } else {
            setVisibility(GONE);
        }
    }

}

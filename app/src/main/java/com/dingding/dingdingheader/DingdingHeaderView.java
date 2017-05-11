package com.dingding.dingdingheader;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkcool.circletextimageview.CircleTextImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mickey on 17/5/10.
 */

public class DingdingHeaderView extends LinearLayout {


    @BindView(R.id.circle_text)
    CircleTextImageView circleText;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    public DingdingHeaderView(Context context) {
        this(context, null);
    }

    public DingdingHeaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DingdingHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        View view = inflate(context, R.layout.header_dingding, this);
        ButterKnife.bind(view);
    }

    public CircleTextImageView getCircleText() {
        return circleText;
    }

    public TextView getName() {
        return name;
    }

    public LinearLayout getLlBottom() {
        return llBottom;
    }
}

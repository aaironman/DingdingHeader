package com.dingding.dingdingheader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.noties.scrollable.CanScrollVerticallyDelegate;
import ru.noties.scrollable.OnFlingOverListener;
import ru.noties.scrollable.ScrollableLayout;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.header)
    DingdingHeaderView header;
    @BindView(R.id.sv)
    ScrollView sv;
    @BindView(R.id.scrollable_layout)
    ScrollableLayout scrollableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolBar(toolbar,true);
        initView();
    }

    public void initToolBar(Toolbar toolbar, boolean isShowBackIcon) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(isShowBackIcon);
    }

    private void initView(){
        scrollableLayout.setCanScrollVerticallyDelegate(new CanScrollVerticallyDelegate() {
            @Override
            public boolean canScrollVertically(int direction) {
                return sv.canScrollVertically(direction);
            }
        });
        scrollableLayout.addOnScrollChangedListener(new DingdingHeaderViewOnScrollChangedListener(this,header));

        scrollableLayout.setOnFlingOverListener(new OnFlingOverListener() {
            @Override
            public void onFlingOver(int y, long duration) {
                sv.smoothScrollBy(0, y);
            }
        });
        ViewTreeObserver viewTreeObserver = sv.getViewTreeObserver();
        viewTreeObserver
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        sv.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) sv.getLayoutParams();
                        layoutParams.topMargin = getSupportActionBar().getHeight() + header.getLlBottom().getHeight();
                        Log.v("mickey","getSupportActionBar().getHeight:" + getSupportActionBar().getHeight() + "--header.getLlBottom().getHeight:" + header.getLlBottom().getHeight());
                        sv.setLayoutParams(layoutParams);
                        int maxScrollY = dip2px(300) - layoutParams.topMargin;
                        scrollableLayout.setMaxScrollY(maxScrollY);
                    }
                });

    }


    public int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}

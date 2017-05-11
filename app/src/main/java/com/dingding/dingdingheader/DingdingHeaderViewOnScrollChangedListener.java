package com.dingding.dingdingheader;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import ru.noties.scrollable.OnScrollChangedListener;

/**
 * Created by mickey on 17/5/10.
 */

public class DingdingHeaderViewOnScrollChangedListener implements OnScrollChangedListener {

    private Context context;
    private DingdingHeaderView dingdingHeaderView;

    public DingdingHeaderViewOnScrollChangedListener(Context context,DingdingHeaderView dingdingHeaderView) {
        this.context = context;
        this.dingdingHeaderView = dingdingHeaderView;
    }

    @Override
    public void onScrollChanged(int y, int oldY, int maxY) {

        final View circleText = dingdingHeaderView.getCircleText();
        final View nameText = dingdingHeaderView.getName();
        final View bottomLayout = dingdingHeaderView.getLlBottom();

        int actionBarHeight = ((AppCompatActivity)context).getSupportActionBar().getHeight();
        int circleTextTop = circleText.getTop();
        int circleTextHeight = circleText.getHeight();

        int nameTop = nameText.getTop();
        int nameHeight = nameText.getHeight();
        int nameLeft = nameText.getLeft();

        int bottomLayoutTop = bottomLayout.getTop();

        final float bootomHeaderY;
        final float nameHeaderY;
        final float circleHeaderY;

        float nameScale = 1F;
        float nameFraction;
        float circleScale = .2F;
        float circleFraction;

        int navigationIconPos = dip2px(60);//假设最后圆形中心在屏幕左端60dp处
        float circleXDistance = getScreenWidth()/2-navigationIconPos;//圆形移动的水平最大距离
        float circleHeaderX;

        float nameTextPos = nameLeft - (navigationIconPos + circleTextHeight*circleScale);//最后文字的位置，圆形字中心+圆形字缩放后的宽度，所以文字与圆形字的间距为缩放后宽度的一半
        float nameHeaderX;

        if (Float.compare(y, bottomLayoutTop-actionBarHeight) < 0) {
            bootomHeaderY = .0F;
        } else {
            bootomHeaderY = y - (bottomLayoutTop-actionBarHeight);
        }
        if (Float.compare(y, nameTop+ (nameHeight-actionBarHeight)/2) < 0){
            nameHeaderY = .0F;
            float nameDistance = (nameTop+ (nameHeight-actionBarHeight)/2);
            nameFraction =1-y/nameDistance *((1-nameScale));
//            nameHeaderX = nameTextPos*(y/nameDistance);
        }else {
            nameHeaderY = y - (nameTop+ (nameHeight-actionBarHeight)/2);
            nameFraction = nameScale;
//            nameHeaderX = nameTextPos;
        }

        if (Float.compare(y, circleTextTop+ (circleTextHeight-actionBarHeight)/2) < 0){
            circleHeaderY = .0F;
            float circleDistance = (circleTextTop+ (circleTextHeight-actionBarHeight)/2);
            circleFraction = 1-y/circleDistance *((1-circleScale));
            circleHeaderX = circleXDistance* (y/circleDistance);
            nameHeaderX = y*(nameTextPos/(circleTextTop+ (circleTextHeight-actionBarHeight)/2));

        }else {
            circleHeaderY = y - (circleTextTop+ (circleTextHeight-actionBarHeight)/2);
            circleFraction = 1* circleScale;
            circleHeaderX = circleXDistance;

            nameHeaderX = nameTextPos;
        }
        Log.v("mickey","nameFraction:" + nameFraction + "--circleFraction:" + circleFraction);
        circleText.setTranslationY(circleHeaderY);
        circleText.setTranslationX(-circleHeaderX);
        circleText.setScaleX(circleFraction);
        circleText.setScaleY(circleFraction);

        nameText.setTranslationY(nameHeaderY);
        nameText.setTranslationX(-nameHeaderX);
        nameText.setScaleX(nameFraction);
        nameText.setScaleY(nameFraction);

        bottomLayout.setTranslationY(bootomHeaderY);

    }

    /**
     * 得到屏幕宽度
     *
     *
     * @return
     */
    public int getScreenWidth() {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }
    public int dip2px(float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}

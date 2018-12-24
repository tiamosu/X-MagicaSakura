package com.bilibili.magicasakurademo.test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.ColorInt;
import androidx.annotation.IntDef;

/**
 * @author limuyang
 * @date 2018/8/14
 */
public interface ILayout {
    int HIDE_RADIUS_SIDE_NONE = 0;
    int HIDE_RADIUS_SIDE_TOP = 1;
    int HIDE_RADIUS_SIDE_RIGHT = 2;
    int HIDE_RADIUS_SIDE_BOTTOM = 3;
    int HIDE_RADIUS_SIDE_LEFT = 4;

    @IntDef(value = {
            HIDE_RADIUS_SIDE_NONE,
            HIDE_RADIUS_SIDE_TOP,
            HIDE_RADIUS_SIDE_RIGHT,
            HIDE_RADIUS_SIDE_BOTTOM,
            HIDE_RADIUS_SIDE_LEFT})
    @Retention(RetentionPolicy.SOURCE)
    @interface HideRadiusSide {
    }

    /**
     * limit the width of a layout
     */
    boolean setWidthLimit(int widthLimit);

    /**
     * limit the height of a layout
     */
    boolean setHeightLimit(int heightLimit);

    /**
     * use the shadow elevation from the theme
     */
    void setUseThemeGeneralShadowElevation();

    /**
     * determine if the outline contain the padding area, usually false
     */
    void setOutlineExcludePadding(boolean outlineExcludePadding);

    /**
     * See {@link android.view.View#setElevation(float)}
     */
    void setShadowElevation(int elevation);

    int getShadowElevation();

    /**
     * set the outline alpha, which will change the shadow
     */
    void setShadowAlpha(float shadowAlpha);

    /**
     * get the outline alpha we set
     */
    float getShadowAlpha();

    /**
     * set the layout radius
     */
    void setRadius(int radius);

    /**
     * set the layout radius with one or none side been hidden
     */
    void setRadius(int radius, @LayoutHelper.HideRadiusSide int hideRadiusSide);

    /**
     * get the layout radius
     */
    int getRadius();

    /**
     * inset the outline if needed
     */
    void setOutlineInset(int left, int top, int right, int bottom);

    /**
     * the shadow elevation only work after L, so we provide a downgrading compatible solutions for android 4.x
     * usually we use border, but the border may be redundant for android L+. so will not show border default,
     * if your designer like the border exists with shadow, you can call setShowBorderOnlyBeforeL(false)
     */
    void setShowBorderOnlyBeforeL(boolean showBorderOnlyBeforeL);

    /**
     * in some case, we maybe hope the layout only have radius in one side.
     * but there is no convenient way to write the code like canvas.drawPath,
     * so we take another way that hide one radius side
     */
    void setHideRadiusSide(@HideRadiusSide int hideRadiusSide);

    /**
     * get the side that we have hidden the radius
     */
    int getHideRadiusSide();

    /**
     * this method will determine the radius and shadow.
     */
    void setRadiusAndShadow(int radius, int shadowElevation, float shadowAlpha);

    /**
     * this method will determine the radius and shadow with one or none side be hidden
     */
    void setRadiusAndShadow(int radius, @HideRadiusSide int hideRadiusSide, int shadowElevation, float shadowAlpha);

    /**
     * border color, if you don not set it, the layout will not draw the border
     */
    void setBorderColor(@ColorInt int borderColor);

    /**
     * border width, default is 1px, usually no need to set
     */
    void setBorderWidth(int borderWidth);

    /**
     * config the top divider
     */
    void updateTopDivider(int topInsetLeft, int topInsetRight, int topDividerHeight, int topDividerColor);

    /**
     * config the bottom divider
     */
    void updateBottomDivider(int bottomInsetLeft, int bottomInsetRight, int bottomDividerHeight, int bottomDividerColor);

    /**
     * config the left divider
     */
    void updateLeftDivider(int leftInsetTop, int leftInsetBottom, int leftDividerWidth, int leftDividerColor);

    /**
     * config the right divider
     */
    void updateRightDivider(int rightInsetTop, int rightInsetBottom, int rightDividerWidth, int rightDividerColor);

    /**
     * show top divider, and hide others
     */
    void onlyShowTopDivider(int topInsetLeft, int topInsetRight, int topDividerHeight, int topDividerColor);

    /**
     * show bottom divider, and hide others
     */
    void onlyShowBottomDivider(int bottomInsetLeft, int bottomInsetRight, int bottomDividerHeight, int bottomDividerColor);

    /**
     * show left divider, and hide others
     */
    void onlyShowLeftDivider(int leftInsetTop, int leftInsetBottom, int leftDividerWidth, int leftDividerColor);

    /**
     * show right divider, and hide others
     */
    void onlyShowRightDivider(int rightInsetTop, int rightInsetBottom, int rightDividerWidth, int rightDividerColor);

    /**
     * after config the border, sometimes we need change the alpha of divider with animation,
     * so we provide a method to individually change the alpha
     *
     * @param dividerAlpha [0, 255]
     */
    void setTopDividerAlpha(int dividerAlpha);

    /**
     * @param dividerAlpha [0, 255]
     */
    void setBottomDividerAlpha(int dividerAlpha);

    /**
     * @param dividerAlpha [0, 255]
     */
    void setLeftDividerAlpha(int dividerAlpha);

    /**
     * @param dividerAlpha [0, 255]
     */
    void setRightDividerAlpha(int dividerAlpha);
}

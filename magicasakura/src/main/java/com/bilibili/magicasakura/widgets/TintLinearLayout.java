package com.bilibili.magicasakura.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.bilibili.magicasakura.R;
import com.bilibili.magicasakura.utils.ThemeUtils;
import com.bilibili.magicasakura.utils.TintManager;

/**
 * @author xyczero617@gmail.com
 * @time 16/2/14
 */
public class TintLinearLayout extends LinearLayout implements Tintable,
        AppCompatBackgroundHelper.BackgroundExtensible {

    private AppCompatBackgroundHelper mBackgroundHelper;
    private int mDividerColorId;

    public TintLinearLayout(Context context) {
        this(context, null);
    }

    public TintLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TintLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            return;
        }
        final TintManager tintManager = TintManager.get(context);
        mBackgroundHelper = new AppCompatBackgroundHelper(this, tintManager);
        mBackgroundHelper.loadFromAttribute(attrs, defStyleAttr);

        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TintLinearLayout);
        mDividerColorId = ta.getResourceId(R.styleable.TintLinearLayout_dividerTint, 0);
        ta.recycle();

        setDividerTint();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (getBackground() != null) {
            invalidateDrawable(getBackground());
        }
    }

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundDrawableExternal(background);
        }
    }

    @Override
    public void setBackgroundResource(int resId) {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundResId(resId);
        } else {
            super.setBackgroundResource(resId);
        }
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundColor(color);
        }
    }

    @Override
    public void setBackgroundTintList(int resId) {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundTintList(resId, null);
        }
    }

    @Override
    public void setBackgroundTintList(int resId, PorterDuff.Mode mode) {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundTintList(resId, mode);
        }
    }

    @Override
    public void tint() {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.tint();
            setDividerTint();
        }
    }

    private void setDividerTint() {
        Drawable drawableDivider = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            drawableDivider = getDividerDrawable();
        }
        if (drawableDivider != null && mDividerColorId != 0) {
            final int color = ThemeUtils.getColorById(getContext(), mDividerColorId);
            final GradientDrawable gradientDrawable = (GradientDrawable) drawableDivider;
            gradientDrawable.setColor(color);
        }
    }
}

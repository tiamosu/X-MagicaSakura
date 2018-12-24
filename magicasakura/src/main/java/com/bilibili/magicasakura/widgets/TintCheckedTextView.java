package com.bilibili.magicasakura.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;

import com.bilibili.magicasakura.R;
import com.bilibili.magicasakura.utils.TintManager;

import androidx.annotation.DrawableRes;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

/**
 * @author xyczero617@gmail.com
 * @time 16/2/1
 * <p/>
 * special view for replacing view in preference , not recommend to use it in common
 * layout: select_dialog_singlechoice_xxx
 */
public class TintCheckedTextView extends AppCompatCheckedTextView implements Tintable {

    private static final int[] ATTRS = {
            android.R.attr.drawableLeft,
            R.attr.drawableLeftTint
    };

    public TintCheckedTextView(Context context) {
        this(context, null);
    }

    public TintCheckedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.checkedTextViewStyle);
    }

    @SuppressLint("ResourceType")
    public TintCheckedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, ATTRS);
        final int drawLeftId = array.getResourceId(0, 0);
        final int drawLeftTintId = array.getResourceId(1, 0);
        array.recycle();
        if (drawLeftId != 0 && drawLeftTintId != 0) {
            tintCheckTextView(context, drawLeftId, drawLeftTintId);
        }
    }

    public void tintCheckTextView(Context context, @DrawableRes int resId, int tintId) {
        final Drawable drawable = ContextCompat.getDrawable(context, resId);
        if (drawable != null) {
            final Drawable newDrawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTintList(newDrawable, TintManager.get(getContext()).getColorStateList(tintId));
            DrawableCompat.setTintMode(newDrawable, PorterDuff.Mode.SRC_IN);
            //android-sdk-23 material layout is deprecate android.R.styleable#CheckedTextView_checkMark
            //follow android native style, check position is left when version is above 5.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setCompoundDrawablesRelativeWithIntrinsicBounds(newDrawable, null, null, null);
                setCheckMarkDrawable(null);
            } else {
                setCheckMarkDrawable(newDrawable);
                setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        }
    }

    @Override
    public void setCheckMarkDrawable(Drawable d) {
        super.setCheckMarkDrawable(d);
    }

    @Override
    public void setCheckMarkDrawable(int resId) {
        super.setCheckMarkDrawable(resId);
    }

    @Override
    public void tint() {
    }
}

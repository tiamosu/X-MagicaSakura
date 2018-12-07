package com.bilibili.magicasakurademo.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.bilibili.magicasakura.widgets.Tintable;
import com.bilibili.magicasakurademo.R;

/**
 * @author weixia
 * @date 2018/12/6.
 */
public class CustomTintEditText extends AppCompatEditText implements Tintable {
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_CAN_CLEAR = 1;
    private static final int TYPE_CAN_WATCH_PWD = 2;
    private final static int DEFAULT_PADDING = 15;

    //内边距大小
    private int padding, paddingLeft, paddingTop, paddingRight, paddingBottom;
    //是否含有边框
    private boolean mIsBorderView;
    //边框颜色
    private int mBorderColor, mBorderColorResId;
    //含有焦点时的边框颜色
    private int mBorderFocusColor, mBorderFocusColorResId;
    //边框圆角大小
    private float mBorderCornerRadius;
    //边框宽度大小
    private int mBorderStrokeWidth;
    //EditText的背景颜色
    private int mBackgroundColor, mBackgroundColorResId;

    /**
     * 功能的类型
     * 默认为0，没有功能
     * 1，带有清除文本功能
     * 2，带有查看密码功能
     */
    private int mFuncType;
    //清除图标
    private Drawable mClearIconDrawable;
    //查看密码时的图标资源
    private int mPwdShowIconResId;
    //隐藏密码时的图标资源
    private int mPwdHideIconResId;
    //正处于查看密码状态
    private boolean mIsShowingPwd = false;
    //普通状态时的左图标
    private Drawable mLeftDrawable;
    //普通状态时的右图标
    private Drawable mRightDrawable;

    //左边图标颜色
    private int mLeftIconTint, mLeftIconTintResId;
    //右边图标颜色
    private int mRightIconTint, mRightIconTintResId;

    //图标宽度
    private int mIconWidth;
    //图标高度
    private int mIconHeight;

    //是否含有焦点
    private boolean mIsFocused = false;

    private OnRightClickListener mOnRightClickListener;

    public void setOnRightClickListener(OnRightClickListener onRightClickListener) {
        mOnRightClickListener = onRightClickListener;
    }

    public CustomTintEditText(Context context) {
        this(context, null);
    }

    public CustomTintEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public CustomTintEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomTintEditText);
        padding = ta.getDimensionPixelSize(R.styleable.CustomTintEditText_android_padding, -1);
        paddingLeft = ta.getDimensionPixelSize(R.styleable.CustomTintEditText_android_paddingLeft, DEFAULT_PADDING);
        paddingTop = ta.getDimensionPixelSize(R.styleable.CustomTintEditText_android_paddingTop, DEFAULT_PADDING);
        paddingRight = ta.getDimensionPixelSize(R.styleable.CustomTintEditText_android_paddingRight, DEFAULT_PADDING);
        paddingBottom = ta.getDimensionPixelSize(R.styleable.CustomTintEditText_android_paddingBottom, DEFAULT_PADDING);

        mIsBorderView = ta.getBoolean(R.styleable.CustomTintEditText_tet_isBorderView, false);
        mBorderColorResId = ta.getResourceId(R.styleable.CustomTintEditText_tet_borderColor, -1);
        mBorderFocusColorResId = ta.getResourceId(R.styleable.CustomTintEditText_tet_borderFocusColor, -1);
        mBorderCornerRadius = ta.getDimension(R.styleable.CustomTintEditText_tet_borderCornerRadius, 0);
        mBorderStrokeWidth = ta.getDimensionPixelSize(R.styleable.CustomTintEditText_tet_borderStrokeWidth, 1);

        mBackgroundColorResId = ta.getResourceId(R.styleable.CustomTintEditText_tet_backgroundColor, -1);
        mLeftIconTintResId = ta.getResourceId(R.styleable.CustomTintEditText_tet_leftIconTint, -1);
        mRightIconTintResId = ta.getResourceId(R.styleable.CustomTintEditText_tet_rightIconTint, -1);

        if (ta.hasValue(R.styleable.CustomTintEditText_tet_clearIcon)) {
            mClearIconDrawable = ta.getDrawable(R.styleable.CustomTintEditText_tet_clearIcon);
        } else {
            mClearIconDrawable = ContextCompat.getDrawable(context, android.R.drawable.ic_menu_close_clear_cancel);
        }

        mFuncType = ta.getInt(R.styleable.CustomTintEditText_tet_funcType, TYPE_NORMAL);
        mPwdShowIconResId = ta.getResourceId(R.styleable.CustomTintEditText_tet_pwdShowIcon, -1);
        mPwdHideIconResId = ta.getResourceId(R.styleable.CustomTintEditText_tet_pwdHideIcon, -1);
        mLeftDrawable = getCompoundDrawables()[0];
        mRightDrawable = getCompoundDrawables()[2];

        mIconWidth = ta.getDimensionPixelSize(R.styleable.CustomTintEditText_tet_iconWidth, 40);
        mIconHeight = ta.getDimensionPixelSize(R.styleable.CustomTintEditText_tet_iconHeight, 40);
        ta.recycle();

        initView();
    }

    private void initView() {
        mBorderColor = mBorderColorResId != -1
                ? ThemeUtils.getColorById(getContext(), mBorderColorResId) : Color.GRAY;
        mBorderFocusColor = mBorderFocusColorResId != -1
                ? ThemeUtils.getColorById(getContext(), mBorderFocusColorResId) : Color.GRAY;
        mBackgroundColor = mBackgroundColorResId != -1
                ? ThemeUtils.getColorById(getContext(), mBackgroundColorResId) : Color.TRANSPARENT;
        mLeftIconTint = mLeftIconTintResId != -1
                ? ThemeUtils.getColorById(getContext(), mLeftIconTintResId) : Color.GRAY;
        mRightIconTint = mRightIconTintResId != -1
                ? ThemeUtils.getColorById(getContext(), mRightIconTintResId) : Color.GRAY;

        if (mIsBorderView) {
            final int borderColor = mIsFocused ? mBorderFocusColor : mBorderColor;
            setBackGroundOfLayout(getShapeBackground(borderColor));
        } else {
            setPadding(false);
        }

        switch (mFuncType) {
            case TYPE_CAN_CLEAR:
                handleClearButton();
                break;
            case TYPE_CAN_WATCH_PWD:
                if (!TextUtils.isEmpty(getText())) {
                    showPasswordVisibilityIndicator(true);
                } else {
                    showPasswordVisibilityIndicator(false);
                }
                break;
            default:
                handleLeftRightButton();
                break;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getCompoundDrawables()[2] == null
                || event.getAction() != MotionEvent.ACTION_UP) {
            return super.onTouchEvent(event);
        }

        final boolean isTouchedDrawableRight =
                event.getX() > getWidth() - getTotalPaddingRight()
                        && event.getX() < getWidth() - getPaddingRight();
        if (!isTouchedDrawableRight) {
            return super.onTouchEvent(event);
        }
        if (mOnRightClickListener != null) {
            mOnRightClickListener.onClick(this);
        }
        if (mFuncType == TYPE_CAN_CLEAR) {
            setText("");
            handleClearButton();
        } else if (mFuncType == TYPE_CAN_WATCH_PWD) {
            togglePasswordVisibility();
            event.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        try {
            if (mFuncType == TYPE_CAN_CLEAR) {
                handleClearButton();
                return;
            }
            if (mFuncType == TYPE_CAN_WATCH_PWD) {
                if (text.length() > 0) {
                    showPasswordVisibilityIndicator(true);
                } else {
                    mIsShowingPwd = false;
                    maskPassword();
                    showPasswordVisibilityIndicator(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        this.mIsFocused = focused;
        if (mIsBorderView) {
            final int borderColor = focused ? mBorderFocusColor : mBorderColor;
            setBackGroundOfLayout(getShapeBackground(borderColor));
        }
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    private void setBackGroundOfLayout(Drawable shape) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(shape);
        } else {
            setBackgroundDrawable(shape);
        }
        setPadding(true);
    }

    private Drawable getShapeBackground(@ColorInt int color) {
        final GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(mBorderCornerRadius);
        shape.setColor(mBackgroundColor);
        shape.setStroke(mBorderStrokeWidth, color);
        return shape;
    }

    private void setPadding(boolean isRound) {
        int extraPadding;
        int extraPad;
        if (isRound) {
            extraPadding = 5;
            extraPad = 0;
        } else {
            extraPad = 5;
            extraPadding = 0;
        }
        if (padding != -1) {
            setPadding(padding + extraPadding, padding, padding, padding + extraPad);
        } else {
            setPadding(paddingLeft + extraPadding, paddingTop, paddingRight, paddingBottom + extraPad);
        }
    }

    private void handleClearButton() {
        if (mClearIconDrawable == null) {
            return;
        }
        if (mRightIconTint != -1) {
            DrawableCompat.setTint(mClearIconDrawable, mRightIconTint);
        }
        mClearIconDrawable.setBounds(0, 0, mIconWidth, mIconHeight);
        if (getText() == null || getText().length() == 0) {
            setCompoundDrawables(null, getCompoundDrawables()[1],
                    null, getCompoundDrawables()[3]);
        } else {
            setCompoundDrawables(null, getCompoundDrawables()[1],
                    mClearIconDrawable, getCompoundDrawables()[3]);
        }
    }

    private void showPasswordVisibilityIndicator(boolean show) {
        if (!show) {
            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
                    null, getCompoundDrawables()[3]);
            return;
        }

        final Drawable original = mIsShowingPwd
                ? (mPwdShowIconResId != -1 ? ContextCompat.getDrawable(getContext(), mPwdShowIconResId) : null)
                : (mPwdHideIconResId != -1 ? ContextCompat.getDrawable(getContext(), mPwdHideIconResId) : null);
        if (original != null) {
            original.mutate();
            if (mRightIconTint != -1) {
                DrawableCompat.setTint(original, mRightIconTint);
            }
            original.setBounds(0, 0, mIconWidth, mIconHeight);
            setCompoundDrawables(null, getCompoundDrawables()[1],
                    original, getCompoundDrawables()[3]);
        }
    }

    private void handleLeftRightButton() {
        if (mLeftDrawable != null) {
            mLeftDrawable.setBounds(0, 0, mIconWidth, mIconHeight);
            if (mLeftIconTint != -1) {
                DrawableCompat.setTint(mLeftDrawable, mLeftIconTint);
            }
        }
        if (mRightDrawable != null) {
            mRightDrawable.setBounds(0, 0, mIconWidth, mIconHeight);
            if (mRightIconTint != -1) {
                DrawableCompat.setTint(mRightDrawable, mRightIconTint);
            }
        }
        setCompoundDrawables(mLeftDrawable, getCompoundDrawables()[1],
                mRightDrawable, getCompoundDrawables()[3]);
    }

    private void togglePasswordVisibility() {
        // Store the selection
        final int selectionStart = getSelectionStart();
        final int selectionEnd = getSelectionEnd();
        // Set transformation method to show/hide password
        if (mIsShowingPwd) {
            maskPassword();
        } else {
            unmaskPassword();
        }
        // Restore selection
        setSelection(selectionStart, selectionEnd);
        // Toggle flag and show indicator
        mIsShowingPwd = !mIsShowingPwd;
        showPasswordVisibilityIndicator(true);
    }

    //hide it
    private void maskPassword() {
        setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    //make it visible
    private void unmaskPassword() {
        setTransformationMethod(null);
    }

    @Override
    public void tint() {
        initView();
    }

    /**
     * 右图标点击的回调
     */
    public interface OnRightClickListener {
        void onClick(EditText editText);
    }
}

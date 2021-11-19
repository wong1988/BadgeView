package io.github.wong1988.badge;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import io.github.wong1988.badge.attr.BadgeMax;

public class BadgeView extends AppCompatTextView {

    // view的高度
    private int mHeight;
    // 用户设置的padding
    private int mPaddingTop;
    private int mPaddingBottom;
    private int mPaddingLeft;
    private int mPaddingRight;
    // 是否是圆点
    private boolean mIsDot;
    // 最大值的样式
    private BadgeMax mBadgeMax;
    // 原文
    private String mOriginalText;

    public BadgeView(@NonNull Context context) {
        this(context, null);
    }

    public BadgeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BadgeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取自定义数据
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BadgeView);
        int style = ta.getInt(R.styleable.BadgeView_badge_style, 0);
        setDotStyle(style != 0);
        int maxEnum = ta.getInt(R.styleable.BadgeView_badge_max, 0);
        BadgeMax temp = (maxEnum == 0 ? BadgeMax.MAX_99 : BadgeMax.MAX_999);
        setBadgeMax(temp);
        ta.recycle();

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (oldh != h && h != mHeight) {
            mHeight = h;
            post(new Runnable() {
                @Override
                public void run() {
                    // 当高度发生变化时，重设最小宽度，
                    // 一般为设置textSize会发生改变
                    setMinWidth(mHeight);
                }
            });
        }

    }

    @Override
    public final void setVisibility(int visibility) {
        // 由内部控制，不允许外部设置
    }

    @Override
    public final void setSingleLine(boolean singleLine) {
        // 强制设置单行
        super.setSingleLine(true);
    }

    @Override
    public final void setSingleLine() {
        // 由内部控制，不允许外部设置
    }

    @Override
    public final void setGravity(int gravity) {
        // 强制设置居中
        super.setGravity(Gravity.CENTER);
    }


    @Override
    public final void setPadding(int left, int top, int right, int bottom) {

        // 重写setPadding方法
        mPaddingTop = top;
        mPaddingBottom = bottom;
        mPaddingLeft = left;
        mPaddingRight = right;

        String s = getText().toString();

        if (s.length() == 1) {
            super.setPadding(0, mPaddingTop, 0, mPaddingBottom);
        } else if (s.length() > 1) {
            super.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
        }
    }


    @Override
    public void setText(CharSequence str, BufferType type) {

        if (mBadgeMax == null) {
            // super(context, attrs, defStyleAttr)
            // 会调用此方法，会导致mBadgeMax还未赋值
            // 先设置默认值，获取到真正的值后在进行重新setText
            mBadgeMax = BadgeMax.MAX_99;

            // 获取用户设置的padding
            mPaddingTop = getPaddingTop();
            mPaddingBottom = getPaddingBottom();
            mPaddingLeft = getPaddingLeft();
            mPaddingRight = getPaddingRight();
            // 文字居中
            setGravity(Gravity.CENTER);
            // 单行
            setSingleLine(true);
            // 初始化默认背景
            if (getBackground() == null)
                setBackground(getNormalDrawable());
        }

        // 如果text不为空检查text是否合法

        if (!TextUtils.isEmpty(str)) {

            try {

                // 转换成数字类型
                long l = Long.parseLong(str.toString());

                if (mIsDot) {
                    // 既然是圆点类型，能格式化为数字，肯定不符合规范
                    // 赋值 并重新设置
                    mOriginalText = str.toString();
                    super.setText(l > 0 ? " " : "", type);

                    // 动态设置
                    super.setVisibility(l > 0 ? VISIBLE : GONE);
                    super.setPadding(l > 0 ? 0 : mPaddingLeft, mPaddingTop, l > 0 ? 0 : mPaddingRight, mPaddingBottom);
                    return;
                } else {
                    // 可输入最大值
                    int max = (mBadgeMax == BadgeMax.MAX_999 ? 999 : 99);

                    if (l > max) {
                        // 超出用户设置的最大值，不符合规范
                        // 赋值 并重新设置
                        mOriginalText = str.toString();
                        super.setText(mBadgeMax == BadgeMax.MAX_999 ? "999+" : "99+", type);

                        // 动态设置
                        super.setVisibility(VISIBLE);
                        super.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
                        return;
                    } else if (l <= 0) {
                        // 小于零，也不符合规范
                        // 赋值 并重新设置
                        mOriginalText = str.toString();
                        super.setText("", type);
                        // 动态设置
                        super.setVisibility(GONE);
                        return;
                    }
                }

            } catch (NumberFormatException e) {
                // 非数字类型
                if (mIsDot) {
                    // 圆点类型，不支持非数字
                    mOriginalText = str.toString();
                    super.setText("", type);
                    // 动态设置
                    super.setVisibility(GONE);
                    return;
                }
            }

        }

        super.setText(str, type);

        // 动态设置
        super.setVisibility(getText().toString().length() > 0 ? VISIBLE : GONE);
        super.setPadding(getText().toString().length() == 1 ? 0 : mPaddingLeft, mPaddingTop, getText().toString().length() == 1 ? 0 : mPaddingRight, mPaddingBottom);
        mOriginalText = TextUtils.isEmpty(str) ? null : str.toString();
    }

    // 默认的drawable
    private GradientDrawable getNormalDrawable() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.parseColor("#fd393d"));
        drawable.setStroke(2, Color.parseColor("#fae1e4"));
        drawable.setCornerRadius(5000);
        return drawable;
    }

    /**
     * 设置圆点模式
     */
    public final void setDotStyle(boolean dot) {

        if (mIsDot == dot)
            return;

        this.mIsDot = dot;
        setText(mOriginalText);
    }

    /**
     * 设置最大值
     */
    public final void setBadgeMax(BadgeMax max) {
        if (max != null && max != mBadgeMax) {
            mBadgeMax = max;
            setText(mOriginalText);
        }
    }
}
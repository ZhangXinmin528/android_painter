package com.example.android_painter.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout.Alignment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

import static android.graphics.BlurMaskFilter.Blur;

/**
 * Created by ZhangXinmin on 2018/1/16.
 * Copyright (c) 2018 . All rights reserved.
 * Easy way to set {@link SpannableStringBuilder} for {@link android.widget.TextView}.
 */
public final class SpanUtils {

    private SpanUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取建造者
     *
     * @param context
     * @param initialText    样式字符串文本
     * @param isApplyInitial
     * @return {@link Builder}
     */
    public static Builder getBuilder(@NonNull Context context, @NonNull CharSequence initialText, @NonNull boolean isApplyInitial) {
        return new Builder(context, initialText, isApplyInitial);
    }

    public static class Builder {

        private Context mContext;
        private int defaultValue = 0x12000000;
        // initial text content
        private CharSequence initialText;
        //append text content
        private CharSequence appendText;

        private int flag;
        @ColorInt
        private int foregroundColor;
        @ColorInt
        private int backgroundColor;
        @ColorInt
        private int quoteColor;

        private boolean isLeadingMargin;
        /**
         * the indent for the first line of the paragraph
         * 首行偏移量
         */
        private int first;
        /**
         * the indent for the remaining lines of the paragraph
         * 其余行偏移量
         */
        private int rest;

        private boolean isBullet;
        /**
         * 指示圆点与文字间距
         */
        private int gapWidth;
        private int bulletColor;

        //文字缩放比例
        private float proportion;
        private float xProportion;
        //state of strikethrough
        private boolean isStrikethrough;
        //state of underline
        private boolean isUnderline;
        private boolean isSuperscript;
        private boolean isSubscript;
        private boolean isBold;
        private boolean isItalic;
        private boolean isBoldItalic;
        private String fontFamily;
        private Alignment align;

        private boolean imageIsBitmap;
        private Bitmap bitmap;
        private boolean imageIsDrawable;
        private Drawable drawable;
        private boolean imageIsUri;
        private Uri uri;
        private boolean imageIsResourceId;
        @DrawableRes
        private int resourceId;

        private ClickableSpan clickSpan;
        private String url;

        private boolean isBlur;
        private float radius;
        private Blur style;
        private boolean isInitial;
        //初始使用样式
        private boolean isApplyInitial;

        private int mType;
        private final int mTypeCharSequence = 0;
        private final int mTypeImage = 1;
        private final int mTypeSpace = 2;

        private SpannableStringBuilder mSpanBuilder;

        private Builder(@NonNull Context context, @NonNull CharSequence initialText,
                        @NonNull boolean isApplyInitial) {
            mContext = context;
            this.initialText = initialText;
            this.isApplyInitial = isApplyInitial;

            flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
            foregroundColor = defaultValue;
            backgroundColor = defaultValue;
            quoteColor = defaultValue;
            proportion = -1;
            xProportion = -1;
            isInitial = true;
            mSpanBuilder = new SpannableStringBuilder();
        }

        /**
         * 设置标识
         *
         * @param flag <ul>
         *             <li>{@link Spanned#SPAN_INCLUSIVE_EXCLUSIVE}</li>
         *             <li>{@link Spanned#SPAN_INCLUSIVE_INCLUSIVE}</li>
         *             <li>{@link Spanned#SPAN_EXCLUSIVE_EXCLUSIVE}</li>
         *             <li>{@link Spanned#SPAN_EXCLUSIVE_INCLUSIVE}</li>
         *             </ul>
         * @return {@link Builder}
         */
        public Builder setFlag(int flag) {
            this.flag = flag;
            return this;
        }

        /**
         * 设置前景色:文字颜色
         *
         * @param color 前景色
         * @return {@link Builder}
         */
        public Builder setTextColor(@ColorInt int color) {
            this.foregroundColor = color;
            return this;
        }

        /**
         * 设置背景色
         *
         * @param color 背景色
         * @return {@link Builder}
         */
        public Builder setBackgroundColor(@ColorInt int color) {
            this.backgroundColor = color;
            return this;
        }

        /**
         * 设置引用线的颜色，会导致换行
         *
         * @param color 引用线的颜色
         * @return {@link Builder}
         */
        public Builder setQuoteColor(@ColorInt int color) {
            this.quoteColor = color;
            return this;
        }

        /**
         * 设置缩进
         *
         * @param first 首行缩进
         * @param rest  剩余行缩进
         * @return {@link Builder}
         */
        public Builder setLeadingMargin(@IntRange(from = 0) int first, @IntRange(from = 0) int rest) {
            this.first = first;
            this.rest = rest;
            isLeadingMargin = true;
            return this;
        }

        /**
         * 设置列表标记
         * <p>
         * 若 isApplyInitial = false, it's ineffective;
         * </p>
         *
         * @param gapWidth 列表标记和文字间距离
         * @param color    列表标记的颜色
         * @return {@link Builder}
         */
        public Builder setBullet(@IntRange(from = 0) int gapWidth, @ColorInt int color) {
            this.gapWidth = gapWidth;
            bulletColor = color;
            isBullet = true;
            return this;
        }

        /**
         * 设置字体比例:
         * 指的是相对于设置的字号的比例
         *
         * @param proportion 比例
         * @return {@link Builder}
         */
        public Builder setProportion(@FloatRange(from = 0, fromInclusive = false) float proportion) {
            this.proportion = proportion;
            return this;
        }

        /**
         * 设置字体横向比例
         *
         * @param proportion 比例
         * @return {@link Builder}
         */
        public Builder setXProportion(@FloatRange(from = 0, fromInclusive = false) float proportion) {
            this.xProportion = proportion;
            return this;
        }

        /**
         * 设置删除线
         *
         * @return {@link Builder}
         */
        public Builder setStrikethrough() {
            this.isStrikethrough = true;
            return this;
        }

        /**
         * 设置下划线
         *
         * @return {@link Builder}
         */
        public Builder setUnderline() {
            this.isUnderline = true;
            return this;
        }

        /**
         * 设置上标
         *
         * @return {@link Builder}
         */
        public Builder setSuperscript() {
            this.isSuperscript = true;
            return this;
        }

        /**
         * 设置下标
         *
         * @return {@link Builder}
         */
        public Builder setSubscript() {
            this.isSubscript = true;
            return this;
        }

        /**
         * 设置粗体
         *
         * @return {@link Builder}
         */
        public Builder setBold() {
            isBold = true;
            return this;
        }

        /**
         * 设置斜体
         *
         * @return {@link Builder}
         */
        public Builder setItalic() {
            isItalic = true;
            return this;
        }

        /**
         * 设置粗斜体
         *
         * @return {@link Builder}
         */
        public Builder setBoldItalic() {
            isBoldItalic = true;
            return this;
        }

        /**
         * 设置字体
         *
         * @param fontFamily 字体
         *                   <ul>
         *                   <li>monospace</li>
         *                   <li>serif</li>
         *                   <li>sans-serif</li>
         *                   </ul>
         * @return {@link Builder}
         */
        public Builder setFontFamily(@Nullable String fontFamily) {
            this.fontFamily = fontFamily;
            return this;
        }

        /**
         * 设置对齐
         *
         * @param align 对其方式
         *              <ul>
         *              <li>{@link Alignment#ALIGN_NORMAL}正常</li>
         *              <li>{@link Alignment#ALIGN_OPPOSITE}相反</li>
         *              <li>{@link Alignment#ALIGN_CENTER}居中</li>
         *              </ul>
         * @return {@link Builder}
         */
        public Builder setAlign(@Nullable Alignment align) {
            this.align = align;
            return this;
        }

        /**
         * 设置图片
         *
         * @param bitmap 图片位图
         * @return {@link Builder}
         * @hide
         */
        private Builder setBitmap(@NonNull Bitmap bitmap) {
            this.bitmap = bitmap;
            imageIsBitmap = true;
            return this;
        }

        /**
         * 设置图片
         *
         * @param drawable 图片资源
         * @return {@link Builder}
         * @hide
         */
        private Builder setDrawable(@NonNull Drawable drawable) {
            this.drawable = drawable;
            imageIsDrawable = true;
            return this;
        }

        /**
         * 设置图片
         *
         * @param uri 图片uri
         * @return {@link Builder}
         * @hide
         */
        private Builder setUri(@NonNull Uri uri) {
            this.uri = uri;
            imageIsUri = true;
            return this;
        }

        /**
         * 设置图片
         *
         * @param resourceId 图片资源id
         * @return {@link Builder}
         */
        public Builder setResourceId(@DrawableRes int resourceId) {
            this.resourceId = resourceId;
            imageIsResourceId = true;
            return this;
        }

        /**
         * 设置点击事件
         * <p>需添加view.setMovementMethod(LinkMovementMethod.getInstance())</p>
         * <p>使用之前需要先使用{@link }</p>
         *
         * @param clickSpan 点击事件
         * @return {@link Builder}中的 append方法添加想要点击的文字。
         */
        public Builder setClickSpan(@NonNull ClickableSpan clickSpan) {

            if (!TextUtils.isEmpty(appendText)) {
                int start = mSpanBuilder.length() - appendText.length();
                int end = mSpanBuilder.length();
                mSpanBuilder.setSpan(clickSpan, start, end, flag);
            }
            return this;
        }

        /**
         * 设置超链接
         * <p>需添加view.setMovementMethod(LinkMovementMethod.getInstance())</p>
         *
         * @param url 超链接
         * @return {@link Builder}
         */
        public Builder setUrl(@NonNull String url) {
            this.url = url;
            return this;
        }

        /**
         * 设置模糊
         * <p>尚存bug，其他地方存在相同的字体的话，相同字体出现在之前的话那么就不会模糊，出现在之后的话那会一起模糊</p>
         * <p>推荐还是把所有字体都模糊这样使用</p>
         * <p>以上bug关闭硬件加速即可</p>
         *
         * @param radius 模糊半径（需大于0）
         * @param style  模糊样式<ul>
         *               <li>{@link Blur#NORMAL}</li>
         *               <li>{@link Blur#SOLID}</li>
         *               <li>{@link Blur#OUTER}</li>
         *               <li>{@link Blur#INNER}</li>
         *               </ul>
         * @return {@link Builder}
         */
        public Builder setBlur(float radius, Blur style) {
            this.radius = radius;
            this.style = style;
            this.isBlur = true;
            return this;
        }

        /**
         * 追加样式字符串
         *
         * @param appendText 样式字符串文本
         * @param isApply    追加文字是否应用之前样式
         * @return {@link Builder}
         */
        public Builder append(@NonNull CharSequence appendText, @NonNull boolean isApply) {
            //应用初始样式
            if (isInitial) {
                setSpan(isApplyInitial);
            }

            this.appendText = appendText;
            setSpan(isApply);
            return this;
        }

        /**
         * 创建样式字符串
         *
         * @return 样式字符串
         */
        public SpannableStringBuilder create() {
            //应用初始样式
            if (isInitial) {
                setSpan(isApplyInitial);
            }
            return mSpanBuilder;
        }

        /**
         * 设置样式
         *
         * @param isApply 是否应用样式
         */
        private void setSpan(boolean isApply) {
            int start = mSpanBuilder.length();
            if (isInitial) {
                mSpanBuilder.append(this.initialText);
                isInitial = false;
            } else {
                mSpanBuilder.append(this.appendText);
            }
            int end = mSpanBuilder.length();

            if (isApply) {

                if (foregroundColor != defaultValue) {
                    mSpanBuilder.setSpan(new ForegroundColorSpan(foregroundColor), start, end, flag);
                }
                if (backgroundColor != defaultValue) {
                    mSpanBuilder.setSpan(new BackgroundColorSpan(backgroundColor), start, end, flag);
                }
                if (isLeadingMargin) {
                    mSpanBuilder.setSpan(new LeadingMarginSpan.Standard(first, rest), start, end, flag);
                }
                if (quoteColor != defaultValue) {
                    mSpanBuilder.setSpan(new QuoteSpan(quoteColor), start, end, 0);
                }
                if (isBullet) {
                    mSpanBuilder.setSpan(new BulletSpan(gapWidth, bulletColor), start, end, 0);
                }
                if (proportion != -1) {
                    mSpanBuilder.setSpan(new RelativeSizeSpan(proportion), start, end, flag);
                }
                if (xProportion != -1) {
                    mSpanBuilder.setSpan(new ScaleXSpan(xProportion), start, end, flag);
                }
                if (isStrikethrough) {
                    mSpanBuilder.setSpan(new StrikethroughSpan(), start, end, flag);
                }
                if (isUnderline) {
                    mSpanBuilder.setSpan(new UnderlineSpan(), start, end, flag);
                }
                if (isSuperscript) {
                    mSpanBuilder.setSpan(new SuperscriptSpan(), start, end, flag);
                }
                if (isSubscript) {
                    mSpanBuilder.setSpan(new SubscriptSpan(), start, end, flag);
                }
                if (isBold) {
                    mSpanBuilder.setSpan(new StyleSpan(Typeface.BOLD), start, end, flag);
                }
                if (isItalic) {
                    mSpanBuilder.setSpan(new StyleSpan(Typeface.ITALIC), start, end, flag);
                }
                if (isBoldItalic) {
                    mSpanBuilder.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), start, end, flag);
                }
                if (fontFamily != null) {
                    mSpanBuilder.setSpan(new TypefaceSpan(fontFamily), start, end, flag);
                }
                if (align != null) {
                    mSpanBuilder.setSpan(new AlignmentSpan.Standard(align), start, end, flag);
                }

                if (imageIsBitmap || imageIsDrawable || imageIsUri || imageIsResourceId) {
                    if (imageIsBitmap) {
                        mSpanBuilder.setSpan(new ImageSpan(mContext, bitmap), start, end, flag);
                        bitmap = null;
                    } else if (imageIsDrawable) {
                        mSpanBuilder.setSpan(new ImageSpan(drawable), start, end, flag);
                        drawable = null;
                    } else if (imageIsUri) {
                        mSpanBuilder.setSpan(new ImageSpan(mContext, uri), start, end, flag);
                        uri = null;
                    } else {
                        mSpanBuilder.setSpan(new ImageSpan(mContext, resourceId), start, end, flag);
                        resourceId = 0;
                    }
                }

                if (url != null) {
                    mSpanBuilder.setSpan(new URLSpan(url), start, end, flag);
                }
                if (isBlur) {
                    mSpanBuilder.setSpan(new MaskFilterSpan(new BlurMaskFilter(radius, style)), start, end, flag);
                }
            }

            flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

        }

    }
}
package com.soowin.staremblem.utils.myView;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;
import android.util.AttributeSet;

import com.soowin.staremblem.utils.PublicApplication;

/**
 * Created by hxt on 2018/3/13 0013.
 * 自定义文字view 汉字和英文分别使用不同字体文件
 */

public class MyTextView extends android.support.v7.widget.AppCompatTextView {
    private static final String TAG = MyTextView.class.getCanonicalName();

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        SpannableStringBuilder ssb = null;
        if (text != null) {
            if (text.length() > 0)
                ssb = new SpannableStringBuilder(text);
            for (int i = 0; i < text.length(); i++) {
                String s = text.toString();
                String w = s.substring(i, i + 1);
                if (isEnglish(w)) {//判断是否为英文
                    ssb.setSpan(new CustomTypefaceSpan(w, PublicApplication.typefaceE), i, i + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                } else if (isChinese(w)) {
                    ssb.setSpan(new CustomTypefaceSpan(w, PublicApplication.typefaceC), i, i + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                } else {
                    ssb.setSpan(new TypefaceSpan(w), i, i + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                }
            }
        }
        super.setText(ssb, type);
    }

    public static boolean isEnglish(String s) {
        char c = s.charAt(0);
        int i = (int) c;
        if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isChinese(String s) {
        char c = s.charAt(0);
        int i = (int) c;
        if ((i >= 0x4e00) && (i <= 0x9fbb)) {
            return true;
        } else if (i >= 48 && i <= 57) {
            return true;
        } else {
            return false;
        }
    }


    public class CustomTypefaceSpan extends TypefaceSpan {
        private final Typeface newType;

        public CustomTypefaceSpan(String family, Typeface type) {
            super(family);
            newType = type;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            applyCustomTypeFace(ds, newType);
        }

        @Override
        public void updateMeasureState(TextPaint paint) {
            applyCustomTypeFace(paint, newType);
        }

        private void applyCustomTypeFace(Paint paint, Typeface tf) {
            /* int oldStyle;
           Typeface old = paint.getTypeface();
            if (old == null) {
                oldStyle = 0;
            } else {
                oldStyle = old.getStyle();
            } int fake = oldStyle & ~tf.getStyle();
            if ((fake & Typeface.BOLD) != 0) {
                paint.setFakeBoldText(true);
            }

            if ((fake & Typeface.ITALIC) != 0) {
                paint.setTextSkewX(-0.25f);
            }*/

            paint.setTypeface(tf);
        }
    }
}

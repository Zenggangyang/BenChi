package com.soowin.staremblem.utils;

import android.content.Context;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.soowin.staremblem.R;
import com.soowin.staremblem.utils.myView.EdittextCustomTypefaceSpan;

/**
 * Created by Administrator on 2018/3/23.
 */

public class EditTextUtils {

    public static void init(EditText editText, ChangeCall changeCall) {
        initEditText(editText, EditChange.HINT);
        editText.addTextChangedListener(new EditTextChangedListener(editText, changeCall));
    }

    public static void init2(EditText[] editText, ChangeCall[] calls) {
        if (editText.length > 0) {
            for (int i = 0; i < editText.length; i++) {
                init(editText[i], calls[i]);
            }
        }
    }

    public static void init(EditText[] editText, ChangeCall calls) {
        if (editText.length > 0) {
            for (int i = 0; i < editText.length; i++) {
                init(editText[i], calls);
            }
        }
    }

    private static void initEditText(EditText editText, EditChange editChange) {
        String string = null;
        switch (editChange) {
            case HINT:
                string = editText.getHint().toString();
                break;
            case TEXT:
                string = editText.getText().toString();
                break;
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder(string);
        for (int i = 0; i < string.length(); i++) {
            String s = string.toString();
            String w = s.substring(i, i + 1);
            if (isEnglish(w)) {//判断是否为英文
                ssb.setSpan(new EdittextCustomTypefaceSpan(w, PublicApplication.typefaceE), i, i + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            } else {
                ssb.setSpan(new EdittextCustomTypefaceSpan(w, PublicApplication.typefaceC), i, i + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }
        }
        switch (editChange) {
            case HINT:
                editText.setHint(ssb);
                break;
            case TEXT:
                editText.setText(ssb);

                break;
        }

    }

    private static boolean isEnglish(String s) {
        char c = s.charAt(0);
        int i = (int) c;
        if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断EditText是否为空
     * true:有空值， false：无空值
     * @param editTexts  输入框数组
     * @param textViews  提示的TextView数组
     * @param mContext
     * @return
     */
    public static boolean isEmpty(EditText[] editTexts, TextView[] textViews, Context mContext) {
        boolean isEmpty = true;
        for (int i = 0; i < editTexts.length; i++) {
            if (TextUtils.isEmpty(editTexts[i].getText().toString())) {
                isEmpty = true;
                textViews[i].setVisibility(View.VISIBLE);
                textViews[i].setText(mContext.getResources().getString(R.string.app_transfer_prompt_input));
            } else {
                isEmpty = false;
                textViews[i].setVisibility(View.INVISIBLE);
            }
        }
        return isEmpty;
    }

    /**
     * 判断TextView是否填写（选择）
     * true:有空值， false：无空值
     * @param textViews  输入框数组
     * @param hintTextViews  提示的TextView数组
     * @param hintTexts  提示的文字数组
     * @return
     */
    public static boolean isEmpty2(TextView[] textViews,TextView[] hintTextViews, String[] hintTexts,Context mContext) {
        boolean isEmpty = true;
        for (int i = 0; i < textViews.length; i++) {
            if (textViews[i].getText().toString().equals(hintTexts[i].toString())){
                //未填写
                isEmpty = true;
                hintTextViews[i].setVisibility(View.VISIBLE);
                hintTextViews[i].setText(mContext.getResources().getString(R.string.app_transfer_prompt_input));
            } else {
                isEmpty = false;
                hintTextViews[i].setVisibility(View.GONE);
            }
        }
        return isEmpty;
    }


    enum EditChange {
        HINT, TEXT;
    }

    public interface ChangeCall {
        void onTextChanged();
    }

    static class EditTextChangedListener implements TextWatcher {
        EditText editText;
        ChangeCall changeCall;

        public EditTextChangedListener(EditText editText, ChangeCall changeCall) {
            this.editText = editText;
            this.changeCall = changeCall;
        }

        public void afterTextChanged(Editable s) {

        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence text, int start, int before, int count) {

            editText.removeTextChangedListener(this);
            initEditText(editText, EditChange.TEXT);
            editText.setSelection(editText.length());
            editText.addTextChangedListener(this);

            if (changeCall != null) {
                changeCall.onTextChanged();
            }
        }
    }

}

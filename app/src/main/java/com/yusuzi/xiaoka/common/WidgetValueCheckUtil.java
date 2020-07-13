package com.yusuzi.xiaoka.common;

import android.widget.EditText;

public class WidgetValueCheckUtil {

    public static String getEditValue (EditText editText) {
        if(editText == null) {
            return "";
        }
        if(editText.getText() == null) {
            return "";
        }
        return editText.getText().toString();
    }
}

package com.brsatalay.projectbase.library.core.data.model;

import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;

/**
 * Created by barisatalay on 10.04.2018.
 */

public class mdlDialog {
    private String title;

    private String description;

    private String buttonTitle;

    private boolean isHtml = false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Spanned getDescription() {
        if(isHtml)
            return textToHtml(description);
        else
            return new SpannableString(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getButtonTitle() {
        return buttonTitle;
    }

    public void setButtonTitle(String buttonTitle) {
        this.buttonTitle = buttonTitle;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public void setHtml(boolean html) {
        isHtml = html;
    }

    public static Spanned textToHtml(String pValue){
        if (android.os.Build.VERSION.SDK_INT < 24){
            return Html.fromHtml(pValue);
        } else{
            return Html.fromHtml(pValue, Html.FROM_HTML_MODE_COMPACT);
        }
    }
}

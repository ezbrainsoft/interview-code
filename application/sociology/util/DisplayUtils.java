package com.jlesoft.civilservice.koreanhistoryexam9.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.crashlytics.android.Crashlytics;

public class DisplayUtils {

    private static ProgressDialog mProgressDialog = null;

    public static int dp2px(Context context, int dp){
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return px;
    }

    public static void showProgressDialog(Activity activity, String message) {
        try{
            View focusView = activity.getCurrentFocus();
            if(focusView != null) {
                ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(focusView.getWindowToken(), 0);
            }

            hideProgressDialog();
            mProgressDialog = new ProgressDialog(new ContextThemeWrapper(activity, android.R.style.Theme_Holo_Light));
            mProgressDialog.setMessage(message);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

        }catch (Exception e){
        }

    }

    public static void showProgressDialog(Activity activity) {
        showProgressDialog(activity, "");
    }

    public static void hideProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                mProgressDialog = null;
            }
        } catch (Exception e) {
            mProgressDialog = null;
        }
    }
}

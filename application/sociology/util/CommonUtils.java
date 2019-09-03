package com.jlesoft.civilservice.koreanhistoryexam9.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import com.jlesoft.civilservice.koreanhistoryexam9.everydayEnglish.EverydayTTSService;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api110GetDownloadTodayTen;
import com.jlesoft.civilservice.koreanhistoryexam9.model.ApiGetReviewAndRefineQuestion;
import com.jlesoft.civilservice.koreanhistoryexam9.model.ConfirmQuestionJimun;
import com.jlesoft.civilservice.koreanhistoryexam9.model.DanwonStudyQuestionJimun;
import com.jlesoft.civilservice.koreanhistoryexam9.model.DayStudyQuestionJimun;
import com.jlesoft.civilservice.koreanhistoryexam9.model.HistoryQuestionJimun;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api98GetDownloadTodayFive;
import com.jlesoft.civilservice.koreanhistoryexam9.model.subject.SubjectQuestionJimun;
import com.jlesoft.civilservice.koreanhistoryexam9.word.WordTTSService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class CommonUtils {

    /*
    * 어휘학습 TTS 서비스가 현재 진행중인지 종료중인지 여부를 묻는 메소드
    * */
    public static boolean isServiceRunning(Context context){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (TTSService.class.getName().equals(service.service.getClassName()))
                return true;
        }
        return false;
    }

    /*
    * 생활영어 TTS 서비스가 현재 진행중인지 종료중인지 여부를 묻는 메소드
    * */
    public static boolean isEverydayServiceRunning(Context context){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (EverydayTTSService.class.getName().equals(service.service.getClassName()))
                return true;
        }
        return false;
    }

    /*
     * 국어 어휘학습 TTS 서비스가 현재 진행중인지 종료중인지 여부를 묻는 메소드
     * */
    public static boolean isKoeanServiceRunning(Context context){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (WordTTSService.class.getName().equals(service.service.getClassName()))
                return true;
        }
        return false;
    }

    public static final SimpleDateFormat DATE_HHmmssSSS = new SimpleDateFormat("HH:mm:ss.SSS");

    public static boolean getConnectNetwork(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    /*
    * 현재 시간을 아래 형식에 맞쳐서 보낸다.
    * */
    private static SimpleDateFormat chFormat = new SimpleDateFormat("MM월dd일\n HH시mm분");

    public static String formatNowToDate() {
        Calendar nowTime = Calendar.getInstance();
        return chFormat.format(nowTime.getTime());
    }

    public static String formatStringToDate(String strDate){//"04월26일\n오후 06시35분"
        SimpleDateFormat transFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = transFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static int getAppVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pInfo = null;

        try {
            pInfo = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }

        String currentVersion = pInfo.versionName;

        return Integer.parseInt(currentVersion.replace(".", ""));
    }

    public static String getOriginalAppVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pInfo = null;

        try {
            pInfo = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }

        return pInfo.versionName;
    }

    public static int dpToPx(Context context, int dp){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
    }

    public static float pxToDp(Context context, int px){
        float density = context.getResources().getDisplayMetrics().density;

       DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
       int dp = Math.round(px/(displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)) ;
       return dp;
    }

    public static int convertDP(Context context, int px) {
        Resources r = context.getResources();
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, r.getDisplayMetrics());
    }

    public static int getDimensValue(Context context, int dimensID) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(dimensID), dm);

        return result;
    }

    public static double getDensity(Context context) {

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager mgr = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        mgr.getDefaultDisplay().getMetrics(metrics);

        return metrics.densityDpi;

    }

    /**
     * 화면 dp size
     */
    public static int[] getScreenWidthHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int[] result = new int[2];
        result[0] = size.x;
        result[1] = size.y;

        return result;
    }

    /**
     * 오름차순
     * 서버에서 받아온 지문을 문제풀이 전에 before_order 값 기준으로 정렬해준다.
     *
     */
    public static class JimunAscBeforeStudy implements Comparator<DayStudyQuestionJimun> {
        @Override
        public int compare(DayStudyQuestionJimun p1, DayStudyQuestionJimun p2) {
            if (p1.getOrderNumBeforeStudy() > p2.getOrderNumBeforeStudy()) return 1;
            if (p1.getOrderNumBeforeStudy() < p2.getOrderNumBeforeStudy()) return -1;
            return 0;
        }
    }



    /**
     * 오름차순
     * 서버에서 받아온 지문을 문제풀이 후에 after_order 값 기준으로 정렬해준다.
     * 심화학습 B타입일 경우 문제풀이 후에는 ㄱ-ㄴ-ㄷ-ㄹ가 아닌, 연도 순으로 보여줘야 해서 필요하다.
     *
     */

    public static class DayQuizAfterStudy implements Comparator<Api110GetDownloadTodayTen.Jimun> {
        @Override
        public int compare(Api110GetDownloadTodayTen.Jimun p1, Api110GetDownloadTodayTen.Jimun p2) {
            if (p1.getOrderNumAfterStudy() > p2.getOrderNumAfterStudy()) return 1;
            if (p1.getOrderNumAfterStudy() < p2.getOrderNumAfterStudy()) return -1;
            return 0;
        }
    }

    public static class JimunAscAfterStudy implements Comparator<DayStudyQuestionJimun> {
        @Override
        public int compare(DayStudyQuestionJimun p1, DayStudyQuestionJimun p2) {
            if (p1.getOrderNumAfterStudy() > p2.getOrderNumAfterStudy()) return 1;
            if (p1.getOrderNumAfterStudy() < p2.getOrderNumAfterStudy()) return -1;
            return 0;
        }
    }

    public static class JimunAscConfirmQuestion implements Comparator<ConfirmQuestionJimun> {
        @Override
        public int compare(ConfirmQuestionJimun p1, ConfirmQuestionJimun p2) {
            if (p1.getIpaOrder() > p2.getIpaOrder()) return 1;
            if (p1.getIpaOrder() < p2.getIpaOrder()) return -1;
            return 0;
        }
    }

    public static class JimunAscHistoryQuestion implements Comparator<HistoryQuestionJimun> {
        @Override
        public int compare(HistoryQuestionJimun p1, HistoryQuestionJimun p2) {
            if (p1.getIpaOrder() > p2.getIpaOrder()) return 1;
            if (p1.getIpaOrder() < p2.getIpaOrder()) return -1;
            return 0;
        }
    }

    /**
     * 오름차순
     * 서버에서 받아온 선지 리스트를 before_order 값 기준으로 정렬해준다.
     * 심화학습 D타입일 경우
     *
     */
    public static class SunjiAsc implements Comparator<ApiGetReviewAndRefineQuestion.ApiSunjiList> {
        @Override
        public int compare(ApiGetReviewAndRefineQuestion.ApiSunjiList p1, ApiGetReviewAndRefineQuestion.ApiSunjiList p2) {
            if (p1.getOrderNum() > p2.getOrderNum()) return 1;
            if (p1.getOrderNum() < p2.getOrderNum()) return -1;
            return 0;
        }
    }


    /**
     * 오름차순
     * 서버에서 받아온 지문을 문제풀이 전에 before_order 값 기준으로 정렬해준다.
     *
     */
    public static class DanwonJimunAscBeforeStudy implements Comparator<DanwonStudyQuestionJimun> {
        @Override
        public int compare(DanwonStudyQuestionJimun p1, DanwonStudyQuestionJimun p2) {
            if (p1.getOrderNumBeforeStudy() > p2.getOrderNumBeforeStudy()) return 1;
            if (p1.getOrderNumBeforeStudy() < p2.getOrderNumBeforeStudy()) return -1;
            return 0;
        }
    }
    public static class SubjectJimunAscBeforeStudy implements Comparator<SubjectQuestionJimun> {
        @Override
        public int compare(SubjectQuestionJimun p1, SubjectQuestionJimun p2) {
            if (p1.getOrderNumBeforeStudy() > p2.getOrderNumBeforeStudy()) return 1;
            if (p1.getOrderNumBeforeStudy() < p2.getOrderNumBeforeStudy()) return -1;
            return 0;
        }
    }

    /**
     * 오름차순
     * 서버에서 받아온 지문을 문제풀이 후에 after_order 값 기준으로 정렬해준다.
     * 심화학습 B타입일 경우 문제풀이 후에는 ㄱ-ㄴ-ㄷ-ㄹ가 아닌, 연도 순으로 보여줘야 해서 필요하다.
     *
     */



    public static class DanwonJimunAscAfterStudy implements Comparator<DanwonStudyQuestionJimun> {
        @Override
        public int compare(DanwonStudyQuestionJimun p1, DanwonStudyQuestionJimun p2) {
            if (p1.getOrderNumAfterStudy() > p2.getOrderNumAfterStudy()) return 1;
            if (p1.getOrderNumAfterStudy() < p2.getOrderNumAfterStudy()) return -1;
            return 0;
        }
    }

    public static class SubjectJimunAscAfterStudy implements Comparator<SubjectQuestionJimun> {
        @Override
        public int compare(SubjectQuestionJimun p1, SubjectQuestionJimun p2) {
            if (p1.getOrderNumAfterStudy() > p2.getOrderNumAfterStudy()) return 1;
            if (p1.getOrderNumAfterStudy() < p2.getOrderNumAfterStudy()) return -1;
            return 0;
        }
    }

    public static class DanwonJimunAscConfirmQuestion implements Comparator<ConfirmQuestionJimun> {
        @Override
        public int compare(ConfirmQuestionJimun p1, ConfirmQuestionJimun p2) {
            if (p1.getIpaOrder() > p2.getIpaOrder()) return 1;
            if (p1.getIpaOrder() < p2.getIpaOrder()) return -1;
            return 0;
        }
    }

    /**
     * 오름차순
     * 서버에서 받아온 선지 리스트를 before_order 값 기준으로 정렬해준다.
     * 심화학습 D타입일 경우
     *
     */
    public static class DannwonSunjiAsc implements Comparator<ApiGetReviewAndRefineQuestion.ApiSunjiList> {
        @Override
        public int compare(ApiGetReviewAndRefineQuestion.ApiSunjiList p1, ApiGetReviewAndRefineQuestion.ApiSunjiList p2) {
            if (p1.getOrderNum() > p2.getOrderNum()) return 1;
            if (p1.getOrderNum() < p2.getOrderNum()) return -1;
            return 0;
        }
    }
}

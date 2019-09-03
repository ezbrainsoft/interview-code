package com.jlesoft.civilservice.koreanhistoryexam9.util;

import android.content.Context;
import android.graphics.Color;

import androidx.core.content.ContextCompat;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.widget.EditText;
import android.widget.TextView;

import com.jlesoft.civilservice.koreanhistoryexam9.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {

    public static final DecimalFormat MONEY_FORMAT_1 = new DecimalFormat("#,##0");

    public static final SimpleDateFormat DATE_FORAMT_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSsss");
    public static final SimpleDateFormat DATE_FORAMT_2 = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_FORAMT_3 = new SimpleDateFormat("yy.MM.dd");
    public static final SimpleDateFormat DATE_FORAMT_4 = new SimpleDateFormat("a hh:mm");

    public static boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            err = true;
        }
        return err;
    }

    public static boolean isNull(String str) {
        if (str == null || str.trim().equals("") || str.trim().toLowerCase().equals("none")) {
            return true;
        }
        return false;
    }

    public static boolean isNull(TextView view) {
        if (view == null) {
            return true;
        }
        return isNull(view.getText().toString());
    }

    public static boolean isNull(EditText view) {
        if (view == null) {
            return true;
        }

        return isNull(view.getText().toString());
    }

    public static boolean isNotNull(String str) {
        return !isNull(str);
    }

    public static boolean isNotNull(TextView view) {
        return !isNull(view);
    }

    public static boolean isNotNull(EditText view) {
        return !isNull(view);
    }

    public static String getString(EditText view) {

        if (isNull(view)) {
            return null;
        }

        return view.getText().toString().trim();
    }

    public static String getString(TextView view) {
        if (isNull(view)) {
            return null;
        }
        return view.getText().toString().trim();
    }

    /*
    한글 받침에 따라서 '을/를' 구분하기
    String name1=StringUtil.getComleteWordByJongsung(name,"을","를");
    String name2=StringUtil.getComleteWordByJongsung(name,"이","가");
    String name3=StringUtil.getComleteWordByJongsung(name,"은","는");

    * */
    public static final String getComleteWordByJongsung(String name, String firstValue, String secondValue) {
        char lastName = name.charAt(name.length() - 1);
        // 한글의 제일 처음과 끝의 범위밖일 경우는 오류
        if (lastName < 0xAC00 || lastName > 0xD7A3) {
            return name;
        }

        String seletedValue = (lastName - 0xAC00) % 28 > 0 ? firstValue : secondValue;
        return name + seletedValue;
    }

    public static final String getComleteWordByJosa(String word1, String word2, String firstValue, String secondValue) {
        char lastName1 = word1.charAt(word1.length() - 1);
        char lastName2 = word2.charAt(word2.length() - 1);

        // 한글의 제일 처음과 끝의 범위밖일 경우는 오류
        if (lastName1 < 0xAC00 || lastName1 > 0xD7A3) {
            return "";
        }

        String seletedValue1 = (lastName1 - 0xAC00) % 28 > 0 ? firstValue : secondValue;
        String seletedValue2 = (lastName2 - 0xAC00) % 28 > 0 ? firstValue : secondValue;

        if(!seletedValue1.equals(seletedValue2)) {
            return firstValue + "/" + secondValue;
        }else{
            return  "";
        }

    }

    public static int[] getCharactorPosition(String fullStr, String targetStr) {
        int[] result = new int[2];
        result[0] = fullStr.indexOf(targetStr);
        result[1] = result[0] + targetStr.length();

        return result;
    }

    public static String removeAllSpecialCharacter(String content) {

        content = content.replaceAll("\\{#\\}", "");
        content = content.replaceAll("\\{@", "");
        content = content.replaceAll("\\}", "");
        content = content.replaceAll("\\.", "");
        content = content.replaceAll("\\/", "");

        return content;
    }

    public static String removeSpecialCharacter(String content){
        content = content.replaceAll("\\{#\\}", "\n");
        content = content.replaceAll("\\{@", "");
        content = content.replaceAll("\\}", "");
        content = content.replaceAll("\\.", "");
        return content;
    }

    public static SpannableString replaceUnderlineSpan(Context context, String content) {
        ArrayList<Integer> sunjiPointStart = new ArrayList<>();
        ArrayList<Integer> sunjiPointEnd = new ArrayList<>();

        content = content.replaceAll("\\{#\\}", "\n");

        if (content.contains("{")) {
            int countMarkers = 0;
            int fromIndex = -1;
            while ((fromIndex = content.indexOf("{", fromIndex + 1)) >= 0) {
                countMarkers++;
            }

            for (int i = 0; i < countMarkers; i++) {
                sunjiPointStart.add(content.indexOf("{"));
                sunjiPointEnd.add(content.indexOf(("}")) - 2);

                content = content.replaceFirst("\\{@", "");
                content = content.replaceFirst("\\}", "");
            }
        }

        if ((sunjiPointEnd.size() == sunjiPointStart.size()) && (sunjiPointEnd.size() != 0)) {
            SpannableString sunjiSpannable = new SpannableString(content);
            for (int i = 0; i < sunjiPointEnd.size(); i++) {
                if (sunjiPointEnd.get(i) > sunjiPointStart.get(i)) {
                    sunjiSpannable.setSpan(new UnderlineSpan(), sunjiPointStart.get(i), sunjiPointEnd.get(i), 0);
                }
            }
            return sunjiSpannable;
        } else {
            return new SpannableString(content);
        }
    }

    public static SpannableString replaceSpecialCharacterColor(Context context, String content) {
        ArrayList<Integer> sunjiPointStart = new ArrayList<>();
        ArrayList<Integer> sunjiPointEnd = new ArrayList<>();

        content = content.replaceAll("\\{#\\}", "\n");

        if (content.contains("{")) {
            int countMarkers = 0;
            int fromIndex = -1;
            while ((fromIndex = content.indexOf("{", fromIndex + 1)) >= 0) {
                countMarkers++;
            }

            for (int i = 0; i < countMarkers; i++) {
                sunjiPointStart.add(content.indexOf("{"));
                sunjiPointEnd.add(content.indexOf(("}")) - 2);

                content = content.replaceFirst("\\{@", "");
                content = content.replaceFirst("\\}", "");
            }
        }

        if ((sunjiPointEnd.size() == sunjiPointStart.size()) && (sunjiPointEnd.size() != 0)) {
            SpannableString sunjiSpannable = new SpannableString(content);
            for (int i = 0; i < sunjiPointEnd.size(); i++) {
                if (sunjiPointEnd.get(i) > sunjiPointStart.get(i)) {
//                    sunjiSpannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.col_1491E8)), sunjiPointStart.get(i), sunjiPointEnd.get(i), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sunjiSpannable.setSpan(new ForegroundColorSpan(Color.parseColor("#1491E8")), sunjiPointStart.get(i), sunjiPointEnd.get(i), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            return sunjiSpannable;
        } else {
            return new SpannableString(content);
        }
    }

    public static SpannableString replaceSpecialCharacterColor(Context context, String
            content, String color) {
        ArrayList<Integer> sunjiPointStart = new ArrayList<>();
        ArrayList<Integer> sunjiPointEnd = new ArrayList<>();

        content = content.replaceAll("\\{#\\}", "\n");

        if (content.contains("{")) {
            int countMarkers = 0;
            int fromIndex = -1;
            while ((fromIndex = content.indexOf("{", fromIndex + 1)) >= 0) {
                countMarkers++;
            }

            for (int i = 0; i < countMarkers; i++) {
                sunjiPointStart.add(content.indexOf("{"));
                sunjiPointEnd.add(content.indexOf(("}")) - 2);

                content = content.replaceFirst("\\{@", "");
                content = content.replaceFirst("\\}", "");
            }
        }

        if ((sunjiPointEnd.size() == sunjiPointStart.size()) && (sunjiPointEnd.size() != 0)) {
            SpannableString sunjiSpannable = new SpannableString(content);
            for (int i = 0; i < sunjiPointEnd.size(); i++) {
                if (sunjiPointEnd.get(i) > sunjiPointStart.get(i)) {
//                    sunjiSpannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.col_1491E8)), sunjiPointStart.get(i), sunjiPointEnd.get(i), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sunjiSpannable.setSpan(new ForegroundColorSpan(Color.parseColor(color)), sunjiPointStart.get(i), sunjiPointEnd.get(i), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            return sunjiSpannable;
        } else {
            return new SpannableString(content);
        }
    }

    public static SpannableString replaceSpecialCharacterColor(Context context, String content, int color) {
        ArrayList<Integer> sunjiPointStart = new ArrayList<>();
        ArrayList<Integer> sunjiPointEnd = new ArrayList<>();

        content = content.replaceAll("\\{#\\}", "\n");

        if (content.contains("{")) {
            int countMarkers = 0;
            int fromIndex = -1;
            while ((fromIndex = content.indexOf("{", fromIndex + 1)) >= 0) {
                countMarkers++;
            }

            for (int i = 0; i < countMarkers; i++) {
                sunjiPointStart.add(content.indexOf("{"));
                sunjiPointEnd.add(content.indexOf(("}")) - 2);

                content = content.replaceFirst("\\{@", "");
                content = content.replaceFirst("\\}", "");
            }
        }

        if ((sunjiPointEnd.size() == sunjiPointStart.size()) && (sunjiPointEnd.size() != 0)) {
            SpannableString sunjiSpannable = new SpannableString(content);
            for (int i = 0; i < sunjiPointEnd.size(); i++) {
                if (sunjiPointEnd.get(i) > sunjiPointStart.get(i)) {
                    sunjiSpannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color)), sunjiPointStart.get(i), sunjiPointEnd.get(i), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            return sunjiSpannable;
        } else {
            return new SpannableString(content);
        }
    }

    public static String formatTimeString(String date) {
        String chDate = "";
        SimpleDateFormat format = new SimpleDateFormat("a hh시mm분");


        return chDate;
    }

    /** 몇분전, 방금 전, */
    /*private static class TIME_MAXIMUM{
        public static final int SEC = 60;
        public static final int MIN = 60;
        public static final int HOUR = 24;
        public static final int DAY = 30;
        public static final int MONTH = 12;
    }
    public static String formatTimeString(long regTime) {
        long curTime = System.currentTimeMillis();
        long diffTime = (curTime - regTime) / 1000;
        String msg = null;
        if (diffTime < TIME_MAXIMUM.SEC) {
            msg = "방금 전";
        } else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
            msg = diffTime + "분 전";
        } else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
            msg = (diffTime) + "시간 전";
        } else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
            msg = (diffTime) + "일 전";
        } else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
            msg = (diffTime) + "달 전";
        } else {
            msg = (diffTime) + "년 전";
        }
        return msg;
    }*/
}

package com.jlesoft.civilservice.koreanhistoryexam9.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;

import com.jlesoft.civilservice.koreanhistoryexam9.MyPageActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.day.mainTest.MainTestDetailActivity;

public class BarChart extends View {

    private Paint mTodayPaint, mTotalPaint, mTextPaint;

    private float mTextGap;
    private int[] mPointNum, mPoints, mPointX, mPointY;
    private int mUnit, mOrigin, mDivide;

    private float fDensity = 0.0f;

    public BarChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        fnGetDisplayMetrics(context);

       Paint paint = new Paint();
       paint.setColor(Color.BLACK);
       paint.setTextSize(fnGetRealPxFromDp(10));
       paint.setTextAlign(Paint.Align.CENTER);
       mTextPaint = paint;
       mTextGap = fnGetRealPxFromDp(2);

        //막대 옵션
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.col_9A081D));
        paint.setStrokeWidth(32);
        mTodayPaint = paint;

        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.col_90));
        paint.setStrokeWidth(32);
        mTotalPaint = paint;

    }

    private void fnGetDisplayMetrics(Context cxt) {
        final DisplayMetrics dm = cxt.getResources().getDisplayMetrics();
        fDensity = dm.density;
    }

    private float fnGetRealPxFromDp(float fDp) {
        return (fDensity != 1.0f) ? fDensity * fDp : fDp;
    }

    //그래프 정보를 받는다
    public void setPoints(int[] pointNum, int[] points, int unit, int origin, int divide, int mode) {

        if(mode == MyPageActivity.DANWON){
            mTodayPaint.setColor(getResources().getColor(R.color.col_9A081D));
        }else if(mode == MyPageActivity.PRETEST){
            mTodayPaint.setColor(getResources().getColor(R.color.col_DE673C));
        }else if(mode == MainTestDetailActivity.TEST){
            mTodayPaint.setColor(getResources().getColor(R.color.col_00b050));
        }

        mPoints = points;   //y축 값 배열
        mPointNum = pointNum;
        mUnit = unit;       //y축 단위
        mOrigin = origin;   //y축 원점
        mDivide = divide;   //y축 값 갯수
    }

    //그래프를 만든다
    public void draw() {
        int height = getHeight();
        int[] points = mPoints;

        float gapx = ((getWidth() / 6));
        float halfgab = gapx / 2;

        //y축 단위 사이의 거리
        float gapy = (height- fnGetRealPxFromDp(12)) / mDivide ;


        int length = points.length;
        mPointX = new int[length];
        mPointY = new int[length];


        int num = 0;
        for(int i = 0 ; i < length ; i++) {
            if(i % 2 == 0){
                //막대 좌표를 구한다

                int x  = (int)halfgab + (int)( num * gapx);
                int y = (int)(height - (((points[i] / mUnit) - mOrigin) * gapy));



                mPointX[i] = x;
                mPointY[i] = y;
                num++;
            }else{
                int y = (int)(height - (((points[i] / mUnit) - mOrigin) * gapy));

                mPointX[i] = mPointX[i-1];
                mPointY[i] = y;
            }
        }
    }

    //그래프를 그린다(onCreate 등에서 호출시)
    public void drawForBeforeDrawView() {
        //뷰의 크기를 계산하여 그래프를 그리기 때문에 뷰가 실제로 만들어진 시점에서 함수를 호출해 준다
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                draw();

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                else
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPointX != null && mPointY != null) {
            int length = mPointX.length;

            int bottom = getHeight();
            for (int i = 0; i < length; i++) {
                int x = mPointX[i];
                int y = mPointY[i];

                //믹대를 그린다
                if(i % 2 == 0){
                    canvas.drawText("" + mPointNum[i], x-18, y - mTextGap, mTextPaint);
                    canvas.drawLine(x-18, y, x-18, bottom, mTodayPaint);
                }else{
                    canvas.drawText("" + mPointNum[i], x+18, y - mTextGap, mTextPaint);
                    canvas.drawLine(x+18, y, x+18, bottom, mTotalPaint);
                }
            }
        }
    }

    public int convertGraph(String str) {
        if (!TextUtils.isEmpty(str)) {
            return (int) Math.round(Double.parseDouble(str) * 0.1);
        } else {
            return 0;
        }
    }

    public int convertGraph(int num) {
        if (num != 0) {
            return (int) Math.round(num * 0.1);
        } else {
            return 0;
        }
    }

    public int convertX(String str) {
        if (!TextUtils.isEmpty(str)) {
            return Math.round(Integer.parseInt(str));
        } else {
            return 0;
        }
    }

    public int convertX(int num) {
        if (num != 0) {
            return Math.round(num);
        } else {
            return 0;
        }
    }
}


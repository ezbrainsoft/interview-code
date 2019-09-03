package com.jlesoft.civilservice.koreanhistoryexam9.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.jlesoft.civilservice.koreanhistoryexam9.R;

public class CompletionChart extends View {
    private Context context;
    private Paint slicePaint, textPaint, circlePaint, outCirclePaint;
    private Path path;
    private float fDensity = 0.0f;
    private int iDisplayWidth;
    private int iDisplayHeight;

    private int iCenterWidth;
    private RectF r = null;

    private Paint paintPieFill, paintCenterCircle, paintText,strokeCirclePaint,backPaint;

    private int percentege;
    private int MODE = QUESTION;
    public static int QUESTION = 0;
    public static int PASTEST = 1;

    private void fnGetDisplayMetrics(Context cxt) {
        final DisplayMetrics dm = cxt.getResources().getDisplayMetrics();
        fDensity = dm.density;
    }
    private float fnGetRealPxFromDp(float fDp) {
        return (fDensity != 1.0f) ? fDensity * fDp : fDp;
    }

    public CompletionChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        fnGetDisplayMetrics(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        backPaint = new Paint();
        backPaint.setAntiAlias(true);
        backPaint.setDither(true);
        backPaint.setStyle(Paint.Style.FILL);
        backPaint.setColor(getResources().getColor(R.color.col_B3));
        RectF r = new RectF(fnGetRealPxFromDp(0),fnGetRealPxFromDp(0),fnGetRealPxFromDp(60), fnGetRealPxFromDp(60));
        canvas.drawArc(r, 0, 360, true, backPaint);

        slicePaint = new Paint();
        slicePaint.setAntiAlias(true);
        slicePaint.setDither(true);
        slicePaint.setStyle(Paint.Style.FILL);
        if(MODE == PASTEST ){
            slicePaint.setColor(getResources().getColor(R.color.col_DE673C));
        }else{
            slicePaint.setColor(getResources().getColor(R.color.col_9A081D));
        }

        canvas.drawArc(r, -90, percentege, true, slicePaint);

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.WHITE);
        circlePaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(iDisplayWidth/2, iDisplayWidth/2, iDisplayWidth/3, circlePaint);
    }

    public void setData(float per, int mode){
        MODE = mode;
        this.percentege = (int)(per / 100 * 360);;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // get screen size
        iDisplayWidth = MeasureSpec.getSize(widthMeasureSpec);
        iDisplayHeight = MeasureSpec.getSize(heightMeasureSpec);


    /*
     * determine the rectangle size
     */
        iCenterWidth = iDisplayWidth / 2;

        if(r == null){
            r = new RectF(iCenterWidth, iCenterWidth, iCenterWidth, iCenterWidth);

        }
        setMeasuredDimension(iDisplayWidth, iDisplayWidth);
    }
}


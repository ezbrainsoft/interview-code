package com.jlesoft.civilservice.koreanhistoryexam9.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.util.LogUtil;

public class LineChart extends View {
    private ShapeDrawable mLineShape;
    private Paint mPointPaint;

    private float mThickness;
    private int[] mPoints, mPointX, mPointY;
    private int mLineColor, mUnit, mOrigin, mDivide, myGrade;
    private float mPointSize, mPointRadius;

    private float fDensity = 0.0f;

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        fnGetDisplayMetrics(context);
        setTypes(context, attrs);
    }

    //그래프 옵션을 받는다
    private void setTypes(Context context, AttributeSet attrs) {

        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);
        mPointSize = fnGetRealPxFromDp(10);
        mPointRadius = mPointSize / 2;
        mLineColor = getResources().getColor(R.color.col_9695);
        mThickness = fnGetRealPxFromDp(3);
    }

    //그래프 정보를 받는다
    public void setPoints(int[] points, int unit, int origin, int divide, int myGrade) {

        mPoints = points;   //y축 값 배열
        mUnit = unit;       //y축 단위
        mOrigin = origin;   //y축 원점
        mDivide = divide;   //y축 값 갯수
        this.myGrade = myGrade;
    }

    //그래프를 만든다
    private void draw() {
        Path path = new Path();

        int height = getHeight();
        int[] points = mPoints;

        //x축 점 사이의 거리
        float gapx = (float) getWidth() / points.length;

        //y축 단위 사이의 거리
        float gapy = (height - mPointSize) / mDivide;

        float halfgab = gapx / 2;

        int length = points.length;
        mPointX = new int[length];
        mPointY = new int[length];

        for (int i = 0; i < length; i++) {
            //점 좌표를 구한다
            int x = (int) (halfgab + (i * gapx));
            int y = (int) (height - mPointRadius - (((points[i] / mUnit) - mOrigin) * gapy));

            mPointX[i] = x;
            mPointY[i] = y;

            //선을 그린다
            if (i == 0)
                path.moveTo(x, y);
            else
                path.lineTo(x, y);
        }

        //그려진 선으로 shape을 만든다
        ShapeDrawable shape = new ShapeDrawable(new PathShape(path, 1, 1));
        shape.setBounds(0, 0, 1, 1);

        Paint paint = shape.getPaint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(mLineColor);
        paint.setStrokeWidth(mThickness);
        paint.setAntiAlias(true);

        mLineShape = shape;
    }

    //그래프를 그린다(onCreate 등에서 호출시)
    public void drawForBeforeDrawView() {
        //뷰의 크기를 계산하여 그래프를 그리기 때문에 뷰가 실제로 만들어진 시점에서 함수를 호출해 준다
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                draw();
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //선을 그린다
        if (mLineShape != null)
            mLineShape.draw(canvas);

        //점을 그린다
        if (mPointX != null && mPointY != null) {
            int length = mPointX.length;
            for (int i = 0; i < length; i++) {
                if (i == myGrade) {
                    mPointPaint.setColor(getResources().getColor(R.color.col_00b050));
                    canvas.drawCircle(mPointX[i], mPointY[i], mPointRadius, mPointPaint);
                } else {
                    mPointPaint.setColor(Color.BLACK);
                    canvas.drawCircle(mPointX[i], mPointY[i], mPointRadius / 2, mPointPaint);
                }
            }
        }
    }

    private void fnGetDisplayMetrics(Context cxt) {
        final DisplayMetrics dm = cxt.getResources().getDisplayMetrics();
        fDensity = dm.density;
    }

    private float fnGetRealPxFromDp(float fDp) {
        return (fDensity != 1.0f) ? fDensity * fDp : fDp;
    }

    public int strDoubleToInt(String str) {
        if (!TextUtils.isEmpty(str)) {
            return (int) Math.round(Double.parseDouble(str));
        } else {
            return 0;
        }
    }

    public int strToOnePlace(String str) {
        if (str.length() > 1) {
            return (int) Math.round(Double.parseDouble(str) / 10);
        } else {
            return Integer.parseInt(str);
        }
    }


    public int max(int n[]) {
        int max = n[0];

        for (int i = 1; i < n.length; i++) {
            if (n[i] > max)
                max = n[i];
        }

        return max;
    }


    public int min(int n[]) {
        int min = n[0];

        for (int i = 1; i < n.length; i++)
            if (n[i] < min) min = n[i];

        return min;
    }
}

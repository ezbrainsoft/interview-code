package com.jlesoft.civilservice.koreanhistoryexam9.util;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class SDModeLayout extends ViewGroup {
    int deviceWidth;

    public SDModeLayout(Context context) {
        this(context, null, 0);
    }

    public SDModeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SDModeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        final Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point deviceDisplay = new Point();
        display.getSize(deviceDisplay);
        deviceWidth = deviceDisplay.x;
    }

    /*
    child view의 위치를 잡아주는 일
    */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {//left, top, right, bottom

        final int count = getChildCount();
        int curWidth, curHeight, curLeft, curTop, maxHeight;

        //get the available size of child view
        //차일드 뷰의 사용 가능한 사이즈
        final int childLeft = this.getPaddingLeft();//자신의 왼쪽패딩값
        final int childTop = this.getPaddingTop();//자신의 위쪽패딩값
        final int childRight = this.getMeasuredWidth() - this.getPaddingRight();//
        final int childBottom = this.getMeasuredHeight() - this.getPaddingBottom();
        final int childWidth = childRight - childLeft;
        final int childHeight = childBottom - childTop;


        maxHeight = 0;
        curLeft = childLeft;
        curTop = childTop;


        for (int i = 0; i < count; i++) {

            View child = getChildAt(i);
            child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST));
            curWidth = child.getMeasuredWidth();//자식뷰의 넓이.child.measure() 호출 후 호출
            curHeight = child.getMeasuredHeight();//자식뷰의 높이.child.measure() 호출 후 호출

            if (curLeft + curWidth >= childRight) {

                curLeft = childLeft;

                curTop += maxHeight;
                maxHeight = 0;
            }

            child.layout(curLeft, curTop, curLeft + curWidth, curTop + curHeight);

            if (maxHeight < curHeight)
                maxHeight = curHeight;

            curLeft += curWidth;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        int maxHeight = 0;
        int maxWidth = 0;
        int childState = 0;
        int mLeftWidth = 0;
        int rowCount = 0;

        final int childLeft = this.getPaddingLeft();//자신의 왼쪽패딩값
        final int childTop = this.getPaddingTop();//자신의 위쪽패딩값
        final int childRight = this.getMeasuredWidth() - this.getPaddingRight();//
        final int childBottom = this.getMeasuredHeight() - this.getPaddingBottom();
        final int childWidth = childRight - childLeft;
        final int childHeight = childBottom - childTop;

        int width = MeasureSpec.getSize(widthMeasureSpec);

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            maxWidth += Math.max(maxWidth, child.getMeasuredWidth());
            mLeftWidth += child.getMeasuredWidth();

            if ((mLeftWidth / width) > rowCount) {
                maxHeight += child.getMeasuredHeight();
                rowCount++;

            } else {
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
            }
            childState = combineMeasuredStates(childState, child.getMeasuredState());
        }

        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());

        int w = resolveSizeAndState(maxWidth, widthMeasureSpec, childState);
        int h = resolveSizeAndState(maxHeight, heightMeasureSpec, childState << MEASURED_HEIGHT_STATE_SHIFT);

        setMeasuredDimension(w, h);
    }
}
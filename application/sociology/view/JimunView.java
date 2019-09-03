package com.jlesoft.civilservice.koreanhistoryexam9.view;

import android.content.Context;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlesoft.civilservice.koreanhistoryexam9.BaseActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api110GetDownloadTodayTen;
import com.jlesoft.civilservice.koreanhistoryexam9.model.ConfirmQuestionJimun;
import com.jlesoft.civilservice.koreanhistoryexam9.model.DayStudyQuestionJimun;
import com.jlesoft.civilservice.koreanhistoryexam9.model.HistoryQuestionJimun;

import java.util.ArrayList;

/**
 * Created by NZ on 2017-12-11.
 * 문제 유형 중 심화학습(tfType : L), 그중에서도 B/C 타입일 때 보여주는 ㄱ,ㄴ,ㄷ,ㄹ 형식의 지문을 보여줄 때 사용하는 커스텀뷰
 */

public class JimunView extends RelativeLayout {

    private FragmentActivity activity;

    private TextView tvJimunNum;
    private TextView tvJimunTitle;
    private TextView tvJimunExplain;
    private boolean enableExplainMode = false;

    // 뷰를 선언할 때 기본적으로 뷰 내에 있는 TextView를 전부 선언해준다.
    public JimunView(FragmentActivity activity) {
        super(activity);
        this.activity = activity;
        inflateView();
    }

    public void inflateView() {
        LayoutInflater inflater = (LayoutInflater)getParentContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View baseView = inflater.inflate(R.layout.item_jimun, null);

        tvJimunNum = (TextView) baseView.findViewById(R.id.tv_jimun_num);
        tvJimunTitle = (TextView) baseView.findViewById(R.id.tv_jimun_title);
        tvJimunExplain = (TextView) baseView.findViewById(R.id.tv_jimun_explain);

        tvJimunNum.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize);
        tvJimunTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize);
        tvJimunExplain.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize);

        addView(baseView);
    }

    public Context getParentContext(){
        return activity;
    }

    /**
     * 해당 뷰에 들어갈 데이터를 가공해준다.
     */
    public void setDayStudyData(DayStudyQuestionJimun data) {
        String num = data.getJimunTitle().substring(0,2);
        tvJimunNum.setText(num);
        setSpannableString(tvJimunTitle,  data.getJimunTitle().substring(3));
        setSpannableString(tvJimunExplain, data.getIpaMirrorText());
    }

    public void setConfirmQuestionData(ConfirmQuestionJimun data) {
        String num = data.getJimunTitle().substring(0,2);
        tvJimunNum.setText(num);
        setSpannableString(tvJimunTitle,  data.getJimunTitle().substring(3));
        setSpannableString(tvJimunExplain, data.getIpaMirrorText());
    }

    public void setHistoryQuestionData(HistoryQuestionJimun data) {
        String num = data.getJimunTitle().substring(0,2);
        tvJimunNum.setText(num);
        setSpannableString(tvJimunTitle,  data.getJimunTitle().substring(3));
        setSpannableString(tvJimunExplain, data.getIpaMirrorText());
    }

    public void setDayQuizStudyData(Api110GetDownloadTodayTen.Jimun data){
        String num = data.getJimunTitle().substring(0,2);
        tvJimunNum.setText(num);
        setSpannableString(tvJimunTitle,  data.getJimunTitle().substring(3));
        setSpannableString(tvJimunExplain, data.getIpaMirrorText());
    }

    /**
     * 문제를 풀고난 뒤 풀이를 볼 때 화면에 보여지는 레이아웃을 수정하기 위해 만든 메서드이다.
     * ㄱ,ㄴ,ㄷ,ㄹ의 텍스트컬러를 바꾸고, 각 지문에 대한 해설을 추가로 보여준다.
     */
    public void setExplainMode() {
        enableExplainMode = true;
        tvJimunNum.setTextColor(ContextCompat.getColor(activity, R.color.col_820214));
        tvJimunExplain.setVisibility(VISIBLE);
    }

    /**
     * 이 메서드를 통해 뷰와 뷰에 들어갈 string 값을 넘겨준다.
     * 메서드에서는 string 중 마커 표시할 부분이 있는지 체크해서 있을 경우 각 부분의 시작과 끝 index 값을 담아둔 뒤,
     * 기존에 협의된 마커 색상으로 해당 텍스트 부분에만 마커 표시를 해준다.
     * 문제 제출 전이거나, 마커 표시할 부분이 없을 경우에는 단순히 view에 string 값만 넣어준다.
     * @param textView
     * @param text
     */
    public void setSpannableString(TextView textView, String text) {
        ArrayList<Integer> markerPointStart = new ArrayList<>();
        ArrayList<Integer> markerPointEnd = new ArrayList<>();

        if (text.contains("{")) {
            int countMarkers = 0;
            int fromIndex = -1;
            while ((fromIndex = text.indexOf("{", fromIndex + 1)) >= 0) {
                countMarkers++;
            }
            for (int i = 0; i < countMarkers; i++) {
                markerPointStart.add(text.indexOf("{"));
                markerPointEnd.add(text.indexOf(("}")) - 2);

                text = text.replaceFirst("\\{@", "");
                text = text.replaceFirst("\\}", "");
            }
        }

        // 문제 제출 후 마커 표시있는 부분은 마커 처리
        if (enableExplainMode) {
            // 보기에 마커 표시 필요한 부분이 있을 경우 해당 부분 텍스트 위에 마커 표시를 해준다.
            if ((markerPointEnd.size() == markerPointStart.size()) && (markerPointEnd.size() != 0)) {
                SpannableString spannableString = new SpannableString(text);
                for (int i = 0; i < markerPointEnd.size(); i++) {
                    if (markerPointEnd.get(i) > markerPointStart.get(i)) {
                        spannableString.setSpan(new BackgroundColorSpan(ContextCompat.getColor(activity, R.color.col_80FFE0B2)), markerPointStart.get(i), markerPointEnd.get(i),
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
                textView.setText(spannableString);
            }
            // 보기에 마커 표시 필요한 부분이 없을 경우 단순 텍스트 설정
            else {
                textView.setText(text);
            }
        } else {
            textView.setText(text);
        }
    }
}

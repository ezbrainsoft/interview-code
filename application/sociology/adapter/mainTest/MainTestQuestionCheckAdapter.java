package com.jlesoft.civilservice.koreanhistoryexam9.adapter.mainTest;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlesoft.civilservice.koreanhistoryexam9.BaseActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.BuildConfig;
import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.model.mainTest.MainTestQuestion;
import com.jlesoft.civilservice.koreanhistoryexam9.model.mainTest.MainTestQuestionSunji;

import java.util.ArrayList;

public class MainTestQuestionCheckAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MainTestQuestionSunji> sunjiList;
    private MainTestQuestionSunji sunjiData;
    private MainTestQuestion questionData;
    private Context mContext;
    private ArrayList<Integer> sunjiPointStart, sunjiPointEnd, sunjiExplainPointStart, sunjiExplainPointEnd;

    private OnItemSelectListener listener;

    public interface OnItemSelectListener { // create an interface
        void onItemSelect(MainTestQuestion questionData, int position); // create callback function
    }

    public MainTestQuestionCheckAdapter(Context mContext, ArrayList<MainTestQuestionSunji> sunjiList, MainTestQuestion questionData) {
        this.mContext = mContext;
        this.sunjiList = sunjiList;
        this.questionData = questionData;
    }

    public MainTestQuestionCheckAdapter(Context mContext, ArrayList<MainTestQuestionSunji> sunjiList, MainTestQuestion questionData, OnItemSelectListener listener) {
        this.mContext = mContext;
        this.sunjiList = sunjiList;
        this.questionData = questionData;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_day_study_question, parent, false);

        TextView tvSunji = (TextView) v.findViewById(R.id.tv_sunji);
        tvSunji.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize);
        TextView tvSunjiExplain = (TextView) v.findViewById(R.id.tv_sunji_explain);
        tvSunjiExplain.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize);

        return new GenericViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        sunjiData = sunjiList.get(position);

        GenericViewHolder genericHolder = (GenericViewHolder) holder;

        String sunji = sunjiData.getIpaTitle();
        String sunjiExplain = sunjiData.getIpaMirrorText();

        sunjiPointStart = new ArrayList<>();
        sunjiPointEnd = new ArrayList<>();
        sunjiExplainPointStart = new ArrayList<>();
        sunjiExplainPointEnd = new ArrayList<>();

        if (sunji.contains("{")) {
            int countMarkers = 0;
            int fromIndex = -1;
            while ((fromIndex = sunji.indexOf("{", fromIndex + 1)) >= 0) {
                countMarkers++;
            }
            for (int i = 0; i < countMarkers; i++) {
                sunjiPointStart.add(sunji.indexOf("{"));
                sunjiPointEnd.add(sunji.indexOf(("}")) - 2);

                sunji = sunji.replaceAll("\\{#\\}", "\n");
                sunji = sunji.replaceFirst("\\{@", "");
                sunji = sunji.replaceFirst("\\}", "");
            }
        }
        // 문제 제출 전(문제 안풀은 경우)
        if (questionData.getSelectSunji() == 0) {

            genericHolder.tvSunji.setText(sunji);
            genericHolder.tvSunjiExplain.setVisibility(View.GONE);

            switch (position) {
                case 0:
                    genericHolder.ivNum.setImageResource(R.drawable.ic_num01);
                    break;

                case 1:
                    genericHolder.ivNum.setImageResource(R.drawable.ic_num02);
                    break;

                case 2:
                    genericHolder.ivNum.setImageResource(R.drawable.ic_num03);
                    break;

                case 3:
                    genericHolder.ivNum.setImageResource(R.drawable.ic_num04);
                    break;

                case 4:
                    genericHolder.ivNum.setImageResource(R.drawable.ic_num05);
                    break;
            }
        } else {

            // 보기에 마커 표시 필요한 부분이 있을 경우 마커 텍스트 설정
            if ((sunjiPointEnd.size() == sunjiPointStart.size()) && (sunjiPointEnd.size() != 0)) {
                SpannableString sunjiSpannable = new SpannableString(sunji);
                for (int i = 0; i < sunjiPointEnd.size(); i++) {
                    if (sunjiPointEnd.get(i) > sunjiPointStart.get(i)) {
                        if(BuildConfig.APP_NAME.equals(BaseActivity.KOREAN) || BuildConfig.APP_NAME.equals(BaseActivity.ENGLISH)){
                            sunjiSpannable.setSpan(new UnderlineSpan(), sunjiPointStart.get(i), sunjiPointEnd.get(i), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }else{
                            sunjiSpannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.col_C708)), sunjiPointStart.get(i), sunjiPointEnd.get(i),
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                }
                genericHolder.tvSunji.setText(sunjiSpannable);
            }
            // 보기에 마커 표시 필요한 부분이 없을 경우 단순 텍스트 설정
            else {
                genericHolder.tvSunji.setText(sunji);
            }

            // 해설이 있을 경우
            if (!TextUtils.isEmpty(sunjiExplain)) {
                if (sunjiExplain.contains("{")) {
                    int countMarkers = 0;
                    int fromIndex = -1;
                    while ((fromIndex = sunjiExplain.indexOf("{", fromIndex + 1)) >= 0) {
                        countMarkers++;
                    }
                    for (int i = 0; i < countMarkers; i++) {
                        sunjiExplainPointStart.add(sunjiExplain.indexOf("{"));
                        sunjiExplainPointEnd.add(sunjiExplain.indexOf(("}")) - 2);

                        sunjiExplain = sunjiExplain.replaceAll("\\{#\\}", "\n");
                        sunjiExplain = sunjiExplain.replaceFirst("\\{@", "");
                        sunjiExplain = sunjiExplain.replaceFirst("\\}", "");
                    }
                }

                // 해설에 마커 표시가 필요한 경우 마커 텍스트 설정
                if ((sunjiExplainPointEnd.size() == sunjiExplainPointStart.size()) && (sunjiExplainPointEnd.size() != 0)) {
                    SpannableString sunjiExplainSpannable = new SpannableString(sunjiExplain);
                    for (int i = 0; i < sunjiExplainPointEnd.size(); i++) {
                        if (sunjiExplainPointEnd.get(i) > sunjiExplainPointStart.get(i)) {
                            if(BuildConfig.APP_NAME.equals(BaseActivity.KOREAN) || BuildConfig.APP_NAME.equals(BaseActivity.ENGLISH)){
                                sunjiExplainSpannable.setSpan(new UnderlineSpan(), sunjiPointStart.get(i), sunjiPointEnd.get(i), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }else {
                                sunjiExplainSpannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.col_3372dc)), sunjiExplainPointStart.get(i), sunjiExplainPointEnd.get(i),
                                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }
                    }
                    genericHolder.tvSunjiExplain.setText(sunjiExplainSpannable);
                }
                // 해설에 마커 표시가 필요없는 경우 단순 텍스트 설정
                else {
                    genericHolder.tvSunjiExplain.setText(sunjiExplain);
                }
                genericHolder.tvSunjiExplain.setVisibility(View.VISIBLE);
            } else {
                genericHolder.tvSunjiExplain.setVisibility(View.GONE);
            }

            int selectSunji = questionData.getSelectSunji();
            int thisSunji = sunjiData.getSqiIdx();
            String correct = sunjiData.getIpaType();

            switch (position) {
                case 0:
                    if (correct.equalsIgnoreCase("O")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_o);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_result_border);
                        break;
                    } else if (selectSunji == thisSunji && correct.equalsIgnoreCase("X")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_x);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_wrong_border);
                        break;
                    } else {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num01);
                    }
                    break;

                case 1:
                    if (correct.equalsIgnoreCase("O")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_o);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_result_border);
                        break;
                    } else if (selectSunji == thisSunji && correct.equalsIgnoreCase("X")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_x);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_wrong_border);
                        break;
                    } else {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num02);
                    }
                    break;

                case 2:
                    if (correct.equalsIgnoreCase("O")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_o);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_result_border);
                        break;
                    } else if (selectSunji == thisSunji && correct.equalsIgnoreCase("X")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_x);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_wrong_border);
                        break;
                    } else {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num03);
                    }
                    break;

                case 3:
                    if (correct.equalsIgnoreCase("O")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_o);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_result_border);
                        break;
                    } else if (selectSunji == thisSunji && correct.equalsIgnoreCase("X")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_x);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_wrong_border);
                        break;
                    } else {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num04);
                    }
                    break;
                case 4:
                    if (correct.equalsIgnoreCase("O")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_o);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_result_border);
                        break;
                    } else if (selectSunji == thisSunji && correct.equalsIgnoreCase("X")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_x);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_wrong_border);
                        break;
                    } else {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num05);
                    }
                    break;
            }
        }

        if(listener != null){
            genericHolder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemSelect(questionData, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return sunjiList.size();
    }

    public static class GenericViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rl;
        public TextView tvSunji, tvSunjiExplain;
        public ImageView ivNum;

        public GenericViewHolder(View view) {
            super(view);
            rl = (RelativeLayout) view.findViewById(R.id.rl);
            tvSunji = (TextView) view.findViewById(R.id.tv_sunji);
            tvSunjiExplain = (TextView) view.findViewById(R.id.tv_sunji_explain);
            ivNum = (ImageView) view.findViewById(R.id.iv_sunji);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout header;
        public TextView tvQuestion;
        public ImageView ivQuestion;

        public HeaderViewHolder(View view) {
            super(view);
            header = (RelativeLayout) view.findViewById(R.id.header);
            tvQuestion = (TextView) view.findViewById(R.id.tv_question);
            ivQuestion = (ImageView) view.findViewById(R.id.iv_question);
        }
    }
}
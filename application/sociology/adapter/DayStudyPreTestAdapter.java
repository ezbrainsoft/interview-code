package com.jlesoft.civilservice.koreanhistoryexam9.adapter;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlesoft.civilservice.koreanhistoryexam9.BaseActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api10GetPastTest;

import java.util.ArrayList;

public class DayStudyPreTestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Api10GetPastTest.SunjiList> sunjiList;
    private Api10GetPastTest.SunjiList sunjiData;
    private Api10GetPastTest.QuestionList questionData;
    private Context mContext;
    private ArrayList<Integer> sunjiPointStart, sunjiPointEnd;

    public DayStudyPreTestAdapter(Context mContext, Api10GetPastTest.QuestionList questionData,
                                  ArrayList<Api10GetPastTest.SunjiList> sunjiList) {
        this.mContext = mContext;
        this.questionData = questionData;
        this.sunjiList = sunjiList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_day_study_question, parent, false);
        TextView tvSunji = (TextView) v.findViewById(R.id.tv_sunji);
        TextView tvSunjiEx = (TextView) v.findViewById(R.id.tv_sunji_explain);
        tvSunji.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize);
        tvSunjiEx.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize);

        return new GenericViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        sunjiData = sunjiList.get(position);
        GenericViewHolder genericHolder = (GenericViewHolder) holder;

        String sunji = sunjiData.getIpaTitle();

        sunjiPointStart = new ArrayList<>();
        sunjiPointEnd = new ArrayList<>();

        if (sunji.contains("{")) {
            int countMarkers = 0;
            int fromIndex = -1;
            while ((fromIndex = sunji.indexOf("{", fromIndex + 1)) >= 0) {
                countMarkers++;
            }
            for (int i = 0; i < countMarkers; i++) {
                sunjiPointStart.add(sunji.indexOf("{"));
                sunjiPointEnd.add(sunji.indexOf(("}")) - 2);

                sunji = sunji.replaceFirst("\\{@", "");
                sunji = sunji.replaceFirst("\\}", "");
            }
        }

        // 문제 제출 전
        if (questionData.getSubmit().equalsIgnoreCase("N")) {
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
            }
        }
        // 문제 제출 후
        else {
            // 보기에 마커 표시 필요한 부분이 있을 경우 마커 텍스트 설정
            if ((sunjiPointEnd.size() == sunjiPointStart.size()) && (sunjiPointEnd.size() != 0)) {
                SpannableString sunjiSpannable = new SpannableString(sunji);
                for (int i = 0; i < sunjiPointEnd.size(); i++) {
                    if (sunjiPointEnd.get(i) > sunjiPointStart.get(i)) {
                        sunjiSpannable.setSpan(new BackgroundColorSpan(ContextCompat.getColor(mContext, R.color.col_80FFE0B2)), sunjiPointStart.get(i), sunjiPointEnd.get(i),
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
                genericHolder.tvSunji.setText(sunjiSpannable);
            }
            // 보기에 마커 표시 필요한 부분이 없을 경우 단순 텍스트 설정
            else {
                genericHolder.tvSunji.setText(sunji);
            }

            genericHolder.tvSunjiExplain.setVisibility(View.GONE);

            String select = sunjiData.getSelect();
            String correct = sunjiData.getIpaType();

            switch (position) {
                case 0:
                    if (correct.equalsIgnoreCase("O")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_o);
                        break;
                    } else if (select.equalsIgnoreCase("O") && correct.equalsIgnoreCase("X")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_x);
                        break;
                    } else {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num01);
                    }
                    break;

                case 1:
                    if (correct.equalsIgnoreCase("O")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_o);
                        break;
                    } else if (select.equalsIgnoreCase("O") && correct.equalsIgnoreCase("X")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_x);
                        break;
                    } else {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num02);
                    }
                    break;

                case 2:
                    if (correct.equalsIgnoreCase("O")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_o);
                        break;
                    } else if (select.equalsIgnoreCase("O") && correct.equalsIgnoreCase("X")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_x);
                        break;
                    } else {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num03);
                    }
                    break;

                case 3:
                    if (correct.equalsIgnoreCase("O")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_o);
                        break;
                    } else if (select.equalsIgnoreCase("O") && correct.equalsIgnoreCase("X")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_x);
                        break;
                    } else {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num04);
                    }
                    break;

                case 4:
                    if (correct.equalsIgnoreCase("O")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_o);
                        break;
                    } else if (select.equalsIgnoreCase("O") && correct.equalsIgnoreCase("X")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_x);
                        break;
                    } else {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num05);
                    }
                    break;
            }
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
        public LinearLayout questionPart;
        public TextView tvQuestion, tvCategory;
        public ImageView ivQuestion;

        public HeaderViewHolder(View view) {
            super(view);
            header = (RelativeLayout) view.findViewById(R.id.header);
            questionPart = (LinearLayout) view.findViewById(R.id.ll_question);
            tvCategory = (TextView) view.findViewById(R.id.tv_category);
            tvQuestion = (TextView) view.findViewById(R.id.tv_question);
            ivQuestion = (ImageView) view.findViewById(R.id.iv_question);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout footer;
        public TextView tvExplain;

        public FooterViewHolder(View view) {
            super(view);
            footer = (LinearLayout) view.findViewById(R.id.footer);
            tvExplain = (TextView) view.findViewById(R.id.tv_explain);
        }
    }
}
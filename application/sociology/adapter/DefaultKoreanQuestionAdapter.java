package com.jlesoft.civilservice.koreanhistoryexam9.adapter;

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
import com.jlesoft.civilservice.koreanhistoryexam9.BaseKoreanActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.model.DefaultQuestionListDao;
import com.jlesoft.civilservice.koreanhistoryexam9.util.LogUtil;
import com.jlesoft.civilservice.koreanhistoryexam9.util.MODE;
import com.jlesoft.civilservice.koreanhistoryexam9.util.StringUtil;

import java.util.ArrayList;


public class DefaultKoreanQuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<DefaultQuestionListDao.Sunji> sunjiList;
    private DefaultQuestionListDao.Sunji sunjiData;
    private DefaultQuestionListDao.Question questionData;
    private Context mContext;
    private ArrayList<Integer> sunjiPointStart, sunjiPointEnd, sunjiExplainPointStart, sunjiExplainPointEnd;
    private String sqClass, whereIs;

    public DefaultKoreanQuestionAdapter(Context mContext, DefaultQuestionListDao.Question questionData, String whereIs) {
        this.mContext = mContext;
        this.sunjiList = questionData.getSunjiList();
        this.questionData = questionData;
        this.sqClass = questionData.getSqClass();
        this.whereIs = whereIs;
    }

    public DefaultKoreanQuestionAdapter(Context mContext, ArrayList<DefaultQuestionListDao.Sunji> sunjiList, DefaultQuestionListDao.Question questionData) {
        this.mContext = mContext;
        this.sunjiList = sunjiList;
        this.questionData = questionData;
        this.sqClass = questionData.getSqClass();
    }

    public static class GenericViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rl;
        public TextView tvSunji, tvSunjiExplain, tvSqiWContents;
        public ImageView ivNum;

        public GenericViewHolder(View view) {
            super(view);
            rl = view.findViewById(R.id.rl);
            tvSunji = view.findViewById(R.id.tv_sunji);
            tvSunjiExplain = view.findViewById(R.id.tv_sunji_explain);
            ivNum = view.findViewById(R.id.iv_sunji);
            tvSqiWContents = view.findViewById(R.id.tv_sqi_w_contents);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_day_study_question, parent, false);
        return new GenericViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        sunjiData = sunjiList.get(position);

        GenericViewHolder genericHolder = (GenericViewHolder) holder;

        genericHolder.tvSunji.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize);
        genericHolder.tvSunjiExplain.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize);
        genericHolder.tvSqiWContents.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize);

        String sunji = sunjiData.getIpaTitle();
        String sunjiExplain = sunjiData.getIpaMirrorText();

        sunjiPointStart = new ArrayList<>();
        sunjiPointEnd = new ArrayList<>();

        sunjiExplainPointStart = new ArrayList<>();
        sunjiExplainPointEnd = new ArrayList<>();

        sunji = sunji.replaceAll("\\{#\\}", "\n");

        // 문제 제출 전
        if (questionData.getSolvedYN().equalsIgnoreCase("N")) {

            //선지
            if (sunji.contains("{") && !questionData.getSqClass().equals("W")) {
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

                // 해설에 마커 표시가 필요한 경우 마커 텍스트 설정
                if ((sunjiPointEnd.size() == sunjiPointStart.size()) && (sunjiPointEnd.size() != 0)) {
                    SpannableString sunjiSpannable = new SpannableString(sunji);

                    for (int i = 0; i < sunjiPointEnd.size(); i++) {
                        if (sunjiPointEnd.get(i) > sunjiPointStart.get(i)) {
                            sunjiSpannable.setSpan(new UnderlineSpan(), sunjiPointStart.get(i), sunjiPointEnd.get(i), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        }
                    }
                    genericHolder.tvSunji.setText(sunjiSpannable);
                }

            } else {
                sunji = sunji.replaceFirst("\\{@", "");
                sunji = sunji.replaceFirst("\\}", "");

                genericHolder.tvSunji.setText(sunji);
            }

            genericHolder.tvSunjiExplain.setVisibility(View.GONE);

            String select = sunjiData.getSelect();
            int selectSunji = questionData.getSelectSunji();

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

            if(questionData.getTfType().equals(MODE.TF_TYPE_T) || questionData.getTfType().equals(MODE.TF_TYPE_F) || questionData.getTfType().equals(MODE.TF_TYPE_C)){
                if (select.equals("O") && sunjiData.getSqiIdx() == selectSunji) {
                    genericHolder.rl.setBackground(mContext.getResources().getDrawable(R.drawable.sunji_scoring_border));
                } else {
                    genericHolder.rl.setBackground(mContext.getResources().getDrawable(R.drawable.sunji_border));
                }
            }else{
                if (select.equals("O") && sunjiData.getIpaIdx() == selectSunji) {
                    genericHolder.rl.setBackground(mContext.getResources().getDrawable(R.drawable.sunji_scoring_border));
                } else {
                    genericHolder.rl.setBackground(mContext.getResources().getDrawable(R.drawable.sunji_border));
                }
            }
        }
        // 문제 제출 후
        else {
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

            if ((sunjiPointEnd.size() == sunjiPointStart.size()) && (sunjiPointEnd.size() != 0)) {

                SpannableString sunjiSpannable = new SpannableString(sunji);

                for (int i = 0; i < sunjiPointEnd.size(); i++) {

                    if (sunjiPointEnd.get(i) > sunjiPointStart.get(i)) {
                        sunjiSpannable.setSpan(new UnderlineSpan(), sunjiPointStart.get(i), sunjiPointEnd.get(i), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
                genericHolder.tvSunji.setText(sunjiSpannable);

            } else {
                genericHolder.tvSunji.setText(sunji);
            }

            //해설
            if (BaseActivity.permit.contains("past_explain") && StringUtil.isNotNull(sunjiExplain)) {
                sunjiExplain = sunjiExplain.replaceAll("\\{#\\}", "\n");

                String sqClass = questionData.getSqClass();

                if (!TextUtils.isEmpty(sqClass)) {

                    if (sqClass.equals(MODE.SQ_CLASS_V) || sqClass.equals(MODE.SQ_CLASS_W)) {

                        // 해설이 있을 경우
                        if (StringUtil.isNotNull(sunjiExplain)) {
                            if (sunjiExplain.contains("{")) {
                                int countMarkers = 0;
                                int fromIndex = -1;
                                while ((fromIndex = sunjiExplain.indexOf("{", fromIndex + 1)) >= 0) {
                                    countMarkers++;
                                }
                                for (int i = 0; i < countMarkers; i++) {
                                    sunjiExplainPointStart.add(sunjiExplain.indexOf("{"));
                                    sunjiExplainPointEnd.add(sunjiExplain.indexOf(("}")) - 2);

                                    sunjiExplain = sunjiExplain.replaceFirst("\\{@", "");
                                    sunjiExplain = sunjiExplain.replaceFirst("\\}", "");
                                }
                            }

                            // 해설에 마커 표시가 필요한 경우 마커 텍스트 설정
                            if ((sunjiExplainPointEnd.size() == sunjiExplainPointStart.size()) && (sunjiExplainPointEnd.size() != 0)) {

                                SpannableString sunjiExplainSpannable = new SpannableString(sunjiExplain);

                                for (int i = 0; i < sunjiExplainPointEnd.size(); i++) {
                                    if (sunjiExplainPointEnd.get(i) > sunjiExplainPointStart.get(i)) {
                                        sunjiExplainSpannable.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.col_3372dc)), sunjiExplainPointStart.get(i), sunjiExplainPointEnd.get(i),
                                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    }
                                }
                                genericHolder.tvSunjiExplain.setText(sunjiExplainSpannable);
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

                    } else if (sqClass.equals(MODE.SQ_CLASS_X)) {

                        if (!TextUtils.isEmpty(sunjiData.getSqiWContents())) {
                            genericHolder.tvSqiWContents.setVisibility(View.VISIBLE);
                            genericHolder.tvSqiWContents.setText(sunjiData.getSqiWContents());
                        }

                        genericHolder.tvSunjiExplain.setTextColor(mContext.getResources().getColor(R.color.col_595959));
                        genericHolder.tvSunjiExplain.setText(sunjiExplain);
                        genericHolder.tvSunjiExplain.setVisibility(View.VISIBLE);

                    } else if (sqClass.equals(MODE.SQ_CLASS_F)) {

                        if (!TextUtils.isEmpty(sunjiData.getSqiWContents())) {
                            genericHolder.tvSqiWContents.setVisibility(View.VISIBLE);

                            String sqiWContents = sunjiData.getSqiWContents();

                            SpannableString sunjiExplainSpannable = new SpannableString(sunjiExplain);

                            if (sunjiExplain.startsWith(sqiWContents)) {

                                sunjiExplainSpannable.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.col_3372dc)), 0, sqiWContents.length(),
                                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }

                            genericHolder.tvSunjiExplain.setText(sunjiExplainSpannable);
                            genericHolder.tvSqiWContents.setVisibility(View.GONE);
                            genericHolder.tvSunjiExplain.setVisibility(View.VISIBLE);
                        }

                    } else if (sqClass.equals(MODE.SQ_CLASS_Z)) {//기출문제- 직렬별
                        // 해설이 있을 경우
                        if (StringUtil.isNotNull(sunjiExplain)) {
                            if (sunjiExplain.contains("{")) {

                                int countMarkers = 0;
                                int fromIndex = -1;

                                while ((fromIndex = sunjiExplain.indexOf("{", fromIndex + 1)) >= 0) {
                                    countMarkers++;
                                }

                                for (int i = 0; i < countMarkers; i++) {
                                    sunjiExplainPointStart.add(sunjiExplain.indexOf("{"));
                                    sunjiExplainPointEnd.add(sunjiExplain.indexOf(("}")) - 2);

                                    sunjiExplain = sunjiExplain.replaceFirst("\\{@", "");
                                    sunjiExplain = sunjiExplain.replaceFirst("\\}", "");
                                }
                            }

                            // 해설에 마커 표시가 필요한 경우 마커 텍스트 설정
                            if ((sunjiExplainPointEnd.size() == sunjiExplainPointStart.size()) && (sunjiExplainPointEnd.size() != 0)) {
                                SpannableString sunjiExplainSpannable = new SpannableString(sunjiExplain);

                                for (int i = 0; i < sunjiExplainPointEnd.size(); i++) {

                                    if (sunjiExplainPointEnd.get(i) > sunjiExplainPointStart.get(i)) {

                                        if (sunjiData.getIpaType().equals("O")) {
                                            sunjiExplainSpannable.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.col_3372dc)), sunjiExplainPointStart.get(i), sunjiExplainPointEnd.get(i),
                                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                        } else {
                                            sunjiExplainSpannable.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.col_C708)), sunjiExplainPointStart.get(i), sunjiExplainPointEnd.get(i),
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
                    } else {
                        sunjiExplain = sunjiExplain.replaceAll("\\{@", "");
                        sunjiExplain = sunjiExplain.replaceAll("\\}", "");

                        genericHolder.tvSunjiExplain.setText(sunjiExplain);
                        genericHolder.tvSunjiExplain.setVisibility(View.VISIBLE);
                    }

                } else { //sq_class가 없는 경우. 기출문제
                    sunjiExplain = sunjiExplain.replaceAll("\\{@", "");
                    sunjiExplain = sunjiExplain.replaceAll("\\}", "");

                    genericHolder.tvSunjiExplain.setText(sunjiExplain);
                    genericHolder.tvSunjiExplain.setVisibility(View.VISIBLE);
                }

            } else {
                genericHolder.tvSunjiExplain.setVisibility(View.GONE);
            }

            String select = sunjiData.getSelect();
            String correct = sunjiData.getIpaType();

            switch (position) {
                case 0:
                    if (correct.equalsIgnoreCase("O")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_o);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_result_border);
                        break;
                    } else if (select.equalsIgnoreCase("O") && correct.equalsIgnoreCase("X")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_x);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_wrong_border);
                        break;
                    } else {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num01);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_border);
                    }
                    break;

                case 1:
                    if (correct.equalsIgnoreCase("O")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_o);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_result_border);
                        break;
                    } else if (select.equalsIgnoreCase("O")
                            && correct.equalsIgnoreCase("X")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_x);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_wrong_border);
                        break;
                    } else {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num02);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_border);
                    }
                    break;

                case 2:
                    if (correct.equalsIgnoreCase("O")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_o);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_result_border);
                        break;
                    } else if (select.equalsIgnoreCase("O") && correct.equalsIgnoreCase("X")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_x);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_wrong_border);
                        break;
                    } else {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num03);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_border);
                    }
                    break;

                case 3:
                    if (correct.equalsIgnoreCase("O")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_o);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_result_border);
                        break;
                    } else if (select.equalsIgnoreCase("O") && correct.equalsIgnoreCase("X")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_x);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_wrong_border);
                        break;
                    } else {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num04);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_border);
                    }
                    break;

                case 4:
                    if (correct.equalsIgnoreCase("O")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_o);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_result_border);
                        break;
                    } else if (select.equalsIgnoreCase("O") && correct.equalsIgnoreCase("X")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_x);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_wrong_border);
                        break;
                    } else {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num05);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_border);
                    }
                    break;
            }
        }
    }


    @Override
    public int getItemCount() {
        return sunjiList.size();
    }
}

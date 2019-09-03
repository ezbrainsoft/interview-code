package com.jlesoft.civilservice.koreanhistoryexam9.word.adapter;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
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
import com.jlesoft.civilservice.koreanhistoryexam9.model.ApiGetReviewAndRefineQuestion;
import com.jlesoft.civilservice.koreanhistoryexam9.util.MODE;
import com.jlesoft.civilservice.koreanhistoryexam9.util.StringUtil;

import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;

public class ReviewRefineKoreanQuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ApiGetReviewAndRefineQuestion.ApiResultData resultData;
    private ArrayList<ApiGetReviewAndRefineQuestion.ApiSunjiList> sunjiList;
    ApiGetReviewAndRefineQuestion.ApiSunjiList sunjiData;

    private Context mContext;
    private ArrayList<Integer> sunjiPointStart, sunjiPointEnd, sunjiExplainPointStart, sunjiExplainPointEnd;
    private boolean isOpenTutorialGuidePopup;

    private String sqClass;

    public ReviewRefineKoreanQuestionAdapter(Context mContext, ApiGetReviewAndRefineQuestion.ApiResultData resultData,
                                             ArrayList<ApiGetReviewAndRefineQuestion.ApiSunjiList> sunjiList, int questionCount,
                                             boolean isOpenTutorialGuidePopup, String sqClass) {
        this.mContext = mContext;
        this.resultData = resultData;
        this.sunjiList = sunjiList;
        this.isOpenTutorialGuidePopup = isOpenTutorialGuidePopup;
        this.sqClass = sqClass;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.item_rv_day_study_question, parent, false);
            return new GenericViewHolder (v);
    }

    @Override
    public void onBindViewHolder (final RecyclerView.ViewHolder holder, int position) {

        sunjiData = sunjiList.get(position);

        GenericViewHolder genericHolder = (GenericViewHolder) holder;
        genericHolder.tvSunji.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize);
        genericHolder.tvSunjiExplain.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize);
        genericHolder.tvSqiWContents.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize);

        String sunji = "";
        sunji = StringEscapeUtils.unescapeHtml4(sunjiData.getIpaTitle());
        sunji = sunji.replaceAll("\\{#\\}", "\n");

        String sunjiExplain = StringEscapeUtils.unescapeHtml4(sunjiData.getIpaMirrorText());
        sunjiExplain = sunjiExplain.replaceAll("\\{#\\}", "\n");

        if(isOpenTutorialGuidePopup && !sunjiData.isOriginSunji()) {
            genericHolder.ivNew.setVisibility(View.VISIBLE);
        }

        if (StringUtil.isNull(sunjiExplain)) {
            sunjiExplain = "";
        }

        sunjiPointStart = new ArrayList<>();
        sunjiPointEnd = new ArrayList<>();
        sunjiExplainPointStart = new ArrayList<>();
        sunjiExplainPointEnd = new ArrayList<>();

        // 문제 제출 전
        if (resultData.getSubmitYN().equalsIgnoreCase("N")) {
            //선지
            if (sunji.contains("{") && !sqClass.equals(MODE.SQ_CLASS_W)) {
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

                        if (sunjiPointEnd.get(i) > sunjiPointStart.get(i)) {
                            sunjiSpannable.setSpan(new UnderlineSpan(), sunjiPointStart.get(i), sunjiPointEnd.get(i), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                }
                genericHolder.tvSunji.setText(sunjiSpannable);
            } else {
                genericHolder.tvSunji.setText(sunji);
            }

            //해설
            if (StringUtil.isNotNull(sunjiExplain)) {
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
                        }
                        // 해설에 마커 표시가 필요없는 경우 단순 텍스트 설정
                        else {
                            genericHolder.tvSunjiExplain.setText(sunjiExplain);
                        }
                        genericHolder.tvSunjiExplain.setVisibility(View.VISIBLE);
                    } else {
                        genericHolder.tvSunjiExplain.setVisibility(View.GONE);
                    }
                } else if(sqClass.equals(MODE.SQ_CLASS_X)) {

                    if (!TextUtils.isEmpty(sunjiData.getSqiWContents())) {
                        genericHolder.tvSqiWContents.setVisibility(View.VISIBLE);
                        genericHolder.tvSqiWContents.setText(sunjiData.getSqiWContents());
                    }

                    if (!TextUtils.isEmpty(sunjiExplain)) {
                        genericHolder.tvSunjiExplain.setTextColor(mContext.getResources().getColor(R.color.col_595959));
                        genericHolder.tvSunjiExplain.setText(sunjiExplain);
                        genericHolder.tvSunjiExplain.setVisibility(View.VISIBLE);
                    }

                } else {
                    sunjiExplain = sunjiExplain.replaceAll("\\{@", "");
                    sunjiExplain = sunjiExplain.replaceAll("\\}", "");
                    sunjiExplain = sunjiExplain.replaceAll("\\{#\\}", "\n");

                    genericHolder.tvSunjiExplain.setText(sunjiExplain);
                    genericHolder.tvSunjiExplain.setVisibility(View.VISIBLE);
                }
            }else{
                genericHolder.tvSunjiExplain.setVisibility(View.GONE);
            }

            String select = sunjiData.getSelect();
            String correct = sunjiData.getIpaType();

            switch (position) {
                case 0:
                    if(correct.equalsIgnoreCase("O")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_o);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_result_border);
                        break;
                    } else if (select.equalsIgnoreCase("O") && correct.equalsIgnoreCase("X")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_x);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_wrong_border);
                        break;
                    } else {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num01);
                    }
                    break;

                case 1:
                    if(correct.equalsIgnoreCase("O")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_o);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_result_border);
                        break;
                    } else if (select.equalsIgnoreCase("O") && correct.equalsIgnoreCase("X")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_x);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_wrong_border);
                        break;
                    } else {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num02);
                    }
                    break;

                case 2:
                    if(correct.equalsIgnoreCase("O")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_o);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_result_border);
                        break;
                    } else if (select.equalsIgnoreCase("O") && correct.equalsIgnoreCase("X")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_x);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_wrong_border);
                        break;
                    } else {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num03);
                    }
                    break;

                case 3:
                    if(correct.equalsIgnoreCase("O")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_o);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_result_border);
                        break;
                    } else if (select.equalsIgnoreCase("O") && correct.equalsIgnoreCase("X")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_x);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_wrong_border);
                        break;
                    } else {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num04);
                    }
                    break;
                case 4:
                    if(correct.equalsIgnoreCase("O")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_o);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_result_border);
                        break;
                    } else if (select.equalsIgnoreCase("O") && correct.equalsIgnoreCase("X")) {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num_x);
                        genericHolder.rl.setBackgroundResource(R.drawable.sunji_wrong_border);
                        break;
                    } else {
                        genericHolder.ivNum.setImageResource(R.drawable.ic_num05);
                    }
                    break;
            }
        }
    }

    @Override
    public int getItemCount () {
        return sunjiList.size ();
    }

    public static class GenericViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rl;
        public TextView tvSunji, tvSunjiExplain,tvSqiWContents;
        public ImageView ivNum, ivNew;

        public GenericViewHolder(View view) {
            super(view);
            rl = (RelativeLayout) view.findViewById(R.id.rl);
            tvSunji = (TextView) view.findViewById(R.id.tv_sunji);
            tvSunjiExplain = (TextView) view.findViewById(R.id.tv_sunji_explain);
            ivNum = (ImageView) view.findViewById(R.id.iv_sunji);
            ivNew = (ImageView) view.findViewById(R.id.iv_new);
            tvSqiWContents = (TextView) view.findViewById(R.id.tv_sqi_w_contents);
        }
    }
}
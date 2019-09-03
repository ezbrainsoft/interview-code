package com.jlesoft.civilservice.koreanhistoryexam9.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.JsonObject;
import com.jlesoft.civilservice.koreanhistoryexam9.BaseActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.BuildConfig;
import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.englishWord.EnglishWordMainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.everydayEnglish.EverydayEnglishMainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.grammar.GrammarMainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.network.NetworkResponse;
import com.jlesoft.civilservice.koreanhistoryexam9.network.RequestData;
import com.jlesoft.civilservice.koreanhistoryexam9.previous.PreTestMainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.randomOX.RandomOXMainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.smartNote.SmartNoteMainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.study.DanwonMainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.studygroup.StudyGroupStatus1Activity;
import com.jlesoft.civilservice.koreanhistoryexam9.util.CommonUtils;
import com.jlesoft.civilservice.koreanhistoryexam9.util.LogUtil;
import com.jlesoft.civilservice.koreanhistoryexam9.view.model.withBottomDialogData;
import com.jlesoft.civilservice.koreanhistoryexam9.word.WordMainActivity;

import androidx.annotation.Nullable;
import retrofit2.Call;

public class WithBottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public static WithBottomSheetDialog getInstance() {
        return new WithBottomSheetDialog();
    }


    private withBottomDialogData.ResultData data;

    private CheckBox cbClose;
    private Button btnClose;
    private TextView tvGoWith;
    private LinearLayout llRoot;
    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.with_bottom_sheet_dialog, container, false);
            cbClose = (CheckBox) view.findViewById(R.id.cb_close);
            btnClose = (Button) view.findViewById(R.id.btn_close);
            tvGoWith = (TextView) view.findViewById(R.id.tv_go_with);
            llRoot = (LinearLayout) view.findViewById(R.id.ll_root);
            if(BaseActivity.isCheckWithDialog){
                cbClose.setChecked(true);
            }


            cbClose.setOnCheckedChangeListener(this);
            btnClose.setOnClickListener(this);
            tvGoWith.setOnClickListener(this);

            getHttpData();
        }
        return view;
    }

    private void getHttpData() {
        if (CommonUtils.getConnectNetwork(getActivity())) {

            JsonObject jo = new JsonObject();
            jo.addProperty("user_code", BaseActivity.userCode);

            RequestData.getInstance().getStudyWithInfo(jo, new NetworkResponse<withBottomDialogData>() {
                @Override
                public void onSuccess(Call call, withBottomDialogData clazz) {

                    if (clazz.statusCode.equals("200")) {
                        if (clazz.resultData.representYN.equals("N")) {
                            LogUtil.d("1representYN = "+clazz.resultData.representYN);
                            BaseActivity.isWithRepresent = true;
                            dismiss();

                        } else {
                            LogUtil.d("2representYN = "+clazz.resultData.representYN);
                            data = clazz.resultData;

                            tvGoWith.setText(data.roomInfo.getTitle() +" (바로가기)");

                            try {
                                if (null != data.memberStudy.voca) {
                                    LinearLayout root = (LinearLayout) getLayoutInflater().inflate(R.layout.item_with_bottom_sheet_dialog, null);
                                    TextView tvSubject = root.findViewById(R.id.tv_subject);
                                    TextView tvCount = root.findViewById(R.id.tv_count);
                                    TextView tvResult = root.findViewById(R.id.tv_result);

                                    int total = data.memberStudy.voca.total;
                                    int target = data.memberStudy.voca.target;
                                    tvSubject.setText("어휘학습");
                                    tvCount.setText(total + "/" + target);

                                    if (data.memberStudy.voca.completeYN.equals("Y")) {
                                        tvResult.setText("완료");

                                        if(BuildConfig.APP_NAME.equals(BaseActivity.ENGLISH)){
                                            EnglishWordMainActivity.withVoca = true;

                                        }else if(BuildConfig.APP_NAME.equals(BaseActivity.KOREAN)){
                                            WordMainActivity.withVoca = true;
                                        }

                                    } else {
                                        tvResult.setText("미완료");
                                    }
                                    llRoot.addView(root);
                                }
                            } catch (Exception e) {
                                e.toString();
                                EnglishWordMainActivity.withVoca = true;
                            }

                            try {
                                if (null != data.memberStudy.life) {
                                    LinearLayout root = (LinearLayout) getLayoutInflater().inflate(R.layout.item_with_bottom_sheet_dialog, null);
                                    TextView tvSubject = root.findViewById(R.id.tv_subject);
                                    TextView tvCount = root.findViewById(R.id.tv_count);
                                    TextView tvResult = root.findViewById(R.id.tv_result);

                                    tvSubject.setText("생활영어");
                                    tvCount.setText(data.memberStudy.life.total + "/" + data.memberStudy.life.target);
                                    if (data.memberStudy.life.completeYN.equals("Y")) {
                                        tvResult.setText("완료");
                                        EverydayEnglishMainActivity.withEveryday = true;
                                    } else {
                                        tvResult.setText("미완료");
                                    }
                                    llRoot.addView(root);
                                }
                            } catch (Exception e) {
                                e.toString();
                                EverydayEnglishMainActivity.withEveryday = true;
                            }

                            try {
                                if (null != data.memberStudy.gram) {
                                    LinearLayout root = (LinearLayout) getLayoutInflater().inflate(R.layout.item_with_bottom_sheet_dialog, null);
                                    TextView tvSubject = root.findViewById(R.id.tv_subject);
                                    TextView tvCount = root.findViewById(R.id.tv_count);
                                    TextView tvResult = root.findViewById(R.id.tv_result);
//                                    TextView tvRank = root.findViewById(R.id.tv_rank);

                                    tvSubject.setText("문법");
                                    tvCount.setText(data.memberStudy.gram.total + "/" + data.memberStudy.gram.target);
                                    if (data.memberStudy.gram.completeYN.equals("Y")) {
                                        tvResult.setText("완료");
                                        GrammarMainActivity.withGramma = true;
                                    } else {
                                        tvResult.setText("미완료");
                                    }
                                    llRoot.addView(root);
                                }
                            } catch (Exception e) {
                                e.toString();
                                GrammarMainActivity.withGramma = true;
                            }

                            try {
                                if (null != data.memberStudy.quest) {
                                    LinearLayout root = (LinearLayout) getLayoutInflater().inflate(R.layout.item_with_bottom_sheet_dialog, null);
                                    TextView tvSubject = root.findViewById(R.id.tv_subject);
                                    TextView tvCount = root.findViewById(R.id.tv_count);
                                    TextView tvResult = root.findViewById(R.id.tv_result);

                                    int total = data.memberStudy.quest.total;
                                    int target = data.memberStudy.quest.target;
                                    tvSubject.setText("강화학습");
                                    tvCount.setText(total + "/" + target);

                                    if (data.memberStudy.quest.completeYN.equals("Y")) {
                                        tvResult.setText("완료");
                                        DanwonMainActivity.withQuest = true;

                                    } else {
                                        tvResult.setText("미완료");
                                    }
                                    llRoot.addView(root);
                                }
                            } catch (Exception e) {
                                e.toString();
                                DanwonMainActivity.withQuest = true;
                            }

                            try {
                                if (null != data.memberStudy.ox) {
                                    LinearLayout root = (LinearLayout) getLayoutInflater().inflate(R.layout.item_with_bottom_sheet_dialog, null);
                                    TextView tvSubject = root.findViewById(R.id.tv_subject);
                                    TextView tvCount = root.findViewById(R.id.tv_count);
                                    TextView tvResult = root.findViewById(R.id.tv_result);

                                    int total = data.memberStudy.ox.total;
                                    int target = data.memberStudy.ox.target;
                                    tvSubject.setText("OX");
                                    tvCount.setText(total + "/" + target);

                                    if (data.memberStudy.ox.completeYN.equals("Y")) {
                                        tvResult.setText("완료");
                                        RandomOXMainActivity.withOX = true;

                                    } else {
                                        tvResult.setText("미완료");
                                    }
                                    llRoot.addView(root);
                                }
                            } catch (Exception e) {
                                e.toString();
                                RandomOXMainActivity.withOX = true;
                            }

                            try {
                                if (null != data.memberStudy.note) {
                                    LinearLayout root = (LinearLayout) getLayoutInflater().inflate(R.layout.item_with_bottom_sheet_dialog, null);
                                    TextView tvSubject = root.findViewById(R.id.tv_subject);
                                    TextView tvCount = root.findViewById(R.id.tv_count);
                                    TextView tvResult = root.findViewById(R.id.tv_result);

                                    tvSubject.setText("노트");
                                    tvCount.setText(data.memberStudy.note.total + "/" + data.memberStudy.note.target);
                                    if (data.memberStudy.note.completeYN.equals("Y")) {
                                        tvResult.setText("완료");
                                        SmartNoteMainActivity.withNote = true;
                                    } else {
                                        tvResult.setText("미완료");
                                    }
                                    llRoot.addView(root);
                                }
                            } catch (Exception e) {
                                e.toString();
                                SmartNoteMainActivity.withNote = true;
                            }

                            try {
                                if (null != data.memberStudy.past) {
                                    LinearLayout root = (LinearLayout) getLayoutInflater().inflate(R.layout.item_with_bottom_sheet_dialog, null);
                                    TextView tvSubject = root.findViewById(R.id.tv_subject);
                                    TextView tvCount = root.findViewById(R.id.tv_count);
                                    TextView tvResult = root.findViewById(R.id.tv_result);

                                    tvSubject.setText("기출");
                                    tvCount.setText(data.memberStudy.past.total + "/" + data.memberStudy.past.target);
                                    if (data.memberStudy.past.completeYN.equals("Y")) {
                                        tvResult.setText("완료");
                                        PreTestMainActivity.withPast = true;
                                    } else {
                                        tvResult.setText("미완료");
                                    }
                                    llRoot.addView(root);
                                }
                            } catch (Exception e) {
                                e.toString();
                                PreTestMainActivity.withPast = true;
                            }
                        }
                    }
                }

                @Override
                public void onFail(Call call, String msg) {
                    LogUtil.d("onFail : "+msg);
                }
            });
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            BaseActivity.isCheckWithDialog = true;
        } else {
            BaseActivity.isCheckWithDialog = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                dismiss();
                break;

            case R.id.tv_go_with:
                Intent withIntent = new Intent(getActivity(), StudyGroupStatus1Activity.class);
                withIntent.putExtra("data", data.roomInfo);
                startActivity(withIntent);
                dismiss();
                break;
        }
    }
}

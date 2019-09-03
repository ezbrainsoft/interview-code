package com.jlesoft.civilservice.koreanhistoryexam9.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatRadioButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jlesoft.civilservice.koreanhistoryexam9.BaseActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.DialogTypeEnum;
import com.jlesoft.civilservice.koreanhistoryexam9.MainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.PrefConsts;
import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.network.NetworkResponse;
import com.jlesoft.civilservice.koreanhistoryexam9.network.RequestData;
import com.jlesoft.civilservice.koreanhistoryexam9.setting.payment.PaymentListActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.user.LoginActivity;

import java.util.ArrayList;

import retrofit2.Call;

public class DialogUtil {

    /**
     * 2버튼 dialog
     *
     * @param context
     * @param type     :DialogTypeEnum
     * @param message  :MESSAGE 내용
     * @param listener :SAVE, OK, DELETE 등 처리 버튼 리스너
     * @return
     */
    public static Dialog showBaseMessageDialog(final Context context, final DialogTypeEnum type, final String message, final View.OnClickListener listener) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_base_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        final TextView tvMsg = (TextView) dialog.findViewById(R.id.tvMessage);
        final TextView tvOk = (TextView) dialog.findViewById(R.id.btnOk);
        final TextView tvCancel = (TextView) dialog.findViewById(R.id.btnCancel);
        final ImageView ivIcon = (ImageView) dialog.findViewById(R.id.ivIcon);

        tvMsg.setText(message);

        if (type == DialogTypeEnum.OK) {
            tvOk.setText("확인");
            tvOk.setBackgroundColor(context.getResources().getColor(R.color.col_5A));
            ivIcon.setImageResource(R.drawable.ic_pop_complate);
            tvCancel.setVisibility(View.GONE);
        } else if (type == DialogTypeEnum.OKCANCEL) {
            tvOk.setText("확인");
        } else if (type == DialogTypeEnum.SAVE) {
            tvOk.setText("저장");
        } else if (type == DialogTypeEnum.DELETE) {
            tvOk.setText("삭제");
        }

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        return dialog;
    }

    public static Dialog showEngQuizSelectDialog(final Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_content_radio_btn);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        final RadioGroup rbGroup = dialog.findViewById(R.id.rg);
        final RadioButton rbWord = dialog.findViewById(R.id.rb_word);
        final RadioButton rbMean = dialog.findViewById(R.id.rb_mean);

        boolean quizMode = PrefHelper.getBoolean(context, PrefConsts.ENG_QUIZ_SETTING, false);


        if(quizMode == false) rbWord.setChecked(true);
        else    rbMean.setChecked(true);

        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_word){
                    PrefHelper.setBoolean(context, PrefConsts.ENG_QUIZ_SETTING, false);

                }else if(checkedId == R.id.rb_mean){
                    PrefHelper.setBoolean(context, PrefConsts.ENG_QUIZ_SETTING, true);
                }
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public static void showTodayState(final Context context, String userCode) {

        JsonObject jo = new JsonObject();
        jo.addProperty("user_code", userCode);

        RequestData.getInstance().getMemberToday(jo, new NetworkResponse<JsonObject>() {
            @Override
            public void onSuccess(Call call, JsonObject clazz) {

                String statusCode = clazz.get("statusCode").getAsString();

                if (statusCode.equals("200")) {
                    JsonObject obj = clazz.getAsJsonObject("resultData");
                    String cnt = obj.get("q_cnt").getAsString();

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("오늘 학습 콘텐츠 이용량");
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View dialogView = inflater.inflate(R.layout.dialog_today_state_layout, null);
                    TextView todayCnt = dialogView.findViewById(R.id.tv_today_count);
                    todayCnt.setText(cnt + "/30개");
                    TextView tvMission = dialogView.findViewById(R.id.tv_mission_state);
                    if (Integer.parseInt(cnt) >= 30) {
                        tvMission.setText("완료!");
                    } else {
                        tvMission.setText("미완료");
                    }

                    builder.setView(dialogView);
                    builder.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            }

            @Override
            public void onFail(Call call, String msg) {
            }
        });
    }

    public static Dialog showTextSizeDialog(final Context context, final View.OnClickListener listener) {
        if(!((Activity)context).isFinishing()) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setTitle("글자크기조정");
            dialog.setContentView(R.layout.dialog_text_size);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setDimAmount(0.7f);

            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);

            int fontSize = PrefHelper.getInt(context, PrefConsts.POPUP_FONT_SIZE, PrefConsts.FONT_SIZE_1);
            switch (fontSize) {
                case PrefConsts.FONT_SIZE_1_3:
                    RadioButton btn13 = (RadioButton) dialog.findViewById(R.id.btn_more_small3_text);
                    btn13.setChecked(true);
                    break;
                case PrefConsts.FONT_SIZE_1_2:
                    RadioButton btn12 = (RadioButton) dialog.findViewById(R.id.btn_more_small2_text);
                    btn12.setChecked(true);
                    break;
                case PrefConsts.FONT_SIZE_1:
                    RadioButton btn = (RadioButton) dialog.findViewById(R.id.btn_more_small1_text);
                    btn.setChecked(true);
                    break;
                case PrefConsts.FONT_SIZE_2:
                    RadioButton btn2 = (RadioButton) dialog.findViewById(R.id.btn_small_text);
                    btn2.setChecked(true);
                    break;
                case PrefConsts.FONT_SIZE_3:
                    RadioButton btn3 = (RadioButton) dialog.findViewById(R.id.btn_nomal_text);
                    btn3.setChecked(true);
                    break;
                case PrefConsts.FONT_SIZE_4:
                    RadioButton btn4 = (RadioButton) dialog.findViewById(R.id.btn_big_text);
                    btn4.setChecked(true);
                    break;
                case PrefConsts.FONT_SIZE_5:
                    RadioButton btn5 = (RadioButton) dialog.findViewById(R.id.btn_more_big_text);
                    btn5.setChecked(true);
                    break;

            }

            dialog.show();


            ((TextView) dialog.findViewById(R.id.btnCancel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            ((TextView) dialog.findViewById(R.id.btnOk)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroup);
                    int checkedId = radioGroup.getCheckedRadioButtonId();
                    switch (checkedId) {
                        case R.id.btn_more_small3_text:
                            BaseActivity.fontSize = PrefConsts.FONT_SIZE_1_3;
                            PrefHelper.setInt(context, PrefConsts.POPUP_FONT_SIZE, PrefConsts.FONT_SIZE_1_3);
                            break;

                        case R.id.btn_more_small2_text:
                            BaseActivity.fontSize = PrefConsts.FONT_SIZE_1_2;
                            PrefHelper.setInt(context, PrefConsts.POPUP_FONT_SIZE, PrefConsts.FONT_SIZE_1_2);
                            break;

                        case R.id.btn_more_small1_text:
                            BaseActivity.fontSize = PrefConsts.FONT_SIZE_1;
                            PrefHelper.setInt(context, PrefConsts.POPUP_FONT_SIZE, PrefConsts.FONT_SIZE_1);
                            break;

                        case R.id.btn_small_text:
                            BaseActivity.fontSize = PrefConsts.FONT_SIZE_2;
                            PrefHelper.setInt(context, PrefConsts.POPUP_FONT_SIZE, PrefConsts.FONT_SIZE_2);
                            break;

                        case R.id.btn_nomal_text:
                            BaseActivity.fontSize = PrefConsts.FONT_SIZE_3;
                            PrefHelper.setInt(context, PrefConsts.POPUP_FONT_SIZE, PrefConsts.FONT_SIZE_3);
                            break;

                        case R.id.btn_big_text:
                            BaseActivity.fontSize = PrefConsts.FONT_SIZE_4;
                            PrefHelper.setInt(context, PrefConsts.POPUP_FONT_SIZE, PrefConsts.FONT_SIZE_4);
                            break;

                        case R.id.btn_more_big_text:
                            BaseActivity.fontSize = PrefConsts.FONT_SIZE_5;
                            PrefHelper.setInt(context, PrefConsts.POPUP_FONT_SIZE, PrefConsts.FONT_SIZE_5);
                            break;
                    }

                    listener.onClick(dialog.getWindow().getDecorView());
                    dialog.dismiss();

                }
            });
            return dialog;
        }else {
            return null;
        }
    }

    public static Dialog showImgSmartNoteMemoDialog(Context context, String content, View.OnClickListener listener) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_img_notice);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setDimAmount(0.7f);

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();

        dialog.findViewById(R.id.img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        return dialog;
    }

    public static Dialog showDefaultCoachmarkDialog(final Context context, final String type) {
        RelativeLayout rl = null;
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        switch (type) {
            case "review_button":
                dialog.setContentView(R.layout.dialog_review_button_coachmark);
                break;

            case "refine_button":
                dialog.setContentView(R.layout.dialog_refine_button_coachmark);
                break;

            case "review":
                dialog.setContentView(R.layout.dialog_review_coachmark);
                break;

            case "review_plus":
                dialog.setContentView(R.layout.dialog_review_plus_coachmark);
                break;

            case "wrongnote_explain":
                dialog.setContentView(R.layout.dialog_wrong_answer_explain_coachmark);
                break;
        }
        rl = (RelativeLayout) dialog.findViewById(R.id.rl_coachmark);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        dialog.show();

        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        return dialog;
    }

    public static Dialog showClickCoachmarkDialog(final BaseActivity activity, final String type, final View.OnClickListener clickListener) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        RelativeLayout rl = null;
        ImageButton btnNext = null;

        switch (type) {
            case "tutorial_start": // 5p
                dialog.setContentView(R.layout.dialog_tq1);
                btnNext = (ImageButton) dialog.findViewById(R.id.btn_next);
                break;

            case "tutorial_introduce_review": // 8p
                dialog.setContentView(R.layout.dialog_tq4);
                btnNext = (ImageButton) dialog.findViewById(R.id.btn_next);
                break;

            case "tutorial_last": // 15p
                dialog.setContentView(R.layout.dialog_tq6);
                btnNext = (ImageButton) dialog.findViewById(R.id.btn_next);
                break;
        }

        rl = (RelativeLayout) dialog.findViewById(R.id.rl_coachmark);
        final RelativeLayout rlForTag = (RelativeLayout) dialog.findViewById(R.id.rl_forTag);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        dialog.show();

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
                activity.finish();

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlForTag.setTag(type);
                clickListener.onClick(dialog.getWindow().getDecorView());
                dialog.dismiss();
            }
        });
        return dialog;
    }

    //오류내용
    public static void showErrorReportListDialog(final Context context, String userCode, String ipIdx) {
        if (CommonUtils.getConnectNetwork(context)) {
            JsonObject jo = new JsonObject();
            jo.addProperty("user_code", userCode);
            jo.addProperty("ip_idx", ipIdx);


            RequestData.getInstance().getQuestionReportStaus(jo, new NetworkResponse<JsonObject>() {
                @Override
                public void onSuccess(Call call, JsonObject clazz) {
                    if (clazz.get("statusCode").getAsString().equals("200")) {
                        JsonObject obj = clazz.get("resultData").getAsJsonObject();

                        if (Integer.parseInt(obj.get("cnt").getAsString()) > 0) {

                            JsonArray list = obj.get("list").getAsJsonArray();

                            JsonObject item;
                            ArrayList<String> strArr = new ArrayList<>();

                            for (int i = 0; i < list.size(); i++) {
                                item = list.get(i).getAsJsonObject();
                                strArr.add(new StringBuilder().append("- ").append(item.get("wdate").getAsString()).append(". ").append(item.get("report_result").getAsString()).toString());
                            }

                            final Dialog dialog = new Dialog(context);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.dialog_error_list);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                            dialog.setCancelable(false);
                            dialog.setCanceledOnTouchOutside(false);

                            dialog.show();

                            ((ListView) dialog.findViewById(R.id.lv_error_content)).setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, strArr));

                            (dialog.findViewById(R.id.tv_close)).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });
                        } else {
                            Toast.makeText(context, context.getString(R.string.msg_no_error_content), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFail(Call call, String msg) {
                    Toast.makeText(context, context.getString(R.string.server_error_default_msg), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(context, context.getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
        }
    }

    public static Dialog showImgNoticePopupDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_img_notice);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setDimAmount(0.7f);

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();

        dialog.findViewById(R.id.img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        return dialog;
    }

    public static Dialog showNoticePopupDialog(final Context context, String content) {
        if(!((MainActivity) context).isFinishing()){
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_notice);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setDimAmount(0.7f);

            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);


            dialog.show();
            ((TextView) dialog.findViewById(R.id.tvContent)).setText(content);

            (dialog.findViewById(R.id.close_txt)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            (dialog.findViewById(R.id.tvClose)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            return dialog;
        }else{
            return null;
        }
    }

    public static AlertDialog.Builder showCheckNetwork(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("인터넷 연결이 원할하지 않습니다.");
        builder.setPositiveButton("재시도", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNeutralButton("취소", null);

        builder.show();

        return builder;
    }

    public static Dialog showEventDialog(final Context context, final View.OnClickListener listener) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_event);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        dialog.show();

        final TextView tvEvent = (TextView) dialog.findViewById(R.id.tv_event);
        ImageButton event1 = (ImageButton) dialog.findViewById(R.id.btn_event1);
        ImageButton event2 = (ImageButton) dialog.findViewById(R.id.btn_event2);

        tvEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefHelper.setBoolean(context, PrefConsts.EVENT_DISPLAY, false);
                dialog.dismiss();
            }
        });

        event1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvEvent.setTag("openevent");
                listener.onClick(dialog.getWindow().getDecorView());
            }
        });

        event2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvEvent.setTag("survey");
                listener.onClick(dialog.getWindow().getDecorView());
            }
        });

        ImageButton close = (ImageButton) dialog.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public static Dialog showStudyRuleDialog(final Context context, final View.OnClickListener listener) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_member_service_scroll_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        final Button btnClose = (Button) dialog.findViewById(R.id.btnClose);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });

        return dialog;
    }

    public static Dialog showServiceTermsDialog(final Context context, final View.OnClickListener listener) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_member_service_scroll_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        final Button btnClose = (Button) dialog.findViewById(R.id.btnClose);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });

        return dialog;
    }

    public static Dialog showPrivateTermsDialog(final Context context, final DialogTypeEnum type, final View.OnClickListener listener) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_text_scroll_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final Button btnClose = (Button) dialog.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });

        dialog.show();

        return dialog;
    }

    public static Dialog showChargeDialog(final Context context){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_purcharse_move);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnGoPurcharse = dialog.findViewById(R.id.btn_go_purcharse);
        Button btnRestorePurcharse = dialog.findViewById(R.id.btn_restore_purcharse);
        Button btnLogin = dialog.findViewById(R.id.btn_login);
        Button btnClose = dialog.findViewById(R.id.btn_close);

        if(!TextUtils.isEmpty(BaseActivity.userID)){
            btnLogin.setVisibility(View.GONE);
        }
        if(BaseActivity.buyYN.equalsIgnoreCase("Y")){
            btnRestorePurcharse.setVisibility(View.VISIBLE);
        }else{
            btnRestorePurcharse.setVisibility(View.GONE);
        }

        btnGoPurcharse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PaymentListActivity.class));
                dialog.dismiss();
            }
        });

        btnRestorePurcharse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PaymentListActivity.class);
                intent.putExtra("current", 1);
                context.startActivity(intent);
                dialog.dismiss();

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, LoginActivity.class));
                dialog.dismiss();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

        return dialog;
    }
}
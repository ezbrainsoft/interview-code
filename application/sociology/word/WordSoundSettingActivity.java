package com.jlesoft.civilservice.koreanhistoryexam9.word;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jlesoft.civilservice.koreanhistoryexam9.BaseActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.PrefConsts;
import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.util.LogUtil;
import com.jlesoft.civilservice.koreanhistoryexam9.util.PrefHelper;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class WordSoundSettingActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.ll_v)
    LinearLayout llV;//표준어 / 외래어 / 사자성어 / 고유어
    @BindView(R.id.ll_y)
    LinearLayout llY;//한자어

    @BindView(R.id.rg_order)
    RadioGroup rgOrder;
    @BindView(R.id.rg_refresh)
    RadioGroup rgRefresh;
    @BindView(R.id.rg_speed)
    RadioGroup rgSpeed;


    @BindView(R.id.cb_word)
    CheckBox cb_word;
    @BindView(R.id.cb_mean)
    CheckBox cb_mean;
    @BindView(R.id.cb_exam)
    CheckBox cb_exam;

    @BindView(R.id.cb_y_word)
    CheckBox cbYWord;
    @BindView(R.id.cb_y_word_syno)
    CheckBox cbYWordSyno;
    @BindView(R.id.cb_y_word_mean)
    CheckBox cbYWordMean;
    @BindView(R.id.cb_y_word_exam)
    CheckBox cbYWordExam;

    @BindView(R.id.rb_order)
    RadioButton rb_order;
    @BindView(R.id.rb_random)
    RadioButton rb_random;
    @BindView(R.id.rb_one)
    RadioButton rb_one;
    @BindView(R.id.rb_infinity)
    RadioButton rb_infinity;

    @BindView(R.id.rb_1)
    RadioButton rb_1;
    @BindView(R.id.rb_2)
    RadioButton rb_2;
    @BindView(R.id.rb_3)
    RadioButton rb_3;
    @BindView(R.id.rb_4)
    RadioButton rb_4;
    @BindView(R.id.rb_5)
    RadioButton rb_5;

    String sqClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korean_word_sound_setting);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sqClass = getIntent().getStringExtra("sq_class");

        if(TextUtils.isEmpty(sqClass)){
            finish();

        }else{
            ButterKnife.bind(this);

            tvTitle.setText("듣기설정");

            rgOrder.setOnCheckedChangeListener(this);
            rgRefresh.setOnCheckedChangeListener(this);
            rgSpeed.setOnCheckedChangeListener(this);

            if (sqClass.equals("V")) {// 표준어

                llV.setVisibility(View.VISIBLE);
                boolean word = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_WORD, true);
                boolean mean = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_MEAN, false);
                boolean exam = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_EXAM, false);

                LogUtil.d("onCreate "+sqClass + " && "+ word+" && "+mean+ " && "+exam);

                if (word) cb_word.setChecked(true);
                if (mean) cb_mean.setChecked(true);
                if (exam) cb_exam.setChecked(true);

            } else if (sqClass.equals("W")) {//외래어

                llV.setVisibility(View.VISIBLE);
                cb_exam.setVisibility(View.GONE);
                boolean word = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_W_WORD, true);
                boolean mean = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_W_MEAN, false);

                LogUtil.d("onCreate "+sqClass + " && "+ word+" && "+mean);

                if (word) cb_word.setChecked(true);
                if (mean) cb_mean.setChecked(true);

            } else if (sqClass.equals("X")) {//사자성어

                llV.setVisibility(View.VISIBLE);

                boolean word = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_X_WORD, true);
                boolean mean = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_X_MEAN, false);
                boolean exam = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_X_EXAM, false);

                cb_word.setText("사자성어");
                cb_mean.setText("한자");
                cb_exam.setText("예문");

                if (word) cb_word.setChecked(true);
                if (mean) cb_mean.setChecked(true);
                if (exam) cb_exam.setChecked(true);

            } else if (sqClass.equals("Y")) {//한자어

                llY.setVisibility(View.VISIBLE);

                boolean word = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_Y_WORD, true);
                boolean mean = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_Y_MEAN, false);
                boolean exam = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_Y_EXAM, false);
                boolean syno = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_Y_SYNONYM, false);

                LogUtil.d("onCreate "+sqClass + " && "+ word+" && "+syno+" && "+mean+ " && "+exam);

                if (word) cbYWord.setChecked(true);
                if (mean) cbYWordMean.setChecked(true);
                if (exam) cbYWordExam.setChecked(true);
                if (syno)  cbYWordSyno.setChecked(true);

            } else if (sqClass.equals("F")) {//고유어

                llV.setVisibility(View.VISIBLE);
                boolean word = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_F_WORD, true);
                boolean mean = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_F_MEAN, false);
                boolean exam = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_F_EXAM, false);

                LogUtil.d("onCreate "+sqClass + " && "+ word+" && "+mean+ " && "+exam);

                if (word) cb_word.setChecked(true);
                if (mean) cb_mean.setChecked(true);
                if (exam) cb_exam.setChecked(true);

            }

            String order = PrefHelper.getString(this, PrefConsts.WORD_KEY_ORDER, PrefConsts.ORDER);
            String refresh = PrefHelper.getString(this, PrefConsts.WORD_KEY_REFRESH, PrefConsts.REFRESH_ONE);
            float speed = PrefHelper.getFloat(this, PrefConsts.WORD_KEY_SPEED, 1.25f);

            if (order.equals(PrefConsts.ORDER)) {
                rb_order.setChecked(true);
            } else {
                rb_random.setChecked(true);
            }

            if (refresh.equals(PrefConsts.REFRESH_ONE)) {
                rb_one.setChecked(true);
            } else {
                rb_infinity.setChecked(true);
            }

            if (speed == 0.75f) {
                rb_1.setChecked(true);
            } else if (speed == 1.00f) {
                rb_2.setChecked(true);
            } else if (speed == 1.25f) {
                rb_3.setChecked(true);
            } else if (speed == 1.75f) {
                rb_4.setChecked(true);
            } else if (speed == 2.25f) {
                rb_5.setChecked(true);
            }
        }


    }

    @OnCheckedChanged({R.id.cb_word, R.id.cb_mean, R.id.cb_exam, R.id.cb_y_word , R.id.cb_y_word_syno , R.id.cb_y_word_mean , R.id.cb_y_word_exam})
    void checkWord(CompoundButton button, boolean checked) {
        switch (button.getId()) {
            case R.id.cb_word:
                cb_word.setChecked(checked);
                if (sqClass.equals("V"))
                    PrefHelper.setBoolean(this, PrefConsts.WORD_KEY_LISTENER_WORD, checked);
                 else if(sqClass.equals("F"))
                    PrefHelper.setBoolean(this, PrefConsts.WORD_KEY_LISTENER_F_WORD, checked);
                 else if(sqClass.equals("X"))
                    PrefHelper.setBoolean(this, PrefConsts.WORD_KEY_LISTENER_X_WORD, checked);
                else
                    PrefHelper.setBoolean(this, PrefConsts.WORD_KEY_LISTENER_W_WORD, checked);
                break;

            case R.id.cb_mean:
                cb_mean.setChecked(checked);
                if (sqClass.equals("V"))
                    PrefHelper.setBoolean(this, PrefConsts.WORD_KEY_LISTENER_MEAN, checked);
                else if(sqClass.equals("F"))
                    PrefHelper.setBoolean(this, PrefConsts.WORD_KEY_LISTENER_F_MEAN, checked);
                else if(sqClass.equals("X"))
                    PrefHelper.setBoolean(this, PrefConsts.WORD_KEY_LISTENER_X_MEAN, checked);
                else
                    PrefHelper.setBoolean(this, PrefConsts.WORD_KEY_LISTENER_W_MEAN, checked);
                break;

            case R.id.cb_exam:
                cb_exam.setChecked(checked);
                if (sqClass.equals("V"))
                    PrefHelper.setBoolean(this, PrefConsts.WORD_KEY_LISTENER_EXAM, checked);
                else if(sqClass.equals("F"))
                    PrefHelper.setBoolean(this, PrefConsts.WORD_KEY_LISTENER_F_EXAM, checked);
                else if(sqClass.equals("X"))
                    PrefHelper.setBoolean(this, PrefConsts.WORD_KEY_LISTENER_X_EXAM, checked);
                else
                    PrefHelper.setBoolean(this, PrefConsts.WORD_KEY_LISTENER_F_MEAN, checked);
                break;

                // 한자어
            case R.id.cb_y_word:
                PrefHelper.setBoolean(this, PrefConsts.WORD_KEY_LISTENER_Y_WORD, checked);
                break;
            case R.id.cb_y_word_mean:
                PrefHelper.setBoolean(this, PrefConsts.WORD_KEY_LISTENER_Y_MEAN, checked);
                break;
            case R.id.cb_y_word_exam:
                PrefHelper.setBoolean(this, PrefConsts.WORD_KEY_LISTENER_Y_EXAM, checked);
                break;
            case R.id.cb_y_word_syno:
                PrefHelper.setBoolean(this, PrefConsts.WORD_KEY_LISTENER_Y_SYNONYM, checked);
                break;
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_order:
                PrefHelper.setString(this, PrefConsts.WORD_KEY_ORDER, PrefConsts.ORDER);
                break;
            case R.id.rb_random:
                PrefHelper.setString(this, PrefConsts.WORD_KEY_ORDER, PrefConsts.ORDER_RANDOM);
                break;

            case R.id.rb_one:
                PrefHelper.setString(this, PrefConsts.WORD_KEY_REFRESH, PrefConsts.REFRESH_ONE);
                break;
            case R.id.rb_infinity:
                PrefHelper.setString(this, PrefConsts.WORD_KEY_REFRESH, PrefConsts.REFRESH_INFINITE);
                break;

            case R.id.rb_1:
                PrefHelper.setFloat(this, PrefConsts.WORD_KEY_SPEED, 0.75f);
                break;
            case R.id.rb_2:
                PrefHelper.setFloat(this, PrefConsts.WORD_KEY_SPEED, 1.00f);
                break;
            case R.id.rb_3:
                PrefHelper.setFloat(this, PrefConsts.WORD_KEY_SPEED, 1.25f);
                break;
            case R.id.rb_4:
                PrefHelper.setFloat(this, PrefConsts.WORD_KEY_SPEED, 1.75f);
                break;
            case R.id.rb_5:
                PrefHelper.setFloat(this, PrefConsts.WORD_KEY_SPEED, 2.25f);
                break;
        }
    }

    @OnClick(R.id.btnBack)
    void btnBack() {
        finish();
    }

    @OnClick(R.id.btn_listen)
    void clickListen() {
        setResult(RESULT_OK);
        finish();
    }
}

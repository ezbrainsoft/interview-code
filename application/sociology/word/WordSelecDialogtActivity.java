package com.jlesoft.civilservice.koreanhistoryexam9.word;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.util.CommonUtils;
import com.jlesoft.civilservice.koreanhistoryexam9.word.model.WordQuestionDao;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WordSelecDialogtActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.tableLayout)
    TableLayout tableLay;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.ll_correct_root)
    LinearLayout llCorrectRoot;

    private LayoutInflater inflater;
    ArrayList<WordQuestionDao.WordQuestionItem>  dataArr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setContentView(R.layout.activity_main_test_question_select);

        ButterKnife.bind(this);

        dataArr = (ArrayList<WordQuestionDao.WordQuestionItem> ) getIntent().getSerializableExtra("data");

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        setDisplaySize();

        BindUi();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setDisplaySize();
    }

    private void setDisplaySize() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);


        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        defaultDisplay.getSize(size);

        root.getLayoutParams().width = size.x - CommonUtils.dpToPx(this, 20);
    }

    private void BindUi() {

        int count = dataArr.size();
        int line = count / 5;
        if (count % 5 > 0) {
            line++;
        }

        int num = 0;
        for (int i = 0; i < line; i++) {
            TableRow row = (TableRow) inflater.inflate(R.layout.item_main_test_question_select_num, null);

            for (int j = 0; j < 5; j++) {
                LinearLayout root = (LinearLayout) row.getChildAt(j);

                TextView tvNum = (TextView) root.getChildAt(0);
                if (num + 1 <= count) {
                    if (dataArr.get(num).getSolvedYN().equalsIgnoreCase("N")) {
                        tvNum.setBackgroundColor(getResources().getColor(R.color.col_D0CD));
                    }else{
                        if(dataArr.get(num).getCsIsResult().equals("f")){
                            tvNum.setBackgroundColor(getResources().getColor(R.color.col_fbe5d6));
                        }
                    }
                    tvNum.setText(num + 1 + "");
                    root.setTag(num);
                    root.setOnClickListener(this);
                } else {
                    tvNum.setBackgroundColor(Color.TRANSPARENT);
                }

                num++;
            }
            tableLay.addView(row);
        }
    }

    @Override
    public void onClick(View v) {
        int num = (int) v.getTag();
        Intent intent = new Intent();
        intent.putExtra("num", num);
        setResult(RESULT_OK, intent);
        finish();

    }

    @OnClick(R.id.ll_close_btn)
    void clickClose() {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}

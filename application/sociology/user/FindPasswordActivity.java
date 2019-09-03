package com.jlesoft.civilservice.koreanhistoryexam9.user;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jlesoft.civilservice.koreanhistoryexam9.BaseActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.network.NetworkResponse;
import com.jlesoft.civilservice.koreanhistoryexam9.network.RequestData;
import com.google.gson.JsonObject;
import com.jlesoft.civilservice.koreanhistoryexam9.view.ClearEditText;

import org.json.JSONException;
import org.json.JSONObject;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class FindPasswordActivity extends BaseActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvCount)
    TextView tvCount;

    @BindView(R.id.tvEmail)
    ClearEditText tvEmail;

    @BindView(R.id.tvEmailMessage)
    TextView tvEmailMessage;

    @BindView(R.id.login_progress)
    ProgressBar login_progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvTitle.setText("비밀번호 찾기");
        tvCount.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.btnBack)
    void backButtonClick() {
        finish();
    }

    @OnClick(R.id.email_send_email)
    void emailSendEmailClick() {
        String email = tvEmail.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(email)) {
            tvEmailMessage.setVisibility(View.VISIBLE);
            focusView = tvEmail;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            JsonObject jo = new JsonObject();
            jo.addProperty("user_id", email);

            login_progress.setVisibility(View.VISIBLE);
            RequestData.getInstance().memberFindPw(jo, new NetworkResponse<JsonObject>() {
                @Override
                public void onSuccess(Call call, JsonObject clazz) {
                    try {
                        JSONObject responseJO = new JSONObject(clazz.toString());
                        String resultData = responseJO.getString("resultData");

                        JSONObject resultDataJO = new JSONObject(resultData);

                        if (resultDataJO.getString("subStatus").equalsIgnoreCase("ERROR")) {
                            Toast.makeText(FindPasswordActivity.this, "전송실패. 이메일을 확인해주세요.", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(FindPasswordActivity.this, "임시 비밀번호를 전송 하였습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        login_progress.setVisibility(View.GONE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFail(Call call, String msg) {

                }
            });
        }
    }
}

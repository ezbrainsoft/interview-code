package com.jlesoft.civilservice.koreanhistoryexam9.user;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jlesoft.civilservice.koreanhistoryexam9.BaseActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.PrefConsts;
import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.network.NetworkResponse;
import com.jlesoft.civilservice.koreanhistoryexam9.network.RequestData;
import com.jlesoft.civilservice.koreanhistoryexam9.util.PrefHelper;
import com.google.gson.JsonObject;
import com.jlesoft.civilservice.koreanhistoryexam9.view.ClearEditText;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class MyInfoActivity extends BaseActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvCount)
    TextView tvCount;

    @BindView(R.id.tvEmail)
    ClearEditText tvEmail;

    @BindView(R.id.tvEmailMessage)
    TextView tvEmailMessage;

    @BindView(R.id.tvPasswordMessage)
    TextView tvPasswordMessage;

    @BindView(R.id.tvPasswordConfirmMessage)
    TextView tvPasswordConfirmMessage;

    @BindView(R.id.login_progress)
    ProgressBar login_progress;

    @BindView(R.id.password_temp)
    ClearEditText password_temp;

    @BindView(R.id.password_new)
    ClearEditText password_new;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);


        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvTitle.setText("내정보");
        tvCount.setVisibility(View.INVISIBLE);

        if (!TextUtils.isEmpty(userID)) {
            tvEmail.setText(userID);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6 && password.length() <= 12;
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
            //임시비밀번호전송
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
                            Toast.makeText(MyInfoActivity.this, "전송실패. 이메일을 확인해주세요.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MyInfoActivity.this, "임시 비밀번호를 전송 하였습니다.", Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.password_change_button)
    void passwordChangeButtonClick() {
        boolean cancel = false;
        View focusView = null;

        tvEmailMessage.setVisibility(View.INVISIBLE);
        tvPasswordMessage.setVisibility(View.INVISIBLE);
        tvPasswordConfirmMessage.setVisibility(View.INVISIBLE);

        String xPassword = password_temp.getText().toString();
        final String newPassword = password_new.getText().toString();

        if (TextUtils.isEmpty(xPassword)) {
            tvPasswordMessage.setVisibility(View.VISIBLE);
            tvPasswordMessage.setText(R.string.input_pw_empty);
            focusView = password_temp;
            cancel = true;
        } else if (!isPasswordValid(xPassword)) {
            tvPasswordMessage.setVisibility(View.VISIBLE);
            tvPasswordMessage.setText(R.string.input_pw_type_error);
            focusView = password_temp;
            cancel = true;
        } else if (TextUtils.isEmpty(newPassword)) {
            tvPasswordConfirmMessage.setVisibility(View.VISIBLE);
            tvPasswordConfirmMessage.setText(R.string.input_pw_empty);
            focusView = password_new;
            cancel = true;
        } else if (!isPasswordValid(newPassword)) {
            tvPasswordConfirmMessage.setVisibility(View.VISIBLE);
            tvPasswordConfirmMessage.setText(R.string.input_pw_type_error);
            focusView = password_new;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            //비밀번호 변경
            JsonObject jo =new JsonObject();
            jo.addProperty("user_id", userID);
            jo.addProperty("user_code", userCode);
            jo.addProperty("new_pw", newPassword);
            jo.addProperty("now_pw", xPassword);

            login_progress.setVisibility(View.VISIBLE);
            RequestData.getInstance().memberUpdatePw(jo, new NetworkResponse<JsonObject>() {
                @Override
                public void onSuccess(Call call, JsonObject clazz) {
                    try {
                        JSONObject responseJO = new JSONObject(clazz.toString());
                        String resultData = responseJO.getString("resultData");

                        JSONObject resultDataJO = new JSONObject(resultData);
                        if (resultDataJO.getString("subStatus").equalsIgnoreCase("ERROR")) {
                            Toast.makeText(MyInfoActivity.this, "비밀번호 변경 실패. 관리자에 문의하세요.", Toast.LENGTH_SHORT).show();
                        } else if (resultDataJO.getString("subStatus").equalsIgnoreCase("PASSWORD NOT MATCH")) {
                            Toast.makeText(MyInfoActivity.this, "패스워드가 일치하지 않습니다. 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MyInfoActivity.this, "비밀번호를 변경하였습니다.", Toast.LENGTH_SHORT).show();
                            PrefHelper.setString(MyInfoActivity.this, PrefConsts.USER_PASSWORD, newPassword);
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

package com.jlesoft.civilservice.koreanhistoryexam9.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.CheckBox;
import android.widget.Toast;

import com.jlesoft.civilservice.koreanhistoryexam9.BaseActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.DialogTypeEnum;
import com.jlesoft.civilservice.koreanhistoryexam9.PrefConsts;
import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.SettingActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.network.NetworkResponse;
import com.jlesoft.civilservice.koreanhistoryexam9.network.RequestData;
import com.jlesoft.civilservice.koreanhistoryexam9.util.DeviceUuidFactory;
import com.jlesoft.civilservice.koreanhistoryexam9.util.DialogUtil;
import com.jlesoft.civilservice.koreanhistoryexam9.util.Dlog;
import com.google.gson.JsonObject;
import com.jlesoft.civilservice.koreanhistoryexam9.util.PrefHelper;
import com.jlesoft.civilservice.koreanhistoryexam9.view.ClearEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import retrofit2.Call;

public class SignUpActivity extends BaseActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvCount)
    TextView tvCount;

    @BindView(R.id.tvEmail)
    ClearEditText tvEmail;

    @BindView(R.id.tvEmailConfirm)
    ClearEditText tvEmailConfirm;

    @BindView(R.id.etPassword)
    ClearEditText etPassword;

    @BindView(R.id.password_confirm)
    ClearEditText etPassword_confirm;

    @BindView(R.id.tvPasswordMessage)
    TextView tvPasswordMessage;

    @BindView(R.id.tvPasswordConfirmMessage)
    TextView tvPasswordConfirmMessage;

    @BindView(R.id.tvEmailMessage)
    TextView tvEmailMessage;

    @BindView(R.id.tvEmailMessageConfirm)
    TextView tvEmailMessageConfirm;

    @BindView(R.id.tvCheckEmailMessage)
    TextView tvCheckEmailMessage;

    @BindView(R.id.tvCheckEmailMessageConfirm)
    TextView tvCheckEmailMessageConfirm;

    @BindView(R.id.tvCheckEmailSuMessage)
    TextView tvCheckEmailSuMessage;

    @BindView(R.id.tvCheckEmailSuMessageConfrim)
    TextView tvCheckEmailSuMessageConfrim;

    @BindView(R.id.login_progress)
    ProgressBar login_progress;

    @BindView(R.id.cbService)
    CheckBox cbService;

    @BindView(R.id.cbPrivate)
    CheckBox cbPrivate;

    @BindView(R.id.tvService)
    TextView tvService;

    @BindView(R.id.tvPrivate)
    TextView tvPrivate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvTitle.setText("회원가입");
        tvCount.setVisibility(View.INVISIBLE);

        tvService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.service_terms_url)));
                startActivity(intent);
            }
        });
        tvPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.private_terms_url)));
                startActivity(intent);
            }
        });
    }

    /**
     * 가입 완료후 Dialog 출력.
     */
    private void showDoneJoin(String msg) {
        DialogUtil.showBaseMessageDialog(SignUpActivity.this, DialogTypeEnum.OK, msg, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("userId", tvEmailConfirm.getText().toString().trim());
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private boolean isEmailValid(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            err = true;
        }
        return err;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6 && password.length() <= 12;
    }

    @OnClick(R.id.btnBack)
    void backButtonClick() {
        finish();
    }

    @OnClick(R.id.email_sign_in_button)
    void emailSignUpButtonClick() {

        tvEmailMessage.setVisibility(View.INVISIBLE);
        tvPasswordMessage.setVisibility(View.INVISIBLE);
        tvPasswordConfirmMessage.setVisibility(View.INVISIBLE);

        String email = tvEmail.getText().toString();
        String emailCorfirm = tvEmailConfirm.getText().toString();
        String password = etPassword.getText().toString();
        String passwordCheck = etPassword_confirm.getText().toString();

        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(email)) {
            tvEmailMessage.setVisibility(View.VISIBLE);
            tvEmailMessage.setText(R.string.input_email_empty);
            focusView = tvEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            tvEmailMessage.setVisibility(View.VISIBLE);
            tvEmailMessage.setText(R.string.input_email_type_error);
            focusView = tvEmail;
            cancel = true;
        }else if(TextUtils.isEmpty(emailCorfirm)) {
            tvEmailMessageConfirm.setVisibility(View.VISIBLE);
            tvEmailMessageConfirm.setText(R.string.input_email_empty);

            focusView = tvEmailConfirm;
            cancel = true;
        }else if(!email.equals(emailCorfirm)){
            tvEmailMessageConfirm.setVisibility(View.VISIBLE);
            tvEmailMessageConfirm.setText(R.string.input_email_again_confirm);

            tvCheckEmailSuMessageConfrim.setVisibility(View.GONE);
            tvCheckEmailMessageConfirm.setVisibility(View.VISIBLE);
            focusView = tvEmailConfirm;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            tvPasswordMessage.setVisibility(View.VISIBLE);
            tvPasswordMessage.setText(R.string.input_pw_empty);
            focusView = etPassword;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            tvPasswordMessage.setVisibility(View.VISIBLE);
            tvPasswordMessage.setText(R.string.input_pw_type_error);
            focusView = etPassword;
            cancel = true;
        } else if (TextUtils.isEmpty(passwordCheck)) {
            tvPasswordConfirmMessage.setVisibility(View.VISIBLE);
            tvPasswordConfirmMessage.setText(R.string.input_pw_empty);
            focusView = etPassword_confirm;
            cancel = true;
        } else if (!isPasswordValid(passwordCheck)) {
            tvPasswordConfirmMessage.setVisibility(View.VISIBLE);
            tvPasswordConfirmMessage.setText(R.string.input_pw_type_error);
            focusView = etPassword_confirm;
            cancel = true;
        } else if (!password.equals(passwordCheck)) {
            tvPasswordConfirmMessage.setVisibility(View.VISIBLE);
            tvPasswordConfirmMessage.setText(R.string.input_pw_compare_not_match);
            focusView = etPassword_confirm;
            cancel = true;
        } else if (!cbPrivate.isChecked() || !cbService.isChecked()) {
            Toast.makeText(this, "이용약관 및 개인정보 취급방침 읽고 체크해주세요.", Toast.LENGTH_SHORT).show();
            cancel = true;
            focusView = cbPrivate;
        }

        String UUID = PrefHelper.getString(this, PrefConsts.PREF_TEMP_DEVICE_ID, "");

        if(TextUtils.isEmpty(UUID)){
            DeviceUuidFactory uuidFactory = new DeviceUuidFactory(SignUpActivity.this);
            UUID = uuidFactory.getDeviceUuid().toString();
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            //회원가입
            login_progress.setVisibility(View.VISIBLE);

            JsonObject jo = new JsonObject();
            jo.addProperty("user_code", userCode);
            jo.addProperty("user_id", email);
            jo.addProperty("user_pw", password);
            jo.addProperty("uuid", UUID);

            RequestData.getInstance().memberJoin(jo, new NetworkResponse<JsonObject>() {
                @Override
                public void onSuccess(Call call, JsonObject clazz) {
                    login_progress.setVisibility(View.GONE);

                    try {
                        JSONObject responseJO = new JSONObject(clazz.toString());
                        String resultData = responseJO.getString("resultData");
                        JSONObject resultDataJO = new JSONObject(resultData);

                        if(resultDataJO.getString("subStatus").equalsIgnoreCase("OK")) {

                            String userCode = resultDataJO.getString("user_code");
                            String user_id = resultDataJO.getString("user_id");
                            String msg = resultDataJO.getString("msg");
                            Dlog.d("userCode : " + userCode);
                            Dlog.d("user_id : " + user_id);

                            //가입후 유저 코드 업데이트.
//                            PrefHelper.setString(SignUpActivity.this, PrefConsts.PREF_DEVICE_ID, userCode);
//                            BaseActivity.userCode = PrefHelper.getString(SignUpActivity.this, PrefConsts.PREF_DEVICE_ID, "");

                            showDoneJoin(msg);

                        }else if(resultDataJO.getString("subStatus").equalsIgnoreCase("ERROR")){
                            String msg = resultDataJO.getString("msg");
                            Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFail(Call call, String msg) {
                    login_progress.setVisibility(View.GONE);
                    Toast.makeText(SignUpActivity.this, getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @OnFocusChange(R.id.tvEmailConfirm)
    public void tvEmailConfirmFocus(boolean hasFocus) {
        String email = tvEmail.getText().toString();
        if (hasFocus && isEmailValid(email)) {
            JsonObject  jo = new JsonObject();
            jo.addProperty("user_id", email);

            RequestData.getInstance().memberEmailCheck(jo, new NetworkResponse() {
                @Override
                public void onSuccess(Call call, Object clazz) {
                    login_progress.setVisibility(View.GONE);
                    try {
                        JSONObject responseJO = new JSONObject(clazz.toString());
                        String resultData = responseJO.getString("resultData");

                        JSONObject resultDataJO = new JSONObject(resultData);
                        if (resultDataJO.getString("subStatus").equalsIgnoreCase("N")) {
                            tvEmail.requestFocus();
                            tvCheckEmailMessage.setVisibility(View.VISIBLE);
                            tvCheckEmailSuMessage.setVisibility(View.GONE);
                        } else {
                            tvCheckEmailMessage.setVisibility(View.GONE);
                            tvCheckEmailSuMessage.setVisibility(View.VISIBLE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFail(Call call, String msg) {
                    login_progress.setVisibility(View.GONE);
                }
            });
        }
    }

    @OnFocusChange(R.id.etPassword)
    public void passwordFocus(boolean hasFocus) {
        String email = tvEmail.getText().toString();
        String emailConfirm = tvEmailConfirm.getText().toString();

        if(!TextUtils.isEmpty(email) && email.equals(emailConfirm) && tvCheckEmailSuMessage.getVisibility() == View.VISIBLE){
            tvCheckEmailMessageConfirm.setVisibility(View.GONE);
            tvCheckEmailSuMessageConfrim.setVisibility(View.VISIBLE);
            tvEmailMessageConfirm.setVisibility(View.GONE);
        }else{
            tvEmailConfirm.requestFocus();
            tvCheckEmailMessageConfirm.setVisibility(View.VISIBLE);
            tvCheckEmailSuMessageConfrim.setVisibility(View.GONE);
        }
    }
}

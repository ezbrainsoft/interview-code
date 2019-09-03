package com.jlesoft.civilservice.koreanhistoryexam9;

import android.accounts.Account;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.jlesoft.civilservice.koreanhistoryexam9.network.NetworkResponse;
import com.jlesoft.civilservice.koreanhistoryexam9.network.RequestData;
import com.jlesoft.civilservice.koreanhistoryexam9.setting.InquiryListActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.user.LoginActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.user.SignUpActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.util.DialogUtil;
import com.jlesoft.civilservice.koreanhistoryexam9.util.LogUtil;
import com.jlesoft.civilservice.koreanhistoryexam9.util.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class AccountActivity extends BaseActivity {

    @BindView(R.id.btn_login_start)
    Button btnLoginStart;
    @BindView(R.id.btn_nonmember_start)
    Button btnNonmemberStart;
    @BindView(R.id.tvCustommer)
    TextView tvCustommer;
    @BindView(R.id.tvAgreement)
    TextView tvAgreement;
    @BindView(R.id.tvInformation)
    TextView tvInformation;
    @BindView(R.id.tv_complate_date)
    TextView tvComplateDate;

    String click_action;
    String idx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        click_action = getIntent().getStringExtra("click_action");
        idx = getIntent().getStringExtra("idx");

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_account);

        ButterKnife.bind(this);

        switch (BuildConfig.APP_NAME) {
            case CERTIFIEDREALTOR:
                btnNonmemberStart.setTextColor(getResources().getColor(R.color.col_085395));
                break;
            case GOVERNMENT:
                btnNonmemberStart.setTextColor(getResources().getColor(R.color.col_4A148C));
                break;
            case SOCIAL:
                btnNonmemberStart.setTextColor(getResources().getColor(R.color.col_7c1edd));
                break;
            case ADMINLAW:
                btnNonmemberStart.setTextColor(getResources().getColor(R.color.col_3cb3fe));
                break;
            case SOCIALWORKER:
                btnNonmemberStart.setTextColor(getResources().getColor(R.color.col_db137a));
                break;
            case KOREAN:
                btnNonmemberStart.setTextColor(getResources().getColor(R.color.col_0470dc));
                break;
            case ENGLISH:
                btnNonmemberStart.setTextColor(getResources().getColor(R.color.col_0db97e));
                break;
            case LITIGATION:
                btnNonmemberStart.setTextColor(getResources().getColor(R.color.col_2d238d));
                break;
            case CERTIFIEDREALTOR2:
                btnNonmemberStart.setTextColor(getResources().getColor(R.color.col_a80b22));
                break;
        }

        initBind();
    }

    public void initBind(){
        String date = getIntent().getStringExtra("date");

        if (!TextUtils.isEmpty(date)) {
            String[] dateArr = date.split("-");
            tvComplateDate.setText("무료이용 " + dateArr[0] + "-" + dateArr[1] + "-" + dateArr[2] + "까지\n로그인해주세요");
        }else{
            tvComplateDate.setText("");
        }
    }

    @OnClick(R.id.btn_login_start)
    void btn_login_start() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, PrefConsts.INTENT_REQUEST_LOGIN_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("click_action", click_action);
            intent.putExtra("idx", idx);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.btn_nonmember_start)
    void btn_nonmember_start() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("click_action", click_action);
        intent.putExtra("idx", idx);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.tvCustommer)
    void tvCustommer() {
        RequestData.getInstance().getCustomerAskPageUrl(new NetworkResponse<JsonObject>() {
            @Override
            public void onSuccess(Call call, JsonObject clazz) {
                JsonObject jsonObject = clazz.getAsJsonObject("resultData");
                String url = jsonObject.get("url_customer").getAsString();
                if (StringUtil.isNotNull(url)) {
                    Intent i = new Intent(AccountActivity.this, InquiryListActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(AccountActivity.this, getString(R.string.server_error_default_msg, ""), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail(Call call, String msg) {
                Toast.makeText(AccountActivity.this, getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    @OnClick(R.id.tvAgreement)
    void tvAgreement() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.service_terms_url)));
        startActivity(intent);
    }

    @OnClick(R.id.tvInformation)
    void tvInformation() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.private_terms_url)));
        startActivity(intent);
    }
}

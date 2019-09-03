package com.jlesoft.civilservice.koreanhistoryexam9.user;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;

import androidx.annotation.NonNull;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CheckBox;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jlesoft.civilservice.koreanhistoryexam9.BaseActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.PrefConsts;
import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.fcm.MyFirebaseInstanceIDService;
import com.jlesoft.civilservice.koreanhistoryexam9.model.setting.PurcharseData;
import com.jlesoft.civilservice.koreanhistoryexam9.network.NetworkResponse;
import com.jlesoft.civilservice.koreanhistoryexam9.network.RequestData;
import com.jlesoft.civilservice.koreanhistoryexam9.util.CommonUtils;
import com.jlesoft.civilservice.koreanhistoryexam9.util.DeviceUuidFactory;
import com.jlesoft.civilservice.koreanhistoryexam9.util.PrefHelper;
import com.google.gson.JsonObject;
import com.jlesoft.civilservice.koreanhistoryexam9.view.ClearEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_form)
    View mLoginFormView;

    @BindView(R.id.password)
    ClearEditText mPasswordView;

    @BindView(R.id.email)
    ClearEditText mEmailView;

    @BindView(R.id.login_progress)
    View mProgressView;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvCount)
    TextView tvCount;

    @BindView(R.id.cbAutoLogin)
    CheckBox cbAutoLogin;

    @BindView(R.id.tvEmailMessage)
    TextView tvEmailMessage;

    @BindView(R.id.tvPasswordMessage)
    TextView tvPasswordMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvTitle.setText("로그인");
        tvCount.setVisibility(View.INVISIBLE);

        mEmailView.setText(PrefHelper.getString(this, PrefConsts.SAVE_USER_ID, ""));

//        populateAutoComplete();

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == 100 || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
    }

    /*@Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Log.d(TAG, "onCreateLoader");
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        if (null != cursor) {
            List<String> emails = new ArrayList<>();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                emails.add(cursor.getString(ProfileQuery.ADDRESS));
                cursor.moveToNext();
            }

            addEmailsToAutoComplete(emails);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }*/

    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;
        tvPasswordMessage.setVisibility(View.INVISIBLE);
        tvEmailMessage.setVisibility(View.INVISIBLE);


        if (TextUtils.isEmpty(email)) {
            tvEmailMessage.setVisibility(View.VISIBLE);
            tvEmailMessage.setText(R.string.input_email_empty);
            focusView = mEmailView;
            cancel = true;

        } else if (!isEmailValid(email)) {
            tvEmailMessage.setVisibility(View.VISIBLE);
            tvEmailMessage.setText(R.string.input_email_type_error);
            focusView = mEmailView;
            cancel = true;

        } else if (TextUtils.isEmpty(password)) {
            tvPasswordMessage.setVisibility(View.VISIBLE);
            tvPasswordMessage.setText(R.string.input_pw_empty);
            focusView = mPasswordView;
            cancel = true;

        } else if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            tvPasswordMessage.setVisibility(View.VISIBLE);
            tvPasswordMessage.setText(R.string.input_pw_type_error);
            focusView = mPasswordView;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();

        } else {
            String UUID = PrefHelper.getString(this, PrefConsts.PREF_TEMP_DEVICE_ID, null);

            if(UUID == null){
                DeviceUuidFactory uuidFactory = new DeviceUuidFactory(LoginActivity.this);
                UUID = uuidFactory.getDeviceUuid().toString();
            }

            JsonObject jo = new JsonObject();
            jo.addProperty("user_code", userCode);
            jo.addProperty("user_id", email);
            jo.addProperty("user_pw", password);
            jo.addProperty("device", Build.MODEL);
            jo.addProperty("uuid", UUID);
            jo.addProperty("vendor", "google");
            jo.addProperty("app_version", CommonUtils.getOriginalAppVersion(this));

            showProgress(true);

            RequestData.getInstance().memberLogin(jo, new NetworkResponse<JsonObject>() {
                @Override
                public void onSuccess(Call call, JsonObject clazz) {
                    showProgress(false);

                    try {
                        JSONObject responseJO = new JSONObject(clazz.toString());
                        String resultData = responseJO.getString("resultData");

                        JSONObject resultDataJO = new JSONObject(resultData);

                        if (resultDataJO.getString("subStatus").equalsIgnoreCase("ERROR")) {
                            Toast.makeText(LoginActivity.this, "로그인 실패. 아이디와 비밀번호를 확인하세요", Toast.LENGTH_SHORT).show();

                        } else {
                            String userCode = resultDataJO.getString("user_code");
                            String user_id = resultDataJO.getString("user_id");
                            String nickname = resultDataJO.getString("m_nickname");

                            //가입후 유저 코드 업데이트.
                            PrefHelper.setString(LoginActivity.this, PrefConsts.PREF_DEVICE_ID, userCode);
                            PrefHelper.setString(LoginActivity.this, PrefConsts.USER_ID, user_id);
                            PrefHelper.setString(LoginActivity.this, PrefConsts.NICK_NAME, nickname);
                            PrefHelper.setString(LoginActivity.this, PrefConsts.USER_PASSWORD, mPasswordView.getText().toString());
                            PrefHelper.setString(LoginActivity.this, PrefConsts.SAVE_USER_ID, user_id);

                            if (cbAutoLogin.isChecked()) {//자동로그인 여부
                                PrefHelper.setBoolean(LoginActivity.this, PrefConsts.AUTO_LOGIN, true);
                            } else {
                                PrefHelper.setBoolean(LoginActivity.this, PrefConsts.AUTO_LOGIN, false);
                            }

                            BaseActivity.userCode = PrefHelper.getString(LoginActivity.this, PrefConsts.PREF_DEVICE_ID, "");
                            BaseActivity.userID = PrefHelper.getString(LoginActivity.this, PrefConsts.USER_ID, "");
                            BaseActivity.nickname = PrefHelper.getString(LoginActivity.this, PrefConsts.NICK_NAME, "");

                            httpPurcharse();
                        }
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

   /* private void httpInsertOrder() {
        String missPurcharse = PrefHelper.getString(this, PrefConsts.MISS_SEND_PURCHARSE, "");

        if (!TextUtils.isEmpty(missPurcharse)) {
            String[] params = missPurcharse.split(",");

            if (CommonUtils.getConnectNetwork(LoginActivity.this)) {
                DisplayUtils.showProgressDialog(this, "저장 중");
                JsonObject jo = new JsonObject();
                jo.addProperty("user_code", userCode);
                jo.addProperty("user_id", userID);
                jo.addProperty("goods_code", params[0]);
                jo.addProperty("order_code", params[1]);
                jo.addProperty("purchaseState", params[2]);
                jo.addProperty("purchaseToken", params[3]);
                jo.addProperty("purchaseTime", params[4]);

                RequestData.getInstance().getPGInsertOrder(jo, new NetworkResponse<JsonObject>() {
                    @Override
                    public void onSuccess(Call call, JsonObject clazz) {
                        DisplayUtils.hideProgressDialog();
                        if (clazz.get("statusCode").getAsString().equals("200")) {
                            PrefHelper.setString(LoginActivity.this, PrefConsts.MISS_SEND_PURCHARSE, "");
                            httpPurcharse();
                        }
                    }

                    @Override
                    public void onFail(Call call, String msg) {
                        DisplayUtils.hideProgressDialog();
                        Toast.makeText(LoginActivity.this, "결제한 상품정보가 전달되지 않았습니다. 고객문의로 문의바랍니다.", Toast.LENGTH_SHORT).show();
                        httpPurcharse();
                    }
                });
            } else {
                DisplayUtils.hideProgressDialog();
                if (!LoginActivity.this.isFinishing()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setCancelable(false);
                    builder.setMessage("Wifi/데이터 연결이 원할하지 않습니다. 네트워크를 확인하여 새로고침을 눌러주세요.");
                    builder.setPositiveButton(getString(R.string.refresh), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            httpInsertOrder();
                        }
                    });
                    builder.setNegativeButton(getString(R.string.finish), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            httpPurcharse();
                        }
                    });
                    builder.show();
                }
            }
        } else {
            httpPurcharse();
        }
    }*/

    public void httpPurcharse() {

        if (CommonUtils.getConnectNetwork(LoginActivity.this)) {

            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "getInstanceId failed", task.getException());
                        return;
                    }

                    // Get new Instance ID token
                    String getToken = task.getResult().getToken();

                    String oldToken = PrefHelper.getString(LoginActivity.this, PrefConsts.PUSH_TOKEN, "");

                    if (!TextUtils.isEmpty(getToken) && !oldToken.equals(getToken) ) {
                        PrefHelper.setString(LoginActivity.this, PrefConsts.PUSH_TOKEN, getToken);
                        Log.d(TAG, "FCM token: " + getToken + "\n" + getToken);
                        MyFirebaseInstanceIDService.sendRegistrationToServer(getToken);
                    }
                }
            });

            //구매상품 리스트 가져오기

            JsonObject jo = new JsonObject();
            jo.addProperty("user_code", userCode);
            jo.addProperty("app_version", CommonUtils.getAppVersion(this));
            jo.addProperty("device_id", Build.MODEL);
            jo.addProperty("user_id", userID);

            RequestData.getInstance().getPgGoodsInfo(jo, new NetworkResponse<JsonObject>() {

                @Override
                public void onSuccess(Call call, JsonObject clazz) {

                    if (clazz.get("statusCode").getAsString().equals("200")) {
                        purcharseArr.clear();
                        JsonObject resultData = clazz.get("resultData").getAsJsonObject();
                        JsonObject goodsInfo = resultData.get("goods_info").getAsJsonObject();

                        period = goodsInfo.get("period").getAsString();
                        permit = goodsInfo.get("app_permit").getAsString();
                        //구매상품 리스트
                        JsonArray json = resultData.get("buy_list").getAsJsonArray();
                        if (json.size() > 0) {
                            for (int i = 0; i < json.size(); i++) {
                                JsonObject asJsonObject = json.get(i).getAsJsonObject();
                                PurcharseData purcharseData = new Gson().fromJson(asJsonObject.toString(), PurcharseData.class);
                                purcharseArr.add(purcharseData);
                            }
                            title = goodsInfo.get("title").getAsString();
                            String app_permit = goodsInfo.get("app_permit").getAsString();
                            permit = app_permit;
                        }

                        setResult(RESULT_OK);
                        finish();
                    }
                }

                @Override
                public void onFail(Call call, String msg) {
                    Toast.makeText(LoginActivity.this, getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(LoginActivity.this, getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isEmailValid(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            err = true;
        }
        return err;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /*private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {//최초 한번은 false이므로 권한 요청을 띄워준다.
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);//권한요청
        }
        return false;
    }
*/
   /* private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }*/

    @OnClick(R.id.btnBack)
    void backButtonClick() {
        finish();
    }

    @OnClick(R.id.email_sign_in_button)
    void emailClick() {
        attemptLogin();
    }

    @OnClick(R.id.email_sign_up_button)
    void signUpClick() {
        Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivityForResult(i, PrefConsts.INTENT_REQUEST_SIGN_UP_ACTIVITY);
    }

    @OnClick(R.id.btnFindPW)
    void btnFindPWClick() {
        Intent i = new Intent(LoginActivity.this, FindPasswordActivity.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PrefConsts.INTENT_REQUEST_SIGN_UP_ACTIVITY && resultCode == RESULT_OK && data != null){
            mEmailView.setText(data.getStringExtra("userId"));
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}


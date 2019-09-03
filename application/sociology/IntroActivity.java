package com.jlesoft.civilservice.koreanhistoryexam9;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import android.view.WindowManager;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jlesoft.civilservice.koreanhistoryexam9.fcm.MyFirebaseInstanceIDService;
import com.jlesoft.civilservice.koreanhistoryexam9.model.setting.PurcharseData;
import com.jlesoft.civilservice.koreanhistoryexam9.network.NetworkResponse;
import com.jlesoft.civilservice.koreanhistoryexam9.network.RequestData;
import com.jlesoft.civilservice.koreanhistoryexam9.util.CommonUtils;
import com.jlesoft.civilservice.koreanhistoryexam9.util.DeviceUuidFactory;
import com.jlesoft.civilservice.koreanhistoryexam9.util.DisplayUtils;
import com.jlesoft.civilservice.koreanhistoryexam9.util.Dlog;
import com.jlesoft.civilservice.koreanhistoryexam9.util.LogUtil;
import com.jlesoft.civilservice.koreanhistoryexam9.util.PrefHelper;
import com.jlesoft.civilservice.koreanhistoryexam9.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.ExecutionException;

import retrofit2.Call;

public class IntroActivity extends BaseActivity {

    public static final int MY_PERMISSION_REQUEST_STORAGE = 100;

    String SaveMarketVersion = "";
    String SaveAppVersion = "";
    String Apppackage = "";
    private boolean isLogin;
    String click_action;
    String idx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        click_action = getIntent().getStringExtra("click_action");
        idx = getIntent().getStringExtra("idx");

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_intro);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                    }
                });

                checkAppVersion();
            }
        }, 800);
    }

    private void checkAppVersion() {

        if (!CommonUtils.getConnectNetwork(this)) {
            if (!IntroActivity.this.isFinishing()) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setMessage(getResources().getString(R.string.msg_no_connect_network));
                builder.setPositiveButton(getString(R.string.refresh), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkAppVersion();
                    }
                });
                builder.setNegativeButton(getString(R.string.finish), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.finishAffinity(IntroActivity.this);
                    }
                });
                builder.show();
            }

        } else {

            JsonObject jo = new JsonObject();
            jo.addProperty("user_code", userCode);

            RequestData.getInstance().getAppVersion(jo, new NetworkResponse<JsonObject>() {
                @Override
                public void onSuccess(Call call, JsonObject object) {
                    if (object != null) {
                        JsonObject resultData = object.getAsJsonObject("resultData");

                        // 현재 설치된 앱의 버전을 체크해서 int값으로 가지고 온다.
                        int currentAppVersionCode = getAppVersion();

                        // 호출한 api에서 보내주는 버전을 int값으로 가지고 온다.
                        int latestAppVersionCode = Integer.parseInt(resultData.get("app_version").getAsString().replace(".", ""));

                        int updateOption = resultData.get("update_type").getAsInt();
                        reinforceYN = resultData.get("reinforce_yn").getAsString();
                        mainService = resultData.get("main_service").getAsString();
                        mainSwitch = resultData.get("main_switch").getAsString();


                        LogUtil.d("현재 버전 : " + currentAppVersionCode);
                        LogUtil.d( "서버에서 받은 최신버전 정보 : " + latestAppVersionCode);
                        LogUtil.d( "업데이트 옵션 : " + updateOption);
                        LogUtil.d( "강화시험 공개 : " + reinforceYN);
                        LogUtil.d( "메뉴 : " + mainService);

                        if (latestAppVersionCode > currentAppVersionCode) {

                            switch (updateOption) {
                                case 1:
                                    showMustUpdateGuideDialog();
                                    break;

                                case 2:
                                    showOptionalUpdateGuideDialog();
                                    break;

                                default:
                                    login();
                                    break;
                            }
                        } else {
                            login();
                        }
                    }
                }

                @Override
                public void onFail(Call call, String msg) {
                    Toast.makeText(IntroActivity.this, getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                    login();
                    return;
                }
            });
        }
    }

    private void showMustUpdateGuideDialog() {
        if (!IntroActivity.this.isFinishing()) {

            new AlertDialog.Builder(IntroActivity.this)
                    .setMessage("업데이트가 필요합니다.\n업데이트를 해 주세요.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();

                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + Apppackage)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + Apppackage)));
                            }
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .show();
        }
    }

    private void showOptionalUpdateGuideDialog() {
        if (!IntroActivity.this.isFinishing()) {

            new AlertDialog.Builder(IntroActivity.this)
                    .setMessage("새로운 업데이트가 있습니다.\n업데이트하시겠습니까?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();

                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + Apppackage)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + Apppackage)));
                            }
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            login();
                        }
                    })
                    .setCancelable(false)
                    .show();
        }
    }

    public void CompareVersion() {
        if (isNetWork() == true) {
            try {
                LogUtil.e("설치된 앱 버전 : " + SaveAppVersion);
                SaveMarketVersion = new getMarketVersion().execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "인터넷 연결이 되어있지 않아 버전정보를 확인할 수 없습니다. 앱을 종료합니다.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private Boolean isNetWork() {//버전 확인을 위한 네트워크 체크 함수
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isMobileAvailable = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isAvailable();
        boolean isMobileConnect = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean isWifiAvailable = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isAvailable();
        boolean isWifiConnect = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

        if ((isWifiAvailable && isWifiConnect) || (isMobileAvailable && isMobileConnect)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {

    }

    private class getMarketVersion extends AsyncTask<String, Void, String> {

        String MarketVersion = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String AppFromPlayStore = "https://play.google.com/store/apps/details?id=" + Apppackage;
                Document doc = Jsoup.connect(AppFromPlayStore).get();
                MarketVersion = doc.getElementsByAttributeValue("itemprop", "softwareVersion").first().text();
            } catch (Exception e) {
                e.printStackTrace();
            }

            LogUtil.e("리턴할 마켓 버전 : " + MarketVersion);
            return MarketVersion;
        }

        @Override
        protected void onPostExecute(String MarketVersion) {
            this.MarketVersion = MarketVersion;
            int currentAppVersionCode = Integer.parseInt(SaveAppVersion.replace(".", ""));
            int marketAppVersionCode = Integer.parseInt(MarketVersion.replace(".", ""));

            LogUtil.e("현재 버전 코드 : " + currentAppVersionCode + " & 마켓 버전 코드 : " + marketAppVersionCode);


            // 1. 마켓에서 받아온 버전 값이 비어있지 않고
            // 2. 현재 설치된 버전 값도 비어있지 않으며
            // 3. 두 버전의 값을 int 로 변경한 값을 비교했을 때 마켓에 올라간 버전의 값이 더 클 경우에만 업데이트 팝업을 띄움.
            if (StringUtil.isNotNull(MarketVersion) && StringUtil.isNotNull(SaveAppVersion) && marketAppVersionCode > currentAppVersionCode) {
                if (!IntroActivity.this.isFinishing()) {
                    new AlertDialog.Builder(IntroActivity.this)
                            .setMessage("업데이트가 필요합니다.\n업데이트를 해 주세요.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();

                                    try {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + Apppackage)));
                                    } catch (android.content.ActivityNotFoundException anfe) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + Apppackage)));
                                    }
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            })
                            .setCancelable(false)
                            .show();
                }

            } else {
                DisplayUtils.hideProgressDialog();
                login();
            }
        }
    }

    private int getAppVersion() {
        PackageManager pm = getPackageManager();
        PackageInfo pInfo = null;

        try {
            pInfo = pm.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }

        Apppackage = pInfo.packageName;

        String currentVersion = pInfo.versionName;

        return Integer.parseInt(currentVersion.replace(".", ""));
    }

    private void login() {
        final String email = PrefHelper.getString(IntroActivity.this, PrefConsts.USER_ID, "");
        final String password = PrefHelper.getString(IntroActivity.this, PrefConsts.USER_PASSWORD, "");
        String device_id = PrefHelper.getString(IntroActivity.this, PrefConsts.PREF_DEVICE_ID, "");
        boolean auto_login = PrefHelper.getBoolean(IntroActivity.this, PrefConsts.AUTO_LOGIN, false);
        showOXToast = PrefHelper.getBoolean(IntroActivity.this, PrefConsts.SHOW_OX_TOAST, true);
        showStateBtn = PrefHelper.getBoolean(this, PrefConsts.SHOW_STATE_BTN, true);
        scoringSetting = PrefHelper.getBoolean(this, PrefConsts.SCORING_SETTING, false);
        String UUID = PrefHelper.getString(this, PrefConsts.PREF_TEMP_DEVICE_ID, null);

        if(UUID == null){
            uuidFactory = new DeviceUuidFactory(IntroActivity.this);
            UUID = uuidFactory.getDeviceUuid().toString();
        }

        if (auto_login && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(UUID)) {

            if (CommonUtils.getConnectNetwork(this)) {

                JsonObject jo = new JsonObject();
                jo.addProperty("user_code", userCode);
                jo.addProperty("user_id", email);
                jo.addProperty("user_pw", password);
                jo.addProperty("device", Build.MODEL);
                jo.addProperty("uuid", UUID);
                jo.addProperty("vendor", "google");
                jo.addProperty("app_version", CommonUtils.getOriginalAppVersion(this));

                RequestData.getInstance().memberLogin(jo, new NetworkResponse<JsonObject>() {
                    @Override
                    public void onSuccess(Call call, JsonObject clazz) {

                        try {
                            JSONObject responseJO = new JSONObject(clazz.toString());
                            String resultData = responseJO.getString("resultData");

                            JSONObject resultDataJO = new JSONObject(resultData);

                            if (resultDataJO.getString("subStatus").equalsIgnoreCase("ERROR")) {
                                Toast.makeText(IntroActivity.this, "로그인 실패. 아이디와 비밀번호를 확인하세요", Toast.LENGTH_SHORT).show();

                                PrefHelper.setString(IntroActivity.this, PrefConsts.USER_ID, "");
                                PrefHelper.setString(IntroActivity.this, PrefConsts.USER_PASSWORD, "");
                                PrefHelper.setString(IntroActivity.this, PrefConsts.NICK_NAME, "");
                                PrefHelper.setString(IntroActivity.this, PrefConsts.PREF_DEVICE_ID, "");
                                userCode = "";
                                userID = "";
                                checkUserCode();
                                purcharseArr.clear();
                                permit = "";

                                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                        if (!task.isSuccessful()) {
                                            Log.w(TAG, "getInstanceId failed", task.getException());
                                            return;
                                        }

                                        // Get new Instance ID token
                                        String getToken = task.getResult().getToken();

                                        String oldToken = PrefHelper.getString(IntroActivity.this, PrefConsts.PUSH_TOKEN, "");

                                        if (!TextUtils.isEmpty(getToken) && !oldToken.equals(getToken) ) {

                                            PrefHelper.setString(IntroActivity.this, PrefConsts.PUSH_TOKEN, getToken);
                                            MyFirebaseInstanceIDService.sendRegistrationToServer(getToken);
                                        }
                                    }
                                });

                                isLogin = false;
                                httpPurcharse();

                            } else {
                                String userCode = resultDataJO.getString("user_code");
                                String user_id = resultDataJO.getString("user_id");
                                String nickname = resultDataJO.getString("m_nickname");

                                PrefHelper.setString(IntroActivity.this, PrefConsts.PREF_DEVICE_ID, userCode);
                                PrefHelper.setString(IntroActivity.this, PrefConsts.USER_ID, user_id);
                                PrefHelper.setString(IntroActivity.this, PrefConsts.NICK_NAME, nickname);

                                BaseActivity.userCode = PrefHelper.getString(IntroActivity.this, PrefConsts.PREF_DEVICE_ID, "");
                                BaseActivity.userID = PrefHelper.getString(IntroActivity.this, PrefConsts.USER_ID, "");
                                BaseActivity.nickname = PrefHelper.getString(IntroActivity.this, PrefConsts.NICK_NAME, "");

                                isLogin = true;

                                httpPurcharse();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            finish();
                        }
                    }

                    @Override
                    public void onFail(Call call, String msg) {
                        finish();
                    }
                });
            } else {
                if (!IntroActivity.this.isFinishing()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setCancelable(false);
                    builder.setMessage(getResources().getString(R.string.msg_no_connect_network));
                    builder.setPositiveButton(getString(R.string.refresh), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            login();
                        }
                    });
                    builder.setNegativeButton(getString(R.string.finish), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.show();
                }
            }
        } else {
            //로그아웃

            PrefHelper.setString(IntroActivity.this, PrefConsts.USER_ID, "");
            PrefHelper.setString(IntroActivity.this, PrefConsts.USER_PASSWORD, "");
            PrefHelper.setString(IntroActivity.this, PrefConsts.PREF_DEVICE_ID, "");
            PrefHelper.setBoolean(IntroActivity.this, PrefConsts.AUTO_LOGIN, false);
            purcharseArr.clear();
            userID = "";
            userCode = "";
            period = "";
            checkUserCode();

            isLogin = false;
            httpPurcharse();
        }
    }

    public void httpPurcharse() {
        if (CommonUtils.getConnectNetwork(IntroActivity.this)) {

            JsonObject jo = new JsonObject();
            jo.addProperty("user_code", userCode);
            jo.addProperty("app_version", CommonUtils.getAppVersion(this));
            jo.addProperty("device_id", Build.MODEL);
            jo.addProperty("user_id", userID);

            RequestData.getInstance().getPgGoodsInfo(jo, new NetworkResponse<JsonObject>() {

                @Override
                public void onSuccess(Call call, JsonObject clazz) {

                    try {
                        if (clazz.get("statusCode").getAsString().equals("200")) {
                            purcharseArr.clear();
                            JsonObject resultData = clazz.get("resultData").getAsJsonObject();

                            JsonObject goodsInfo = resultData.get("goods_info").getAsJsonObject();
                            period = goodsInfo.get("period").getAsString();
                            permit = goodsInfo.get("app_permit").getAsString();
                            String freeDate = goodsInfo.get("free_date").getAsString();

                            //구매상품 리스트
                            JsonArray json = resultData.get("buy_list").getAsJsonArray();
                            if (json.size() > 0) {
                                for (int i = 0; i < json.size(); i++) {
                                    JsonObject asJsonObject = json.get(i).getAsJsonObject();
                                    PurcharseData purcharseData = new Gson().fromJson(asJsonObject.toString(), PurcharseData.class);
                                    purcharseArr.add(purcharseData);//NullPointerException: Attempt to invoke virtual method 'java.lang.String com.google.gson.JsonElement.getAsString()' on a null object reference
                                }
                                title = goodsInfo.get("title").getAsString();
                            }

                            if (isLogin) {
                                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                                intent.putExtra("click_action", click_action);
                                intent.putExtra("idx", idx);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();

                            } else {
                                Intent intent = new Intent(IntroActivity.this, AccountActivity.class);
                                intent.putExtra("click_action", click_action);
                                intent.putExtra("idx", idx);
                                intent.putExtra("date", freeDate);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        finish();
                    }
                }

                @Override
                public void onFail(Call call, String msg) {
                    Toast.makeText(IntroActivity.this, getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else {
            Toast.makeText(IntroActivity.this, getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * Permission check.
     */

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to write the permission.
                Toast.makeText(this, "Read/Write external storage", Toast.LENGTH_SHORT).show();
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSION_REQUEST_STORAGE);

            // MY_PERMISSION_REQUEST_STORAGE is an
            // app-defined int constant

        } else {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        login();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                                login();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();

                } else {
                    Dlog.d("Permission always deny");
                }
                break;
        }
    }
}

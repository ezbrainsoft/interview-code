package com.jlesoft.civilservice.koreanhistoryexam9;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jlesoft.civilservice.koreanhistoryexam9.englishWord.EnglishWordDayStudyActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.everydayEnglish.bookmark.EverydayMainExpressionBookmarkFragment;
import com.jlesoft.civilservice.koreanhistoryexam9.everydayEnglish.bookmark.EverydayMainPhraseBookmarkFragment;
import com.jlesoft.civilservice.koreanhistoryexam9.everydayEnglish.bookmark.EverydayMainProverbBookmarkFragment;
import com.jlesoft.civilservice.koreanhistoryexam9.model.setting.PurcharseData;
import com.jlesoft.civilservice.koreanhistoryexam9.network.NetworkResponse;
import com.jlesoft.civilservice.koreanhistoryexam9.network.RequestData;
import com.jlesoft.civilservice.koreanhistoryexam9.studygroup.talk.StudyGroupTalkQuestSettingDialog;
import com.jlesoft.civilservice.koreanhistoryexam9.util.BaseApplication;
import com.jlesoft.civilservice.koreanhistoryexam9.util.CommonUtils;
import com.jlesoft.civilservice.koreanhistoryexam9.util.DeviceUuidFactory;
import com.jlesoft.civilservice.koreanhistoryexam9.util.DialogUtil;
import com.jlesoft.civilservice.koreanhistoryexam9.util.DisplayUtils;
import com.jlesoft.civilservice.koreanhistoryexam9.util.LogUtil;
import com.google.gson.JsonObject;
import com.jlesoft.civilservice.koreanhistoryexam9.util.PrefHelper;
import com.jlesoft.civilservice.koreanhistoryexam9.view.WithBottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import retrofit2.Call;

public class BaseActivity extends AppCompatActivity {
    public static String TAG;

    public static final String HISTORY = "한국사";
    public static final String CERTIFIEDREALTOR = "공인중개사1차";
    public static final String GOVERNMENT = "행정학";
    public static final String SOCIAL = "사회";
    public static final String ADMINLAW = "행정법";
    public static final String KOREAN = "국어";
    public static final String SOCIALWORKER = "사회복지사";
    public static final String ENGLISH = "영어";
    public static final String LITIGATION = "형사소송법";
    public static final String CERTIFIEDREALTOR2 = "공인중개사2차";


    //    public static final String WEB_URL = "http://www.learntolearn.co.kr";
    public static final String WEB_URL = BuildConfig.WEB_URL;
    private static final String FILE_NAME = BuildConfig.FILE_NAME;
    private static final String UUID_NAME = BuildConfig.UUID_NAME;
    public static String APP_NAME = BuildConfig.APP_NAME;
    public static boolean isCheckWithDialog = false;//위드팝업창 안보이기
    public static boolean isWithRepresent = false;//대표 위드 유무여부

    //    public ApiService apiService;
    DeviceUuidFactory uuidFactory;
    public String tempDeviceId;
    public static String userCode = "";//M0003998, M0005942
    public static String userID = "";
    public static String nickname = "";

    public static ArrayList<PurcharseData> purcharseArr = new ArrayList<>();//구매상품
    public static String permit = "";//"question,smartnote,blank,pasttest,keyword,exam,ox,daily,newbie,history,compare,random,mypage,past_explain(기출설명), reading,past_solve(기출문제 선지 체크), personal(맞춤학습)", study(스터디 학습)
    public static String title = "";
    public static String period = "";
    public static String buyYN = "N";
    public static String reinforceYN;
    public static String mainService; //메인 메뉴에 보여주는 메뉴 (smartnote,voca,question,pasttest,test,compare,historical,keyword,ox,random, everyday, study)
    public static String mainSwitch; //메인 메뉴 활성되는 메뉴

    public static boolean showOXToast = true;//문제 풀이시 OX 이미지 보여줄지 여부
    public static boolean showStateBtn = true; // 문제 왼쪽 하단 현황버튼 보여줄지 여부
    public static boolean scoringSetting = false;//채점 버튼

    public static int fontSize;

    public static Toast toastTrue;
    public static Toast toastWrong;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getName();

        setStatusBarTranslucent(true);

        userID = PrefHelper.getString(this, PrefConsts.USER_ID, "");
        String deviceId = PrefHelper.getString(this, PrefConsts.PREF_DEVICE_ID, "");

        if (TextUtils.isEmpty(userID)) {
            if (userCode.equals("")) {
                getUserCode();
            }
        } else {
            userCode = deviceId;
            if (userCode.equals("")) {
                getUserCode();
                PrefHelper.setString(this, PrefConsts.PREF_DEVICE_ID, userCode);
            }
        }

        if(TextUtils.isEmpty(tempDeviceId)){
            SharedPreferences prefs = getSharedPreferences(PrefConsts.PREF_FILE_NAME, 0);
            String uuid = prefs.getString(PrefConsts.PREF_TEMP_DEVICE_ID, null);

            if(uuid == null){
                uuidFactory = new DeviceUuidFactory(BaseActivity.this);
                tempDeviceId = uuidFactory.getDeviceUuid().toString();
            }else{
                tempDeviceId = uuid;
            }
        }

        if (BuildConfig.APP_NAME.equals(ENGLISH)) {
            fontSize = PrefHelper.getInt(this, PrefConsts.POPUP_FONT_SIZE, PrefConsts.FONT_SIZE_1_2);
        } else {
            fontSize = PrefHelper.getInt(this, PrefConsts.POPUP_FONT_SIZE, PrefConsts.FONT_SIZE_1);
        }
    }

    public void checkUserCode() {
        if (userCode.equals("")) {
            getUserCode();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (BuildConfig.IS_DEBUG) {
            Toast.makeText(this, "[" + userCode + ", " + userID + ", " + nickname + "]" + getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
            LogUtil.d("BaseActivity onResume BuildConfig.IS_DEBUG : " + BuildConfig.IS_DEBUG);
            LogUtil.d("onResume userCode : " + userCode + ", userID : " + userID);
            LogUtil.d("onResume className:" + getClass().getName());
            LogUtil.d("onResume mainService:" + mainService);
            LogUtil.d("onResume permit:" + permit);
        }

        /*
         * 인트로 화면이 아닌 경우 userCode, permit, mainService가 없으면 앱을 종료시킨다.
         * */
        if (!getClass().getName().contains("IntroActivity")) {
            if (TextUtils.isEmpty(userCode) || TextUtils.isEmpty(permit) || TextUtils.isEmpty(mainService)) {
                ActivityCompat.finishAffinity(this);
            }

            /*
             * 앱 자체가 백그라운드로 돌아간 경우 forground가 됐을때 구매정보를 다시 한번 가져온다.
             * */
            if(getApplication() instanceof BaseApplication){
                if (((BaseApplication)getApplication()).isReturnedForground()) {
                    httpPurcharse(getClass().getName()) ;
                }
            }
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            getWindow().clearFlags(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    private void getUserCode() {

        File file = new File(getFilesDir(), FILE_NAME);
        LogUtil.d("file " + file.getAbsolutePath());
        LogUtil.d("file " + getFilesDir().getPath());

        if (!file.exists()) {
            getTempMemberID();

        } else {

            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String userCode = bufferedReader.readLine();

                if (TextUtils.isEmpty(userCode) || userCode.equalsIgnoreCase("null")) {
                    getTempMemberID();

                } else {
                    BaseActivity.userCode = userCode;
                }

            } catch (NullPointerException e) {
                getTempMemberID();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getTempMemberID() {

        uuidFactory = new DeviceUuidFactory(BaseActivity.this);
        tempDeviceId = uuidFactory.getDeviceUuid().toString();

        /**
         * 신규 로직
         */
        if (CommonUtils.getConnectNetwork(this)) {

            JsonObject jo = new JsonObject();
            jo.addProperty("uuid", tempDeviceId);

            RequestData.getInstance().getTempID(jo, new NetworkResponse<JsonObject>() {

                @Override
                public void onSuccess(Call call, JsonObject clazz) {
                    DisplayUtils.hideProgressDialog();
                    String statusCode = clazz.get("statusCode").getAsString();

                    if (statusCode.equals("200")) {
                        JsonObject resultData = clazz.get("resultData").getAsJsonObject();
                        String userCode = resultData.get("user_code").getAsString();
                        if (TextUtils.isEmpty(userCode) || userCode.equalsIgnoreCase("null")) {
                            getTempMemberID();
                            return;
                        }

                        PrefHelper.setString(BaseActivity.this, PrefConsts.PREF_DEVICE_ID, userCode);
                        saveUserCode(clazz.toString());
                    }
                }

                @Override
                public void onFail(Call call, String msg) {
                    DisplayUtils.hideProgressDialog();
                    Toast.makeText(BaseActivity.this, getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(BaseActivity.this, getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void saveUserCode(String s) {
        File file = new File(getFilesDir(), FILE_NAME);
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try {
            JSONObject responseJO = new JSONObject(s);
            String resultData = responseJO.getString("resultData");

            JSONObject resultDataJO = new JSONObject(resultData);
            String userCode1 = resultDataJO.getString("user_code");

            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(userCode1);
            bufferedWriter.flush();


        } catch (JSONException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (fileWriter != null) {
                fileWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showPopupMenu(ImageButton btnMenu) {
        PopupMenu popup = new PopupMenu(BaseActivity.this, btnMenu);
        MenuInflater inflater = popup.getMenuInflater();
        Menu menu = popup.getMenu();
        inflater.inflate(R.menu.menu_default, menu);

        if (BuildConfig.APP_NAME.equals(SOCIALWORKER) || BuildConfig.APP_NAME.equals(CERTIFIEDREALTOR) || BuildConfig.APP_NAME.equals(CERTIFIEDREALTOR2)) {
            menu.findItem(R.id.toWithDialog).setVisible(false);
            menu.findItem(R.id.toWithQuest).setVisible(false);
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = null;

                switch (item.getItemId()) {
                    case R.id.toMain:
                        intent = new Intent(BaseActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        finish();
                        break;


                    case R.id.toSetting:
                        intent = new Intent(BaseActivity.this, MainActivity.class);
                        intent.putExtra("moveToSetting", true);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.toTextSize:
                        showTextSizeSettingDialog();
                        break;

                    case R.id.toTodayState:
                        showTodayStateDialog();
                        break;
                }
                return false;
            }
        });
        popup.show();
    }

    public void showPopupOXMenu(ImageButton btnMenu, final FragmentPagerAdapter adapter, final TextView tvCount, boolean isWithDialog) {

        PopupMenu popup = new PopupMenu(BaseActivity.this, btnMenu);
        MenuInflater inflater = popup.getMenuInflater();
        Menu menu = popup.getMenu();
        inflater.inflate(R.menu.menu_ox_default, menu);

        if (isWithDialog) {
            menu.findItem(R.id.toWithDialog).setVisible(true);
        }

        if (BuildConfig.APP_NAME.equals(SOCIALWORKER) || BuildConfig.APP_NAME.equals(CERTIFIEDREALTOR) || BuildConfig.APP_NAME.equals(CERTIFIEDREALTOR2)) {
            menu.findItem(R.id.toWithDialog).setVisible(false);
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = null;

                switch (item.getItemId()) {
                    case R.id.toMain:
                        EnglishWordDayStudyActivity.dataArr.clear();
                        EverydayMainExpressionBookmarkFragment.expressionArr.clear();
                        EverydayMainPhraseBookmarkFragment.phraseArr.clear();
                        EverydayMainProverbBookmarkFragment.proverbArr.clear();

                        intent = new Intent(BaseActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        finish();
                        break;

                    case R.id.toSetting:
                        EnglishWordDayStudyActivity.dataArr.clear();
                        EverydayMainExpressionBookmarkFragment.expressionArr.clear();
                        EverydayMainPhraseBookmarkFragment.phraseArr.clear();
                        EverydayMainProverbBookmarkFragment.proverbArr.clear();

                        intent = new Intent(BaseActivity.this, MainActivity.class);
                        intent.putExtra("moveToSetting", true);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        finish();
                        break;
                    case R.id.toWithDialog:
                        WithBottomSheetDialog bottomSheetDialog = WithBottomSheetDialog.getInstance();
                        bottomSheetDialog.show(getSupportFragmentManager(), "bottomSheet");
                        break;
                }
                return false;
            }
        });
        popup.show();
    }

    public void showPopupMenu(ImageButton btnMenu, final FragmentPagerAdapter adapter, final TextView tvCount) {
        showPopupMenu(false, btnMenu, adapter, tvCount );
    }

    public void showPopupMenu(boolean sdmode, ImageButton btnMenu, final FragmentPagerAdapter adapter, final TextView tvCount) {

        PopupMenu popup = new PopupMenu(BaseActivity.this, btnMenu);
        MenuInflater inflater = popup.getMenuInflater();
        Menu menu = popup.getMenu();
        inflater.inflate(R.menu.menu_default, menu);

        if (BuildConfig.APP_NAME.equals(SOCIALWORKER) || BuildConfig.APP_NAME.equals(CERTIFIEDREALTOR) || BuildConfig.APP_NAME.equals(CERTIFIEDREALTOR2)) {
            menu.findItem(R.id.toWithDialog).setVisible(false);
            menu.findItem(R.id.toWithQuest).setVisible(false);
        }

        if(BuildConfig.APP_NAME.equals(ENGLISH) ){
            menu.findItem(R.id.toSDMode).setVisible(true);

            if(sdmode){
                menu.findItem(R.id.toSDMode).setTitle("SD MODE 해제");
            }else{
                menu.findItem(R.id.toSDMode).setTitle("SD MODE 적용");
            }
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = null;

                switch (item.getItemId()) {
                    case R.id.toMain:
                        EnglishWordDayStudyActivity.dataArr.clear();
                        EverydayMainExpressionBookmarkFragment.expressionArr.clear();
                        EverydayMainPhraseBookmarkFragment.phraseArr.clear();
                        EverydayMainProverbBookmarkFragment.proverbArr.clear();

                        intent = new Intent(BaseActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        finish();
                        break;

                    case R.id.toSetting:
                        EnglishWordDayStudyActivity.dataArr.clear();
                        EverydayMainExpressionBookmarkFragment.expressionArr.clear();
                        EverydayMainPhraseBookmarkFragment.phraseArr.clear();
                        EverydayMainProverbBookmarkFragment.proverbArr.clear();

                        intent = new Intent(BaseActivity.this, MainActivity.class);
                        intent.putExtra("moveToSetting", true);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        finish();
                        break;

                    case R.id.toTextSize:
                        showTextSizeSettingDialog(adapter, tvCount);
                        break;

                    case R.id.toSDMode:
                        setSDMODESetting(adapter, sdmode);
                        break;

                }
                return false;
            }
        });
        popup.show();
    }

    public void showPopupMenu(ImageButton btnMenu, final FragmentPagerAdapter adapter, final TextView tvCount, boolean showWithDialog) {

        PopupMenu popup = new PopupMenu(BaseActivity.this, btnMenu);
        MenuInflater inflater = popup.getMenuInflater();
        Menu menu = popup.getMenu();
        inflater.inflate(R.menu.menu_default, menu);

        menu.findItem(R.id.toWithDialog).setVisible(true);

        if (BuildConfig.APP_NAME.equals(SOCIALWORKER) || BuildConfig.APP_NAME.equals(CERTIFIEDREALTOR) || BuildConfig.APP_NAME.equals(CERTIFIEDREALTOR2)) {
            menu.findItem(R.id.toWithDialog).setVisible(false);
            menu.findItem(R.id.toWithQuest).setVisible(false);
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = null;

                switch (item.getItemId()) {
                    case R.id.toMain:
                        EnglishWordDayStudyActivity.dataArr.clear();
                        EverydayMainExpressionBookmarkFragment.expressionArr.clear();
                        EverydayMainPhraseBookmarkFragment.phraseArr.clear();
                        EverydayMainProverbBookmarkFragment.proverbArr.clear();

                        intent = new Intent(BaseActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        finish();
                        break;

                    case R.id.toSetting:
                        EnglishWordDayStudyActivity.dataArr.clear();
                        EverydayMainExpressionBookmarkFragment.expressionArr.clear();
                        EverydayMainPhraseBookmarkFragment.phraseArr.clear();
                        EverydayMainProverbBookmarkFragment.proverbArr.clear();

                        intent = new Intent(BaseActivity.this, MainActivity.class);
                        intent.putExtra("moveToSetting", true);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        finish();
                        break;

                    case R.id.toTextSize:
                        showTextSizeSettingDialog(adapter, tvCount);
                        break;

                    case R.id.toWithDialog:
                        WithBottomSheetDialog bottomSheetDialog = WithBottomSheetDialog.getInstance();
                        bottomSheetDialog.show(getSupportFragmentManager(), "bottomSheet");
                        break;

                    case R.id.toWithQuest:
                        startActivityForResult(new Intent(BaseActivity.this, StudyGroupTalkQuestSettingDialog.class), PrefConsts.INTENT_REQUEST_STUDY_GROUP_SETTING_TALK_QUEST_SETTING);

                        break;

                }
                return false;
            }
        });
        popup.show();
    }

    public void showPopupMenu(ImageButton btnMenu, final FragmentPagerAdapter adapter, final TextView tvCount, final TextView tvBottom, boolean isShowWith) {

        PopupMenu popup = new PopupMenu(BaseActivity.this, btnMenu);
        MenuInflater inflater = popup.getMenuInflater();
        Menu menu = popup.getMenu();
        inflater.inflate(R.menu.menu_default, menu);

        if (isShowWith) {
            menu.findItem(R.id.toWithDialog).setVisible(true);
        }

        if (BuildConfig.APP_NAME.equals(SOCIALWORKER) || BuildConfig.APP_NAME.equals(CERTIFIEDREALTOR) || BuildConfig.APP_NAME.equals(CERTIFIEDREALTOR2)) {
            menu.findItem(R.id.toWithDialog).setVisible(false);
            menu.findItem(R.id.toWithQuest).setVisible(false);
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = null;

                switch (item.getItemId()) {
                    case R.id.toMain:
                        intent = new Intent(BaseActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        finish();
                        break;

                    case R.id.toSetting:
                        intent = new Intent(BaseActivity.this, MainActivity.class);
                        intent.putExtra("moveToSetting", true);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        finish();
                        break;

                    case R.id.toTextSize:
                        showTextSizeSettingDialog(adapter, tvCount, tvBottom);
                        break;

                    case R.id.toTodayState:
                        showTodayStateDialog();
                        break;

                    case R.id.toWithDialog:
                        WithBottomSheetDialog bottomSheetDialog = WithBottomSheetDialog.getInstance();
                        bottomSheetDialog.show(getSupportFragmentManager(), "bottomSheet");
                        break;

                    case R.id.toWithQuest:
                        startActivityForResult(new Intent(BaseActivity.this, StudyGroupTalkQuestSettingDialog.class), PrefConsts.INTENT_REQUEST_STUDY_GROUP_SETTING_TALK_QUEST_SETTING);
                        break;

                }
                return false;
            }
        });
        popup.show();
    }

    public void showPopupMenu(ImageButton btnMenu, final FragmentPagerAdapter adapter, final TextView tvCount, boolean showWithDialog, boolean showWithSetting) {

        PopupMenu popup = new PopupMenu(BaseActivity.this, btnMenu);
        MenuInflater inflater = popup.getMenuInflater();
        Menu menu = popup.getMenu();
        inflater.inflate(R.menu.menu_default, menu);

        menu.findItem(R.id.toWithDialog).setVisible(true);

        if (showWithSetting) {
            menu.findItem(R.id.toWithQuest).setVisible(true);
        }

        if (BuildConfig.APP_NAME.equals(SOCIALWORKER) || BuildConfig.APP_NAME.equals(CERTIFIEDREALTOR) || BuildConfig.APP_NAME.equals(CERTIFIEDREALTOR2)) {
            menu.findItem(R.id.toWithDialog).setVisible(false);
            menu.findItem(R.id.toWithQuest).setVisible(false);
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = null;

                switch (item.getItemId()) {
                    case R.id.toMain:
                        EnglishWordDayStudyActivity.dataArr.clear();
                        EverydayMainExpressionBookmarkFragment.expressionArr.clear();
                        EverydayMainPhraseBookmarkFragment.phraseArr.clear();
                        EverydayMainProverbBookmarkFragment.proverbArr.clear();

                        intent = new Intent(BaseActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        finish();
                        break;

                    case R.id.toSetting:
                        EnglishWordDayStudyActivity.dataArr.clear();
                        EverydayMainExpressionBookmarkFragment.expressionArr.clear();
                        EverydayMainPhraseBookmarkFragment.phraseArr.clear();
                        EverydayMainProverbBookmarkFragment.proverbArr.clear();

                        intent = new Intent(BaseActivity.this, MainActivity.class);
                        intent.putExtra("moveToSetting", true);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        finish();
                        break;

                    case R.id.toTextSize:
                        showTextSizeSettingDialog(adapter, tvCount);
                        break;

                    case R.id.toWithDialog:
                        WithBottomSheetDialog bottomSheetDialog = WithBottomSheetDialog.getInstance();
                        bottomSheetDialog.show(getSupportFragmentManager(), "bottomSheet");
                        break;

                    case R.id.toWithQuest:
                        startActivity(new Intent(BaseActivity.this, StudyGroupTalkQuestSettingDialog.class));

                        break;

                }
                return false;
            }
        });
        popup.show();
    }

    public void showPopupMenu(ImageButton btnMenu, final FragmentPagerAdapter adapter, final TextView tvCount, final TextView tvBottom, boolean isShowWith, boolean showWithSetting) {

        PopupMenu popup = new PopupMenu(BaseActivity.this, btnMenu);
        MenuInflater inflater = popup.getMenuInflater();
        Menu menu = popup.getMenu();
        inflater.inflate(R.menu.menu_default, menu);

        if (isShowWith) {
            menu.findItem(R.id.toWithDialog).setVisible(true);
        }
        if (showWithSetting) {
            menu.findItem(R.id.toWithQuest).setVisible(true);
        }

        if (BuildConfig.APP_NAME.equals(SOCIALWORKER) || BuildConfig.APP_NAME.equals(CERTIFIEDREALTOR) || BuildConfig.APP_NAME.equals(CERTIFIEDREALTOR2)) {
            menu.findItem(R.id.toWithDialog).setVisible(false);
            menu.findItem(R.id.toWithQuest).setVisible(false);
        }


        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = null;

                switch (item.getItemId()) {
                    case R.id.toMain:
                        intent = new Intent(BaseActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        finish();
                        break;

                    case R.id.toSetting:
                        intent = new Intent(BaseActivity.this, MainActivity.class);
                        intent.putExtra("moveToSetting", true);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        finish();
                        break;

                    case R.id.toTextSize:
                        showTextSizeSettingDialog(adapter, tvCount, tvBottom);
                        break;

                    case R.id.toTodayState:
                        showTodayStateDialog();
                        break;

                    case R.id.toWithDialog:
                        WithBottomSheetDialog bottomSheetDialog = WithBottomSheetDialog.getInstance();
                        bottomSheetDialog.show(getSupportFragmentManager(), "bottomSheet");
                        break;

                    case R.id.toWithQuest:
                        startActivity(new Intent(BaseActivity.this, StudyGroupTalkQuestSettingDialog.class));
                        break;

                }
                return false;
            }
        });
        popup.show();
    }

    //글자크기 변경
    public void showTodayStateDialog() {
        DialogUtil.showTodayState(this, userCode);
    }

    public void setSDMODESetting(FragmentPagerAdapter adapter, boolean sdMode){
        PrefHelper.setBoolean(this, PrefConsts.ENGLISH_SDMODE_PRETEST_SETTING, !sdMode);
        adapter.notifyDataSetChanged();

    }

    public void showTextSizeSettingDialog() {
        DialogUtil.showTextSizeDialog(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void showTextSizeSettingDialog(final FragmentPagerAdapter adapter, final TextView tvCount) {

        DialogUtil.showTextSizeDialog(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void showTextSizeSettingDialog(final FragmentPagerAdapter adapter, TextView tvCount, final TextView bottom) {

        DialogUtil.showTextSizeDialog(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
                bottom.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize);
            }
        });
    }

    public void showTextSizeSettingDialogNoParam() {

        DialogUtil.showTextSizeDialog(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void initToast() {
        try {
            if (showOXToast) {
                toastTrue = Toast.makeText(BaseActivity.this, "", Toast.LENGTH_SHORT);
                toastWrong = Toast.makeText(BaseActivity.this, "", Toast.LENGTH_SHORT);

                ImageView imgT = new ImageButton(BaseActivity.this);
                ImageView imgF = new ImageButton(BaseActivity.this);

                imgT.setBackgroundResource(R.drawable.mark_o);
                imgF.setBackgroundResource(R.drawable.mark_x);

                toastTrue.setView(imgT);
                toastWrong.setView(imgF);

                toastTrue.setGravity(Gravity.CENTER, 0, 0);
                toastWrong.setGravity(Gravity.CENTER, 0, 0);

                toastTrue.setDuration(Toast.LENGTH_SHORT);
                toastWrong.setDuration(Toast.LENGTH_SHORT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToast(boolean flag) {
        try {
            if (showOXToast) {
                if (toastTrue.getView().isShown()) {
                    toastTrue.cancel();
                }
                if (toastWrong.getView().isShown()) {
                    toastWrong.cancel();
                }
                if (flag) {
                    toastTrue.show();
                } else {
                    toastWrong.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeToast() {
        try {
            if (showOXToast) {
                if (toastTrue.getView().isShown()) {
                    toastTrue.cancel();
                }
                if (toastWrong.getView().isShown()) {
                    toastWrong.cancel();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void moveToNextPage() {
    }

    public void moveToPrePage() {
    }

    public void httpPurcharse(String className) {
        if (CommonUtils.getConnectNetwork(BaseActivity.this)) {

            JsonObject jo = new JsonObject();
            jo.addProperty("user_code", userCode);
            jo.addProperty("app_version", CommonUtils.getAppVersion(this));
            jo.addProperty("device_id", Build.MODEL);
            jo.addProperty("user_id", userID);

            RequestData.getInstance().getPgGoodsInfo(jo, new NetworkResponse<JsonObject>() {

                @Override
                public void onSuccess(Call call, JsonObject clazz) {
                    DisplayUtils.hideProgressDialog();

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
                                purcharseArr.add(purcharseData);//NullPointerException: Attempt to invoke virtual method 'java.lang.String com.google.gson.JsonElement.getAsString()' on a null object reference
                            }
                            title = goodsInfo.get("title").getAsString();
                            String app_permit = goodsInfo.get("app_permit").getAsString();
                            permit = app_permit;
                        }

                        try{
                            if (className.contains("MainActivity")) {
                                LogUtil.d("instanceof MainActivity");
                                ((MainActivity) BaseActivity.this).setPurcharse();

                            }else if(className.contains("SettingActivity")){
                                LogUtil.d("instanceof SettingActivity");
                                ((SettingActivity) BaseActivity.this).loginResult();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFail(Call call, String msg) {
                    Toast.makeText(BaseActivity.this, getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(BaseActivity.this, getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                moveToNextPage();
            } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                moveToPrePage();

            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

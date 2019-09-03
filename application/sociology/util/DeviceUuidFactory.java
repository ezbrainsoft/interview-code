package com.jlesoft.civilservice.koreanhistoryexam9.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.jlesoft.civilservice.koreanhistoryexam9.BaseActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.BuildConfig;
import com.jlesoft.civilservice.koreanhistoryexam9.PrefConsts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.UUID;


public class DeviceUuidFactory {

    private Context context;

    protected volatile static UUID uuid;
    SharedPreferences prefs;

    public DeviceUuidFactory(Context context) {
        this.context = context;
        if (uuid == null) {

            synchronized (DeviceUuidFactory.class) {

                if (uuid == null) {
                    prefs = context.getSharedPreferences(PrefConsts.PREF_FILE_NAME, 0);
                    String id = prefs.getString(PrefConsts.PREF_TEMP_DEVICE_ID, null);

                    if (id != null) {
                        uuid = UUID.fromString(id);
                    } else {
                        getFileUUID();
                    }
                }
            }
        }
    }

    private void getFileUUID(){
        File file = new File(context.getFilesDir(), BuildConfig.UUID_NAME);
        LogUtil.d("UUID file = " + file.getAbsolutePath());
        LogUtil.d("UUID file = " + context.getFilesDir().getPath());

        if (!file.exists()) {
            makeUUID();

        } else {
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String uuidFile = bufferedReader.readLine();

                if (TextUtils.isEmpty(uuidFile) || uuidFile.equalsIgnoreCase("null")) {
                    makeUUID();

                } else {
                    prefs.edit().putString(PrefConsts.PREF_TEMP_DEVICE_ID, uuidFile.toString()).commit();
                    uuid = UUID.fromString(uuidFile);
                }

            } catch (NullPointerException e) {
                makeUUID();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void makeUUID(){
        final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        try {
            if (!"9774d56d682e549c".equals(androidId)) {
                uuid = UUID.nameUUIDFromBytes(androidId
                        .getBytes("utf8"));
            } else {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    //권한이 없는 경우

                    //최초 권한 요청인지, 혹은 사용자에 의한 재요청인지 확인
                    if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_PHONE_STATE)){
                        //사용자가 임의로 권한을 취소시킨 경우
                        //권한재요청

                        final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                        uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();

                    }else {
                        //최초로 권한을 요청하는 경우(첫실행)
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, PrefConsts.REQUEST_PERMISSION_READ_PHONE_STATE);
                    }

                }else {
                    //사용 권한이 있음을 확인한 경우
                    String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                    uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        // Write the value out to the prefs file
        prefs.edit().putString(PrefConsts.PREF_TEMP_DEVICE_ID, uuid.toString()).commit();
    }

    public UUID getDeviceUuid() {
        return uuid;
    }

}

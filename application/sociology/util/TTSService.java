package com.jlesoft.civilservice.koreanhistoryexam9.util;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;


import com.jlesoft.civilservice.koreanhistoryexam9.PrefConsts;
import com.jlesoft.civilservice.koreanhistoryexam9.englishWord.EnglishWordDaySoundSettingActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.englishWord.model.EnglishWordDayListDao;

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.Nullable;

import static android.speech.tts.TextToSpeech.SUCCESS;

public class TTSService extends Service {

    private boolean isStop;
    private TextToSpeech engTts;
    private TextToSpeech korTts;
    private String listener, refresh, language;
    private ArrayList<EnglishWordDayListDao.WordDayItem> sendArr = new ArrayList<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("TTSService onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sendArr.addAll((ArrayList<EnglishWordDayListDao.WordDayItem>) intent.getSerializableExtra("data"));

        listener = intent.getStringExtra("listener");
        refresh = intent.getStringExtra("refresh");

        language = PrefHelper.getString(this, PrefConsts.EVERYDAY_KEY_LANGUAGE , PrefConsts.LANGUAGE_ENGLISH );

        LogUtil.d("TTSService : " + sendArr.size() + " && " + listener + " && " + refresh);

        if (sendArr.size() > 0) {

            korTts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {

                    if (status == SUCCESS) {
                        int result = korTts.setLanguage(Locale.KOREA);
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            //지원안함
                        } else {
                            korTts.setPitch(0.7f);//음성 톤
                            korTts.setSpeechRate(1.25f);//읽는 속도
                        }

                        if(language.equals(PrefConsts.LANGUAGE_ENGLISH)){
                            setEngTtsSetting();
                        }else{
                            Thread thread = new Thread(new TTSSound());
                            thread.start();
                        }

                    }
                }
            });


        } else {
            stopSelf();
        }

        return Service.START_NOT_STICKY;
    }


    private void setEngTtsSetting() {

        engTts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status == SUCCESS) {
                    int result = engTts.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        //지원안함
                    } else {
                        engTts.setPitch(0.7f);//음성 톤
                        engTts.setSpeechRate(1.25f);//읽는 속도
                    }

                    Thread thread = new Thread(new TTSSound());
                    thread.start();
                }
            }
        });
    }


    private class TTSSound implements Runnable {
        private Handler handler = new Handler();

        @Override
        public void run() {
            try {

                handler.post(new Runnable() {
                    @Override
                    public void run() { // Toast로 Count 띄우기
                        Toast.makeText(getApplicationContext(), "듣기가 시작됩니다.", Toast.LENGTH_SHORT).show(); // Log로 Count 찍어보기
                    }
                });

                while (true) {

                    for (int i = 0; i < sendArr.size(); i++) {
                        if (isStop) {
                            break;
                        }

                        LogUtil.d("TTSService word = " + sendArr.get(i).word);
                        if(language.equals(PrefConsts.LANGUAGE_ENGLISH)){
                            engTts.speak(sendArr.get(i).word, TextToSpeech.QUEUE_ADD, null, null);
                        }else{
                            korTts.speak(sendArr.get(i).word, TextToSpeech.QUEUE_ADD, null, null);
                        }


                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (listener.equals(EnglishWordDaySoundSettingActivity.LISTENER_WORD_MEAN)) {
                            for (String explain : sendArr.get(i).info.explainKor) {
                                korTts.speak(explain, TextToSpeech.QUEUE_ADD, null, null);
                                LogUtil.d("TTSService explain = " + explain);
                            }

                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    if (refresh.equals(EnglishWordDaySoundSettingActivity.REFRESH_ONE)) {
                        break;
                    }
                }
            } catch (Exception e) {
                LogUtil.d(e.toString());
                stopSelf();
            }
        }
    }

    @Override
    public void onDestroy() {
        LogUtil.d("TTLService onDestroy");
        if (korTts != null) {
            korTts.stop();
            korTts.shutdown();
        }
        if (engTts != null) {
            engTts.stop();
            engTts.shutdown();
        }
        isStop = true;

        Toast.makeText(getApplicationContext(), "종료되었습니다. ", Toast.LENGTH_SHORT).show(); // Log로 Count 찍어보기
        super.onDestroy();
    }
}

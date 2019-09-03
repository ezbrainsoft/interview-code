package com.jlesoft.civilservice.koreanhistoryexam9.word;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.widget.Toast;

import com.jlesoft.civilservice.koreanhistoryexam9.PrefConsts;
import com.jlesoft.civilservice.koreanhistoryexam9.englishWord.EnglishWordDaySoundSettingActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.englishWord.model.EnglishWordDayListDao;
import com.jlesoft.civilservice.koreanhistoryexam9.util.LogUtil;
import com.jlesoft.civilservice.koreanhistoryexam9.util.PrefHelper;
import com.jlesoft.civilservice.koreanhistoryexam9.util.StringUtil;
import com.jlesoft.civilservice.koreanhistoryexam9.word.model.WordQuestionDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import androidx.annotation.Nullable;

import static android.speech.tts.TextToSpeech.SUCCESS;

public class WordTTSService extends Service {

    private boolean isStop;
    private TextToSpeech korTts;
    private String order, refresh;
    private ArrayList<WordQuestionDao.WordQuestionItem> sendArr = new ArrayList<>();
    private float speed;
    private boolean exam, mean, word, syno;
    private String sqClass;
    private Thread thread;
    boolean bookmark;


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
        sendArr.addAll((ArrayList<WordQuestionDao.WordQuestionItem>) intent.getSerializableExtra("data"));
        sqClass = intent.getStringExtra("sq_class");


        order = PrefHelper.getString(this, PrefConsts.WORD_KEY_ORDER, PrefConsts.ORDER);
        if (order == PrefConsts.ORDER_RANDOM)
            Collections.shuffle(sendArr);

        refresh = PrefHelper.getString(this, PrefConsts.WORD_KEY_REFRESH, PrefConsts.REFRESH_ONE);
        speed = PrefHelper.getFloat(this, PrefConsts.WORD_KEY_SPEED, 1.25f);

        if (sqClass.equals("V")) {//표준어
            word = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_WORD, true);
            mean = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_MEAN, false);
            exam = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_EXAM, false);

            LogUtil.d("TTSService V: " + sendArr.size() + " && " + word + " && " + mean + " && " + exam + " && " + order + " && " + refresh + " && " + speed);

            if (!word && !mean && !exam) {
                stopSelf();
            }

        } else if (sqClass.equals("W")) { //외래어
            word = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_W_WORD, true);
            mean = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_W_MEAN, false);

            LogUtil.d("TTSService W: " + sendArr.size() + " && " + word + " && " + mean + " && " + order + " && " + refresh + " && " + speed);

            if (!word && !mean) {
                stopSelf();
            }

        } else if (sqClass.equals("X")) { //사자성어
            word = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_X_WORD, true);
            mean = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_X_MEAN, false);
            exam = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_X_EXAM, false);

            LogUtil.d("TTSService X: " + sendArr.size() + " && " + word + " && " + mean + " && " + exam + " && " + order + " && " + refresh + " && " + speed);

            if (!word && !mean && !exam) {
                stopSelf();
            }

        } else if (sqClass.equals("Y")) { //한자어
            word = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_Y_WORD, true);
            mean = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_Y_MEAN, false);
            exam = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_Y_EXAM, false);
            exam = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_Y_EXAM, false);
            syno = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_Y_SYNONYM, false);

            LogUtil.d("TTSService : " + sendArr.size() + " && " + word + " && " + syno + " && " + mean + " && " + order + " && " + refresh + " && " + speed);

            if (!word && !syno && !mean && !exam) {
                stopSelf();
            }

        } else if (sqClass.equals("F")) { //고유어
            word = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_F_WORD, true);
            mean = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_F_MEAN, false);
            exam = PrefHelper.getBoolean(this, PrefConsts.WORD_KEY_LISTENER_F_EXAM, false);

            LogUtil.d("TTSService F: " + sendArr.size() + " && " + word + " && " + mean + " && " + exam + " && " + order + " && " + refresh + " && " + speed);

            if (!word && !mean && !exam) {
                stopSelf();
            }
        }

        LogUtil.d("TTSService : " + sendArr.size() + " && " + sqClass);

        if (sendArr.size() > 0 && !TextUtils.isEmpty(sqClass)) {
            setKorTtsSetting();
        } else {
            stopSelf();
        }
        return Service.START_NOT_STICKY;
    }


    private void setKorTtsSetting() {
        korTts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status == SUCCESS) {
                    int result = korTts.setLanguage(Locale.KOREA);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        //지원안함
                    } else {
                        korTts.setPitch(0.7f);//음성 톤
                        korTts.setSpeechRate(speed);//읽는 속도
                    }

                    thread = new Thread(new TTSSound());
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

                        if (!sqClass.equals("F")) { // 표준어 / 외래어 / 사자성어 / 한자어
                            if (word) {
                                if (sqClass.equals("X") || sqClass.equals("Y")) {
                                    korTts.speak(sendArr.get(i).getSqiWContents(), TextToSpeech.QUEUE_ADD, null, null);
                                } else {
                                    korTts.speak(sendArr.get(i).getTKeyword(), TextToSpeech.QUEUE_ADD, null, null);
                                }

                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                            if (mean) {
                                if (sqClass.equals("W")) {
                                    String meanThird[] = sendArr.get(i).getSqiCommentary().split(" - ");

                                    if (meanThird.length > 0) {
                                        String strMean = meanThird[meanThird.length - 1];
                                        if (!TextUtils.isEmpty(strMean)) {
                                            korTts.speak(StringUtil.removeAllSpecialCharacter(strMean), TextToSpeech.QUEUE_ADD, null, null);
                                        }
                                    }

                                } else if (sqClass.equals("X") || sqClass.equals("Y")) {
                                    korTts.speak(StringUtil.removeAllSpecialCharacter(sendArr.get(i).getFKeyword()), TextToSpeech.QUEUE_ADD, null, null);
                                } else {
                                    korTts.speak(StringUtil.removeAllSpecialCharacter(sendArr.get(i).getSqiCommentary()), TextToSpeech.QUEUE_ADD, null, null);
                                }

                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                            if (exam) {
                                if (sqClass.equals("V")) {
                                    korTts.speak(StringUtil.removeAllSpecialCharacter(sendArr.get(i).getSqiRContents()), TextToSpeech.QUEUE_ADD, null, null);
                                } else {
                                    korTts.speak(StringUtil.removeAllSpecialCharacter(sendArr.get(i).getSqiCommentary()), TextToSpeech.QUEUE_ADD, null, null);
                                }

                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }

                            if (syno) {
                                korTts.speak(StringUtil.removeAllSpecialCharacter(sendArr.get(i).getSynonym()), TextToSpeech.QUEUE_ADD, null, null);

                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else { // 고유어
                            if (word) {
                                korTts.speak(sendArr.get(i).getSqiRContents(), TextToSpeech.QUEUE_ADD, null, null);

                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                            if (mean) {
                                korTts.speak(StringUtil.removeAllSpecialCharacter(sendArr.get(i).getSqiCommentary()), TextToSpeech.QUEUE_ADD, null, null);

                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                            if (exam) {
                                korTts.speak(StringUtil.removeAllSpecialCharacter(sendArr.get(i).getTKeyword()), TextToSpeech.QUEUE_ADD, null, null);

                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (refresh.equals(PrefConsts.REFRESH_ONE)) {
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

        isStop = true;

        Toast.makeText(getApplicationContext(), "종료되었습니다. ", Toast.LENGTH_SHORT).show(); // Log로 Count 찍어보기
        super.onDestroy();
    }
}

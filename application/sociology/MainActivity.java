package com.jlesoft.civilservice.koreanhistoryexam9;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jlesoft.civilservice.koreanhistoryexam9.combineSearch.CombineSearchMainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.compare.CompareMainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.day.mainDay.DayKoreanQuizActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.day.mainDay.DayOXQuizActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.day.mainDay.DayQuizActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.englishWord.EnglishWordDayQuizActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.englishWord.EnglishWordMainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.englishWord.model.EnglishWordDayQuizDao;
import com.jlesoft.civilservice.koreanhistoryexam9.everydayEnglish.EverydayEnglishMainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.fcm.MyFirebaseInstanceIDService;
import com.jlesoft.civilservice.koreanhistoryexam9.fcm.NotificationManager;
import com.jlesoft.civilservice.koreanhistoryexam9.fitStudy.FitMainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.grammar.GrammarMainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.grammar.GrammarOXQuizActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.grammar.dao.GrammarOXDao;
import com.jlesoft.civilservice.koreanhistoryexam9.history.SolvedHistoryMainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.keyword.KeywordMainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.day.mainTest.MainTestListActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.keyword.KeywordStudyActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api110GetDownloadTodayTen;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api206GetDailyTodayTenKor;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api75GetSmartNoteQuestion;
import com.jlesoft.civilservice.koreanhistoryexam9.model.DefaultOXQuestion;
import com.jlesoft.civilservice.koreanhistoryexam9.model.DefaultQuestionListDao;
import com.jlesoft.civilservice.koreanhistoryexam9.model.EverydayDailyStudyDao;
import com.jlesoft.civilservice.koreanhistoryexam9.model.popup.Api146GetBbsBannerList;
import com.jlesoft.civilservice.koreanhistoryexam9.model.popup.Api91GetConfigDashBoard;
import com.jlesoft.civilservice.koreanhistoryexam9.model.setting.PurcharseData;
import com.jlesoft.civilservice.koreanhistoryexam9.network.NetworkResponse;
import com.jlesoft.civilservice.koreanhistoryexam9.network.RequestData;
import com.jlesoft.civilservice.koreanhistoryexam9.popup.PopupBannerActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.popup.PopupCalendarActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.popup.PopupChartActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.popup.PopupNoticeActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.popup.SunjiSaveListActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.previous.PreTestEnglishSeriesQuestionActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.previous.PreTestKoreanSeriesQuestionActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.previous.PreTestMainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.randomOX.RandomOXMainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.reading.EnglishReadingMainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.setting.NoticeActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.setting.NoticeDetailActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.setting.payment.PaymentListActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.setting.payment.PaymentOldVerDetailActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.smartNote.SmartNoteMainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.smartNote.SmartNoteViewActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.sourceBook.SourceBookMainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.study.DanwonMainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.studygroup.StudyRoomListActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.studygroup.talk.StudyGroupTalkActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.user.LoginActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.util.CommonUtils;
import com.jlesoft.civilservice.koreanhistoryexam9.util.CompletionChart;
import com.jlesoft.civilservice.koreanhistoryexam9.util.DialogUtil;
import com.jlesoft.civilservice.koreanhistoryexam9.util.DisplayUtils;
import com.jlesoft.civilservice.koreanhistoryexam9.util.LogUtil;
import com.jlesoft.civilservice.koreanhistoryexam9.util.PrefHelper;
import com.jlesoft.civilservice.koreanhistoryexam9.util.StringUtil;
import com.jlesoft.civilservice.koreanhistoryexam9.word.WordMainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.word.WordOXQuizActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.word.model.WordQuestionDao;
import com.jlesoft.civilservice.koreanhistoryexam9.wrongAnswerNoteBook.WrongAnswerNoteBookMainActivity;

import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;


public class MainActivity extends BaseActivity {
    @BindView(R.id.tvNotice)
    TextView tvNotice;

    @BindView(R.id.scroll)
    ScrollView scroll;

    @BindView(R.id.menu_list)
    RecyclerView menuList;

    @BindView(R.id.progress_chart1)
    CompletionChart chart1;
    @BindView(R.id.progress_chart2)
    CompletionChart chart2;

    @BindView(R.id.tv_question_progress)
    TextView tvQuestionProgress;
    @BindView(R.id.tv_question_score)
    TextView tvQuestionScore;
    @BindView(R.id.tv_past_progress)
    TextView tvPastProgress;
    @BindView(R.id.tv_past_score)
    TextView tvPastScore;
    @BindView(R.id.progress_chart3)
    CompletionChart chart3;
    @BindView(R.id.progress_chart4)
    CompletionChart chart4;

    //하단메뉴
    @BindView(R.id.btnHelp)
    ImageView btnHelp;
    @BindView(R.id.btnHistory)
    ImageView btnHistory;
    @BindView(R.id.btnCalendar)
    ImageView btnCalendar;
    @BindView(R.id.btnMyPage)
    ImageView btnMyPage;

    //학습현황
    @BindView(R.id.board_state_gruop)
    RadioGroup boardStateGruop;
    @BindView(R.id.status_bar_lay)
    LinearLayout statusBarLay;
    @BindView(R.id.status_newbie_lay)
    LinearLayout statusNewbieLay;
    @BindView(R.id.status_day_lay)
    LinearLayout statusDayLay;
    @BindView(R.id.status_test_lay)
    LinearLayout statusTestLay;
    @BindView(R.id.status_everyday_lay)
    LinearLayout statusEverydayLay;

    //일일문제
    @BindView(R.id.btn_day_study)
    RadioButton btnDayStudy;
    @BindView(R.id.btn_day_ox)
    RadioButton btnDayOX;
    @BindView(R.id.btn_day_previous)
    RadioButton btnDayPre;
    @BindView(R.id.btn_day_note)
    RadioButton btnDayNote;
    @BindView(R.id.btn_day_keyword)
    RadioButton btnDayKeyword;
    @BindView(R.id.btn_day_voca)
    RadioButton btnDayVoca;
    @BindView(R.id.btn_day_grammar)
    RadioButton btnDayGrammar;
    @BindView(R.id.rg_day)
    RadioGroup dayMenuGroup;
    @BindView(R.id.tv_board_msg)
    TextView tvBoardMsg;


    //일일문제 - 강화
    @BindView(R.id.tv_q1_correct)
    TextView tv_q1_correct;
    @BindView(R.id.tv_q2_correct)
    TextView tv_q2_correct;
    @BindView(R.id.tv_q3_correct)
    TextView tv_q3_correct;
    @BindView(R.id.tv_q4_correct)
    TextView tv_q4_correct;
    @BindView(R.id.tv_q5_correct)
    TextView tv_q5_correct;
    @BindView(R.id.tv_q1_result)
    TextView tv_q1_result;
    @BindView(R.id.tv_q2_result)
    TextView tv_q2_result;
    @BindView(R.id.tv_q3_result)
    TextView tv_q3_result;
    @BindView(R.id.tv_q4_result)
    TextView tv_q4_result;
    @BindView(R.id.tv_q5_result)
    TextView tv_q5_result;
    @BindView(R.id.tv_q6_correct)
    TextView tv_q6_correct;
    @BindView(R.id.tv_q7_correct)
    TextView tv_q7_correct;
    @BindView(R.id.tv_q8_correct)
    TextView tv_q8_correct;
    @BindView(R.id.tv_q9_correct)
    TextView tv_q9_correct;
    @BindView(R.id.tv_q10_correct)
    TextView tv_q10_correct;
    @BindView(R.id.tv_q6_result)
    TextView tv_q6_result;
    @BindView(R.id.tv_q7_result)
    TextView tv_q7_result;
    @BindView(R.id.tv_q8_result)
    TextView tv_q8_result;
    @BindView(R.id.tv_q9_result)
    TextView tv_q9_result;
    @BindView(R.id.tv_q10_result)
    TextView tv_q10_result;

    @BindView(R.id.status_day_study)
    LinearLayout statusDayStudy;

    @BindView(R.id.ll_q1)
    LinearLayout ll_q1;
    @BindView(R.id.ll_q2)
    LinearLayout ll_q2;
    @BindView(R.id.ll_q3)
    LinearLayout ll_q3;
    @BindView(R.id.ll_q4)
    LinearLayout ll_q4;
    @BindView(R.id.ll_q5)
    LinearLayout ll_q5;
    @BindView(R.id.ll_q6)
    LinearLayout ll_q6;
    @BindView(R.id.ll_q7)
    LinearLayout ll_q7;
    @BindView(R.id.ll_q8)
    LinearLayout ll_q8;
    @BindView(R.id.ll_q9)
    LinearLayout ll_q9;
    @BindView(R.id.ll_q10)
    LinearLayout ll_q10;

    //일일문제 - 어휘
    @BindView(R.id.tv_voca_q1_correct)
    TextView tv_voca_q1_correct;
    @BindView(R.id.tv_voca_q2_correct)
    TextView tv_voca_q2_correct;
    @BindView(R.id.tv_voca_q3_correct)
    TextView tv_voca_q3_correct;
    @BindView(R.id.tv_voca_q4_correct)
    TextView tv_voca_q4_correct;
    @BindView(R.id.tv_voca_q5_correct)
    TextView tv_voca_q5_correct;
    @BindView(R.id.tv_voca_q1_result)
    TextView tv_voca_q1_result;
    @BindView(R.id.tv_voca_q2_result)
    TextView tv_voca_q2_result;
    @BindView(R.id.tv_voca_q3_result)
    TextView tv_voca_q3_result;
    @BindView(R.id.tv_voca_q4_result)
    TextView tv_voca_q4_result;
    @BindView(R.id.tv_voca_q5_result)
    TextView tv_voca_q5_result;
    @BindView(R.id.tv_voca_q6_correct)
    TextView tv_voca_q6_correct;
    @BindView(R.id.tv_voca_q7_correct)
    TextView tv_voca_q7_correct;
    @BindView(R.id.tv_voca_q8_correct)
    TextView tv_voca_q8_correct;
    @BindView(R.id.tv_voca_q9_correct)
    TextView tv_voca_q9_correct;
    @BindView(R.id.tv_voca_q10_correct)
    TextView tv_voca_q10_correct;
    @BindView(R.id.tv_voca_q6_result)
    TextView tv_voca_q6_result;
    @BindView(R.id.tv_voca_q7_result)
    TextView tv_voca_q7_result;
    @BindView(R.id.tv_voca_q8_result)
    TextView tv_voca_q8_result;
    @BindView(R.id.tv_voca_q9_result)
    TextView tv_voca_q9_result;
    @BindView(R.id.tv_voca_q10_result)
    TextView tv_voca_q10_result;

    @BindView(R.id.status_day_voca)
    LinearLayout statusDayVoca;

    @BindView(R.id.ll_voca_q1)
    LinearLayout ll_voca_q1;
    @BindView(R.id.ll_voca_q2)
    LinearLayout ll_voca_q2;
    @BindView(R.id.ll_voca_q3)
    LinearLayout ll_voca_q3;
    @BindView(R.id.ll_voca_q4)
    LinearLayout ll_voca_q4;
    @BindView(R.id.ll_voca_q5)
    LinearLayout ll_voca_q5;
    @BindView(R.id.ll_voca_q6)
    LinearLayout ll_voca_q6;
    @BindView(R.id.ll_voca_q7)
    LinearLayout ll_voca_q7;
    @BindView(R.id.ll_voca_q8)
    LinearLayout ll_voca_q8;
    @BindView(R.id.ll_voca_q9)
    LinearLayout ll_voca_q9;
    @BindView(R.id.ll_voca_q10)
    LinearLayout ll_voca_q10;

    //일일문제 - 문법
    @BindView(R.id.tv_grammar_q1_correct)
    TextView tv_grammar_q1_correct;
    @BindView(R.id.tv_grammar_q2_correct)
    TextView tv_grammar_q2_correct;
    @BindView(R.id.tv_grammar_q3_correct)
    TextView tv_grammar_q3_correct;
    @BindView(R.id.tv_grammar_q4_correct)
    TextView tv_grammar_q4_correct;
    @BindView(R.id.tv_grammar_q5_correct)
    TextView tv_grammar_q5_correct;
    @BindView(R.id.tv_grammar_q1_result)
    TextView tv_grammar_q1_result;
    @BindView(R.id.tv_grammar_q2_result)
    TextView tv_grammar_q2_result;
    @BindView(R.id.tv_grammar_q3_result)
    TextView tv_grammar_q3_result;
    @BindView(R.id.tv_grammar_q4_result)
    TextView tv_grammar_q4_result;
    @BindView(R.id.tv_grammar_q5_result)
    TextView tv_grammar_q5_result;
    @BindView(R.id.tv_grammar_q6_correct)
    TextView tv_grammar_q6_correct;
    @BindView(R.id.tv_grammar_q7_correct)
    TextView tv_grammar_q7_correct;
    @BindView(R.id.tv_grammar_q8_correct)
    TextView tv_grammar_q8_correct;
    @BindView(R.id.tv_grammar_q9_correct)
    TextView tv_grammar_q9_correct;
    @BindView(R.id.tv_grammar_q10_correct)
    TextView tv_grammar_q10_correct;
    @BindView(R.id.tv_grammar_q6_result)
    TextView tv_grammar_q6_result;
    @BindView(R.id.tv_grammar_q7_result)
    TextView tv_grammar_q7_result;
    @BindView(R.id.tv_grammar_q8_result)
    TextView tv_grammar_q8_result;
    @BindView(R.id.tv_grammar_q9_result)
    TextView tv_grammar_q9_result;
    @BindView(R.id.tv_grammar_q10_result)
    TextView tv_grammar_q10_result;

    @BindView(R.id.status_day_grammar)
    LinearLayout statusDayGrammar;

    @BindView(R.id.ll_grammar_q1)
    LinearLayout ll_grammar_q1;
    @BindView(R.id.ll_grammar_q2)
    LinearLayout ll_grammar_q2;
    @BindView(R.id.ll_grammar_q3)
    LinearLayout ll_grammar_q3;
    @BindView(R.id.ll_grammar_q4)
    LinearLayout ll_grammar_q4;
    @BindView(R.id.ll_grammar_q5)
    LinearLayout ll_grammar_q5;
    @BindView(R.id.ll_grammar_q6)
    LinearLayout ll_grammar_q6;
    @BindView(R.id.ll_grammar_q7)
    LinearLayout ll_grammar_q7;
    @BindView(R.id.ll_grammar_q8)
    LinearLayout ll_grammar_q8;
    @BindView(R.id.ll_grammar_q9)
    LinearLayout ll_grammar_q9;
    @BindView(R.id.ll_grammar_q10)
    LinearLayout ll_grammar_q10;

    //일일문제 - 기출
    @BindView(R.id.tv_pre_q1_correct)
    TextView tv_pre_q1_correct;
    @BindView(R.id.tv_pre_q2_correct)
    TextView tv_pre_q2_correct;
    @BindView(R.id.tv_pre_q3_correct)
    TextView tv_pre_q3_correct;
    @BindView(R.id.tv_pre_q4_correct)
    TextView tv_pre_q4_correct;
    @BindView(R.id.tv_pre_q5_correct)
    TextView tv_pre_q5_correct;
    @BindView(R.id.tv_pre_q6_result)
    TextView tv_pre_q6_result;
    @BindView(R.id.tv_pre_q7_result)
    TextView tv_pre_q7_result;
    @BindView(R.id.tv_pre_q8_result)
    TextView tv_pre_q8_result;
    @BindView(R.id.tv_pre_q9_result)
    TextView tv_pre_q9_result;
    @BindView(R.id.tv_pre_q10_result)
    TextView tv_pre_q10_result;

    @BindView(R.id.tv_pre_q1_result)
    TextView tv_pre_q1_result;
    @BindView(R.id.tv_pre_q2_result)
    TextView tv_pre_q2_result;
    @BindView(R.id.tv_pre_q3_result)
    TextView tv_pre_q3_result;
    @BindView(R.id.tv_pre_q4_result)
    TextView tv_pre_q4_result;
    @BindView(R.id.tv_pre_q5_result)
    TextView tv_pre_q5_result;
    @BindView(R.id.tv_pre_q6_correct)
    TextView tv_pre_q6_correct;
    @BindView(R.id.tv_pre_q7_correct)
    TextView tv_pre_q7_correct;
    @BindView(R.id.tv_pre_q8_correct)
    TextView tv_pre_q8_correct;
    @BindView(R.id.tv_pre_q9_correct)
    TextView tv_pre_q9_correct;
    @BindView(R.id.tv_pre_q10_correct)
    TextView tv_pre_q10_correct;

    @BindView(R.id.status_day_pre)
    LinearLayout statusDayPre;

    @BindView(R.id.ll_pre_q1)
    LinearLayout ll_pre_q1;
    @BindView(R.id.ll_pre_q2)
    LinearLayout ll_pre_q2;
    @BindView(R.id.ll_pre_q3)
    LinearLayout ll_pre_q3;
    @BindView(R.id.ll_pre_q4)
    LinearLayout ll_pre_q4;
    @BindView(R.id.ll_pre_q5)
    LinearLayout ll_pre_q5;
    @BindView(R.id.ll_pre_q6)
    LinearLayout ll_pre_q6;
    @BindView(R.id.ll_pre_q7)
    LinearLayout ll_pre_q7;
    @BindView(R.id.ll_pre_q8)
    LinearLayout ll_pre_q8;
    @BindView(R.id.ll_pre_q9)
    LinearLayout ll_pre_q9;
    @BindView(R.id.ll_pre_q10)
    LinearLayout ll_pre_q10;

    //일일문제- ox
    @BindView(R.id.tv_ox_q1_correct)
    TextView tv_ox_q1_correct;
    @BindView(R.id.tv_ox_q2_correct)
    TextView tv_ox_q2_correct;
    @BindView(R.id.tv_ox_q3_correct)
    TextView tv_ox_q3_correct;
    @BindView(R.id.tv_ox_q4_correct)
    TextView tv_ox_q4_correct;
    @BindView(R.id.tv_ox_q5_correct)
    TextView tv_ox_q5_correct;
    @BindView(R.id.tv_ox_q1_result)
    TextView tv_ox_q1_result;
    @BindView(R.id.tv_ox_q2_result)
    TextView tv_ox_q2_result;
    @BindView(R.id.tv_ox_q3_result)
    TextView tv_ox_q3_result;
    @BindView(R.id.tv_ox_q4_result)
    TextView tv_ox_q4_result;
    @BindView(R.id.tv_ox_q5_result)
    TextView tv_ox_q5_result;
    @BindView(R.id.tv_ox_q6_correct)
    TextView tv_ox_q6_correct;
    @BindView(R.id.tv_ox_q7_correct)
    TextView tv_ox_q7_correct;
    @BindView(R.id.tv_ox_q8_correct)
    TextView tv_ox_q8_correct;
    @BindView(R.id.tv_ox_q9_correct)
    TextView tv_ox_q9_correct;
    @BindView(R.id.tv_ox_q10_correct)
    TextView tv_ox_q10_correct;
    @BindView(R.id.tv_ox_q6_result)
    TextView tv_ox_q6_result;
    @BindView(R.id.tv_ox_q7_result)
    TextView tv_ox_q7_result;
    @BindView(R.id.tv_ox_q8_result)
    TextView tv_ox_q8_result;
    @BindView(R.id.tv_ox_q9_result)
    TextView tv_ox_q9_result;
    @BindView(R.id.tv_ox_q10_result)
    TextView tv_ox_q10_result;

    @BindView(R.id.status_day_ox)
    LinearLayout statusDayOX;

    @BindView(R.id.ll_ox_q1)
    LinearLayout ll_ox_q1;
    @BindView(R.id.ll_ox_q2)
    LinearLayout ll_ox_q2;
    @BindView(R.id.ll_ox_q3)
    LinearLayout ll_ox_q3;
    @BindView(R.id.ll_ox_q4)
    LinearLayout ll_ox_q4;
    @BindView(R.id.ll_ox_q5)
    LinearLayout ll_ox_q5;
    @BindView(R.id.ll_ox_q6)
    LinearLayout ll_ox_q6;
    @BindView(R.id.ll_ox_q7)
    LinearLayout ll_ox_q7;
    @BindView(R.id.ll_ox_q8)
    LinearLayout ll_ox_q8;
    @BindView(R.id.ll_ox_q9)
    LinearLayout ll_ox_q9;
    @BindView(R.id.ll_ox_q10)
    LinearLayout ll_ox_q10;

    //일일문제 - 스마트노트
    @BindView(R.id.status_day_note)
    LinearLayout statusDayNote;
    @BindView(R.id.day_note_list)
    ListView dayNoteList;

    //일일문제 - 키워드
    @BindView(R.id.status_day_keyword)
    LinearLayout statusDayKeyword;
    @BindView(R.id.day_keyword_list)
    ListView dayKeywordList;


    //일일기본문제
    @BindView(R.id.ll_newbie_root)
    RelativeLayout llNewbieRoot;
    @BindView(R.id.ll_newbie_previous)
    LinearLayout llNewbiePrevious;
    @BindView(R.id.ll_newbie_next)
    LinearLayout llNewbieNext;

    @BindView(R.id.btn_newbie_day_study)
    RadioButton btnNewbieDayStudy;
    @BindView(R.id.btn_newbie_day_ox)
    RadioButton btnNewbieDayOX;
    @BindView(R.id.btn_newbie_day_previous)
    RadioButton btnNewbieDayPre;
    @BindView(R.id.btn_newbie_day_note)
    RadioButton btnNewbieDayNote;
    @BindView(R.id.btn_newbie_day_keyword)
    RadioButton btnNewbieDayKeyword;
    @BindView(R.id.rg_newbie_day)
    RadioGroup dayNewbieMenuGroup;
    @BindView(R.id.tv_newbie_board_msg)
    TextView tvNewbieBoardMsg;
    @BindView(R.id.tv_newbie_board_count)
    TextView tvNewbieBoardCount;
    @BindView(R.id.tv_newbie_board_total_count)
    TextView tvNewbieBoardTotalCount;


    //일일기본문제 - 강화
    @BindView(R.id.tv_newbie_q1_correct)
    TextView tv_newbie_q1_correct;
    @BindView(R.id.tv_newbie_q2_correct)
    TextView tv_newbie_q2_correct;
    @BindView(R.id.tv_newbie_q3_correct)
    TextView tv_newbie_q3_correct;
    @BindView(R.id.tv_newbie_q4_correct)
    TextView tv_newbie_q4_correct;
    @BindView(R.id.tv_newbie_q5_correct)
    TextView tv_newbie_q5_correct;
    @BindView(R.id.tv_newbie_q1_result)
    TextView tv_newbie_q1_result;
    @BindView(R.id.tv_newbie_q2_result)
    TextView tv_newbie_q2_result;
    @BindView(R.id.tv_newbie_q3_result)
    TextView tv_newbie_q3_result;
    @BindView(R.id.tv_newbie_q4_result)
    TextView tv_newbie_q4_result;
    @BindView(R.id.tv_newbie_q5_result)
    TextView tv_newbie_q5_result;
    @BindView(R.id.tv_newbie_q6_correct)
    TextView tv_newbie_q6_correct;
    @BindView(R.id.tv_newbie_q7_correct)
    TextView tv_newbie_q7_correct;
    @BindView(R.id.tv_newbie_q8_correct)
    TextView tv_newbie_q8_correct;
    @BindView(R.id.tv_newbie_q9_correct)
    TextView tv_newbie_q9_correct;
    @BindView(R.id.tv_newbie_q10_correct)
    TextView tv_newbie_q10_correct;
    @BindView(R.id.tv_newbie_q6_result)
    TextView tv_newbie_q6_result;
    @BindView(R.id.tv_newbie_q7_result)
    TextView tv_newbie_q7_result;
    @BindView(R.id.tv_newbie_q8_result)
    TextView tv_newbie_q8_result;
    @BindView(R.id.tv_newbie_q9_result)
    TextView tv_newbie_q9_result;
    @BindView(R.id.tv_newbie_q10_result)
    TextView tv_newbie_q10_result;

    @BindView(R.id.status_newbie_day_study)
    LinearLayout statusNewbieDayStudy;

    @BindView(R.id.ll_newbie_q1)
    LinearLayout ll_newbie_q1;
    @BindView(R.id.ll_newbie_q2)
    LinearLayout ll_newbie_q2;
    @BindView(R.id.ll_newbie_q3)
    LinearLayout ll_newbie_q3;
    @BindView(R.id.ll_newbie_q4)
    LinearLayout ll_newbie_q4;
    @BindView(R.id.ll_newbie_q5)
    LinearLayout ll_newbie_q5;
    @BindView(R.id.ll_newbie_q6)
    LinearLayout ll_newbie_q6;
    @BindView(R.id.ll_newbie_q7)
    LinearLayout ll_newbie_q7;
    @BindView(R.id.ll_newbie_q8)
    LinearLayout ll_newbie_q8;
    @BindView(R.id.ll_newbie_q9)
    LinearLayout ll_newbie_q9;
    @BindView(R.id.ll_newbie_q10)
    LinearLayout ll_newbie_q10;

    //일일기본문제 - 기출
    @BindView(R.id.tv_newbie_pre_q1_correct)
    TextView tv_newbie_pre_q1_correct;
    @BindView(R.id.tv_newbie_pre_q2_correct)
    TextView tv_newbie_pre_q2_correct;
    @BindView(R.id.tv_newbie_pre_q3_correct)
    TextView tv_newbie_pre_q3_correct;
    @BindView(R.id.tv_newbie_pre_q4_correct)
    TextView tv_newbie_pre_q4_correct;
    @BindView(R.id.tv_newbie_pre_q5_correct)
    TextView tv_newbie_pre_q5_correct;
    @BindView(R.id.tv_newbie_pre_q6_result)
    TextView tv_newbie_pre_q6_result;
    @BindView(R.id.tv_newbie_pre_q7_result)
    TextView tv_newbie_pre_q7_result;
    @BindView(R.id.tv_newbie_pre_q8_result)
    TextView tv_newbie_pre_q8_result;
    @BindView(R.id.tv_newbie_pre_q9_result)
    TextView tv_newbie_pre_q9_result;
    @BindView(R.id.tv_newbie_pre_q10_result)
    TextView tv_newbie_pre_q10_result;

    @BindView(R.id.tv_newbie_pre_q1_result)
    TextView tv_newbie_pre_q1_result;
    @BindView(R.id.tv_newbie_pre_q2_result)
    TextView tv_newbie_pre_q2_result;
    @BindView(R.id.tv_newbie_pre_q3_result)
    TextView tv_newbie_pre_q3_result;
    @BindView(R.id.tv_newbie_pre_q4_result)
    TextView tv_newbie_pre_q4_result;
    @BindView(R.id.tv_newbie_pre_q5_result)
    TextView tv_newbie_pre_q5_result;
    @BindView(R.id.tv_newbie_pre_q6_correct)
    TextView tv_newbie_pre_q6_correct;
    @BindView(R.id.tv_newbie_pre_q7_correct)
    TextView tv_newbie_pre_q7_correct;
    @BindView(R.id.tv_newbie_pre_q8_correct)
    TextView tv_newbie_pre_q8_correct;
    @BindView(R.id.tv_newbie_pre_q9_correct)
    TextView tv_newbie_pre_q9_correct;
    @BindView(R.id.tv_newbie_pre_q10_correct)
    TextView tv_newbie_pre_q10_correct;

    @BindView(R.id.status_newbie_day_pre)
    LinearLayout statusNewbieDayPre;

    @BindView(R.id.ll_newbie_pre_q1)
    LinearLayout ll_newbie_pre_q1;
    @BindView(R.id.ll_newbie_pre_q2)
    LinearLayout ll_newbie_pre_q2;
    @BindView(R.id.ll_newbie_pre_q3)
    LinearLayout ll_newbie_pre_q3;
    @BindView(R.id.ll_newbie_pre_q4)
    LinearLayout ll_newbie_pre_q4;
    @BindView(R.id.ll_newbie_pre_q5)
    LinearLayout ll_newbie_pre_q5;
    @BindView(R.id.ll_newbie_pre_q6)
    LinearLayout ll_newbie_pre_q6;
    @BindView(R.id.ll_newbie_pre_q7)
    LinearLayout ll_newbie_pre_q7;
    @BindView(R.id.ll_newbie_pre_q8)
    LinearLayout ll_newbie_pre_q8;
    @BindView(R.id.ll_newbie_pre_q9)
    LinearLayout ll_newbie_pre_q9;
    @BindView(R.id.ll_newbie_pre_q10)
    LinearLayout ll_newbie_pre_q10;

    //일일기본문제- ox
    @BindView(R.id.tv_newbie_ox_q1_correct)
    TextView tv_newbie_ox_q1_correct;
    @BindView(R.id.tv_newbie_ox_q2_correct)
    TextView tv_newbie_ox_q2_correct;
    @BindView(R.id.tv_newbie_ox_q3_correct)
    TextView tv_newbie_ox_q3_correct;
    @BindView(R.id.tv_newbie_ox_q4_correct)
    TextView tv_newbie_ox_q4_correct;
    @BindView(R.id.tv_newbie_ox_q5_correct)
    TextView tv_newbie_ox_q5_correct;
    @BindView(R.id.tv_newbie_ox_q1_result)
    TextView tv_newbie_ox_q1_result;
    @BindView(R.id.tv_newbie_ox_q2_result)
    TextView tv_newbie_ox_q2_result;
    @BindView(R.id.tv_newbie_ox_q3_result)
    TextView tv_newbie_ox_q3_result;
    @BindView(R.id.tv_newbie_ox_q4_result)
    TextView tv_newbie_ox_q4_result;
    @BindView(R.id.tv_newbie_ox_q5_result)
    TextView tv_newbie_ox_q5_result;
    @BindView(R.id.tv_newbie_ox_q6_correct)
    TextView tv_newbie_ox_q6_correct;
    @BindView(R.id.tv_newbie_ox_q7_correct)
    TextView tv_newbie_ox_q7_correct;
    @BindView(R.id.tv_newbie_ox_q8_correct)
    TextView tv_newbie_ox_q8_correct;
    @BindView(R.id.tv_newbie_ox_q9_correct)
    TextView tv_newbie_ox_q9_correct;
    @BindView(R.id.tv_newbie_ox_q10_correct)
    TextView tv_newbie_ox_q10_correct;
    @BindView(R.id.tv_newbie_ox_q6_result)
    TextView tv_newbie_ox_q6_result;
    @BindView(R.id.tv_newbie_ox_q7_result)
    TextView tv_newbie_ox_q7_result;
    @BindView(R.id.tv_newbie_ox_q8_result)
    TextView tv_newbie_ox_q8_result;
    @BindView(R.id.tv_newbie_ox_q9_result)
    TextView tv_newbie_ox_q9_result;
    @BindView(R.id.tv_newbie_ox_q10_result)
    TextView tv_newbie_ox_q10_result;

    @BindView(R.id.status_newbie_day_ox)
    LinearLayout statusNewbieDayOX;

    @BindView(R.id.ll_newbie_ox_q1)
    LinearLayout ll_newbie_ox_q1;
    @BindView(R.id.ll_newbie_ox_q2)
    LinearLayout ll_newbie_ox_q2;
    @BindView(R.id.ll_newbie_ox_q3)
    LinearLayout ll_newbie_ox_q3;
    @BindView(R.id.ll_newbie_ox_q4)
    LinearLayout ll_newbie_ox_q4;
    @BindView(R.id.ll_newbie_ox_q5)
    LinearLayout ll_newbie_ox_q5;
    @BindView(R.id.ll_newbie_ox_q6)
    LinearLayout ll_newbie_ox_q6;
    @BindView(R.id.ll_newbie_ox_q7)
    LinearLayout ll_newbie_ox_q7;
    @BindView(R.id.ll_newbie_ox_q8)
    LinearLayout ll_newbie_ox_q8;
    @BindView(R.id.ll_newbie_ox_q9)
    LinearLayout ll_newbie_ox_q9;
    @BindView(R.id.ll_newbie_ox_q10)
    LinearLayout ll_newbie_ox_q10;

    //일일기본문제 - 스마트노트
    @BindView(R.id.status_newbie_day_note)
    LinearLayout statusNewbieDayNote;
    @BindView(R.id.day_newbie_note_list)
    ListView dayNewbieNoteList;

    //일일기본문제 - 키워드
    @BindView(R.id.status_newbie_day_keyword)
    LinearLayout statusNewbieDayKeyword;
    @BindView(R.id.day_newbie_keyword_list)
    ListView dayNewbieKeywordList;

    //강화시험
    @BindView(R.id.tv_take_state)
    TextView tvTakeState;
    @BindView(R.id.btn_take_state)
    TextView btnTakeState;
    @BindView(R.id.tv_grade)
    TextView tvGrade;
    @BindView(R.id.tv_total_grade)
    TextView tvTotalGrade;
    @BindView(R.id.tv_ranking)
    TextView tvRanking;
    @BindView(R.id.tv_total_ranking)
    TextView tvTotalRanking;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_average_time)
    TextView tvAverageTime;
    @BindView(R.id.tv_test_title)
    TextView tvTestTitle;

    //생활영어
    @BindView(R.id.rg_every)
    RadioGroup rgEveryGroup;
    @BindView(R.id.btn_day_phrase)
    RadioButton btn_day_phrase;
    @BindView(R.id.btn_day_proverb)
    RadioButton btn_day_proverb;
    @BindView(R.id.btn_day_expression)
    RadioButton btn_day_expression;

    @BindView(R.id.status_day_every)
    ScrollView status_day_every;
    @BindView(R.id.status_day_every_proverb)
    ScrollView status_day_every_proverb;
    @BindView(R.id.status_day_every_expression)
    ScrollView status_day_every_expression;

    @BindView(R.id.tv_idiom_r_contents)
    TextView tv_idiom_r_contents;
    @BindView(R.id.tv_idiom_commentary)
    TextView tv_idiom_commentary;
    @BindView(R.id.tv_idiom_w_contents)
    TextView tv_idiom_w_contents;
    @BindView(R.id.tv_idiom_f_keyword)
    TextView tv_idiom_f_keyword;
    @BindView(R.id.iv_idiom_bookmark)
    ImageView iv_idiom_bookmark;
    @BindView(R.id.tv_prob_r_conents)
    TextView tv_prob_r_conents;
    @BindView(R.id.tv_prob_w_contents)
    TextView tv_prob_w_contents;
    @BindView(R.id.tv_prob_commentary)
    TextView tv_prob_commentary;
    @BindView(R.id.iv_prob_bookmark)
    ImageView iv_prob_bookmark;
    @BindView(R.id.tv_exp_conents)
    TextView tv_exp_conents;
    @BindView(R.id.tv_exp_commentary)
    TextView tv_exp_commentary;
    @BindView(R.id.iv_exp_bookmark)
    ImageView iv_exp_bookmark;


    @BindView(R.id.tv_newbie_no_data)
    TextView tvNewbieNoData;

    @BindView(R.id.btn_day)
    RadioButton btnDay;
    @BindView(R.id.btn_everyday)
    RadioButton btnEveryday;
    @BindView(R.id.btn_state)
    RadioButton btnState;
    @BindView(R.id.btn_test)
    RadioButton btnTest;
    @BindView(R.id.btn_newbie)
    RadioButton btnNewbie;

    @BindView(R.id.btn_purcharse_other)
    Button btnPurcharseOther;
    @BindView(R.id.tv_purcharse_title)
    TextView tvPurcharseTitle;

    @BindView(R.id.ll_snackbar)
    LinearLayout llSnackbar;
    @BindView(R.id.btn_snack_other)
    Button btnSnackOther;
    @BindView(R.id.btn_snack_close)
    Button btnSnackClose;

    Intent i;
    Api91GetConfigDashBoard data;

    private boolean isShowProgress = true;

    DayNoteAdapter noteNewbieAdapter = null;

    DayNoteAdapter noteAdapter = null;
    DayKeywordAdapter keywordAdapter = null;
    MainMenuAdapter menuAdapter;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent.hasExtra("moveToDanwonStudy")) {
            moveToDanwonStudy();
        } else if (intent.hasExtra("moveToSubject")) {
            moveToSubject();
        } else if (intent.hasExtra("moveToWrongAnswerNote")) {
            moveToAnswerNote();
        } else if (intent.hasExtra("moveToPreTest1")) {
            moveToPreviousTests1();
        } else if (intent.hasExtra("moveToPreTest2")) {
            moveToPreviousTests2();
        } else if (intent.hasExtra("moveToSetting")) {
            moveToSetting();
        } else if (intent.hasExtra("moveToSmartNote")) {
            moveToSmartNote();
        } else if (intent.hasExtra("moveToFinish")) {
            finish();
        } else if (intent.hasExtra("moveToAccount")) {
            String date = getIntent().getStringExtra("date");
            moveToAccount(date);
        } else if (intent.hasExtra("btnKeyword")) {
            btnKeyword();
        } else if (intent.hasExtra("btnCompare")) {
            btnCompare();
        } else if (intent.hasExtra("moveToRandomOX")) {
            moveToRandomOX();
        } else if (intent.hasExtra("moveToMyPage")) {
            moveToMypage();
        } else if(intent.hasExtra("moveToEnglishWord")){
            clickEnglishBtnWord();//영어 어휘
        } else if(intent.hasExtra("moveToEveryDay")){
            moveToEveryDay();//생활영어
        } else if(intent.hasExtra("moveToGrammer")){
            moveToGrammar();//문법학습 영어
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        showBottomNotice();
        checkPopupNotice(false, "N");

        // TODO :: 이벤트 진행 시 해당 로직 주석 해제 및 DialogUtil 등 수정
       /* boolean showCoachmark = PrefHelper.getBoolean(this, PrefConsts.EVENT_DISPLAY, true);
        if (showCoachmark) {
            DialogUtil.showEventDialog(this, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tvEvent = (TextView) v.findViewById(R.id.tv_event);

                    if (tvEvent.getTag().toString().equalsIgnoreCase("openevent")) {
                        Intent i = new Intent(MainActivity.this, SurveyAndEventActivity.class);
                        i.putExtra("type", "openevent");
                        startActivity(i);
                    } else {
                        Intent i = new Intent(MainActivity.this, SurveyAndEventActivity.class);
                        i.putExtra("type", "survey");
                        startActivity(i);
                    }
                }
            });
        }*/

        initDayBindUI();

        btnDay.setChecked(true);
        boardStateGruop.setVisibility(View.GONE);

        if (BuildConfig.APP_NAME.equals(BaseActivity.KOREAN)) {
            btnDayStudy.setBackground(getResources().getDrawable(R.drawable.box_3372dc_radio_btn_select));
            btnDayNote.setVisibility(View.GONE);
            btnDayKeyword.setVisibility(View.GONE);

        } else if (BuildConfig.APP_NAME.equals(BaseActivity.ENGLISH)) {
            boardStateGruop.setVisibility(View.VISIBLE);
            btnState.setVisibility(View.GONE);
            btnNewbie.setVisibility(View.GONE);
            btnDay.setVisibility(View.VISIBLE);
            btnEveryday.setVisibility(View.VISIBLE);
            btnTest.setVisibility(View.GONE);

            btnDayStudy.setVisibility(View.GONE);
            btnDayKeyword.setVisibility(View.GONE);
            btnDayNote.setVisibility(View.GONE);
            btnDayOX.setVisibility(View.GONE);
            btnDayVoca.setVisibility(View.VISIBLE);
            btnDayGrammar.setVisibility(View.VISIBLE);
            btnDayPre.setChecked(true);

            btn_day_phrase.setChecked(true);

        } else if (BuildConfig.APP_NAME.equals(BaseActivity.SOCIALWORKER)) {
            btnDayStudy.setVisibility(View.GONE);
            btnDayKeyword.setVisibility(View.GONE);
            btnDayNote.setVisibility(View.GONE);
            btnDayPre.setChecked(true);

        } else if (BuildConfig.APP_NAME.equals(BaseActivity.CERTIFIEDREALTOR2)) {
            btnDayStudy.setVisibility(View.GONE);
            btnDayKeyword.setVisibility(View.GONE);
            btnDayNote.setVisibility(View.GONE);
            btnDayPre.setChecked(true);

        } else if (BuildConfig.APP_NAME.equals(BaseActivity.ADMINLAW)) {
            btnDayPre.setChecked(true);

        } else {
            btnDayStudy.setVisibility(View.GONE);
            btnDayKeyword.setVisibility(View.GONE);
            btnDayPre.setChecked(true);
        }

        btnHelp.setVisibility(View.GONE);
        btnHistory.setVisibility(View.GONE);
        if(BuildConfig.APP_NAME.equals(SOCIALWORKER) || BuildConfig.APP_NAME.equals(CERTIFIEDREALTOR)||BuildConfig.APP_NAME.equals(CERTIFIEDREALTOR2)){
            btnCalendar.setVisibility(View.GONE);
        }else{
            btnCalendar.setVisibility(View.VISIBLE);
        }
        btnMyPage.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(mainService)) {
            if (mainService.split(",").length < 3) {
                menuList.setLayoutManager(new LinearLayoutManager(this));

            } else if (mainService.split(",").length < 4) {
                menuList.setLayoutManager(new GridLayoutManager(this, 3));

            } else if (mainService.split(",").length == 4) {
                menuList.setLayoutManager(new GridLayoutManager(this, 2));

            } else {
                menuList.setLayoutManager(new GridLayoutManager(this, 4));
            }

            menuAdapter = new MainMenuAdapter();
            menuList.setAdapter(menuAdapter);
            menuList.addItemDecoration(new ItemDecoration());

            getExtraData();

        } else {
            Toast.makeText(this, "다시 실행해 주세요.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void getExtraData() {
        checkPushToken();

        if (getIntent().hasExtra("moveToDanwonStudy")) {
            moveToDanwonStudy();
        } else if (getIntent().hasExtra("moveToSubject")) {
            moveToSubject();
        } else if (getIntent().hasExtra("moveToWrongAnswerNote")) {
            moveToAnswerNote();
        } else if (getIntent().hasExtra("moveToPreTest1")) {
            moveToPreviousTests1();
        } else if (getIntent().hasExtra("moveToPreTest2")) {
            moveToPreviousTests2();
        } else if (getIntent().hasExtra("moveToSetting")) {
            moveToSetting();
        } else if (getIntent().hasExtra("moveToSmartNote")) {
            moveToSmartNote();
        } else if (getIntent().hasExtra("moveToFinish")) {
            finish();
        } else if (getIntent().hasExtra("moveToAccount")) {
            String date = getIntent().getStringExtra("date");
            moveToAccount(date);
        } else if (getIntent().hasExtra("btnKeyword")) {
            btnKeyword();
        } else if (getIntent().hasExtra("btnCompare")) {
            btnCompare();
        } else if (getIntent().hasExtra("moveToRandomOX")) {
            moveToRandomOX();
        } else if (getIntent().hasExtra("moveToMyPage")) {
            moveToMypage();
        } else if (getIntent().hasExtra("moveToSunji")) {
            moveToSunjiSave();
        } else if (getIntent().hasExtra("moveToFit")) {
//            moveToFitStudy();
        }else if(getIntent().hasExtra("moveToKoreanVoca")){
            clickKoreanBtnWord();
        } else if(getIntent().hasExtra("moveToEnglishWord")){
            clickEnglishBtnWord();//영어 어휘
        } else if(getIntent().hasExtra("moveToEveryDay")){
            moveToEveryDay();//생활영어
        } else if(getIntent().hasExtra("moveToGrammer")){
            moveToGrammar();//문법학습 영어
        }else {
            String click_action = getIntent().getStringExtra("click_action");
            String idx = getIntent().getStringExtra("idx");

            moveFCM(click_action, idx);
        }
    }

    public void moveFCM(String clickAction, String idx) {

        if (!TextUtils.isEmpty(clickAction)) {
            switch (clickAction) {
                case "voca":
                    Intent intentVoca = new Intent(this, NoticeActivity.class);
                    startActivity(intentVoca);
                    break;

                case "notice":
                    if (!TextUtils.isEmpty(idx)) {
                        Intent intent = new Intent(this, NoticeDetailActivity.class);
                        intent.putExtra("idx", idx);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(this, NoticeActivity.class);
                        startActivity(intent);
                    }
                    break;

                case "question":
                    if (!TextUtils.isEmpty(idx)) {
                        Intent intent = new Intent(this, DanwonMainActivity.class);
                        intent.putExtra("position", Integer.parseInt(idx));
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(this, DanwonMainActivity.class);
                        startActivity(intent);
                    }
                    break;

                case "smartnote":
                    moveToSmartNote();
                    break;

                case "pasttest":
                    if (!TextUtils.isEmpty(idx)) {
                        Intent intent1 = new Intent(this, PreTestMainActivity.class);
                        intent1.putExtra("position", Integer.parseInt(idx));
                        startActivity(intent1);
                    } else {
                        Intent intent1 = new Intent(this, PreTestMainActivity.class);
                        startActivity(intent1);
                    }
                    break;

                case "exam":
                    btnMainTest();
                    break;

                case "ox":
                    moveToAnswerNote();
                    break;

                case "history":
                    Intent intent1 = new Intent(this, SourceBookMainActivity.class);
                    startActivity(intent1);
                    break;

                case "setting":
                    Intent settingIntent = new Intent(this, SettingActivity.class);
                    startActivity(settingIntent);
                    break;

                case "payment":
                    if (!TextUtils.isEmpty(idx)) {
                        Intent payIntent = new Intent(this, PaymentOldVerDetailActivity.class);
                        payIntent.putExtra("idx", idx);
                        startActivity(payIntent);
                    } else {
                        Intent payIntent = new Intent(this, PaymentListActivity.class);
                        startActivity(payIntent);
                    }
                    break;

                case "survey":
                    Intent surveyIntent = new Intent(this, SurveyAndEventActivity.class);
                    surveyIntent.putExtra("type", "");
                    surveyIntent.putExtra("url", "");
                    startActivity(surveyIntent);
                    break;

                case "compare":
                    btnCompare();
                    break;

                case "keyword":
                    btnKeyword();
                    break;
                case "width":
                    Intent widthIntent = new Intent(this, StudyGroupTalkActivity.class);
                    widthIntent.putExtra("idx", idx);
                    startActivity(widthIntent);
                    break;
            }
        }
    }

    private void initDayBindUI() {
        boardStateGruop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.btn_day) {
                    httpTodayTenRate();
                    PrefHelper.setInt(MainActivity.this, PrefConsts.LAST_MAIN_BOARD_SELECT_OPEN, PrefConsts.MAIN_BOARD_DAY_QUIZ);
                    statusDayLay.setVisibility(View.VISIBLE);
                    statusNewbieLay.setVisibility(View.GONE);
                    statusBarLay.setVisibility(View.GONE);
                    statusTestLay.setVisibility(View.GONE);
                    statusEverydayLay.setVisibility(View.GONE);

                } else if (checkedId == R.id.btn_everyday) {
                    httpEverydayTenRate();
                    PrefHelper.setInt(MainActivity.this, PrefConsts.LAST_MAIN_BOARD_SELECT_OPEN, PrefConsts.MAIN_BOARD_EVERYDAY);
                    statusDayLay.setVisibility(View.GONE);
                    statusNewbieLay.setVisibility(View.GONE);
                    statusBarLay.setVisibility(View.GONE);
                    statusTestLay.setVisibility(View.GONE);
                    statusEverydayLay.setVisibility(View.VISIBLE);

                } else if (checkedId == R.id.btn_newbie) {
                    btnNewbie.clearAnimation();

                    httpNewbieTenRate(Integer.parseInt(tvNewbieBoardCount.getText().toString()));
                    PrefHelper.setInt(MainActivity.this, PrefConsts.LAST_MAIN_BOARD_SELECT_OPEN, PrefConsts.MAIN_BOARD_NEWBIE);
                    statusDayLay.setVisibility(View.GONE);
                    statusNewbieLay.setVisibility(View.VISIBLE);
                    statusBarLay.setVisibility(View.GONE);
                    statusTestLay.setVisibility(View.GONE);

                } else if (checkedId == R.id.btn_state) {
                    httpProgress(false);
                    PrefHelper.setInt(MainActivity.this, PrefConsts.LAST_MAIN_BOARD_SELECT_OPEN, PrefConsts.MAIN_BOARD_STUDY_STATUS);
                    statusDayLay.setVisibility(View.GONE);
                    statusNewbieLay.setVisibility(View.GONE);
                    statusBarLay.setVisibility(View.VISIBLE);
                    statusTestLay.setVisibility(View.GONE);

                } else if (checkedId == R.id.btn_test) {
                    httpTest();
                    PrefHelper.setInt(MainActivity.this, PrefConsts.LAST_MAIN_BOARD_SELECT_OPEN, PrefConsts.MAIN_BOARD_TEST);
                    statusDayLay.setVisibility(View.GONE);
                    statusNewbieLay.setVisibility(View.GONE);
                    statusBarLay.setVisibility(View.GONE);
                    statusTestLay.setVisibility(View.VISIBLE);
                }
            }
        });

        dayMenuGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_day_study:
                        statusDayStudy.setVisibility(View.VISIBLE);
                        statusDayOX.setVisibility(View.GONE);
                        statusDayPre.setVisibility(View.GONE);
                        statusDayVoca.setVisibility(View.GONE);
                        statusDayGrammar.setVisibility(View.GONE);
                        statusDayNote.setVisibility(View.GONE);
                        statusDayKeyword.setVisibility(View.GONE);
                        tvBoardMsg.setText(getString(R.string.main_board1_msg));
                        break;
                    case R.id.btn_day_ox:
                        statusDayStudy.setVisibility(View.GONE);
                        statusDayOX.setVisibility(View.VISIBLE);
                        statusDayPre.setVisibility(View.GONE);
                        statusDayVoca.setVisibility(View.GONE);
                        statusDayGrammar.setVisibility(View.GONE);
                        statusDayNote.setVisibility(View.GONE);
                        statusDayKeyword.setVisibility(View.GONE);
                        tvBoardMsg.setText(getString(R.string.main_board1_msg));
                        break;
                    case R.id.btn_day_previous:
                        statusDayStudy.setVisibility(View.GONE);
                        statusDayOX.setVisibility(View.GONE);
                        statusDayPre.setVisibility(View.VISIBLE);
                        statusDayVoca.setVisibility(View.GONE);
                        statusDayGrammar.setVisibility(View.GONE);
                        statusDayNote.setVisibility(View.GONE);
                        statusDayKeyword.setVisibility(View.GONE);
                        tvBoardMsg.setText(getString(R.string.main_board1_msg));
                        break;
                    case R.id.btn_day_voca:
                        statusDayStudy.setVisibility(View.GONE);
                        statusDayOX.setVisibility(View.GONE);
                        statusDayPre.setVisibility(View.GONE);
                        statusDayVoca.setVisibility(View.VISIBLE);
                        statusDayGrammar.setVisibility(View.GONE);
                        statusDayNote.setVisibility(View.GONE);
                        statusDayKeyword.setVisibility(View.GONE);
                        tvBoardMsg.setVisibility(View.GONE);
                        break;
                    case R.id.btn_day_grammar:
                        statusDayStudy.setVisibility(View.VISIBLE);
                        statusDayOX.setVisibility(View.GONE);
                        statusDayPre.setVisibility(View.GONE);
                        statusDayVoca.setVisibility(View.GONE);
                        statusDayGrammar.setVisibility(View.VISIBLE);
                        statusDayNote.setVisibility(View.GONE);
                        statusDayKeyword.setVisibility(View.GONE);
                        tvBoardMsg.setVisibility(View.GONE);
                        break;
                    case R.id.btn_day_note:
                        statusDayStudy.setVisibility(View.GONE);
                        statusDayOX.setVisibility(View.GONE);
                        statusDayPre.setVisibility(View.GONE);
                        statusDayVoca.setVisibility(View.GONE);
                        statusDayGrammar.setVisibility(View.GONE);
                        statusDayNote.setVisibility(View.VISIBLE);
                        statusDayKeyword.setVisibility(View.GONE);
                        tvBoardMsg.setText(getString(R.string.main_board2_msg));
                        break;
                    case R.id.btn_day_keyword:
                        statusDayStudy.setVisibility(View.GONE);
                        statusDayOX.setVisibility(View.GONE);
                        statusDayPre.setVisibility(View.GONE);
                        statusDayVoca.setVisibility(View.GONE);
                        statusDayGrammar.setVisibility(View.GONE);
                        statusDayNote.setVisibility(View.GONE);
                        statusDayKeyword.setVisibility(View.VISIBLE);
                        tvBoardMsg.setText(getString(R.string.main_board3_msg));
                        break;
                }
            }
        });

        rgEveryGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.btn_day_phrase) {
                    status_day_every.setVisibility(View.VISIBLE);
                    status_day_every_proverb.setVisibility(View.GONE);
                    status_day_every_expression.setVisibility(View.GONE);

                } else if (checkedId == R.id.btn_day_proverb) {
                    status_day_every.setVisibility(View.GONE);
                    status_day_every_proverb.setVisibility(View.VISIBLE);
                    status_day_every_expression.setVisibility(View.GONE);

                } else if (checkedId == R.id.btn_day_expression) {
                    status_day_every.setVisibility(View.GONE);
                    status_day_every_proverb.setVisibility(View.GONE);
                    status_day_every_expression.setVisibility(View.VISIBLE);
                }
            }
        });

        LinearLayout[] llDayLayes = {ll_q1, ll_q2, ll_q3, ll_q4, ll_q5, ll_q6, ll_q7, ll_q8, ll_q9, ll_q10};
        LinearLayout[] llDayOXLayes = {ll_ox_q1, ll_ox_q2, ll_ox_q3, ll_ox_q4, ll_ox_q5, ll_ox_q6, ll_ox_q7, ll_ox_q8, ll_ox_q9, ll_ox_q10};
        LinearLayout[] llDayPreLayes = {ll_pre_q1, ll_pre_q2, ll_pre_q3, ll_pre_q4, ll_pre_q5, ll_pre_q6, ll_pre_q7, ll_pre_q8, ll_pre_q9, ll_pre_q10};
        LinearLayout[] llDayVocaLayes = {ll_voca_q1, ll_voca_q2, ll_voca_q3, ll_voca_q4, ll_voca_q5, ll_voca_q6, ll_voca_q7, ll_voca_q8, ll_voca_q9, ll_voca_q10};
        LinearLayout[] llDayGrammarLayes = {ll_grammar_q1, ll_grammar_q2, ll_grammar_q3, ll_grammar_q4, ll_grammar_q5, ll_grammar_q6, ll_grammar_q7, ll_grammar_q8, ll_grammar_q9, ll_grammar_q10};

        for (int i = 0; i < llDayLayes.length; i++) {
            llDayLayes[i].setTag(i);
            llDayLayes[i].setOnClickListener(dayClick);
        }

        for (int i = 0; i < llDayLayes.length; i++) {
            llDayOXLayes[i].setTag(i);
            llDayOXLayes[i].setOnClickListener(dayClick);
        }

        for (int i = 0; i < llDayPreLayes.length; i++) {
            llDayPreLayes[i].setTag(i);
            llDayPreLayes[i].setOnClickListener(dayClick);
        }

        for (int i = 0; i < llDayVocaLayes.length; i++) {
            llDayVocaLayes[i].setTag(i);
            llDayVocaLayes[i].setOnClickListener(dayClick);
        }

        for (int i = 0; i < llDayGrammarLayes.length; i++) {
            llDayGrammarLayes[i].setTag(i);
            llDayGrammarLayes[i].setOnClickListener(dayClick);
        }

        btnDayStudy.setChecked(true);

        dayNewbieMenuGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_newbie_day_study:
                        statusNewbieDayStudy.setVisibility(View.VISIBLE);
                        statusNewbieDayOX.setVisibility(View.GONE);
                        statusNewbieDayPre.setVisibility(View.GONE);
                        statusNewbieDayNote.setVisibility(View.GONE);
                        statusNewbieDayKeyword.setVisibility(View.GONE);
                        tvNewbieBoardMsg.setText(getString(R.string.main_board1_newbie_msg));
                        break;

                    case R.id.btn_newbie_day_ox:
                        statusNewbieDayStudy.setVisibility(View.GONE);
                        statusNewbieDayOX.setVisibility(View.VISIBLE);
                        statusNewbieDayPre.setVisibility(View.GONE);
                        statusNewbieDayNote.setVisibility(View.GONE);
                        statusNewbieDayKeyword.setVisibility(View.GONE);
                        tvNewbieBoardMsg.setText(getString(R.string.main_board1_msg));
                        break;

                    case R.id.btn_newbie_day_previous:
                        statusNewbieDayStudy.setVisibility(View.GONE);
                        statusNewbieDayOX.setVisibility(View.GONE);
                        statusNewbieDayPre.setVisibility(View.VISIBLE);
                        statusNewbieDayNote.setVisibility(View.GONE);
                        statusNewbieDayKeyword.setVisibility(View.GONE);
                        tvNewbieBoardMsg.setText(getString(R.string.main_board1_msg));
                        break;

                    case R.id.btn_newbie_day_note:
                        statusNewbieDayStudy.setVisibility(View.GONE);
                        statusNewbieDayOX.setVisibility(View.GONE);
                        statusNewbieDayPre.setVisibility(View.GONE);
                        statusNewbieDayNote.setVisibility(View.VISIBLE);
                        statusNewbieDayKeyword.setVisibility(View.GONE);
                        tvNewbieBoardMsg.setText(getString(R.string.main_board2_msg));
                        break;

                    case R.id.btn_newbie_day_keyword:
                        statusNewbieDayStudy.setVisibility(View.GONE);
                        statusNewbieDayOX.setVisibility(View.GONE);
                        statusNewbieDayPre.setVisibility(View.GONE);
                        statusNewbieDayNote.setVisibility(View.GONE);
                        statusNewbieDayKeyword.setVisibility(View.VISIBLE);
                        tvNewbieBoardMsg.setText(getString(R.string.main_board3_msg));
                        break;

                }
            }
        });

        LinearLayout[] llNewbieDayLayes = {ll_newbie_q1, ll_newbie_q2, ll_newbie_q3, ll_newbie_q4, ll_newbie_q5, ll_newbie_q6, ll_newbie_q7, ll_newbie_q8, ll_newbie_q9, ll_newbie_q10};
        LinearLayout[] llNewbieDayOXLayes = {ll_newbie_ox_q1, ll_newbie_ox_q2, ll_newbie_ox_q3, ll_newbie_ox_q4, ll_newbie_ox_q5, ll_newbie_ox_q6, ll_newbie_ox_q7, ll_newbie_ox_q8, ll_newbie_ox_q9, ll_newbie_ox_q10};
        LinearLayout[] llNewbieDayPreLayes = {ll_newbie_pre_q1, ll_newbie_pre_q2, ll_newbie_pre_q3, ll_newbie_pre_q4, ll_newbie_pre_q5, ll_newbie_pre_q6, ll_newbie_pre_q7, ll_newbie_pre_q8, ll_newbie_pre_q9, ll_newbie_pre_q10};

        for (int i = 0; i < llDayLayes.length; i++) {
            llNewbieDayLayes[i].setTag(i);
            llNewbieDayLayes[i].setOnClickListener(dayClick);
        }

        for (int i = 0; i < llDayLayes.length; i++) {
            llNewbieDayOXLayes[i].setTag(i);
            llNewbieDayOXLayes[i].setOnClickListener(dayClick);
        }

        for (int i = 0; i < llDayPreLayes.length; i++) {
            llNewbieDayPreLayes[i].setTag(i);
            llNewbieDayPreLayes[i].setOnClickListener(dayClick);
        }

        btnNewbieDayStudy.setChecked(true);

        dayNoteList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scroll.requestDisallowInterceptTouchEvent(true);
                int action = event.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_UP:
                        scroll.requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });

        dayNewbieNoteList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                scroll.requestDisallowInterceptTouchEvent(true);
                int action = event.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_UP:
                        scroll.requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
        dayNewbieNoteList.setOnTouchListener(onTouch);
    }

    View.OnTouchListener onTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
            }
            return false;
        }
    };

    View.OnClickListener dayClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int num = (int) v.getTag();
            dayStatusClick(num);
        }
    };

    private void checkPushToken() {
        //푸쉬 정보 체크
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    return;
                }

                // Get new Instance ID token
                String getToken = task.getResult().getToken();
                String oldToken = PrefHelper.getString(MainActivity.this, PrefConsts.PUSH_TOKEN, "");

                if (!TextUtils.isEmpty(getToken) && !oldToken.equals(getToken) ) {
                    PrefHelper.setString(MainActivity.this, PrefConsts.PUSH_TOKEN, getToken);
                    MyFirebaseInstanceIDService.sendRegistrationToServer(getToken);
                }
            }
        });

        final boolean isPushEnable = NotificationManager.isNotificationEnabled(this);
        final boolean savePush = PrefHelper.getBoolean(this, PrefConsts.PUSH_SAVE_AGREE, true);
        Log.d(TAG, "push isEnable : " + isPushEnable);//현재 설정에서 세팅된 푸쉬 알람 여부 값  : true 동의, false 해제

        boolean isAgreement = PrefHelper.getBoolean(this, PrefConsts.PUSH_AGREEMENT, false);

        if (!isAgreement) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("중요 공지사항, 기능 개선 및 버그 수정, 설문 알람 등의 푸시 알람 전송에 동의하시겠습니까?\n\n※설정>알람 에서 변경가능");
            builder.setCancelable(false);
            builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        PrefHelper.setBoolean(MainActivity.this, PrefConsts.PUSH_SAVE_AGREE, isPushEnable);
                        httpPushEnable(isPushEnable);
                        dialog.dismiss();
                        return true;
                    }
                    return false;
                }
            });
            builder.setNegativeButton("동의안함", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (isPushEnable) {
                        goToNotification();
                    }
                }
            });
            builder.setPositiveButton("동의", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (!isPushEnable) {
                        goToNotification();
                    }
                    Toast.makeText(MainActivity.this, "알림을 수신 동의하였습니다.", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });
            builder.show();

            PrefHelper.setBoolean(this, PrefConsts.PUSH_AGREEMENT, true);
            httpPushEnable(isPushEnable);
        } else {
            if (isPushEnable != savePush) {
                PrefHelper.setBoolean(MainActivity.this, PrefConsts.PUSH_SAVE_AGREE, isPushEnable);
                httpPushEnable(isPushEnable);
            }
        }
    }

    private void goToNotification() {
        Intent intent = new Intent();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (getString(R.string.default_notification_channel_id) != null) {
                intent.setAction(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, getString(R.string.default_notification_channel_id));
            } else {
                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            }
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());

        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", getPackageName());
            intent.putExtra("app_uid", getApplicationInfo().uid);

        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + getPackageName()));
        }
        startActivityForResult(intent, PrefConsts.INTENT_REQUEST_PUSH_SETTING_ACTIVITY);
    }

    private void httpPushEnable(final boolean agree) {

        JsonObject jo = new JsonObject();
        jo.addProperty("user_code", userCode);
        if (agree) {
            jo.addProperty("agree_push", "Y");
        } else {
            jo.addProperty("agree_push", "N");
        }

        RequestData.getInstance().getMemberAgree(jo, new NetworkResponse<JsonObject>() {
            @Override
            public void onSuccess(Call call, JsonObject clazz) {
                LogUtil.d("동의 수신 변경 : " + agree);
            }

            @Override
            public void onFail(Call call, String msg) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //인앱정보
        setPurcharse();

        int checkId = boardStateGruop.getCheckedRadioButtonId();

        switch (checkId) {
            case R.id.btn_state:
                httpProgress(false);
                break;
            case R.id.btn_newbie:
                httpNewbieTenRate(Integer.parseInt(tvNewbieBoardCount.getText().toString()));//일일기본 정답률
                break;
            case R.id.btn_day:
                httpTodayTenRate();//일일랜덤 정답률
                break;
            case R.id.btn_test:
                httpTest();
                break;
            case R.id.btn_everyday:
                httpEverydayTenRate();
                break;
        }

        showBottomNotice();
        checkAppVersion();

        /*
        * 권한에 생활영어(daily)가 없는 경우 enable시킨다.
        * */
        if (BuildConfig.APP_NAME.equals(BaseActivity.ENGLISH)) {
            if (permit.contains("daily") || BuildConfig.DEBUG == false) {
                btnEveryday.setEnabled(true);
            } else {
                btnEveryday.setEnabled(false);
            }
        }
    }

    public void setPurcharse(){
        if (!TextUtils.isEmpty(userID) && !TextUtils.isEmpty(userCode)) { //아이디 체크
            if (purcharseArr.size() > 0) {
                tvPurcharseTitle.setText(period + "까지");
            } else {
                tvPurcharseTitle.setText("구매내역 없음");
            }
        } else {
            tvPurcharseTitle.setText("로그인 정보 없음");
            purcharseArr.clear();
        }
    }

    @OnClick(R.id.iv_refresh)
    void clickRefresh(){
        int checkId = boardStateGruop.getCheckedRadioButtonId();

        switch (checkId) {
            case R.id.btn_state:
                httpProgress(false);
                break;
            case R.id.btn_newbie:
                httpNewbieTenRate(Integer.parseInt(tvNewbieBoardCount.getText().toString()));//일일기본 정답률
                break;
            case R.id.btn_day:
                httpTodayTenRate();//일일랜덤 정답률
                break;
            case R.id.btn_test:
                httpTest();
                break;
            case R.id.btn_everyday:
                httpEverydayTenRate();
                break;
        }
    }

    @OnClick(R.id.ll_newbie_next)
    void clickBtnNewbieNext() {
        int count = Integer.parseInt(tvNewbieBoardCount.getText().toString());
        httpNewbieTenRate(count + 1);
    }

    @OnClick(R.id.ll_newbie_previous)
    void clickBtnNewbiePrevious() {
        int count = Integer.parseInt(tvNewbieBoardCount.getText().toString());
        if (count != 1) {
            httpNewbieTenRate(count - 1);
        } else {
            Toast toast = Toast.makeText(this, getString(R.string.msg_first_num), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void httpNewbieTenRate(int orderCode) {
        JsonObject jo = new JsonObject();
        jo.addProperty("user_code", userCode);
        jo.addProperty("order_code", orderCode);

        RequestData.getInstance().getDownloadTodayTenRateNewbie(jo, new NetworkResponse<JsonObject>() {

            @Override
            public void onSuccess(Call call, JsonObject clazz) {
                JsonObject resultData = clazz.get("resultData").getAsJsonObject();

                if (clazz.get("statusCode").getAsString().equalsIgnoreCase("200") && resultData.get("result").getAsString().equalsIgnoreCase("OK")) {
                    int question_cnt = resultData.get("question_cnt").getAsInt();
                    int past_cnt = resultData.get("past_cnt").getAsInt();
                    int ox_cnt = resultData.get("ox_cnt").getAsInt();
                    int smartnote_cnt = resultData.get("smartnote_cnt").getAsInt();
                    int keyword_cnt = resultData.get("keyword_cnt").getAsInt();

                    tvNewbieBoardCount.setText(resultData.get("order_code").getAsString());
                    tvNewbieBoardTotalCount.setText("/ 전체" + resultData.get("total_cnt").getAsString() + "회");

                    if (question_cnt == 0 && past_cnt == 0 && ox_cnt == 0 && smartnote_cnt == 0 && keyword_cnt == 0) {
                        btnNewbieDayStudy.setVisibility(View.GONE);
                        btnNewbieDayPre.setVisibility(View.GONE);
                        btnNewbieDayOX.setVisibility(View.GONE);
                        btnNewbieDayNote.setVisibility(View.GONE);
                        btnNewbieDayKeyword.setVisibility(View.GONE);

                        llNewbieRoot.setVisibility(View.VISIBLE);
                        statusNewbieDayStudy.setVisibility(View.GONE);
                        statusNewbieDayOX.setVisibility(View.GONE);
                        statusNewbieDayPre.setVisibility(View.GONE);
                        statusNewbieDayNote.setVisibility(View.GONE);
                        statusNewbieDayKeyword.setVisibility(View.GONE);
                        tvNewbieNoData.setVisibility(View.VISIBLE);

                        return;
                    }

                    if (question_cnt == 0) {
                        btnNewbieDayStudy.setVisibility(View.GONE);
                        if (btnNewbieDayStudy.isChecked()) {
                            statusNewbieDayStudy.setVisibility(View.GONE);
                            tvNewbieNoData.setVisibility(View.VISIBLE);
                        }
                    } else {
                        btnNewbieDayStudy.setVisibility(View.VISIBLE);
                    }

                    if (past_cnt == 0) {
                        btnNewbieDayPre.setVisibility(View.GONE);
                        if (btnNewbieDayPre.isChecked()) {
                            if (btnNewbieDayStudy.getVisibility() == View.VISIBLE) {
                                btnNewbieDayStudy.setChecked(true);
                            } else {
                                btnNewbieDayOX.setChecked(true);
                            }
                        }
                    } else {
                        btnNewbieDayPre.setVisibility(View.VISIBLE);
                    }

                    if (ox_cnt == 0) {
                        btnNewbieDayOX.setVisibility(View.GONE);
                        if (btnNewbieDayOX.isChecked()) {
                            if (btnNewbieDayStudy.getVisibility() == View.VISIBLE) {
                                btnNewbieDayStudy.setChecked(true);
                            } else {
                                btnNewbieDayNote.setChecked(true);
                            }
                        }
                    } else {
                        btnNewbieDayOX.setVisibility(View.VISIBLE);
                    }

                    if (smartnote_cnt == 0) {
                        btnNewbieDayNote.setVisibility(View.GONE);
                        if (btnNewbieDayNote.isChecked()) {
                            if (btnNewbieDayStudy.getVisibility() == View.VISIBLE) {
                                btnNewbieDayStudy.setChecked(true);
                            } else {
                                btnNewbieDayKeyword.setChecked(true);
                            }
                        }
                    } else {
                        btnNewbieDayNote.setVisibility(View.VISIBLE);
                    }

                    if (keyword_cnt == 0) {
                        btnNewbieDayKeyword.setVisibility(View.GONE);
                        if (btnNewbieDayKeyword.isChecked()) {
                            btnNewbieDayStudy.setChecked(true);
                        }
                    } else {
                        btnNewbieDayKeyword.setVisibility(View.VISIBLE);
                    }

                    JsonArray question = resultData.get("question").getAsJsonArray();
                    JsonArray ox = resultData.get("ox").getAsJsonArray();
                    JsonArray past = resultData.get("past").getAsJsonArray();
                    JsonArray smartnote = resultData.get("smartnote").getAsJsonArray();
                    JsonArray keyword = resultData.get("keyword").getAsJsonArray();


                    TextView correctArr[] = {tv_newbie_q1_correct, tv_newbie_q2_correct, tv_newbie_q3_correct, tv_newbie_q4_correct, tv_newbie_q5_correct, tv_newbie_q6_correct, tv_newbie_q7_correct, tv_newbie_q8_correct, tv_newbie_q9_correct, tv_newbie_q10_correct};
                    TextView resultArr[] = {tv_newbie_q1_result, tv_newbie_q2_result, tv_newbie_q3_result, tv_newbie_q4_result, tv_newbie_q5_result, tv_newbie_q6_result, tv_newbie_q7_result, tv_newbie_q8_result, tv_newbie_q9_result, tv_newbie_q10_result};

                    TextView correctOXArr[] = {tv_newbie_ox_q1_correct, tv_newbie_ox_q2_correct, tv_newbie_ox_q3_correct, tv_newbie_ox_q4_correct, tv_newbie_ox_q5_correct, tv_newbie_ox_q6_correct, tv_newbie_ox_q7_correct, tv_newbie_ox_q8_correct, tv_newbie_ox_q9_correct, tv_newbie_ox_q10_correct};
                    TextView resultOXArr[] = {tv_newbie_ox_q1_result, tv_newbie_ox_q2_result, tv_newbie_ox_q3_result, tv_newbie_ox_q4_result, tv_newbie_ox_q5_result, tv_newbie_ox_q6_result, tv_newbie_ox_q7_result, tv_newbie_ox_q8_result, tv_newbie_ox_q9_result, tv_newbie_ox_q10_result};

                    TextView correctPreArr[] = {tv_newbie_pre_q1_correct, tv_newbie_pre_q2_correct, tv_newbie_pre_q3_correct, tv_newbie_pre_q4_correct, tv_newbie_pre_q5_correct, tv_newbie_pre_q6_correct, tv_newbie_pre_q7_correct, tv_newbie_pre_q8_correct, tv_newbie_pre_q9_correct, tv_newbie_pre_q10_correct};
                    TextView resultPreArr[] = {tv_newbie_pre_q1_result, tv_newbie_pre_q2_result, tv_newbie_pre_q3_result, tv_newbie_pre_q4_result, tv_newbie_pre_q5_result, tv_newbie_pre_q6_result, tv_newbie_pre_q7_result, tv_newbie_pre_q8_result, tv_newbie_pre_q9_result, tv_newbie_pre_q10_result};


                    //강화
                    for (int i = 0; i < question.size(); i++) {
                        JsonObject obj = question.get(i).getAsJsonObject();
                        resultArr[i].setText(obj.get("correct_rate").getAsString() + "%");

                        if (obj.get("solve").getAsString().equalsIgnoreCase("Y")) {
                            if (obj.get("cs_is_result").getAsString().equalsIgnoreCase("f")) {
                                correctArr[i].setTextColor(getResources().getColor(R.color.col_9A081D));
                                correctArr[i].setText("X");
                            } else {
                                correctArr[i].setTextColor(getResources().getColor(R.color.col_085395));
                                correctArr[i].setText("O");
                            }
                        } else {
                            correctArr[i].setText("?");
                            correctArr[i].setTextColor(Color.BLACK);
                        }
                    }

                    //ox
                    for (int i = 0; i < ox.size(); i++) {
                        JsonObject obj = ox.get(i).getAsJsonObject();
                        resultOXArr[i].setText(obj.get("correct_rate").getAsString() + "%");

                        if (obj.get("solve").getAsString().equalsIgnoreCase("Y")) {
                            if (obj.get("cs_is_result").getAsString().equalsIgnoreCase("f")) {
                                correctOXArr[i].setTextColor(getResources().getColor(R.color.col_9A081D));
                                correctOXArr[i].setText("X");
                            } else {
                                correctOXArr[i].setTextColor(getResources().getColor(R.color.col_085395));
                                correctOXArr[i].setText("O");
                            }
                        } else {
                            correctOXArr[i].setText("?");
                            correctOXArr[i].setTextColor(Color.BLACK);
                        }
                    }

                    //기출
                    for (int i = 0; i < past.size(); i++) {
                        JsonObject obj = past.get(i).getAsJsonObject();
                        resultPreArr[i].setText(obj.get("correct_rate").getAsString() + "%");

                        if (obj.get("solve").getAsString().equalsIgnoreCase("Y")) {
                            if (obj.get("cs_is_result").getAsString().equalsIgnoreCase("f")) {
                                correctPreArr[i].setTextColor(getResources().getColor(R.color.col_9A081D));
                                correctPreArr[i].setText("X");
                            } else {
                                correctPreArr[i].setTextColor(getResources().getColor(R.color.col_085395));
                                correctPreArr[i].setText("O");
                            }
                        } else {
                            correctPreArr[i].setText("?");
                            correctPreArr[i].setTextColor(Color.BLACK);
                        }
                    }

                    //노트
                    Api110GetDownloadTodayTen.SmartNote[] array = new Gson().fromJson(smartnote.toString(), Api110GetDownloadTodayTen.SmartNote[].class);
                    List<Api110GetDownloadTodayTen.SmartNote> smartNotes = Arrays.asList(array);


                    if (noteNewbieAdapter == null) {
                        noteNewbieAdapter = new DayNoteAdapter(smartNotes);
                        dayNewbieNoteList.setAdapter(noteNewbieAdapter);

                    } else {
                        noteNewbieAdapter.setData(smartNotes);
                        noteNewbieAdapter.notifyDataSetChanged();
                    }

                    //키워드
                    Api110GetDownloadTodayTen.Keyword[] keywordArr = new Gson().fromJson(keyword.toString(), Api110GetDownloadTodayTen.Keyword[].class);
                    List<Api110GetDownloadTodayTen.Keyword> keywords = Arrays.asList(keywordArr);

                    dayNewbieKeywordList.setAdapter(null);
                    dayNewbieKeywordList.setAdapter(new DayKeywordAdapter(keywords));

                } else if (clazz.get("statusCode").getAsString().equalsIgnoreCase("200") && resultData.get("result").getAsString().equalsIgnoreCase("ERR")) {
                    Toast toast = Toast.makeText(MainActivity.this, getString(R.string.msg_last_num), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();

                }
            }

            @Override
            public void onFail(Call call, String msg) {
            }
        });
    }


    //강화시험 대시보드
    private void httpTest() {
        if (isShowProgress) {
            DisplayUtils.showProgressDialog(MainActivity.this, getString(R.string.loading_dialog_check_msg));
        }

        JsonObject jo = new JsonObject();
        jo.addProperty("user_code", userCode);

        RequestData.getInstance().getReinforceDashBoard(jo, new NetworkResponse<JsonObject>() {
            @Override
            public void onSuccess(Call call, JsonObject clazz) {
                if (isShowProgress) {
                    isShowProgress = false;
                    DisplayUtils.hideProgressDialog();
                }
                DisplayUtils.hideProgressDialog();

                if (clazz.get("statusCode").getAsString().equals("200")) {
                    JsonObject obj = clazz.get("resultData").getAsJsonObject();

                    reinforceYN = obj.get("reinforce_yn").getAsString();

                    if (reinforceYN.equalsIgnoreCase("N")) {
                        btnTest.setVisibility(View.GONE);
                        statusTestLay.setVisibility(View.GONE);

                        int boardSelect = PrefHelper.getInt(MainActivity.this, PrefConsts.LAST_MAIN_BOARD_SELECT_OPEN, PrefConsts.MAIN_BOARD_DAY_QUIZ);
                        if (boardSelect == PrefConsts.MAIN_BOARD_TEST) {
                            btnDay.setChecked(true);
                        }

                    } else {
                        btnTest.setVisibility(View.VISIBLE);
                        tvTestTitle.setText(obj.get("exam_name").getAsString());
                        if (obj.get("grade").getAsString().equals("0")) {
                            tvTakeState.setText("미응시");
                            tvTakeState.setTextColor(getResources().getColor(R.color.col_DE673C));

                            btnTakeState.setBackgroundResource(R.drawable.circle_bg_00b050);
                            btnTakeState.setText("시작");
                        } else {
                            tvTakeState.setText("응시완료");
                            tvTakeState.setTextColor(getResources().getColor(R.color.col_00b050));

                            btnTakeState.setBackgroundResource(R.drawable.circle_bg_black);
                            btnTakeState.setText("확인");
                        }

                        String grade = obj.get("score").getAsString();

                        tvGrade.setText(grade + "점");
                        tvRanking.setText(obj.get("percent").getAsString() + "%");//석차

                        int useTime = Integer.parseInt(obj.get("use_time").getAsString());
                        tvTime.setText(obj.get("use_time").getAsString().equals("0") ? "00:00" : useTime / 60 + ":" + useTime % 60);//풀이시간

                        tvTotalGrade.setText(obj.get("total_average").getAsString().equals("0") ? "?" : "평균 " + obj.get("total_average").getAsString() + "점");
                        tvTotalRanking.setText(obj.get("percent").getAsString().equals("0") ? "?" : obj.get("grade").getAsString() + "/" + obj.get("total_take").getAsString() + "명");
                        int useTotalTime = Integer.parseInt(obj.get("total_use_time").getAsString());
                        tvAverageTime.setText(obj.get("total_use_time").getAsString().equals("0") ? "00:00" : "평균" + useTotalTime / 60 + "분" + useTotalTime % 60 + "초");
                    }
                }
            }

            @Override
            public void onFail(Call call, String msg) {
                if (isShowProgress) {
                    DisplayUtils.hideProgressDialog();
                }
            }
        });
    }

    //문제정답률
    private void httpTodayTenRate() {

        JsonObject jo = new JsonObject();
        jo.addProperty("user_code", userCode);

        RequestData.getInstance().getDownloadTodayTenRate(jo, new NetworkResponse<JsonObject>() {

            @Override
            public void onSuccess(Call call, JsonObject clazz) {
                if (clazz.get("statusCode").getAsString().equalsIgnoreCase("200")) {
                    JsonObject resultData = clazz.get("resultData").getAsJsonObject();
                    JsonArray question = resultData.get("question").getAsJsonArray();
                    JsonArray ox = resultData.get("ox").getAsJsonArray();
                    JsonArray past = resultData.get("past").getAsJsonArray();
                    JsonArray smartnote = resultData.get("smartnote").getAsJsonArray();
                    JsonArray keyword = resultData.get("keyword").getAsJsonArray();
                    JsonArray voca = null;
                    JsonArray grammar = null;
                    if (BuildConfig.APP_NAME.equals(ENGLISH)) {
                        voca = resultData.get("voca").getAsJsonArray();
                        grammar = resultData.get("grammar").getAsJsonArray();
                    }


                    TextView correctArr[] = {tv_q1_correct, tv_q2_correct, tv_q3_correct, tv_q4_correct, tv_q5_correct, tv_q6_correct, tv_q7_correct, tv_q8_correct, tv_q9_correct, tv_q10_correct};
                    TextView resultArr[] = {tv_q1_result, tv_q2_result, tv_q3_result, tv_q4_result, tv_q5_result, tv_q6_result, tv_q7_result, tv_q8_result, tv_q9_result, tv_q10_result};

                    TextView correctOXArr[] = {tv_ox_q1_correct, tv_ox_q2_correct, tv_ox_q3_correct, tv_ox_q4_correct, tv_ox_q5_correct, tv_ox_q6_correct, tv_ox_q7_correct, tv_ox_q8_correct, tv_ox_q9_correct, tv_ox_q10_correct};
                    TextView resultOXArr[] = {tv_ox_q1_result, tv_ox_q2_result, tv_ox_q3_result, tv_ox_q4_result, tv_ox_q5_result, tv_ox_q6_result, tv_ox_q7_result, tv_ox_q8_result, tv_ox_q9_result, tv_ox_q10_result};

                    TextView correctPreArr[] = {tv_pre_q1_correct, tv_pre_q2_correct, tv_pre_q3_correct, tv_pre_q4_correct, tv_pre_q5_correct, tv_pre_q6_correct, tv_pre_q7_correct, tv_pre_q8_correct, tv_pre_q9_correct, tv_pre_q10_correct};
                    TextView resultPreArr[] = {tv_pre_q1_result, tv_pre_q2_result, tv_pre_q3_result, tv_pre_q4_result, tv_pre_q5_result, tv_pre_q6_result, tv_pre_q7_result, tv_pre_q8_result, tv_pre_q9_result, tv_pre_q10_result};

                    TextView correctVocaArr[] = {tv_voca_q1_correct, tv_voca_q2_correct, tv_voca_q3_correct, tv_voca_q4_correct, tv_voca_q5_correct, tv_voca_q6_correct, tv_voca_q7_correct, tv_voca_q8_correct, tv_voca_q9_correct, tv_voca_q10_correct};
                    TextView resultVocaArr[] = {tv_voca_q1_result, tv_voca_q2_result, tv_voca_q3_result, tv_voca_q4_result, tv_voca_q5_result, tv_voca_q6_result, tv_voca_q7_result, tv_voca_q8_result, tv_voca_q9_result, tv_voca_q10_result};

                    TextView correctGrammarArr[] = {tv_grammar_q1_correct, tv_grammar_q2_correct, tv_grammar_q3_correct, tv_grammar_q4_correct, tv_grammar_q5_correct, tv_grammar_q6_correct, tv_grammar_q7_correct, tv_grammar_q8_correct, tv_grammar_q9_correct, tv_grammar_q10_correct};
                    TextView resultGrammarArr[] = {tv_grammar_q1_result, tv_grammar_q2_result, tv_grammar_q3_result, tv_grammar_q4_result, tv_grammar_q5_result, tv_grammar_q6_result, tv_grammar_q7_result, tv_grammar_q8_result, tv_grammar_q9_result, tv_grammar_q10_result};


                    //강화
                    for (int i = 0; i < question.size(); i++) {
                        JsonObject obj = question.get(i).getAsJsonObject();
                        resultArr[i].setText(obj.get("correct_rate").getAsString() + "%");

                        if (obj.get("solve").getAsString().equalsIgnoreCase("Y")) {
                            if (obj.get("cs_is_result").getAsString().equalsIgnoreCase("f")) {
                                correctArr[i].setTextColor(getResources().getColor(R.color.col_9A081D));
                                correctArr[i].setText("X");
                            } else {
                                correctArr[i].setTextColor(getResources().getColor(R.color.col_085395));
                                correctArr[i].setText("O");
                            }
                        } else {
                            correctArr[i].setText("?");
                            correctArr[i].setTextColor(Color.BLACK);
                        }
                    }

                    //ox
                    for (int i = 0; i < ox.size(); i++) {
                        JsonObject obj = ox.get(i).getAsJsonObject();
                        resultOXArr[i].setText(obj.get("correct_rate").getAsString() + "%");

                        if (obj.get("solve").getAsString().equalsIgnoreCase("Y")) {
                            if (obj.get("cs_is_result").getAsString().equalsIgnoreCase("f")) {
                                correctOXArr[i].setTextColor(getResources().getColor(R.color.col_9A081D));
                                correctOXArr[i].setText("X");
                            } else {
                                correctOXArr[i].setTextColor(getResources().getColor(R.color.col_085395));
                                correctOXArr[i].setText("O");
                            }
                        } else {
                            correctOXArr[i].setText("?");
                            correctOXArr[i].setTextColor(Color.BLACK);
                        }
                    }

                    //기출
                    for (int i = 0; i < past.size(); i++) {
                        JsonObject obj = past.get(i).getAsJsonObject();
                        resultPreArr[i].setText(obj.get("correct_rate").getAsString() + "%");

                        if (obj.get("solve").getAsString().equalsIgnoreCase("Y")) {
                            if (obj.get("cs_is_result").getAsString().equalsIgnoreCase("f")) {
                                correctPreArr[i].setTextColor(getResources().getColor(R.color.col_9A081D));
                                correctPreArr[i].setText("X");
                            } else {
                                correctPreArr[i].setTextColor(getResources().getColor(R.color.col_085395));
                                correctPreArr[i].setText("O");
                            }
                        } else {
                            correctPreArr[i].setText("?");
                            correctPreArr[i].setTextColor(Color.BLACK);
                        }
                    }

                    if (BuildConfig.APP_NAME.equals(ENGLISH)) {
                        //어휘
                        for (int i = 0; i < voca.size(); i++) {
                            JsonObject obj = voca.get(i).getAsJsonObject();
                            resultVocaArr[i].setText(obj.get("correct_rate").getAsString() + "%");

                            if (obj.get("solve").getAsString().equalsIgnoreCase("Y")) {
                                if (obj.get("cs_is_result").getAsString().equalsIgnoreCase("f")) {
                                    correctVocaArr[i].setTextColor(getResources().getColor(R.color.col_9A081D));
                                    correctVocaArr[i].setText("X");
                                } else {
                                    correctVocaArr[i].setTextColor(getResources().getColor(R.color.col_085395));
                                    correctVocaArr[i].setText("O");
                                }
                            } else {
                                correctVocaArr[i].setText("?");
                                correctVocaArr[i].setTextColor(Color.BLACK);
                            }
                        }

                        //문법
                        for (int i = 0; i < grammar.size(); i++) {
                            JsonObject obj = grammar.get(i).getAsJsonObject();
                            resultGrammarArr[i].setText(obj.get("correct_rate").getAsString() + "%");

                            if (obj.get("solve").getAsString().equalsIgnoreCase("Y")) {
                                if (obj.get("cs_is_result").getAsString().equalsIgnoreCase("f")) {
                                    correctGrammarArr[i].setTextColor(getResources().getColor(R.color.col_9A081D));
                                    correctGrammarArr[i].setText("X");
                                } else {
                                    correctGrammarArr[i].setTextColor(getResources().getColor(R.color.col_085395));
                                    correctGrammarArr[i].setText("O");
                                }
                            } else {
                                correctGrammarArr[i].setText("?");
                                correctGrammarArr[i].setTextColor(Color.BLACK);
                            }
                        }
                    }


                    //노트
                    Api110GetDownloadTodayTen.SmartNote[] array = new Gson().fromJson(smartnote.toString(), Api110GetDownloadTodayTen.SmartNote[].class);
                    List<Api110GetDownloadTodayTen.SmartNote> smartNotes = Arrays.asList(array);

                    if (noteAdapter == null) {
                        noteAdapter = new DayNoteAdapter(smartNotes);
                        dayNoteList.setAdapter(noteAdapter);
                    } else {
                        noteAdapter.setData(smartNotes);
                        noteAdapter.notifyDataSetChanged();
                    }

                    //키워드
                    Api110GetDownloadTodayTen.Keyword[] keywordArr = new Gson().fromJson(keyword.toString(), Api110GetDownloadTodayTen.Keyword[].class);
                    List<Api110GetDownloadTodayTen.Keyword> keywords = Arrays.asList(keywordArr);

                    if (keywordAdapter == null) {
                        keywordAdapter = new DayKeywordAdapter(keywords);
                        dayKeywordList.setAdapter(keywordAdapter);

                    } else {
                        keywordAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFail(Call call, String msg) {
                LogUtil.d(msg);
                return;
            }
        });
    }

    private void httpEverydayTenRate() {
        JsonObject jo = new JsonObject();
        jo.addProperty("user_code", userCode);

        RequestData.getInstance().getEverydayDailyStudy(jo, new NetworkResponse<EverydayDailyStudyDao>() {

            @Override
            public void onSuccess(Call call, EverydayDailyStudyDao clazz) {
                try{
                    if (clazz.statusCode.equals("200")) {
                        ArrayList<EverydayDailyStudyDao.Idiom> idiomArr = clazz.resultData.idiom;
                        ArrayList<EverydayDailyStudyDao.Proverb> probArr = clazz.resultData.proverb;
                        ArrayList<EverydayDailyStudyDao.Express> ExpArr = clazz.resultData.express;

                        if(idiomArr != null && idiomArr.size() > 0){
                            final EverydayDailyStudyDao.Idiom idiom = idiomArr.get(0);

                            tv_idiom_r_contents.setText(idiom.sqi_r_contents);
                            tv_idiom_commentary.setText(idiom.sqi_commentary);
                            tv_idiom_w_contents.setText(StringUtil.replaceSpecialCharacterColor(MainActivity.this, idiom.sqi_w_contents));
                            tv_idiom_f_keyword.setText(idiom.f_keyword);

                            if (idiom.bookmark.equals("Y")) {
                                iv_idiom_bookmark.setBackgroundResource(R.drawable.ic_eng_idiom_list_bm_on);
                            } else {
                                iv_idiom_bookmark.setBackgroundResource(R.drawable.ic_eng_idiom_list_bm_off);
                            }

                            iv_idiom_bookmark.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (idiom.bookmark.equals("Y")) {
                                        idiom.bookmark = "N";
                                        sendHttpBookmark(idiom.sq_kind, idiom.sq_class, idiom.sqIdx, false);
                                        iv_idiom_bookmark.setBackgroundResource(R.drawable.ic_eng_idiom_list_bm_off);
                                    } else {
                                        idiom.bookmark = "Y";
                                        sendHttpBookmark(idiom.sq_kind, idiom.sq_class, idiom.sqIdx, true);
                                        iv_idiom_bookmark.setBackgroundResource(R.drawable.ic_eng_idiom_list_bm_on);
                                    }
                                }
                            });
                        }

                        if(probArr != null && probArr.size() > 0){
                            final EverydayDailyStudyDao.Proverb prov = probArr.get(0);
                            if (!TextUtils.isEmpty(prov.getSqi_r_contents())) {
                                tv_prob_r_conents.setText(prov.getSqi_r_contents());
                            } else {
                                tv_prob_r_conents.setVisibility(View.GONE);
                            }

                            if (!TextUtils.isEmpty(prov.getSqi_w_contents())) {
                                tv_prob_w_contents.setText(prov.getSqi_w_contents());
                            } else {
                                tv_prob_w_contents.setVisibility(View.GONE);
                            }

                            if (!TextUtils.isEmpty(prov.getSqi_commentary())) {
                                tv_prob_commentary.setText(prov.getSqi_commentary());
                            } else {
                                tv_prob_commentary.setVisibility(View.GONE);
                            }

                            if (prov.bookmark.equals("Y")) {
                                iv_prob_bookmark.setBackgroundResource(R.drawable.ic_eng_idiom_list_bm_on);
                            } else {
                                iv_prob_bookmark.setBackgroundResource(R.drawable.ic_eng_idiom_list_bm_off);
                            }

                            iv_prob_bookmark.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (prov.bookmark.equals("Y")) {
                                        prov.bookmark = "N";
                                        iv_prob_bookmark.setBackgroundResource(R.drawable.ic_eng_idiom_list_bm_off);
                                        sendHttpBookmark(prov.sq_kind, prov.sq_class, prov.sqIdx, false);
                                    } else {
                                        prov.bookmark = "Y";
                                        iv_prob_bookmark.setBackgroundResource(R.drawable.ic_eng_idiom_list_bm_on);
                                        sendHttpBookmark(prov.sq_kind, prov.sq_class, prov.sqIdx, true);
                                    }
                                }
                            });
                        }

                        if(ExpArr != null && ExpArr.size() > 0){
                            final EverydayDailyStudyDao.Express express = ExpArr.get(0);
                            tv_exp_conents.setText(express.getSqi_r_contents());
                            tv_exp_commentary.setText(express.getSqi_commentary());

                            if (express.bookmark.equals("Y")) {
                                iv_exp_bookmark.setBackgroundResource(R.drawable.ic_eng_idiom_list_bm_on);
                            } else {
                                iv_exp_bookmark.setBackgroundResource(R.drawable.ic_eng_idiom_list_bm_off);
                            }

                            iv_exp_bookmark.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (express.bookmark.equals("Y")) {
                                        express.bookmark = "N";
                                        sendHttpBookmark(express.sq_kind, express.sq_class, express.sqIdx, false);
                                        iv_exp_bookmark.setBackgroundResource(R.drawable.ic_eng_idiom_list_bm_off);
                                    } else {
                                        express.bookmark = "Y";
                                        sendHttpBookmark(express.sq_kind, express.sq_class, express.sqIdx, true);
                                        iv_exp_bookmark.setBackgroundResource(R.drawable.ic_eng_idiom_list_bm_on);
                                    }
                                }
                            });
                        }
                    }
                }catch (Exception e){
                }
            }

            @Override
            public void onFail(Call call, String msg) {
                Toast.makeText(MainActivity.this, getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendHttpBookmark(String sqKind, String sqClass, String sqIdx, final boolean isChecked) {

        if (CommonUtils.getConnectNetwork(this)) {

            JsonObject jo = new JsonObject();
            jo.addProperty("user_code", userCode);
//            jo.addProperty("app_day", appDay);
            jo.addProperty("sq_kind", sqKind);
            jo.addProperty("sq_class", sqClass);
            jo.addProperty("ip_idx", sqIdx);
            if (isChecked) {
                jo.addProperty("ptype", "add");
            } else {
                jo.addProperty("ptype", "del");
            }


            RequestData.getInstance().getEverydayDayBookmark(jo, new NetworkResponse<JsonObject>() {
                @Override
                public void onSuccess(Call call, JsonObject clazz) {
                    if (clazz.get("statusCode").getAsString().equals("200")) {

                        if (isChecked) {
                            Toast.makeText(MainActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFail(Call call, String msg) {
                    LogUtil.d("onFail : " + msg);
                    Toast.makeText(MainActivity.this, getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(this, getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
        }
    }

    private class DayKeywordAdapter extends BaseAdapter {
        List<Api110GetDownloadTodayTen.Keyword> keywords = new ArrayList<>();

        public DayKeywordAdapter(List<Api110GetDownloadTodayTen.Keyword> keywords) {
            this.keywords = keywords;
        }

        @Override
        public int getCount() {
            return keywords.size();
        }

        @Override
        public Api110GetDownloadTodayTen.Keyword getItem(int position) {
            return keywords.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Api110GetDownloadTodayTen.Keyword keyword = keywords.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_status_day_keyword_item, null);
            }

            ((TextView) convertView.findViewById(R.id.tv_category)).setText(keyword.gubun);

            if (keyword.color.equalsIgnoreCase("blue")) {
                ((TextView) convertView.findViewById(R.id.tv_title)).setTextColor(getResources().getColor(R.color.col_085395));
            } else if (keyword.color.equalsIgnoreCase("green")) {
                ((TextView) convertView.findViewById(R.id.tv_title)).setTextColor(getResources().getColor(R.color.col_088E0E));
            } else {
                ((TextView) convertView.findViewById(R.id.tv_title)).setTextColor(getResources().getColor(R.color.col_C708));
            }
            ((TextView) convertView.findViewById(R.id.tv_title)).setText(keyword.title);
            ((TextView) convertView.findViewById(R.id.tv_txt)).setText("기출빈도:" + keyword.frequency + "       정답률:" + keyword.per_of_correct + "%");

            Button btnQuestion = convertView.findViewById(R.id.btn_question);

            btnQuestion.setTag(position);
            btnQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (((boardStateGruop.getCheckedRadioButtonId() == R.id.btn_day) && permit.contains("daily")) || ((boardStateGruop.getCheckedRadioButtonId() == R.id.btn_newbie) && permit.contains("newbie"))) {
                        if (CommonUtils.getConnectNetwork(MainActivity.this)) {

                            int position = (int) v.getTag();

                            final Api110GetDownloadTodayTen.Keyword data = keywords.get(position);

                            DisplayUtils.showProgressDialog(MainActivity.this, getString(R.string.loading_dialog_check_msg));
                            JsonObject jo = new JsonObject();
                            jo.addProperty("user_code", userCode);
                            jo.addProperty("title", data.title);
                            jo.addProperty("ip_category3", data.ip_category3);
                            jo.addProperty("where_is", "E");

                            RequestData.getInstance().getKeywordPasttestQuestion(jo, new NetworkResponse<DefaultQuestionListDao>() {
                                @Override
                                public void onSuccess(Call call, DefaultQuestionListDao clazz) {
                                    DisplayUtils.hideProgressDialog();

                                    if (clazz.getStatusCode().equals("200") && null != clazz && clazz.getResultData().getQuestionList().size() != 0) {
                                        ArrayList<DefaultQuestionListDao.Question> result = clazz.getResultData().questionList;

                                        for (int i = 0; i < result.size(); i++) {

                                            if (BuildConfig.APP_NAME.equals(KOREAN))
                                                result.get(i).setSqClass("Z");

                                            result.get(i).setIpTitle(StringEscapeUtils.unescapeHtml4(result.get(i).getIpTitle()));
                                            result.get(i).setExplain(StringEscapeUtils.unescapeHtml4(result.get(i).getExplain()));

                                            if (!TextUtils.isEmpty(result.get(i).getIpContent()) && result.get(i).getIpContent().contains("png")) {
                                                result.get(i).setIpContent(result.get(i).getIpContent());
                                                result.get(i).setIpContentSource(result.get(i).getIpContentSource());
                                            }

                                            ArrayList<DefaultQuestionListDao.Sunji> sunjiList = result.get(i).getSunjiList();

                                            if (null != sunjiList && sunjiList.size() > 0) {
                                                for (int j = 0; j < sunjiList.size(); j++) {
                                                    sunjiList.get(j).setSelect("X");
                                                }
                                                result.get(i).setSunjiList(sunjiList);
                                                result.get(i).setSolvedYN("N");
                                            }
                                        }

                                        if (clazz.getStatusCode().equals("200") && clazz.getResultData().questionList.size() != 0) {
                                            if (BuildConfig.APP_NAME.equals(KOREAN)) {
                                                Intent intent = new Intent(MainActivity.this, PreTestKoreanSeriesQuestionActivity.class);
                                                intent.putExtra("data", clazz.getResultData().questionList);
                                                intent.putExtra("where_is", "Z");
                                                intent.putExtra("title", data.title);
                                                intent.putExtra("ipCagetory3", data.ip_category3);
                                                startActivity(intent);

                                            } else {
                                                Intent intent = new Intent(MainActivity.this, KeywordStudyActivity.class);
                                                intent.putExtra("data", clazz.getResultData().questionList);
                                                intent.putExtra("where_is", "K");
                                                intent.putExtra("title", data.title);
                                                intent.putExtra("ipCagetory3", data.ip_category3);
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onFail(Call call, String msg) {
                                    DisplayUtils.hideProgressDialog();
                                    Toast.makeText(MainActivity.this, getResources().getString(R.string.server_error_default_msg) + "\n" + msg, Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(MainActivity.this, getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (permit.contains("day") || permit.contains("newbie")) {

                    } else {
                        DialogUtil.showChargeDialog(MainActivity.this);
                    }
                }
            });

            return convertView;
        }
    }

    private class DayNoteAdapter extends BaseAdapter {
        List<Api110GetDownloadTodayTen.SmartNote> smartNotes;

        public DayNoteAdapter(List<Api110GetDownloadTodayTen.SmartNote> smartNotes) {
            this.smartNotes = smartNotes;
        }

        public void setData(List<Api110GetDownloadTodayTen.SmartNote> smartNotes) {
            this.smartNotes = smartNotes;
        }

        @Override
        public int getCount() {
            return smartNotes.size();
        }

        @Override
        public Api110GetDownloadTodayTen.SmartNote getItem(int position) {
            return smartNotes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_status_day_note_item, null);
            }

            if (smartNotes.size() <= 0) {
                return convertView;
            }

            TextView tvNum = convertView.findViewById(R.id.tv_num);
            TextView tvCategory = convertView.findViewById(R.id.tv_category);
            TextView tvTitle = convertView.findViewById(R.id.tv_title);
            ImageView bookMark = convertView.findViewById(R.id.iv_bookmark);

            tvNum.setText(smartNotes.get(position).num + "");

            String category = smartNotes.get(position).category;
            if (category.contains("(")) {
                category = category.substring(0, category.indexOf("(")).trim();
            } else if (category.contains("현대사")) {
                category = category.substring(0, 4);
            }

            tvCategory.setText(category);
            tvTitle.setText(smartNotes.get(position).sTitle);

            if (TextUtils.isEmpty(smartNotes.get(position).bookmark) || smartNotes.get(position).bookmark.equalsIgnoreCase("N")) {
                bookMark.setBackgroundResource(R.drawable.sn_bm_off);
            } else {
                bookMark.setBackgroundResource(R.drawable.sn_bm_on);
            }

            convertView.setTag(position);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((boardStateGruop.getCheckedRadioButtonId() == R.id.btn_day) && permit.contains("daily")) || ((boardStateGruop.getCheckedRadioButtonId() == R.id.btn_newbie) && permit.contains("newbie"))) {
                        if (CommonUtils.getConnectNetwork(MainActivity.this)) {
                            final int position = (int) v.getTag();

                            JsonObject jo = new JsonObject();
                            jo.addProperty("user_code", userCode);

                            if (boardStateGruop.getCheckedRadioButtonId() == R.id.btn_day) {
                                RequestData.getInstance().getSmartNoteDaily(jo, new NetworkResponse<Api75GetSmartNoteQuestion>() {
                                    @Override
                                    public void onSuccess(Call call, Api75GetSmartNoteQuestion clazz) {

                                        Intent intent = new Intent(MainActivity.this, SmartNoteViewActivity.class);
                                        intent.putExtra("data", clazz);
                                        intent.putExtra("sIdx", smartNotes.get(position).s_idx + "");
                                        intent.putExtra("page", position + 1);
                                        intent.putExtra("mode", "D");
                                        SmartNoteMainActivity.withNote = false;
                                        startActivity(intent);

                                    }

                                    @Override
                                    public void onFail(Call call, String msg) {
                                        Toast.makeText(MainActivity.this, getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                jo.addProperty("order_code", tvNewbieBoardCount.getText().toString());

                                RequestData.getInstance().getSmartNoteDailyNewbie(jo, new NetworkResponse<Api75GetSmartNoteQuestion>() {
                                    @Override
                                    public void onSuccess(Call call, Api75GetSmartNoteQuestion clazz) {

                                        Intent intent = new Intent(MainActivity.this, SmartNoteViewActivity.class);
                                        intent.putExtra("data", clazz);
                                        intent.putExtra("sIdx", smartNotes.get(position).s_idx + "");
                                        intent.putExtra("page", position + 1);
                                        intent.putExtra("mode", "D");
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onFail(Call call, String msg) {
                                        Toast.makeText(MainActivity.this, getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(MainActivity.this, getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        DialogUtil.showChargeDialog(MainActivity.this);
                    }
                }
            });
            return convertView;
        }
    }

    private void httpProgress(final boolean retry) {
        JsonObject jo = new JsonObject();
        jo.addProperty("user_code", userCode);

        RequestData.getInstance().getConfigDashBoard(jo, new NetworkResponse<Api91GetConfigDashBoard>() {
            @Override
            public void onSuccess(Call call, Api91GetConfigDashBoard clazz) {
                data = clazz;
                if (retry) moveToChartPopup();
                else initStateBoard();
            }

            @Override
            public void onFail(Call call, String msg) {
                Log.d(TAG, msg);
                return;
            }
        });

    }

    private void initStateBoard() {
        int progress = data.resultData.question.totalAcc.progress;
        int correct = data.resultData.question.totalAcc.correct_rate;

        int pProgress = Integer.parseInt(data.resultData.pasttest.solve.progress);
        int pCorrect = data.resultData.pasttest.totalAcc.correct_rate;

        chart1.setData(progress, CompletionChart.QUESTION);
        chart2.setData(correct, CompletionChart.QUESTION);
        chart3.setData(pProgress, CompletionChart.PASTEST);
        chart4.setData(pCorrect, CompletionChart.PASTEST);

        tvQuestionProgress.setText(progress + "");
        tvQuestionScore.setText(correct + "");
        tvPastProgress.setText(pProgress + "");
        tvPastScore.setText(pCorrect + "");

    }

    @OnClick({R.id.status_test_lay})
    void btnMainTest() {
        if (permit.contains("exam")) {
            if (CommonUtils.getConnectNetwork(this)) {
                startActivityForResult(new Intent(MainActivity.this, MainTestListActivity.class), PrefConsts.INTENT_REQUEST_MAINTEST_LIST_ACTIVITY);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
            }
        } else {
            DialogUtil.showChargeDialog(this);
        }
    }

    //일일랜덤
    public void dayStatusClick(final int clickNum) {
        if (permit.contains("daily") || BuildConfig.DEBUG == true) {

            if (CommonUtils.getConnectNetwork(this)) {
                DisplayUtils.showProgressDialog(this, "문제 체크 중");
                //국어
                if (BuildConfig.APP_NAME.equals(BaseActivity.KOREAN)) {
                    if (dayMenuGroup.getCheckedRadioButtonId() == R.id.btn_day_study) {//강화학습

                        JsonObject jo = new JsonObject();
                        jo.addProperty("user_code", userCode);

                        RequestData.getInstance().getDailyTodayTenKorReinforce(jo, new NetworkResponse<Api206GetDailyTodayTenKor>() {
                            @Override
                            public void onSuccess(Call call, Api206GetDailyTodayTenKor clazz) {
                                DisplayUtils.hideProgressDialog();
                                if (null != clazz && clazz.statusCode.equalsIgnoreCase("200")) {
                                    moveDayKoreanQuestion(clazz, clickNum);
                                }
                            }

                            @Override
                            public void onFail(Call call, String msg) {
                                DisplayUtils.hideProgressDialog();
                                Log.d(TAG, "getDownloadTodayFive error : " + msg);
                                Toast.makeText(MainActivity.this, getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });

                    } else if (dayMenuGroup.getCheckedRadioButtonId() == R.id.btn_day_ox) {//ox
                        JsonObject jo = new JsonObject();
                        jo.addProperty("user_code", userCode);

                        RequestData.getInstance().getDailyTodayTenKorOX(jo, new NetworkResponse<Api206GetDailyTodayTenKor>() {
                            @Override
                            public void onSuccess(Call call, Api206GetDailyTodayTenKor clazz) {
                                DisplayUtils.hideProgressDialog();
                                if (null != clazz && clazz.statusCode.equalsIgnoreCase("200")) {
                                    moveDayKoreanOX(clazz, clickNum);
                                }
                            }

                            @Override
                            public void onFail(Call call, String msg) {
                                DisplayUtils.hideProgressDialog();
                                LogUtil.d("getDownloadTodayFive error : " + msg);
                                Toast.makeText(MainActivity.this, getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });

                    } else if (dayMenuGroup.getCheckedRadioButtonId() == R.id.btn_day_previous) {//국어>일일기출
                        JsonObject jo = new JsonObject();
                        jo.addProperty("user_code", userCode);

                        RequestData.getInstance().getDailyTodayTenKorPast(jo, new NetworkResponse<Api206GetDailyTodayTenKor>() {
                            @Override
                            public void onSuccess(Call call, Api206GetDailyTodayTenKor clazz) {
                                DisplayUtils.hideProgressDialog();
                                if (null != clazz && clazz.statusCode.equalsIgnoreCase("200")) {
                                    moveDayKoreanPast(clazz, clickNum);
                                }
                            }

                            @Override
                            public void onFail(Call call, String msg) {
                                DisplayUtils.hideProgressDialog();
                                LogUtil.d( "getDownloadTodayFive error : " + msg);
                                Toast.makeText(MainActivity.this, getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                    }
                } else if (BuildConfig.APP_NAME.equals(ENGLISH)) {

                    if (boardStateGruop.getCheckedRadioButtonId() == R.id.btn_day) {

                        if (dayMenuGroup.getCheckedRadioButtonId() == R.id.btn_day_previous) {
                            DisplayUtils.showProgressDialog(this, "문제 체크 중");

                            JsonObject jo = new JsonObject();
                            jo.addProperty("user_code", userCode);

                            RequestData.getInstance().getDownloadTodayTen(jo, new NetworkResponse<Api110GetDownloadTodayTen>() {
                                @Override
                                public void onSuccess(Call call, Api110GetDownloadTodayTen clazz) {
                                    DisplayUtils.hideProgressDialog();
                                    if (null != clazz && clazz.statusCode.equalsIgnoreCase("200")) {
                                        moveDayQuestion(clazz, clickNum);
                                    }
                                }

                                @Override
                                public void onFail(Call call, String msg) {
                                    DisplayUtils.hideProgressDialog();
                                    LogUtil.d("getDownloadTodayFive error : " + msg);
                                    Toast.makeText(MainActivity.this, getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            });


                        } else if (dayMenuGroup.getCheckedRadioButtonId() == R.id.btn_day_voca) {//영어 일일어휘
                            DisplayUtils.showProgressDialog(this, "문제 체크 중");

                            JsonObject jo = new JsonObject();
                            jo.addProperty("user_code", BaseActivity.userCode);

                            RequestData.getInstance().getVocabularyVocaDailyQuiz(jo, new NetworkResponse<EnglishWordDayQuizDao>() {

                                @Override
                                public void onSuccess(Call call, EnglishWordDayQuizDao clazz) {
                                    DisplayUtils.hideProgressDialog();

                                    ArrayList<EnglishWordDayQuizDao.WordDayQuizItem> questionList = clazz.resultData.list;

                                    Collections.sort(questionList, new Comparator<EnglishWordDayQuizDao.WordDayQuizItem>() {
                                        @Override
                                        public int compare(EnglishWordDayQuizDao.WordDayQuizItem o1, EnglishWordDayQuizDao.WordDayQuizItem o2) {
                                            if (o1.getAppOrder() > o2.getAppOrder()) return 1;
                                            if (o1.getAppOrder() < o2.getAppOrder()) return -1;
                                            return 0;
                                        }
                                    });

                                    Intent intent = new Intent(MainActivity.this, EnglishWordDayQuizActivity.class);
                                    intent.putExtra("data", questionList);
                                    intent.putExtra("where_is", "D");
                                    intent.putExtra("position", clickNum);
                                    intent.putExtra("title", "어휘학습");
                                    EnglishWordMainActivity.withVoca = false;
                                    startActivity(intent);
                                }

                                @Override
                                public void onFail(Call call, String msg) {
                                    DisplayUtils.hideProgressDialog();
                                    Toast.makeText(MainActivity.this, getResources().getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "getPrevioustestGetQuestion \n" + msg);
                                }
                            });

                        } else if (dayMenuGroup.getCheckedRadioButtonId() == R.id.btn_day_grammar) {
                            if (CommonUtils.getConnectNetwork(this)) {
                                DisplayUtils.showProgressDialog(this, "문제 체크 중");

                                JsonObject jo = new JsonObject();
                                jo.addProperty("user_code", BaseActivity.userCode);

                                RequestData.getInstance().getGrammarDailyQuiz(jo, new NetworkResponse<GrammarOXDao>() {

                                    @Override
                                    public void onSuccess(Call call, GrammarOXDao clazz) {
                                        DisplayUtils.hideProgressDialog();
                                        ArrayList<GrammarOXDao.Quiz> questionList = clazz.resultData.list;

                                        Collections.sort(questionList, new Comparator<GrammarOXDao.Quiz>() {
                                            @Override
                                            public int compare(GrammarOXDao.Quiz o1, GrammarOXDao.Quiz o2) {
                                                if (o1.getAppOrder() > o2.getAppOrder()) return 1;
                                                if (o1.getAppOrder() < o2.getAppOrder()) return -1;
                                                return 0;
                                            }
                                        });

                                        Intent intent = new Intent(MainActivity.this, GrammarOXQuizActivity.class);
                                        intent.putExtra("data", questionList);
                                        intent.putExtra("startNum", clickNum);
                                        intent.putExtra("where_is", "D");
                                        intent.putExtra("title", "문법 OX");
                                        GrammarMainActivity.withGramma = false;
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onFail(Call call, String msg) {
                                        DisplayUtils.hideProgressDialog();
                                        Toast.makeText(MainActivity.this, getResources().getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "getVocabularyStandardVocaList \n" + msg);
                                    }
                                });
                            } else {
                                Toast.makeText(MainActivity.this, getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if (boardStateGruop.getCheckedRadioButtonId() == R.id.btn_everyday) {
                        if (dayMenuGroup.getCheckedRadioButtonId() == R.id.btn_day_phrase) {//영어>일일 생활영어>숙어
                            status_day_every.setVisibility(View.VISIBLE);
                            status_day_every_proverb.setVisibility(View.GONE);
                            status_day_every_expression.setVisibility(View.GONE);
                        } else if (dayMenuGroup.getCheckedRadioButtonId() == R.id.btn_day_proverb) {//영어>일일 생활영어>속담
                            status_day_every.setVisibility(View.GONE);
                            status_day_every_proverb.setVisibility(View.VISIBLE);
                            status_day_every_expression.setVisibility(View.GONE);
                        } else if (dayMenuGroup.getCheckedRadioButtonId() == R.id.btn_day_expression) {//영어>일일 생활영어>표현
                            status_day_every.setVisibility(View.GONE);
                            status_day_every_proverb.setVisibility(View.GONE);
                            status_day_every_expression.setVisibility(View.VISIBLE);
                        }
                    }

                } else {
                    JsonObject jo = new JsonObject();
                    jo.addProperty("user_code", userCode);

                    RequestData.getInstance().getDownloadTodayTen(jo, new NetworkResponse<Api110GetDownloadTodayTen>() {
                        @Override
                        public void onSuccess(Call call, Api110GetDownloadTodayTen clazz) {
                            DisplayUtils.hideProgressDialog();
                            if (null != clazz && clazz.statusCode.equalsIgnoreCase("200")) {
                                moveDayQuestion(clazz, clickNum);
                            }
                        }

                        @Override
                        public void onFail(Call call, String msg) {
                            DisplayUtils.hideProgressDialog();
                            LogUtil.d("getDownloadTodayFive error : " + msg);
                            Toast.makeText(MainActivity.this, getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });
                }

            } else {
                Toast.makeText(MainActivity.this, getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
            }
        } else {
            DialogUtil.showChargeDialog(this);
        }
    }

    //입문 메뉴분기 처리
    /*private void moveNewbieQuestion(Api110GetDownloadTodayTen clazz, int clickNum) {

        if (dayNewbieMenuGroup.getCheckedRadioButtonId() == R.id.btn_newbie_day_study) { // 일일입문>강화

            ArrayList<Api110GetDownloadTodayTen.Question> questionList = clazz.resultData.questionList;

            Collections.sort(questionList, new Comparator<Api110GetDownloadTodayTen.Question>() {
                @Override
                public int compare(Api110GetDownloadTodayTen.Question o1, Api110GetDownloadTodayTen.Question o2) {
                    if (o1.getNum() > o2.getNum()) return 1;
                    if (o1.getNum() < o2.getNum()) return -1;
                    return 0;
                }
            });

            //강화
            for (int i = 0; i < questionList.size(); i++) {
                questionList.get(i).setIpTitle(StringEscapeUtils.unescapeHtml4(questionList.get(i).getIpTitle()));

                if (!TextUtils.isEmpty(questionList.get(i).getIpContent()) && questionList.get(i).getIpContent().contains("png")) {
                    questionList.get(i).setIpContent(questionList.get(i).getIpContent());
                }
            }

            for (int i = 0; i < questionList.size(); i++) {
                if (questionList.get(i).getSolved().equalsIgnoreCase("Y")) {
                    int selectSunji = Integer.parseInt(questionList.get(i).getSelectSunji());
                    for (int j = 0; j < questionList.get(i).getSunjiList().size(); j++) {
                        if (selectSunji == questionList.get(i).getSunjiList().get(j).getSqiIdx()) {
                            Log.d(TAG, "selectSunji : " + selectSunji + " == sqiIdx : " + questionList.get(i).getSunjiList().get(j).getSqiIdx());
                            questionList.get(i).getSunjiList().get(j).setSelect("O");
                        }
                    }
                }
            }

            Intent intent = new Intent(MainActivity.this, DayQuizActivity.class);
            intent.putExtra("data", clazz);
            intent.putExtra("mode", DayQuizActivity.MODE_NEWBIE_STUDY);
            intent.putExtra("startNum", clickNum);
            intent.putExtra("where_is", "W");
            intent.putExtra("orderCode", tvNewbieBoardCount.getText().toString());
            startActivity(intent);

        } else if (dayNewbieMenuGroup.getCheckedRadioButtonId() == R.id.btn_newbie_day_ox) { // 일일랜덤 > ox
            ArrayList<DefaultOXQuestion.OxQuiz> oxList = clazz.resultData.oxList;

            Collections.sort(oxList, new Comparator<DefaultOXQuestion.OxQuiz>() {
                @Override
                public int compare(DefaultOXQuestion.OxQuiz o1, DefaultOXQuestion.OxQuiz o2) {
                    if (o1.getNum() > o2.getNum()) return 1;
                    if (o1.getNum() < o2.getNum()) return -1;
                    return 0;
                }
            });

            //OX
            for (int i = 0; i < oxList.size(); i++) {
                oxList.get(i).setIpaTitle(StringEscapeUtils.unescapeHtml4(oxList.get(i).getIpaTitle()));
                oxList.get(i).setIpaMirrorText(StringEscapeUtils.unescapeHtml4(oxList.get(i).getIpaMirrorText()));
            }

            for (int i = 0; i < oxList.size(); i++) {
                if (oxList.get(i).getSolved().equalsIgnoreCase("Y")) {
                    if (oxList.get(i).getCs_is_result().equalsIgnoreCase("t")) {
                        oxList.get(i).setSelect("O");
                    } else {
                        oxList.get(i).setSelect("X");
                    }
                }
            }

            Intent intent = new Intent(MainActivity.this, DayOXQuizActivity.class);
            intent.putExtra("data", oxList);
            intent.putExtra("mode", DayOXQuizActivity.MODE_NEWBIE_OX);
            intent.putExtra("startNum", clickNum);
            intent.putExtra("where_is", "W");
            intent.putExtra("orderCode", tvNewbieBoardCount.getText().toString());
            startActivity(intent);

        } else if (dayNewbieMenuGroup.getCheckedRadioButtonId() == R.id.btn_newbie_day_previous) { //일일입문 > 기출
            ArrayList<DefaultQuestionListDao.Question> pastList = clazz.resultData.pastQuestionList;

            Collections.sort(pastList, new Comparator<DefaultQuestionListDao.Question>() {
                @Override
                public int compare(DefaultQuestionListDao.Question o1, DefaultQuestionListDao.Question o2) {
                    if (o1.getNum() > o2.getNum()) return 1;
                    if (o1.getNum() < o2.getNum()) return -1;
                    return 0;
                }
            });

            //기출
            for (int i = 0; i < pastList.size(); i++) {
                pastList.get(i).setIpTitle(StringEscapeUtils.unescapeHtml4(pastList.get(i).getIpTitle()));

                if (!TextUtils.isEmpty(pastList.get(i).getIpContent()) && pastList.get(i).getIpContent().contains("png")) {
                    pastList.get(i).setIpContent(pastList.get(i).getIpContent());
                }
            }

            for (int i = 0; i < pastList.size(); i++) {
                pastList.get(i).setExplain(StringEscapeUtils.unescapeHtml4(pastList.get(i).getExplain()));
                if (pastList.get(i).getSolvedYN().equalsIgnoreCase("Y")) {
                    int selectSunji = pastList.get(i).getSelectSunji();
                    for (int j = 0; j < pastList.get(i).getSunjiList().size(); j++) {
                        if (selectSunji == pastList.get(i).getSunjiList().get(j).getIpaIdx()) {
                            pastList.get(i).getSunjiList().get(j).setSelect("O");
                        }
                    }
                }
            }

            Intent intent = new Intent(MainActivity.this, DayQuizActivity.class);
            intent.putExtra("data", clazz);
            intent.putExtra("mode", DayQuizActivity.MODE_NEWBIE_PRE);
            intent.putExtra("startNum", clickNum);
            intent.putExtra("where_is", "W");
            intent.putExtra("orderCode", tvNewbieBoardCount.getText().toString());
            startActivity(intent);
        }
    }*/

    //일일학습 메뉴 분기
    private void moveDayQuestion(Api110GetDownloadTodayTen clazz, int clickNum) {

        if (dayMenuGroup.getCheckedRadioButtonId() == R.id.btn_day_study) { // 일일랜덤>강화
            ArrayList<DefaultQuestionListDao.Question> questionList = clazz.resultData.questionList;
            if (questionList == null || questionList.size() <= 0) {
                return;
            }

            Collections.sort(questionList, new Comparator<DefaultQuestionListDao.Question>() {
                @Override
                public int compare(DefaultQuestionListDao.Question o1, DefaultQuestionListDao.Question o2) {
                    if (o1.getNum() > o2.getNum()) return 1;
                    if (o1.getNum() < o2.getNum()) return -1;
                    return 0;
                }
            });

            //강화
            for (int i = 0; i < questionList.size(); i++) {
                questionList.get(i).setIpTitle(StringEscapeUtils.unescapeHtml4(questionList.get(i).getIpTitle()));

                int selectSunji = questionList.get(i).getSelectSunji();

                if (questionList.get(i).getSolvedYN().equalsIgnoreCase("Y")) {

                    for (int j = 0; j < questionList.get(i).getSunjiList().size(); j++) {
                        if (selectSunji == questionList.get(i).getSunjiList().get(j).getSqiIdx()) {
                            questionList.get(i).getSunjiList().get(j).setSelect("O");
                        } else {
                            questionList.get(i).getSunjiList().get(j).setSelect("X");
                        }
                    }
                } else {
                    for (int j = 0; j < questionList.get(i).getSunjiList().size(); j++) {
                        questionList.get(i).getSunjiList().get(j).setSelect("X");
                    }
                }
            }

            Intent intent = new Intent(MainActivity.this, DayQuizActivity.class);
            intent.putExtra("data", questionList);
            intent.putExtra("mode", DayQuizActivity.MODE_STUDY);
            intent.putExtra("where_is", "D");
            intent.putExtra("startNum", clickNum);
            startActivity(intent);

        } else if (dayMenuGroup.getCheckedRadioButtonId() == R.id.btn_day_ox) { // 일일랜덤 > ox
            ArrayList<DefaultOXQuestion.OxQuiz> oxList = clazz.resultData.oxList;

            if (oxList == null || oxList.size() <= 0) {
                return;
            }

            Collections.sort(oxList, new Comparator<DefaultOXQuestion.OxQuiz>() {
                @Override
                public int compare(DefaultOXQuestion.OxQuiz o1, DefaultOXQuestion.OxQuiz o2) {
                    if (o1.getNum() > o2.getNum()) return 1;
                    if (o1.getNum() < o2.getNum()) return -1;
                    return 0;
                }
            });

            //OX

            for (int i = 0; i < oxList.size(); i++) {
                oxList.get(i).setIpaTitle(StringEscapeUtils.unescapeHtml4(oxList.get(i).getIpaTitle()));
                oxList.get(i).setIpaMirrorText(StringEscapeUtils.unescapeHtml4(oxList.get(i).getIpaMirrorText()));

                if (oxList.get(i).getSolved().equalsIgnoreCase("Y")) {
                    if (oxList.get(i).getCs_is_result().equalsIgnoreCase("t")) {
                        oxList.get(i).setSelect("O");
                    } else {
                        oxList.get(i).setSelect("X");
                    }
                }
            }

            Intent intent = new Intent(MainActivity.this, DayOXQuizActivity.class);
            intent.putExtra("data", oxList);
            intent.putExtra("mode", DayOXQuizActivity.MODE_OX);
            intent.putExtra("startNum", clickNum);
            intent.putExtra("where_is", "D");
            startActivity(intent);

        } else if (dayMenuGroup.getCheckedRadioButtonId() == R.id.btn_day_previous) { //일일랜덤 > 기출

            ArrayList<DefaultQuestionListDao.Question> pastList = clazz.resultData.pastQuestionList;

            if (pastList == null || pastList.size() <= 0) {
                return;
            }

            Collections.sort(pastList, new Comparator<DefaultQuestionListDao.Question>() {
                @Override
                public int compare(DefaultQuestionListDao.Question o1, DefaultQuestionListDao.Question o2) {
                    if (o1.getNum() > o2.getNum()) return 1;
                    if (o1.getNum() < o2.getNum()) return -1;
                    return 0;
                }
            });

            //기출
            for (int i = 0; i < pastList.size(); i++) {
                pastList.get(i).setIpTitle(StringEscapeUtils.unescapeHtml4(pastList.get(i).getIpTitle()));
                pastList.get(i).setExplain(StringEscapeUtils.unescapeHtml4(pastList.get(i).getExplain()));

                if (!TextUtils.isEmpty(pastList.get(i).getIpContent()) && pastList.get(i).getIpContent().contains("png"))
                    pastList.get(i).setIpContent(pastList.get(i).getIpContent());

                int selectSunji = pastList.get(i).getSelectSunji();

                for (int j = 0; j < pastList.get(i).getSunjiList().size(); j++) {
                    if (pastList.get(i).getSolvedYN().equalsIgnoreCase("Y")) {
                        if (selectSunji == pastList.get(i).getSunjiList().get(j).getSqiIdx()) {
                            pastList.get(i).getSunjiList().get(j).setSelect("O");
                        } else {
                            pastList.get(i).getSunjiList().get(j).setSelect("X");
                        }
                    } else {
                        pastList.get(i).getSunjiList().get(j).setSelect("X");
                    }
                }
            }

            Intent intent = null;
            if (BuildConfig.APP_NAME.equals(ENGLISH)) {
                intent = new Intent(MainActivity.this, PreTestEnglishSeriesQuestionActivity.class);
            } else {
                intent = new Intent(MainActivity.this, DayQuizActivity.class);
            }

            intent.putExtra("data", pastList);
            intent.putExtra("mode", DayQuizActivity.MODE_PRE);
            intent.putExtra("where_is", "D");
            intent.putExtra("title", "일일 기출문제");
            intent.putExtra("startNum", clickNum);

            startActivity(intent);

        } else if (dayMenuGroup.getCheckedRadioButtonId() == R.id.btn_day_voca) {//어휘
            ArrayList<DefaultQuestionListDao.Question> pastList = clazz.resultData.pastQuestionList;
            if (pastList == null || pastList.size() <= 0) {
                Toast.makeText(this, "문제가 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            Collections.sort(pastList, new Comparator<DefaultQuestionListDao.Question>() {
                @Override
                public int compare(DefaultQuestionListDao.Question o1, DefaultQuestionListDao.Question o2) {
                    if (o1.getNum() > o2.getNum()) return 1;
                    if (o1.getNum() < o2.getNum()) return -1;
                    return 0;
                }
            });

            for (int i = 0; i < pastList.size(); i++) {
                pastList.get(i).setIpTitle(StringEscapeUtils.unescapeHtml4(pastList.get(i).getIpTitle()));
                pastList.get(i).setExplain(StringEscapeUtils.unescapeHtml4(pastList.get(i).getExplain()));

                if (!TextUtils.isEmpty(pastList.get(i).getIpContent()) && pastList.get(i).getIpContent().contains("png"))
                    pastList.get(i).setIpContent(pastList.get(i).getIpContent());

                int selectSunji = pastList.get(i).getSelectSunji();
                for (int j = 0; j < pastList.get(i).getSunjiList().size(); j++) {
                    if (pastList.get(i).getSolvedYN().equalsIgnoreCase("Y")) {
                        if (selectSunji == pastList.get(i).getSunjiList().get(j).getIpaIdx()) {
                            pastList.get(i).getSunjiList().get(j).setSelect("O");
                        } else {
                            pastList.get(i).getSunjiList().get(j).setSelect("X");
                        }
                    } else {
                        pastList.get(i).getSunjiList().get(j).setSelect("X");
                    }
                }
            }

            Intent intent = new Intent(MainActivity.this, DayQuizActivity.class);
            intent.putExtra("data", pastList);
            intent.putExtra("mode", DayQuizActivity.MODE_PRE);
            intent.putExtra("where_is", "D");
            intent.putExtra("startNum", clickNum);

            startActivity(intent);

        } else if (dayMenuGroup.getCheckedRadioButtonId() == R.id.btn_day_grammar) {//문법
            ArrayList<DefaultQuestionListDao.Question> pastList = clazz.resultData.pastQuestionList;

            if (pastList == null || pastList.size() <= 0) {
                Toast.makeText(this, "문제가 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            Collections.sort(pastList, new Comparator<DefaultQuestionListDao.Question>() {
                @Override
                public int compare(DefaultQuestionListDao.Question o1, DefaultQuestionListDao.Question o2) {
                    if (o1.getNum() > o2.getNum()) return 1;
                    if (o1.getNum() < o2.getNum()) return -1;
                    return 0;
                }
            });

            //기출
            for (int i = 0; i < pastList.size(); i++) {
                pastList.get(i).setIpTitle(StringEscapeUtils.unescapeHtml4(pastList.get(i).getIpTitle()));
                pastList.get(i).setExplain(StringEscapeUtils.unescapeHtml4(pastList.get(i).getExplain()));
                if (!TextUtils.isEmpty(pastList.get(i).getIpContent()) && pastList.get(i).getIpContent().contains("png"))
                    pastList.get(i).setIpContent(pastList.get(i).getIpContent());

                int selectSunji = pastList.get(i).getSelectSunji();
                for (int j = 0; j < pastList.get(i).getSunjiList().size(); j++) {
                    if (pastList.get(i).getSolvedYN().equalsIgnoreCase("Y")) {
                        if (selectSunji == pastList.get(i).getSunjiList().get(j).getIpaIdx()) {
                            pastList.get(i).getSunjiList().get(j).setSelect("O");
                        } else {
                            pastList.get(i).getSunjiList().get(j).setSelect("X");
                        }
                    } else {
                        pastList.get(i).getSunjiList().get(j).setSelect("X");
                    }
                }
            }

            Intent intent = new Intent(MainActivity.this, DayQuizActivity.class);
            intent.putExtra("data", pastList);
            intent.putExtra("mode", DayQuizActivity.MODE_PRE);
            intent.putExtra("where_is", "D");
            intent.putExtra("startNum", clickNum);

            startActivity(intent);
        }
    }


    //한국어 > 일일학습 강화
    private void moveDayKoreanQuestion(Api206GetDailyTodayTenKor clazz, int clickNum) {

        if (dayMenuGroup.getCheckedRadioButtonId() == R.id.btn_day_study) { // 일일랜덤>강화

            try {
                ArrayList<DefaultQuestionListDao.Question> questionList = clazz.resultData.questionList;
                if (questionList == null || questionList.size() <= 0) return;

                Collections.sort(questionList, new Comparator<DefaultQuestionListDao.Question>() {
                    @Override
                    public int compare(DefaultQuestionListDao.Question o1, DefaultQuestionListDao.Question o2) {
                        if (o1.getNum() > o2.getNum()) return 1;
                        if (o1.getNum() < o2.getNum()) return -1;
                        return 0;
                    }
                });

                //강화
                int selectSunji = 0;
                for (int i = 0; i < questionList.size(); i++) {
                    questionList.get(i).setIpTitle(StringEscapeUtils.unescapeHtml4(questionList.get(i).getIpTitle()));

                    if (questionList.get(i).getSolvedYN().equalsIgnoreCase("Y")) {
                        selectSunji = questionList.get(i).getSelectSunji();
                    }
                    for (int j = 0; j < questionList.get(i).getSunjiList().size(); j++) {
                        if (questionList.get(i).getSolvedYN().equalsIgnoreCase("Y")) {
                            if (selectSunji == questionList.get(i).getSunjiList().get(j).getIpaIdx()) {
                                questionList.get(i).getSunjiList().get(j).setSelect("O");
                            } else {
                                questionList.get(i).getSunjiList().get(j).setSelect("X");
                            }
                        } else {
                            questionList.get(i).getSunjiList().get(j).setSelect("X");
                        }
                    }
                }

                Intent intent = new Intent(MainActivity.this, DayKoreanQuizActivity.class);
                intent.putExtra("data", questionList);
                intent.putExtra("mode", DayKoreanQuizActivity.MODE_DAY_STUDY);
                intent.putExtra("where_is", "D");//일일문제
                intent.putExtra("startNum", clickNum);
                startActivity(intent);

            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
    }

    //국어 > 일일학습 > ox
    private void moveDayKoreanOX(Api206GetDailyTodayTenKor clazz, int clickNum) {

        try {
            ArrayList<WordQuestionDao.WordQuestionItem> oxList = clazz.resultData.oxList;
            if (oxList == null || oxList.size() <= 0) return;

            Collections.sort(oxList, new Comparator<WordQuestionDao.WordQuestionItem>() {
                @Override
                public int compare(WordQuestionDao.WordQuestionItem o1, WordQuestionDao.WordQuestionItem o2) {
                    if (o1.getNum() > o2.getNum()) return 1;
                    if (o1.getNum() < o2.getNum()) return -1;
                    return 0;
                }
            });

            Intent intent = new Intent(MainActivity.this, WordOXQuizActivity.class);
            intent.putExtra("data", clazz.resultData.oxList);
            intent.putExtra("startNum", clickNum);
            intent.putExtra("title", "일일 어휘학습");
            intent.putExtra("where_is", "D");//일일문제
            WordMainActivity.withVoca = false;
            startActivity(intent);

        } catch (Exception e) {
            e.toString();
        }
    }

    //국어 > 일일기출
    private void moveDayKoreanPast(Api206GetDailyTodayTenKor clazz, int clickNum) {
        ArrayList<DefaultQuestionListDao.Question> pastList = clazz.resultData.pastQuestion;
        if (pastList == null || pastList.size() <= 0) return;

        Collections.sort(pastList, new Comparator<DefaultQuestionListDao.Question>() {
            @Override
            public int compare(DefaultQuestionListDao.Question o1, DefaultQuestionListDao.Question o2) {
                if (o1.getNum() > o2.getNum()) return 1;
                if (o1.getNum() < o2.getNum()) return -1;
                return 0;
            }
        });

        //기출
        for (int i = 0; i < pastList.size(); i++) {
            pastList.get(i).setIpTitle(StringEscapeUtils.unescapeHtml4(pastList.get(i).getIpTitle()));
            pastList.get(i).setExplain(StringEscapeUtils.unescapeHtml4(pastList.get(i).getExplain()));
            pastList.get(i).setSqClass("Z");

            if (!TextUtils.isEmpty(pastList.get(i).getIpContent()) && pastList.get(i).getIpContent().contains("png")) {
                pastList.get(i).setIpContent(pastList.get(i).getIpContent());
            }

            int selectSunji = pastList.get(i).getSelectSunji();
            for (int j = 0; j < pastList.get(i).getSunjiList().size(); j++) {
                if (pastList.get(i).getSolvedYN().equalsIgnoreCase("Y")) {
                    if (selectSunji == pastList.get(i).getSunjiList().get(j).getIpaIdx()) {
                        pastList.get(i).getSunjiList().get(j).setSelect("O");
                    } else {
                        pastList.get(i).getSunjiList().get(j).setSelect("X");
                    }
                } else {
                    pastList.get(i).getSunjiList().get(j).setSelect("X");
                }
            }
        }

        Intent intent = new Intent(MainActivity.this, DayKoreanQuizActivity.class);
        intent.putExtra("data", pastList);
        intent.putExtra("mode", DayKoreanQuizActivity.MODE_DAY_PRE);
        intent.putExtra("where_is", "D");
        intent.putExtra("startNum", clickNum);

        startActivity(intent);
    }

    void clickKoreanBtnWord() {
        if (permit.contains("voca") || BuildConfig.IS_DEBUG) {
            if (CommonUtils.getConnectNetwork(this)) {
                i = new Intent(MainActivity.this, WordMainActivity.class);
                i.putExtra("position", 0);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
            }
        } else {
            DialogUtil.showChargeDialog(this);
        }
    }

    void clickEnglishBtnWord() {

        if (permit.contains("voca") || BuildConfig.IS_DEBUG) {
            if (CommonUtils.getConnectNetwork(this)) {
                i = new Intent(MainActivity.this, EnglishWordMainActivity.class);
                i.putExtra("position", 0);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
            }
        } else {
            DialogUtil.showChargeDialog(this);
        }
    }

    @OnClick(R.id.ll_more)
    void moveToChartPopup() {
        if (data == null) {
            httpProgress(true);

        } else {
            Intent intent = new Intent(MainActivity.this, PopupChartActivity.class);
            intent.putExtra("data", data);
            intent.putExtra("mode", CompletionChart.QUESTION);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    @OnClick(R.id.ll_past_more)
    void moveToChartPastPopup() {
        if (data == null) {
            httpProgress(true);
        } else {
            Intent intent = new Intent(MainActivity.this, PopupChartActivity.class);
            intent.putExtra("data", data);
            intent.putExtra("mode", CompletionChart.PASTEST);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    void moveToDanwonStudy() {
        if (permit.contains("question")) {
            if (CommonUtils.getConnectNetwork(this)) {
                i = new Intent(MainActivity.this, DanwonMainActivity.class);
                i.putExtra("position", 0);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
            }
        } else {
            DialogUtil.showChargeDialog(this);
        }
    }

    //강화학습(주제별)
    void moveToSubject() {
        if (permit.contains("question")) {
            if (CommonUtils.getConnectNetwork(this)) {
                i = new Intent(MainActivity.this, DanwonMainActivity.class);
                i.putExtra("position", 1);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
            }
        } else {
            DialogUtil.showChargeDialog(this);
        }
    }

    //문법학습(영어)
    void moveToGrammar() {
        if (BuildConfig.DEBUG || permit.contains("grammar")) {
            if (CommonUtils.getConnectNetwork(this)) {
                i = new Intent(MainActivity.this, GrammarMainActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
            }
        } else {
            DialogUtil.showChargeDialog(this);
        }
    }

    //생활영어
    void moveToEveryDay() {
        if (BuildConfig.DEBUG || permit.contains("everyday")) {
            if (CommonUtils.getConnectNetwork(this)) {
                i = new Intent(MainActivity.this, EverydayEnglishMainActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
            }
        } else {
            DialogUtil.showChargeDialog(this);
        }
    }

    //독해학습
    void moveToReading() {
        if (BuildConfig.DEBUG || permit.contains("reading")) {
            if (CommonUtils.getConnectNetwork(this)) {
                i = new Intent(MainActivity.this, EnglishReadingMainActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
            }
        } else {
            DialogUtil.showChargeDialog(this);
        }
    }

    @OnClick({R.id.btnMyPage})
    void moveToMypage() {
        if (CommonUtils.getConnectNetwork(this)) {
            i = new Intent(MainActivity.this, MyPageActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
        }
    }

    void moveToSunjiSave() {
        if (permit.contains("past_explain") || BuildConfig.DEBUG) {
            if (CommonUtils.getConnectNetwork(this)) {
                i = new Intent(MainActivity.this, SunjiSaveListActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
            }
        } else {
            DialogUtil.showChargeDialog(this);
        }
    }

    void moveToFitStudy() {
        if (permit.contains("personal") || BuildConfig.DEBUG) {
            if (CommonUtils.getConnectNetwork(this)) {
                i = new Intent(MainActivity.this, FitMainActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
            }
        } else {
            DialogUtil.showChargeDialog(this);
        }
    }

    void moveToSearch() {
        if (permit.contains("search")) {
            if (CommonUtils.getConnectNetwork(this)) {
                i = new Intent(MainActivity.this, CombineSearchMainActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
            }
        } else {
            DialogUtil.showChargeDialog(this);
        }
    }

    void moveToStudy(){
        if (permit.contains("study")||BuildConfig.DEBUG == true) {
            if (CommonUtils.getConnectNetwork(this)) {
                i = new Intent(MainActivity.this, StudyRoomListActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
            }
        } else {
            DialogUtil.showChargeDialog(this);
        }
    }

    @OnClick(R.id.btnSetting)
    void moveToSetting() {
        i = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(i);
    }

    private void moveToAccount(String date) {
        Intent intent = new Intent(this, AccountActivity.class);
        intent.putExtra("date", date);
        startActivity(intent);
        finish();
    }


    private void moveToSmartNote(){
        if (CommonUtils.getConnectNetwork(this)) {
            i = new Intent(MainActivity.this, SmartNoteMainActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
        }
    }

        private void moveToAnswerNote(){
        if (permit.contains("ox")) {
            if (CommonUtils.getConnectNetwork(this)) {
                i = new Intent(MainActivity.this, WrongAnswerNoteBookMainActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
            }
        } else {
            DialogUtil.showChargeDialog(this);
        }
    }

        private void moveToRandomOX() {
        if (permit.contains("random")) {
            if (CommonUtils.getConnectNetwork(this)) {
                i = new Intent(MainActivity.this, RandomOXMainActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
            }
        } else {
            DialogUtil.showChargeDialog(this);
        }
    }

    //    @OnClick(R.id.btnPrevTests)
    void moveToPreviousTests1() {
        if (CommonUtils.getConnectNetwork(this)) {
            i = new Intent(this, PreTestMainActivity.class);
            i.putExtra("position", 0);
            startActivity(i);
        }
    }

    void moveToPreviousTests2() {
        if (CommonUtils.getConnectNetwork(this)) {
            i = new Intent(this, PreTestMainActivity.class);
            i.putExtra("position", 1);
            startActivity(i);
        }
    }

    //    @OnClick(R.id.btnCompare)
    void btnCompare() {
        if (permit.contains("compare")) {
            if (CommonUtils.getConnectNetwork(this)) {
                startActivity(new Intent(this, CompareMainActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
            }
        } else {
            DialogUtil.showChargeDialog(this);
        }
    }

    @OnClick(R.id.btnHelp)
    void btnHelpClick() {
//        Intent i = new Intent(MainActivity.this, TutorialActivity.class);
        startActivityForResult(new Intent(MainActivity.this, TutorialActivity.class), PrefConsts.INTENT_TUTORIAL_CLICK);
    }

    @OnClick(R.id.btnHistory)
    void btnHistoryClick() {
        if (CommonUtils.getConnectNetwork(this)) {
            startActivity(new Intent(MainActivity.this, SolvedHistoryMainActivity.class));
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
        }

    }

    //    @OnClick(R.id.btnKeyword)
    void btnKeyword() {
        if (permit.contains("keyword")) {
            if (CommonUtils.getConnectNetwork(this)) {
                startActivity(new Intent(MainActivity.this, KeywordMainActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
            }
        } else {
            DialogUtil.showChargeDialog(this);
        }
    }

    //    @OnClick(R.id.btn_source_book)
    void btnSourceBook() {
        if (permit.contains("history")) {
            if (CommonUtils.getConnectNetwork(this)) {
                startActivity(new Intent(MainActivity.this, SourceBookMainActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
            }
        } else {
            DialogUtil.showChargeDialog(this);
        }
    }


    @OnClick(R.id.btnNotice)
    void btnNotice() {
        if (CommonUtils.getConnectNetwork(this)) {
            checkPopupNotice(true, "Y");
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.btnCalendar)
    void btnCalendar() {
        if (CommonUtils.getConnectNetwork(this)) {
            eventCalendarPopup();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PrefConsts.INTENT_TUTORIAL_CLICK) {
            startActivity(new Intent(MainActivity.this, TutorialQuestionActivity.class));

        } else if (requestCode == PrefConsts.INTENT_REQUEST_PUSH_SETTING_ACTIVITY) {
            boolean isPushEnable = NotificationManager.isNotificationEnabled(this);
            boolean isSavePush = PrefHelper.getBoolean(this, PrefConsts.PUSH_SAVE_AGREE, true);
            if (isPushEnable != isSavePush) {
                httpPushEnable(isPushEnable);
            }

        } else if (requestCode == PrefConsts.INTENT_REQUEST_MAINTEST_LIST_ACTIVITY && resultCode == RESULT_OK) {
            isShowProgress = true;
            DisplayUtils.showProgressDialog(this, "데이타 가져오는 중..");
        }
    }

    private void showBottomNotice() {
        if (CommonUtils.getConnectNetwork(this)) {
            JsonObject jo = new JsonObject();
            jo.addProperty("user_code", userCode);

            RequestData.getInstance().getNotice(jo, new NetworkResponse<JsonObject>() {
                @Override
                public void onSuccess(Call call, JsonObject clazz) {
                    JsonObject jsonObject = clazz.getAsJsonObject("resultData");
                    String notice = jsonObject.get("notice").getAsString();
                    tvNotice.setText(notice);//설문조사참여하기
                    String past = jsonObject.get("past_button").getAsString();
                    mainService = jsonObject.get("main_service").getAsString();
                    mainSwitch = jsonObject.get("main_switch").getAsString();

                    if (mainService.split(",").length < 3) {
                        menuList.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                    } else if (mainService.split(",").length < 4) {
                        menuList.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));

                    } else if (mainService.split(",").length == 4) {
                        menuList.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

                    } else {
                        menuList.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
                    }

                    menuAdapter.setMenuArr();
                    menuAdapter.notifyDataSetChanged();

                    if (past.equalsIgnoreCase("Y")) {
                        btnHistory.setVisibility(View.VISIBLE);
                    } else {
                        btnHistory.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFail(Call call, String msg) {
                    Toast.makeText(MainActivity.this, getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                    return;
                }
            });
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkAppVersion() {

        if (!CommonUtils.getConnectNetwork(this)) {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
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
                    finish();
                }
            });
            builder.show();

        } else {

            JsonObject jo = new JsonObject();
            jo.addProperty("user_code", userCode);

            RequestData.getInstance().getAppVersion(jo, new NetworkResponse<JsonObject>() {
                @Override
                public void onSuccess(Call call, JsonObject object) {
//                DisplayUtils.hideProgressDialog();
                    if (object != null) {
                        JsonObject resultData = object.getAsJsonObject("resultData");

                        // 현재 설치된 앱의 버전을 체크해서 int값으로 가지고 온다.
                        int currentAppVersionCode = CommonUtils.getAppVersion(MainActivity.this);

                        // 호출한 api에서 보내주는 버전을 int값으로 가지고 온다.
                        int latestAppVersionCode = Integer.parseInt(resultData.get("app_version").getAsString().replace(".", ""));

                        int updateOption = resultData.get("update_type").getAsInt();
//                        reinforceYN = resultData.get("reinforce_yn").getAsString();
                        mainService = resultData.get("main_service").getAsString();

                        Log.e(TAG, "현재 버전 : " + currentAppVersionCode);
                        Log.e(TAG, "서버에서 받은 최신버전 정보 : " + latestAppVersionCode);
                        Log.e(TAG, "업데이트 옵션 : " + updateOption);
//                        Log.e(TAG, "강화시험 공개 : " + reinforceYN);
                        Log.e(TAG, "메뉴 : " + mainService);

                        if (latestAppVersionCode > currentAppVersionCode) {

                            switch (updateOption) {
                                case 1:
                                    showMustUpdateGuideDialog();
                                    break;

                                case 2:
                                    showOptionalUpdateGuideDialog();
                                    break;
                            }
                        }
                    }
                }

                @Override
                public void onFail(Call call, String msg) {
                    Toast.makeText(MainActivity.this, getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                    return;
                }
            });
        }
    }

    private void showMustUpdateGuideDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                .setMessage("업데이트가 필요합니다.\n업데이트를 해 주세요.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();

                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
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

    private void showOptionalUpdateGuideDialog() {
        if (!MainActivity.this.isFinishing()) {
            new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                    .setMessage("새로운 업데이트가 있습니다.\n업데이트 하시겠습니까?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();

                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                            }
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setCancelable(false)
                    .show();
        }
    }

    private void eventCalendarPopup() {
        Intent intent = new Intent(MainActivity.this, PopupCalendarActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void bannerPopupNotice() {
        if (CommonUtils.getConnectNetwork(this)) {
            JsonObject jo = new JsonObject();
            jo.addProperty("user_code", userCode);

            RequestData.getInstance().getBbsBannerList(jo, new NetworkResponse<Api146GetBbsBannerList>() {
                @Override
                public void onSuccess(Call call, Api146GetBbsBannerList clazz) {
                    if (clazz.statusCode.equals("200")) {

                        Intent intent = new Intent(MainActivity.this, PopupBannerActivity.class);
                        intent.putExtra("data", clazz.resultData);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    }
                }

                @Override
                public void onFail(Call call, String msg) {
                    Toast.makeText(MainActivity.this, getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkPopupNotice(final boolean isFlag, String notice) {
        if (CommonUtils.getConnectNetwork(this)) {
            JsonObject jo = new JsonObject();
            jo.addProperty("user_code", userCode);
            jo.addProperty("from_notice", notice);

            RequestData.getInstance().getPopupNotice(jo, new NetworkResponse<JsonObject>() {

                @Override
                public void onSuccess(Call call, JsonObject clazz) {
                    final JsonObject jsonObject = clazz.getAsJsonObject("resultData");

                    String noticeExist = jsonObject.get("popup_use_yn").getAsString();
                    String noticeContent = jsonObject.get("popup_contents").getAsString().replace("\\r", "");
                    int serverNoticeVersion = jsonObject.get("popup_version").getAsInt();


                    Log.e(TAG, "content " + noticeExist + " && " + noticeContent + " && " + serverNoticeVersion);

                    int clientNoticeVersion = PrefHelper.getInt(MainActivity.this, PrefConsts.POPUP_NOTICE_VERSION, -1);

                    LogUtil.e("text 받아온 버전 : " + serverNoticeVersion + " & 클라이언트에 저장된 버전 : " + clientNoticeVersion);

                    if (noticeExist.equalsIgnoreCase("Y")) {

                        if (serverNoticeVersion > clientNoticeVersion) {
                            PrefHelper.setInt(MainActivity.this, PrefConsts.POPUP_NOTICE_VERSION, serverNoticeVersion);
                            DialogUtil.showNoticePopupDialog(MainActivity.this, noticeContent);
                        } else if (isFlag) {
                            DialogUtil.showNoticePopupDialog(MainActivity.this, noticeContent);
                        }
                    }

                    //// 이미지 팝업 /////////////////

                    String popupHtmlUseYN = jsonObject.get("popup_html_use_yn").getAsString();
                    String popupHtmlUrl = jsonObject.get("popup_html_url").getAsString();
                    String popupHtmlLink = jsonObject.get("popup_html_link").getAsString();
                    String popupHtmlPeriod = jsonObject.get("popup_html_period").getAsString();
                    int popupHtmlVersion = jsonObject.get("popup_html_version").getAsInt();

                    Log.e(TAG, "img && " + popupHtmlUseYN + " && " + popupHtmlUrl + " && " + popupHtmlLink + " && " + popupHtmlPeriod + " && " + popupHtmlVersion);

                    int clientNoticeImgVersion = PrefHelper.getInt(MainActivity.this, PrefConsts.POPUP_IMG_NOTICE_VERSION, -1);

                    LogUtil.e("img 받아온 버전 : " + popupHtmlVersion + " & 클라이언트에 저장된 버전 : " + clientNoticeImgVersion);


                    if (popupHtmlUseYN.equalsIgnoreCase("Y")) {
                        if (popupHtmlVersion > clientNoticeImgVersion) {
                            PrefHelper.setInt(MainActivity.this, PrefConsts.POPUP_IMG_NOTICE_VERSION, popupHtmlVersion);
                            showImgPopupActivity(popupHtmlUrl, popupHtmlLink, popupHtmlPeriod);
                        } else if (isFlag) {
                            showImgPopupActivity(popupHtmlUrl, popupHtmlLink, popupHtmlPeriod);
                        }
                    }

                    ////이미지 팝업2///////////////////
                    String popupHtml2UseYN = jsonObject.get("popup_html2_use_yn").getAsString();
                    String popupHtml2Url = jsonObject.get("popup_html2_url").getAsString();
                    int popupHtml2Version = jsonObject.get("popup_html2_version").getAsInt();

                    Log.e(TAG, "img2 " + popupHtml2UseYN + " && " + popupHtml2Url + " && " + popupHtml2Version);

                    int clientNoticeImg2Version = PrefHelper.getInt(MainActivity.this, PrefConsts.POPUP_IMG2_NOTICE_VERSION, -1);

                    LogUtil.e("img2 받아온 버전 : " + popupHtml2Version + " & 클라이언트에 저장된 버전 : " + clientNoticeImg2Version);

                    if (popupHtml2UseYN.equalsIgnoreCase("Y")) {
                        if (popupHtml2Version > clientNoticeImg2Version) {
                            PrefHelper.setInt(MainActivity.this, PrefConsts.POPUP_IMG2_NOTICE_VERSION, popupHtml2Version);
                            showImg2PopupActivity(popupHtml2Url);
                        } else if (isFlag) {
                            showImg2PopupActivity(popupHtml2Url);
                        }
                    }

                    //배너공지
                    String bannerYN = jsonObject.get("banner_notice_yn").getAsString();
                    int bannerVer = Integer.parseInt(jsonObject.get("banner_notice_version").getAsString());

                    int bannerNoticeVersion = PrefHelper.getInt(MainActivity.this, PrefConsts.POPUP_BANNER_NOTICE_VERSION, -1);

                    if (bannerYN.equalsIgnoreCase("Y")) {
                        if (bannerVer > bannerNoticeVersion) {
                            PrefHelper.setInt(MainActivity.this, PrefConsts.POPUP_BANNER_NOTICE_VERSION, bannerVer);
                            bannerPopupNotice();
                        } else if (isFlag) {
                            bannerPopupNotice();
                        }
                    }
                }

                @Override
                public void onFail(Call call, String msg) {
                    Toast.makeText(MainActivity.this, getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                    return;
                }
            });
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
        }
    }

    private void showImg2PopupActivity(final String url) {
        Log.d(TAG, "showImg2PopupActivity");

        Intent intent = new Intent(MainActivity.this, PopupNoticeActivity.class);
        intent.putExtra("mode", PopupNoticeActivity.IMG2);
        intent.putExtra("url", url);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void showImgPopupActivity(String url, String link, String period) {
        LogUtil.d("showImgPopupActivity url :" + url + ", link :" + link + ", period :" + period);

        boolean isFlag = false;
        if (period.equalsIgnoreCase("none")) {
            isFlag = true;
        }

        if (isFlag) {
            Intent intent = new Intent(this, PopupNoticeActivity.class);
            intent.putExtra("mode", PopupNoticeActivity.IMG);
            intent.putExtra("url", url);
            intent.putExtra("link", link);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    @OnClick(R.id.btn_purcharse_other)
    void btn_purcharse_other() {
        if (!TextUtils.isEmpty(userID) && !TextUtils.isEmpty(userCode)) {//아이디 체크
            Intent intent = new Intent(this, PaymentListActivity.class);
            intent.putExtra("current", 1);
            startActivity(intent);
        } else {
            startActivityForResult(new Intent(this, LoginActivity.class), PrefConsts.INTENT_REQUEST_LOGIN_ACTIVITY);
        }
    }

    class ItemDecoration extends RecyclerView.ItemDecoration {
        public ItemDecoration() {
        }

        //마지막 아이템이 아닌 경우, 공백 추가
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.top = CommonUtils.dpToPx(MainActivity.this, 2);
            outRect.left = CommonUtils.dpToPx(MainActivity.this, 2);
            outRect.right = CommonUtils.dpToPx(MainActivity.this, 2);
            outRect.bottom = CommonUtils.dpToPx(MainActivity.this, 2);
        }
    }

    class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.ViewHolder> {
        private String[] menuItemArr;

        public MainMenuAdapter() {
            menuItemArr = mainService.split(",");
        }

        public void setMenuArr() {
            menuItemArr = mainService.split(",");
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.btn_main_menu)
            Button  btnMenu;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        @Override
        public MainMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.main_menu_item, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(MainMenuAdapter.ViewHolder holder, int position) {
            setMenu(holder, position);
        }

        private void setMenu(MainMenuAdapter.ViewHolder holder, int position) {
            switch (menuItemArr[position]) {
                case "voca":
                    if (BuildConfig.APP_NAME.equals(BaseActivity.KOREAN)) {
                        holder.btnMenu.setBackgroundColor(getResources().getColor(R.color.col_2196f3));
                    } else {
                        holder.btnMenu.setBackgroundColor(getResources().getColor(R.color.col_fbe300));
                        holder.btnMenu.setTextColor(Color.BLACK);
                    }
                    if (mainService.split(",").length > 4) {
                        holder.btnMenu.setText(getString(R.string.main_menu_word));
                    } else {
                        holder.btnMenu.setText(getString(R.string.word));
                    }
                    if (!BuildConfig.IS_DEBUG && !mainSwitch.contains("voca")) {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        holder.btnMenu.setBackgroundColor(Color.parseColor("#808080"));
                    } else {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (BuildConfig.APP_NAME.equals(BaseActivity.KOREAN)) {
                                    clickKoreanBtnWord();
                                } else {
                                    clickEnglishBtnWord();
                                }
                            }
                        });
                    }
                    break;

                case "smartnote":
                    holder.btnMenu.setBackgroundColor(getResources().getColor(R.color.col_085395));
                    holder.btnMenu.setTextColor(Color.WHITE);
                    if (mainService.split(",").length > 4) {
                        holder.btnMenu.setText(getString(R.string.main_menu_smartnote));
                    } else {
                        holder.btnMenu.setText(getString(R.string.smart_note));
                    }
                    if (!BuildConfig.IS_DEBUG && !mainSwitch.contains("smartnote")) {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        holder.btnMenu.setBackgroundColor(Color.parseColor("#808080"));
                    } else {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                moveToSmartNote();
                            }
                        });
                    }
                    break;

                case "keyword":
                    holder.btnMenu.setBackgroundColor(getResources().getColor(R.color.col_203864));
                    if (mainService.split(",").length > 4) {
                        holder.btnMenu.setText(getString(R.string.main_menu_keyword));
                    } else {
                        holder.btnMenu.setText(getString(R.string.keyword_study));
                    }
                    if (!BuildConfig.IS_DEBUG && !mainSwitch.contains("keyword")) {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        holder.btnMenu.setBackgroundColor(Color.parseColor("#808080"));
                    } else {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                btnKeyword();
                            }
                        });
                    }
                    break;

                case "exam":
                    holder.btnMenu.setBackgroundColor(getResources().getColor(R.color.col_00b050));
                    if (mainService.split(",").length > 4) {
                        holder.btnMenu.setText(getString(R.string.main_menu_test));
                    } else {
                        holder.btnMenu.setText(getString(R.string.main_test));
                    }

                    if (!BuildConfig.IS_DEBUG && !mainSwitch.contains("exam")) {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        holder.btnMenu.setBackgroundColor(Color.parseColor("#808080"));
                    } else {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                btnMainTest();
                            }
                        });
                    }
                    break;

                case "question":
                    holder.btnMenu.setBackgroundColor(getResources().getColor(R.color.col_9A081D));
                    if (mainService.split(",").length > 4) {
                        holder.btnMenu.setText(getString(R.string.main_menu_question));
                    } else {
                        holder.btnMenu.setText(getString(R.string.study_danwon));
                    }
                    if (!BuildConfig.IS_DEBUG && !mainSwitch.contains("question")) {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        holder.btnMenu.setBackgroundColor(Color.parseColor("#808080"));
                    } else {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                moveToDanwonStudy();
                            }
                        });
                    }
                    break;

                case "ox":
                    holder.btnMenu.setBackgroundColor(getResources().getColor(R.color.col_BF392E));
                    if (mainService.split(",").length > 4) {
                        holder.btnMenu.setText(getString(R.string.main_menu_ox));
                    } else {
                        holder.btnMenu.setText(getString(R.string.wrong_note));
                    }
                    if (!BuildConfig.IS_DEBUG && !mainSwitch.contains("ox")) {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        holder.btnMenu.setBackgroundColor(Color.parseColor("#808080"));
                    } else {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                moveToAnswerNote();
                            }
                        });
                    }
                    break;

                case "pasttest":
                    holder.btnMenu.setBackgroundColor(getResources().getColor(R.color.col_DE673C));
                    if (mainService.split(",").length > 4) {
                        holder.btnMenu.setText(getString(R.string.main_menu_pasttest));
                    } else {
                        holder.btnMenu.setText(getString(R.string.prev_tests));
                    }
                    if (!BuildConfig.IS_DEBUG && !mainSwitch.contains("pasttest")) {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        holder.btnMenu.setBackgroundColor(Color.parseColor("#808080"));
                    } else {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                moveToPreviousTests1();

                            }
                        });
                    }
                    break;

                case "compare":
                    holder.btnMenu.setBackgroundColor(getResources().getColor(R.color.col_3b3838));
                    if (mainService.split(",").length > 4) {
                        holder.btnMenu.setText(getString(R.string.main_menu_compare));
                    } else {
                        holder.btnMenu.setText(getString(R.string.compare_study));
                    }
                    if (!BuildConfig.IS_DEBUG && !mainSwitch.contains("compare")) {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        holder.btnMenu.setBackgroundColor(Color.parseColor("#808080"));
                    } else {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                btnCompare();
                            }
                        });
                    }
                    break;

                case "historical":
                    holder.btnMenu.setBackgroundColor(getResources().getColor(R.color.col_4B0082F));
                    holder.btnMenu.setBackgroundColor(getResources().getColor(R.color.col_3b3838));

                    if (mainService.split(",").length > 4) {
                        holder.btnMenu.setText(getString(R.string.main_menu_history));
                    } else {
                        holder.btnMenu.setText(getString(R.string.historical_study));
                    }
                    if (!BuildConfig.IS_DEBUG && !mainSwitch.contains("historical")) {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        holder.btnMenu.setBackgroundColor(Color.parseColor("#808080"));
                    } else {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    }
                    break;
                case "random":
                    holder.btnMenu.setBackgroundColor(getResources().getColor(R.color.col_880E4F));
                    if (mainService.split(",").length > 4) {
                        holder.btnMenu.setText(getString(R.string.main_menu_randon_ox));
                    } else {
                        holder.btnMenu.setText(getString(R.string.randon_ox));
                    }
                    if (!BuildConfig.IS_DEBUG && !mainSwitch.contains("random")) {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        holder.btnMenu.setBackgroundColor(Color.parseColor("#808080"));
                    } else {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                moveToRandomOX();
                            }
                        });
                    }
                    break;
                case "mypage":
                    holder.btnMenu.setBackgroundColor(getResources().getColor(R.color.col_5A));
                    if (mainService.split(",").length > 4) {
                        holder.btnMenu.setText(getString(R.string.main_menu_my_page));
                    } else {
                        holder.btnMenu.setText(getString(R.string.my_page));
                    }
                    if (!BuildConfig.IS_DEBUG && !mainSwitch.contains("mypage")) {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        holder.btnMenu.setBackgroundColor(Color.parseColor("#808080"));
                    } else {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                moveToMypage();
                            }
                        });
                    }
                    break;

                case "grammar":
                    holder.btnMenu.setBackgroundColor(getResources().getColor(R.color.col_03A596));
                    if (mainService.split(",").length > 4) {
                        holder.btnMenu.setText(getString(R.string.main_menu_grammar));
                    } else {
                        holder.btnMenu.setText(getString(R.string.grammar));
                    }
                    if (!BuildConfig.IS_DEBUG && !mainSwitch.contains("grammar")) {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        holder.btnMenu.setBackgroundColor(Color.parseColor("#808080"));
                    } else {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                moveToGrammar();
                            }
                        });
                    }
                    break;

                case "everyday":
                    holder.btnMenu.setBackgroundColor(getResources().getColor(R.color.col_50B9FF));
                    if (mainService.split(",").length > 4) {
                        holder.btnMenu.setText(getString(R.string.main_menu_everyday));
                    } else {
                        holder.btnMenu.setText(getString(R.string.everyday));
                    }
                    if (!BuildConfig.IS_DEBUG && !mainSwitch.contains("everyday")) {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        holder.btnMenu.setBackgroundColor(Color.parseColor("#808080"));
                    } else {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                moveToEveryDay();
                            }
                        });
                    }
                    break;
                case "reading":
                    holder.btnMenu.setBackgroundColor(getResources().getColor(R.color.col_002060));
                    if (mainService.split(",").length > 4) {
                        holder.btnMenu.setText(getString(R.string.main_menu_reading));
                    } else {
                        holder.btnMenu.setText(getString(R.string.reading));
                    }
                    if (!BuildConfig.IS_DEBUG && !mainSwitch.contains("reading")) {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        holder.btnMenu.setBackgroundColor(Color.parseColor("#808080"));
                    } else {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                moveToReading();
                            }
                        });
                    }
                    break;

                case "personal":
                    holder.btnMenu.setBackgroundColor(getResources().getColor(R.color.col_5A));
                    if (mainService.split(",").length > 4) {
                        holder.btnMenu.setText(getString(R.string.main_menu_fit));
                    } else {
                        holder.btnMenu.setText(getString(R.string.fit));
                    }
                    if (!BuildConfig.IS_DEBUG && !mainSwitch.contains("personal")) {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        holder.btnMenu.setBackgroundColor(Color.parseColor("#808080"));
                    } else {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                moveToFitStudy();
                            }
                        });
                    }
                    break;
                case "search":
                    holder.btnMenu.setBackgroundColor(getResources().getColor(R.color.col_112F41));
                    if (mainService.split(",").length > 4) {
                        holder.btnMenu.setText(getString(R.string.main_menu_search));
                    } else {
                        holder.btnMenu.setText(getString(R.string.search));
                    }
                    if (!BuildConfig.IS_DEBUG && !mainSwitch.contains("search")) {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        holder.btnMenu.setBackgroundColor(Color.parseColor("#808080"));
                    } else {
                        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                moveToSearch();
                            }
                        });
                    }
                    break;

                    case "study":
                        holder.btnMenu.setBackgroundColor(getResources().getColor(R.color.col_AD1457));
                        if (mainService.split(",").length > 4) {
                            holder.btnMenu.setText(getString(R.string.main_menu_study_room));
                        } else {
                            holder.btnMenu.setText(getString(R.string.study_room));
                        }
                        if (!BuildConfig.IS_DEBUG && !mainSwitch.contains("study")) {
                            holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(MainActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                            holder.btnMenu.setBackgroundColor(Color.parseColor("#808080"));
                        } else {
                            holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    moveToStudy();
                                }
                            });
                        }
                        break;

                default:
                    holder.btnMenu.setBackgroundColor(Color.parseColor("#808080"));
                    if (mainService.split(",").length > 4) {
                        holder.btnMenu.setText("준비\n중");
                    } else {
                        holder.btnMenu.setText("준비중");
                    }

                    holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    holder.btnMenu.setBackgroundColor(Color.parseColor("#808080"));


                    break;
            }
        }

        @Override
        public int getItemCount() {
            return menuItemArr.length;
        }
    }
}

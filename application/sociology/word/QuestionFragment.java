package com.jlesoft.civilservice.koreanhistoryexam9.word;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.gustavofao.jsonapi.Models.JSONApiObject;
import com.jlesoft.civilservice.koreanhistoryexam9.BaseActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.PrefConsts;
import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.day.mainDay.DayKoreanQuizActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.model.DefaultQuestionListDao;
import com.jlesoft.civilservice.koreanhistoryexam9.network.NetworkResponse;
import com.jlesoft.civilservice.koreanhistoryexam9.network.RequestData;
import com.jlesoft.civilservice.koreanhistoryexam9.util.CommonUtils;
import com.jlesoft.civilservice.koreanhistoryexam9.util.DisplayUtils;
import com.jlesoft.civilservice.koreanhistoryexam9.util.LogUtil;
import com.jlesoft.civilservice.koreanhistoryexam9.util.MODE;
import com.jlesoft.civilservice.koreanhistoryexam9.util.PrefHelper;
import com.jlesoft.civilservice.koreanhistoryexam9.word.adapter.WordListAdapter;
import com.jlesoft.civilservice.koreanhistoryexam9.word.model.WordListDao;

import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;


public class QuestionFragment extends Fragment {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.ll_bottom_lay)
    LinearLayout llBottomLay;

    private String userCode;
    private Activity activity;
    private View view;
    private String sqClass;

    WordListAdapter adapter;

    Gson gson;
    ArrayList<WordListDao.WordListItem> dataArr = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
            userCode = ((WordListActivity) activity).userCode;
            sqClass = ((WordListActivity) activity).sqClass;

            ButterKnife.bind(this, view);
            llBottomLay.setVisibility(View.GONE);

            gson = new Gson();

            initRecyclerView();
            getPreTestCategory();
        }
        return view;
    }


    public void getPreTestCategory() {

        if (CommonUtils.getConnectNetwork(activity)) {

            JsonObject jo = new JsonObject();
            jo.addProperty("user_code", userCode);
            jo.addProperty("sq_class", sqClass);

            RequestData.getInstance().getVocabularyStandardQuestionCategory(jo, new NetworkResponse<JsonObject>() {
                @Override
                public void onSuccess(Call call, JsonObject clazz) {
                    if (clazz.get("statusCode").getAsString().equals("200")) {

                        String lastAppDay = "";

                        switch (sqClass){
                            case MODE.SQ_CLASS_V:
                                lastAppDay = PrefHelper.getString(activity, PrefConsts.INTENT_REQUEST_WORD_QUESTION_V_LIST_APPDAY, "");
                                break;
                            case MODE.SQ_CLASS_W:
                                lastAppDay = PrefHelper.getString(activity, PrefConsts.INTENT_REQUEST_WORD_QUESTION_W_LIST_APPDAY, "");
                                break;
                            case MODE.SQ_CLASS_X:
                                lastAppDay = PrefHelper.getString(activity, PrefConsts.INTENT_REQUEST_WORD_QUESTION_X_LIST_APPDAY, "");
                                break;
                            case MODE.SQ_CLASS_Y:
                                lastAppDay = PrefHelper.getString(activity, PrefConsts.INTENT_REQUEST_WORD_QUESTION_Y_LIST_APPDAY, "");
                                break;
                            case MODE.SQ_CLASS_F:
                                lastAppDay = PrefHelper.getString(activity, PrefConsts.INTENT_REQUEST_WORD_QUESTION_F_LIST_APPDAY, "");
                                break;
                        }

                        JsonArray jsonArr = clazz.get("resultData").getAsJsonArray();
                        dataArr.clear();

                        int scrollPosition = 0;
                        for (int i = 0; i < jsonArr.size(); i++) {
                            WordListDao.WordListItem item = gson.fromJson(jsonArr.get(i).toString(), WordListDao.WordListItem.class);

                            if(item.getAppDay().equals(lastAppDay)){
                                item.setCheckView("Y");
                                scrollPosition = i;
                            }else{
                                item.setCheckView("N");
                            }
                            dataArr.add(item);
                        }
                        adapter.notifyDataSetChanged();
                        rv.scrollToPosition(scrollPosition);
                    }
                }

                @Override
                public void onFail(Call call, String msg) {
                    Toast.makeText(activity, activity.getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                    activity.finish();
                }
            });
        } else {
            Toast.makeText(activity, activity.getString(R.string.msg_no_connect_network), Toast.LENGTH_SHORT).show();
        }
    }


    public void initRecyclerView() {
        adapter = new WordListAdapter(activity, dataArr);

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        final GestureDetector gestureDetector = new GestureDetector(activity, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        rv.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                final int position = rv.getChildAdapterPosition(child);

                if (child != null && gestureDetector.onTouchEvent(e)) {

                    final WordListDao.WordListItem data = dataArr.get(position);

                    if (data.myCnt.equals("0")) {
                        PopupMenu popup = new PopupMenu(activity, child.findViewById(R.id.anchor), Gravity.LEFT);
                        MenuInflater inflater = popup.getMenuInflater();
                        Menu menu = popup.getMenu();
                        inflater.inflate(R.menu.menu_list, menu);

                        menu.findItem(R.id.resume).setVisible(false);
                        menu.findItem(R.id.reset).setVisible(false);
                        menu.findItem(R.id.solve_mode).setVisible(true);
                        menu.findItem(R.id.mode).setVisible(true);

                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {

                                switch (item.getItemId()) {
                                    case R.id.solve_mode:
                                        moveQuestion(data, MODE.SOLVED_MODE);
                                        break;

                                    case R.id.mode:
                                        moveQuestion(data, MODE.LEARNING_MODE);
                                        break;
                                }
                                return false;
                            }
                        });
                        popup.show();

                    } else {
                        if (data.getTotalCnt().equals("0")) {
                            Toast.makeText(activity, "문제가 없습니다.", Toast.LENGTH_SHORT).show();

                        } else {
                            PopupMenu popup = new PopupMenu(activity, child.findViewById(R.id.anchor), Gravity.LEFT);

                            MenuInflater inflater = popup.getMenuInflater();
                            Menu menu = popup.getMenu();
                            inflater.inflate(R.menu.menu_list, menu);
                            menu.findItem(R.id.mode).setVisible(true);

                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {

                                    switch (item.getItemId()) {

                                        case R.id.resume:
                                            moveQuestion(data, MODE.EXPLAIN_MODE);
                                            break;

                                        case R.id.reset:
                                            //새로풀기 서버로 전송
                                            JsonObject jo = new JsonObject();
                                            jo.addProperty("user_code", BaseActivity.userCode);
                                            jo.addProperty("sq_class", sqClass);
                                            jo.addProperty("gubun", "question");
                                            jo.addProperty("app_day", dataArr.get(position).appDay);

                                            RequestData.getInstance().getVocabularyReset(jo, new NetworkResponse<JsonObject>() {

                                                @Override
                                                public void onSuccess(Call<JSONApiObject> call, JsonObject clazz) {
                                                    LogUtil.d("서버로 reset 전송");
                                                    moveQuestion(data, MODE.SOLVED_MODE);
                                                }

                                                @Override
                                                public void onFail(Call<JSONApiObject> call, String msg) {
                                                    Toast.makeText(activity, getResources().getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            break;
                                        case R.id.mode:
                                            moveQuestion(data, MODE.LEARNING_MODE);
                                            break;
                                    }
                                    return false;
                                }
                            });
                            popup.show();
                        }
                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }

    public void moveQuestion(final WordListDao.WordListItem data, int mode) {

        if (isAdded()) {

            DisplayUtils.showProgressDialog(activity, "문제 체크 중");
            JsonObject jo = new JsonObject();
            jo.addProperty("user_code", BaseActivity.userCode);
            jo.addProperty("app_day", data.appDay);
            jo.addProperty("sq_class", sqClass);

            RequestData.getInstance().getVocabularyStandardQuestionList(jo, new NetworkResponse<DefaultQuestionListDao>() {

                @Override
                public void onSuccess(Call call, DefaultQuestionListDao clazz) {
                    DisplayUtils.hideProgressDialog();
                    ArrayList<DefaultQuestionListDao.Question> questionList = clazz.getResultData().questionList;

                    if (questionList == null || questionList.size() <= 0) return;

                    int startNum = 0;

                    if(mode != MODE.LEARNING_MODE){

                        for (int i = 0; i < questionList.size(); i++) {
                            if (questionList.get(i).getSolvedYN().equalsIgnoreCase("N")) {
                                startNum = i;
                                break;
                            }
                        }
                    }

                    for (int k = 0; k < questionList.size(); k++) {
                        questionList.get(k).setIpAppDay(data.getAppDay());
                        int selectSJ = questionList.get(k).getSelectSunji();

                        /* 한자어 4지선다 A로 오므로 바꿔준다.*/
                        if(questionList.get(k).getSqClass().equals("A"))
                            questionList.get(k).setSqClass(MODE.SQ_CLASS_Y);

                        ArrayList<DefaultQuestionListDao.Sunji> sunjiList = questionList.get(k).sunjiList;

                        for (int l = 0; l < sunjiList.size(); l++) {
                            sunjiList.get(l).setIpaTitle(StringEscapeUtils.unescapeHtml4(sunjiList.get(l).getIpaTitle()));
                            sunjiList.get(l).setIpaMirrorText(StringEscapeUtils.unescapeHtml4(sunjiList.get(l).getIpaMirrorText()));

                            if(mode == MODE.LEARNING_MODE){
                                questionList.get(k).setSolvedYN("Y");

                                if(sunjiList.get(l).getIpaType().equals("O")){
                                    sunjiList.get(l).setSelect("O");
                                }else{
                                    sunjiList.get(l).setSelect("X");
                                }
                            }else if(mode == MODE.EXPLAIN_MODE){
                                if (sunjiList.get(l).getIpaIdx() == selectSJ) {
                                    sunjiList.get(l).setSelect("O");
                                } else {
                                    sunjiList.get(l).setSelect("X");
                                }
                            }else {
                                questionList.get(k).setSolvedYN("N");
                                questionList.get(k).setSelectSunji(0);

                                sunjiList.get(l).setSelect("X");
                            }
                        }
                    }

                    Intent intent = new Intent(activity, DayKoreanQuizActivity.class);
                    intent.putExtra("data", questionList);
                    intent.putExtra("startNum", startNum);
                    intent.putExtra("where_is", "C");
                    intent.putExtra("mode", DayKoreanQuizActivity.MODE_DAY_STUDY);
                    intent.putExtra("popup_mode", mode);
                    intent.putExtra("title", ((WordListActivity)activity).tvTitle.getText().toString());
                    startActivityForResult(intent, PrefConsts.REQUEST_WORD_WORD_ACTIVITY);
                }

                @Override
                public void onFail(Call call, String msg) {
                    DisplayUtils.hideProgressDialog();
                    Toast.makeText(activity, getResources().getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PrefConsts.REQUEST_WORD_WORD_ACTIVITY) {
            getPreTestCategory();
        }
    }
}

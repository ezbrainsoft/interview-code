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
import com.jlesoft.civilservice.koreanhistoryexam9.network.NetworkResponse;
import com.jlesoft.civilservice.koreanhistoryexam9.network.RequestData;
import com.jlesoft.civilservice.koreanhistoryexam9.util.CommonUtils;
import com.jlesoft.civilservice.koreanhistoryexam9.util.DisplayUtils;
import com.jlesoft.civilservice.koreanhistoryexam9.util.LogUtil;
import com.jlesoft.civilservice.koreanhistoryexam9.util.MODE;
import com.jlesoft.civilservice.koreanhistoryexam9.util.PrefHelper;
import com.jlesoft.civilservice.koreanhistoryexam9.word.adapter.WordListAdapter;
import com.jlesoft.civilservice.koreanhistoryexam9.word.model.WordListDao;
import com.jlesoft.civilservice.koreanhistoryexam9.word.model.WordQuestionDao;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

import static android.app.Activity.RESULT_OK;


public class WordFragment extends Fragment {

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
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
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
            DisplayUtils.showProgressDialog(activity, "문제 체크 중...");

            JsonObject jo = new JsonObject();
            jo.addProperty("user_code", userCode);
            jo.addProperty("sq_class", sqClass);

            RequestData.getInstance().getVocabularyStandardCategoryVoca(jo, new NetworkResponse<JsonObject>() {
                @Override
                public void onSuccess(Call call, JsonObject clazz) {
                    DisplayUtils.hideProgressDialog();

                    if (clazz.get("statusCode").getAsString().equals("200")) {

                        String lastAppDay = "";

                        switch (sqClass){
                            case MODE.SQ_CLASS_V:
                                lastAppDay = PrefHelper.getString(activity, PrefConsts.INTENT_REQUEST_WORD_V_LIST_APPDAY, "");
                                break;
                            case MODE.SQ_CLASS_W:
                                lastAppDay = PrefHelper.getString(activity, PrefConsts.INTENT_REQUEST_WORD_W_LIST_APPDAY, "");
                                break;
                            case MODE.SQ_CLASS_X:
                                lastAppDay = PrefHelper.getString(activity, PrefConsts.INTENT_REQUEST_WORD_X_LIST_APPDAY, "");
                                break;
                            case MODE.SQ_CLASS_Y:
                                lastAppDay = PrefHelper.getString(activity, PrefConsts.INTENT_REQUEST_WORD_Y_LIST_APPDAY, "");
                                break;
                            case MODE.SQ_CLASS_F:
                                lastAppDay = PrefHelper.getString(activity, PrefConsts.INTENT_REQUEST_WORD_F_LIST_APPDAY, "");
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
                    DisplayUtils.hideProgressDialog();
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

                    if (data.myCnt.equals("0")) {//풀이이력이 없으면

                        PopupMenu popup = new PopupMenu(activity, child.findViewById(R.id.anchor), Gravity.LEFT);

                        MenuInflater inflater = popup.getMenuInflater();
                        Menu menu = popup.getMenu();
                        inflater.inflate(R.menu.menu_mode, menu);

                        menu.findItem(R.id.explain_mode).setVisible(false);
                        menu.findItem(R.id.learning_mode).setVisible(true);

                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                if (item.getItemId() == R.id.solve_mode) {//풀이모드
                                    moveQuestion(data, MODE.SOLVED_MODE);

                                } else {//학습모드
                                    moveQuestion(data, MODE.LEARNING_MODE);
                                }
                                return false;
                            }
                        });
                        popup.show();

                    } else {

                        //문제가 아예 없는 경우
                        if (data.getTotalCnt().equals("0")) {
                            Toast.makeText(activity, "문제가 없습니다.", Toast.LENGTH_SHORT).show();

                        // 문제풀이 이력이 있는 경우
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
                                            moveQuestion(data, MODE.SOLVED_MODE);
                                            break;

                                        case R.id.reset:
                                            dataArr.get(position).setMyCnt("0");
                                            adapter.notifyDataSetChanged();

                                            //새로풀기 서버로 전송
                                            JsonObject jo = new JsonObject();
                                            jo.addProperty("user_code", BaseActivity.userCode);
                                            jo.addProperty("sq_class", sqClass);
                                            jo.addProperty("gubun", "voca");
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


    public void moveQuestion(WordListDao.WordListItem data, int mode) {
        if (isAdded()) {
            DisplayUtils.showProgressDialog(activity, "문제 체크 중");

            JsonObject jo = new JsonObject();
            jo.addProperty("user_code", BaseActivity.userCode);
            jo.addProperty("app_day", data.appDay);
            jo.addProperty("sq_class", sqClass);

            RequestData.getInstance().getVocabularyStandardVocaList(jo, new NetworkResponse<WordQuestionDao>() {

                @Override
                public void onSuccess(Call call, WordQuestionDao clazz) {
                    DisplayUtils.hideProgressDialog();

                    if (isAdded()) {
                        ArrayList<WordQuestionDao.WordQuestionItem> questionList = clazz.mainCategoryList;

                        int startNum = 0;
                        for (int i = 0; i < questionList.size(); i++) {
                            if (questionList.get(i).getSolvedYN().equalsIgnoreCase("N")) {
                                startNum = i;
                                break;
                            }
                        }

                        Intent intent = null;

                        if (mode == MODE.SOLVED_MODE) {//풀이모드
                            intent = new Intent(activity, WordOXQuizActivity.class);
                            intent.putExtra("mode", MODE.SOLVED_MODE);
                            intent.putExtra("startNum", startNum);
                            intent.putExtra("data", questionList);
                            startActivityForResult(intent, PrefConsts.REQUEST_WORD_WORD_ACTIVITY);

                        } else {//해설모드
                            intent = new Intent(activity, WordLearningModeListActivity.class);
                            intent.putExtra("mode", MODE.LEARNING_MODE);
                            intent.putExtra("startNum", 0);
                            intent.putExtra("data", questionList);
                            startActivity(intent);
                        }
                    }
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
        if (requestCode == PrefConsts.REQUEST_WORD_WORD_ACTIVITY && resultCode == RESULT_OK) {
            getPreTestCategory();
        }
    }
}

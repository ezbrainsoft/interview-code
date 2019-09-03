package com.jlesoft.civilservice.koreanhistoryexam9.adapter;

import android.app.Activity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.model.DayStudyList;
import com.jlesoft.civilservice.koreanhistoryexam9.model.DayStudyQuestion;
import com.jlesoft.civilservice.koreanhistoryexam9.model.DayStudyQuestionJimun;
import com.jlesoft.civilservice.koreanhistoryexam9.model.DayStudyQuestionSunji;
import com.jlesoft.civilservice.koreanhistoryexam9.model.OneDayStudyData;
import com.jlesoft.civilservice.koreanhistoryexam9.network.NetworkResponse;
import com.jlesoft.civilservice.koreanhistoryexam9.network.RequestData;
import com.jlesoft.civilservice.koreanhistoryexam9.util.CommonUtils;
import com.jlesoft.civilservice.koreanhistoryexam9.util.DisplayUtils;
import com.jlesoft.civilservice.koreanhistoryexam9.util.GetRandomText;
import com.jlesoft.civilservice.koreanhistoryexam9.util.LogUtil;

import org.apache.commons.text.StringEscapeUtils;

import java.util.Collections;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import static com.jlesoft.civilservice.koreanhistoryexam9.BaseActivity.userCode;

public class DayStudyListAdapter extends RecyclerView.Adapter<DayStudyListAdapter.ViewHolder> {

    private RealmResults<DayStudyList> dayStudyList;
    private Realm realm;
    private Activity activity;

    OnDayQuestionSelectListener mOnDayQuestionSelectListener;

    public interface OnDayQuestionSelectListener {
        void startStudy(RealmResults<DayStudyList> dayStudyList, String ipcCode, int day, int position);
        void resumeStudy(RealmResults<DayStudyList> dayStudyList, String ipcCode, int day, int submitCount, int questionCount, int position);
        void resetStudy(RealmResults<DayStudyQuestion> questionList, RealmResults<DayStudyList> dayStudyList, String ipcCode, int day, int position);
    }

    public DayStudyListAdapter(Activity activity, RealmResults<DayStudyList> dayStudyList, Realm realm, OnDayQuestionSelectListener listener) {
        this.activity = activity;
        this.dayStudyList = dayStudyList;
        this.realm = realm;
        this.mOnDayQuestionSelectListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearList;
        public TextView tvTitle, tvCount;
        public ImageView ivCheckView;
        public View anchor;
        public TextView tvDetail;

        public ViewHolder(View view) {
            super(view);
            linearList = (LinearLayout) view.findViewById(R.id.linear_list);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvCount = (TextView) view.findViewById(R.id.tv_cnt);
            ivCheckView = (ImageView) view.findViewById(R.id.iv_check_view);
            anchor = (View) view.findViewById(R.id.anchor);
            tvDetail = view.findViewById(R.id.tv_detail);
        }
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pre_rv_list, parent, false);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(final ViewHolder holder, final int position) {
        final DayStudyList listData = dayStudyList.get(position);

       RealmResults<DayStudyQuestion> submitQuestionResults = realm.where(DayStudyQuestion.class)
                .equalTo("ipcCode", listData.getIpcCode()).equalTo("ipAppDay", listData.getIpAppDay())
               .notEqualTo("studyType", "R")
               .equalTo("solved", "Y").findAll();

        int rightCount=0;
        for (int i = 0 ; i < submitQuestionResults.size();i++){
            String selectSunji = submitQuestionResults.get(i).getSelectSunji();
            for (int j = 0 ;j < submitQuestionResults.get(i).getSunjiList().size();j++){
                if(submitQuestionResults.get(i).getSunjiList().get(j).getIpaType().equalsIgnoreCase("O")){
                    int rightSunji = submitQuestionResults.get(i).getSunjiList().get(j).getSqiIdx();
                    if(selectSunji.equalsIgnoreCase(rightSunji+"")){
                        rightCount++;
                    }
                }
            }
        }

        if(submitQuestionResults.size() > 0){
            holder.tvDetail.setText((int)(100 / submitQuestionResults.size() * rightCount)+
                    "점 (풀이:"+submitQuestionResults.size()+"개 - 맞힘:"+rightCount+"개, 틀림:"+(submitQuestionResults.size() - rightCount)+"개)");
        }else{
            holder.tvDetail.setText("미풀이");
        }

        holder.tvTitle.setText("Day " + String.valueOf(listData.getIpAppDay()));
        holder.tvCount.setText(submitQuestionResults.size() + "/" + String.valueOf(listData.getTotalCnt()));

        if(!TextUtils.isEmpty(listData.getCheckView())&&listData.getCheckView().equalsIgnoreCase("Y")) {
            holder.ivCheckView.setVisibility(View.VISIBLE);
        } else {
            holder.ivCheckView.setVisibility(View.GONE);
        }

        holder.tvTitle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String ipcCode = listData.getIpcCode();
                final int day = listData.getIpAppDay();
                final String version = listData.getVersion();
                final RealmResults<DayStudyQuestion> questionList = realm.where(DayStudyQuestion.class)
                        .equalTo("ipcCode", ipcCode).equalTo("ipAppDay", day).findAll();
                final RealmResults<DayStudyQuestion> submitQuestionCount = realm.where(DayStudyQuestion.class)
                        .equalTo("ipcCode", ipcCode).equalTo("ipAppDay", day).equalTo("solved", "Y").findAll();

                int submitQuestion = -1;
                if(submitQuestionCount.size() > 0){
                    for (int i = 0; i <questionList.size();i++){
                        if(questionList.get(i).getSolved().equalsIgnoreCase("N")){
                            submitQuestion = i;
                            break;
                        }
                    }
                    if(submitQuestion == -1){
                        submitQuestion = questionList.size();
                    }
                }

                getNewDayStudyQuestion(dayStudyList, ipcCode, day, position, holder, questionList, submitQuestion);//다운로드 받으로 가기
            }
        });

        holder.linearList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.tvTitle.performClick();
            }
        });
    }

    @Override public int getItemCount() {
        return dayStudyList.size();
    }


    // 문제 다운로드 받기
    private void getNewDayStudyQuestion(final RealmResults<DayStudyList> dayStudyList, final String ipcCode,
                                        final int day, final int position, final ViewHolder holder, final RealmResults<DayStudyQuestion> questionList, final int submitQuestionCount) {

        DisplayUtils.showProgressDialog(activity, activity.getString(R.string.dialog_download_question_msg));
        LogUtil.e("새 문제 다운로드");
        JsonObject jo = new JsonObject();
        jo.addProperty("ipc_code", ipcCode);//카테고리 코드
        jo.addProperty("day", day);
        jo.addProperty("user_code", userCode);

        RequestData.getInstance().getNewDayStudyQuestion(jo, new NetworkResponse<OneDayStudyData>() {
            @Override
            public void onSuccess(Call call, OneDayStudyData clazz) {

                // 데이터를 새로 받아온 것과 같은 날짜에 기존 저장된 데이터를 가져온다
                DayStudyList originDayStudyListData = realm.where(DayStudyList.class).equalTo("ipcCode", ipcCode).equalTo("ipAppDay", day).findFirst();

                /**
                 * 기존 코드대로 하는 것보다 기존 데이터를 날리고 새로 넣는걸로 로직 변경해볼 것
                 */

                // 해당 데이터의 상위 카테고리와 새로 받아온 버전 코드를 먼저 저장해둔다
                String ipcUpCode = originDayStudyListData.getIpcUpCode();
                String version = clazz.getResultData().getVersion();

                LogUtil.e("문제 버전 체크(업데이트될 버전) : " + version);

                realm.beginTransaction();

                RealmResults<DayStudyQuestion> originQuestionList = realm.where(DayStudyQuestion.class).equalTo("ipcCode", ipcCode).equalTo("ipAppDay", day).findAll();


                //선지 리스트, 지문 리스트 삭제
                for (int i = 0; i < originQuestionList.size(); i++) {
                    RealmList<DayStudyQuestionSunji> originSunjiList = originQuestionList.get(i).getSunjiList();
                    RealmList<DayStudyQuestionJimun> originJimunList = originQuestionList.get(i).getJimunList();
                    originSunjiList.deleteAllFromRealm();
                    originJimunList.deleteAllFromRealm();
                }
                originQuestionList.deleteAllFromRealm();

                // 새로 받아온 데이터를 저장하기 위해 새로 realm용 오브젝트를 생성하고, 오브젝트에 데이터를 담아 저장한다.
                RealmList<DayStudyQuestion> newQuestionList = clazz.getResultData().getQuestionList();

                originDayStudyListData.setTotalCnt(newQuestionList.size());
                originDayStudyListData.setNormalQuestionCount(newQuestionList.size());
                originDayStudyListData.setCheckView("N");// 연필체크 이미지 표시 여부
                originDayStudyListData.setVersion(version); // TODO :: 문제를 새로 받아올 때 업데이트되는 버전코드

                RealmList<DayStudyQuestion> tempQuestionList = new RealmList<>();

                if (newQuestionList != null) {
                    for (int i = 0; i < newQuestionList.size(); i++) {
                        newQuestionList.get(i).setRnd(GetRandomText.getRandomText());
                        newQuestionList.get(i).setIpcCode(ipcCode);
                        newQuestionList.get(i).setIpAppDay(day);

                        if(!TextUtils.isEmpty(newQuestionList.get(i).getSelectSunji())){// && !newQuestionList.get(i).getSelectSunji().equals("0")
                            newQuestionList.get(i).setSolved("Y");
                        }else{
                            newQuestionList.get(i).setSolved("N");
                        }

                        newQuestionList.get(i).setsIdx(0);
                        newQuestionList.get(i).setNum(i+1);
                        newQuestionList.get(i).setStudyType("N");
                        newQuestionList.get(i).setIpTitle(StringEscapeUtils.unescapeHtml4(newQuestionList.get(i).getIpTitle()));

                        if (newQuestionList.get(i).getIpContent().contains(".png")) {
                            newQuestionList.get(i).setIpContent(newQuestionList.get(i).getIpContent());
                            newQuestionList.get(i).setIpContentSource(newQuestionList.get(i).getIpContentSource());
                        }

                        RealmList<DayStudyQuestionSunji> newSunjiList = newQuestionList.get(i).getSunjiList();
                        RealmList<DayStudyQuestionJimun> newJimunList = newQuestionList.get(i).getJimunList();

                        if (newSunjiList != null) {
                            for (int j = 0; j < newSunjiList.size(); j++) {
                                newSunjiList.get(j).setRnd(GetRandomText.getRandomText());
                                newSunjiList.get(j).setIpaTitle(StringEscapeUtils.unescapeHtml4(newSunjiList.get(j).getIpaTitle()));
                                newSunjiList.get(j).setIpaMirrorText(StringEscapeUtils.unescapeHtml4(newSunjiList.get(j).getIpaMirrorText()));
                            }

                            newQuestionList.get(i).setSunjiList(newSunjiList);
                        }
                        if (newJimunList != null) {
                            for (int j = 0; j < newJimunList.size(); j++) {
                                newJimunList.get(j).setRnd(GetRandomText.getRandomText());
                                newJimunList.get(j).setJimunTitle(StringEscapeUtils.unescapeHtml4(newJimunList.get(j).getJimunTitle()));
                                newJimunList.get(j).setIpaMirrorText(StringEscapeUtils.unescapeHtml4(newJimunList.get(j).getIpaMirrorText()));
                            }
                            Collections.sort(newJimunList, new CommonUtils.JimunAscBeforeStudy());
                            newQuestionList.get(i).setJimunList(newJimunList);
                        }
                    }

                    tempQuestionList.addAll(realm.copyToRealm(newQuestionList));
                    originDayStudyListData.setQuestionList(tempQuestionList);
                }
                realm.copyToRealmOrUpdate(originDayStudyListData);
                realm.commitTransaction();

                DisplayUtils.hideProgressDialog();

                RealmResults<DayStudyQuestion> dayStudyQuestion = realm.where(DayStudyQuestion.class)
                        .equalTo("ipcCode", ipcCode).equalTo("ipAppDay", day)
                        .equalTo("solved", "Y")
                        .findAll();


                if(dayStudyQuestion.size() <= 0){
                    mOnDayQuestionSelectListener.startStudy(dayStudyList, ipcCode, day, position);
                }else{

                    PopupMenu popup = new PopupMenu(activity, holder.anchor, Gravity.LEFT);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.menu_list, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (mOnDayQuestionSelectListener == null) return false;
                            switch (item.getItemId()) {
                                case R.id.resume:
                                    int questionCount = questionList.size();
                                    mOnDayQuestionSelectListener.resumeStudy(dayStudyList, ipcCode, day, submitQuestionCount, questionCount, position);
                                    break;

                                case R.id.reset:
                                    mOnDayQuestionSelectListener.resetStudy(questionList, dayStudyList, ipcCode, day, position);
                                    break;
                            }
                            return false;
                        }
                    });
                    popup.show();
                }
            }

            @Override
            public void onFail(Call call, String msg) {
                DisplayUtils.hideProgressDialog();
                Toast.makeText(activity, activity.getString(R.string.server_error_default_msg, msg), Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
}
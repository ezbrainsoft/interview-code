package com.jlesoft.civilservice.koreanhistoryexam9.word.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class WordListDao {
    @SerializedName("statusCode")
    @Expose
    public String statusCode;

    @SerializedName("resultData")
    @Expose
    public ArrayList<WordListItem> mainCategoryList;

    public class WordListItem implements Serializable {

        @SerializedName("ipc_code")
        @Expose
        public String ipcCode;

        @SerializedName("app_day")
        @Expose
        public String appDay;

        @SerializedName("cnt")
        @Expose
        public String cnt;

        @SerializedName("total_cnt")
        @Expose
        public String totalCnt;

        @SerializedName("check_view")
        @Expose
        public String checkView;

        @SerializedName("t_cnt")
        @Expose
        public String tCnt;

        @SerializedName("f_cnt")
        @Expose
        public String fCnt;

        @SerializedName("score")
        @Expose
        public String score;

        @SerializedName("my_cnt")
        @Expose
        public String myCnt;


        public String getCnt() {
            return cnt;
        }

        public void setCnt(String cnt) {
            this.cnt = cnt;
        }

        public String getIpcCode() {
            return ipcCode;
        }

        public void setIpcCode(String ipcCode) {
            this.ipcCode = ipcCode;
        }

        public String getAppDay() {
            return appDay;
        }

        public void setAppDay(String appDay) {
            this.appDay = appDay;
        }

        public String getTotalCnt() {
            return totalCnt;
        }

        public void setTotalCnt(String totalCnt) {
            this.totalCnt = totalCnt;
        }

        public String getCheckView() {
            return checkView;
        }

        public void setCheckView(String checkView) {
            this.checkView = checkView;
        }

        public String gettCnt() {
            return tCnt;
        }

        public void settCnt(String tCnt) {
            this.tCnt = tCnt;
        }

        public String getfCnt() {
            return fCnt;
        }

        public void setfCnt(String fCnt) {
            this.fCnt = fCnt;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getMyCnt() {
            return myCnt;
        }

        public void setMyCnt(String myCnt) {
            this.myCnt = myCnt;
        }

        @Override
        public String toString() {
            return "WordListItem{" +
                    "appDay='" + appDay + '\'' +
                    ", totalCnt='" + totalCnt + '\'' +
                    ", checkView='" + checkView + '\'' +
                    ", tCnt='" + tCnt + '\'' +
                    ", fCnt='" + fCnt + '\'' +
                    ", score='" + score + '\'' +
                    ", myCnt='" + myCnt + '\'' +
                    '}';
        }
    }
}

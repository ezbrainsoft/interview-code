package com.jlesoft.civilservice.koreanhistoryexam9.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by JonginYeom on 2017-09-11.
 * 단원 랜덤 하위 리스트 데이타
 */

public class StudyListSubCategory extends RealmObject {
    @SerializedName("ipc_code")
    @Expose
    @PrimaryKey
    public String ipcCode;

    @SerializedName("ipc_name")
    @Expose
    public String ipcName;

    @SerializedName("check_view")
    @Expose
    public String checkView;

    @SerializedName("question_cnt")
    @Expose
    public String questionCnt;

    @SerializedName("solved_cnt")
    @Expose
    public String solvedCnt;

    //////////////////////////////////////////////////////
    public String getIpcCode() {
        return ipcCode;
    }

    public void setIpcCode(String ipcCode) {
        this.ipcCode = ipcCode;
    }

    public String getIpcName() {
        return ipcName;
    }

    public void setIpcName(String ipcName) {
        this.ipcName = ipcName;
    }

    public String getCheckView() {
        return checkView;
    }

    public void setCheckView(String checkView) {
        this.checkView = checkView;
    }

    public String getQuestionCnt() {
        return questionCnt;
    }

    public void setQuestionCnt(String questionCnt) {
        this.questionCnt = questionCnt;
    }

    public String getSolvedCnt() {
        return solvedCnt;
    }

    public void setSolvedCnt(String solvedCnt) {
        this.solvedCnt = solvedCnt;
    }

    @Override
    public String toString() {
        return "StudyListSubCategory{" +
                "ipcCode='" + ipcCode + '\'' +
                ", ipcName='" + ipcName + '\'' +
                ", checkView='" + checkView + '\'' +
                ", questionCnt='" + questionCnt + '\'' +
                ", solvedCnt='" + solvedCnt + '\'' +
                '}';
    }
}

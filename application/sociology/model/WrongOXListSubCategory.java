package com.jlesoft.civilservice.koreanhistoryexam9.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 1 on 2018-02-19.
 */

public class WrongOXListSubCategory extends RealmObject {

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
    /////////////////////////////////////
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
}

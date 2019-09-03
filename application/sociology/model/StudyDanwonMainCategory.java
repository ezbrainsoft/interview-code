package com.jlesoft.civilservice.koreanhistoryexam9.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 1 on 2018-02-07.
 */

public class StudyDanwonMainCategory extends RealmObject {
    @SerializedName("ipc_up_code")
    @Expose
    @PrimaryKey
    public String ipcUpCode;

    @SerializedName("ipc_name")
    @Expose
    public String ipcName;

    @SerializedName("sub")
    @Expose
    public RealmList<StudyDanwonSubCategory> subDanwonCategoryList;

    ////////////////////////////////////////////////////////////////


    public String getIpcUpCode() {
        return ipcUpCode;
    }

    public void setIpcUpCode(String ipcUpCode) {
        this.ipcUpCode = ipcUpCode;
    }

    public String getIpcName() {
        return ipcName;
    }

    public void setIpcName(String ipcName) {
        this.ipcName = ipcName;
    }

    public RealmList<StudyDanwonSubCategory> getSubDanwonCategoryList() {
        return subDanwonCategoryList;
    }

    public void setSubDanwonCategoryList(RealmList<StudyDanwonSubCategory> subDanwonCategoryList) {
        this.subDanwonCategoryList = subDanwonCategoryList;
    }

    @Override
    public String toString() {
        return "StudyDanwonMainCategory{" +
                "ipcUpCode='" + ipcUpCode + '\'' +
                ", ipcName='" + ipcName + '\'' +
                ", subDanwonCategoryList=" + subDanwonCategoryList +
                '}';
    }
}

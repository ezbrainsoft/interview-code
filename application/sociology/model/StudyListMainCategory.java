package com.jlesoft.civilservice.koreanhistoryexam9.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by JonginYeom on 2017-09-11.
 */
@RealmClass
public class StudyListMainCategory extends RealmObject {
    @SerializedName("ipc_up_code")
    @Expose
    @PrimaryKey
    public String ipcUpCode;

    @SerializedName("ipc_name")
    @Expose
    public String ipcName;

    @SerializedName("sub")
    @Expose
    public RealmList<StudyListSubCategory> subCategoryList;

///////////////////////////////////////////////////////////////////////

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

    public RealmList<StudyListSubCategory> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(RealmList<StudyListSubCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    @Override
    public String toString() {
        return "StudyListMainCategory{" +
                "ipcUpCode='" + ipcUpCode + '\'' +
                ", ipcName='" + ipcName + '\'' +
                ", subCategoryList=" + subCategoryList +
                '}';
    }
}

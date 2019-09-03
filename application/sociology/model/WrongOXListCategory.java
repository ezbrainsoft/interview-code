package com.jlesoft.civilservice.koreanhistoryexam9.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 1 on 2018-02-19.
 */

public class WrongOXListCategory extends RealmObject{
    @SerializedName("ipc_up_code")
    @Expose
    @PrimaryKey
    public int ipcUpCode;

    @SerializedName("ipc_name")
    @Expose
    public String ipcName;

    @SerializedName("sub")
    @Expose
    public RealmList<WrongOXListSubCategory> subCategoryList;

    ////////////////////////////////////////////////////////////
    public int getIpcUpCode() {
        return ipcUpCode;
    }

    public void setIpcUpCode(int ipcUpCode) {
        this.ipcUpCode = ipcUpCode;
    }

    public String getIpcName() {
        return ipcName;
    }

    public void setIpcName(String ipcName) {
        this.ipcName = ipcName;
    }

    public RealmList<WrongOXListSubCategory> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(RealmList<WrongOXListSubCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

}

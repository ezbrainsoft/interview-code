package com.jlesoft.civilservice.koreanhistoryexam9.word.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class WordSearchListDao {
    @SerializedName("statusCode")
    @Expose
    public String statusCode;

    @SerializedName("resultData")
    @Expose
    public ResultData resultData;

    public class ResultData{
        @SerializedName("list")
        @Expose
        public ArrayList<WordListItem> list;
    }

    public class WordListItem implements Serializable {

        @SerializedName("ipc_code")
        @Expose
        public String ipcCode;

        @SerializedName("word")
        @Expose
        public String word;

        @SerializedName("explain_kor")
        @Expose
        public String explainKor;


    }
}

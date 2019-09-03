package com.jlesoft.civilservice.koreanhistoryexam9.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1 on 2018-04-05.
 */

public class MyPageVO {
    @SerializedName("today_solve")
    @Expose
    public String  today_solve;

    @SerializedName("today_progress")
    @Expose
    public String  today_progress;

    @SerializedName("today_grade")
    @Expose
    public String today_grade;

    @SerializedName("total_solve")
    @Expose
    public String  total_solve;

    @SerializedName("total_grade")
    @Expose
    public String total_grade ;

    @SerializedName("ox_cnt")
    @Expose
    public String  ox_cnt;

    @SerializedName("confirm_cnt")
    @Expose
    public String  confirm_cnt;

    @SerializedName("past_solve")
    @Expose
    public String past_solve ;

    @SerializedName("past_progress")
    @Expose
    public String past_progress ;

    @SerializedName("past_grade")
    @Expose
    public String  past_grade;

    @SerializedName("my_note")
    @Expose
    public String  my_note;

    @SerializedName("my_memo")
    @Expose
    public String  my_memo;

    @SerializedName("my_report_yn")
    @Expose
    public String  myReportYN;


}

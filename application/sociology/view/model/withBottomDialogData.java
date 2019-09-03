package com.jlesoft.civilservice.koreanhistoryexam9.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jlesoft.civilservice.koreanhistoryexam9.studygroup.dao.StudyGroupListDao;

import java.io.Serializable;

public class withBottomDialogData {
    @SerializedName("statusCode")
    @Expose
    public String statusCode;

    @SerializedName("resultData")
    @Expose
    public ResultData resultData;

    public class ResultData implements Serializable{
        @SerializedName("represent_yn")
        @Expose
        public String representYN;

        @SerializedName("with_idx")
        @Expose
        public String withIdx;

        @SerializedName("member_study")
        @Expose
        public MemberStudy memberStudy;

        @SerializedName("room_info")
        @Expose
        public StudyGroupListDao.StudyGroupList roomInfo;
    }

    public class MemberStudy{
        @SerializedName("voca")
        @Expose
        public Voca voca ;

        @SerializedName("life")
        @Expose
        public Life life ;


        @SerializedName("note")
        @Expose
        public Note note ;

        @SerializedName("gram")
        @Expose
        public Gram gram;

        @SerializedName("past")
        @Expose
        public Past past;

        @SerializedName("quest")
        @Expose
        public Quest quest;

        @SerializedName("ox")
        @Expose
        public OX ox;

    }

    public class Voca{
        @SerializedName("target")
        @Expose
        public int target;

        @SerializedName("total")
        @Expose
        public int total;

        @SerializedName("q_cnt")
        @Expose
        public String qCnt;

        @SerializedName("t_cnt")
        @Expose
        public String tCnt;

        @SerializedName("complete_yn")
        @Expose
        public String completeYN;
    }

    public class Quest{
        @SerializedName("target")
        @Expose
        public int target;

        @SerializedName("total")
        @Expose
        public int total;

        @SerializedName("q_cnt")
        @Expose
        public String qCnt;

        @SerializedName("t_cnt")
        @Expose
        public String tCnt;

        @SerializedName("complete_yn")
        @Expose
        public String completeYN;
    }

    public class OX{
        @SerializedName("target")
        @Expose
        public int target;

        @SerializedName("total")
        @Expose
        public int total;

        @SerializedName("q_cnt")
        @Expose
        public String qCnt;

        @SerializedName("t_cnt")
        @Expose
        public String tCnt;

        @SerializedName("complete_yn")
        @Expose
        public String completeYN;
    }

    public class Life{
        @SerializedName("target")
        @Expose
        public int target;

        @SerializedName("total")
        @Expose
        public int total;

        @SerializedName("q_cnt")
        @Expose
        public String qCnt;

        @SerializedName("t_cnt")
        @Expose
        public String tCnt;

        @SerializedName("complete_yn")
        @Expose
        public String completeYN;
    }


    public class Note{
        @SerializedName("target")
        @Expose
        public int target;

        @SerializedName("total")
        @Expose
        public int total;

        @SerializedName("q_cnt")
        @Expose
        public String qCnt;

        @SerializedName("t_cnt")
        @Expose
        public String tCnt;

        @SerializedName("complete_yn")
        @Expose
        public String completeYN;
    }

    public class Gram{
        @SerializedName("target")
        @Expose
        public int target;

        @SerializedName("total")
        @Expose
        public int total;

        @SerializedName("q_cnt")
        @Expose
        public String qCnt;

        @SerializedName("t_cnt")
        @Expose
        public String tCnt;

        @SerializedName("complete_yn")
        @Expose
        public String completeYN;
    }

    public class Past{
        @SerializedName("target")
        @Expose
        public int target;

        @SerializedName("total")
        @Expose
        public int total;

        @SerializedName("q_cnt")
        @Expose
        public String qCnt;

        @SerializedName("t_cnt")
        @Expose
        public String tCnt;

        @SerializedName("complete_yn")
        @Expose
        public String completeYN;
    }
}

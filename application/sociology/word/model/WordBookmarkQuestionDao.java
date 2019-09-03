package com.jlesoft.civilservice.koreanhistoryexam9.word.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class WordBookmarkQuestionDao {
    @SerializedName("statusCode")
    @Expose
    public String statusCode;

    @SerializedName("resultData")
    @Expose
    public ResultData resultData;

    public class ResultData{
        @SerializedName("total_cnt")
        @Expose
        public int totalCnt;

        @SerializedName("total_page")
        @Expose
        public int totalPage;

        @SerializedName("now_page")
        @Expose
        public int nowPage;

        @SerializedName("page_per_cnt")
        @Expose
        public int pagePerCnt;

        @SerializedName("list")
        @Expose
        public ArrayList<WordQuestionItem> mainCategoryList;

    }

    public class WordQuestionItem implements Serializable{
        @SerializedName("num")
        @Expose
        public int num;

        @SerializedName("sq_idx")
        @Expose
        public int sqIdx;

        @SerializedName("sqi_idx")
        @Expose
        public int sqiIdx;

        @SerializedName("app_day")
        @Expose
        public int appDay;

        @SerializedName("app_order")
        @Expose
        public String appOrder;

        @SerializedName("antonym")
        @Expose
        public String antonym;

        @SerializedName("synonym")
        @Expose
        public String synonym;

        @SerializedName("sqi_r_contents")
        @Expose
        public String sqiRContents;

        @SerializedName("sqi_w_contents")
        @Expose
        public String sqiWContents;

        @SerializedName("sqi_commentary")
        @Expose
        public String sqiCommentary;

        @SerializedName("sq_class")
        @Expose
        public String sqClass;

        @SerializedName("where_is")
        @Expose
        public String whereIs;

        @SerializedName("t_keyword")
        @Expose
        public String TKeyword;

        @SerializedName("f_keyword")
        @Expose
        public String FKeyword;

        @SerializedName("select_sunji")
        @Expose
        public int selectSunji;

        @SerializedName("solved_yn")
        @Expose
        public String solvedYN;

        @SerializedName("cs_is_result")
        @Expose
        public String csIsResult ;

        public String select;

        @SerializedName("bookmark")
        @Expose
        public String bookmark ;

        @SerializedName("sqi_field")
        @Expose
        public String sqiField;

        @SerializedName("add_sunji")
        @Expose
        public String addSunji;

        @SerializedName("reporting")
        @Expose
        public String reporting;

        @SerializedName("add_f_hanja")
        @Expose
        public String addFHanja;

        @SerializedName("add_f_korea")
        @Expose
        public String addFKorea;

        @SerializedName("add_f_explain")
        @Expose
        public String addFExplain;

        @SerializedName("t_contents")
        @Expose
        public String tContents;

        @SerializedName("f_title")
        @Expose
        public String fTitle;

        //////////////////////////


        public String getfTitle() {
            return fTitle;
        }

        public void setfTitle(String fTitle) {
            this.fTitle = fTitle;
        }

        public String gettContents() {
            return tContents;
        }

        public void settContents(String tContents) {
            this.tContents = tContents;
        }

        public String getAddFHanja() {
            return addFHanja;
        }

        public void setAddFHanja(String addFHanja) {
            this.addFHanja = addFHanja;
        }

        public String getAddFKorea() {
            return addFKorea;
        }

        public void setAddFKorea(String addFKorea) {
            this.addFKorea = addFKorea;
        }

        public String getAddFExplain() {
            return addFExplain;
        }

        public void setAddFExplain(String addFExplain) {
            this.addFExplain = addFExplain;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getSqIdx() {
            return sqIdx;
        }

        public void setSqIdx(int sqIdx) {
            this.sqIdx = sqIdx;
        }

        public int getSqiIdx() {
            return sqiIdx;
        }

        public void setSqiIdx(int sqiIdx) {
            this.sqiIdx = sqiIdx;
        }

        public int getAppDay() {
            return appDay;
        }

        public void setAppDay(int appDay) {
            this.appDay = appDay;
        }

        public String getAppOrder() {
            return appOrder;
        }

        public void setAppOrder(String appOrder) {
            this.appOrder = appOrder;
        }

        public String getAntonym() {
            return antonym;
        }

        public void setAntonym(String antonym) {
            this.antonym = antonym;
        }

        public String getSynonym() {
            return synonym;
        }

        public void setSynonym(String synonym) {
            this.synonym = synonym;
        }

        public String getSqiRContents() {
            return sqiRContents;
        }

        public void setSqiRContents(String sqiRContents) {
            this.sqiRContents = sqiRContents;
        }

        public String getSqiWContents() {
            return sqiWContents;
        }

        public void setSqiWContents(String sqiWContents) {
            this.sqiWContents = sqiWContents;
        }

        public String getSqiCommentary() {
            return sqiCommentary;
        }

        public void setSqiCommentary(String sqiCommentary) {
            this.sqiCommentary = sqiCommentary;
        }

        public String getSqClass() {
            return sqClass;
        }

        public void setSqClass(String sqClass) {
            this.sqClass = sqClass;
        }

        public String getWhereIs() {
            return whereIs;
        }

        public void setWhereIs(String whereIs) {
            this.whereIs = whereIs;
        }

        public String getTKeyword() {
            return TKeyword;
        }

        public void setTKeyword(String TKeyword) {
            this.TKeyword = TKeyword;
        }

        public String getFKeyword() {
            return FKeyword;
        }

        public void setFKeyword(String FKeyword) {
            this.FKeyword = FKeyword;
        }

        public int getSelectSunji() {
            return selectSunji;
        }

        public void setSelectSunji(int selectSunji) {
            this.selectSunji = selectSunji;
        }

        public String getSolvedYN() {
            return solvedYN;
        }

        public void setSolvedYN(String solvedYN) {
            this.solvedYN = solvedYN;
        }

        public String getCsIsResult() {
            return csIsResult;
        }

        public void setCsIsResult(String csIsResult) {
            this.csIsResult = csIsResult;
        }

        public String getSelect() {
            return select;
        }

        public void setSelect(String select) {
            this.select = select;
        }

        public String getBookmark() {
            return bookmark;
        }

        public void setBookmark(String bookmark) {
            this.bookmark = bookmark;
        }

        public String getSqiField() {
            return sqiField;
        }

        public void setSqiField(String sqiField) {
            this.sqiField = sqiField;
        }

        public String getAddSunji() {
            return addSunji;
        }

        public void setAddSunji(String addSunji) {
            this.addSunji = addSunji;
        }

        public String getReporting() {
            return reporting;
        }

        public void setReporting(String reporting) {
            this.reporting = reporting;
        }

        @Override
        public String toString() {
            return "WordQuestionItem{" +
                    "sqIdx=" + sqIdx +
                    ", sqiIdx=" + sqiIdx +
                    ", appDay=" + appDay +
                    ", appOrder='" + appOrder + '\'' +
                    ", antonym='" + antonym + '\'' +
                    ", synonym='" + synonym + '\'' +
                    ", sqiRContents='" + sqiRContents + '\'' +
                    ", sqiWContents='" + sqiWContents + '\'' +
                    ", sqiCommentary='" + sqiCommentary + '\'' +
                    ", sqClass='" + sqClass + '\'' +
                    ", whereIs='" + whereIs + '\'' +
                    ", TKeyword='" + TKeyword + '\'' +
                    ", FKeyword='" + FKeyword + '\'' +
                    ", selectSunji=" + selectSunji +
                    ", solvedYN='" + solvedYN + '\'' +
                    ", csIsResult='" + csIsResult + '\'' +
                    ", select='" + select + '\'' +
                    ", bookmark='" + bookmark + '\'' +
                    ", sqiField='" + sqiField + '\'' +
                    ", addSunji='" + addSunji + '\'' +
                    ", reporting='" + reporting + '\'' +
                    '}';
        }
    }
}

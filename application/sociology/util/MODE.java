package com.jlesoft.civilservice.koreanhistoryexam9.util;

public class MODE {

    public static final int EXPLAIN_MODE = 0;//해설모드, 이어풀기모드
    public static final int SOLVED_MODE = 1;//풀이모드, 새로풀기모드
    public static final int LEARNING_MODE = 2;//학습모드
    public static final int OX_MODE = 3; //OX모드

    public static final String WHERE_IS_R = "R";//오답노트
    public static final String WHERE_IS_C = "C";//단원
    public static final String WHERE_IS_Z = "Z";//기출문제 직렬
    public static final String WHERE_IS_Y = "Y";//기출문제 단원
    public static final String WHERE_IS_B = "B";//기출문제 북마크
    public static final String WHERE_IS_K = "K";//키워드
    public static final String WHERE_IS_D = "D";//일일학습
    public static final String WHERE_IS_N = "N";//스마트노트 -> 기출문제
    public static final String WHERE_IS_M = "M";//스마트메모->기출문제
    public static final String WHERE_IS_X = "X";//기출 북마크 랜덤
    public static final String WHERE_IS_E = "E";//강화일반(국어)

    public static final String TF_TYPE_T = "T";// 개념문제 T (강화 문제)
    public static final String TF_TYPE_F = "F";// 개념문제 F (강화 문제)
    public static final String TF_TYPE_C = "C";// 개념문제 C (국어 강화에만 적용되어 있음)
    public static final String TF_TYPE_R = "R";// 기출문제
    public static final String TF_TYPE_S = "S";// 기출일반
    public static final String TF_TYPE_B = "B";// 기출 2차 사례문제 (하단에 복습버튼만 나온다)
    public static final String TF_TYPE_L = "L";// 플로팅문제

    /* 국어 어휘학습 */
    public static final String[] WORD_MENU = {"표준어", "외래어", "사자성어", "한자어", "고유어"};

    public static final String SQ_CLASS_V = "V";//표준어
    public static final String SQ_CLASS_W = "W";//외래어
    public static final String SQ_CLASS_X = "X";//사자성어
    public static final String SQ_CLASS_Y = "Y";//한자어(어휘에선 Y로 오고, 문제에선 sq_class가 A로 온다. 북마크에서도 A로 온다.)
    public static final String SQ_CLASS_F = "F";//고유어

    public static final String SQ_CLASS_Z = "Z";//기출문제(직렬별)

}

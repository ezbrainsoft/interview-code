package com.jlesoft.civilservice.koreanhistoryexam9.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.gustavofao.jsonapi.Models.JSONApiObject;
import com.jlesoft.civilservice.koreanhistoryexam9.BuildConfig;
import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.combineSearch.dao.CombinSearchPastListDao;
import com.jlesoft.civilservice.koreanhistoryexam9.englishWord.model.EnglishWordDayListDao;
import com.jlesoft.civilservice.koreanhistoryexam9.englishWord.model.EnglishWordDayQuizDao;
import com.jlesoft.civilservice.koreanhistoryexam9.englishWord.model.EnglishWordFitRandomDao;
import com.jlesoft.civilservice.koreanhistoryexam9.englishWord.model.EnglishWordMainListDao;
import com.jlesoft.civilservice.koreanhistoryexam9.everydayEnglish.model.EveryDayBookmarkWordDao;
import com.jlesoft.civilservice.koreanhistoryexam9.everydayEnglish.model.EveryDayPharseListDao;
import com.jlesoft.civilservice.koreanhistoryexam9.everydayEnglish.model.EveryDayWordDao;
import com.jlesoft.civilservice.koreanhistoryexam9.everydayEnglish.model.EverydayTogether;
import com.jlesoft.civilservice.koreanhistoryexam9.grammar.dao.GrammarOXDao;
import com.jlesoft.civilservice.koreanhistoryexam9.grammar.dao.GrammarQuestionDao;
import com.jlesoft.civilservice.koreanhistoryexam9.grammar.dao.GrammarQuestionListDao;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api110GetDownloadTodayTen;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api123GetBbsNoticeList;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api206GetDailyTodayTenKor;
import com.jlesoft.civilservice.koreanhistoryexam9.model.DefaultCategoryMain;
import com.jlesoft.civilservice.koreanhistoryexam9.model.DefaultCategorySubList;
import com.jlesoft.civilservice.koreanhistoryexam9.model.DefaultOXQuestion;
import com.jlesoft.civilservice.koreanhistoryexam9.model.DefaultQuestionListDao;
import com.jlesoft.civilservice.koreanhistoryexam9.model.EverydayDailyStudyDao;
import com.jlesoft.civilservice.koreanhistoryexam9.model.popup.Api146GetBbsBannerList;
import com.jlesoft.civilservice.koreanhistoryexam9.model.pretest.Api158GetPrevioustestMainList;
import com.jlesoft.civilservice.koreanhistoryexam9.model.pretest.Api159GetPrevioustestSubList;
import com.jlesoft.civilservice.koreanhistoryexam9.model.pretest.Api160GetPrevioustestGetQuestion;
import com.jlesoft.civilservice.koreanhistoryexam9.model.sourceBook.Api149GetHistoricalMainList;
import com.jlesoft.civilservice.koreanhistoryexam9.model.mainTest.Api104GetReinforceList;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api11GetStudyQuestion;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api54GetWrongOXListCategory;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api55GetWrongListCategory;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api16GetPreTestCategory;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api17GetPreTestQuestion;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api23GetDownloadConfirm;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api24GetDownloadOxQuiz;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api25GetDownloadExplain;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api26GetDownloadReview;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api27GetDownloadReviewPlus;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api2GetStudyList;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api75GetSmartNoteQuestion;
import com.jlesoft.civilservice.koreanhistoryexam9.model.keyword.Api51getKeywordList;
import com.jlesoft.civilservice.koreanhistoryexam9.model.keyword.Api83GetDownloadKeywordQuestion;
import com.jlesoft.civilservice.koreanhistoryexam9.model.mainTest.Api105GetReinforceQuestion;
import com.jlesoft.civilservice.koreanhistoryexam9.model.mainTest.Api108GetReinforceExamAfter;
import com.jlesoft.civilservice.koreanhistoryexam9.model.popup.Api91GetConfigDashBoard;
import com.jlesoft.civilservice.koreanhistoryexam9.model.popup.Api85GetEventAttendance;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api98GetDownloadTodayFive;
import com.jlesoft.civilservice.koreanhistoryexam9.model.pretest.Api143GetFavoriteBookMarkCategoryPast;
import com.jlesoft.civilservice.koreanhistoryexam9.model.setting.Api137GetBbsFaqList;
import com.jlesoft.civilservice.koreanhistoryexam9.model.smartNote.Api68GetSmartNoteList;
import com.jlesoft.civilservice.koreanhistoryexam9.model.smartNote.Api69GetSmartNoteOne;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api70GetCategorySolvedHistory;
import com.jlesoft.civilservice.koreanhistoryexam9.model.Api71GetDownloadSolvedHistoryQuestion;
import com.jlesoft.civilservice.koreanhistoryexam9.model.smartNote.Api72GetCategorySmartNote;
import com.jlesoft.civilservice.koreanhistoryexam9.model.ApiGetReviewAndRefineQuestion;
import com.jlesoft.civilservice.koreanhistoryexam9.model.OneDayStudyData;
import com.jlesoft.civilservice.koreanhistoryexam9.model.smartNote.Api74GetSmartNoteQuestion;
import com.jlesoft.civilservice.koreanhistoryexam9.model.smartNote.Api77GetPrevioustestSmartnote;
import com.jlesoft.civilservice.koreanhistoryexam9.model.smartNote.Api79GetSmartNoteView;
import com.jlesoft.civilservice.koreanhistoryexam9.model.smartNote.Api80GetSmartNoteCate3All;
import com.jlesoft.civilservice.koreanhistoryexam9.model.smartNote.Api88GetSmartNoteBookMemoAll;
import com.jlesoft.civilservice.koreanhistoryexam9.model.smartNote.Api88GetSmartNoteBookMemoCategory;
import com.jlesoft.civilservice.koreanhistoryexam9.model.sourceBook.Api150GetHistoricalSubList;
import com.jlesoft.civilservice.koreanhistoryexam9.model.sourceBook.Api156GetHistoricalPasttest;
import com.jlesoft.civilservice.koreanhistoryexam9.model.subject.Api93GetSubjectList;
import com.jlesoft.civilservice.koreanhistoryexam9.model.subject.Api94GetSubjectQuestion;
import com.jlesoft.civilservice.koreanhistoryexam9.popup.model.GetSunjiSaveDao;
import com.jlesoft.civilservice.koreanhistoryexam9.popup.model.SunjiSaveListDao;
import com.jlesoft.civilservice.koreanhistoryexam9.previous.model.SDModePasttestDao;
import com.jlesoft.civilservice.koreanhistoryexam9.reading.model.EnglishReadingBaseDao;
import com.jlesoft.civilservice.koreanhistoryexam9.reading.model.EnglishReadingListDao;
import com.jlesoft.civilservice.koreanhistoryexam9.reading.model.EnglishReadingOriginalDao;
import com.jlesoft.civilservice.koreanhistoryexam9.setting.dao.InquiryDao;
import com.jlesoft.civilservice.koreanhistoryexam9.studygroup.dao.RequestMemberDao;
import com.jlesoft.civilservice.koreanhistoryexam9.studygroup.dao.StudyGroupBoardDao;
import com.jlesoft.civilservice.koreanhistoryexam9.studygroup.dao.StudyGroupListDao;
import com.jlesoft.civilservice.koreanhistoryexam9.studygroup.dao.StudyGroupMyWithListDao;
import com.jlesoft.civilservice.koreanhistoryexam9.studygroup.dao.StudyGroupRoomInfoDao;
import com.jlesoft.civilservice.koreanhistoryexam9.studygroup.dao.StudyGroupSendTalkDao;
import com.jlesoft.civilservice.koreanhistoryexam9.studygroup.dao.StudyGroupStateDao;
import com.jlesoft.civilservice.koreanhistoryexam9.studygroup.dao.StudyGroupTalkDao;
import com.jlesoft.civilservice.koreanhistoryexam9.util.JSONApiObjectPost;
import com.jlesoft.civilservice.koreanhistoryexam9.util.LogUtil;
import com.jlesoft.civilservice.koreanhistoryexam9.view.model.withBottomDialogData;
import com.jlesoft.civilservice.koreanhistoryexam9.word.model.WordBookmarkQuestionDao;
import com.jlesoft.civilservice.koreanhistoryexam9.word.model.WordListDao;
import com.jlesoft.civilservice.koreanhistoryexam9.word.model.WordQuestionDao;
import com.jlesoft.civilservice.koreanhistoryexam9.word.model.WordSearchListDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class RequestData {

//    public final static int REQUEST_ROW_COUNT = 30;

    private HashMap<Call, Callback> requestMap = new HashMap<>();

//    public static final String API_URL = "http://www.learntolearn.co.kr/rest/v1/";
    public static final String API_URL = BuildConfig.API_URL;
    private static Context CONTEXT;

    private static HashMap<String, String> HEADERS = new HashMap<String, String>() {
        {
            put("Content-Type", "application/json");
        }
    };

    public void setContext(Context ctx) {
        CONTEXT = ctx;
    }

    private RequestData() {
    }

    private static class Singleton {
        private static final RequestData instance = new RequestData();
    }

    public static RequestData getInstance() {
        System.out.println("create instance");
        return Singleton.instance;
    }

    public static String getBaseURL() {
        return API_URL;

    }

    private Gson gson;

    private OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        if(BuildConfig.DEBUG){
            httpClient.addInterceptor(logging);
        }
        httpClient.networkInterceptors().add(new Interceptor() {

            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder();
                requestBuilder.method(original.method(), original.body());
                Iterator<String> keys = HEADERS.keySet().iterator();

                while (keys.hasNext()) {
                    String key = keys.next();
                    requestBuilder.addHeader(key, HEADERS.get(key));
                }
                return chain.proceed(requestBuilder.build());
            }
        });
        return httpClient.build();
    }

    private Retrofit getClient() {
        if (gson == null) {
            //Tue, 01 Aug 2017 00:54:13 GMT
            gson = new GsonBuilder()
//            .setDateFormat("EEE, dd MMM yyyy HH:mm:ss Z")
                    .setDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                    .create();

        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getBaseURL())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getOkHttpClient())
                .build();

        return retrofit;
    }

   /* private Retrofit getClient(JSONConverterFactory converter) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getBaseURL())
                .addConverterFactory(converter)
                .client(getOkHttpClient())
                .build();

        return retrofit;
    }*/

    private void request(Call call, Callback callback) {
        requestMap.put(call, callback);
        if (!isRequestRefreshAuth) {
            call.enqueue(callback);
        }
    }

    /**
     * API No. 1 임시 아이디 발급
     *
     * @param jo
     * @param responseListener
     */
    public void getTempID(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getTempID(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 2 학습 리스트 가져오기 -> 53번으로 변경
     * @param jo
     * @param responseListener
     *
    public void getStudyList(JsonObject jo, final NetworkResponse responseListener) {
    RequestService service = getClient().create(RequestService.class);
    Call<Api2GetStudyList> result = service.getStudyList(jo);

    Callback callback = new Callback<Api2GetStudyList>() {
    @Override public void onResponse(Call<Api2GetStudyList> call, retrofit2.Response<Api2GetStudyList> response) {
    returnResponse(call, response, responseListener);
    }

    @Override public void onFailure(Call<Api2GetStudyList> call, Throwable t) {
    responseListener.onFail(call, t.toString());
    }
    };

    request(result, callback);
    }*/

    /** API 3,4번은 사용하지 않고, 대신 API 2번으로 가져온 리스트의 문제를 가져오는 통합 API인 11번을 사용함. */

    /**
     * API No. 5 정답 제출하기
     *
     * @param jo
     * @param responseListener
     */
    public void sendAnswer(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.sendAnswer(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 6 복습문제 가져오기
     *
     * @param jo
     * @param responseListener
     */
    public void getReviewQuestion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<ApiGetReviewAndRefineQuestion> result = service.getReviewQuestion(jo);

        Callback callback = new Callback<ApiGetReviewAndRefineQuestion>() {
            @Override
            public void onResponse(Call<ApiGetReviewAndRefineQuestion> call, retrofit2.Response<ApiGetReviewAndRefineQuestion> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<ApiGetReviewAndRefineQuestion> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 7 보강문제 가져오기
     *
     * @param jo
     * @param responseListener
     */
    public void getRefineQuestion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<ApiGetReviewAndRefineQuestion> result = service.getRefineQuestion(jo);

        Callback callback = new Callback<ApiGetReviewAndRefineQuestion>() {
            @Override
            public void onResponse(Call<ApiGetReviewAndRefineQuestion> call, retrofit2.Response<ApiGetReviewAndRefineQuestion> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<ApiGetReviewAndRefineQuestion> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 8 복습문제 정답제출
     *
     * @param jo
     * @param responseListener
     */
    public void sendReviewAnswer(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.sendReviewAnswer(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 9 복습plus(보강문제) 정답제출
     *
     * @param jo
     * @param responseListener
     */
    public void sendRefineAnswer(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.sendRefineAnswer(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 10 기출문제 가져오기
     *
     * @param jo
     * @param responseListener
     */
    public void getPastTest(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultQuestionListDao> result = service.getPastTest(jo);

        Callback callback = new Callback<DefaultQuestionListDao>() {
            @Override
            public void onResponse(Call<DefaultQuestionListDao> call, retrofit2.Response<DefaultQuestionListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultQuestionListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API 11 날짜별 문제리스트 전체 다운로드
     *
     * @param jo
     * @param responseListener
     */
    public void getDownloadQuestion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api11GetStudyQuestion> result = service.getDownloadQuestion(jo);

        Callback callback = new Callback<Api11GetStudyQuestion>() {
            @Override
            public void onResponse(Call<Api11GetStudyQuestion> call, retrofit2.Response<Api11GetStudyQuestion> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api11GetStudyQuestion> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 12 확인학습 새로 풀기
     *
     * @param jo
     * @param responseListener
     */
    public void setQuestionReset(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.setQuestionReset(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 13 오답노트 강화학습 리스트
     *
     * @param jo
     * @param responseListener
     */
    public void getOxnoteConfirm(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultCategoryMain> result = service.getOxnoteConfirm(jo);

        Callback callback = new Callback<DefaultCategoryMain>() {
            @Override
            public void onResponse(Call<DefaultCategoryMain> call, retrofit2.Response<DefaultCategoryMain> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultCategoryMain> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /** API 14번 사용하지 않음 */

    /**
     * API No. 15 문제풀이 카테고리 가져오기
     *
     * @param jo
     * @param responseListener
     */
    public void getOxnoteExplainCategory(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getOxnoteExplainCategory(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 16 기출문제 카테고리 가져오기
     *
     * @param jo
     * @param responseListener
     */
    public void getPreviousCategory(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api16GetPreTestCategory> result = service.getPreviousCategory(jo);

        Callback callback = new Callback<Api16GetPreTestCategory>() {
            @Override
            public void onResponse(Call<Api16GetPreTestCategory> call, retrofit2.Response<Api16GetPreTestCategory> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api16GetPreTestCategory> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 17 기출문제 직렬별 다운받기
     *
     * @param jo
     * @param responseListener
     */
    public void getPreviousQuestion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api17GetPreTestQuestion> result = service.getPreviousQuestion(jo);

        Callback callback = new Callback<Api17GetPreTestQuestion>() {
            @Override
            public void onResponse(Call<Api17GetPreTestQuestion> call, retrofit2.Response<Api17GetPreTestQuestion> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api17GetPreTestQuestion> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 18 로그인
     *
     * @param jo
     * @param responseListener
     */
    public void memberLogin(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.memberLogin(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 19 회원가입
     *
     * @param jo
     * @param responseListener
     */
    public void memberJoin(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.memberJoin(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 20 비밀번호 찾기 (임시비밀번호)
     *
     * @param jo
     * @param responseListener
     */
    public void memberFindPw(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.memberFindPw(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 21 비밀번호 변경
     *
     * @param jo
     * @param responseListener
     */
    public void memberUpdatePw(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.memberUpdatePw(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 22 오답노트 초기화
     *
     * @param jo
     * @param responseListener
     */
    public void memberReset(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.memberReset(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 23 오답노트 확인(다운로드)
     *
     * @param jo
     * @param responseListener
     */
    public void getDownloadConfirm(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api23GetDownloadConfirm> result = service.getDownloadConfirm(jo);

        Callback callback = new Callback<Api23GetDownloadConfirm>() {
            @Override
            public void onResponse(Call<Api23GetDownloadConfirm> call, retrofit2.Response<Api23GetDownloadConfirm> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api23GetDownloadConfirm> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 24 오답노트 OX(다운로드)
     *
     * @param jo
     * @param responseListener
     */
    public void getDownloadOXQuiz(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api24GetDownloadOxQuiz> result = service.getDownloadOXQuiz(jo);

        Callback callback = new Callback<Api24GetDownloadOxQuiz>() {
            @Override
            public void onResponse(Call<Api24GetDownloadOxQuiz> call, retrofit2.Response<Api24GetDownloadOxQuiz> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api24GetDownloadOxQuiz> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 25 오답노트 풀이(다운로드)
     *
     * @param jo
     * @param responseListener
     */
    public void getDownloadExplain(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api25GetDownloadExplain> result = service.getDownloadExplain(jo);

        Callback callback = new Callback<Api25GetDownloadExplain>() {
            @Override
            public void onResponse(Call<Api25GetDownloadExplain> call, retrofit2.Response<Api25GetDownloadExplain> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api25GetDownloadExplain> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 26 복습문제 다운로드 (기본 문제 풀이 완료 후)
     *
     * @param jo
     * @param responseListener
     */
    public void getDownloadReview(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api26GetDownloadReview> result = service.getDownloadReview(jo);

        Callback callback = new Callback<Api26GetDownloadReview>() {
            @Override
            public void onResponse(Call<Api26GetDownloadReview> call, retrofit2.Response<Api26GetDownloadReview> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api26GetDownloadReview> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 27 복습plus문제 다운로드 (복습 문제 풀이 완료 후)
     *
     * @param jo
     * @param responseListener
     */
    public void getDownloadReviewPlus(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api27GetDownloadReviewPlus> result = service.getDownloadReviewPlus(jo);

        Callback callback = new Callback<Api27GetDownloadReviewPlus>() {
            @Override
            public void onResponse(Call<Api27GetDownloadReviewPlus> call, retrofit2.Response<Api27GetDownloadReviewPlus> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api27GetDownloadReviewPlus> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 28 확인학습 새로 풀기 선택 시 해당 날짜 문제풀이 이력 리셋
     *
     * @param jo
     * @param responseListener
     */
    public void resetDayStudyHistory(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.resetDayStudyHistory(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 29 아이디 중복 확인
     *
     * @param jo
     * @param responseListener
     */
    public void memberEmailCheck(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.memberEmailCheck(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 30 오답노트 OX 별표 해제하고 페이지 나갈 때 삭제
     *
     * @param jo
     * @param responseListener
     */
    public void deleteOXQuiz(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.deleteOXQuiz(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 31 오답노트 확인 별표 해제하고 페이지 나갈 때 삭제
     *
     * @param jo
     * @param responseListener
     */
    public void deleteConfirmQuestion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.deleteConfirmQuestion(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 32 오답노트 풀이 별표 해제하고 페이지 나갈 때 삭제
     *
     * @param jo
     * @param responseListener
     */
    public void deleteExplainQuestion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.deleteExplainQuestion(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 33 해당 학습 day 문제 버전 체크하기
     *
     * @param jo
     * @param responseListener
     */
    public void checkDayStudyVersion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.checkDayStudyVersion(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 34 해당 학습 day 문제 다운로드
     *
     * @param jo
     * @param responseListener
     */
    public void getNewDayStudyQuestion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<OneDayStudyData> result = service.getNewDayStudyQuestion(jo);

        Callback callback = new Callback<OneDayStudyData>() {
            @Override
            public void onResponse(Call<OneDayStudyData> call, retrofit2.Response<OneDayStudyData> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<OneDayStudyData> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 35 복습버튼 정답 제출
     *
     * @param jo
     * @param responseListener
     */
    public void sendReviewButtonAnswer(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.sendReviewButtonAnswer(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 36 보강버튼 정답 제출
     *
     * @param jo
     * @param responseListener
     */
    public void sendRefineButtonAnswer(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.sendRefineButtonAnswer(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 37 기출버튼 정답 제출
     *
     * @param jo
     * @param responseListener
     */
    public void sendPastTestButtonAnswer(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.sendPastTestButtonAnswer(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 38 오답노트 확인 단원 선택 시 로그 전송
     *
     * @param jo
     * @param responseListener
     */
    public void sendSelectOxCategory(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.sendSelectOxCategory(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 39 오답노트 OX 정답 제출
     *
     * @param jo
     * @param responseListener
     */
    public void sendOxQuizAnswer(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.sendOxQuizAnswer(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 40 오답노트 풀이 정답 제출
     *
     * @param jo
     * @param responseListener
     */
    public void sendOxExplainAnswer(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.sendOxExplainAnswer(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 41 기출문제 정답 제출
     *
     * @param jo
     * @param responseListener
     */
    public void sendPreTestAnswer(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.sendPreTestAnswer(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 42 튜토리얼 다운로드
     *
     * @param jo
     * @param responseListener
     */
    public void getTutorial(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<OneDayStudyData> result = service.getTutorial(jo);

        Callback callback = new Callback<OneDayStudyData>() {
            @Override
            public void onResponse(Call<OneDayStudyData> call, retrofit2.Response<OneDayStudyData> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<OneDayStudyData> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 43 튜토리얼 첫페이지 접속 로그
     *
     * @param jo
     * @param responseListener
     */
    public void sendTutorialFirstPage(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.sendTutorialFirstPage(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 44 튜토리얼 마지막 페이지 접속 로그
     *
     * @param jo
     * @param responseListener
     */
    public void sendTutorialLastPage(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.sendTutorialLastPage(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 45 튜토리얼 각 페이지 접속 로그
     *
     * @param jo
     * @param responseListener
     */
    public void sendTutorialSaveLog(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.sendTutorialSaveLog(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 46 공지사항 가져오기
     *
     * @param jo
     * @param responseListener
     */
    public void getNotice(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getNotice(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 47 이벤트 url 가져오기
     *
     * @param responseListener
     */
    public void getEventUrl(final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getEventUrl();

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 48 설문조사 url 가져오기
     *
     * @param responseListener
     */
    public void getSurveyUrl(final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getSurveyUrl();

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 50 앱버전 가져오기
     *
     * @param responseListener
     */
    public void getAppVersion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getAppVersion(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 51 키워드 복습(전체)
     *
     * @param responseListener
     */
    public void getKeyworList(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api51getKeywordList> result = service.getKeyworList(jo);

        Callback callback = new Callback<Api51getKeywordList>() {
            @Override
            public void onResponse(Call<Api51getKeywordList> call, retrofit2.Response<Api51getKeywordList> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api51getKeywordList> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }


    /**
     * API No. 53 한국사 리스트 가져오기
     *
     * @param responseListener
     */
    public void getStudyList(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api2GetStudyList> result = service.getStudyList(jo);

        Callback callback = new Callback<DefaultCategoryMain>() {
            @Override
            public void onResponse(Call<DefaultCategoryMain> call, retrofit2.Response<DefaultCategoryMain> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultCategoryMain> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 54 한국사 리스트(OX)
     *
     * @param responseListener
     */
    public void getOxnoteList(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api54GetWrongOXListCategory> result = service.getOxnoteList(jo);

        Callback callback = new Callback<Api54GetWrongOXListCategory>() {
            @Override
            public void onResponse(Call<Api54GetWrongOXListCategory> call, retrofit2.Response<Api54GetWrongOXListCategory> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api54GetWrongOXListCategory> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 54 한국사 리스트(OX)
     *
     * @param responseListener
     */
    public void getConfirmNoteList(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api55GetWrongListCategory> result = service.getConfirmNoteList(jo);

        Callback callback = new Callback<Api55GetWrongListCategory>() {
            @Override
            public void onResponse(Call<Api55GetWrongListCategory> call, retrofit2.Response<Api55GetWrongListCategory> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api55GetWrongListCategory> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 56 오류 신고
     *
     * @param responseListener
     */
    public void sendErrorReport(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.sendErrorReport(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 57 팝업 공지
     *
     * @param responseListener
     */
    public void getPopupNotice(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getPopupNotice(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }


    /**
     * API No. 58 맞춘 문제 오답노트에 추가
     *
     * @param responseListener
     */
    public void sendQuestionToOxNote(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.sendQuestionToOxNote(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 59 설정 > 고객 문의
     *
     * @param responseListener
     */
    public void getCustomerAskPageUrl(final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getCustomerAskPageUrl();

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 60 스마트노트
     *
     * @param responseListener
     */
    public void getSmartNotePageUrl(final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getSmartNotePageUrl();

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API 61 단원별 리스트 다운로드
     *
     * @param jo
     * @param responseListener
     */
    public void getListDanwonCategory(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultCategoryMain> result = service.getCategoryListDanwon(jo);

        Callback callback = new Callback<DefaultCategoryMain>() {
            @Override
            public void onResponse(Call<DefaultCategoryMain> call, retrofit2.Response<DefaultCategoryMain> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultCategoryMain> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API 62 단원 서브 리스트
     *
     * @param jo
     * @param responseListener
     */
    public void getCategoryListDanwonSub(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<DefaultCategorySubList> result = service.getCategoryListDanwonSub(jo);

        Callback callback = new Callback<DefaultCategorySubList>() {
            @Override
            public void onResponse(Call<DefaultCategorySubList> call, retrofit2.Response<DefaultCategorySubList> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultCategorySubList> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 63 해당 학습 단원별 버전 체크
     *
     * @param jo
     * @param responseListener
     */
    public void getCheckDanwonStudyVersion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getCheckDanwonStudyVersion(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 64 해당 단원 문제 다시 다운로드
     * @param jo
     * @param responseListener
     */
    /*public void getNewDanwonStudyQuestion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<OneDanwonStudyData> result = service.getNewDanwonStudyQuestion(jo);

        Callback callback = new Callback<OneDanwonStudyData>() {
            @Override
            public void onResponse(Call<OneDanwonStudyData> call, retrofit2.Response<OneDanwonStudyData> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<OneDanwonStudyData> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }*/


    /**
     * API No. 67. 단원순서문제 새로 풀기
     *
     * @param jo
     * @param responseListener
     */
    public void getCategoryOxYN(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getCategoryOxYN(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 66. 단원순서문제 새로 풀기
     *
     * @param jo
     * @param responseListener
     */
    public void getQuestionResetDanwon(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getQuestionResetDanwon(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API API No. 68 스마트노트-리스트
     *
     * @param jo
     * @param responseListener
     */
    public void getSmartNoteList(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api68GetSmartNoteList> result = service.getSmartNoteList(jo);

        Callback callback = new Callback<Api68GetSmartNoteList>() {
            @Override
            public void onResponse(Call<Api68GetSmartNoteList> call, retrofit2.Response<Api68GetSmartNoteList> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api68GetSmartNoteList> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 69 스마트노트-보기
     *
     * @param responseListener
     */
    public void getSmartNoteOne(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api69GetSmartNoteOne> result = service.getSmartNoteOne(jo);

        Callback callback = new Callback<Api69GetSmartNoteOne>() {
            @Override
            public void onResponse(Call<Api69GetSmartNoteOne> call, retrofit2.Response<Api69GetSmartNoteOne> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api69GetSmartNoteOne> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 70 이전 풀이 문제
     *
     * @param responseListener
     */
    public void getCategorySolvedHistory(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api70GetCategorySolvedHistory> result = service.getCategorySolvedHistory(jo);

        Callback callback = new Callback<Api70GetCategorySolvedHistory>() {
            @Override
            public void onResponse(Call<Api70GetCategorySolvedHistory> call, retrofit2.Response<Api70GetCategorySolvedHistory> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api70GetCategorySolvedHistory> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API API No. 71 이전 풀이 문제 단락 다운로드
     *
     * @param jo
     * @param responseListener
     */
    public void getDownloadSolvedHistoryQuestion(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api71GetDownloadSolvedHistoryQuestion> result = service.getDownloadSolvedHistoryQuestion(jo);

        Callback callback = new Callback<Api71GetDownloadSolvedHistoryQuestion>() {
            @Override
            public void onResponse(Call<Api71GetDownloadSolvedHistoryQuestion> call, retrofit2.Response<Api71GetDownloadSolvedHistoryQuestion> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api71GetDownloadSolvedHistoryQuestion> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API API No. 72 스마트노트-카테고리
     *
     * @param jo
     * @param responseListener
     */
    public void getCategorySmartNote(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api72GetCategorySmartNote> result = service.getCategorySmartNote(jo);

        Callback callback = new Callback<Api72GetCategorySmartNote>() {
            @Override
            public void onResponse(Call<Api72GetCategorySmartNote> call, retrofit2.Response<Api72GetCategorySmartNote> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api72GetCategorySmartNote> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API API No. 73 스마트노트 - 연관 기출문제
     *
     * @param jo
     * @param responseListener
     */
    public void getSmartNotePasttest(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<DefaultQuestionListDao> result = service.getSmartNotePasttest(jo);

        Callback callback = new Callback<DefaultQuestionListDao>() {
            @Override
            public void onResponse(Call<DefaultQuestionListDao> call, retrofit2.Response<DefaultQuestionListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultQuestionListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API API No. 74 스마트노트 - 연관 강화문제
     *
     * @param jo
     * @param responseListener
     */
    public void getSmartNoteQuestion(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<DefaultQuestionListDao> result = service.getSmartNoteQuestion(jo);

        Callback callback = new Callback<Api74GetSmartNoteQuestion>() {
            @Override
            public void onResponse(Call<Api74GetSmartNoteQuestion> call, retrofit2.Response<Api74GetSmartNoteQuestion> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api74GetSmartNoteQuestion> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 75 스마트노트 - 강화학습에 연결된 스마트노트들
     */
    public void getSmartNoteSmartNoteFromQuestion(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api75GetSmartNoteQuestion> result = service.getSmartNoteSmartNoteFromQuestion(jo);

        Callback callback = new Callback<Api75GetSmartNoteQuestion>() {
            @Override
            public void onResponse(Call<Api75GetSmartNoteQuestion> call, retrofit2.Response<Api75GetSmartNoteQuestion> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API API No. 76 기출문제 새로풀기
     *
     * @param jo
     * @param responseListener
     */
    public void getPrevioustestReset(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getPrevioustestReset(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 77.기출문제에 연동된 스마트노트
     */
    public void getPrevioustestSmartNote(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api77GetPrevioustestSmartnote> result = service.getPrevioustestSmartNote(jo);

        Callback callback = new Callback<Api77GetPrevioustestSmartnote>() {
            @Override
            public void onResponse(Call<Api77GetPrevioustestSmartnote> call, retrofit2.Response<Api77GetPrevioustestSmartnote> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API API No. 78.스마트노트 오류신고
     *
     * @param jo
     * @param responseListener
     */
    public void getSmartNoteReport(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getSmartNoteReport(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API API No. 79.스마트노트 3개씩 가져오기
     *
     * @param jo
     * @param responseListener
     */
    public void getSmartNoteView(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api79GetSmartNoteView> result = service.getSmartNoteView(jo);

        Callback callback = new Callback<Api79GetSmartNoteView>() {
            @Override
            public void onResponse(Call<Api79GetSmartNoteView> call, retrofit2.Response<Api79GetSmartNoteView> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api79GetSmartNoteView> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }


    /**
     * API API No. 80.스마트노트 3개씩 가져오기
     *
     * @param jo
     * @param responseListener
     */
    public void getSmartNoteCate3All(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api80GetSmartNoteCate3All> result = service.getSmartNoteCate3All(jo);

        Callback callback = new Callback<Api80GetSmartNoteCate3All>() {
            @Override
            public void onResponse(Call<Api80GetSmartNoteCate3All> call, retrofit2.Response<Api80GetSmartNoteCate3All> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api80GetSmartNoteCate3All> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API API No. 81.스마트노트 로그 쌓기
     *
     * @param jo
     * @param responseListener
     */
    public void getSmartNoteLogs(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getSmartNoteLogs(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }


    /**
     * API API No. 83.키워드의 강화학습 가져오기
     *
     * @param jo
     * @param responseListener
     */
    public void getDownloadKeywordQuestion(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api83GetDownloadKeywordQuestion> result = service.getDownloadKeywordQuestion(jo);

        Callback callback = new Callback<Api83GetDownloadKeywordQuestion>() {
            @Override
            public void onResponse(Call<Api83GetDownloadKeywordQuestion> call, retrofit2.Response<Api83GetDownloadKeywordQuestion> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api83GetDownloadKeywordQuestion> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }


    /**
     * API API No. 85. 출석부
     *
     * @param jo
     * @param responseListener
     */
    public void getEventAttendance(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api85GetEventAttendance> result = service.getEventAttendance(jo);

        Callback callback = new Callback<Api85GetEventAttendance>() {
            @Override
            public void onResponse(Call<Api85GetEventAttendance> call, retrofit2.Response<Api85GetEventAttendance> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api85GetEventAttendance> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API API No. 86. 스마트노트-메모
     *
     * @param jo
     * @param responseListener
     */
    public void getSmartNoteMemo(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getSmartNoteMemo(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }


    /**
     * API API No. 87. 스마트노트-북마크
     *
     * @param jo
     * @param responseListener
     */
    public void getSmartBookMark(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getSmartBookMark(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API API No. 88. 스마트노트 - 메모 카테고리별
     *
     * @param jo
     * @param responseListener
     */
    public void getSmartNoteBookMemoAll(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api88GetSmartNoteBookMemoAll> result = service.getSmartNoteBookMemoAll(jo);

        Callback callback = new Callback<Api88GetSmartNoteBookMemoAll>() {
            @Override
            public void onResponse(Call<Api88GetSmartNoteBookMemoAll> call, retrofit2.Response<Api88GetSmartNoteBookMemoAll> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api88GetSmartNoteBookMemoAll> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API API No. 88. 스마트노트 - 메모 카테고리별
     *
     * @param jo
     * @param responseListener
     */
    public void getSmartNoteBookMemoCategory(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api88GetSmartNoteBookMemoCategory> result = service.getSmartNoteBookMemoCategory(jo);

        Callback callback = new Callback<Api88GetSmartNoteBookMemoCategory>() {
            @Override
            public void onResponse(Call<Api88GetSmartNoteBookMemoCategory> call, retrofit2.Response<Api88GetSmartNoteBookMemoCategory> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api88GetSmartNoteBookMemoCategory> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 91.메인 학습 현황판
     */
    public void getConfigDashBoard(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api91GetConfigDashBoard> result = service.getConfigDashBoard(jo);

        Callback callback = new Callback<Api91GetConfigDashBoard>() {
            @Override
            public void onResponse(Call<Api91GetConfigDashBoard> call, retrofit2.Response<Api91GetConfigDashBoard> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api91GetConfigDashBoard> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }


    /**
     * API API No. 92. 스마트노트 3차 북마크만 가져오기
     *
     * @param jo
     * @param responseListener
     */
    public void getSmartNoteCate3BookMark(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api80GetSmartNoteCate3All> result = service.getSmartNoteCate3BookMark(jo);

        Callback callback = new Callback<Api80GetSmartNoteCate3All>() {
            @Override
            public void onResponse(Call<Api80GetSmartNoteCate3All> call, retrofit2.Response<Api80GetSmartNoteCate3All> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api80GetSmartNoteCate3All> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 93. 강화학습 - 주제별 리스트
     *
     * @param jo
     * @param responseListener
     */
    public void getSubjectList(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api93GetSubjectList> result = service.getSubjectList(jo);

        Callback callback = new Callback<Api93GetSubjectList>() {
            @Override
            public void onResponse(Call<Api93GetSubjectList> call, retrofit2.Response<Api93GetSubjectList> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api93GetSubjectList> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 94. 오늘 학습한 내용용
     */
    public void getMemberToday(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getMemberToday(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }


    /**
     * API No. 95. 강화학습 - 주제별 서브 리스트&문제
     *
     * @param jo
     * @param responseListener
     */
    public void getSubjectQuestion(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api94GetSubjectQuestion> result = service.getSubjectQuestion(jo);

        Callback callback = new Callback<Api94GetSubjectQuestion>() {
            @Override
            public void onResponse(Call<Api94GetSubjectQuestion> call, retrofit2.Response<Api94GetSubjectQuestion> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api94GetSubjectQuestion> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 96.이전문제 로그 쌓기
     *
     * @param jo
     * @param responseListener
     */
    public void getQuestionPastQuestionLogs(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getQuestionPastQuestionLogs(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }


    /**
     * API No. 97.마이페이지
     *
     * @param jo
     * @param responseListener
     */
    public void getConfigMyPage(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getConfigMyPage(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 98.일일5문제
     */
    public void getDownloadTodayFive(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api98GetDownloadTodayFive> result = service.getDownloadTodayFive(jo);

        Callback callback = new Callback<Api98GetDownloadTodayFive>() {
            @Override
            public void onResponse(Call<Api98GetDownloadTodayFive> call, retrofit2.Response<Api98GetDownloadTodayFive> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api98GetDownloadTodayFive> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 99.일일5문제 정답율
     */
    public void getDownloadTodayFiveRate(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getDownloadTodayFiveRate(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 100.기출문제 머문시간
     */

    public void getPrevioustestSaveTime(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getPrevioustestSaveTime(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 101.난이도 문제
     */

    public void getDifficultyList(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getDifficultyList(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 102.토큰전송
     */
    public void getConfigSaveToken(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getConfigSaveToken(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 103.강화시험 대시보드
     */
    public void getReinforceDashBoard(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getReinforceDashBoard(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 104.강화시험 리스트
     */
    public void getReinforceList(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api104GetReinforceList> result = service.getReinforceList(jo);

        Callback callback = new Callback<Api104GetReinforceList>() {
            @Override
            public void onResponse(Call<Api104GetReinforceList> call, retrofit2.Response<Api104GetReinforceList> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api104GetReinforceList> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 105.강화시험 문제
     */

    public void getReinforceQuestion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api105GetReinforceQuestion> result = service.getReinforceQuestion(jo);

        Callback callback = new Callback<Api105GetReinforceQuestion>() {
            @Override
            public void onResponse(Call<Api105GetReinforceQuestion> call, retrofit2.Response<Api105GetReinforceQuestion> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api105GetReinforceQuestion> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 106.푸쉬 동의
     */
    public void getMemberAgree(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getMemberAgree(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 107. 강화시험 답제출
     */
    public void getReinforceAnswer(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getReinforceAnswer(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 108. 강화시험 풀이 후
     */
    public void getReinforceExamAfter(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api108GetReinforceExamAfter> result = service.getReinforceExamAfter(jo);

        Callback callback = new Callback<Api108GetReinforceExamAfter>() {
            @Override
            public void onResponse(Call<Api108GetReinforceExamAfter> call, retrofit2.Response<Api108GetReinforceExamAfter> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api108GetReinforceExamAfter> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 110.  일일10문제
     */

    public void getDownloadTodayTen(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api110GetDownloadTodayTen> result = service.getDownloadTodayTen(jo);

        Callback callback = new Callback<Api110GetDownloadTodayTen>() {
            @Override
            public void onResponse(Call<Api110GetDownloadTodayTen> call, retrofit2.Response<Api110GetDownloadTodayTen> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api110GetDownloadTodayTen> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 111.일일 10문제 정답율
     */
    public void getDownloadTodayTenRate(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getDownloadTodayTenRate(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }


    /**
     * API No. 112. 일일 스마트노트 호출
     */
    public void getSmartNoteDaily(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api75GetSmartNoteQuestion> result = service.getSmartNoteDaily(jo);

        Callback callback = new Callback<Api75GetSmartNoteQuestion>() {
            @Override
            public void onResponse(Call<Api75GetSmartNoteQuestion> call, retrofit2.Response<Api75GetSmartNoteQuestion> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api75GetSmartNoteQuestion> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }


    /**
     * API No. 118.오류현황
     */

    public void getQuestionReportStaus(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getQuestionReportStaus(jo);

        Callback callback = new Callback<Api75GetSmartNoteQuestion>() {
            @Override
            public void onResponse(Call<Api75GetSmartNoteQuestion> call, retrofit2.Response<Api75GetSmartNoteQuestion> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api75GetSmartNoteQuestion> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 119.일일 10문제 - newbie
     */

    public void getDownloadTodayTenNewbie(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api110GetDownloadTodayTen> result = service.getDownloadTodayTenNewbie(jo);

        Callback callback = new Callback<Api110GetDownloadTodayTen>() {
            @Override
            public void onResponse(Call<Api110GetDownloadTodayTen> call, retrofit2.Response<Api110GetDownloadTodayTen> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api110GetDownloadTodayTen> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 120.일일 10문제 정답율 - newbie
     */
    public void getDownloadTodayTenRateNewbie(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getDownloadTodayTenRateNewbie(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 121. 일일 스마트노트 호출 - newbie
     */
    public void getSmartNoteDailyNewbie(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api75GetSmartNoteQuestion> result = service.getSmartNoteDailyNewbie(jo);

        Callback callback = new Callback<Api75GetSmartNoteQuestion>() {
            @Override
            public void onResponse(Call<Api75GetSmartNoteQuestion> call, retrofit2.Response<Api75GetSmartNoteQuestion> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api75GetSmartNoteQuestion> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 122. 무료 이벤트 신청
     */
    public void getEventEventFree(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getEventEventFree(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 123. 공지사항 리스트
     */
    public void getBbsNoticeList(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api123GetBbsNoticeList> result = service.getBbsNoticeList(jo);

        Callback callback = new Callback<Api123GetBbsNoticeList>() {

            @Override
            public void onResponse(Call<Api123GetBbsNoticeList> call, retrofit2.Response<Api123GetBbsNoticeList> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api123GetBbsNoticeList> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 124. 공지사항 내용보기
     */
    public void getBbsNoticeView(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getBbsNoticeView(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 125. 내가 등록한 오류 리스트
     */

    public void getConfigMyReportList(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getConfigMyReportList(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 126. 내가 등록한 오류 상세보기
     */

    public void getConfigMyReportView(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getConfigMyReportView(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }



    /**
     * API No. 127. 주문결제정보저장
     */

    public void getPGInsertOrder(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getPGInsertOrder(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 128. 주문 결제 UPDATE
     */

    public void getPGUpdateOrder(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getPGUpdateOrder(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 129. 이용권 구매 YN
     */

    public void getPGPayUseYN(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getPGPayUseYN(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 130. 이용권 상품 리스트
     */

    public void getPGGoodsList(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getPGGoodsList(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }


    /**
     * API No. 131. 이용권 상품 구매 리스트
     */

    public void getPGBuyList(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getPGBuyList(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 133. 주문번호 직접입력 받기
     */

    public void getPGRegisterOrder(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getPGRegisterOrder(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 134. 강화문제 즐겨찾기
     */

    public void getFavoriteBookmark(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getFavoriteBookmark(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 135. 강화문제 즐겨찾기 리스트
     */

    public void getFavoriteBookmarkListDanwon(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<DefaultCategoryMain> result = service.getFavoriteBookmarkListDanwon(jo);

        Callback callback = new Callback<DefaultCategoryMain>() {

            @Override
            public void onResponse(Call<DefaultCategoryMain> call, retrofit2.Response<DefaultCategoryMain> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultCategoryMain> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 136. 강화문제 북마크 문제들
     */

    public void getFavoriteBookmarkQuestion(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<DefaultQuestionListDao> result = service.getFavoriteBookmarkQuestion(jo);

        Callback callback = new Callback<DefaultQuestionListDao>() {

            @Override
            public void onResponse(Call<DefaultQuestionListDao> call, retrofit2.Response<DefaultQuestionListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultQuestionListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 137. FAQ 리스트
     */
    public void getBbsFaqList(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api137GetBbsFaqList> result = service.getBbsFaqList(jo);

        Callback callback = new Callback<Api137GetBbsFaqList>() {

            @Override
            public void onResponse(Call<Api137GetBbsFaqList> call, retrofit2.Response<Api137GetBbsFaqList> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api137GetBbsFaqList> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 138. FAQ 내용보기
     */
    public void getBbsFaqView(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getBbsFaqView(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 139. 구매한 상품권 정보 조회
     */
    public void getPgGoodsInfo(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getPgGoodsInfo(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 140. 이용권 없음
     */
    public void getPGNoRights(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getPGNoRights(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 141.다음에 등록
     */
    public void getPgNextRegist(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getPgNextRegist(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 142. 기출문제 북마크 하기
     */
    public void getFavoriteBookmarkPast(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getFavoriteBookmarkPast(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 143. 강화문제 북마크 카테고리
     */
    public void getFavoriteBookmarkCategoryPast(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api143GetFavoriteBookMarkCategoryPast> result = service.getFavoriteBookmarkCategoryPast(jo);

        Callback callback = new Callback<Api143GetFavoriteBookMarkCategoryPast>() {

            @Override
            public void onResponse(Call<Api143GetFavoriteBookMarkCategoryPast> call, retrofit2.Response<Api143GetFavoriteBookMarkCategoryPast> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api143GetFavoriteBookMarkCategoryPast> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 144. 기출문제 북마크 문제들
     */
    public void getFavoriteBookmarkPastList(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<DefaultQuestionListDao> result = service.getFavoriteBookmarkPastList(jo);

        Callback callback = new Callback<DefaultQuestionListDao>() {

            @Override
            public void onResponse(Call<DefaultQuestionListDao> call, retrofit2.Response<DefaultQuestionListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultQuestionListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }



    /**
     * API No. 145. 코드등록 팝업 다시 안띄우기
     */
    public void getConfigSetNeverOpen(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getConfigSetNeverOpen(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 146. 배너공지 리스트
     */
    public void getBbsBannerList(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api146GetBbsBannerList> result = service.getBbsBannerList(jo);

        Callback callback = new Callback<Api146GetBbsBannerList>() {

            @Override
            public void onResponse(Call<Api146GetBbsBannerList> call, retrofit2.Response<Api146GetBbsBannerList> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api146GetBbsBannerList> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 147. 배너공지 내용보기
     */
    public void getBbsBannerView(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getBbsBannerView(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 148. 주문 코드 확인 요청
     */
    public void getBbsInsertCode(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getBbsInsertCode(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }



    /**
     * API No. 149. 사료학습 단원3차 리스트
     */
    public void getHistoricalMainList(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api149GetHistoricalMainList> result = service.getHistoricalMainList(jo);

        Callback callback = new Callback<Api149GetHistoricalMainList>() {

            @Override
            public void onResponse(Call<Api149GetHistoricalMainList> call, retrofit2.Response<Api149GetHistoricalMainList> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api149GetHistoricalMainList> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }


    /**
     * API No. 150. 사료학습 단원 4차리스트
     */
    public void getHistoricalSubList(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api150GetHistoricalSubList> result = service.getHistoricalSubList(jo);

        Callback callback = new Callback<Api150GetHistoricalSubList>() {

            @Override
            public void onResponse(Call<Api150GetHistoricalSubList> call, retrofit2.Response<Api150GetHistoricalSubList> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api150GetHistoricalSubList> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 151. 사료학습 5차의 사료들
     */
    public void getHistoricalHistoricalList(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getHistoricalHistoricalList(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 152.  사료의 해설 확인
     */
    public void getHistoricalExplain(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getHistoricalExplain(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 153.  사료집 북마크하기
     */
    public void getHistoricalBookMark(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getHistoricalBookMark(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 154.  북마크된 사료집
     */
    public void getHistoricalBookMarkList(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getHistoricalBookMarkList(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 155. 사료집 새로 풀기
     */
    public void getHistoricalReset(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getHistoricalReset(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 156.  사료집에 연관된 기출문제
     */
    public void getHistoricalPasttest(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api156GetHistoricalPasttest> result = service.getHistoricalPasttest(jo);

        Callback callback = new Callback<Api156GetHistoricalPasttest>() {

            @Override
            public void onResponse(Call<Api156GetHistoricalPasttest> call, retrofit2.Response<Api156GetHistoricalPasttest> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api156GetHistoricalPasttest> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 157. 사료집 오류 신고
     */
    public void getHistoricalReport(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getHistoricalReport(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 158. 단원별 기출문제 3차카테고리
     */
    public void getPrevioustestMainList(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api158GetPrevioustestMainList> result = service.getPrevioustestMainList(jo);

        Callback callback = new Callback<Api158GetPrevioustestMainList>() {

            @Override
            public void onResponse(Call<Api158GetPrevioustestMainList> call, retrofit2.Response<Api158GetPrevioustestMainList> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api158GetPrevioustestMainList> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 159. 단원별 기출문제 4차카테고리
     */
    public void getPrevioustestSubList(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api159GetPrevioustestSubList> result = service.getPrevioustestSubList(jo);

        Callback callback = new Callback<Api159GetPrevioustestSubList>() {

            @Override
            public void onResponse(Call<Api159GetPrevioustestSubList> call, retrofit2.Response<Api159GetPrevioustestSubList> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api159GetPrevioustestSubList> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 160. 단원별 기출문제 리스트
     */
    public void getPrevioustestGetQuestion(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<DefaultQuestionListDao> result = service.getPrevioustestGetQuestion(jo);

        Callback callback = new Callback<DefaultQuestionListDao>() {

            @Override
            public void onResponse(Call<DefaultQuestionListDao> call, retrofit2.Response<DefaultQuestionListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultQuestionListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 161. 단원별 기출문제 다시풀기
     */
    public void getPrevioustestResetDanwon(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getPrevioustestResetDanwon(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 162. 사료집 연동 강화문제
     */
    public void getHistoricalRelationQuestion(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api74GetSmartNoteQuestion> result = service.getHistoricalRelationQuestion(jo);

        Callback callback = new Callback<Api74GetSmartNoteQuestion>() {

            @Override
            public void onResponse(Call<Api74GetSmartNoteQuestion> call, retrofit2.Response<Api74GetSmartNoteQuestion> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api74GetSmartNoteQuestion> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 163. 사료집 연동 스마트노트
     */
    public void getHistoricalRelationSmartnote(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api77GetPrevioustestSmartnote> result = service.getHistoricalRelationSmartnote(jo);

        Callback callback = new Callback<Api77GetPrevioustestSmartnote>() {

            @Override
            public void onResponse(Call<Api77GetPrevioustestSmartnote> call, retrofit2.Response<Api77GetPrevioustestSmartnote> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api77GetPrevioustestSmartnote> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 164. 기출문제 메인 카테고리
     */
    public void getPrevioustestCategoryMain(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getPrevioustestCategoryMain(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 165. 기출문제 서브 카테고리
     */
    public void getPrevioustestCategorySub(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getPrevioustestCategorySub(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 166. 해당 직렬,년도의 기출문제
     */
    public void getPrevioustestQuestionList(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultQuestionListDao> result = service.getPrevioustestQuestionList(jo);

        Callback callback = new Callback<DefaultQuestionListDao>() {

            @Override
            public void onResponse(Call<DefaultQuestionListDao> call, retrofit2.Response<DefaultQuestionListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultQuestionListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 167. 기출문제에 연동된 사료집
     */
    public void getPrevioustestRelationHistorical(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getPrevioustestRelationHistorical(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 168. 스마트노트에 연동된 사료집
     */
    public void getSmartnoteRelationHistorical(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getSmartnoteRelationHistorical(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 169. 기출문제 북마트 랜덤 10개
     */
    public void getFavoriteBookmarkPastRandom(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultQuestionListDao> result = service.getFavoriteBookmarkPastRandom(jo);

        Callback callback = new Callback<DefaultQuestionListDao>() {

            @Override
            public void onResponse(Call<DefaultQuestionListDao> call, retrofit2.Response<DefaultQuestionListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultQuestionListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 170. 강화문제 북마트 랜덤 10개
     */
    public void getFavoriteBookmarkQuestionRandom(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultQuestionListDao> result = service.getFavoriteBookmarkQuestionRandom(jo);

        Callback callback = new Callback<DefaultQuestionListDao>() {

            @Override
            public void onResponse(Call<DefaultQuestionListDao> call, retrofit2.Response<DefaultQuestionListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultQuestionListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 171. 비교학습 리스트
     */
    public void getCompareList(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getCompareList(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 172. 비교학습 내용보기
     */
    public void getCompareView(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getCompareView(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 173. 비교학습 북마크하기
     */
    public void getCompareBookmark(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getCompareBookmark(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 174. 비교학습 북마크 리스트
     */
    public void getCompareBookmarkList(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getCompareBookmarkList(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 175. 비교학습 빈칸퀴즈
     */
    public void getCompareQuiz(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getCompareQuiz(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 176. 비교학습 연동 스마트노트
     */
    public void getCompareRelationSmartnote(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api75GetSmartNoteQuestion> result = service.getCompareRelationSmartnote(jo);

        Callback callback = new Callback<Api75GetSmartNoteQuestion>() {

            @Override
            public void onResponse(Call<Api75GetSmartNoteQuestion> call, retrofit2.Response<Api75GetSmartNoteQuestion> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api75GetSmartNoteQuestion> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 177. 비교학습 로그
     */
    public void getCompareLogs(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getCompareLogs(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 178. 한국사 정보가져오기
     */

    public void getCompareRunToRunPayInfo(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getCompareRunToRunPayInfo(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 179. 랜덤 OX - 카테고리_메인
     */
    public void getOXRandomCategoryMain(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultCategoryMain> result = service.getOXRandomCategoryMain(jo);

        Callback callback = new Callback<DefaultCategoryMain>() {

            @Override
            public void onResponse(Call<DefaultCategoryMain> call, retrofit2.Response<DefaultCategoryMain> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultCategoryMain> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 180. 랜덤 OX - 카테고리_sub
     */
    public void getOXRandomCategorySub(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultCategorySubList> result = service.getOXRandomCategorySub(jo);

        Callback callback = new Callback<DefaultCategorySubList>() {

            @Override
            public void onResponse(Call<DefaultCategorySubList> call, retrofit2.Response<DefaultCategorySubList> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultCategorySubList> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 181. 랜덤 OX - 단원별 10개
     */
    public void getOXRandomGetQuestion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultOXQuestion> result = service.getOXRandomGetQuestion(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 182. 키워드 - 기출키워드
     */
    public void getKeywordListPasttest(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api51getKeywordList> result = service.getKeywordListPasttest(jo);

        Callback callback = new Callback<Api51getKeywordList>() {

            @Override
            public void onResponse(Call<Api51getKeywordList> call, retrofit2.Response<Api51getKeywordList> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api51getKeywordList> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 183. 키워드 - 기출키워드의 문제들
     */
    public void getKeywordPasttestQuestion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultQuestionListDao> result = service.getKeywordPasttestQuestion(jo);

        Callback callback = new Callback<DefaultQuestionListDao>() {

            @Override
            public void onResponse(Call<DefaultQuestionListDao> call, retrofit2.Response<DefaultQuestionListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultQuestionListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 184. 오답노트 - ox 카테고리(2,3차바로나오게)
     */
    public void getOXNoteCategoryOX(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultCategoryMain> result = service.getOXNoteCategoryOX(jo);

        Callback callback = new Callback<DefaultCategoryMain>() {

            @Override
            public void onResponse(Call<DefaultCategoryMain> call, retrofit2.Response<DefaultCategoryMain> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultCategoryMain> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 185. 오답노트 - 기출 확인 카테고리(2,3차바로나오게)
     */
    public void getOXNoteCategoryPasttestConfirm(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultCategoryMain> result = service.getOXNoteCategoryPasttestConfirm(jo);

        Callback callback = new Callback<DefaultCategoryMain>() {

            @Override
            public void onResponse(Call<DefaultCategoryMain> call, retrofit2.Response<DefaultCategoryMain> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultCategoryMain> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 186. 오답노트 - OX 문제들
     */
    public void getOXNoteOXQuizQuestion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultOXQuestion> result = service.getOXNoteOXQuizQuestion(jo);

        Callback callback = new Callback<DefaultOXQuestion>() {

            @Override
            public void onResponse(Call<DefaultOXQuestion> call, retrofit2.Response<DefaultOXQuestion> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultOXQuestion> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 187. 오답노트 - 기출 확인문제들
     */

    public void getOXNoteConfrimPastTest(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultQuestionListDao> result = service.getOXNoteConfrimPastTest(jo);

        Callback callback = new Callback<DefaultQuestionListDao>() {

            @Override
            public void onResponse(Call<DefaultQuestionListDao> call, retrofit2.Response<DefaultQuestionListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultQuestionListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 188. 강화학습 - 단원 문제들
     */
    public void getCategoryListDanwonQuestion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultQuestionListDao> result = service.getCategoryListDanwonQuestion(jo);

        Callback callback = new Callback<DefaultQuestionListDao>() {

            @Override
            public void onResponse(Call<DefaultQuestionListDao> call, retrofit2.Response<DefaultQuestionListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultQuestionListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 189. 강화학습 - 랜덤 문제들
     */
    public void getCategoryListRandomQuestion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultQuestionListDao> result = service.getCategoryListRandomQuestion(jo);

        Callback callback = new Callback<DefaultQuestionListDao>() {

            @Override
            public void onResponse(Call<DefaultQuestionListDao> call, retrofit2.Response<DefaultQuestionListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultQuestionListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 190. 강화학습 - 랜덤 day list
     */
    public void getCategoryListRandomDay(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultCategorySubList> result = service.getCategoryListRandomDay(jo);

        Callback callback = new Callback<DefaultCategorySubList>() {

            @Override
            public void onResponse(Call<DefaultCategorySubList> call, retrofit2.Response<DefaultCategorySubList> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultCategorySubList> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 191. 오답노트 틀린문제-강화 리스트
     */
    public void getOXNoteCategoryConfrim(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultQuestionListDao> result = service.getOXNoteCategoryConfrim(jo);

        Callback callback = new Callback<DefaultQuestionListDao>() {

            @Override
            public void onResponse(Call<DefaultQuestionListDao> call, retrofit2.Response<DefaultQuestionListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultQuestionListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }



    /**
     * API No. 192. 오답노트 틀린문제- 강화 문제
     */
    public void getOXNoteConfrimQuestion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultQuestionListDao> result = service.getOXNoteConfrimQuestion(jo);

        Callback callback = new Callback<DefaultQuestionListDao>() {

            @Override
            public void onResponse(Call<DefaultQuestionListDao> call, retrofit2.Response<DefaultQuestionListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultQuestionListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 193. 오답노트 틀린 기출문제 삭제
     */
    public void getOXNoteDeleteConfirmPasttest(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getOXNoteDeleteConfirmPasttest(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 193. 표준어-어휘 카테고리
     */

    public void getVocabularyStandardCategoryVoca(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getVocabularyStandardCategoryVoca(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 194. 표준어-문제 카테고리
     */
    public void getVocabularyStandardQuestionCategory(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getVocabularyStandardQuestionCategory(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /*
     * API No. 195. 표준어-어휘 리스트
     */
    public void getVocabularyStandardVocaList(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<WordQuestionDao> result = service.getVocabularyStandardVocaList(jo);

        Callback callback = new Callback<WordQuestionDao>() {

            @Override
            public void onResponse(Call<WordQuestionDao> call, retrofit2.Response<WordQuestionDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<WordQuestionDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 196. 표준어-어휘 답제출
     */
    public void getVocabularyStandardVocaListAnswer(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getVocabularyStandardVocaListAnswer(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 197. 표준어-문제 리스트
     */
    public void getVocabularyStandardQuestionList(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultQuestionListDao> result = service.getVocabularyStandardQuestionList(jo);

        Callback callback = new Callback<DefaultQuestionListDao>() {

            @Override
            public void onResponse(Call<DefaultQuestionListDao> call, retrofit2.Response<DefaultQuestionListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultQuestionListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 198. 표준어-문제 답제출
     */
    public void getVocabularyStandardQuestionAnswer(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getVocabularyStandardQuestionAnswer(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 199. 어휘 - 새로풀기
     */
    public void getVocabularyReset(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getVocabularyReset(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 200.북마크하기
     */
    public void getVocabularyBookmark(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getVocabularyBookmark(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 201. 문제 - 북마크 어휘 리스트
     */
    public void getVocabularyBookmarkVocaList(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<WordQuestionDao> result = service.getVocabularyBookmarkVocaList(jo);

        Callback callback = new Callback<WordQuestionDao>() {

            @Override
            public void onResponse(Call<WordQuestionDao> call, retrofit2.Response<WordQuestionDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<WordQuestionDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 202. 문제 - 북마크 문제 카테고리
     */
    public void getVocabularyBookmarkQuestionCategory(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<WordListDao> result = service.getVocabularyBookmarkQuestionCategory(jo);

        Callback callback = new Callback<WordListDao>() {

            @Override
            public void onResponse(Call<WordListDao> call, retrofit2.Response<WordListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<WordListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 203. 문제 - 북마크 문제 리스트
     */
    public void getVocabularyBookmarkQuestionList(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<DefaultQuestionListDao> result = service.getVocabularyBookmarkQuestionList(jo);

        Callback callback = new Callback<DefaultQuestionListDao>() {

            @Override
            public void onResponse(Call<DefaultQuestionListDao> call, retrofit2.Response<DefaultQuestionListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultQuestionListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 204. 어휘 - 북마크 랜덤 리스트
     */
    public void getVocabularyBookmarkVocaRandom(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<WordQuestionDao> result = service.getVocabularyBookmarkVocaRandom(jo);

        Callback callback = new Callback<WordQuestionDao>() {

            @Override
            public void onResponse(Call<WordQuestionDao> call, retrofit2.Response<WordQuestionDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<WordQuestionDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 205. 문제- 북마크 랜덤 리스트
     */
    public void getVocabularyBookmarkQuestionRandom(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultQuestionListDao> result = service.getVocabularyBookmarkQuestionRandom(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 207. 일일 10문제-강화문제 (국어전용)
     */

    public void getDailyTodayTenKorReinforce(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api206GetDailyTodayTenKor> result = service.getDailyTodayTenKorReinforce(jo);

        Callback callback = new Callback<Api206GetDailyTodayTenKor>() {

            @Override
            public void onResponse(Call<Api206GetDailyTodayTenKor> call, retrofit2.Response<Api206GetDailyTodayTenKor> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api206GetDailyTodayTenKor> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 208. 일일 10문제-OX (국어전용)
     */
    public void getDailyTodayTenKorOX(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api206GetDailyTodayTenKor> result = service.getDailyTodayTenKorOX(jo);

        Callback callback = new Callback<Api206GetDailyTodayTenKor>() {

            @Override
            public void onResponse(Call<Api206GetDailyTodayTenKor> call, retrofit2.Response<Api206GetDailyTodayTenKor> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api206GetDailyTodayTenKor> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 209. 일일 10문제-기출문제 (국어전용)
     */

    public void getDailyTodayTenKorPast(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api206GetDailyTodayTenKor> result = service.getDailyTodayTenKorPast(jo);

        Callback callback = new Callback<Api206GetDailyTodayTenKor>() {

            @Override
            public void onResponse(Call<Api206GetDailyTodayTenKor> call, retrofit2.Response<Api206GetDailyTodayTenKor> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api206GetDailyTodayTenKor> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 211. 오답노트 OX 보카 (국어전용)
     */

    public void getOXNoteOxquizQuestionVoca(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api206GetDailyTodayTenKor> result = service.getOXNoteOxquizQuestionVoca(jo);

        Callback callback = new Callback<Api206GetDailyTodayTenKor>() {

            @Override
            public void onResponse(Call<Api206GetDailyTodayTenKor> call, retrofit2.Response<Api206GetDailyTodayTenKor> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api206GetDailyTodayTenKor> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 193. 어휘학습 - 학습일 리스트
     */

    public void getVocabularyDayList(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<EnglishWordMainListDao> result = service.getVocabularyDayList(jo);

        Callback callback = new Callback<EnglishWordMainListDao>() {

            @Override
            public void onResponse(Call<EnglishWordMainListDao> call, retrofit2.Response<EnglishWordMainListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<EnglishWordMainListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 195. 어휘학습일별 단어들
     */
    public void getVocabularyDayVoca(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<EnglishWordDayListDao> result = service.getVocabularyDayVoca(jo);

        Callback callback = new Callback<EnglishWordDayListDao>() {

            @Override
            public void onResponse(Call<EnglishWordDayListDao> call, retrofit2.Response<EnglishWordDayListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<EnglishWordDayListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

//    /**
//     * API No. 196. 영어 단어 - 북마크하기
//     */
//    public void getVocabulary(JsonObject jo, final NetworkResponse responseListener) {
//        RequestService service = getClient().create(RequestService.class);
//        Call<JsonObject> result = service.getVocabulary(jo);
//
//        Callback callback = new Callback<JsonObject>() {
//
//            @Override
//            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
//                returnResponse(call, response, responseListener);
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                responseListener.onFail(call, t.toString());
//            }
//        };
//        request(result, callback);
//    }

    /**
     * API No. 197. 내 북마크
     */
    public void getVocabularyBookmarkVoca(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<EnglishWordDayListDao> result = service.getVocabularyBookmarkVoca(jo);

        Callback callback = new Callback<EnglishWordDayListDao>() {

            @Override
            public void onResponse(Call<EnglishWordDayListDao> call, retrofit2.Response<EnglishWordDayListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<EnglishWordDayListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 198. 미암기
     */
    public void getVocabularyMemory(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getVocabularyMemory(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 199. 미암기 리스트
     */
    public void getVocabularyMemoryVaca(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getVocabularyMemoryVaca(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }
    /**
     * API No. 200. 어휘학습 퀴즈 리스트
     */
    public void getVocabularyVocaQuiz(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<EnglishWordDayQuizDao> result = service.getVocabularyVocaQuiz(jo);

        Callback callback = new Callback<EnglishWordDayQuizDao>() {

            @Override
            public void onResponse(Call<EnglishWordDayQuizDao> call, retrofit2.Response<EnglishWordDayQuizDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<EnglishWordDayQuizDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 201. 어휘학습 퀴즈 답제출
     */

        public void getVocabularyVocaQuizAnswer(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getVocabularyVocaQuizAnswer(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 202. 어휘학습 퀴즈 답제출
     */

    public void getVocabularyVocaView(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getVocabularyVocaView(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 203. 문법학습 리스트(OX)
     */
    public void getGrammarDayGrammarListOX(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getGrammarDayGrammarListOX(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 204. 문법학습 문제들
     */
    public void getGrammarDayGrammarOX(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<GrammarOXDao> result = service.getGrammarDayGrammarOX(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 205. 문법OX 새로풀기
     */
    public void getGrammarReset(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getGrammarReset(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 206 문법OX 답제출
     */
    public void getGrammarOXAnswer(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getGrammarOXAnswer(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 207.문법OX 북마크
     */
    public void getGrammarBookmark(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getGrammarBookmark(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 208.문법OX 북마크 리스트
     */
//    public void getGrammarBookmarkGrammarOxList(JsonObject jo, final NetworkResponse responseListener) {
//        RequestService service = getClient().create(RequestService.class);
//        Call<GrammarBookmarkOXDao> result = service.getGrammarBookmarkGrammarOxList(jo);
//
//        Callback callback = new Callback<GrammarBookmarkOXDao>() {
//
//            @Override
//            public void onResponse(Call<GrammarBookmarkOXDao> call, retrofit2.Response<GrammarBookmarkOXDao> response) {
//                returnResponse(call, response, responseListener);
//            }
//
//            @Override
//            public void onFailure(Call<GrammarBookmarkOXDao> call, Throwable t) {
//                responseListener.onFail(call, t.toString());
//            }
//        };
//        request(result, callback);
//    }

    /**
     * API No. 209.문법OX 북마크 리스트
     */
    public void getGrammarBookmarkGrammarOXView(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<GrammarOXDao> result = service.getGrammarBookmarkGrammarOXView(jo);

        Callback callback = new Callback<GrammarOXDao>() {

            @Override
            public void onResponse(Call<GrammarOXDao> call, retrofit2.Response<GrammarOXDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<GrammarOXDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 210.문법 OX 북마크 랜덤 보기
     */
    public void getGrammarGrammarOxView(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<GrammarOXDao> result = service.getGrammarGrammarOxView(jo);

        Callback callback = new Callback<GrammarOXDao>() {

            @Override
            public void onResponse(Call<GrammarOXDao> call, retrofit2.Response<GrammarOXDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<GrammarOXDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 211. 문법 문제 리스트
     */
    public void getGrammarGrammarListQuestion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getGrammarGrammarListQuestion(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 212. 문법 문제들
     */
    public void getGrammarGrammarQuestion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<GrammarQuestionDao> result = service.getGrammarGrammarQuestion(jo);

        Callback callback = new Callback<GrammarQuestionDao>() {

            @Override
            public void onResponse(Call<GrammarQuestionDao> call, retrofit2.Response<GrammarQuestionDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<GrammarQuestionDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 213. 문법 문제 새로풀기
     */
    public void getGrammarGrammarResetQuestion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getGrammarGrammarResetQuestion(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 214 문법 문제 북마크 하기
     */
    public void getGrammarGrammarBookmarkQuestion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getGrammarGrammarBookmarkQuestion(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 215 문법 문제 북마크 리스트
     */
    public void getGrammarGrammarBookmarkQuestionList(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getGrammarGrammarBookmarkQuestionList(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 216. 문법 문제 북마크 보기
     */
    public void getGrammarGrammarBookmarkQuestionView(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<GrammarQuestionDao> result = service.getGrammarGrammarBookmarkQuestionView(jo);

        Callback callback = new Callback<GrammarQuestionDao>() {

            @Override
            public void onResponse(Call<GrammarQuestionDao> call, retrofit2.Response<GrammarQuestionDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<GrammarQuestionDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 217. 문법 문제 북마크 보기(랜덤)
     */
    public void getGrammarGrammarQuestionRandom(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<GrammarQuestionDao> result = service.getGrammarGrammarQuestionRandom(jo);

        Callback callback = new Callback<GrammarQuestionDao>() {

            @Override
            public void onResponse(Call<GrammarQuestionDao> call, retrofit2.Response<GrammarQuestionDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<GrammarQuestionDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 218 생활영어 학습 리스트
     */
    public void getEverydayDayList(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<EveryDayPharseListDao> result = service.getEverydayDayList(jo);

        Callback callback = new Callback<EveryDayPharseListDao>() {

            @Override
            public void onResponse(Call<EveryDayPharseListDao> call, retrofit2.Response<EveryDayPharseListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<EveryDayPharseListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 219 생활영어 보기
     */
//    public void getEverydayDayView(JsonObject jo, final NetworkResponse responseListener) {
//        RequestService service = getClient().create(RequestService.class);
//        Call<JsonObject> result = service.getEverydayDayView(jo);
//
//        Callback callback = new Callback<EveryDayPharseListDao>() {
//
//            @Override
//            public void onResponse(Call<EveryDayPharseListDao> call, retrofit2.Response<EveryDayPharseListDao> response) {
//                returnResponse(call, response, responseListener);
//            }
//
//            @Override
//            public void onFailure(Call<EveryDayPharseListDao> call, Throwable t) {
//                responseListener.onFail(call, t.toString());
//            }
//        };
//        request(result, callback);
//    }

    /**
     * API No. 220 생활영어 상세보기
     */
    public void getEverydayViewDetail(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<EveryDayWordDao> result = service.getEverydayViewDetail(jo);

        Callback callback = new Callback<EveryDayWordDao>() {

            @Override
            public void onResponse(Call<EveryDayWordDao> call, retrofit2.Response<EveryDayWordDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<EveryDayWordDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 220 생활영어 북마크 하기
     */
    public void getEverydayDayBookmark(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getEverydayDayBookmark(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 221 생활영어 북마크 리스트
     */

    public void getEverydayBookmarkList(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<EveryDayWordDao> result = service.getEverydayBookmarkList(jo);

        Callback callback = new Callback<EveryDayWordDao>() {

            @Override
            public void onResponse(Call<EveryDayWordDao> call, retrofit2.Response<EveryDayWordDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<EveryDayWordDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }


    /**
     * API No. 222. 생활영어 학습로그
     */
    public void getEverydayStudyLogs(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getEverydayStudyLogs(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 224 생활영어 모아보기
     */
    public void getEverydayTogether(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<EverydayTogether> result = service.getEverydayTogether(jo);

        Callback callback = new Callback<EverydayTogether>() {

            @Override
            public void onResponse(Call<EverydayTogether> call, retrofit2.Response<EverydayTogether> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<EverydayTogether> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 225 생활영어 검색하기
     */
    public void getEverydaySearchEveryday(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<EveryDayWordDao> result = service.getEverydaySearchEveryday(jo);

        Callback callback = new Callback<EveryDayWordDao>() {

            @Override
            public void onResponse(Call<EveryDayWordDao> call, retrofit2.Response<EveryDayWordDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<EveryDayWordDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 226 일일 어휘
     */
    public void getVocabularyVocaDailyQuiz(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<EnglishWordDayQuizDao> result = service.getVocabularyVocaDailyQuiz(jo);

        Callback callback = new Callback<EnglishWordDayQuizDao>() {

            @Override
            public void onResponse(Call<EnglishWordDayQuizDao> call, retrofit2.Response<EnglishWordDayQuizDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<EnglishWordDayQuizDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 227 일일 문법
     */
    public void getGrammarDailyQuiz(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<GrammarOXDao> result = service.getGrammarDailyQuiz(jo);

        Callback callback = new Callback<GrammarOXDao>() {

            @Override
            public void onResponse(Call<GrammarOXDao> call, retrofit2.Response<GrammarOXDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<GrammarOXDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 228 일일 생활영어
     */
    public void getEverydayDailyStudy(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<EverydayDailyStudyDao> result = service.getEverydayDailyStudy(jo);

        Callback callback = new Callback<EverydayDailyStudyDao>() {

            @Override
            public void onResponse(Call<EverydayDailyStudyDao> call, retrofit2.Response<EverydayDailyStudyDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<EverydayDailyStudyDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 231 기본 SD모드 초기 링크값
     */
    public void getSDModeLink(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<EnglishReadingListDao> result = service.getSDModeLink(jo);

        Callback callback = new Callback<EnglishReadingListDao>() {

            @Override
            public void onResponse(Call<EnglishReadingListDao> call, retrofit2.Response<EnglishReadingListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<EnglishReadingListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 232 기본 SD모드
     */
    public void getSDModeBase(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<EnglishReadingBaseDao> result = service.getSDModeBase(jo);

        Callback callback = new Callback<EnglishReadingBaseDao>() {

            @Override
            public void onResponse(Call<EnglishReadingBaseDao> call, retrofit2.Response<EnglishReadingBaseDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<EnglishReadingBaseDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 233 시제
     */
    public void getSDModeTense(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getSDModeTense(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 234 SD버튼 기출
     */
    public void getSDModePastTest(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<SDModePasttestDao> result = service.getSDModePastTest(jo);

        Callback callback = new Callback<SDModePasttestDao>() {

            @Override
            public void onResponse(Call<SDModePasttestDao> call, retrofit2.Response<SDModePasttestDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<SDModePasttestDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 235 기본모드에서 기출모드 불어오기
     */
    public void getSDModeFromPastTest(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<SDModePasttestDao> result = service.getSDModeFromPastTest(jo);

        Callback callback = new Callback<SDModePasttestDao>() {

            @Override
            public void onResponse(Call<SDModePasttestDao> call, retrofit2.Response<SDModePasttestDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<SDModePasttestDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 236 기출 원문보기
     */
    public void getSDModeOrigin(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<EnglishReadingOriginalDao> result = service.getSDModeOrigin(jo);

        Callback callback = new Callback<EnglishReadingOriginalDao>() {

            @Override
            public void onResponse(Call<EnglishReadingOriginalDao> call, retrofit2.Response<EnglishReadingOriginalDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<EnglishReadingOriginalDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 237 어휘 북마크 40개씩 출력하기
     */
    public void getVocabularyBookmarkVocaV2(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<EnglishWordDayListDao> result = service.getVocabularyBookmarkVocaV2(jo);

        Callback callback = new Callback<EnglishWordDayListDao>() {

            @Override
            public void onResponse(Call<EnglishWordDayListDao> call, retrofit2.Response<EnglishWordDayListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<EnglishWordDayListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 238 생활영어 40개씩 출력하기
     */
    public void getEverydayBookmarkListV2(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<EveryDayBookmarkWordDao> result = service.getEverydayBookmarkListV2(jo);

        Callback callback = new Callback<EveryDayBookmarkWordDao>() {

            @Override
            public void onResponse(Call<EveryDayBookmarkWordDao> call, retrofit2.Response<EveryDayBookmarkWordDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<EveryDayBookmarkWordDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 239 어휘학습 단어 검색
     */
    public void getVocabularyWordSearch(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<EnglishWordDayListDao> result = service.getVocabularyWordSearch(jo);

        Callback callback = new Callback<EveryDayBookmarkWordDao>() {

            @Override
            public void onResponse(Call<EveryDayBookmarkWordDao> call, retrofit2.Response<EveryDayBookmarkWordDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<EveryDayBookmarkWordDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 240 검색 자동완성
     */
    public void getVocabularyAutoComplete(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<WordSearchListDao> result = service.getVocabularyAutoComplete(jo);

        Callback callback = new Callback<WordSearchListDao>() {

            @Override
            public void onResponse(Call<WordSearchListDao> call, retrofit2.Response<WordSearchListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<WordSearchListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 241 표제어 팝업 보기
     */
    public void getVocabularyViewVocaPopup(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<EnglishWordDayListDao> result = service.getVocabularyViewVocaPopup(jo);

        Callback callback = new Callback<EnglishWordDayListDao>() {

            @Override
            public void onResponse(Call<EnglishWordDayListDao> call, retrofit2.Response<EnglishWordDayListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<EnglishWordDayListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 242 OX 북마크 하기
     */
    public void getFavoriteBookmarkOX(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getFavoriteBookmarkOX(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 194 북마크 ox 카테고리
     */
    public void getFavoriteBookmarkOXCategory(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultCategoryMain> result = service.getFavoriteBookmarkOXCategory(jo);

        Callback callback = new Callback<DefaultCategoryMain>() {

            @Override
            public void onResponse(Call<DefaultCategoryMain> call, retrofit2.Response<DefaultCategoryMain> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultCategoryMain> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }


    /**
     * API No. 195 북마크 ox 문제들
     */
    public void getFavoriteBookmarkOXQuestion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultOXQuestion> result = service.getFavoriteBookmarkOXQuestion(jo);

        Callback callback = new Callback<DefaultOXQuestion>() {

            @Override
            public void onResponse(Call<DefaultOXQuestion> call, retrofit2.Response<DefaultOXQuestion> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultOXQuestion> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 196 북마크 카테고리 서브
     */
    public void getFavoriteBookmarkOXCategorySub(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultCategorySubList> result = service.getFavoriteBookmarkOXCategorySub(jo);

        Callback callback = new Callback<DefaultCategorySubList>() {

            @Override
            public void onResponse(Call<DefaultCategorySubList> call, retrofit2.Response<DefaultCategorySubList> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultCategorySubList> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 214 선지저장하기
     */
    public void getFavoriteSaveSunji(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getFavoriteSaveSunji(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 215 저장된 선지 리스트(100개 페이징)
     */
    public void getFavoriteViewSunjiList(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<SunjiSaveListDao> result = service.getFavoriteViewSunjiList(jo);

        Callback callback = new Callback<SunjiSaveListDao>() {

            @Override
            public void onResponse(Call<SunjiSaveListDao> call, retrofit2.Response<SunjiSaveListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<SunjiSaveListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 216 문제에 저장된 선지와 메모정보
     */
    public void getFavoriteSunjiInfo(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<GetSunjiSaveDao> result = service.getFavoriteSunjiInfo(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 217 저장된 선지의 문제 보기
     */
    public void getFavoriteSunjiQuestion(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api110GetDownloadTodayTen> result = service.getFavoriteSunjiQuestion(jo);

        Callback callback = new Callback<Api110GetDownloadTodayTen>() {

            @Override
            public void onResponse(Call<Api110GetDownloadTodayTen> call, retrofit2.Response<Api110GetDownloadTodayTen> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api110GetDownloadTodayTen> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 218 맞춤학습 - 기출, 강화, OX
     */
    public void getPersonalQuestions(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api110GetDownloadTodayTen> result = service.getPersonalQuestions(jo);

        Callback callback = new Callback<Api110GetDownloadTodayTen>() {

            @Override
            public void onResponse(Call<Api110GetDownloadTodayTen> call, retrofit2.Response<Api110GetDownloadTodayTen> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api110GetDownloadTodayTen> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 222 에러로그
     */
    public void getLogsSaveLogs(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getLogsSaveLogs(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 222. 고객문의 리스트
     */

    public void getBbsCustomerList(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<InquiryDao> result = service.getBbsCustomerList(jo);

        Callback callback = new Callback<InquiryDao>() {

            @Override
            public void onResponse(Call<InquiryDao> call, retrofit2.Response<InquiryDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<InquiryDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 223. 고객문의 글쓰기
     */
    public void getBbsCustomerInsert(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getBbsCustomerInsert(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 202 기출검색
     */
    public void getSearchPast(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<CombinSearchPastListDao> result = service.getSearchPast(jo);

        Callback callback = new Callback<DefaultCategoryMain>() {

            @Override
            public void onResponse(Call<DefaultCategoryMain> call, retrofit2.Response<DefaultCategoryMain> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultCategoryMain> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 203 노트검색
     */
    public void getSearchNote(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<CombinSearchPastListDao> result = service.getSearchNote(jo);

        Callback callback = new Callback<DefaultCategoryMain>() {

            @Override
            public void onResponse(Call<DefaultCategoryMain> call, retrofit2.Response<DefaultCategoryMain> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultCategoryMain> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 204 강화검색
     */
    public void getSearchReinforce(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<CombinSearchPastListDao> result = service.getSearchReinforce(jo);

        Callback callback = new Callback<DefaultCategoryMain>() {

            @Override
            public void onResponse(Call<DefaultCategoryMain> call, retrofit2.Response<DefaultCategoryMain> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultCategoryMain> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 205 사료검색
     */
    public void getSearchHistorical(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<CombinSearchPastListDao> result = service.getSearchHistorical(jo);

        Callback callback = new Callback<DefaultCategoryMain>() {

            @Override
            public void onResponse(Call<DefaultCategoryMain> call, retrofit2.Response<DefaultCategoryMain> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultCategoryMain> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 206 기출검색 문제 보기
     */
    public void getSearchPastView(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api160GetPrevioustestGetQuestion> result = service.getSearchPastView(jo);

        Callback callback = new Callback<Api160GetPrevioustestGetQuestion>() {

            @Override
            public void onResponse(Call<Api160GetPrevioustestGetQuestion> call, retrofit2.Response<Api160GetPrevioustestGetQuestion> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api160GetPrevioustestGetQuestion> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 207 노트검색 결과 보기
     */
    public void getSearchNoteView(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<Api80GetSmartNoteCate3All> result = service.getSearchNoteView(jo);

        Callback callback = new Callback<Api80GetSmartNoteCate3All>() {

            @Override
            public void onResponse(Call<Api80GetSmartNoteCate3All> call, retrofit2.Response<Api80GetSmartNoteCate3All> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api80GetSmartNoteCate3All> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 207 노트검색 결과 보기
     */
    public void getSearchReinforceView(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultQuestionListDao> result = service.getSearchReinforceView(jo);

        Callback callback = new Callback<DefaultQuestionListDao>() {

            @Override
            public void onResponse(Call<DefaultQuestionListDao> call, retrofit2.Response<DefaultQuestionListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultQuestionListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 209 사료검색 결과 보기
     */
    public void getSearchHistoricalView(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getSearchHistoricalView(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 255 단어 맞춤 학습
     */
    public void getVocabularyVocaMyStudy(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<EnglishWordFitRandomDao> result = service.getVocabularyVocaMyStudy(jo);

        Callback callback = new Callback<EnglishWordFitRandomDao>() {

            @Override
            public void onResponse(Call<EnglishWordFitRandomDao> call, retrofit2.Response<EnglishWordFitRandomDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<EnglishWordFitRandomDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 256. 내 북마크 100개씩 가져오기
     */
    public void getVocabularyBookmarkVocaListPaging(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<WordBookmarkQuestionDao> result = service.getVocabularyBookmarkVocaListPaging(jo);

        Callback callback = new Callback<WordBookmarkQuestionDao>() {

            @Override
            public void onResponse(Call<WordBookmarkQuestionDao> call, retrofit2.Response<WordBookmarkQuestionDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<WordBookmarkQuestionDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 224. 저장된 선지 삭제
     */
    public void getFavoriteDeleteSunji(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getFavoriteDeleteSunji(jo);

        Callback callback = new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API API No. 225.출석부 해당월
     *
     * @param jo
     * @param responseListener
     */
    public void getEventAttendanceMonth(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api85GetEventAttendance> result = service.getEventAttendanceMonth(jo);

        Callback callback = new Callback<Api85GetEventAttendance>() {
            @Override
            public void onResponse(Call<Api85GetEventAttendance> call, retrofit2.Response<Api85GetEventAttendance> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api85GetEventAttendance> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 301. 앱 안내사항 리스트
     */
    public void getBbsAppIntroList(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<Api123GetBbsNoticeList> result = service.getBbsAppIntroList(jo);

        Callback callback = new Callback<Api85GetEventAttendance>() {
            @Override
            public void onResponse(Call<Api85GetEventAttendance> call, retrofit2.Response<Api85GetEventAttendance> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api85GetEventAttendance> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 302. 앱안내사항 내용보기
     */
    public void getBbsAppIntroView(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getBbsAppIntroView(jo);

        Callback callback = new Callback<Api85GetEventAttendance>() {
            @Override
            public void onResponse(Call<Api85GetEventAttendance> call, retrofit2.Response<Api85GetEventAttendance> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<Api85GetEventAttendance> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 303. 결제로그
     */
    public void getPgBuyBefore(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getPgBuyBefore(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 310. 스터디룸 만들기
     */
    public void getStudySaveRoom(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getStudySaveRoom(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 311. 스터디룸 리스트
     */
    public void getStudyListRoom(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<StudyGroupListDao> result = service.getStudyListRoom(jo);

        Callback callback = new Callback<StudyGroupListDao>() {
            @Override
            public void onResponse(Call<StudyGroupListDao> call, retrofit2.Response<StudyGroupListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<StudyGroupListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 312. 닉네임 변경하기
     */
    public void getMemberChageNickName(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getMemberChageNickName(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };

        request(result, callback);
    }

    /**
     * API No. 313 스터디그룹 승인요청
     */
    public void getStudyJoinRoom(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getStudyJoinRoom(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 314 스터디그룹 수정하기
     */
    public void getStudyUpdateRoom(JsonObject jo, final NetworkResponse responseListener) {

        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getStudyUpdateRoom(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 315 스터디그룹 폐쇄하기
     */
    public void getStudyCloseRoom(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getStudyCloseRoom(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 316 스터디그룹 신청자리스트
     */
    public void getStudyRequestList(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<RequestMemberDao> result = service.getStudyRequestList(jo);

        Callback callback = new Callback<RequestMemberDao>() {
            @Override
            public void onResponse(Call<RequestMemberDao> call, retrofit2.Response<RequestMemberDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<RequestMemberDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 317. 스터디그룹 가입 승인하기
     */
    public void getStudyRequestConfirm(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getStudyRequestConfirm(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 318. 스터디그룹 출석부 학습완료 상태
     */
    public void getStudyRequestMyRoom(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getStudyRequestMyRoom(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 319. 스터디그룹 출석부 통계
     */
    public void getStudyRequestStudyStatistic(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<StudyGroupStateDao> result = service.getStudyRequestStudyStatistic(jo);

        Callback callback = new Callback<StudyGroupStateDao>() {
            @Override
            public void onResponse(Call<StudyGroupStateDao> call, retrofit2.Response<StudyGroupStateDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<StudyGroupStateDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 320. 스터디그룹 게시판 리스트
     */
    public void getStudyBbsList(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<StudyGroupBoardDao> result = service.getStudyBbsList(jo);

        Callback callback = new Callback<StudyGroupBoardDao>() {
            @Override
            public void onResponse(Call<StudyGroupBoardDao> call, retrofit2.Response<StudyGroupBoardDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<StudyGroupBoardDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 321. 스터디그룹 게시판 글쓰기
     */
    public void getStudyBbsWrite(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getStudyBbsWrite(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 322. 스터디그룹 탈퇴하기
     */
    public void getStudyRoomWithdraw(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getStudyRoomWithdraw(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 400. 기출어휘 카테고리
     */
    public void getVocabularyPastVocaCategory(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getVocabularyPastVocaCategory(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 401. 기출어휘 리스트 40개씩
     */
    public void getVocabularyPastVocaList(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getVocabularyPastVocaList(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 402. 런투런위드 기본정보
     */
    public void getStudyRoomInfo(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<StudyGroupRoomInfoDao> result = service.getStudyRoomInfo(jo);

        Callback callback = new Callback<StudyGroupRoomInfoDao>() {
            @Override
            public void onResponse(Call<StudyGroupRoomInfoDao> call, retrofit2.Response<StudyGroupRoomInfoDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<StudyGroupRoomInfoDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 403. 기출어휘 퀴즈 40개씩
     */
    public void getVocabularyPastVocaQuiz(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getVocabularyPastVocaQuiz(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 404. 어휘 난이도 카테고리
     */
    public void getVocabularyVocaLevelCategory(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getVocabularyVocaLevelCategory(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 404. 어휘 난이도 리스트
     */
    public void getVocabularyVocaLevelList(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getVocabularyVocaLevelList(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 407. 스터디그룹 게시판 글삭제
     */
    public void getStudyBbsDelete(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getStudyBbsDelete(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 408. 런투런위드 대화방 리스트
     */
    public void getStudytalkList(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<StudyGroupTalkDao> result = service.getStudytalkList(jo);

        Callback callback = new Callback<StudyGroupTalkDao>() {
            @Override
            public void onResponse(Call<StudyGroupTalkDao> call, retrofit2.Response<StudyGroupTalkDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<StudyGroupTalkDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 409. 런투런위드 대화방 글쓰기
     */
    public void getStudyTalkWrite(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<StudyGroupSendTalkDao> result = service.getStudyTalkWrite(jo);

        Callback callback = new Callback<StudyGroupSendTalkDao>() {
            @Override
            public void onResponse(Call<StudyGroupSendTalkDao> call, retrofit2.Response<StudyGroupSendTalkDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<StudyGroupSendTalkDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 410 런투런위드 게시판 글수정
     */
    public void getStudyBbsUpdate(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getStudyBbsUpdate(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }
    /**
     * API No. 412 런투런위드 회원 강퇴
     */
    public void getStudyRoomOut(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getStudyRoomOut(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 413 런투런위드 회원 차단
     */
    public void getStudyRoomBlock(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getStudyRoomBlock(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 414 런투런위드 대표위드 설정하기
     */
    public void getStudyRoomRepresent(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<JsonObject> result = service.getStudyRoomRepresent(jo);

        Callback callback = new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 415. 런투런위드 위드 요약 정보
     */
    public void getStudyWithInfo(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<withBottomDialogData> result = service.getStudyWithInfo(jo);

        Callback callback = new Callback<withBottomDialogData>() {
            @Override
            public void onResponse(Call<withBottomDialogData> call, retrofit2.Response<withBottomDialogData> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<withBottomDialogData> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 416. 위드톡 기출문제 가져오기
     */
    public void getStudyWithPast(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<DefaultQuestionListDao> result = service.getStudyWithPast(jo);

        Callback callback = new Callback<DefaultQuestionListDao>() {
            @Override
            public void onResponse(Call<DefaultQuestionListDao> call, retrofit2.Response<DefaultQuestionListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<DefaultQuestionListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }

    /**
     * API No. 417. 내가 가입한 위드 목록과 대표위드
     */
    public void getStudyMyWithList(JsonObject jo, final NetworkResponse responseListener) {
        RequestService service = getClient().create(RequestService.class);
        Call<StudyGroupMyWithListDao> result = service.getStudyMyWithList(jo);

        Callback callback = new Callback<StudyGroupMyWithListDao>() {
            @Override
            public void onResponse(Call<StudyGroupMyWithListDao> call, retrofit2.Response<StudyGroupMyWithListDao> response) {
                returnResponse(call, response, responseListener);
            }

            @Override
            public void onFailure(Call<StudyGroupMyWithListDao> call, Throwable t) {
                responseListener.onFail(call, t.toString());
            }
        };
        request(result, callback);
    }
    private boolean isRequestRefreshAuth = false;

    private synchronized void returnResponse(Call call, retrofit2.Response response, NetworkResponse responseListener) {
        LogUtil.e(call.request().url() + " // response.code() : " + response.code());

        if (response.isSuccessful()) {
            requestMap.remove(call);
            if (response.body() instanceof JSONApiObject) {
                JSONApiObject body = (JSONApiObject) response.body();
                if (response.body() == null) {
                    if (response.errorBody() == null) {
                        responseListener.onSuccess(call, null);
                    } else {
                        try {
                            responseListener.onFail(call, new String(response.errorBody().bytes()));
                        } catch (IOException e) {
                            responseListener.onFail(call, "Not found Error body");
                        }
                    }
                } else {
                    if (body.getData().size() == 1) {
                        responseListener.onSuccess(call, body.getData(0));
                    } else {
                        responseListener.onSuccess(call, body.getData());
                    }
                }
            } else if (response.body() instanceof JSONApiObjectPost) {

                JSONApiObjectPost post = (JSONApiObjectPost) response.body();
                responseListener.onSuccess(call, post.getData());

            } else {
                responseListener.onSuccess(call, response.body());
            }
        }


        if (isRequestRefreshAuth) {
            return;
        }

        if (response.code() == 400) {
            responseListener.onFail(call, CONTEXT.getString(R.string.server_error_default_msg));
            return;
        }
        if (response.code() == 422 || response.code() == 401) {
            isRequestRefreshAuth = true;
//            allRequestCancel();
            return;
        }

        if (response.code() > 220) {
            try {
                JSONObject obj = new JSONObject(new String(response.errorBody().bytes()));
                JSONArray array = (JSONArray) obj.get("errors");
                JSONObject obj2 = (JSONObject) array.get(0);
                responseListener.onFail(call, "서버에 일시적인 오류가 생겼습니다. 잠시 후 다시 시도 해주세요. (Code(" + response.code() + "), MSG : " + obj2.get("detail") + ")");
            } catch (IOException e) {
                LogUtil.e(e.toString());
                responseListener.onFail(call, "서버에 일시적인 오류가 생겼습니다. 잠시 후 다시 시도 해주세요. (Code : " + response.code() + ")");
            } catch (JSONException e) {
                LogUtil.e(e.toString());
                responseListener.onFail(call, "서버에 일시적인 오류가 생겼습니다. 잠시 후 다시 시도 해주세요. (MSG : " + response.code() + ")");
            }
            return;
        }
    }


    interface RequestService {

        final String CONTENT_TYPE = "Content-Type: application/json";

        /**
         * API No. 1 임시 아이디 발급
         */
        @POST("member/regist")
        Call<JsonObject> getTempID(@Body JsonObject getTempID);

        /**
         * API No. 2 학습 리스트 가져오기
         * 53번으로 변경
         * @POST("category/list")
         * Call<DefaultCategoryMain> getStudyList(@Body JsonObject studyList);
         */

        /** API 3,4번은 사용하지 않고, 대신 API 2번으로 가져온 리스트의 문제를 가져오는 통합 API인 11번을 사용함. */
        /**
         * API No. 3 선택된 단원의 날짜별 학습 리스트 가져오기
         */
        @POST("category/day")
        Call<JsonObject> getDayStudyList(@Body JsonObject dayStudyList);

        /**
         * API No. 4 문제 가져오기
         */
        @POST("question/paper")
        Call<JsonObject> getDayPapers(@Body JsonObject dayPaper);

        /**
         * API No. 5 정답 제출하기
         */
        @POST("question/answer")
        Call<JsonObject> sendAnswer(@Body JsonObject sendAnswer);

        /**
         * API No. 6 복습문제 가져오기 /
         */
        @POST("question/review")
        Call<ApiGetReviewAndRefineQuestion> getReviewQuestion(@Body JsonObject getReviewQuestion);

        /**
         * API No. 7 보강문제 가져오기
         */
        @POST("question/refine")
        Call<ApiGetReviewAndRefineQuestion> getRefineQuestion(@Body JsonObject getRefineQuestion);

        /**
         * API No. 8 복습문제 정답제출
         */
        @POST("question/reviewanswer")
        Call<JsonObject> sendReviewAnswer(@Body JsonObject sendReviewAnswer);

        /**
         * API No. 9 복습plus(보강문제) 정답제출
         */
        @POST("question/refineanswer")
        Call<JsonObject> sendRefineAnswer(@Body JsonObject sendRefineAnswer);

        /**
         * API No. 10 기출문제 가져오기
         */
        @POST("question/pasttest")
        Call<DefaultQuestionListDao> getPastTest(@Body JsonObject getPastTest);

        /**
         * API No. 11 기출문제 가져오기
         * 2018/2/7 API No. 11 확인학습 랜덤풀이 문제 가져오기??
         */
        @POST("download/question")
        Call<Api11GetStudyQuestion> getDownloadQuestion(@Body JsonObject getDownloadQuestion);

        /**
         * API No. 12 확인학습 새로 풀기
         */
        @POST("question/reset")
        Call<JsonObject> setQuestionReset(@Body JsonObject setQuestionReset);

        /**
         * API No. 13 오답노트 확인
         */
        @POST("oxnote/confirm")
        Call<DefaultCategoryMain> getOxnoteConfirm(@Body JsonObject getOxnoteConfirm);

        /** API 14번 사용하지 않음 */

        /**
         * API No. 15 문제풀이 카테고리 가져오기
         */
        @POST("oxnote/explain")
        Call<JsonObject> getOxnoteExplainCategory(@Body JsonObject getOxnoteExplainCategory);

        /**
         * API No. 16 기출문제 카테고리 가져오기
         */
        @POST("previoustest/category")
        Call<Api16GetPreTestCategory> getPreviousCategory(@Body JsonObject getPreviousCategory);

        /**
         * API No. 17 기출문제 직렬별 다운받기
         */
        @POST("download/pasttest")
        Call<Api17GetPreTestQuestion> getPreviousQuestion(@Body JsonObject getPreviousQuestion);

        /**
         * API No. 18 로그인
         */
        @POST("member/login")
        Call<JsonObject> memberLogin(@Body JsonObject setMemberJoin);

        /**
         * API No. 19 회원가입
         */
        @POST("member/join")
        Call<JsonObject> memberJoin(@Body JsonObject setMemberJoin);

        /**
         * API No. 20 비밀번호 찾기 (임시비밀번호)
         */
        @POST("member/findpw")
        Call<JsonObject> memberFindPw(@Body JsonObject setMemberFindPw);

        /**
         * API No. 21 비밀번호 변경
         */
        @POST("member/updatepw")
        Call<JsonObject> memberUpdatePw(@Body JsonObject setMemberFindPw);

        /**
         * API No. 22 오답노트 초기화
         */
        @POST("member/reset")
        Call<JsonObject> memberReset(@Body JsonObject setMemberFindPw);

        /**
         * API No. 23 오답노트 확인(다운로드)
         */
        @POST("download/confirm")
        Call<Api23GetDownloadConfirm> getDownloadConfirm(@Body JsonObject getDownloadConfirm);

        /**
         * API No. 24 오답노트 OX(다운로드)
         */
        @POST("download/oxquiz")
        Call<Api24GetDownloadOxQuiz> getDownloadOXQuiz(@Body JsonObject getDownloadOXQuiz);

        /**
         * API No. 25 오답노트 풀이(다운로드)
         */
        @POST("download/explain")
        Call<Api25GetDownloadExplain> getDownloadExplain(@Body JsonObject getDownloadExplain);

        /**
         * API No. 26 복습문제 다운로드 (기본 문제 풀이 완료 후)
         */
        @POST("download/review")
        Call<Api26GetDownloadReview> getDownloadReview(@Body JsonObject getDownloadReview);

        /**
         * API No. 27 복습plus문제 다운로드 (복습 문제 풀이 완료 후)
         */
        @POST("download/reviewplus")
        Call<Api27GetDownloadReviewPlus> getDownloadReviewPlus(@Body JsonObject getDownloadReviewPlus);

        /**
         * API No. 28 확인학습 새로 풀기 선택 시 해당 날짜 문제풀이 이력 리셋
         */
        @POST("question/resetday")
        Call<JsonObject> resetDayStudyHistory(@Body JsonObject resetDayStudyHistory);

        /**
         * API No. 29 아이디 중복 확인
         */
        @POST("member/duplicateid")
        Call<JsonObject> memberEmailCheck(@Body JsonObject memberEmailCheck);

        /**
         * API No. 30 오답노트 OX 별표 해제하고 페이지 나갈 때 삭제
         */
        @POST("oxnote/deleteox")
        Call<JsonObject> deleteOXQuiz(@Body JsonObject deleteOXQuiz);

        /**
         * API No. 31 오답노트 확인 별표 해제하고 페이지 나갈 때 삭제
         */
        @POST("oxnote/deleteconfirm")
        Call<JsonObject> deleteConfirmQuestion(@Body JsonObject deleteConfirmQuestion);

        /**
         * API No. 32 오답노트 풀이 별표 해제하고 페이지 나갈 때 삭제
         */
        @POST("oxnote/deleteexplain")
        Call<JsonObject> deleteExplainQuestion(@Body JsonObject deleteExplainQuestion);

        /**
         * API No. 33 해당 학습 day 문제 버전 체크하기
         */
//        @Headers(CONTENT_TYPE)
        @POST("category/daycheck")
        Call<JsonObject> checkDayStudyVersion(@Body JsonObject param);

        /**
         * API No. 34 해당 학습 day 문제 다운로드
         */
        @POST("download/question_day")
        Call<OneDayStudyData> getNewDayStudyQuestion(@Body JsonObject param);

        /**
         * API No. 35 복습버튼 정답 제출
         */
        @POST("question/answer_review")
        Call<JsonObject> sendReviewButtonAnswer(@Body JsonObject param);

        /**
         * API No. 36 보강버튼 정답 제출
         */
        @POST("question/answer_refine")
        Call<JsonObject> sendRefineButtonAnswer(@Body JsonObject param);

        /**
         * API No. 37 기출버튼 정답 제출
         */
        @POST("question/answer_pasttest")
        Call<JsonObject> sendPastTestButtonAnswer(@Body JsonObject param);

        /**
         * API No. 38 오답노트 확인 단원 선택 시 로그 전송
         */
        @POST("question/select_ox_category")
        Call<JsonObject> sendSelectOxCategory(@Body JsonObject param);

        /**
         * API No. 39 오답노트 OX 정답 제출
         */
        @POST("question/answer_ox")
        Call<JsonObject> sendOxQuizAnswer(@Body JsonObject param);

        /**
         * API No. 40 오답노트 풀이 정답 제출
         */
        @POST("question/answer_ox_explain")
        Call<JsonObject> sendOxExplainAnswer(@Body JsonObject param);

        /**
         * API No. 41 기출문제 정답 제출
         */
        @POST("question/answer_previous")
        Call<JsonObject> sendPreTestAnswer(@Body JsonObject param);

        /**
         * API No. 42 튜토리얼 다운로드
         */
        @POST("tutorial/question")
        Call<OneDayStudyData> getTutorial(@Body JsonObject param);

        /**
         * API No. 43 튜토리얼 첫페이지 접속 로그
         */
        @POST("tutorial/firstpage")
        Call<JsonObject> sendTutorialFirstPage(@Body JsonObject param);

        /**
         * API No. 44 튜토리얼 마지막 페이지 접속 로그
         */
        @POST("tutorial/lastpage")
        Call<JsonObject> sendTutorialLastPage(@Body JsonObject param);

        /**
         * API No. 45 튜토리얼 각 페이지 접속 로그
         */
        @POST("tutorial/savelog")
        Call<JsonObject> sendTutorialSaveLog(@Body JsonObject param);

        /**
         * API No. 46 공지사항 가져오기
         */
        @POST("config/notice")
        Call<JsonObject> getNotice(@Body JsonObject param);

        /**
         * API No. 47 이벤트 url 가져오기
         */
        @GET("config/event")
        Call<JsonObject> getEventUrl();

        /**
         * API No. 48 설문조사 url 가져오기
         */
        @GET("config/question")
        Call<JsonObject> getSurveyUrl();

        /**
         * API No. 49 환경설정(공지사항, 이벤트, 설문조사) 한 번에 가져오기. 사용하지 않음 (46, 47, 48, 50을 필요한 타이밍에 개별 호출)
         */
        @POST("config/summary")
        Call<JsonObject> getAllConfigData(@Body JsonObject param);

        /**
         * API No. 50 앱버전 가져오기
         */
        @POST("config/version_info")
        Call<JsonObject> getAppVersion(@Body JsonObject param);

        /**
         * API No. 51 키워드 복습(전체)
         */
        @POST("keyword/list")
        Call<Api51getKeywordList> getKeyworList(@Body JsonObject param);

        /**
         * API No. 53 한국사 리스트(확인학습)
         */
        @POST("category/list_default")
        Call<Api2GetStudyList> getStudyList(@Body JsonObject studyList);

        /**
         * API No. 54 한국사 리스트(OX)
         */
        @POST("category/list_ox")
        Call<Api54GetWrongOXListCategory> getOxnoteList(@Body JsonObject studyList);

        /**
         * API No. 55 한국사 리스트(확인:틀린문제 다시보기)
         */
        @POST("category/list_confirm")
        Call<Api55GetWrongListCategory> getConfirmNoteList(@Body JsonObject studyList);

        /**
         * API No. 56 오류 신고
         */
        @POST("bbs/write")
        Call<JsonObject> sendErrorReport(@Body JsonObject param);

        /**
         * API No. 57 팝업 공지
         */
        @POST("config/popup")
        Call<JsonObject> getPopupNotice(@Body JsonObject param);

        /**
         * API No. 58 맞춘 문제 오답노트에 추가
         */
        @POST("question/sendnote")
        Call<JsonObject> sendQuestionToOxNote(@Body JsonObject param);

        /**
         * API No. 59 설정 > 고객문의
         */
        @POST("config/customer")
        Call<JsonObject> getCustomerAskPageUrl();

        /**
         * API No. 60 스마트노트
         */
        @POST("smartnote/sample")
        Call<JsonObject> getSmartNotePageUrl();


        /**
         * API No. 61 단원별 한국사 리스트
         */
        @POST("category/list_danwon")
        Call<DefaultCategoryMain> getCategoryListDanwon(@Body JsonObject danwonStudyList);

        /**
         * API No. 62 단원 서브 리스트
         */
        @POST("category/list_danwon_sub")
        Call<DefaultCategorySubList> getCategoryListDanwonSub(@Body JsonObject param);

        /**
         * API No. 63 단원별 버전 체크
         */
        @POST("category/danwoncheck")
        Call<JsonObject> getCheckDanwonStudyVersion(@Body JsonObject param);

//        /**
//         * API No. 64 해당 단원의 문제만 다시 다운로드
//        */
//        @POST("download/danwon_question_day")
//        Call<OneDanwonStudyData> getNewDanwonStudyQuestion(@Body JsonObject getDownloadDanwonQuestionDay);


        /**
         * API No. 66 단원순서문제 새로 풀기
         */
        @POST("question/reset_danwon")
        Call<JsonObject> getQuestionResetDanwon(@Body JsonObject param);

        /**
         * API No. 67 OX퀴즈 보유 유무 파악
         */
        @POST("category/ox_yn")
        Call<JsonObject> getCategoryOxYN(@Body JsonObject param);

        /**
         * API No. 68 스마트노트-리스트
         */
        @POST("smartnote/list")
        Call<Api68GetSmartNoteList> getSmartNoteList(@Body JsonObject param);

        /**
         * API No. 69 스마트노트-보기
         */
        @POST("smartnote/one")
        Call<Api69GetSmartNoteOne> getSmartNoteOne(@Body JsonObject param);

        /**
         * API No. 70 이전 풀이 문제 카테고리 리스트
         */
        @POST("category/solved_history")
        Call<Api70GetCategorySolvedHistory> getCategorySolvedHistory(@Body JsonObject param);

        /**
         * API No. 71 이전 풀이 문제 단락 다운로드
         */
        @POST("download/soved_history_question")
        Call<Api71GetDownloadSolvedHistoryQuestion> getDownloadSolvedHistoryQuestion(@Body JsonObject param);

        /**
         * API No. 72 스마트노트-카테고리
         */
        @POST("category/smartnote")
        Call<Api72GetCategorySmartNote> getCategorySmartNote(@Body JsonObject param);

        /**
         * API No. 73 스마트노트 - 연관 기출문제
         */
        @POST("smartnote/pasttest")
        Call<DefaultQuestionListDao> getSmartNotePasttest(@Body JsonObject param);

        /**
         * API No. 74 스마트노트 - 연관 강화문제
         */
        @POST("smartnote/question")
        Call<DefaultQuestionListDao> getSmartNoteQuestion(@Body JsonObject param);

        /**
         * API No. 75 스마트노트 - 강화학습에 연결된 스마트노트들
         */
        @POST("smartnote/smartnote_from_question")
        Call<Api75GetSmartNoteQuestion> getSmartNoteSmartNoteFromQuestion(@Body JsonObject param);

        /**
         * API No. 76 기출문제 새로풀기
         */
        @POST("previoustest/reset")
        Call<JsonObject> getPrevioustestReset(@Body JsonObject param);

        /**
         * API No. 77.기출문제에 연동된 스마트노트
         */
        @POST("previoustest/smartnote")
        Call<Api77GetPrevioustestSmartnote> getPrevioustestSmartNote(@Body JsonObject param);

        /**
         * API No. 78.스마트노트 오류신고
         */
        @POST("smartnote/report")
        Call<JsonObject> getSmartNoteReport(@Body JsonObject param);


        /**
         * API No. 79.스마트노트 3개씩 가져오기
         */
        @POST("smartnote/view")
        Call<Api79GetSmartNoteView> getSmartNoteView(@Body JsonObject param);

        /**
         * API No. 80.스마트노트 3개씩 가져오기
         */
        @POST("smartnote/cate3all")
        Call<Api80GetSmartNoteCate3All> getSmartNoteCate3All(@Body JsonObject param);


        /**
         * API No. 81.스마트노트 로그 쌓기
         */
        @POST("smartnote/logs")
        Call<JsonObject> getSmartNoteLogs(@Body JsonObject param);

        /**
         * API No. 83. 키워드의 강화학습 가져오기
         */
        @POST("download/keyword_question")
        Call<Api83GetDownloadKeywordQuestion> getDownloadKeywordQuestion(@Body JsonObject param);

        /**
         * API No. 85. 출석부
         */
        @POST("event/attendance")
        Call<Api85GetEventAttendance> getEventAttendance(@Body JsonObject param);

        /**
         * API No. 86. 스마트노트-메모
         */
        @POST("smartnote/memo")
        Call<JsonObject> getSmartNoteMemo(@Body JsonObject param);

        /**
         * API No. 87. 스마트노트-북마크
         */
        @POST("smartnote/bookmark")
        Call<JsonObject> getSmartBookMark(@Body JsonObject param);

        /**
         * API No. 87.스마트노트 - 메모 모아보기
         */
        @POST("smartnote/memo_all")
        Call<Api88GetSmartNoteBookMemoAll> getSmartNoteBookMemoAll(@Body JsonObject param);

        /**
         * API No. 88. 스마트노트-메모 모아보기
         */
        @POST("smartnote/memo_category")
        Call<Api88GetSmartNoteBookMemoCategory> getSmartNoteBookMemoCategory(@Body JsonObject param);

        /**
         * API No. 91.메인 학습 현황판
         */
        @POST("config/dashboard")
        Call<Api91GetConfigDashBoard> getConfigDashBoard(@Body JsonObject param);

        /**
         * API No. 92. 스마트노트 3차 북마크만 가져오기
         */
        @POST("smartnote/cate3bookmark")
        Call<Api80GetSmartNoteCate3All> getSmartNoteCate3BookMark(@Body JsonObject param);

        /**
         * API No. 93. 강화학습 - 주제별 카테고리 가져오기
         */
        @POST("category/subject")
        Call<Api93GetSubjectList> getSubjectList(@Body JsonObject param);

        /**
         * API No. 94. 강화학습-주제별 문제 가져오기
         */
        @POST("category/subject_question")
        Call<Api94GetSubjectQuestion> getSubjectQuestion(@Body JsonObject param);

        /**
         * API No. 95. 오늘 학습한 내용
         */
        @POST("member/today")
        Call<JsonObject> getMemberToday(@Body JsonObject param);


        /**
         * API No. 96. 이전문제 로그 쌓기
         */
        @POST("question/past_question_logs")
        Call<JsonObject> getQuestionPastQuestionLogs(@Body JsonObject param);


        /**
         * API No. 97.마이페이지
         */
        @POST("config/mypage")
        Call<JsonObject> getConfigMyPage(@Body JsonObject param);

        /**
         * API No. 98.일일5문제
         */
        @POST("download/today_five")
        Call<Api98GetDownloadTodayFive> getDownloadTodayFive(@Body JsonObject param);

        /**
         * API No. 99.일일5문제 정답율
         */
        @POST("download/today_five_rate")
        Call<JsonObject> getDownloadTodayFiveRate(@Body JsonObject param);

        /**
         * API No. 100.기출문제 머문시간
         */
        @POST("previoustest/save_time")
        Call<JsonObject> getPrevioustestSaveTime(@Body JsonObject param);


        /**
         * API No. 101.난이도 문제
         */
        @POST("difficulty/list")
        Call<JsonObject> getDifficultyList(@Body JsonObject param);

        /**
         * API No. 102.토큰전송
         */
        @POST("config/save_token")
        Call<JsonObject> getConfigSaveToken(@Body JsonObject param);

        /**
         * API No. 103.강화시험 대시보드
         */
        @POST("reinforce/dashboard")
        Call<JsonObject> getReinforceDashBoard(@Body JsonObject param);

        /**
         * API No. 104.강화시험 리스트
         */
        @POST("reinforce/list")
        Call<Api104GetReinforceList> getReinforceList(@Body JsonObject param);

        /**
         * API No. 105.강화시험 문제
         */
        @POST("reinforce/question")
        Call<Api105GetReinforceQuestion> getReinforceQuestion(@Body JsonObject param);

        /**
         * API No. 106.푸쉬 동의
         */
        @POST("member/agree")
        Call<JsonObject> getMemberAgree(@Body JsonObject param);

        /**
         * API No. 107. 강화시험 답제출
         */
        @POST("reinforce/answer")
        Call<JsonObject> getReinforceAnswer(@Body JsonObject param);

        /**
         * API No. 108.  강화시험 풀이 후
         */
        @POST("reinforce/exam_after")
        Call<Api108GetReinforceExamAfter> getReinforceExamAfter(@Body JsonObject param);

        /**
         * API No. 110.  일일10문제
         */
        @POST("download/today_ten")
        Call<Api110GetDownloadTodayTen> getDownloadTodayTen(@Body JsonObject param);

        /**
         * API No. 111.일일 10문제 정답율
         */
        @POST("download/today_ten_rate")
        Call<JsonObject> getDownloadTodayTenRate(@Body JsonObject param);

        /**
         * API No. 112.일일스마트노트호출
         */
        @POST("smartnote/daily")
        Call<Api75GetSmartNoteQuestion> getSmartNoteDaily(@Body JsonObject param);

        /**
         * API No. 118.오류현황
         */
        @POST("question/report_status")
        Call<JsonObject> getQuestionReportStaus(@Body JsonObject param);

        /**
         * API No. 119.일일 10문제 - newbie
         */
        @POST("download/today_ten_newbie")
        Call<Api110GetDownloadTodayTen> getDownloadTodayTenNewbie(@Body JsonObject param);

        /**
         * API No. 120.일일 10문제 정답율 - newbie
         */
        @POST("download/today_ten_rate_newbie")
        Call<JsonObject> getDownloadTodayTenRateNewbie(@Body JsonObject param);

        /**
         * API No. 121. 일일 스마트노트 호출 - newbie
         */
        @POST("smartnote/daily_newbie")
        Call<Api75GetSmartNoteQuestion> getSmartNoteDailyNewbie(@Body JsonObject param);

        /**
         * API No. 122. 무료 이벤트 신청
         */
        @POST("event/event_free")
        Call<JsonObject> getEventEventFree(@Body JsonObject param);

        /**
         * API No. 123. 공지사항 리스트
         */
        @POST("bbs/notice_list")
        Call<Api123GetBbsNoticeList> getBbsNoticeList(@Body JsonObject param);

        /**
         * API No. 124. 공지사항 내용보기
         */
        @POST("bbs/notice_view")
        Call<JsonObject> getBbsNoticeView(@Body JsonObject param);


        /**
         * API No. 125. 내가 등록한 오류 리스트
         */
        @POST("config/my_report_list")
        Call<JsonObject> getConfigMyReportList(@Body JsonObject param);

        /**
         * API No. 126. 내가 등록한 오류 상세보기
         */
        @POST("config/my_report_view")
        Call<JsonObject> getConfigMyReportView(@Body JsonObject param);

        /**
         * API No. 127. 주문 결제 정보 저장
         */
        @POST("pg/insert_order")
        Call<JsonObject> getPGInsertOrder(@Body JsonObject param);

        /**
         * API No. 128. 주문 결제 UPDATE
         */
        @POST("pg/update_order")
        Call<JsonObject> getPGUpdateOrder(@Body JsonObject param);

        /**
         * API No. 129. 이용권 구매 YN
         */
        @POST("pg/pay_use_yn")
        Call<JsonObject> getPGPayUseYN(@Body JsonObject param);

        /**
         * API No. 130. 이용권 상품 리스트
         */
        @POST("pg/goods_list")
        Call<JsonObject> getPGGoodsList(@Body JsonObject param);

        /**
         * API No. 131. 이용권 상품 구매 리스트
         */
        @POST("pg/buy_list")
        Call<JsonObject> getPGBuyList(@Body JsonObject param);

        /**
         * API No. 132. 암호화 키 가져오기
         */
        @POST("pg/get_key")
        Call<JsonObject> getPGGetKey(@Body JsonObject param);

        /**
         * API No. 133. 주문번호 직접입력 받기
         */
        @POST("pg/regist_order")
        Call<JsonObject> getPGRegisterOrder(@Body JsonObject param);

        /**
         * API No. 134. 강화문제 즐겨찾기
         */
        @POST("favorite/bookmark")
        Call<JsonObject> getFavoriteBookmark(@Body JsonObject param);

        /**
         * API No. 135. 강화문제 즐겨찾기 리스트
         */
        @POST("favorite/bookmark_category")
        Call<DefaultCategoryMain> getFavoriteBookmarkListDanwon(@Body JsonObject param);

        /**
         * API No. 136. 강화문제 북마크 문제들
         */
        @POST("favorite/bookmark_question")
        Call<DefaultQuestionListDao> getFavoriteBookmarkQuestion(@Body JsonObject param);

        /**
         * API No. 137. FAQ 리스트
         */
        @POST("bbs/faq_list")
        Call<Api137GetBbsFaqList> getBbsFaqList(@Body JsonObject param);

        /**
         * API No. 138. FAQ 내용보기
         */
        @POST("bbs/faq_view")
        Call<JsonObject> getBbsFaqView(@Body JsonObject param);

        /**
         * API No. 139. 구매한 상품권 정보 조회
         */
        @POST("pg/goods_info")
        Call<JsonObject> getPgGoodsInfo(@Body JsonObject param);

        /**
         * API No. 140. 이용권 없음
         */
        @POST("pg/no_rights")
        Call<JsonObject> getPGNoRights(@Body JsonObject param);

        /**
         * API No. 141. 다음에 등록
         */
        @POST("pg/next_regist")
        Call<JsonObject> getPgNextRegist(@Body JsonObject param);

        /**
         * API No. 142. 기출문제 북마크 하기
         */
        @POST("favorite/bookmark_past")
        Call<JsonObject> getFavoriteBookmarkPast(@Body JsonObject param);

        /**
         * API No. 143. 강화문제 북마크 카테고리
         */
        @POST("favorite/bookmark_category_past")
        Call<Api143GetFavoriteBookMarkCategoryPast> getFavoriteBookmarkCategoryPast(@Body JsonObject param);

        /**
         * API No. 144. 기출문제 북마크 문제들
         */
        @POST("favorite/bookmark_past_list")
        Call<DefaultQuestionListDao> getFavoriteBookmarkPastList(@Body JsonObject param);

        /**
         * API No. 145. 코드등록 팝업 다시 안띄우기
         */
        @POST("config/set_never_open")
        Call<JsonObject> getConfigSetNeverOpen(@Body JsonObject param);

        /**
         * API No. 146. 배너공지 리스트
         */
        @POST("bbs/banner_list")
        Call<Api146GetBbsBannerList> getBbsBannerList(@Body JsonObject param);

        /**
         * API No. 147. 배너공지 내용보기
         */
        @POST("bbs/banner_view")
        Call<JsonObject> getBbsBannerView(@Body JsonObject param);

        /**
         * API No. 148. 주문 코드 확인 요청
         */
        @POST("bbs/insert_code")
        Call<JsonObject> getBbsInsertCode(@Body JsonObject param);

        /**
         * API No. 149. 사료학습 단원 3차리스트
         */
        @POST("historical/main_list")
        Call<Api149GetHistoricalMainList> getHistoricalMainList(@Body JsonObject param);

        /**
         * API No. 150. 사료학습 단원 4차리스트
         */
        @POST("historical/sub_list")
        Call<Api150GetHistoricalSubList> getHistoricalSubList(@Body JsonObject param);

        /**
         * API No. 151.  4차카테고리의 사료들
         */
        @POST("historical/historical_list")
        Call<JsonObject> getHistoricalHistoricalList(@Body JsonObject param);

        /**
         * API No. 152. 사료의 해설 확인
         */
        @POST("historical/explain")
        Call<JsonObject> getHistoricalExplain(@Body JsonObject param);

        /**
         * API No. 153. 사료집 북마크하기
         */
        @POST("historical/bookmark")
        Call<JsonObject> getHistoricalBookMark(@Body JsonObject param);

        /**
         * API No. 154. 북마크된 사료집
         */
        @POST("historical/bookmark_list")
        Call<JsonObject> getHistoricalBookMarkList(@Body JsonObject param);

        /**
         * API No. 155. 사료집 새로풀기
         */
        @POST("historical/reset")
        Call<JsonObject> getHistoricalReset(@Body JsonObject param);

        /**
         * API No. 156. 사료집에 연관된 기출문제
         */
        @POST("historical/pasttest")
        Call<Api156GetHistoricalPasttest> getHistoricalPasttest(@Body JsonObject param);

        /**
         * API No. 157. 사료집 오류신고
         */
        @POST("historical/report")
        Call<JsonObject> getHistoricalReport(@Body JsonObject param);

        /**
         * API No. 158. 단원별 기출문제 3차카테고리
         */
        @POST("previoustest/main_list")
        Call<Api158GetPrevioustestMainList> getPrevioustestMainList(@Body JsonObject param);

        /**
         * API No. 159. 단원별 기출문제 4차카테고리
         */
        @POST("previoustest/sub_list")
        Call<Api159GetPrevioustestSubList> getPrevioustestSubList(@Body JsonObject param);

        /**
         * API No. 160. 단원별 기출문제 리스트
         */
        @POST("previoustest/get_question")
        Call<DefaultQuestionListDao> getPrevioustestGetQuestion(@Body JsonObject param);

        /**
         * API No. 161. 단원별 기출문제 다시풀기
         */
        @POST("previoustest/reset_danwon")
        Call<JsonObject> getPrevioustestResetDanwon(@Body JsonObject param);

        /**
         * API No. 162. 사료집 연동 강화문제
         */
        @POST("historical/relation_question")
        Call<Api74GetSmartNoteQuestion> getHistoricalRelationQuestion(@Body JsonObject param);

        /**
         * API No. 163. 사료집 연동 스마트노트
         */
        @POST("historical/relation_smartnote")
        Call<Api77GetPrevioustestSmartnote> getHistoricalRelationSmartnote(@Body JsonObject param);

        /**
         * API No. 164. 기출문제 메인 카테고리
         */
        @POST("previoustest/category_main")
        Call<JsonObject> getPrevioustestCategoryMain(@Body JsonObject param);

        /**
         * API No. 165. 기출문제 서브 카테고리
         */
        @POST("previoustest/category_sub")
        Call<JsonObject> getPrevioustestCategorySub(@Body JsonObject param);

        /**
         * API No. 166. 해당 직렬,년도의 기출문제
         */
        @POST("previoustest/question_list")
        Call<DefaultQuestionListDao> getPrevioustestQuestionList(@Body JsonObject param);

        /**
         * API No. 167. 기출문제에 연동된 사료집
         */
        @POST("previoustest/relation_historical")
        Call<JsonObject> getPrevioustestRelationHistorical(@Body JsonObject param);

        /**
         * API No. 168. 스마트노트에 연동된 사료집
         */
        @POST("smartnote/relation_historical")
        Call<JsonObject> getSmartnoteRelationHistorical(@Body JsonObject param);

        /**
         * API No. 169. 기출문제 북마트 랜덤 10개
         */
        @POST("favorite/bookmark_past_random")
        Call<DefaultQuestionListDao> getFavoriteBookmarkPastRandom(@Body JsonObject param);

        /**
         * API No. 170. 강화문제 북마트 랜덤 10개
         */
        @POST("favorite/bookmark_question_random")
         Call<DefaultQuestionListDao> getFavoriteBookmarkQuestionRandom(@Body JsonObject param);

        /**
         * API No. 171. 비교학습 리스트
         */
        @POST("compare/list")
        Call<JsonObject> getCompareList(@Body JsonObject param);

        /**
         * API No. 172. 비교학습 내용보기
         */
        @POST("compare/view")
        Call<JsonObject> getCompareView(@Body JsonObject param);

        /**
         * API No. 173. 비교학습 북마크하기
         */
        @POST("compare/bookmark")
        Call<JsonObject> getCompareBookmark(@Body JsonObject param);

        /**
         * API No. 174. 비교학습 북마크 리스트
         */
        @POST("compare/bookmark_list")
        Call<JsonObject> getCompareBookmarkList(@Body JsonObject param);

        /**
         * API No. 175. 비교학습 빈칸퀴즈
         */
        @POST("compare/quiz")
        Call<JsonObject> getCompareQuiz(@Body JsonObject param);

        /**
         * API No. 176. 비교학습 연동 스마트노트
         */
        @POST("compare/relation_smartnote")
        Call<Api75GetSmartNoteQuestion> getCompareRelationSmartnote(@Body JsonObject param);

        /**
         * API No. 177. 비교학습 로그
         */
        @POST("compare/logs")
        Call<JsonObject> getCompareLogs(@Body JsonObject param);

        /**
         * API No. 178. 한국사 정보가져오기
         */
        @POST("pg/runtorun_pay_info")
        Call<JsonObject> getCompareRunToRunPayInfo(@Body JsonObject param);

        /**
         * API No. 179. 랜덤 OX - 카테고리_메인
         */
        @POST("oxrandom/category_main")
        Call<DefaultCategoryMain> getOXRandomCategoryMain(@Body JsonObject param);

        /**
         * API No. 180. 랜덤 OX - 카테고리_sub
         */
        @POST("oxrandom/category_sub")
        Call<DefaultCategorySubList> getOXRandomCategorySub(@Body JsonObject param);

        /**
         * API No. 181. 랜덤 OX - 단원별 10개
         */
        @POST("oxrandom/get_question")
        Call<DefaultOXQuestion> getOXRandomGetQuestion(@Body JsonObject param);

        /**
         * API No. 182. 키워드 - 기출키워드
         */
        @POST("keyword/list_pasttest")
        Call<Api51getKeywordList> getKeywordListPasttest(@Body JsonObject param);

        /**
         * API No. 183. 키워드 - 기출키워드의 문제들
         */
        @POST("keyword/keyword_pasttest_question")
        Call<DefaultQuestionListDao> getKeywordPasttestQuestion(@Body JsonObject param);

        /**
         * API No. 184. 오답노트 - ox 카테고리(2,3차바로나오게)
         */
        @POST("oxnote/category_ox")
        Call<DefaultCategoryMain> getOXNoteCategoryOX(@Body JsonObject param);

        /**
         * API No. 185. 오답노트 - 기출 확인 카테고리(2,3차바로나오게)
         */
        @POST("oxnote/category_pasttest_confirm")
        Call<DefaultCategoryMain> getOXNoteCategoryPasttestConfirm(@Body JsonObject param);

        /**
         * API No. 186. 오답노트 - OX 문제들
         */
        @POST("oxnote/oxquiz_question")
        Call<DefaultOXQuestion> getOXNoteOXQuizQuestion(@Body JsonObject param);

        /**
         * API No. 187. 오답노트 - 기출 확인문제들
         */
        @POST("oxnote/confirm_pasttest")
        Call<DefaultQuestionListDao> getOXNoteConfrimPastTest(@Body JsonObject param);

        /**
         * API No. 188. 강화학습 - 단원 문제들
         */
        @POST("category/list_danwon_question")
        Call<DefaultQuestionListDao> getCategoryListDanwonQuestion(@Body JsonObject param);

        /**
         * API No. 189. 강화학습 - 랜덤 문제들
         */
        @POST("category/list_random_question")
        Call<DefaultQuestionListDao> getCategoryListRandomQuestion(@Body JsonObject param);

        /**
         * API No. 190. 강화학습 - 랜덤 day list
         */
        @POST("category/list_random_day")
        Call<DefaultCategorySubList> getCategoryListRandomDay(@Body JsonObject param);

        /**
         * API No. 191. 오답노트 강화 틀린문제 카테고리
         */
        @POST("oxnote/category_confirm")
        Call<DefaultQuestionListDao> getOXNoteCategoryConfrim(@Body JsonObject param);


        /**
         * API No. 192. 오답노트 틀린문제 강화 문제
         */
        @POST("oxnote/confirm_question")
        Call<DefaultQuestionListDao> getOXNoteConfrimQuestion(@Body JsonObject param);

        /**
         * API No. 193. 오답노트 틀린 기출문제 삭제
         */
        @POST("oxnote/deleteconfirm_pasttest")
        Call<JsonObject> getOXNoteDeleteConfirmPasttest(@Body JsonObject param);

        /**
         * API No. 193. 표준어-어휘 카테고리
         */
        @POST("vocabulary/standard_voca_category")
        Call<JsonObject> getVocabularyStandardCategoryVoca(@Body JsonObject param);

        /*
         * API No. 195. 표준어-어휘 리스트
         */
        @POST("vocabulary/standard_voca_list")
        Call<WordQuestionDao> getVocabularyStandardVocaList(@Body JsonObject param);

        /**
         * API No. 196. 표준어-어휘 답제출
         */
        @POST("vocabulary/standard_voca_list_answer")
        Call<JsonObject> getVocabularyStandardVocaListAnswer(@Body JsonObject param);

        /**
         * API No. 194. 표준어-문제 카테고리
         */
        @POST("vocabulary/standard_question_category")
        Call<JsonObject> getVocabularyStandardQuestionCategory(@Body JsonObject param);

        /**
         * API No. 197. 표준어-문제 리스트
         */
        @POST("vocabulary/standard_question_list")
        Call<DefaultQuestionListDao> getVocabularyStandardQuestionList(@Body JsonObject param);

        /**
         * API No. 198. 표준어-문제 답제출
         */
        @POST("vocabulary/standard_question_answer")
        Call<JsonObject> getVocabularyStandardQuestionAnswer(@Body JsonObject param);

        /**
         * API No. 199. 어휘 - 새로풀기
         */
        @POST("vocabulary/reset")
        Call<JsonObject> getVocabularyReset(@Body JsonObject param);

        /**
         * API No. 200.북마크하기
         */
        @POST("vocabulary/bookmark")
        Call<JsonObject> getVocabularyBookmark(@Body JsonObject param);

        /**
         * API No. 201. 문제 - 북마크 어휘 리스트
         */
        @POST("vocabulary/bookmark_voca_list")
        Call<WordQuestionDao> getVocabularyBookmarkVocaList(@Body JsonObject param);

        /**
         * API No. 202. 문제 - 북마크 문제 카테고리
         */
        @POST("vocabulary/bookmark_question_category")
        Call<WordListDao> getVocabularyBookmarkQuestionCategory(@Body JsonObject param);

        /**
         * API No. 203. 문제 - 북마크 문제 리스트
         */
        @POST("vocabulary/bookmark_question_list")
        Call<DefaultQuestionListDao> getVocabularyBookmarkQuestionList(@Body JsonObject param);

        /**
         * API No. 204. 어휘 - 북마크 랜덤 리스트
         */
        @POST("vocabulary/bookmark_voca_random")
        Call<WordQuestionDao> getVocabularyBookmarkVocaRandom(@Body JsonObject param);

        /**
         * API No. 205. 문제- 북마크 랜덤 리스트
         */
        @POST("vocabulary/bookmark_question_random")
        Call<DefaultQuestionListDao> getVocabularyBookmarkQuestionRandom(@Body JsonObject param);

        /**
         * API No. 207. 일일 10문제-강화문제 (국어전용)
         */
        @POST("daily/today_ten_kor_reinforce")
        Call<Api206GetDailyTodayTenKor> getDailyTodayTenKorReinforce(@Body JsonObject param);

        /**
         * API No. 208. 일일 10문제-OX (국어전용)
         */
        @POST("daily/today_ten_kor_ox")
        Call<Api206GetDailyTodayTenKor> getDailyTodayTenKorOX(@Body JsonObject param);

        /**
         * API No. 209. 일일 10문제-기출문제 (국어전용)
         */
        @POST("daily/today_ten_kor_past")
        Call<Api206GetDailyTodayTenKor> getDailyTodayTenKorPast(@Body JsonObject param);

        /**
         * API No. 211. 오답노트 OX 보카 (국어전용)
         */
        @POST("oxnote/oxquiz_question_voca")
        Call<Api206GetDailyTodayTenKor> getOXNoteOxquizQuestionVoca(@Body JsonObject param);

        /**
         * API No. 194. 어휘학습 - 학습일 리스트
         */
        @POST("vocabulary/day_list")
        Call<EnglishWordMainListDao> getVocabularyDayList(@Body JsonObject param);

        /**
         * API No. 195. 어휘학습일별 단어들
         */
        @POST("vocabulary/day_voca")
        Call<EnglishWordDayListDao> getVocabularyDayVoca(@Body JsonObject param);

//        /**
//         * API No. 196. 영어 단어 - 북마크하기
//         */
//        @POST("vocabulary/bookmark")
//        Call<JsonObject> getVocabularyBookmark(@Body JsonObject param);

        /**
         * API No. 197. 내 북마크
         */
        @POST("vocabulary/bookmark_voca")
        Call<EnglishWordDayListDao> getVocabularyBookmarkVoca(@Body JsonObject param);

        /**
         * API No. 198. 미암기
         */
        @POST("vocabulary/memory")
        Call<JsonObject> getVocabularyMemory(@Body JsonObject param);

        /**
         * API No. 199. 미암기 리스트
         */
        @POST("vocabulary/memory_voca")
        Call<JsonObject> getVocabularyMemoryVaca(@Body JsonObject param);

        /**
         * API No. 200. 어휘학습 퀴즈 리스트
         */
        @POST("vocabulary/voca_quiz")
        Call<EnglishWordDayQuizDao> getVocabularyVocaQuiz(@Body JsonObject param);

        /**
         * API No. 201. 어휘학습 퀴즈 답제출
         */
        @POST("vocabulary/voca_quiz_answer")
        Call<JsonObject> getVocabularyVocaQuizAnswer(@Body JsonObject param);

        /**
         * API No. 202. 어휘학습 퀴즈 답제출
         */
        @POST("vocabulary/voca_view")
        Call<JsonObject> getVocabularyVocaView(@Body JsonObject param);

        /**
         * API No. 203. 문법학습 리스트(OX)
         */
        @POST("grammar/grammar_list_ox")
        Call<JsonObject> getGrammarDayGrammarListOX(@Body JsonObject param);

        /**
         * API No. 204. 문법학습 문제들
         */
        @POST("grammar/grammar_ox")
        Call<GrammarOXDao> getGrammarDayGrammarOX(@Body JsonObject param);

        /**
         * API No. 205. 문법OX 새로풀기
         */
        @POST("grammar/grammar_reset")
        Call<JsonObject> getGrammarReset(@Body JsonObject param);

        /**
         * API No. 206 문법OX 답제출
         */
        @POST("grammar/grammar_ox_answer")
        Call<JsonObject> getGrammarOXAnswer(@Body JsonObject param);

        /**
         * API No. 207.문법OX 북마크
         */
        @POST("grammar/bookmark")
        Call<JsonObject> getGrammarBookmark(@Body JsonObject param);

//        /**
//         * API No. 208.문법OX 북마크 리스트
//         */
//        @POST("grammar/bookmark_grammar_ox_list")
//        Call<GrammarBookmarkOXDao> getGrammarBookmarkGrammarOxList(@Body JsonObject param);

        /**
         * API No. 209.문법OX 북마크 리스트
         */
        @POST("grammar/bookmark_grammar_ox_view")
        Call<GrammarOXDao> getGrammarBookmarkGrammarOXView(@Body JsonObject param);

        /**
         * API No. 210.문법 OX 북마크 랜덤 보기
         */
        @POST("grammar/bookmark_grammar_ox_random")
        Call<GrammarOXDao> getGrammarGrammarOxView(@Body JsonObject param);

        /**
         * API No. 211. 문법 문제 리스트
         */
        @POST("grammar/grammar_list_question")
        Call<JsonObject> getGrammarGrammarListQuestion(@Body JsonObject param);

        /**
         * API No. 212. 문법 문제들
         */
        @POST("grammar/grammar_question")
        Call<GrammarQuestionDao> getGrammarGrammarQuestion(@Body JsonObject param);

        /**
         * API No. 213. 문법 문제 새로풀기
         */
        @POST("grammar/grammar_reset_question")
        Call<JsonObject> getGrammarGrammarResetQuestion(@Body JsonObject param);

        /**
         * API No. 214 문법 문제 북마크 하기
         */
        @POST("grammar/grammar_bookmark_question")
        Call<JsonObject> getGrammarGrammarBookmarkQuestion(@Body JsonObject param);

        /**
         * API No. 215 문법 문제 북마크 리스트
         */
        @POST("grammar/grammar_bookmark_question_list")
        Call<JsonObject> getGrammarGrammarBookmarkQuestionList(@Body JsonObject param);

        /**
         * API No. 216. 문법 문제 북마크 보기
         */
        @POST("grammar/grammar_bookmark_question_view")
        Call<GrammarQuestionDao> getGrammarGrammarBookmarkQuestionView(@Body JsonObject param);

        /**
         * API No. 217. 문법 문제 북마크 보기(랜덤)
         */
        @POST("grammar/grammar_bookmark_question_random")
        Call<GrammarQuestionDao> getGrammarGrammarQuestionRandom(@Body JsonObject param);

        /**
         * API No. 218 생활영어 학습 리스트
         */
        @POST("everyday/day_list")
        Call<EveryDayPharseListDao> getEverydayDayList(@Body JsonObject param);

        /**
         * API No. 219 생활영어 보기
         */
//        @POST("everyday/view")
//        Call<JsonObject> getEverydayDayView(@Body JsonObject param);

        /**
         * API No. 220 생활영어 상세보기
         */
        @POST("everyday/view_detail")
        Call<EveryDayWordDao> getEverydayViewDetail(@Body JsonObject param);

        /**
         * API No. 220 생활영어 북마크 하기
         */
        @POST("everyday/bookmark")
        Call<JsonObject> getEverydayDayBookmark(@Body JsonObject param);

        /**
         * API No. 221 생활영어 북마크 리스트
         */
        @POST("everyday/bookmark_list")
        Call<EveryDayWordDao> getEverydayBookmarkList(@Body JsonObject param);

        /**
         * API No. 222. 생활영어 학습로그
         */
        @POST("everyday/study_logs")
        Call<JsonObject> getEverydayStudyLogs(@Body JsonObject param);

        /**
         * API No. 224 생활영어 모아보기
         */
        @POST("everyday/together")
        Call<EverydayTogether> getEverydayTogether(@Body JsonObject param);

        /**
         * API No. 225 생활영어 검색하기
         */
        @POST("everyday/search_everyday")
        Call<EveryDayWordDao> getEverydaySearchEveryday(@Body JsonObject param);

        /**
         * API No. 226 일일 어휘
         */
        @POST("vocabulary/voca_daily_quiz")
        Call<EnglishWordDayQuizDao> getVocabularyVocaDailyQuiz(@Body JsonObject param);

        /**
         * API No. 227 일일 문법
         */
        @POST("grammar/grammar_daily_quiz")
        Call<GrammarOXDao> getGrammarDailyQuiz(@Body JsonObject param);

        /**
         * API No. 228 일일 생활영어
         */
        @POST("everyday/daily_study")
        Call<EverydayDailyStudyDao> getEverydayDailyStudy(@Body JsonObject param);

        /**
         * API No. 231 기본 SD모드 초기 링크값
         */
        @POST("sdmode/sdmode_link")
        Call<EnglishReadingListDao> getSDModeLink(@Body JsonObject param);

        /**
         * API No. 232 기본 SD모드
         */
        @POST("sdmode/sdmode_base")
        Call<EnglishReadingBaseDao> getSDModeBase(@Body JsonObject param);

        /**
         * API No. 233 시제
         */
        @POST("sdmode/sdmonde_tense")
        Call<JsonObject> getSDModeTense(@Body JsonObject param);

        /**
         * API No. 234 SD버튼 기출
         */
        @POST("sdmode/sdmonde_pasttest")
        Call<SDModePasttestDao> getSDModePastTest(@Body JsonObject param);

        /**
         * API No. 235 기본모드에서 기출모드 불어오기
         */
        @POST("sdmode/sdmonde_from_pasttest")
        Call<SDModePasttestDao> getSDModeFromPastTest(@Body JsonObject param);

        /**
         * API No. 236 기출 원문보기
         */
        @POST("sdmode/sdmonde_origin")
        Call<EnglishReadingOriginalDao> getSDModeOrigin(@Body JsonObject param);

        /**
         * API No. 237 어휘 북마크 40개씩 출력하기
         */
        @POST("vocabulary/bookmark_voca_v2")
        Call<EnglishWordDayListDao> getVocabularyBookmarkVocaV2(@Body JsonObject param);

        /**
         * API No. 238 생활영어 40개씩 출력하기
         */
        @POST("everyday/bookmark_list_v2")
        Call<EveryDayBookmarkWordDao> getEverydayBookmarkListV2(@Body JsonObject param);

        /**
         * API No. 239 어휘학습 단어 검색
         */
        @POST("vocabulary/word_search")
        Call<EnglishWordDayListDao> getVocabularyWordSearch(@Body JsonObject param);

        /**
         * API No. 240 검색 자동완성
         */
        @POST("vocabulary/auto_complete")
        Call<WordSearchListDao> getVocabularyAutoComplete(@Body JsonObject param);

        /**
         * API No. 241 표제어 팝업 보기
         */
        @POST("vocabulary/view_voca_popup")
            Call<EnglishWordDayListDao> getVocabularyViewVocaPopup(@Body JsonObject param);

        /**
         * API No. 242 OX 북마크 하기
         */
        @POST("favorite/bookmark_ox")
        Call<JsonObject> getFavoriteBookmarkOX(@Body JsonObject param);

        /**
         * API No. 194 북마크 ox 카테고리
         */
        @POST("favorite/bookmark_ox_category")
        Call<DefaultCategoryMain> getFavoriteBookmarkOXCategory(@Body JsonObject param);

        /**
         * API No. 195 북마크 ox 문제들
         */
        @POST("favorite/bookmark_ox_question")
        Call<DefaultOXQuestion> getFavoriteBookmarkOXQuestion(@Body JsonObject param);

        /**
         * API No. 196 북마크 카테고리 서브
         */
        @POST("favorite/bookmark_ox_category_sub")
        Call<DefaultCategorySubList> getFavoriteBookmarkOXCategorySub(@Body JsonObject param);

        /**
         * API No. 214 선지저장하기
         */
        @POST("favorite/save_sunji")
        Call<JsonObject> getFavoriteSaveSunji(@Body JsonObject param);

        /**
         * API No. 215 저장된 선지 리스트(100개 페이징)
         */
        @POST("favorite/view_sunji_list")
        Call<SunjiSaveListDao> getFavoriteViewSunjiList(@Body JsonObject param);

        /**
         * API No. 216 문제에 저장된 선지와 메모정보
         */
        @POST("favorite/sunji_info")
        Call<GetSunjiSaveDao> getFavoriteSunjiInfo(@Body JsonObject param);

        /**
         * API No. 217 저장된 선지의 문제 보기
         */
        @POST("favorite/sunji_question")
        Call<Api110GetDownloadTodayTen> getFavoriteSunjiQuestion(@Body JsonObject param);

        /**
         * API No. 218 맞춤학습 - 기출, 강화, OX
         */
        @POST("personal/questions")
        Call<Api110GetDownloadTodayTen> getPersonalQuestions(@Body JsonObject param);

        /**
         * API No. 222 에러로그
         */
        @POST("logs/save_logs")
        Call<JsonObject> getLogsSaveLogs(@Body JsonObject param);

        /**
         * API No. 222. 고객문의 리스트
         */
        @POST("bbs/customer_list")
        Call<InquiryDao> getBbsCustomerList(@Body JsonObject param);

        /**
         * API No. 223. 고객문의 글쓰기
         */
        @POST("bbs/customer_insert")
        Call<JsonObject> getBbsCustomerInsert(@Body JsonObject param);

        /**
         * API No. 227 기출검색
         */
        @POST("search/search_past")
        Call<CombinSearchPastListDao> getSearchPast(@Body JsonObject param);

        /**
         * API No. 228 노트검색
         */
        @POST("search/search_note")
        Call<CombinSearchPastListDao> getSearchNote(@Body JsonObject param);

        /**
         * API No. 229 강화검색
         */
        @POST("search/search_reinforce")
        Call<CombinSearchPastListDao> getSearchReinforce(@Body JsonObject param);

        /**
         * API No. 230 사료검색
         */
        @POST("search/search_historical")
        Call<CombinSearchPastListDao> getSearchHistorical(@Body JsonObject param);

        /**
         * API No. 206 기출검색 문제 보기
         */
        @POST("search/search_past_view")
        Call<Api160GetPrevioustestGetQuestion> getSearchPastView(@Body JsonObject param);

        /**
         * API No. 207 노트검색 결과 보기
         */
        @POST("search/search_note_view")
        Call<Api80GetSmartNoteCate3All> getSearchNoteView(@Body JsonObject param);

        /**
         * API No. 208 강화검색 문제 보기
         */
        @POST("search/search_reinforce_view")
        Call<DefaultQuestionListDao> getSearchReinforceView(@Body JsonObject param);

        /**
         * API No. 209 사료검색 결과 보기
         */
        @POST("search/search_historical_view")
        Call<JsonObject> getSearchHistoricalView(@Body JsonObject param);

        /**
         * API No. 255 단어 맞춤 학습
         */
        @POST("vocabulary/voca_my_study")
        Call<EnglishWordFitRandomDao> getVocabularyVocaMyStudy(@Body JsonObject param);

        /**
         * API No. 256. 내 북마크 100개씩 가져오기
         */
        @POST("vocabulary/bookmark_voca_list_paging")
        Call<WordBookmarkQuestionDao> getVocabularyBookmarkVocaListPaging(@Body JsonObject param);

        /**
         * API No. 224. 저장된 선지 삭제
         */
        @POST("favorite/delete_sunji")
        Call<JsonObject> getFavoriteDeleteSunji(@Body JsonObject param);

        /**
         * API No. 출석부 해당월
         */
        @POST("event/attendance_month")
        Call<Api85GetEventAttendance> getEventAttendanceMonth(@Body JsonObject param);

        /**
         * API No. 301. 앱 안내사항 리스트
         */
        @POST("bbs/app_intro_list")
        Call<Api123GetBbsNoticeList> getBbsAppIntroList(@Body JsonObject param);

        /**
         * API No. 302. 앱안내사항 내용보기
         */
        @POST("bbs/app_intro_view")
        Call<JsonObject> getBbsAppIntroView(@Body JsonObject param);

        /**
         * API No. 303. 결제로그
         */
        @POST("pg/buy_before")
        Call<JsonObject> getPgBuyBefore(@Body JsonObject param);

        /**
         * API No. 310. 스터디룸 만들기
         */
        @POST("study/save_room")
        Call<JsonObject> getStudySaveRoom(@Body JsonObject param);

        /**
         * API No. 311. 스터디룸 리스트
         */
        @POST("study/list_room")
        Call<StudyGroupListDao> getStudyListRoom(@Body JsonObject param);

        /**
         * API No. 312. 닉네임 변경하기
         */
        @POST("member/change_nickname")
        Call<JsonObject> getMemberChageNickName(@Body JsonObject param);

        /**
         * API No. 313 스터디그룹 승인요청
         */
        @POST("study/join_room")
        Call<JsonObject> getStudyJoinRoom(@Body JsonObject param);

        /**
         * API No. 314 스터디그룹 수정하기
         */
        @POST("study/update_room")
        Call<JsonObject> getStudyUpdateRoom(@Body JsonObject param);

        /**
         * API No. 315 스터디그룹 폐쇄하기
         */
        @POST("study/close_room")
        Call<JsonObject> getStudyCloseRoom(@Body JsonObject param);

        /**
         * API No. 316 스터디그룹 신청자리스트
         */
        @POST("study/request_list")
        Call<RequestMemberDao> getStudyRequestList(@Body JsonObject param);

        /**
         * API No. 317. 스터디그룹 가입 승인하기
         */
        @POST("study/request_confirm")
        Call<JsonObject> getStudyRequestConfirm(@Body JsonObject param);

        /**
         * API No. 318. 스터디그룹 출석부 학습완료 상태
         */
        @POST("study/my_room")
        Call<JsonObject> getStudyRequestMyRoom(@Body JsonObject param);

        /**
         * API No. 319. 스터디그룹 출석부 통계
         */
        @POST("study/study_statistic")
        Call<StudyGroupStateDao> getStudyRequestStudyStatistic(@Body JsonObject param);

        /**
         * API No. 320. 스터디그룹 게시판 리스트
         */
        @POST("study/bbs_list")
        Call<StudyGroupBoardDao> getStudyBbsList(@Body JsonObject param);

        /**
         * API No. 321. 스터디그룹 게시판 글쓰기
         */
        @POST("study/bbs_write")
        Call<JsonObject> getStudyBbsWrite(@Body JsonObject param);

        /**
         * API No. 322. 스터디그룹 탈퇴하기
         */
        @POST("study/room_withdraw")
        Call<JsonObject> getStudyRoomWithdraw(@Body JsonObject param);

        /**
         * API No. 400. 기출어휘 카테고리
         */
        @POST("vocabulary/past_voca_category")
        Call<JsonObject> getVocabularyPastVocaCategory(@Body JsonObject param);

        /**
         * API No. 401. 기출어휘 리스트 40개씩
         */
        @POST("vocabulary/past_voca_list")
        Call<JsonObject> getVocabularyPastVocaList(@Body JsonObject param);

        /**
         * API No. 402. 런투런위드 기본정보
         */
        @POST("study/room_info")
        Call<StudyGroupRoomInfoDao> getStudyRoomInfo(@Body JsonObject param);

        /**
         * API No. 403. 기출어휘 퀴즈 40개씩
         */
        @POST("vocabulary/past_voca_quiz")
        Call<JsonObject> getVocabularyPastVocaQuiz(@Body JsonObject param);

        /**
         * API No. 404. 어휘 난이도 카테고리
         */
        @POST("vocabulary/voca_level_category")
        Call<JsonObject> getVocabularyVocaLevelCategory(@Body JsonObject param);

        /**
         * API No. 404. 어휘 난이도 리스트
         */
        @POST("vocabulary/voca_level_list")
        Call<JsonObject> getVocabularyVocaLevelList(@Body JsonObject param);

        /**
         * API No. 407. 스터디그룹 게시판 글삭제
         */
        @POST("study/bbs_delete")
        Call<JsonObject> getStudyBbsDelete(@Body JsonObject param);

        /**
         * API No. 408. 런투런위드 대화방 리스트
         */
        @POST("study/talk_list")
        Call<StudyGroupTalkDao> getStudytalkList(@Body JsonObject param);

        /**
         * API No. 409. 런투런위드 대화방 글쓰기
         */
        @POST("study/talk_write")
        Call<StudyGroupSendTalkDao> getStudyTalkWrite(@Body JsonObject param);


        /**
         * API No. 410 런투런위드 게시판 글수정
         */
        @POST("study/bbs_update")
        Call<JsonObject> getStudyBbsUpdate(@Body JsonObject param);

        /**
         * API No. 412 런투런위드 회원 강퇴
         */
        @POST("study/room_out")
        Call<JsonObject> getStudyRoomOut(@Body JsonObject param);

        /**
         * API No. 413 런투런위드 회원 차단
         */
        @POST("study/room_block")
        Call<JsonObject> getStudyRoomBlock(@Body JsonObject param);

        /**
         * API No. 414 런투런위드 대표위드 설정하기
         */
        @POST("study/room_represent")
        Call<JsonObject> getStudyRoomRepresent(@Body JsonObject param);

        /**
         * API No. 415. 런투런위드 위드 요약 정보
         */
        @POST("study/with_info")
        Call<withBottomDialogData> getStudyWithInfo(@Body JsonObject param);

        /**
         * API No. 416. 위드톡 기출문제 가져오기
         */
        @POST("study/with_past")
        Call<DefaultQuestionListDao> getStudyWithPast(@Body JsonObject param);

        /**
         * API No. 417. 내가 가입한 위드 목록과 대표위드
         */
        @POST("study/my_with_list")
        Call<StudyGroupMyWithListDao> getStudyMyWithList(@Body JsonObject param);

    }
}

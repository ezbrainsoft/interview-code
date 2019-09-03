package com.jlesoft.civilservice.koreanhistoryexam9.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.gustavofao.jsonapi.Models.JSONApiObject;
import com.jlesoft.civilservice.koreanhistoryexam9.BuildConfig;
import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.util.JSONApiObjectPost;
import com.jlesoft.civilservice.koreanhistoryexam9.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class Request {
    private HashMap<Call, Callback> requestMap = new HashMap<>();

    public static final String API_URL = "https://openapi.com/v3/rest/";
    private static Context CONTEXT;

    private static HashMap<String, String> HEADERS = new HashMap<String, String>() {
        {
            put("Content-Type", "application/json");
        }
    };

    public void setContext(Context ctx) {
        CONTEXT = ctx;
    }

    private Request() {
    }

    private static class Singleton {
        private static final Request instance = new Request();
    }

    public static Request getInstance() {
        System.out.println("create instance");
        return Request.Singleton.instance;
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
                okhttp3.Request original = chain.request();

                okhttp3.Request.Builder requestBuilder = original.newBuilder();
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

    private void request(Call call, Callback callback) {
        requestMap.put(call, callback);
        if (!isRequestRefreshAuth) {
            call.enqueue(callback);
        }
    }

    /**
     * API No. 주문확인(카드결제)
     *
     * @param jo
     * @param responseListener
     */
    public void getOrdersVerify(JsonObject jo, final NetworkResponse responseListener) {
        Request.RequestService service = getClient().create(Request.RequestService.class);
        Call<JsonObject> result = service.getOrdersVerify(jo);

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
     * API No. 주문확인(안드로이드)
     *
     * @param jo
     * @param responseListener
     */
    public void getOrdersGoogle(JsonObject jo, final NetworkResponse responseListener) {
        Request.RequestService service = getClient().create(Request.RequestService.class);
        Call<JsonObject> result = service.getOrdersGoogle(jo);

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

    private boolean isRequestRefreshAuth = false;

    private synchronized void returnResponse(Call call, retrofit2.Response response, NetworkResponse responseListener) {
        LogUtil.d(call.request().url() + " // response.code() : " + response.code());

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
        if (response.code() == 403) {
            try {
                String responseStr = response.toString();
                LogUtil.d("responseStr : "+responseStr);

                JSONObject obj = new JSONObject(new String(response.errorBody().bytes()));
                boolean data = (boolean) obj.get("data");
                if (!data) {
                    String message = (String) obj.get("message") + " ("+403+")";
                    responseListener.onFail(call, message);
                }
            } catch (IOException e) {
                LogUtil.e("IOE e = "+e.toString());
                responseListener.onFail(call, "서버에 일시적인 오류가 생겼습니다. 잠시 후 다시 시도 해주세요. (Code : " + response.code() + ")");
            } catch (JSONException e) {
                LogUtil.e("JSONE e = "+e.toString());
                responseListener.onFail(call, "서버에 일시적인 오류가 생겼습니다. 잠시 후 다시 시도 해주세요. (MSG : " + response.code() + ")");
            }
            return;
        }

        if (response.code() == 422 || response.code() == 401) {
            isRequestRefreshAuth = true;
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
         * API No. 주문확인(카드결제)
         */
        @POST("orders/verify")
        Call<JsonObject> getOrdersVerify(@Body JsonObject resultObject);

        /**
         * API No. 주문생성(안드로이드)
         */
        @POST("orders/google")
        Call<JsonObject> getOrdersGoogle(@Body JsonObject resultObject);
    }
}

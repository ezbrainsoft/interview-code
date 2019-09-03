package com.jlesoft.civilservice.koreanhistoryexam9.network;

import com.gustavofao.jsonapi.Models.JSONApiObject;

import retrofit2.Call;

public interface NetworkResponse<T> {
    void onSuccess(Call<JSONApiObject> call, T clazz);
    void onFail(Call<JSONApiObject> call, String msg);
}

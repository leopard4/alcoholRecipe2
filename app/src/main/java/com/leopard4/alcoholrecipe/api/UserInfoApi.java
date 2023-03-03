package com.leopard4.alcoholrecipe.api;

import com.leopard4.alcoholrecipe.model.UserInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserInfoApi {
    // 서버에 대한 API 끝점을 정의하는 인터페이스를 만듭니다
    @POST("/user/sendUserInfoData")
    Call<Void> sendUserInfoData(@Body UserInfo userInfo);
}

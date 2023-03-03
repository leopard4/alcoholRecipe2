package com.leopard4.alcoholrecipe.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserInfo  {
//    추가정보
//- 주종
//- 도수
//- 누구와함께
// 서버로 보낼 데이터를 나타내는 데이터 모델 클래스를 정의합니다

    // 여기서 @SerializedName주석은 클래스의 변수 이름을 요청 본문의 해당 JSON 키에 매핑하는 데 사용됩니다.
    @SerializedName("alcoholType")
    private List<String> alcoholType;

    @SerializedName("alcoholDegree")
    private int alcoholDegree;

    @SerializedName("alcoholWith")
    private List<String> alcoholWith;

    public UserInfo() {
    }

    public UserInfo(List<String> alcoholType, int alcoholDegree, List<String> alcoholWith) {
        this.alcoholType = alcoholType;
        this.alcoholDegree = alcoholDegree;
        this.alcoholWith = alcoholWith;
    }

    public List<String> getAlcoholType() {
        return alcoholType;
    }

    public void setAlcoholType(List<String> alcoholType) {
        this.alcoholType = alcoholType;
    }

    public int getAlcoholDegree() {
        return alcoholDegree;
    }

    public void setAlcoholDegree(int alcoholDegree) {
        this.alcoholDegree = alcoholDegree;
    }

    public List<String> getAlcoholWith() {
        return alcoholWith;
    }

    public void setAlcoholWith(List<String> alcoholWith) {
        this.alcoholWith = alcoholWith;
    }
}

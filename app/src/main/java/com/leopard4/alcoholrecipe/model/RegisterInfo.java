package com.leopard4.alcoholrecipe.model;

import java.util.ArrayList;

public class RegisterInfo {
    // 신규회원정보를 담는 클래스
    // 담는 정보의 형태는 다음과같다
    // {
    //     "alcoholDegree": 11,
    //     "alcoholType": ["와인","맥주","소주","양주","기타"],
    //     "alcoholWith": "친구,연인,가족,혼자",
    // }

    private ArrayList<String> alcoholType;
    private String alcoholDegree;
    private String alcoholWith;

    public RegisterInfo() {
    }

    public RegisterInfo(ArrayList<String> alcoholType, String alcoholDegree, String alcoholWith) {
        this.alcoholType = alcoholType;
        this.alcoholDegree = alcoholDegree;
        this.alcoholWith = alcoholWith;
    }


}

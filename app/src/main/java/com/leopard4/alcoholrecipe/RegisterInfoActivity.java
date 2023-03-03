package com.leopard4.alcoholrecipe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.leopard4.alcoholrecipe.api.UserInfoApi;
import com.leopard4.alcoholrecipe.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterInfoActivity extends AppCompatActivity {

    // 체크박스 연결을 위한 변수 셋팅
    CheckBox cbMakgeolli, cbBeer, cbWine, cbFriend, cbLiquor, cbFamily, cbSoju, cbEtc, cbEtc2, cbAlone, cbRectal, cbAcquaintance, cbAgree, cbLover;
    Button btnPass, btnOk;
    TextView txtDosu;
    // seekbar 연결을 위한 변수 셋팅
    SeekBar seekBar;

    int alcoholDegree;
    List<String> alcoholType = new ArrayList<>();
    List<String> alcoholWith = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);

        // 체크박스 연결
        cbMakgeolli = findViewById(R.id.cbMakgeolli);
        cbBeer = findViewById(R.id.cbBeer);
        cbWine = findViewById(R.id.cbWine);
        cbFriend = findViewById(R.id.cbFriend);
        cbLiquor = findViewById(R.id.cbLiquor);
        cbFamily = findViewById(R.id.cbFamily);
        cbSoju = findViewById(R.id.cbSoju);
        cbEtc = findViewById(R.id.cbEtc);
        cbEtc2 = findViewById(R.id.cbEtc2);
        cbAlone = findViewById(R.id.cbAlone);
        cbRectal = findViewById(R.id.cbRectal);
        cbAcquaintance = findViewById(R.id.cbAcquaintance);
        cbAgree = findViewById(R.id.cbAgree);
        cbLover = findViewById(R.id.cbLover);
        // seekBar 연결
        seekBar = findViewById(R.id.seekBar);
        // 버튼 연결
        btnPass = findViewById(R.id.btnPass);
        btnOk = findViewById(R.id.btnOk);
        // 도수 표시 연결
        txtDosu = findViewById(R.id.txtDosu);

        // seekbar 값 변경시 도수 표시
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtDosu.setText(String.valueOf(progress));


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 변수에 저장
                alcoholDegree = seekBar.getProgress();
                Log.i("alcoholDegree", String.valueOf(alcoholDegree));
            }
        });
        // todo : Log는 나중에 지워야함
        // alcoholType 체크박스 값 저장
        cbMakgeolli.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                alcoholType.add("막걸리");
            } else {
                alcoholType.remove("막걸리");
            }
            Log.i("alcoholType", alcoholType.toString());
        });
        cbBeer.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                alcoholType.add("맥주");
            } else {
                alcoholType.remove("맥주");
            }
            Log.i("alcoholType", alcoholType.toString());
        });
        cbWine.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                alcoholType.add("와인");
            } else {
                alcoholType.remove("와인");
            }
            Log.i("alcoholType", alcoholType.toString());
        });
        cbLiquor.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                alcoholType.add("양주");
            } else {
                alcoholType.remove("양주");
            }
            Log.i("alcoholType", alcoholType.toString());
        });
        cbSoju.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                alcoholType.add("소주");
            } else {
                alcoholType.remove("소주");
            }
            Log.i("alcoholType", alcoholType.toString());
        });
        cbEtc.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                alcoholType.add("기타");
            } else {
                alcoholType.remove("기타");
            }
            Log.i("alcoholType", alcoholType.toString());
        });
        // alcoholWith 체크박스 값 저장
        cbAlone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                alcoholWith.add("혼자");
            } else {
                alcoholWith.remove("혼자");
            }
            Log.i("alcoholWith", alcoholWith.toString());
        });
        cbRectal.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                alcoholWith.add("친구");
            } else {
                alcoholWith.remove("친구");
            }
            Log.i("alcoholWith", alcoholWith.toString());
        });
        cbAcquaintance.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                alcoholWith.add("지인");
            } else {
                alcoholWith.remove("지인");
            }
            Log.i("alcoholWith", alcoholWith.toString());
        });
        cbFamily.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                alcoholWith.add("가족");
            } else {
                alcoholWith.remove("가족");
            }
            Log.i("alcoholWith", alcoholWith.toString());
        });
        cbFriend.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                alcoholWith.add("친구");
            } else {
                alcoholWith.remove("친구");
            }
            Log.i("alcoholWith", alcoholWith.toString());
        });
        cbLover.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                alcoholWith.add("연인");
            } else {
                alcoholWith.remove("연인");
            }
            Log.i("alcoholWith", alcoholWith.toString());
        });

        cbEtc2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                alcoholType.add("기타");
            } else {
                alcoholType.remove("기타");
            }
            Log.i("alcoholType", alcoholType.toString());
        });
        // 건너뛰기를 누르면 메인화면으로 이동 후 종료
        btnPass.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        // 완료 버튼을 눌르면 해당데이터를 서버로 전송
        btnOk.setOnClickListener(v -> {
            // 정보제공 동의에 체크하지않았으면 알러트 다이얼로그를 띄운다
            if (!cbAgree.isChecked()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("알림");
                builder.setMessage("정보제공에 동의해주세요");
                builder.setPositiveButton("확인", (dialog, which) -> {
                });
                builder.show();

                return;

            }
            // todo : 서버로 UserInfo 객체 전송
            // todo : 데이터 전송 후 메인화면으로 이동
            // Retrofit 인스턴스를 생성하고 이를 사용하여 API 인터페이스의 인스턴스를 생성합니다.
            //여기서 baseUrl메서드는 모든 API 호출에 대한 기본 URL을 지정합니다.
            // 메소드 addConverterFactory는 Java 오브젝트와 JSON 사이의 변환에 사용할 변환기를 지정합니다.
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            // Retrofit 인스턴스를 사용하여 API 인터페이스의 구현을 만듭니다.
            UserInfoApi userInfoApi = retrofit.create(UserInfoApi.class);

            // API 인터페이스 인스턴스를 사용하여 API를 호출합니다.
            UserInfo userInfo = new UserInfo();
            userInfo.setAlcoholDegree(alcoholDegree);
            userInfo.setAlcoholType(alcoholType);
            userInfo.setAlcoholWith(alcoholWith);

            Call<Void> call = userInfoApi.sendUserInfoData(userInfo);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.i("성공", "성공");
                    // handle success
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.i("실패", "실패");
                    // handle failure
                }
            });

//            요약하면
//            Retrofit 2를 사용하여 데이터를 서버로 보내는 합리적인 방법은
//            데이터를 나타내는 데이터 모델 클래스를 정의하고 API 끝점을 정의하는 인터페이스를 만들고
//            Retrofit 인스턴스를 만들고 이를 사용하여 API 인스턴스를 만드는 것입니다.
//            인터페이스를 사용하고 API 인터페이스 인스턴스를 사용하여 API를 호출합니다.




            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }

}
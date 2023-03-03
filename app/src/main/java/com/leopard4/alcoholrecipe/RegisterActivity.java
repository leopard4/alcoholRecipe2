package com.leopard4.alcoholrecipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Layout;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.leopard4.alcoholrecipe.api.NetworkClient;
import com.leopard4.alcoholrecipe.api.UserApi;
import com.leopard4.alcoholrecipe.config.Config;
import com.leopard4.alcoholrecipe.model.RegisterInfo;
import com.leopard4.alcoholrecipe.model.Res;
import com.leopard4.alcoholrecipe.model.User;
import com.leopard4.alcoholrecipe.model.UserRes;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    EditText editEmail, editPassword, editPassword2, editNickname;
    Button btnOk, btnMatches,btnMatches2, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editPassword2 = findViewById(R.id.editPassword2);
        editNickname = findViewById(R.id.editNickname);
        btnOk = findViewById(R.id.btnOk);
        btnMatches = findViewById(R.id.btnMatches);
        btnLogin = findViewById(R.id.btnLogin);

        // 1.다른곳을 클릭하면 키패드를 내리기위해 (3단계)
        // 현재의 뷰를 먼저 연결해준다
        View view = this.getCurrentFocus();
        // 2.레이아웃 부분을 클릭하면
        findViewById(R.id.registerLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 3.키패드를 내린다
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editEmail.getWindowToken(), 0);
            }
        });

        // editEmail의 포커스가 사라졌을 때 이메일 형식 체크
        editEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){ // 포커스가 사라졌을 때
                    String email = editEmail.getText().toString().trim();
                    Pattern pattern = Patterns.EMAIL_ADDRESS;
                    if(!pattern.matcher(email).matches()){
                        // setError메세지를 펼쳐서 보여준다
                        editEmail.setError("이메일 형식이 아닙니다.");
                    }
                }
            }

        });
        //todo : 아이디 중복 체크
        // 서버에 요청해서 중복되는 아이디가 있는지 확인
        // 중복되는 아이디가 있으면 alert로 알려주고
        // 없으면 회원가입 진행
//        btnMatches.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String email = editEmail.getText().toString().trim();
//                if(email.length() == 0){
//                    // Alert 다이얼로그를 띄운다
//                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//                    builder.setTitle("알림");
//                    builder.setMessage("이메일을 입력해주세요.");
//                    builder.setPositiveButton("확인", null);
//                    builder.show();
//                    return;
//                }
//                // 이메일 형식 체크
//                Pattern pattern = Patterns.EMAIL_ADDRESS;
//                if(!pattern.matcher(email).matches()){
//                    // Alert 다이얼로그를 띄운다
//                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//                    builder.setTitle("알림");
//                    builder.setMessage("이메일 형식이 아닙니다.");
//                    builder.setPositiveButton("확인", null);
//                    builder.show();
//                    return;
//                }
//                // todo : 서버에 요청 (이메일 중복체크 API를 서버에서 개발후 진행)
//                dialog = ProgressDialog.show(RegisterActivity.this, "", "잠시만 기다려주세요...", true);
//                Retrofit retrofit = NetworkClient.getRetrofitClient(RegisterActivity.this);
//                UserApi userApi = retrofit.create(UserApi.class);
//                Call<Res> call = userApi.checkEmail(email);
//                call.enqueue(new Callback<Res>() {
//                    @Override
//                    public void onResponse(Call<Res> call, Response<Res> response) {
//                        dialog.dismiss();
//                        if(response.isSuccessful()){
//                            Res res = response.body();
//                            if(res != null){
//                                if(res.isSuccess()){
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//                                    builder.setTitle("알림");
//                                    builder.setMessage("사용 가능한 이메일입니다.");
//                                    builder.setPositiveButton("확인", null);
//                                    builder.show();
//                                }else{
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//                                    builder.setTitle("알림");
//                                    builder.setMessage("이미 사용중인 이메일입니다.");
//                                    builder.setPositiveButton("확인", null);
//                                    builder.show();
//                                }
//                            }
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<Res> call, Throwable t) {
//                        dialog.dismiss();
//                        Toast.makeText(RegisterActivity.this, "서버와 통신중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
        // editNickName의 포커스가 사라졌을 때 닉네임 길이 체크
        editNickname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){ // 포커스가 사라졌을 때
                    String username = editNickname.getText().toString().trim();
                    if(username.length() < 2 || username.length() > 12){
                        editNickname.setError("닉네임 길이는 2~12글자입니다.");
                    }
                }
            }
        });

        // todo: 닉네임 중복체크


        // editPassword의 포커스가 사라졌을 때 비밀번호 길이 체크
        editPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){ // 포커스가 사라졌을 때
                    String password = editPassword.getText().toString().trim();
                    if(password.length() < 4 || password.length() > 12){
                        editPassword.setError("비밀번호 길이는 4~12글자입니다.");
                    }
                }
            }
        });
        // editPassword2의 포커스가 사라졌을 때 비밀번호 확인 체크
        editPassword2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){ // 포커스가 사라졌을 때
                    String password = editPassword.getText().toString().trim();
                    String password2 = editPassword2.getText().toString().trim();
                    if (!password.equals(password2)) {
                        editPassword2.setError("비밀번호가 일치하지 않습니다.");
                    }
                }
            }
        });

        // 완료버튼을 눌렀을시 이상이없는지 체크하고 서버에 회원가입 요청
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 이메일 가져와서 형식 체크
                String email = editEmail.getText().toString().trim();
                Pattern pattern = Patterns.EMAIL_ADDRESS;
                if(!pattern.matcher(email).matches()){
                    // setError메세지를 펼쳐서 보여준다
                    editEmail.setError("이메일 형식이 아닙니다.");
                    return;
                }

                // 비밀번호 체크
                String password = editPassword.getText().toString().trim();
                String password2 = editPassword2.getText().toString().trim();
                // 우리 기획에는 비번길이가 4~12 만 허용
                if(password.length() < 4 || password.length() > 12){
                    editPassword.setError("비밀번호 길이를 확인하세요.");
                    return;
                }
                // 비밀번호 확인과 같은지 체크
                if (!password.equals(password2)) {
                    editPassword2.setError("비밀번호가 일치하지 않습니다.");
                    return;
                }
                // 닉네임 체크
                String username = editNickname.getText().toString().trim();
                if(username.length() < 2 || username.length() > 12){
                    Toast.makeText(RegisterActivity.this, "닉네임 길이를 확인하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 1. 다이얼로그를 화면에 보여준다.
                showProgress("회원가입 중입니다...");

                // 2-1. 레트로핏 변수 생성
                Retrofit retrofit =
                        NetworkClient.getRetrofitClient(RegisterActivity.this);
                UserApi api = retrofit.create(UserApi.class); // 레트로핏으로 서버에 요청할 객체 생성


                User user = new User(username, email, password); // User 객체 생성
                Call<UserRes> call = api.register(user); // 서버에 요청

                call.enqueue(new Callback<UserRes>() { // 비동기로 서버에 요청
                    @Override
                    public void onResponse(@NonNull Call<UserRes> call, @NonNull Response<UserRes> response) {
                        dismissProgress(); // 다이얼로그 사라짐

                        if(response.isSuccessful()) {

                            UserRes res = response.body(); // 응답받은 데이터 == UserRes 객체

//                            res.getAccess_token(); // 토큰값
                            SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString(Config.ACCESS_TOKEN, res.getAccess_token());
                            editor.apply(); // 저장

                            Intent intent = new Intent(RegisterActivity.this, RegisterInfoActivity.class);
                            startActivity(intent);
                            finish();

                        }else{
                            Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserRes> call, Throwable t) {
                        dismissProgress(); // 다이얼로그 사라짐
                    }
                });




            }
        });

        // 로그인 화면으로 이동
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    // 로직처리가 시작되면 화면에 보여지는 함수
    void showProgress(String message){
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(message);
        dialog.show();
    }
    // 로직처리가 끝나면 화면에서 사라지는 함수
    void dismissProgress(){
        dialog.dismiss();
    }
}
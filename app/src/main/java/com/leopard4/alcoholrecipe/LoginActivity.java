package com.leopard4.alcoholrecipe;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.leopard4.alcoholrecipe.api.NetworkClient;
import com.leopard4.alcoholrecipe.api.UserApi;
import com.leopard4.alcoholrecipe.config.Config;
import com.leopard4.alcoholrecipe.model.User;
import com.leopard4.alcoholrecipe.model.UserRes;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    EditText editEmail;
    EditText editPassword;
    Button btnLogin;
    TextView txtRegister;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnOk);
        txtRegister = findViewById(R.id.txtRegister);

        // 1.다른곳을 클릭하면 키패드를 내리기위해 (3단계)
        // 현재의 뷰를 먼저 연결해준다
        View view = this.getCurrentFocus();
        // 2.레이아웃 부분을 클릭하면
        findViewById(R.id.LoginLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 3.키패드를 내린다
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editEmail.getWindowToken(), 0);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString().trim();
                Pattern pattern = Patterns.EMAIL_ADDRESS;

                if(pattern.matcher(email).matches() == false){
                    Toast.makeText(LoginActivity.this, "이메일 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String password = editPassword.getText().toString().trim();
                // 우리 기획에는 비번길이가 4~12 만 허용
                if(password.length() < 4 || password.length() > 12){
                    Toast.makeText(LoginActivity.this, "비번 길이를 확인하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                showProgress("로그인 중입니다.");

                Retrofit retrofit = NetworkClient.getRetrofitClient(LoginActivity.this);
                UserApi api = retrofit.create(UserApi.class);

                User user = new User(email, password);

                Call<UserRes> call = api.login(user);
                call.enqueue(new Callback<UserRes>() {
                    @Override
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                        dismissProgress();

                        if(response.isSuccessful()){


                            UserRes res = response.body();

                            SharedPreferences sp =
                                    getApplication().getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString(Config.ACCESS_TOKEN, res.getAccess_token() );
                            editor.apply();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else if (response.code() == 400) {
                            Toast.makeText(LoginActivity.this, "회원가입이 되어있지 않거나 비번이 틀렸습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            Toast.makeText(LoginActivity.this, "정상적으로 처리되지 않았습니다.", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<UserRes> call, Throwable t) {
                        dismissProgress();
                        Toast.makeText(LoginActivity.this, "정상적으로 처리되지 않았습니다.", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }
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
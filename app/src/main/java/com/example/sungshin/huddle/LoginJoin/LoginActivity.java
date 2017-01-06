package com.example.sungshin.huddle.LoginJoin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sungshin.huddle.Application.ApplicationController;
import com.example.sungshin.huddle.MainActivity;
import com.example.sungshin.huddle.Network.NetworkService;
import com.huddle.huddle.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnJoin;
    EditText IdEdit, PasswdEdit;


    Uri data;
    NetworkService service;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById((R.id.btnLogin));
        btnJoin = (Button) findViewById(R.id.btnJoin);
        IdEdit = (EditText) findViewById(R.id.ID);
        PasswdEdit = (EditText) findViewById(R.id.Passwd);


        // 로그인버튼을 눌렀을 때, 사용자에게 로그인 중이라는 것을 보여줄 필요가 있습니다.
        mProgressDialog = new ProgressDialog(LoginActivity.this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("로그인중...");
        mProgressDialog.setIndeterminate(true);

        //미리 retrofit를 bulid 한 것을 가져온다.
        service = ApplicationController.getInstance().getNetworkService();

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (IdEdit.length() == 0 || PasswdEdit.length() == 0) {
                    //아이디나 패스워드를 입력하지 않은경우
                    Toast.makeText(getApplicationContext(), "아이디와 패스워드를 입력 주세요", Toast.LENGTH_SHORT).show();
                } else {
                    //진행하는 다이얼로그 보여주기
                    mProgressDialog.show();

                    //id와 passswd를 body에 담아서 보내기 위한것
                    //        RequestBody id = RequestBody.create(MediaType.parse("Body"), IdEdit.getText().toString());
                    //      RequestBody pw = RequestBody.create(MediaType.parse("Body"), PasswdEdit.getText().toString());


                    // TODO: 2016. 11. 21. 등록 요청

                    LoginListData data = new LoginListData(IdEdit.getText().toString(), PasswdEdit.getText().toString());
                    Call<LoginResult> requestLogin = service.requestLogin(data);

                    requestLogin.enqueue(new Callback<LoginResult>() {
                        @Override
                        public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {

                            if(response.isSuccessful()){

                                if (response.body().result.equals("SUCCESS")) {
                                    Log.i("myTag",response.body().result);
                                    mProgressDialog.dismiss();

                                    Toast.makeText(getApplicationContext(), "로그인성공", Toast.LENGTH_SHORT).show();
                                    Intent intentMain = new Intent(LoginActivity.this,MainActivity.class);

                                    ApplicationController.getInstance().myInfo = response.body().info;

                                    startActivity(intentMain);

                                } else if (response.body().result.equals("FAIL")) {
                                    mProgressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "로그인실패", Toast.LENGTH_SHORT).show();
                                } else {
                                    mProgressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "예상치 못한 오류 발생", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                        @Override
                        public void onFailure(Call<LoginResult> call, Throwable t) {
                            Log.i("myTag",t.toString());
                            Log.i("myTag",t.getMessage());

                            Toast.makeText(getApplicationContext(), "로그인 완전 실패", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }
                    });
                }
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            Intent intent = new Intent (LoginActivity.this , JoinActivity.class);
            startActivity(intent);

            }

        });
    }
}
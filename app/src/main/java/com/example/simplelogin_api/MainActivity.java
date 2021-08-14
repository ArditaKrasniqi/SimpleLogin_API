package com.example.simplelogin_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private Button btn_SignUp;
    private Button btn_LogIn;
    private Button btn_showUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_SignUp = findViewById(R.id.btn_SignUp);
        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSignUpClicked();

            }
        });

        btn_LogIn = findViewById(R.id.btn_Login);
        btn_LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogInClicked();
            }
        });

        btn_showUsers = findViewById(R.id.btn_ShowUsers);
        btn_showUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                

            }
        });

    }


    private void btnLogInClicked() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.getLoginInformation("eve.holt@reqres.in","cityslicka");
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
            Log.e(TAG, "onResponse: "+response.code());
            Log.e(TAG, "onResponse: "+response.body().getToken());

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage());

            }
        });
    }

    private void btnSignUpClicked(){
      ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<User> call = apiInterface.getUserInformation("Ardita","Android Developer");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.e(TAG, "onResponse:"+response.code());
                Log.e(TAG,"onResponse: name:"+response.body().getName());
                Log.e(TAG,"onResponse: job:"+response.body().getJob());
                Log.e(TAG,"onResponse: id:"+response.body().getId());
                Log.e(TAG,"onResponse: createdAt:"+response.body().getCreatedAt());


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG,"onFailure:"+t.getMessage());

            }
        });
    }
}
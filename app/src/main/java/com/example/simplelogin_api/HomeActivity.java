package com.example.simplelogin_api;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.content.ContentValues.TAG;

public class HomeActivity extends AppCompatActivity {
    private Button btn_userList;
    private RecyclerView recyclerView;
    private ArrayList<Data> arrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();

        btn_userList = findViewById(R.id.btn_userList);
        btn_userList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

                Call<UserModel> call = apiInterface.getData();
                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        Log.e(TAG, "onResponse: code: "+response.code());
                        ArrayList<UserModel.data> data = response.body().getData();


                        for (UserModel.data data1 : data){
//                            Log.e(TAG,"on response: ids:"+data1.getId());
//                            Log.e(TAG,"on response: names:"+data1.getFirst_name());
                            Log.e(TAG, "on response: emails: "+data1.getEmail());
                            arrayList.add(new Data(data1.getFirst_name(),data1.getEmail()));


                        }

                        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(arrayList);
                        recyclerView.setAdapter(recyclerAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                    }
                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Log.e(TAG, "on failure: "+ t.getMessage());
                    }
                });
            }
        });





    }
}
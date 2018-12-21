package com.github.alexdochioiu.example.screens;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.github.alexdochioiu.daggersharpener.SharpComponent;
import com.github.alexdochioiu.example.MyApplication;
import com.github.alexdochioiu.example.R;
import com.github.alexdochioiu.example.models.GithubRepo;
import com.github.alexdochioiu.example.network.GithubService;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SharpComponent(sharpDependencies = {MyApplication.class})
public class HomeActivity extends AppCompatActivity {

    private Unbinder unbinder;
    @BindView(R.id.activity_main_rv)
    RecyclerView recyclerView;

    @Inject
    GithubService githubService;
    @Inject
    ReposAdapter adapter;

    Call<List<GithubRepo>> reposCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        unbinder = ButterKnife.bind(this);

        DaggerSharpHomeActivityComponent.builder()
                .sharpMyApplicationComponent(((MyApplication) getApplication()).getComponent())
                .build()
                .inject(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        reposCall = githubService.getAllRepos();
        reposCall.enqueue(new Callback<List<GithubRepo>>() {
            @Override
            public void onResponse(@NonNull Call<List<GithubRepo>> call, @NonNull Response<List<GithubRepo>> response) {
                adapter.setRepoList(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<GithubRepo>> call, @NonNull Throwable t) {
                Toast.makeText(HomeActivity.this, "Error getting repos " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (unbinder != null) {
            unbinder.unbind();
        }
        if (reposCall != null) {
            reposCall.cancel();
        }
    }
}

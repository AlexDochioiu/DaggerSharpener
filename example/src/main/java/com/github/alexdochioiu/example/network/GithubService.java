package com.github.alexdochioiu.example.network;

import com.github.alexdochioiu.example.models.GithubRepo;
import com.github.alexdochioiu.example.models.GithubUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Alexandru Iustin Dochioiu on 21-Dec-18
 */
public interface GithubService {
    @GET("users/{username}/repos")
    Call<List<GithubRepo>> getReposForUser(@Path("username") String username);

    @GET("repositories")
    Call<List<GithubRepo>> getAllRepos();

    @GET("users/{username}")
    Call<GithubUser> getUser(@Path("username") String username);
}

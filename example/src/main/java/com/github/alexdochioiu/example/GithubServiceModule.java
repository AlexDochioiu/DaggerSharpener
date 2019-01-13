package com.github.alexdochioiu.example;

import com.fatboyindustrial.gsonjodatime.DateTimeConverter;
import com.github.alexdochioiu.example.network.GithubService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alexandru Iustin Dochioiu on 21-Dec-18
 */
@Module(includes = NetworkModule.class)
class GithubServiceModule {
    @Provides
    @SharpMyApplicationScope
    GithubService githubService(Retrofit gitHubRetrofit) {
        return gitHubRetrofit.create(GithubService.class);
    }

    @Provides
    @SharpMyApplicationScope
    Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeConverter());
        return gsonBuilder.create();
    }

    @Provides
    @SharpMyApplicationScope
    Retrofit retrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl("https://api.github.com/")
                .build();
    }
}

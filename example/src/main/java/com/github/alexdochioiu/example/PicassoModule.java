package com.github.alexdochioiu.example;

import android.content.Context;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by Alexandru Iustin Dochioiu on 21-Dec-18
 */
@Module(includes = {NetworkModule.class, ContextModule.class})
class PicassoModule {
    @Provides
    @SharpMyApplicationScope
    @Named("myPicasso")
    Picasso picasso(Context context, OkHttp3Downloader okHttp3Downloader) {
        return new Picasso.Builder(context)
                .downloader(okHttp3Downloader)
                .build();
    }

    @Provides
    @SharpMyApplicationScope
    OkHttp3Downloader okHttp3Downloader(OkHttpClient okHttpClient) {
        return new OkHttp3Downloader(okHttpClient);
    }
}

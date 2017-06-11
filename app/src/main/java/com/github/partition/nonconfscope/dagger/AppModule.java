package com.github.partition.nonconfscope.dagger;

import com.github.partition.nonconfscope.repos.GithubService;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

  @Provides
  @AppScope
  OkHttpClient provideOkHttpClient() {
    return new OkHttpClient();
  }

  @Provides
  @AppScope
  Retrofit provideRetrofit(OkHttpClient okHttpClient) {
    return new Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  @Provides
  @AppScope
  GithubService provideGithubService(Retrofit retrofit) {
    return retrofit.create(GithubService.class);
  }
}

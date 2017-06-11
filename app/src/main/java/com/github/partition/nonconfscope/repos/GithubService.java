package com.github.partition.nonconfscope.repos;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface GithubService {

  @GET("/users/nasa/repos")
  Observable<List<Repo>> getRepos();
}

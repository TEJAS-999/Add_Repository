package com.example.add_room_repository.api;

import com.example.add_room_repository.db.entity.RepositoryModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApi {

    @GET("/repos/{owner}/{repo}")
    Call<RepositoryModel> getRepository(@Path("owner") String owner, @Path("repo") String repo);

}

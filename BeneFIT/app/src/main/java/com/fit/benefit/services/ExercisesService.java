// interface used for Gson and Retrofit
package com.fit.benefit.services;

import com.fit.benefit.models.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ExercisesService {
    @GET("exercise")
    Call<Response> getExercise(@Query("language") int lan,
                               @Query("limit") int limit,
                               @Query("offset") int offset,
                               @Header("Authorization") String apiKey);

}